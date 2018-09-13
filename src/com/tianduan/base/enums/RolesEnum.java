package com.tianduan.base.enums;

import com.tianduan.model.Role;
import com.tianduan.repository.RoleRepository;
import com.tianduan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum RolesEnum {
    ORDINARY("ordinary", "普通角色"),
    CLIENT("client", "客户角色"),
    ENGINEER("engineer", "工程师角色"),
    ADMIN("admin", "管理员角色");

//    @Autowired
//    private static RoleService roleService;

    private String name;

    private String description;

    private static Map<String, RolesEnum> map = new HashMap<>();

    static {
        for (RolesEnum roles : RolesEnum.values()) {
            map.put(roles.getName(), roles);
        }
    }

    public static RolesEnum getRolesEnumByName(String name) {
        if (map.keySet().contains(name)) {
            return map.get(name);
        }
        return null;
    }

    RolesEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
