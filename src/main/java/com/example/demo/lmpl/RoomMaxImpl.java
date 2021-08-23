package com.example.demo.lmpl;


import com.example.demo.entity.Room;
import com.example.demo.entity.RoomMax;
import com.example.demo.entity.userfacedata;
import com.example.demo.service.RoomMaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Transactional
public class RoomMaxImpl implements RoomMaxService {

    @Autowired
    DataSource dataSource;

    @Override
    public RoomMax findOne(String room_id){
        RoomMax RoomMax = new RoomMax();
        try {
            Connection conn = dataSource.getConnection();
            String sql = "select * from room where room_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, room_id );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                RoomMax=getdata(rs);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RoomMax;
    }

    public RoomMax getdata(ResultSet rs) throws SQLException {

        return new RoomMax(
                rs.getString("room_id"),
                rs.getString("room_max")

        );
    }
}
