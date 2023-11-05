package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Favourite;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;

import java.util.List;

public interface FavoriteService {

    List<Favourite> favoritesByUserId(Integer userId, Integer pageNo, Integer pageSize);
    Boolean existsByUserId(Integer userId);
    Boolean existsBySpaceId(Integer spaceId);

    Favourite saveFavourite(Space space, User user);

    Boolean existsBySpaceIdAndUserId(Integer spaceId, Integer userId);
}
