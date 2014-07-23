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
 * 注册界面
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// 1.获取注册的按钮
		ImageButton btnRegister = (ImageButton) findViewById(R.id.btnSubmit_register);

		// 2.绑定单击事件
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 3.获取注册的信息
				// 获取用户名
				String uname = ((EditText) findViewById(R.id.txtUname_register))
						.getText().toString();
				// 获取密码
				String upwd = ((EditText) findViewById(R.id.txtPwd_register))
						.getText().toString();
				// 获取重复密码
				String aginPwd = ((EditText) findViewById(R.id.txtAginPwd_register))
						.getText().toString();

				// 4.判断密码是否重复
				if (upwd.equals(aginPwd)) {
					// 封装用户信息
					Users users = new Users();
					users.setUname(uname);
					users.setUpwd(upwd);

					// 保存用户信息
					UsersDAO usersDAO = new UsersDAO(RegisterActivity.this);
					usersDAO.saveUsers(users);

					// 提示保存成功
					Toast.makeText(RegisterActivity.this, "保存成功",
							Toast.LENGTH_LONG).show();

					// 跳转到登陆页面
					Intent intent = new Intent(RegisterActivity.this,
							LoginActivity.class);
					startActivity(intent);

					RegisterActivity.this.finish();

				} else {
					// 错误提示
					Toast.makeText(RegisterActivity.this, "两次密码不一致",
							Toast.LENGTH_LONG).show();
					// 清空原信息
					((EditText) findViewById(R.id.txtPwd_register)).setText("");
					((EditText) findViewById(R.id.txtAginPwd_register))
							.setText("");
				}
			}
		});

		// 获取取消按钮
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
