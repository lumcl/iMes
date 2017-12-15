<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {
		if ($('#bcdnr').val() == "") {
			$('#bcdnr').focus();
			$('#bcdnr').select();
		} else if ($('#bqty').val() == "") {
			$('#bqty').focus();
			$('#bqty').select();
		} else {
			nextBox = $('#cb_1');
			nextBox.focus();
			nextBox.select();
		}
		
		$('#bcdnr').keypress(function(event) {
			if (event.keyCode == 13) {
				nextBox = $('#bqty');
				nextBox.focus();
				nextBox.select();
				event.preventDefault();
				return false;
			}
		});
		
		$('#bqty').keypress(function(event) {
			if (event.keyCode == 13) {
				if (this.value == 0 || this.value > 5) {
					alert("燈珠數量必須 1 到 5");
					this.value = "";
				} else {
					nextBox = $('#cb_1');
					nextBox.focus();
					nextBox.select();
				}
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
		<li><a href="#tab-1">BCD004 燈珠連接燈板掃描</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/BCD004/create" method="post">
			<table>
				<tr>
					<th><b>燈珠條碼</b></th>
					<th><input type="text" name="bcdnr" id="bcdnr" style="width: 300px;" value="${sessionScope.bcd004_bcdnr}"></th>
					<th><b>燈珠數量</b></th>
					<th><input type="number" name="bqty" id="bqty" value="${sessionScope.bcd004_bqty}" min="1" max="5"></th>
				</tr>
			</table>
			<table>
				<caption>燈板條碼</caption>
				<tr>
					<c:forEach begin="1" end="15" step="1" var="i">
						<tr>
							<th>${i}</th>
							<td>
								<input id="cb_${i}" name="cb_${i}" style="width: 300px;" class="cbinput">
							</td>
							<th>${i+15}</th>
							<td>
								<input id="cb_${i+15}" name="cb_${i+15}" style="width: 300px;" class="cbinput">
							</td>
						</tr>
					</c:forEach>
				</tr>
				<tr>
					<th></th>
					<th>
					</th>
					<th></th>
					<th>
						<h2>
							<input id="submit" type="submit" value="確認提交">
						</h2>
					</th>
				</tr>
			</table>
		</form>
	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />