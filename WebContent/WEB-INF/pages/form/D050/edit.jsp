<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/pages/layout/header.jsp" />
<script type="text/javascript" src="/iMes/javascript/D050.js"></script>

<div id="tabs">
	<ul>
		<li><a href="#tab-1">D050 － 客戶訂單取消表單</a></li>
	</ul>

	<div id="tab-1">

		<jsp:include page="/WEB-INF/pages/form/D050/_header.jsp" />

		<c:if test="${(d050h.bdbh != '')}">
			<jsp:include page="/WEB-INF/pages/form/D050/_salesorders.jsp" />
			<jsp:include page="/WEB-INF/pages/form/_route.jsp" />
		</c:if>


	</div>

</div>

<div id="dialog-visitor" title="來訪人信息維護"></div>

<script type="text/javascript">
	$(document).ready(function() {
		if ($('#kunnr').val() == "") {
			$('#kunnr').focus();
			$('#kunnr').select();
		}
	});
</script>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />