<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MM61 - 查詢材料區域最低單價</a></li>
 </ul>

 <div id="tab-1">
  <form action="MM61" method="post">
   <input type="hidden" name="action" value="MM61">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="執行" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="復原" />
    </button>
   </div>

   <table>
    <tr>
     <th>料號</th>
     <th>規格(模糊查詢)</th>
    </tr>
    <tr>
     <td><textarea cols="30" rows="4" name="MATNRS">${param.MATNRS}</textarea></td>
     <td><input type="text" name="MAKTX" value="${param.MAKTX}" size="40"></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <div id="icon">
   <button type="button" onclick="$.toExcel('#MM61List')">
    <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
   </button>
  </div>

  <table id="MM61List">
   <thead>
    <tr>
     <th>料號</th>
     <th>規格</th>
     <th>料類</th>
     <th>單位</th>
     <th>DTRMB IR</th>
     <th>DTRMB 廠家</th>
     <th>DTRMB 單價</th>
     <th>DT外幣 IR</th>
     <th>DT外幣 廠家</th>
     <th>DT外幣 單價</th>
     <th>DT計劃2</th>
     <th>DT平均</th>
     <th>TXRMB IR</th>
     <th>TXRMB 廠家</th>
     <th>TXRMB 單價</th>
     <th>TX外幣 IR</th>
     <th>TX外幣 廠家</th>
     <th>TX外幣 單價</th>
     <th>TX計劃2</th>
     <th>TX平均</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach var="e" items="${list}">
     <tr>
      <td>${e.MATNR}</td>
      <td>${e.MAKTX}</td>
      <td>${e.MATKL}</td>
      <td>${e.MEINS}</td>
      <td>${e.DTRPIR}</td>
      <td>${e.DTRPSP}</td>
      <td>${e.DTRP}</td>
      <td>${e.DTFPIR}</td>
      <td>${e.DTFPSP}</td>
      <td>${e.DTFP}</td>
      <td>${e.DTZPLP2}</td>
      <td>${e.DTVERPR}</td>
      <td>${e.TXRPIR}</td>
      <td>${e.TXRPSP}</td>
      <td>${e.TXRP}</td>
      <td>${e.TXFPIR}</td>
      <td>${e.TXFPSP}</td>
      <td>${e.TXFP}</td>
      <td>${e.TXZPLP2}</td>
      <td>${e.TXVERPR}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />