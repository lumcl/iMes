<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#form1').submit(function() {
			if ($('#password').val() == '' || $('#newpassword').val()=='' || $('#cfmpassword').val()==''){
				alert("密碼不能為空, 請重新輸入");
				return false;
			}
			if ($('#newpassword').val() == $('#cfmpassword').val()){

			}else{
				alert("新密碼和確認密碼不一樣, 請重新輸入");
				return false;
			}
		});
	});
</script>

<form action="user" method="post" id="form1" name="form1">
 <input type="hidden" name="action" value="chgpassword" />
 <fieldset>
  <legend>修改用戶密碼</legend>
  <div id="icon">
   <button type="submit">
    <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />
   </button>
      <button type="reset">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" />
   </button>
   <button type="button" onclick="history.go(-1)">
    <img src="/iMes/stylesheet/icons/S_F_BACK.GIF" />
   </button>
  </div>
  <table>
   <tr>
    <th>用戶帳戶 :</th>
    <td>${sessionScope.uid}</td>
   </tr>
   <tr>
    <th>用戶姓名 :</th>
    <td>${sessionScope.uname}</td>
   </tr>
   <tr>
    <th>現有密碼 :</th>
    <td><input type="password" name="password" id="password"/></td>
   </tr>
   <tr>
    <th>新密碼 :</th>
    <td><input type="password" name="newpassword" id="newpassword" /></td>
   </tr>
   <tr>
    <th>確認密碼 :</th>
    <td><input type="password" name="cfmpassword" id="cfmpassword" /></td>
   </tr>
  </table>
 </fieldset>
</form>
<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />