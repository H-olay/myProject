package com.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.actionSuper.InfoSuperAction;
import com.pojo.CreatePage;
import com.pojo.InfoSingle;
import com.util.DBUtil;
import com.util.DoString;
import com.util.OpDB;

public class InfoAction extends InfoSuperAction {

	public String ListShow() throws Exception {
		request.setAttribute("mainPage", "/pages/show/listshow.jsp");
		// 获取信息类别
		String infoType = request.getParameter("infoType");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		/* 获取所有付费信息 */
		String sqlPayfor = "select * from tb_info where " + "(info_type=?) and (info_state='1') and "
				+ "(info_payfor='1') order by info_date desc";
		pstmt = conn.prepareStatement(sqlPayfor);
		pstmt.setInt(1, Integer.parseInt(infoType));
		rs = pstmt.executeQuery();
		OpDB op = new OpDB();
		List<InfoSingle> onepayforlist = new ArrayList<InfoSingle>();
		onepayforlist = op.OpListShow(rs);
		request.setAttribute("onepayforlist", onepayforlist);
		/* 获取当前页要显示的免费信息 */
		String sqlFreeAll = "select * from tb_info where " + "(info_type=?) and (info_state='1') and "
				+ "(info_payfor='0') order by info_date desc";
		int perR = 1;  //每页显示1条记录
		String strCurrentP = request.getParameter("showpage");//获取请求中传递的当前页码
		String gowhich = "info_ListShow.action?infoType="+Integer.parseInt(infoType);//设置分页超链接请求的资源
		CreatePage createPage = new CreatePage();
		createPage.setPerR(perR);
		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		conn2 = DBUtil.getConn();
		pstmt2 = conn2.prepareStatement(sqlFreeAll);
		pstmt2.setInt(1, Integer.parseInt(infoType));
		rs2 = pstmt2.executeQuery();
		if(rs2.next()){
			rs2.last();//将指针移动到结果集的最后一行
			createPage.setAllR(rs2.getRow());
			createPage.setAllP();//设置总页数
			createPage.setCurrentP(strCurrentP);
			createPage.setPageInfo();//设置分页状态栏信息
			createPage.setPageLink(gowhich);//设置分页导航链接
			rs2.close();
		}
		int top1 = createPage.getPerR();//获取每页显示记录数
		int currentP = createPage.getCurrentP();//获取当前页码
		String sqlFreeSub = "";//查询某类别中某一页的sql语句
		if(currentP==1){
			sqlFreeSub = "select * from tb_info where info_type=? and info_state='1' and info_payfor='0' order by info_date desc limit 0,"+top1;
		}else{
			int top2 = (currentP-1)*top1;
			sqlFreeSub = "select * from tb_info where info_type=? and info_state='1' and info_payfor='0' order by info_date desc limit "+top2+","+currentP*top1;
		}
		Connection conn3 = DBUtil.getConn();
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		pstmt3 = conn3.prepareStatement(sqlFreeSub);
		pstmt3.setInt(1, Integer.parseInt(infoType));
		rs3 = pstmt3.executeQuery();
		OpDB op2 = new OpDB();
		List<InfoSingle> onefreelist = op2.OpListShow(rs3);
		request.setAttribute("onefreelist", onefreelist);
		request.setAttribute("createPage", createPage);
		return SUCCESS;
	}

	public String SingleShow() throws Exception {
		request.setAttribute("mainPage", "/pages/show/singleshow.jsp");
		String id = request.getParameter("id");
		String sql = "select * from tb_info where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		rs = pstmt.executeQuery();
		OpDB op = new OpDB();
		infoSingle = op.OpSingleShow(rs);
		if (infoSingle == null) {
			request.setAttribute("mainPage", "/pages/error.jsp");
			addFieldError("SingleShowNoExist", getText("city.singlshow.no.exist"));
		}
		return SUCCESS;
	}

	/* 发布信息 */
	public String Add() throws Exception {
		//request.setCharacterEncoding("UTF-8");
		String addType = request.getParameter("addType");
		if (addType == null || addType.equals("")) {
			request.setAttribute("mainPage", "/pages/add/addInfo.jsp");
			addType = "linkTo";
		}
		if (addType.equals("add")) {
			request.setAttribute("mainPage", "/pages/error.jsp");
			int type = Integer.valueOf(infoSingle.getInfoType());
			String title = infoSingle.getInfoTitle();
			title = new String(title.getBytes("iso8859-1"),"utf-8");
			// 转换信息内容中的HTML字符
			String content = DoString.HTMLChange(infoSingle.getInfoContent());
			content = new String(content.getBytes("iso8859-1"),"utf-8");
			String phone = infoSingle.getInfoPhone();
			phone = phone.replaceAll(",", "●");// 替换","符号
			String linkman = infoSingle.getInfoLinkman();
			linkman = new String(linkman.getBytes("iso8859-1"),"utf-8");
			String email = infoSingle.getInfoEmail();
			String date = DoString.dateTimeChange(new Date());
			String state = "0";
			String payfor = "0";
			String sql1 = "insert into tb_info values(0,?,?,?,?,?,?,?,?,?)";
			OpDB op = new OpDB();
			int i = -1;
			i = op.OpUdate(sql1, type, title, content, phone, linkman, email, date, state, payfor);
			if (i <= 0) {
				addFieldError("addE", getText("发布失败!请重新发布信息或联系客服"));
			} else {
				String sql2 = "select * from tb_info where info_date=?";
				Connection conn2 = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				conn2 = DBUtil.getConn();
				pstmt2 = conn2.prepareStatement(sql2);
				pstmt2.setString(1, date);
				rs2 = pstmt2.executeQuery();
				int infoNum = 0;
				if (rs2.next()) {
					// 获取刚刚发布的ID值
					infoNum = rs2.getInt("id");
					addFieldError("addS", getText("发布成功，信息ID为") + infoNum);
				}
			}
		}
		return SUCCESS;
	}
	
	public String SearchShow(){
		request.setAttribute("mainPage","/pages/show/searchshow.jsp");		
		
		String subsql=searchInfo.getSubsql();
		String sqlvalue=searchInfo.getSqlvalue();		
		String type=searchInfo.getType();
		
		
		
		
		
		return SUCCESS;
	}

	/*验证发布表单*/
	public void validateAdd() {
		request.setAttribute("mainPage", "/pages/add/addInfo.jsp");
		String addType = request.getParameter("addType");
		if (addType == null || addType.equals(""))
			addType = "linkTo";

		if (addType.equals("add")) {
			int type = infoSingle.getInfoType();
			String title = infoSingle.getInfoTitle();
			String content = infoSingle.getInfoContent();
			String phone = infoSingle.getInfoPhone();
			String linkman = infoSingle.getInfoLinkman();
			String email = infoSingle.getInfoEmail();

			boolean mark = true;
			if (type <= 0) {
				mark = false;
				addFieldError("typeError", getText("信息类别不能为空"));
			}
			if (title == null || title.equals("")) {
				mark = false;
				addFieldError("titleError", getText("标题不能为空"));
			}
			if (content == null || content.equals("")) {
				mark = false;
				addFieldError("contentError", getText("内容不能为空"));
			}
			if (phone == null || phone.equals("")) {
				mark = false;
				addFieldError("phoneError", getText("电话不能为空"));
			}
			if (linkman == null || linkman.equals("")) {
				mark = false;
				addFieldError("linkmanError", getText("联系人不能为空"));
			}
			if (email == null || email.equals("")) {
				mark = false;
				addFieldError("emailError", getText("邮件不能为空"));
			}
			if (mark) {
				String phoneRegex = "(\\d{3}-)\\d{8}|(\\d{4}-)(\\d{7}|\\d{8})|\\d{11}";
				if (phone.indexOf(",") < 0) {
					if (!infoSingle.getInfoPhone().matches(phoneRegex)) {
						addFieldError("phoneError", getText("电话号码不符合格式"));
					}
				} else {
					String[] phones = phone.split(",");
					for (int i = 0; i < phones.length; i++) {
						if (!phones[i].matches(phoneRegex)) {
							addFieldError("phoneError", getText("电话号码不符合格式"));
							break;
						}
					}
				}
				String emailRegex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
				if (!infoSingle.getInfoEmail().matches(emailRegex)) {
					addFieldError("emailError", getText("邮件不符合格式"));
				}
			}
		}
	}

	/*验证搜索表单*/
	public void validateSearchShow() {
		request.setAttribute("mainPage","/pages/error.jsp");		
		String subsql=searchInfo.getSubsql();
		String sqlvalue=searchInfo.getSqlvalue();
		String type=searchInfo.getType();
		
		if(subsql==null||subsql.equals("")){
			addFieldError("SearchNoC",getText("city.info.search.no.condition"));
		}
		if(sqlvalue==null||sqlvalue.equals("")){
			addFieldError("SearchNoV",getText("city.info.search.no.value"));
		}
		if(type==null||type.equals("")){
			addFieldError("SearchNoT",getText("city.info.search.no.type"));
		}
	}
	
}
