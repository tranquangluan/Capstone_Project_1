package com.example.capstoneproject1.services.space;

import com.example.capstoneproject1.dto.request.SpaceUpdateForm;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SpaceService {

    Space findSpaceById(Integer id);

    Integer countSpaceByCategoryId(Integer categoryId);

    Page<Space> getAllSpaces(Integer ownerId, Integer spaceId, Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, BigDecimal priceFrom, BigDecimal PriceTo, Float areaFrom, Float areaTo);

    Optional<Space> findById(Integer spaceId);

    Space saveSpace(Space space);

    void updateSpace(SpaceUpdateForm spaceUpdateForm, Integer spaceId);

    Boolean deleteSpace(Space space) throws IOException;

    Optional<Space> findByIdAndOwnerId(Integer spaceId, User owner);

    Integer countSpaceByStatus0();
    Integer countSpaceByStatus1();
    Integer countSpaceByStatus2();
    Integer countSpaceByStatus3();
    Integer countSpaceByStatus4();
    Integer countSpaceByStatus5();
    List<Space> getPostSpaceByAmount(Integer number, Integer number1);

}
