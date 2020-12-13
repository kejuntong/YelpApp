package com.kejuntong.yelpapp.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejuntong.yelpapp.R;
import com.kejuntong.yelpapp.model.connection.YelpFusionApi;
import com.kejuntong.yelpapp.model.connection.YelpFusionApiFactory;
import com.kejuntong.yelpapp.model.data.Business;
import com.kejuntong.yelpapp.model.data.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity implements SearchResultAdapter.ItemClickListener{

    RecyclerView recyclerView;
    SearchResultAdapter adapter;
    List<Business> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // set up the RecyclerView
        recyclerView = findViewById(R.id.search_result_list);

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 3 ? manager.getSpanCount() : 1;
            }
        });

        recyclerView.setLayoutManager(manager);
        adapter = new SearchResultAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        try {
            testCall();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void testCall() throws IOException {
        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        YelpFusionApi yelpFusionApi = apiFactory.createAPI(getString(R.string.api_key));

        Map<String, String> params = new HashMap<>();

// general params
        params.put("term", "indian food");
        params.put("location", "NYC");

        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);

        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                // Update UI text with the searchResponse.
                data.clear();
                data.addAll(searchResponse.getBusinesses());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened, do something to handle it.
            }
        };

        call.enqueue(callback);
    }
}
