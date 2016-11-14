package com.zyx.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostActivity extends Activity {
	private SharedPreferences sp;
	private TextView number;
	private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//如果第一次进入，则进入想到界面；若不是第一次进入，则直接进入防盗界面
		if(sp.getBoolean("first", true)){
			//进入向导界面
			Intent intent=new Intent(getApplication(),Setup1Activity.class);
			startActivity(intent);
			finish();
			
		}else{
			//加载防盗界面
			setContentView(R.layout.activity_lost);
			//找控件
		  number=(TextView)findViewById(R.id.tv_lostfind_safenum);
		
		  image=(ImageView)findViewById(R.id.tv_lostfind_protected);
			
			initData();
		}
		
	}
	/**
	 * 设置向导界面的安全号码到textview中
	 */
	private void initData() {
		//设置向导界面的安全号码到textview中
		String sp_num=sp.getString("phone", "110");
		number.setText(sp_num);
		//判断手机防盗是否开启
		boolean check=sp.getBoolean("checkbox", false);
		if(check==true){
			//开启--换图片
			image.setImageResource(R.drawable.lock);
			
		}else{
			//没有开启
			image.setImageResource(R.drawable.unlock);
		}
		
	}
	/**
	 * 重新进入向导界面
	 * 点击事件
	 */
	public void resetup(View v){
		Intent intent=new Intent(this,Setup1Activity.class);
		startActivity(intent);
		finish();
		
	}



}
