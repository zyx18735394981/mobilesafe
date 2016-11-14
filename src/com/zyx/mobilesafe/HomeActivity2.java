package com.zyx.mobilesafe;


import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caojiang.life.HomeActivity;
import com.zyx.mobilesafe.utils.ToastUtils;

public class HomeActivity2 extends Activity{
	private SharedPreferences sp;
	private GridView gv_home_gridview;
	private AlertDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//定义参数
		sp = getSharedPreferences("config", MODE_PRIVATE);
	//加载布局文件
		setContentView(R.layout.activity_home);
	    gv_home_gridview=(GridView)findViewById(R.id.gv_home_gridview);
	    gv_home_gridview.setAdapter(new MyAdapter());
	   
	    gv_home_gridview.setOnItemClickListener(new   OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//position  条目的位置
				//根据条目的位置 判断用户点了哪个位置
				
				switch (position) {
			
				case 0:
					//进入手机防盗
				    //弹出对话框，输入密码，若是第一次输入，需要两个提示框，输入密码和确认密码
                    //若第二次输入，只需要一个提示框，请输入密码
				if(TextUtils.isEmpty(sp.getString("password", ""))){
					//设置密码
					
					showSetPassDialog();
					
				}else{
					//输入密码
					showEnterPassword();
					
				}
				
					
					
					break;
				case 1:
					// 跳转到通信卫士模块
					
					Intent intent2=new Intent(getApplicationContext(), BlackNumberActivity.class);
					startActivity(intent2);
					break;
				case 2:
					//软件管理模块SoftManagerActivity
					Intent intent3=new Intent(getApplicationContext(), SoftManagerActivity.class);
					startActivity(intent3);
					break;
				case 3:
					//进程管理模块TaskActivity
					Intent intent4=new Intent(getApplicationContext(), TaskActivity.class);
					startActivity(intent4);
					break;
				case 4:
					ToastUtils.showToast(getApplicationContext(), "正在开发中，敬请期待~");

					break;
				case 5:
					//手机杀毒模块TaskActivity
					Intent intent6=new Intent(getApplicationContext(), BingduActivity.class);
					startActivity(intent6);
					break;
				case 6:
					//缓存清理模块TaskActivity
					Intent intent7=new Intent(getApplicationContext(), ClearActivity.class);
					startActivity(intent7);
					break;
				case 7:
					//高级工具模块
					 
					Intent intent8=new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(intent8);
					break;
					
				case 8:
					//进入设置中心
					Intent intent=new Intent(HomeActivity2.this,SettingActivity.class);
					startActivity(intent);//跳转到设置中心界面
					break;
				}
				
			}

		} );
	    
	    
	    
	    
	 // 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

		// 将广告条加入到布局中
		adLayout.addView(adView);
	    
	}
	


	private class MyAdapter extends BaseAdapter{
//对数据初始化
		int[] imageId = { R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
				R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
				R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings };
		String[] names = { "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理",
				"高级工具", "设置中心" };
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 9;
		}
//获取条目对应的数据
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
//获取条目的id
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
//设置条目的样式
		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			
			View view= View.inflate(getApplicationContext(), R.layout.item_home, null);
			//每个条目的样式都不一样  初始化控件  去设置控件的值
			ImageView iv_itemhome_icon=(ImageView)view.findViewById(R.id.iv_itemhome_icon);
			
			TextView tv_itemhome_text=(TextView)view.findViewById(R.id.tv_itemhome_text);
			//给图片赋值
			iv_itemhome_icon.setImageResource(imageId[position]);
			//给文本赋值
			tv_itemhome_text.setText(names[position]);
			return view;
		}
		
		
		
	}


	
	/**
	 * 输入密码
	 */
				private void showEnterPassword() {
					AlertDialog.Builder builder=new Builder(this);
					builder.setCancelable(false);
				View view=View.inflate(getApplicationContext(), R.layout.dialog_inputpassword, null);
				

				   builder.setView(view);
				   
					
			final EditText input_password=(EditText)view.findViewById(R.id.et_input_password);
			Button btn_ok=(Button)view.findViewById(R.id.btn_ok);
			Button btn_cancle=(Button)view.findViewById(R.id.btn_cancle);
			
			btn_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//点击确定将输入的密码跟参数里的密码进行比对
				String inputPwd=input_password.getText().toString();
				//取参数里面的密码
				String sp_pwd=sp.getString("password", "");
				if(inputPwd.equals(sp_pwd)){
					//进入主界面
					dialog.dismiss();
					Intent intent=new Intent(getApplicationContext(),LostActivity.class);
					startActivity(intent);
					
				}else{
					//密码不正确
					ToastUtils.showToast(getApplicationContext(), "密码不正确！");
					return;
					
				}
					
				}
			});
			
			btn_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//点击取消
					dialog.dismiss();
		
					
				}
			});
			dialog = builder.create();
			dialog.show();	
				}
	/**
	 * 设置密码
	 */
				private void showSetPassDialog() {
					
			AlertDialog.Builder builder=new Builder(this);
			     
			
					//设置对话框不能取消
					builder.setCancelable(false);
					//加载项界面
					
				View view=View.inflate(this, R.layout.dialog_setpassword, null);
				builder.setView(view);
				   
			
			   
				//找控件
		final	EditText et_setpassword_password=(EditText)view.findViewById(R.id.et_setpassword_password);
		final	EditText et_setpassword_confrim=(EditText)view.findViewById(R.id.et_setpassword_confrim);
			Button btn_ok=(Button)view.findViewById(R.id.btn_ok);
			Button btn_cancle=(Button)view.findViewById(R.id.btn_cancle);
			
			//给确定设置点击事件---判断不为空 一致 把password保存到参数里面
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd=et_setpassword_password.getText().toString().trim();
				String check_pwd=et_setpassword_confrim.getText().toString().trim();
				if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(check_pwd)){
					
					if(pwd.equals(check_pwd)){
						//将密码保存到参数里面
						
					Editor edit=sp.edit();
					edit.putString("password", pwd);
					edit.commit();//提交
					
					dialog.dismiss();
					ToastUtils.showToast(getApplicationContext(), "密码设置成功");
					
				
						
					}else{
						ToastUtils.showToast(getApplicationContext(), "两次密码不一致");
						return;
					}
					
					
					
				}else{
					ToastUtils.showToast(getApplicationContext(), "请输入密码");
				     return;
				 }
			}
		});
			
			btn_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//点击取消，对话框消失
					dialog.dismiss();
					
				}
			});	
			   dialog = builder.create();
			   dialog.show();
				
				}
	

}
