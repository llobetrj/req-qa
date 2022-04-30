package edu.upc.fib.reqqa.egress;

import edu.upc.fib.reqqa.domain.model.Categories;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysisDetail;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FormatDisplayAnalysisResult {
    public String getDisplayAnalysisResults(String id, Requirement requirement, List<RequirementAnalysis> requirementAnalysisList) {
        // check if the requirement is perfect defined
        if (requirementAnalysisList.size()>0) {
            int detailsCount = requirementAnalysisList.get(0).getRequirementAnalysisDetailList().size();
            if (detailsCount == 0) {
                return "No defects found!";
            }
        }
        HashMap<String,String> ambiWords = new HashMap<>();
        HashMap<String,String> ambiWordsContext = new HashMap<>();

        List<String> values = new ArrayList<>();
        // get all ambiguities
        HashMap<String,String> ambiguities = new HashMap<>();
        requirementAnalysisList.forEach(requirementAnalysis -> {
            requirementAnalysis.getRequirementAnalysisDetailList().forEach(analysis -> {
                if (analysis.getCategory().equals(Categories.AMBIGUITY)) {
                    ambiguities.put(analysis.getTitle(), analysis.getDescription());
                    ambiWords.merge(analysis.getText(), analysis.getTitle(), (a, b) -> a + "," + b);
                    String text = requirement.getText();
                    int start = analysis.getIndex_start();
                    if (start > 10) start -= 10;
                    else start = 0;
                    int end = analysis.getIndex_end();
                    int reallyEnd = text.length();
                    if ((end + 10) < text.length()) end += 10;
                    else end = text.length();

                    String context = "..."+requirement.getText().substring(start,end)+"...";
                    //ambiWordsContext.merge(analysis.getText(), context,(a, b) -> a + "," + b);
                    ambiWordsContext.putIfAbsent(analysis.getText(), context);
                }
            });
        });

        // Create table of all ambiguities
        values.add("| **Ambiguity** | **Description** |");
        values.add("| --- | --- |");
        ambiguities.forEach((key,value) -> values.add("|**"+key+"**|"+value+"|"));
        values.add("");

        // Create table of texts
        values.add("| **Text** | **Context** | **Ambiguities** |");
        values.add("| --- | --- | --- |");
        ambiWords.forEach((key,value) -> values.add("|**"+key+"**|"+ambiWordsContext.get(key)+"|"+value+"|"));
        values.add("");


        requirementAnalysisList.forEach(elem -> {
            // assume all are from the same id
            if (id.equals(elem.getId())) {
                List<RequirementAnalysisDetail> reqDetail = elem.getRequirementAnalysisDetailList();
                reqDetail.forEach(detail -> {
                    values.add("Requirement: "+ requirement.getText());
                    values.add("## "+detail.getTitle());
                    values.add("### "+detail.getLanguage_construct());
                    values.add("* "+detail.getDescription());
                    values.add("* **"+detail.getText()+"**");

                    //values.add(detail.toString());
                });

            }
        });
        String value = StringUtils.collectionToDelimitedString(values,"\n");
        return value;
    }
}
