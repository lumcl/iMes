<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MM63 - 庫存庫齡</a></li>
 </ul>

 <div id="tab-1">
  <form action="MM63" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#MM63_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>天數</th>
     <th>工廠</th>
     <th>物料</th>
     <th>物料群組</th>
     <th>採購群組</th>
     <th>物料說明</th>
     <th>物料</th>
     <th>物料群組</th>
     <th>採購群組</th>
     <th>批號</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="agday_low" size="10" value=${param.agday_low }></td>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="ekgrp_low" size="6" value=${param.ekgrp_low }></td>
     <td><input type="text" name="maktx_low" size="32" value=${param.maktx_low }></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="ekgrp_textarea">${param.ekgrp_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="15" rows="4" name="charg_textarea">${param.charg_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="agday_high" size="10" value=${param.agday_high }></td>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="text" name="ekgrp_high" size="6" value=${param.ekgrp_high }></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <table id="MM63_table">
   <thead>
    <tr>
     <th>工廠</th>
     <th>料號</th>
     <th>物料組</th>
     <th>規格</th>
     <th>UM</th>
     <th>採購</th>
     <th>單價</th>
     <th>數量</th>
     <th>金額</th>
     <th>007 Q</th>
     <th>015 Q</th>
     <th>030 Q</th>
     <th>060 Q</th>
     <th>090 Q</th>
     <th>180 Q</th>
     <th>360 Q</th>
     <th>999 Q</th>
     <th>1000+ Q</th>
     <th>007 $</th>
     <th>015 $</th>
     <th>030 $</th>
     <th>060 $</th>
     <th>090 $</th>
     <th>180 $</th>
     <th>360 $</th>
     <th>999 $</th>
     <th>1000+ $</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${list}" var="e">
     <tr>
      <td>${e.werks}</td>
      <td>${e.matnr}</td>
      <td>${e.matkl}</td>
      <td>${e.maktx}</td>
      <td>${e.meins}</td>
      <td>${e.ekgrp}</td>
      <td class="dec4"><fmt:formatNumber value="${e.matcs}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.menge}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.mtamt}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty007}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty015}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty030}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty060}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty090}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty180}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty360}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qty999}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.qtymax}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt007}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt015}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt030}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt060}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt090}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt180}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt360}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amt999}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.amtmax}" pattern="#,###.###" /></td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />