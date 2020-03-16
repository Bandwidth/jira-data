package jira.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jira.dto.ActionItems;
import jira.dto.Assignee;
import jira.dto.JiraInfo;
import jira.dto.SprintInfo;

public class ActionItemAggregator {



    public ActionItems buildActionItems(SprintInfo sprintInfo){

        ActionItems actionItems = new ActionItems();

        for(JiraInfo jiraInfo : sprintInfo.getJiraInfoList()){

            actionItems.getMissingLogTime().putAll(collectMissingLogTime(jiraInfo));


        }
        return actionItems;
    }


    private Map<String, List<Assignee>> collectMissingLogTime(JiraInfo jiraInfo){

        Map<String, List<Assignee>> missingLogTime = new HashMap<>();
        List<Assignee> assigneeList = new ArrayList<>();

        for (String key : jiraInfo.getAssigneeTimeLoggedMap().keySet()){
            Assignee assignee = jiraInfo.getAssigneeTimeLoggedMap().get(key);
            if(assignee.getSecondsLogged() == 0){
                assigneeList.add(assignee);
            }
        }

        if(!assigneeList.isEmpty()){
            missingLogTime.put(jiraInfo.getJiraKey(), assigneeList);
        }

        return missingLogTime;
    }






}
