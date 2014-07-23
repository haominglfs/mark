package com.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * �������ݿ�Ĺ�����
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

	// ����һ��ʹ�ö���ʱ���Զ�������ֻ����һ��,�÷���һ�����ڴ������ݿ�ı�ṹ
	@Override
	public void onCreate(SQLiteDatabase db) {

		// 1.����users��mark��step��
		String sql = "create table users (uid integer primary key autoincrement, uname varchar(20), upwd varchar(20))";
		// ����sql�Z��
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
