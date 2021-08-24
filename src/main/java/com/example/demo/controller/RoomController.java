package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dao.RoomDAO;
import com.example.demo.entity.Room;

@RestController
public class RoomController {
    @Autowired
    RoomDAO dao;



    @GetMapping(value = {"/room"})
    public List<Room> retrieveRoom() throws SQLException{
        return dao.findAll();
    }

    @GetMapping(value = {"/room/{id}"})
    public Room retrieveOneRoom (@PathVariable("id") String id) throws SQLException{
        return dao.findOne(id);
    }

    @PostMapping(value = "/room")
    public void processFormCreate(@RequestBody Room room) throws SQLException {
        dao.insert(room);
    }

    @PutMapping(value = "/room")
    public void processFormUpdate(@RequestBody Room room) throws SQLException {
        dao.update(room);
    }

    @DeleteMapping(value = "/room/{id}")
    public void deleteRoom(@PathVariable("id") String id) {
        dao.delete(id);
    }


}