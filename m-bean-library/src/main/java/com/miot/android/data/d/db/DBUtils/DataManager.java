package com.miot.android.data.d.db.DBUtils;


import android.content.Context;

import com.lidroid.xutils.DbUtils;


/**
 * Created by Administrator on 2016/7/4 0004.
 */
public abstract class DataManager implements DbUtils.DbUpgradeListener {
	protected final Context context;

	public static Class<?> [] cazz=null;

	/**
	 * 数据库管理器
	 */
	public final DbUtils dbUtils;

	public DataManager(Context context) {
		this.context = context;
		DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(context);
		daoConfig.setDbVersion(11);
		daoConfig.setDbName(DBManager.DB_NAME);
		daoConfig.setDbUpgradeListener(this);
		this.dbUtils = DbUtils.create(daoConfig);
		dbUtils.configDebug(true);
	}

	@Override
	public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
		try {
			if (oldVersion <10) {
				if (cazz!=null){
					for (Class<?> className : cazz){
						db.dropTable(className);
						db.createTableIfNotExist(className);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 删除所有的表数据
	 */
	public void deleteAllTable() {
		try {
			dbUtils.beginTransaction();
//			for (Class<?> c : PublicApplication.classAllTables) {
//				dbUtils.createTableIfNotExist(c);
//				dbUtils.execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(dbUtils,
//						c, null));
//			}
			dbUtils.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally {
			dbUtils.endTransaction();
		}
	}
}

