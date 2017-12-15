<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">送貨名單</a></li>
	</ul>

	<div id="tab-1">
		<div id="bdf">
			<c:forEach items="${list}" var="e">
				<div class="s1">
					<a href="/iMes/Q001/copy?id=${e.ID}">${e.LFDW }</a>
				</div>
			</c:forEach>
		</div>
	</div>

</div>

<style>
.s1 {
	width: 150px;
	display: inline-block;
	padding: 5px;
	font-size: large;
}
</style>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />