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
 * 登陆界面
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 加载login界面
		setContentView(R.layout.activity_login);

		// 1.获取登陆按钮
		ImageButton btnLogin = (ImageButton) this
				.findViewById(R.id.btnSubmit_login);

		// 2.绑定单击事件
		btnLogin.setOnClickListener(new OnClickListener() {

			// 3.处理单击事件
			@Override
			public void onClick(View v) {
				// 4.获取用户名，密码
				String uname = ((EditText) findViewById(R.id.txtUname_login))
						.getText().toString();
				String upwd = ((EditText) findViewById(R.id.txtPwd_login))
						.getText().toString();

				// 5.判断用户名密码是否正确
				// 创建UserDAO
				UsersDAO usersDAO = new UsersDAO(LoginActivity.this);

				// 6.执行查询信息
				Users users = usersDAO.findByUnameAndPwd(uname, upwd);

				// 7.判断执行结果
				if (users != null) {
					// 8.成功
					// 保存登陆信息
					SharedPreferences pre = LoginActivity.this
							.getSharedPreferences("usersInfo",
									Context.MODE_WORLD_READABLE);
					SharedPreferences.Editor editor = pre.edit();
					editor.putInt("uid", users.getUid());
					editor.putString("uname", users.getUname());

					editor.commit();

					// 9.启动主界面
					// System.out.println("---------启动主界面---------");
					Intent intent = new Intent(LoginActivity.this,
							IndexActivity.class);
					startActivity(intent);

					// 10.关闭当前界面
					LoginActivity.this.finish();

				} else {
					// 9.失败
					// 提示信息
					Toast.makeText(LoginActivity.this, "用户名密码错误",
							Toast.LENGTH_LONG).show();

					// 清空原来错误的用户名密码
					((EditText) findViewById(R.id.txtUname_login)).setText("");
					((EditText) findViewById(R.id.txtPwd_login)).setText("");
				}
			}
		});

		// 获取注册按钮
		ImageButton btnRegister = (ImageButton) findViewById(R.id.btnRegister_login);
		// 绑定单击事件
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.启动注册界面
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				// 关闭登陆界面
				LoginActivity.this.finish();

			}
		});

	}

}
