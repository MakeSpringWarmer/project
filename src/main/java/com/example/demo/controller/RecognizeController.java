package com.example.demo.controller;


import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.contorl.GetResultUtil;
import com.example.demo.engine.entity.Face;
import com.example.demo.engine.entity.ModelAppend;
import com.example.demo.engine.entity.ModelAppendResult;
import com.example.demo.engine.entity.RecognizeFace;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
import com.example.demo.service.UserfacedataService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin
public class RecognizeController {

    private static Logger LOGGER = LoggerFactory.getLogger(TrainFaceController.class);
    final static FolderUtil folderUtil = new FolderUtil();
    final static EngineUtil engineUtil = new EngineUtil();
    final static CreateEngineFileUtil createEngineFileUtil = new CreateEngineFileUtil();
    final static GetResultUtil getResultUtil = new GetResultUtil();

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
    final static StringBuilder modelInserFilePath = new StringBuilder(enginePath + "\\Singal_For_Model_Insert.txt");
    final static String resolution = "720p";

    final static File outputfaceFile = new File(outputfacePath.toString());
    final static File faceDBFile = new File(faceDBPath.toString());
    final static File outputframeFile = new File(outputframepath.toString());
    final static File jsonFolderFile = new File(jsonFolderPath.toString());

    final static StringBuilder jeffFaceDBPath = new StringBuilder(faceDBPath + "\\jeff");


    final static String filepath = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup";
    final static RecognizeFace recognizeFace = new RecognizeFace();

    @Autowired
    UserfacedataService userfacedataService;

    @PostMapping("/Recognize")
    public void Recognize() throws IOException {
        String time = modelAppend();

        FileWriter fw = new FileWriter(enginePath+"\\document\\OpenClose.txt");
        fw.write("Open");
        fw.flush();
        fw.close();

        recognition(faceDBPath + "date" + time + ".faceDB");



    }
    @PostMapping("/GetResult/{select}")
    public String GetResult(@PathVariable String select) throws FileNotFoundException {
        String OpenClose = "Open";
//        while(OpenClose.equals("Open")) {
//        while(true){
//            String result = "0";
//            try {

        List<Face> faceList = getResultUtil.cacheResult(jsonFolderPath.toString(), catchJsonName.toString());
        if (faceList == null) {
            System.out.println("null");
            return "0";
        } else {
            Face f = faceList.get(faceList.size() - 1);
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            System.out.println(f.getPersonId());
            System.out.println(f.getSystemTime());
            if(f.getPersonId()!=null){
                System.out.println("not null");
                getInOut(f.getPersonId(), select);
                return f.getPersonId();
            }

//                    System.out.println("result");
        }
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//            try{
//                FileReader fr = new FileReader(enginePath+"\\document\\OpenClose.txt");
//                BufferedReader br = new BufferedReader(fr);
//                OpenClose = br.readLine();
//                fr.close();
//            }catch(IOException e) {
//                e.printStackTrace();
//            }
//            return result;

//        System.out.println("no");
        return "0";
        }

//        System.out.println("GetResult End!");
//    }
    @PostMapping("/Letin/{id}/{select}")
    public void Letin(@PathVariable String id,@PathVariable  String select){
        if(userfacedataService.getInOut(id).equals("Out")) {
            userfacedataService.updateIn(id, select);
        }
    }

    @PostMapping("/Close")
    public void Close() throws IOException {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM RecognizeFace.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw = new FileWriter(enginePath+"\\document\\OpenClose.txt");
        fw.write("Close");
        fw.flush();
        fw.close();
    }

    public String getInOut(String id,String select) {
        if (userfacedataService.getInOut(id).equals("Out") && select.equals("機房")) {
            userfacedataService.updateIn(id, select);
        }else{
            userfacedataService.updateIn(id, "Out");
        }
        return null;
    }


    public void recognition(String usedFaceDB) {

        recognizeFace.setEnginePath(enginePath.toString());
        recognizeFace.setTrainedFaceDBPath(usedFaceDB);
        recognizeFace.setOutputFacePath(outputfacePath.toString());
        recognizeFace.setOutputFramePath(outputframepath.toString());
        recognizeFace.setJsonPath(jsonFolderPath + "\\output");
        recognizeFace.setHideMainWindow(false);
        recognizeFace.setOutputFrame(false);
        recognizeFace.setOutputFace(false);
        recognizeFace.setOnface(true);
        recognizeFace.setThreshold(0.6);
        recognizeFace.setResolution(resolution);
        recognizeFace.setWebcam("1");
        recognizeFace.setMinimumFaceSize(100);
        recognizeFace.setThreads(1);

        engineUtil.recognizeFace(recognizeFace);

    }

    public void stopRecognition(String usedFaceDB){
        recognizeFace.setEnginePath(enginePath.toString());
        recognizeFace.setTrainedFaceDBPath(usedFaceDB);
        recognizeFace.setOutputFacePath(outputfacePath.toString());
        recognizeFace.setOutputFramePath(outputframepath.toString());
        recognizeFace.setJsonPath(jsonFolderPath + "\\output");
        recognizeFace.setHideMainWindow(false);
        recognizeFace.setOutputFrame(false);
        recognizeFace.setOutputFace(false);
        recognizeFace.setOnface(true);
        recognizeFace.setThreshold(0.6);
        recognizeFace.setResolution(resolution);
        recognizeFace.setWebcam("0");
        recognizeFace.setMinimumFaceSize(100);
        recognizeFace.setThreads(1);


        engineUtil.stopRecognizeFace(recognizeFace, RecognizeFace.RECOGNIZEMODE_.LIVENESS);
    }

    public static String modelAppend() throws IOException {

        File folder1 = new File(filepath);
        String[] list1 = folder1.list();
        List<String> faceDBList = new ArrayList<>();


        for (int i = 0; i < list1.length; i++) {
            if (!list1[i].substring(0, 4).equals("date") && !list1[i].equals("AppendDate.txt") && !list1[i].equals("ModelAppend.egroupList")) {
                faceDBList.add(faceDBPath + list1[i]);
            }
        }

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        String formattedDate = myDateObj.format(myFormatObj);


        FileWriter fw = new FileWriter(filepath + "\\AppendDate.txt");
        fw.write(formattedDate);
        fw.flush();
        fw.close();



        FileReader fr = new FileReader(filepath + "\\AppendDate.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }

        String dateAsString = sb.toString();
        fr.close();

        // Model Append
        final ModelAppend modelAppend = new ModelAppend();
        modelAppend.setTrainedFaceDBPath(faceDBPath + "date" + dateAsString + ".faceDB");
        modelAppend.setFaceDBList(faceDBList);
        modelAppend.setListPath(modelAppendListPath.toString());
        modelAppend.setEnginePath(enginePath.toString());
        final ModelAppendResult modelAppendResult = engineUtil.modelAppend(modelAppend, false, 25000);
        LOGGER.info("modelInsertResult : " + new Gson().toJson(modelAppendResult));

        return dateAsString;
    }


}

