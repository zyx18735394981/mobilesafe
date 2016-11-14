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
		
		//�ҿؼ�
		initUI();
		
		//��ȡϵͳ��ÿ��Ӧ�õĻ���
		getData();
		
	}

	/**
	 * ��ȡ����
	 */
	private void getData() {
		// 1.��ȡ�������߶���	
		 pm=(PackageManager)getPackageManager();
		//��ȡ�����Ǻ�ʱ����
		new Thread(){
			public void run() {
				
				
				// 2.��ȡ��װ���ֻ��ϵ����е�Ӧ��
				List<PackageInfo> list=pm.getInstalledPackages(0);
				// 3.���������������ֵ(�ֻ�������Ӧ�õ�����)
				// ������������ֵ�������� ���ֵ ��ǰֵ����̬�仯����
				pb.setMax(list.size());
				// 4.����ÿһ��Ӧ��,��ȡ�л����Ӧ����Ϣ(Ӧ������,ͼ��,�����С,����)
				 index =0;
				for(PackageInfo packInfo:list){
					String packageName=packInfo.packageName;//����
					// ͨ����������ȡ��ǰapp���Ƿ��л��档
					String cache=getPackageCache(packageName);
					if(cache!=null){
						
						//����textview
						try {
							
						ApplicationInfo list1=	pm.getApplicationInfo(packageName, 0);
						
						//�õ�app����
						 name=list1.loadLabel(pm).toString();
				
						tv.setText(name);
						//�õ�ͼ��
						Drawable icon=list1.loadIcon(pm);
					
				     
						//���ý���������
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
	 * ͨ��������ȡ��Ӧ�õĻ���
	 * @param packageName
	 */
   protected String getPackageCache(String packageName) {
	   String cache=null;
	   
	 //��ȡ����
	 		PackageManager pm = getPackageManager();
	 		//pm.getPackageSizeInfo("com.example.writecache", mStatsObserver);
	 		/**
	 		 * 10-22 05:24:52.906: I/System.out(15794): cachesize:4.00KB codesize:0.96MB datasize:0.00B
	 		 */
	 		IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
	 	        public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {
	 	        	long cachesize = stats.cacheSize;//�����С
	 	        	long codesize = stats.codeSize;//Ӧ�ó���Ĵ�С
	 	        	long datasize = stats.dataSize;//���ݴ�С
	 	        	
	 	        	String cache = Formatter.formatFileSize(getApplicationContext(), cachesize);
	 	        	String code = Formatter.formatFileSize(getApplicationContext(), codesize);
	 	        	String data = Formatter.formatFileSize(getApplicationContext(), datasize);
	 	        	
	 	        	
	 	        	
	 	        	System.out.println("cachesize:"+cache +" codesize:"+code+" datasize:"+data);
	 	        }
	 		};
	 		
	 		//�����ȡ����
	 		try {
	 			Class<?> loadClass = ClearActivity.class.getClassLoader().loadClass("android.content.pm.PackageManager");
	 			Method method = loadClass.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
	 			//receiver : ���ʵ��,���ز���,�������Ǿ�̬�ı���ָ��
	 			method.invoke(pm, "com.example.writecache",mStatsObserver);
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
			return cache;
	   }
/**
 * �ҿؼ�
 */
	private void initUI() {
		 pb=(ProgressBar)findViewById(R.id.progressBar1);
		 tv=(TextView)findViewById(R.id.textview1);
		
	}

}
