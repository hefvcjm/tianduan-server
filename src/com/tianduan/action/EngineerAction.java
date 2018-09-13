package com.tianduan.action;

import com.tianduan.base.*;
import com.tianduan.base.Util.PropertiesUtil;
import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.*;
import com.tianduan.service.EngineerService;
import com.tianduan.service.RoleService;
import com.tianduan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Scope("prototype")
@RequestMapping("/engineer")
public class EngineerAction extends BaseAction<Engineer> {

    @Autowired
    EngineerService engineerService;
    @Autowired
    private UserService userService;
    @Autowired
    RoleService roleService;

    @Override
    public EngineerService getService() {
        return engineerService;
    }

    @Override
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Engineer model) {
        User user = userService.getRepository().findByObjectId(model.getUser().getObjectId());
        if (user != null) {
            Role role = roleService.getRepository().findByName(RolesEnum.ENGINEER.getName());
            if (PropertiesUtil.checkRoleIsOverlap(user, role)) {
                return new JsonResponse(new FailDetail("角色冲突"), Message.ExecuteFailSelfDetail);
            }
            user.addRole(role);
            model.setUser(user);
            return super.create(model);
        }
        return new JsonResponse(new FailDetail("用户不存在"), Message.ExecuteFailSelfDetail);
    }
}
