package com.example.demo.engine.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.engine.util.AttributeCheck;

public class ModelInsertResult {
    List<ModelInsertInfo> modelInsertInfoList;
    private boolean isSuccess;
    private String faceReload;
    private String reloadTime;
    // init func
    private AttributeCheck attributeCheck;

    public List<ModelInsertInfo> getModelInsertInfoList() {
        if(attributeCheck==null){
            attributeCheck = new AttributeCheck();
        }
        if(!attributeCheck.listNotEmpty(modelInsertInfoList)){
            modelInsertInfoList = new ArrayList<>();
        }
        return modelInsertInfoList;
    }

    public void setModelInsertInfoList(List<ModelInsertInfo> modelInsertInfoList) {
        this.modelInsertInfoList = modelInsertInfoList;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

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
}
