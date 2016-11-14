package com.zyx.mobilesafe.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
/**
 * ��ȡϵͳ����ϵ��
 * @author Administrator
 *
 */
public class getPhonePerson {
	public static List<HashMap<String,String>>  getInfo(Context context){
		SystemClock.sleep(3000);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		// 1.��ȡ���ݽ�����
		ContentResolver resolver = context.getContentResolver();
		// 2.��ȡ�����ṩ�ߵĵ�ַ:com.android.contacts www.baidu.com/jdk
		// raw_contacts��ĵ�ַ :raw_contacts
		// view_data��ĵ�ַ : data
		// 3.���ɲ�ѯ��ַ
		Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");// http://
		Uri date_uri = Uri.parse("content://com.android.contacts/data");
		// 4.��ѯ����,�Ȳ�ѯraw_contacts,��ѯcontact_id
		// projection : ��ѯ���ֶ�
		Cursor cursor = resolver.query(raw_uri, new String[] { "contact_id" },
				null, null, null);
		// 5.����cursor
		while (cursor.moveToNext()) {
			// 6.��ȡ��ѯ������
			String contact_id = cursor.getString(0);
			// cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
			// : ��ѯ�ֶ���cursor������ֵ,һ�㶼�����ڲ�ѯ�ֶαȽ϶��ʱ��
			// �ж�contact_id�Ƿ�Ϊ��
			if (!TextUtils.isEmpty(contact_id)) {//null   ""
				// 7.����contact_id��ѯview_data���е�����
				// selection : ��ѯ����
				// selectionArgs :��ѯ�����Ĳ���
				// sortOrder : ����
				// ��ָ��: 1.null.���� 2.����Ϊnull
				Cursor c = resolver.query(date_uri, new String[] { "data1",
						"mimetype" }, "raw_contact_id=?",
						new String[] { contact_id }, null);
				HashMap<String, String> map = new HashMap<String, String>();
				// 8.����c
				while (c.moveToNext()) {
					// 9.��ȡ����
					String data1 = c.getString(0);
					String mimetype = c.getString(1);
					// 10.��������ȥ�жϻ�ȡ��data1���ݲ�����
				if(data1!=null&&mimetype!=null){
					
			
					if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
						// �绰
						map.put("phone", data1);
					} else if (mimetype.equals("vnd.android.cursor.item/name")) {
						// ����
						map.put("name", data1);
					}
					}
				}
				// 11.��ӵ�����������
				list.add(map);
				// 12.�ر�cursor
				c.close();
			}
		}
		// 12.�ر�cursor
		cursor.close();
		return list;
	}
	

}
