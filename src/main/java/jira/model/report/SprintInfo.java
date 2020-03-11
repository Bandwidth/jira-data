package jira.model.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SprintInfo {


    private String sprintName;
    private List<JiraInfo> jiraInfoList;
    private Map<String, Integer> assigneeSecondsLoggedMap = new HashMap<>();
    private boolean printJiraHours;



}
