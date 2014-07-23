package com.android.activity;

import android.app.Activity;
import android.content.Intent;
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
 * ע�����
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// 1.��ȡע��İ�ť
		ImageButton btnRegister = (ImageButton) findViewById(R.id.btnSubmit_register);

		// 2.�󶨵����¼�
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 3.��ȡע�����Ϣ
				// ��ȡ�û���
				String uname = ((EditText) findViewById(R.id.txtUname_register))
						.getText().toString();
				// ��ȡ����
				String upwd = ((EditText) findViewById(R.id.txtPwd_register))
						.getText().toString();
				// ��ȡ�ظ�����
				String aginPwd = ((EditText) findViewById(R.id.txtAginPwd_register))
						.getText().toString();

				// 4.�ж������Ƿ��ظ�
				if (upwd.equals(aginPwd)) {
					// ��װ�û���Ϣ
					Users users = new Users();
					users.setUname(uname);
					users.setUpwd(upwd);

					// �����û���Ϣ
					UsersDAO usersDAO = new UsersDAO(RegisterActivity.this);
					usersDAO.saveUsers(users);

					// ��ʾ����ɹ�
					Toast.makeText(RegisterActivity.this, "����ɹ�",
							Toast.LENGTH_LONG).show();

					// ��ת����½ҳ��
					Intent intent = new Intent(RegisterActivity.this,
							LoginActivity.class);
					startActivity(intent);

					RegisterActivity.this.finish();

				} else {
					// ������ʾ
					Toast.makeText(RegisterActivity.this, "�������벻һ��",
							Toast.LENGTH_LONG).show();
					// ���ԭ��Ϣ
					((EditText) findViewById(R.id.txtPwd_register)).setText("");
					((EditText) findViewById(R.id.txtAginPwd_register))
							.setText("");
				}
			}
		});

		// ��ȡȡ����ť
		ImageButton btnCancel = (ImageButton) findViewById(R.id.btnCancel_register);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
				RegisterActivity.this.finish();
			}
		});
	}

}
