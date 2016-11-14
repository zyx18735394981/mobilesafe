package com.zyx.mobilesafe.entity;

import android.graphics.drawable.Drawable;

public class TaskInfo {
	private String name;
	private Drawable icon;
	private String packageName;
	private boolean isUser;
	private long romSize;
	private boolean checked;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public long getRomSize() {
		return romSize;
	}
	public void setRomSize(long romSize) {
		this.romSize = romSize;
	}
	public TaskInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskInfo(String name, Drawable icon, String packageName,
			boolean isUser, long romSize) {
		super();
		this.name = name;
		this.icon = icon;
		this.packageName = packageName;
		this.isUser = isUser;
		this.romSize = romSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
