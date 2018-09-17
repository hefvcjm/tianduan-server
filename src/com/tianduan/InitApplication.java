package com.tianduan;

import com.tianduan.base.Util.HttpUtil;
import com.tianduan.base.Util.PasswordUtil;
import com.tianduan.base.Util.TokenUtil;
import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.Role;
import com.tianduan.model.User;
import com.tianduan.service.RoleService;
import com.tianduan.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class InitApplication implements ApplicationRunner {

    protected static Logger logger = Logger.getLogger(InitApplication.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        init();
        logger.info("finished init!");
    }

    private static final String ADMIN_USER = "$admin";
    private static final String ADMIN_PWD = "admin";
    private static final String ADMIN_NAME = "admin";
    private static final String ADMIN_PHONE = "123456";

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
        User user = userService.getRepository().findByPhone(ADMIN_PHONE);
        if (user == null) {
            user = new User();
            user.setPhone(ADMIN_PHONE);
            user.setUsername(ADMIN_USER);
            user.setName(ADMIN_NAME);
            user.setRegistertime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            user.setObjectId(UUID.randomUUID().toString().replace("-", ""));
            user.setToken(TokenUtil.getToken(user.getPhone(), user.getObjectId()));
            user.setPassword(PasswordUtil.encode(ADMIN_PWD));
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRepository().findByName(RolesEnum.ADMIN.getName()));
            user.setRoles(roles);
            userService.getRepository().save(user);
        }
        HttpUtil.initHandlerMethodMap(requestMappingHandlerMapping.getHandlerMethods());
    }
}
