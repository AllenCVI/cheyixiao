package com.autoforce.cheyixiao.common.update;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.DeviceUtil;

import java.util.List;

/**
 * Created by liusilong on 2018/12/4.
 * version:1.0
 * Describe:
 */
public class UpdateDialog extends Dialog {

    private LinearLayout tipContainer;

    public UpdateDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public UpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        tipContainer = dialogView.findViewById(R.id.linear_update_container);
        Button button = dialogView.findViewById(R.id.btn_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onClickListener.onClick(dialogView);
            }
        });

        LinearLayout.LayoutParams buttonLayoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        buttonLayoutParams.width = (int) (DeviceUtil.getScreenWidth(context.getApplicationContext()) * 0.7f);
        setContentView(dialogView);
        Window window = this.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = (int) (DeviceUtil.getScreenWidth(context.getApplicationContext()) * 0.85f);
        window.setAttributes(lp);
    }



    private View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 设置更新内容
     *
     * @param tips 更新
     */
    public void setTips(List<String> tips) {
        if (tips == null) {
            return;
        }
        if (tips.size() == 0) {
            return;
        }
        if (tipContainer.getChildCount() > 0) {
            tipContainer.removeAllViews();
        }

        int size = tips.size();
        for (int i = 0; i < size; i++) {
            TextView textView = generateTextView(tips.get(i));
            tipContainer.addView(textView);
        }

    }

    private TextView generateTextView(String content) {
        TextView textView = new TextView(this.getContext());
        textView.setPadding(0,5,0,5);
        textView.setTextSize(15);
        textView.setTextColor(Color.parseColor("#666666"));
        textView.setText(content);
        return textView;
    }
}
