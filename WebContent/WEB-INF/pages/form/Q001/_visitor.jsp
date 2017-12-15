<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form action="/iMes/Q001/saveVisitor" method="post" id="visitorForm" accept-charset="UTF-8">
	<input type="hidden" id="q001L-id" name="id" value="${q001L.id}" />
	<input type="hidden" id="q001L-q001h_id" name="q001h_id" value="${q001L.q001h_id}" />
	<input type="hidden" id="q001L-bdbh" name="bdbh" value="${q001L.bdbh}" />

	<div id="nav">
		<ul>
			<li>
				<button type="button" id="saveVisitorBtn">
					<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" />保存信息
				</button>
			</li>
			<li>
				<button type="button" id="closeBtn">
					<img src="/iMes/stylesheet/icons/S_F_BACK.GIF" />退出
				</button>
			</li>
		</ul>
	</div>
	<div id="bdf">
		<div>
			<label for="lfxm">姓名</label>
			<input type="text" id="q001L-lfxm" name="lfxm" value="${q001L.lfxm}" class="required visitors" />
		</div>
		<div>
			<label for="sex">性別</label>
			<input type="radio" id="q001L-sex-M" name="sex" value="M" ${q001L.isSex("M")} />
			<span style="padding-right: 15px; padding-left: 10px">男性</span>
			<input type="radio" id="q001L-sex-F" name="sex" value="F" ${q001L.isSex("F")} />
			<span style="padding-right: 15px; padding-left: 10px">女性</span>
		</div>
		<div>
			<label for="zjlx">證件類型</label>
			<input type="radio" id="q001L-zjlx-P" name="zjlx" value="身分證" ${q001L.isZjlx("身份證")} />
			身份證
			<input type="radio" id="q001L-zjlx-D" name="zjlx" value="駕駛證" ${q001L.isZjlx("駕駛證")} />
			駕駛證
			<input type="radio" id="q001L-zjlx-T" name="zjlx" value="旅遊證件" ${q001L.isZjlx("旅遊證件")} />
			旅遊證件
			<input type="radio" id="q001L-zjlx-N" name="zjlx" value="名信片" ${q001L.isZjlx("名信片")} />
			名信片
			<input type="radio" id="q001L-zjlx-O" name="zjlx" value="其他" ${q001L.isZjlx("其他")} />
			其他
		</div>
		<div>
			<label for="zjhm">證件號碼</label>
			<input type="text" id="q001L-zjhm" name="zjhm" value="${q001L.zjhm}" class="required" />
		</div>
		<div>
			<label for="lfdw">單位</label>
			<input type="text" id="q001L-lfdw" name="lfdw" value="${q001L.lfdw}" class="required" />
		</div>
		<div>
			<label for="lfzw">職位</label>
			<input type="text" id="q001L-lfzw" name="lfzw" value="${q001L.lfzw}" class="required" />
		</div>
		<div>
			<label for="lfdw">電話</label>
			<input type="text" id="q001L-lfdh" name="lfdh" value="${q001L.lfdh}" class="required" />
		</div>
		<div>
			<label for="lfyj">郵件</label>
			<input type="text" id="q001L-lfyj" name="lfyj" value="${q001L.lfyj}" />
		</div>
		<div>
			<label for="addr" style="position: relative; bottom: 25px;">地址</label>
			<textarea rows="3" cols="28" id="q001L-addr" name="addr">${q001L.addr}</textarea>
		</div>
		<div>
			<label for="cphm">車牌號碼</label>
			<input type="text" id="q001L-cphm" name="cphm" value="${q001L.cphm}" />
		</div>
		<div>
			<label for="hghm">貨櫃號碼</label>
			<input type="text" id="q001L-hghm" name="hghm" value="${q001L.hghm}" />
		</div>
		<div>
			<label for="lfbz" style="position: relative; bottom: 25px;">備註</label>
			<textarea rows="3" cols="28" id="q001L-lfbz" name="lfbz">${q001L.lfbz}</textarea>
		</div>
	</div>
</form>

<script type="text/javascript">
	$(function() {
		
		$("#closeBtn").click(function(event) {
			$("#dialog-visitor").dialog("destroy");
		});
		$("#saveVisitorBtn").click(function(event) {
			if (confirm('請確認資料是否正確并需保存?\n Please confirmed the form data.')) {
				$(this).attr('disabled', "true");
				$("#visitorForm").submit();
			}
		});
		
		$("#visitorForm").validate({
			rules : {
				zjlx : {
					required : true
				},
				sex : {
					required : true
				}
			},
			invalidHandler : function(form) {
				$('#saveVisitorBtn').removeAttr("disabled");
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		
		$('.visitors').each(function() {
			$(this).bind("keydown", function(event) {
				if (event.keyCode == '13') {
					return false;
				} else if (event.keyCode === $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
					event.preventDefault();
				}
			}).autocomplete({
				source : function(request, response) {
					$.getJSON("/iMes/Q001/visitors", {
						term : encodeURI(alltrim(request.term), "utf-8")
					}, response);
				},
				delay : 10,
				minLength : 1,
				select : function(event, ui) {
					var record = this.value.split("|");
					this.value = record[0];
					$('#q001L-lfxm').val(record[0]);
					(record[1] == "M") ? $('#q001L-sex-M').attr('checked', 'checked') : $('#q001L-sex-F').attr('checked', 'checked');
					if (record[2] == "身分證") {
						$('#q001L-zjlx-P').attr('checked', 'checked');
					} else if (record[2] == "駕駛證") {
						$('#q001L-zjlx-D').attr('checked', 'checked');
					} else if (record[2] == "旅遊證件") {
						$('#q001L-zjlx-T').attr('checked', 'checked');
					} else if (record[2] == "名信片") {
						$('#q001L-zjlx-N').attr('checked', 'checked');
					} else if (record[2] == "其他") {
						$('#q001L-zjlx-O').attr('checked', 'checked');
					}
					$('#q001L-zjhm').val(record[3]);
					$('#q001L-lfdw').val(record[4]);
					$('#q001L-lfzw').val(record[5]);
					$('#q001L-lfdh').val(record[6]);
					$('#q001L-lfyj').val(record[7]);
					$('#q001L-addr').val(record[8]);
					$('#q001L-cphm').val(record[9]);
					$('#q001L-hghm').val(record[10]);
					$('#q001L-lfbz').val(record[11]);
					return false;
				}
			});
		});
		
	});
</script>


