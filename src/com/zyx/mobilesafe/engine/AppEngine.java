package com.zyx.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.zyx.mobilesafe.entity.AppInfo;

public class AppEngine {
	public static  List<AppInfo> getAppInfos(Context context){
		List<AppInfo> list=new ArrayList<AppInfo>();
		
		 PackageManager pm=context.getPackageManager();
		List<PackageInfo> installedPackages=pm.getInstalledPackages(0);
		for(PackageInfo info:installedPackages){
		        String packageName=info.packageName;
		        String versionName=info.versionName;
		        
		      ApplicationInfo appInfo=info.applicationInfo;
		              Drawable icon=appInfo.loadIcon(pm);
		             String appName=appInfo.loadLabel(pm).toString();
		             
		        int flags=appInfo.flags;
		        
		        Boolean isUser;
		        if((appInfo.FLAG_SYSTEM&flags)==appInfo.FLAG_SYSTEM){
		        	//系统程序
		        	isUser=false;
		        }else{
		        	//用户程序
		        	isUser=true;
		        }
		         
		        Boolean isSD;
		        if((appInfo.FLAG_EXTERNAL_STORAGE&flags)==appInfo.FLAG_EXTERNAL_STORAGE){
		        	//SD卡上
		        	isSD=true;
		        }else{
		        	//手机内存
		        	isSD=false;
		        }
		        AppInfo infos=new AppInfo(appName, icon, packageName, versionName, isSD, isUser);
		        
		        list.add(infos);
			
		}
		
		return list;
	}

}
