package com.example.capstoneproject1.services.impl;

import com.example.capstoneproject1.models.Favourite;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.FavoriteRepository;
import com.example.capstoneproject1.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;


    @Override
    public List<Favourite> favoritesByUserId(Integer userId , Integer pageNo, Integer pageSize) {

        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Favourite> favouritesByUserId = favoriteRepository.findAllByUserId(userId,pageable);

            return favouritesByUserId.getContent();
        }catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Boolean existsByUserId(Integer userId) {
        return favoriteRepository.existsByUserId(userId);
    }

    @Override
    public Boolean existsBySpaceId(Integer spaceId) {
        return favoriteRepository.existsBySpaceId(spaceId);
    }

    @Override
    public Favourite saveFavourite(Space space, User user) {
        Favourite favorite = new Favourite(space, user);
       return  favoriteRepository.save(favorite);
    }

    @Override
    public Boolean existsBySpaceIdAndUserId(Integer spaceId, Integer userId) {
        return favoriteRepository.existsBySpaceIdAndUserId(spaceId, userId);
    }

}
