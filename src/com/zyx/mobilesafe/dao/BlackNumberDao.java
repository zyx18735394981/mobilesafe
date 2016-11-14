package com.zyx.mobilesafe.dao;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zyx.mobilesafe.db.BlackNumberOpenHelper;
import com.zyx.mobilesafe.entity.BlackNumberInfo;
//��ɾ�Ĳ�

public class BlackNumberDao {
	
	public static final int ALL=0;
	public static final int SMS=1;
	public static final int PHONE=2;
	
	
	private BlackNumberOpenHelper blackNumOpenHlper;
	
	//�ڹ��캯���л�ȡBlackNumOpenHlper
	public BlackNumberDao(Context context){
		blackNumOpenHlper = new BlackNumberOpenHelper(context);
	}
	/**����һ����Ŀ
	 * @param phone	���صĵ绰����
	 * @param mode	��������(1:����	2:�绰	3:��������(����+�绰))
	 */
	public void insert(String phone,int mode){
		//1,�������ݿ�,׼����д�����
		SQLiteDatabase db = blackNumOpenHlper.getWritableDatabase();
         //�������
		ContentValues values = new ContentValues();
		values.put("phone", phone);
		values.put("mode", mode);
		db.insert(BlackNumberOpenHelper.DB_NAME, null, values);
		db.close();
	}
	
	/**�����ݿ���ɾ��һ���绰����
	 * @param phone	ɾ���绰����
	 */
	public void delete(String phone){
		SQLiteDatabase db = blackNumOpenHlper.getWritableDatabase();

		db.delete(BlackNumberOpenHelper.DB_NAME, "phone = ?", new String[]{phone});
		
		db.close();
	}
	/**
	 * ���ݵ绰����ȥ,��������ģʽ
	 * @param phone	��������ģʽ�ĵ绰����
	 * @param mode	Ҫ����Ϊ��ģʽ(1:����	2:�绰	3:��������(����+�绰)
	 */
	public void update(String phone,String mode){
		SQLiteDatabase db = blackNumOpenHlper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("mode", mode);
		
		db.update(BlackNumberOpenHelper.DB_NAME, contentValues, "phone = ?", new String[]{phone});
		
		db.close();
	}

	/**
	 * @return	��ѯ�����ݿ������еĺ����Լ������������ڵļ���
	 */
	public List<BlackNumberInfo> findAll(){
		System.out.println("��ȡ���ݵķ���");
		List<BlackNumberInfo> blackNumberList = new ArrayList<BlackNumberInfo>();
		//��ȡ���ݿ�
		SQLiteDatabase db = blackNumOpenHlper.getReadableDatabase();
		//��ѯ
		Cursor cursor = db.query(BlackNumberOpenHelper.DB_NAME, new String[]{"phone","mode"}, null, null, null, null, "_id desc");//desc�����ѯ,asc:�����ѯ,Ĭ�������ѯ
				while(cursor.moveToNext()){
			BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
			blackNumberInfo.phone = cursor.getString(0);
			blackNumberInfo.mode = cursor.getInt(1);
			blackNumberList.add(blackNumberInfo);
		}
		cursor.close();
		db.close();
		return blackNumberList;
	}
	
	/**
	 * ÿ�β�ѯ20������
	 * @param index	��ѯ������ֵ
	 */
	public List<BlackNumberInfo> find(int index){
		SQLiteDatabase db = blackNumOpenHlper.getReadableDatabase();
             //sql���    ����ֵ              0    20   40        
		Cursor cursor = db.rawQuery("select phone,mode from "+BlackNumberOpenHelper.DB_NAME+" order by _id desc limit ?,20;", new String[]{index+""});
		
		List<BlackNumberInfo> blackNumberList = new ArrayList<BlackNumberInfo>();
		while(cursor.moveToNext()){
			BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
			blackNumberInfo.phone = cursor.getString(0);
			blackNumberInfo.mode = cursor.getInt(1);
			blackNumberList.add(blackNumberInfo);
		}
		cursor.close();
		db.close();
		
		return blackNumberList;
	}
	/**
	 * @return	���ݿ������ݵ�����Ŀ����,����0����û�����ݻ��쳣
	 */
	public int getCount(){
		SQLiteDatabase db = blackNumOpenHlper.getReadableDatabase();
		int count = 0;
		Cursor cursor = db.rawQuery("select count(*) from "+BlackNumberOpenHelper.DB_NAME+";", null);
		if(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return count;
	}
	/**
	 * @param phone	ͨ�����������룬��ѯ���������������ģʽ
	 * @return	
	 */
	public int getMode(String phone){
		SQLiteDatabase db = blackNumOpenHlper.getReadableDatabase();
		int mode =-1;
		System.out.println(phone+"@@@@@@@@@");
		Cursor cursor = db.query(BlackNumberOpenHelper.DB_NAME, new String[]{"mode"}, "phone = ?", new String[]{phone}, null, null,null);
		if(cursor.moveToNext()){
			mode = cursor.getInt(0);
		}
		System.out.println(mode+"########");
		cursor.close();
		db.close();
		return mode;
	}

}
