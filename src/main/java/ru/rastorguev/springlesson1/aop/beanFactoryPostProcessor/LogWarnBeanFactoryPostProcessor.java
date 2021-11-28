package ru.rastorguev.springlesson1.aop.beanFactoryPostProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.aop.beanPostProcessors.annotations.CacheResult;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LogWarnBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.isPrototype()) {
                log.warn("beanDefinitionName: {}, scope: {}", beanDefinitionName, beanDefinition.getScope());
            }

            String beanClassName = beanDefinition.getBeanClassName();

            if (beanClassName == null) continue;

            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                log.error("postProcessBeanFactory error :  ", e);
            }

            if (beanClass == null) continue;

            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(CacheResult.class)) {
                    log.warn("Annotation @CacheResult found. Class: {}, Method: {}", beanDefinition.getBeanClassName(), method.getName());
                }
            }
        }

    }

}
