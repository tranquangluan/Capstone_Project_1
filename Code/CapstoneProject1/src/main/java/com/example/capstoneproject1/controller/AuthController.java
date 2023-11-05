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
import com.example.capstoneproject1.services.AuthService;
import com.example.capstoneproject1.services.UserServiceImpl;
import com.example.capstoneproject1.services.impl.RoleServiceImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
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

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping(value = "/register", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    } , produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> register(SignUpForm signUpForm) {
        System.out.printf(signUpForm.getEmail());
        if (userServiceImpl.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage(1,"Email Already Exists!",409), HttpStatus.CONFLICT);
        }
        User user = new User(signUpForm.getName(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getProvince(), signUpForm.getDistrict(), signUpForm.getWard(), signUpForm.getAddress());
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleServiceImpl.findByRoleCode("R3").orElseThrow(() -> new RuntimeException("Role not found!"));
        roles.add(roleUser);
        user.setRoles(roles);
        userServiceImpl.save(user);
        return new ResponseEntity<>(new ResponseMessage(0,"Created Account Successfully!", 200), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    } , produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> login(SignInForm signInForm) {
        Boolean user = authService.findUserByEmail(signInForm.getEmail());
        if (!user)
            return new ResponseEntity<>(new ResponseMessage(1,"Email Hasn't Been Registered!", 401), HttpStatus.NOT_FOUND);

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
    } , produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> refreshToken(RefreshTokenForm refreshTokenForm) {
        try {
            if ( refreshTokenForm.getRefreshToken() == null || refreshTokenForm.getRefreshToken().isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage(1, "Required Refresh Token!",400 ), HttpStatus.BAD_REQUEST);
            }

            // validate the refresh token
            if (jwtTokenProvider.validateToken(refreshTokenForm.getRefreshToken())) {
                String userName = jwtTokenProvider.getUserEmailFromToken(refreshTokenForm.getRefreshToken());
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                UserPrinciple userPrinciple = new UserPrinciple(userDetails.getUsername(), userDetails.getAuthorities());
                // generate a new token by user principal
                String newToken = jwtTokenProvider.generateTokenByUserPrinciple(userPrinciple);

                if(newToken.isEmpty()) {
                    return new ResponseEntity<>(new ResponseMessage(1,"Fail to generate new access token. Let's try more time!", 400), HttpStatus.BAD_REQUEST);
                }else {
                    return new ResponseEntity<>(new RefreshTokenResponse("Generate access token successfully!", newToken), HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<>(new ResponseMessage(1,jwtTokenProvider.getMessage(), 400), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1,"Refresh token is missing", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response) {
        // Get authenticated user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Cancel the user's session
            logoutHandler.logout(request, response, authentication);
            return ResponseEntity.ok(new ResponseMessage(0,"Logout Successful", 200));
        }
        return ResponseEntity.ok(new ResponseMessage(1, "No user is logged in", 409));
    }
}
