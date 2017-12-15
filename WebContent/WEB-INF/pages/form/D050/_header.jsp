<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form action="/iMes/D050/saveHeader" method="post" id="headerForm">

	<input type="hidden" id="id" name="id" value="${d050h.id}">

	<div id="nav">
		<ul>
			<c:if test="${(d050h.qhks !='Y' && d050h.sqyh == sessionScope.uid) || (d050h.id == 0)}">
				<li>
					<button type="button" id="saveHeaderBtn">
						<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" />保存單據
					</button>
				</li>
			</c:if>
			<c:if test="${(d050h.qhks !='Y' && d050h.sqyh == sessionScope.uid && d050h.bdbh != '')}">
				<li>
					<button type="button" id="workflowBtn">
						<img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" />啟動流程
					</button>
				</li>
			</c:if>
			<c:if test="${(d050h.bdbh != '')}">
				<li>
					<button type="button" id="createBtn" onclick="location.href='/iMes/D050/newHeader'">
						<img src="/iMes/stylesheet/icons/S_B_CREA.GIF" />新建單據
					</button>
				</li>
			</c:if>
			<li>
				<button type="button" onclick="history.back()">
					<img src="/iMes/stylesheet/icons/S_F_BACK.GIF" />返回前頁
				</button>
			</li>
		</ul>
	</div>

	<div id="bdf">
		<div>
			<label for="bdbh">表單編碼</label>
			<input type="text" id="bdbh" name="bdbh" value="${d050h.bdbh}" readonly="readonly" />
			<label for="gsdm">公司代碼</label>
			<input type="text" id="gsdm" name="gsdm" value="${d050h.gsdm}" readonly="readonly" />
			<label for="bdrq">表單日期</label>
			<input type="text" id="bdrq" name="bdrq" value="${d050h.bdrq}" />
		</div>
		<div>
			<label for="bdztText">表單狀態</label>
			<input type="text" id="bdztText" name="bdztText" value="${d050h.bdztText}" readonly="readonly" />
			<label for="bdjgText">表單結果</label>
			<input type="text" id="bdjgText" name="bdjgText" value="${d050h.bdjgText}" readonly="readonly" />
			<label for="qhks">流程開始</label>
			<input type="text" id="qhks" name="qhks" value="${d050h.qhks}" readonly="readonly" />
		</div>
		<div>
			<label for="kunnr">客戶代號</label>
			<input type="text" id="kunnr" name="kunnr" value="${d050h.kunnr}" onchange="ajaxGetCustomer();" />
			<label for="name1">客戶名稱</label>
			<input type="text" id="name1" name="name1" value="${d050h.name1}" readonly="readonly" />
			<label for="sortl">客戶簡稱</label>
			<input type="text" id="sortl" name="sortl" value="${d050h.sortl}" readonly="readonly" />
		</div>
		<div>
			<label for="bdyy" style="vertical-align: top;">取消原因</label>
			<textarea rows="5" cols="121" id="bdyy" name="bdyy">${d050h.bdyy}</textarea>
		</div>

		<div>
			<label for="salam">銷售金額</label>
			<input type="text" id="salam" name="salam" value="${d050h.salam}" readonly="readonly" />
			<label for="matam">材料金額</label>
			<input type="text" id="matam" name="matam" value="${d050h.matam}" readonly="readonly" />
			<label for="bdfd">表單附件</label>
			<input type="text" id="bdfd" name="bdfd" value="${d050h.bdfd}" />
		</div>

		<div>
			<label for="sqyh">申請用戶</label>
			<input type="text" id="sqyh" name="sqyh" value="${d050h.sqyh}" readonly="readonly" />
			<label for="qhyh">表單核准人</label>
			<input type="text" id="qhyh" name="qhyh" value="${d050h.qhyh}" readonly="readonly" />
			<label for="qhsj">表單核准時間</label>
			<input type="text" id="qhsj" name="qhsj" value="${d050h.qhsj}" readonly="readonly" />
		</div>
	</div>
</form>