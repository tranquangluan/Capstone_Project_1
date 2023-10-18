//package com.example.capstoneproject1.controller;
//
//import com.example.capstoneproject1.models.CategorySpace;
//import com.example.capstoneproject1.models.Space;
//import com.example.capstoneproject1.services.CategorySpaceService;
//import com.example.capstoneproject1.services.SpaceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/space")
//public class TestSpace {
//    @Autowired
//    SpaceService spaceService;
//
//    @Autowired
//    CategorySpaceService categorySpaceService;
//
//    @GetMapping("/list")
//    public String showList(ModelMap modelMap)
//    {
//        List<Space> spaces = (List<Space>) spaceService.findAll();
//        modelMap.addAttribute("spaces", spaces);
//        return "HomePage";
//    }
//
//    @GetMapping("/create")
//    public String showCreate(ModelMap modelMap) {
//        Space space = new Space();
//        modelMap.addAttribute("space", space);
//        List<CategorySpace> categorySpaces = (List<CategorySpace>) categorySpaceService.findAll();
//        modelMap.addAttribute("categorySpaces", categorySpaces);
//        return "/create";
//    }
//
//    @PostMapping("/create")
//    public String doCreate(@ModelAttribute("space") Space space, BindingResult bindingResult
//            , ModelMap modelMap) {
//        if (bindingResult.hasErrors()) {
//            return "/create";
//        }
//
//        spaceService.update(space);
//        return "redirect:/space/list";
//    }
//
//    @GetMapping("/update/{id}")
//    public String showUpdate(@PathVariable(value = "id") int id,ModelMap modelMap) {
//        Space space = spaceService.findById(id);
//        modelMap.addAttribute("space", space);
//        List<CategorySpace> categorySpaces = (List<CategorySpace>) categorySpaceService.findAll();
//        modelMap.addAttribute("categorySpaces", categorySpaces);
//        return "/update";
//    }
//
//    @PostMapping("/update")
//    public String doUpdate(@ModelAttribute("space") Space space, BindingResult bindingResult
//            , ModelMap modelMap) {
//        if (bindingResult.hasErrors()) {
//            return "/update";
//        }
//        spaceService.update(space);
//        return "redirect:/space/list";
//    }
//
//    @GetMapping("/detail/{id}")
//    public String detail(@PathVariable(value = "id") int id,ModelMap modelMap) {
//        Space space = spaceService.findById(id);
//        modelMap.addAttribute("space", space);
//        return "DetailSpace";
//    }
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable(value = "id") int id) {
//        spaceService.delete(id);
//        return "redirect:/space/list";
//    }
//}
