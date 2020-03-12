package jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Item {

    private String field;
    private String fieldtype;
    private String fieldId;

    private String from;
    private String fromString;

    private String to;
    private String toString;

}
