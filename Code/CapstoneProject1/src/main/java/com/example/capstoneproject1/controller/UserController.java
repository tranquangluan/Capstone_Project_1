package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.UserEditForm;
import com.example.capstoneproject1.dto.response.user.ListUsersResponse;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.user.UpdateAnDeleteUserResponse;
import com.example.capstoneproject1.dto.response.user.UserResponse;
import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.CloudinaryService;
import com.example.capstoneproject1.services.user.UserService;
import com.example.capstoneproject1.services.role.RoleService;
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
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PreAuthorize("hasAnyAuthority('User','Owner')")
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        try {

            String bearerToken = request.getHeader("Authorization");
            String token = "";
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
            }
            if (jwtTokenProvider.validateToken(token)) {
                String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
                Optional<User> userOptional = userRepository.findByEmail(userEmail);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponseMessage(1, jwtTokenProvider.getMessage(), 404), HttpStatus.NOT_FOUND);
                }
            } else
                return new ResponseEntity<>(new ResponseMessage(1, jwtTokenProvider.getMessage(), 400), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @PutMapping(value = "/edit-profile", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> editUser(UserEditForm userEditForm, @RequestParam(required = false, value = "avartar") MultipartFile avartar, HttpServletRequest request) {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userRepository.findByEmail(userEmail);

            if (!userOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            }

            if (userEditForm.getFullName() != null) {
                userOptional.get().setName(userEditForm.getFullName());
            }

            if (userEditForm.getAddress() != null) {
                userOptional.get().setProvince(userEditForm.getProvince());
                userOptional.get().setDistrict(userEditForm.getDistrict());
                userOptional.get().setWard(userEditForm.getWard());
                userOptional.get().setAddress(userEditForm.getAddress());
            }
            if (userEditForm.getGender() != null)
                userOptional.get().setGender(userEditForm.getGender());

            if (userEditForm.getDateOfBirth() != null) {
                java.sql.Date sqlDate = new java.sql.Date(userEditForm.getDateOfBirth().getTime());
                userOptional.get().setDateOfBirth(sqlDate);
            }

            if (userEditForm.getPhone() != null) {
                userOptional.get().setPhone(userEditForm.getPhone());
            }

            if (userEditForm.getOldPassword() != null && userEditForm.getNewPassword() != null) {
                String oldPassword = userEditForm.getOldPassword();
                String storedPassword = userOptional.get().getPassword();
                if (passwordEncoder.matches(oldPassword, storedPassword)) {
                    userOptional.get().setPassword(passwordEncoder.encode(userEditForm.getNewPassword()));
                } else {
                    return new ResponseEntity<>(new ResponseMessage(1, "Old Password Was Incorrect!", 400), HttpStatus.BAD_REQUEST);
                }
            }


            if (avartar != null) {
                if (!avartar.isEmpty()) {
                    // delete in cloudinary before updating
                    if (userOptional.get().getAvatarId() != null) {
                        cloudinaryService.delete(userOptional.get().getAvatarId());
                    }
                    Map result = cloudinaryService.upload(avartar);
                    System.out.println(result);
                    String imageUrl = (String) result.get("secure_url");
                    String imageId = (String) result.get("public_id");
                    userOptional.get().setAvatar(imageUrl);
                    userOptional.get().setAvatarId(imageId);
                } else {
                    // delete image in stored when userOptional not sent image when request url
                    if (userOptional.get().getAvatarId() != null) {
                        cloudinaryService.delete(userOptional.get().getAvatarId());
                    }
                }
            }

            userRepository.save(userOptional.get());
            return new ResponseEntity<>(new ResponseMessage(0, "Update Profile Successfully!", 200), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0", required = false, name = "page") Integer page,
                                         @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                         @RequestParam(defaultValue = "email", required = false, name = "sortBy") String sortBy,
                                         @RequestParam(defaultValue = "ASC", required = false, name = "sortDir") String sortDir,
                                         @RequestParam(defaultValue = "", required = false, name = "searchByEmail") String searchByEmail,
                                         @RequestParam(defaultValue = "", required = false, name = "searchByName") String searchByName,
                                         @RequestParam(required = false, name = "searchById") Integer searchById) {
        try {
            List<User> listUsers = userService.getAllUsers(searchById, searchByEmail, searchByName, page, limit, sortBy, sortDir);
            if (!listUsers.isEmpty())
                return new ResponseEntity<>(new ListUsersResponse(0, "Get List Users Successfully!", listUsers, 200), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/update-user")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = true, name = "userId") Integer userId,
                                         @RequestParam(required = true, name = "role") String role, HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> adminOptional = userRepository.findByEmail(userEmail);
            Optional<User> userOptional = userRepository.findById(userId);
            // user not found
            if( !userOptional.isPresent() || !adminOptional.isPresent() )
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            System.out.println(userOptional.get().getId() + ">>>>" + userId);
            // can not update yourself
            if(Objects.equals(adminOptional.get().getId(), userOptional.get().getId()))
                return new ResponseEntity<>(new ResponseMessage(1, "You cannot update yourself!", 400), HttpStatus.BAD_REQUEST);


            Optional<Role> roleUser = roleService.findByRoleCode(role);
            if(!roleUser.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "Role Not Found!", 404), HttpStatus.NOT_FOUND);
            }
            //  delete old role
            userService.deleteRoleByUserId(userId);

            Set<Role> roles = userOptional.get().getRoles();
            // Set role
            roles.add(roleUser.get());
            userOptional.get().setRoles(roles);
            // Update User
            userService.save(userOptional.get());
            return new ResponseEntity<>(new UpdateAnDeleteUserResponse(0, "Update User Successful!", userOptional.get() ,200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> getAllUsers(@RequestParam( required = true, name = "userId") Integer userId, HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userRepository.findById(userId);
            // user not found
            if( !userOptional.isPresent() )
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            // check can not delete yourself
            if(Objects.equals(userOptional.get().getEmail(), userEmail))
                return new ResponseEntity<>(new ResponseMessage(1, "You cannot delete yourself!", 400), HttpStatus.BAD_REQUEST);

            User userDeleted = userOptional.get();
            // delete user by user id
            if(userService.deleteUserByUserId(userId))
                return new ResponseEntity<>(new UpdateAnDeleteUserResponse(0, "Delete User Successful!",userDeleted ,200), HttpStatus.OK);
            return new ResponseEntity<>(new UpdateAnDeleteUserResponse(1, "Delete User Fail!",400), HttpStatus.BAD_REQUEST);


        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}
