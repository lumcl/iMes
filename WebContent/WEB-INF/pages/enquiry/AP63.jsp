<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">AP貨到付款計劃</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/AP63" method="post" id="formAp63">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#AP63_table')">
							<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />導出Excel
						</button>
					</li>
				</ul>
			</div>
			<table>
				<caption>查詢參數</caption>
				<thead>
					<tr>
						<th></th>
						<th>收貨日期</th>
						<th>公司代碼</th>
						<th>付款條件</th>
						<th>供應商代碼</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>From</th>
						<td>
							<input type="text" name="budat_low" value="${param.budat_low}" class="required datepick">
						</td>
						<td>
							<input type="text" name="bukrs_low" value="${param.bukrs_low}" class="required">
						</td>
						<td>
							<input type="text" name="zterm_low" value="${param.zterm_low == null ? '0001' : param.zterm_low}" class="required">
						</td>
						<td>
							<input type="text" name="lifnr_low" value="${param.lifnr_low}">
						</td>
					</tr>
					<tr>
						<th>To</th>
						<td>
							<input type="text" name="budat_high" value="${param.budat_high}" class="datepick">
						</td>
						<td>
							<input type="text" name="bukrs_high" value="${param.bukrs_high}">
						</td>
						<td>
							<input type="text" name="zterm_high" value="${param.zterm_high}">
						</td>
						<td>
							<input type="text" name="lifnr_high" value="${param.lifnr_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="AP63_table">
				<caption>AP貨到付款計劃查询结果</caption>
				<thead>
					<tr>
						<th>收貨日期</th>
						<th>公司代碼</th>
						<th>供應商代碼</th>
						<th>供應商名稱</th>
						<th>採購文件</th>
						<th>採購行號</th>
						<th>物料號</th>
						<th>規格說明</th>
						<th>工廠</th>
						<th>單價</th>
						<th>倍數</th>
						<th>幣別</th>
						<th>入庫數量</th>
						<th>付款金額</th>
						<th>稅碼</th>
						<th>MvT</th>
						<th>年份</th>
						<th>過帳文件號</th>
						<th>過帳序號</th>
						<th>PO日期</th>
						<th>入庫者</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="e" items="${list}">
						<tr>
							<td>${e.BUDAT}</td>
							<td>${e.BUKRS}</td>
							<td>${e.LIFNR}</td>
							<td>${e.NAME1}</td>
							<td>${e.EBELN}</td>
							<td>${e.EBELP}</td>
							<td>${e.MATNR}</td>
							<td>${e.TXZ01}</td>
							<td>${e.WERKS}</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.NETPR}" pattern="#,###.00" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.PEINH}" pattern="#,###.####" />
							</td>
							<td>${e.WAERS}</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.MENGE}" pattern="#,###.####" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.WRBTR}" pattern="#,###.00" />
							</td>
							<td>${e.MWSKZ}</td>
							<td>${e.BWART}</td>
							<td>${e.GJAHR}</td>
							<td>${e.BELNR}</td>
							<td>${e.BUZEI}</td>
							<td>${e.BEDAT}</td>
							<td>${e.ERNAM}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

</div>

<script type="text/javascript">
	$(function() {
		$("#formAp63").validate({
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
</script>
<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />