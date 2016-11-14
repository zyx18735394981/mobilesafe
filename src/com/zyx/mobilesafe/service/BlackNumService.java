package com.zyx.mobilesafe.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.zyx.mobilesafe.dao.BlackNumberDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
/**
 * ʵ�ֺ������͵绰������
 * @author Administrator
 *
 */
public class BlackNumService extends Service {
	private SmsReceiver smsReceiver;
	private BlackNumberDao blackNumDao;
	private TelephonyManager tm;
	private MyPhoneStateListener myPhoneStateListener;
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private class SmsReceiver extends BroadcastReceiver{
/**
 * ���ض���
 */
		@Override
		public void onReceive(Context context, Intent intent) {
		System.out.println("�Զ��Ž��м�����������");
			 //���ܽ������ŵĲ���
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for(Object obj:objs){
				//������SmsMessage
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
				String body = smsMessage.getMessageBody();//��ȡ���ŵ�����
				String sender = smsMessage.getOriginatingAddress();//��ȡ������
				System.out.println("���ݷ����˺���,׼��ȥ��ȡ���������ģʽ");
				//���ݷ����˺���,��ȡ���������ģʽ
				int mode = blackNumDao.getMode(sender);
				System.out.println("�����˺���:"+mode);
				//�ж��Ƿ��Ƕ������ػ�����ȫ������
				if (mode == BlackNumberDao.SMS || mode == BlackNumberDao.ALL) {
					System.out.println("�������ز�����");
					abortBroadcast();//���ع㲥�¼�,���ض��Ų���
				}
			}
			
		}
		
	}
	
	@Override
	public void onCreate() {
	
		super.onCreate();
		System.out.println("����ע��㲥�����߽��ܶ���");//��ִ��
		blackNumDao=new BlackNumberDao(getApplicationContext());
		//�ô���ע��һ�����ŵ����Ĺ㲥������
		//1.�����㲥������
		  smsReceiver=new SmsReceiver();
		//2.���ü����Ĺ㲥�¼�
		IntentFilter intentfiler=new IntentFilter();
		//�㲥�����ߵ�������ȼ���Integer.MAX_VALUE
		intentfiler.setPriority(1000);//��ִ��
		intentfiler.addAction("android.provider.Telephony.SMS_RECEIVED");
		//3.ע��㲥������
		registerReceiver(smsReceiver, intentfiler);
	
		myPhoneStateListener = new MyPhoneStateListener();
		
		//�����绰״̬����
		tm=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		//����1������
		tm.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);;
		
		
		
	}
	
	private class MyPhoneStateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, final String incomingNumber) {
			
			super.onCallStateChanged(state, incomingNumber);
			System.out.println("�Ե绰���м���,������");
			//���������״̬,�������ģʽ�Ƿ��ǵ绰���أ�����Ҷ�
			if(state==TelephonyManager.CALL_STATE_RINGING){
			  int mode=blackNumDao.getMode(incomingNumber);
			  System.out.println(mode+"QQQQQ");
			 
				if(mode==BlackNumberDao.PHONE||mode==BlackNumberDao.ALL){
					//�Ҷϵ绰
					//�Ҷϵ绰 1.5
					endCall();
					
			//ɾ��ͨ����¼
					//1.��ȡ���ݽ�����
					final ContentResolver resolver = getContentResolver();
					//2.��ȡ�����ṩ�ߵ�ַ  call_log   calls��ĵ�ַ:calls
					//3.��ȡִ�в���·��
					final Uri uri = Uri.parse("content://call_log/calls");
					//4.ɾ������--����һ
					//resolver.delete(uri, "number=?", new String[]{incomingNumber});
					//������==ͨ�����ݹ۲��߹۲������ṩ������,����仯,��ȥִ��ɾ������(�Ƽ�)
					//notifyForDescendents : ƥ�����,true : ��ȷƥ��   false:ģ��ƥ��
					resolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
						//�����ṩ�����ݱ仯��ʱ�����
						@Override
						public void onChange(boolean selfChange) {
							super.onChange(selfChange);
							//ɾ��ͨ����¼
							resolver.delete(uri, "number=?", new String[]{incomingNumber});
							//ע�����ݹ۲���
							resolver.unregisterContentObserver(this);
						}
					});
					
				}
			}
			
			
		}
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//ע�����ŵļ���
		unregisterReceiver(smsReceiver);
		
		//ȡ���绰����
		tm.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
/**
 * �Ҷϵ绰
 */
	public void endCall() {
		//ͨ���������ʵ��
				try {
					//1.ͨ���������������Ӧ���class�ļ�
					//Class<?> forName = Class.forName("android.os.ServiceManager");
					Class<?> loadClass = BlackNumService.class.getClassLoader().loadClass("android.os.ServiceManager");
					//2.��ȡ������Ӧ�ķ���
					//name : ������
					//parameterTypes : ��������
					Method method = loadClass.getDeclaredMethod("getService", String.class);
					//3.ִ�з���,��ȡ����ֵ
					//receiver : ���ʵ��
					//args : ����Ĳ���
					IBinder invoke = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
					//aidl
					ITelephony iTelephony = ITelephony.Stub.asInterface(invoke);
					//�Ҷϵ绰
					iTelephony.endCall();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

}
