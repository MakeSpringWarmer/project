package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@RestController
@CrossOrigin
public class getPhothController {

    String Path = "C:\\github\\StudentFace\\";

    @RequestMapping(value="/uploadphoto/{name}",method = RequestMethod.POST)
    public Object fileUpload(@PathVariable String name, MultipartFile file) throws IllegalAccessException,IOException{
        if (file==null){
            System.out.println("file does not exist");
            return false;
        }
        if(file.getSize()==0){
            return false;
        }

        String CreateFolder = Path + name;
        File newFolder = new File(CreateFolder);
        newFolder.mkdir();

        System.err.println("文件是否為空 ： " + file.isEmpty());
        System.err.println("文件大小為 ：" + file.getSize());
        System.err.println("文件類行為 ： " + file.getContentType());
        System.err.println("文件的名字： " + file.getName());
        System.err.println("文件的originName為： " + file.getOriginalFilename());

        File newFile = new File(CreateFolder+ "\\" + name+".jpg");
        file.transferTo(newFile);

        return new ModelAndView("redirect:/upload/"+name);

    }


}
