<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D089.js"></script>

<div id="tabs">
	<ul>
		<li><a href="#tab-1">報價表單 Header</a></li>
		<li><a href="#tab-2">報價物料 Material</a></li>
		<li><a href="#tab-5">報價上傳 Upload</a></li>
		<li><a href="/iMes/D089/GetChgPriceErrLog?GSDM=${D089H.GSDM}&BDDM=${D089H.BDDM}&BDBH=${D089H.BDBH}">錯誤信息 ErrorLog</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/D089/D089HSAVE" method="post" id="saveForm" name="saveForm">
			<div id="icon">
				<c:if test="${(D089H.SQYH == sessionScope.uid) && (D089H.QHKS != 'X')}">
					<button type="button" id="saveBtn">
						<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存 Save" />
					</button>
					<button type="reset">
						<img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原 Redo" />
					</button>
					<button type="button" id="sbmqhBtn" onclick="javascript: if (confirm('啟動簽核流程?\n Submit for approval?')) location.href='/iMes/D089/D089SBMQH?GSDM=${D089H.GSDM}&BDDM=${D089H.BDDM}&BDBH=${D089H.BDBH}'">
						<img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
					</button>
				</c:if>
				<button id="create" type="button" onclick="location.href='/iMes/D089/CREATE'">
					<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" alt="建立報價單" />
				</button>
			</div>

			<table>
				<caption>協力供應商報價單 Supplier Quotation</caption>
				<tbody>
					<tr>
						<th>供應商<br />Supplier
						</th>
						<td>
							<input type="text" name="LIFNR" id="LIFNR" value="${D089H.LIFNR}" class="required" onchange="getSupplier()">
						</td>
						<th>表單代碼<br />Form
						</th>
						<td>
							<input type="text" name="BDDM" id="BDDM" value="${D089H.BDDM}" readonly="readonly" tabindex="99">
						</td>
						<th>表單編號<br />DocNbr
						</th>
						<td>
							<input type="text" name="BDBH" id="BDBH" value="${D089H.BDBH}" readonly="readonly" tabindex="99">
						</td>
						<th>表單日期<br />DocDate
						</th>
						<td>
							<input type="text" name="BDRQ" id="BDRQ" value="${D089H.BDRQ}" class="required datepick" tabindex="99">
						</td>
					</tr>

					<tr>
						<th>公司代碼<br />Company
						</th>
						<td>
							<select name="GSDM" id="GSDM" class="required" tabindex="99">
								<option value="" <c:if test="${D089H.GSDM == ' '}">selected="selected"</c:if>>Select...</option>
								<option value="L100" <c:if test="${D089H.GSDM == 'L100'}">selected="selected"</c:if>>L100 台北立德</option>
								<option value="L111" <c:if test="${D089H.GSDM == 'L111'}">selected="selected"</c:if>>L111 Phils</option>
								<option value="L210" <c:if test="${D089H.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
								<option value="L300" <c:if test="${D089H.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
								<option value="L400" <c:if test="${D089H.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
								<option value="L700" <c:if test="${D089H.GSDM == 'L700'}">selected="selected"</c:if>>L700 BVI</option>
								<option value="L920" <c:if test="${D089H.GSDM == 'L920'}">selected="selected"</c:if>>L920 LEI JP</option>
							</select>
						</td>

						<th>工廠<br />Plant
						</th>
						<td>
							<select name="WERKS" id="WERKS" class="required" tabindex="99">
								<option value="" <c:if test="${D089H.WERKS == ' '}">selected="selected"</c:if>>Select...</option>
								<option value="101A" <c:if test="${D089H.WERKS == '101A'}">selected="selected"</c:if>>101A</option>
								<option value="111A" <c:if test="${D089H.WERKS == '111A'}">selected="selected"</c:if>>111A</option>
								<option value="112A" <c:if test="${D089H.WERKS == '112A'}">selected="selected"</c:if>>112A</option>
								<option value="281A" <c:if test="${D089H.WERKS == '281A'}">selected="selected"</c:if>>281A</option>
								<option value="282A" <c:if test="${D089H.WERKS == '282A'}">selected="selected"</c:if>>282A</option>
								<option value="381A" <c:if test="${D089H.WERKS == '381A'}">selected="selected"</c:if>>381A</option>
								<option value="382A" <c:if test="${D089H.WERKS == '382A'}">selected="selected"</c:if>>382A</option>
								<option value="481A" <c:if test="${D089H.WERKS == '481A'}">selected="selected"</c:if>>481A</option>
								<option value="482A" <c:if test="${D089H.WERKS == '482A'}">selected="selected"</c:if>>482A</option>
								<option value="701A" <c:if test="${D089H.WERKS == '701A'}">selected="selected"</c:if>>701A</option>
								<option value="921A" <c:if test="${D089H.WERKS == '921A'}">selected="selected"</c:if>>921A</option>
							</select>
						</td>

						<th>採購組織<br />POrg
						</th>
						<td>
							<select name="EKORG" id="EKORG" class="required" tabindex="99">
								<option value="" <c:if test="${D089H.EKORG == ' '}">selected="selected"</c:if>>Select...</option>
								<option value="L100" <c:if test="${D089H.EKORG == 'L100'}">selected="selected"</c:if>>L100</option>
								<option value="L111" <c:if test="${D089H.EKORG == 'L111'}">selected="selected"</c:if>>L111</option>
								<option value="L300" <c:if test="${D089H.EKORG == 'L300'}">selected="selected"</c:if>>L300</option>
								<option value="L400" <c:if test="${D089H.EKORG == 'L400'}">selected="selected"</c:if>>L400</option>
								<option value="L700" <c:if test="${D089H.EKORG == 'L700'}">selected="selected"</c:if>>L700</option>
								<option value="L920" <c:if test="${D089H.EKORG == 'L920'}">selected="selected"</c:if>>L920</option>
							</select>
						</td>



						<th>短名<br />Short
						</th>
						<td>
							<input type="text" name="SORTL" id="SORTL" value="${D089H.SORTL}" readonly="readonly" tabindex="99">
						</td>
					</tr>

					<tr>
						<th>稅碼<br />Tax Code
						</th>
						<td>
							<input type="text" name="MWSKZ" id="MWSKZ" value="${D089H.MWSKZ}" readonly="readonly" tabindex="99" class="required">
						</td>

						<th>幣別<br />Currency
						</th>
						<td>
							<input type="text" name="WAERS" id="WAERS" value="${D089H.WAERS}" readonly="readonly" tabindex="99">
						</td>

						<th>公司名稱<br />Name
						</th>
						<td colspan="3">
							<input type="text" name="NAME1" id="NAME1" value="${D089H.NAME1}" readonly="readonly" style="width: 240px" tabindex="99">
						</td>

					</tr>

					<tr>
						<th>申請者<br />Requester
						</th>
						<td id="SQYH">${D089H.SQYH}</td>

						<th>表單狀態<br />Status
						</th>
						<td id="BDZT">${D089H.BDZTT}</td>

						<th>表單結果<br />Result
						</th>
						<td id="BDJG">
							<c:if test="${D089H.BDJG}=='Y'">OK</c:if>
							<c:if test="${D089H.BDJG}=='N'">NG</c:if>
						</td>

						<th>表單附檔<br />Attachment
						</th>
						<td id="BDFD">
							<c:if test="${D089H.BDFD.length() > 0}">
								<a href="/iMes/FileDownloader?filePath=${D089H.BDFD}">下載/Download</a>
							</c:if>
						</td>

					</tr>

					<tr>
						<th>原因代碼<br />Reason
						</th>
						<td>
							<select name="BDYY" id="BDYY" class="required">
								<option value="" <c:if test="${D089H.BDYY == ''}">selected="selected"</c:if>>Select...</option>
								<option value="A. New Price" <c:if test="${D089H.BDYY == 'A. New Price'}">selected="selected"</c:if>>A. New Price</option>
								<option value="B. Cost Down" <c:if test="${D089H.BDYY == 'B. Cost Down'}">selected="selected"</c:if>>B. Cost Down</option>
								<option value="C. Cost Up" <c:if test="${D089H.BDYY == 'C. Cost Up'}">selected="selected"</c:if>>C. Cost Up</option>
								<option value="D. Extend Agrm" <c:if test="${D089H.BDYY == 'D. Extend Agrm'}">selected="selected"</c:if>>D. Extend Agrm</option>
								<option value="E. Close Agrm" <c:if test="${D089H.BDYY == 'E. Close Agrm'}">selected="selected"</c:if>>E. Close Agrm</option>
							</select>
						</td>

						<th>建立時間<br />Created
						</th>
						<td id="JLSJ">
							<fmt:formatDate value="${D089H.JLSJ}" type="both" pattern="yyyyMMdd HH:mm" />
						</td>

						<th>核准人<br />Approved by
						</th>
						<td id="QHYH">${D089H.QHYH}</td>

						<th>核准時間<br />Approved at
						</th>
						<td id="QHSJ">
							<fmt:formatDate value="${D089H.QHSJ}" type="both" pattern="yyyyMMdd HH:mm" />
						</td>
					</tr>

					<tr>
						<th>最低差異%<br />MinDiff%
						</th>
						<td class="dec6" style="text-align: left;">
							<fmt:formatNumber value="${D089H.MINPT}" pattern="#,###.######" />
							%
						</td>
						<th>最高差異%<br />MaxDiff%
						</th>
						<td class="dec6" style="text-align: left;">
							<fmt:formatNumber value="${D089H.MAXPT}" pattern="#,###.######" />
							%
						</td>
						<th>簽核性質<br />ChangeType
						</th>
						<td>${D089H.MAXPTT}</td>
						<th>變價金額</th>
						<td class="dec6" style="text-align: left;">
							<fmt:formatNumber value="${D089H.BDAMT}" pattern="#,###.######" />
						</td>
					</tr>

					<tr>
						<th>金屬幣別<br />MetalCurr
						</th>
						<td>
							<select name="MTCUR" id="MTCUR">
								<option value="USD" <c:if test="${D089H.MTCUR == 'USD'}">selected="selected"</c:if>>USD</option>
								<option value="RMB" <c:if test="${D089H.MTCUR == 'RMB'}">selected="selected"</c:if>>RMB</option>
							</select>
						</td>
						<th>錫價<br />Tin
						</th>
						<td>
							<input type="text" name="SNAMT" id="SNAMT" value="<fmt:formatNumber value="${D089H.SNAMT}" pattern="#" />">
						</td>
						<th>銀價<br />Silver
						</th>
						<td>
							<input type="text" name="AGAMT" id="AGAMT" value="<fmt:formatNumber value="${D089H.AGAMT}" pattern="#" />">
						</td>
						<th>銅價<br />Copper
						</th>
						<td>
							<input type="text" name="CUAMT" id="CUAMT" value="<fmt:formatNumber value="${D089H.CUAMT}" pattern="#" />">
						</td>
					</tr>

					<tr>
						<th>表單內容<br />Remarks
						</th>
						<td colspan="7">
							<textarea cols="120" rows="5" name="BDNR" id="BDNR">${D089H.BDNR}</textarea>
						</td>
					</tr>


				</tbody>


			</table>
		</form>

		<c:if test="${(D089H.SQYH == sessionScope.uid) && (D089H.BDBH != '') && (D089H.QHKS != 'X')}">
			<form action="/iMes/FileUploader" method="post" enctype="multipart/form-data">
				<input type="hidden" name="action" value="Document" />
				<input type="hidden" name="GSDM" value="${D089H.GSDM}">
				<input type="hidden" name="BDDM" value="${D089H.BDDM}">
				<input type="hidden" name="BDBH" value="${D089H.BDBH}">
				<br />
				<table>
					<caption>文件上傳 / File Upload</caption>
					<tr>
						<td>
							<input type="file" name="file" />
						</td>
						<td>
							<button type="button" onclick="this.disabled=true;this.form.submit();">
								<img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
							</button>
						</td>
					</tr>
				</table>
			</form>
		</c:if>

		<jsp:include page="/WEB-INF/pages/form/_route.jsp" />

	</div>

	<div id="tab-2">
		<form id="fDeleteLine" action="/iMes/D089/D089LDELETEMARK" method="post">
			<input type="hidden" name="DLTTX" id="DLTTX">
			<input type="hidden" name="GSDM" value="${D089H.GSDM}">
			<input type="hidden" name="BDDM" value="${D089H.BDDM}">
			<input type="hidden" name="BDBH" value="${D089H.BDBH}">
			<input type="hidden" name="RID" id="RID">
		</form>

		<form action="/iMes/D089/D089LEDIT" method="post" id="matEditForm" name="matEditForm">
			<div id="icon">
				<c:if test="${(D089H.SQYH == sessionScope.uid) && (D089H.BDBH != '') && (D089H.QHKS != 'X')}">
					<button type="button" id="matEditBtn">
						<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存 Save" />
					</button>
					<button type="button" id="matDltBtn" onclick="javascript: if (confirm('刪除全部?\n Delete All?')) location.href='/iMes/D089/D089LDLTALL?GSDM=${D089H.GSDM}&BDDM=${D089H.BDDM}&BDBH=${D089H.BDBH}'">
						<img src="/iMes/stylesheet/icons/S_B_DELE.GIF" alt="全部刪除 Delete All" />
					</button>
				</c:if>
				<button type="reset">
					<img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原 Redo" />
				</button>
			</div>

			<input type="hidden" name="GSDM" value="${D089H.GSDM}">
			<input type="hidden" name="BDDM" value="${D089H.BDDM}">
			<input type="hidden" name="BDBH" value="${D089H.BDBH}">

			<table>
				<caption>${D089H.LIFNR} ${D089H.SORTL} - ${D089H.EKORG} / ${D089H.WERKS} / ${D089H.MWSKZ}</caption>
				<thead>
					<tr>
						<th>序<br />Sq
						</th>
						<th></th>
						<th></th>
						<th>區域差異%<br />Reg Dif%
						</th>
						<th>料號<br />Material
						</th>
						<th>規格<br />Specification
						</th>
						<th>UM<br />UM
						</th>
						<th>幣別<br />Cur
						</th>
						<th>報價單價<br />Price
						</th>
						<th>報價(RMB)<br />New Prc
						</th>
						<th>舊價(RMB)<br />Old Prc
						</th>
						<th>舊價差異<br />Prc Dif
						</th>
						<th>舊價差異%<br />Prc Dif%
						</th>
						<th>區域(RMB)<br />Reg Prc
						</th>
						<th>區域差異<br />Reg Dif%
						</th>
						<th>LT<br />LT
						</th>
						<th>MOQ<br />MOQ
						</th>
						<th>有效日期<br />Valid
						</th>
						<th>失效日期<br />Until
						</th>
						<th>變價生效<br />ChgMtd
						</th>
						<th>日期規則<br />Rules
						</th>
						<th>追溯日期<br />Date
						</th>
						<th>資訊記錄<br />Infnr
						</th>
						<th>組<br />Grp
						</th>
						<th>物料組<br />MatGrp
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${D089L}" var="e">
						<tr id="D${e.ROWID}">
							<td>${e.ROWID}</td>
							<td>
								<c:if test="${(D089H.SQYH == sessionScope.uid) && (D089H.BDBH != '') && (D089H.QHKS != 'X')}">
									<button type="button" onclick="javascript: if (confirm('刪除確認\n Delete Confirmation.')) location.href='/iMes/D089/D089LDLT?GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}&SQNR=${e.SQNR}'">
										<img src="/iMes/stylesheet/icons/S_B_DELE.GIF" alt="刪除" />
									</button>
								</c:if>
								<c:if test="${(e.DLTFG=='N') && (D089H.QHKS == 'X')}">
									<button type="button" onclick="deleteLine('${e.RID}')">
										<img src="/iMes/stylesheet/icons/S_B_DELE.GIF" alt="刪除" />
									</button>
								</c:if>
								<button type="button" onclick="getPriceHistory('D${e.ROWID}', '${e.MATNR}','${e.RMBPR}')">
									<img src="/iMes/stylesheet/icons/S_PRANAL.GIF" alt="歷史記錄" />
								</button>
								<button type="button" onclick="getOpenPO('D${e.ROWID}','${e.MATNR}','${D089H.LIFNR}','${e.RMBPR}')">
									<img src="/iMes/stylesheet/icons/S_B_ORDA.GIF" alt="Open PO" />
								</button>
							</td>
							<td>
								<c:if test="${(D089H.SQYH == sessionScope.uid) && (D089H.BDBH != '') && (D089H.QHKS != 'X')}">
									<input type="checkbox" name="SQNRCbx" id="SQNRCbx-${e.SQNR}" value="${e.SQNR}" onclick="toggleInputField(this)">
								</c:if>
								<c:if test="${e.DLTFG=='Y' }">已删除<br />Deleted</c:if>
							</td>
							<td class="dec6" style="color: ${e.COLOR }; background: ${e.BGCOLOR }">
								<b><fmt:formatNumber value="${e.MINPT}" pattern="#,###.######" />%</b>
							</td>
							<td>${e.MATNR}</td>
							<td>${e.MAKTX}</td>
							<td>${e.MEINS}</td>
							<td>${D089H.WAERS}</td>
							<td class="dec6">
								<fmt:formatNumber value="${e.UNTPR}" pattern="#,###.######" />
							</td>
							<td class="dec6" bgcolor="#F7F8E0">
								<fmt:formatNumber value="${e.RMBPR}" pattern="#,###.######" />
							</td>
							<td class="dec6" bgcolor="#EFFBEF">
								<fmt:formatNumber value="${e.OLDPR}" pattern="#,###.######" />
							</td>
							<td class="dec6">
								<fmt:formatNumber value="${e.OLDDF}" pattern="#,###.######" />
							</td>
							<td class="dec6">
								<fmt:formatNumber value="${e.OLDPT}" pattern="#,###.######" />
								%
							</td>
							<td class="dec6" bgcolor="#E0E0F8">
								<fmt:formatNumber value="${e.MINPR}" pattern="#,###.######" />
							</td>
							<td class="dec6">
								<fmt:formatNumber value="${e.MINDF}" pattern="#,###.######" />
							</td>
							<td class="dec6">
								<fmt:formatNumber value="${e.PLIFZ}" pattern="#,###.######" />
							</td>
							<td class="dec6">
								<fmt:formatNumber value="${e.BSTRF}" pattern="#,###.######" />
							</td>
							<td>
								<input type="text" name="DATAB_${e.SQNR}" id="DATAB_${e.SQNR}" value="${e.DATAB}" class="required datepick" disabled="disabled" style="width: 65px">
							</td>
							<td>
								<input type="text" name="PRDAT_${e.SQNR}" id="PRDAT_${e.SQNR}" value="${e.PRDAT}" class="required datepick" disabled="disabled" style="width: 65px">
							</td>
							<td>
								<select name="CHGPO_${e.SQNR}" id="CHGPO_${e.SQNR}" class="required" style="width: 60px" disabled="disabled">
									<option value="">Select...</option>
									<option value="1" <c:if test="${e.CHGPO == '1'}">selected="selected"</c:if>>最優</option>
									<option value="2" <c:if test="${e.CHGPO == '2'}">selected="selected"</c:if>>舊PO</option>
									<option value="3" <c:if test="${e.CHGPO == '3'}">selected="selected"</c:if>>新PO</option>
								</select>
							</td>
							<td>
								<select name="DATYP_${e.SQNR}" id="DATYP_${e.SQNR}" class="required" style="width: 60px" disabled="disabled">
									<option value="">Select...</option>
									<option value="C" <c:if test="${e.DATYP == 'C'}">selected="selected"</c:if>>建立</option>
									<option value="D" <c:if test="${e.DATYP == 'D'}">selected="selected"</c:if>>交貨</option>
								</select>
							</td>
							<td>
								<input type="text" name="PODAT_${e.SQNR}" id="PODAT_${e.SQNR}" value="${e.PODAT}" class="datepick" disabled="disabled" style="width: 70px">
							</td>
							<td>${e.INFNR}</td>
							<td>${e.EKGRP}</td>
							<td>${e.MATKL}</td>
						</tr>
						<c:if test="${e.DLTFG=='Y' }">
							<tr>
								<td colspan='25'>
									<table>
										<tr>
											<th>删除者 / Deleted By</th>
											<td>${e.DLTID}</td>
											<th>日期 / Date</th>
											<td>${e.DLTDT}</td>
											<th>Remark</th>
											<td colspan="3">${e.DLTTX}</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>

	<div id="tab-5">
		<form action="/iMes/D089/MATUPLOAD" method="post" id="matUploadForm" name="matUploadForm">
			<div id="icon">
				<c:if test="${(D089H.SQYH == sessionScope.uid) && (D089H.BDBH != '') && (D089H.QHKS != 'X')}">
					<button type="button" id="matUploadBtn">
						<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存 Save" />
					</button>
				</c:if>
				<button type="reset">
					<img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原 Redo" />
				</button>
			</div>

			<input type="hidden" name="GSDM" value="${D089H.GSDM}">
			<input type="hidden" name="BDDM" value="${D089H.BDDM}">
			<input type="hidden" name="BDBH" value="${D089H.BDBH}">

			<table>
				<caption>上傳物料單價 [Upload Material Price]</caption>
				<tbody>
					<tr>
						<th>供應商<br />[Supplier]
						</th>
						<td>${D089H.LIFNR}- ${D089H.SORTL}</td>
						<th>工廠/採購組織<br />[Plant/PurOrg]
						</th>
						<td>
							<b>${D089H.WERKS} / ${D089H.EKORG} / ${D089H.WAERS} / ${D089H.MWSKZ}</b>
						</td>
					</tr>
					<tr>
						<th>有效日期<br />[From Date]
						</th>
						<td>
							<input type="text" name="DATAB" id="DATAB" value="${D089H.BDRQ}" class="required datepick">
						</td>
						<th>到期日期<br />[To Date]
						</th>
						<td>
							<input type="text" name="PRDAT" id="PRDAT" value="${D089H.getPRDAT()}" readonly="readonly">
						</td>
					</tr>
					<tr>
						<th>變價策略<br />[Chg Strategy]
						</th>
						<td>
							<select name="CHGPO" id="CHGPO" class="required">
								<option value="1" selected="selected">最優策略 [The Best]</option>
								<option value="2">未結採購單 [Open PO]</option>
								<option value="3">新採購單 [New PO]</option>
							</select>
						</td>
						<th>追溯日期<br />[Claim Date]
						</th>
						<td>
							<select name="DATYP" id="DATYP" class="required">
								<option value="C" selected="selected">PO建立日期 [PO Create Date]</option>
							</select>
							<input type="text" name="PODAT" id="PODAT" value="" class="datepick">
						</td>
					</tr>
					<tr>
						<th>粘貼資料<br />[Paste Data]
						</th>
						<td colspan="3">
							<textarea cols="60" rows="20" name="PASTE" id="PASTE" class="required"></textarea>
						</td>
					</tr>
				</tbody>
			</table>

		</form>
	</div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />