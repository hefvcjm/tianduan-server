package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.Repair;
import com.tianduan.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairService extends BaseService<Repair> {

    @Autowired
    RepairRepository repairRepository;

    @Override
    public RepairRepository getRepository() {
        return repairRepository;
    }

}
