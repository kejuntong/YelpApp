package com.kejuntong.yelpapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kejuntong.yelpapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BusinessPhotosAdapter extends RecyclerView.Adapter<BusinessPhotosAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    BusinessPhotosAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_restaurant_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(mData.get(position)).into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImage;

        ViewHolder(View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.photo_image);
        }
    }

}
