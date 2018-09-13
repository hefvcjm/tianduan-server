package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Util.HttpUtil;
import com.tianduan.model.Client;
import com.tianduan.model.Repair;
import com.tianduan.model.User;
import com.tianduan.service.ClientService;
import com.tianduan.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Scope("prototype")
@RequestMapping("/repair")
public class RepairAction extends BaseAction<Repair> {

    @Autowired
    RepairService repairService;
    @Autowired
    ClientService clientService;

    @Override
    public RepairService getService() {
        return repairService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Repair repair, @RequestPart MultipartFile[] pictures, @RequestPart MultipartFile[] audios, @RequestPart MultipartFile[] videos) {
        Client client = clientService.getRepository().findByObjectId(repair.getClient().getObjectId());
        repair.setClient(client);
        repair.setTicket(UUID.randomUUID().toString().replace("-", ""));
        repair.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return super.create(repair);
    }

}
