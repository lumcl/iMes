<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MM67 - 採購進料記錄</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/MM67" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#MM67_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠/ Plant</th>
     <th>進料日期/ RcvDate</th>
     <th>物料/ Material</th>
     <th>供應商/ Supplier</th>
     <th>物料群組/ MatGrp</th>
     <th>物料說明/ MatDesc</th>
     <th>物料/ Material</th>
     <th>物料群組/ MatGrp</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="budat_low" size="10" value=${param.budat_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="lifnr_low" size="12" value=${param.lifnr_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="maktx_low" size="32" value=${param.maktx_low }></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="budat_high" size="10" value=${param.budat_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="lifnr_high" size="12" value=${param.lifnr_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="MM67_table">
    <thead>
     <tr>
      <th>Reg</th>
      <th>Plant</th>
      <th>Material</th>
      <th>MatGrp</th>
      <th>MatDesc</th>
      <th>Supplier</th>
      <th>Short</th>
      <th>TranDate</th>
      <th>Curr</th>
      <th>Price</th>
      <th>Qty</th>
      <th>Amount</th>
      <th>USDPrc</th>
      <th>USDAmt</th>
      <th>MRate</th>
      <th>UM</th>
      <th>MGrpDesc</th>
      <th>GrpDesc</th>
      <th>CTYPE</th>
      <th>Company Name</th>
      <th>MvT</th>
      <th>PurOrd</th>
      <th>Line</th>
      <th>DocNo</th>
      <th>DocLn</th>
      <th>Typ</th>
      <th>POType</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr>
       <td>${e.VTWEG}</td>
       <td>${e.WERKS}</td>
       <td>${e.MATNR}</td>
       <td>${e.MATKL}</td>
       <td>${e.MAKTX}</td>
       <td>${e.LIFNR}</td>
       <td>${e.SORTL}</td>
       <td>${e.BUDAT}</td>
       <td>${e.WAERS}</td>
       <td class="dec6"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.######" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.MENGE}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.WRBTR}" pattern="#,###.###" /></td>
       <td class="dec6"><fmt:formatNumber value="${e.MRATE * e.NETPR}" pattern="#,###.######" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.MRATE * e.NETPR * e.MENGE}" pattern="#,###.####" /></td>
       <td class="dec6"><fmt:formatNumber value="${e.MRATE}" pattern="#,###.######" /></td>
       <td>${e.MEINS}</td>
       <td>${e.WGBEZ}</td>
       <td>${e.HRKTX}</td>
       <td>${e.CTYPE}</td>
       <td>${e.NAME1}</td>
       <td>${e.BWART}</td>
       <td>${e.EBELN}</td>
       <td>${e.EBELP}</td>
       <td>${e.BELNR}</td>
       <td>${e.BUZEI}</td>
       <td>${e.SHKZG}</td>
       <td>${e.BSART}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />