package com.zyx.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class StremUtil {
	/**
	 * ������Ϣת�����ַ���
	 * @return
	 * @throws IOException 
	 */
	public static String parstreamUtil(InputStream is) throws IOException{
		//�ַ���,��ȡ��
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		//д����
		StringWriter sw=new StringWriter();
		//��д����
		String str=null;//����һ�����ݻ�����
		while((str=br.readLine())!=null){
			//д�����
			sw.write(str);
		}
		//����
		sw.close();
		br.close();	
		return sw.toString();
		
	}

}
