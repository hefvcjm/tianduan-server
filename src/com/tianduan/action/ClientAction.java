package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.FailDetail;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Message;
import com.tianduan.base.Util.PropertiesUtil;
import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.Client;
import com.tianduan.model.Role;
import com.tianduan.model.User;
import com.tianduan.service.ClientService;
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
@RequestMapping("/client")
public class ClientAction extends BaseAction<Client> {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public ClientService getService() {
        return clientService;
    }

    @Override
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody Client model) {
        User user = userService.getRepository().findByObjectId(model.getUser().getObjectId());
        if (user != null) {
            Role role = roleService.getRepository().findByName(RolesEnum.CLIENT.getName());
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
