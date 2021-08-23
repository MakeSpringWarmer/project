package com.example.demo.controller;

import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.sql.SQLException;

@RestController
public class ListController {
    @Autowired
    UserfacedataService userfacedataService;

    @GetMapping("/List")
    public List<userfacedata> List()throws SQLException {
        System.out.println("list");
        return userfacedataService.findList();
    }
}
