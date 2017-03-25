<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'test.jsp' starting page</title>
<script type="text/javascript">
   		 window.onunload=function(){
       		 var newWindow;
		     if((window.screenLeft>=10000 && window.screenTop>=10000)||event.altKey){  
		        newWindow=window.open('退出程序地址','网页名称','width=0,height=0,top=4000,left=4000');//新窗口将在视区之外打开
		        newWindow.opener=null;
		        sleep(5000);	
		        newWindow.close();//新窗口关闭
		
		     }  
		     function sleep(milisecond){
			   var currentDate,beginDate=new Date();		
			   var beginHour,beginMinute,beginSecond,beginMs;		
			   var hourGaps,minuteGaps,secondGaps,msGaps,gaps;		
			   beginHour=beginDate.getHours();			
			   beginMinute=beginDate.getMinutes();		
			   beginSecond=beginDate.getSeconds();		
			   beginMs=beginDate.getMilliseconds();		
			   do	
			   {	
			      currentDate=new Date();
			      hourGaps=currentDate.getHours() - beginHour;
			      minuteGaps=currentDate.getMinutes() - beginMinute;
			      secondGaps=currentDate.getSeconds() - beginSecond;
			      msGaps=currentDate.getMilliseconds() - beginMs;     
			      if(hourGaps<0)   hourGaps+=24;   //考虑进时进分进秒的特殊情况
			      gaps=hourGaps*3600+ minuteGaps*60+ secondGaps;
			      gaps=gaps*1000+msGaps;
			   }while(gaps<milisecond);  
			} 
}
   		
   </script>

</head>

<body>
	This is my JSP page.
	<br>
</body>
</html>
