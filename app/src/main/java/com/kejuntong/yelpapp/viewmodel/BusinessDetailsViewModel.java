package com.kejuntong.yelpapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kejuntong.yelpapp.model.data.Business;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusinessDetailsViewModel extends BaseViewModel {
    private static final String TAG = "BusinessDetailsViewModel";

    public LiveData<Business> getBusinessDetails(String businessId) {

        MutableLiveData<Business> businessLiveData = new MutableLiveData<>();

        Callback<Business> callback = new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                Business business = response.body();
                businessLiveData.postValue(business);
            }
            @Override
            public void onFailure(Call<Business> call, Throwable t) {

            }
        };

        Call<Business> call = yelpFusionApi.getBusiness(businessId);
        call.enqueue(callback);

        return businessLiveData;
    }

}
