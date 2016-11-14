package com.zyx.mobilesafe.engine;



import java.util.ArrayList;
import java.util.List;

import com.zyx.mobilesafe.entity.TaskInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;


public class TaskEngline {
	/**
	 * ��ȡϵͳ�����н�����Ϣ
	 * @return
	 */
	public static List<TaskInfo> getTaskAllInfo(Context context){
		List<TaskInfo> list = new ArrayList<TaskInfo>();
		//1.���̵Ĺ�����
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.getPackageManager();
		//2.��ȡ�����������еĽ�����Ϣ
		List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
		//��������
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			TaskInfo taskInfo = new TaskInfo();
			//3.��ȡ��Ӧ����Ϣ
			//��ȡ���̵�����,��ȡ����
			String packagName = runningAppProcessInfo.processName;
			taskInfo.setPackageName(packagName);
			//��ȡ������ռ���ڴ�ռ�,int[] pids : ���뼸�����̵�pid,�ͻ᷵�ؼ���������ռ�Ŀռ�
			MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
			int totalPss = memoryInfo[0].getTotalPss();
			long ramSize = totalPss*1024;
			taskInfo.setRomSize(ramSize);
			try {
				//��ȡapplication��Ϣ
				//packageName : ����     flags:ָ����Ϣ��ǩ
				ApplicationInfo applicationInfo = pm.getApplicationInfo(packagName, 0);
				//��ȡͼ��
				Drawable icon = applicationInfo.loadIcon(pm);
				taskInfo.setIcon(icon);
				//��ȡ����
				String name = applicationInfo.loadLabel(pm).toString();
				taskInfo.setName(name);
				//��ȡ��������б�ǩ��Ϣ,�Ƿ���ϵͳ�������Ա�ǩ����ʽչʾ
				int flags = applicationInfo.flags;
				boolean isUser;
				//�ж��Ƿ����û�����
				if ((applicationInfo.FLAG_SYSTEM & flags) == applicationInfo.FLAG_SYSTEM) {
					//ϵͳ����
					isUser = false;
				}else{
					//�û�����
					isUser = true;
				}
				//������Ϣ
				taskInfo.setUser(isUser);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			//taskinfo��ӵ�����
			list.add(taskInfo);
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
