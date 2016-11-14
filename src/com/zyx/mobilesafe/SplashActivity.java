package com.zyx.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.youmi.android.AdManager;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zyx.mobilesafe.utils.CopyDB;
import com.zyx.mobilesafe.utils.StremUtil;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
/**
 * 重点  提醒用户更新版本
 * @author Administrator
 *
 */

public class SplashActivity extends Activity {
	private  TextView  tv_splash_verversionname;
	private  TextView   tv_spalsh_plan;
	private String version_code;
	private String version_url;
	private String version_des;
	private long startTime;
	private long endTime;
	/**
	 * 对话框  和异常的处理
	 */
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("有新版本");
				//弹出对话框		
				showdialog();	
				break;
			case 2:
				System.out.println("没有新版本");
				//跳到主界面	
				enterHome();
				break;
			case 3:
				System.out.println("连接失败");
				//服务器出现异常
				Toast.makeText(getApplicationContext(), "服务器异常~", 0).show();
				enterHome();
				break;
			case 4:
				System.out.println("URL异常");
				//URL异常~
				Toast.makeText(getApplicationContext(), "错误号:404", 0).show();
				enterHome();
				break;
			case 5:
				System.out.println("IO异常");
				//IO异常
				Toast.makeText(getApplicationContext(), "网络没有连接", 0).show();
				enterHome();
				break;
			case 6:
				System.out.println("Json解析异常");
				//Json解析异常
				Toast.makeText(getApplicationContext(), "错误号:500", 0).show();
				enterHome();
				break;
			}
		
			
		};
	};
/**
 * 入口
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        CopyDB.copyDb(getApplicationContext(), "antivirus.db");
        tv_spalsh_plan=(TextView)findViewById(R.id.tv_spalsh_plan);
        
    tv_splash_verversionname=(TextView)findViewById(R.id.tv_splash_versionname);
   
    tv_splash_verversionname.setText("版本号:"+getVersionName());//动态获取清单文件的版本号
    
    //嵌入广告
    AdManager.getInstance(this).init("c8ec50fbb5c55bf6", "3edb95ece404d59c",false);
  
    SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
    
    if(sp.getBoolean("update", true)){
    	 Update();//提醒用户更新版本
   }else{
    	//跳转到主界面
    	//睡2秒再跳  不能在主线程去睡，因为主线程是有渲染界面的操作，如果主线程睡两秒，没办法渲染界面了
   //	SystemClock.sleep(2000);
    	new Thread(){
    		public void run() {
    			SystemClock.sleep(2000);
    			enterHome();
    		};
    	}.start();
    	
    }
  
    }
    /**
     * 弹出对话框
     */
    protected void showdialog() {
    	
		AlertDialog.Builder builder=new Builder(this);
		//设置对话不能消失
    	builder.setCancelable(false);
		//设置对话框的标题
		builder.setTitle("新版本"+version_code);
		//设置对话框的图标
		builder.setIcon(R.drawable.title);
		//设置对话框的描述信息
		builder.setMessage(version_des);
		//设置升级 取消的按钮
		builder.setPositiveButton("升级", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			      //点击升级之后执行的操作
				//3.下载最新版本
				download();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 点击取消执行的操作
				//跳转到主界面 ----隐藏对话框，跳转主界面
				dialog.dismiss();//隐藏对话框
				//跳转主界面
				enterHome();
				
			}
		});
		
		//显示对话框
		builder.show();
		
	}
    /**
     * 点击升级后下载最新版本
     */
    protected void download() { 
		
    	HttpUtils httputils=new HttpUtils();
    	//判断SD卡是否挂载
 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    		
    		System.out.println("进入下载的方法,准备路径");
    		System.out.println("version_url==="+version_url);
    		
    	
    	//url 下载的路径
    	//target 保存新版本的路径
    	//callback requestCallBack
    	httputils.download(version_url, "mnt/sdcard/1.apk", new RequestCallBack<File>() {
			
    		//下载成功调用的方法
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				System.out.println("下载成功了~准备安装了");
				//安装最新版本
				installAPK();
				
			}
			//下载失败调用的方法
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
				System.out.println("下载失败了~");
			}
			
		    
			
			//当前下载进度的操作
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				
				System.out.println("正在下载~");
				
				// total下载总进度  current下载当前进度  isUploading是否支持断点续传
				
				
				//设置下载控件可见,同时设置相应的下载进度
				tv_spalsh_plan.setVisibility(View.VISIBLE);//设置可见
				tv_spalsh_plan.setText(current+"/"+total);//  30/100
				
			}
			
		});
    	
    	
    	}	
		
	}
    
    /**
     *  安装最新的版本
     */
	protected void installAPK() {
		Intent intent=new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
	/*	intent.setData(Uri.fromFile(new File("mnt/sdcard/1.apk")));
		intent.setType("application/vnd.android.package-archive");//不能同时存在 单独设置会相互覆盖
		*/
		intent.setDataAndType(Uri.fromFile(new File("mnt/sdcard/1.apk")), "application/vnd.android.package-archive");
		//在当前activity退出的时候，会调用activity的onActivityResult方法   
		//requestCode  请求码  标示从哪个activity跳转过来的
		//ABC   a--c  b--c   c要区分intent是谁跳转过来的，就用到请求码了
		startActivityForResult(intent, 0);
		System.out.println("安装成功了~");
		
		
	}
	/**
	 * 进入安装的界面点取消后 回到主界面
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//跳回主界面
		enterHome();
	}
	/**
     *跳转主界面 
     */
	protected void enterHome() {
		Intent intent=new Intent(this,HomeActivity2.class);
		startActivity(intent);
		finish();//结束splash这个界面
		
	}
	/**
     * 提醒用户更新版本
     */
    private void Update(){
    	//1.连接服务器，查看是否有最新版本   联网操作，耗时操作，4.0以后不允许在主线程中操作
    	new Thread(){
    		public void run() {
    			//在连接之前获取一个时间
       		 startTime=System.currentTimeMillis();
    			
    			Message age=Message.obtain();
  		//1.1   连接服务器     
    			 //1.1.1设置连接路径
    			try {
					URL url=new URL("http://192.168.10.130:8080/update.html");
				 //1.1.2 获取连接操作
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();//http协议
				 //1.1.3设置超时时间
					conn.setConnectTimeout(5000);//设置连接超时时间
					//conn.setReadTimeout(5000);//设置读取超时时间
				//1.1.4设置请求方式
					conn.setRequestMethod("GET");
				//1.1.5 获取服务器返回的状态码 200 404 500
					int code=conn.getResponseCode();
					if(code==200){
						//连接成功,获取服务器返回来的数据,code 新版本的版本号   apkUrl新版本的下载路径   desinfo升级信息:优化了那些信息，修改了那些bug
						//获取数据之前，还需要知道服务器是如何封装数据的  xml  json
						//json封装   {"code":"2.0","apkUrl":"XXXXXXX";"des":"有新版本快来升级~"}
						
						System.out.println("连接成功!");
						//获取服务器返回的数据
						InputStream is=conn.getInputStream();//流信息
						//将获取到的is流信息转换成字符串-----放在工具类里面去执行
					  String json=StremUtil.parstreamUtil(is);
						//解析json
					  try {
						JSONObject obj=new JSONObject(json);
						//获取数据
						 version_code=obj.getString("code");
						 version_url=obj.getString("apkUrl");
						 version_des=obj.getString("des");
		 System.out.println(version_code+version_url+version_des);
					//1.2 查看是否有新版本
						 //判断服务器返回来的数据跟当前得到的版本号比较
						 if(version_code.equals(getVersionName())){
							 //一致的话没有最新版本	 
							 System.out.println("没有新版本");
							 
							 age.what=2;//发送消息
							 
						 }else{
							 System.out.println("有新版本,去升级");
							 //有最新版本
							 //2.弹出对话框，提醒用户更新版本
					  //当前在子线程中 不能弹出对话框  子线程不能更改主线程的ui
							 //创建handler 给handler发送一个消息
							age.what=1;
					 
						 }
					} catch (JSONException e) {
						// json解析异常
						age.what=6;
						e.printStackTrace();
					}
				
						
					}else{
						//连接失败
						
						System.out.println("连接失败!");
						age.what=3;
					}
					
					
				} catch (MalformedURLException e) {
					// 路径异常
					e.printStackTrace();
					age.what=4;
					
				} catch (IOException e) {
					//io异常
					e.printStackTrace();
					age.what=5;
				}finally{
					
					//处理连接外网连接时间的问题
					//在获取成功后在再获取时间
			 endTime=System.currentTimeMillis();
			
			long dTime=endTime-startTime;//确保延迟的时间始终是2s
			
			if(dTime<2000){
				//睡两秒钟
				SystemClock.sleep(2000-dTime);//保证时钟都是睡两秒
			}
					
			
					//不管有没有异常都会执行
					handler.sendMessage(age);//不过成功失败都给handler发送消息
					
					
				}
    			
    			
    			
    		};
    		
    	}.start();
    	
    	
    	
    	
    }
    
    
    
    
/**
 * 获取当前应用程序的版本名称
 */
    private String getVersionName(){
    	//通过包名获取清单文件的信息,返回的就是保存有清单文件的javaBean 
    	String versionName=null;
    	
    	try {
    		PackageManager pm= getPackageManager();
        	//packageName包名   getPageName() 获取包名的
    		//flags指定信息标签   0代表获取一些基础的信息   包名，版本号   
    		//获取权限的标签   GET_PERMISSION  基本信息_权限
		PackageInfo info=pm.getPackageInfo("com.zyx.mobilesafe", 0);
		
	     	versionName=info.versionName;
    	} catch (NameNotFoundException e) {
			// 包名找不到的异常
			e.printStackTrace();
		}
    	return versionName;
    } 

}