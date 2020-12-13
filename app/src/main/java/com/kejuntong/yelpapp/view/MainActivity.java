package com.kejuntong.yelpapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.kejuntong.yelpapp.R;
import com.kejuntong.yelpapp.model.connection.YelpFusionApi;
import com.kejuntong.yelpapp.model.connection.YelpFusionApiFactory;
import com.kejuntong.yelpapp.model.data.SearchResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String apiKey =
            "DPM5KiXHlyfN2pquXd0EaDbDtC2rhc3MHXIshaIdWyAcHLxtY2kt6y88EwGzb-YpWNq2khDtoYLqmasFDvOUOpeyGHNxpxzZIdYpKmkLIl85BCJ2U_3xq5J6SXHVX3Yx";

    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = findViewById(R.id.test_button);

        testButton.setOnClickListener(v -> {
            try {
                testCall();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void testCall() throws IOException {
        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        YelpFusionApi yelpFusionApi = apiFactory.createAPI(apiKey);

        Map<String, String> params = new HashMap<>();

// general params
        params.put("term", "indian food");
        params.put("location", "NYC");
//        params.put("latitude", "40.581140");
//        params.put("longitude", "-111.914184");

        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);

        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                // Update UI text with the searchResponse.
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened, do something to handle it.
            }
        };

        call.enqueue(callback);
    }
}