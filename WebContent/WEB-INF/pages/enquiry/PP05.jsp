<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">PP05 - 生產訂單作業查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="PP05" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#PP05_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠<br/>Plant</th>
     <th>工單<br/>Order</th>
     <th>過帳日期<br/>PostDt</th>
     <th>機種<br/>FinishGoods</th>
     <th>工單<br/>Order</th>
     <th>機種<br/>FinishGoods</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="aufnr_low" size="14" value=${param.aufnr_low }></td>
     <td><input type="text" name="budat_low" size="10" value=${param.budat_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td rowspan="2"><textarea cols="16" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="aufnr_high" size="14" value=${param.aufnr_high }></td>
     <td><input type="text" name="budat_high" size="10" value=${param.budat_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="PP05_table">
    <thead>
     <tr>
      <th>序<br/>Sq</th>
      <th>工單<br/>Order</th>
      <th>機種<br/>FinishGoods</th>
      <th>工廠<br/>Plant</th>
      <th>工序<br/>OpNbr</th>
      <th>過帳日期<br/>PostDt</th>
      <th>生產線<br/>PrdLine</th>
      <th>數量<br/>Qty</th>
      <th>UM</th>
      <th>正常<br/>Normal</th>
      <th>A</th>
      <th>B</th>
      <th>C</th>
      <th>機器<br/>Mac</th>
      <th>總人工<br/>Lab</th>
      <th>作業說明<br/>OpDesc</th>
      <th>機種說明<br/>FGDesc</th>
      <th>建立者<br/>Creator</th>
      <th>工作中心<br/>WCtr</th>
      <th>標準人工<br/>StdLab</th>
      <th>標準機器<br/>StdMac</th>
      <th>基數<br/>Base</th>
      <th>標準工時<br/>StdHrs</th>
      <th>差異<br/>Diff</th>
      <th>效率%<br/>Effi</th>
      <th>部門歸屬<br/>Dept</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr ${e.COLOR}>
       <td class="dec4"><fmt:formatNumber value="${e.ROWID}" pattern="#,###.###" /></td>
       <td>${e.AUFNR}</td>
       <td>${e.MATNR}</td>
       <td>${e.WERKS}</td>
       <td>${e.VORNR}</td>
       <td>${e.BUDAT}</td>
       <td>${e.CARBPL}</td>
       <td class="dec4"><fmt:formatNumber value="${e.LMNGA}" pattern="#,###.###" /></td>
       <td>${e.MEINH}</td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM01}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM02}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM03}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM04}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM05}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM06}" pattern="#,###.###" /></td>
       <td>${e.LTXA1}</td>
       <td>${e.MAKTX}</td>
       <td>${e.ERNAM}</td>
       <td>${e.ARBPL}</td>
       <td class="dec4"><fmt:formatNumber value="${e.VGW01}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.VGW05}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.BMSCH}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.STDLAB}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.DIFHRS}" pattern="#,###.##" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.PERFM}" pattern="#,###.##" /></td>
       <td>${e.AFRUT}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>

  </c:if>

 </div>


</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />