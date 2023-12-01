package com.example.capstoneproject1.services.sharing;

import com.example.capstoneproject1.models.Sharing;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SharingService {
    List<Sharing> getAllSpaces(Integer ownerId,Integer userSharingId, Integer sharingId ,Integer spaceId, Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, BigDecimal priceFrom, BigDecimal PriceTo, Float areaFrom, Float areaTo);
    Optional<Sharing> findById(Integer sharingId);
    Sharing saveSharing(Sharing sharing);

    Boolean existsSharingBySpaceAndUser(Space space, User user);
    Boolean existsSharingByUser(User user);

    Optional<Sharing> findSharingBySpaceAndUser(Space space, User user);

    Boolean deleteSharing(Space space);
}
