<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">Q001 － 拜訪通知單</a></li>
	</ul>
<c:set var="HEADER_HOST" scope="page" value="${header['host']}"/>
<c:set var="REMOTEADDR" scope="page" value="${pageContext.request.remoteAddr }"/>
<c:set var="QUERYSTRING" scope="page" value="${pageContext.request.queryString}"/>
<c:set var="REQUESTURL" scope="page" value="${pageContext.request.requestURL}"/>
<c:set var="CONTEXTPATH" scope="page" value="${pageContext.request.contextPath}"/>
<c:set var="REQUEST_METHOD" scope="page" value="${pageContext.request.method}"/>
<c:set var="PROTOCOL" scope="page" value="${pageContext.request.protocol}"/>
<c:set var="REMOTEUSER" scope="page" value="${pageContext.request.remoteUser}"/>
<c:set var="SESSIONID" scope="page" value="${pageContext.session.id}"/>
<c:set var="SERVERINFO" scope="page" value="${pageContext.servletContext.serverInfo}"/>

<c:forEach items="${fn:split(REMOTEADDR,'.')}" var="val" varStatus="vs">
<c:if test="${vs.index==1}">
	<c:set var="REMOTEADDR_IP" scope="page" value="${val}"/>
</c:if>
</c:forEach>
	<div id="tab-1">
		<form action="/iMes/Q001/searchHeader" method="post">
			<div id="nav">
				<ul>					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/Q001/newHeader'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建單據
						</button>
					</li>
					<li>
						<button type="button" id="copyBtn" onclick="location.href='/iMes/Q001/selectVisitor'">
							<img src="/iMes/stylesheet/icons/S_F_COPY.GIF" />送貨拷貝
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#Q001H_table')">
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
						<th>公司</th>
						<th>預計到達日起</th>
						<th>通知單號</th>
						<th>申請人</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>從</th>
						<td>
							<input type="text" id="gsdm_low" name="gsdm_low" value="<c:if test="${REMOTEADDR_IP=='91'}">L300</c:if><c:if test="${REMOTEADDR_IP=='31'}">L400</c:if>">
						</td>
						<td>
							<input type="text" id="ydrq_low" name="ydrq_low" class="datepick" value="${params.ydrq_low}">
						</td>
						<td>
							<input type="text" id="bdbh_low" name="bdbh_low" value="${params.bdbh_low}">
						</td>
						<td>
							<input type="text" id="sqyh_low" name="sqyh_low" value="${params.sqyh_low}">
						</td>
					</tr>
					<tr>
						<th>到</th>
						<td>
							<input type="text" id="gsdm_high" name="gsdm_high" class="datepick" value="${params.gsdm_high}">
						</td>
						<td>
							<input type="text" id="ydrq_high" name="ydrq_high" class="datepick" value="${params.ydrq_high}">
						</td>
						<td>
							<input type="text" id="bdbh_high" name="bdbh_high" value="${params.bdbh_high}">
						</td>
						<td>
							<input type="text" id="sqyh_high" name="sqyh_high" value="${params.sqyh_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="Q001H_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>操作</th>
						<th>公司</th>
						<th>來訪單位</th>
						<th>原因</th>
						<th>來訪擔當</th>
						<th>來訪擔當姓名</th>
						<th>預計到達</th>
						<th>預計離開</th>
						<th>狀態</th>
						<th>結果</th>
						<th>申請人</th>
						<th>申请人姓名</th>
						<th>表單</th>
						<th>類別</th>
						<th>來訪目的</th>
						<th>表單日前</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td><a href="/iMes/Q001/editHeader?id=${e.ID}"><img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" style=""/>內容</a></td>
							<td>${e.GSDM}</td>
							<td>${e.LFDW}</td>
							<td>${e.BDYY}</td>
							<td>${e.LFDD}</td>
							<td>${e.LFDD_NAME}</td>
							<td>${e.YDRQ} ${e.YDSJ}</td>
							<td>${e.YLRQ} ${e.YLSJ}</td>
							<td>${e.BDZT}</td>
							<td>${e.BDJG}</td>
							<td>${e.SQYH}</td>
							<td>${e.SQYH_NAME}</td>
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