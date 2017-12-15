<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">PP06 - 生產訂單嘜頭查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="PP06" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#PP06_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>工廠<br/>Plant</th>
     <th>工單<br/>Order</th>
     <th>機種<br/>FinishGoods</th>
     <th>工單<br/>Order</th>
     <th>機種<br/>FinishGoods</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="aufnr_low" size="14" value=${param.aufnr_low }></td>
     <td><input type="text" name="matnr_low" size="20" value=${param.matnr_low }></td>
     <td rowspan="2"><textarea cols="16" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="matnr_textarea">${param.matnr_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="aufnr_high" size="14" value=${param.aufnr_high }></td>
     <td><input type="text" name="matnr_high" size="20" value=${param.matnr_high }></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="PP06_table">
    <thead>
     <tr>
      <th>序<br/>Sq</th>
      <th>工單<br/>Order</th>
      <th>工廠<br/>Plant</th>
      <th>機種<br/>FinishGoods</th>
      <th>工單數量<br/>OrdQty</th>
      <th>完成數量<br/>FinQty</th>
      <th>完成日期<br/>FinDt</th>
      <th>外箱正嘜<br/>ShpMrk</th>
      <th>外箱側嘜<br/>SideMrk</th>
      <th>客戶<br/>CusNbr</th>
      <th>短名<br/>Name</th>
      <th>計劃員<br/>Planner</th>
      <th>類別<br/>Grp</th>
      <th>單位<br/>UM</th>
      <th>機種說明<br/>FGDescription</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr ${e.COLOR}>
       <td class="dec4"><fmt:formatNumber value="${e.ROWID}" pattern="#,###.###" /></td>
       <td>${e.AUFNR}</td>
       <td>${e.WERKS}</td>
       <td>${e.MATNR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.PSMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###.###" /></td>
       <td>${e.GLTRP}</td>
       <td>${e.Z001}</td>
       <td>${e.Z002}</td>
       <td>${e.KUNNR}</td>
       <td>${e.SORTL}</td>
       <td>${e.FEVOR}</td>
       <td>${e.MATKL}</td>
       <td>${e.MEINS}</td>
       <td>${e.MAKTX}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>

  </c:if>

 </div>


</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />