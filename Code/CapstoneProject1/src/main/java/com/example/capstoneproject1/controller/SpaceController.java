package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        Space space1 = new Space();
        space1.setId(id);
        space1.setStatus(space.getStatus());
        space1.setPrice(space.getPrice());
//        space1.setImage(space.getImage());
        space1.setDescription(space.getDescription());
        space1.setBathroomNumbers(space.getBathroomNumbers());
        space1.setBedroomNumbers(space.getBedroomNumbers());
        space1.setArea(space.getArea());
        spaceService.update(space1);
        return new ResponseEntity<>(space1, HttpStatus.OK);
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

    
}
