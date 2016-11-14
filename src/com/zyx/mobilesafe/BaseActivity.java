package com.zyx.mobilesafe;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import com.baidu.mapapi.model.LatLng;
import com.zyx.mobilesafe.utils.ToastUtils;

import android.app.Activity;
import android.os.Bundle;


public abstract class BaseActivity extends Activity {
	protected MapView mMapView;
	protected  BaiduMap baiduMap;
	private double weidu=37.836934;
	private double jingdu=112.559513;
	
	protected LatLng zhongbei=new LatLng(38.015895, 112.448364);
	protected LatLng huakang=new LatLng(37.836934, 112.559513);
	protected LatLng home=new LatLng(35.889040, 111.433749);

	//加final是为了不让子类覆盖  原因是预防这里的一些类还没初始化就被子类调用
	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);  
	     setContentView(R.layout.activity_main);
	        //获取地图控件
	        mMapView=(MapView)findViewById(R.id.bmapView);//用于显示   
	        //获取地图的控制器
	       baiduMap=mMapView.getMap();//用于控制
	       
	       //1.隐藏缩放按钮、比例尺
	     //  mMapView.showScaleControl(false);//隐藏比例尺，默认显示
	     //  mMapView.showZoomControls(false);//隐藏缩放按钮，默认显示
	   
	      // 2.获取获取最小（3）、最大缩放级别（20）
	      float maxZoomLevel=baiduMap.getMaxZoomLevel();//	获取地图最大缩放级别
	      float minZoomLevel=baiduMap.getMinZoomLevel();//	获取地图最小缩放级别
	      System.out.println("max="+maxZoomLevel+"min="+minZoomLevel);//20  3.0
	      
	      // 3.设置地图中心点为太原-华康大厦   MapStatusUpdate没有构造方法  用工厂来创建   纬度37.836934  经度112.559513
	      MapStatusUpdate mapstatusUpdate=MapStatusUpdateFactory.newLatLng(new LatLng(weidu, jingdu));
	       baiduMap.setMapStatus(mapstatusUpdate);//改变地图状态
	     
	       // 4.设置地图缩放为15
	       mapstatusUpdate= MapStatusUpdateFactory.zoomTo(15);//缩放
	       baiduMap.setMapStatus(mapstatusUpdate);//改变地图状态
	       
	       //5.获取地图ui控制  指南针
	      /* UiSettings uiSettings=baiduMap.getUiSettings();
	       uiSettings.setCompassEnabled(false);//隐藏指南针
*/	       
	      //让子类去实现
	       init();
	}
	
	/**
	 * 让子类实现
	 */
	public abstract void init();
	
	/**
	 * 在屏幕中央显示一个Toast
	 * @param text
	 */
	public void showToast(CharSequence text){
		
		ToastUtils.showToast(this, text);
	}
	
 
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
	        mMapView.onResume();  
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
	        mMapView.onPause();  
	        }
     @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	        mMapView.onDestroy();  
	    
	    }  

	    

}
