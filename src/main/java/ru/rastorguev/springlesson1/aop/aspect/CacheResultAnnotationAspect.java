package ru.rastorguev.springlesson1.aop.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.rastorguev.springlesson1.aop.utils.Util.returnRequiredType;

@Slf4j
@Component
@Aspect
public class CacheResultAnnotationAspect {

    private static final Map<String, Map<MethodArgs, Object>> CACHE = new ConcurrentHashMap<>();

    @Around("annotationCacheResult()")
    public Object cacheResultAdvice (ProceedingJoinPoint jp) {

        if (jp.getSignature().toShortString() == null || jp.getArgs() == null)
            return returnRequiredType(((MethodSignature) jp.getSignature()).getReturnType());

        String methodName = jp.getSignature().toShortString();
        MethodArgs args = getMethodArgs(jp.getArgs());

        Map<MethodArgs, Object> methodArgsObjectMap = CACHE.get(methodName);
        if (methodArgsObjectMap != null) {
            log.info("Method: {} has cache. Cache: {}", methodName, methodArgsObjectMap);
            log.info("Check cache result by method with args: {}({})", methodName, args);
            Object result = methodArgsObjectMap.get(args);
            if (result != null) {
                log.info("Return result from cache: method: {}({}), result: {}", methodName, args, result);
                return result;
            }else {
                log.info("Call original method and record result into cache");
                try {
                    result = jp.proceed();
                } catch (Throwable e) {
                    log.error("EXCEPTION:", e);
                    return returnRequiredType(((MethodSignature) jp.getSignature()).getReturnType());
                }
                methodArgsObjectMap.put(args, result);
                return result;
            }
        } else {
            log.info("Method: {} not cache.", methodName);
            Object result;
            try {
                result = jp.proceed();
            } catch (Throwable e) {
                log.error("EXCEPTION:", e);
                return returnRequiredType(((MethodSignature) jp.getSignature()).getReturnType());
            }
            methodArgsObjectMap = new ConcurrentHashMap<>();
            methodArgsObjectMap.put(
                    args,
                    result
            );
            CACHE.put(methodName, methodArgsObjectMap);
            return result;
        }
    }

    @Pointcut("@annotation(ru.rastorguev.springlesson1.aop.aspect.anotations.CacheResult)")
    public void annotationCacheResult() {}


    private static class MethodArgs {

        private final LinkedList<Object> args;

        public MethodArgs(LinkedList<Object> args) {
            this.args = args;
        }

        public LinkedList<Object> getArgs() {
            return args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodArgs that = (MethodArgs) o;

            return args.equals(that.args);
        }

        @Override
        public int hashCode() {
            return args.hashCode();
        }

        @Override
        public String toString() {
            return "MethodArgs{" +
                    "args=" + args +
                    '}';
        }
    }

    private MethodArgs getMethodArgs(Object[] args) {
        LinkedList<Object> linkedArgs = new LinkedList<>();
        Collections.addAll(linkedArgs, args);
        return new MethodArgs(linkedArgs);
    }
}
