package ru.rastorguev.springlesson1.ioc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.ioc.interfaces.ExternalService;
import ru.rastorguev.springlesson1.ioc.pojo.ExternalInfo;

@Slf4j
@Component
public class Flow {

    private ExternalService externalService;
    private ExternalInfoProcessImpl externalInfoProcess;

    public Flow(ExternalService externalService, @Lazy ExternalInfoProcessImpl externalInfoProcess) {
        this.externalService = externalService;
        this.externalInfoProcess = externalInfoProcess;
    }

    public void run(Integer id) {
        ExternalInfo externalInfo = externalService.getExternalInfo(id);
        if (externalInfo != null) {
            externalInfoProcess.run(externalInfo);
        } else log.info("Flow.run({}) externalInfo == null, ExternalInfoProcess.getClass() : {}", id, externalInfo.getClass());
    }

}
