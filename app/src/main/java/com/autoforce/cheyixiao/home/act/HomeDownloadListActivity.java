package com.autoforce.cheyixiao.home.act;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseActivity;
import com.autoforce.cheyixiao.common.FilesDownload;
import com.autoforce.cheyixiao.common.FilesDownloadPool;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.home.HomeConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by liusilong on 2018/12/25.
 * version:1.0
 * Describe:
 */
public class HomeDownloadListActivity extends BaseActivity {

    private static final String TAG = "HomeDownloadListActivit";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_home_download_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        Map<String, ?> all = SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_LIST_RECORD).getAll();

        Set<String> strings = all.keySet();

        List<String> ids = new ArrayList<>();
        for (String string : strings) {
            ids.add((String) all.get(string));
        }

        for (String id : ids) {
            Log.e(TAG, "initView: " + id);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DownloadAdapter(ids));
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeDownloadListActivity.class);
        context.startActivity(intent);
    }


    class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.Holder> {

        List<String> carIds;

        public DownloadAdapter(List<String> carIds) {
            this.carIds = carIds;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_download_list_item, parent, false);
            return new Holder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            String carId = carIds.get(position);
            FilesDownload filesDownload = FilesDownloadPool.getInstance().getFilesDownload(holder.itemView.getContext().getApplicationContext(), carId);
//            if (!filesDownload.isDownloading()) {
//                filesDownload.dealOfflineExperience();
//            }
            filesDownload.setListener((downloadedNumber, totalNumber) -> runOnUiThread(() -> holder.textView.setText(carId + ":\t" + downloadedNumber + "/" + totalNumber)));
        }

        @Override
        public int getItemCount() {
            return carIds.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView textView;

            public Holder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_download);
            }
        }
    }


}
