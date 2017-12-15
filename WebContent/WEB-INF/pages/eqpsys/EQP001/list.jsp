<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">EQP001 工具物件主档</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/EQP001/search" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="searchBtn">
							<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" />執行查詢
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/EQP001/create'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建工具
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#EQP001_table')">
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
						<th>財產編號</th>
						<th>物件狀態</th>
						<th>保管部門</th>
						<th>保管人</th>
						<th>使用機種</th>
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
							<input type="text" id="eqpnbr_low" name="eqpnbr_low" value="${param.eqpnbr_low}">
						</td>
						<td>
							<input type="text" id="eqptyp_low" name="eqptyp_low" value="${param.eqptyp_low}" style="width: 50px;" title=" A ATE &#10 H 耐壓治具 &#10 I ICT &#10 T 特性治具 &#10 W 外觀治具 &#10 Z 過錫爐治具">
						</td>
						<td>
							<input type="text" id="eqpgrp_low" name="eqpgrp_low" value="${param.eqpgrp_low}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="astnbr_low" name="astnbr_low" value="${param.astnbr_low}">
						</td>
						<td>
							<input type="text" id="eqpsts_low" name="eqpsts_low" value="${param.eqpsts_low}" style="width: 50px;" title="OK 良好&#10NG 不良&#10MT 保養&#10RP 送修&#10SC 報廢&#10LS 遺失&#10LN 借出">
						</td>
						<td>
							<input type="text" id="rspdep_low" name="rspdep_low" value="${param.rspdep_low}">
						</td>
						<td>
							<input type="text" id="rspuid_low" name="rspuid_low" value="${param.rspuid_low}">
						</td>
						<td>
							<input type="text" id="prdnbr_low" name="prdnbr_low" value="${param.prdnbr_low}">
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
							<input type="text" id="eqpnbr_high" name="eqpnbr_high" value="${param.eqpnbr_high}">
						</td>
						<td>
							<input type="text" id="eqptyp_high" name="eqptyp_high" value="${param.eqptyp_high}" style="width: 50px;" title=" A ATE &#10 H 耐壓治具 &#10 I ICT &#10 T 特性治具 &#10 W 外觀治具 &#10 Z 過錫爐治具">
						</td>
						<td>
							<input type="text" id="eqpgrp_high" name="eqpgrp_high" value="${param.eqpgrp_high}" style="width: 50px;">
						</td>
						<td>
							<input type="text" id="astnbr_high" name="astnbr_high" value="${param.astnbr_high}">
						</td>
						<td>
							<input type="text" id="eqpsts_high" name="eqpsts_high" value="${param.eqpsts_high}" style="width: 50px;" title="OK 良好&#10NG 不良&#10MT 保養&#10RP 送修&#10SC 報廢&#10LS 遺失&#10LN 借出">
						</td>
						<td>
							<input type="text" id="rspdep_high" name="rspdep_high" value="${param.rspdep_high}">
						</td>
						<td>
							<input type="text" id="rspuid_high" name="rspuid_high" value="${param.rspuid_high}">
						</td>
						<td>
							<input type="text" id="prdnbr_high" name="prdnbr_high" value="${param.prdnbr_high}">
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<c:if test="${pageContext.request.method=='POST'}">
			<table id="EQP001_table" class="alt">
				<caption>查詢結果</caption>
				<thead>
					<tr>
						<th>操作</th>
						<th>序号</th>
						<th>公司</th>
						<th>工廠</th>
						<th>物件號碼</th>
						<th>物件說明</th>
						<th>物件大類</th>
						<th>物件小類</th>
						<th>財產編號</th>
						<th>物件狀態</th>
						<th>保管部門</th>
						<th>保管人</th>
						<th>使用機種</th>
						<th>放置位置</th>
						<th>治具放置櫃</th>
						<th>PCB板號</th>
						<th>組件</th>
						<th>機身編號</th>
						<th>線別</th>
						<th>保養日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>
								<a href="/iMes/EQP001/edit?id=${e.ID}"><img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" style="" />內容</a>
							</td>
							<td>${e.ROWID}</td>
							<td>${e.CMPNBR}</td>
							<td>${e.FACNBR}</td>
							<td>${e.EQPNBR}</td>
							<td>${e.EQPDES}</td>
							<td>${e.EQPTYP}</td>
							<td>${e.EQPGRP}</td>
							<td>${e.ASTNBR}</td>
							<td>${e.EQPSTS}</td>
							<td>${e.RSPDEP}</td>
							<td>${e.RSPUID}</td>
							<td>${e.PRDNBR}</td>
							<td>${e.LOCATE}</td>
							<td>${e.LOCKER}</td>
							<td>${e.PCBNBR}</td>
							<td>${e.COMPNR}</td>
							<td>${e.SERIAL}</td>
							<td>${e.PRDLIN}</td>
							<td>${e.MNTDAT}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />