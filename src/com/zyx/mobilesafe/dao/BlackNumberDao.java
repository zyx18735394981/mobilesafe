package com.zyx.mobilesafe.dao;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zyx.mobilesafe.db.BlackNumberOpenHelper;
import com.zyx.mobilesafe.entity.BlackNumberInfo;
//增删改查

public class BlackNumberDao {
	
	public static final int ALL=0;
	public static final int SMS=1;
	public static final int PHONE=2;
	
	
	private BlackNumberOpenHelper blackNumOpenHlper;
	
	//在构造函数中获取BlackNumOpenHlper
	public BlackNumberDao(Context context){
		blackNumOpenHlper = new BlackNumberOpenHelper(context);
	}
	/**增加一个条目
	 * @param phone	拦截的电话号码
	 * @param mode	拦截类型(1:短信	2:电话	3:拦截所有(短信+电话))
	 */
	public void insert(String phone,int mode){
		//1,开启数据库,准备做写入操作
		SQLiteDatabase db = blackNumOpenHlper.getWritableDatabase();
         //添加数据
		ContentValues values = new ContentValues();
		values.put("phone", phone);
		values.put("mode", mode);
		db.insert(BlackNumberOpenHelper.DB_NAME, null, values);
		db.close();
	}
	
	/**从数据库中删除一条电话号码
	 * @param phone	删除电话号码
	 */
	public void delete(String phone){
		SQLiteDatabase db = blackNumOpenHlper.getWritableDatabase();

		db.delete(BlackNumberOpenHelper.DB_NAME, "phone = ?", new String[]{phone});
		
		db.close();
	}
	/**
	 * 根据电话号码去,更新拦截模式
	 * @param phone	更新拦截模式的电话号码
	 * @param mode	要更新为的模式(1:短信	2:电话	3:拦截所有(短信+电话)
	 */
	public void update(String phone,String mode){
		SQLiteDatabase db = blackNumOpenHlper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("mode", mode);
		
		db.update(BlackNumberOpenHelper.DB_NAME, contentValues, "phone = ?", new String[]{phone});
		
		db.close();
	}

	/**
	 * @return	查询到数据库中所有的号码以及拦截类型所在的集合
	 */
	public List<BlackNumberInfo> findAll(){
		System.out.println("获取数据的方法");
		List<BlackNumberInfo> blackNumberList = new ArrayList<BlackNumberInfo>();
		//获取数据库
		SQLiteDatabase db = blackNumOpenHlper.getReadableDatabase();
		//查询
		Cursor cursor = db.query(BlackNumberOpenHelper.DB_NAME, new String[]{"phone","mode"}, null, null, null, null, "_id desc");//desc倒序查询,asc:正序查询,默认正序查询
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
	 * 每次查询20条数据
	 * @param index	查询的索引值
	 */
	public List<BlackNumberInfo> find(int index){
		SQLiteDatabase db = blackNumOpenHlper.getReadableDatabase();
             //sql语句    条件值              0    20   40        
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
	 * @return	数据库中数据的总条目个数,返回0代表没有数据或异常
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
	 * @param phone	通过黑名单号码，查询黑名单号码的拦截模式
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
