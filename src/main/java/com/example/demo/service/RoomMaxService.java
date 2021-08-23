package com.example.demo.service;

import com.example.demo.entity.RoomMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface  RoomMaxService {

    @Autowired
    public RoomMax findOne(String room_id);
}
