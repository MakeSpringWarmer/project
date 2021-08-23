package com.example.demo.entity;


public class RoomMax {

    private String room_id;
    private String room_max;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_max() {
        return room_max;
    }

    public void setRoom_max(String room_max) {
        this.room_max = room_max;
    }

    public RoomMax(){

    }

    public RoomMax(String room_id,String room_max){
        this.room_id = room_id;
        this.room_max = room_max;
    }
}
