package com.zyx.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class StremUtil {
	/**
	 * 将流信息转换成字符串
	 * @return
	 * @throws IOException 
	 */
	public static String parstreamUtil(InputStream is) throws IOException{
		//字符流,读取流
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		//写入流
		StringWriter sw=new StringWriter();
		//读写操作
		String str=null;//创建一个数据缓冲区
		while((str=br.readLine())!=null){
			//写入操作
			sw.write(str);
		}
		//关流
		sw.close();
		br.close();	
		return sw.toString();
		
	}

}
