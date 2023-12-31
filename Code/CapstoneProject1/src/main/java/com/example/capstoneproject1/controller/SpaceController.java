package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.SpaceForm;
import com.example.capstoneproject1.dto.request.SpaceUpdateForm;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.space.ListSpaceResponse;
import com.example.capstoneproject1.dto.response.space.PageSpace;
import com.example.capstoneproject1.dto.response.space.SpaceResponse;
import com.example.capstoneproject1.dto.response.user.UpdateAnDeleteUserResponse;
import com.example.capstoneproject1.models.*;
import com.example.capstoneproject1.repository.ImageRepository;
import com.example.capstoneproject1.repository.StatusRepository;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.CloudinaryService;
import com.example.capstoneproject1.services.category.CategoryService;
import com.example.capstoneproject1.services.notification.NotificationService;
import com.example.capstoneproject1.services.space.SpaceServiceImpl;
import com.example.capstoneproject1.services.status.StatusService;
import com.example.capstoneproject1.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/spaces")
//@CrossOrigin(origins = "http://localhost:3000")
public class SpaceController {

    @Autowired
    SpaceServiceImpl spaceServiceImpl;

    @Autowired
    UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    StatusService statusService;
    @Autowired
    NotificationService notificationService;


    @GetMapping(value = "/list-spaces")
    public ResponseEntity<?> getSpaces(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                       @RequestParam(defaultValue = "6", required = false, name = "limit") Integer limit,
                                       @RequestParam(defaultValue = "title", required = false, name = "sortBy") String sortBy,
                                       @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                       @RequestParam(required = false, name = "status") Integer status,
                                       @RequestParam(required = false, name = "categoryId") Integer categoryId,
                                       @RequestParam(required = false, name = "searchByProvince") String searchByProvince,
                                       @RequestParam(required = false, name = "searchByDistrict") String searchByDistrict,
                                       @RequestParam(required = false, name = "searchByWard") String searchByWard,
                                       @RequestParam(required = false, name = "priceFrom") BigDecimal priceFrom,
                                       @RequestParam(required = false, name = "priceTo") BigDecimal priceTo,
                                       @RequestParam(required = false, name = "areaFrom") Float areaFrom,
                                       @RequestParam(required = false, name = "areaTo") Float areaTo,
                                       @RequestParam(required = false, name = "spaceId") Integer spaceId,
                                       @RequestParam(required = false, name = "ownerId") Integer ownerId,
                                       @RequestParam(required = false, name = "topRate") Integer topRate) {
        try {
            PageSpace pageSpace = spaceServiceImpl.getAllSpaces(ownerId, spaceId, status, page - 1, limit, sortBy, sortDir, categoryId, searchByProvince, searchByDistrict, searchByWard, priceFrom, priceTo, areaFrom, areaTo,topRate);
            Integer totalPages = pageSpace.getTotalPages();
            List<Space> listSpaces = pageSpace.getListSpaces();
            if (!listSpaces.isEmpty())
                return new ResponseEntity<>(new ListSpaceResponse(0, "Get Spaces Successfully", listSpaces.size(), totalPages, listSpaces, 200), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ListSpaceResponse(1, "Space Not Found", 0, 404), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAnyAuthority('Admin','Owner')")
    @PostMapping(value = "/create-space", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> createSpace(@Valid SpaceForm spaceForm, @RequestParam(value = "images") List<MultipartFile> images, HttpServletRequest request) throws IOException {

        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            Optional<CategorySpace> categorySpaceOptional = categoryService.findById(spaceForm.getCategoryId());
            if (!categorySpaceOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Category Not Found!", 404), HttpStatus.NOT_FOUND);

            // find default status pending
            Optional<Status> statusOptional = statusService.findBySpaceStatusId(3);
            if (!statusOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Status Not Found!", 404), HttpStatus.NOT_FOUND);

            // Set properties from spaceForm
            Space space = new Space(spaceForm.getTitle(), statusOptional.get(), spaceForm.getPrice(), spaceForm.getDescription(), spaceForm.getBathroomsNumber(), spaceForm.getBedroomsNumber(), spaceForm.getPeopleNumber(), spaceForm.getArea(), spaceForm.getProvince(), spaceForm.getDistrict(), spaceForm.getWard(), spaceForm.getAddress(), categorySpaceOptional.get(), userOptional.get());
            // Save the space entity to get the ID
            Space savedSpace = spaceServiceImpl.saveSpace(space);
            // handle upload image on cloudinary server
            List<Map> results = cloudinaryService.uploadMultiple(images);
            if (results.size() > 0) {
                // loop and save info in image Object
                for (Map result : results) {
                    Image image = new Image();
                    String imageUrl = (String) result.get("secure_url");
                    String imageId = (String) result.get("public_id");
                    // save the imageId and imageUrl into db
                    image.setImageId(imageId);
                    image.setImageUrl(imageUrl);
                    // connect relationship Image and Space
                    image.setSpaceId(savedSpace);
                    // save the image in the database
                    imageRepository.save(image);
                }
                spaceServiceImpl.saveSpace(savedSpace);
            } else {
                return new ResponseEntity<>(new ResponseMessage(1, "Required image in space!", 400), HttpStatus.BAD_REQUEST);
            }
            // get user has role admin
            List<User> adminsList = userRepository.findByRoleCode("R1");
            User userSender = userOptional.get();
            // create message
            for (User admin : adminsList) {
                Boolean isCreated = notificationService.createMessage(userSender, admin, "New Space Created", "A new space has been created.");
                if(!isCreated)
                    return new ResponseEntity<>(new SpaceResponse(0, "Create new space fail!", 400), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new SpaceResponse(0, "Create new space successful!", savedSpace, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new SpaceResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin','Owner')")
    @PutMapping(value = "/update-space", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> updateSpace(@Valid SpaceUpdateForm spaceUpdateForm, @RequestParam(required = false, value = "images") List<MultipartFile> images, @RequestParam(value = "spaceId") Integer spaceId, HttpServletRequest request) throws IOException {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userRepository.findByEmail(userEmail);

            if (!userOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            }

            if (spaceUpdateForm.getCategoryId() != null) {
                Optional<CategorySpace> categorySpaceOptional = categoryService.findById(spaceUpdateForm.getCategoryId());
                if (!categorySpaceOptional.isPresent()) {
                    return new ResponseEntity<>(new ResponseMessage(1, "Category Not Found!", 404), HttpStatus.NOT_FOUND);
                }
            }

            Optional<Space> spaceOptional = spaceServiceImpl.findById(spaceId);
            if (!spaceOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);

            // delete Image in repository
            String[] imageIds = spaceUpdateForm.getImagesId();
            if (imageIds != null) {
                for (String imageId : imageIds) {
                    if (imageRepository.existsById(imageId)) {
                        imageRepository.deleteById(imageId);
                        cloudinaryService.delete(imageId);
                    }
                }
            }
            // handle fields
            spaceServiceImpl.updateSpace(spaceUpdateForm, spaceId);
            // handle image
            if (images != null) {
                List<Map> results = cloudinaryService.uploadMultiple(images);
                if (!results.isEmpty()) {
                    // loop and save info in image Object
                    for (Map result : results) {
                        Image image = new Image();
                        String imageUrl = (String) result.get("secure_url");
                        String imageId = (String) result.get("public_id");
                        // save the imageId and imageUrl into db
                        image.setImageId(imageId);
                        image.setImageUrl(imageUrl);
                        // connect relationship Image and Space
                        image.setSpaceId(spaceOptional.get());
                        // save the image in the database
                        imageRepository.save(image);
                    }
                    spaceServiceImpl.saveSpace(spaceOptional.get());
                } else {
                    return new ResponseEntity<>(new ResponseMessage(1, "Requires at least 1 image!", 401), HttpStatus.BAD_REQUEST);
                }
            }
            // update status when repost space
            Space space = spaceOptional.get();
            Integer deniedId = 5;
            Integer pendingId = 3;
            if(space.getStatus().getId().equals(deniedId))
                space.setStatus(statusService.findBySpaceStatusId(pendingId).get());
            spaceServiceImpl.saveSpace(space);
            return new ResponseEntity<>(new SpaceResponse(0, "Update space successfully!", spaceOptional.get(), 200), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new SpaceResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Owner')")
    @DeleteMapping("/delete-space")
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "spaceId") Integer spaceId, HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            // user not found
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            // space notfound
            Optional<Space> spaceOptional = spaceServiceImpl.findById(spaceId);
            if (!spaceOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);

            // You cannot delete space that is not yours
            Optional<Space> spaceByOwnerId = spaceServiceImpl.findByIdAndOwnerId(spaceId, userOptional.get());
            if (!spaceByOwnerId.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "You cannot delete space that is not yours!", 400), HttpStatus.CONFLICT);


            if (spaceServiceImpl.deleteSpace(spaceOptional.get()))
                return new ResponseEntity<>(new ResponseMessage(0, "Delete Space Successful!", 200), HttpStatus.OK);
            return new ResponseEntity<>(new UpdateAnDeleteUserResponse(1, "Delete Space Fail!", 400), HttpStatus.BAD_REQUEST);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateStatusController(Integer statusCode, Integer spaceId, HttpServletRequest request) {
        String token = jwtTokenFilter.getJwtFromRequest(request);
        String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        // user not found
        if (!userOptional.isPresent())
            return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
        // space not found
        Optional<Space> spaceOptional = spaceServiceImpl.findById(spaceId);
        if (!spaceOptional.isPresent())
            return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);

        // space not found
        Optional<Status> statusOptional = statusService.findBySpaceStatusId(statusCode);
        if (!statusOptional.isPresent())
            return new ResponseEntity<>(new ResponseMessage(1, "Status Not Found!", 404), HttpStatus.NOT_FOUND);

        Integer spaceStatusId = spaceOptional.get().getStatus().getId();
        if (spaceStatusId != 3)
            return new ResponseEntity<>(new ResponseMessage(1, "Space has updated!", 404), HttpStatus.NOT_FOUND);

        Status status = statusOptional.get();

        if (spaceServiceImpl.updateStatus(spaceId, status)) {
            User sender = userOptional.get();
            User receiver = spaceOptional.get().getOwnerId();
            // add the notification
            Boolean isCreated = notificationService.createMessage(sender, receiver, "Approve", "Your post has been " + (statusCode == 0 ? "accepted." : "rejected"));
            if(!isCreated)
                return new ResponseEntity<>(new SpaceResponse(0, "Create new feedback fail!", 400), HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(new ResponseMessage(0, "Update space successfully!", 200), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseMessage(1, "Update space fail!", 400), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/denied-space")
    public ResponseEntity<?> deniedSpace(@RequestParam(name = "spaceId") Integer spaceId, HttpServletRequest request) {
        try {
            return updateStatusController(5, spaceId, request);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/accept-space")
    public ResponseEntity<?> acceptSpace(@RequestParam(name = "spaceId") Integer spaceId, HttpServletRequest request) {
        try {
            return updateStatusController(0, spaceId, request);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }


}
