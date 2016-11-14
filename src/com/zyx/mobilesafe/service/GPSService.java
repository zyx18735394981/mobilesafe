package com.zyx.mobilesafe.service;
			
import java.util.List;
			
			
			
			
			
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
			
			public class GPSService extends Service{
				private  MyLocationListener listener;
				private  LocationManager locationManager;
				private SharedPreferences sp;
			
				@Override
				public IBinder onBind(Intent intent) {
					
					return null;
				}
			
				@Override
				public void onCreate() {
					
					super.onCreate();
					sp=getSharedPreferences("config",MODE_PRIVATE);
					 listener=new MyLocationListener();
			         
				        //1.获取位置的管理者
				        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
				        //2.1获取定位方式  true 返回所有可用的定位方式  
				        List<String> list=locationManager.getProviders(true);
				        
				        for(String s:list){
				        	//gps:GPS定位                 passive:被动 基站定位       network:wifi定位
				        	System.out.println("定位方式有:"+s);
				        }
				        //2.2 获取最佳的定位方式
				        Criteria c=new Criteria();
				        c.setAltitudeRequired(true);//设置是否可以定位海拔  ,或写true一定会返回GPS定位的
				        String ss= locationManager.getBestProvider(c, true);
				        
				        System.out.println("最佳的定位方式:"+ss);
				        //3.定位操作
				        //采用gps方式进行定位
				        locationManager.requestLocationUpdates(ss, 0, 0,listener );
				        
					
					
				}
				private class MyLocationListener implements LocationListener{
			//定位位置改变
					@Override
					public void onLocationChanged(Location location) {
					double latitude=location.getLatitude();//获取纬度,平行
					double longitude=location.getLongitude();//获取经度
					System.out.println(latitude+"=="+longitude);
					//给安全号码发送一个包含经纬度坐标的短信
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(sp.getString("phone", "18735194981"), null, "张祎欣手机丢了，现在手机位置:经度:"+longitude+"纬度:"+latitude, null, null);
					//停止服务
					stopSelf();
				
				}
			//定位不可用的时候调用
				@Override
					public void onProviderDisabled(String provider) {
						
						
					}
			//定位可用的时候调用
					@Override
					public void onProviderEnabled(String provider) {
						
						
					}
					//定位状态改变	
					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						
						
					}
					
				}
				
				@Override
				public void onDestroy() {
					
					super.onDestroy();
					locationManager.removeUpdates(listener);
				}
			}
