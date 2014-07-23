package com.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 管理数据库的工具类
 * 
 * @author Administrator
 * 
 */
public class DBSQLiteHelper extends SQLiteOpenHelper {

	public DBSQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBSQLiteHelper(Context context) {
		super(context, "db_mark.db", null, 1);
	}

	// 当第一次使用对象时，自动调用且只调用一次,该方法一般用于创建数据库的表结构
	@Override
	public void onCreate(SQLiteDatabase db) {

		// 1.創建users表，mark表，step表
		String sql = "create table users (uid integer primary key autoincrement, uname varchar(20), upwd varchar(20))";
		// 執行sql語句
		db.execSQL(sql);

		sql = "create table mark (mid integer primary key autoincrement, mtitle varchar(50), mcount integer, mdesc text, lastcontent text, lasttime date, uid integer)";
		db.execSQL(sql);

		sql = "create table step(sid integer primary key autoincrement, scontent text, slastTime date, mid integer)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
