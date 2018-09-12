package com.tianduan.action;

import com.tianduan.base.*;
import com.tianduan.model.Client;
import com.tianduan.model.Engineer;
import com.tianduan.model.Model;
import com.tianduan.model.User;
import com.tianduan.service.EngineerService;
import com.tianduan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/engineer")
public class EngineerAction extends BaseAction<Engineer> {

    @Autowired
    EngineerService engineerService;
    @Autowired
    private UserService userService;

    @Override
    public EngineerService getService() {
        return engineerService;
    }

    @Override
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Engineer model) {
        User user = userService.getRepository().findByObjectId(model.getUser().getObjectId());
        if (user != null) {
            model.setUser(user);
            return super.create(model);
        }
        return new JsonResponse(new FailDetail("用户不存在"), Message.ExecuteFailSelfDetail);
    }
}
