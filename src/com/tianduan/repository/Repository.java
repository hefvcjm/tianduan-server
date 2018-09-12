package com.tianduan.repository;

import com.tianduan.model.Model;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public class Repository<T extends Model> {

    @Autowired
    private Repository<T> repository;

    @Autowired
    private <S extends PagingAndSortingRepository<T, Long>> S modelRepository;

    private static Logger logger = Logger.getLogger(Repository.class);

    public final Repository<T> getRepository() {
        return repository;
    }

    public final <S extends PagingAndSortingRepository<T, Long>> S getRepositoryByModelName() {
        return (S) modelRepository;
    }
}
