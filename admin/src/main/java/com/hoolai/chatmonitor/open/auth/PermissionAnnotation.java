package com.hoolai.chatmonitor.open.auth;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionAnnotation {

    PermissionType value() default PermissionType.LOGINED;
}
