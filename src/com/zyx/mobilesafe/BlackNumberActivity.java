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
		
		//��listview���ӻ��������¼�
		lv_blacknumber.setOnScrollListener(new OnScrollListener() {
			//������״̬�ı�ʱ���õķ���
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//view --listview  scrollstate ������״̬
				//��listview��ֹ��ʱ�� �жϽ������һ����Ŀ�Ƿ��ǲ�ѯ���ݵ����һ����Ŀ  ��--������һ������   ����--������������
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE){
					//��ȡ�������һ����Ŀ--��20����Ŀ
					int position=lv_blacknumber.getLastVisiblePosition();
					//�ж��Ƿ��ǲ�ѯ���ݵ����һ������  0--19
					if(position==list.size()-1){
						//��--������һ������
						//���²�ѯ����ʼλ��  0--19 20--39
						startIndex+=20;
						fillData();
						
					}
				}
			}
			//��������ʱ����õķ���
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	/**
	 * ��������---���첽���ط�ʽ��ѯ���ݿ�Ĳ���
	 * ��ֹ��ʱ������ԭ��
	 */
	private void fillData() {
		new MyAsyTaksUtils() {
			
			@Override
			public void preTask() {
				System.out.println("���߳�֮ǰ��ʾ������");
				loading.setVisibility(View.VISIBLE);
				
				
			}
			
			@Override
			public void postTask() {
				System.out.println("�����̺߳����ؽ�����");
				if(myAdapter==null){
					myAdapter=new MyAdapter();
					lv_blacknumber.setAdapter(myAdapter);
				}else{
					myAdapter.notifyDataSetChanged();//���ǵ�һ�μ��أ����и��²���
				}
				
				//���ؽ�����
				loading.setVisibility(View.INVISIBLE);
				
				
			}
			
			@Override
			public void doingTask() {
				System.out.println("�����߳��л�ȡ����");
				if(list==null){
					 list=blackNumDao.find(startIndex);
				}else{
					//���ǵ�һ�μ���
					//addAll  ��һ���������͵���һ��������
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
//��ȡ��Ŀ��Ӧ������
		@Override
		public Object getItem(int position) {
			
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}
/**
 * listview���û���Ĳ���
 */
		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			//���û���Ĳ���
			View view=null;
			ViewHolder viewHolder;
			if(convertView!=null){
				view=convertView;
				//��view�����еõ��ؼ�������
				viewHolder=(ViewHolder)view.getTag();
			}else{
			    view=View.inflate(getApplicationContext(), R.layout.listview_blacknumber_item, null);
			    //�����ؼ�������
			    viewHolder=new ViewHolder();
			    //�ѿؼ������������
			    viewHolder.tv_phone=(TextView)view.findViewById(R.id.tv_phone);
			    viewHolder.tv_mode=(TextView)view.findViewById(R.id.tv_mode);
			    viewHolder.iv_delete=(ImageView)view.findViewById(R.id.iv_delete);
			    //��������view�������һ��
			    view.setTag(viewHolder);
			}
			

			//������ʾ����
			final BlackNumberInfo info=list.get(position);
			viewHolder.tv_phone.setText(info.getPhone());
			 int mode=list.get(position).getMode();
			 switch (mode) {
			case BlackNumberDao.ALL:
				
				viewHolder.tv_mode.setText("ȫ������");
				break;
            case BlackNumberDao.SMS:
            	viewHolder.tv_mode.setText("��������");
				
				break;
            case BlackNumberDao.PHONE:
            	viewHolder.tv_mode.setText("�绰����");
				
				break;

			}
			 //��ɾ��imageview���õ������
			 /**
			  * ɾ��������
			  */
			 viewHolder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//ʵ��ɾ������
					AlertDialog.Builder builder=new Builder(BlackNumberActivity.this);
					builder.setMessage("��ȷ��Ҫɾ������������:"+info.getPhone()+"��?");
					//����ȷ����ȡ����ť
					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//ɾ��������
							//1.ɾ�����ݿ���ĺ���������
							blackNumDao.delete(info.getPhone());
							//2.������ɾ���ú���������
							   //2.1 ��������������ݵ�list������ɾ����Ӧ������
							list.remove(position);//ɾ����Ŀ��Ӧλ�õ�����
							   //2.2���½���
							myAdapter.notifyDataSetChanged();//���½���Ĳ���
							//3 ���ضԻ���
							dialog.dismiss();
							
						}
					});
					//ȡ����ť
					builder.setNegativeButton("ȡ��", null);
					//��ʾ�Ի���
					builder.show();
					
				}
			});
			
			return view;
		}
		
	}
	/**
	 * ��ŵ�����
	 */
	class ViewHolder{
		 TextView tv_phone,tv_mode;
		 ImageView  iv_delete;
		  
		
		
	}
	
	
	
	/**
	 * ��Ӻ���������
	 */
	
	public void addBlacknum(View v){
		//��Ӻ���������
		AlertDialog.Builder builder=new Builder(this);
		View view=View.inflate(getApplicationContext(), R.layout.dialog_add_blacknumber, null);
		//��ʼ���ؼ�
		final EditText et_phone=(EditText)view.findViewById(R.id.et_phone);
		final RadioGroup rg_group=(RadioGroup)view.findViewById(R.id.rg_group);
		Button bt_submit=(Button)view.findViewById(R.id.bt_submit);
		Button bt_cancel=(Button)view.findViewById(R.id.bt_cancel);
		//���ð�ť�ĵ���¼�
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��Ӻ���������
				//1.��ȡ����ĺ���������
		String black_num=et_phone.getText().toString();
		if(TextUtils.isEmpty(black_num)){
			ToastUtils.showToast(getApplicationContext(), "���������������");
			return;
		}else{
			//2.��ȡ����ģʽ
			int mode=-1;
			int rg_id=rg_group.getCheckedRadioButtonId();//��ȡѡ�е�id
			switch (rg_id) {
			case R.id.rb_phone:
				//�绰����
				mode=BlackNumberDao.PHONE;
				break;
			case R.id.rb_sms:
				//��������
				mode=BlackNumberDao.SMS;
				break;
			case R.id.rb_all:
				//ȫ������
				mode=BlackNumberDao.ALL;
				break;

			}
			//��Ӻ�����
			 //1.��ӵ����ݿ���
			blackNumDao.insert(black_num, mode);
			//2.��ӵ�����
			   //2.1��ӵ�list��������
			//list.add(new BlackNumberInfo(black_num, mode));
			//Ҫ��ӵĲ�������listview�ĵ�0��λ��
			list.add(0, new BlackNumberInfo(black_num, mode));
			   //2.2���½���
			myAdapter.notifyDataSetChanged();
			dialog.dismiss();
			
		}
				
				
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//���ȡ�����ضԻ���
				dialog.dismiss();
				
			}
		});
		builder.setView(view);
		//builder.show();
	 dialog= builder.create();
	  dialog.show();
		
		
		
	}
}





