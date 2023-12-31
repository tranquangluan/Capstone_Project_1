package com.example.capstoneproject1.services.space;

import com.example.capstoneproject1.dto.request.SpaceUpdateForm;
import com.example.capstoneproject1.dto.response.space.PageSpace;
import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.CategoryRepository;
import com.example.capstoneproject1.repository.SpaceRepository;
import com.example.capstoneproject1.repository.StatusRepository;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.services.image.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageServiceImpl imageServiceImpl;


    @Override
    public Space findSpaceById(Integer id) {
        return spaceRepository.findById(id).orElse(null);
    }


    @Override
    public Integer countSpaceByCategoryId(Integer categoryId) {
        return spaceRepository.countByCategoryId_Id(categoryId);
    }

    @Override
    public PageSpace getAllSpaces(Integer ownerId, Integer spaceId, Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, BigDecimal priceFrom, BigDecimal priceTo, Float areaFrom, Float areaTo, Integer topRate) {
        try {
            System.out.println("SpaceIds >>>>>>>>>>>>>>>"+spaceId);
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                pageable = PageRequest.of(pageNo, pageSize, sort);

            }
            Page<Space> pageSpace = spaceRepository.findSpacesByConditions(status, categoryId, province, district, ward, priceFrom, priceTo, areaFrom, areaTo, spaceId, ownerId,topRate, pageable);
            Integer totalPages = pageSpace.getTotalPages();
            List<Space> listSpaces = pageSpace.getContent();
            return new PageSpace(totalPages, listSpaces);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new PageSpace();
        }

    }

    @Override
    public Optional<Space> findById(Integer spaceId) {
        return spaceRepository.findById(spaceId);
    }

    @Override
    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    @Override
    public void updateSpace(SpaceUpdateForm spaceUpdateForm, Integer spaceId) {
        String title = spaceUpdateForm.getTitle();
        BigDecimal price = spaceUpdateForm.getPrice();
        String description = spaceUpdateForm.getDescription();
        Integer bathroomNumbers = spaceUpdateForm.getBathroomsNumber();
        Integer bedroomNumbers = spaceUpdateForm.getBedroomsNumber();
        Integer peopleNumbers = spaceUpdateForm.getPeopleNumber();
        Float area = spaceUpdateForm.getArea();
        String province = spaceUpdateForm.getProvince();
        String district = spaceUpdateForm.getDistrict();
        String ward = spaceUpdateForm.getWard();
        String address = spaceUpdateForm.getAddress();

        CategorySpace categorySpace = null;
        if (spaceUpdateForm.getCategoryId() != null) {
            Optional<CategorySpace> categorySpaceOptional = categoryRepository.findById(spaceUpdateForm.getCategoryId());
            if (categorySpaceOptional.isPresent()) {
                categorySpace = categorySpaceOptional.get();
            }
        }

        spaceRepository.updateSpace(title, price, description, bathroomNumbers, bedroomNumbers, peopleNumbers, area, province, district, ward, address, categorySpace, spaceId);

    }

    @Override
    public Boolean deleteSpace(Space space) throws IOException {
        if (space != null) {
            imageServiceImpl.deleteImageBySpaceId(space);
            spaceRepository.deleteById(space.getId());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Space> findByIdAndOwnerId(Integer spaceId, User owner) {
        return spaceRepository.findSpaceByIdAndOwnerId(spaceId, owner);
    }

    @Override
    public Boolean updateStatus(Integer spaceId, Status status) {
        try {
            spaceRepository.updateStatus(spaceId, status);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi Đây: >>>>" + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean updateOwnerId(Integer spaceId, User ownerId) {
        try {
            spaceRepository.updateOwnerId(spaceId, ownerId);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi Đây: >>>>" + e.getMessage());
            return false;
        }
    }

    @Override
    public Integer countSpaceByStatus0() {
        return spaceRepository.countSpaceByStatus0();
    }

    @Override
    public Integer countSpaceByStatus1() {
        return spaceRepository.countSpaceByStatus1();
    }

    @Override
    public Integer countSpaceByStatus2() {
        return spaceRepository.countSpaceByStatus2();
    }

    @Override
    public Integer countSpaceByStatus3() {
        return spaceRepository.countSpaceByStatus3();
    }

    @Override
    public Integer countSpaceByStatus4() {
        return spaceRepository.countSpaceByStatus4();
    }

    @Override
    public Integer countSpaceByStatus5() {
        return spaceRepository.countSpaceByStatus5();
    }

    @Override
    public Page<Space> getPostSpaceByConditions(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer categoryId, String province, String district, String ward, Integer ownerId) {
        try {
            if (sortDir.toUpperCase().equals("DESC")) {
                Sort sort = Sort.by(sortBy).descending();
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Space> pageSpace = spaceRepository.getPostSpaceByConditions(categoryId, province, district, ward, ownerId, pageable);
                return pageSpace;
            } else if (sortDir.toUpperCase().equals("ASC")) {
                Sort sort = Sort.by(sortBy).ascending();
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Space> pageSpace = spaceRepository.getPostSpaceByConditions(categoryId, province, district, ward, ownerId, pageable);
                return pageSpace;
            } else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Space> pageSpace = spaceRepository.getPostSpaceByConditions(categoryId, province, district, ward, ownerId, pageable);
                return pageSpace;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public List<Object[]> getStaticPostByDate(Integer date) {
        return spaceRepository.getStaticPostByDate(date);
    }
    @Override
    public List<Object[]> getStaticPostByMonthAndYear(Integer month, Integer year) {
        return spaceRepository.getStaticPostByMonthAndYear(month,year);
    }

    @Override
    public List<Object[]> getStaticPostByYear(Integer year) {
        return spaceRepository.getStaticPostByYear(year);
    }

    @Override
    public List<Object[]> getStaticBookingByDate(Integer date) {
        return spaceRepository.getStaticBookingByDate(date);
    }

    @Override
    public List<Object[]> getStaticBookingByMonthAndYear(Integer month, Integer year) {
        return spaceRepository.getStaticBookingByMonthAndYear(month,year);
    }

    @Override
    public List<Object[]> getStaticBookingByYear(Integer year) {
        return spaceRepository.getStaticBookingByYear(year);
    }

    @Override
    public Map<String, Integer> convertToMap(List<Object[]> result) {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        for (Object[] row : result) {
            String date = row[0].toString();
            Integer count = Integer.parseInt(row[1].toString());
            resultMap.put(date, count);
        }
        return resultMap;
    }




}
