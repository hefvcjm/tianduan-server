package com.tianduan.service;

import com.tianduan.base.BaseService;
import com.tianduan.model.User;
import com.tianduan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

}
