package com.android.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.dao.MarkDAO;
import com.android.domain.Mark;
import com.android.mark_demo.R;

/**
 * ������
 * 
 * @author Administrator
 * 
 */
public class IndexActivity extends Activity {

	PopupWindow popWin;
	List<Mark> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		// 1.��ȡ��½ʱ������û���Ϣ
		SharedPreferences sp = this.getSharedPreferences("usersInfo",
				Context.MODE_WORLD_READABLE);
		String uname = sp.getString("uname", "��");
		int uid = sp.getInt("uid", 0);

		// 2.��ȡ���������������ʾ����
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle_index);
		txtTitle.setText(uname + "���¼�");

		// ��ȡ�˵���ť
		ImageButton btnMenu = (ImageButton) findViewById(R.id.btnMenu_index);
		btnMenu.setOnClickListener(new OnClickListener() {

			// �����Զ���˵�����
			@Override
			public void onClick(View v) {

				// �����Զ���˵�����
				View menu_view = LayoutInflater.from(IndexActivity.this)
						.inflate(R.layout.mark_menu, null);

				// �����Ѷ���ĳߴ�
				Resources res = getResources();
				int menu_width = res
						.getDimensionPixelSize(R.dimen.mark_menu_width);
				int menu_height = res
						.getDimensionPixelSize(R.dimen.mark_menu_height);

				// ����popupwindow
				popWin = new PopupWindow(menu_view, menu_width, menu_height,
						true);
				// ���ö���
				popWin.setAnimationStyle(R.style.menu_in_out);

				// ���õ���������λ�ã��õ����Ĳ˵�������ʧ
				menu_view.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (popWin != null && popWin.isShowing()) {
							popWin.dismiss(); // ��ʧ
							popWin = null;
						}

						return true;
					}
				});

				// ��ʾ�����˵�
				popWin.showAsDropDown(v);
			}
		});

		// ��ȡ��ǰ�û��µ�����mark
		MarkDAO markDAO = new MarkDAO(IndexActivity.this);
		list = markDAO.findAllByUid(uid);
		// System.out.println(list.size() + "------" + uid);
		// ��list�����е����ݷ��뵽lv�ؼ���
		// ��ȡlv�ؼ�
		ListView lv = (ListView) findViewById(R.id.lv_index);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Mark mark = list.get(position);
				
				//�򿪾����stepActivity
				Intent intent = new Intent(IndexActivity.this, StepActivity.class);
				//��StepActivity����markid
				intent.putExtra("mid", mark.getMid());
				startActivity(intent);
				IndexActivity.this.finish();
			}

		});
		// ����adapter��list�����е����ݷ��뵽lv�ؼ���
		BaseAdapter adapter = new BaseAdapter() {

			// ÿһ����¼�����������ʾ
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// ��ȡÿһ������
				Mark mark = list.get(position);

				// ����ÿһ��item��ʾ�Ĳ����ļ�
				View view = LayoutInflater.from(
						IndexActivity.this.getBaseContext()).inflate(
						R.layout.mark_list_item, null);

				// ��ȡmark�е�����ֵ����view����ʾ
				// title
				TextView txtTitle = (TextView) view
						.findViewById(R.id.txtTitle_list_item);
				String eTitle = mark.getMtitle() + "<font color='#ff0000'>"
						+ mark.getMcount() + "</font>";
				txtTitle.setText(Html.fromHtml(eTitle));

				// content
				TextView txtContent = (TextView) view
						.findViewById(R.id.txtContent_list_item);
				txtContent.setText(mark.getLastcontent());

				// date
				TextView txtDate = (TextView) view
						.findViewById(R.id.txtDate_list_item);
				txtDate.setText(mark.getLasttime());

				view.setId(position);

				return view;
			}

			// ÿһ����¼��id
			@Override
			public long getItemId(int position) {
				return position;
			}

			// ÿһ��item��Ҫ�ľ�������
			@Override
			public Object getItem(int position) {
				return list.get(position);
			}

			// list�������ж��ٸ�Ԫ�أ�lv�о��ж��ٸ�
			@Override
			public int getCount() {
				return list.size();
			}
		};

		// ��adapter��䵽lv�ؼ���
		lv.setAdapter(adapter);
	}

	// �޸�����ķ���
	public void btnUpdatePwd(View v) {
		// 1.�رյ����Ĳ˵�
		if (popWin != null && popWin.isShowing()) {
			popWin.dismiss(); // ��ʧ
			popWin = null;
		}
		// ת���޸Ľ���
		Intent intent = new Intent(this, UpdatePwdActivity.class);
		startActivity(intent);

		// Toast.makeText(this, "�󶨳ɹ�", Toast.LENGTH_LONG).show();
	}

	// ����mark
	public void btnCreateMark(View v) {
		// 1.�رյ����Ĳ˵�
		if (popWin != null && popWin.isShowing()) {
			popWin.dismiss(); // ��ʧ
			popWin = null;
		}

		// 2.ת��mark����
		Intent intent = new Intent(IndexActivity.this, MarkActivity.class);
		startActivity(intent);
		IndexActivity.this.finish();
	}
}
