package com.example.demo.controller;

import com.example.demo.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import com.example.demo.entity.User;

@RestController
public class UserController {
    @Autowired
    UserDAO dao;

    @GetMapping(value="/user")
        public List<User> retrieveUser() throws SQLException{
            return dao.findAll();
        }
    }

