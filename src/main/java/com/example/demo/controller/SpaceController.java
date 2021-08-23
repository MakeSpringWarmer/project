package com.example.demo.controller;

import com.example.demo.dao.LendDAO;
import com.example.demo.dao.RoomDAO;
import com.example.demo.entity.FaceAndLend;
import com.example.demo.entity.Lend;
import com.example.demo.entity.userfacedata;
import com.example.demo.service.LendAndFaceService;
import com.example.demo.service.UserfacedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.Path;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
public class SpaceController {

    @Autowired
    private UserfacedataService userfacedataService;
    private LendAndFaceService lendAndFaceService;
    private LendDAO dao;

    @GetMapping("/Space/{room}")
    public List<userfacedata> showIn(@PathVariable String room) throws SQLException {
        return userfacedataService.findIn(room);
    }

    @PostMapping("/faceandroom")
    public List<FaceAndLend> faceandroom()throws SQLException{
        return lendAndFaceService.ifroom();
    }


}
