<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">D089</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/D089/LISTMATNR" method="post">
			<div id="icon">
				<button type="button" onclick="this.disabled=true;this.form.submit();">
					<img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
				</button>
				<button type="reset">
					<img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
				</button>
				<button id="create" type="button" onclick="location.href='/iMes/D089/CREATE'">
					<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" alt="建立報價單" />
				</button>
				<button type="button" onclick="$.toExcel('#D089_table')">
					<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
				</button>
			</div>

			<table>
				<tr>
					<th></th>
					<th>公司<br />Company
					</th>
					<th>單號<br />DocNumber
					</th>
					<th>日期<br />DocDate
					</th>
					<th>供應商<br />Supplier
					</th>
					<th>料號<br />Material
					</th>
					<th>結果<br />Result
					</th>
					<th>狀態<br />Status
					</th>
				</tr>
				<tr>
					<th>從[Low]</th>
					<td>
						<input type="text" name="gsdm_low" value="${param.gsdm_low}">
					</td>
					<td>
						<input type="text" name="bdbh_low" value="${param.bdbh_low}">
					</td>
					<td>
						<input type="text" name="bdrq_low" value="${param.bdrq_low}">
					</td>
					<td>
						<input type="text" name="lifnr_low" value="${param.lifnr_low}">
					</td>
					<td>
						<input type="text" name="matnr_low" value="${param.matnr_low}">
					</td>
					<td>
						<input type="text" name="bdjq_low" value="${param.bdjq_low}">
					</td>
					<td>
						<input type="text" name="bdzt_low" value="${param.bdzt_low}">
					</td>
				</tr>
				<tr>
					<th>到[End]</th>
					<td>
						<input type="text" name="gsdm_high" value="${param.gsdm_high}">
					</td>
					<td>
						<input type="text" name="bdbh_high" value="${param.bdbh_high}">
					</td>
					<td>
						<input type="text" name="bdrq_high" value="${param.bdrq_high}">
					</td>
					<td>
						<input type="text" name="lifnr_high" value="${param.lifnr_high}">
					</td>
					<td>
						<input type="text" name="matnr_high" value="${param.matnr_high}">
					</td>
					<td>
						<input type="text" name="bdjq_high" value="${param.bdjq_high}">
					</td>
					<td>
						<input type="text" name="bdzt_high" value="${param.bdzt_high}">
					</td>
				</tr>
			</table>
		</form>


		<c:if test="${pageContext.request.method=='POST'}">
			<table id="D089_table">
				<thead>
					<tr>
						<th></th>
						<th>單號</th>
						<th>公司</th>
						<th>報價日期</th>
						<th>核准時間</th>
						<th>結果</th>
						<th>報價原因</th>
						<th>供應商</th>
						<th>名稱</th>
						<th>料號</th>
						<th>規格</th>
						<th>單位</th>
						<th>幣別</th>
						<th>報價</th>
						<th>RMB報價</th>
						<th>舊價</th>
						<th>差異%</th>
						<th>工廠</th>
						<th>上傳</th>
						<th>變價</th>
						<th>料類</th>
						<th>採購組</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="e">
						<tr>
							<td>
								<button type="button" onclick="location.href='/iMes/D089/EDIT?GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
									<img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" />
								</button>
							</td>
							<td>${e.BDBH}</td>
							<td>${e.GSDM}</td>
							<td>${e.BDRQ}</td>
							<td>${e.QHSJ}</td>
							<td>${e.BDJG}</td>
							<td>${e.BDYY}</td>
							<td>${e.LIFNR}</td>
							<td>${e.SORTL}</td>
							<td>${e.MATNR}</td>
							<td>${e.MAKTX}</td>
							<td>${e.MEINS}</td>
							<td>${e.WAERS}</td>
							<td>${e.UNTPR}</td>
							<td>${e.RMBPR}</td>
							<td>${e.OLDPR}</td>
							<td>${e.OLDPT}</td>
							<td>${e.WERKS}</td>
							<td>${e.SAPCF}</td>
							<td>${e.RFCCF}</td>
							<td>${e.MATKL}</td>
							<td>${e.EKGRP}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />