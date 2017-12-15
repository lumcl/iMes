<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">CS11 - 機種BOM展開</a></li>
 </ul>

 <div id="tab-1">
  <form action="CS11" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#CS11_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠<br/>Plant</th>
     <th>機種<br/>FinishGoods</th>
     <th>物料<br/>Material</th>
     <th>物料群組<br/>MatGrp</th>
     <th>物料規格<br/>MatSpec</th>
     <th>機種<br/>FinsihGoods</th>
     <th>物料<br/>Material</th>
     <th>物料群組<br/>MatGrp</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="pmatnr_low" size="20" value=${param.pmatnr_low }></td>
     <td><input type="text" name="cmatnr_low" size="20" value=${param.cmatnr_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td><input type="text" name="maktx_low" value="${param.maktx_low}" size="40"></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="pmatnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="cmatnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="pmatnr_high" size="20" value=${param.pmatnr_high }></td>
     <td><input type="text" name="cmatnr_high" size="20" value=${param.cmatnr_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <table id="CS11_table">
   <thead>
    <tr>
     <th>工廠<br/>Plant</th>
     <th>機種<br/>FinishGoods</th>
     <th>機種說明<br/>FGDesc</th>
     <th>料類<br/>MatDesc</th>
     <th>料號<br/>Material</th>
     <th>料號規格<br/>MatSpec</th>
     <th>料倉<br/>MatWhse</th>
     <th>單個用量<br/>Usage</th>
     <th>單位<br/>UM</th>
     <th>位置<br/>Pos</th>
     <th>代<br/>Alt</th>
     <th>主替<br/>Main</th>
     <th>組<br/>AGrp</th>
     <th>ECN</th>
     <th>階<br/>Lvl</th>
     <th>半成品<br/>Wip</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${list}" var="e">
     <tr>
      <td>${e.WERKS}</td>
      <td>${e.PMATNR}</td>
      <td>${e.PMAKTX}</td>
      <td>${e.CMATKL}</td>
      <td>${e.CMATNR}</td>
      <td>${e.CMAKTX}</td>
      <td>${e.CWERKS}</td>
      <td>${e.DUSAGE}</td>
      <td>${e.DUOM}</td>
      <td>${e.POTX1}</td>
      <td>${e.ALPOS}</td>
      <td>${e.EWAHR}</td>
      <td>${e.ALPGR}</td>
      <td>${e.AENNR}</td>
      <td>${e.BLEVEL}</td>
      <td>${e.DPMATNR}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />