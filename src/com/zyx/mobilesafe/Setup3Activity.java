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
	//���Բ���
	if(!sp.getString("phone", "").equals(null)){
		et_phone_number.setText(sp.getString("phone", ""));
	}
	}
	


	@Override
	public void next_click() {
		String phone_number=et_phone_number.getText().toString().trim();
		if(TextUtils.isEmpty(phone_number)){
			ToastUtils.showToast(getApplicationContext(), "�����밲ȫ�ֻ���");
			return;
		}else{
			//���浽����
			Editor edit=sp.edit();
			edit.putString("phone", phone_number);
			edit.commit();
		}
		//��ת�����ĸ�����
		Intent intent=new Intent(this,Setup4Activity.class);
		startActivity(intent);
		 finish();
		  //ƽ�ƶ���
		   overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);

			
	}

	@Override
	public void pre_click() {
		//��ת���ڶ�������
		Intent intent=new Intent(this,Setup2Activity.class);
		startActivity(intent);
		 finish();
		  //ƽ�ƶ���
		   overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);

		
	}


/**
 * ѡ����ϵ�˰�ť����¼�
 */
	public void selectContact(View v){
		//��ת��ѡ����ϵ�˽���
		Intent intent=new Intent(this,ContactActivity.class);
		//startActivityForResult �����ڵ�activity�˳���ʱ��ص���֮ǰActivity��onActivityResult����
		startActivityForResult(intent, 0);
		
		
	}
/**
 * �������ִ�и÷���
 */
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	super.onActivityResult(requestCode, resultCode, data);
	if(data!=null){
		//����ѡ����ϵ�˴��ݹ���������
		String phone_num=data.getStringExtra("num");//������ػ���ֿ�ָ���쳣
		//���õ���ֵ���ø������ؼ�
		et_phone_number.setText(phone_num);
		
	}
	
	
}

}
