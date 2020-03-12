package jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Person {


    private String displayName;
    private String name;
//    private String key;
    private String emailAddress;


    @Override
    public String toString() {
        return "displayName='" + displayName ;
    }
}
