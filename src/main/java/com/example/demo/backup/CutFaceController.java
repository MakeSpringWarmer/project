package com.example.demo.controller;

import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.contorl.GetResultUtil;
import com.example.demo.engine.entity.RecognizeFace;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.io.File;
import java.util.concurrent.TimeUnit;

@RestController
public class CutFaceController {
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
    final static StringBuilder jsonFolderPath = new StringBuilder(enginePath + "\\json");
    final static StringBuilder catchJsonName = new StringBuilder("output.cache.egroup");
    final static StringBuilder modelInserFilePath = new StringBuilder(enginePath + "\\Singal_For_Model_Insert.txt");
    final static String resolution = "720p";

    final static File outputfaceFile = new File(outputfacePath.toString());
    final static File faceDBFile = new File(faceDBPath.toString());
    final static File jsonFolderFile = new File(jsonFolderPath.toString());

    final static StringBuilder jeffFaceDBPath = new StringBuilder(faceDBPath + "\\jeff");


    final static String filepath = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\eGroup";

//    @PostMapping("/CutFace/{name}")
    public Response OutPutFace(@PathVariable String name) throws Exception {
        String FacePath = resourcesPath.toString();
        String CreateFacePath = FacePath + name;

        File newFace = new File(CreateFacePath);
        newFace.mkdir();

        String newFacePath = newFace.toString();
        boolean out = false;

        CutFace(name,newFacePath,out);


       return null;
    }

    public static String CutFace(String name,String newFacePath,boolean out) throws Exception {

        String rootDir = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\resources\\" + name;
        // 轮询间隔 5 秒
        long interval = TimeUnit.SECONDS.toMillis(1);
        //
        FileAlterationObserver observer = new FileAlterationObserver(
                rootDir,
                FileFilterUtils.and(
                        FileFilterUtils.fileFileFilter(),
                        FileFilterUtils.suffixFileFilter(".jpg")),
                null);
        observer.addListener(new MyFileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval,observer);
        // 开始监控
        monitor.start();


        Integer count = 0;


            StringBuffer fileList = new StringBuffer();
                java.io.File folder = new java.io.File(newFacePath);
                String[] list = folder.list();

                    final RecognizeFace recognizeFace = new RecognizeFace();
                    recognizeFace.setEnginePath(enginePath.toString());
                    recognizeFace.setTrainedFaceDBPath(faceDBPath + "empty.faceDB");
                    recognizeFace.setOutputFacePath(resourcesPath + name);
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

                    count++;
                    System.out.println(count);

            return "finish";
    }

}
