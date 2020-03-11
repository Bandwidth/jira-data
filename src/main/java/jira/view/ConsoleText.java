package jira.view;

import java.util.List;
import java.util.Map;

import jira.model.report.Assignee;
import jira.model.report.JiraInfo;
import jira.model.report.SprintInfo;
import jira.model.report.StatusHistory;
import jira.service.JiraDataUtil;

public class ConsoleText {

    private static final int OFFSET = 20;

    public static void printSprintInfo(SprintInfo sprintInfo){

        System.out.println("\n========================================================\n");

        System.out.printf("%" + OFFSET + "s\n", "Sprint Data");
        System.out.printf("%" + OFFSET + "s : %s\n\n", "Sprint Name",sprintInfo.getSprintName());
        printSprintAssigneeSecondsLoggedMap(sprintInfo.getAssigneeSecondsLoggedMap());

        System.out.println("\n========================================================\n\n\n");

        ConsoleText.printJiraInfoList(sprintInfo.getJiraInfoList());

    }

    public static void printSprintAssigneeSecondsLoggedMap(Map<String, Integer> assigneeSecondsLoggedMap){

        System.out.printf("%"+OFFSET+"s\n", "Dev Hours");
        System.out.printf("%20s - %s\n", "------------","------");


        for (Map.Entry<String,Integer> entry :assigneeSecondsLoggedMap.entrySet()){

            float hours = entry.getValue().floatValue() / (60*60);
            System.out.printf("%" + OFFSET + "s : %.2f\n", entry.getKey(), hours);
        }

    }




    public static void printJiraInfoList(List<JiraInfo> jiraInfoList){

        for(JiraInfo jiraInfo : jiraInfoList){
            printJiraInfo(jiraInfo);
        }
    }

    private static void printJiraInfo(JiraInfo jiraInfo){

        System.out.printf("%" + OFFSET + "s : %s\n", "Jira",        JiraDataUtil.JIRA_LINK + jiraInfo.getJiraKey());
        System.out.printf("%" + OFFSET + "s : %s\n", "Summary",     jiraInfo.getSummary());
        System.out.printf("%" + OFFSET + "s : %s\n", "Epic Link",   jiraInfo.getEpicLink());
        System.out.printf("%" + OFFSET + "s + %s\n", "-----------------","-----------------");

        System.out.printf("%" + OFFSET + "s : %s\n", "Implementer", jiraInfo.getCoder().getPerson().getAssigneeName());
        System.out.printf("%" + OFFSET + "s : %s\n", "Tester 1",    jiraInfo.getTester01().getPerson().getAssigneeName());
        System.out.printf("%" + OFFSET + "s : %s\n", "Tester 2",    jiraInfo.getTester02().getPerson().getAssigneeName());

        System.out.printf("%" + OFFSET + "s + %s\n", "-----------------","-----------------");
        System.out.printf("%" + OFFSET + "s : %.2f\n", "Points Estimate", jiraInfo.getPointsEstimate());
        System.out.printf("%" + OFFSET + "s : %.2f\n", "Points Actual",   jiraInfo.getPointsActual());
        System.out.printf("%" + OFFSET + "s + %s\n", "-----------------","-----------------");
        System.out.printf("%" + OFFSET + "s : %.2f\n", "Point Delta",    jiraInfo.getPointsEstimateDelta());
        System.out.printf("%" + OFFSET + "s : %s\n", "Point Quality",    jiraInfo.getPointsEstimateQuality());

        System.out.println();
        System.out.println("**********************************************************************\n");


        printAssigneeTimeLoggedMap(jiraInfo.getAssigneeTimeLoggedMap());
        printStatusHistoryList(jiraInfo.getStatusHistoryList());
    }

    private static void printAssigneeTimeLoggedMap(Map<String, Assignee> assigneeTimeLoggedMap){
        System.out.printf("%" + 40 + "s + %s\n", "-----------------","-----------------");
        for(String assigneeKey : assigneeTimeLoggedMap.keySet()){
            System.out.printf("%40s : %s\n", "Logger", assigneeKey);
            System.out.printf("%40s : %.2f Hours\n", "Time",    ((float)assigneeTimeLoggedMap.get(assigneeKey).getSecondsLogged()) / (60*60));
            System.out.printf("%40s + %s\n", "-----------------","-----------------");
        }

    }

    private static void printStatusHistoryList(List<StatusHistory> statusHistoryList){

        int count = 1;
        for(StatusHistory statusHistory : statusHistoryList){

            String countString = Integer.toString(count++);
            if(statusHistory.getToStatus().equals("In Progress")){
                countString = "***  " + countString;
            }

            System.out.printf("%40s : %s to %s\n",countString, statusHistory.getFromStatus(), statusHistory.getToStatus());
            System.out.printf("%40s : %tI:%<tM %<Tp  %<tm/%<te/%<tY\n", "", statusHistory.getCreated());

        }
        System.out.printf("%" + 40 + "s + %s\n", "-----------------","-----------------");
        System.out.println("\n**********************************************************************\n");

    }



}
