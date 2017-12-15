<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">BCD005 Serial Number Enquiry</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/BCD005/enquiry" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#BCP001_table')">
							<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />導出Excel
						</button>
					</li>
				</ul>
			</div>

			<table>
				<tr>
					<th>SN#</th>
					<td>
						<input type="text" name="bcdnr" id="bcdnr" style="width: 300px;" value="${param.bcdnr}">
					</td>
					<td>
						<input type="text" name="bcdnr_end" id="bcdnr_end" style="width: 300px;" value="${param.bcdnr_end}">
					</td>
				</tr>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="BCP001_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>層次</th>
						<th>條碼</th>
						<th>說明</th>
						<th>MT</th>
						<th>MEV</th>
						<th>MTCP</th>
						<th>供應商</th>
						<th>名稱</th>
						<th>輸入日期</th>
						<th>輸入時間</th>
						<th>作業人員</th>
						<th>料號</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>${e.level}</td>
							<td>${e.dspbcdnr}</td>
							<td>${e.product}</td>
							<td>${e.mtype}</td>
							<td>${e.mev}</td>
							<td>${e.mtcp}</td>
							<td>${e.lifnr}</td>
							<td>${e.sortl}</td>
							<td>${e.erdat}</td>
							<td>${e.ertim}</td>
							<td>${e.ernam}</td>
							<td>${e.matnr}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<br />

			<table>
				<caption>LED Driver Inspection Results</caption>
				<thead>
					<tr>
						<th></th>
						<th>LED_VOUT_1_1</th>
						<th>LED_IOUT_1_1</th>
						<th>VOUT_2_1_2</th>
						<th>IOUT_2_1_2</th>
						<th>VOUT_1_1_3</th>
						<th>IOUT_1_1_3</th>
						<th>VOUT_2_1_3</th>
						<th>IOUT_2_1_3</th>
						<th>THD_1_4</th>
						<th>PF_1_6</th>
						<th>EFF_1_6</th>
						<th>OVP_TRIP_1_7</th>
						<th>IINRMS_1_6</th>
						<th>PIN_1_4</th>
						<th>POUT_1_4</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${insp}" var="e">
						<tr>
							<td>${e.ROWID}</td>
							<td>${e.LED_VOUT_1_1}</td>
							<td>${e.LED_IOUT_1_1}</td>
							<td>${e.VOUT_2_1_2}</td>
							<td>${e.IOUT_2_1_2}</td>
							<td>${e.VOUT_1_1_3}</td>
							<td>${e.IOUT_1_1_3}</td>
							<td>${e.VOUT_2_1_3}</td>
							<td>${e.IOUT_2_1_3}</td>
							<td>${e.THD_1_4}</td>
							<td>${e.PF_1_6}</td>
							<td>${e.EFF_1_6}</td>
							<td>${e.OVP_TRIP_1_7}</td>
							<td>${e.IINRMS_1_6}</td>
							<td>${e.PIN_1_4}</td>
							<td>${e.POUT_1_4}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</c:if>

	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />