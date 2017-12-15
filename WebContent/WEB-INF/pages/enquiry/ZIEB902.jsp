<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">ZIEB902 - 物料文件清單</a></li>
 </ul>

 <div id="tab-1">
 <form action="ZIEB902" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#MB51_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th><br/> </th>
     <th>過帳日期 <br/>Pstng Dt<br/> </th>
     <th>工廠<br/>Plan</th>
     <th>物料<br/>Material</th>
     <th>批號<br/>Lot</th>
     <th>文件號碼<br/>Doc</th>
     <th>文件年份<br/>Year </th>
     <th>物料群組<br/>Matl Grp</th>
     <th>移動類型<br/>MvT</th>
     <th>輸入日期<br/>Entry Dt</th>
     <th>物料<br/>Material</th>
     <th>物料群組<br/>Matl Grp</th>
    </tr>
    <tr>
     <th>從<br/> </th>
     <td><input type="text" name="budat_low" size="10" value=${param.budat_low }></td>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="charg_low" size="12" value=${param.charg_low }></td>
     <td><input type="text" name="mblnr_low" size="12" value=${param.mblnr_low }></td>
     <td><input type="text" name="mjahr_low" size="6" value=${param.charg_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="bwart_low" size="5" value=${param.bwart_low }></td>
     <td><input type="text" name="cpudt_low" size="10" value=${param.cpudt_low }></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到<br/> </th>
     <td><input type="text" name="budat_high" size="10" value=${param.budat_high }></td>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="charg_high" size="12" value=${param.charg_high }></td>
     <td><input type="text" name="mblnr_high" size="12" value=${param.mblnr_high }></td>
     <td><input type="text" name="mjahr_high" size="6" value=${param.charg_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="text" name="bwart_high" size="5" value=${param.bwart_high }></td>
     <td><input type="text" name="cpudt_high" size="10" value=${param.cpudt_high }></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <table id="MB51_table">
   <thead>
    <tr>
     <th>過帳日期<br/>Pstng Dt </th>
     <th>倉庫<br/>Whse</th>
     <th>MvT</th>
     <th>料號<br/>Material</th>
     <th>料號說明<br/>Spec</th>
     <th>料類<br/>MatGrp</th>
     <th>批號<br/>Lot</th>
     <th>數量<br/>Qty</th>
     <th>單位<br/>UM</th>
     <th>幣別<br/>Curr</th>
     <th>金額<br/>Amount</th>
     <th>工單<br/>Prd Ord</th>
     <th>機種<br/>Finish Goods</th>
     <th>工廠<br/>Plant</th>
     <th>供應商<br/>Supplier</th>
     <th>廠商<br/>Name</th>
     <th>採購單<br/>PONbr</th>
     <th>PO行<br/>POLine </th>
     <th>客戶<br/>Customer</th>
     <th>名稱<br/>Name</th>
     <th>銷售單<br/>SO Nbr</th>
     <th>SO行<br/>SOLn</th>
     <th>文件號<br/>Doc Nbr </th>
     <th>文件年<br/>Doc Yr </th>
     <th>文件行<br/>Doc Ln </th>
     <th>輸入日期<br/>Entry Dt </th>
     <th>時間<br/>Time </th>
     <th>用戶<br/>User </th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${list}" var="e">
     <tr>
      <td>${e.BUDAT}</td>
      <td>${e.WERKS}</td>
      <td>${e.BWART}</td>
      <td>${e.MATNR}</td>
      <td>${e.MAKTX}</td>
      <td>${e.MATKL}</td>
      <td>${e.CHARG}</td>
      <td class="dec4"><fmt:formatNumber value="${e.MENGE}" pattern="#,###.###" /></td>
      <td>${e.MEINS}</td>
      <td>${e.WAERS}</td>
      <td class="dec4"><fmt:formatNumber value="${e.DMBTR}" pattern="#,###.###" /></td>
      <td>${e.AUFNR}</td>
      <td>${e.PMATNR}</td>
      <td>${e.PWERK}</td>
      <td>${e.LIFNR}</td>
      <td>${e.LNAME}</td>
      <td>${e.EBELN}</td>
      <td>${e.EBELP}</td>
      <td>${e.KUNNR}</td>
      <td>${e.KNAME}</td>
      <td>${e.KDAUF}</td>
      <td>${e.KDPOS}</td>
      <td>${e.MBLNR}</td>
      <td>${e.MJAHR}</td>
      <td class="dec4"><fmt:formatNumber value="${e.ZEILE}" pattern="#,###.###" /></td>
      <td>${e.CPUDT}</td>
      <td>${e.CPUTM}</td>
      <td>${e.USNAM}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />