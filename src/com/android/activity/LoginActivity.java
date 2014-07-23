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
 * ��½����
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����login����
		setContentView(R.layout.activity_login);

		// 1.��ȡ��½��ť
		ImageButton btnLogin = (ImageButton) this
				.findViewById(R.id.btnSubmit_login);

		// 2.�󶨵����¼�
		btnLogin.setOnClickListener(new OnClickListener() {

			// 3.�������¼�
			@Override
			public void onClick(View v) {
				// 4.��ȡ�û���������
				String uname = ((EditText) findViewById(R.id.txtUname_login))
						.getText().toString();
				String upwd = ((EditText) findViewById(R.id.txtPwd_login))
						.getText().toString();

				// 5.�ж��û��������Ƿ���ȷ
				// ����UserDAO
				UsersDAO usersDAO = new UsersDAO(LoginActivity.this);

				// 6.ִ�в�ѯ��Ϣ
				Users users = usersDAO.findByUnameAndPwd(uname, upwd);

				// 7.�ж�ִ�н��
				if (users != null) {
					// 8.�ɹ�
					// �����½��Ϣ
					SharedPreferences pre = LoginActivity.this
							.getSharedPreferences("usersInfo",
									Context.MODE_WORLD_READABLE);
					SharedPreferences.Editor editor = pre.edit();
					editor.putInt("uid", users.getUid());
					editor.putString("uname", users.getUname());

					editor.commit();

					// 9.����������
					// System.out.println("---------����������---------");
					Intent intent = new Intent(LoginActivity.this,
							IndexActivity.class);
					startActivity(intent);

					// 10.�رյ�ǰ����
					LoginActivity.this.finish();

				} else {
					// 9.ʧ��
					// ��ʾ��Ϣ
					Toast.makeText(LoginActivity.this, "�û����������",
							Toast.LENGTH_LONG).show();

					// ���ԭ��������û�������
					((EditText) findViewById(R.id.txtUname_login)).setText("");
					((EditText) findViewById(R.id.txtPwd_login)).setText("");
				}
			}
		});

		// ��ȡע�ᰴť
		ImageButton btnRegister = (ImageButton) findViewById(R.id.btnRegister_login);
		// �󶨵����¼�
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.����ע�����
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				// �رյ�½����
				LoginActivity.this.finish();

			}
		});

	}

}
