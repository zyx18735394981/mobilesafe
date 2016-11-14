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
	
	
	
	//����2:û�б����û��������
		//һֱ����Ĭ�ϵĴ���ʾ����
		//����״̬--�ò���
	//����ò������棡
	//name �ļ�����  mode Ȩ��
	 sp=getSharedPreferences("config", MODE_PRIVATE);
	
        sv_setting_update=(SettingView)findViewById(R.id.sv_setting);
        
       // sv_setting_update.setTitle("��ʾ����");
        //defValue  ȱʡֵ
        if(sp.getBoolean("update", true)){
        	 //��ʼ���Զ���ؼ��и����ؼ���ֵ   
          //  sv_setting_update.setDes("����ʾ����");
            sv_setting_update.setCheckbox(true);
        	
        }else{
        	//sv_setting_update.setDes("�ر���ʾ����");
            sv_setting_update.setCheckbox(false);
        	
        }
       /**
         * ��ӵ���¼�
         */
        sv_setting_update.setOnClickListener(this);     
	}
    //�����Զ�����Ͽؼ��ĵ���¼�
    //����1:���checkbox����������Ϣû�иı�,
	//ԭ��:��Ϊcheckbox�������е���¼��ͻ�ȡ�����¼�,
	//�����checkbox,���checkbox�ͻ�ִ�����Լ��ĵ���¼�
    //������ִ����Ŀ�ĵ���¼�
	//-------------------------------------------------
    //����2:û�б����û��������
	//һֱ����Ĭ�ϵĴ���ʾ����
	//����״̬--�ò���
		@Override
		public void onClick(View v) {
			
			Editor edit=sp.edit();
			
			//����״̬
			//����checkbox���е�״̬���ı�checkbox��״̬
			if(sv_setting_update.isChecked()){
				//��ȡ��checkbox���е�״̬�������Ҫ����෴��
				//�ر���ʾ����
				//sv_setting_update.setDes("�ر���ʾ����");
				sv_setting_update.setCheckbox(false);
				//��״̬���浽��������
				edit.putBoolean("update", false);
			
			}else{
				//����ʾ����
				//sv_setting_update.setDes("����ʾ����");
				sv_setting_update.setCheckbox(true);
				//��״̬���浽��������
				edit.putBoolean("update", true);
				
			}
			edit.commit();//���浽�ļ�
		}

@Override
protected void onStart() {
	
	super.onStart();
	blacknum();
	
}
/**
 * ���غ���������
 */
private void blacknum() {
	// ��̬�Ļ�ȡ�����Ƿ���
	if (AdressUtils.isRunningService(
			"com.zyx.mobilesafe.service.BlackNumService",
			getApplicationContext())) {
		// ��������
		sv_blacknum.setCheckbox(true);
	} else {
		// �رշ���
		sv_blacknum.setCheckbox(false);
	}
	sv_blacknum.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SettingActivity.this,
					BlackNumService.class);
			// ����checkbox��״̬����������Ϣ��״̬
			// isChecked() : ֮ǰ��״̬
			if (sv_blacknum.isChecked()) {
				// �ر���ʾ����
				stopService(intent);
				// ����checkbox��״̬
				sv_blacknum.setCheckbox(false);
			} else {
				// ����ʾ����
				startService(intent);
				sv_blacknum.setCheckbox(true);
			}
		}
	});
	
	
}
}
