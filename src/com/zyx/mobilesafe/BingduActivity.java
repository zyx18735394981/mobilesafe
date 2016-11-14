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
	private List<String> bungdulist;//����в�����Ӧ������
	private boolean b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_bingdu);
		
		//�ҿؼ�
		initUI();
		
		//������ת
		AnimationIV();
		
		//ɨ��������ý���������
		Scanner();
	}
	/**
	 * //ɨ��������ý���������
	 */
	private void Scanner() {
		 pm=getPackageManager();
		//��ȡ�ֻ����app�Ǻ�ʱ�������������߳�����
		new Thread(){
			public void run() {
		List<PackageInfo> list=pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
		//���ý�������󳤶�
		progressbar1.setMax(list.size());
		int count=0;
		//ѭ������
	
		for(final PackageInfo info:list){
		
			SystemClock.sleep(100);
			count++;
			progressbar1.setProgress(count);
  final String name=info.applicationInfo.loadLabel(pm).toString();	
			//�޸����̵߳�UI
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
		
				tv_title.setText("����ɨ��:"+name);
				
				//��ɨ���name��ʾ��linearlayout����
				TextView tv=new TextView(getApplicationContext());
					tv.setTextColor(Color.BLACK);
					tv.setText(name);
					tv.setTextSize(18);
					//��tv��ӵ����Բ�����
					ll.addView(tv, 0);
					
					
					/**
					 * ��ʼ��ʽɱ��
					 */
			//1.�õ�Ӧ�ó����������
	    	  Signature s[]=info.signatures;
		     //��ȡǩ����Ϣ
	    	  String ss=s[0].toCharsString();
	    	//��ǩ����Ϣ����MD5����
	    	  String ss2=MD5Util.passwordMD5(ss);
	    	  
	    	  //ȥ���������ݿ�ȥ�ȶ�
			 b=BingDuDao.queryBingdu(getApplicationContext(), ss2);
		
			//���ݷ���ֵ��������ʾ����
			if(b==true){
				//�в���
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
					//ɨ����ɣ���ת����ֹͣ����ʾɨ�����
					tv_title.setText("ɨ�����,����"+bungdulist.size()+"������");
					
					iv_img1.clearAnimation();
					//�����Ի���
					AlertDialog.Builder builder=new Builder(BingduActivity.this);
					
					builder.setTitle("����");
					builder.setIcon(R.drawable.title);
					builder.setMessage("���ֲ����Ƿ���Ҫ����");
					
					builder.setPositiveButton("ȷ��", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//ж��Ӧ��
							dialog.dismiss();
							
							for(String packageName:bungdulist){
								//ִ��ж�ز���
								Intent intent = new Intent();
								intent.setAction("android.intent.action.DELETE");
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setData(Uri.parse("package:"+packageName));
								startActivity(intent);
								
								tv_title.setText("�����Ѿ�����ϵͳ�Ѿ���ȫ");
							}
							
							
						}
					});
					
					
					builder.setNegativeButton("ȡ��", null);
					
					AlertDialog dialog=builder.create();
				     dialog.show();
				}else{
					//ɨ����ɣ���ת����ֹͣ����ʾɨ�����
					tv_title.setText("ɨ�����,û�з��ֲ���");
					iv_img1.clearAnimation();
					
				}
				
				
				
		
				
			}
		});
		
				
			};
			
		}.start();
		
	
		
	}
	/**
	 * ���ö�����ת
	 */
private void AnimationIV() {
		RotateAnimation ra=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(3000);
		ra.setRepeatCount(Animation.INFINITE);
		//���ڻῨ�����Լ��붯���岹��
		LinearInterpolator li=new LinearInterpolator();
		ra.setInterpolator(li);
		iv_img1.startAnimation(ra);
		
	}
/**
 * �ҿؼ�
 */
	private void initUI() {
	 iv_img1=(ImageView)findViewById(R.id.iv_img1);
	 //������
	 progressbar1=(ProgressBar)findViewById(R.id.progressBar1);
	 
	 //textview
	 tv_title=(TextView)findViewById(R.id.tv_title);
	 
	  ll=(LinearLayout)findViewById(R.id.ll);
		
	}
}
