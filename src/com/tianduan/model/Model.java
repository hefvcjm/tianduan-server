package com.tianduan.model;

import com.tianduan.base.annotation.ToStringIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@MappedSuperclass
public class Model implements Serializable {

    private static final long SERIALVERSIONUID = 465432156461223165L;

    /**
     * 主键名称
     */
    public static final String COL_PRIMARYKEY = "id";
    /**
     *
     */
    public static final String COL_OBJECTID = "objectId";

    @Id
    @Column(name = COL_PRIMARYKEY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(name = COL_OBJECTID, unique = true, nullable = false)
    protected String objectId = UUID.randomUUID().toString().replace("-", "");

    public Model() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object model) {
        if (model instanceof Model) {
            Class<? extends Model> clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Column.class) != null) {
                    String fieldName = field.getName();
                    String funName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    try {
                        Method method = clazz.getMethod(funName);
                        Object thisGet = method.invoke(this);
                        Object modelGet = method.invoke(model);
                        if (!thisGet.equals(modelGet)) {
                            return false;
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        return false;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Map<String, Object> str = new HashMap<>();
        Class<? extends Model> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Column.class) != null && field.getAnnotation(ToStringIgnore.class) == null) {
                String fieldName = field.getName();
                String funName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Method method = clazz.getMethod(funName);
                    Object get = method.invoke(this);
                    if (get == null) {
                        get = "null";
                    }
                    str.put(fieldName, get);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return str.toString();
    }
}
