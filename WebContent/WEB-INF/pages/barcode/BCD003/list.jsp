<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">BCD003 掃描結果</a></li>
	</ul>

	<div id="tab-1">

			<table id="BCP002_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>位置</th>
						<th>掃描序號</th>
						<th>料號</th>
						<th>说明</th>
						<th>批次</th>
						<th>輸入日期</th>
						<th>輸入時間</th>
						<th>輸入用戶</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>${e.POSNR}</td>
							<td>${e.CBCDNR}</td>
							<td>${e.CMATNR}</td>
							<td>${e.CMAKTX}</td>
							<td>${e.CHARG}</td>
							<td>${e.ERDAT}</td>
							<td>${e.ERTIM}</td>
							<td>${e.ERNAM}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<a href="/iMes/BCD003">繼續下一個掃描</a>

	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />