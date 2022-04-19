package edu.upc.fib.reqqa.domain.service.api;

import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;

import java.util.List;

public interface IRequirementExternalService {

    List<RequirementAnalysis> getRequirementAnalysis(List<Requirement> requirementList);

}
