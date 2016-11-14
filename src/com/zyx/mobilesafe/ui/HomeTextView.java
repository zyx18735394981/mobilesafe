package com.zyx.mobilesafe.ui;
/**
 * 自定义控件
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class HomeTextView extends TextView {
/**
 * 在代码中使用的时候调用
 * @param context
 */
	public HomeTextView(Context context) {
		super(context);
		
	}
/**
 * 布局文件中使用的时候调用
 * 多了样式文件
 * @param context
 * @param attrs
 * @param defStyle
 */
	public HomeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	
	
/**
 * 布局文件中使用的时候调用
 * 布局文件中的控件都是可以用代码来表示的
 * AttributeSet保存控件的所有属性
 * @param context
 * @param attrs
 */
	public HomeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
//设置自定义的textView  自动获取焦点
	
	/**
	 * 设置是否获取焦点
	 */
	@Override
		public boolean isFocused() {
			// true 获取焦点
		    //false 不去获取
			return true;
		}
}
