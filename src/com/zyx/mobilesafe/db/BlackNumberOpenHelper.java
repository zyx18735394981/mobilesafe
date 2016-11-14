package com.zyx.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//创建数据库
public class BlackNumberOpenHelper extends SQLiteOpenHelper {
	//方便以后修改
	public static final String DB_NAME="info";
	public BlackNumberOpenHelper(Context context) {
		super(context, "blacknumber.db", null, 1);//上下文  数据库名称  游标工厂  数据库的版本号
	}
//第一次创建数据库时调用  创建表结构的操作
	@Override
	public void onCreate(SQLiteDatabase db) {
		//mode 拦截类型 短信 电话
		System.out.println("创建数据库表了");
		db.execSQL("create table "+DB_NAME+"(_id integer primary key autoincrement,phone varchar(20),mode varchar(5))");
		}
//当数据库版本发生变化的时候调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
