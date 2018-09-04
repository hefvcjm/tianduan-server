package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.Client;
import com.tianduan.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends BaseService<Client> {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientRepository getRepository() {
        return clientRepository;
    }
}
