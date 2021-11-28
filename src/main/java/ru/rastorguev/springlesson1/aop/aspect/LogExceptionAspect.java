package ru.rastorguev.springlesson1.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import static ru.rastorguev.springlesson1.aop.utils.Util.returnRequiredType;

@Slf4j
@Component
@Aspect
public class LogExceptionAspect {

//    @AfterThrowing(value = "execution(* *(..))", throwing = "e")
//    @AfterThrowing(value = "within(ru.rastorguev..*)", throwing = "e")
//    public void throwAdvice(JoinPoint jp, Exception e) {
//        log.error("METHOD {} FINISHED WORK WITH EXCEPTION:", jp.getSignature().toShortString(), e);
//    }

    @Around("within(ru.rastorguev..*)")
    public Object throwAdvice(ProceedingJoinPoint jp) {
        try {
            return jp.proceed();
        } catch (Throwable e) {
            log.error("METHOD {} FINISHED WORK WITH EXCEPTION :", jp.getSignature().toShortString(), e);
            return returnRequiredType(((MethodSignature) jp.getSignature()).getReturnType());
        }
    }

}
