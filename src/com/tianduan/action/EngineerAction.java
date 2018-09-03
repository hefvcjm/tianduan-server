package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.model.Engineer;
import com.tianduan.repository.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/engineer")
public class EngineerAction extends BaseAction<Engineer> {

    @Autowired
    EngineerRepository engineerRepository;

    @Override
    public EngineerRepository getRepository() {
        return engineerRepository;
    }
}
