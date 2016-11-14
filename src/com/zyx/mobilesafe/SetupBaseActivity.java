package com.zyx.mobilesafe;

import com.zyx.mobilesafe.utils.ToastUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;

public abstract class SetupBaseActivity extends Activity {
	private GestureDetector gestureDetector;
	protected SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		
		
		 gestureDetector=new GestureDetector(this,new MyGestureDetector());
		
		
	}
	//����Ĵ����¼�
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			gestureDetector.onTouchEvent(event);
			return super.onTouchEvent(event);
		}
	public class MyGestureDetector extends SimpleOnGestureListener{
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float startX=e1.getRawX();
			float endX=e2.getRawX();
			float startY=e1.getRawY();
			float endY=e2.getRawY();
			if(startX-endX>100){
				//��һ������
				next_click();
			}else if(endX-startX>100){
				//��һ������
				pre_click();
			}else if(Math.abs(startX-endX)>50){
				ToastUtils.showToast(getApplicationContext(), "��ֹб��");
				
			}
			return true;
		}
		
		
		
		
	}
	
	/**
	 * ��һҳ��ť����¼�
	 */
	public void nextClick(View v){
		//ִ�г��󷽷�
		next_click();
	
	}
	/**
	 * ��һҳ��ť����¼�
	 */
	public void preClick(View v){
		//ִ�г��󷽷�
		pre_click();

	}
	/**
	 * ��һҳ�ĳ��󷽷�
	 */
	public abstract void next_click();
	
	/**
	 * ��һҳ�ĳ��󷽷�
	 */
	public abstract void pre_click();
	
	/**
	 * ���ز������д���
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==event.KEYCODE_BACK){
			pre_click();
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
