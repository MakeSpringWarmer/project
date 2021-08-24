package com.example.demo.entity;

public class FaceAndLend {
    private String date;
    private String time;
    private String room_id;
    private String user_id;

    public FaceAndLend(){

    }

    public FaceAndLend(String date, String time,String room_id,String user_id){
        this.date = date;
        this.time = time;
        this.room_id = room_id;
        this.user_id = user_id;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getRoom_id(){
        return room_id;
    }

    public void setRoom_id(String room_id){
        this.room_id = room_id;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
}
