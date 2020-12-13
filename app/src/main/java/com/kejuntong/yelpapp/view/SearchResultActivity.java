package com.kejuntong.yelpapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejuntong.yelpapp.R;
import com.kejuntong.yelpapp.model.connection.YelpFusionApi;
import com.kejuntong.yelpapp.model.connection.YelpFusionApiFactory;
import com.kejuntong.yelpapp.model.data.Business;
import com.kejuntong.yelpapp.model.data.Category;
import com.kejuntong.yelpapp.model.data.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kejuntong.yelpapp.view.SearchResultItem.TYPE_CATEGORY_HEADER;

public class SearchResultActivity extends AppCompatActivity implements SearchResultAdapter.ItemClickListener{

    EditText searchTerm;
    EditText searchLocation;
    RecyclerView recyclerView;
    SearchResultAdapter adapter;
    Spinner spinner;
    Button searchButton;
    List<SearchResultItem> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchTerm = findViewById(R.id.search_term_input);
        searchLocation = findViewById(R.id.search_location_input);
        recyclerView = findViewById(R.id.search_result_list);
        spinner = findViewById(R.id.sort_spinner);
        searchButton = findViewById(R.id.search_button);

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == TYPE_CATEGORY_HEADER ? manager.getSpanCount() : 1;
            }
        });

        recyclerView.setLayoutManager(manager);
        adapter = new SearchResultAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        searchButton.setOnClickListener(v -> {
            try {
                testCall(searchTerm.getText().toString(), searchLocation.getText().toString(), spinner.getSelectedItem().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void testCall(String term, String location, String sortBy) throws IOException {
        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        YelpFusionApi yelpFusionApi = apiFactory.createAPI(getString(R.string.api_key));

        Map<String, String> params = new HashMap<>();

        params.put("term", term);
        params.put("location", location);
        params.put("sort_by", sortBy);

        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);

        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();

                // to group item according to category
                Map<String, List<SearchResultItem>> map = new HashMap<>();
                for (Business business : searchResponse.getBusinesses()) {
                    for (Category category : business.getCategories()) {
                        String categoryTitle = category.getTitle();
                        if (!map.containsKey(categoryTitle)) {
                            List<SearchResultItem> list = new ArrayList<>();
                            list.add(new SearchResultItem(business));
                            map.put(categoryTitle, list);
                        } else {
                            map.get(categoryTitle).add(new SearchResultItem(business));
                        }
                    }
                }

                data.clear();

                for (Map.Entry<String, List<SearchResultItem>> entry : map.entrySet()) {
                    String categoryName = entry.getKey();
                    List<SearchResultItem> businessList = entry.getValue();
                    data.add(new SearchResultItem(new SearchCategoryHeader(categoryName, businessList.size())));
                    data.addAll(businessList);
                }

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        };

        call.enqueue(callback);
    }
}
