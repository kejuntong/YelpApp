package com.kejuntong.yelpapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.kejuntong.yelpapp.model.connection.YelpFusionApi;
import com.kejuntong.yelpapp.model.connection.YelpFusionApiFactory;

import java.io.IOException;

public class BaseViewModel extends ViewModel {

    protected static final String API_KEY = "DPM5KiXHlyfN2pquXd0EaDbDtC2rhc3MHXIshaIdWyAcHLxtY2kt6y88EwGzb-YpWNq2khDtoYLqmasFDvOUOpeyGHNxpxzZIdYpKmkLIl85BCJ2U_3xq5J6SXHVX3Yx";
    protected YelpFusionApi yelpFusionApi;

    public BaseViewModel() {
        try {
            yelpFusionApi = new YelpFusionApiFactory().createAPI(API_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
