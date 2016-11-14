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
 * ���̹���ģ��
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
		//�ҵ�listview�ؼ�
        lv_task=(ListView)findViewById(R.id.lv_task);
        loading=(ProgressBar)findViewById(R.id.loading);
        tv_title=(TextView)findViewById(R.id.tv_title);
        
        //�ҿռ� �ܹ����ٸ�����  �ڴ�ռ�
         tv_appnum1=(TextView)findViewById(R.id.tv_appnum1);
         tv_appnum2=(TextView)findViewById(R.id.tv_appnum2);
        //�����̸�����ֵ
         appnum1=TaskUtil.getTaskCount(getApplicationContext());
        tv_appnum1.setText("��������:"+appnum1);
        //��������ռ�ڴ��С��ֵ
        /**
         * ����SDK�İ汾ȥѡ���Ӧ�ķ���
         */
        //��ȡandroid SDK�İ汾
       
        int sdk=android.os.Build.VERSION.SDK_INT;
        if(sdk>=16){
        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
        }else{
        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
        }
        
        
       long  appnum2=TaskUtil.getTaskRoom(getApplicationContext());
        //����ת��
       String a1= Formatter.formatFileSize(getApplicationContext(), appnum2);
       String a2= Formatter.formatFileSize(getApplicationContext(), appnum22);
        
        
        tv_appnum2.setText("ʣ��/���ڴ�:"+a1+"/"+a2);
        
        
        
        
        
        //��listview�������
        fillData();
        //����textView����
        listViewOnSroll();
	}
	/**
	 * ����textview����
	 */
	
	private void listViewOnSroll() {
	
		lv_task.setOnScrollListener(new OnScrollListener() {
			
			//����״̬�ı��ʱ�����
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				
			}
			//������ʱ�����
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			
				
				
				if(UserList!=null&&SystemList!=null){
					if(firstVisibleItem>=UserList.size()+1){
						//ϵͳӦ��
						tv_title.setText("ϵͳ����"+"("+SystemList.size()+")");
						
					}else{
						//�û�Ӧ��
						tv_title.setText("�û�Ӧ��"+"("+UserList.size()+")");
						
					}
				}
				
			}
		});
	}
/**
 * ��listview��������
 */
	private void fillData() {
		new MyAsyTaksUtils() {
			
			@Override
			public void preTask() {
				loading.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void postTask() {
				//listview��������
				 myAdapter=new MyAdapter();
				lv_task.setAdapter(myAdapter);
				//���ؽ���
				loading.setVisibility(View.INVISIBLE);
				
			}
			
			@Override
			public void doingTask() {
				//��ȡ����
		 	list=TaskEngline.getTaskAllInfo(getApplicationContext());
		 	//������������--�û�  ϵͳ
		 	 UserList=new ArrayList<TaskInfo>();
		     SystemList=new ArrayList<TaskInfo>();
		 	for(int i=0;i<list.size();i++){
		 		if(list.get(i).isUser()){
		 			//�û�����
		 			UserList.add(list.get(i));
		 		}else{
		 			SystemList.add(list.get(i));
		 		}
		 	}
				
				
			}
		}.execute();
		
		
	}
//���渴��
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
				//ϵͳ����
					TextView textView=new TextView(getApplicationContext());
					textView.setText("�û�����:"+UserList.size());
					textView.setBackgroundColor(Color.GRAY);
					textView.setTextColor(Color.WHITE);
					return textView;
				   
			}else if(position==UserList.size()+1){
				//ϵͳ����
				TextView textView=new TextView(getApplicationContext());
				textView.setText("ϵͳ����:"+SystemList.size());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				return textView;
			}
	        
			
			View view;
			ViewAdapter viewAdapter = null;
			if(convertView!=null && convertView instanceof RelativeLayout){
				view=convertView;
				//���
				viewAdapter=(ViewAdapter)view.getTag();
			}else{
				view=View.inflate(getApplicationContext(), R.layout.item_task, null);
			viewAdapter=new ViewAdapter();
			viewAdapter.iv_icon=(ImageView)view.findViewById(R.id.iv_icon);
			viewAdapter.tv_name=(TextView)view.findViewById(R.id.tv_name);
			viewAdapter.tv_packageName=(TextView)view.findViewById(R.id.tv_packageName);
			viewAdapter.tv_size=(TextView)view.findViewById(R.id.tv_size);
			viewAdapter.checkBox1=(CheckBox)view.findViewById(R.id.checkBox1);
			
			//��
		         view.setTag(viewAdapter);
			
			}
			
			//1.��ȡӦ�ó������Ϣ
			TaskInfo info = null;
			//���ݾ�Ҫ��userappinfo��systemappinfo�л�ȡ
			if (position <= UserList.size()) {
				if(position!=0){
					
				//�û�����
			
				info = UserList.get(position-1);
				}else{
				info = UserList.get(position);
				}
			}else{
				//ϵͳ����
				info = SystemList.get(position - UserList.size() - 2);
			}
		//�ж����ͼ��Ϊ�յĻ�����Ĭ�ϵ�ͼ��
			if(info.getIcon()==null){
				viewAdapter.iv_icon.setImageResource(R.drawable.bg);
			}else{
				viewAdapter.iv_icon.setImageDrawable(info.getIcon());
			}
			//�������Ϊ�գ�����Ϊ���ַ���
			if(info.getName()==null){
				viewAdapter.tv_name.setText("");
			}else{
				viewAdapter.tv_name.setText(info.getName());
			}
			
			viewAdapter.tv_packageName.setText(info.getPackageName());
			
			//����ת��
			String formatFileSize = Formatter.formatFileSize(getApplicationContext(), info.getRomSize());
		
			viewAdapter.tv_size.setText("�ڴ�ռ��:"+formatFileSize);
			
			
			//��Ϊcheckbox��״̬�����һ����,����һ��Ҫ��̬�޸ĵĿؼ���״̬,�������ȥ����,���ǽ�״̬���浽bean����,��ÿ�θ���ʹ�ÿؼ���ʱ��
			//����ÿ����Ŀ��Ӧ��bean���󱣴��״̬,�����ÿؼ���ʾ����Ӧ��״̬
			if (info.isChecked()) {
				viewAdapter.checkBox1.setChecked(true);
			}else{
				viewAdapter.checkBox1.setChecked(false);
			}
			//�ж���������ǵ�Ӧ�ó���,�Ͱ�checkbox����,���ǵĻ���ʾ,��getview����if������else
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
 * ����ť���õ���¼�
 * 
 */
//ȫѡ����
		public void all(View v){
			
			for(int i=0;i<list.size();i++){
				
				 UserList.get(i).setChecked(true);
				
			}
			
			//���½���
			myAdapter.notifyDataSetChanged();
		}
//��ѡ����
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
//����
		public void clear(View v){
			//1.��ȡ���̵Ĺ�����
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			//����ɱ��������Ϣ�ļ���
			List<TaskInfo> deleteTaskInfos = new ArrayList<TaskInfo>();
			
			for (int i = 0; i < UserList.size(); i++) {
				if (UserList.get(i).isChecked()) {
					//ɱ������
					//packageName : ���̵İ���
					//ɱ����̨����
					am.killBackgroundProcesses(UserList.get(i).getPackageName());
					deleteTaskInfos.add(UserList.get(i));//��ɱ���Ľ�����Ϣ����ļ�����
				}
			}
			//ϵͳ����
			for (int i = 0; i < SystemList.size(); i++) {
				if (SystemList.get(i).isChecked()) {
					//ɱ������
					//packageName : ���̵İ���
					//ɱ����̨����
					am.killBackgroundProcesses(SystemList.get(i).getPackageName());
					deleteTaskInfos.add(SystemList.get(i));//��ɱ���Ľ�����Ϣ����ļ�����
				}
			}
			long memory=0;
			//����deleteTaskInfos,�ֱ��userappinfo��systemappinfo��ɾ��deleteTaskInfos�е�����
			for (TaskInfo taskInfo : deleteTaskInfos) {
				if (taskInfo.isUser()) {
					UserList.remove(taskInfo);
				}else{
					SystemList.remove(taskInfo);
				}
				memory+=taskInfo.getRomSize();
			}
			//����ת��
			String deletesize = Formatter.formatFileSize(getApplicationContext(), memory);
			Toast.makeText(getApplicationContext(), "������"+deleteTaskInfos.size()+"������,�ͷ�"+deletesize+"�ڴ�ռ�", 0).show();
			
			//���������еĽ��̸���Ԥ��ʣ�����ڴ�
			appnum1=appnum1-deleteTaskInfos.size();
			 tv_appnum1.setText("��������:"+appnum1);
			
			 //����ʣ���ڴ�����ڴ�
			 //��������ռ�ڴ��С��ֵ
		        /**
		         * ����SDK�İ汾ȥѡ���Ӧ�ķ���
		         */
		        //��ȡandroid SDK�İ汾
		        long appnum22;
		        int sdk=android.os.Build.VERSION.SDK_INT;
		        if(sdk>=16){
		        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
		        }else{
		        	 appnum22=TaskUtil.getSumRoom(getApplicationContext());
		        }
		        
		        
		       long  appnum2=TaskUtil.getTaskRoom(getApplicationContext());
		        //����ת��
		       String a1= Formatter.formatFileSize(getApplicationContext(), appnum2);
		       String a2= Formatter.formatFileSize(getApplicationContext(), appnum22);
		        
		        
		        tv_appnum2.setText("ʣ��/���ڴ�:"+a1+"/"+a2);
		        
			 
			 
			//Ϊ�´����������׼��
			deleteTaskInfos.clear();
			deleteTaskInfos=null;
			//���½���
			myAdapter.notifyDataSetChanged();
		}
//����
		public void setting(View v){
			
		}


}
