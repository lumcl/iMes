<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">工具物件主檔維護</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/EQP001/save" method="post" id="saveForm">

			<input type="hidden" id="id" name="id" value="${eqpMas.id}">
			<div id="nav">
				<ul>
					<li>
						<button type="button" id="saveBtn">
							<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" />保存資料
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/EQP001/create'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建工具
						</button>
					</li>
					<li>
						<button type="button" onclick="history.back()">
							<img src="/iMes/stylesheet/icons/S_F_BACK.GIF" />返回前頁
						</button>
					</li>
				</ul>
			</div>

			<div id="bdf">

				<div id="basicSection">
					<h2>
						<span class="section">基礎資料</span>
					</h2>
					<div>
						<label for="cmpnbr">公司</label>
						<input type="radio" id="cmpnbr" name="cmpnbr" value="L210" ${eqpMas.isCmpnbr("L210")}>
						<span class="label-for-radio">L210 東莞領航</span>
						<input type="radio" id="cmpnbr" name="cmpnbr" value="L300" ${eqpMas.isCmpnbr("L300")}>
						<span class="label-for-radio">L300 東莞立德</span>
						<input type="radio" id="cmpnbr" name="cmpnbr" value="L400" ${eqpMas.isCmpnbr("L400")}>
						<span class="label-for-radio">L400 江蘇領先</span>
					</div>
					<div>
						<label for="facnbr">工廠</label>
						<input type="radio" id="facnbr" name="facnbr" value="281A" ${eqpMas.isFacnbr("281A")}>
						<span class="label-for-radio">281A</span>
						<input type="radio" id="facnbr" name="facnbr" value="381A" ${eqpMas.isFacnbr("381A")}>
						<span class="label-for-radio">381A</span>
						<input type="radio" id="facnbr" name="facnbr" value="382A" ${eqpMas.isFacnbr("382A")}>
						<span class="label-for-radio">382A</span>
						<input type="radio" id="facnbr" name="facnbr" value="481A" ${eqpMas.isFacnbr("481A")}>
						<span class="label-for-radio">481A</span>
						<input type="radio" id="facnbr" name="facnbr" value="482A" ${eqpMas.isFacnbr("482A")}>
						<span class="label-for-radio">482A</span>
					</div>
					<div>
						<label for="eqpnbr">物件號碼</label>
						<input type="text" id="eqpnbr" name="eqpnbr" value="${eqpMas.eqpnbr}" class="required" <c:if test="${eqpMas.id != 0 }"> readonly="readonly"</c:if> />
					</div>
					<div>
						<label for="eqptyp">物件大類</label>
						<select id="eqptyp" name="eqptyp" style="height: 25px;" class="required">
							<option value="">請選擇</option>
							<option value="A" ${eqpMas.isEqptyp("A")}>A ATE</option>
							<option value="H" ${eqpMas.isEqptyp("H")}>H 耐壓治具</option>
							<option value="I" ${eqpMas.isEqptyp("I")}>I ICT</option>
							<option value="T" ${eqpMas.isEqptyp("T")}>T 特性治具</option>
							<option value="W" ${eqpMas.isEqptyp("W")}>W 外觀治具</option>
							<option value="Z" ${eqpMas.isEqptyp("Z")}>Z 過錫爐治具</option>
						</select>
						<label for="eqpgrp">物件小類</label>
						<input type="text" id="eqpgrp" name="eqpgrp" value="${eqpMas.eqpgrp}" />
					</div>
					<div>
						<label for="eqpdes">物件說明</label>
						<input type="text" id="eqpdes" name="eqpdes" value="${eqpMas.eqpdes}" style="width: 613px;" class="required" />
					</div>
					<div>
						<label for="eqpmdl">物件型號</label>
						<input type="text" id="eqpmdl" name="eqpmdl" value="${eqpMas.eqpmdl}" style="width: 613px;" />
					</div>
					<div>
						<label for="eqpspc">物件規格</label>
						<input type="text" id="eqpspc" name="eqpspc" value="${eqpMas.eqpspc}" style="width: 613px;" />
					</div>
					<div>
						<label for="eqpsts">物件狀態</label>
						<input type="radio" id="eqpsts" name="eqpsts" value="OK" ${eqpMas.isEqpsts("OK")}>
						<span class="label-for-radio">OK 良好</span>
						<input type="radio" id="eqpsts" name="eqpsts" value="NG" ${eqpMas.isEqpsts("NG")}>
						<span class="label-for-radio">NG 不良</span>
						<input type="radio" id="eqpsts" name="eqpsts" value="MT" ${eqpMas.isEqpsts("MT")}>
						<span class="label-for-radio">MT 保養</span>
						<input type="radio" id="eqpsts" name="eqpsts" value="RP" ${eqpMas.isEqpsts("RP")}>
						<span class="label-for-radio">RP 送修</span>
						<input type="radio" id="eqpsts" name="eqpsts" value="SC" ${eqpMas.isEqpsts("SC")}>
						<span class="label-for-radio">SC 報廢</span>
						<input type="radio" id="eqpsts" name="eqpsts" value="LS" ${eqpMas.isEqpsts("LS")}>
						<span class="label-for-radio">LS 遺失</span>
						<input type="radio" id="eqpsts" name="eqpsts" value="LN" ${eqpMas.isEqpsts("LN")}>
						<span class="label-for-radio">LN 借出</span>
					</div>
					<div>
						<label for="astnbr">財產編號</label>
						<input type="text" id="astnbr" name="astnbr" value="${eqpMas.astnbr}" />
						<label for="fixtyp">資產類別</label>
						<input type="radio" id="fixtyp" name="fixtyp" value="FA" ${eqpMas.isFixtyp("FA")}>
						<span class="label-for-radio">固定資產</span>
						<input type="radio" id="fixtyp" name="fixtyp" value="CA" ${eqpMas.isFixtyp("CA")}>
						<span class="label-for-radio">列管資產</span>
					</div>
					<div>
						<label for="duedat">預計資產到期日</label>
						<input type="text" id="duedat" name="duedat" value="${eqpMas.duedat}" class="datepick" />
					</div>
				</div>

				<div id="storageSection">
					<h2>
						<span class="section">保管信息</span>
					</h2>
					<div>
						<label for="locate">放置位置</label>
						<input type="text" id="locate" name="locate" value="${eqpMas.locate}" />
					</div>
					<div>
						<label for="rspdep">保管部門</label>
						<input type="text" id="rspdep" name="rspdep" value="${eqpMas.rspdep}" class="depts" />
						<label for="rspuid">保管人</label>
						<input type="text" id="rspuid" name="rspuid" value="${eqpMas.rspuid}" class="users" />
					</div>
					<div>
						<label for="eqpqty">數量</label>
						<input type="text" id="eqpqty" name="eqpqty" value="${eqpMas.eqpqty}" class="required" />
						<label for="eqpuom">單位</label>
						<input type="radio" id="eqpuom" name="eqpuom" value="EA" class="required" ${eqpMas.isEqpiom("EA")} />
						<span class="label-for-radio">EA</span>
						<input type="radio" id="eqpuom" name="eqpuom" value="KG" class="required" ${eqpMas.isEqpiom("KG")} />
						<span class="label-for-radio">KG</span>
						<input type="radio" id="eqpuom" name="eqpuom" value="M" class="required" ${eqpMas.isEqpiom("M")} />
						<span class="label-for-radio">M</span>
						<input type="radio" id="eqpuom" name="eqpuom" value="L" class="required" ${eqpMas.isEqpiom("L")} />
						<span class="label-for-radio">L</span>
					</div>
					<div>
						<label for="locker">治具放置櫃</label>
						<input type="text" id="locker" name="locker" value="${eqpMas.locker}" />
					</div>
				</div>

				<div id="productSection">
					<h2>
						<span class="section">機種對應表</span>
					</h2>
					<div>
						<label for="prdnbr">使用機種</label>
						<input type="text" id="prdnbr" name="prdnbr" value="${eqpMas.prdnbr}" />
					</div>
					<div>
						<label for="prddes">使用機種說明</label>
						<input type="text" id="prddes" name="prddes" value="${eqpMas.prddes}" style="width: 613px;" />
					</div>
					<div>
						<label for="pcbnbr">PCB板號</label>
						<input type="text" id="pcbnbr" name="pcbnbr" value="${eqpMas.pcbnbr}" />
					</div>
				</div>

				<div id="compSection">
					<h2>
						<span class="section">組件信息</span>
					</h2>
					<div>
						<label for="compnr">組件</label>
						<input type="text" id="compnr" name="compnr" value="${eqpMas.compnr}" />
						<label for="compmd">組件型號</label>
						<input type="text" id="compmd" name="compmd" value="${eqpMas.compmd}" />
					</div>
					<div>
						<label for="serial">機身序列號</label>
						<input type="text" id="serial" name="serial" value="${eqpMas.serial}" />
					</div>
					<div>
						<label for="prdlin">線別</label>
						<input type="text" id="prdlin" name="prdlin" value="${eqpMas.prdlin}" />
					</div>
				</div>

				<div id="mntSection">
					<h2>
						<span class="section">保養信息</span>
					</h2>
					<div>
						<label for="strmnt">開始保養日期</label>
						<input type="text" id="strmnt" name="strmnt" value="${eqpMas.strmnt}" class="datepick" />
						<label for="mntcyc">保養週期 (天)</label>
						<input type="text" id="mntcyc" name="mntcyc" value="${eqpMas.mntcyc}" />
					</div>
					<div>
						<label for="mntdat">保養日期</label>
						<input type="text" id="mntdat" name="mntdat" value="${eqpMas.mntdat}" class="datepick" />
					</div>
					<div>
						<label for="remark">備註</label>
						<textarea rows="3" cols="75" id="remark" name="remark" style="vertical-align: middle">${q001H.vdradd}</textarea>
					</div>
				</div>

				<div id="purchaseSection">
					<h2>
						<span class="section">採購信息</span>
					</h2>
					<div>
						<label for="mfgnam">生產廠家</label>
						<input type="text" id="mfgnam" name="mfgnam" value="${eqpMas.mfgnam}" />
					</div>
					<div>
						<label for="brand">品牌</label>
						<input type="text" id="brand" name="brand" value="${eqpMas.brand}" />
					</div>
					<div>
						<label for="buyer">購買者</label>
						<input type="text" id="buyer" name="buyer" value="${eqpMas.buyer}" class="users" />
						<label for="purdat">購買日期</label>
						<input type="text" id="purdat" name="purdat" value="${eqpMas.purdat}" class="datepick" />
					</div>
					<div>
						<label for="rmbamt">購買價格 (RMB)</label>
						<input type="text" id="rmbamt" name="rmbamt" value="${eqpMas.rmbamt}" />
					</div>
					<div>
						<label for="purdoc">購買參考文件</label>
						<input type="text" id="purdoc" name="purdoc" value="${eqpMas.purdoc}" />
						<label for="appdoc">簽呈編號</label>
						<input type="text" id="appdoc" name="appdoc" value="${eqpMas.appdoc}" />
					</div>
					<div>
						<label for="iqcuid">驗收人員</label>
						<input type="text" id="iqcuid" name="iqcuid" value="${eqpMas.iqcuid}" class="users" />
						<label for="iqcdat">驗收日期</label>
						<input type="text" id="iqcdat" name="iqcdat" value="${eqpMas.iqcdat}" class="datepick" />
					</div>
					<div>
						<label for="wtydue">質保到期日</label>
						<input type="text" id="wtydue" name="wtydue" value="${eqpMas.wtydue}" class="datepick" />
					</div>
					<div>
						<label for="vendor">供應商編號</label>
						<input type="text" id="vendor" name="vendor" value="${eqpMas.vendor}" />
					</div>
					<div>
						<label for="vdrnam">供應商公司名稱</label>
						<input type="text" id="vdrnam" name="vdrnam" value="${eqpMas.vdrnam}" class="long" />
					</div>
					<div>
						<label for="vdrcnt">供應商聯繫人</label>
						<input type="text" id="vdrcnt" name="vdrcnt" value="${eqpMas.vdrcnt}" />
						<label for="vdrphn">聯繫電話</label>
						<input type="text" id="vdrphn" name="vdrphn" value="${eqpMas.vdrphn}" />
					</div>
					<div>
						<label for="vdradd">供應商聯繫地址</label>
						<textarea rows="3" cols="75" id="vdradd" name="vdradd" style="vertical-align: middle">${q001H.vdradd}</textarea>
					</div>
				</div>
				<div></div>
				<div>
					<label for="crtuid">建立用戶</label>
					<input type="text" id="crtuid" name="crtuid" value="${eqpMas.crtuid}" readonly="readonly" />
					<label for="crtdat">建立時間</label>
					<input type="text" id="crtdat" name="crtdat" value="${eqpMas.crtdat}" readonly="readonly" />
				</div>
				<div>
					<label for="chguid">更改用戶</label>
					<input type="text" id="chguid" name="chguid" value="${eqpMas.chguid}" readonly="readonly" />
					<label for="chgdat">更改時間</label>
					<input type="text" id="chgdat" name="chgdat" value="${eqpMas.chgdat}" readonly="readonly" />
				</div>

			</div>

		</form>
	</div>

</div>

<script type="text/javascript">
	$(function() {
		$("#saveBtn").click(function(event) {
			if (confirm('請確認資料是否正確并需保存?')) {
				$(this).attr('disabled', "true");
				$("#saveForm").submit();
			}
		});
		
		$("#saveForm").validate({
			rules : {
				cmpnbr : {
					required : true
				},
				facnbr : {
					required : true
				}
			},
			invalidHandler : function(form) {
				$('#saveBtn').removeAttr("disabled");
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		
	});
</script>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />



