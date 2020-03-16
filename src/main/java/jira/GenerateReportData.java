package jira;

import jira.service.JiraDataTransformer;

public class GenerateReportData {



    public static void main(String... args){

        String sprintName = "2019 - Sprint 23";
        sprintName = "2020-Sprint 03 Boo (CT)";
        sprintName = "2020-Sprint 04 Charizard (HT)";
        sprintName = "2020-Sprint 05 Cpt Falcon (CT)";
        sprintName = "2020-Sprint 06 Byleth (HT)";
        sprintName = "2020-Sprint 07 Donkey Kong(CT)";
        sprintName = "2020-Sprint 08 Eevee (CT)";
        sprintName = "2020 - Sprint 09 Earthworm Jim"; // 15 points
        sprintName = "2020 - Sprint 10 Funky Kong CT";
        sprintName = "2020.11 Fox (HT)";
        sprintName = "2020.12 Goomba (CT)";


        JiraDataTransformer dataTransformer = new JiraDataTransformer();
        // dataTransformer.getJqlReport(jql);
        dataTransformer.getSprintReport(sprintName);


    }

}
