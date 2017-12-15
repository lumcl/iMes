<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MM66 - 原材料排程</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/MM66/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#MM66_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠</th>
     <th>物料</th>
     <th>供應商</th>
     <th>採購員</th>
     <th>物料群組</th>
     <th>採購群組</th>
     <th>物料說明</th>
     <th>物料</th>
     <th>物料群組</th>
     <th>採購群組</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="lifnr_low" size="12" value=${param.lifnr_low }></td>
     <td><input type="text" name="userid_low" size="18" value=${param.userid_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="ekgrp_low" size="6" value=${param.ekgrp_low }></td>
     <td><input type="text" name="maktx_low" size="32" value=${param.maktx_low }></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="ekgrp_textarea">${param.ekgrp_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="lifnr_high" size="12" value=${param.lifnr_high }></td>
     <td><input type="text" name="userid_high" size="18" value=${param.userid_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="text" name="ekgrp_high" size="6" value=${param.ekgrp_high }></td>
     <td></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="MM66_table">
    <thead>
     <tr>
      <th>工廠</th>
      <th>料號</th>
      <th>規格</th>
      <th>料類</th>
      <th>需求日期</th>
      <th>需求單</th>
      <th>機種</th>
      <th>PO日期</th>
      <th>採購單</th>
      <th>行號</th>
      <th>項</th>
      <th>數量</th>
      <th>UM</th>
      <th>供應商</th>
      <th>名稱</th>
      <th>組織</th>
      <th>採購組</th>
      <th>圖號</th>
      <th>版本</th>
      <th>MOQ</th>
      <th>客戶</th>
      <th>建立時間</th>
      <th>BDMNG</th>
      <th>ENMNG</th>
      <th>LGORT</th>
      <th>POSNR</th>
      <th>RSNUM</th>
      <th>RSPOS</th>
      <th>STYPE</th>
      <th>BDART</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr>
       <td>${e.WERKS}</td>
       <td>${e.MATNR}</td>
       <td>${e.MAKTX}</td>
       <td>${e.MATKL}</td>
       <td>${e.BDTER}</td>
       <td>${e.AUFNR}</td>
       <td>${e.PMATNR}</td>
       <td>${e.AEDAT}</td>
       <td>${e.EBELN}</td>
       <td>${e.EBELP}</td>
       <td>${e.ETENR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.MENGE}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.LIFNR}</td>
       <td>${e.SORTL}</td>
       <td>${e.EKORG}</td>
       <td>${e.EKGRP}</td>
       <td>${e.ZEINR}</td>
       <td>${e.AESZN}</td>
       <td class="dec4"><fmt:formatNumber value="${e.BSTRF}" pattern="#,###.###" /></td>
       <td>${e.PBDNR}</td>
       <td><fmt:formatDate value="${e.JLSJ}" type="both" pattern="yyyyMMdd" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.BDMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ENMNG}" pattern="#,###.###" /></td>
       <td>${e.LGORT}</td>
       <td>${e.POSNR}</td>
       <td>${e.RSNUM}</td>
       <td>${e.RSPOS}</td>
       <td>${e.STYPE}</td>
       <td>${e.BDART}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />