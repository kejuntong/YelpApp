package com.kejuntong.yelpapp.view;

import com.kejuntong.yelpapp.model.data.Business;

public class SearchResultItem {
    private int type;
    private Business business;
    private SearchCategoryHeader header;

    public SearchResultItem(Object data) {
        if (data instanceof Business) {
            this.business = (Business) data;
            type = TYPE_SEARCH_ITEM;
        } else if (data instanceof SearchCategoryHeader) {
            this.header = (SearchCategoryHeader) data;
            type = TYPE_CATEGORY_HEADER;
        } else {
            type = TYPE_INVALID;
        }
    }

    public Business getBusiness() {
        return this.business;
    }

    public SearchCategoryHeader getHeader() {
        return this.header;
    }

    public int getType() {
        return this.type;
    }

    public static final int TYPE_CATEGORY_HEADER = 1;
    public static final int TYPE_SEARCH_ITEM = 2;
    public static final int TYPE_INVALID = -1;
}
