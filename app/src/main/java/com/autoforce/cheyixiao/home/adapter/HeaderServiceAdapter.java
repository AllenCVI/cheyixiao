package com.autoforce.cheyixiao.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.ImageLoaderUtils;
import com.autoforce.cheyixiao.home.bean.HomeService;

import java.util.List;

/**
 * Created by liusilong on 2018/11/23.
 * version:1.0
 * Describe:
 */
public class HeaderServiceAdapter extends RecyclerView.Adapter<HeaderServiceAdapter.ContentVH> {

    private List<HomeService> homeServices;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public HeaderServiceAdapter(List<HomeService> homeServices) {
        this.homeServices = homeServices;
    }

    @NonNull
    @Override
    public ContentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_service_item, parent, false);
        return new ContentVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentVH holder, int position) {
        HomeService homeService = homeServices.get(position);
        ImageLoaderUtils.loadImage(homeService.getIcon(), holder.imageView, -1, -1, null);
        holder.textView.setText(homeService.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.serviceOnClick(homeService);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeServices.size();
    }

    static class ContentVH extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;

        ContentVH(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.home_service_linear);
            imageView = itemView.findViewById(R.id.home_service_iv);
            textView = itemView.findViewById(R.id.home_service_tv);
        }
    }
}

