package com.example.demo.controller;

import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.entity.ModelAppend;
import com.example.demo.engine.entity.ModelAppendResult;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
import com.example.demo.engine.contorl.GetResultUtil;
import com.example.demo.engine.entity.Face;
import com.example.demo.engine.entity.RecognizeFace;
import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class RecognizeFaceController2 {

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
    // final static StringBuilder allJsonName = new StringBuilder("output." + LocalDate.now() + ".egroup");
    final static StringBuilder modelInserFilePath = new StringBuilder(enginePath + "\\Singal_For_Model_Insert.txt");
    final static String resolution = "720p";

    final static File outputfaceFile = new File(outputfacePath.toString());
    final static File faceDBFile = new File(faceDBPath.toString());
    final static File outputframeFile = new File(outputframepath.toString());
    final static File jsonFolderFile = new File(jsonFolderPath.toString());

    final static StringBuilder jeffFaceDBPath = new StringBuilder(faceDBPath + "\\jeff");
//    final static StringBuilder FaceImageFolderPath = new StringBuilder(resourcesPath + "jeff");
//    final static File FaceImageFolder = new File(FaceImageFolderPath.toString());

    final static String filepath = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup";
    final static RecognizeFace recognizeFace = new RecognizeFace();

    @Autowired
    private  static UserfacedataService userfacedataService;

    @PostMapping("/Recognize/Open2")
    public ModelAndView RecognizeFace() throws IOException {


//        modelAppend();
        String time = modelAppend();
        System.out.println(time);

        Thread recognitionThread = new Thread(new Runnable() {

            public void run() {
                recognition(faceDBPath + "date" + time +".faceDB");
            }
        });

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        recognitionThread.start();
        List<Face> faceList = getResultUtil.cacheResult(jsonFolderPath.toString(), catchJsonName.toString());
        scheduledThreadPool.scheduleAtFixedRate(new getName(faceList), 5, 1, TimeUnit.SECONDS);


        return new ModelAndView("redirect:/test/");
    }

    @PostMapping("/Recognize/Close2")
    public Response closeRecognize(){
        System.out.println("before close");
        engineUtil.stopRecognizeFace(recognizeFace, RecognizeFace.RECOGNIZEMODE_.LIVENESS);
        System.out.println("after close");
        return null;
    }

    public static class getName implements Runnable {
        private List<Face> faceList;

        public getName(List<Face> faceList) {
            this.faceList = faceList;
        }

        public void run() {
            faceList = getResultUtil.cacheResult(jsonFolderPath.toString(), catchJsonName.toString());
            if (faceList == null) {
                System.out.println("null");
            } else {
                Face f = faceList.get(faceList.size() - 1);
                System.out.println(f.getPersonId());
                System.out.println(f.getSystemTime());
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);
                System.out.println("Time Now " + formattedDate);
            }
        }
        public static String getInOut(String id){
            System.out.println(userfacedataService.getInOut(id));
            return userfacedataService.getInOut(id);
        }

    }

    public static void passID(String id){
        String Id = id;
    }


    public static void recognition(String usedFaceDB) {

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
        // Start recognition
        engineUtil.recognizeFace(recognizeFace);
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