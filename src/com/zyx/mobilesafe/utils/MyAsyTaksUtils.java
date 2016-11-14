package com.zyx.mobilesafe.utils;

import android.os.Handler;

/**
 * 异步加载控件
 * @author Administrator
 *
 */
public abstract class MyAsyTaksUtils {
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			//在子线程后执行的方法
			postTask();
			
		};
	};
	/**
	 * 在子线程之前执行的方法
	 */
	public abstract void preTask();
	
	/**
	 * 在子线程中执行的方法
	 */
	public abstract void doingTask();
	
	/**
	 * 在子线程后执行的方法
	 */
	public abstract void postTask();
	
	/**
	 * 执行的方法
	 */
	public void execute(){
		// 在子线程之前执行的方法
		preTask();
		new Thread(){
			public void run() {
				//在子线程中执行的方法
				doingTask();
				handler.sendEmptyMessage(0);
			};
		}.start();
		
	}

}
