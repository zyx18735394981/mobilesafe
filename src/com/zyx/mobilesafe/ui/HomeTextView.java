package com.zyx.mobilesafe.ui;
/**
 * �Զ���ؼ�
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class HomeTextView extends TextView {
/**
 * �ڴ�����ʹ�õ�ʱ�����
 * @param context
 */
	public HomeTextView(Context context) {
		super(context);
		
	}
/**
 * �����ļ���ʹ�õ�ʱ�����
 * ������ʽ�ļ�
 * @param context
 * @param attrs
 * @param defStyle
 */
	public HomeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	
	
/**
 * �����ļ���ʹ�õ�ʱ�����
 * �����ļ��еĿؼ����ǿ����ô�������ʾ��
 * AttributeSet����ؼ�����������
 * @param context
 * @param attrs
 */
	public HomeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
//�����Զ����textView  �Զ���ȡ����
	
	/**
	 * �����Ƿ��ȡ����
	 */
	@Override
		public boolean isFocused() {
			// true ��ȡ����
		    //false ��ȥ��ȡ
			return true;
		}
}
