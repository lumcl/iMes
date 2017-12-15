<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iMes Manufacturing System</title>
</head>
<link type="text/css" rel="stylesheet" href="/iMes/stylesheet/redmond/jquery-ui-1.8.17.custom.css">
<script type="text/javascript" src="/iMes/javascript/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/iMes/javascript/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="/iMes/javascript/jquery.validate.min.js"></script>
<script type="text/javascript" src="/iMes/javascript/app.js"></script>
<link type="text/css" rel="stylesheet" href="/iMes/stylesheet/style.css">
<body>
 <form action="/iMes/toExcel" method="post" id="excelform" accept-charset="UTF-8"><input type="hidden" name="htmlStr" id="htmlStrInput" /></form>
 <form action="/iMes/AppTcode" method="post" id="AppTcode_form">
 <div id="header">
  <ul id="menu">
   <li><a href="http://www.lei.com.tw">Leader Electronics Inc.</a></li>
   <li><a href="/iMes/home">Home</a></li>
   <li><input type="text" name="AppTcode" id="AppTcode"/><input type="submit" value="Go" id="AppTcode_button" style="width:40px;"></li>
   <li><a href="/iMes/login">Logout</a></li>
   <li><a href="/iMes/user?action=chgpassword">${sessionScope.uid} - ${sessionScope.uname}</a></li>
  </ul>
 </div>
  </form>
 <div id="clear"></div>
