package com.example.demo.controller;


import com.example.demo.entity.RoomMax;
import com.example.demo.service.RoomMaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomMaxController {

    @Autowired
    private RoomMaxService roomMaxService;

    @GetMapping("/roomMax/{room_id}")
    public RoomMax findOne(@PathVariable("room_id") String room_id){
        RoomMax result = roomMaxService.findOne(room_id);
        return result;
    }
}
