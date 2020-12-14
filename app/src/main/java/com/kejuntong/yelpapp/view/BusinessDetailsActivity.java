package com.kejuntong.yelpapp.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejuntong.yelpapp.Constants;
import com.kejuntong.yelpapp.R;
import com.kejuntong.yelpapp.viewmodel.BusinessDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class BusinessDetailsActivity extends AppCompatActivity {

    TextView businessName;
    TextView businessLocation;

    RecyclerView recyclerView;
    BusinessPhotosAdapter adapter;
    List<String> photoUrls = new ArrayList<>();

    BusinessDetailsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        businessName = findViewById(R.id.name_content);
        businessLocation = findViewById(R.id.location_content);

        recyclerView = findViewById(R.id.photo_gallery);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BusinessPhotosAdapter(this, photoUrls);
        recyclerView.setAdapter(adapter);

        String businessId = (String) getIntent().getExtras().getSerializable(Constants.BUSINESS_ID);

        viewModel = new BusinessDetailsViewModel();
        viewModel.getBusinessDetails(businessId).observe(BusinessDetailsActivity.this, business -> {

            businessName.setText(business.getName());
            businessLocation.setText(business.getLocation().getAddress1());

            photoUrls.clear();
            List<String> photoList = business.getPhotos();
            if (photoList != null) {
                photoUrls.addAll(photoList);
                adapter.notifyDataSetChanged();
            }
        });

    }

}
