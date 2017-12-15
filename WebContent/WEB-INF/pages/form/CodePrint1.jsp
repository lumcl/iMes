<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Label Print</title>
</HEAD>
<link type="text/css" rel="stylesheet" href="/iMes/stylesheet/redmond/jquery-ui-1.8.17.custom.css">
<script type="text/javascript" src="/iMes/javascript/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/iMes/javascript/jquery-ui-1.8.17.custom.min.js"></script>
<link type="text/css" rel="stylesheet" href="/iMes/stylesheet/print.css">

<style type="text/css">
<!--
body {
	margin-left:48px;
	font-size: 48px;
}
.title {
 /**background-image: url(images/two.jpg); */
	position: relative;
	width: 565px;
/**	font-size: 12px;*/
	float:left;
	margin:0 auto;
}
.title_lei {
	height:2px;
	width:50px;
	padding-top:40px;
	padding-left:40px;
	float:left;
}
.title_header {
	padding:20px,10px,10px,50px;
	float:left;
}
.title_imes {
	height:2px;
	width:20px;
	padding-top:-2px;
	padding-left:100px;
	float:left;
}
.label {
	clear:both;
}
.title_label1 {
	float:left;
}

#mian{
	width:108px;
	border:1px solid #ccc;
	overflow:hidden;
}

.alway{
	width:100%;
	height:30px;
	line-height:30px;
	clear:both
}
/* 每行的DIV ,宽度为100%；这样的好处就是只要父DIV增加宽度或者减少宽度那么下面的元素会跟着增加或者减少！*/

.list{
	width:10%;
	height:100%;
	float:left;
}
.list10{
	width:18%;
	height:100%;
	float:left;
	border-top:1px solid #000;
	border-left:1px solid #000;
	border-right:1px solid #000;
	border-bottom:1px solid #000;
}
/* 这是一列对吧,这个元素将是.alway下面的子DIVCSS样式 ，至于高度100%，首先你得声明下！*/

-->
</style>
<style type="text/css" media="print">
.noprint{
	display:none;
}
</style>
<script type="text/javascript">
</script>

<BODY>
<c:forEach var="e" items="${d}">
<div class="title">
	<div class="title_lei"><img src="/iMes/stylesheet/images/lei.jpg"/></div>
	<div class="title_header"><strong><span><font size="6">Leader Electronics</font></span><br/><span><font size="6">DT${e.GSDM}</font></span><br/><span><font size="6">進  料  標  示  紙</font></span></strong></div>
	<div class="title_imes"><img alt="imes" src="/iMes/qrcode?data=1234567890-123-11\nPO:12345678-10\nMT:LS-XXXX" style="display: inline;"></div>
</div>
<div class="label">
	<div class="title_label"><span><strong><font size="5">PO NO:</font></strong></span><span><font size="5">12345678901234567890</font></span><br/><span><strong><font size="5">料   號:</font></strong></span><span><font size="5">12345678901234567890</font></span><br/><span><strong><font size="5">倉   別:</font></strong></span><span><font size="5">123456</font></span></div>
	<div class="title_label"><span><strong><font size="5">環保階段:</font></strong></span><span><font size="5">12345678</font></span><span><strong><font size="5">    日期:</font></strong></span><span><font size="5">2012-12-31</font></span></div>
	<div class="title_label"><span><strong><font size="5">廠商LotNo:</font></strong></span><span><font size="5">12345678901234567890</font></span><br/><span><strong><font size="5">立德LotNo:</font></strong></span><span><font size="5">12345678901234567890</font></span><br/><span><strong><font size="5">規格:</font></strong></span><span><font size="5">1234567890123456789012345678901234567890</font></span></div>
	<div class="title_label"><span><strong><font size="5">廠商:</font></strong></span><span><font size="5">12345678</font></span></div>
</div>
<div id="main">
	<div class="alway"><div class="list"><strong><font size="5">進量:</font></strong></div><div class="list10"></div><div class="list10"></div><div class="list10"></div><div class="list10"></div></div>
	<div class="alway"><div class="list"><strong><font size="5">發量:</font></strong></div><div class="list10"></div><div class="list10"></div><div class="list10"></div><div class="list10"></div></div>
	<div class="alway"><div class="list"><strong><font size="5">余量:</font></strong></div><div class="list10"></div><div class="list10"></div><div class="list10"></div><div class="list10"></div></div>
</div>

<!-- the First end -->
</c:forEach>
</BODY>
</html>
