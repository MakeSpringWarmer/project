package com.example.demo.entity;

public class Room {
    private String room_id;
    private Integer room_max;

    public Room() {

    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public Integer getRoom_max(){
        return room_max;
    }

    public void setRoom_max(Integer room_max){
        this.room_max = room_max;
    }
}