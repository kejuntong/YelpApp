package com.kejuntong.yelpapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kejuntong.yelpapp.model.data.Business;
import com.kejuntong.yelpapp.model.data.Category;
import com.kejuntong.yelpapp.model.data.SearchResponse;
import com.kejuntong.yelpapp.view.SearchCategoryHeader;
import com.kejuntong.yelpapp.view.SearchResultItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultViewModel extends BaseViewModel {
    private static final String TAG = "SearchResultViewModel";

    private final MutableLiveData<List<SearchResultItem>> searchResultLiveData = new MutableLiveData<>();

    public LiveData<List<SearchResultItem>> getSearchResult() {
        return searchResultLiveData;
    }

    public void search(String term, String location, String sortBy) {

        if (yelpFusionApi == null) {
            Log.e(TAG, "yelpFusionApi not created");
            return;
        }

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

                List<SearchResultItem> data = new ArrayList<>();

                for (Map.Entry<String, List<SearchResultItem>> entry : map.entrySet()) {
                    String categoryName = entry.getKey();
                    List<SearchResultItem> businessList = entry.getValue();
                    data.add(new SearchResultItem(new SearchCategoryHeader(categoryName, businessList.size())));
                    data.addAll(businessList);
                }

                searchResultLiveData.postValue(data);
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // when failure, just post a fake header as "no result (0)"
                // not a good approach
                List<SearchResultItem> data = new ArrayList<>();
                data.add(new SearchResultItem(new SearchCategoryHeader("no result", 0)));
                searchResultLiveData.postValue(data);
            }
        };

        call.enqueue(callback);
    }
}
