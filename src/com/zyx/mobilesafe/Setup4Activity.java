package com.zyx.mobilesafe;

import com.zyx.mobilesafe.utils.ToastUtils;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Setup4Activity extends SetupBaseActivity{
	
	private CheckBox cb_box;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_setup4);
		cb_box=(CheckBox)findViewById(R.id.cb_box);
		//回显操作
		if(sp.getBoolean("checkbox", false)==true){
			//开启
			cb_box.setText("你已经开启了防盗保护");
			cb_box.setChecked(true);
		}else{
		
			//关闭
			cb_box.setText("你没有开启防盗保护");
			cb_box.setChecked(false);
		}
	    
	    //给checkbox添加点击事件
		//状态改变时调用
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//buttonView checkbox
			//isChecked 是否勾选 改变之后的状态 点击之后的值
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//根据checkbox的状态设置信息
				Editor edit=sp.edit();	
				if(isChecked==true){
					//开启
					cb_box.setText("你已经开启了防盗保护");
					cb_box.setChecked(true);
					//保存到参数
					edit.putBoolean("checkbox", true);
					
				}else{
					//关闭
					cb_box.setText("你没有开启防盗保护");
					cb_box.setChecked(false);
					//保存到参数
					edit.putBoolean("checkbox", false);
					
				}
				edit.commit();
				
			}
		});
	}
	




	@Override
	public void next_click() {
		ToastUtils.showToast(this, "设置完成");
		//往参数里面保存false
	Editor edit=sp.edit();
	edit.putBoolean("first", false);
	edit.commit();
		//跳转到防盗界面
	Intent intent=new Intent(this,LostActivity.class);
	startActivity(intent);
	finish();
		
		
	}


	@Override
	public void pre_click() {
		//跳转到第3个界面
				Intent intent=new Intent(this,Setup3Activity.class);
				startActivity(intent);
				 finish();
				  //平移动画
				   overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);

			
		
	}

}
