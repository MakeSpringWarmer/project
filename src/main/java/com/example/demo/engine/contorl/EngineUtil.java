package com.example.demo.engine.contorl;

import com.example.demo.engine.util.CheckStatusUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.example.demo.engine.util.AttributeCheck;
import com.example.demo.engine.util.CmdUtil;
import com.example.demo.engine.util.TxtUtil;
import com.example.demo.engine.util.TxtUtil.Charsets;
import com.example.demo.engine.entity.ModelAppend;
import com.example.demo.engine.entity.ModelAppendResult;
import com.example.demo.engine.entity.ModelCompare;
import com.example.demo.engine.entity.ModelInsert;
import com.example.demo.engine.entity.ModelInsertResult;
import com.example.demo.engine.entity.ModelSwitch;
import com.example.demo.engine.entity.ModelSwitchResult;
import com.example.demo.engine.entity.RecognizeFace;
import com.example.demo.engine.entity.RecognizeFace.RECOGNIZEMODE_;
import com.example.demo.engine.entity.TrainFace;
import com.example.demo.engine.entity.TrainResult;

public class EngineUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(CmdUtil.class);

    public TrainResult trainFace(TrainFace trainFace, boolean deleteTrainResultStatus) {
        // init func
        final AttributeCheck attributeCheck = new AttributeCheck();
        final CheckStatusUtil checkStatusUtil = new CheckStatusUtil();
        // init variabl
        TrainResult trainResult = new TrainResult();

        trainFace.generateCli();
        if (attributeCheck.listNotEmpty(trainFace.getCommandList())) {
            final CmdUtil cmdUtil = new CmdUtil();
            String trainResultLogPath = trainFace.getEnginePath() + "\\Status.TrainResultCPU.eGroup";
            if (cmdUtil.cmdProcessBuilder(trainFace.getCommandList())) {
                // init variable
                trainResult = checkStatusUtil.trainFace(trainResultLogPath);
                if (deleteTrainResultStatus) {
                    try {
                        Files.delete(Paths.get(trainResultLogPath));
                    } catch (IOException e) {
                        LOGGER.error(new Gson().toJson(e));
                    }
                }
            } else {
                trainResult.setTrainCmdSuccess(false);
            }
        } else {
            trainResult.setTrainCmdSuccess(false);
        }
        return trainResult;
    }


    public boolean modelCompare(ModelCompare modelCompare) {
        boolean flag = false;
        // init func
        modelCompare.generateCli();
        if (modelCompare.getCommandList() != null) {
            final CmdUtil cmdUtil = new CmdUtil();
            flag = cmdUtil.cmdProcessBuilder(modelCompare.getCommandList());
        }
        return flag;
    }

    public boolean stopRecognizeFace(RecognizeFace recognizeFace, RECOGNIZEMODE_ recognizeMode_) {
        boolean flag = false;
        // init func
        recognizeFace.getStopCli(recognizeMode_);
        if (recognizeFace.getCommandList() != null) {
            final CmdUtil cmdUtil = new CmdUtil();
            flag = cmdUtil.cmdProcessBuilder(recognizeFace.getCommandList());
        }
        return flag;
    }

    public boolean recognizeFace(RecognizeFace recognizeFace) {
        boolean flag = false;
        // init func
        recognizeFace.generateCli();
        LOGGER.info("cli=" + recognizeFace.getCli());
        if (recognizeFace.getCommandList() != null) {
            final CmdUtil cmdUtil = new CmdUtil();
            flag = cmdUtil.cmdProcessBuilder(recognizeFace.getCommandList());
        }
        return flag;
    }

    public HashMap<String, Boolean> recognizeFace(List<RecognizeFace> recognizeFaceList, boolean waitRecognizeDone) {
        // init func
        final Gson gson = new Gson();
        // init variable
        HashMap<String, Boolean> hashMap = new HashMap<>();
        Thread RECOGNITION_THREAD;

        if (waitRecognizeDone) {
            // init variable
            final ExecutorService executorService = Executors.newFixedThreadPool(recognizeFaceList.size());
            final List<Future<String>> resultList = new ArrayList<Future<String>>();

            for (int i = 0; i < recognizeFaceList.size(); i++) {
                final int index = i + 1;
                final RecognizeFace recognizeFace_fix = recognizeFaceList.get(i);
                Future future = executorService.submit(new Callable() {
                    public Object call() throws Exception {
                        // init func
                        final CmdUtil cmdUtil = new CmdUtil();
                        recognizeFace_fix.generateCli();
                        if (recognizeFace_fix.getCommandList() != null) {
                            boolean flag = cmdUtil.cmdProcessBuilder(recognizeFace_fix.getCommandList());
                            hashMap.put(gson.toJson(recognizeFace_fix), flag);
                        }
                        return "???????????????:" + index + "????????????";
                    }
                });
                resultList.add(future);
            }
            // Monitor execute thread status
            executorService.shutdown();
            for (Future<String> fs : resultList) {
                try {
                    while (!fs.isDone());
                    LOGGER.debug(fs.get());
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error(new Gson().toJson(e));
                }
            }
        } else {
            for (RecognizeFace recognizeFace : recognizeFaceList) {
                RecognizeFace recognizeFace_fix = recognizeFace;
                RECOGNITION_THREAD = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // init func
                        final CmdUtil cmdUtil = new CmdUtil();
                        recognizeFace_fix.generateCli();
                        if (recognizeFace_fix.getCommandList() != null) {
                            boolean flag = cmdUtil.cmdProcessBuilder(recognizeFace_fix.getCommandList());
                            hashMap.put(gson.toJson(recognizeFace_fix), flag);
                        }
                    }
                });
                RECOGNITION_THREAD.start();
            }
        }


        return hashMap;
    }

    public ModelAppendResult modelAppend(ModelAppend modelAppend, boolean deleteModelAppendStatus, long waitTime) {
        // init func
        final AttributeCheck attributeCheck = new AttributeCheck();
        // init variable
        ModelAppendResult modelAppendResult = new ModelAppendResult();
        if (modelAppend != null
                && attributeCheck.stringsNotNull(modelAppend.getEnginePath(), modelAppend.getListPath(), modelAppend.getTrainedFaceDBPath())
                && (attributeCheck.listNotEmpty(modelAppend.getFaceDBList()) || !modelAppend.getFaceDBHashset().isEmpty())) {
            // init func
            final TxtUtil txtUtil = new TxtUtil();
            final CheckStatusUtil checkStatusUtil = new CheckStatusUtil();
            // init variable
            final List<String> dataList = new ArrayList<>();
            final String modelAppendStatusPath = modelAppend.getEnginePath() + "\\Status.ModelAppend.eGroup";

            if (!modelAppend.getFaceDBHashset().isEmpty()) {
                for (String faceDBPath : modelAppend.getFaceDBHashset()) {
                    dataList.add(faceDBPath);
                }
            } else {
                final int modelCount = modelAppend.getFaceDBList().size();
                for (int i = 0; i < modelCount; i++) {
                    dataList.add(modelAppend.getFaceDBList().get(i));
                }
            }
            txtUtil.create(modelAppend.getListPath(), dataList, Charsets.BIG5);
            modelAppend.generateCli(modelAppend.getEnginePath());
            if (modelAppend.getCommandList() != null) {
                final CmdUtil cmdUtil = new CmdUtil();
                if (cmdUtil.cmdProcessBuilder(modelAppend.getCommandList())) {
                    modelAppendResult = checkStatusUtil.modelAppend(modelAppendStatusPath, waitTime);

                    if (deleteModelAppendStatus) {
                        try {
                            Files.delete(Paths.get(modelAppendStatusPath));
                        } catch (IOException e) {
                            LOGGER.error(new Gson().toJson(e));
                        }
                    }
                } else {
                    modelAppendResult.setAppendCmdSuccess(false);
                }
            } else {
                modelAppendResult.setAppendCmdSuccess(false);
            }
        } else {
            modelAppendResult.setAppendCmdSuccess(false);
        }
        return modelAppendResult;
    }

    public ModelSwitchResult modelSwitch(ModelSwitch modelSwitch, boolean deleteModelSwitchStatus) {
        // init func
        final AttributeCheck attributeCheck = new AttributeCheck();
        // init variable
        boolean flag = false;
        ModelSwitchResult modelSwitchResult = new ModelSwitchResult();
        if (modelSwitch != null && attributeCheck.stringsNotNull(modelSwitch.getNewModelPath(), modelSwitch.getSwitchFilePath(),
                modelSwitch.getEnginePath(), modelSwitch.getModelSwitchStatusPath())) {
            // init variable
            final String newModelFaceDB_path = modelSwitch.getNewModelPath() + ".faceDB";
            final File newModelFaceDB_file = new File(newModelFaceDB_path);

            if (newModelFaceDB_file.exists()) {
                // init func
                final CheckStatusUtil checkStatusUtil = new CheckStatusUtil();
                // Model
                final List<String> dataList = new ArrayList<>();
                dataList.add(newModelFaceDB_path);

                // init func
                final TxtUtil txtUtil = new TxtUtil();
                flag = txtUtil.create(modelSwitch.getSwitchFilePath(), dataList, Charsets.BIG5);
                if (flag) {
                    modelSwitchResult = checkStatusUtil.modelSwitch(modelSwitch.getModelSwitchStatusPath());
                    if (modelSwitchResult != null && deleteModelSwitchStatus) {
                        try {
                            Files.delete(Paths.get(modelSwitch.getModelSwitchStatusPath()));
                        } catch (IOException e) {
                            LOGGER.error(new Gson().toJson(e));
                        }
                    }
                }
            }
        }
        return modelSwitchResult;
    }

    public ModelInsertResult modelInsert(ModelInsert modelInsert, boolean deleteModelInsertStatusFlag, long waitTimeMs) {
        // init func
        final AttributeCheck attributeCheck = new AttributeCheck();
        // init variable
        ModelInsertResult modelInsertResult = new ModelInsertResult();

        if (modelInsert != null && attributeCheck.listNotEmpty(modelInsert.getFaceDBList()) && attributeCheck.stringsNotNull(modelInsert.getListPath())) {
            // init func
            final TxtUtil txtUtil = new TxtUtil();
            final CheckStatusUtil checkStatusUtil = new CheckStatusUtil();
            // init variable
            final int modelCount = modelInsert.getFaceDBList().size();
            final List<String> dataList = new ArrayList<>();
            final String modelInsertLog_path = modelInsert.getEnginePath() + "\\Status.ModelInsert.eGroup";
            final File modelInsertLog_file = new File(modelInsertLog_path);

            for (int i = 0; i < modelCount; i++) {
                dataList.add(modelInsert.getFaceDBList().get(i));
            }
            txtUtil.create(modelInsert.getListPath(), dataList, Charsets.BIG5);
            modelInsertResult = checkStatusUtil.modelInsert(modelInsertLog_path, waitTimeMs);
            if (deleteModelInsertStatusFlag) {
                modelInsertLog_file.delete();
            }
        }
        return modelInsertResult;
    }
}
