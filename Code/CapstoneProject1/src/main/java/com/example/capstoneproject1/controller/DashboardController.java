package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.CountByRoleAndCountPostByStatus;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.space.ListSpaceResponse;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.services.space.SpaceService;
import com.example.capstoneproject1.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@PreAuthorize("hasAnyAuthority('Admin')")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    SpaceService spaceService;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/dashboard")
    public ResponseEntity<?> viewDashBoard() {
        try {
            Map<String, Integer> countOverview = new HashMap<>();
            Integer countAdmin = userService.countUsersRoleAdmin();
            Integer countOwner = userService.countUsersRoleOwner();
            Integer countUser = userService.countUsersRoleUser();
            Integer countPostSpaceStatus0 = spaceService.countSpaceByStatus0();
            Integer countPostSpaceStatus1 = spaceService.countSpaceByStatus1();
            Integer countPostSpaceStatus2 = spaceService.countSpaceByStatus2();
            Integer countPostSpaceStatus3 = spaceService.countSpaceByStatus3();
            Integer countPostSpaceStatus4 = spaceService.countSpaceByStatus4();
            Integer countPostSpaceStatus5 = spaceService.countSpaceByStatus5();
            countOverview.put("NumberAdminAccount", countAdmin);
            countOverview.put("NumberOwnerAccount", countOwner);
            countOverview.put("NumberUserAccount", countUser);
            countOverview.put("NumberPostSpaceNotRent", countPostSpaceStatus0);
            countOverview.put("NumberPostSpaceHadRent", countPostSpaceStatus1);
            countOverview.put("NumberPostSharingSpace", countPostSpaceStatus2);
            countOverview.put("NumberPostSpaceWaitingApproval", countPostSpaceStatus3);
            countOverview.put("NumberTopPostSpace", countPostSpaceStatus4);
            countOverview.put("NumberPostSpaceRejected", countPostSpaceStatus5);
            if (!countOverview.isEmpty()) {
                return new ResponseEntity<>(new CountByRoleAndCountPostByStatus(0, "Count Successfully!", countOverview, 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CountByRoleAndCountPostByStatus(1, "Have problem in count execute!", new HashMap<>(), 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/static")
    public ResponseEntity<?> viewDashBoard(@RequestParam(required = false, name = "categoryId") Integer categoryId,
                                           @RequestParam(required = false, name = "searchByProvince") String searchByProvince,
                                           @RequestParam(required = false, name = "searchByDistrict") String searchByDistrict,
                                           @RequestParam(required = false, name = "searchByWard") String searchByWard,
                                           @RequestParam(required = false, name = "ownerId") Integer ownerId) {
        try {
            return new ResponseEntity<>(new ResponseMessage(1, "", 400), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/recent-post")
    public ResponseEntity<?> viewDashBoard(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                           @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                           @RequestParam(defaultValue = "created_at", required = false, name = "sortBy") String sortBy,
                                           @RequestParam(defaultValue = "DESC", required = false, name = "sortDir") String sortDir,
                                           @RequestParam(required = false, name = "categoryId") Integer categoryId,
                                           @RequestParam(required = false, name = "searchByProvince") String searchByProvince,
                                           @RequestParam(required = false, name = "searchByDistrict") String searchByDistrict,
                                           @RequestParam(required = false, name = "searchByWard") String searchByWard,
                                           @RequestParam(required = false, name = "ownerId") Integer ownerId) {
        try {
            Page<Space> spaceList = spaceService.getPostSpaceByConditions(page - 1, limit, sortBy, sortDir, categoryId, searchByProvince, searchByDistrict, searchByWard, ownerId);
            if (!spaceList.isEmpty()) {
                return new ResponseEntity<>(new ListSpaceResponse(0, "Get Recent Post Successfully!", (int) spaceList.getTotalElements(), spaceList.getTotalPages(), spaceList.getContent(), 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


}
