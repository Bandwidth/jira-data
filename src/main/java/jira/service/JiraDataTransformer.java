package jira.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import jira.data.JiraWebApiData;
import jira.model.api.History;
import jira.model.api.Item;
import jira.model.api.Jira;
import jira.model.api.Person;
import jira.model.api.Worklog;
import jira.model.report.Assignee;
import jira.model.report.JiraInfo;
import jira.model.report.SprintInfo;
import jira.model.report.StatusHistory;
import jira.view.ConsoleText;
import jira.view.EmailHtml;

public class JiraDataTransformer {

    private static final String QUOTE = "\"" ;
    private final String MISSING_EPIC = "EPIC MISSING/Please Link an Epic" ;

    private static Logger log = LogManager.getRootLogger();


    public void getSprintReport(String sprintName){

        SprintInfo sprintInfo = buildSprintReportData(sprintName);
        ConsoleText.printSprintInfo(sprintInfo);
        try {
            EmailHtml emailHtml = new EmailHtml();
            emailHtml.emailSprintInfo(sprintInfo);
        } catch (GeneralSecurityException e) {
            log.error("gmail security issue : ",e);
        } catch (IOException e) {
            log.error("gmail IO issue : ",e);
        } catch (MessagingException e) {
            log.error("gmail messaging issue : ",e);
        }
    }

    private SprintInfo buildSprintReportData(String sprintName){

        SprintInfo sprintInfo = new SprintInfo();
        sprintInfo.setSprintName(sprintName);

        String jql = "Sprint = " + QUOTE + sprintName + QUOTE;
        Jira[] jiras = getJqlJiras(jql);
        sprintInfo.setJiraInfoList(buildReportData(jiras));
        sprintInfo.setAssigneeSecondsLoggedMap(getAssigneeSprintSecondsLoggedMap(sprintInfo.getJiraInfoList()));

        return sprintInfo;
    }

    private Map getAssigneeSprintSecondsLoggedMap(List<JiraInfo> jiras){

        Map<String, Integer> assigneeSprintSecondsMap = new HashMap<>();
        for(JiraInfo jira : jiras){
            for(String assigneeKey : jira.getAssigneeTimeLoggedMap().keySet()){
                int seconds = jira.getAssigneeTimeLoggedMap().get(assigneeKey).getSecondsLogged();
                assigneeSprintSecondsMap.merge(assigneeKey, new Integer(seconds), Integer::sum);
            }
        }
        return assigneeSprintSecondsMap;
    }




    private List<JiraInfo> buildReportData(Jira[] jiras){

        List<JiraInfo> jiraInfoList = new ArrayList<>();

        for(Jira jira : jiras){

            JiraInfo jiraInfo = new JiraInfo();

            // Jira Description
            jiraInfo.setJiraKey(jira.getKey());
            jiraInfo.setSummary(jira.getFields().getSummary());
            String epicLink = jira.getFields().getCustomfield_10205();
            jiraInfo.setEpicLink(StringUtils.isNotEmpty(epicLink)? epicLink : MISSING_EPIC);

            // Jira Assignees
            if(jira.getFields().getAssignee() != null){
                jiraInfo.setCoder(mapPersonToAssignee(jira.getFields().getAssignee(), Assignee.AssigneeType.CODER));
            }
            if(jira.getFields().getCustomfield_11700() != null){
                jiraInfo.setTester01(mapPersonToAssignee(jira.getFields().getCustomfield_11700(), Assignee.AssigneeType.TESTER01));
            }
            if(jira.getFields().getCustomfield_12837() != null){
                jiraInfo.setTester02(mapPersonToAssignee(jira.getFields().getCustomfield_12837(), Assignee.AssigneeType.TESTER02));
            }

            // Jira Estimation/Time Data
            float pointsEstimate = jira.getFields().getCustomfield_10002();
            jiraInfo.setPointsEstimate(pointsEstimate);
            float pointsActual = (float)jira.getFields().getTimespent()/(60*60*8);
            jiraInfo.setPointsActual(pointsActual);
            float pointDelta = pointsEstimate - pointsActual;
            jiraInfo.setPointsEstimateDelta(pointDelta);
            jiraInfo.setPointsEstimateQuality(JiraDataUtil.getPointsEstimateQuality(pointDelta));

            jiraInfo.setAssigneeTimeLoggedMap(buildAssigneeTimeLoggedMap(jiraInfo, jira.getFields().getWorklog().getWorklogs()));
            jiraInfo.setStatusHistoryList(buildStatusHistoryList(jira.getChangelog().getHistories()));

            jiraInfoList.add(jiraInfo);
        }

        return jiraInfoList;
    }


    private List<StatusHistory> buildStatusHistoryList(History[] histories){

        List<StatusHistory> statusHistoryList = new ArrayList<>();

        for(History history : histories){
            for(Item item : history.getItems()){

                if(item.getField().equals("status")){

                    StatusHistory statusHistory = new StatusHistory();

                    statusHistory.setId(history.getId());
                    statusHistory.setPerson(buildStatusChangePerson(history.getAuthor()));
                    statusHistory.setCreated(history.getCreated());
                    statusHistory.setFromStatus(item.getFromString());
                    statusHistory.setToStatus(item.getToString());

                    statusHistoryList.add(statusHistory);
                }
            }
        }

        Collections.sort(statusHistoryList);
        return statusHistoryList;
    }


    private jira.model.report.Person buildStatusChangePerson(Person changePerson){

        jira.model.report.Person person = new jira.model.report.Person();
        person.setAssigneeName(changePerson.getDisplayName());
        person.setAssigneeEmail(changePerson.getEmailAddress());

        return person;
    }

    private Map<String, Assignee> buildAssigneeTimeLoggedMap(JiraInfo jiraInfo, Worklog[] worklogs){

        Map<String,Assignee> assigneeTimeLoggedMap = new HashMap<>();

        addTicketAssigneesToAssigneeTimeLoggedMap(assigneeTimeLoggedMap, jiraInfo);
        addWorklogAssigneesToAssigneeTimeLoggedMap(assigneeTimeLoggedMap, worklogs);

        return assigneeTimeLoggedMap;
    }

    private void addTicketAssigneesToAssigneeTimeLoggedMap(Map<String, Assignee> assigneeTimeLoggedMap, JiraInfo jiraInfo){

        addTicketAssigneeToAssigneeTimeLoggedMap(assigneeTimeLoggedMap,jiraInfo.getCoder());
        addTicketAssigneeToAssigneeTimeLoggedMap(assigneeTimeLoggedMap,jiraInfo.getTester01());
        addTicketAssigneeToAssigneeTimeLoggedMap(assigneeTimeLoggedMap,jiraInfo.getTester02());
    }

    private void addWorklogAssigneesToAssigneeTimeLoggedMap(Map<String, Assignee> assigneeTimeLoggedMap, Worklog[] worklogs){

        for(Worklog worklog : worklogs){
            String workLogNameKey = worklog.getAuthor().getDisplayName();
            int workLogSecondsLogged = worklog.getTimeSpentSeconds();
            Assignee workLogAssignee;

            if(assigneeTimeLoggedMap.containsKey(workLogNameKey)){
                workLogAssignee = assigneeTimeLoggedMap.get(workLogNameKey);
                workLogSecondsLogged = workLogAssignee.getSecondsLogged() + workLogSecondsLogged;
            }else{
                workLogAssignee =  mapPersonToAssignee(worklog.getAuthor(), Assignee.AssigneeType.EMPTY);
            }
            workLogAssignee.setSecondsLogged(workLogSecondsLogged);
            assigneeTimeLoggedMap.put(workLogNameKey, workLogAssignee);
        }
    }

    private void addTicketAssigneeToAssigneeTimeLoggedMap(Map<String, Assignee> assigneeTimeLoggedMap, Assignee assignee){
        if(StringUtils.isNotEmpty(assignee.getPerson().getAssigneeName())){
            assigneeTimeLoggedMap.put(assignee.getPerson().getAssigneeName(),assignee);
        }
    }

    private Assignee mapPersonToAssignee(Person person, Assignee.AssigneeType type){

        Assignee assignee = new Assignee();

        assignee.setAssigneeType(type);
        assignee.getPerson().setAssigneeName(person.getDisplayName());
        assignee.getPerson().setAssigneeEmail(person.getEmailAddress());

        return assignee;
    }

    private Jira[] getJqlJiras(String jql){

        JiraWebApiData jiraWebApiData = new JiraWebApiData();
        String jiraData = jiraWebApiData.getSprintJiraJsonString(jql);

        ObjectMapper objectMapper = new ObjectMapper();

        Jira[] jiras = null;
        try {
            jiras = objectMapper.readValue(jiraData, Jira[].class);
        } catch (IOException e) {
            log.error("Issue marshalling data : ",e);
        }

        return jiras;
    }

}
