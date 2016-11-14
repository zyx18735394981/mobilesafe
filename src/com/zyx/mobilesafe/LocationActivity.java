package com.zyx.mobilesafe;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

public class LocationActivity extends BaseActivity {

	public LocationClient mLocationClient=null;
	public BDLocationListener myListener=new MyLocationListener(); 
	
	public class MyLocationListener implements BDLocationListener {
		 //在这个方法里面接收定位结果
        @Override
        public void onReceiveLocation(BDLocation location) {
        	
        	if(location!=null){
        		MyLocationData.Builder builder = new MyLocationData.Builder();
        		builder.accuracy(location.getRadius());//设置半径
        		builder.direction(location.getDirection());//设置方向
        		builder.latitude(location.getLatitude());//设置纬度
        		builder.longitude(location.getLongitude());//设置经度
        	System.out.println("当前手机的纬度:"+location.getLatitude());	
        	System.out.println("当前手机的经度:"+location.getLongitude());
				MyLocationData data=builder.build(); 
        		baiduMap.setMyLocationData(data);//把定位数据显示到地图上面
        	}
           
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
                   sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
           
            System.out.println("BaiduLocationApiDem=="+sb.toString());
        }
	
	}
	

	@Override
	public void init() {
		//声明LocationClient类
		mLocationClient=new LocationClient(getApplicationContext());
		//注册监听
		mLocationClient.registerLocationListener((BDLocationListener) myListener);
		
		initLocation();
	
		 MyLocationConfiguration.LocationMode mode= MyLocationConfiguration.LocationMode.COMPASS;//罗盘模式
		//定位图层的配置
		boolean enableDirection=true;//设置允许显示方向
		BitmapDescriptor customMarker=BitmapDescriptorFactory.fromResource(R.drawable.photo2);//设置自定义图标
		baiduMap.setMyLocationEnabled(true);//开启定位图层
		MyLocationConfiguration config=new MyLocationConfiguration(mode, enableDirection, customMarker);
		baiduMap.setMyLocationConfigeration(config);
		
		//开始定位
		mLocationClient.start();

	}



@Override
protected void onPause() {
	super.onPause();
	//停止定位
		mLocationClient.stop();
}

	private void initLocation(){
        LocationClientOption option = new LocationClientOption();
      //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1s才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }



}



