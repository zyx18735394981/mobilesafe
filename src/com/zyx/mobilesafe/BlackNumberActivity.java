package com.zyx.mobilesafe;

import java.util.List;

import com.zyx.mobilesafe.dao.BlackNumberDao;
import com.zyx.mobilesafe.entity.BlackNumberInfo;
import com.zyx.mobilesafe.service.BlackNumService;
import com.zyx.mobilesafe.service.GPSService;
import com.zyx.mobilesafe.utils.MyAsyTaksUtils;
import com.zyx.mobilesafe.utils.ToastUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;


public class BlackNumberActivity extends Activity {
	private ListView lv_blacknumber;
	private ProgressBar loading;
	private BlackNumberDao blackNumDao;
	private List<BlackNumberInfo> list;
	private MyAdapter myAdapter;
	private AlertDialog dialog;
	private int startIndex=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blacknumber);
		
		 blackNumDao=new BlackNumberDao(this);
		
		lv_blacknumber=(ListView)findViewById(R.id.lv_blacknumber);
		loading=(ProgressBar)findViewById(R.id.loading);
		
		fillData();
		
		//给listview增加滑动监听事件
		lv_blacknumber.setOnScrollListener(new OnScrollListener() {
			//当滑动状态改变时调用的方法
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//view --listview  scrollstate 滑动的状态
				//当listview静止的时候 判断界面最后一个条目是否是查询数据的最后一个条目  是--加载下一波数据   不是--进行其他操作
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE){
					//获取界面最后一个条目--第20个条目
					int position=lv_blacknumber.getLastVisiblePosition();
					//判断是否是查询数据的最后一个数据  0--19
					if(position==list.size()-1){
						//是--加载下一波数据
						//更新查询的起始位置  0--19 20--39
						startIndex+=20;
						fillData();
						
					}
				}
			}
			//当滑动的时候调用的方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	/**
	 * 加载数据---用异步加载方式查询数据库的操作
	 * 防止耗时操作的原因
	 */
	private void fillData() {
		new MyAsyTaksUtils() {
			
			@Override
			public void preTask() {
				System.out.println("子线程之前显示进度条");
				loading.setVisibility(View.VISIBLE);
				
				
			}
			
			@Override
			public void postTask() {
				System.out.println("在子线程后隐藏进度条");
				if(myAdapter==null){
					myAdapter=new MyAdapter();
					lv_blacknumber.setAdapter(myAdapter);
				}else{
					myAdapter.notifyDataSetChanged();//不是第一次加载，进行更新操作
				}
				
				//隐藏进度条
				loading.setVisibility(View.INVISIBLE);
				
				
			}
			
			@Override
			public void doingTask() {
				System.out.println("在子线程中获取数据");
				if(list==null){
					 list=blackNumDao.find(startIndex);
				}else{
					//不是第一次加载
					//addAll  将一个集合整和到另一个集合中
					list.addAll(blackNumDao.find(startIndex));
				}
			
				
			}
		}.execute();
		
	}
    
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return list.size();
		}
//获取条目对应的数据
		@Override
		public Object getItem(int position) {
			
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}
/**
 * listview复用缓存的操作
 */
		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			//复用缓存的操作
			View view=null;
			ViewHolder viewHolder;
			if(convertView!=null){
				view=convertView;
				//从view对象中得到控件的容器
				viewHolder=(ViewHolder)view.getTag();
			}else{
			    view=View.inflate(getApplicationContext(), R.layout.listview_blacknumber_item, null);
			    //创建控件的容器
			    viewHolder=new ViewHolder();
			    //把控件存放在容器中
			    viewHolder.tv_phone=(TextView)view.findViewById(R.id.tv_phone);
			    viewHolder.tv_mode=(TextView)view.findViewById(R.id.tv_mode);
			    viewHolder.iv_delete=(ImageView)view.findViewById(R.id.iv_delete);
			    //将容器和view对象绑定在一起
			    view.setTag(viewHolder);
			}
			

			//设置显示数据
			final BlackNumberInfo info=list.get(position);
			viewHolder.tv_phone.setText(info.getPhone());
			 int mode=list.get(position).getMode();
			 switch (mode) {
			case BlackNumberDao.ALL:
				
				viewHolder.tv_mode.setText("全部拦截");
				break;
            case BlackNumberDao.SMS:
            	viewHolder.tv_mode.setText("短信拦截");
				
				break;
            case BlackNumberDao.PHONE:
            	viewHolder.tv_mode.setText("电话拦截");
				
				break;

			}
			 //给删除imageview设置点击操作
			 /**
			  * 删除黑名单
			  */
			 viewHolder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//实现删除操作
					AlertDialog.Builder builder=new Builder(BlackNumberActivity.this);
					builder.setMessage("您确定要删除黑名单号码:"+info.getPhone()+"吗?");
					//设置确定和取消按钮
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//删除黑名单
							//1.删除数据库里的黑名单号码
							blackNumDao.delete(info.getPhone());
							//2.界面上删除该黑名单号码
							   //2.1 将存放有所有数据的list集合中删除相应的数据
							list.remove(position);//删除条目对应位置的数据
							   //2.2更新界面
							myAdapter.notifyDataSetChanged();//更新界面的操作
							//3 隐藏对话框
							dialog.dismiss();
							
						}
					});
					//取消按钮
					builder.setNegativeButton("取消", null);
					//显示对话框
					builder.show();
					
				}
			});
			
			return view;
		}
		
	}
	/**
	 * 存放的容器
	 */
	class ViewHolder{
		 TextView tv_phone,tv_mode;
		 ImageView  iv_delete;
		  
		
		
	}
	
	
	
	/**
	 * 添加黑名单操作
	 */
	
	public void addBlacknum(View v){
		//添加黑名单操作
		AlertDialog.Builder builder=new Builder(this);
		View view=View.inflate(getApplicationContext(), R.layout.dialog_add_blacknumber, null);
		//初始化控件
		final EditText et_phone=(EditText)view.findViewById(R.id.et_phone);
		final RadioGroup rg_group=(RadioGroup)view.findViewById(R.id.rg_group);
		Button bt_submit=(Button)view.findViewById(R.id.bt_submit);
		Button bt_cancel=(Button)view.findViewById(R.id.bt_cancel);
		//设置按钮的点击事件
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//添加黑名单操作
				//1.获取输入的黑名单号码
		String black_num=et_phone.getText().toString();
		if(TextUtils.isEmpty(black_num)){
			ToastUtils.showToast(getApplicationContext(), "请输入黑名单号码");
			return;
		}else{
			//2.获取拦截模式
			int mode=-1;
			int rg_id=rg_group.getCheckedRadioButtonId();//获取选中的id
			switch (rg_id) {
			case R.id.rb_phone:
				//电话拦截
				mode=BlackNumberDao.PHONE;
				break;
			case R.id.rb_sms:
				//短信拦截
				mode=BlackNumberDao.SMS;
				break;
			case R.id.rb_all:
				//全部拦截
				mode=BlackNumberDao.ALL;
				break;

			}
			//添加黑名单
			 //1.添加到数据库中
			blackNumDao.insert(black_num, mode);
			//2.添加到界面
			   //2.1添加到list集合里面
			//list.add(new BlackNumberInfo(black_num, mode));
			//要添加的参数放在listview的第0个位置
			list.add(0, new BlackNumberInfo(black_num, mode));
			   //2.2更新界面
			myAdapter.notifyDataSetChanged();
			dialog.dismiss();
			
		}
				
				
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//点击取消隐藏对话框
				dialog.dismiss();
				
			}
		});
		builder.setView(view);
		//builder.show();
	 dialog= builder.create();
	  dialog.show();
		
		
		
	}
}





