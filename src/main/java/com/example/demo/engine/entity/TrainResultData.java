package com.example.demo.engine.entity;

public class TrainResultData {
    private Integer faceCount;
    private Integer sucessCount;
    private Integer failCount;
    private String imagePerSec;
    private String totalTime;
    private String startTime;
    private String endTime;

    public Integer getFaceCount() {
        return faceCount;
    }
    public void setFaceCount(Integer faceCount) {
        this.faceCount = faceCount;
    }
    public Integer getSucessCount() {
        return sucessCount;
    }
    public void setSucessCount(Integer sucessCount) {
        this.sucessCount = sucessCount;
    }
    public Integer getFailCount() {
        return failCount;
    }
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }
    public String getImagePerSec() {
        return imagePerSec;
    }
    public void setImagePerSec(String imagePerSec) {
        this.imagePerSec = imagePerSec;
    }
    public String getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
