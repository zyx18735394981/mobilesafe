package com.zyx.mobilesafe.entity;


public class BlackNumberInfo {
	public int id;
	public String phone;
	public int mode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public BlackNumberInfo(String phone, int mode) {
		super();
		this.phone = phone;
		this.mode = mode;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BlackNumberInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		if(mode>=0&&mode<=2){
			this.mode=mode;
		}else{
			mode=0;
		}
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "BlackNumberInfo [phone=" + phone + ", mode=" + mode + "]";
	}
}
