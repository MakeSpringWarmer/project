package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Room;

public interface RoomDAO {

    public int insert(Room room);

    public List<Room> findAll();

    public Room findOne(String room_id);

    public int update(Room room);

    public int delete(String room_id);

    public List<Room> getMax(String room_id);
}