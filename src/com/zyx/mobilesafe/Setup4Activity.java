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
		//���Բ���
		if(sp.getBoolean("checkbox", false)==true){
			//����
			cb_box.setText("���Ѿ������˷�������");
			cb_box.setChecked(true);
		}else{
		
			//�ر�
			cb_box.setText("��û�п�����������");
			cb_box.setChecked(false);
		}
	    
	    //��checkbox��ӵ���¼�
		//״̬�ı�ʱ����
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//buttonView checkbox
			//isChecked �Ƿ�ѡ �ı�֮���״̬ ���֮���ֵ
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//����checkbox��״̬������Ϣ
				Editor edit=sp.edit();	
				if(isChecked==true){
					//����
					cb_box.setText("���Ѿ������˷�������");
					cb_box.setChecked(true);
					//���浽����
					edit.putBoolean("checkbox", true);
					
				}else{
					//�ر�
					cb_box.setText("��û�п�����������");
					cb_box.setChecked(false);
					//���浽����
					edit.putBoolean("checkbox", false);
					
				}
				edit.commit();
				
			}
		});
	}
	




	@Override
	public void next_click() {
		ToastUtils.showToast(this, "�������");
		//���������汣��false
	Editor edit=sp.edit();
	edit.putBoolean("first", false);
	edit.commit();
		//��ת����������
	Intent intent=new Intent(this,LostActivity.class);
	startActivity(intent);
	finish();
		
		
	}


	@Override
	public void pre_click() {
		//��ת����3������
				Intent intent=new Intent(this,Setup3Activity.class);
				startActivity(intent);
				 finish();
				  //ƽ�ƶ���
				   overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);

			
		
	}

}
