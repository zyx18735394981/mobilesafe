package com.zyx.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class CopyDB{
	public static  void copyDb(Context context,String dbname) {
		File file = new File(context.getFilesDir(), dbname);
		// �ж��ļ��Ƿ����
		if (!file.exists()) {
			// ��assetsĿ¼�н����ݿ��ȡ����
			// 1.��ȡassets�Ĺ�����
			AssetManager am = context.getAssets();
			InputStream in = null;
			FileOutputStream out = null;
			try {
				// 2.��ȡ���ݿ�
				in = am.open(dbname);
				// д����
				// getCacheDir : ��ȡ�����·��
				// getFilesDir : ��ȡ�ļ���·��
				out = new FileOutputStream(file);
				// 3.��д����
				// ���û�����
				byte[] b = new byte[1024];
				int len = -1;
				while ((len = in.read(b)) != -1) {
					out.write(b, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				 try {
					in.close();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			}
		}
	}

}
	