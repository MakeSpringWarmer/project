package com.example.demo.controller;

import com.example.demo.engine.entity.RecognizeFace;
import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
import com.example.demo.engine.contorl.GetResultUtil;
import com.example.demo.engine.entity.Face;
import com.example.demo.engine.entity.RecognizeFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class test {
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

    public static void main(String args[])throws IOException{

//        cutFace(faceDBPath +"empty.faceDB");
        taskkill();

    }

    public static void taskkill(){
        final RecognizeFace recognizeFace = new RecognizeFace();
        recognizeFace.setEnginePath("taskkill /f /im notepad.exe");
        // Start recognition
        engineUtil.recognizeFace(recognizeFace);


    }

    public static void cutFace(String usedFaceDB){
        final RecognizeFace recognizeFace = new RecognizeFace();
        recognizeFace.setEnginePath(enginePath.toString());
        recognizeFace.setTrainedFaceDBPath(usedFaceDB);
        recognizeFace.setOutputFacePath(outputfacePath.toString());
        recognizeFace.setOutputFramePath(outputframepath.toString());
        recognizeFace.setJsonPath(jsonFolderPath + "\\output");
        recognizeFace.setHideMainWindow(true);
        recognizeFace.setOutputFrame(false);
        recognizeFace.setOutputFace(true);
        recognizeFace.setOnface(true);
        recognizeFace.setThreshold(0.6);
        recognizeFace.setResolution(resolution);
        recognizeFace.setWebcam("0");
        recognizeFace.setMinimumFaceSize(100);
        recognizeFace.setThreads(1);
        // Start recognition
        engineUtil.recognizeFace(recognizeFace);


    }

    public static void readData() throws IOException {
        FileReader fr = new FileReader("C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup\\AppendDate.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
        }

        String dateAsString = sb.toString();
        System.out.println(dateAsString);
    }

    public static void writeData() throws IOException{
        FileWriter fw = new FileWriter("C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup\\write.txt");
        fw.write("HI GOGOGO!");
        fw.flush();
        fw.close();
    }


    public static void writeTime() throws IOException{
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println("Time Now " + formattedDate);

        FileWriter fw = new FileWriter("C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup\\AppendDate.txt");
        fw.write(formattedDate);
        fw.flush();
        fw.close();

    }

    public static void getDataName(){
            File folder1 = new File("C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup");
            String[] list1 = folder1.list();

            for (int i = 0; i < list1.length; i++) {
                if (!list1[i].substring(0, 4).equals("date") && !list1[i].equals("AppendDate.txt")&& !list1[i].equals("ModelAppend.egroupList")) {
                    System.out.println(list1[i]);
                }
//        String folderPath = new String();
//        folderPath = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup";
//        StringBuffer fileList = new StringBuffer();
//
//        try{
//            java.io.File folder = new java.io.File(folderPath);
//            String[] list = folder.list();
//            for(int i = 0; i < list.length; i++){
//                if()
//                fileList.append(list[i]).append(", ");
//            }
//        }catch(Exception e){
//            System.out.println("'"+folderPath+"'此資料夾不存在");
//        }
//        System.out.println(fileList);
            }
        }
}
