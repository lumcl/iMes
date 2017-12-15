<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">BCD002 Scan Results</a></li>
	</ul>

	<div id="tab-1">

			<table id="BCP002_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>序号</th>
						<th>Barcode</th>
						<th>子條碼</th>
						<th>位置</th>
						<th>輸入日期</th>
						<th>輸入時間</th>
						<th>輸入用戶</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>${e.ROWID}</td>
							<td>${e.BCDNR}</td>
							<td>${e.CBCDNR}</td>
							<td>${e.POSNR}</td>
							<td>${e.ERDAT}</td>
							<td>${e.ERTIM}</td>
							<td>${e.ERNAM}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<a href="/iMes/BCD002">繼續下一個掃描</a>

	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />