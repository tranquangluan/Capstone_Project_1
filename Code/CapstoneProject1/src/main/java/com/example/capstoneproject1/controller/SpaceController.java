package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.services.SpaceService;
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

    @GetMapping(value = "")
    public List<Space> getSpaces(){
        return (List<Space>) spaceService.findAll();
    }

    // Lấy đối tượng khi truyền ID
    @GetMapping(value = "/detail", produces = "application/json")
    public ResponseEntity<Space> getSpaceById(@RequestParam(name = "id") Integer id) {
        Space space = spaceService.findById(id); //.orElse(null)
        if(space == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(space, HttpStatus.OK);
    }
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
        spa.setStatus(space.getStatus());
        spa.setPrice(space.getPrice());
//        spa.setImage(space.getImage());
        spa.setDescription(space.getDescription());
        spa.setBathroomNumbers(space.getBathroomNumbers());
        spa.setBedroomNumbers(space.getBedroomNumbers());
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

    @GetMapping(value = "/booking", produces = "application/json")
    public ResponseEntity<Space> getBooking(@RequestParam(name = "id") Integer id) {
        Space space = spaceService.findById(id); //.orElse(null)
        if(space == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(space, HttpStatus.OK);
    }
}
