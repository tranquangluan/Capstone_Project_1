package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "")
    public List<User> getSpaces(){
        return (List<User>) userService.findAll();
    }

    // Lấy đối tượng khi truyền ID
    @GetMapping(value = "/detail", produces = "application/json")
    public ResponseEntity<User> getSpaceById(@RequestParam(name = "id") Integer id) {
        User user = userService.findById(id); //.orElse(null)
        if(user == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Thêm một đối tượng
    @PostMapping(path = "/add", consumes = "application/json")
    public ResponseEntity<User> addSpace(@RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update đối tượng
    @PutMapping(value = "/update")
    public ResponseEntity<User> updateSpaceByID(@RequestParam(name = "id") Integer id,
                                                 @RequestBody User user){
        User userTemp = userService.findById(id); //.orElse(null)

        if(userTemp == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        User user1 = new User();
        user.setId(id);
//        user.setStatus(space.getStatus());
//        user.setPrice(space.getPrice());

        userService.update(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    // Delete đối tượng
    @DeleteMapping("/delete")
    public ResponseEntity<String> updateSpaceByID(@RequestParam(name = "id") Integer id){
        User userTemp = userService.findById(id); //.orElse(null)

        if(userTemp == null){
            return new ResponseEntity<>("Not found object", HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @GetMapping(value = "/booking", produces = "application/json")
    public ResponseEntity<User> getBooking(@RequestParam(name = "id") Integer id) {
        User user = userService.findById(id); //.orElse(null)
        if(user == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
