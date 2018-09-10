package com.tianduan.chat.message;

import com.tianduan.model.User;
import com.tianduan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMessage {

    @Autowired
    private static UserRepository repository;

    public static boolean isUserExist(String objectId) {
        User user = repository.findByObjectId(objectId);
        if (user != null) {
            return true;
        }
        return false;
    }

}
