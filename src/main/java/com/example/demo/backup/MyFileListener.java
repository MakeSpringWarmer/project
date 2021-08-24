package com.example.demo.controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MyFileListener extends FileAlterationListenerAdaptor{
    public static Logger LOGGER = LoggerFactory.getLogger(TrainFaceController.class);

    Integer count = 0;
    @Override
    public void onFileCreate(File file) {
        count=count+1;
        System.out.println("[新建]:" + file.getAbsolutePath());
        System.out.println(count);
        if(count==10){
            try {
                killProc("RecognizeFace.exe");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static void killProc(String processName) throws IOException {
        LOGGER.info("關閉應用程式：" + processName);
        if (StringUtils.isEmpty(processName)) {
            executeCmd("taskkill /F /IM " + processName);
        }
    }

    public static String executeCmd(String command) throws IOException {

        LOGGER.info("Execute command : " + command);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /c " + command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder build = new StringBuilder();
        while ((line = br.readLine()) != null) {
            LOGGER.info(line);
            build.append(line);
        }
        return build.toString();
    }

}


