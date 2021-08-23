package com.example.demo.engine.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.engine.util.AttributeCheck;

public class ModelInsert {
    private String ListPath;
    private String enginePath;
    private List<String> faceDBList;
    // init func
    private AttributeCheck attributeCheck;

    public String getEnginePath() {
        return enginePath;
    }

    public void setEnginePath(String enginePath) {
        this.enginePath = enginePath;
    }

    public String getListPath() {
        return ListPath;
    }

    public void setListPath(String listPath) {
        ListPath = listPath;
    }

    public List<String> getFaceDBList() {
        if (attributeCheck == null) {
            attributeCheck = new AttributeCheck();
        }
        if (!attributeCheck.listNotEmpty(faceDBList)) {
            faceDBList = new ArrayList<>();
        }
        return faceDBList;
    }

    public void setFaceDBList(List<String> faceDBList) {
        this.faceDBList = faceDBList;
    }
}
