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
 * 主界面
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

		// 1.获取登陆时保存的用户信息
		SharedPreferences sp = this.getSharedPreferences("usersInfo",
				Context.MODE_WORLD_READABLE);
		String uname = sp.getString("uname", "我");
		int uid = sp.getInt("uid", 0);

		// 2.获取标题组件，更改显示内容
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle_index);
		txtTitle.setText(uname + "的事件");

		// 获取菜单按钮
		ImageButton btnMenu = (ImageButton) findViewById(R.id.btnMenu_index);
		btnMenu.setOnClickListener(new OnClickListener() {

			// 弹出自定义菜单界面
			@Override
			public void onClick(View v) {

				// 加载自定义菜单界面
				View menu_view = LayoutInflater.from(IndexActivity.this)
						.inflate(R.layout.mark_menu, null);

				// 加载已定义的尺寸
				Resources res = getResources();
				int menu_width = res
						.getDimensionPixelSize(R.dimen.mark_menu_width);
				int menu_height = res
						.getDimensionPixelSize(R.dimen.mark_menu_height);

				// 定义popupwindow
				popWin = new PopupWindow(menu_view, menu_width, menu_height,
						true);
				// 设置动画
				popWin.setAnimationStyle(R.style.menu_in_out);

				// 设置当按下任意位置，让弹出的菜单界面消失
				menu_view.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (popWin != null && popWin.isShowing()) {
							popWin.dismiss(); // 消失
							popWin = null;
						}

						return true;
					}
				});

				// 显示弹出菜单
				popWin.showAsDropDown(v);
			}
		});

		// 获取当前用户下的所有mark
		MarkDAO markDAO = new MarkDAO(IndexActivity.this);
		list = markDAO.findAllByUid(uid);
		// System.out.println(list.size() + "------" + uid);
		// 将list集合中的数据放入到lv控件中
		// 获取lv控件
		ListView lv = (ListView) findViewById(R.id.lv_index);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Mark mark = list.get(position);
				
				//打开具体的stepActivity
				Intent intent = new Intent(IndexActivity.this, StepActivity.class);
				//给StepActivity传递markid
				intent.putExtra("mid", mark.getMid());
				startActivity(intent);
				IndexActivity.this.finish();
			}

		});
		// 创建adapter将list集合中的数据放入到lv控件中
		BaseAdapter adapter = new BaseAdapter() {

			// 每一条记录的内容如何显示
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// 获取每一个数据
				Mark mark = list.get(position);

				// 加载每一个item显示的布局文件
				View view = LayoutInflater.from(
						IndexActivity.this.getBaseContext()).inflate(
						R.layout.mark_list_item, null);

				// 获取mark中的属性值并在view中显示
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

			// 每一个记录的id
			@Override
			public long getItemId(int position) {
				return position;
			}

			// 每一个item需要的具体数据
			@Override
			public Object getItem(int position) {
				return list.get(position);
			}

			// list集合中有多少个元素，lv中就有多少个
			@Override
			public int getCount() {
				return list.size();
			}
		};

		// 将adapter填充到lv控件中
		lv.setAdapter(adapter);
	}

	// 修改密码的方法
	public void btnUpdatePwd(View v) {
		// 1.关闭弹出的菜单
		if (popWin != null && popWin.isShowing()) {
			popWin.dismiss(); // 消失
			popWin = null;
		}
		// 转向到修改界面
		Intent intent = new Intent(this, UpdatePwdActivity.class);
		startActivity(intent);

		// Toast.makeText(this, "绑定成功", Toast.LENGTH_LONG).show();
	}

	// 创建mark
	public void btnCreateMark(View v) {
		// 1.关闭弹出的菜单
		if (popWin != null && popWin.isShowing()) {
			popWin.dismiss(); // 消失
			popWin = null;
		}

		// 2.转向到mark界面
		Intent intent = new Intent(IndexActivity.this, MarkActivity.class);
		startActivity(intent);
		IndexActivity.this.finish();
	}
}
