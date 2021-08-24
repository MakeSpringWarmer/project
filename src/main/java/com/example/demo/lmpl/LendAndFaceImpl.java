package com.example.demo.lmpl;

import com.example.demo.entity.FaceAndLend;
import com.example.demo.entity.userfacedata;
import com.example.demo.service.LendAndFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class LendAndFaceImpl implements LendAndFaceService {

    @Autowired
    DataSource dataSource;

    @Override
    public List<FaceAndLend> ifroom() {
        List<FaceAndLend> faceandlend = new ArrayList<FaceAndLend>();
        try{
            Connection conn = dataSource.getConnection();
            String sql = "select * from lendingsheet";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                faceandlend.add(getdata(rs));
            }
        }catch(Exception e) {
            //something wrong
            System.out.println(e);
        }
        return faceandlend;
    }

    public FaceAndLend getdata(ResultSet rs) throws SQLException{

        return new FaceAndLend(
                rs.getString("date"),
                rs.getString("time"),
                rs.getString("room_id"),
                rs.getString("user_id")
        );
    }
}
