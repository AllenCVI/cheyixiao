package com.autoforce.cheyixiao.home.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.DeviceUtil;
import com.autoforce.cheyixiao.common.utils.DrawableUtils;
import com.autoforce.cheyixiao.common.utils.ImageLoaderUtils;
import com.autoforce.cheyixiao.home.bean.HomeBanner;
import com.autoforce.cheyixiao.home.bean.HomeHeaderBean;
import com.autoforce.cheyixiao.home.bean.HomeHotCar;
import com.autoforce.cheyixiao.home.bean.HomeService;
import com.autoforce.cheyixiao.home.view.banner.MZBannerView;
import com.autoforce.cheyixiao.home.view.banner.holder.MZHolderCreator;
import com.autoforce.cheyixiao.home.view.banner.holder.MZViewHolder;
import com.orhanobut.logger.Logger;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;

import java.util.List;

/**
 * Created by liusilong on 2018/11/20.
 * version:1.0
 * Describe: home 页头部适配器
 */
public class HeaderAdapter extends IndexableHeaderAdapter<HomeHeaderBean> {

    private static final String TAG = "HeaderAdapter";
    private MZBannerView bannerView;

    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public HeaderAdapter(String index, String indexTitle, List<HomeHeaderBean> datas) {
        super(index, indexTitle, datas);
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        Log.e(TAG, "onCreateContentViewHolder: ...");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_item, parent, false);
        ContentHolder contentHolder = new ContentHolder(itemView);
        this.bannerView = contentHolder.bannerView;
        return contentHolder;
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, HomeHeaderBean entity) {
        ContentHolder contentHolder = (ContentHolder) holder;
        // 设置 Banner
        setBanner(contentHolder.bannerView, entity.getBanners());
        // 是指服务项
        setService(contentHolder.recyclerView, entity.getServices());
        // 设置 热门车型
        setHotCar(contentHolder.linearHotCar, entity.getHotCars());

        setTextViewScale(contentHolder.tv_hotcar);

        setTextViewScale(contentHolder.tv_pinpai);

        Log.e(TAG, "onBindContentViewHolder: ...");

    }


    private void setTextViewScale(TextView textView) {

        DrawableUtils.resizeDrawable(textView, DrawableUtils.DRAWABLE_LEFT, 3 / 4f);


    }

    public void bannerStart() {
        Log.e(TAG, "bannerStart: 000");
        if (this.bannerView != null) {
//            this.bannerView.pause();
            this.bannerView.start();
            Log.e(TAG, "bannerStart: 111");
        }
    }

    public void bannerPause() {
        if (this.bannerView != null) {
            this.bannerView.pause();
        }
    }

    /**
     * 设置服务列表
     * <pre>
     *  services:
     *      size <= 5 一行 size 列
     *      size > 5 && size <= 8  一行 4 列
     *      size > 8 一行 5 列
     * </pre>
     *
     * @param recyclerView recyclerView
     * @param services     服务列表
     */
    private void setService(RecyclerView recyclerView, List<HomeService> services) {
        if (null != services && !services.isEmpty() && null != recyclerView) {
            int size = services.size();
            if (size <= 5) {
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), size));
            } else if (size > 5 && size <= 8) {
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 4));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 5));
            }
            HeaderServiceAdapter serviceAdapter = new HeaderServiceAdapter(services);
            serviceAdapter.setItemClickListener(itemClickListener);
            recyclerView.setAdapter(serviceAdapter);
        }
    }

    /**
     * 设置 热门车型
     *
     * @param linearHotCar 热门车型父 View
     * @param hotCars      热门车型列表
     */
    private void setHotCar(LinearLayout linearHotCar, List<HomeHotCar> hotCars) {
        if (!hotCars.isEmpty() && null != linearHotCar) {
            linearHotCar.removeAllViews();
            for (int i = 0; i < hotCars.size(); i++) {
                View hotCarItem = createHotCarItem(linearHotCar, hotCars.get(i));
                linearHotCar.addView(hotCarItem);
            }
        }
    }

    /**
     * 设置 Banner 轮播
     *
     * @param bannerView banner view\
     * @param banners    轮播图列表
     */
    private void setBanner(MZBannerView<HomeBanner> bannerView, List<HomeBanner> banners) {
        Logger.e("setBanner----------------");
        if (banners != null && !banners.isEmpty()) {
            bannerView.setPages(banners, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            bannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    Log.e(TAG, "onPageClick: " + position);
                    if (itemClickListener != null) {
                        itemClickListener.bannerOnclick(banners.get(position));
                    }
                }
            });

            bannerStart();
        }

    }

    /**
     * 创建热门车型单个 item
     *
     * @param parent item 父 view
     * @param car    car 实体
     * @return
     */
    private View createHotCarItem(ViewGroup parent, HomeHotCar car) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hot_car_item, parent, false);
        ImageView ivHotCar = itemView.findViewById(R.id.hot_car_item_iv);
        TextView tvHotCar = itemView.findViewById(R.id.hot_car_item_tv);
        tvHotCar.setMaxEms(4);
        tvHotCar.setMaxLines(1);
        ImageLoaderUtils.loadImage(car.getImageUrl(), ivHotCar, -1, -1, null);
        tvHotCar.setText(car.getCarInfos().getCarName());

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemView.getLayoutParams();
        layoutParams.setMarginEnd(DeviceUtil.dip2px(itemView.getContext(), 16));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.hotCarOnClick(car);
                }
            }
        });
        return itemView;
    }

    static class BannerViewHolder implements MZViewHolder<HomeBanner> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_banner_item, null);
            imageView = view.findViewById(R.id.home_banner_iv);
            return view;
        }

        @Override
        public void onBind(Context context, int position, HomeBanner data) {
            ImageLoaderUtils.loadImage(data.getImageUrl(), imageView, R.mipmap.ic_launcher, -1, null);
        }
    }


    class ContentHolder extends RecyclerView.ViewHolder {
        MZBannerView bannerView;
        RecyclerView recyclerView;
        LinearLayout linearHotCar;
        TextView tv_hotcar;
        TextView tv_pinpai;

        public ContentHolder(View itemView) {
            super(itemView);
            linearHotCar = itemView.findViewById(R.id.hot_car_linear);
            bannerView = itemView.findViewById(R.id.banner);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tv_hotcar = itemView.findViewById(R.id.tv_hotcar);

            tv_pinpai = itemView.findViewById(R.id.tv_pinpai);

////             设置 Banner
//            setBanner(bannerView, headerBean.getBanners());
//            setService(recyclerView, headerBean.getServices());
//            // 设置 热门车型
//            setHotCar(linearHotCar, headerBean.getHotCars());
        }
    }
}
