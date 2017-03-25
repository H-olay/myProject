package com.pojo;

public class InfoSingle {
	private int id;//信息ID
	private int infoType;//信息类型
	private String infoTitle;//信息标题
	private String infoContent;//信息内容
	private String infoLinkman;//联系人
	private String infoPhone;//联系电话
	private String infoEmail;//E-mail地址
	private String infoDate;//信息发布时间
	private String infoState;//信息审核状态
	private String infoPayfor;//信息付费状态
	
	//截取信息标题
	public String getSubInfoTitle(int len){
		if(len<=0||len>this.infoTitle.length()){
			len = this.infoTitle.length();
		}
		return this.infoTitle.substring(0,len);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInfoType() {
		return infoType;
	}
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getInfoLinkman() {
		return infoLinkman;
	}
	public void setInfoLinkman(String infoLinkman) {
		this.infoLinkman = infoLinkman;
	}
	public String getInfoPhone() {
		return infoPhone;
	}
	public void setInfoPhone(String infoPhone) {
		this.infoPhone = infoPhone;
	}
	public String getInfoEmail() {
		return infoEmail;
	}
	public void setInfoEmail(String infoEmail) {
		this.infoEmail = infoEmail;
	}
	public String getInfoDate() {
		return infoDate;
	}
	public void setInfoDate(String infoDate) {
		this.infoDate = infoDate;
	}
	public String getInfoState() {
		return infoState;
	}
	public void setInfoState(String infoState) {
		this.infoState = infoState;
	}
	public String getInfoPayfor() {
		return infoPayfor;
	}
	public void setInfoPayfor(String infoPayfor) {
		this.infoPayfor = infoPayfor;
	}
	
}
