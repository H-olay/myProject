package com.actionSuper;

import com.pojo.InfoSingle;
import com.pojo.SearchInfo;

public class InfoSuperAction extends MySuperAction {
	//用来封装从数据表中查询出的记录和发布信息时的表单数据
	protected InfoSingle infoSingle;
	protected SearchInfo searchInfo;
	
	public InfoSingle getInfoSingle() {
		return infoSingle;
	}

	public void setInfoSingle(InfoSingle infoSingle) {
		this.infoSingle = infoSingle;
	}

	public SearchInfo getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}
	
	
}
