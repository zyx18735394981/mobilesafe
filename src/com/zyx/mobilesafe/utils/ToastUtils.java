package com.zyx.mobilesafe.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
/**
 * ����Ļ������ʾһ��Toast
 * @param text
 */
public class ToastUtils {
	public static void showToast(Context context,CharSequence text){
		Toast toast=Toast.makeText(context, text, Toast.LENGTH_SHORT);
		//��ʾ����Ļ����
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
