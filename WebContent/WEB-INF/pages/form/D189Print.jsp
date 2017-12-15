<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Print</title>
</HEAD>
<link type="text/css" rel="stylesheet" href="/iMes/stylesheet/redmond/jquery-ui-1.8.17.custom.css">
<script type="text/javascript" src="/iMes/javascript/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/iMes/javascript/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="/iMes/javascript/app.js"></script>

<style type="text/css">
<!--
#tab-1{
	height:800px;
	width:1050px;
	background-image:url(/iMes/stylesheet/images/3.jpg);
	background-position:center;
	background-repeat:no-repeat;
}
-->
</style>

<script type="text/javascript">
function printsetup(){
// 打印页面设置
	wb.execwb(8,1);
}

function printpreview(){
// 打印页面预览

	wb.execwb(7,1);
}

function printit()
{
	if (confirm('确定打印吗？')) {
		setPrintStatus();

		bdhtml=window.document.body.innerHTML;
		sprnstr="<!--start-print-->";
		eprnstr="<!--end-print-->";
		prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		window.document.body.innerHTML=prnhtml; 
		window.print();
		window.document.body.innerHTML=bdhtml; 
		//wb.execwb(6,6);
		//document.getElementsByName("button_print").style.display='none';
		//document.getElementsByName("button_setup").style.display='none';
		//document.getElementsByName("button_show").style.display='none';
		//document.getElementsByName("button_fh").style.display='none';
	}
}

function setPrintStatus() {
	  jQuery.ajax({
	    url : "/iMes/ajax",
	    data : {
		  GSDM : $('#GSDM').val(),
		  BDDM : $('#BDDM').val(),
	      BDBH : $('#BDBH').val(),
	      action : 'setPrintStatus'
	    },
	    cache : false
	  }).done(function(html) {
	  });
	}

</script>
<BODY>
<div id="tabs">
  <input type="hidden" name="GSDM" value="${D189.GSDM}">
  <input type="hidden" name="BDDM" value="${D189.BDDM}">
  <input type="hidden" name="BDBH" value="${D189.BDBH}">
 <div id="tab-${D189.DYZT}">
 
<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0"></OBJECT>
<input type="button" name="button_print" value="打印" onclick="javascript:printit()"/>
<input type="button" name="button_setup" value="打印页面设置" onclick="javascript:printsetup()"/>
<input type="button" name="button_show" value="打印预览" onclick="javascript:printpreview()"/>
<input type="button" name="button_fh" value="关闭" onclick="javascript:window.close();"/>

<!--start-print-->


   <table border="1" cellSpacing="0" cellPadding="0" style="width:1150px;">
    <caption><font size="8">重工申請單</font></caption>
    <tbody>
     <tr>
      <th colspan="2">公司代碼</th>
      <td>${D189.GSDM}</td>
      <th>库存仓别</th>
      <td>${D189.KCCB}</td>
      <th>客户编号</th>
      <td>${D189.KHBH}</td>
     </tr>
     <tr>
      <th colspan="2">设计编号</th>
      <td>${D189.SJBH}</td>
      <th>归属部門</th>
      <td>${D189.BMMC}</td>
      <th>重工数量</th>
      <td>${D189.CQTY}</td>
     </tr>
     <tr>
      <th colspan="2">出货日期</th>
      <td>${D189.CHSJ}</td>
      <th>表單編號</th>
      <td>${D189.BDBH}</td> 
      <th>表單附檔</th>
      <c:if test="${D189.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${D189.BDFD}">下載</a></td>
      </c:if>
      <c:if test="${D189.BDFD.length()== null ||D189.BDFD.length()==0 }">
       <td></td>
      </c:if>
     </tr>
     <tr>
      <th colspan="2">表單日期</th>
      <td>${D189.BDRQ}</td>
      <th>重工說明會</th>
      <td colspan="3">
      <c:choose>
        <c:when test="${D189.SMH eq '1'}"> 
	      <input type="radio" name="SMH" value="1" disabled="disabled" checked>不需要
	      <input type="radio" name="SMH" value="0" disabled="disabled">需要
	    </c:when>
        <c:when test="${D189.SMH eq '0'}"> 
	      <input type="radio" name="SMH" value="1" disabled="disabled">不需要
	      <input type="radio" name="SMH" value="0" disabled="disabled" checked>需要
	    </c:when>
	    <c:otherwise>
          <input type="radio" name="SMH" value="1" disabled="disabled">不需要
          <input type="radio" name="SMH" value="0" disabled="disabled" checked>需要
	    </c:otherwise>
      </c:choose>
      </td>
     </tr>
     <tr>
      <th>重工原因说明</th>
      <td colspan="7">${D189.CGYY}</td>
     </tr>
     <tr>
      <th>重工处理方式说明</th>
      <td colspan="7">${D189.CGFS}</td>
     </tr>
    </tbody>
   </table>
   
<table border="1" cellSpacing="0" cellPadding="0" >
<caption>簽核記錄</caption>
 <thead>
  <tr>
   <th>序號</th>
   <th>&nbsp;</th>
   <th>簽核人</th>
   <th>通知<br />核准時間
   </th>
   <th>結果</th>
   <th>文件</th>
   <th>內容</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach var="e" items="${BDLCS}">
   <tr>
    <td>${e.BZDM}</td>
    <td>${e.QHLX}</td>
    <td>${e.YSYH}<br /> <b>${e.QHYH}</b>
    </td>
    <td><fmt:formatDate value="${e.YJSJ}" type="both" pattern="yyyyMMdd HH:mm" /> <br /> <b><fmt:formatDate value="${e.QHSJ}" type="both" pattern="yyyyMMdd HH:mm" /></b></td>
     <td>&nbsp;${e.QHJG}</td>
     <td>&nbsp;</td>
    <td>&nbsp;${e.QHNR}</td>
   </tr>
  </c:forEach>
 </tbody>
</table>

<!--end-print-->

<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0"></OBJECT>
<input type="button" name="button_print" value="打印" onclick="javascript:printit()"/>
<input type="button" name="button_setup" value="打印页面设置" onclick="javascript:printsetup()"/>
<input type="button" name="button_show" value="打印预览" onclick="javascript:printpreview()"/>
<input type="button" name="button_fh" value="关闭" onclick="javascript:window.close()">
  
 </div> 
 
</div>
</BODY>
</html>
