package com.android.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.dao.MarkDAO;
import com.android.domain.Mark;
import com.android.mark_demo.R;

/**
 * ����mark����
 * 
 * @author Administrator
 * 
 */
public class MarkActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_mark);

		// ��ȡ�ύ��ť
		ImageView imgAddMark = (ImageView) findViewById(R.id.imgSubmitMark_create_mark);
		imgAddMark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 1.��ȡ�������Ϣ
				String mtitle = ((EditText) findViewById(R.id.txtTitle_create_mark))
						.getText().toString();
				String mdesc = ((EditText) findViewById(R.id.txtDesc_create_mark))
						.getText().toString();

				// 2.��ȡ�û�id
				SharedPreferences sp = MarkActivity.this.getSharedPreferences(
						"usersInfo", Context.MODE_WORLD_READABLE);
				int uid = sp.getInt("uid", 0);

				// 3.��װ����
				Mark mark = new Mark();
				mark.setMtitle(mtitle);
				mark.setMcount(0);
				mark.setMdesc(mdesc);
				mark.setLastcontent("�ոմ���");
				mark.setLasttime(new SimpleDateFormat("yyyy-MM-dd").format(
						new Date()).toString());
				mark.setUid(uid);

				// 4.����mark
				MarkDAO markDAO = new MarkDAO(MarkActivity.this);
				markDAO.saveMark(mark);

				// 5.��ʾ������
				Toast.makeText(MarkActivity.this, "����ɹ�", Toast.LENGTH_SHORT)
						.show();

				// 6.ת��indexActivity
				Intent intent = new Intent(MarkActivity.this,
						IndexActivity.class);
				startActivity(intent);
				MarkActivity.this.finish();
			}
		});

	}
}
