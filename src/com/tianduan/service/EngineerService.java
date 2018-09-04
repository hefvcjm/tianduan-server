package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.Engineer;
import com.tianduan.repository.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineerService extends BaseService<Engineer> {

    @Autowired
    EngineerRepository engineerRepository;

    @Override
    public EngineerRepository getRepository() {
        return engineerRepository;
    }

}
