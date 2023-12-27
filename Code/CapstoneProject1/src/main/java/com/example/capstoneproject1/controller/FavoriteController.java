package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.favoutite.FavoriteResponse;
import com.example.capstoneproject1.dto.response.favoutite.ListFavoriteResponse;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.models.Favourite;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.favourite.FavoriteService;
import com.example.capstoneproject1.services.space.SpaceService;
import com.example.capstoneproject1.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @PostMapping(value = "/create-favorite")
    public ResponseEntity<?> createFavorite(@RequestParam(required = true, name = "spaceId") Integer spaceId, HttpServletRequest request) {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);
            Optional<Space> space = spaceService.findById(spaceId);

            if (space.isPresent()) {
                if (userOptional.isPresent()) {
                    // check has been created space in my favorite
                    if (favoriteService.existsBySpaceIdAndUserId(spaceId, userOptional.get().getId())) {
                        return new ResponseEntity<>(new ResponseMessage(1, "This Space Has Been In Your Favorite!", 400), HttpStatus.BAD_REQUEST);
                    } else {
                        Favourite favorite = favoriteService.saveFavourite(space.get(), userOptional.get());
                        return new ResponseEntity<>(new FavoriteResponse(0, "Create Favorite Successful!", true, favorite, 201), HttpStatus.CREATED);
                    }
                } else {
                    return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @GetMapping(value = "/list-favorite")
    public ResponseEntity<?> createFavorite(@RequestParam(required = false, name = "spaceId") Integer spaceId,
                                            @RequestParam(defaultValue = "4", required = false, name = "limit") Integer limit,
                                            @RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                            HttpServletRequest request) {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);
            // check user existence
            if (!userOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            }
            Integer userId = userOptional.get().getId();
            // return when find by spaceId
            if (spaceId != null) {
                if (favoriteService.existsBySpaceIdAndUserId(spaceId, userId)) {
                    return new ResponseEntity<>(new FavoriteResponse(0, "Have space in your favourite!", true, 200), HttpStatus.OK);
                }
                return new ResponseEntity<>(new FavoriteResponse(0, "Have not space in your favourite!!", false, 200), HttpStatus.OK);
            }
            // return list of favorites when not param spaceId
            List<Favourite> listFavorites = favoriteService.favoritesByUserId(userId, page - 1, limit);
            return new ResponseEntity<>(new ListFavoriteResponse(0, "Get List Favorite Successful!", listFavorites.size(), listFavorites, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @DeleteMapping(value = "/delete-favorite")
    public ResponseEntity<?> deleteFavorite(@RequestParam(required = true, name = "favoriteId") Integer favoriteId, HttpServletRequest request) {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);

            if (userOptional.isPresent()) {
                if (favoriteService.existsByFavouriteIdAndUserId(favoriteId, userOptional.get().getId())) {
                    favoriteService.deleteFavourite(favoriteId);
                    return new ResponseEntity<>(new ResponseMessage(0, "Delete Favorite Successful!", 200), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponseMessage(1, "This favorite does not exist in your favorite!", 400), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('User' , 'Admin', 'Owner')")
    @PutMapping(value = "/update-favorite")
    public ResponseEntity<?> updateFavorite(@RequestParam(required = true, name = "spaceId") Integer spaceId, HttpServletRequest request) {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);

            System.out.println(spaceId);
            if (favoriteService.existsBySpaceId(spaceId)) {
                if (userOptional.isPresent()) {
                    // if has been saved then delete it
                    if (favoriteService.existsBySpaceIdAndUserId(spaceId, userOptional.get().getId())) {
                        favoriteService.deleteBySpaceIdAndUserId(spaceId, userOptional.get().getId());
                        return new ResponseEntity<>(new FavoriteResponse(0, "Update Favorite Successful!", false, 200), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new ResponseMessage(1, "Update Favorite Fail Or Does Saved!", 400), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found Or Not Saved!", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


}
