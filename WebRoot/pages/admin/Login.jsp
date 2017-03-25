<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>

<html>
<head>
    <title>管理员登录</title>
    <base href="<%=basePath%>">
    <link type="text/css" rel="stylesheet" href="<s:url value='css/style.css'/>">
</head>
<body bgcolor="#E7ECEF">
    <div align="center">
        <s:form action="log_Login.action" theme="simple">
            <table border="0" cellspacing="0" cellpadding="0" style="margin-top:130">
                <tr><td><img src="images/logon_top.gif"></td></tr>                
                <tr height="180">
                    <td background="images/logon_middle.gif" align="center" valign="top">
                         <table border="0" width="95%" cellspacing="0" cellpadding="0">
                             <tr height="50"><td colspan="2"><s:fielderror/></td></tr>
                             <tr height="30">
                                 <td align="right" width="40%">用户名：&nbsp;&nbsp;</td>
                                 <td style="text-indent:5"><s:textfield name="user.userName" size="30"/></td>
                             </tr>                
                             <tr height="30">
                                 <td align="right">密&nbsp;&nbsp;码：&nbsp;&nbsp;</td>
                                 <td style="text-indent:5"><s:password name="user.userPassword" size="30"/></td>
                             </tr>
                             <tr height="60">
                                 <td></td>
                                 <td>
                                     <s:submit value="登录"/>
                                     <s:reset value="重置"/>
                                     <s:a href="index.jsp">[返回首页]</s:a>
                                 </td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr><td><img src="images/logon_end.gif"></td></tr>
            </table>
        </s:form>     
    </div>
</body>
</html>