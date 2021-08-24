package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Room;


@Repository
public class RoomDAODB implements RoomDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

//jdbcTemplate

    public Room findOne(String room_id) {

        return this.jdbcTemplate.queryForObject(
                "select * from room where room_id = ?",
                new Object[]{room_id}, new RoomMapper());
    }

    public List<Room> findAll() {
        return this.jdbcTemplate.query( "select * from room",
                new RoomMapper());
    }

    public List<Room> getMax(String room_id){
        return this.jdbcTemplate.query(
                "select room_max from room where room_id = ?",
                new Object[]{room_id}, new RoomMapper());
    }

    private static final class RoomMapper implements RowMapper<Room> {

        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {

            Room room = new Room();
            room.setRoom_id(rs.getString("room_id"));
            return room;

        }

    }


    public int insert(Room room) {
        return jdbcTemplate.update(
                "insert into room (room_id) values(?)",
                room.getRoom_id());
    }

    public int update(Room room) {
        return jdbcTemplate.update(
                "update room set room_id=?",
                room.getRoom_id());
    }

    public int delete(String room_id) {
        return jdbcTemplate.update(
                "delete from room where room_id =?", room_id);
    }

}