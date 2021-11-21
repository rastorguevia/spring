package ru.rastorguev.springlesson1.ioc;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.ioc.annotations.CacheResult;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CacheResultBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Map<String, Object>> cache = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        boolean cacheResultPresented = false;

        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CacheResult.class)) {
                log.info("Found method with annotation @CacheResult Method name : {}", method.getName());
                cacheResultPresented = true;
            }
        }

        if (!cacheResultPresented) return bean;

        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {

            //if (invocation.getMethod().isAnnotationPresent(CacheResult.class)) {

                String methodName = invocation.getMethod().getName();
                String methodArguments = Arrays.toString(invocation.getArguments());

                //проверка вложенной мапы
                Map<String, Object> nestedMap = cache.get(methodName);
                if (nestedMap == null) {

                    Object proceed = invocation.proceed();

                    cache.put(methodName, new HashMap<>() {{
                        put(methodArguments, proceed);
                    }});

                    log.info("No cached values found. New value cached.");
                    log.info("methodName {}, methodArguments {}, proceed {}", methodName, methodArguments, proceed);
                    return proceed;
                }

                //проверка значений вложенной мапы
                Object methodCachedResult = nestedMap.get(methodArguments);

                if (methodCachedResult == null) {

                    Object proceed = invocation.proceed();

                    nestedMap.put(methodArguments, proceed);

                    log.info("The method name was found, but the set of arguments is different. Added a new value.");
                    return proceed;
                }

                log.info("Returned cached value.");
                return methodCachedResult;

            //} else return invocation.proceed();



        });
        return proxyFactory.getProxy();

    }

}
