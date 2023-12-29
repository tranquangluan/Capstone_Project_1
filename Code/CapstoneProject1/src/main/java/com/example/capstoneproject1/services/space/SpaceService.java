package com.example.capstoneproject1.services.space;

import com.example.capstoneproject1.dto.request.SpaceUpdateForm;
import com.example.capstoneproject1.dto.response.space.PageSpace;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SpaceService {

    Space findSpaceById(Integer id);

    Integer countSpaceByCategoryId(Integer categoryId);

    PageSpace getAllSpaces(Integer ownerId, Integer spaceId, Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, BigDecimal priceFrom, BigDecimal PriceTo, Float areaFrom, Float areaTo, Integer topRate);

    Optional<Space> findById(Integer spaceId);

    Space saveSpace(Space space);

    void updateSpace(SpaceUpdateForm spaceUpdateForm, Integer spaceId);

    Boolean deleteSpace(Space space) throws IOException;

    Optional<Space> findByIdAndOwnerId(Integer spaceId, User owner);

    Boolean updateStatus(Integer spaceId, Status status);
    Boolean updateOwnerId(Integer spaceId, User ownerId);

    Integer countSpaceByStatus0();
    Integer countSpaceByStatus1();
    Integer countSpaceByStatus2();
    Integer countSpaceByStatus3();
    Integer countSpaceByStatus4();
    Integer countSpaceByStatus5();
    Page<Space> getPostSpaceByConditions(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, Integer ownerId);
    List<Object[]> getStaticDashboardByDate(Integer date);
    List<Object[]> getStaticDashboardByMonthAndYear(Integer month, Integer year);
    List<Object[]> getStaticDashboardByYear(Integer year);
    Map<String, Integer> convertToMap(List<Object[]> result);
}
