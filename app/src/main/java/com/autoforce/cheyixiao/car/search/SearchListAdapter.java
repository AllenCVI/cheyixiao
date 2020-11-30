package com.autoforce.cheyixiao.car.search;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.autoforce.cheyixiao.App;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.car.base.refresh.StatusAdapter;
import com.autoforce.cheyixiao.common.data.local.bean.HighlightData;
import com.autoforce.cheyixiao.common.data.remote.bean.SearchCarListResult;
import com.autoforce.cheyixiao.common.utils.TextViewUtils;
import com.autoforce.cheyixiao.common.view.bold.BoldTextView;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;

/**
 * Created by xialihao on 2018/11/19.
 */
public class SearchListAdapter extends StatusAdapter<SearchCarListResult.ResultsBean> {


    @Override
    public BaseHolder<SearchCarListResult.ResultsBean> getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_search_car;
    }

    class ViewHolder extends BaseHolder<SearchCarListResult.ResultsBean> {

        @BindView(R.id.tv_title)
        BoldTextView tvTitle;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_color)
        TextView tvColor;
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_spec)
        TextView tvSpec;
        @BindView(R.id.tv_fixed)
        TextView tvFixed;
        @BindView(R.id.tv_expect)
        TextView tvExpect;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_status_detail)
        TextView tvStatusDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(SearchCarListResult.ResultsBean data, int position) {

            Context context = itemView.getContext();
            Resources resources = context.getResources();

            tvTitle.setText(data.getSpec());

            // 状态显示
            try {
                int status = data.getStatus();
                tvStatus.setText(Constants.STATUS[status - 1]);

                int redColor = ContextCompat.getColor(context, R.color.redD5);
                int greyColor = ContextCompat.getColor(context, R.color.black9);

                tvStatusDetail.setVisibility(View.VISIBLE);

                switch (status) {
                    // 寻车中
                    case 1:
                        tvStatus.setTextColor(redColor);
                        String quoteNum = String.valueOf(data.getQuoteNum());
                        tvStatusDetail.setText(TextViewUtils.highlightContent(context, resources.getString(R.string.quote_numbers_format, quoteNum),
                                new HighlightData(quoteNum, R.style.textsize14TextcolorRedd5Style)));
                        break;
                    //寻车完成
                    case 2:
                        tvStatus.setTextColor(redColor);
                        tvStatusDetail.setVisibility(View.GONE);
                        break;
                    //寻车取消
                    case 3:
                        tvStatus.setTextColor(greyColor);
                        tvStatusDetail.setVisibility(View.GONE);
                        break;
                    // 寻车结束
                    case 4:
                        tvStatus.setTextColor(greyColor);
                        tvStatusDetail.setVisibility(View.GONE);
                        break;
                }

            } catch (IndexOutOfBoundsException e) {

            }


            showText(tvColor, data.getColor());
            showText(tvCity, data.getPlateCity());
            showText(tvSpec, data.getCarFormat());

            // 定金显示
            String orderFee = String.valueOf(data.getOrderFee());
            tvFixed.setText(TextViewUtils.highlightContent(context, resources.getString(R.string.order_price_format, orderFee),
                    new HighlightData(orderFee, R.style.textsize14TextcolorRedd5Style)));

            // 期望价显示
            String expectedPrice = data.getExpectedPrice();
            if (expectedPrice != null) {
                tvExpect.setText(TextViewUtils.highlightContent(context, resources.getString(R.string.expect_price_format, expectedPrice),
                        new HighlightData(expectedPrice, R.style.textsize14TextcolorRedd5Style)));
            }

            // 时间显示
            tvDate.setText(data.getCreatedAt());

        }

        private void showText(TextView textView, String string) {

            if (TextUtils.isEmpty(string)) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(string);
            }
        }
    }

    interface Constants {
        String[] STATUS = App.getInstance().getResources().getStringArray(R.array.car_search_status);
    }

}
