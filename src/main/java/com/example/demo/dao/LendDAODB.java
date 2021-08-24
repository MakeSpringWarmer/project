package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Lend;
import org.springframework.web.bind.annotation.CrossOrigin;


@Repository
@Transactional
public class LendDAODB implements LendDAO {
 @Autowired
private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate  
 public List<Lend> findAll(String date, String room_id) {
    return this.jdbcTemplate.query(
            "select * from lendingsheet where date = ? and room_id = ?",
            new Object[]{date, room_id}, new LendMapper());
}

 public List<Lend> findCanBorrow(String date, String room_id, String time){
     return this.jdbcTemplate.query(
             "select * from lendingsheet where date = ? and room_id = ? and time = ? ",
             new Object[]{date, room_id, time}, new LendMapper());
}

public List<Lend> lendingsheet(){
     return this.jdbcTemplate.query(
             "select * from lendingsheet",
             new Object[]{},new LendMapper());
}

public List<Lend> ifroom(String user_id,String room_id){
     return this.jdbcTemplate.query(
             "select * from lendingsheet where user_id = ? and room_id = ?",
             new Object[]{user_id, room_id}, new LendMapper());
}

public List<Lend> getTime(String user_id,String room_id){
     return  this.jdbcTemplate.query(
             "select time, date from lendingsheet where user_id = ? and room_id = ?",
             new Object[]{user_id,room_id}, new LendMapperTime());
}

 public List<Lend> findOne(String user_id) {
     return this.jdbcTemplate.query(
      "select distinct date, room_id from lendingsheet where user_id = ?",
      new Object[]{user_id}, new LendMapperShort());
 }

 public List<Lend> findAll(String user_id, String date, String room_id) {
     return this.jdbcTemplate.query(
             "select * from lendingsheet where user_id = ? and date = ? and room_id = ?",
             new Object[]{user_id, date, room_id}, new LendMapper());
 }

 private static final class LendMapper implements RowMapper<Lend> {

     public Lend mapRow(ResultSet rs, int rowNum) throws SQLException {

         Lend lend = new Lend();
         lend.setRoom_id(rs.getString("room_id"));
         lend.setUser_id(rs.getString("user_id"));
         lend.setDate(rs.getString("date"));
         lend.setTime(rs.getString("time"));
         return lend;

     }
 }

 private class LendMapperTime implements  RowMapper<Lend>{

     public Lend mapRow(ResultSet rs, int rowNum) throws SQLException {

         Lend lend = new Lend();
         lend.setTime(rs.getString("time"));
         lend.setDate(rs.getString("date"));
         return lend;

     }

 }


    private static final class LendMapperShort implements RowMapper<Lend> {

        public Lend mapRow(ResultSet rs, int rowNum) throws SQLException {

            Lend lend = new Lend();
            lend.setRoom_id(rs.getString("room_id"));
            lend.setDate(rs.getString("date"));
            return lend;

        }

    }

    private static final class LendMapperCheckPeople implements RowMapper<Lend>{

     public Lend mapRow(ResultSet rs, int rowNum) throws SQLException{

         Lend lend = new Lend();
         lend.setDate(rs.getString("date"));
         lend.setRoom_id(rs.getString("room_id"));
         lend.setTime(rs.getString("time"));
         return lend;
        }
    }


 public int insert(Lend lend) {
  return jdbcTemplate.update(
    "insert into lendingsheet (room_id, user_id, date, time) values(?, ?, ?, ?)",
    lend.getRoom_id(), lend.getUser_id(), lend.getDate(), lend.getTime());
 }

// public int update(Lend lend, Long id) {
//  return jdbcTemplate.update(
//    "update lendingsheet set room_id=?, user_id=?, date=?, time=? where AINum_Lend =?",
//    lend.getRoom_id(), lend.getUser_id(), lend.getDate(), lend.getTime(), id);
// }

 public int delete(Lend lend) {
  return jdbcTemplate.update(
    "delete from lendingsheet where room_id=? and date=? and time=?", lend.getRoom_id(), lend.getDate(), lend.getTime());
 }



}