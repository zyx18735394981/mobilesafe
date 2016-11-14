package com.zyx.mobilesafe;

import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zyx.mobilesafe.engine.getPhonePerson;
import com.zyx.mobilesafe.utils.MyAsyTaksUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * 选择联系人界面
 * @author Administrator
 *
 */
public class ContactActivity extends Activity {
	private ListView lv_contact_contacts;
	private List<HashMap<String,String>> list;
	@ViewInject(R.id.loading)//注解初始化控件,类似Spring，通过注解的形式生成javaBean,通过反射执行了findViewById
	private ProgressBar loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		//进度条 初始化控件
		ViewUtils.inject(this);//进度条控件初始化
		
		
/**
 * 异步加载框架--将子线程前 中 后的操作封装到工具类中去执行
 */
		new MyAsyTaksUtils() {
			
			@Override
			public void preTask() {
				//在加载数据之前，显示进度条---在子线程之前执行的操作
				loading.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void postTask() {
				lv_contact_contacts.setAdapter(new MyAdapter());
				//数据加载完成，隐藏进度条
				loading.setVisibility(View.INVISIBLE);
				
			}
			
			@Override
			public void doingTask() {
				//得到所有的联系人--在子线程中执行
				 list=getPhonePerson.getInfo(getApplicationContext());//耗时操作--解决办法：放在子线程
			
				
			}
		}.execute();
		
		lv_contact_contacts=(ListView)findViewById(R.id.lv_contact_contacts);//也可以用注解的形式去初始化界面
		
		//给listview添加项点击事件
		lv_contact_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				//把联系人号码传到前一个界面
				Intent intent=new Intent();
				intent.putExtra("num", list.get(position).get("phone"));
				//将数据传递给设置安全号码界面
                   setResult(RESULT_OK, intent);
				//点击条目，消除界面
				finish();		
			}
		});
		
	}
/**
 * 显示界面数据
 * @author Administrator
 *
 */
	class MyAdapter extends BaseAdapter{
//获取条目的个数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
//获取条目的数据
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
//获取条目的id
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view=null;
		 if(arg1!=null){
			 view=arg1;
			 
		 }else{
			 view=View.inflate(getApplicationContext(), R.layout.item_contact, null);
		 }
		 //找控件
            TextView name=(TextView)view.findViewById(R.id.tv_itemcontact_name);
		    
            TextView phone=(TextView)view.findViewById(R.id.tv_itemcontact_phone);
          //得到值
		 String Preson_name=list.get(arg0).get("name");
		 String Preson_phone=list.get(arg0).get("phone");
		 //给控件赋值
		 name.setText(Preson_name);
		 phone.setText(Preson_phone);
		 
		 
			return view;
		}
		
	}

}
