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

        String resultExpected = "#### Annotated requirement\n" +
                "This [is](#Vague \"Vague\") a requirement\n" +
                "\n" +
                "#### Ambiguities found\n" +
                "| **Text** | **Context** | **Ambiguities** |\n" +
                "| --- | --- | --- |\n" +
                "|**is**|... is a...|Vague|\n" +
                "\n" +
                "* * *\n" +
                "#### Ambiguities descriptions\n" +
                "| **Ambiguity** | **Description** |\n" +
                "| --- | --- |\n" +
                "|**Vague**|desc|\n";


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

    @Test
    public void TestOrderDisplay() {
        String id = "1";
        String reqTest = "This is all the requirement";
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

        requirementAnalysisDetail = new RequirementAnalysisDetail();
        requirementAnalysisDetail.setText("all");
        requirementAnalysisDetail.setTitle("Dangerous Plural");
        requirementAnalysisDetail.setCategory(Categories.AMBIGUITY);
        requirementAnalysisDetail.setDescription("desc");
        requirementAnalysisDetail.setIndex_start(8);
        requirementAnalysisDetail.setIndex_end(11);
        requirementAnalysisDetail.setLanguage_construct("Dangerous Plural");
        requirementAnalysisDetailList.add(requirementAnalysisDetail);

        requirementAnalysis.setRequirementAnalysisDetailList(requirementAnalysisDetailList);
        requirementAnalysis.setId(id);
        List<RequirementAnalysis> requirementAnalysisList = new ArrayList<>(Collections.emptyList());
        requirementAnalysisList.add(requirementAnalysis);
        String result = formatDisplayAnalysisResult.getDisplayAnalysisResults("1",requirement, requirementAnalysisList);

        String resultExpected = "#### Annotated requirement\n" +
                "This [is](#Vague \"Vague\") [all](#DangerousPlural \"Dangerous Plural\") the requirement\n" +
                "\n" +
                "#### Ambiguities found\n" +
                "| **Text** | **Context** | **Ambiguities** |\n" +
                "| --- | --- | --- |\n" +
                "|**is**|... is all the...|Vague|\n" +
                "|**all**|... is all the...|Dangerous Plural|\n" +
                "\n" +
                "* * *\n" +
                "#### Ambiguities descriptions\n" +
                "| **Ambiguity** | **Description** |\n" +
                "| --- | --- |\n" +
                "|**Vague**|desc|\n" +
                "|**Dangerous Plural**|desc|\n";


        assertThat(result).isEqualTo(resultExpected);
    }

}