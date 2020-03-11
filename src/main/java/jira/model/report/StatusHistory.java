package jira.model.report;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusHistory implements Comparable<StatusHistory> {

    private int id;
    private Person person;
    private Timestamp created;
    private String fromStatus;
    private String toStatus;

    @Override
    public int compareTo(final StatusHistory that) {
        return this.created.compareTo(that.created);
    }

}
