package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.MaintainStatus;
import com.tianduan.repository.MaintainStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintainStatusService extends BaseService<MaintainStatus> {

    @Autowired
    MaintainStatusRepository maintainStatusRepository;

    @Override
    public MaintainStatusRepository getRepository() {
        return maintainStatusRepository;
    }

}
