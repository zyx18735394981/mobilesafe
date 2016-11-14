package com.zyx.mobilesafe;


import android.content.Intent;
import android.os.Bundle;


public class Setup1Activity extends SetupBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup1);
	}

	//下一页按钮点击事件
	@Override
	public void next_click() {
		//跳转到第二个界面
				Intent intent=new Intent(this,Setup2Activity.class);
				startActivity(intent);
			   finish();
			   //平移动画
			   overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);
	}

	@Override
	public void pre_click() {
		//第一个界面没有上一页
		
	}

}
