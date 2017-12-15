<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">EQP002 备品配件主档</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/EQP002/search" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/EQP002/create'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建備件
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#EQP002_table')">
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
						<th>工廠</th>
						<th>物件號碼</th>
						<th>物件大類</th>
						<th>物件小類</th>
						<th>物件狀態</th>
						<th>保管部門</th>
						<th>保管人</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>從</th>
						<td>
							<input type="text" id="cmpnbr_low" name="cmpnbr_low" value="${param.cmpnbr_low}" style="width: 50px;" title="L210 東莞領航&#10L300 東莞立德&#10L400 江蘇領先">
						</td>
						<td>
							<input type="text" id="facnbr_low" name="facnbr_low" value="${param.facnbr_low}" style="width: 50px;" title=" 281A &#10 381A &#10 382A&#10 481A&#10 482A">
						</td>
						<td>
							<input type="text" id="matnbr_low" name="matnbr_low" value="${param.matnbr_low}">
						</td>
						<td>
							<input type="text" id="mattyp_low" name="mattyp_low" value="${param.mattyp_low}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="matgrp_low" name="matgrp_low" value="${param.matgrp_low}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="matsts_low" name="matsts_low" value="${param.matsts_low}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="rspdep_low" name="rspdep_low" value="${param.rspdep_low}">
						</td>
						<td>
							<input type="text" id="rspuid_low" name="rspuid_low" value="${param.rspuid_low}">
						</td>
					</tr>
					<tr>
						<th>到</th>
						<td>
							<input type="text" id="cmpnbr_high" name="cmpnbr_high" value="${param.cmpnbr_high}" style="width: 50px;" title="L210 東莞領航&#10L300 東莞立德&#10L400 江蘇領先">
						</td>
						<td>
							<input type="text" id="facnbr_high" name="facnbr_high" value="${param.facnbr_high}" style="width: 50px;" title=" 281A &#10 381A &#10 382A&#10 481A&#10 482A">
						</td>
						<td>
							<input type="text" id="matnbr_high" name="matnbr_high" value="${param.matnbr_high}">
						</td>
						<td>
							<input type="text" id="mattyp_high" name="mattyp_high" value="${param.mattyp_high}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="matgrp_high" name="matgrp_high" value="${param.matgrp_high}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="matsts_high" name="matsts_high" value="${param.matsts_high}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="rspdep_high" name="rspdep_high" value="${param.rspdep_high}">
						</td>
						<td>
							<input type="text" id="rspuid_high" name="rspuid_high" value="${param.rspuid_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="EQP002_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th></th>
						<th>序</th>
						<th>公司</th>
						<th>工廠</th>
						<th>物料號碼</th>
						<th>物料大類</th>
						<th>物料小類</th>
						<th>物料狀態</th>
						<th>物料說明</th>
						<th>物料型號</th>
						<th>物料規格</th>
						<th>物料品牌</th>
						<th>生產廠家</th>
						<th>安全數量</th>
						<th>庫存數量</th>
						<th>物料單位</th>
						<th>放置位置</th>
						<th>保管部門</th>
						<th>保管人</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>
								<a href="/iMes/EQP002/edit?id=${e.ID}"><img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" style="" />內容</a>
							</td>
							<td>${e.ROWID}</td>
							<td>${e.CMPNBR}</td>
							<td>${e.FACNBR}</td>
							<td>${e.MATNBR}</td>
							<td>${e.MATTYP}</td>
							<td>${e.MATGRP}</td>
							<td>${e.MATSTS}</td>
							<td>${e.MATDES}</td>
							<td>${e.MATMDL}</td>
							<td>${e.MATSPE}</td>
							<td>${e.MATBRD}</td>
							<td>${e.MFGNAM}</td>
							<td>${e.SAFQTY}</td>
							<td>${e.STKQTY}</td>
							<td>${e.MATUOM}</td>
							<td>${e.LOCATE}</td>
							<td>${e.REPDEP}</td>
							<td>${e.REPUID}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />