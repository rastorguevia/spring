package ru.rastorguev.springlesson1.ioc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.ioc.annotations.CacheResult;

import java.util.Arrays;

@Component
public class CacheResultBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object key = Arrays.toString(invocation.getArguments()) + invocation.getMethod().getName();
                Object proceededObj = invocation.proceed();
                if (invocation.getMethod().isAnnotationPresent(CacheResult.class)) {
                    return cache(key, proceededObj);
                }
                return proceededObj;
            }
        });
        return proxyFactory.getProxy();
    }

    //тк данная аннотация должная работать и для других методов - ключ = поданые значения + название метода.
    //как я понял можно сделать собственную реализацию на мапах, но зачем когда есть реализация спринга?
    @Cacheable(cacheNames = "cacheResult", key = "#key")
    public Object cache(Object key, Object proceededObj) {
        return proceededObj;
    }
}
