package com.tianduan.repository;

import com.tianduan.model.Client;
import com.tianduan.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findByUser(User user);

    Client findByCode(String code);
}
