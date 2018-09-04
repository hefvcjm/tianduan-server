package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.model.RepairStatus;
import com.tianduan.service.RepairStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/repair/status")
public class RepairStatusAction extends BaseAction<RepairStatus> {

    @Autowired
    RepairStatusService repairStatusService;

    @Override
    public RepairStatusService getService() {
        return repairStatusService;
    }
}
