package com.example.demo.controller;


import com.example.demo.entity.userfacedata;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class faceController {

    @Autowired
    private UserfacedataService userfacedataService;


    @GetMapping("/userface")
    public List<userfacedata> getAll(){
        System.out.println("getAll called");
        List<userfacedata> result = userfacedataService.findAll();
        /*
        for (int i =0; i<result.size(); i++) {
            System.out.println(result.get(i).getUserFace_Name());
        }
        System.out.println(result.size());
        */
        return result;
    }

    @GetMapping("/userwithoutroot")
    public List<userfacedata> getNotRoot(){
        List<userfacedata> result = userfacedataService.findList();
        return result;
    }

    @GetMapping("/useridlist/{id}")
    public List<String> getIdList(@PathVariable("id") String userface_id){
        List<String> result = userfacedataService.findIdList(userface_id);
        return result;

    }

    @GetMapping("/userface/{id}")
    public userfacedata getOne(@PathVariable("id") String userface_id ){
        System.out.println("getSingle called id:" + userface_id) ;
        userfacedata result = userfacedataService.findOne(userface_id);
        System.out.println(result.getUserFace_Id());
        return result;
    }

    @GetMapping("/userfaceid")
    public List<String> getallid(){

        System.out.println("called");
        List<String> result = userfacedataService.getid();
        System.out.println("called after"+result.size());
        //List<String> result = ""
        return result;
    }

    @PostMapping("/userface")
    public int insert(@RequestBody  userfacedata userfacedata){
        System.out.println("called POST");
        int result = userfacedataService.insert(userfacedata);
        System.out.println(result);
        return result;
    }

    @PutMapping("/userface/{id}")
    public void updateState(@PathVariable("id") String userface_id){
        userfacedataService.updateState(userface_id);
    }

    @PutMapping("/userfacecode/{id}/{code}")
    public void updateCode(@PathVariable("id") String userface_id , @PathVariable("code") String userface_state){
        System.out.println(userface_state);
        userfacedataService.updateCode(userface_id , userface_state);
    }
}
