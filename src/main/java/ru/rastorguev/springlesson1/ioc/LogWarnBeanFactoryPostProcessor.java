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
    public void postProcessBeanFactory(ConfigurableListableBeanFactory BeanFactory) throws BeansException {

        for (String beanDefinitionName : BeanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = BeanFactory.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getScope() == BeanDefinition.SCOPE_PROTOTYPE) {
                log.warn("beanDefinitionName: {}, scope: {}", beanDefinitionName, beanDefinition.getScope());
            }

        }

    }

}
