package com.zyx.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
/**
 * ���ù㲥 �����ֻ������Ĳ���
 * @author Administrator
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		sp=arg0.getSharedPreferences("config", arg0.MODE_PRIVATE);
		if(sp.getBoolean("checkbox", false)==true){
			System.out.println("�ֻ�������~");
			
			//�����ڲ������sim�Ƿ�����ص�simһ�£�����һ�£����ͱ�������
			//��ȡ���ص�sim��
			TelephonyManager tm=(TelephonyManager)arg0.getSystemService(arg0.TELEPHONY_SERVICE);
			String sim=tm.getSimSerialNumber();//�õ�SIM�����к�
			//��ȡ�������sim
			String sp_sim=sp.getString("sim", "");
			//�����ĸ�������ֻ�����checkbox���ϣ���˵�������˷���,�ŷ��ͷ�������
	
			if(!TextUtils.isEmpty(sp_sim)&&!TextUtils.isEmpty(sim)){
				if(!sim.equals(sp_sim)){
					//���ͱ�������
					SmsManager sms=SmsManager.getDefault();
					sms.sendTextMessage(sp.getString("phone", "18735194981"), null, "����һ�ģ������ֻ���װ�˷�����������ֻ�������~", null, null);
				}
			
		}	
		}
	}

}
