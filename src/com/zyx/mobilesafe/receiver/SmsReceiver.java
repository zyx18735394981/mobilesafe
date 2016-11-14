package com.zyx.mobilesafe.receiver;


import com.zyx.mobilesafe.R;
import com.zyx.mobilesafe.service.GPSService;


import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
/**
 * 监听短信
 * @author Administrator
 *
 */
public class SmsReceiver extends BroadcastReceiver {
	private static MediaPlayer mediaPlayer;
	private SharedPreferences sp;
	
//真机测试的时候记得判断发件人是不是安全号码
	@Override
	public void onReceive(Context context, Intent intent) {
		sp=context.getSharedPreferences("config", context.MODE_PRIVATE);
		
		//超级管理员是否激活
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(context, Admin.class);
		
		System.out.println("对手机短信进行监听");
		//接收解析短信
		//接受解析短信
		//70汉字一条短信,71汉字两条短信
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for(Object obj:objs){
			//解析成SmsMessage
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getMessageBody();//获取短信的内容
			String sender = smsMessage.getOriginatingAddress();//获取发件人
			//真机测试加发件人判断
			System.out.println("发件人:"+sender);
		
			System.out.println("短信内容:"+body);
	//if (sp.getString("phone", "").equals(sender)) {//发送者等于安全号码时执行四种命令。		
			//abortBroadcast();//拦截操作,原声的安卓系统里可以使用，某些机型屏蔽了该功能 :小米
			
			//判断短信内容是哪个指令
			if("GPS".equals(body)){
			System.out.println("进入GPS追踪");
			
				//GPS追踪--用百度地图的定位功能
		//Intent local_intent=new Intent(context,LocationActivity.class);
		//	context.startActivity(local_intent);
				
			
			//GPS追踪
			//开启一个服务
			Intent intent_gps = new Intent(context,GPSService.class);
			context.startService(intent_gps);
			
			
			
				//执行完操作记得拦截短信
				abortBroadcast();
			}else if("playmusic".equals(body)){
				//播放报警音乐
				//在播放报警音乐之前,将系统音量设置成最大
				//声音的管理者
				AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
				//设置系统音量的大小
				//streamType : 声音的类型
				//index : 声音的大小   0最小    15最大
				//flags : 指定信息的标签
				//getStreamMaxVolume : 获取系统最大音量,streamType:声音的类型
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
				
				//判断是否在播放报警音乐
				if (mediaPlayer!=null) {
					mediaPlayer.release();//释放资源
				}
				mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
				//mediaPlayer.setVolume(1.0f, 1.0f);//设置最大音量,音量比例
				//mediaPlayer.setLooping(true);
				mediaPlayer.start();
				
				System.out.println("播放报警音乐");
				abortBroadcast();//拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
			
			}else if("delete".equals(body)){
				//远程删除数据---是安全号码发过来的才进行删除数据操作
			
				
				if (devicePolicyManager.isAdminActive(componentName)) {
					devicePolicyManager.wipeData(0);//远程删除数据
				}
				
				
				abortBroadcast();
			}else if("lock".equals(body)){
				//远程锁屏
			
				if (devicePolicyManager.isAdminActive(componentName)) {
					devicePolicyManager.lockNow();
				}
				
				
				abortBroadcast();
			}
		

	}

}
	//}
	}
