<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
	<ul>
		<li><a href="#tab-1">EQP002 备品配件主档</a></li>
	</ul>

	<div id="tab-1">

		<form action="/iMes/EQP002/save" method="post" id="saveForm">
			<input type="hidden" id="id" name="id" value="${mtrMas.id}">
			<div id="nav">
				<ul>
					<li>
						<button type="button" id="saveBtn">
							<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" />保存資料
						</button>
					</li>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/EQP002/create'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建備件
						</button>
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
						${mtrMas.radioCmpnbr}
					</div>
					<div>
						<label for="facnbr">工廠</label>
						${mtrMas.radioFacnbr}
					</div>
					<div>
						<label for="matnbr">物料號碼</label>
						<input type="text" id="matnbr" name="matnbr" value="${mtrMas.matnbr}" class="required" <c:if test="${mtrMas.id != 0 }"> readonly="readonly"</c:if> />
					</div>
					<div>
						<label for="matsts">物料狀態</label>
						<input type="text" id="matsts" name="matsts" value="${mtrMas.matsts}" />
					</div>
					<div>
						<label for="matdes">物料說明</label>
						<input type="text" id="matdes" name="matdes" value="${mtrMas.matdes}" style="width: 613px;" class="required" />
					</div>
					<div>
						<label for="matmdl">物料型號</label>
						<input type="text" id="matmdl" name="matmdl" value="${mtrMas.matmdl}" style="width: 613px;" />
					</div>
					<div>
						<label for="matspe">物料規格</label>
						<input type="text" id="matspe" name="matspe" value="${mtrMas.matspe}" style="width: 613px;" />
					</div>
					<div>
						<label for="matbrd">物料品牌</label>
						<input type="text" id="matbrd" name="matbrd" value="${mtrMas.matbrd}" />
						<label for="mfgnam">生產廠家</label>
						<input type="text" id="mfgnam" name="mfgnam" value="${mtrMas.mfgnam}" />
					</div>
					<div>
						<label for="safqty">安全數量</label>
						<input type="text" id="safqty" name="safqty" value="${mtrMas.safqty}" />
						<label for="stkqty">庫存數量</label>
						<input type="text" id="stkqty" name="stkqty" value="${mtrMas.stkqty}" disabled="disabled" />
					</div>
					<div>
						<label for="matuom">物料單位</label>
						${mtrMas.radioMatuom}
					</div>
					<div>
						<label for="locate">放置位置</label>
						<input type="text" id="locate" name="locate" value="${mtrMas.locate}" />
					</div>
					<div>
						<label for="repdep">保管部門</label>
						<input type="text" id="repdep" name="repdep" value="${mtrMas.repdep}" />
						<label for="repuid">保管人</label>
						<input type="text" id="repuid" name="repuid" value="${mtrMas.repuid}" class="users" />
					</div>
					<div>
						<label for="crtdat">建立时间</label>
						<input type="text" id="crtdat" name="crtdat" value="${mtrMas.crtdat}" />
						<label for="crtuid">建立用户</label>
						<input type="text" id="crtuid" name="crtuid" value="${mtrMas.crtuid}" />
					</div>
					<div>
						<label for="chgdat">更改时间</label>
						<input type="text" id="chgdat" name="chgdat" value="${mtrMas.chgdat}" />
						<label for="chguid">更改用户</label>
						<input type="text" id="chguid" name="chguid" value="${mtrMas.chguid}" />
					</div>

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