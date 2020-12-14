package com.kejuntong.yelpapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kejuntong.yelpapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.kejuntong.yelpapp.view.SearchResultItem.TYPE_CATEGORY_HEADER;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<SearchResultItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    SearchResultAdapter(Context context, List<SearchResultItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_CATEGORY_HEADER) {
            view = mInflater.inflate(R.layout.item_search_category_header, parent, false);
        } else {
            view = mInflater.inflate(R.layout.item_search_result, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_CATEGORY_HEADER) {
            holder.categoryHeader.setText(mData.get(position).getHeader().getTitle());
        } else {
            holder.businessName.setText(mData.get(position).getBusiness().getName());
            Picasso.get().load(mData.get(position).getBusiness().getImageUrl()).into(holder.businessImage);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryHeader;

        TextView businessName;
        ImageView businessImage;

        ViewHolder(View itemView) {
            super(itemView);
            categoryHeader = itemView.findViewById(R.id.category_header);

            businessName = itemView.findViewById(R.id.info_text);
            businessImage = itemView.findViewById(R.id.business_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
