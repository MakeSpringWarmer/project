package com.example.demo.service;

import com.example.demo.entity.userfacedata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface UserfacedataService {

    @Autowired


    public List<userfacedata> findAll();

    public userfacedata findOne(String userface_id);

    public List<String> getid();

    public String getInOut(String userface_id);

    public List<userfacedata> findList();

    public int insert(userfacedata userfacedata);

    public int updateState(String userface_id);

    public int updateIn(String userface_id, String room);

    public int updateOut(String userface_id);

    public List<userfacedata> findIn(String room);


    int updateCode(String userface_id , String userface_state);


}
