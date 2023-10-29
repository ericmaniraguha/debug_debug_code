package com.data.reconciliation.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MissingRecordsEntity {
    private String id;
    private String msg;
    private String produced_at; // Change the type to String
    private String source; // Add this field for data source (es or rdbms)

    // Setters and Getters for id, msg, and produced_at
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProduced_at() {
        return produced_at;
    }

    // This is the only method for setting the 'produced_at' field, handling conversion from LocalDateTime to String
    public void setProduced_at(LocalDateTime produced_at) {
        // Convert the LocalDateTime to String before setting
        if (produced_at != null) {
            this.produced_at = produced_at.format(DateTimeFormatter.ISO_DATE_TIME);
        } else {
            this.produced_at = null;
        }
    }

    // Add a method to set the 'produced_at' field from a String value
    public void setProduced_at(String produced_at) {
        this.produced_at = produced_at;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
