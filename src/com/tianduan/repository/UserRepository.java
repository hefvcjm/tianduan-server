package com.tianduan.repository;

import com.tianduan.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByPhone(String phone);

    User findByUsername(String username);

    User findByObjectId(String objectId);
}
