<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">ME03 - 來源信息認可清單</a></li>
 </ul>

 <div id="tab-1">
  <form action="ME03" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#ME03_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠</th>
     <th>物料</th>
     <th>供應商</th>
     <th>物料群組</th>
     <th>採購組</th>
     <th>物料說明</th>
     <th>物料</th>
     <th>供應商</th>
     <th>物料群組</th>
     <th>採購組</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="lifnr_low" size="12" value=${param.lifnr_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="ekgrp_low" size="6" value=${param.ekgrp_low }></td>
     <td><input type="text" name="maktx_low" size="30" value=${param.maktx_low }></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="14" rows="4" name="lifnr_textarea">${param.lifnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="15" rows="4" name="ekgrp_textarea">${param.ekgrp_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="lifnr_high" size="12" value=${param.lifnr_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="text" name="ekgrp_high" size="6" value=${param.ekgrp_high }></td>
     <td></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <table id="ME03_table">
   <thead>
    <tr>
     <th>採購</th>
     <th>料類</th>
     <th>料號</th>
     <th>料號規格</th>
     <th>工廠</th>
     <th>供應商</th>
     <th>短名</th>
     <th>生效日期</th>
     <th>有效期限</th>
     <th>FIX</th>
     <th>BLK</th>
     <th>MRP</th>
     <th>家數</th>
     <th>貨幣</th>
     <th>價格</th>
     <th>基數</th>
     <th>價格有效</th>
     <th>RMB單價</th>
     <th>USD單價</th>
     <th>圖號</th>
     <th>LT</th>
     <th>MOQ</th>
     <th>MRP群</th>
     <th>QI有效</th>
     <th>認可</th>
     <th>訂單</th>
     <th>QI說明</th>
     <th>QI凍結</th>
     <th>名稱</th>
     <th>資訊記錄</th>
     <th>RMB匯率</th>
     <th>USD匯率</th>
     <th>凍結</th>

    </tr>
   </thead>
   <tbody>
    <c:forEach items="${list}" var="e">
     <tr>
      <td>${e.EKGRP}</td>
      <td>${e.MATKL}</td>
      <td>${e.MATNR}</td>
      <td>${e.MAKTX}</td>
      <td>${e.WERKS}</td>
      <td>${e.LIFNR}</td>
      <td>${e.SORTL}</td>
      <td>${e.VDATU}</td>
      <td>${e.BDATU}</td>
      <td>${e.FLIFN}</td>
      <td>${e.NOTKZ}</td>
      <td>${e.AUTET}</td>
       <td class="dec4"><fmt:formatNumber value="${e.COUNT}" pattern="#,###.###" /></td>
      <td>${e.WAERS}</td>
       <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.PEINH}" pattern="#,###.###" /></td>
      <td>${e.PRDAT}</td>
       <td class="dec4"><fmt:formatNumber value="${e.RMBPR}" pattern="#,###.######" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.USDPR}" pattern="#,###.######" /></td>
      <td>${e.ZEINR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.PLIFZ}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.BSTRF}" pattern="#,###.###" /></td>
      <td>${e.DISGR}</td>
      <td>${e.FREI_DAT}</td>
       <td class="dec4"><fmt:formatNumber value="${e.FREI_MNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.BEST_MG}" pattern="#,###.###" /></td>
      <td>${e.SPERRGRUND}</td>
      <td>${e.KURZTEXT}</td>
      <td>${e.NAME1}</td>
      <td>${e.INFNR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.RMBCR}" pattern="#,###.######" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.USDCR}" pattern="#,###.######" /></td>
      <td>${e.SPERQ}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />