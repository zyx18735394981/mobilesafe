package com.zyx.mobilesafe;


import android.content.Intent;
import android.os.Bundle;


public class Setup1Activity extends SetupBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup1);
	}

	//��һҳ��ť����¼�
	@Override
	public void next_click() {
		//��ת���ڶ�������
				Intent intent=new Intent(this,Setup2Activity.class);
				startActivity(intent);
			   finish();
			   //ƽ�ƶ���
			   overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);
	}

	@Override
	public void pre_click() {
		//��һ������û����һҳ
		
	}

}
