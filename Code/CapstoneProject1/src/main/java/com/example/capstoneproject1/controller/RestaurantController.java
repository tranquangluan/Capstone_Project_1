package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.models.Restaurant;
import com.example.capstoneproject1.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantRepository restaurantRepository;

    // Hiển thị ra danh sách các đối tượng
    @GetMapping(value = "/list")
    public List<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }

    // Lấy đối tượng khi truyền ID
    @GetMapping(value = "/find", produces = "application/json")
    public ResponseEntity<Restaurant> getRestaurantById(@RequestParam(name = "id") long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if(restaurant == null){
//            throw  new RestaurantNotFoundException("Restaurant id not found " + id);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    // Thêm một đối tượng
    @PostMapping(path = "/add", consumes = "application/json")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant){
        restaurantRepository.save(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Update đối tượng
    @PutMapping(value = "/update")
    public ResponseEntity<Restaurant> updateRestaurantByID(@RequestParam(name = "id") long id,
                                                           @RequestBody Restaurant restaurant){
        Restaurant restaurantTemp = restaurantRepository.findById(id).orElse(null);

        if(restaurantTemp == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Restaurant res = new Restaurant();
        res.setId(id);
        res.setName(restaurant.getName());
        res.setAddress(restaurant.getAddress());
        restaurantRepository.save(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Delete đối tượng
    @DeleteMapping("/delete")
    public ResponseEntity<String> updateRestaurantByID(@RequestParam(name = "id") long id){
        Restaurant restaurantTemp = restaurantRepository.findById(id).orElse(null);

        if(restaurantTemp == null){
            return new ResponseEntity<>("Not found object", HttpStatus.NOT_FOUND);
        }

        restaurantRepository.deleteById(id);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }
    // thuộc tính consumes là một thuộc tính dùng để định dạng kiểu dữ liệu được chấp nhận bởi server
    // ResponseEntity dùng để biểu diễn sự phản hồi HTTP như mã trạng thái,..
    // RequestBody dùng để lấy thông tin từ phần thân của yêu cầu HTTP và chuyển đổi thành đối tượng trong Java
    // thuộc tính produces là một thuộc tính được sử dụng để xác định kiểu trả về cho client
    // @RestController là một annotation trong Spring Framework để đánh dấu một class là một RESTful controller.
    // RESTful controller là một thành phần của ứng dụng Web Service, nó xử lý các yêu cầu HTTP từ client và trả về response thông qua các API.
}
