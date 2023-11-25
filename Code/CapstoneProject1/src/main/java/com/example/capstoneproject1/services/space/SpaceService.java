package com.example.capstoneproject1.services.space;

import com.example.capstoneproject1.dto.request.SpaceUpdateForm;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.SpaceStatus;
import com.example.capstoneproject1.models.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SpaceService {

    Space findSpaceById(Integer id);

    Integer countSpaceByCategoryId(Integer categoryId);

    List<Space> getAllSpaces(Integer ownerId, Integer spaceId, Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, BigDecimal priceFrom, BigDecimal PriceTo, Float areaFrom, Float areaTo);

    Optional<Space> findById(Integer spaceId);

    Space saveSpace(Space space);

    void updateSpace(SpaceUpdateForm spaceUpdateForm, Integer spaceId);

    Boolean deleteSpace(Space space) throws IOException;

    Optional<Space> findByIdAndOwnerId(Integer spaceId, User owner);

    Boolean updateStatus(Integer spaceId, SpaceStatus spaceStatus);

}
