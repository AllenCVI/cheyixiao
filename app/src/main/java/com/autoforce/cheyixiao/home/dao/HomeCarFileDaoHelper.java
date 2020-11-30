package com.autoforce.cheyixiao.home.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * create by AiYaChao on 2018/12/16
 */
public class HomeCarFileDaoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cyx.db";
    private static final int DATABASE_VERSION = 1;

    public HomeCarFileDaoHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStr = "create table fileDownload"
                + " (" + "fileName text primary key, "
                + "filePath text, "
                + "carId text)";
        db.execSQL(createTableStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
