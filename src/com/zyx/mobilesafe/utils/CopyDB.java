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
		// 判断文件是否存在
		if (!file.exists()) {
			// 从assets目录中将数据库读取出来
			// 1.获取assets的管理者
			AssetManager am = context.getAssets();
			InputStream in = null;
			FileOutputStream out = null;
			try {
				// 2.读取数据库
				in = am.open(dbname);
				// 写入流
				// getCacheDir : 获取缓存的路径
				// getFilesDir : 获取文件的路径
				out = new FileOutputStream(file);
				// 3.读写操作
				// 设置缓冲区
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
	