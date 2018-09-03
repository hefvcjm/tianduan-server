package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.model.Maintain;
import com.tianduan.repository.MaintainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/maintain")
public class MaintainAction extends BaseAction<Maintain> {

    @Autowired
    MaintainRepository maintainRepository;

    @Override
    public MaintainRepository getRepository() {
        return maintainRepository;
    }
}
