package com.example.demo.controller;


import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.entity.*;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainFaceController {
    private static Logger LOGGER = LoggerFactory.getLogger(TrainFaceController.class);
    final static FolderUtil folderUtil = new FolderUtil();
    final static EngineUtil engineUtil = new EngineUtil();
    final static CreateEngineFileUtil createEngineFileUtil = new CreateEngineFileUtil();

    final static boolean logDeleteFlag = false;

    final static String quickStartPath = "C:\\";
    final static StringBuilder enginePath = new StringBuilder(quickStartPath + "\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20");
    final static StringBuilder faceDBPath = new StringBuilder(enginePath + "\\eGroup\\");
    final static StringBuilder resourcesPath = new StringBuilder(enginePath + "\\resources\\");
    final static StringBuilder trainResultLogPath = new StringBuilder(enginePath + "\\Status.TrainResultCPU.eGroup");
    final static StringBuilder trainListPath = new StringBuilder(resourcesPath + "list.txt");
    final static StringBuilder modelAppendListPath = new StringBuilder(faceDBPath + "\\ModelAppend.egroupList");
    final static StringBuilder outputfacePath = new StringBuilder(enginePath + "\\outputFace");
    final static StringBuilder outputframepath = new StringBuilder(enginePath + "\\outputFrame");
    final static StringBuilder jsonFolderPath = new StringBuilder(enginePath + "\\json");
    final static StringBuilder catchJsonName = new StringBuilder("output.cache.egroup");
    // final static StringBuilder allJsonName = new StringBuilder("output." + LocalDate.now() + ".egroup");
    final static StringBuilder modelInserFilePath = new StringBuilder(enginePath + "\\Singal_For_Model_Insert.txt");

    final static File outputfaceFile = new File(outputfacePath.toString());
    final static File faceDBFile = new File(faceDBPath.toString());
    final static File outputframeFile = new File(outputframepath.toString());
    final static File jsonFolderFile = new File(jsonFolderPath.toString());

    final static StringBuilder jeffFaceDBPath = new StringBuilder(faceDBPath + "\\jeff");
//    final static StringBuilder FaceImageFolderPath = new StringBuilder(resourcesPath + "jeff");
//    final static File FaceImageFolder = new File(FaceImageFolderPath.toString());

    @PostMapping("/Train/{name}")
    @CrossOrigin
    public String TrainPhoto(@PathVariable String name, HttpServletResponse response) throws IOException {




        final List<TrainFace> trainFaceList = new ArrayList<>();
        // Set training variable
        final TrainFace trainFace = new TrainFace();
        trainFace.setTrainListPath(trainListPath.toString());
        trainFace.setModelPath((faceDBPath+name));
        trainFace.setEnginePath(enginePath.toString());
        trainFace.setPersonId(name);
        // Get image in folder and set training image
        trainFace.setImagePathList(getFaceImageFolder(name));
        // Add to trainFace list
        trainFaceList.add(trainFace);
        // Create train face list
        createEngineFileUtil.createTrainFaceTxt(trainListPath.toString(), trainFaceList);
        // Start training and get result
        final TrainResult trainResult = engineUtil.trainFace(trainFace, logDeleteFlag);
        LOGGER.info("trainResult=" + new Gson().toJson(trainResult));

        FileReader fr = new FileReader(enginePath+"/"+"Status.TrainResultCPU.eGroup");
        LineNumberReader reader = new LineNumberReader(fr);
        int line3 =3;
        int line2 =2;
        String txt = "";
        String returntxt3 = "";
        String returntxt2 ="";
        int templine = 0;
        while(txt!=null){
            templine++;
            txt = reader.readLine();
            if(templine==line2){
                System.out.println(reader.getLineNumber());
                System.out.println(txt.substring(txt.length()-4));
                returntxt2 = txt.substring(txt.length()-4);
            }
            if(templine==line3){
                System.out.println(reader.getLineNumber());
                System.out.println(txt.substring(0,1));
                returntxt3 = txt.substring(0,1);
            }
        }
        fr.close();
        reader.close();
        if(returntxt3.equals("1")&&!returntxt2.equals("Fail")) {
            modelInsert(name);
            return "1";
        }
        if(!returntxt3.equals("1")||returntxt2.equals("Fail")){
            File file = new File(faceDBPath+name+".faceDB");
            if(file.exists()){
                file.delete();
            }
        }
//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter out = response.getWriter();
//        out.print("123");


        return "0";
    }

    public static List<String> getFaceImageFolder(String name) {
        List<String> imagePathList = new ArrayList<>();
        final StringBuilder FaceImageFolderPath = new StringBuilder(resourcesPath+name);
        final File FaceImageFolder = new File(FaceImageFolderPath.toString());
        imagePathList = folderUtil.listPath(FaceImageFolder);
        return imagePathList;
    }

    public static void modelInsert(String name){
        List<String> faceDBList = new ArrayList<>();
        faceDBList.add(faceDBPath + name + ".faceDB");
        // Set model insert variable
        ModelInsert modelInsert = new ModelInsert();
        modelInsert.setEnginePath(enginePath.toString());
        modelInsert.setFaceDBList(faceDBList);
        modelInsert.setListPath(modelInserFilePath.toString());
        ModelInsertResult modelInsertResult = engineUtil.modelInsert(modelInsert, false, 3000);
        LOGGER.info("modelInsertResult : " + new Gson().toJson(modelInsertResult));
    }

    public static void modelAppend(String name){
        List<String> faceDBList = new ArrayList<>();
        faceDBList.add(faceDBPath + name + ".faceDB");
        faceDBList.add(faceDBPath + "all.faceDB");

        // Model Append
        final ModelAppend modelAppend = new ModelAppend();
        modelAppend.setTrainedFaceDBPath(faceDBPath + "all.faceDB");
        modelAppend.setFaceDBList(faceDBList);
        modelAppend.setListPath(modelAppendListPath.toString());
        modelAppend.setEnginePath(enginePath.toString());
        final ModelAppendResult modelAppendResult = engineUtil.modelAppend(modelAppend, false, 25000);
        LOGGER.info("modelInsertResult : " + new Gson().toJson(modelAppendResult));

    }





}