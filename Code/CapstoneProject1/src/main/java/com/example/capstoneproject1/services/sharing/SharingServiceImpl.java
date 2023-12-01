package com.example.capstoneproject1.services.sharing;

import com.example.capstoneproject1.models.Sharing;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.SharingRepository;
import com.example.capstoneproject1.repository.SpaceRepository;
import com.example.capstoneproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SharingServiceImpl implements SharingService {

    @Autowired
    SharingRepository sharingRepository;

    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Sharing> getAllSpaces(Integer ownerId, Integer userSharingId, Integer sharingId, Integer spaceId, Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, BigDecimal priceFrom, BigDecimal priceTo, Float areaFrom, Float areaTo) {
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("spaceId." + sortBy).ascending()
                        : Sort.by("spaceId." + sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Sharing> pageSpace = sharingRepository.findSpacesByConditions(status, categoryId, province, district, ward, priceFrom, priceTo, areaFrom, areaTo, spaceId, sharingId, ownerId, userSharingId, pageable);
                return pageSpace.getContent();
            } else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Sharing> pageSpace = sharingRepository.findSpacesByConditions(status, categoryId, province, district, ward, priceFrom, priceTo, areaFrom, areaTo, spaceId, sharingId, ownerId, userSharingId, pageable);
                return pageSpace.getContent();
            }
        } catch (Exception e) {
            System.out.println("Lỗi" + e.getMessage());
            return new ArrayList<>();
        }

    }

    @Override
    public Optional<Sharing> findById(Integer sharingId) {
        return sharingRepository.findById(sharingId);
    }

    @Override
    public Sharing saveSharing(Sharing sharing) {
        return sharingRepository.save(sharing);
    }

    @Override
    public Boolean existsSharingBySpaceAndUser(Space space, User user) {
        Optional<Sharing> sharingOptional = sharingRepository.findBySpaceIdAndUserId(space, user);
        return sharingOptional.isPresent();
    }

    @Override
    public Boolean existsSharingByUser(User user) {
        Optional<Sharing> sharingOptional = sharingRepository.findByUserId(user);
        return sharingOptional.isPresent();
    }

    @Override
    public Optional<Sharing> findSharingBySpaceAndUser(Space space, User user) {
        return sharingRepository.findBySpaceIdAndUserId(space, user);
    }

    @Override
    public Boolean deleteSharing(Space space) {
        Optional<Sharing> sharing = sharingRepository.findBySpaceId(space);
        if (sharing.isPresent()) {
            sharingRepository.delete(sharing.get());
            return true;
        }
        return false;
    }
}
