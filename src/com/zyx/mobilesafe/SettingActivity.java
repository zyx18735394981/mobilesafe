package com.zyx.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zyx.mobilesafe.service.BlackNumService;
import com.zyx.mobilesafe.ui.SettingView;
import com.zyx.mobilesafe.utils.AdressUtils;

public class SettingActivity extends Activity implements OnClickListener{
	private SettingView sv_setting_update;
	private SettingView sv_blacknum;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_setting);
	
	sv_blacknum=(SettingView)findViewById(R.id.sv_blacknum);
	
	
	
	//问题2:没有保存用户点击操作
		//一直都是默认的打开提示更新
		//保存状态--用参数
	//解决用参数保存！
	//name 文件名称  mode 权限
	 sp=getSharedPreferences("config", MODE_PRIVATE);
	
        sv_setting_update=(SettingView)findViewById(R.id.sv_setting);
        
       // sv_setting_update.setTitle("提示更新");
        //defValue  缺省值
        if(sp.getBoolean("update", true)){
        	 //初始化自定义控件中各个控件的值   
          //  sv_setting_update.setDes("打开提示更新");
            sv_setting_update.setCheckbox(true);
        	
        }else{
        	//sv_setting_update.setDes("关闭提示更新");
            sv_setting_update.setCheckbox(false);
        	
        }
       /**
         * 添加点击事件
         */
        sv_setting_update.setOnClickListener(this);     
	}
    //设置自定义组合控件的点击事件
    //问题1:点击checkbox发现描述信息没有改变,
	//原因:因为checkbox天生是有点击事件和获取焦点事件,
	//当点击checkbox,这个checkbox就会执行他自己的点击事件
    //而不会执行条目的点击事件
	//-------------------------------------------------
    //问题2:没有保存用户点击操作
	//一直都是默认的打开提示更新
	//保存状态--用参数
		@Override
		public void onClick(View v) {
			
			Editor edit=sp.edit();
			
			//更改状态
			//根据checkbox现有的状态来改变checkbox的状态
			if(sv_setting_update.isChecked()){
				//获取到checkbox现有的状态，点击后要变成相反的
				//关闭提示更新
				//sv_setting_update.setDes("关闭提示更新");
				sv_setting_update.setCheckbox(false);
				//将状态保存到参数里面
				edit.putBoolean("update", false);
			
			}else{
				//打开提示更新
				//sv_setting_update.setDes("打开提示更新");
				sv_setting_update.setCheckbox(true);
				//将状态保存到参数里面
				edit.putBoolean("update", true);
				
			}
			edit.commit();//保存到文件
		}

@Override
protected void onStart() {
	
	super.onStart();
	blacknum();
	
}
/**
 * 拦截黑名单操作
 */
private void blacknum() {
	// 动态的获取服务是否开启
	if (AdressUtils.isRunningService(
			"com.zyx.mobilesafe.service.BlackNumService",
			getApplicationContext())) {
		// 开启服务
		sv_blacknum.setCheckbox(true);
	} else {
		// 关闭服务
		sv_blacknum.setCheckbox(false);
	}
	sv_blacknum.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SettingActivity.this,
					BlackNumService.class);
			// 根据checkbox的状态设置描述信息的状态
			// isChecked() : 之前的状态
			if (sv_blacknum.isChecked()) {
				// 关闭提示更新
				stopService(intent);
				// 更新checkbox的状态
				sv_blacknum.setCheckbox(false);
			} else {
				// 打开提示更新
				startService(intent);
				sv_blacknum.setCheckbox(true);
			}
		}
	});
	
	
}
}
