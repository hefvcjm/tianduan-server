package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.FailDetail;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Message;
import com.tianduan.base.Util.FileUtil;
import com.tianduan.base.Util.HttpUtil;
import com.tianduan.base.Util.PropertiesUtil;
import com.tianduan.base.enums.RepairStatusesEnum;
import com.tianduan.model.Client;
import com.tianduan.model.Repair;
import com.tianduan.model.RepairStatus;
import com.tianduan.model.User;
import com.tianduan.service.ClientService;
import com.tianduan.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public JsonResponse create(@RequestBody Repair repair, HttpServletRequest request) {
        Client client = clientService.getRepository().findByUser(HttpUtil.getCurrentUser(request));
        if (client == null) {
            return new JsonResponse(new FailDetail("无权限报修"), Message.ExecuteFailSelfDetail);
        }
        repair.setClient(client);
        repair.setTicket(UUID.randomUUID().toString().replace("-", ""));
        repair.setObjectId(UUID.randomUUID().toString().replace("-", ""));
        repair.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        RepairStatus status = new RepairStatus();
        status.setRepair(repair);
        status.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        status.setStatus(RepairStatusesEnum.COMMITED.getName());
        status.setObjectId(UUID.randomUUID().toString().replace("-", ""));
        if (repair.getStatuses() == null) {
            repair.setStatuses(new HashSet<>());
            repair.addStatus(status);
        }
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = headerNames.nextElement();
//            headers.add(key, request.getHeader(key));
//        }
//        ResponseEntity<JsonResponse> response = restTemplate.exchange("http://localhost:8080/tianduan/repair/status/new"
//                , HttpMethod.PUT
//                , new HttpEntity<RepairStatus>(status, headers)
//                , JsonResponse.class);
//        logger.info(response.getBody());
        return super.create(repair);
    }

    @RequestMapping(value = "/queryall", method = RequestMethod.GET)
    public JsonResponse queryall() {
        Client client = clientService.getRepository().findByUser(HttpUtil.getCurrentUser(request));
        if (client == null) {
            return new JsonResponse(new FailDetail("无权限报修"), Message.ExecuteFailSelfDetail);
        }
        Repair[] repairs = repairService.getRepository().findByClient(client);
        return new JsonResponse(repairs);
    }

    @RequestMapping(value = "/file/{objectId}", method = RequestMethod.POST)
    public JsonResponse addFiles(@PathVariable String objectId, @RequestParam MultipartFile[] pictures, @RequestParam MultipartFile[] audios, @RequestParam MultipartFile[] videos, HttpServletRequest request) {
        Repair repair = repairService.getRepository().findByObjectId(objectId);
        if (repair == null) {
            return new JsonResponse(Message.ExecuteFail);
        }
        /*报修信息文件存放格式：
         * [base-path]\[user.ObjectId]\repairs\[repairs.objectId]\[pictures|audios|videos]\[order-file]
         * 其中file按顺序命名，0开始,后面接上原始名称
         */
        User user = HttpUtil.getCurrentUser(request);
        if (user == null) {
            return new JsonResponse(Message.ExecuteFail);
        }
        repair.setPictures(saveFiles(pictures, user, repair.getObjectId(), "pictures", null).toString());
        repair.setAudios(saveFiles(audios, user, repair.getObjectId(), "audios", null).toString());
        repair.setVideos(saveFiles(videos, user, repair.getObjectId(), "videos", null).toString());
        return super.update(repair);
    }

    private List<String> saveFiles(MultipartFile[] files, User user, String objectId, String subclass, String fileType) {
        String path = PropertiesUtil.getProperties("upload.file.save.base-path") + "\\"
                + user.getObjectId() + "\\" + "repairs" + "\\"
                + objectId;
        int i = 0;
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            String realPath = path + "\\" + subclass + "\\";
            String name = i + "-" + file.getOriginalFilename();
            try {
                FileUtil.saveFile(file.getBytes(), realPath, name);
                paths.add(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        return paths;
    }

}
