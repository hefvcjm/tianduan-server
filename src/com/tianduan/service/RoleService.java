package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.Role;
import com.tianduan.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role> {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }
}
