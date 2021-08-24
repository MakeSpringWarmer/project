package com.example.demo.controller;

import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;


@RestController
@CrossOrigin
public class testController {
    @Autowired
    public static UserfacedataService userfacedataService;

    @GetMapping("/Hi")
    public Response show(){
        System.out.println("hi");
        return null;
    }


    }

