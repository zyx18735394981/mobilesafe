package com.zyx.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
/**
 * 设置广播 监听手机重启的操作
 * @author Administrator
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		sp=arg0.getSharedPreferences("config", arg0.MODE_PRIVATE);
		if(sp.getBoolean("checkbox", false)==true){
			System.out.println("手机重启了~");
			
			//检查存在参数里的sim是否跟本地的sim一致，若不一致，则发送报警短信
			//获取本地的sim卡
			TelephonyManager tm=(TelephonyManager)arg0.getSystemService(arg0.TELEPHONY_SERVICE);
			String sim=tm.getSimSerialNumber();//得到SIM的序列号
			//获取参数里的sim
			String sp_sim=sp.getString("sim", "");
			//当第四个界面的手机防盗checkbox勾上，才说明开启了防盗,才发送防盗短信
	
			if(!TextUtils.isEmpty(sp_sim)&&!TextUtils.isEmpty(sim)){
				if(!sim.equals(sp_sim)){
					//发送报警短信
					SmsManager sms=SmsManager.getDefault();
					sms.sendTextMessage(sp.getString("phone", "18735194981"), null, "我是一心，我在手机里装了防盗软件，我手机被盗了~", null, null);
				}
			
		}	
		}
	}

}
