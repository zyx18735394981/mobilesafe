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
			         
				        //1.��ȡλ�õĹ�����
				        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
				        //2.1��ȡ��λ��ʽ  true �������п��õĶ�λ��ʽ  
				        List<String> list=locationManager.getProviders(true);
				        
				        for(String s:list){
				        	//gps:GPS��λ                 passive:���� ��վ��λ       network:wifi��λ
				        	System.out.println("��λ��ʽ��:"+s);
				        }
				        //2.2 ��ȡ��ѵĶ�λ��ʽ
				        Criteria c=new Criteria();
				        c.setAltitudeRequired(true);//�����Ƿ���Զ�λ����  ,��дtrueһ���᷵��GPS��λ��
				        String ss= locationManager.getBestProvider(c, true);
				        
				        System.out.println("��ѵĶ�λ��ʽ:"+ss);
				        //3.��λ����
				        //����gps��ʽ���ж�λ
				        locationManager.requestLocationUpdates(ss, 0, 0,listener );
				        
					
					
				}
				private class MyLocationListener implements LocationListener{
			//��λλ�øı�
					@Override
					public void onLocationChanged(Location location) {
					double latitude=location.getLatitude();//��ȡγ��,ƽ��
					double longitude=location.getLongitude();//��ȡ����
					System.out.println(latitude+"=="+longitude);
					//����ȫ���뷢��һ��������γ������Ķ���
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(sp.getString("phone", "18735194981"), null, "�ŵt���ֻ����ˣ������ֻ�λ��:����:"+longitude+"γ��:"+latitude, null, null);
					//ֹͣ����
					stopSelf();
				
				}
			//��λ�����õ�ʱ�����
				@Override
					public void onProviderDisabled(String provider) {
						
						
					}
			//��λ���õ�ʱ�����
					@Override
					public void onProviderEnabled(String provider) {
						
						
					}
					//��λ״̬�ı�	
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
