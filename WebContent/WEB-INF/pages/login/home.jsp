<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">Menu</a></li>
	</ul>

	<div id="tab-1">

		<table>
			<caption>iMes系統模塊</caption>
			<thead>
				<tr>
					<th>簽核流程</th>
					<th>SAP查詢</th>
					<th>管理報表</th>
					<th>機器人/網站</th>
					<th>相關系統網站</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/qh?action=Find&STRQHZT=2&STRYSYH=${sessionScope.uid}">未簽核單據查詢</a></li>
							<li class="menuitem"><a href="/iMes/qh?action=Find&STRQHZT=2&STRSQYH=${sessionScope.uid}">申請人單據查詢</a></li>
							<li class="menuitem"><a href="/iMes/DELEGATE">設置簽核代理人</a></li>
							<c:if test="${sessionScope.uid != 'DT'}">
								<li class="menuitem"><a href="/iMes/D031?action=FindOrder">D031 - 訂單異動通知單</a></li>
							</c:if>
							<li class="menuitem"><a href="/iMes/D031?action=List">D031 - 訂單異動查詢</a></li>
							<li class="menuitem"><a href="/iMes/D272">D272 - 生產溢領單</a></li>
							<li class="menuitem"><a href="/iMes/D272L">D272L- 溢領明細單</a></li>
							<li class="menuitem"><a href="/iMes/D400">D400- 簽呈管理</a></li>
							<li class="menuitem"><a href="/iMes/D089">D089- 供應商報價單簽核</a></li>
							<li class="menuitem"><a href="/iMes/D089/LISTMATNR">D089L- 物料報價明細</a></li>
							<li class="menuitem"><a href="/iMes/D089/SapUpdateLog">D089- SAP更新報價錯誤信息</a></li>
							<li class="menuitem"><a href="/iMes/Q001">Q001- 拜訪通知單</a></li>
							<li class="menuitem"><a href="/iMes/EQP001">EQP001- 工具設備維護</a></li>
							<li class="menuitem"><a href="/iMes/EQP002">EQP002- 配件耗材維護</a></li>
							<li class="menuitem"><a href="/iMes/D888">D888- 出廠單</a></li>
							<li class="menuitem"><a href="/iMes/D189">D189- 重工申請單</a></li>
							<li class="menuitem"><a href="/iMes/D300">D300- 派工令異動申請單</a></li>
							<li class="menuitem"><a href="/iMes/D301">D301- 外修申請單</a></li>
							<li class="menuitem"><a href="/iMes/D600">D600- 試產單</a></li>
						</ul>
					</td>
					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/MD04">MD04 - 庫存/需求清單</a></li>
							<li class="menuitem"><a href="/iMes/MD10">MD10 - MRP Report By Product Bom</a></li>
							<li class="menuitem"><a href="/iMes/MMBE">MMBE - 批次庫存清單</a></li>
							<li class="menuitem"><a href="/iMes/MB51">MB51 - 物料文件清單</a></li>
							<li class="menuitem"><a href="/iMes/CS11">CS11 - 機種單階BOM展開</a></li>
							<li class="menuitem"><a href="/iMes/PP01">PP01 - 工作中心 Schedule</a></li>
							<li class="menuitem"><a href="/iMes/PP02">PP02 - MO Schedule</a></li>
							<li class="menuitem"><a href="/iMes/PP03">PP03 - 多工單元件查詢</a></li>
							<li class="menuitem"><a href="/iMes/PP05">PP05 - 多工單作業查詢</a></li>
							<li class="menuitem"><a href="/iMes/PP06">PP06 - 工單嘜頭查詢</a></li>
							<li class="menuitem"><a href="/iMes/COOIS">COOIS- 工單元件查詢</a></li>
							<li class="menuitem"><a href="/iMes/COOISN">COOISN- 工單元件查詢批次</a></li>
							<li class="menuitem"><a href="/iMes/CO03">CO03 - 工單查詢</a></li>
							<li class="menuitem"><a href="/iMes/CO03A">CO03A- 工單物料異動清單</a></li>
							<li class="menuitem"><a href="/iMes/MM61">MM61 - 區域最低採購價</a></li>
							<li class="menuitem"><a href="/iMes/MM63">MM63 - 工廠庫齡報表</a></li>
							<li class="menuitem"><a href="/iMes/MM67">MM67 - 採購進料記錄</a></li>
							<li class="menuitem"><a href="/iMes/MM66">MM66 - 原材料需求排程</a></li>
							<li class="menuitem"><a href="/iMes/ME03">ME03 - 來源信息認可清單</a></li>
							<li class="menuitem"><a href="/iMes/SD01">SD01 - Open SO對應庫存</a></li>
						</ul>
					</td>

					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/AP_RPT?action=AP61">AP未結清清單</a></li>
							<li class="menuitem"><a href="/iMes/AP_RPT?action=AP62">IEB152 - 進口清單</a></li>
							<li class="menuitem"><a href="/iMes/AP63">AP63 - 貨到付款計劃</a></li>
							<li class="menuitem"><a href="/iMes/SD61">SD61 - 訂單后呆料計算</a></li>
						</ul>
					</td>
					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/SGME01">SGME01 - DT零件認可維護</a></li>
							<li class="menuitem"><a href="http://ihouse.l-e-i.com:8080/plmq/" target="_blank">LEI PLMQ 查詢網站</a></li>
							<li class="menuitem"><a href="http://ihouse.l-e-i.com:8080/pur/" target="_blank">LEI PUR 採購查詢網站</a></li>
							<li class="menuitem"><a href="http://172.31.4.177:8080/sap/" target="_blank">LEI SAP 成本查詢網站</a></li>
						</ul>
					</td>
					<td width="200px" valign="top">						
					    <ul>
							<li class='menuitem'><a href='http://sapweb.l-e-i.com:3158/' target='_blank'>Dashboard - SapWeb</a></li>
							<li class='menuitem'><a href='http://172.31.4.220:3158/' target='_blank'>SapWeb</a></li>
							<li class='menuitem'><a href='http://ips.l-e-i.com:3058/' target='_blank'>IPS</a></li>
							<li class='menuitem'><a href='http://172.91.1.88:3058/' target='_blank'>IPQC</a></li>
							<li class='menuitem'><a href='http://172.91.132.3:3358/' target='_blank'>Barcode</a></li>
							<li class='menuitem'><a href='http://172.91.132.3:9292/' target='_blank'>Warehouse</a></li>
							<li class='menuitem'><a href='http://172.91.132.7:3018/' target='_blank'>iStock</a></li>
							<li class='menuitem'><a href='http://172.91.132.20:3058/' target='_blank'>易關通中間系統</a></li>
							<li class='menuitem'><a href='http://172.91.132.20:9292/' target='_blank'>易關通中間系統(舊)</a></li>
							<li class='menuitem'><a href='http://172.91.132.116:3058/' target='_blank'>SOP</a></li>
							<li class='menuitem'><a href='http://172.31.1.66:3058/' target='_blank'>QH</a></li>
							<li class='menuitem'><a href='http://172.31.1.66:3158/' target='_blank'>YGTWEB</a></li>
							<li class='menuitem'><a href='http://eform.l-e-i.com:3058/' target='_blank'>QH</a></li>
							<li class='menuitem'><a href='http://172.91.132.23:3000/pcblab' target='_blank'>240W</a></li>
							<li class='menuitem'><a href='http://172.91.1.158/ebuyer/' target='_blank'>TX eBuyer</a></li>
							<li class='menuitem'><a href='http://172.91.1.158/gen/' target='_blank'>TX 服务平台</a></li>
							<li class='menuitem'><a href='http://tpplm.l-e-i.com/' target='_blank'>TPPLM</a></li>
							<li class='menuitem'><a href='http://sdworkflow.l-e-i.com:8080/sd/login.jsp' target='_blank'>簽核流程Workflow</a></li>
							<li class='menuitem'><a href='http://172.91.132.3:3001/' target='_blank'>盘点(扫条码)</a></li>
						</ul>
					</td>
				</tr>
				<tr>
					<th>免3C認證管理系統</th>
					<th>Barcode掃描</th>
					<th>生产管理</th>
					<th>TX-管理部</th>
					<th></th>
				</tr>
				<tr>
					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/ZIEB901">ZIEB901 - 進口清單</a></li>
							<li class="menuitem"><a href="/iMes/ZIEB902">ZIEB902 - 物料文件清單</a></li>
							<li class="menuitem"><a href="/iMes/ZIEB903">ZIEB903 - 成品文件清單</a></li>
							<li class="menuitem"><a href="/iMes/ZIEB904">ZIEB903 - 批次庫存清單</a></li>
						</ul>
					</td>

					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/BCD005">BCD005 - S/N查詢</a></li>
							<li class="menuitem"><a href="/iMes/BCD003">BCD003 - 父件與子件連接掃描</a></li>
							<li class="menuitem"><a href="/iMes/BCD004">BCD004 - 燈珠與燈板連接掃描</a></li>
							<li class="menuitem"><a href="/iMes/BCD002">BCD002 - 掃描連接查詢</a></li>
						</ul>
					</td>

					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="/iMes/PRD001">PRD001 - 產線計畫维护</a></li>
						</ul>
					</td>

					<td width="200px" valign="top">
						<ul>
							<li class="menuitem"><a href="http://172.91.1.158/greenmap" target="_blank">TX-管理部 green map</a></li>
						</ul>
					</td>

					<td width="200px" valign="top">
					</td>

				</tr>
			</tbody>
		</table>

	</div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />