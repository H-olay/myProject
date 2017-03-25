package com.pojo;

public class AdminShowType {
	private String stateType;//保存审核状态
	private String payforType;//保存付费状态
	private int infoType;//保存信息类别
	public String getStateType() {
		return stateType;
	}
	public void setStateType(String stateType) {
		this.stateType = stateType;
	}
	public String getPayforType() {
		return payforType;
	}
	public void setPayforType(String payforType) {
		this.payforType = payforType;
	}
	public int getInfoType() {
		return infoType;
	}
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	
}
