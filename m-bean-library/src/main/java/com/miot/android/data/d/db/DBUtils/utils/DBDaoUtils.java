package com.miot.android.data.d.db.DBUtils.utils;

import android.annotation.TargetApi;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.text.TextUtils;

/**
 * Created by xdf on 2016/6/29.
 */
public class DBDaoUtils {
    public static String sql="CREATE TABLE TableName ( id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar, type Integer );" +
                             "CREATE TABLE TableName ( id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar, type Integer );";
    public static String sql1="CREATE TABLE TableName ( id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar, type Integer );";
    public static String select="select * from TableName;";
    public static String insert="insert into tableName(id,name,type)values(\\\"1\\\",\\\"热水器\\\",\\\"100\\\");";
    public static String deleted="delete from tableName where id = 1;";
    public static int getTableNameCount(String sql){
         int count=0;
         if(!TextUtils.isEmpty(sql)){
             char[] ch=sql.toCharArray();
             for(int i=0;i<ch.length;i++){
                 if(ch[i]==';'){
                     count++;
                 }
             }
             return count;
         }
         return -1;
     }

    public static String[] parseCreareTableNameSQl(String sql){
        String[] s=sql.split(";", getTableNameCount(sql));
        for (int i=0;i<s.length;i++){
            if(!s[i].endsWith(";")){
                s[i]=s[i]+";";
            }
            //Log.e("out","==sql=create="+s[i]+"\n");
        }
        return s;
    }

    public static String getCreateTableName(String sql){
        return sql.substring(sql.indexOf("exists")+"exists".length()+1,sql.indexOf("(")).trim();
    }

    public static String getDropTableName(String sql){
        return sql.substring(sql.indexOf("EXISTS")+"EXISTS".length()+1,sql.indexOf(";")).trim();
    }

    public static String[] parseDropTableNameSQl(String sql){
        String[] s=sql.split(";", getTableNameCount(sql));
        for (int i=0;i<s.length;i++){
            if(!s[i].endsWith(";")){
                s[i]=s[i]+";";
            }
            //Log.e("out","==sql=drop="+s[i]+"\n");
        }
        return s;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static long DBBatchInsert(SQLiteStatement statement, String sql){
        try {
            if(DatabaseUtils.getSqlStatementType(sql)!=DatabaseUtils.STATEMENT_ATTACH){
                return statement.executeInsert();
            }
        }catch (Exception e){
        }finally {
            if(statement!=null){
                statement.close();
            }
        }
        return -1L;
    }

//    acquireReference();
//    try {
//        if (DatabaseUtils.getSqlStatementType(sql) == DatabaseUtils.STATEMENT_ATTACH) {
//            boolean disableWal = false;
//            synchronized (mLock) {
//                if (!mHasAttachedDbsLocked) {
//                    mHasAttachedDbsLocked = true;
//                    disableWal = true;
//                }
//            }
//            if (disableWal) {
//                disableWriteAheadLogging();
//            }
//        }
//
//        SQLiteStatement statement = new SQLiteStatement(this, sql, bindArgs);
//        try {
//            return statement.executeUpdateDelete();
//        } finally {
//            statement.close();
//        }
//    } finally {
//        releaseReference();
//    }

}
