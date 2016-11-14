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
 * ѡ����ϵ�˽���
 * @author Administrator
 *
 */
public class ContactActivity extends Activity {
	private ListView lv_contact_contacts;
	private List<HashMap<String,String>> list;
	@ViewInject(R.id.loading)//ע���ʼ���ؼ�,����Spring��ͨ��ע�����ʽ����javaBean,ͨ������ִ����findViewById
	private ProgressBar loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		//������ ��ʼ���ؼ�
		ViewUtils.inject(this);//�������ؼ���ʼ��
		
		
/**
 * �첽���ؿ��--�����߳�ǰ �� ��Ĳ�����װ����������ȥִ��
 */
		new MyAsyTaksUtils() {
			
			@Override
			public void preTask() {
				//�ڼ�������֮ǰ����ʾ������---�����߳�֮ǰִ�еĲ���
				loading.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void postTask() {
				lv_contact_contacts.setAdapter(new MyAdapter());
				//���ݼ�����ɣ����ؽ�����
				loading.setVisibility(View.INVISIBLE);
				
			}
			
			@Override
			public void doingTask() {
				//�õ����е���ϵ��--�����߳���ִ��
				 list=getPhonePerson.getInfo(getApplicationContext());//��ʱ����--����취���������߳�
			
				
			}
		}.execute();
		
		lv_contact_contacts=(ListView)findViewById(R.id.lv_contact_contacts);//Ҳ������ע�����ʽȥ��ʼ������
		
		//��listview��������¼�
		lv_contact_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				//����ϵ�˺��봫��ǰһ������
				Intent intent=new Intent();
				intent.putExtra("num", list.get(position).get("phone"));
				//�����ݴ��ݸ����ð�ȫ�������
                   setResult(RESULT_OK, intent);
				//�����Ŀ����������
				finish();		
			}
		});
		
	}
/**
 * ��ʾ��������
 * @author Administrator
 *
 */
	class MyAdapter extends BaseAdapter{
//��ȡ��Ŀ�ĸ���
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
//��ȡ��Ŀ������
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
//��ȡ��Ŀ��id
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
		 //�ҿؼ�
            TextView name=(TextView)view.findViewById(R.id.tv_itemcontact_name);
		    
            TextView phone=(TextView)view.findViewById(R.id.tv_itemcontact_phone);
          //�õ�ֵ
		 String Preson_name=list.get(arg0).get("name");
		 String Preson_phone=list.get(arg0).get("phone");
		 //���ؼ���ֵ
		 name.setText(Preson_name);
		 phone.setText(Preson_phone);
		 
		 
			return view;
		}
		
	}

}
