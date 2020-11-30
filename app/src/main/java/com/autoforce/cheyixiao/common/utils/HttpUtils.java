package com.autoforce.cheyixiao.common.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import com.liulishuo.okdownload.core.listener.DownloadListener3;
import com.liulishuo.okdownload.core.listener.DownloadListener4;
import com.liulishuo.okdownload.core.listener.assist.Listener4Assist;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    public static boolean downloadFile(String urlStr, OutputStream fileOutPutStream) {
        return downloadFile(urlStr, fileOutPutStream, "");
    }

    public static boolean downloadFile(String urlStr, OutputStream out, String urlKey) {
        HttpURLConnection conn = null;
        InputStream in = null;

        try {
            URL fileUrl = new URL(urlStr);
            conn = (HttpURLConnection) fileUrl.openConnection();
            conn.connect();
            int connectCode = conn.getResponseCode();
            if (connectCode == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
                int size = 0;
                byte[] buff = new byte[1024];
                while ((size = in.read(buff)) != -1) {
                    out.write(buff, 0, size);
                }
                //Log.e(TAG, "文件:" + urlStr + "下载成功, fileKey:" + urlKey);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Log.e(TAG, "文件:" + urlStr + "下载失败, fileKey:" + urlKey);
        return false;
    }

    public static String downloadDataString(String dataUrl) {
        HttpURLConnection conn = null;
        StringBuffer sbf = new StringBuffer("");
        BufferedReader reader = null;
        try {
            URL fileUrl = new URL(dataUrl);
            conn = (HttpURLConnection) fileUrl.openConnection();
            conn.connect();
            int connectCode = conn.getResponseCode();
            if (connectCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8 * 1024);
                String dataLine = null;
                while ((dataLine = reader.readLine()) != null) {
                    sbf.append(dataLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sbf.toString();
    }
}
