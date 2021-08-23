package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.LendDAO;
import com.example.demo.entity.Lend;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@CrossOrigin
public class LendController {
    @Autowired
    LendDAO dao;

    @GetMapping(value = {"/lendingsheet"})
    public List<Lend> lendingsheet(){
        return dao.lendingsheet();
    }
    @GetMapping(value = {"/lendingsheet/{user_id}/{date}/{roomid}"})
    public List<Lend> retrieveLend(@PathVariable("user_id") String user_id, @PathVariable("date") String date, @PathVariable("roomid") String roomid) throws SQLException{
        return dao.findAll(user_id, date, roomid);
    }

    @GetMapping(value = {"/lendingsheet2/{date}/{roomid}/{time}"})
    public List<Lend> countBorrowed(@PathVariable("date") String date, @PathVariable("roomid") String roomid, @PathVariable("time") String time)throws SQLException{
        return dao.findCanBorrow(date, roomid, time);
    }

    @GetMapping(value = {"/lendingsheet/{date}/{roomid}"})
    public List<Lend> retrieveAllLend (@PathVariable("date") String date, @PathVariable("roomid") String roomid) throws SQLException{
        return dao.findAll(date, roomid);
    }

    @GetMapping(value = {"/ifroom/{user_id}/{room_id}"})
    public List<Lend> ifroom (@PathVariable("user_id") String user_id, @PathVariable("room_id")String room_id) throws SQLException{
        System.out.println(dao.ifroom(user_id,room_id));
        return dao.ifroom(user_id,room_id);
    }
    @PostMapping("/getTime/{user_id}/{room_id}")
    public List<Lend> getTime(@PathVariable String user_id,@PathVariable String room_id){
//        String[] myArray = new String[dao.getTime(user_id).size()];
//        dao.getTime(user_id).toArray(myArray);
//        for(int i =0;i<myArray.length;i++){
//            System.out.println(myArray[i]);
//        }

        return dao.getTime(user_id, room_id);
    }

    @GetMapping(value = {"/lendingsheet/{user_id}"})
    public List<Lend> retrieveOneLend (@PathVariable("user_id") String user_id) throws SQLException{

        return dao.findOne(user_id);
    }

    @PostMapping(value = "/lendingsheet")
    public void processFormCreate(@RequestBody Lend lend) throws SQLException {
        dao.insert(lend);
    }

//    @PutMapping(value = "/lendingsheet/{id}")
//    public void processFormUpdate(@PathVariable("id") Long id, @RequestBody Lend lend) throws SQLException {
//        dao.update(lend, id);
//    }

    @PostMapping(value = "/lendingsheetdelete")
    public void deleteLend(@RequestBody Lend lend) throws SQLException{
        dao.delete(lend);
        System.out.println("delete");
    }



}