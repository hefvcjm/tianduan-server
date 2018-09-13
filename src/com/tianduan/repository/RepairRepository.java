package com.tianduan.repository;

import com.tianduan.model.Repair;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RepairRepository extends PagingAndSortingRepository<Repair, Long> {
    Repair findByTicket(String ticket);

    Repair findByObjectId(String objectId);
}
