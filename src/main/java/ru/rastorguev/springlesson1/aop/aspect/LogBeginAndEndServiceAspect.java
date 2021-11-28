package ru.rastorguev.springlesson1.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogBeginAndEndServiceAspect {

    @Before("allMethodInService()")
    public void logBeginServiceMethodAdvice(JoinPoint jp) {
        log.info("METHOD {} START!", jp.getSignature().toShortString());
    }

    @After("allMethodInService()")
    public void logEndServiceMethodAdvice(JoinPoint jp) {
        log.info("METHOD {} STOP!", jp.getSignature().toShortString());
    }

    @Pointcut("within(ru.rastorguev.springlesson1.aop.service..*)")
    public void allMethodInService() {}

}
