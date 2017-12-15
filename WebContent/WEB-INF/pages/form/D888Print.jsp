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
		sprnstr="<!--startprint-->";
		eprnstr="<!--endprint-->";
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
  <input type="hidden" name="GSDM" value="${D888.GSDM}">
  <input type="hidden" name="BDDM" value="${D888.BDDM}">
  <input type="hidden" name="BDBH" value="${D888.BDBH}">
 <div id="tab-${D888.DYZT}">
 
<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0"></OBJECT>
<input type="button" name="button_print" value="打印" onclick="javascript:printit()"/>
<input type="button"　name="button_setup" value="打印页面设置" onclick="javascript:printsetup();"/>
<input type="button"　name="button_show" value="打印预览" onclick="javascript:printpreview();"/>
<input type="button" name="button_fh" value="关闭" onclick="javascript:window.close();">

<!--startprint-->


   <table border="1" cellSpacing="0" cellPadding="0" width="1050">
    <caption><font size="8">出廠單</font></caption>
    <tbody>
     <tr>
      <th>公司代碼</th>
      <td>${D888.GSDM}</td>
      <th>出廠類型</th>
      <td>${D888.CCLX}</td>
      <th>財產編號</th>
      <td>${D888.CCBH}</td>
      <th>領料編號</th>
      <td>${D888.LLBH}</td>
      <th>供應商</th>
      <td>${D888.GYSM}</td>
     </tr>
     <tr>
      <th>部門代碼</th>
      <td>${D888.BMBH}</td>
      <th>部門名稱</th>
      <td>${D888.BMMC}</td>
      <th>總金額</th>
      <td>${D888.ZJE}元</td>     
      <th>跟蹤</th>
      <td>${D888.BDGZ}</td>
      <th>表單編號</th>
      <td>${D888.BDBH}</td>
     </tr>
    <tr>
     <th>退料編號</th>
     <td>${D888.TLBH}</td>
     <th>物料編號</th>
     <td>${D888.WLBH}</td>
     <th>訂單編號</th>
     <td>${D888.DDBH}</td>
     <th>表單日期</th>
     <td>${D888.BDRQ}</td>            
     <th>表單附檔</th>
     <c:if test="${D888.BDFD.length() > 0}">
      <td><a href="/iMes/FileDownloader?filePath=${D888.BDFD}">下載</a></td>
     </c:if>
     <c:if test="${D888.BDFD.length()== null ||D888.BDFD.length()==0 }">
      <td></td>
     </c:if>
    </tr>
    <tr>
     <th>出廠內容</th>
     <td colspan="10">${D888.CCNR}</td>
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

<!--endprint-->

<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0"></OBJECT>
<input type="button" name="button_print" value="打印" onclick="javascript:printit()"/>
<input type="button"　name="button_setup" value="打印页面设置" onclick="javascript:printsetup();"/>
<input type="button"　name="button_show" value="打印预览" onclick="javascript:printpreview();"/>
<input type="button" name="button_fh" value="关闭" onclick="javascript:window.close();">
  
 </div> 
 
</div>
</BODY>
</html>
