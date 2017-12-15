<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#bcdnr').focus();
		$('#bcdnr').keypress(function(event) {
			if (event.keyCode == 13) {
				nextBox = $('#cb_1');
				nextBox.focus();
				nextBox.select();
				event.preventDefault();
				return false;
			}
		});
		
		$(".cbinput").keypress(function(event) {
			if (event.keyCode == 13) {
				var duplicate = false;
				
				i = Number(this.id.substring(3));
				
				for ( var k = 1; k < i; k++) {
					var obj = $("#cb_" + k);
					if (obj.val().length > 0 && obj.val() == this.value) {
						duplicate = true;
						alert("條碼重複, barcode duplicated scanned");
						this.value = "";
						break;
					}
				}
				
				if (duplicate == false) {
					j = i + 1;
					try {
						nextBox = $("#cb_" + j);
						nextBox.focus();
						nextBox.select();
					} catch (e) {
					}
				}
				event.preventDefault();
				return false;
			}
		});
		
	});
</script>

<div id="tabs">
	<ul>
		<li><a href="#tab-1">BCD003 條碼序號連接輸入</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/BCD003/create" method="post">

			<table>
				<tr>
					<th><b>父件條碼</b></th>
					<th><input type="text" name="bcdnr" id="bcdnr" style="width: 300px;" value="${param.mattyp_low}"></th>
				</tr>
				<tr>
					<c:forEach begin="1" end="20" step="1" var="i">
						<tr>
							<th>${i}</th>
							<td>
								<input id="cb_${i}" name="cb_${i}" style="width: 300px;" class="cbinput">
							</td>
						</tr>
					</c:forEach>
				</tr>
			</table>
			<input type="reset" value="清空表單">
			<input id="submit" type="submit" value="確認提交" style="height: 30px; font-size: xx-large;">
		</form>



	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />