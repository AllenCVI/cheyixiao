package com.autoforce.cheyixiao.home.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HomeCarFilePathDao {

    private static final String TAG = "HomeCarFilePathDao";

    HomeCarFileDaoHelper helper;

    public HomeCarFilePathDao(Context context) {
        helper = new HomeCarFileDaoHelper(context);
    }

    /*public boolean isHomeCarFilePathTableExist(String tableName) {
        boolean isExist = false;
        SQLiteDatabase database = helper.getWritableDatabase();
        String queryStr = "select count(*) from sqlite_master where type = ? and name = ?";
        Cursor cursor = database.rawQuery(queryStr, new String[]{"table", tableName});
        isExist = cursor.getInt(0) > 0 ? true : false;
        if (cursor != null && (!cursor.isClosed()))
            cursor.close();
        return isExist;
    }*/

    /*public void createHomeCarFilePathTable(String tableName) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String createTableStr = "create table "
                + tableName
                + " (" + "fileName text primary key, "
                + "filePath text)";
        database.execSQL(createTableStr);
    }*/

    public void insertHomeCarFilePathData(String fileName, String filePath, String carId) {
        String tableName = "fileDownload";
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fileName", fileName);
        values.put("filePath", filePath);
        long insertNumber = database.insert(tableName, null, values);
        Log.e(TAG, insertNumber > 0 ? ("数据插入成功fileName:" + fileName + ", filePath:" + filePath) : ("数据插入失败fileName:" + fileName + ", filePath:" + filePath));
    }

    public void deleteHomeCarFilePathData(String fileName) {
        String tableName = "fileDownload";
        SQLiteDatabase database = helper.getWritableDatabase();
        int deleteNumber = database.delete(tableName, "fileName = ?", new String[]{fileName});
        Log.e(TAG, deleteNumber > 0 ? ("数据删除成功fileName:" + fileName) : ("数据删除失败fileName:" + fileName));
    }

    public void updateHomeCarFilePathData(String fileName, String filePath) {
        String tableName = "fileDownload";
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("filePath", filePath);
        int updateNumber = database.update(tableName, values, "fileName = ?", new String[]{fileName});
        Log.e(TAG, updateNumber > 0 ? ("数据更新成功fileName:" + fileName + ", filePath:" + filePath) : ("数据更新失败fileName:" + fileName + ", filePath:" + filePath));
    }

    public String queryHomeCarFilePathData(String fileName) {
        String tableName = "fileDownload";
        String filePath = null;
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(tableName, new String[]{"filePath"}, "fileName = ?", new String[]{fileName}, null, null, null);
        /*if (cursor.moveToFirst()) {
            do {
                filePath = cursor.getString(cursor.getColumnIndex(fileName));
                break;
            } while (cursor.moveToNext());
        }*/
        while (cursor.moveToNext()) {
            filePath = cursor.getString(cursor.getColumnIndex("filePath"));
            break;
        }
        if (cursor != null && (!cursor.isClosed()))
            cursor.close();
        return filePath;
    }

    public int queryHomeCarFilePathNumber() {
        String tableName = "fileDownload";
        SQLiteDatabase database = helper.getReadableDatabase();
        String queryStr = "select count(*) from " + tableName;
        Cursor cursor = database.rawQuery(queryStr, null);
        int queryNumber = cursor.getInt(0);
        return queryNumber;
    }
}
