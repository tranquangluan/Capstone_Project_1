package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.UserEditForm;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.UserResponse;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyAuthority('User')")
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String bearerToken = request.getHeader("Authorization");
            String token = "";
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token =  bearerToken.substring(7);
            }
            if (jwtTokenProvider.validateToken(token)) {
                String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
                Optional<User> userOptional = userRepository.findByEmail(userEmail);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponseMessage(jwtTokenProvider.getMessage()), HttpStatus.NOT_FOUND);
                }
            } else
                return new ResponseEntity<>(new ResponseMessage(jwtTokenProvider.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @PutMapping(value = "/edit-profile" , produces = "application/json")
    public ResponseEntity<?> editUser(@Valid @RequestBody UserEditForm userEditForm ,@RequestParam String userId) {

        try {

            Optional<User> user = userRepository.findById(Integer. parseInt(userId));

            if( !user.isPresent()) {
                return new ResponseEntity<>("User Not Found!", HttpStatus.NOT_FOUND);
            }

            if (userEditForm.getFullName() != null) {
                user.get().setName(userEditForm.getFullName());
            }

            if(userEditForm.getAddress() != null) {
                user.get().setProvince(userEditForm.getProvince());
                user.get().setDistrict(userEditForm.getDistrict());
                user.get().setWard(userEditForm.getWard());
                user.get().setAddress(userEditForm.getAddress());
            }

            if(userEditForm.getDateOfBirth() != null) {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//                Date date = dateFormat.parse(userEditForm.getDateOfBirth());
                java.sql.Date sqlDate = new java.sql.Date(userEditForm.getDateOfBirth().getTime());
                user.get().setDateOfBirth(sqlDate);
            }

            if(userEditForm.getPhone() != null) {
                user.get().setPhone(userEditForm.getPhone());
            }

            if (userEditForm.getOldPassword() != null && userEditForm.getNewPassword() != null) {
                String oldPassword = userEditForm.getOldPassword();
                String storedPassword =  user.get().getPassword();
                if (passwordEncoder.matches(oldPassword,storedPassword)) {
                        user.get().setPassword(passwordEncoder.encode(userEditForm.getNewPassword()));
                }else {
                    return new ResponseEntity<>(new ResponseMessage("Old Password Was Incorrect!"), HttpStatus.ACCEPTED);
                }
            }

            if (userEditForm.getAvatar() != null) {
                user.get().setAvatar(userEditForm.getAvatar());
            }

            userRepository.save(user.get());
            return new ResponseEntity<>(new ResponseMessage("Update Profile Successfully!"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
