package com.kejuntong.yelpapp.model.data;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

public class Term implements Serializable {
    @JsonGetter("text")
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    String text;
}
