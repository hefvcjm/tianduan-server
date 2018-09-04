package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.model.MaintainStatus;
import com.tianduan.service.MaintainStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/maintain/status")
public class MaintainStatusAction extends BaseAction<MaintainStatus> {

    @Autowired
    MaintainStatusService maintainStatusService;

    @Override
    public MaintainStatusService getService() {
        return maintainStatusService;
    }
}
