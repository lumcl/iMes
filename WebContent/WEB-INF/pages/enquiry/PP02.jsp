<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">PP02 - Open工單明細</a></li>
 </ul>

 <div id="tab-1">
  <form action="PP02" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#PP02_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>選項<br/>Opt</th>
     <th>工廠<br/>Plan</th>
     <th>計劃員<br/>Planner</th>
     <th>基本開始日期<br/>StrDate</th>
     <th>基本完成日期<br/>EndDate</th>
     <th>機種<br/>FinishGoods</th>
     <th>工單/計劃單<br/>Order</th>
     <th>計劃員<br/>Planner</th>
     <th>機種<br/>FinishGoods</th>
     <th>工單/計劃單<br/>Order</th>
    </tr>
    <tr>
     <th>從</th>
     <td rowspan="2">
      <input type="checkbox" name="teko_checkbox" value="X"  <c:if test="${param.teko_checkbox=='X'}"> checked="checked" </c:if>> 含完工[IncFin] 
      <br/>
      <input type="checkbox" name="aufnr_checkbox" value="X" <c:if test="${param.aufnr_checkbox=='X'}"> checked="checked" </c:if>> 工單[PrdOrd] 
     </td>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="dispo_low" size="5" value=${param.dispo_low }></td>
     <td><input type="text" name="gstrp_low" size="10" value=${param.gstrp_low }></td>
     <td><input type="text" name="gltrp_low" size="10" value=${param.gltrp_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td><input type="text" name="aufnr_low" size="14" value=${param.aufnr_low }></td>
     <td rowspan="2"><textarea cols="7" rows="4" name="dispo_textarea">${param.dispo_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="16" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="dispo_high" size="5" value=${param.dispo_high }></td>
     <td><input type="text" name="gstrp_high" size="10" value=${param.gstrp_high }></td>
     <td><input type="text" name="gltrp_high" size="10" value=${param.gltrp_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
     <td><input type="text" name="aufnr_high" size="14" value=${param.aufnr_high }></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="PP02_table">
    <thead>
     <tr>
      <th>項<br/>Seq</th>
      <th>工廠<br/>Plant</th>
      <th>工單<br/>Order</th>
      <th>機種<br/>FinishGoods</th>
      <th>類別<br/>FGGrp</th>
      <th>需求<br/>Req</th>
      <th>完成<br/>Finish</th>
      <th>剩餘<br/>Remain</th>
      <th>UM</th>
      <th>客戶名<br/>CusName</th>
      <th>基本開始<br/>StrDate</th>
      <th>基本完成<br/>EndDate</th>
      <th>最早領料<br/>IssueDt</th>
      <th>實際開始<br/>ActStr</th>
      <th>重新排程<br/>Reschd</th>
      <th>PC</th>
      <th>狀態<br/>Sts</th>
      <th>工單狀態<br/>OrdSts</th>
      <th>機種說明<br/>FGDesciption</th>
      <th>客戶號<br/>CusNbr</th>
      <th>人工<br/>Lab</th>
      <th>機器<br/>Mch</th>
      <th>剩餘人工<br/>RemLab</th>
      <th>剩餘機器<br/>RemMac</th>
      <th>核發日期<br/>AprvDt</th>
      <th>實際完成<br/>ActFin</th>
      <th>備註<br/>Remark</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr>
       <td>${e.ROWID}</td>
       <td>${e.WERKS}</td>
       <td><fmt:formatNumber value="${e.AUFNR}" pattern="#" /></td>
       <td>${e.MATNR}</td>
       <td>${e.MATKL}</td>
       <td class="dec4"><fmt:formatNumber value="${e.PSMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###.###" /></td>
       <td class="dec4" bgcolor="#cef6ce"><fmt:formatNumber value="${e.PSMNG - e.WEMNG}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.SORTL}</td>
       <td class="num"><fmt:formatNumber value="${e.GSTRP}" pattern="#" /></td>
       <td class="num"><fmt:formatNumber value="${e.GLTRP}" pattern="#" /></td>
       <td class="num"><fmt:formatNumber value="${e.AUFMD}" pattern="#" /></td>
       <td class="num"><fmt:formatNumber value="${e.GSTRI}" pattern="#" /></td>
       <td class="num"><fmt:formatNumber value="${e.UMDAT}" pattern="#" /></td>
       <td>${e.DISPO}</td>
       <td>${e.MSTAE}</td>
       <td>${e.JESTD}</td>
       <td>${e.MAKTX}</td>
       <td>${e.KUNNR}</td>
       <td class="dec2"><fmt:formatNumber value="${e.VGW01}" pattern="#,###.##" /></td>
       <td class="dec2"><fmt:formatNumber value="${e.VGW05}" pattern="#,###.##" /></td>
       <td class="dec2" bgcolor="#cef6ce"><fmt:formatNumber value="${((e.PSMNG - e.WEMNG) * e.VGW01) / e.PSMNG}" pattern="#,###.##" /></td>
       <td class="dec2" bgcolor="#cef6ce"><fmt:formatNumber value="${((e.PSMNG - e.WEMNG) * e.VGW05) / e.PSMNG}" pattern="#,###.##" /></td>
       <td class="num"><fmt:formatNumber value="${e.FTRMI}" pattern="#" /></td>
       <td class="num"><fmt:formatNumber value="${e.GLTRI}" pattern="#" /></td>
       <td>${e.TEXT1}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>

  </c:if>

 </div>


</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />