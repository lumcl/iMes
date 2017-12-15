<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">BCD001 Serial Number Enquiry</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/BCD001/enquiry" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#BCD001_table')">
							<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />導出Excel
						</button>
					</li>
				</ul>
			</div>

			<table>
				<tr>
					<th>SN#</th>
					<td>
						<input type="text" name="bcdnr" id="bcdnr" style="width: 300px;" value="${param.mattyp_low}">
					</td>
				</tr>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="BCP001_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>序号</th>
						<th>掃描序號</th>
						<th>料號</th>
						<th>批次</th>
						<th>輸入日期</th>
						<th>輸入時間</th>
						<th>輸入用戶</th>
						<th>刪除旗標</th>
						<th>狀態</th>
						<th>工單號</th>
						<th>採購單</th>
						<th>採購單行號</th>
						<th>機種說明</th>
						<th>機種類別</th>
						<th>供應商號碼</th>
						<th>供應商名稱</th>
						<th>工廠</th>
						<th>收貨日期</th>
						<th>生產日期</th>
						<th>供應商批次</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>${e.ROWID}</td>
							<td>${e.BCDNR}</td>
							<td>${e.MATNR}</td>
							<td>${e.CHARG}</td>
							<td>${e.ERDAT}</td>
							<td>${e.ERTIM}</td>
							<td>${e.ERNAM}</td>
							<td>${e.LOEKZ}</td>
							<td>${e.STATS}</td>
							<td>${e.AUFNR}</td>
							<td>${e.EBELN}</td>
							<td>${e.EBELP}</td>
							<td>${e.MAKTX}</td>
							<td>${e.MATKL}</td>
							<td>${e.LINFR}</td>
							<td>${e.SORTL}</td>
							<td>${e.WERKS}</td>
							<td>${e.BUDAT}</td>
							<td>${e.MFGDT}</td>
							<td>${e.BATCH}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<br/>
			
			<table>
				<caption>Inspection Results</caption>
				<thead>
					<tr>
						<th></th>
						<th>MTYPE</th>
						<th>MEV</th>
						<th>MTCP</th>
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
							<td>${e.MTYPE}</td>
							<td>${e.MEV}</td>
							<td>${e.MTCP}</td>
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

			<br/>

			<table id="BCP002_table" class="alt">
				<caption>Link Relations</caption>
				<thead>
					<tr>
						<th>序号</th>
						<th>掃描序號</th>
						<th>料號</th>
						<th>说明</th>
						<th>批次</th>
						<th>輸入日期</th>
						<th>輸入時間</th>
						<th>輸入用戶</th>
						<th>位置</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${link}" var="e">
						<tr>
							<td>${e.ROWID}</td>
							<td>${e.CBCDNR}</td>
							<td>${e.CMATNR}</td>
							<td>${e.CMAKTX}</td>
							<td>${e.CHARG}</td>
							<td>${e.ERDAT}</td>
							<td>${e.ERTIM}</td>
							<td>${e.ERNAM}</td>
							<td>${e.POSNR}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />