<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form action="/iMes/D050/saveSalesOrder" method="post" id="salesOrderForm">
	<div id="bdf">
		<div id="adminSection">
			<h2>
				<span class="section">客戶銷售訂單</span>
			</h2>
			<input type="hidden" id="id" name="id" value="${d050s.id}">
			<input type="hidden" id="bdbh" name="bdbh" value="${d050s.bdbh}">
			<div style="display: inline-table; vertical-align: middle;">
				<table>
					<thead>
						<tr>
							<th>訂單號</th>
							<th>行號</th>
							<th>數量</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<input type="text" id="vbeln" name="vbeln" style="width: 80px;" />
							</td>
							<td>
								<input type="text" id="posnr" name="posnr" style="width: 50px;" />
							</td>
							<td>
								<input type="text" id="menge" name="menge" style="width: 80px;" />
							</td>
							<td>
								<button type="button" id="createBtn" onclick="location.href='/iMes/D050/newHeader'" style="width: 80px;">
									<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />增加SO
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<table>
			<thead>
				<tr>
					<th>訂單號</th>
					<th>行號</th>
					<th>工廠</th>
					<th>料號</th>
					<th>數量</th>
					<th>幣別</th>
					<th>單價</th>
					<th>USD價格</th>
					<th>USD金額</th>
					<th>交貨日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach items="${d050ss}" var="e">
						<td>${e.vbeln}</td>
						<td>${e.posnr}</td>
						<td>${e.werks}</td>
						<td>${e.matnr}</td>
						<td>${e.menge}</td>
						<td>${e.waerk}</td>
						<td>${e.netpr}</td>
						<td>${e.usdpr}</td>
						<td>${e.usdam}</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</form>
