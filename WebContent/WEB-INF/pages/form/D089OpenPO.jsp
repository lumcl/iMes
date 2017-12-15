<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table>
 <thead>
  <tr>
   <th>PO</th>
   <th>Line</th>
   <th>Plant</th>
   <th>POQty</th>
   <th>RcvQty</th>
   <th>OpnQty</th>
   <th>Supplier</th>
   <th>Name</th>
   <th>CrtDate</th>
   <th>ShpDate</th>
   <th>Cur</th>
   <th>UntPrc</th>
   <th>RMBPrc</th>
   <th>Diff</th>
   <th>Diff%</th>

  </tr>
 </thead>
 <tbody>
  <c:forEach items="${list}" var="e">
   <tr bgcolor="${e.BGCOLOR}">
    <td>${e.EBELN}</td>
    <td>${e.EBELP}</td>
    <td>${e.WERKS}</td>
    <td class="dec6"><fmt:formatNumber value="${e.MENGE}" pattern="#,###.######" /></td>
    <td class="dec6"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###.######" /></td>
    <td class="dec6"><fmt:formatNumber value="${e.REMNG}" pattern="#,###.######" /></td>
    <td>${e.LIFNR}</td>
    <td>${e.SORTL}</td>
    <td>${e.AEDAT}</td>
    <td>${e.EINDT}</td>
    <td>${e.WAERS}</td>
    <td class="dec6"><fmt:formatNumber value="${e.NETPR}" pattern="#,##0.000000" /></td>
    <td class="dec6"><fmt:formatNumber value="${e.UPR}" pattern="#,##0.000000" /></td>
    <td class="dec6"><fmt:formatNumber value="${e.DIF}" pattern="#,##0.000000" /></td>
    <td class="dec6"><fmt:formatNumber value="${e.DIFPT}" pattern="#,##0.00" />%</td>
   </tr>
  </c:forEach>
 </tbody>
</table>
