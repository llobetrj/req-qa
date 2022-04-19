package edu.upc.fib.reqqa.domain.service.spi;

import edu.upc.fib.reqqa.domain.service.api.IRequirementExternalService;

public interface RequirementExternalServiceProvider {
    IRequirementExternalService create();
}
