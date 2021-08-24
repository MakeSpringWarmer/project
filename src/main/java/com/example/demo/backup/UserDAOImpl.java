package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{

    @Autowired
    private DataSource dataSource;

    public List<User> findAll(){
        List<User> user = new ArrayList<User>();
        try{
            Connection conn = dataSource.getConnection();
            String sql = "select UserFace_Id, UserFace_Mail, UserFace_Role, UserFace_State," +
                    " UserFace_Name, UserFace_Password";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                user.add(getUser(rs));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    public User getUser(ResultSet rs) throws SQLException{

        return new User(
                rs.getLong("UserFace_Id"),
                rs.getString("UserFace_Mail"),
                rs.getString("UserFace_Role"),
                rs.getString("UserFace_State"),
                rs.getString("UserFace_Name"),
                rs.getString("UserFace_Password")
        );
    }
}

