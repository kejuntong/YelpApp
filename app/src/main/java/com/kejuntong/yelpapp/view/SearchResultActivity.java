package com.kejuntong.yelpapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kejuntong.yelpapp.Constants;
import com.kejuntong.yelpapp.R;
import com.kejuntong.yelpapp.viewmodel.SearchResultViewModel;
import java.util.ArrayList;
import java.util.List;

import static com.kejuntong.yelpapp.view.SearchResultItem.TYPE_CATEGORY_HEADER;
import static com.kejuntong.yelpapp.view.SearchResultItem.TYPE_SEARCH_ITEM;

public class SearchResultActivity extends AppCompatActivity implements SearchResultAdapter.ItemClickListener{

    EditText searchTerm;
    EditText searchLocation;
    RecyclerView recyclerView;
    SearchResultAdapter adapter;
    Spinner spinner;
    Button searchButton;
    ProgressBar progressBar;
    List<SearchResultItem> data = new ArrayList<>();

    SearchResultViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        setupViews();
        setupViewModel();
        setupSearchButton();
    }

    @Override
    public void onItemClick(View view, int position) {
        SearchResultItem item = data.get(position);
        if (item.getType() == TYPE_SEARCH_ITEM) {
            Intent intent = new Intent(SearchResultActivity.this, BusinessDetailsActivity.class);
            intent.putExtra(Constants.BUSINESS_ID, data.get(position).getBusiness().getId());
            startActivity(intent);
        }
    }

    private void setupViews() {
        searchTerm = findViewById(R.id.search_term_input);
        searchLocation = findViewById(R.id.search_location_input);
        recyclerView = findViewById(R.id.search_result_list);
        spinner = findViewById(R.id.sort_spinner);
        searchButton = findViewById(R.id.search_button);
        progressBar = findViewById(R.id.progress_bar);

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
    }

    private void setupViewModel() {
        viewModel = new SearchResultViewModel();
        viewModel.getSearchResult().observe(SearchResultActivity.this, searchResultItems -> {
            data.clear();
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            data.addAll(searchResultItems);
            adapter.notifyDataSetChanged();
        });
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.search(searchTerm.getText().toString(), searchLocation.getText().toString(), spinner.getSelectedItem().toString());
        });
    }

}
