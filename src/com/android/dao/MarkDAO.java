package com.android.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.domain.Mark;

public class MarkDAO extends DBSQLiteHelper {

	public MarkDAO(Context context) {
		super(context);
	}

	// 保存mark
	public void saveMark(Mark mark) {

		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "insert into mark values(null,?,?,?,?,?,?)";

		db.execSQL(
				sql,
				new Object[] { mark.getMtitle(), mark.getMcount(),
						mark.getMdesc(), mark.getLastcontent(),
						mark.getLasttime(), mark.getUid() });
	}

	// 获取当前用户下的所有mark
	public List<Mark> findAllByUid(int uid) {

		List<Mark> list = new ArrayList<Mark>();

		String sql = "select * from mark where uid = ?";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(sql, new String[] { uid + "" });

		while (cursor.moveToNext()) {
			Mark mark = new Mark();
			mark.setMid(cursor.getInt(0));
			mark.setMtitle(cursor.getString(1));
			mark.setMcount(cursor.getInt(2));
			mark.setMdesc(cursor.getString(3));
			mark.setLastcontent(cursor.getString(4));
			mark.setLasttime(cursor.getString(5));
			mark.setUid(cursor.getInt(6));
			list.add(mark);
		}

		cursor.close();
		db.close();
		return list;
	}

}
