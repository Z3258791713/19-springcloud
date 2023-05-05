package com.jxnu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

@RestController
public class TeacherController {

    @GetMapping("teacher")
    public String teach(){
        return "教书学习";
    }
}
