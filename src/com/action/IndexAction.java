package com.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.actionSuper.MySuperAction;
import com.pojo.InfoSingle;
import com.util.DBUtil;
import com.util.DoString;
import com.util.OpDB;


public class IndexAction extends MySuperAction {
	//MySuperAction为自定义类，该类继承了ActionSupport类
	public static TreeMap typeMap;//用来存储信息类别
	public static TreeMap searchMap;//用来存储sql查询tb_info表语句的参数类别
	public String execute() throws Exception{
		/* 查询所有收费信息，按发布时间降序排列 */
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql1 = "select * from tb_info where "
				+ "(info_state='1') and (info_payfor='1')"
				+ " order by info_date desc";
		conn = DBUtil.getConn();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql1);
		List<InfoSingle> payforlist = new ArrayList<InfoSingle> ();
		OpDB op = new OpDB();
		payforlist = op.OpListShow(rs);
		if(conn!=null){
			conn.close();
		}
		if(stmt!=null){
			stmt.close();
		}
		
		request.setAttribute("payforlist", payforlist);
		session.put("typeMap", typeMap);
		session.put("searchMap", searchMap);
		
		/* 查询所有免费信息，按发布时间降序排列 */
		List allsublist = new ArrayList();
		if(typeMap!=null&&typeMap.size()!=0){
			String sql3 = "select * from tb_info where "
				+ "(info_type =?) and (info_state='1') "
				+ "and (info_payfor='0') order by info_date desc limit 0,5";
			Connection conn2 = null;
			conn2 = DBUtil.getConn();
			PreparedStatement pstmt = null;
			ResultSet rs2 = null;
			
			/*先调用Map对象的keySet()方法获取typeMap对象中包含的所有
			 * key值，返回一个Set对象，然后调用Set对象的iterator()
			 * 方法转换为iterator对象
			 */
			Iterator itype = typeMap.keySet().iterator();
			while(itype.hasNext()){
				Integer sign = (Integer)itype.next();
				pstmt = conn2.prepareStatement(sql3);
				pstmt.setInt(1, sign);
				rs2 = pstmt.executeQuery();
				List<InfoSingle> onesublist = new ArrayList<InfoSingle> ();
				OpDB op2 = new OpDB();
				onesublist = op2.OpListShow(rs2);
				allsublist.add(onesublist);
			}
		}
		request.setAttribute("allsublist", allsublist); 
		return SUCCESS;
	}
	//静态代码块，在IndexAction类第一次被调用时执行
	static{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		/*初始化所有信息类别*/
		String sql2 = "select * from tb_type order by type_sign";
		conn = DBUtil.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			OpDB op = new OpDB();
			try {
				typeMap = op.OpGetListBox(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(typeMap==null)
			typeMap = new TreeMap();
		/* 初始化搜索功能的下拉列表 */
		searchMap=new TreeMap();
		searchMap.put("ID值","id");
		searchMap.put("信息标题","info_title");
		searchMap.put("信息内容","info_content");
		searchMap.put("联系人","info_linkman");
		searchMap.put("联系电话","info_phone");
		searchMap.put("E-mail地址","info_email");
	}
}
