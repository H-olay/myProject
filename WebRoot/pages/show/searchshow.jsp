<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>��ѯ-�鿴��Ϣ�б�</title>
    <script type="text/javascript" src="<s:url value='js/DeleteCheck.js'/>"></script>
</head>
<body>
    <div align="center">
        <table border="0" width="670" cellspacing="0" cellpadding="0" style="margin-top:5">
            <tr height="30"><td style="text-indent:5" valign="bottom"><font color="#004790"><b>����ѯ���</b></td></tr>
            <tr height="1"><td></td></tr>
            <tr>
                <td align="center" style="border:1 solid">
                    <table border="0" width="100%" rules="rows" cellspacing="0">
                        <tr align="center" height="30" bgcolor="#F0F0F0">
                            <td width="6%"><b>���</b></td>
                            <td width="11%"><b>��Ϣ���</b></td>                            
                            <td width="8%"><b>IDֵ</b></td>
                            <td width="38%"><b>��Ϣ����</b></td>
                            <td width="22%"><b>����ʱ��</b></td>
                            <td width="15%"><b>��ϵ��</b></td>
                        </tr>
                        <s:set name="searchlist" value="#request.searchlist"/>
                        <s:if test="#searchlist==null||#searchlist.size()==0">
                            <tr height="80"><td colspan="8" align="center">���Բ���δ�ҵ�������������Ϣ�����</td></tr>
                        </s:if>
                        <s:else>
                            <s:iterator status="status" value="searchlist">
                                <s:if test="#status.even">
                                <tr height="30" bgcolor="#F9F9F9"></s:if>
                                <s:else>
                                <tr height="30"></s:else>
                                    <td align="center"><b><s:property value="#status.index+1"/></b></td>
                                    <td align="center"><s:property value="#session.typeMap[infoType]"/></td>
                                    <td style="text-indent:10"><s:property value="id"/></td>
                                    <td style="text-indent:5"><a href="info_SingleShow.action?id=<s:property value='id'/>"><s:property value="getSubInfoTitle(19)"/></a></td>
                                    <td align="center"><s:property value="infoDate"/></td>                                    
                                    <td style="text-indent:10"><s:property value="infoLinkman"/></td>
                                </tr>
                            </s:iterator>
                        </s:else>
                    </table>
                </td>
            </tr>
            <tr height="1"><td></td></tr>
            <tr height="30"><td align="center"><jsp:include page="/pages/page.jsp"/></td></tr>
        </table>
        <br>
    </div>
</body>
</html>