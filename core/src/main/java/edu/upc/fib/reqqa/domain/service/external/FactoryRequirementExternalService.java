package edu.upc.fib.reqqa.domain.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class FactoryRequirementExternalService {

    private final Map<String, RequirementExternalService> implementationMap = new HashMap<>();

    private final ApplicationContext context;

    @Autowired
    public FactoryRequirementExternalService(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void initialize() {
        populateDataMapperMap(context.getBeansOfType(RequirementExternalService.class).values().iterator());
    }

    private void populateDataMapperMap(final Iterator<RequirementExternalService> classIterator) {
        while (classIterator.hasNext()) {
            RequirementExternalService externalService = classIterator.next();
            implementationMap.put(externalService.getClass().getName(), externalService);
        }
    }

    public Map<String, RequirementExternalService> getImplementationMap() {
        return implementationMap;
    }

}
