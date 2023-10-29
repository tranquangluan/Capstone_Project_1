package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.models.Space;
//import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.services.BookingService;
import com.example.capstoneproject1.services.SpaceService;
import com.example.capstoneproject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/space")
public class SpaceController {

    @Autowired
    SpaceService spaceService;
    @Autowired
    UserService userService;
    @Autowired
    BookingService bookingService;

    @GetMapping(value = "")
    public List<Space> getSpaces(){
        return spaceService.getList();
    }

    // Lấy đối tượng khi truyền ID
    @GetMapping(value = "/detail", produces = "application/json")
    public ResponseEntity<Space> getSpaceById(@RequestParam(name = "id") Integer id) {
        Space space = spaceService.detailSpace(id);
        if(space == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(space, HttpStatus.OK);
    }
//    @GetMapping(value = "/detailOwner", produces = "application/json")      //có vấn đề chỗ owner
//    public ResponseEntity<User> getOwnerById(@RequestParam(name = "id") Integer id) {
//        User user = userService.findById(id);
//        if(user == null){
////            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
    @GetMapping(value = "/search", produces = "application/json")
    public List<Space> getSpaceBy(@RequestParam(name = "price") BigDecimal price ,
                                            @RequestParam(name = "area") float area,
                                            @RequestParam(name = "categoryId") Integer categoryId,
                                            @RequestParam(name = "province") String province,
                                            @RequestParam(name = "district") String district,
                                            @RequestParam(name = "ward") String ward,
                                            @RequestParam(name = "address") String address) {

        List<Space> spacelist = spaceService.search(price,area,categoryId,province,district,ward,address);
        return spacelist;
    }


    // Thêm một đối tượng
    @PostMapping(path = "/add", consumes = "application/json")
    public ResponseEntity<Space> addSpace(@RequestBody Space space){
        spaceService.update(space);
        return new ResponseEntity<>(space, HttpStatus.OK);
    }

    // Update đối tượng
    @PutMapping(value = "/update")
    public ResponseEntity<Space> updateSpaceByID(@RequestParam(name = "id") Integer id,
                                                           @RequestBody Space space){
        Space spaceTemp = spaceService.findById(id); //.orElse(null)

        if(spaceTemp == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Space spa = new Space();
        spa.setId(id);
        spa.setTitle(space.getTitle());
        spa.setStatus(space.getStatus());
        spa.setPrice(space.getPrice());
        spa.setDistrict(space.getDistrict());
        spa.setProvince(space.getProvince());
        spa.setWard(space.getWard());
        spa.setPeopleNumbers(space.getPeopleNumbers());
//        spa.setImage(space.getImage());
        spa.setDescription(space.getDescription());
        spa.setBathroomNumbers(space.getBathroomNumbers());
        spa.setBedroomNumbers(space.getBedroomNumbers());
        spa.setAddress(space.getAddress());
        spa.setCategoryId(space.getCategoryId());
        spa.setArea(space.getArea());
        spaceService.update(spa);
        return new ResponseEntity<>(spa, HttpStatus.OK);
    }

    // Delete đối tượng
    @DeleteMapping("/delete")
    public ResponseEntity<String> updateSpaceByID(@RequestParam(name = "id") Integer id){
        Space spaceTemp = spaceService.findById(id); //.orElse(null)

        if(spaceTemp == null){
            return new ResponseEntity<>("Not found object", HttpStatus.NOT_FOUND);
        }

        spaceService.delete(id);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

//@GetMapping(value = "/booking", produces = "application/json") // xung đột user khó hiểu
  //  public ResponseEntity<Space> getBooking(@RequestParam(name = "id") Integer id,
        //                                    @RequestBody User user) {
      //  Space space = spaceService.detailSpace(id); //.orElse(null)
      //  if(space == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        //}
       // return new ResponseEntity<>(space, HttpStatus.OK);
  //  }

//    @PutMapping(value = "/user/profile")
//    public ResponseEntity<User> updateUserInformation(@RequestParam(name = "id") Integer id,
//                                                @RequestBody User user){
//        User userTemp = userService.findById(id); //.orElse(null)
//
//        if(userTemp == null){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        User user1 = new User();
//        user1.setId(id);
//        user1.setAvatar(user.getAvatar());
//        user1.setName(user.getName());
//        user1.setGender(user.getGender());
//        user1.setDateOfBirth(user.getDateOfBirth());
//        user1.setPhone(user.getPhone());
//        user1.setEmail(user.getEmail());
//        user1.setAddress(user.getAddress());
//        userService.update(user1);
//        return new ResponseEntity<>(user1, HttpStatus.OK);
//    }
//
//
}
