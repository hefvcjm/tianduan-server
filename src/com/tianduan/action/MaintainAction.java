package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.FailDetail;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Message;
import com.tianduan.base.Util.HttpUtil;
import com.tianduan.base.enums.MaintainStatusesEnum;
import com.tianduan.base.enums.RepairStatusesEnum;
import com.tianduan.model.*;
import com.tianduan.service.EngineerService;
import com.tianduan.service.MaintainService;
import com.tianduan.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Maintain maintain, HttpServletRequest request) {
        Repair repair = repairService.getRepository().findByObjectId(maintain.getRepair().getObjectId());
        User user = HttpUtil.getCurrentUser(request);
        if (user == null) {
            return new JsonResponse(new FailDetail("账号未登录"), Message.ExecuteFailSelfDetail);
        }
        if (repair == null) {
            return new JsonResponse(new FailDetail("不存在该维修单"), Message.ExecuteFailSelfDetail);
        }
        Engineer engineer = engineerService.getRepository().findByUser(user);
        if (engineer == null) {
            return new JsonResponse(new FailDetail("不存在该工程师"), Message.ExecuteFailSelfDetail);
        }
        Set<Engineer> engineerSet = new HashSet<>();
        engineerSet.add(engineer);
        Set<MaintainStatus> maintainStatusSet = new HashSet<>();
        maintain.setEngineers(engineerSet);
        maintain.setStatuses(maintainStatusSet);
        maintain.setObjectId(UUID.randomUUID().toString().replace("-", ""));
        maintain.setRepair(repair);
        MaintainStatus status = new MaintainStatus();
        status.setMaintain(maintain);
        status.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        status.setStatus(MaintainStatusesEnum.HANDLING.getName());
        status.setObjectId(UUID.randomUUID().toString().replace("-", ""));
        if (repair.getStatuses() == null) {
            maintain.setStatuses(new HashSet<>());
            maintain.addStatus(status);
        }
        return super.create(maintain);
    }
}
