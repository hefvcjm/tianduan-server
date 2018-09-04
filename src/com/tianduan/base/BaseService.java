package com.tianduan.base;

import com.tianduan.model.Model;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class BaseService<T extends Model> {

    public abstract <S extends PagingAndSortingRepository<T, Long>> S getRepository();

}
