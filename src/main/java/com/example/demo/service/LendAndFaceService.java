package com.example.demo.service;

import com.example.demo.entity.FaceAndLend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface LendAndFaceService {

    @Autowired
    public List<FaceAndLend> ifroom();

}
