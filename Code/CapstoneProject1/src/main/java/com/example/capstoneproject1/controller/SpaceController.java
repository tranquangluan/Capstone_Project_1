package com.example.capstoneproject1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaceController {
    @GetMapping("")
    public String showHomePage(){
        return "list";
    }
}
