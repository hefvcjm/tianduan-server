package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.model.Client;
import com.tianduan.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/client")
public class ClientAction extends BaseAction<Client> {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientRepository getRepository() {
        return clientRepository;
    }


}
