<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/PRD001.js"></script>

<div id="tabs">
	<ul>
		<li><a href="#tab-1">PRD001 － 生产管理维护</a></li>
	</ul>

	<div id="tab-1">

		<form action="/iMes/PRD001/saveHeader" method="post" id="headerForm">

			<input type="hidden" id="id" name="id" value="${prd001H.id}">

			<div id="nav">
				<ul>
					<c:if test="${(prd001H.id == 0)}">
						<li>
							<button type="button" id="saveHeaderBtn">
								<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" />保存
							</button>
						</li>
					</c:if>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/PRD001/newHeader'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建
						</button>
					</li>
					<li>
						<button type="button" onclick="history.back()">
							<img src="/iMes/stylesheet/icons/S_F_BACK.GIF" />返回前頁
						</button>
					</li>
					<c:if test="${prd001H.jlyh == sessionScope.uid && prd001H.id != 0}">
						<li>
							<button type="button" id="cancelBtn" onclick="location.href='/iMes/PRD001/cancelAction?id=${prd001H.id}'">
								<img src="/iMes/stylesheet/icons/S_B_DELE.GIF" />刪除
							</button>
						</li>
					</c:if>
				</ul>
			</div>

			<div id="bdf">
				<div>
					<label for="scsj">生產日期</label>
					<input type="text" id="scsj" name="scsj" value="${prd001H.scsj}" class="required datepick" />
				</div>
				<div>
					<label for="line">線別</label>
					<input type="text" id="line" name="line" value="${prd001H.line}" />
				</div>
				<div>
					<label for="mo">MO</label>
					<input type="text" id="mo" name="mo" value="${prd001H.mo}" />
				</div>
				<div>
					<label for="planqty">計畫數量</label>
					<input type="text" id="planqty" name="planqty" value="<fmt:formatNumber value="${prd001H.planqty}" pattern="#,###" />" />
				</div>
				<div>
					<label for="factqty">實際數量</label>
					<input type="text" id="factqty" name="factqty" value="<fmt:formatNumber value="${prd001H.factqty}" pattern="#,###" />" />
				</div>
			</div>
		</form>

	</div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />