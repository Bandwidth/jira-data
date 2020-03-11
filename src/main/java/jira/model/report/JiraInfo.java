package jira.model.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JiraInfo {


    private String jiraKey;
    private String summary;
    private String epicLink;

    private Assignee coder = new Assignee();
    private Assignee tester01 = new Assignee();
    private Assignee tester02 = new Assignee();

    private float pointsEstimate;
    private float pointsActual;
    private float pointsEstimateDelta;
    private String pointsEstimateQuality;
    private Map<String,Assignee> assigneeTimeLoggedMap = new HashMap<>();

    private List<StatusHistory> statusHistoryList;

}
