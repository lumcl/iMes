<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/Q001.js"></script>

<div id="tabs">
	<ul>
		<li><a href="#tab-1">Q001 － 拜訪通知單</a></li>
	</ul>

	<div id="tab-1">

		<form action="/iMes/Q001/saveHeader" method="post" id="headerForm">

			<input type="hidden" id="id" name="id" value="${q001H.id}">

			<div id="nav">
				<ul>
					<c:if test="${(q001H.qhks !='Y' && q001H.sqyh == sessionScope.uid) || (q001H.id == 0)}">
						<li>
							<button type="button" id="saveHeaderBtn">
								<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" />保存單據
							</button>
						</li>
					</c:if>
					<c:if test="${(q001H.qhks !='Y' && q001H.sqyh == sessionScope.uid)}">
						<li>
							<button type="button" id="workflowBtn">
								<img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" />啟動流程
							</button>
						</li>
					</c:if>
					<li>
						<button type="button" id="createBtn" onclick="location.href='/iMes/Q001/newHeader'">
							<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建單據
						</button>
					</li>
					<li>
						<button type="button" onclick="history.back()">
							<img src="/iMes/stylesheet/icons/S_F_BACK.GIF" />返回前頁
						</button>
					</li>
					<c:if test="${q001H.id != 0}">
						<li>
							<button type="button" id="addVisitorBtn">
								<img src="/iMes/stylesheet/icons/S_CREPOS.GIF" />增加來訪人員
							</button>
						</li>
					</c:if>
					<c:if test="${q001H.sqyh == sessionScope.uid && q001H.id != 0}">
						<li>
							<button type="button" id="cancelBtn" onclick="location.href='/iMes/Q001/cancellation?id=${q001H.id}'">
								<img src="/iMes/stylesheet/icons/S_B_DELE.GIF" />取消來訪
							</button>
						</li>
					</c:if>
				</ul>
			</div>

			<div id="bdf">
				<div>
					<div></div>
					<label for="gsdm">公司代碼</label>
					<select name="gsdm" id="gsdm">
				        <option value="L210" <c:if test="${q001H.gsdm == 'L300'}">selected="selected"</c:if>>L210 東莞領航</option>
				        <option value="L300" <c:if test="${q001H.gsdm == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
				        <option value="L400" <c:if test="${q001H.gsdm == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
				    </select>
					<label for="bdbh">表單編碼</label>
					<input type="text" id="bdbh" name="bdbh" value="${q001H.bdbh}" readonly="readonly" />
				</div>
				<div>
					<label for="bdrq">表單日期</label>
					<input type="text" id="bdrq" name="bdrq" value="${q001H.bdrq}" class="required datepick" />
					<label for="bdzt">表單狀態</label>
					${q001H.bdztTxt} ${q001H.bdjgTxt} <c:if test="${q001H.bdfd.length() > 0}">
      <td><a href="/iMes/FileDownloader?filePath=${q001H.bdfd}">下載 Download</a></td>
     </c:if>
     <c:if test="${q001H.bdfd.length()== null ||q001H.bdfd.length()==0 }">
      <td></td>
     </c:if>
				</div>
				<div>
					<label for="lflb">來訪類別</label>
					<input type="radio" id="lflb" name="lflb" value="government" ${q001H.isLflb("government")}>
					<span class="label-for-radio">政府</span>
					<input type="radio" id="lflb" name="lflb" value="customer" ${q001H.isLflb("customer")}>
					<span class="label-for-radio">客户</span>
					<input type="radio" id="lflb" name="lflb" value="supplier" ${q001H.isLflb("supplier")}>
					<span class="label-for-radio">供應商</span>
					<input type="radio" id="lflb" name="lflb" value="other" ${q001H.isLflb("other")}>
					<span class="label-for-radio">其它</span>
				</div>
				<div>
					<label for="lfdw">來訪單位</label>
					<input type="text" id="lfdw" name="lfdw" value="${q001H.lfdw}" style="width: 652px;" class="required" />
				</div>
				<label for="lfry">來訪人員</label>
				<div style="display: inline-table; vertical-align: middle;">
					<table id="lfry">
						<thead>
							<tr>
								<th>序</th>
								<th>姓名</th>
								<th>性別</th>
								<th>職位</th>
								<th>電話</th>
								<th>證件</th>
								<th>證件號碼</th>
								<th>到達時間</th>
								<th>離開時間</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${visitors}" var="e">
								<tr>
									<td>${e.ROWID}</td>
									<td>${e.LFXM}</td>
									<td>${e.SEX}</td>
									<td>${e.LFZW}</td>
									<td>${e.LFDH}</td>
									<td>${e.ZJLX}</td>
									<td>${e.ZJHM}</td>
									<td>${e.JspSdsj}</td>
									<td>${e.JspSlsj}</td>
									<td>
										<a href="#" onclick="editVisitor(${e.ID})">修改</a> <a href="/iMes/Q001/deleteVisitor?id=${e.ID}&q001h_id=${e.Q001H_ID}">刪除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div>
					<label for="bdyy">來訪原因</label>
					<input type="text" id="bdyy" name="bdyy" value="${q001H.bdyy}" class="required" />
					<label for="sqyh">申請用戶</label>
					${q001H.sqyh} &nbsp; ${q001H.jlsj}
				</div>
				<div>
					<label for="lfdd" class="label-for-textarea">來訪擔當</label>
					<textarea rows="3" cols="80" id="lfdd" name="lfdd" class="users required">${q001H.lfdd}</textarea>
				</div>
				<div>
					<label for="ydsj">預計到達時間</label>
					<input type="text" id="ydrq" name="ydrq" value="${q001H.ydrq}" class="required datepick" style="width: 146px;" />
					<input type="text" id="ydsj_xs" name="ydsj_xs" value="${q001H.ydsj_xs}" style="width: 30px; text-align: center;" />
					:
					<input type="text" id="ydsj_fz" name="ydsj_fz" value="${q001H.ydsj_fz}" style="width: 30px; text-align: center;" />
					<label for="sdsj">實際到達時間</label>
					${q001H.sdrq} ${q001H.sdsj}
				</div>
				<div>
					<label for="ylsj">預計離開時間</label>
					<input type="text" id="ylrq" name="ylrq" value="${q001H.ylrq}" class="required datepick" style="width: 146px;" />
					<input type="text" id="ylsj_xs" name="ylsj_xs" value="${q001H.ylsj_xs}" style="width: 30px; text-align: center;" />
					:
					<input type="text" id="ylsj_fz" name="ylsj_fz" value="${q001H.ylsj_fz}" style="width: 30px; text-align: center;" />
					<label for="sdsj">實際离开時間</label>
					${q001H.slrq} ${q001H.slsj}
				</div>
				<div>
					<label for="lfmd" class="label-for-textarea">來訪目的</label>
					<textarea rows="3" cols="80" id="lfmd" name="lfmd">${q001H.lfmd}</textarea>
				</div>
				<div>
					<label for="pbry" class="label-for-textarea">陪伴人員</label>
					<textarea rows="3" cols="80" id="pbry" name="pbry" class="users">${q001H.pbry}</textarea>
				</div>
				<div>
					<label for="gjzg" class="label-for-textarea">高階主管</label>
					<textarea rows="3" cols="80" id="gjzg" name="gjzg" class="users">${q001H.gjzg}</textarea>
				</div>
				<div>
					<label for="hqsj">會前會時間</label>
					<input type="text" id="hqrq" name="hqrq" value="${q001H.hqrq}" class="datepick" style="width: 146px;" />
					<input type="text" id="hqsj_xs" name="hqsj_xs" value="${q001H.hqsj_xs}" style="width: 30px; text-align: center;" />
					:
					<input type="text" id="hqsj_fz" name="hqsj_fz" value="${q001H.hqsj_fz}" style="width: 30px; text-align: center;" />
				</div>
				<div>
					<label for="bezu" class="label-for-textarea">備註</label>
					<textarea rows="3" cols="80" id="bezu" name="bezu">${q001H.bezu}</textarea>
				</div>
				<div>
					<label for="hqyh" class="label-for-textarea">會簽用戶</label>
					<textarea rows="3" cols="80" id="hqyh" name="hqyh" class="users">${q001H.hqyh}</textarea>
				</div>

				<div id="adminSection">
					<h2>
						<span class="section">總務注意事項</span>
					</h2>

					<div>
						<label for="jcfs">進場方式</label>
						<input type="radio" id="jcfs" name="jcfs" value="diy" ${q001H.isJcfs("diy")}>
						<span class="label-for-radio">自行進入DT廠</span>
						<input type="radio" id="jcfs" name="jcfs" value="diytx" ${q001H.isJcfs("diytx")}>
						<span class="label-for-radio">自行進入TX廠</span>
						<input type="radio" id="jcfs" name="jcfs" value="airport" ${q001H.isJcfs("airport")}>
						<span class="label-for-radio">機場接人</span>
						<input type="radio" id="jcfs" name="jcfs" value="station" ${q001H.isJcfs("station")}>
						<span class="label-for-radio">鎮江火車站接人</span>
						<input type="radio" id="jcfs" name="jcfs" value="other" ${q001H.isJcfs("other")}>
						<span class="label-for-radio">其他</span>
					</div>
					<div>
						<label for="jsdd" class="label-for-textarea">接送地點/機場</label>
						<textarea rows="3" cols="80" id="jsdd" name="jsdd" class="users">${q001H.jsdd}</textarea>
					</div>
					<div>
						<label for="bfrs">拜訪人數</label>
						<input type="text" id="bfrs" name="bfrs" value="<fmt:formatNumber value="${q001H.bfrs}" pattern="#,###.##" />" />
					</div>
					<div>
						<label for="jcdd">就餐地點</label>
						<input type="radio" id="jcdd" name="jcdd" value="restaurant" ${q001H.isJcdd("restaurant")}>
						<span class="label-for-radio">飯店</span>
						<input type="radio" id="jcdd" name="jcdd" value="canteen" ${q001H.isJcdd("canteen")}>
						<span class="label-for-radio">公司餐廳</span>
						<input type="radio" id="jcdd" name="jcdd" value="diy" ${q001H.isJcdd("diy")}>
						<span class="label-for-radio">自行解決</span>

					</div>
					<div>
						<label for="jcbz">就餐標準</label>
						<input type="text" id="jcbz" name="jcbz" value="<fmt:formatNumber value="${q001H.jcbz}" pattern="#,###.##" />" />
					</div>
					<div>
						<label for="jcsj">就餐時間</label>
						<input type="text" id="jcsj" name="jcsj" value="${q001H.jcsj}" />
					</div>
					<div>
						<label for="jcry">就餐人數</label>
						<input type="text" id="jcry" name="jcry" value="${q001H.jcry}" />
					</div>
					<div>
						<label for="fygs">就餐費用歸屬</label>
						<input type="text" id="fygs" name="fygs" value="${q001H.fygs}" class="depts" />
					</div>
					<div>
						<label for="zsap">住宿安排</label>
						<input type="checkbox" id="zsap" name="zsap" value="hotel" ${q001H.isZsap("hotel")}>
						<span class="label-for-radio">酒店</span>
						<input type="checkbox" id="zsap" name="zsap" value="dom" ${q001H.isZsap("dom")}>
						<span class="label-for-radio">高舍</span>
						<input type="checkbox" id="zsap" name="zsap" value="txdom" ${q001H.isZsap("txdom")}>
						<span class="label-for-radio">东舍</span>
						<input type="checkbox" id="zsap" name="zsap" value="diy" ${q001H.isZsap("diy")}>
						<span class="label-for-radio">自行解決</span>
						<input type="checkbox" id="zsap" name="zsap" value="no" ${q001H.isZsap("no")}>
						<span class="label-for-radio">不需要</span>
					</div>
					<div>
						<label for="zsts">住宿天數</label>
						<input type="text" id="zsts" name="zsts" value="<fmt:formatNumber value="${q001H.zsts}" pattern="#,###.##" />" />
					</div>
					<div>
						<label for="hyzy">會議室</label>
						<input type="checkbox" id="hyzy" name="hyzy" value="r101" ${q001H.isHyzy("r101")}>
						<span class="label-for-radio">R101</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="r102" ${q001H.isHyzy("r102")}>
						<span class="label-for-radio">R102</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="r103" ${q001H.isHyzy("r103")}>
						<span class="label-for-radio">R103</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="r104" ${q001H.isHyzy("r104")}>
						<span class="label-for-radio">R104</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="frontdesk" ${q001H.isHyzy("frontdesk")}>
						<span class="label-for-radio">前台</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="rdcenter" ${q001H.isHyzy("rdcenter")}>
						<span class="label-for-radio">工程中心</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxA" ${q001H.isHyzy("sxA")}>
						<span class="label-for-radio">三相二樓A</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxB" ${q001H.isHyzy("sxB")}>
						<span class="label-for-radio">三相二樓B</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("sxC")}>
						<span class="label-for-radio">三相二樓C</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("sxD")}>
						<span class="label-for-radio">三相二樓大視訊</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("scD")}>
						<span class="label-for-radio">三廠一樓大會議室</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("ds1")}>
						<span class="label-for-radio">東舍視訊會議室</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("sc1A")}>
						<span class="label-for-radio">三廠一樓A</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("sc1B")}>
						<span class="label-for-radio">三廠一樓B</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("rcGC")}>
						<span class="label-for-radio">二廠三樓工程</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("rc1EDU")}>
						<span class="label-for-radio">二廠一樓教育訓練中心</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="sxC" ${q001H.isHyzy("sc3PE")}>
						<span class="label-for-radio">三廠三樓PE視訊會議室</span> <br />
						<label for="hyzy">資源設備</label>
						<input type="checkbox" id="hyzy" name="hyzy" value="whtbrd" ${q001H.isHyzy("whtbrd")}>
						<span class="label-for-radio">電子白板</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="whtbrdpen" ${q001H.isHyzy("whtbrdpen")}>
						<span class="label-for-radio">白板筆</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="projector" ${q001H.isHyzy("projector")}>
						<span class="label-for-radio">投影儀</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="dvdplayer" ${q001H.isHyzy("dvdplayer")}>
						<span class="label-for-radio">影碟機</span>
						<input type="checkbox" id="hyzy" name="hyzy" value="cmpcd" ${q001H.isHyzy("cmpcd")}>
						<span class="label-for-radio">公司簡介光盤 (中/英/日版)</span>
					</div>
					<div>
						<label for="xlfs">公司型錄分數</label>
						<input type="text" id="xlfs" name="xlfs" value="<fmt:formatNumber value="${q001H.xlfs}" pattern="#,###.##" />" />
					</div>
					<div>
						<label for="qtzy">其他資源</label>
						<input type="checkbox" id="qtzy" name="qtzy" value="welcomebrd" ${q001H.isQtzy("welcomebrd")}>
						<span class="label-for-radio">歡迎牌於綜合辦門口</span>
						<input type="checkbox" id="qtzy" name="qtzy" value="flower" ${q001H.isQtzy("flower")}>
						<span class="label-for-radio">會議桌上迎賓盆花</span>
						<input type="checkbox" id="qtzy" name="qtzy" value="coffe" ${q001H.isQtzy("coffe")}>
						<span class="label-for-radio">咖啡</span>
						<input type="checkbox" id="qtzy" name="qtzy" value="tea" ${q001H.isQtzy("tea")}>
						<span class="label-for-radio">茶水</span>
						<input type="checkbox" id="qtzy" name="qtzy" value="water" ${q001H.isQtzy("water")}>
						<span class="label-for-radio">礦泉水</span>
					</div>
					<div>
						<label for="zwbz" class="label-for-textarea">總務備註</label>
						<textarea rows="3" cols="80" id="zwbz" name="zwbz">${q001H.zwbz}</textarea>
					</div>
				</div>
			</div>
		</form>
		
		<form action="/iMes/FileUploader" method="post" enctype="multipart/form-data">
		   <input type="hidden" name="action" value="Document" />
		   <input type="hidden" name="GSDM" value="${q001H.gsdm}" />
		   <input type="hidden" name="BDDM" value="${q001H.bddm}" />
		   <input type="hidden" name="BDBH" value="${q001H.bdbh}" />
		   <br />
		
		   <table>
		    <tr>
		     <th colspan="2" align="center">文件上傳 <br/>File upload</th>
		    </tr>
		    <tr>
		     <td><input type="file" name="file" style="font-size: large;" /></td>
		     <td><button type="button" onclick="this.disabled=true;this.form.submit();">
		       <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
		      </button></td>
		    </tr>
		   </table>
		  </form>
		  
		<jsp:include page="/WEB-INF/pages/form/_route.jsp" />

	</div>

</div>

<div id="dialog-visitor" title="來訪人信息維護"></div>

<script type="text/javascript">
	function editVisitor(id){
		$("#dialog-visitor").load("/iMes/Q001/editVisitor?id="+id, function() {
			visitorDialog();
		});
	}

	function visitorDialog() {
		$("#dialog-visitor").dialog({
			autoOpen : true,
			height : 680,
			width : 500,
			modal : true,
			close : function() {
			},
			open : function() {
				$("#q001L-lfxm").focus();
				$("#q001L-q001h_id").val($("#id").val());
				$("#q001L-bdbh").val($("#bdbh").val());
				if ($("#q001L-lfdw").val().trim() == "") {
					$("#q001L-lfdw").val($("#lfdw").val());
				}
			}
		});
	};
	
	$(function() {
		$("#addVisitorBtn").click(function(event) {
			$("#dialog-visitor").load("/iMes/Q001/newVisitor", function() {
				visitorDialog();
			});
		});
	});
</script>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />