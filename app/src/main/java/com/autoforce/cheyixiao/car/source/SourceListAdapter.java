package com.autoforce.cheyixiao.car.source;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.car.base.refresh.StatusAdapter;
import com.autoforce.cheyixiao.common.data.remote.bean.CarSourceListResult;
import com.autoforce.cheyixiao.common.utils.FormatUtils;
import com.autoforce.cheyixiao.common.utils.StringUtils;
import com.autoforce.cheyixiao.common.view.bold.BoldTextView;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;

/**
 * Created by xialihao on 2018/11/16.
 */
public class SourceListAdapter extends StatusAdapter<CarSourceListResult.ResultsBean> {


    @Override
    public BaseHolder<CarSourceListResult.ResultsBean> getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_car_source;
    }

    class ViewHolder extends BaseHolder<CarSourceListResult.ResultsBean> {

        @BindView(R.id.tv_name)
        BoldTextView tvName;
        @BindView(R.id.tv_quote)
        TextView tvQuote;
        @BindView(R.id.tv_guide)
        TextView tvGuide;
        @BindView(R.id.tv_procedure)
        TextView tvProcedure;
        @BindView(R.id.tv_gap)
        TextView tvGap;
        @BindView(R.id.tv_one)
        TextView tvOne;
        @BindView(R.id.tv_two)
        TextView tvTwo;
        @BindView(R.id.tv_three)
        TextView tvThree;
        @BindView(R.id.tv_four)
        TextView tvFour;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(CarSourceListResult.ResultsBean data, int position) {
            Context context = itemView.getContext();
            tvName.setText(data.getSpec());
            String quote = data.getQuote();
            if(TextUtils.isEmpty(quote) || !quote.contains(".")) {
                tvQuote.setText(quote);
            }else {
                tvQuote.setText(context.getResources().getString(R.string.ten_thousands_unit, quote));
            }
            tvProcedure.setText(data.getProcedures());
            tvGuide.setText(context.getResources().getString(R.string.guide_price_format, data.getGuidePrice()));
            double priceGap = getPriceGap(quote, data.getGuidePrice());
            String prefix;
            if (priceGap >= 0) {
                prefix = StringUtils.getString(R.string.low);
            } else {
                prefix = StringUtils.getString(R.string.up);
                priceGap = -priceGap;
            }
            tvGap.setText(prefix + context.getResources().getString(R.string.gap_price, FormatUtils.formatWithDecimals(priceGap, false)));
            showText(tvOne, data.getCarFormat());
            showText(tvTwo, data.getOuterColor() + "/" + data.getInnerColor());
            showText(tvThree, context.getResources().getString(R.string.car_location_format, data.getCarLocation()));
            showText(tvFour, context.getResources().getString(R.string.sell_area_format, data.getSellArea()));
        }

        private double getPriceGap(String quotePrice, String guidePrice) {

            try {
                float quote = Float.valueOf(quotePrice);
                float guide = Float.valueOf(guidePrice);

                return guide - quote;
            } catch (NumberFormatException e) {
                return 0;
            }

        }

        private void showText(TextView textView, CharSequence text) {
            if (TextUtils.isEmpty(text)) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }

            textView.setText(text);
        }
    }

}
