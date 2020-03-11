package jira.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Fields {

    private Resolution resolution;
    private String summary;
    private String[] labels;
    private WorkLogData worklog;
    private Person assignee;
    //tester 1
    private Person customfield_11700;
    //tester 2
    private Person customfield_12837;
    //Estimated Points
    private float customfield_10002;
    //Actual Points
    private int timespent;
    //Epic Link
    private String customfield_10205;

}
