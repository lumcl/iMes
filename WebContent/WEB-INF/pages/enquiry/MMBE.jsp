<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MMBE - 批次庫存清單</a></li>
 </ul>

 <div id="tab-1">
  <form action="MMBE" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#MMBE_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠<br/>Plant</th>
     <th>物料<br/>Material</th>
     <th>物料群組<br/>MatGrp</th>
     <th>物料說明<br/>MatDesc</th>
     <th>儲位<br/>Loc</th>
     <th>物料<br/>Material</th>
     <th>物料群組<br/>MatGrp</th>
     <th>批號<br/>LotNbr</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="maktx_low" size="30" value=${param.maktx_low }></td>
     <td><input type="text" name="lgort_low" size="6" value=${param.lgort_low }></td>
     <td rowspan="2"><textarea cols="20" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="15" rows="4" name="charg_textarea">${param.charg_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="checkbox" name="dsptotal_checkbox" value="X" <c:if test="${param.dsptotal_checkbox == 'X'}">checked="checked"</c:if>> 顯示合計</td>
     <td><input type="text" name="lgort_high" size="6" value=${param.lgort_high }></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <table id="MMBE_table">
   <thead>
    <tr>
     <th>工廠<br/>Plant</th>
     <th>料號<br/>Material</th>
     <th>儲位<br/>Loc</th>
     <th>批次號<br/>LotNbr</th>
     <th>未限制<br/>Unrest</th>
     <th>檢驗<br/>Inspec</th>
     <th>限制<br/>Restr</th>
     <th>管制<br/>Block</th>
     <th>UM</th>
     <th>承約<br/>Reserved</th>
     <th>料類<br/>MatGrp</th>
     <th>料號說明<br/>MatDesc</th>
     <th>過帳日期<br/>PostDt</th>
     <th>名稱<br/>Name</th>
     <th>廠商<br/>Supplier</th>
     <th>採購單<br/>PurOrd</th>
     <th>PO行<br/>Line</th>
     <th>工單<br/>MO</th>
     <th>倉庫<br/>Whse</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${list}" var="e">
     <tr <c:if test="${e.CHARG == '**工廠合計[Plant]'}">bgcolor="#fcd5b4"</c:if> <c:if test="${e.CHARG == '****儲位合計[Loc]'}">bgcolor="#ebf1de"</c:if>>
      <td>${e.WERKS}</td>
      <td>${e.MATNR}</td>
      <td>${e.LGORT}</td>
      <td>${e.CHARG}</td>
      <td class="dec4"><fmt:formatNumber value="${e.CLABS}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.CINSM}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.CSPEM}" pattern="#,###.###" /></td>
      <td class="dec4"><fmt:formatNumber value="${e.CEINM}" pattern="#,###.###" /></td>
      <td>${e.MEINS}</td>
      <td class="dec4"><fmt:formatNumber value="${e.VMENG}" pattern="#,###.###" /></td>
      <td>${e.MATKL}</td>
      <td>${e.MAKTX}</td>
      <td>${e.BUDAT}</td>
      <td>${e.SORTL}</td>
      <td>${e.LIFNR}</td>
      <td>${e.EBELN}</td>
      <td class="dec4"><fmt:formatNumber value="${e.EBELP}" pattern="#,###.###" /></td>
      <td>${e.AUFNR}</td>
      <td>${e.RWERK}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />