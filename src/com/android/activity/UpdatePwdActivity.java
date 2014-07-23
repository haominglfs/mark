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

		// 获取取消按钮
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

		// 获取修改提交的按钮
		ImageButton btnSubmit = (ImageButton) findViewById(R.id.btnSubmit_update_pwd);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 1.获取用户输入的信息
				String oldPwd = ((EditText) findViewById(R.id.txtOldPwd_update_pwd))
						.getText().toString();
				String newPwd = ((EditText) findViewById(R.id.txtPwd_update_pwd))
						.getText().toString();
				String newAgainPwd = ((EditText) findViewById(R.id.txtAgainPwd_update_pwd))
						.getText().toString();

				// 2.非空校验

				// 3.判断oldpwd是否合适
				// 3.1获取用户id
				SharedPreferences sp = UpdatePwdActivity.this
						.getSharedPreferences("usersInfo",
								Context.MODE_WORLD_READABLE);
				int uid = sp.getInt("uid", 0);

				// 3.2根据用户id获取用户信息
				UsersDAO usersDAO = new UsersDAO(UpdatePwdActivity.this);
				Users users = usersDAO.findById(uid);

				// 3.3判断密码是否正确
				if (users.getUpwd().equals(oldPwd)) { // 密码输入正确

					// 4.判断输入的两次密码是否相同
					if (newPwd.equals(newAgainPwd)) {
						// 5.修改用户的密码
						usersDAO.updateUsers(uid, newPwd);
						// 6.提示修改成功
						Toast.makeText(UpdatePwdActivity.this, "密码修改成功",
								Toast.LENGTH_LONG).show();
						// 7.转向到indexactivity界面
						Intent intent = new Intent(UpdatePwdActivity.this,
								IndexActivity.class);
						startActivity(intent);
						UpdatePwdActivity.this.finish();

					} else {
						Toast.makeText(UpdatePwdActivity.this,
								"你输入两次的密码不相同,请重新输入", Toast.LENGTH_LONG).show();
					}

				} else {// 密码错误
					Toast.makeText(UpdatePwdActivity.this, "你输入的旧密码不正确,请重新输入",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}
}
