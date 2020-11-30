package com.autoforce.cheyixiao.common.utils;

import android.support.annotation.StringRes;
import com.autoforce.cheyixiao.App;

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getString(@StringRes int stringId) {
        return App.getInstance().getString(stringId);
    }

    public static String formatVersionName() {

        String versionName = AppMessageUtils.getAppVersionName(App.getInstance());

        String[] split = versionName.split("\\.");

        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            if (str.length() < 2) {
                sb.append("0");
            }
            sb.append(str);
        }

        return sb.toString();
    }
}
