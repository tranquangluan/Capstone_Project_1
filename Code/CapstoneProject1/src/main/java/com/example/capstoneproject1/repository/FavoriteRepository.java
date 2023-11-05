package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavoriteRepository extends JpaRepository<Favourite,Integer> {

    Page<Favourite> findAllByUserId(Integer userId,Pageable pageable);

    Boolean existsByUserId(Integer userId);
    Boolean existsBySpaceId(Integer spaceId);
    Boolean existsBySpaceIdAndUserId(Integer spaceId, Integer userId);

}
