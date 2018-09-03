package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Util.HttpUtil;
import com.tianduan.model.Repair;
import com.tianduan.model.User;
import com.tianduan.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Scope("prototype")
@RequestMapping("repair")
public class RepairAction extends BaseAction<Repair> {

    @Autowired
    RepairRepository repairRepository;

    @Override
    public RepairRepository getRepository() {
        return repairRepository;
    }

    @Override
    @RequestMapping(value = {"", "/new"}, method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Repair repair) {
        User user = HttpUtil.getCurrentUser(request);
        repair.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return super.create(repair);
    }

}
