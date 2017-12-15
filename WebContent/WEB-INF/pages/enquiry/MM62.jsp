<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MM62 - 供應商送貨排程</a></li>
 </ul>

 <div id="tab-1">
  <form action="MM62" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#MM62_table')">
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
<table id="MM62_table">
 <caption>${mrpDate} ${mrpTime }</caption>
 <thead>
  <tr>
   <th>日期</th>
   <th>重新排程</th>
   <th>MRP元素</th>
   <!-- 
   <th>開始/核發</th>
    -->
   <th>單號</th>
   <th>項</th>
   <th>MRP要素資料</th>
   <th>收貨/需求數量</th>
   <th>可用數量</th>
   <th>工廠</th>
   <th>儲位</th>
   <th>單號</th>
   <!-- 
   <th>MRP_ITEM</th>
    -->
   <th>名稱</th>
   <th>供應商</th>
   <th>客戶</th>
   <th>例外</th>
   <th>IND</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach var="e" items="${list}">
   <tr>
    <td><fmt:formatDate value="${e.DAT00}" type="both" pattern="yyyyMMdd" /></td>
    <td><fmt:formatDate value="${e.UMDAT1}" type="both" pattern="yyyyMMdd" /></td>
    <td>${e.DELB0}</td>
    <!-- 
    <td><fmt:formatDate value="${e.DAT01}" type="both" pattern="yyyyMMdd" /></td>
     -->
    <td>${e.DELNR}</td>
    <td class="int"><fmt:formatNumber value="${e.DELPS}" pattern="#" /></td>
    <td>${e.BAUGR}</td>
    <td class="dec4"><fmt:formatNumber value="${e.MNG01}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${e.ATP01}" pattern="#,###.###" /></td>
    <td>${e.PWWRK}</td>
    <td>${e.LGORT_D}</td>
    <td>${e.DEL12}</td>
    <!-- 
     <td>${e.DELET}</td>
     -->
    <td>${e.SORTL}</td>
    <td>${e.LIFNR}</td>
    <td>${e.KUNNR}</td>
    <td>${e.AUSSL}</td>
    <td>${e.DELKZ}</td>
   </tr>
  </c:forEach>
 </tbody>
</table>  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />