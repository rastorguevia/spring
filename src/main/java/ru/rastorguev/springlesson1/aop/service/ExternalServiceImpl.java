package ru.rastorguev.springlesson1.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.rastorguev.springlesson1.aop.aspect.anotations.CacheResult;
import ru.rastorguev.springlesson1.aop.model.ExternalInfo;
import ru.rastorguev.springlesson1.aop.service.interfaces.ExternalService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ExternalServiceImpl implements ExternalService {

    private Map<Integer, ExternalInfo> externalInfoMap;

    public ExternalServiceImpl() {
        this.externalInfoMap = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        putExternalInfoOnMap(List.of(
                new ExternalInfo(1, null),
                new ExternalInfo(2, "hasInfo"),
                new ExternalInfo(3, "info"),
                new ExternalInfo(4, "information")));
    }

    @Override
    @CacheResult
    public ExternalInfo getExternalInfo(Integer id) {
        try {
            ExternalInfo ei = externalInfoMap.get(id);
            if (ei == null) {
                throw new RuntimeException("Не найдено!");
            } else {
                log.info("Call getExternalInfo id: {}, ExternalInfo: {}", id, ei);
                return ei;
            }
        } catch (Exception e) {
            log.error("getExternalInfo Exception: ", e);
        }
        return null;
    }

    public void putExternalInfoOnMap(List<ExternalInfo> externalInfoList) {
        for (ExternalInfo ei : externalInfoList) {
            externalInfoMap.put(ei.getId(), ei);
        }
    }

    @PreDestroy
    public void destroy() {
        externalInfoMap.clear();
        log.info("HashMap is cleared. HashMap: {}", externalInfoMap.toString());
    }

}
