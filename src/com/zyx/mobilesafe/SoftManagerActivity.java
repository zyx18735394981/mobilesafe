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
		
		//找控件
    	  lv=(ListView)findViewById(R.id.lv);
    	 loading=(ProgressBar)findViewById(R.id.loading);
    	 tv_title=(TextView)findViewById(R.id.tv_title);
    	 
    	 //获取内存--找控件
    	 //应用总数
    	 tv_appnum1=(TextView)findViewById(R.id.tv_appnum1);
    	 //剩余空间
    	 tv_appnum2=(TextView)findViewById(R.id.tv_appnum2);
    	 
    //通过工具类获取sd卡跟手机内存的控件大小	
    	long SDRoom= AppUtil.getAvailableSD();
    	long phoneRoom= AppUtil.getAvailableROM();
    	
    	//数据转换
    	String SDsize=Formatter.formatFileSize(getApplicationContext(), SDRoom);
    	String phonesize= Formatter.formatFileSize(getApplicationContext(), phoneRoom);
    
    	//赋给控件
    	tv_appnum1.setText("SD卡可用空间:"+SDsize);
    	tv_appnum2.setText("内存可用空间:"+phonesize);
    	
    	
    	listViewOnSroll();
    	
    	
    	fillData();
    	
    	listViewItemClick();
    	
    	
    	
	}
	
	
	
	
	
	
	/**
	 * 给条目增加点击事件
	 */
	private void listViewItemClick() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view1, int position,
					long arg3) {

				//弹出气泡
				//1.屏蔽用户程序和系统程序(...个)弹出气泡
				if (position == 0 || position == Userlist.size()+1) {
					return;
				}
				//2.获取条目所对应的应用程序的信息
				//数据就要从userappinfo和systemappinfo中获取
				if (position <= Userlist.size()) {
					//用户程序
					info = Userlist.get(position-1);
				}else{
					//系统程序
					info = Systemlist.get(position - Userlist.size() - 2);
				}
				hiddenPop();
				
			
					View view=View.inflate(getApplicationContext(), R.layout.pop_item, null);
					
					//初始化控件
					LinearLayout ll_popuwindow_uninstall = (LinearLayout) view.findViewById(R.id.ll_popuwindow_uninstall);
					LinearLayout ll_popuwindow_start = (LinearLayout) view.findViewById(R.id.ll_popuwindow_start);
					LinearLayout ll_popuwindow_share = (LinearLayout) view.findViewById(R.id.ll_popuwindow_share);
					LinearLayout ll_popuwindow_detail = (LinearLayout) view.findViewById(R.id.ll_popuwindow_detail);
					//给控件设置点击事件
					ll_popuwindow_uninstall.setOnClickListener(SoftManagerActivity.this);
					ll_popuwindow_start.setOnClickListener(SoftManagerActivity.this);
					ll_popuwindow_share.setOnClickListener(SoftManagerActivity.this);
					ll_popuwindow_detail.setOnClickListener(SoftManagerActivity.this);
					
				
					
					//弹气泡
					 popupWindow =new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					
					 popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					int location[]=new int[2];
					view1.getLocationInWindow(location);
					int x=location[0];
					int y=location[1];
					popupWindow.showAtLocation(parent, Gravity.LEFT|Gravity.TOP, x+200, y);
					
					
					//给pop添加动画
					//缩放
					ScaleAnimation scale=new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0,Animation.RELATIVE_TO_SELF,0.5f);
					
					scale.setDuration(2000);
					//渐变
					AlphaAnimation alpha=new AlphaAnimation(0.5f, 1.0f);
					alpha.setDuration(2000);
					
					//组合动画
					AnimationSet set=new AnimationSet(true);
					set.addAnimation(scale);
					set.addAnimation(alpha);
					
					//执行动画
					view.startAnimation(set);
					
					
					
				}
				
			
			
		});
		
	}




/**
 * 隐藏pup
 */
private void hiddenPop(){
	if(popupWindow!=null){
		popupWindow.dismiss();
		popupWindow=null;
	}
}

/**
 * 在onDestroy里把pop隐藏
 */
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	hiddenPop();
}
	/**
	 * 设置textview浮动
	 */
	
	private void listViewOnSroll() {
	
		lv.setOnScrollListener(new OnScrollListener() {
			
			//滑动状态改变的时候调用
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				
			}
			//滑动的时候调用
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			
				
				//滑动的时候隐藏
				hiddenPop();
				
				if(Userlist!=null&&Systemlist!=null){
					if(firstVisibleItem>=Userlist.size()+1){
						//系统应用
						tv_title.setText("系统应用"+"("+Systemlist.size()+")");
						
					}else{
						//用户应用
						tv_title.setText("用户应用"+"("+Userlist.size()+")");
						
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
				//获取应用程序的集合
		         list=AppEngine.getAppInfos(getApplicationContext());
		    	//将系统应用跟用户应用分开
		         Userlist=new ArrayList<AppInfo>();
		    	Systemlist=new ArrayList<AppInfo>();
		    	for(int i=0;i<list.size();i++){
		    		if(list.get(i).isUser()){
		    			//用户应用
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
 * 缓存复用
 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			if (position == 0) {
				//添加用户程序(...个)textview
				TextView textView = new TextView(getApplicationContext());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				textView.setText("用户程序("+Userlist.size()+")");
				return textView;
			}else if(position == Userlist.size()+1){
				//添加系统程序(....个)textview
				TextView textView = new TextView(getApplicationContext());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				textView.setText("系统程序("+Systemlist.size()+")");
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
			//1.获取应用程序的信息
			AppInfo info=null;
			//数据就要从userappinfo和systemappinfo中获取
			if (position <= Userlist.size()) {
				//用户程序
				info = Userlist.get(position-1);
			}else{
				//系统程序
				info = Systemlist.get(position - Userlist.size() - 2);
			}
			//给控件赋值
			
			viewHolder.iv_icon.setImageDrawable(info.getIcon());
			viewHolder.tv_appname.setText(info.getName());
			viewHolder.tv_versionname.setText(info.getVersionName());
			if(info.isSD()){
				viewHolder.tv_softmanager_issd.setText("SD卡");
			}else{
				viewHolder.tv_softmanager_issd.setText("手机内存~");	
			}
		if(info.isUser()){
			viewHolder.tv_isUser.setText("用户程序");
		}else{
			viewHolder.tv_isUser.setText("系统程序");
		}

			return view;
		}
		
		
		
	}
	
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_versionname,tv_softmanager_issd,tv_appname,tv_isUser;
		
	}
/**
 * 设置点击事件
 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_popuwindow_uninstall:
			System.out.println("卸载");
			uninstall();
			break;
		case R.id.ll_popuwindow_start:
			System.out.println("启动");
			start();
			break;
		case R.id.ll_popuwindow_share:
			System.out.println("分享");
			share();
			break;
		case R.id.ll_popuwindow_detail:
			System.out.println("详情");
			detail();
			break;
		}
		
	}

			
			
			/**
			 * 详情
			 */
			private void detail() {
				Intent intent=new Intent();
				intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
				intent.setData(Uri.parse("package:"+info.getPackageName()));
				startActivity(intent);
				
				
			}




			
			/**
			 * 分享
			 */
			private void share() {
				Intent intent=new Intent();
				intent.setAction("android.intent.action.SEND");
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, "发现一个很牛x软件"+info.getName()+",下载地址:www.baidu.com");
				startActivity(intent);
				
				
			}
			
			
			
			
			
			
			/**
			 * 启动
			 */
			private void start() {
			PackageManager pm=getPackageManager();
		Intent intent=pm.getLaunchIntentForPackage(info.getPackageName());
				if(intent!=null){
					startActivity(intent);
				}else{
					//有些系统程序由于是用c c++写的所以intent会返回空
					ToastUtils.showToast(getApplicationContext(), "系统核心程序，无法启动");
				}
			}
			
			
			
			
			
			
			/**
			 * 卸载操作
			 */
			private void uninstall() {
				if(info.isUser()){
					if(!info.getPackageName().equals(getPackageName())){
						System.out.println("准备xiezai ");
						Intent intent=new Intent();
						intent.setAction("android.intent.action.DELETE");
						intent.addCategory("android.intent.category.DEFAULT");
						intent.setData(Uri.parse("package:"+info.getPackageName()));
						//startActivity(intent);
						startActivityForResult(intent, 0);
						
					}else{
						ToastUtils.showToast(getApplicationContext(), "不能卸载手机卫士~");
					}
					
				}else{
					ToastUtils.showToast(getApplicationContext(), "不能卸载系统程序");
					
				}
				
			}
			
			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				
				super.onActivityResult(requestCode, resultCode, data);
				//进行更新
				fillData();
			}

}
