package com.android.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.domain.Users;

public class UsersDAO extends DBSQLiteHelper {

	public UsersDAO(Context context) {
		super(context);
	}

	// 查询用户名密码是否正确
	public Users findByUnameAndPwd(String uname, String upwd) {

		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "select * from users where uname = ? and upwd = ?";

		Cursor cursor = db.rawQuery(sql, new String[] { uname, upwd });

		Users users = null;
		// 判断获取记录信息
		if (cursor.moveToNext()) {
			// 封装并保存信息
			users = new Users();
			// 封装记录中的第一列，下标从0开始
			users.setUid(cursor.getInt(0));
			users.setUname(cursor.getString(1));
			users.setUpwd(cursor.getString(2));
		}
		// 关闭资源
		cursor.close();
		db.close();

		return users;

	}

	// 保存用户信息
	public void saveUsers(Users users) {

		SQLiteDatabase db = this.getWritableDatabase();

		String sql = "insert into users values(null, ?, ?)";

		db.execSQL(sql, new String[] { users.getUname(), users.getUpwd() });

	}

	// 根据uid获取用户对象
	public Users findById(int uid) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "select * from users where uid = ?";
		Cursor cursor = db.rawQuery(sql, new String[] { uid + "" });
		Users users = null;
		// 判断获取记录信息
		if (cursor.moveToNext()) {
			// 封装并保存信息
			users = new Users();
			// 封装记录中的第一列，下标从0开始
			users.setUid(cursor.getInt(0));
			users.setUname(cursor.getString(1));
			users.setUpwd(cursor.getString(2));
		}
		// 关闭资源
		cursor.close();
		db.close();

		return users;

	}

	// 修改用户密码
	public void updateUsers(int uid, String newPwd) {

		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "update users set upwd = ? where uid = ?";
		db.execSQL(sql, new Object[] { newPwd, uid });
	}
}
