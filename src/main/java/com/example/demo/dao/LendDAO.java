package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Lend;

public interface LendDAO {

 public int insert(Lend lend);

 public List<Lend> findAll(String user_id, String date, String room_id);

 public List<Lend> findOne(String user_id);

 public List<Lend> findAll(String date, String room_id);

 public List<Lend> ifroom(String user_id,String room_id);

 public List<Lend> getTime(String user_id,String room_id);

 public List<Lend> findCanBorrow(String date, String room_id, String time);
// public int update(Lend lend, Long id);

 public List<Lend> lendingsheet();

 public int delete(Lend lend);

}