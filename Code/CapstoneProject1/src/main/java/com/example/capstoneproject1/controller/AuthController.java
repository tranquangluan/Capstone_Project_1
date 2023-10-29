package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.request.SignInForm;
import com.example.capstoneproject1.dto.request.SignUpForm;
import com.example.capstoneproject1.dto.response.JwtResponse;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.security.userPrincal.UserDetailService;
import com.example.capstoneproject1.security.userPrincal.UserPrinciple;
import com.example.capstoneproject1.services.UserServiceImpl;
import com.example.capstoneproject1.services.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/register" , produces  = "application/json")
    public ResponseEntity<?>  register(@RequestBody SignUpForm signUpForm) {
           if(userServiceImpl.existsByEmail(signUpForm.getEmail())) {
               return new ResponseEntity<> (new ResponseMessage("Email is existed"), HttpStatus.OK );
           }
           User user = new User(signUpForm.getName(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()),signUpForm.getProvince(), signUpForm.getDistrict(), signUpForm.getWard(),signUpForm.getAddress());
           Set<Role> roles = new HashSet<>();
           Role roleUser = roleServiceImpl.findByRoleCode("R3").orElseThrow( () -> new RuntimeException("Role not found"));
           roles.add(roleUser);
           user.setRoles(roles);
           userServiceImpl.save(user);
           return new ResponseEntity<>(new ResponseMessage("Created successfully") ,HttpStatus.OK);
    }


    @PostMapping(value = "/login" , produces  = "application/json")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getEmail(), signInForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse("Login Successful", token,"Bearer", userPrinciple.getAuthorities()));
    }

    @GetMapping(value = "/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userName = jwtTokenProvider.getUserNameFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null , userDetails.getAuthorities() );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                else {
                    throw new RuntimeException("Do not have userDetails");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Refresh token is missing");
        }

    }

}
