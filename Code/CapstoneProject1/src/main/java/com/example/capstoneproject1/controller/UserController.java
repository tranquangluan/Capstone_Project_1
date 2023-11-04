package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.UserEditForm;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.UserResponse;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
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

    @Autowired
    CloudinaryService cloudinaryService;

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
                    return new ResponseEntity<>(new ResponseMessage(1, jwtTokenProvider.getMessage(), 401), HttpStatus.NOT_FOUND);
                }
            } else
                return new ResponseEntity<>(new ResponseMessage(1,jwtTokenProvider.getMessage(), 401), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage( 1, e.getMessage(), 401), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @PutMapping(value = "/edit-profile" , consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    } , produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    public @ResponseBody ResponseEntity<?> editUser(UserEditForm userEditForm , @RequestParam(name = "userId") Integer userId, @RequestParam(required=false, value="avartar") MultipartFile avartar) {


        try {
            Optional<User> user = userRepository.findById(userId);

            if( !user.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1 ,"User Not Found!", 404), HttpStatus.NOT_FOUND);
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
            if(userEditForm.getGender() != null)
                user.get().setGender(userEditForm.getGender());

            if(userEditForm.getDateOfBirth() != null) {
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
                    return new ResponseEntity<>(new ResponseMessage(1,"Old Password Was Incorrect!", 401), HttpStatus.ACCEPTED);
                }
            }


            if(avartar != null) {
                if (!avartar.isEmpty()) {
                    // delete in cloudinary before updating
                    if (user.get().getAvatarId() != null) {
                        cloudinaryService.delete( user.get().getAvatarId() );
                    }
                    Map result = cloudinaryService.upload(avartar);
                    System.out.println(result);
                    String imageUrl = (String) result.get("secure_url");
                    String imageId = (String) result.get("public_id");
                    user.get().setAvatar(imageUrl);
                    user.get().setAvatarId(imageId);
                } else {
                    // delete image in stored when user not sent image when request url
                    if (user.get().getAvatarId() != null) {
                        cloudinaryService.delete( user.get().getAvatarId() );
                    }
                }

            }
            userRepository.save(user.get());
            return new ResponseEntity<>(new ResponseMessage(0, "Update Profile Successfully!",201), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}
