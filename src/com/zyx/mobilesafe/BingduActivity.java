package com.zyx.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zyx.mobilesafe.dao.BingDuDao;
import com.zyx.mobilesafe.utils.MD5Util;

public class BingduActivity extends Activity {
	private ImageView iv_img1;
	private PackageManager pm;
	private  ProgressBar progressbar1;
	private TextView tv_title;
	private  LinearLayout ll;
	private List<String> bungdulist;//存放有病毒的应用名称
	private boolean b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_bingdu);
		
		//找控件
		initUI();
		
		//动画旋转
		AnimationIV();
		
		//扫描程序，设置进度条进度
		Scanner();
	}
	/**
	 * //扫描程序，设置进度条进度
	 */
	private void Scanner() {
		 pm=getPackageManager();
		//获取手机里的app是耗时操作，放入子线程里面
		new Thread(){
			public void run() {
		List<PackageInfo> list=pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
		//设置进度条最大长度
		progressbar1.setMax(list.size());
		int count=0;
		//循环集合
	
		for(final PackageInfo info:list){
		
			SystemClock.sleep(100);
			count++;
			progressbar1.setProgress(count);
  final String name=info.applicationInfo.loadLabel(pm).toString();	
			//修改主线程的UI
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
		
				tv_title.setText("正在扫描:"+name);
				
				//将扫描的name显示在linearlayout里面
				TextView tv=new TextView(getApplicationContext());
					tv.setTextColor(Color.BLACK);
					tv.setText(name);
					tv.setTextSize(18);
					//把tv添加到线性布局中
					ll.addView(tv, 0);
					
					
					/**
					 * 开始正式杀毒
					 */
			//1.得到应用程序的特征码
	    	  Signature s[]=info.signatures;
		     //获取签名信息
	    	  String ss=s[0].toCharsString();
	    	//对签名信息进行MD5加密
	    	  String ss2=MD5Util.passwordMD5(ss);
	    	  
	    	  //去病毒的数据库去比对
			 b=BingDuDao.queryBingdu(getApplicationContext(), ss2);
		
			//根据返回值将病毒标示出来
			if(b==true){
				//有病毒
				bungdulist=new ArrayList<String>();
				bungdulist.add(info.packageName);
			
				tv.setTextColor(Color.RED);
			
			}else{
				tv.setTextColor(Color.BLACK);
				bungdulist=new ArrayList<String>();
			}
				}
			});
			
		
			
		}
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
			
				if(bungdulist.size()>0){
					//扫描完成，旋转动画停止，显示扫描完成
					tv_title.setText("扫描完成,发现"+bungdulist.size()+"个病毒");
					
					iv_img1.clearAnimation();
					//弹出对话框
					AlertDialog.Builder builder=new Builder(BingduActivity.this);
					
					builder.setTitle("警告");
					builder.setIcon(R.drawable.title);
					builder.setMessage("发现病毒是否需要清理？");
					
					builder.setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//卸载应用
							dialog.dismiss();
							
							for(String packageName:bungdulist){
								//执行卸载操作
								Intent intent = new Intent();
								intent.setAction("android.intent.action.DELETE");
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setData(Uri.parse("package:"+packageName));
								startActivity(intent);
								
								tv_title.setText("病毒已经清理，系统已经安全");
							}
							
							
						}
					});
					
					
					builder.setNegativeButton("取消", null);
					
					AlertDialog dialog=builder.create();
				     dialog.show();
				}else{
					//扫描完成，旋转动画停止，显示扫描完成
					tv_title.setText("扫描完成,没有发现病毒");
					iv_img1.clearAnimation();
					
				}
				
				
				
		
				
			}
		});
		
				
			};
			
		}.start();
		
	
		
	}
	/**
	 * 设置动画旋转
	 */
private void AnimationIV() {
		RotateAnimation ra=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(3000);
		ra.setRepeatCount(Animation.INFINITE);
		//由于会卡，所以加入动画插补器
		LinearInterpolator li=new LinearInterpolator();
		ra.setInterpolator(li);
		iv_img1.startAnimation(ra);
		
	}
/**
 * 找控件
 */
	private void initUI() {
	 iv_img1=(ImageView)findViewById(R.id.iv_img1);
	 //进度条
	 progressbar1=(ProgressBar)findViewById(R.id.progressBar1);
	 
	 //textview
	 tv_title=(TextView)findViewById(R.id.tv_title);
	 
	  ll=(LinearLayout)findViewById(R.id.ll);
		
	}
}
