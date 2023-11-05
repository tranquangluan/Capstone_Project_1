package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.services.BookingService;
import com.example.capstoneproject1.services.CategorySpaceService;
import com.example.capstoneproject1.services.SpaceService;
import com.example.capstoneproject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    CategorySpaceService categorySpaceService;

    @GetMapping(value = "")
    public List<Space> getSpaces(){
        return spaceService.getList();
    }

    // Lấy đối tượng khi truyền ID
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Space> getSpaceById(@RequestParam(name = "spaceId") Integer id) {
        Space space = spaceService.detailSpace(id);
        if(space == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(space, HttpStatus.OK);
    }
    @GetMapping(value = "/detailOwner", produces = "application/json")      //có vấn đề chỗ owner
    public ResponseEntity<User> getOwnerById(@RequestParam(name = "id") Integer id) {
        User user = userService.findByUserId(id);
        if(user == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping(value = "/", produces = "application/json")
    public List<Space> searchAndSortSpace(@RequestParam(name = "priceMin", required = false, defaultValue = "0") BigDecimal priceMin ,
                                          @RequestParam(name = "priceMax", required = false, defaultValue = "50000000000") BigDecimal priceMax,
                                          @RequestParam(name = "areaMin",required = false, defaultValue = "10") float areaMin,
                                          @RequestParam(name = "areaMax",required = false, defaultValue = "1000000") float areaMax,
                                          @RequestParam(name = "categoryId",required = false) Integer categoryId,
                                          @RequestParam(name = "province",required = false, defaultValue = "") String province,
                                          @RequestParam(name = "district",required = false, defaultValue = "") String district,
                                          @RequestParam(name = "ward",required = false, defaultValue = "") String ward,
                                          @RequestParam(name = "address",required = false, defaultValue = "") String address,
                                          @RequestParam(name = "order",required = false, defaultValue = "null") String order) {
        List<Space> spaceList = spaceService.search(priceMin,priceMax,areaMin,areaMax,categoryId,province,district,ward,address);
        if (order.toString().equals("null")){
            spaceList = spaceService.search(priceMin,priceMax,areaMin,areaMax,categoryId,province,district,ward,address);

        }else if(order.toString().equals("asc")){
            spaceList = spaceService.sortAsc(priceMin,priceMax,areaMin,areaMax,categoryId,province,district,ward,address);

        }else if (order.toString().equals("desc")){
            spaceList = spaceService.sortDesc(priceMin,priceMax,areaMin,areaMax,categoryId,province,district,ward,address);
        }
        System.out.println(order);
        return spaceList;
    }


    // Thêm một đối tượng
    @PostMapping(path = "/add", consumes = "application/json")
    public ResponseEntity<Space> addSpace(@RequestBody Space space){
        spaceService.update(space);
        return new ResponseEntity<>(space, HttpStatus.OK);
    }

    // Update đối tượng
    @PutMapping(value = "")
    public ResponseEntity<Space> updateSpaceByID(@RequestParam(name = "spaceId") Integer id,
                                                 @RequestBody Space space){
        Space spaceTemp = spaceService.findSpaceById(id); //.orElse(null)

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
    @DeleteMapping("")
    public ResponseEntity<String> deleteSpaceByID(@RequestParam(name = "spaceId") Integer id){
        Space spaceTemp = spaceService.findSpaceById(id); //.orElse(null)

        if(spaceTemp == null){
            return new ResponseEntity<>("Not found object", HttpStatus.NOT_FOUND);
        }

        spaceService.delete(id);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @GetMapping(value = "/booking", produces = "application/json") // xung đột user khó hiểu
    public ResponseEntity<Space> getBooking(@RequestParam(name = "id") Integer id,
                                            @RequestBody User user) {
        Space space = spaceService.detailSpace(id); //.orElse(null)
        if(space == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(space, HttpStatus.OK);
    }

    @PutMapping(value = "/user/profile")
    public ResponseEntity<User> updateUserInformation(@RequestParam(name = "id") Integer id,
                                                      @RequestBody User user){
        User userTemp = userService.findByUserId(id); //.orElse(null)

        if(userTemp == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        User user1 = new User();
        user1.setId(id);
        user1.setAvatar(user.getAvatar());
        user1.setName(user.getName());
        user1.setGender(user.getGender());
        user1.setDateOfBirth(user.getDateOfBirth());
        user1.setPhone(user.getPhone());
        user1.setEmail(user.getEmail());
        user1.setAddress(user.getAddress());
        userService.update(user1);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @GetMapping(value = "/category")
    public List<CategorySpace> getCategorySpaces(){
        return (List<CategorySpace>) categorySpaceService.findAll();
    }
}
