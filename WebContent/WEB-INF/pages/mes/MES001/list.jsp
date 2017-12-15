<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">MES001 - PDA收料查询</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/MES001/enquiry" method="post">
			<div id="icon">
				<button type="button" onclick="this.disabled=true;this.form.submit();">
					<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
				</button>
				<button type="reset">
					<img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
				</button>
				<button type="button" onclick="$.toExcel('#MM66_table')">
					<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
				</button>
			</div>
			<table>
				<thead>
					<tr>
						<th></th>
						<th>收料日期</th>
						<th>工廠</th>
						<th>物料</th>
						<th>供應商</th>
						<th>物料群組</th>
						<th>採購組</th>
						<th>供應商DN</th>
						<th>IEB進口號</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>從</th>
						<td>
							<input type="text" name="crdat_low" size="8" value=${param.crdat_low }>
						</td>
						<td>
							<input type="text" name="werks_low" size="6" value=${param.werks_low }>
						</td>
						<td>
							<input type="text" name="matnr_low" size="20" value=${param.matnr_low }>
						</td>
						<td>
							<input type="text" name="lifnr_low" size="12" value=${param.lifnr_low }>
						</td>
						<td>
							<input type="text" name="matkl_low" size="8" value=${param.matkl_low }>
						</td>
						<td>
							<input type="text" name="ekgrp_low" size="6" value=${param.ekgrp_low }>
						</td>
						<td>
							<input type="text" name="refdn_low" size="16" value=${param.refdn_low }>
						</td>
						<td>
							<input type="text" name="imbil_low" size="10" value=${param.imbil_low }>
						</td>
					</tr>
					<tr>
						<th>到</th>
						<td>
							<input type="text" name="crdat_high" size="8" value=${param.crdat_high }>
						</td>
						<td>
							<input type="text" name="werks_high" size="6" value=${param.werks_high }>
						</td>
						<td>
							<input type="text" name="matnr_high" size="20" value=${param.matnr_high }>
						</td>
						<td>
							<input type="text" name="lifnr_high" size="12" value=${param.lifnr_high }>
						</td>
						<td>
							<input type="text" name="matkl_high" size="8" value=${param.matkl_high }>
						</td>
						<td>
							<input type="text" name="ekgrp_high" size="6" value=${param.ekgrp_high }>
						</td>
						<td>
							<input type="text" name="refdn_high" size="16" value=${param.refdn_high }>
						</td>
						<td>
							<input type="text" name="imbil_high" size="10" value=${param.imbil_high }>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<c:if test="${pageContext.request.method=='POST'}">
		<table id="mes001_enquiry">
			<thead>
				<tr>
					<th>ID</th>
					<th>LIFNR</th>
					<th>REFDN</th>
					<th>IMBIL</th>
					<th>MATNR</th>
					<th>MATKL</th>
					<th>MAKTX</th>
					<th>EKGRP</th>
					<th>WERKS</th>
					<th>BATCH</th>
					<th>PRDAT</th>
					<th>VTYPE</th>
					<th>PONUM</th>
					<th>POITM</th>
					<th>EBQTY</th>
										<th>SORTL</th>
					<th>CRDAT</th>
					<th>CRTIM</th>
					<th>CRNAM</th>
					<th>CRTEM</th>
					<th>SMDAT</th>
					<th>SMTIM</th>
					<th>SMNAM</th>
					<th>SMTEM</th>
					<th>SMFLG</th>
					<th>RTYPE</th>
					<th>MTYPE</th>
					<th>MESGE</th>
					<th>CHARG</th>
					<th>MBLNR</th>
					<th>MJAHR</th>
					<th>ZEILE</th>
					<th>RTDAT</th>
					<th>RTTIM</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="e" items="${list}">
					<tr>
						<td>${e.ID}</td>
						<td>${e.LIFNR}</td>
						<td>${e.REFDN}</td>
						<td>${e.IMBIL}</td>
						<td>${e.MATNR}</td>
						<td>${e.MATKL}</td>
						<td>${e.MAKTX}</td>
						<td>${e.EKGRP}</td>
						<td>${e.WERKS}</td>
						<td>${e.BATCH}</td>
						<td>${e.PRDAT}</td>
						<td>${e.VTYPE}</td>
						<td>${e.PONUM}</td>
						<td>${e.POITM}</td>
						<td>${e.EBQTY}</td>
						<td>${e.SORTL}</td>
						<td>${e.CRDAT}</td>
						<td>${e.CRTIM}</td>
						<td>${e.CRNAM}</td>
						<td>${e.CRTEM}</td>
						<td>${e.SMDAT}</td>
						<td>${e.SMTIM}</td>
						<td>${e.SMNAM}</td>
						<td>${e.SMTEM}</td>
						<td>${e.SMFLG}</td>
						<td>${e.RTYPE}</td>
						<td>${e.MTYPE}</td>
						<td>${e.MESGE}</td>
						<td>${e.CHARG}</td>
						<td>${e.MBLNR}</td>
						<td>${e.MJAHR}</td>
						<td>${e.ZEILE}</td>
						<td>${e.RTDAT}</td>
						<td>${e.RTTIM}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />