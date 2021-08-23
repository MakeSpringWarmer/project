package com.example.demo.entity;


import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userfacedata {
    private String UserFace_Id;
    private String UserFace_Mail;
    private String UserFace_Role;
    private String UserFace_State;
    private String UserFace_Password;
    private String UserFace_Name;
    private String UserFace_InOut;

    public String getUserFace_Password() {
        return UserFace_Password;
    }

    public void setUserFace_Password(String userFace_Password) {
        UserFace_Password = userFace_Password;
    }

    public String getUserFace_Name() {
        return UserFace_Name;
    }

    public void setUserFace_Name(String userFace_Name) {
        UserFace_Name = userFace_Name;
    }

    public userfacedata(){
    }

    public userfacedata(String userFace_id, String userFace_mail, String userFace_role, String userFace_state ,
                        String userFace_Password,String userFace_Name,String userFace_InOut) {

        this.UserFace_Id = userFace_id;
        this.UserFace_Mail = userFace_mail;
        this.UserFace_Role = userFace_role;
        this.UserFace_State = userFace_state;
        this.UserFace_Name = userFace_Name;
        this.UserFace_Password = userFace_Password;
        this.UserFace_InOut = userFace_InOut;
    }



    @Id
    public String getUserFace_Id() {
        return UserFace_Id;
    }

    public void setUserFace_Id(String userFace_Id) {
        UserFace_Id = userFace_Id;
    }

    public String getUserFace_Role() {
        return UserFace_Role;
    }

    public void setUserFace_Role(String userFace_Role) {
        UserFace_Role = userFace_Role;
    }

    public String getUserFace_State() {
        return UserFace_State;
    }

    public void setUserFace_State(String userFace_State) {
        UserFace_State = userFace_State;
    }

    public String getUserFace_Mail() {
        return UserFace_Mail;
    }

    public void setUserFace_Mail(String userFace_Mail) {
        UserFace_Mail = userFace_Mail;
    }

    public String getUserFace_InOut() { return UserFace_InOut;}

    public void setUserFace_InOut(String userFace_InOut) { UserFace_InOut = userFace_InOut; }
}
