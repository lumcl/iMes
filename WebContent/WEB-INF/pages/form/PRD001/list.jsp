<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">PRD001 － 產線生产管理</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/PRD001/searchHeader" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />查詢
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/PRD001/newHeader'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#PRD001_table')">
							<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />導出Excel
						</button>
					</li>
				</ul>
			</div>

			<table>
				<caption>查詢條件</caption>
				<thead>
					<tr>
						<th></th>
						<th>生產日起</th>
						<th>線別</th>
						<th>MO</th>
						<th>創建人</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>從</th>
						<td>
							<input type="text" id="scsj_low" name="scsj_low" class="datepick" value="${params.scsj_low}">
						</td>
						<td>
							<input type="text" id="line_low" name="line_low" value="${params.line_low}">
						</td>
						<td>
							<input type="text" id="mo_low" name="mo_low" value="${params.mo_low}">
						</td>
						<td>
							<input type="text" id="jlsj_low" name="jlsj_low" value="${params.jlsj_low}">
						</td>
					</tr>
					<tr>
						<th>到</th>
						<td>
							<input type="text" id="scsj_high" name="scsj_high" class="datepick" value="${params.scsj_high}">
						</td>
						<td>
							<input type="text" id="line_high" name="line_high" value="${params.line_high}">
						</td>
						<td>
							<input type="text" id="mo_high" name="mo_high" value="${params.mo_high}">
						</td>
						<td>
							<input type="text" id="jlsj_high" name="jlsj_high" value="${params.jlsj_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="PRD001_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>操作</th>
						<th>生產日期</th>
						<th>線別</th>
						<th>MO</th>
						<th>計畫數量</th>
						<th>實際數量</th>
						<th>差異數量</th>
						<th>創建日期</th>
						<th>創建用戶</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td><a href="/iMes/PRD001/editHeader?id=${e.ID}"><img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" style=""/>內容</a></td>
							<td>${e.SCSJ}</td>
							<td>${e.LINE}</td>
							<td>${e.MO}</td>
							<td>${e.PLANQTY}</td>
							<td>${e.FACTQTY}</td>
							<td>${e.PLANQTY-e.FACTQTY}</td>
							<td>${e.JLSJ}</td>
							<td>${e.JLYH}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />