package com.hai.test.common;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 13352
 * @description 切面实现类
 * @date 2021.07.20 22:53
 */
@Aspect
@Component
public class SectionImpl {

    /*@Pointcut("execution(* com.hai.test.controller.TestController.mytestController(..))")
    private void pointcut() {

    }

    @SneakyThrows
    @Around("pointcut()")
    public String checkSomething(ProceedingJoinPoint joinPoint) {
        System.out.println("before around!");
        joinPoint.proceed();
        System.out.println("after around!");
        return "around end!";
    }*/

    @Pointcut("@annotation(com.hai.test.common.Section)")
    private void pointcut() {

    }

    @SneakyThrows
    @Around("pointcut()&&@annotation(section)")
    public String checkSomething(ProceedingJoinPoint joinPoint,Section section) {
        if ("boy".equals(section.gender()) && section.age() > 18) {
            return (String) joinPoint.proceed();
        } else {
            return "I am girl!";
        }
    }
}
