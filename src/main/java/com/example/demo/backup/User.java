package com.example.demo.entity;

public class User {
    private Long UserFace_Id;
    private String UserFace_Mail;
    private String UserFace_Role;
    private String UserFace_State;
    private String UserFace_Name;
    private String UserFace_Password;

    public User(Long UserFace_Id, String UserFace_Mail, String UserFace_Role, String UserFace_State,
                String UserFace_Name, String UserFace_Password){
        this.UserFace_Id = UserFace_Id;
        this.UserFace_Mail = UserFace_Mail;
        this.UserFace_Role = UserFace_Role;
        this.UserFace_State = UserFace_State;
        this.UserFace_Name = UserFace_Name;
        this.UserFace_Password = UserFace_Password;
    }

    public Long getUserFace_Id(){
        return UserFace_Id;
    }

    public void setUserFace_Id(){
        this.UserFace_Id = UserFace_Id;
    }

    public String getUserFace_Mail(){
        return UserFace_Mail;
    }

    public void setUserFace_Mail(){
        this.UserFace_Mail = UserFace_Mail;
    }

    public String getUserFace_Role(){
        return UserFace_Role;
    }

    public void setUserFace_Role(){
        this.UserFace_Role = UserFace_Role;
    }

    public String getUserFace_State(){
        return UserFace_State;
    }

    public void setUserFace_State(){
        this.UserFace_State = UserFace_State;
    }

    public String getUserFace_Name(){
        return UserFace_Name;
    }

    public void setUserFace_Name(){
        this.UserFace_Name = UserFace_Name;
    }

    public String getUserFace_Password(){
        return UserFace_Password;
    }

    public void setUserFace_Password(){
        this.UserFace_Password = UserFace_Password;
    }
}
