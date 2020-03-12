package jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Worklog {

    private Person author;
    private int timeSpentSeconds;
    private String timeSpent;
}
