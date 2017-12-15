<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table>
 <thead>
  <tr>
   <th>未限制<br/>Unrestr</th>
   <th>品質檢驗<br/>Inspec</th>
   <th>凍結庫存<br/>Blocked</th>
   <th>限制批次<br/>Restr Used</th>
   <th>凍結退貨<br/>Returns</th>
   <th>收貨凍結<br/>Block Recv</th>
   <th>保留數量<br/>Reserved</th>
   <th>銷售需求<br/>Sales Req</th>
   <th>採購訂單<br/>Pur Ord</th>
   <th>請購需求<br/>PurReq</th>
   <th>生產計劃<br/>Planned</th>
   <th>生產工單<br/>Prod Ord</th>
   <th>銷售預測<br/>Forecast</th>
  </tr>
 </thead>
 <tbody>
  <tr>
    <td class="dec4"><fmt:formatNumber value="${UNRESTRICTED_STCK}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${QUAL_INSPECTION}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${BLKD_STKC}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${RESTR_USE}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${BLKD_RETURNS}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${BLKD_GR}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${RESERVATIONS}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${SALES_REQS}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${PUR_ORDERS}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${PUR_REQ}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${PLND_ORDER}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${PROD_ORDER}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${FORECAST_REQ}" pattern="#,###.###" /></td>
  </tr>
 </tbody>
</table>

<table id="MD04B_table">
 <caption>${mrpDate} ${mrpTime }</caption>
 <thead>
  <tr>
   <th>日期<br/>Date</th>
   <th>重新排程<br/>Resched</th>
   <th>MRP元素</th>
   <!-- 
   <th>開始/核發</th>
    -->
   <th>單號<br/>Order</th>
   <th>項<br/>Line</th>
   <th>MRP要素資料<br/>Element</th>
   <th>收貨/需求數量<br/>Recv/Issue</th>
   <th>可用數量<br/>Avail</th>
   <th>工廠<br/>Plant</th>
   <th>儲位<br/>Store</th>
   <th>單號<br/>Order</th>
   <!-- 
   <th>MRP_ITEM</th>
    -->
   <th>名稱<br/>Name</th>
   <th>供應商<br/>Supplier</th>
   <th>客戶<br/>Customer</th>
   <th>例外<br/>Exception</th>
   <th>IND</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach var="e" items="${list}">
   <tr>
    <td><fmt:formatDate value="${e.DAT00}" type="both" pattern="yyyyMMdd" /></td>
    <td><fmt:formatDate value="${e.UMDAT1}" type="both" pattern="yyyyMMdd" /></td>
    <td>${e.DELB0}</td>
    <!-- 
    <td><fmt:formatDate value="${e.DAT01}" type="both" pattern="yyyyMMdd" /></td>
     -->
    <td>${e.DELNR}</td>
    <td class="int"><fmt:formatNumber value="${e.DELPS}" pattern="#" /></td>
    <td>${e.BAUGR}</td>
    <td class="dec4"><fmt:formatNumber value="${e.MNG01}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${e.ATP01}" pattern="#,###.###" /></td>
    <td>${e.PWWRK}</td>
    <td>${e.LGORT_D}</td>
    <td>${e.DEL12}</td>
    <!-- 
     <td>${e.DELET}</td>
     -->
    <td>${e.SORTL}</td>
    <td>${e.LIFNR}</td>
    <td>${e.KUNNR}</td>
    <td>${e.AUSSL}</td>
    <td>${e.DELKZ}</td>
   </tr>
  </c:forEach>
 </tbody>
</table>