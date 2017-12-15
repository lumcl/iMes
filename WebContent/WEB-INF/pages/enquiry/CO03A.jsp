<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">CO03A - 工單物料異動清單</a></li>
 </ul>

 <div id="tab-1">
  <form action="CO03A" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#CO03A_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>生產訂單<br/>PrdOrd</th>
     <th>過帳日期<br/>PostDt</th>
     <th>工廠<br/>Plant</th>
     <th>物料<br/>Material</th>
     <th>批號<br/>LotNbr</th>
     <th>文件號碼<br/>DocNbr</th>
     <th>文件年份<br/>DocYr</th>
     <th>物料群組<br/>MatGrp</th>
     <th>移動類型<br/>MvT</th>
     <th>生產訂單<br/>PrdOrd</th>
     <th>物料<br/>Material</th>
     <th>物料群組<br/>MatGrp</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="aufnr_low" size="13" value=${param.aufnr_low }></td>
     <td><input type="text" name="budat_low" size="10" value=${param.budat_low }></td>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="charg_low" size="12" value=${param.charg_low }></td>
     <td><input type="text" name="mblnr_low" size="12" value=${param.mblnr_low }></td>
     <td><input type="text" name="mjahr_low" size="6" value=${param.charg_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="bwart_low" size="5" value=${param.bwart_low }></td>
     <td rowspan="2"><textarea cols="18" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="aufnr_high" size="13" value=${param.aufnr_high }></td>
     <td><input type="text" name="budat_high" size="10" value=${param.budat_high }></td>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="charg_high" size="12" value=${param.charg_high }></td>
     <td><input type="text" name="mblnr_high" size="12" value=${param.mblnr_high }></td>
     <td><input type="text" name="mjahr_high" size="6" value=${param.charg_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="text" name="bwart_high" size="5" value=${param.bwart_high }></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <table id="CO03A_table">
   <thead>
    <tr>
     <th>生產訂單<br/>PrdOrd</th>
     <th>過帳日期<br/>PostDt</th>
     <th>MvT</th>
     <th>料號<br/>Material</th>
     <th>說明<br/>Description</th>
     <th>料類<br/>MatGrp</th>
     <th>工廠<br/>Plant</th>
     <th>儲位<br/>Loc</th>
     <th>批次<br/>LotNbr</th>
     <th>金額<br/>Amount</th>
     <th>幣別<br/>Curr</th>
     <th>數量<br/>Qty</th>
     <th>UM</th>
     <th>文件號<br/>DocNbr</th>
     <th>文件年<br/>DocYr</th>
     <th>項<br/>DocLn</th>
     <th>入庫日期<br/>RecDt</th>
     <th>名稱<br/>Name</th>
     <th>廠商<br/>Supplier</th>
     <th>採購單<br/>PurOrd</th>
     <th>PO行<br/>Line</th>
     <th>倉庫<br/>Whse</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${list}" var="e">
     <tr>
      <td>${e.AUFNR}</td>
      <td>${e.BUDAT}</td>
      <td>${e.BWART}</td>
      <td>${e.MATNR}</td>
      <td>${e.MAKTX}</td>
      <td>${e.MATKL}</td>
      <td>${e.WERKS}</td>
      <td>${e.LGORT}</td>
      <td>${e.CHARG}</td>
      <td class="dec4"><fmt:formatNumber value="${e.DMBTR}" pattern="#,###.###" /></td>
      <td>${e.WAERS}</td>
      <td class="dec4"><fmt:formatNumber value="${e.MENGE}" pattern="#,###.###" /></td>
      <td>${e.MEINS}</td>
      <td>${e.MBLNR}</td>
      <td>${e.MJAHR}</td>
      <td>${e.ZEILE}</td>
      <td>${e.MBUDAT}</td>
      <td>${e.SORTL}</td>
      <td>${e.LIFNR}</td>
      <td>${e.EBELN}</td>
      <td class="dec4"><fmt:formatNumber value="${e.EBELP}" pattern="#,###.###" /></td>
      <td>${e.RWERK}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />