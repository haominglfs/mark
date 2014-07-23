package com.android.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.domain.Users;

public class UsersDAO extends DBSQLiteHelper {

	public UsersDAO(Context context) {
		super(context);
	}

	// ��ѯ�û��������Ƿ���ȷ
	public Users findByUnameAndPwd(String uname, String upwd) {

		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "select * from users where uname = ? and upwd = ?";

		Cursor cursor = db.rawQuery(sql, new String[] { uname, upwd });

		Users users = null;
		// �жϻ�ȡ��¼��Ϣ
		if (cursor.moveToNext()) {
			// ��װ��������Ϣ
			users = new Users();
			// ��װ��¼�еĵ�һ�У��±��0��ʼ
			users.setUid(cursor.getInt(0));
			users.setUname(cursor.getString(1));
			users.setUpwd(cursor.getString(2));
		}
		// �ر���Դ
		cursor.close();
		db.close();

		return users;

	}

	// �����û���Ϣ
	public void saveUsers(Users users) {

		SQLiteDatabase db = this.getWritableDatabase();

		String sql = "insert into users values(null, ?, ?)";

		db.execSQL(sql, new String[] { users.getUname(), users.getUpwd() });

	}

	// ����uid��ȡ�û�����
	public Users findById(int uid) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "select * from users where uid = ?";
		Cursor cursor = db.rawQuery(sql, new String[] { uid + "" });
		Users users = null;
		// �жϻ�ȡ��¼��Ϣ
		if (cursor.moveToNext()) {
			// ��װ��������Ϣ
			users = new Users();
			// ��װ��¼�еĵ�һ�У��±��0��ʼ
			users.setUid(cursor.getInt(0));
			users.setUname(cursor.getString(1));
			users.setUpwd(cursor.getString(2));
		}
		// �ر���Դ
		cursor.close();
		db.close();

		return users;

	}

	// �޸��û�����
	public void updateUsers(int uid, String newPwd) {

		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "update users set upwd = ? where uid = ?";
		db.execSQL(sql, new Object[] { newPwd, uid });
	}
}
