package ru.rastorguev.springlesson1.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.aop.model.ExternalInfo;

import static ru.rastorguev.springlesson1.aop.utils.Util.returnRequiredType;

@Slf4j
@Component
@Aspect
public class CheckRequestAnnotationAspect {

    @Value("${id-not-process}")
    private Integer idNotProcess;

    @Around("annotationCheckRequest() && externalInfoExist(info)")
    public Object annotationCheckRequestAdvice(ProceedingJoinPoint jp, ExternalInfo info) {
        try {
            if (!info.getId().equals(idNotProcess)) {
                return jp.proceed();
            } else {
                return returnRequiredType(((MethodSignature) jp.getSignature()).getReturnType());
            }
        } catch (Throwable e) {
            log.error("EXCEPTION:", e);
            return returnRequiredType(((MethodSignature) jp.getSignature()).getReturnType());
        }
    }

    @Pointcut("@annotation(ru.rastorguev.springlesson1.aop.aspect.anotations.CheckRequest)")
    public void annotationCheckRequest() {}

    @Pointcut("execution(* *(.., ru.rastorguev.springlesson1.aop.model.ExternalInfo, ..)) && args(info, ..)")
    public void externalInfoExist(ExternalInfo info) {}

}
