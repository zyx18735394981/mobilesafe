package com.zyx.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//�������ݿ�
public class BlackNumberOpenHelper extends SQLiteOpenHelper {
	//�����Ժ��޸�
	public static final String DB_NAME="info";
	public BlackNumberOpenHelper(Context context) {
		super(context, "blacknumber.db", null, 1);//������  ���ݿ�����  �α깤��  ���ݿ�İ汾��
	}
//��һ�δ������ݿ�ʱ����  ������ṹ�Ĳ���
	@Override
	public void onCreate(SQLiteDatabase db) {
		//mode �������� ���� �绰
		System.out.println("�������ݿ����");
		db.execSQL("create table "+DB_NAME+"(_id integer primary key autoincrement,phone varchar(20),mode varchar(5))");
		}
//�����ݿ�汾�����仯��ʱ�����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
