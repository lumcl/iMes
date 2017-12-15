<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">D050 - 客戶訂單取消查詢</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/D050/searchHeader" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/D050/newHeader'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建單據
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#D050H_table')">
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
						<th>客戶代碼</th>
						<th>取消單號</th>
						<th>機種</th>
						<th>SO單號</th>
						<th>單據日期</th>
						<th>申請人</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>從</th>
						<td>
							<input type="text" id="kunnr_low" name="kunnr_low" value="${params.kunnr_low}">
						</td>
						<td>
							<input type="text" id="bdbh_low" name="bdbh_low" value="${params.bdbh_low}">
						</td>
						<td>
							<input type="text" id="matnr_low" name="matnr_low" value="${params.matnr_low}">
						</td>
						<td>
							<input type="text" id="vbeln_low" name="vbeln_low" value="${params.vbeln_low}">
						</td>
						<td>
							<input type="text" id="bdrq_low" name="bdrq_low" class="datepick" value="${params.bdrq_low}">
						</td>
						<td>
							<input type="text" id="sqyh_low" name="sqyh_low" value="${params.sqyh_low}">
						</td>
					</tr>
					<tr>
						<th>到</th>
						<td>
							<input type="text" id="kunnr_high" name="kunnr_high" value="${params.kunnr_high}">
						</td>
						<td>
							<input type="text" id="bdbh_high" name="bdbh_high" value="${params.bdbh_high}">
						</td>
						<td>
							<input type="text" id="matnr_high" name="matnr_high" value="${params.matnr_high}">
						</td>
						<td>
							<input type="text" id="vbeln_high" name="vbeln_high" value="${params.vbeln_high}">
						</td>
						<td>
							<input type="text" id="bdrq_high" name="bdrq_high" class="datepick" value="${params.bdrq_high}">
						</td>
						<td>
							<input type="text" id="sqyh_high" name="sqyh_high" value="${params.sqyh_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="D050H_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>操作</th>
						<th>公司</th>
						<th>來訪單位</th>
						<th>原因</th>
						<th>來訪擔當</th>
						<th>預計到達</th>
						<th>預計離開</th>
						<th>狀態</th>
						<th>結果</th>
						<th>申請人</th>
						<th>表單</th>
						<th>類別</th>
						<th>來訪目的</th>
						<th>表單日前</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td><a href="/iMes/D050/editHeader?id=${e.ID}"><img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" style=""/>內容</a></td>
							<td>${e.GSDM}</td>
							<td>${e.LFDW}</td>
							<td>${e.BDYY}</td>
							<td>${e.LFDD}</td>
							<td>${e.YDRQ} ${e.YDSJ}</td>
							<td>${e.YLRQ} ${e.YLSJ}</td>
							<td>${e.BDZT}</td>
							<td>${e.BDJG}</td>
							<td>${e.SQYH}</td>
							<td>${e.BDBH}</td>
							<td>${e.LFLB}</td>
							<td>${e.LFMD}</td>
							<td>${e.BDRQ}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />