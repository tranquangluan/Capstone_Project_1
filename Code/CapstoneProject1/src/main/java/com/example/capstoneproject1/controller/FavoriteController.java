package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.FavoriteResponse;
import com.example.capstoneproject1.dto.response.ListFavoriteResponse;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.models.Favourite;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.services.FavoriteService;
import com.example.capstoneproject1.services.SpaceService;
import com.example.capstoneproject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/favorites")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    SpaceService spaceService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyAuthority('User', 'Owner')")
    @PostMapping(value = "/create-favorite")
    public ResponseEntity<?> createFavorite(@RequestParam(required = true, name = "spaceId") Integer spaceId, @RequestParam(required = true, name = "userId") Integer userId) {

        try {
            Optional<User> user = userService.findById(userId);
            Optional<Space> space = spaceService.findById(spaceId);

            if (favoriteService.existsBySpaceIdAndUserId(spaceId, userId))
                return new ResponseEntity<>(new ResponseMessage(1, "This Space Has Been In Your Favorite!", 401), HttpStatus.BAD_REQUEST);


            if (space.isPresent()) {
                if (user.isPresent()) {
                    Favourite favorite = favoriteService.saveFavourite(space.get(), user.get());
                    return new ResponseEntity<>(new FavoriteResponse(0, "Create Favorite Successful!", favorite, 201), HttpStatus.OK);
                } else
                    return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 401), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('User', 'Owner')")
    @GetMapping(value = "/list-favorite")
    public ResponseEntity<?> createFavorite(@RequestParam(required = true, name = "userId") Integer userId,
                                            @RequestParam(defaultValue = "4",required = false, name = "limit") Integer limit,
                                            @RequestParam(defaultValue = "0" ,required = false, name = "page") Integer page) {

        try {
            if( !favoriteService.existsByUserId(userId))
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            List<Favourite> listFavorites = favoriteService.favoritesByUserId(userId, page, limit);
            return new ResponseEntity<>(new ListFavoriteResponse(0,"Get List Favorite Successful!",listFavorites.size(), listFavorites, 200), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 401), HttpStatus.BAD_REQUEST);
        }
    }
}
