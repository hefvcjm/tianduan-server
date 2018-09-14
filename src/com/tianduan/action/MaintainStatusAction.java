package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.JsonResponse;
import com.tianduan.model.MaintainStatus;
import com.tianduan.service.MaintainStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Override
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody MaintainStatus model) {
        return super.create(model);
    }
}
