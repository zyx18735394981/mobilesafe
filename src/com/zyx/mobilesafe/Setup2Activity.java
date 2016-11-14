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
		 
		 //���Բ���
		 if(TextUtils.isEmpty(sp.getString("sim", ""))){
			 sv_setup2_sim.setCheckbox(false);
		 }else{
			 sv_setup2_sim.setCheckbox(true);
			 
		 }
	
	  sv_setup2_sim.setOnClickListener(new OnClickListener() {
		//����� ���õ���¼�
		@Override
		public void onClick(View v) {
			//��SIM��
			//����checkbox��״̬����������Ϣ��״̬
			//isChecked��ȡ����֮ǰ��״̬ 
			Editor edit=sp.edit();
			if(sv_setup2_sim.isChecked()){
				//���
				sv_setup2_sim.setCheckbox(false);
				
				edit.putString("sim", "");
				
				
			}else{
				//��SIM��
				sv_setup2_sim.setCheckbox(true);
				TelephonyManager tm=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
				String sim=tm.getSimSerialNumber();//�õ�SIM�����к�
				//��SIM�� ���Ǳ��浽��������
			    
			    edit.putString("sim", sim);
			   
			}
			
			 edit.commit();
			
		}
	});
	
	}

	@Override
	public void next_click() {
		//��ת���ڶ�������
				Intent intent=new Intent(this,Setup3Activity.class);
				startActivity(intent);
				 finish();
				  //ƽ�ƶ���
				   overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);

			
	}
	@Override
	public void pre_click() {
		//��ת���ڶ�������
				Intent intent=new Intent(this,Setup1Activity.class);
				startActivity(intent);
				 finish();
				  //ƽ�ƶ���
				   overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);

			
		
	}
}
