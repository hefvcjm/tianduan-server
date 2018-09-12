package com.tianduan.repository;

import com.tianduan.model.Model;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

@Component
public class Repository {

    private static Logger logger = Logger.getLogger(Repository.class);

    public static <S extends PagingAndSortingRepository<Model, Long>> S getRepositoryByModelName(String name) {
        String realName = Repository.class.getPackage().getName() + "." + name + "Repository";
        logger.info(realName);
        try {
            return (S) ContextLoader.getCurrentWebApplicationContext().getBean(Class.forName(realName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
