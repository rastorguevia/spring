package ru.rastorguev.springlesson1.aop.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.aop.beanPostProcessors.methodInterceptor.CacheResultMethodInterceptor;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Aspect
public class CacheResultAnnotationAspect {

    private static final Map<String, Map<MethodArgs, Object>> CACHE = new ConcurrentHashMap<>();


    public Object cacheResultAdvice



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
}
