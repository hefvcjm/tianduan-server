package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.Maintain;
import com.tianduan.repository.MaintainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintainService extends BaseService<Maintain> {

    @Autowired
    MaintainRepository maintainRepository;

    @Override
    public MaintainRepository getRepository() {
        return maintainRepository;
    }

}
