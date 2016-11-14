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
 * �ڴ�����ʹ��
 * @param context
 */
	public SettingView(Context context) {
		super(context);
		init();
	}
/**
 * �ڲ����ļ���ʹ��+style
 * @param context
 * @param attrs
 * @param defStyle
 */
	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();//�����ڳ�ʼ���Զ���ؼ���ʱ�����Ӳ��ֿؼ�
	}
/**
 * �ڲ����ļ���ʹ��ʱ����
 * @param context
 * @param attrs
 */
	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		
	String title=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyx.mobilesafe", "title");
	 des_on=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyx.mobilesafe", "des_on");
	 des_off=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.zyx.mobilesafe", "des_off");
	tv_setting_title.setText(title);//��title��ֵ
	

	 
		
	}
/**
 * ��ӿؼ��õ�
 */
	private void init(){
		//��Ӳ����ļ�  
		//����һ    �������ļ�ת����view����
	View view=View.inflate(getContext(), R.layout.setting_view, null);
		
		this.addView(view);
		
		//������  ����view���� ��ȡview����
		//View.inflate(getContext(), R.layout.setting_view, this);
		
		//��ʼ���ؼ� ����  ������Ϣ checkbox
		//��ʾ��setting_view.xml�л�ȡ�ؼ�
		tv_setting_title=(TextView)view.findViewById(R.id.tv_setting_title);
		tv_setting_des=(TextView)view.findViewById(R.id.tv_setting_des);
		cb_setting_update=(CheckBox)view.findViewById(R.id.cb_setting_update);
		
		
		
	
	}
	
	//��Ҫ���һЩ������ʹ����Ա�ܹ�������ȥ�ı��Զ���ؼ��е�ֵ
	/**
	 * ���ñ���ķ���
	 */
	public void setTitle(String title){
		//���ô������ı���
		tv_setting_title.setText(title);
		
	}
	/**
	 * ����������Ϣ�ķ���
	 * 
	 */
	public void setDes(String des){
		//���ô�������������Ϣ
		tv_setting_des.setText(des);
		
	}
	/**
	 * ����checkbox�ķ���
	 */
	public void setCheckbox(boolean isChecked){
		//���ô�������checkbox��״̬
		cb_setting_update.setChecked(isChecked);
		if(isChecked()){
			tv_setting_des.setText(des_on);
		}else{
			tv_setting_des.setText(des_off);
		}
		
	}
	
	/**
	 * ��ȡcheckbox��״̬
	 */
	public boolean isChecked(){
		//��ȡcheckbox��״̬
		return cb_setting_update.isChecked();
	}
}
