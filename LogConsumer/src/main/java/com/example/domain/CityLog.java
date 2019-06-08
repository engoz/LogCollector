package com.example.domain;

import org.springframework.data.annotation.Id;

public class CityLog {

    @Id
    String id;
    String name;
    String level;
    Number timestamp;
    String message;

    public CityLog(String name, String level, Number timestamp, String message) {
        this.name = name;
        this.level = level;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Number getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Number timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
