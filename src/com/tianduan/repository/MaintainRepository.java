package com.tianduan.repository;

import com.tianduan.model.Engineer;
import com.tianduan.model.Maintain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MaintainRepository extends PagingAndSortingRepository<Maintain, Long> {
    Maintain findByObjectId(String objectId);

    @Query(value = "select * from Maintain maintain ", nativeQuery = true)
    Maintain[] findByEngineer(@Param("engineer") Engineer engineer);
}
