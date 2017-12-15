<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">PP01 - Open工單明細</a></li>
 </ul>

 <div id="tab-1">
  <form action="PP01" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#PP01_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠<br/>Plant</th>
     <th>計劃員<br/>Planner</th>
     <th>基本開始日期<br/>StrDt</th>
     <th>基本完成日期<br/>EndDt</th>
     <th>工作中心<br/>WrkCtr</th>
     <th>機種<br/>FinishGoods</th>
     <th>工單<br/>Order</th>
     <th>計劃員<br/>Planner</th>
     <th>工作中心<br/>WrkCtr</th>
     <th>機種<br/>FinishGoods</th>
     <th>工單<br/>Order</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="dispo_low" size="5" value=${param.dispo_low }></td>
     <td><input type="text" name="gstrp_low" size="10" value=${param.gstrp_low }></td>
     <td><input type="text" name="gltrp_low" size="10" value=${param.gltrp_low }></td>
     <td><input type="text" name="arbpl_low" size="12" value=${param.arbpl_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="aufnr_low" size="14" value=${param.aufnr_low }></td>
     <td rowspan="2"><textarea cols="7" rows="4" name="dispo_textarea">${param.dispo_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="14" rows="4" name="arbpl_textarea">${param.arbpl_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="16" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="dispo_high" size="5" value=${param.dispo_high }></td>
     <td><input type="text" name="gstrp_high" size="10" value=${param.gstrp_high }></td>
     <td><input type="text" name="gltrp_high" size="10" value=${param.gltrp_high }></td>
     <td><input type="text" name="arbpl_high" size="12" value=${param.arbpl_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="aufnr_high" size="14" value=${param.aufnr_high }></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="PP01_table">
    <thead>
     <tr>
      <th>項<br/>Sq</th>
      <th>工單<br/>Order</th>
      <th>PC</th>
      <th>工廠<br/>Plant</th>
      <th>機種<br/>FinishGoods</th>
      <th>需求<br/>ReqQty</th>
      <th>完成<br/>FinQty</th>
      <th>剩餘<br/>RemQty</th>
      <th>UM</th>
      <th>工作中心<br/>WrkCtr</th>
      <th>工作中心說明<br/>WrkDesc</th>
      <th>人工<br/>Lab</th>
      <th>機器<br/>Mac</th>
      <th>剩餘人工<br/>RemLab</th>
      <th>剩餘機器<br/>RemMac</th>
      <th>客戶名<br/>CusName</th>
      <th>基本開始<br/>StrDt</th>
      <th>基本完成<br/>EndDt</th>
      <th>實際開始<br/>ActStr</th>
      <th>實際完成<br/>ActEnd</th>
      <th>工單狀態<br/>OrdSts</th>
      <th>機種說明<br/>FGDesc</th>
      <th>備註<br/>Remark</th>
      <th>客戶號<br/>CusNbr</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr>
       <td>${e.ROWID}</td>
       <td>${e.AUFNR}</td>
       <td>${e.DISPO}</td>
       <td>${e.WERKS}</td>
       <td>${e.MATNR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.PSMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###.###" /></td>
       <td class="dec4" bgcolor="#cef6ce"><fmt:formatNumber value="${e.PSMNG - e.WEMNG}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.ARBPL}</td>
       <td>${e.LTXA1}</td>
       <td class="dec2"><fmt:formatNumber value="${e.VGW01}" pattern="#,###.##" /></td>
       <td class="dec2"><fmt:formatNumber value="${e.VGW05}" pattern="#,###.##" /></td>
       <td class="dec2" bgcolor="#cef6ce"><fmt:formatNumber value="${((e.PSMNG - e.WEMNG) * e.VGW01) / e.PSMNG}" pattern="#,###.##" /></td>
       <td class="dec2" bgcolor="#cef6ce"><fmt:formatNumber value="${((e.PSMNG - e.WEMNG) * e.VGW05) / e.PSMNG}" pattern="#,###.##" /></td>
       <td>${e.SORTL}</td>
       <td class="int"><fmt:formatNumber value="${e.GSTRP}" pattern="#" /></td>
       <td class="int"><fmt:formatNumber value="${e.GLTRP}" pattern="#" /></td>
       <td class="int"><fmt:formatNumber value="${e.GSTRI}" pattern="#" /></td>
       <td class="int"><fmt:formatNumber value="${e.GLTRI}" pattern="#" /></td>
       <td>${e.JESTD}</td>
       <td>${e.MAKTX}</td>
       <td>${e.TEXT1}</td>
       <td>${e.KUNNR}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>

  </c:if>

 </div>


</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />