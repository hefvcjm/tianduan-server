package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.RepairStatus;
import com.tianduan.repository.RepairStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairStatusService extends BaseService<RepairStatus> {

    @Autowired
    RepairStatusRepository repairStatusRepository;

    @Override
    public RepairStatusRepository getRepository() {
        return repairStatusRepository;
    }

}
