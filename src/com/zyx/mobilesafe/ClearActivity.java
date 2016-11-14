package com.zyx.mobilesafe;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClearActivity extends Activity {
	private ProgressBar pb;
	private TextView tv;
	private PackageManager pm;
	private int index =0;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_clear);
		
		//找控件
		initUI();
		
		//获取系统中每个应用的缓存
		getData();
		
	}

	/**
	 * 获取缓存
	 */
	private void getData() {
		// 1.获取包管理者对象	
		 pm=(PackageManager)getPackageManager();
		//获取缓存是耗时操作
		new Thread(){
			public void run() {
				
				
				// 2.获取安装在手机上的所有的应用
				List<PackageInfo> list=pm.getInstalledPackages(0);
				// 3.给进度条设置最大值(手机中所有应用的总数)
				// 进度条有两个值必须设置 最大值 当前值（动态变化。）
				pb.setMax(list.size());
				// 4.遍历每一个应用,获取有缓存的应用信息(应用名称,图标,缓存大小,包名)
				 index =0;
				for(PackageInfo packInfo:list){
					String packageName=packInfo.packageName;//包名
					// 通过主包名获取当前app的是否有缓存。
					String cache=getPackageCache(packageName);
					if(cache!=null){
						
						//设置textview
						try {
							
						ApplicationInfo list1=	pm.getApplicationInfo(packageName, 0);
						
						//得到app名称
						 name=list1.loadLabel(pm).toString();
				
						tv.setText(name);
						//得到图标
						Drawable icon=list1.loadIcon(pm);
					
				     
						//设置进度条进度
						index++;
						
		                   runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								tv.setText(name);
								pb.setProgress(index);
								
							}
						});
						
						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
			
					
				}
				
				
			};
			
		}.start();
	
		
	}
	/**
	 * 通过包名获取该应用的缓存
	 * @param packageName
	 */
   protected String getPackageCache(String packageName) {
	   String cache=null;
	   
	 //获取缓存
	 		PackageManager pm = getPackageManager();
	 		//pm.getPackageSizeInfo("com.example.writecache", mStatsObserver);
	 		/**
	 		 * 10-22 05:24:52.906: I/System.out(15794): cachesize:4.00KB codesize:0.96MB datasize:0.00B
	 		 */
	 		IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
	 	        public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {
	 	        	long cachesize = stats.cacheSize;//缓存大小
	 	        	long codesize = stats.codeSize;//应用程序的大小
	 	        	long datasize = stats.dataSize;//数据大小
	 	        	
	 	        	String cache = Formatter.formatFileSize(getApplicationContext(), cachesize);
	 	        	String code = Formatter.formatFileSize(getApplicationContext(), codesize);
	 	        	String data = Formatter.formatFileSize(getApplicationContext(), datasize);
	 	        	
	 	        	
	 	        	
	 	        	System.out.println("cachesize:"+cache +" codesize:"+code+" datasize:"+data);
	 	        }
	 		};
	 		
	 		//反射获取缓存
	 		try {
	 			Class<?> loadClass = ClearActivity.class.getClassLoader().loadClass("android.content.pm.PackageManager");
	 			Method method = loadClass.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
	 			//receiver : 类的实例,隐藏参数,方法不是静态的必须指定
	 			method.invoke(pm, "com.example.writecache",mStatsObserver);
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
			return cache;
	   }
/**
 * 找控件
 */
	private void initUI() {
		 pb=(ProgressBar)findViewById(R.id.progressBar1);
		 tv=(TextView)findViewById(R.id.textview1);
		
	}

}
