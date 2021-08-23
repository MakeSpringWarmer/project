package com.example.demo.controller;

import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class InOutController {
    @Autowired
    UserfacedataService userfacedataService;

    @PutMapping("/changeOut/{id}")
    public String changeOut(@PathVariable String id){
        userfacedataService.updateOut(id);
        return "change";
    }
}
