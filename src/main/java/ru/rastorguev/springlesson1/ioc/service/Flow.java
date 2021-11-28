package ru.rastorguev.springlesson1.ioc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.ioc.model.ExternalInfo;
import ru.rastorguev.springlesson1.ioc.service.interfaces.ExternalInfoProcess;
import ru.rastorguev.springlesson1.ioc.service.interfaces.ExternalService;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // для проверки LogWarnBeanFactoryPostProcessor
public class Flow {

    private ExternalService externalService;
    private ExternalInfoProcess externalInfoProcess;

    public Flow(ExternalService externalService, @Lazy ExternalInfoProcess externalInfoProcess) {
        this.externalService = externalService;
        this.externalInfoProcess = externalInfoProcess;
    }

    public void run(Integer id) {
        ExternalInfo externalInfo = externalService.getExternalInfo(id);
        if (externalInfo != null) {
            externalInfoProcess.run(externalInfo);
        } else log.info("Flow.run({}) externalInfo == null, ExternalInfoProcess.getClass() : {}", id, externalInfoProcess.getClass());
    }

}
