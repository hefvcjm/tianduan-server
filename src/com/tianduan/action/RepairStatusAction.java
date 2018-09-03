package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.model.RepairStatus;
import com.tianduan.repository.RepairStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/repair/status")
public class RepairStatusAction extends BaseAction<RepairStatus> {

    @Autowired
    RepairStatusRepository repairStatusRepository;

    @Override
    public RepairStatusRepository getRepository() {
        return repairStatusRepository;
    }
}
