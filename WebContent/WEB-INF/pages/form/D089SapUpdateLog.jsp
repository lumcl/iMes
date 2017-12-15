<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">SAP系統更新報價錯誤信息</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/D089/SapUpdateLog" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button id="create" type="button" onclick="location.href='/iMes/D089/CREATE'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" alt="建立報價單" />建立報價單
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#D089L_table')">
							<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />導出Excel
						</button>
					</li>
				</ul>
			</div>
			<table>
				<thead>
					<tr>
						<th></th>
						<th>供應商<br />Supplier
						</th>
						<th>料號<br />Material Nbr
						</th>
						<th>申請人<br />Requester
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>From</th>
						<td>
							<input type="text" name="lifnr_low" value="${param.lifnr_low}">
						</td>
						<td>
							<input type="text" name="matnr_low" value="${param.matnr_low}">
						</td>
						<td>
							<input type="text" name="sqyh_low" value="${param.sqyh_low}">
						</td>
					</tr>
					<tr>
						<th>To</th>
						<td>
							<input type="text" name="lifnr_high" value="${param.lifnr_high}">
						</td>
						<td>
							<input type="text" name="matnr_high" value="${param.matnr_high}">
						</td>
						<td>
							<input type="text" name="sqyh_high" value="${param.sqyh_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<table id="D089L_table" class="alt">
			<caption>查詢結果</caption>
			<thead>
				<tr>
					<th>供應商</th>
					<th>短名</th>
					<th>料號</th>
					<th>錯誤信息</th>
					<th>SAP更新時間</th>
					<th>規格</th>
					<th>更新原因</th>
					<th>公司代號</th>
					<th>報價單號</th>
					<th>採購組</th>
					<th>工廠</th>
					<th>申請人</th>
					<th>稅碼</th>
					<th>幣別</th>
					<th>單價</th>
					<th>倍數</th>
					<th>有效日前</th>
					<th>截至日前</th>
					<th>單位</th>
					<th>LT</th>
					<th>MOQ</th>
					<th>序號</th>
					<th>ID</th>
					<th>INFNR</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="e">
					<tr>
						<td>${e.LIFNR}</td>
						<td>${e.SORTL}</td>
						<td>${e.MATNR}</td>
						<td>${e.MSGTX}</td>
						<td>${e.SAPTM}</td>
						<td>${e.MAKTX}</td>
						<td>${e.BDYY}</td>
						<td>${e.GSDM}</td>
						<td>${e.BDBH}</td>
						<td>${e.EKORG}</td>
						<td>${e.WERKS}</td>
						<td>${e.SQYH}</td>
						<td>${e.MWSKZ}</td>
						<td>${e.WAERS}</td>
						<td>${e.NETPR}</td>
						<td>${e.PEINH}</td>
						<td>${e.DATAB}</td>
						<td>${e.PRDAT}</td>
						<td>${e.MEINS}</td>
						<td>${e.PLIFZ}</td>
						<td>${e.BSTRF}</td>
						<td>${e.SQNR}</td>
						<td>${e.ID}</td>
						<td>${e.INFNR}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />