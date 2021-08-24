package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.User;

public interface UserDAO {
    public List<User> findAll();
}
