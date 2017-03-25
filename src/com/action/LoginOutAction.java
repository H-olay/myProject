package com.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.actionSuper.MySuperAction;
import com.pojo.UserSingle;
import com.util.DBUtil;
import com.util.OpDB;

public class LoginOutAction extends MySuperAction {
	private UserSingle user;

	public UserSingle getUser() {
		return user;
	}

	public void setUser(UserSingle user) {
		this.user = user;
	}
	
	/*功能：判断当前用户是否登录*/
	public String isLogin(){
		Boolean mark = (Boolean) session.get("mark");
		if(mark==null){
			return INPUT;
		}
		else{
			return LOGIN;
		}
	}
	
	/*功能：查询数据表，验证是否存在该用户*/
	public String Login() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		String sql = "select * from tb_user where user_name=? and user_password=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,user.getUserName());
		pstmt.setString(2, user.getUserPassword());
		rs = pstmt.executeQuery();
		OpDB op = new OpDB();
		boolean mark = op.LogOn(rs);
		if(mark){
			session.put("mark", mark);
			return LOGIN;
		}else{
			addFieldError("loginE", getText("用户名与密码不匹配!"));
			return INPUT;
		}
	}
	
	/*功能：处理用户退出登录*/
	public String Logout(){
		session.clear();
		return "logout";
	}
	
	/*功能：表单验证*/
	public void validateLogin() {
		String name=user.getUserName();
		String password=user.getUserPassword();
		
		if(name==null||name.equals(""))
			addFieldError("nameError",getText("用户名不能为空!"));
		if(password==null||password.equals(""))
			addFieldError("passwordError",getText("密码不能为空!"));
	}
	
}
