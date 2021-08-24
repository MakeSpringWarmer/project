package com.example.demo.engine.entity;

public class ModelSwitchResult {
    private boolean isSuccess;
    private String faceReload;
    private String reloadTime;
    private String faceDB;

    public String getFaceReload() {
        return faceReload;
    }
    public void setFaceReload(String faceReload) {
        this.faceReload = faceReload;
    }
    public String getReloadTime() {
        return reloadTime;
    }
    public void setReloadTime(String reloadTime) {
        this.reloadTime = reloadTime;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getFaceDB() {
        return faceDB;
    }
    public void setFaceDB(String faceDB) {
        this.faceDB = faceDB;
    }
}
