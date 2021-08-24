package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserfacedataService userfacedataService;

    @PostMapping("/login/{UserId}")
    public Object login(@PathVariable("UserId") String UserId, @RequestBody userfacedata user){
        String truePassword = userfacedataService.findOne(UserId).getUserFace_Password();
        String tempPassword = user.getUserFace_Password();
        String state = userfacedataService.findOne(UserId).getUserFace_Role();
        if(!tempPassword.equals(truePassword)){
            return false;
        }
        if(state.equals("管理員")){
            System.out.println("M");
            return "M";
        }
        return "S";
    }
}
