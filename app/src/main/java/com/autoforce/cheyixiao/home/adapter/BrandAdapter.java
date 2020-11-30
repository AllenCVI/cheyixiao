package com.autoforce.cheyixiao.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.ImageLoaderUtils;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.home.bean.HomeBrandInfo;

import java.util.logging.Logger;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by liusilong on 2018/11/20.
 * version:1.0
 * Describe: 品牌列表适配器
 */
public class BrandAdapter extends IndexableAdapter<HomeBrandInfo> {
    private static final String TAG = "BrandAdapter";

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        String name = parent.getClass().getName();
        Log.e(TAG, "onCreateTitleViewHolder: " + name);
        View titleItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_brand_item_title, parent, false);
        return new TitleVH(titleItemView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View titleItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_brand_item, parent, false);
        return new ContentVH(titleItemView);
    }


    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        TitleVH vh = (TitleVH) holder;
        vh.tvTitle.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, HomeBrandInfo entity) {
        ContentVH contentVH = (ContentVH) holder;
        ImageLoaderUtils.loadImage(entity.getIco(), contentVH.imageView, -1, -1, null);
        contentVH.textView.setText(entity.getName());
        if (entity.getH5() == 0) {
            contentVH.h5Text.setVisibility(View.GONE);
        } else {
            contentVH.h5Text.setVisibility(View.VISIBLE);
        }
        if(entity.getQuanjing() == 0){
            contentVH.quanJing.setVisibility(View.GONE);
        }else {
            contentVH.quanJing.setVisibility(View.VISIBLE);
        }
        contentVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.brandonClick(entity);
                }
            }
        });
    }

    /**
     * 标题
     */
    private class TitleVH extends RecyclerView.ViewHolder {
        TextView tvTitle;

        TitleVH(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.brand_item_title);
        }
    }

    /**
     * 内容 ViewHolder
     */
    private class ContentVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        private  TextView quanJing;
        private  TextView h5Text;

        ContentVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_brand);
            textView = itemView.findViewById(R.id.tv_brand);
            quanJing = itemView.findViewById(R.id.quanjing);
            h5Text = itemView.findViewById(R.id.h5_text);
        }
    }
}
