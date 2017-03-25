<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>发布信息</title>
    <meta http-equiv="content-type" content="text/html"; charset="utf-8">
    <script type="text/javascript" src="<s:url value='js/InputCheck.js'/>"></script>
</head>
<body>
    <div align="center">
    <table border="0" cellpadding="0" cellspacing="0" width="688" height="100%">
        <tr height="20"><td><img src="images/default_t.jpg"></td></tr>
        <tr>
            <td background="images/default_m.jpg" valign="top" align="center">
                <s:form action="info_Add.action" theme="simple" method="post">
                <input type="hidden" name="addType" value="add"/>
                <table border="0" width="650" height="300" rules="all" cellspacing="0">
                    <tr height="30"><td style="text-indent:10"><font color="#004790"><b>■发布信息</b></font></td></tr>
                    <tr>
                        <td align="center">
                            <table border="0" width="650" rules="all" cellspacing="8">                    
                                <tr>
                                    <td width="20%" style="text-indent:10">信息类别：</td>
                                    <td>
                                       <s:select
                                           emptyOption="true"
                                           list="#session.typeMap"                            
                                           name="infoSingle.infoType"/>                            
                                    </td>
                                    <td align="right"><font color="#7F7F7F">[信息标题最多不得超过 40 个字符]&nbsp;&nbsp;</font></td>
                                </tr>
                                <tr><td colspan="3"><s:fielderror><s:param value="%{'typeError'}"/></s:fielderror></td></tr>
                                <tr>
                                    <td style="text-indent:10">信息标题：</td>
                                    <td colspan="2"><s:textfield name="infoSingle.infoTitle" size="80" maxlength="40"/></td>                        
                                </tr>
                                <tr><td colspan="3"><s:fielderror><s:param value="%{'titleError'}"/></s:fielderror></td></tr>
                                <tr>
                                    <td style="text-indent:10">信息内容：</td> 
                                    <td>
                                        <font color="#7F7F7F">
                                            已用：<input type="text" name="ContentUse" value="0" size="4" disabled style="text-align:center;border:0;"> 个&nbsp;&nbsp;
                                            剩余：<input type="text" name="ContentRem" value="500" size="4" disabled style="text-align:center;border:0;"> 个 
                                        </font>
                                    </td>
                                    <td align="right"><font color="#7F7F7F">[信息内容最多不得超过 500 个字符]&nbsp;&nbsp;</font></td>
                                </tr>
                                <tr><td colspan="3"><s:fielderror><s:param value="%{'contentError'}"/></s:fielderror></td></tr>
                                <tr><td colspan="3" align="center"><s:textarea id="content" name="infoSingle.infoContent" rows="12" cols="75" onkeydown="check(content,ContentUse,ContentRem,500)" onkeyup="check(content,ContentUse,ContentRem,500)" onchange="check(content,ContentUse,ContentRem,500)"/></td></tr>
                                <tr>
                                    <td style="text-indent:10">联系电话：</td>
                                    <td colspan="2"><s:textfield label="联系电话" name="infoSingle.infoPhone" size="45"/><font color="gray">&nbsp;[多个电话以逗号分隔！]</font></td>
                                </tr>
                                <tr><td colspan="3"><s:fielderror><s:param value="%{'phoneError'}"/></s:fielderror></td></tr>
                                <tr>
                                    <td style="text-indent:10">联 系 人：</td>
                                    <td colspan="2"><s:textfield label="联 系 人" name="infoSingle.infoLinkman" size="50"/></td>
                                </tr>
                                <tr><td colspan="3"><s:fielderror><s:param value="%{'linkmanError'}"/></s:fielderror></td></tr>
                                <tr>
                                    <td style="text-indent:10">E - mail：</td>
                                    <td colspan="2"><s:textfield label="E - mail" name="infoSingle.infoEmail" size="55"/></td>                    
                                </tr>
                                <tr><td colspan="3"><s:fielderror><s:param value="%{'emailError'}"/></s:fielderror></td></tr>                        
                            </table>                
                        </td>
                    </tr>
                    <tr align="center" height="50">
                        <td>
                            <s:submit value="发布"/>
                            <s:reset value="重填"/>
                        </td>
                    </tr>
                </table>
                </s:form>            
            </td>
        </tr>
        <tr height="26"><td><img src="images/default_e.jpg"></td></tr>        
    </table>
    </div>
</body>
</html>