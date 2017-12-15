<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">SD01 - 生產訂單作業查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="SD01" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#SD01_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>選項</th>
     <th>通路</th>
     <th>可用日期</th>
     <th>交貨日期</th>
     <th>業務組</th>
     <th>客戶</th>
     <th>機種</th>
    </tr>
    <tr>
     <th>從</th>
     <td rowspan="2">
       <input type="checkbox" name="sto_checkbox" value="X" <c:if test="${param.sto_checkbox=='X'}"> checked="checked" </c:if>> 含STO 
     </td>   
     <td><input type="text" name="vtweg_low" size="4" value=${param.vtweg_low }></td>
     <td><input type="text" name="mbdat_low" size="10" value=${param.mbdat_low }></td>
     <td><input type="text" name="edatu_low" size="10" value=${param.edatu_low }></td>
     <td rowspan="2"><textarea cols="7" rows="4" name="vkgrp_textarea">${param.vkgrp_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="14" rows="4" name="kunnr_textarea">${param.kunnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="vtweg_high" size="4" value=${param.vtweg_high }></td>
     <td><input type="text" name="mbdat_high" size="10" value=${param.mbdat_high }></td>
     <td><input type="text" name="edatu_high" size="10" value=${param.edatu_high }></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="SD01_table">
    <thead>
     <tr>
      <th>序</th>
      <th>區</th>
      <th>組</th>
      <th>可用日期</th>
      <th>交貨日期</th>
      <th>客戶</th>
      <th>簡稱</th>
      <th>SO號碼</th>
      <th>SO項</th>
      <th>機種</th>
      <th>工廠</th>
      <th>數量</th>
      <th>UM</th>
      <th>DN號碼</th>
      <th>DN項</th>
      <th>批次</th>
      <th>幣別</th>
      <th>單價</th>
      <th>USD</th>
      <th>USD金額</th>
      <th>481A</th>
      <th>482A</th>
      <th>101A</th>
      <th>381A</th>
      <th>281A</th>
      <th>701A</th>
      <th>382A</th>
      <th>921A</th>
      <th>Y2</th>
      <th>YB</th>
      <th>類別</th>
      <th>機種說明</th>
      <th>排程</th>
      <th>匯率</th>
      <th>確認</th>
      <th>Tr</th>
      <th>名稱</th>
      <th>庫位</th>
      <th>Mvt</th>
      <th>Y1</th>
      <th>Y3</th>
      <th>YC</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr ${e.COLOR}>
       <td>${e.ROWID}</td>
       <td>${e.VTWEG}</td>
       <td>${e.VKGRP}</td>
       <td>${e.MBDAT}</td>
       <td>${e.EDATU}</td>
       <td>${e.KUNNR}</td>
       <td>${e.SORTL}</td>
       <td>${e.VBELN}</td>
       <td class="num"><fmt:formatNumber value="${e.POSNR}" pattern="#" /></td>
       <td>${e.MATNR}</td>
       <td>${e.WERKS}</td>
       <td class="dec4"><fmt:formatNumber value="${e.OMENG}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.DLONR}</td>
       <td>${e.DLPOS}</td>
       <td>${e.CHARG}</td>
       <td>${e.WAERK}</td>
       <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.NETPR * e.USDCR}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.NETPR * e.USDCR * e.OMENG}" pattern="#,###.##" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W481A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W482A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W101A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W381A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W281A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W701A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W382A}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.W921A}" pattern="#,###.###" /></td>
       <td>${e.Y2}</td>
       <td>${e.YB}</td>
       <td>${e.MATKL}</td>
       <td>${e.MAKTX}</td>
       <td>${e.ETENR}</td>
       <td>${e.USDCR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.VMENG}" pattern="#,###.###" /></td>
       <td>${e.VBTYP}</td>
       <td>${e.NAME1}</td>
       <td>${e.LGORT}</td>
       <td>${e.BWART}</td>
       <td>${e.Y1}</td>
       <td>${e.Y3}</td>
       <td>${e.YC}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>

  </c:if>

 </div>


</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />