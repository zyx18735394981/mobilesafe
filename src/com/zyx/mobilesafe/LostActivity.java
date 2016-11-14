package com.zyx.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostActivity extends Activity {
	private SharedPreferences sp;
	private TextView number;
	private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		//�����һ�ν��룬������뵽���棻�����ǵ�һ�ν��룬��ֱ�ӽ����������
		if(sp.getBoolean("first", true)){
			//�����򵼽���
			Intent intent=new Intent(getApplication(),Setup1Activity.class);
			startActivity(intent);
			finish();
			
		}else{
			//���ط�������
			setContentView(R.layout.activity_lost);
			//�ҿؼ�
		  number=(TextView)findViewById(R.id.tv_lostfind_safenum);
		
		  image=(ImageView)findViewById(R.id.tv_lostfind_protected);
			
			initData();
		}
		
	}
	/**
	 * �����򵼽���İ�ȫ���뵽textview��
	 */
	private void initData() {
		//�����򵼽���İ�ȫ���뵽textview��
		String sp_num=sp.getString("phone", "110");
		number.setText(sp_num);
		//�ж��ֻ������Ƿ���
		boolean check=sp.getBoolean("checkbox", false);
		if(check==true){
			//����--��ͼƬ
			image.setImageResource(R.drawable.lock);
			
		}else{
			//û�п���
			image.setImageResource(R.drawable.unlock);
		}
		
	}
	/**
	 * ���½����򵼽���
	 * ����¼�
	 */
	public void resetup(View v){
		Intent intent=new Intent(this,Setup1Activity.class);
		startActivity(intent);
		finish();
		
	}



}
