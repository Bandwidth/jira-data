package jira.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Jira {

    private int id;
    private String self;
    private String key;
    private List<String> assigneeList;
    private Map<String, Integer> timeLoggedMap;
    private Fields fields;
    private Changelog changelog;

}
