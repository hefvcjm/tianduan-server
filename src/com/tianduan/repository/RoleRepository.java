package com.tianduan.repository;

import com.tianduan.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByObjectId(String objectId);

    Role findByName(String name);
}
