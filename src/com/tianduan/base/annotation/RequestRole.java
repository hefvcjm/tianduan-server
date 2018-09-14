package com.tianduan.base.annotation;

import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestRole {
    RolesEnum[] role() default RolesEnum.ORDINARY;
}
