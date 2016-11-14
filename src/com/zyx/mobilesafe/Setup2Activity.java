package com.zyx.mobilesafe;


import com.zyx.mobilesafe.ui.SettingView;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;


public class Setup2Activity extends SetupBaseActivity {
	private SettingView sv_setup2_sim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		 sv_setup2_sim=(SettingView)findViewById(R.id.sv_setup2_sim);
		 
		 //回显操作
		 if(TextUtils.isEmpty(sp.getString("sim", ""))){
			 sv_setup2_sim.setCheckbox(false);
		 }else{
			 sv_setup2_sim.setCheckbox(true);
			 
		 }
	
	  sv_setup2_sim.setOnClickListener(new OnClickListener() {
		//点击项 设置点击事件
		@Override
		public void onClick(View v) {
			//绑定SIM卡
			//根据checkbox的状态设置描述信息的状态
			//isChecked获取的是之前的状态 
			Editor edit=sp.edit();
			if(sv_setup2_sim.isChecked()){
				//解绑
				sv_setup2_sim.setCheckbox(false);
				
				edit.putString("sim", "");
				
				
			}else{
				//绑定SIM卡
				sv_setup2_sim.setCheckbox(true);
				TelephonyManager tm=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
				String sim=tm.getSimSerialNumber();//得到SIM的序列号
				//绑定SIM卡 就是保存到参数里面
			    
			    edit.putString("sim", sim);
			   
			}
			
			 edit.commit();
			
		}
	});
	
	}

	@Override
	public void next_click() {
		//跳转到第二个界面
				Intent intent=new Intent(this,Setup3Activity.class);
				startActivity(intent);
				 finish();
				  //平移动画
				   overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);

			
	}
	@Override
	public void pre_click() {
		//跳转到第二个界面
				Intent intent=new Intent(this,Setup1Activity.class);
				startActivity(intent);
				 finish();
				  //平移动画
				   overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);

			
		
	}
}
