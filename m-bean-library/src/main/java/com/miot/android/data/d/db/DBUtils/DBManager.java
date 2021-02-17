package com.miot.android.data.d.db.DBUtils;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.miot.android.data.d.db.DBUtils.entity.BatchInsertDao;
import com.miot.android.data.d.db.DBUtils.entity.CreateTableOrDeleteOrUpdateDao;
import com.miot.android.data.d.db.DBUtils.entity.DBDao;
import com.miot.android.data.d.db.DBUtils.entity.InsertDao;
import com.miot.android.data.d.db.DBUtils.entity.QueryDao;
import com.miot.android.data.d.db.DBUtils.entity.ReplaceDao;
import com.miot.android.data.d.db.DBUtils.utils.DBDaoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/6/25.
 */
public class DBManager extends DataManager {

    public static Context mContext;
    public final static String DB_NAME = "miot_ble_db";
    public final static int DB_CREATE_TABLE = 0;
    public final static int DB_INSERT = 1;
    public final static int DB_DELETE = 2;
    public final static int DB_UPDATE = 3;
    public final static int DB_QUERY = 4;
    public final static int DB_REPLACE = 5;
    public final static int DB_BATCH_INSERT = 6;
    public final static int DB_ERROR = -1;

    private static DBManager dbManager;
    public DBSQLiteOpenHelper openHelper;

    private int queryId = 0;
    private String insertError = "";
    private String deleteError = "";
    private String updateError = "";
    private String queryError = "";
    private String replaceError = "";
    private String createError = "";
    private String dropError = "";


    public DBManager(Context context) {
        super(context);
        openHelper = new DBSQLiteOpenHelper();

    }

    public static DBManager getInstance(Context context) {
        mContext = context;
        if (dbManager == null) {
            return new DBManager(mContext);
        }
        return dbManager;
    }

    private synchronized boolean dbCraeteTable(String sql) {
        String[] tableName = DBDaoUtils.parseCreareTableNameSQl(sql);

        boolean isCreate = false;
        for (int i = 0; i < tableName.length; i++) {
            try {
                dbUtils.getDatabase().execSQL(tableName[i]);
                isCreate = openHelper.tableIsExist(dbUtils, DBDaoUtils.getCreateTableName(tableName[i]));
                return isCreate;
            } catch (Exception e) {
                e.printStackTrace();
                createError = e.getMessage();
                return false;
            }
        }
        return isCreate;
    }

    private synchronized boolean dbDropTable(String sql) {
        String[] tableName = DBDaoUtils.parseDropTableNameSQl(sql);
        boolean isExitNew = false;
        boolean isDrop = false;
        try {
            for (int i = 0; i < tableName.length; i++) {

                dbUtils.getDatabase().execSQL(tableName[i]);
                isExitNew = openHelper.tableIsExist(dbUtils, DBDaoUtils.getDropTableName(tableName[i]));
                if (!isExitNew) {
                    isDrop = true;
                } else {
                    isDrop = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            dropError = e.getMessage();
            isDrop = false;
        }
        return isDrop;
    }

    private synchronized long dbInsert(String sql) {
        long insertId = 0L;
        try {
            SQLiteStatement statement = dbUtils.getDatabase().compileStatement(sql);
            insertId = statement.executeInsert();
        } catch (Exception e) {
            e.printStackTrace();
            insertError = e.getMessage();
            insertId = -2;
        }
        return insertId;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public long dbBatchInsert(SQLiteStatement statement, String sql){
        long insertId = 0L;
        try {
            if(DatabaseUtils.getSqlStatementType(sql)!=DatabaseUtils.STATEMENT_ATTACH){
                 insertId=statement.executeInsert();
            }
        }catch (Exception e){
            insertError = e.getMessage();
            insertId=-2;
        }finally {
            if(statement!=null){
                statement.close();
            }
        }
        return insertId;
    }

    private List<String> dbBatchInsert(List<String> sql, String json) {
        List<String> batchList = new ArrayList<>();
        SQLiteDatabase database = dbUtils.getDatabase();
        try {
        database.beginTransaction();
        if(sql.size()>0 && sql.get(0).contains("native_h5_room")){
            System.out.println("img");
        }
        for (String str : sql) {
            try {
                database.execSQL(str);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        String batchInsert="{\"container\":\"db\",\"containerData\":{\"code\":\"batchInsertRes\",\"data\":{\"primaryKey\":\"1\",\"resultCode\":\"1\",\"resultMsg\":\"success\"}},\"seq\":\"db8bc4bc-3c6a-4046-9c45-b104e9bd7336\"}";
        batchList.add(batchInsert);
        database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            database.endTransaction();
        }
        return batchList;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private synchronized int dbDelete(String sql) {
        int deleteId = 0;
        try {
            SQLiteStatement statement = dbUtils.getDatabase().compileStatement(sql);
            deleteId = statement.executeUpdateDelete();
        } catch (Exception e) {
            e.printStackTrace();
            deleteError = e.getMessage();
            deleteId = -1;
        }
        return deleteId;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public synchronized int dbUpdate(String sql) {
        int updateId = 0;
        try {
            SQLiteStatement statement = dbUtils.getDatabase().compileStatement(sql);
            updateId = statement.executeUpdateDelete();
        } catch (Exception e) {
            e.printStackTrace();
            updateError = e.getMessage();
            updateId = -1;
        }
        return updateId;
    }

    //Cursor cursor = db.query("user", new String[]{"id","name"}, "id=?", new String[]{"1"}, null, null, null);
    private synchronized Cursor dbQuery(String sql) {
        Cursor cursor = null;
        queryId = 0;
        try {
            cursor = dbUtils.getDatabase().rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            queryId = -1;
            queryError = e.getMessage();
        }
        return cursor;
    }


    private synchronized long dbReplace(String table, ContentValues values) {
        long replaceId = 0;
        try {
            replaceId = dbUtils.getDatabase().replace(table, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            replaceError = e.getMessage();
            replaceId = -1;
        }
        return replaceId;
    }

    public String getDBDealData(String json) {
        int code = DBDao.DBDaoParseCode(json);
        DBDao dbDao = null;
        String sql = "";
        String str = "";
        if (!ReplaceDao.getIsReplaceData(json)) {
            dbDao = DBDao.parseJson(json);
            sql = dbDao.getContainerData().getData().getSql();
        }
        switch (code) {
            case DB_CREATE_TABLE:
                String[] tableName = DBDaoUtils.parseCreareTableNameSQl(sql);
                for (int i = 0; i < tableName.length; i++) {
                    if (tableName[i].startsWith("create") || tableName[i].contains("if not exists")) {
                        boolean isExit = dbCraeteTable(sql);
                        if (isExit) {
                            str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "1", "success", "1");
                        } else if (!isExit && createError.equals("")) {
                            str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "0", "fail", "0");
                        } else if (!isExit && !TextUtils.isEmpty(createError)) {
                            str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "-1", createError, "0");
                        }
                    } else if (tableName[i].startsWith("DROP") || tableName[i].contains("IF EXISTS")) {
                        boolean isDrop = dbDropTable(sql);
                        if (isDrop) {
                            str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "1", "success", "1");
                        } else if (!isDrop && dropError.equals("")) {
                            str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "0", "fail", "0");
                        } else if (!isDrop && !TextUtils.isEmpty(dropError)) {
                            str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "-1", dropError, "0");
                        }
                    }
                }
                break;
            case DB_INSERT:
                long rawId = dbInsert(sql);
                if (rawId > 0) {
                    str = InsertDao.getInsertJson(json, "1", "success", rawId + "");
                } else if (rawId == -1) {
                    str = InsertDao.getInsertJson(json, "0", "fail", "0");
                } else if (rawId == -2) {
                    str = InsertDao.getInsertJson(json, "-1", insertError, "0");
                }
                break;
            case DB_DELETE:
                int deleteId = dbDelete(sql);
                if (deleteId > 0) {
                    str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "1", "success", deleteId + "");
                } else if (deleteId == 0) {
                    str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "0", "fail", "0");
                } else if (deleteId == -1) {
                    str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "-1", deleteError, "0");
                }
                break;
            case DB_UPDATE:
                int updateId = dbUpdate(sql);
                if (updateId > 0) {
                    str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "1", "success", updateId + "");
                } else if (updateId == 0) {
                    str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "0", "fail", "0");
                } else if (updateId == -1) {
                    str = CreateTableOrDeleteOrUpdateDao.getCreateTableOrDeleteJson(json, "-1", updateError, "0");
                }
                break;
            case DB_QUERY:
                Cursor cursor = dbQuery(sql);
                if (cursor != null && queryId != -1) {
                    str = QueryDao.getQueryJson(json, cursor);
                } else if (queryId == -1 && cursor == null) {
                    str = QueryDao.getQueryJson(json, queryId, queryError);
                } else if (queryId != -1 && cursor == null) {
                    str = QueryDao.getQueryJson(json, cursor);
                }
                break;
            case DB_REPLACE:
                String table = "";
                long replaceId = 0;
                try {
                    table = new JSONObject(json).getJSONObject("containerData").getJSONObject("data").getString("tableName");
                    ContentValues value = ReplaceDao.getReplaceData(json);
                    replaceId = dbReplace(table, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (replaceId > 0) {
                    str = InsertDao.getReplacedJson(json, "1", "success", replaceId + "");
                } else if (replaceId == 0) {
                    str = InsertDao.getReplacedJson(json, "0", "fail", "0");
                } else if (replaceId == -1) {
                    str = InsertDao.getReplacedJson(json, "-1", replaceError, "0");
                }
                break;
            case DB_BATCH_INSERT:
                List<String> batchInsertRes = dbBatchInsert(BatchInsertDao.obtainBatchSqlData(json), json);
                for (String s : batchInsertRes) {
                    str = s;
                }
                break;
            case DB_ERROR:
                Log.e("out", "=======json is error======");
                break;
        }
        return str;
    }
}
