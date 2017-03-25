<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head><title>∑÷“≥µº∫Ω¿∏</title>
<base href="<%=basePath%>">
</head>
<body>
    <table border="0" width="100%" cellspacing="0">
        <tr>
            <td width="60%"><s:property escape="false" value="#request.createPage.PageInfo"/></td>
            <td align="center" width="40%"><s:property escape="false" value="#request.createPage.PageLink"/></td>
        </tr>
    </table>
</body>
</html>