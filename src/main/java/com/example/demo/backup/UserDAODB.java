package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.example.demo.entity.User;

@Repository
public class UserDAODB implements UserDAO{
    @Autowired
    JdbcTemplate jdbcTemplate;

//    public List<User> findAll(){
//        return this.jdbcTemplate.query("select UserFace_Id,UserFace_Mail, UserFace_Role," +
//                " UserFace_State, UserFace_Name, UserFace_Passward",(rs,rowNum)->
//                new User(
//                        rs.getLong("UserFace_Id"),
//                        rs.getString("UserFace_Mail"),
//                        rs.getString("UserFace_Role"),
//                        rs.getString("UserFace_State"),
//                        rs.getString("UserFace_Name"),
//                        rs.getString("UserFace_Password")
//                )
//                );
//    }

    public List<User> findAll(){
        return this.jdbcTemplate.query("select UserFace_Id,UserFace_Mail, UserFace_Role," +
                " UserFace_State, UserFace_Name, UserFace_Passward",new UserMapper());
    }

    private static final class UserMapper implements RowMapper<User>{
        public User mapRow(ResultSet rs, int rowNum) throws SQLException{
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
}
