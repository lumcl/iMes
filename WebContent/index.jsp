<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iMes Manufacturing System</title>
</head>
<body>
	<div style="float: right;">
		<img alt="imes" src="/iMes/qrcode?data=1234567890-123-11\nPO:12345678-10\nMT:LS-XXXX" style="display: inline;">
	</div>
	<h1>iMes Login</h1>
	<form action="login" method="post">
		<table>
			<tr>
				<td>用戶帳號:</td>
				<td><input type="text" name="user" size="20" value="${sessionScope.juid}" /></td>
			</tr>
			<tr>
				<td>登入密碼:</td>
				<td><input type="password" name="password" size="20" /></td>
			</tr>
		</table>
		<br /> <input type="submit" value="登入系統" />
	</form>

	<br />
	<h2>${message}</h2>
	<c:if test="${pageContext.request.method=='PUT'}">
		<br />
		<br />
		<h3>用戶帳戶或登入密碼錯誤, 請再次輸入帳戶及密碼</h3>
		<br />
		<h4>如有問題請聯繫MIS</h4>
	</c:if>
</body>
</html>