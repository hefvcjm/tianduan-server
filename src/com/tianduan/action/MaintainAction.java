package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.FailDetail;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Message;
import com.tianduan.model.Engineer;
import com.tianduan.model.Maintain;
import com.tianduan.model.Repair;
import com.tianduan.service.EngineerService;
import com.tianduan.service.MaintainService;
import com.tianduan.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/maintain")
public class MaintainAction extends BaseAction<Maintain> {

    @Autowired
    MaintainService maintainService;

    @Autowired
    RepairService repairService;

    @Autowired
    EngineerService engineerService;

    @Override
    public MaintainService getService() {
        return maintainService;
    }

    @Override
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Maintain model) {
        Repair repair = repairService.getRepository().findByObjectId(model.getRepair().getObjectId());
        if (repair == null) {
            return new JsonResponse(new FailDetail("不存在该维修单"), Message.ExecuteFailSelfDetail);
        }
        model.setRepair(repair);
        return super.create(model);
    }
}
