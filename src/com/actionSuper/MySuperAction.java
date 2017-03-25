package com.actionSuper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/*若要使用HttpServletRequest、HttpServletResponse类对象必须使该类继承
ServletRequestAware、ServletResponseAware接口，另外如果仅是对会话进行
存取数据的操作则可继承SessionAware，否则可通过HttpServletRequest类对象的
getSession()方法来获取会话。
*/
public class MySuperAction extends ActionSupport implements SessionAware,ServletRequestAware,ServletResponseAware {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map session;

	public void setSession(Map session) {
		this.session=session;	
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}	
}
