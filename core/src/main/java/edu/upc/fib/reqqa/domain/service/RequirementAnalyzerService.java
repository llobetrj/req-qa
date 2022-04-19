package edu.upc.fib.reqqa.domain.service;

import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.service.api.IRequirementExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

@Service
public class RequirementAnalyzerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementAnalyzerService.class);

    ServiceLoader<IRequirementExternalService> loader = ServiceLoader
            .load(IRequirementExternalService.class);

    public Iterator<IRequirementExternalService> providers(boolean refresh) {
        if (refresh) {
            loader.reload();
        }
        return loader.iterator();
    }

    public RequirementAnalyzerService(@Autowired FactoryExternalService factoryExternalService) {
        this.factoryExternalService = factoryExternalService;
    }


    private final FactoryExternalService factoryExternalService;


    public List<RequirementAnalysis> analyse(List<Requirement> requirementList) {

        List<RequirementAnalysis> requirementAnalysisList = new ArrayList<>();
        for (RequirementExternalService reqExternalService : factoryExternalService.getImplementationMap().values()) {
            LOGGER.info("class: {}", reqExternalService.toString());
            requirementAnalysisList.addAll(((IRequirementExternalService) reqExternalService).getRequirementAnalysis(requirementList));
        }

/*
        for (RequirementExternalService requirementExternalService : loader) {
            requirementAnalysisList.addAll(requirementExternalService.getRequirementAnalysis(requirementList));
        }*/
/*                loader.stream()
                .map(ServiceLoader.Provider::get)
                .map(req -> req.getRequirementAnalysis(requirementList))
                .collect(Collectors.toList());*/
        /*
                .forEachRemaining(provider -> {
            LOGGER.info("Retrieving services from provider :" + provider);
            List<RequirementAnalysis> requirementAnalysisList = provider.create().getRequirementAnalysis(requirementList);

            requirementAnalysisList.forEach(req -> {
                LOGGER.info("req {}",req.toString());
            });
        } );*/
        return requirementAnalysisList;
    }


}
