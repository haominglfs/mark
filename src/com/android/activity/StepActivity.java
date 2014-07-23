package com.android.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.dao.StepDAO;
import com.android.domain.Step;
import com.android.mark_demo.R;

public class StepActivity extends Activity {

	PopupWindow popWin;
	List<Step> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);

		// ��ȡstep�˵���ť
		ImageButton btnMenu = (ImageButton) findViewById(R.id.btnMenu_step);
		btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ���ؽ���
				View menu_view = LayoutInflater.from(
						StepActivity.this.getBaseContext()).inflate(
						R.layout.step_menu, null);

				// ���ض���ĳߴ�
				Resources rs = getResources();
				int menu_width = rs
						.getDimensionPixelSize(R.dimen.step_menu_width);
				int menu_height = rs
						.getDimensionPixelSize(R.dimen.step_menu_height);

				// ����PopWin
				popWin = new PopupWindow(menu_view, menu_width, menu_height);

				// ���ö���
				popWin.setAnimationStyle(R.style.menu_in_out);

				// ��ʧ
				menu_view.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (popWin != null && popWin.isShowing()) {
							popWin.dismiss();
							popWin = null;
						}
						return true;
					}
				});

				// ��ʾ
				popWin.showAsDropDown(v);

			}
		});

		// ��ѯ��ȡ��ǰmark�µ�����step
		// ��ȡ���ݵ�markid
		Intent intent = getIntent();
		int mid = intent.getIntExtra("mid", 0);
		System.out.println("step ---- " + mid);
		//
		StepDAO stepDAO = new StepDAO(StepActivity.this);
		//
		list = stepDAO.findAllByMid(mid);

		// ��ȡlv�ؼ�
		ListView lv = (ListView) findViewById(R.id.lv_step);

		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Step step = list.get(position);

				View view = LayoutInflater.from(
						StepActivity.this.getBaseContext()).inflate(
						R.layout.step_list_item, null);

				TextView txtDate = (TextView) view
						.findViewById(R.id.txtDate_step_list_item);
				txtDate.setText(step.getSlastTime());

				TextView txtContent = (TextView) view
						.findViewById(R.id.txtContent_step_list_item);
				txtContent.setText(step.getScontent());

				return view;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return list.get(position);
			}

			@Override
			public int getCount() {
				return list.size();
			}
		};

		lv.setAdapter(adapter);
	}

}
