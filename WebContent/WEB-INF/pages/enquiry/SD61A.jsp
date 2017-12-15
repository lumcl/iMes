<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table>
 <tr>
  <th>客戶</th>
  <th>名稱</th>
  <th>工廠</th>
  <th>通路</th>
  <th>機種</th>
  <th>交期</th>
  <th>天數</th>
  <th>數量</th>
  <th>幣別</th>
  <th>單價</th>
  <th>機種說明</th>
 </tr>
 <c:forEach var="e" items="${list}">
  <tr>
   <td>${e.KUNNR}</td>
   <td>${e.SORTL}</td>
   <td id="WERKS">${e.WERKS}</td>
   <td id="VTWEG">${e.VTWEG}</td>
   <td id="MATNR">${e.MATNR}</td>
   <td id="EDATU">${EDATU}</td>
   <td class="dec4" id="CNDAY"><fmt:formatNumber value="${CNDAY}" pattern="#,###.###" /></td>
   <td id="KBMENG" class="dec4"><fmt:formatNumber value="${e.KBMENG}" pattern="####.###" /></td>
   <td>${e.WAERK}</td>
   <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.###" /></td>
   <td>${e.ARKTX}</td>
  </tr>
 </c:forEach>
</table>