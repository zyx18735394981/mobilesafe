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
 * ��������
 * @author Administrator
 *
 */
public class SmsReceiver extends BroadcastReceiver {
	private static MediaPlayer mediaPlayer;
	private SharedPreferences sp;
	
//������Ե�ʱ��ǵ��жϷ������ǲ��ǰ�ȫ����
	@Override
	public void onReceive(Context context, Intent intent) {
		sp=context.getSharedPreferences("config", context.MODE_PRIVATE);
		
		//��������Ա�Ƿ񼤻�
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(context, Admin.class);
		
		System.out.println("���ֻ����Ž��м���");
		//���ս�������
		//���ܽ�������
		//70����һ������,71������������
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for(Object obj:objs){
			//������SmsMessage
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getMessageBody();//��ȡ���ŵ�����
			String sender = smsMessage.getOriginatingAddress();//��ȡ������
			//������Լӷ������ж�
			System.out.println("������:"+sender);
		
			System.out.println("��������:"+body);
	//if (sp.getString("phone", "").equals(sender)) {//�����ߵ��ڰ�ȫ����ʱִ���������		
			//abortBroadcast();//���ز���,ԭ���İ�׿ϵͳ�����ʹ�ã�ĳЩ���������˸ù��� :С��
			
			//�ж϶����������ĸ�ָ��
			if("GPS".equals(body)){
			System.out.println("����GPS׷��");
			
				//GPS׷��--�ðٶȵ�ͼ�Ķ�λ����
		//Intent local_intent=new Intent(context,LocationActivity.class);
		//	context.startActivity(local_intent);
				
			
			//GPS׷��
			//����һ������
			Intent intent_gps = new Intent(context,GPSService.class);
			context.startService(intent_gps);
			
			
			
				//ִ��������ǵ����ض���
				abortBroadcast();
			}else if("playmusic".equals(body)){
				//���ű�������
				//�ڲ��ű�������֮ǰ,��ϵͳ�������ó����
				//�����Ĺ�����
				AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
				//����ϵͳ�����Ĵ�С
				//streamType : ����������
				//index : �����Ĵ�С   0��С    15���
				//flags : ָ����Ϣ�ı�ǩ
				//getStreamMaxVolume : ��ȡϵͳ�������,streamType:����������
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
				
				//�ж��Ƿ��ڲ��ű�������
				if (mediaPlayer!=null) {
					mediaPlayer.release();//�ͷ���Դ
				}
				mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
				//mediaPlayer.setVolume(1.0f, 1.0f);//�����������,��������
				//mediaPlayer.setLooping(true);
				mediaPlayer.start();
				
				System.out.println("���ű�������");
				abortBroadcast();//���ز���,ԭ��androidϵͳ,������ȶ���ϵͳ������,����С��
			
			}else if("delete".equals(body)){
				//Զ��ɾ������---�ǰ�ȫ���뷢�����ĲŽ���ɾ�����ݲ���
			
				
				if (devicePolicyManager.isAdminActive(componentName)) {
					devicePolicyManager.wipeData(0);//Զ��ɾ������
				}
				
				
				abortBroadcast();
			}else if("lock".equals(body)){
				//Զ������
			
				if (devicePolicyManager.isAdminActive(componentName)) {
					devicePolicyManager.lockNow();
				}
				
				
				abortBroadcast();
			}
		

	}

}
	//}
	}
