package com.actionSuper;

import com.pojo.AdminShowType;
import com.pojo.InfoSingle;

public class AdminSuperAction extends MySuperAction {
	protected AdminShowType showType = new AdminShowType();
	protected InfoSingle infoSingle;
	public AdminShowType getShowType() {
		return showType;
	}
	public void setShowType(AdminShowType showType) {
		this.showType = showType;
	}
	public InfoSingle getInfoSingle() {
		return infoSingle;
	}
	public void setInfoSingle(InfoSingle infoSingle) {
		this.infoSingle = infoSingle;
	}

}
