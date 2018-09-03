package com.tianduan.repository;

import com.tianduan.model.Engineer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EngineerRepository extends PagingAndSortingRepository<Engineer, Long> {
}
