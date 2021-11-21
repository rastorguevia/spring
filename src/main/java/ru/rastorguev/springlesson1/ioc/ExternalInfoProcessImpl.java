package ru.rastorguev.springlesson1.ioc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.ioc.interfaces.ExternaInfoProcess;
import ru.rastorguev.springlesson1.ioc.pojo.ExternalInfo;

@Slf4j
@Component
@Lazy
public class ExternalInfoProcessImpl implements ExternaInfoProcess {

    @Value("id-not-process")
    private Integer idNotProcess;

    @Override
    public boolean run(ExternalInfo externalInfo) {
        if (externalInfo.getId() == idNotProcess) {
            log.info("id == idNotProcess = 3 return false");
            return false;
        } else {
            log.info("id != idNotProcess = 3 return true");
            return true;
        }
    }

}
