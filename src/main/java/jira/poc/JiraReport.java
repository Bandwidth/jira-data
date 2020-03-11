package jira.poc;

public class JiraReport {





    public static void main(String... args){
        System.out.println("wtf");

        //String sprintName = "Sprint = \"2019 - Sprint 23\"";
        String sprintName = "Sprint = \"2020-Sprint 03 Boo (CT)\"";

        ParseData parseData = new ParseData();
        parseData.generateSprintReport(sprintName);

    }

}
