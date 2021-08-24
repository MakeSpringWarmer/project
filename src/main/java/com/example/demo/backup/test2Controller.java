package com.example.demo.controller;

import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test2Controller {

    @Autowired
    UserfacedataService userfacedataService;

    @PostMapping("/test2/{id}")
    public String showInOut(@PathVariable String id){
        System.out.println("123");

        return userfacedataService.getInOut((id));
    }
}
