package ru.rastorguev.springlesson1.ioc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

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


            //TODO доделать, не смог найти как достать аннотации методов или класса?

            // Так же создать BeanFactoryPostProcessor, который будет писать в лог WARN если есть бин,
            // который имеет Scope=Prototype и аннотацию @CacheResult. Проверить работоспособность данного BeanFactoryPostProcessor.

        }

    }

}
