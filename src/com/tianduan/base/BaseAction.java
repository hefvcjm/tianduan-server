package com.tianduan.base;

import com.tianduan.base.enums.RolesEnum;
import com.tianduan.base.filter.LoginFilter;
import com.tianduan.model.Model;
import com.tianduan.model.Role;
import com.tianduan.repository.RoleRepository;
import com.tianduan.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseAction<T extends Model> {

    protected static Logger logger = Logger.getLogger(BaseAction.class);

    @Autowired
    protected HttpServletRequest request;

    public abstract BaseService<T> getService();

    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody T model) {
        Object obj = checkField(model);
        if (obj == null) {
            model.setObjectId(UUID.randomUUID().toString().replace("-", ""));
            System.out.println(model.getObjectId());
            getService().getRepository().save(model);
            return new JsonResponse(model);
        } else {
            return new JsonResponse(new FailDetail(obj), Message.ExecuteFailSelfDetail);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResponse update(@RequestBody T model) {
        T t = getService().getRepository().findOne(model.getId());
        if (t == null) {
            return new JsonResponse(null, "更新对象不存在");
        } else {
            getService().getRepository().save(model);
            return new JsonResponse(model);
        }
    }

    protected Object checkField(T model) {
        Map<String, Object> checkResult = new HashMap<>();
        Class<? extends Model> clazz = model.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Column.class);
            if (annotation != null) {
                if (((Column) annotation).unique() || !((Column) annotation).nullable()) {
                    String fieldName = field.getName();
                    Class<?> fieldType = field.getType();
                    String funName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    try {
                        Method method = clazz.getMethod(funName);
                        Object get = method.invoke(model);
                        if (get == null) {
                            checkResult.put(fieldName, "不能为空");
                        } else {
                            if (((Column) annotation).unique()) {
                                Method find = getService().getRepository().getClass().getMethod("findBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), fieldType);
                                T obj = (T) find.invoke(getService().getRepository(), new Object[]{get});
                                if (obj != null) {
                                    checkResult.put(fieldName, "已存在");
                                }
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (checkResult.size() != 0) {
            System.out.println(checkResult);
            return checkResult;
        }
        return null;
    }
}
