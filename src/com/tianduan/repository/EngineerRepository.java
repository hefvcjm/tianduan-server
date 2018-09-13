package com.tianduan.repository;

import com.tianduan.model.Engineer;
import com.tianduan.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EngineerRepository extends PagingAndSortingRepository<Engineer, Long> {

    Engineer findByUser(User user);

    Engineer findByCode(String code);
}
