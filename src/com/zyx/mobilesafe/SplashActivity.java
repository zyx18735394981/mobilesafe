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
 * �ص�  �����û����°汾
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
	 * �Ի���  ���쳣�Ĵ���
	 */
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("���°汾");
				//�����Ի���		
				showdialog();	
				break;
			case 2:
				System.out.println("û���°汾");
				//����������	
				enterHome();
				break;
			case 3:
				System.out.println("����ʧ��");
				//�����������쳣
				Toast.makeText(getApplicationContext(), "�������쳣~", 0).show();
				enterHome();
				break;
			case 4:
				System.out.println("URL�쳣");
				//URL�쳣~
				Toast.makeText(getApplicationContext(), "�����:404", 0).show();
				enterHome();
				break;
			case 5:
				System.out.println("IO�쳣");
				//IO�쳣
				Toast.makeText(getApplicationContext(), "����û������", 0).show();
				enterHome();
				break;
			case 6:
				System.out.println("Json�����쳣");
				//Json�����쳣
				Toast.makeText(getApplicationContext(), "�����:500", 0).show();
				enterHome();
				break;
			}
		
			
		};
	};
/**
 * ���
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        CopyDB.copyDb(getApplicationContext(), "antivirus.db");
        tv_spalsh_plan=(TextView)findViewById(R.id.tv_spalsh_plan);
        
    tv_splash_verversionname=(TextView)findViewById(R.id.tv_splash_versionname);
   
    tv_splash_verversionname.setText("�汾��:"+getVersionName());//��̬��ȡ�嵥�ļ��İ汾��
    
    //Ƕ����
    AdManager.getInstance(this).init("c8ec50fbb5c55bf6", "3edb95ece404d59c",false);
  
    SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
    
    if(sp.getBoolean("update", true)){
    	 Update();//�����û����°汾
   }else{
    	//��ת��������
    	//˯2������  ���������߳�ȥ˯����Ϊ���߳�������Ⱦ����Ĳ�����������߳�˯���룬û�취��Ⱦ������
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
     * �����Ի���
     */
    protected void showdialog() {
    	
		AlertDialog.Builder builder=new Builder(this);
		//���öԻ�������ʧ
    	builder.setCancelable(false);
		//���öԻ���ı���
		builder.setTitle("�°汾"+version_code);
		//���öԻ����ͼ��
		builder.setIcon(R.drawable.title);
		//���öԻ����������Ϣ
		builder.setMessage(version_des);
		//�������� ȡ���İ�ť
		builder.setPositiveButton("����", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			      //�������֮��ִ�еĲ���
				//3.�������°汾
				download();
			}
		});
		
		//����ȡ����ť
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ���ȡ��ִ�еĲ���
				//��ת�������� ----���ضԻ�����ת������
				dialog.dismiss();//���ضԻ���
				//��ת������
				enterHome();
				
			}
		});
		
		//��ʾ�Ի���
		builder.show();
		
	}
    /**
     * ����������������°汾
     */
    protected void download() { 
		
    	HttpUtils httputils=new HttpUtils();
    	//�ж�SD���Ƿ����
 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
    		
    		System.out.println("�������صķ���,׼��·��");
    		System.out.println("version_url==="+version_url);
    		
    	
    	//url ���ص�·��
    	//target �����°汾��·��
    	//callback requestCallBack
    	httputils.download(version_url, "mnt/sdcard/1.apk", new RequestCallBack<File>() {
			
    		//���سɹ����õķ���
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				System.out.println("���سɹ���~׼����װ��");
				//��װ���°汾
				installAPK();
				
			}
			//����ʧ�ܵ��õķ���
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
				System.out.println("����ʧ����~");
			}
			
		    
			
			//��ǰ���ؽ��ȵĲ���
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				
				System.out.println("��������~");
				
				// total�����ܽ���  current���ص�ǰ����  isUploading�Ƿ�֧�ֶϵ�����
				
				
				//�������ؿؼ��ɼ�,ͬʱ������Ӧ�����ؽ���
				tv_spalsh_plan.setVisibility(View.VISIBLE);//���ÿɼ�
				tv_spalsh_plan.setText(current+"/"+total);//  30/100
				
			}
			
		});
    	
    	
    	}	
		
	}
    
    /**
     *  ��װ���µİ汾
     */
	protected void installAPK() {
		Intent intent=new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
	/*	intent.setData(Uri.fromFile(new File("mnt/sdcard/1.apk")));
		intent.setType("application/vnd.android.package-archive");//����ͬʱ���� �������û��໥����
		*/
		intent.setDataAndType(Uri.fromFile(new File("mnt/sdcard/1.apk")), "application/vnd.android.package-archive");
		//�ڵ�ǰactivity�˳���ʱ�򣬻����activity��onActivityResult����   
		//requestCode  ������  ��ʾ���ĸ�activity��ת������
		//ABC   a--c  b--c   cҪ����intent��˭��ת�����ģ����õ���������
		startActivityForResult(intent, 0);
		System.out.println("��װ�ɹ���~");
		
		
	}
	/**
	 * ���밲װ�Ľ����ȡ���� �ص�������
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//����������
		enterHome();
	}
	/**
     *��ת������ 
     */
	protected void enterHome() {
		Intent intent=new Intent(this,HomeActivity2.class);
		startActivity(intent);
		finish();//����splash�������
		
	}
	/**
     * �����û����°汾
     */
    private void Update(){
    	//1.���ӷ��������鿴�Ƿ������°汾   ������������ʱ������4.0�Ժ����������߳��в���
    	new Thread(){
    		public void run() {
    			//������֮ǰ��ȡһ��ʱ��
       		 startTime=System.currentTimeMillis();
    			
    			Message age=Message.obtain();
  		//1.1   ���ӷ�����     
    			 //1.1.1��������·��
    			try {
					URL url=new URL("http://192.168.10.130:8080/update.html");
				 //1.1.2 ��ȡ���Ӳ���
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();//httpЭ��
				 //1.1.3���ó�ʱʱ��
					conn.setConnectTimeout(5000);//�������ӳ�ʱʱ��
					//conn.setReadTimeout(5000);//���ö�ȡ��ʱʱ��
				//1.1.4��������ʽ
					conn.setRequestMethod("GET");
				//1.1.5 ��ȡ���������ص�״̬�� 200 404 500
					int code=conn.getResponseCode();
					if(code==200){
						//���ӳɹ�,��ȡ������������������,code �°汾�İ汾��   apkUrl�°汾������·��   desinfo������Ϣ:�Ż�����Щ��Ϣ���޸�����Щbug
						//��ȡ����֮ǰ������Ҫ֪������������η�װ���ݵ�  xml  json
						//json��װ   {"code":"2.0","apkUrl":"XXXXXXX";"des":"���°汾��������~"}
						
						System.out.println("���ӳɹ�!");
						//��ȡ���������ص�����
						InputStream is=conn.getInputStream();//����Ϣ
						//����ȡ����is����Ϣת�����ַ���-----���ڹ���������ȥִ��
					  String json=StremUtil.parstreamUtil(is);
						//����json
					  try {
						JSONObject obj=new JSONObject(json);
						//��ȡ����
						 version_code=obj.getString("code");
						 version_url=obj.getString("apkUrl");
						 version_des=obj.getString("des");
		 System.out.println(version_code+version_url+version_des);
					//1.2 �鿴�Ƿ����°汾
						 //�жϷ����������������ݸ���ǰ�õ��İ汾�űȽ�
						 if(version_code.equals(getVersionName())){
							 //һ�µĻ�û�����°汾	 
							 System.out.println("û���°汾");
							 
							 age.what=2;//������Ϣ
							 
						 }else{
							 System.out.println("���°汾,ȥ����");
							 //�����°汾
							 //2.�����Ի��������û����°汾
					  //��ǰ�����߳��� ���ܵ����Ի���  ���̲߳��ܸ������̵߳�ui
							 //����handler ��handler����һ����Ϣ
							age.what=1;
					 
						 }
					} catch (JSONException e) {
						// json�����쳣
						age.what=6;
						e.printStackTrace();
					}
				
						
					}else{
						//����ʧ��
						
						System.out.println("����ʧ��!");
						age.what=3;
					}
					
					
				} catch (MalformedURLException e) {
					// ·���쳣
					e.printStackTrace();
					age.what=4;
					
				} catch (IOException e) {
					//io�쳣
					e.printStackTrace();
					age.what=5;
				}finally{
					
					//����������������ʱ�������
					//�ڻ�ȡ�ɹ������ٻ�ȡʱ��
			 endTime=System.currentTimeMillis();
			
			long dTime=endTime-startTime;//ȷ���ӳٵ�ʱ��ʼ����2s
			
			if(dTime<2000){
				//˯������
				SystemClock.sleep(2000-dTime);//��֤ʱ�Ӷ���˯����
			}
					
			
					//������û���쳣����ִ��
					handler.sendMessage(age);//�����ɹ�ʧ�ܶ���handler������Ϣ
					
					
				}
    			
    			
    			
    		};
    		
    	}.start();
    	
    	
    	
    	
    }
    
    
    
    
/**
 * ��ȡ��ǰӦ�ó���İ汾����
 */
    private String getVersionName(){
    	//ͨ��������ȡ�嵥�ļ�����Ϣ,���صľ��Ǳ������嵥�ļ���javaBean 
    	String versionName=null;
    	
    	try {
    		PackageManager pm= getPackageManager();
        	//packageName����   getPageName() ��ȡ������
    		//flagsָ����Ϣ��ǩ   0�����ȡһЩ��������Ϣ   �������汾��   
    		//��ȡȨ�޵ı�ǩ   GET_PERMISSION  ������Ϣ_Ȩ��
		PackageInfo info=pm.getPackageInfo("com.zyx.mobilesafe", 0);
		
	     	versionName=info.versionName;
    	} catch (NameNotFoundException e) {
			// �����Ҳ������쳣
			e.printStackTrace();
		}
    	return versionName;
    } 

}