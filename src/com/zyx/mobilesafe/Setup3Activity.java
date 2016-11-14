package com.zyx.mobilesafe;


import com.zyx.mobilesafe.utils.ToastUtils;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


public class Setup3Activity extends SetupBaseActivity{
	private EditText et_phone_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup3);
		
	et_phone_number=(EditText)findViewById(R.id.et_phone_number);
	//回显操作
	if(!sp.getString("phone", "").equals(null)){
		et_phone_number.setText(sp.getString("phone", ""));
	}
	}
	


	@Override
	public void next_click() {
		String phone_number=et_phone_number.getText().toString().trim();
		if(TextUtils.isEmpty(phone_number)){
			ToastUtils.showToast(getApplicationContext(), "请输入安全手机号");
			return;
		}else{
			//保存到参数
			Editor edit=sp.edit();
			edit.putString("phone", phone_number);
			edit.commit();
		}
		//跳转到第四个界面
		Intent intent=new Intent(this,Setup4Activity.class);
		startActivity(intent);
		 finish();
		  //平移动画
		   overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);

			
	}

	@Override
	public void pre_click() {
		//跳转到第二个界面
		Intent intent=new Intent(this,Setup2Activity.class);
		startActivity(intent);
		 finish();
		  //平移动画
		   overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);

		
	}


/**
 * 选择联系人按钮点击事件
 */
	public void selectContact(View v){
		//跳转到选择联系人界面
		Intent intent=new Intent(this,ContactActivity.class);
		//startActivityForResult 当现在的activity退出的时候回调用之前Activity的onActivityResult方法
		startActivityForResult(intent, 0);
		
		
	}
/**
 * 界面回来执行该方法
 */
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	super.onActivityResult(requestCode, resultCode, data);
	if(data!=null){
		//接受选择联系人传递过来的数据
		String phone_num=data.getStringExtra("num");//点击返回会出现空指针异常
		//将得到的值设置给输入框控件
		et_phone_number.setText(phone_num);
		
	}
	
	
}

}
