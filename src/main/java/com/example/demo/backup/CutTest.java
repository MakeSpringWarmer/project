package com.example.demo.controller;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class CutTest {
    public static void main(String[] args) throws Exception {
        // 监控目录
        String rootDir = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\resources\\Jeff";
        // 轮询间隔 5 秒
        long interval = TimeUnit.SECONDS.toMillis(1);
        //
        FileAlterationObserver observer = new FileAlterationObserver(
                rootDir,
                FileFilterUtils.and(
                        FileFilterUtils.fileFileFilter(),
                        FileFilterUtils.suffixFileFilter(".txt")),
                null);
        observer.addListener(new MyFileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval,observer);
        // 开始监控
        monitor.start();

    }
}
