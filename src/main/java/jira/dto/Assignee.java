package jira.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Assignee {


    public enum AssigneeType{
        EMPTY(   "Not Assigned"),
        CODER(   "Assignee"),
        TESTER01("Tester 1"),
        TESTER02("Tester 2");

        AssigneeType(String assigneeType){
            this.assignmentType = assigneeType;
        }

        @Getter private String assignmentType;
    }

    private AssigneeType assigneeType = AssigneeType.EMPTY;
    private Person person = new Person();
    private int secondsLogged = 0;

    public String toString(){
        return
            "{"
            + "userKey=" + person.getAssigneeName() + ","
            + "userType=" + getAssigneeType().getAssignmentType() + ","
            + "loggedTime=" + getSecondsLogged()
            + "}";
    }

}
