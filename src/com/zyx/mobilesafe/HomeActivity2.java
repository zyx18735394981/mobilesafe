package com.zyx.mobilesafe;


import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caojiang.life.HomeActivity;
import com.zyx.mobilesafe.utils.ToastUtils;

public class HomeActivity2 extends Activity{
	private SharedPreferences sp;
	private GridView gv_home_gridview;
	private AlertDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//�������
		sp = getSharedPreferences("config", MODE_PRIVATE);
	//���ز����ļ�
		setContentView(R.layout.activity_home);
	    gv_home_gridview=(GridView)findViewById(R.id.gv_home_gridview);
	    gv_home_gridview.setAdapter(new MyAdapter());
	   
	    gv_home_gridview.setOnItemClickListener(new   OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//position  ��Ŀ��λ��
				//������Ŀ��λ�� �ж��û������ĸ�λ��
				
				switch (position) {
			
				case 0:
					//�����ֻ�����
				    //�����Ի����������룬���ǵ�һ�����룬��Ҫ������ʾ�����������ȷ������
                    //���ڶ������룬ֻ��Ҫһ����ʾ������������
				if(TextUtils.isEmpty(sp.getString("password", ""))){
					//��������
					
					showSetPassDialog();
					
				}else{
					//��������
					showEnterPassword();
					
				}
				
					
					
					break;
				case 1:
					// ��ת��ͨ����ʿģ��
					
					Intent intent2=new Intent(getApplicationContext(), BlackNumberActivity.class);
					startActivity(intent2);
					break;
				case 2:
					//�������ģ��SoftManagerActivity
					Intent intent3=new Intent(getApplicationContext(), SoftManagerActivity.class);
					startActivity(intent3);
					break;
				case 3:
					//���̹���ģ��TaskActivity
					Intent intent4=new Intent(getApplicationContext(), TaskActivity.class);
					startActivity(intent4);
					break;
				case 4:
					ToastUtils.showToast(getApplicationContext(), "���ڿ����У������ڴ�~");

					break;
				case 5:
					//�ֻ�ɱ��ģ��TaskActivity
					Intent intent6=new Intent(getApplicationContext(), BingduActivity.class);
					startActivity(intent6);
					break;
				case 6:
					//��������ģ��TaskActivity
					Intent intent7=new Intent(getApplicationContext(), ClearActivity.class);
					startActivity(intent7);
					break;
				case 7:
					//�߼�����ģ��
					 
					Intent intent8=new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(intent8);
					break;
					
				case 8:
					//������������
					Intent intent=new Intent(HomeActivity2.this,SettingActivity.class);
					startActivity(intent);//��ת���������Ľ���
					break;
				}
				
			}

		} );
	    
	    
	    
	    
	 // ʵ���������
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// ��ȡҪǶ�������Ĳ���
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

		// ����������뵽������
		adLayout.addView(adView);
	    
	}
	


	private class MyAdapter extends BaseAdapter{
//�����ݳ�ʼ��
		int[] imageId = { R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
				R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
				R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings };
		String[] names = { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���", "����ͳ��", "�ֻ�ɱ��", "��������",
				"�߼�����", "��������" };
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 9;
		}
//��ȡ��Ŀ��Ӧ������
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
//������Ŀ����ʽ
		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			
			View view= View.inflate(getApplicationContext(), R.layout.item_home, null);
			//ÿ����Ŀ����ʽ����һ��  ��ʼ���ؼ�  ȥ���ÿؼ���ֵ
			ImageView iv_itemhome_icon=(ImageView)view.findViewById(R.id.iv_itemhome_icon);
			
			TextView tv_itemhome_text=(TextView)view.findViewById(R.id.tv_itemhome_text);
			//��ͼƬ��ֵ
			iv_itemhome_icon.setImageResource(imageId[position]);
			//���ı���ֵ
			tv_itemhome_text.setText(names[position]);
			return view;
		}
		
		
		
	}


	
	/**
	 * ��������
	 */
				private void showEnterPassword() {
					AlertDialog.Builder builder=new Builder(this);
					builder.setCancelable(false);
				View view=View.inflate(getApplicationContext(), R.layout.dialog_inputpassword, null);
				

				   builder.setView(view);
				   
					
			final EditText input_password=(EditText)view.findViewById(R.id.et_input_password);
			Button btn_ok=(Button)view.findViewById(R.id.btn_ok);
			Button btn_cancle=(Button)view.findViewById(R.id.btn_cancle);
			
			btn_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//���ȷ���������������������������бȶ�
				String inputPwd=input_password.getText().toString();
				//ȡ�������������
				String sp_pwd=sp.getString("password", "");
				if(inputPwd.equals(sp_pwd)){
					//����������
					dialog.dismiss();
					Intent intent=new Intent(getApplicationContext(),LostActivity.class);
					startActivity(intent);
					
				}else{
					//���벻��ȷ
					ToastUtils.showToast(getApplicationContext(), "���벻��ȷ��");
					return;
					
				}
					
				}
			});
			
			btn_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//���ȡ��
					dialog.dismiss();
		
					
				}
			});
			dialog = builder.create();
			dialog.show();	
				}
	/**
	 * ��������
	 */
				private void showSetPassDialog() {
					
			AlertDialog.Builder builder=new Builder(this);
			     
			
					//���öԻ�����ȡ��
					builder.setCancelable(false);
					//���������
					
				View view=View.inflate(this, R.layout.dialog_setpassword, null);
				builder.setView(view);
				   
			
			   
				//�ҿؼ�
		final	EditText et_setpassword_password=(EditText)view.findViewById(R.id.et_setpassword_password);
		final	EditText et_setpassword_confrim=(EditText)view.findViewById(R.id.et_setpassword_confrim);
			Button btn_ok=(Button)view.findViewById(R.id.btn_ok);
			Button btn_cancle=(Button)view.findViewById(R.id.btn_cancle);
			
			//��ȷ�����õ���¼�---�жϲ�Ϊ�� һ�� ��password���浽��������
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd=et_setpassword_password.getText().toString().trim();
				String check_pwd=et_setpassword_confrim.getText().toString().trim();
				if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(check_pwd)){
					
					if(pwd.equals(check_pwd)){
						//�����뱣�浽��������
						
					Editor edit=sp.edit();
					edit.putString("password", pwd);
					edit.commit();//�ύ
					
					dialog.dismiss();
					ToastUtils.showToast(getApplicationContext(), "�������óɹ�");
					
				
						
					}else{
						ToastUtils.showToast(getApplicationContext(), "�������벻һ��");
						return;
					}
					
					
					
				}else{
					ToastUtils.showToast(getApplicationContext(), "����������");
				     return;
				 }
			}
		});
			
			btn_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//���ȡ�����Ի�����ʧ
					dialog.dismiss();
					
				}
			});	
			   dialog = builder.create();
			   dialog.show();
				
				}
	

}
