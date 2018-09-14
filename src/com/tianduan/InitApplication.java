package com.tianduan;

import com.tianduan.base.Util.HttpUtil;
import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.Role;
import com.tianduan.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class InitApplication implements ApplicationRunner {

    protected static Logger logger = Logger.getLogger(InitApplication.class);

    @Autowired
    private RoleService roleService;

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        init();
        logger.info("finished init!");
    }

    private void init() {
        for (RolesEnum roles : RolesEnum.values()) {
            Role role = roleService.getRepository().findByName(roles.getName());
            if (role == null) {
                role = new Role();
                role.setName(roles.getName());
                role.setDescription(roles.getDescription());
                role.setObjectId(UUID.randomUUID().toString().replace("-", ""));
                roleService.getRepository().save(role);
            }
        }
        HttpUtil.initHandlerMethodMap(requestMappingHandlerMapping.getHandlerMethods());
    }
}
