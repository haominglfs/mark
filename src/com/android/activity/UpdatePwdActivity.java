package com.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.dao.UsersDAO;
import com.android.domain.Users;
import com.android.mark_demo.R;

/**
 * 
 * @author Administrator
 * 
 */
public class UpdatePwdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pwd);

		// ��ȡȡ����ť
		ImageButton btnCancel = (ImageButton) findViewById(R.id.btnCancel_update_pwd);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(UpdatePwdActivity.this,
						IndexActivity.class);
				startActivity(intent);
				UpdatePwdActivity.this.finish();

			}
		});

		// ��ȡ�޸��ύ�İ�ť
		ImageButton btnSubmit = (ImageButton) findViewById(R.id.btnSubmit_update_pwd);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 1.��ȡ�û��������Ϣ
				String oldPwd = ((EditText) findViewById(R.id.txtOldPwd_update_pwd))
						.getText().toString();
				String newPwd = ((EditText) findViewById(R.id.txtPwd_update_pwd))
						.getText().toString();
				String newAgainPwd = ((EditText) findViewById(R.id.txtAgainPwd_update_pwd))
						.getText().toString();

				// 2.�ǿ�У��

				// 3.�ж�oldpwd�Ƿ����
				// 3.1��ȡ�û�id
				SharedPreferences sp = UpdatePwdActivity.this
						.getSharedPreferences("usersInfo",
								Context.MODE_WORLD_READABLE);
				int uid = sp.getInt("uid", 0);

				// 3.2�����û�id��ȡ�û���Ϣ
				UsersDAO usersDAO = new UsersDAO(UpdatePwdActivity.this);
				Users users = usersDAO.findById(uid);

				// 3.3�ж������Ƿ���ȷ
				if (users.getUpwd().equals(oldPwd)) { // ����������ȷ

					// 4.�ж���������������Ƿ���ͬ
					if (newPwd.equals(newAgainPwd)) {
						// 5.�޸��û�������
						usersDAO.updateUsers(uid, newPwd);
						// 6.��ʾ�޸ĳɹ�
						Toast.makeText(UpdatePwdActivity.this, "�����޸ĳɹ�",
								Toast.LENGTH_LONG).show();
						// 7.ת��indexactivity����
						Intent intent = new Intent(UpdatePwdActivity.this,
								IndexActivity.class);
						startActivity(intent);
						UpdatePwdActivity.this.finish();

					} else {
						Toast.makeText(UpdatePwdActivity.this,
								"���������ε����벻��ͬ,����������", Toast.LENGTH_LONG).show();
					}

				} else {// �������
					Toast.makeText(UpdatePwdActivity.this, "������ľ����벻��ȷ,����������",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}
}
