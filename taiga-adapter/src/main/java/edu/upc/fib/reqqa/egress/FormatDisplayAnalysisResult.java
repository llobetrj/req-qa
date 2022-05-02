package edu.upc.fib.reqqa.egress;

import edu.upc.fib.reqqa.domain.model.Categories;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysisDetail;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class FormatDisplayAnalysisResult {

    public static final int AVERAGE_LENGTH_3_WORDS = 12;

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
                    ambiWords.merge(analysis.getText()+"#"+analysis.getIndex_start()+"#"+analysis.getIndex_end(), analysis.getTitle(), (a, b) -> a + "," + b);
                    String context = getContext(requirement, analysis);
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
        ambiWords.forEach((key,value) ->
        {
            String[] arrTrueKey = key.split("#");
            String trueKey = Arrays.stream(arrTrueKey).findFirst().orElse("");
            values.add("|**"+trueKey+"**|"+ambiWordsContext.get(trueKey)+"|"+value+"|");
        });
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

    private String getContext(Requirement requirement, RequirementAnalysisDetail analysis) {
        String text = requirement.getText();
        int start = analysis.getIndex_start();
        if (start > AVERAGE_LENGTH_3_WORDS) start -= AVERAGE_LENGTH_3_WORDS;
        else start = 0;
        int end = analysis.getIndex_end();
        if ((end + AVERAGE_LENGTH_3_WORDS) < text.length()) end += AVERAGE_LENGTH_3_WORDS;
        else end = text.length();
        String textToDisplay = requirement.getText().substring(start,end);
        String[] textDisplayLettered = StringUtils.tokenizeToStringArray(textToDisplay," ");
        String[] textAnalysisLettered = StringUtils.tokenizeToStringArray(analysis.getText()," ");

        int from = 0;
        int to = textDisplayLettered.length;
        // boundary check
        if ((textDisplayLettered.length>0) && (!textDisplayLettered[0].equals(textAnalysisLettered[0]))) {
            from = 1;
        }
        // boundary check
        if ((textDisplayLettered.length>1) && (!textDisplayLettered[textDisplayLettered.length-1].equals(textAnalysisLettered[textAnalysisLettered.length-1]))) {
            to -= 1;
        }
        String [] textLettered = Arrays.copyOfRange(textDisplayLettered, from, to);
        textToDisplay = Arrays.stream(textLettered).reduce("",
                (partial, element) -> partial + " " + element);
        String context = "..."+textToDisplay+"...";
        return context;
    }
}
