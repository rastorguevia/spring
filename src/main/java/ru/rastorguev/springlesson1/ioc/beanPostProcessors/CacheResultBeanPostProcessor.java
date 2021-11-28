package ru.rastorguev.springlesson1.ioc.beanPostProcessors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.rastorguev.springlesson1.ioc.beanPostProcessors.annotations.CacheResult;
import ru.rastorguev.springlesson1.ioc.beanPostProcessors.methodInterceptor.CacheResultMethodInterceptor;

import java.lang.reflect.Method;

@Slf4j
//@Component
@Deprecated
public class CacheResultBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Method[] methods = bean.getClass().getDeclaredMethods();
        log.info("postProcessAfterInitialization: {}", beanName);
        for (Method method : methods) {
            if (method.isAnnotationPresent(CacheResult.class)) {
                log.info("Bean {} is proxy. Has annotation @CacheResult on method: {}", beanName, method.getName());
                ProxyFactory proxyFactory = new ProxyFactory(bean);
                proxyFactory.addAdvice(new CacheResultMethodInterceptor());
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }

}
