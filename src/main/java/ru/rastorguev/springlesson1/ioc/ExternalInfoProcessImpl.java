package ru.rastorguev.springlesson1.ioc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.ioc.interfaces.ExternalInfoProcess;
import ru.rastorguev.springlesson1.ioc.pojo.ExternalInfo;

@Slf4j
@Component
@Lazy
public class ExternalInfoProcessImpl implements ExternalInfoProcess {

    @Value("${id-not-process}")
    private Integer idNotProcess;

    @Override
    public boolean run(ExternalInfo externalInfo) {
        if (externalInfo.getId() == idNotProcess) {
            log.info("id = {} = idNotProcess return false", externalInfo.getId());
            return false;
        } else {
            log.info("id = {} return true", externalInfo.getId());
            return true;
        }
    }

}
