package ru.rastorguev.springlesson1.aop.beanPostProcessors;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.aop.beanPostProcessors.annotations.CacheResult;
import ru.rastorguev.springlesson1.aop.beanPostProcessors.methodInterceptor.CacheResultMethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class CustomAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final Map<Class<? extends Annotation>, MethodInterceptor> methodInterceptorMap = Map.of(
            CacheResult.class, new CacheResultMethodInterceptor()
    );

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        for (Method declaredMethod : declaredMethods) {
            for (Map.Entry<Class<? extends Annotation>, MethodInterceptor> proxyEntry : methodInterceptorMap.entrySet()) {
                if (declaredMethod.isAnnotationPresent(proxyEntry.getKey())) {
                    proxyFactory.addAdvice(proxyEntry.getValue());
                }
            }
        }

        if (proxyFactory.getAdvisorCount() > 0) {
            return proxyFactory.getProxy();
        }

        return bean;
    }
}