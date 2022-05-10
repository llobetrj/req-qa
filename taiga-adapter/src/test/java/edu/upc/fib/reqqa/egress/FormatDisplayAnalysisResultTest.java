package edu.upc.fib.reqqa.egress;

import edu.upc.fib.reqqa.domain.model.Categories;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysisDetail;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FormatDisplayAnalysisResultTest implements WithAssertions {

    @InjectMocks
    private FormatDisplayAnalysisResult formatDisplayAnalysisResult;

    @Test
    public void TestDisplay() {
        String id = "1";
        String reqTest = "This is a requirement";
        Requirement requirement = new Requirement(id,reqTest);
        RequirementAnalysis requirementAnalysis = new RequirementAnalysis();

        RequirementAnalysisDetail requirementAnalysisDetail = new RequirementAnalysisDetail();
        requirementAnalysisDetail.setText("is");
        requirementAnalysisDetail.setTitle("Vague");
        requirementAnalysisDetail.setCategory(Categories.AMBIGUITY);
        requirementAnalysisDetail.setDescription("desc");
        requirementAnalysisDetail.setIndex_start(5);
        requirementAnalysisDetail.setIndex_end(7);
        requirementAnalysisDetail.setLanguage_construct("Vague language");
        List<RequirementAnalysisDetail> requirementAnalysisDetailList = new ArrayList<>(Collections.emptyList());
        requirementAnalysisDetailList.add(requirementAnalysisDetail);
        requirementAnalysis.setRequirementAnalysisDetailList(requirementAnalysisDetailList);
        requirementAnalysis.setId(id);
        List<RequirementAnalysis> requirementAnalysisList = new ArrayList<>(Collections.emptyList());
        requirementAnalysisList.add(requirementAnalysis);
        String result = formatDisplayAnalysisResult.getDisplayAnalysisResults("1",requirement, requirementAnalysisList);

        String resultExpected = "| **Ambiguity** | **Description** |\n" +
                "| --- | --- |\n" +
                "|**Vague**|desc|\n" +
                "\n" +
                "| **Text** | **Context** | **Ambiguities** |\n" +
                "| --- | --- | --- |\n" +
                "|**is**|... is a...|Vague|\n" +
                "\n" +
                "Requirement: This [is](#Vague \"Vague\") a requirement\n" +
                "## Vague\n" +
                "### Vague language\n" +
                "* desc\n" +
                "* **is**";


        assertThat(result).isEqualTo(resultExpected);
    }

    @Test
    public void TestRequirementTextDisplay() {
        String id = "1";
        String reqTest = "This is a requirement";
        Requirement requirement = new Requirement(id,reqTest);
        RequirementAnalysis requirementAnalysis = new RequirementAnalysis();

        RequirementAnalysisDetail requirementAnalysisDetail = new RequirementAnalysisDetail();
        requirementAnalysisDetail.setText("is");
        requirementAnalysisDetail.setTitle("Vague");
        requirementAnalysisDetail.setCategory(Categories.AMBIGUITY);
        requirementAnalysisDetail.setDescription("desc");
        requirementAnalysisDetail.setIndex_start(5);
        requirementAnalysisDetail.setIndex_end(7);
        requirementAnalysisDetail.setLanguage_construct("Vague language");
        List<RequirementAnalysisDetail> requirementAnalysisDetailList = new ArrayList<>(Collections.emptyList());
        requirementAnalysisDetailList.add(requirementAnalysisDetail);
        requirementAnalysis.setRequirementAnalysisDetailList(requirementAnalysisDetailList);
        requirementAnalysis.setId(id);
        List<RequirementAnalysis> requirementAnalysisList = new ArrayList<>(Collections.emptyList());
        requirementAnalysisList.add(requirementAnalysis);

        String result = formatDisplayAnalysisResult.getRequirementTextDisplay(requirement, requirementAnalysisList);
        String resultExpected = "This [is](#Vague \"Vague\") a requirement";
        assertThat(result).isEqualTo(resultExpected);
    }


}