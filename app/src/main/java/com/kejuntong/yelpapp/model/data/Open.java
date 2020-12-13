package com.kejuntong.yelpapp.model.data;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

public class Open implements Serializable {
    @JsonGetter("is_overnight")
    public boolean getIsOvernight() {
        return this.isOvernight;
    }
    public void setIsOvernight(boolean isOvernight) {
        this.isOvernight = isOvernight;
    }
    boolean isOvernight;

    @JsonGetter("end")
    public String getEnd() {
        return this.end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    String end;

    @JsonGetter("start")
    public String getStart() {
        return this.start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    String start;

    @JsonGetter("day")
    public int getDay() {
        return this.day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    int day;
}
