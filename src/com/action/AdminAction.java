package com.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.actionSuper.AdminSuperAction;
import com.pojo.AdminShowType;
import com.pojo.CreatePage;
import com.pojo.InfoSingle;
import com.util.DBUtil;
import com.util.OpDB;

public class AdminAction extends AdminSuperAction {
	
	/* 功能：管理员操作-进行列表显示信息 */
	public String ListShow() throws Exception{
		
		session.remove("adminOP");
		
		request.setAttribute("mainPage","../info/listshow.jsp");
		int infoType = showType.getInfoType();//获取选择的信息类别
		String payforType = showType.getPayforType();//获取选择的付费状态
		String stateType = showType.getStateType();//获取选择的审核状态
		session.put("infoType", Integer.valueOf(infoType));//保存已选择的信息类别
		session.put("payforType", payforType);//保存已选择的付费状态
		session.put("stateType", stateType);//保存已选择的审核状态
		String sqlall="";//用来保存查询所有记录的sql语句
		String sqlsub="";//用来保存查询指定页中记录的sql语句
		String mark="";
		int perR=3;//设置每页显示的记录数
		Object[] params = null;//存储sql语句中的参数
		/*没有同时选中“付费”和“审核”的“全部”单选按钮*/
		if(!stateType.equals("all")&&!payforType.equals("all")){
			mark="1";
			sqlall="select * from tb_info where info_type=? and info_state=? and info_payfor=? order by info_date desc";
			sqlsub="select * from tb_info where info_type=? and info_state=? and info_payfor=? order by info_date desc limit 0,"+perR;
			params = new Object[3];
			params[0]=Integer.valueOf(infoType);
			params[1]=stateType;
			params[2]=payforType;
		}
		/*同时选中“付费”和“审核”的“全部”单选按钮*/
		else if(stateType.equals("all")&&payforType.equals("all")){
			mark="2";
			sqlall="select * from tb_info where info_type=? order by info_date desc";
			sqlsub="select * from tb_info where info_type=? order by info_date desc limit 0,"+perR;
			params = new Object[1];
			params[0]=Integer.valueOf(infoType);
		}
		/*选中“付费”的“全部”按钮,“审核”与“未审核”任意一个单选按钮*/
		else if(payforType.equals("all")){
			mark="3";
			sqlall="select * from tb_info where info_type=? and info_state=? order by info_date desc";
			sqlsub="select * from tb_info where info_type=? and info_state=? order by info_date desc limit 0,"+perR;
			params = new Object[2];
			params[0]=Integer.valueOf(infoType);
			params[1]=stateType;
		}/*选中“审核”的“全部”按钮,“付费”与“未付费”任意一个单选按钮*/
		else if(payforType.equals("all")){
			mark="4";
			sqlall="select * from tb_info where info_type=? and info_payfor=? order by info_date desc";
			sqlsub="select * from tb_info where info_type=? and info_payfor=? order by info_date desc limit 0,"+perR;
			params = new Object[2];
			params[0]=Integer.valueOf(infoType);
			params[1]=payforType;
		}
		
		String strCurrentP=request.getParameter("showpage");
		String gowhich="admin_ListShow.action";
		OpDB op = new OpDB();
		CreatePage createPage = new CreatePage();
		createPage = op.OpCreatePage(sqlall, params, perR, strCurrentP, gowhich);
		int currentP = createPage.getCurrentP();//获得当前页码
		if(currentP>1){		//如果不是第一页
			int top = (currentP-1)*perR;
			if(mark.equals("1")){
				sqlsub="select * from tb_info where info_type=? and info_state=? and info_payfor=? order by info_date desc limit "+top+","+currentP*perR;
			}else if(mark.equals("2")){
				sqlsub="select * from tb_info where info_type=? order by info_date desc limit "+top+","+currentP*perR;
			}else if(mark.equals("3")){
				sqlsub="select * from tb_info where info_type=? and info_state=? order by info_date desc limit "+top+","+currentP*perR;
			}else if(mark.equals("4")){
				sqlsub="select * from tb_info where info_type=? and info_payfor=? order by info_date desc limit "+top+","+currentP*perR;
			}
		}
		
		try {
			List<InfoSingle> adminlistshow=op.OpListShow(sqlsub, params);
			request.setAttribute("adminlistshow", adminlistshow);
			request.setAttribute("createPage", createPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/* 功能：管理员操作-显示要进行付费设置的信息 */
	public String SetMoneyShow() throws Exception{
		request.setAttribute("mainPage","../info/moneyshow.jsp");
		String  moneyID = request.getParameter("moneyID");
		String sql = "select * from tb_info where id=?";
		Connection conn = DBUtil.getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(moneyID));
		ResultSet rs = pstmt.executeQuery();
		OpDB op = new OpDB();
		infoSingle = op.OpSingleShow(rs);
		if(infoSingle==null){
			request.setAttribute("mainPage","/pages/error.jsp");
			addFieldError("AdminShowNoExist",getText("用户不存在!"));
		}
		return SUCCESS;
	}
	
	/* 功能：管理员操作-设置已付费信息(更新数据库) */
	public String SetMoney() throws Exception{
		String  moneyID = request.getParameter("moneyID");
		String sql = "update tb_info set info_payfor='1' where id=?";
		Connection conn = DBUtil.getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(moneyID));
		int i = pstmt.executeUpdate();
		conn.commit();
		if(i>0){								//付费成功			
			addFieldError("AdminSetMoneySuccess",getText("该用户设为付费成功"));			
			request.setAttribute("mainPage","/pages/error.jsp");
			return "setMoneySuccess";			
		}
		else{									//付费失败
			addFieldError("AdminSetMoneyUnSuccess",getText("该用户设为付费失败"));			
			request.setAttribute("mainPage","/pages/error.jsp");
			return "UnSuccess";
		}
		
	}
	
	/* 功能：管理员操作-删除信息(更新数据库) */
	public String Delete() throws Exception{
		session.put("adminOP","Delete");	
		String deleteID = request.getParameter("deleteID");
		String sql = "delete from tb_info where id=?";
		Connection conn = DBUtil.getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(deleteID));
		int i = pstmt.executeUpdate();
		conn.commit();
		if(i>0){										
			return "deleteSuccess";			
		}
		else{									
			addFieldError("AdminDeleteUnSuccess",getText("删除信息失败,请重新操作!"));			
			request.setAttribute("mainPage","/pages/error.jsp");
			return "UnSuccess";
		}
	}
	
	/* 功能：管理员操作-显示要审核的信息 */
	public String CheckShow() throws Exception{
		request.setAttribute("mainPage","../info/checkshow.jsp");
		String sql="select * from tb_info where (id = ?)";
		String checkID=request.getParameter("checkID");
		if(checkID==null||checkID.equals(""))
			checkID="-1";
		Connection conn = DBUtil.getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(checkID));
		ResultSet rs = pstmt.executeQuery();
		OpDB op = new OpDB();
		infoSingle = op.OpSingleShow(rs);
		if(infoSingle==null){			
			request.setAttribute("mainPage","/pages/error.jsp");
			addFieldError("AdminShowNoExist",getText("用户不存在!"));
		}
		return SUCCESS;
		
	}
	
	/* 功能：管理员操作-审核信息(更新数据库) */
	public String Check() throws Exception{
		session.put("adminOP","Check");	
		String checkID=request.getParameter("checkID");
		String sql = "update tb_info set info_state='1' where id=?";
		Connection conn = DBUtil.getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(checkID));
		int i = pstmt.executeUpdate();
		conn.commit();
		if(i>0){										
			return "checkSuccess";			
		}
		else{								
			
			addFieldError("AdminCheckUnSuccess",getText("审核失败!"));			
			request.setAttribute("mainPage","/pages/error.jsp");
			return "UnSuccess";
		}
		
	}
	
	/*验证查询付费ID的表单*/
	public void validateSetMoneyShow() {
		request.setAttribute("mainPage","/pages/error.jsp");
		
		String moneyID=request.getParameter("moneyID");		
		if(moneyID==null||moneyID.equals("")){				
			addFieldError("moneyIDError",getText("付费ID为空，请重新输入!"));
		}
		else{															
			try{
				int id=Integer.parseInt(moneyID);
				if(id<0)									
					addFieldError("moneyIDError",getText("付费ID不能小于0,请重新输入!"));
			}catch(NumberFormatException e){
				addFieldError("moneyIDError",getText("付费ID不符合数字格式，请重新输入!"));
				e.printStackTrace();
			}
		}
	}
	
	/* 功能：恢复在“显示方式”中的选择状态 */
	private void comebackState(){
		/* 获取session中保存的选择状态。
		 * 将选择状态保存在session中，
		 * 是在管理员单击“显示”按钮请求列表显示时，
		 * 在ListShow()方法中实现的
		 */
		Integer getInfoType=(Integer)session.get("infoType");
		String getPayForType=(String)session.get("payforType");
		String getStateType=(String)session.get("stateType");
		
		/* 恢复选择的状态 */
		if(getPayForType!=null&&getStateType!=null&&getInfoType!=null){			
			showType.setInfoType(getInfoType.intValue());	
			showType.setPayforType(getPayForType);
			showType.setStateType(getStateType);			
		}
	
	}
	
	/* 功能：验证操作-后台进行列表显示信息时，验证是否选择了一种显示方式及信息类别 */
	public void validateListShow(){
		request.setAttribute("mainPage","/pages/error.jsp");
		
		String adminOP=(String)session.get("adminOP");
		if(adminOP==null)
			adminOP="";
		/*如果是进行了“通过审核”或“删除信息”操作后，再来调用ListShow()方法显示
		 * 信息列表，则需要恢复之前选择的“显示方式”和“信息类别”状态，从而重新在
		 * ListShow()方法中查询出符合条件的记录*/
		if(adminOP.equals("Check")||adminOP.equals("Delete")) { 
		
			comebackState();
			
		}
		else{
			
			int getInfoType=showType.getInfoType();			
			String getPayforType=showType.getPayforType();
			String getStateType=showType.getStateType();
			
			//解决分页的超链接参数没有以上三个参数值的问题
			if(getInfoType<=0){
				if(session.get("infoType")!=null){
					getInfoType=(Integer)session.get("infoType");
					showType.setInfoType(getInfoType);
				}
			}	
			if(getPayforType==null||getPayforType.equals("")){
				getPayforType=(String)session.get("payforType");
				showType.setPayforType(getPayforType);
			}
            if(getStateType==null||getStateType.equals("")){
            	getStateType=(String)session.get("stateType");
            	showType.setStateType(getStateType);
			}	
           
            
			
			if(getInfoType<=0){						//没有选择“信息类别”
				addFieldError("AdminListNoType",getText("请选择信息类别!"));
			}
			else{
				if(getPayforType==null||showType.getPayforType().equals("")){		//没有选择“付费状态”选项
					addFieldError("AdminListNoPayForType",getText("请选择付费选项!"));
				}
				if(getStateType==null||showType.getStateType().equals("")){		//没有选择“审核状态”选项
					addFieldError("AdminListNoStateType",getText("请选择审核选项!"));
				}
			}			
		}
	}
	
}
