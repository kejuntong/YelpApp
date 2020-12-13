package com.kejuntong.yelpapp.view;

public class SearchCategoryHeader {
    String title;
    int count;

    public SearchCategoryHeader(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return this.title;
    }

    public int getCount() {
        return this.count;
    }
}
