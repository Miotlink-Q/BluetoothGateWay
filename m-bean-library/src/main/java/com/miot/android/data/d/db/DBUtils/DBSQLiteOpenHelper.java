package com.miot.android.data.d.db.DBUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lidroid.xutils.DbUtils;

/**
 * Created by xdf on 2016/6/29.
 */
public class DBSQLiteOpenHelper {


    public boolean tableIsExist(DbUtils dbUtils,String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbUtils.getDatabase();
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
}
