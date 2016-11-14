package com.zyx.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zyx.mobilesafe.engine.TaskEngline;
import com.zyx.mobilesafe.entity.TaskInfo;
import com.zyx.mobilesafe.utils.MyAsyTaksUtils;
import com.zyx.mobilesafe.utils.TaskUtil;
/**
 * 进程管理模块
 * @author Administrator
 *
 */
public class TaskActivity extends Activity{
	private  ListView lv_task;
	private ProgressBar loading;
	private List<TaskInfo>	list;
	private MyAdapter myAdapter;
	private TextView tv_title;
	private List<TaskInfo> UserList;
	private 	List<TaskInfo> SystemList;
	private int appnum1;
	private  long appnum22;
	private  TextView tv_appnum2;
	private  TextView tv_appnum1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.task_activity);
		//找到listview控件
        lv_task=(ListView)findViewById(R.id.lv_task);
        loading=(ProgressBar)findViewById(R.id.loading);
        tv_title=(TextView)findViewById(R.id.tv_title);
        
        //找空间 总共多少个进程  内存空间
         tv_appnum1=(TextView)findViewById(R.id.tv_appnum1);
         tv_appnum2=(TextView)findViewById(R.id.tv_appnum2);
        //给进程个数赋值
         appnum1=TaskUtil.getTaskCount(getApplicationContext());
        tv_appnum1.setText("进程总数:"+appnum1);
        //给进程所占内存大小赋值
        /**
         * 根据SDK的版本去选择对应的方法
         */
        //获取android SDK的版本
       
        int sdk=android.os.Build.VERSION.SDK_INT;
        if(sdk>=16){
        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
        }else{
        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
        }
        
        
       long  appnum2=TaskUtil.getTaskRoom(getApplicationContext());
        //数据转换
       String a1= Formatter.formatFileSize(getApplicationContext(), appnum2);
       String a2= Formatter.formatFileSize(getApplicationContext(), appnum22);
        
        
        tv_appnum2.setText("剩余/总内存:"+a1+"/"+a2);
        
        
        
        
        
        //给listview填充数据
        fillData();
        //设置textView滑动
        listViewOnSroll();
	}
	/**
	 * 设置textview浮动
	 */
	
	private void listViewOnSroll() {
	
		lv_task.setOnScrollListener(new OnScrollListener() {
			
			//滑动状态改变的时候调用
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				
			}
			//滑动的时候调用
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			
				
				
				if(UserList!=null&&SystemList!=null){
					if(firstVisibleItem>=UserList.size()+1){
						//系统应用
						tv_title.setText("系统进程"+"("+SystemList.size()+")");
						
					}else{
						//用户应用
						tv_title.setText("用户应用"+"("+UserList.size()+")");
						
					}
				}
				
			}
		});
	}
/**
 * 给listview设置数据
 */
	private void fillData() {
		new MyAsyTaksUtils() {
			
			@Override
			public void preTask() {
				loading.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void postTask() {
				//listview的适配器
				 myAdapter=new MyAdapter();
				lv_task.setAdapter(myAdapter);
				//隐藏进度
				loading.setVisibility(View.INVISIBLE);
				
			}
			
			@Override
			public void doingTask() {
				//获取数据
		 	list=TaskEngline.getTaskAllInfo(getApplicationContext());
		 	//创建两个集合--用户  系统
		 	 UserList=new ArrayList<TaskInfo>();
		     SystemList=new ArrayList<TaskInfo>();
		 	for(int i=0;i<list.size();i++){
		 		if(list.get(i).isUser()){
		 			//用户程序
		 			UserList.add(list.get(i));
		 		}else{
		 			SystemList.add(list.get(i));
		 		}
		 	}
				
				
			}
		}.execute();
		
		
	}
//缓存复用
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return UserList.size()+SystemList.size()+2;
		}

		@Override
		public Object getItem(int position) {
			
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(position==0){
				//系统进程
					TextView textView=new TextView(getApplicationContext());
					textView.setText("用户进程:"+UserList.size());
					textView.setBackgroundColor(Color.GRAY);
					textView.setTextColor(Color.WHITE);
					return textView;
				   
			}else if(position==UserList.size()+1){
				//系统进程
				TextView textView=new TextView(getApplicationContext());
				textView.setText("系统进程:"+SystemList.size());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				return textView;
			}
	        
			
			View view;
			ViewAdapter viewAdapter = null;
			if(convertView!=null && convertView instanceof RelativeLayout){
				view=convertView;
				//解绑
				viewAdapter=(ViewAdapter)view.getTag();
			}else{
				view=View.inflate(getApplicationContext(), R.layout.item_task, null);
			viewAdapter=new ViewAdapter();
			viewAdapter.iv_icon=(ImageView)view.findViewById(R.id.iv_icon);
			viewAdapter.tv_name=(TextView)view.findViewById(R.id.tv_name);
			viewAdapter.tv_packageName=(TextView)view.findViewById(R.id.tv_packageName);
			viewAdapter.tv_size=(TextView)view.findViewById(R.id.tv_size);
			viewAdapter.checkBox1=(CheckBox)view.findViewById(R.id.checkBox1);
			
			//绑定
		         view.setTag(viewAdapter);
			
			}
			
			//1.获取应用程序的信息
			TaskInfo info = null;
			//数据就要从userappinfo和systemappinfo中获取
			if (position <= UserList.size()) {
				if(position!=0){
					
				//用户程序
			
				info = UserList.get(position-1);
				}else{
				info = UserList.get(position);
				}
			}else{
				//系统程序
				info = SystemList.get(position - UserList.size() - 2);
			}
		//判断如果图标为空的话设置默认的图标
			if(info.getIcon()==null){
				viewAdapter.iv_icon.setImageResource(R.drawable.bg);
			}else{
				viewAdapter.iv_icon.setImageDrawable(info.getIcon());
			}
			//如果名称为空，设置为空字符串
			if(info.getName()==null){
				viewAdapter.tv_name.setText("");
			}else{
				viewAdapter.tv_name.setText(info.getName());
			}
			
			viewAdapter.tv_packageName.setText(info.getPackageName());
			
			//数据转化
			String formatFileSize = Formatter.formatFileSize(getApplicationContext(), info.getRomSize());
		
			viewAdapter.tv_size.setText("内存占用:"+formatFileSize);
			
			
			//因为checkbox的状态会跟着一起复用,所以一般要动态修改的控件的状态,不会跟着去复用,而是将状态保存到bean对象,在每次复用使用控件的时候
			//根据每个条目对应的bean对象保存的状态,来设置控件显示的相应的状态
			if (info.isChecked()) {
				viewAdapter.checkBox1.setChecked(true);
			}else{
				viewAdapter.checkBox1.setChecked(false);
			}
			//判断如果是我们的应用程序,就把checkbox隐藏,不是的话显示,在getview中有if必须有else
			if (info.getPackageName().equals(getPackageName())) {
			//	viewAdapter.checkBox1.setVisibility(View.INVISIBLE);
			}else{
				viewAdapter.checkBox1.setVisibility(View.VISIBLE);
			}
			
			return view;
		}
		
	}
static	class ViewAdapter{
		TextView tv_name,tv_packageName,tv_size;
		CheckBox checkBox1;
		ImageView iv_icon;
		
	}



/**
 * 给按钮设置点击事件
 * 
 */
//全选操作
		public void all(View v){
			
			for(int i=0;i<list.size();i++){
				
				 UserList.get(i).setChecked(true);
				
			}
			
			//更新界面
			myAdapter.notifyDataSetChanged();
		}
//反选操作
		public void cancel(View v){
			for(int i=0;i<list.size();i++){
				
				 if(list.get(i).isChecked()==true){
					 list.get(i).setChecked(false);
				 }else{
					 list.get(i).setChecked(true);
				 }
				
			}
			myAdapter.notifyDataSetChanged();
			
		}
//清理
		public void clear(View v){
			//1.获取进程的管理者
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			//保存杀死进程信息的集合
			List<TaskInfo> deleteTaskInfos = new ArrayList<TaskInfo>();
			
			for (int i = 0; i < UserList.size(); i++) {
				if (UserList.get(i).isChecked()) {
					//杀死进程
					//packageName : 进程的包名
					//杀死后台进程
					am.killBackgroundProcesses(UserList.get(i).getPackageName());
					deleteTaskInfos.add(UserList.get(i));//将杀死的进程信息保存的集合中
				}
			}
			//系统进程
			for (int i = 0; i < SystemList.size(); i++) {
				if (SystemList.get(i).isChecked()) {
					//杀死进程
					//packageName : 进程的包名
					//杀死后台进程
					am.killBackgroundProcesses(SystemList.get(i).getPackageName());
					deleteTaskInfos.add(SystemList.get(i));//将杀死的进程信息保存的集合中
				}
			}
			long memory=0;
			//遍历deleteTaskInfos,分别从userappinfo和systemappinfo中删除deleteTaskInfos中的数据
			for (TaskInfo taskInfo : deleteTaskInfos) {
				if (taskInfo.isUser()) {
					UserList.remove(taskInfo);
				}else{
					SystemList.remove(taskInfo);
				}
				memory+=taskInfo.getRomSize();
			}
			//数据转化
			String deletesize = Formatter.formatFileSize(getApplicationContext(), memory);
			Toast.makeText(getApplicationContext(), "共清理"+deleteTaskInfos.size()+"个进程,释放"+deletesize+"内存空间", 0).show();
			
			//更改运行中的进程个数预计剩余总内存
			appnum1=appnum1-deleteTaskInfos.size();
			 tv_appnum1.setText("进程总数:"+appnum1);
			
			 //更改剩余内存和总内存
			 //给进程所占内存大小赋值
		        /**
		         * 根据SDK的版本去选择对应的方法
		         */
		        //获取android SDK的版本
		        long appnum22;
		        int sdk=android.os.Build.VERSION.SDK_INT;
		        if(sdk>=16){
		        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
		        }else{
		        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
		        }
		        
		        
		       long  appnum2=TaskUtil.getTaskRoom(getApplicationContext());
		        //数据转换
		       String a1= Formatter.formatFileSize(getApplicationContext(), appnum2);
		       String a2= Formatter.formatFileSize(getApplicationContext(), appnum22);
		        
		        
		        tv_appnum2.setText("剩余/总内存:"+a1+"/"+a2);
		        
			 
			 
			//为下次清理进程做准备
			deleteTaskInfos.clear();
			deleteTaskInfos=null;
			//更新界面
			myAdapter.notifyDataSetChanged();
		}
//设置
		public void setting(View v){
			
		}


}
