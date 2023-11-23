package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.request.RefreshTokenForm;
import com.example.capstoneproject1.dto.request.SignInForm;
import com.example.capstoneproject1.dto.request.SignUpForm;
import com.example.capstoneproject1.dto.response.JwtResponse;
import com.example.capstoneproject1.dto.response.RefreshTokenResponse;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.security.userPrincal.UserDetailService;
import com.example.capstoneproject1.security.userPrincal.UserPrinciple;
import com.example.capstoneproject1.services.auth.AuthService;
import com.example.capstoneproject1.services.email.EmailServiceImpl;
import com.example.capstoneproject1.services.user.UserServiceImpl;
import com.example.capstoneproject1.services.role.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RoleServiceImpl roleServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private AuthService authService;
    @Autowired
    EmailServiceImpl emailServiceImpl;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping(value = "/register", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> register(@Valid SignUpForm signUpForm, HttpServletRequest request) {
        if (userServiceImpl.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage(1, "Email Already Exists!", 409), HttpStatus.CONFLICT);
        }
        // send OTP
        HttpSession session = request.getSession(true);
        // save OTP in session
        String otp = generateOTP();
        session.setAttribute("otpEmail", otp);
        // set time for OTP 5p
        session.setMaxInactiveInterval(300);
        // send OTP for email
        emailServiceImpl.sendMailOTP(otp, signUpForm.getEmail(),"Verify Email");
        return new ResponseEntity<>(new ResponseMessage(0, "You Need Verify OTP!", 201), HttpStatus.CREATED);
    }

    @PostMapping(value = "/verify-email", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> verifyEmail(HttpServletRequest request, @Valid SignUpForm signUpForm, @NotNull String otp) {

        try {

            HttpSession session = request.getSession(false);
            if (session != null) {
                //Get OTP from session
                String storedOTP = (String) session.getAttribute("otpEmail");
                // Check if the OTP code the user entered matches
                if (storedOTP != null && storedOTP.equals(otp)) {
                    User user = new User(signUpForm.getName(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getProvince(), signUpForm.getDistrict(), signUpForm.getWard(), signUpForm.getAddress());
                    Set<Role> roles = new HashSet<>();
                    Role roleUser = roleServiceImpl.findByRoleCode("R3").orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(roleUser);
                    user.setRoles(roles);
                    userServiceImpl.save(user);
                    return new ResponseEntity<>(new ResponseMessage(0, "Create Account Successful!", 200), HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(new ResponseMessage(1, "Invalid OTP. Please check your entered code.", 400), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "OTP Has Expired. Please request a new OTP.", 400), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> login(@Valid SignInForm signInForm) {
        System.out.println(signInForm.getEmail());
        System.out.println(signInForm.getPassword());
        Optional<User> userOptional = userServiceImpl.findByEmail(signInForm.getEmail());
        if (!userOptional.isPresent())
            return new ResponseEntity<>(new ResponseMessage(1, "Email Hasn't Been Registered!", 404), HttpStatus.NOT_FOUND);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getEmail(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        if (refreshToken != null)
            authService.saveRefreshToken(signInForm.getEmail(), refreshToken);

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse("Login Successful", token, refreshToken, "Bearer", userPrinciple.getAuthorities()));
    }

    @PostMapping(value = "/refresh-token", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> refreshToken(RefreshTokenForm refreshTokenForm) {
        try {
            if (refreshTokenForm.getRefreshToken() == null || refreshTokenForm.getRefreshToken().isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage(1, "Required Refresh Token!", 400), HttpStatus.BAD_REQUEST);
            }

            // validate the refresh token
            if (jwtTokenProvider.validateToken(refreshTokenForm.getRefreshToken())) {
                String userName = jwtTokenProvider.getUserEmailFromToken(refreshTokenForm.getRefreshToken());
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                UserPrinciple userPrinciple = new UserPrinciple(userDetails.getUsername(), userDetails.getAuthorities());
                // generate a new token by user principal
                String newToken = jwtTokenProvider.generateTokenByUserPrinciple(userPrinciple);

                if (newToken.isEmpty()) {
                    return new ResponseEntity<>(new ResponseMessage(1, "Fail to generate new access token. Let's try more time!", 400), HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>(new RefreshTokenResponse("Generate access token successfully!", newToken), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, jwtTokenProvider.getMessage(), 401), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, "Refresh token is missing", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response) {
        // Get authenticated user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Cancel the user's session
            logoutHandler.logout(request, response, authentication);
            return ResponseEntity.ok(new ResponseMessage(0, "Logout Successful", 200));
        }
        return ResponseEntity.ok(new ResponseMessage(1, "No user is logged in", 409));
    }


    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    @PostMapping(value = "/forgot-password", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> forgotPassword(HttpServletRequest request, String email) {

        try {
            Optional<User> userOptional = userServiceImpl.findByEmail(email);

            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Email not existed!", 404), HttpStatus.NOT_FOUND);
            HttpSession session = request.getSession(true);

            // save OTP in session
            String otp = generateOTP();
            session.setAttribute("otp", otp);
            // set time for OTP 5p
            session.setMaxInactiveInterval(300);

            // send OTP for email
            emailServiceImpl.sendMailOTP(otp, email,"Forgot Password");
            return new ResponseEntity<>(new ResponseMessage(0,"Send OTP Successful!", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/reset-password", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> resetPassword(HttpServletRequest request, @NotNull String password, @NotNull String otp, @NotNull String email) {

        try {
            Optional<User> userOptional = userServiceImpl.findByEmail(email);
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Email not existed!", 404), HttpStatus.NOT_FOUND);
            User user = userOptional.get();
            HttpSession session = request.getSession(false);
            if (session != null) {
                //Get OTP from session
                String storedOTP = (String) session.getAttribute("otp");
                // Check if the OTP code the user entered matches
                if (storedOTP != null && storedOTP.equals(otp)) {
                    // Set pass id
                    user.setPassword(passwordEncoder.encode(password));
                    userServiceImpl.save(user);
                    return new ResponseEntity<>(new ResponseMessage(0, "Reset Password Successful!", 200), HttpStatus.OK);
                }else {
                        return new ResponseEntity<>(new ResponseMessage(1, "Invalid OTP. Please check your entered code.", 400), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "OTP Has Expired. Please request a new OTP.", 400), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


}
