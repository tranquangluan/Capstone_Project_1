//package com.example.capstoneproject1.controller;
//
//import com.example.capstoneproject1.dto.response.ResponseMessage;
//import com.example.capstoneproject1.dto.response.sharing.ListSharesResponse;
//import com.example.capstoneproject1.dto.response.sharing.SharingResponse;
//import com.example.capstoneproject1.dto.response.space.ListSpaceResponse;
//import com.example.capstoneproject1.models.Sharing;
//import com.example.capstoneproject1.models.Space;
//import com.example.capstoneproject1.models.SpaceStatus;
//import com.example.capstoneproject1.models.User;
//import com.example.capstoneproject1.repository.CategorySpaceRepository;
//import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
//import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
//import com.example.capstoneproject1.services.CloudinaryService;
//import com.example.capstoneproject1.services.sharing.SharingService;
//import com.example.capstoneproject1.services.space.SpaceService;
//import com.example.capstoneproject1.services.status.StatusServiceImpl;
//import com.example.capstoneproject1.services.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.constraints.NotNull;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/spaces")
//@CrossOrigin(origins = "http://localhost:3000")
//public class SharingController {
//
//    @Autowired
//    SharingService sharingServiceImpl;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    CloudinaryService cloudinaryService;
//
//    @Autowired
//    CategorySpaceRepository categorySpaceRepository;
//
//    @Autowired
//    JwtTokenFilter jwtTokenFilter;
//
//    @Autowired
//    JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    StatusServiceImpl statusService;
//
//    @Autowired
//    SpaceService spaceService;
//
//    @GetMapping(value = "/list-sharing")
//    public ResponseEntity<?> getSpacesSharing(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
//                                              @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
//                                              @RequestParam(defaultValue = "title", required = false, name = "sortBy") String sortBy,
//                                              @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
//                                              @RequestParam(required = false, name = "categoryId") Integer categoryId,
//                                              @RequestParam(required = false, name = "searchByProvince") String searchByProvince,
//                                              @RequestParam(required = false, name = "searchByDistrict") String searchByDistrict,
//                                              @RequestParam(required = false, name = "searchByWard") String searchByWard,
//                                              @RequestParam(required = false, name = "priceFrom") BigDecimal priceFrom,
//                                              @RequestParam(required = false, name = "priceTo") BigDecimal priceTo,
//                                              @RequestParam(required = false, name = "areaFrom") Float areaFrom,
//                                              @RequestParam(required = false, name = "areaTo") Float areaTo,
//                                              @RequestParam(required = false, name = "spaceId") Integer spaceId,
//                                              @RequestParam(required = false, name = "sharingId") Integer sharingId,
//                                              @RequestParam(required = false, name = "userSharingId") Integer userSharingId,
//                                              @RequestParam(required = false, name = "ownerId") Integer ownerId) {
//        try {
//            Integer statusSharing = 2;
//            List<Sharing> listSpaces = sharingServiceImpl.getAllSpaces(ownerId, userSharingId, sharingId, spaceId, statusSharing, page - 1, limit, sortBy, sortDir, categoryId, searchByProvince, searchByDistrict, searchByWard, priceFrom, priceTo, areaFrom, areaTo);
//            if (!listSpaces.isEmpty()) {
//                return new ResponseEntity<>(new ListSharesResponse(0, "Get Spaces Sharing Successfully", listSpaces.size(), listSpaces, 200), HttpStatus.OK);
//            } else
//                return new ResponseEntity<>(new ListSpaceResponse(1, "Space Not Found", 0, 404), HttpStatus.NOT_FOUND);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(new ResponseMessage(1, "Get Spaces Sharing Fail", 400), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    public Optional<User> getUserFromToken(HttpServletRequest request) {
//        String token = jwtTokenFilter.getJwtFromRequest(request);
//        String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
//        return userService.findByEmail(userEmail);
//    }
//
//
//
//    @PostMapping(value = "/create-sharing", consumes = {
//            MediaType.APPLICATION_JSON_VALUE,
//            MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    }, produces = {
//            MediaType.APPLICATION_JSON_VALUE
//    })
//    public @ResponseBody ResponseEntity<?> createSharing(@RequestParam(name = "spaceId") Integer spaceId, @NotNull String content ,  HttpServletRequest request) {
//        try {
//            // handle check user
//            Optional<User> userOptional = getUserFromToken(request);
//            if (!userOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "User not found!", 404), HttpStatus.NOT_FOUND);
//            // handle check space
//            Optional<Space> spaceOptional = spaceService.findById(spaceId);
//            if (!spaceOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "Space not found!", 404), HttpStatus.NOT_FOUND);
//            // handle check status
//            Integer statusCode = 2;
//            Optional<SpaceStatus> statusOptional = statusService.findBySpaceStatusId(statusCode);
//            if (!statusOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "Status not found!", 404), HttpStatus.NOT_FOUND);
//
//            // handle check user has sharing
//            Boolean existsSharing = sharingServiceImpl.existsSharingBySpaceAndUser(spaceOptional.get(), userOptional.get());
//            if(existsSharing)
//                return new ResponseEntity<>(new ResponseMessage(1, "You have shared this space!", 400), HttpStatus.BAD_REQUEST);
//
//            // handle check user has sharing
//            Boolean existsSharingByUser = sharingServiceImpl.existsSharingByUser(userOptional.get());
//            if(existsSharingByUser)
//                return new ResponseEntity<>(new ResponseMessage(1, "You can only share once!", 400), HttpStatus.BAD_REQUEST);
//
//
//            Sharing sharing = new Sharing(spaceOptional.get(),userOptional.get(),content,statusOptional.get());
//            Sharing sharingCreated =  sharingServiceImpl.saveSharing(sharing);
//            return new ResponseEntity<>(new SharingResponse(0, "Create sharing successful!", sharingCreated, 401), HttpStatus.CREATED);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(new ResponseMessage(1, "Create Sharing Fail", 400), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping(value = "/update-sharing", consumes = {
//            MediaType.APPLICATION_JSON_VALUE,
//            MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    }, produces = {
//            MediaType.APPLICATION_JSON_VALUE
//    })
//    public ResponseEntity<?> updateSharing(@RequestParam(required = false, name = "spaceId") Integer spaceId , @NotNull String content , HttpServletRequest request) {
//        try {
//            // handle check user
//            Optional<User> userOptional = getUserFromToken(request);
//            if (!userOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "User not found!", 404), HttpStatus.NOT_FOUND);
//            // handle check space
//            Optional<Space> spaceOptional = spaceService.findById(spaceId);
//            if (!spaceOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "Space not found!", 404), HttpStatus.NOT_FOUND);
//            // handle check status
//            Integer statusCode = 2;
//            Optional<SpaceStatus> statusOptional = statusService.findBySpaceStatusId(statusCode);
//            if (!statusOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "Status not found!", 404), HttpStatus.NOT_FOUND);
//
//            // handle check user has sharing
//            Boolean existsSharing = sharingServiceImpl.existsSharingBySpaceAndUser(spaceOptional.get(), userOptional.get());
//            if(!existsSharing)
//                return new ResponseEntity<>(new ResponseMessage(1, "You have not shared this space!", 400), HttpStatus.BAD_REQUEST);
//
//           // handle
//            Optional<Sharing> sharingOptional = sharingServiceImpl.findSharingBySpaceAndUser(spaceOptional.get() ,userOptional.get());
//
//            Sharing sharing = sharingOptional.get();
//            sharing.setInfoSharing(content);
//            Sharing sharingUpdated =  sharingServiceImpl.saveSharing(sharing);
//
//            return new ResponseEntity<>(new SharingResponse(0, "Update sharing successful!", sharingUpdated, 200), HttpStatus.OK);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(new ResponseMessage(1, "Update Sharing Fail", 400), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping(value = "/delete-sharing")
//    public ResponseEntity<?> deleteSharing(@RequestParam(required = false, name = "spaceId") Integer spaceId , HttpServletRequest request) {
//        try {
//
//            // handle check user
//            Optional<User> userOptional = getUserFromToken(request);
//            if (!userOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "User not found!", 404), HttpStatus.NOT_FOUND);
//            // handle check space
//            Optional<Space> spaceOptional = spaceService.findById(spaceId);
//            if (!spaceOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "Space not found!", 404), HttpStatus.NOT_FOUND);
//            // handle check status
//            Integer statusCode = 2;
//            Optional<SpaceStatus> statusOptional = statusService.findBySpaceStatusId(statusCode);
//            if (!statusOptional.isPresent())
//                return new ResponseEntity<>(new ResponseMessage(1, "Status not found!", 404), HttpStatus.NOT_FOUND);
//
//            // handle check user has sharing
//            Boolean existsSharing = sharingServiceImpl.existsSharingBySpaceAndUser(spaceOptional.get(), userOptional.get());
//            if(!existsSharing)
//                return new ResponseEntity<>(new ResponseMessage(1, "This space is not yours to share!", 400), HttpStatus.BAD_REQUEST);
//
//            Boolean isDeleteSharing = sharingServiceImpl.deleteSharing(spaceOptional.get());
//            if(isDeleteSharing)
//                return new ResponseEntity<>(new ResponseMessage(0, "Delete sharing successful!", 200), HttpStatus.OK);
//            return new ResponseEntity<>(new ResponseMessage(1, "Delete sharing fail!", 400), HttpStatus.BAD_REQUEST);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(new ResponseMessage(1, "Delete sharing fail!", 400), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//}
