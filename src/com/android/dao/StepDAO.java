package com.android.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.domain.Step;

public class StepDAO extends DBSQLiteHelper {

	public StepDAO(Context context) {
		super(context);
	}

	public List<Step> findAllByMid(int mid) {

		List<Step> list = new ArrayList<Step>();
		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "select * from step where mid = ?";

		Cursor cursor = db.rawQuery(sql, new String[] { mid + "" });

		while (cursor.moveToNext()) {
			Step step = new Step();
			step.setSid(cursor.getInt(0));
			step.setScontent(cursor.getString(1));
			step.setSlastTime(cursor.getString(2));
			step.setMid(cursor.getInt(3));
			list.add(step);
		}
		cursor.close();
		db.close();
		return list;
	}
}
