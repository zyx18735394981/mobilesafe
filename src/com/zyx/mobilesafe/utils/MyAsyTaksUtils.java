package com.zyx.mobilesafe.utils;

import android.os.Handler;

/**
 * �첽���ؿؼ�
 * @author Administrator
 *
 */
public abstract class MyAsyTaksUtils {
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			//�����̺߳�ִ�еķ���
			postTask();
			
		};
	};
	/**
	 * �����߳�֮ǰִ�еķ���
	 */
	public abstract void preTask();
	
	/**
	 * �����߳���ִ�еķ���
	 */
	public abstract void doingTask();
	
	/**
	 * �����̺߳�ִ�еķ���
	 */
	public abstract void postTask();
	
	/**
	 * ִ�еķ���
	 */
	public void execute(){
		// �����߳�֮ǰִ�еķ���
		preTask();
		new Thread(){
			public void run() {
				//�����߳���ִ�еķ���
				doingTask();
				handler.sendEmptyMessage(0);
			};
		}.start();
		
	}

}
