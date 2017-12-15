<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">MD10 - MRP Report By Product Bom</a></li>
	</ul>

	<div id="tab-1">
		<form action="MD10" method="post">
			<div id="icon">
				<button type="submit">
					<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
				</button>
				<button type="reset">
					<img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
				</button>
			</div>

			<table>
				<caption>Enquiry</caption>
				<tr>
					<th></th>
					<th>工廠 Plant</th>
					<th>機種 Product</th>
					<th>數量 Quantity</th>
				</tr>
				<tr>
					<th>From</th>
					<td>
						<input type="text" name="strwerks" value="${param.strwerks}">
					</td>
					<td>
						<input type="text" name="strmatnr" value="${param.strmatnr}">
					</td>
					<td>
						<input type="text" name="qty" value="${param.qty}">
					</td>
				</tr>
				<!-- 
				<tr>
					<th>To</th>
					<td>
						<input type="text" name="endwerks" value="${param.endwerks}">
					</td>
					<td>
						<input type="text" name="endmatnr" value="${param.endmatnr}">
					</td>
					<td></td>
				</tr>
				 -->
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<div id="icon">
				<button type="button" onclick="$.toExcel('#MD10List')">
					<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
				</button>
			</div>

			<table id="MD10List">
				<thead>
					<tr>
						<th>MatGrp</th>
						<th>Plant</th>
						<th>Material</th>
						<th>Specification</th>
						<th>Lt</th>
						<th>MOQ</th>
						<th>Uom</th>
						<th>PurOrd</th>
						<th>PurReq</th>
						<th>PlnOrd</th>
						<th>StkBal</th>
						<th>Supply</th>
						<th>Reqmnt</th>
						<th>MrpBal</th>
						<th>BomQty</th>
						<th>ReqQty</th>
						<th>AtpQty</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="e" items="${list}">
						<tr>
							<td>${e.CMATKL}</td>
							<td>${e.CWERKS}</td>
							<td>${e.CMATNR}</td>
							<td>${e.CMAKTX}</td>
							<td>${e.LDTIM}</td>
							<td>${e.MOQ}</td>
							<td>${e.DUOM}</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.POQTY}" pattern="#,###.###" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.PRQTY}" pattern="#,###.###" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.PLNORD}" pattern="#,###.###" />
							</td>
							<td class="dec4" bgcolor="#dbe6f3">
								<fmt:formatNumber value="${e.WHSBAL}" pattern="#,###.###" />
							</td>
							<td class="dec4" bgcolor="#dbe6f3">
								<fmt:formatNumber value="${e.SUPPLY}" pattern="#,###.###" />
							</td>
							<td class="dec4" bgcolor="#dbe6f3">
								<fmt:formatNumber value="${e.REQMNT}" pattern="#,###.###" />
							</td>
							<td class="dec4" bgcolor="#dbe6f3">
								<fmt:formatNumber value="${e.MRPBAL}" pattern="#,###.###" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.BOMQTY}" pattern="#,###.###" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.REQQTY}" pattern="#,###.###" />
							</td>
							<td class="dec4">
								<fmt:formatNumber value="${e.ATPQTY}" pattern="#,###.###" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</c:if>

	</div>



</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />