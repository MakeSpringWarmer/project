package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RestController
public class UploadFileController {

    String NewPath = "C:\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20\\resources";
    String OldPath = "C:\\github\\StudentFace";


    @GetMapping("/upload/{name}")
    @CrossOrigin
    public ModelAndView uploadfile(@PathVariable String name){
        StringBuffer fileList = new StringBuffer();
            try{
                java.io.File folder = new java.io.File(OldPath);
                String[] list = folder.list();
                    for(int i =0;i<list.length;i++){
                        fileList.append(list[i]);
                        if(name.equals(list[i])) {
                            String TemPath = OldPath +"\\"+ name;
                            String TemNewPath = NewPath +"\\" + name;
                            moveFolder(TemPath,TemNewPath);
                        }
                    }
            }catch(Exception e){
                System.out.println("file not found");
            }

//        return new ModelAndView("redirect:/Train/"+name);
        return null;
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i ++ ) {
                boolean isDelete = deleteDir(new File(dir, children[i]));
                if (!isDelete) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File filelist = new File(oldPath);
            String[] file = filelist.list();
            File temp = null;
            for (int i = 0; i < file.length; i ++ ) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/"  + (temp.getName()).toString());
                    byte[] bufferarray = new byte[1024 * 64];
                    int prereadlength;
                    while ((prereadlength = input.read(bufferarray)) != -1) {
                        output.write(bufferarray, 0, prereadlength);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" +  file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("複製整個資料夾內容操作出錯");
        }
    }
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        deleteDir(new File(oldPath));
    }
}
