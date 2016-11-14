package com.zyx.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyx.mobilesafe.engine.AppEngine;
import com.zyx.mobilesafe.entity.AppInfo;
import com.zyx.mobilesafe.utils.AppUtil;
import com.zyx.mobilesafe.utils.MyAsyTaksUtils;
import com.zyx.mobilesafe.utils.ToastUtils;

public class SoftManagerActivity extends Activity implements OnClickListener{
	private List<AppInfo> list;
	private ProgressBar loading;
	private ListView lv;
	private List<AppInfo> Userlist;
	private List<AppInfo> Systemlist;
	private TextView tv_title;
	private PopupWindow popupWindow ;
	private AppInfo info;
	private MyAdapter adapter;
	private TextView tv_appnum1;
	private TextView tv_appnum2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_soft);
		
		//�ҿؼ�
    	  lv=(ListView)findViewById(R.id.lv);
    	 loading=(ProgressBar)findViewById(R.id.loading);
    	 tv_title=(TextView)findViewById(R.id.tv_title);
    	 
    	 //��ȡ�ڴ�--�ҿؼ�
    	 //Ӧ������
    	 tv_appnum1=(TextView)findViewById(R.id.tv_appnum1);
    	 //ʣ��ռ�
    	 tv_appnum2=(TextView)findViewById(R.id.tv_appnum2);
    	 
    //ͨ���������ȡsd�����ֻ��ڴ�Ŀؼ���С	
    	long SDRoom= AppUtil.getAvailableSD();
    	long phoneRoom= AppUtil.getAvailableROM();
    	
    	//����ת��
    	String SDsize=Formatter.formatFileSize(getApplicationContext(), SDRoom);
    	String phonesize= Formatter.formatFileSize(getApplicationContext(), phoneRoom);
    
    	//�����ؼ�
    	tv_appnum1.setText("SD�����ÿռ�:"+SDsize);
    	tv_appnum2.setText("�ڴ���ÿռ�:"+phonesize);
    	
    	
    	listViewOnSroll();
    	
    	
    	fillData();
    	
    	listViewItemClick();
    	
    	
    	
	}
	
	
	
	
	
	
	/**
	 * ����Ŀ���ӵ���¼�
	 */
	private void listViewItemClick() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view1, int position,
					long arg3) {

				//��������
				//1.�����û������ϵͳ����(...��)��������
				if (position == 0 || position == Userlist.size()+1) {
					return;
				}
				//2.��ȡ��Ŀ����Ӧ��Ӧ�ó������Ϣ
				//���ݾ�Ҫ��userappinfo��systemappinfo�л�ȡ
				if (position <= Userlist.size()) {
					//�û�����
					info = Userlist.get(position-1);
				}else{
					//ϵͳ����
					info = Systemlist.get(position - Userlist.size() - 2);
				}
				hiddenPop();
				
			
					View view=View.inflate(getApplicationContext(), R.layout.pop_item, null);
					
					//��ʼ���ؼ�
					LinearLayout ll_popuwindow_uninstall = (LinearLayout) view.findViewById(R.id.ll_popuwindow_uninstall);
					LinearLayout ll_popuwindow_start = (LinearLayout) view.findViewById(R.id.ll_popuwindow_start);
					LinearLayout ll_popuwindow_share = (LinearLayout) view.findViewById(R.id.ll_popuwindow_share);
					LinearLayout ll_popuwindow_detail = (LinearLayout) view.findViewById(R.id.ll_popuwindow_detail);
					//���ؼ����õ���¼�
					ll_popuwindow_uninstall.setOnClickListener(SoftManagerActivity.this);
					ll_popuwindow_start.setOnClickListener(SoftManagerActivity.this);
					ll_popuwindow_share.setOnClickListener(SoftManagerActivity.this);
					ll_popuwindow_detail.setOnClickListener(SoftManagerActivity.this);
					
				
					
					//������
					 popupWindow =new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					
					 popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					int location[]=new int[2];
					view1.getLocationInWindow(location);
					int x=location[0];
					int y=location[1];
					popupWindow.showAtLocation(parent, Gravity.LEFT|Gravity.TOP, x+200, y);
					
					
					//��pop��Ӷ���
					//����
					ScaleAnimation scale=new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF,0.5f);
					
					scale.setDuration(2000);
					//����
					AlphaAnimation alpha=new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(2000);
					
					//��϶���
					AnimationSet set=new AnimationSet(true);
					set.addAnimation(scale);
					set.addAnimation(alpha);
					
					//ִ�ж���
					view.startAnimation(set);
					
					
					
				}
				
			
			
		});
		
	}




/**
 * ����pup
 */
private void hiddenPop(){
	if(popupWindow!=null){
		popupWindow.dismiss();
		popupWindow=null;
	}
}

/**
 * ��onDestroy���pop����
 */
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	hiddenPop();
}
	/**
	 * ����textview����
	 */
	
	private void listViewOnSroll() {
	
		lv.setOnScrollListener(new OnScrollListener() {
			
			//����״̬�ı��ʱ�����
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				
			}
			//������ʱ�����
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			
				
				//������ʱ������
				hiddenPop();
				
				if(Userlist!=null&&Systemlist!=null){
					if(firstVisibleItem>=Userlist.size()+1){
						//ϵͳӦ��
						tv_title.setText("ϵͳӦ��"+"("+Systemlist.size()+")");
						
					}else{
						//�û�Ӧ��
						tv_title.setText("�û�Ӧ��"+"("+Userlist.size()+")");
						
					}
				}
				
			}
		});
	}









	private void fillData() {
		new MyAsyTaksUtils(){

			@Override
			public void preTask() {
				loading.setVisibility(View.VISIBLE);
				
				
			}

			@Override
			public void doingTask() {
				//��ȡӦ�ó���ļ���
		         list=AppEngine.getAppInfos(getApplicationContext());
		    	//��ϵͳӦ�ø��û�Ӧ�÷ֿ�
		         Userlist=new ArrayList<AppInfo>();
		    	Systemlist=new ArrayList<AppInfo>();
		    	for(int i=0;i<list.size();i++){
		    		if(list.get(i).isUser()){
		    			//�û�Ӧ��
		    			Userlist.add(list.get(i));
		    		}else{
		    			Systemlist.add(list.get(i));
		    		}
		    	}
				
			}

			@Override
			public void postTask() {
				if(adapter==null){
					 adapter=new MyAdapter();
						
						lv.setAdapter(adapter);
					
				}else{
					adapter.notifyDataSetChanged();
				}
				loading.setVisibility(View.INVISIBLE);
				
			}
			
		}.execute();
		
	}

	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return Userlist.size()+Systemlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
/**
 * ���渴��
 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			if (position == 0) {
				//����û�����(...��)textview
				TextView textView = new TextView(getApplicationContext());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				textView.setText("�û�����("+Userlist.size()+")");
				return textView;
			}else if(position == Userlist.size()+1){
				//���ϵͳ����(....��)textview
				TextView textView = new TextView(getApplicationContext());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				textView.setText("ϵͳ����("+Systemlist.size()+")");
				return textView;
			}
			
			View view=null;
			ViewHolder viewHolder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view=convertView;
				//jiebang
				viewHolder=(ViewHolder)view.getTag();
			}else{
				view=View.inflate(getApplicationContext(), R.layout.soft_item, null);
		         viewHolder=new ViewHolder();
		         viewHolder.iv_icon=(ImageView)view.findViewById(R.id.iv_icon);
		         viewHolder.tv_appname=(TextView)view.findViewById(R.id.tv_appname);
		         viewHolder.tv_versionname=(TextView)view.findViewById(R.id.tv_versionname);
		         viewHolder.tv_softmanager_issd=(TextView)view.findViewById(R.id.tv_softmanager_issd);
		         viewHolder.tv_isUser=(TextView)view.findViewById(R.id.tv_isUser);
		         //bangding
		         view.setTag(viewHolder);
			}
			//1.��ȡӦ�ó������Ϣ
			AppInfo info=null;
			//���ݾ�Ҫ��userappinfo��systemappinfo�л�ȡ
			if (position <= Userlist.size()) {
				//�û�����
				info = Userlist.get(position-1);
			}else{
				//ϵͳ����
				info = Systemlist.get(position - Userlist.size() - 2);
			}
			//���ؼ���ֵ
			
			viewHolder.iv_icon.setImageDrawable(info.getIcon());
			viewHolder.tv_appname.setText(info.getName());
			viewHolder.tv_versionname.setText(info.getVersionName());
			if(info.isSD()){
				viewHolder.tv_softmanager_issd.setText("SD��");
			}else{
				viewHolder.tv_softmanager_issd.setText("�ֻ��ڴ�~");	
			}
		if(info.isUser()){
			viewHolder.tv_isUser.setText("�û�����");
		}else{
			viewHolder.tv_isUser.setText("ϵͳ����");
		}

			return view;
		}
		
		
		
	}
	
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_versionname,tv_softmanager_issd,tv_appname,tv_isUser;
		
	}
/**
 * ���õ���¼�
 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_popuwindow_uninstall:
			System.out.println("ж��");
			uninstall();
			break;
		case R.id.ll_popuwindow_start:
			System.out.println("����");
			start();
			break;
		case R.id.ll_popuwindow_share:
			System.out.println("����");
			share();
			break;
		case R.id.ll_popuwindow_detail:
			System.out.println("����");
			detail();
			break;
		}
		
	}

			
			
			/**
			 * ����
			 */
			private void detail() {
				Intent intent=new Intent();
				intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
				intent.setData(Uri.parse("package:"+info.getPackageName()));
				startActivity(intent);
				
				
			}




			
			/**
			 * ����
			 */
			private void share() {
				Intent intent=new Intent();
				intent.setAction("android.intent.action.SEND");
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, "����һ����ţx���"+info.getName()+",���ص�ַ:www.baidu.com");
				startActivity(intent);
				
				
			}
			
			
			
			
			
			
			/**
			 * ����
			 */
			private void start() {
			PackageManager pm=getPackageManager();
		Intent intent=pm.getLaunchIntentForPackage(info.getPackageName());
				if(intent!=null){
					startActivity(intent);
				}else{
					//��Щϵͳ������������c c++д������intent�᷵�ؿ�
					ToastUtils.showToast(getApplicationContext(), "ϵͳ���ĳ����޷�����");
				}
			}
			
			
			
			
			
			
			/**
			 * ж�ز���
			 */
			private void uninstall() {
				if(info.isUser()){
					if(!info.getPackageName().equals(getPackageName())){
						System.out.println("׼��xiezai ");
						Intent intent=new Intent();
						intent.setAction("android.intent.action.DELETE");
						intent.addCategory("android.intent.category.DEFAULT");
						intent.setData(Uri.parse("package:"+info.getPackageName()));
						//startActivity(intent);
						startActivityForResult(intent, 0);
						
					}else{
						ToastUtils.showToast(getApplicationContext(), "����ж���ֻ���ʿ~");
					}
					
				}else{
					ToastUtils.showToast(getApplicationContext(), "����ж��ϵͳ����");
					
				}
				
			}
			
			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				
				super.onActivityResult(requestCode, resultCode, data);
				//���и���
				fillData();
			}

}
