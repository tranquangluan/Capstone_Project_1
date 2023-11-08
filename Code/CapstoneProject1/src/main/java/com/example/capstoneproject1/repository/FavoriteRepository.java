package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface FavoriteRepository extends JpaRepository<Favourite,Integer> {

    Page<Favourite> findAllByUserId(Integer userId,Pageable pageable);

    Boolean existsByUserId(Integer userId);
    Boolean existsBySpaceId(Integer spaceId);
    Boolean existsByIdAndUserId(Integer favoriteId, Integer userId);
    Boolean existsBySpaceIdAndUserId(Integer spaceId, Integer userId);
    void deleteBySpaceIdAndUserId(Integer spaceId, Integer userId);
    @Transactional
    void deleteByUserId(Integer userId);
    @Transactional
    void deleteAllByUserId(Integer userId);
}
