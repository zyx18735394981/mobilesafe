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
	 * 获取正在运行的进程个数
	 */
	public static int getTaskCount(Context context){
	ActivityManager am=	(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	List<RunningAppProcessInfo> list=am.getRunningAppProcesses();
		
		return list.size();
	}
	
	/**
	 * 获取剩余内存
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
		//读取文件
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			
					for(int i=0;i<2;i++){
						if((line=br.readLine())!=null){
							
						  result=line;
							
						}
						
					}
					//得到第二个数据   result---剩余内存
					 //获取数字
				    char[] array=result.toCharArray();
				//遍历数组
				    for(char c :array){
				    	if(c>='0'&&c<='9'){
				    		//获取出来
				    		sb.append(c);
				    		
				    	}
				    	
				    }
				  //拿出字符串
				    String s=sb.toString();
				    //转换成long类型
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
	 * 获取总内存
	 */
	public static long getSumRoom(Context context){
		ActivityManager am=	(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
		
		return outInfo.totalMem;
	}
	
	/**
	 * 兼容低版本的方法---获取总内存
	 */
	public static long getSumRoom2(Context context){
		File file=new File("/proc/meminfo");
		StringBuffer sb=new StringBuffer();
		
		//读取文件
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
		    String readLine= br.readLine();
		  //获取数字
		    char[] array=readLine.toCharArray();
		//遍历数组
		    for(char c :array){
		    	if(c>='0'&&c<='9'){
		    		//获取出来
		    		sb.append(c);
		    		
		    	}
		    	
		    }
		    //拿出字符串
		    String s=sb.toString();
		    //转换成long类型
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
