package jira.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionItems {

    private Map<String, List<Assignee>> missingLogTime = new HashMap<>();
    private Map<String, Float> reviewTime;
    private List<String> assignEpic;
    private List<String> assignAssignee;
    private List<String> assignPoints;
    private List<String> reviewEstimation;

}
