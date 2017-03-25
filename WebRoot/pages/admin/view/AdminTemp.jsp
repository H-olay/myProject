<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
  <title>都市信息网-后台管理</title>
  <base href="<%=basePath%>">
  <link type="text/css" rel="stylesheet" href="<s:url value='css/style.css'/>">
</head>
<body>
    <div align="center">
        <table border="0" width="920" cellspacing="0" bgcolor="white">
            <tr><td colspan="2"><%@ include file="top.jsp"%></td></tr>
            <tr height="10" bgcolor="lightgrey"><td colspan="2"></td></tr>
            <tr>
                <td width="700" align="center" valign="top"><jsp:include page="main.jsp"/></td>
                 <td width="200" align="center" valign="top"><jsp:include page="right.jsp"/></td> 
            </tr>
            <tr height="7" bgcolor="lightgrey"><td colspan="2"></td></tr>
            <tr><td colspan="2"><%@ include file="end.jsp" %></td></tr>
        </table>        
    </div>
</body>
</html>