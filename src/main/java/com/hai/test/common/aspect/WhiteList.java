package com.hai.test.common.aspect;

import java.lang.annotation.*;

/**
 * 白名单接口注解
 * @author ZXH
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WhiteList {
}
