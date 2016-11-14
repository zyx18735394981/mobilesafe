package com.zyx.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

public class TaskUtil {
	/**
	 * ��ȡ�������еĽ��̸���
	 */
	public static int getTaskCount(Context context){
	ActivityManager am=	(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	List<RunningAppProcessInfo> list=am.getRunningAppProcesses();
		
		return list.size();
	}
	
	/**
	 * ��ȡʣ���ڴ�
	 * 
	 */
/*	public static long getTaskRoom(Context context){
		ActivityManager am=	(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
		
		return outInfo.availMem;
	}*/
	
	
	
	public static long getTaskRoom(Context context){
		String line;
		String result = null;
		StringBuffer sb=new StringBuffer();
		File file=new File("/proc/meminfo");
		//��ȡ�ļ�
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			
					for(int i=0;i<2;i++){
						if((line=br.readLine())!=null){
							
						  result=line;
							
						}
						
					}
					//�õ��ڶ�������   result---ʣ���ڴ�
					 //��ȡ����
				    char[] array=result.toCharArray();
				//��������
				    for(char c :array){
				    	if(c>='0'&&c<='9'){
				    		//��ȡ����
				    		sb.append(c);
				    		
				    	}
				    	
				    }
				  //�ó��ַ���
				    String s=sb.toString();
				    //ת����long����
				    long l=Long.parseLong(s);
				    return l*1024;
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return 0;
	}
	
	/**
	 * ��ȡ���ڴ�
	 */
	public static long getSumRoom(Context context){
		ActivityManager am=	(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
		
		return outInfo.totalMem;
	}
	
	/**
	 * ���ݵͰ汾�ķ���---��ȡ���ڴ�
	 */
	public static long getSumRoom2(Context context){
		File file=new File("/proc/meminfo");
		StringBuffer sb=new StringBuffer();
		
		//��ȡ�ļ�
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
		    String readLine= br.readLine();
		  //��ȡ����
		    char[] array=readLine.toCharArray();
		//��������
		    for(char c :array){
		    	if(c>='0'&&c<='9'){
		    		//��ȡ����
		    		sb.append(c);
		    		
		    	}
		    	
		    }
		    //�ó��ַ���
		    String s=sb.toString();
		    //ת����long����
		    long l=Long.parseLong(s);
		    return l*1024;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	
	}

}
