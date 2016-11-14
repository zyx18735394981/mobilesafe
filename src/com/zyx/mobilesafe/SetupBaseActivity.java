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
	//界面的触摸事件
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
				//下一步操作
				next_click();
			}else if(endX-startX>100){
				//上一步操作
				pre_click();
			}else if(Math.abs(startX-endX)>50){
				ToastUtils.showToast(getApplicationContext(), "禁止斜划");
				
			}
			return true;
		}
		
		
		
		
	}
	
	/**
	 * 下一页按钮点击事件
	 */
	public void nextClick(View v){
		//执行抽象方法
		next_click();
	
	}
	/**
	 * 上一页按钮点击事件
	 */
	public void preClick(View v){
		//执行抽象方法
		pre_click();

	}
	/**
	 * 下一页的抽象方法
	 */
	public abstract void next_click();
	
	/**
	 * 上一页的抽象方法
	 */
	public abstract void pre_click();
	
	/**
	 * 返回操作进行处理
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==event.KEYCODE_BACK){
			pre_click();
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
