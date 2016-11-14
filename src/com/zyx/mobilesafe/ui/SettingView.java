package com.zyx.mobilesafe.ui;

import com.zyx.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingView extends RelativeLayout {
	private TextView tv_setting_title;
	private TextView tv_setting_des;
	private CheckBox cb_setting_update;
	private String des_on;
	private String des_off;
/**
 * 在代码中使用
 * @param context
 */
	public SettingView(Context context) {
		super(context);
		init();
	}
/**
 * 在布局文件中使用+style
 * @param context
 * @param attrs
 * @param defStyle
 */
	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();//可以在初始化自定义控件的时候就添加布局控件
	}
/**
 * 在布局文件中使用时调用
 * @param context
 * @param attrs
 */
	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		
	String title=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyx.mobilesafe", "title");
	 des_on=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyx.mobilesafe", "des_on");
	 des_off=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyx.mobilesafe", "des_off");
	tv_setting_title.setText(title);//给title赋值
	

	 
		
	}
/**
 * 添加控件用的
 */
	private void init(){
		//添加布局文件  
		//方法一    将布局文件转换成view对象
	View view=View.inflate(getContext(), R.layout.setting_view, null);
		
		this.addView(view);
		
		//方法二  创建view对象 获取view对象
		//View.inflate(getContext(), R.layout.setting_view, this);
		
		//初始化控件 标题  描述信息 checkbox
		//表示从setting_view.xml中获取控件
		tv_setting_title=(TextView)view.findViewById(R.id.tv_setting_title);
		tv_setting_des=(TextView)view.findViewById(R.id.tv_setting_des);
		cb_setting_update=(CheckBox)view.findViewById(R.id.cb_setting_update);
		
		
		
	
	}
	
	//需要添加一些方法，使程序员能够更方便去改变自定义控件中的值
	/**
	 * 设置标题的方法
	 */
	public void setTitle(String title){
		//设置传进来的标题
		tv_setting_title.setText(title);
		
	}
	/**
	 * 设置描述信息的方法
	 * 
	 */
	public void setDes(String des){
		//设置传进来的描述信息
		tv_setting_des.setText(des);
		
	}
	/**
	 * 设置checkbox的方法
	 */
	public void setCheckbox(boolean isChecked){
		//设置传进来的checkbox的状态
		cb_setting_update.setChecked(isChecked);
		if(isChecked()){
			tv_setting_des.setText(des_on);
		}else{
			tv_setting_des.setText(des_off);
		}
		
	}
	
	/**
	 * 获取checkbox的状态
	 */
	public boolean isChecked(){
		//获取checkbox的状态
		return cb_setting_update.isChecked();
	}
}
