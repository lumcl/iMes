<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table>
 <thead>
  <tr>
   <th>供應商</th>
   <th style="width: 120px">名稱</th>
   <th>組織</th>
   <th>工廠</th>
   <th>生效</th>
   <th>截止</th>
   <th>RMB單價</th>
   <th>原幣別</th>
   <th>原報價</th>
   <th>資訊記錄</th>
   <th>差異</th>
   <th>差異%</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach items="${list}" var="e">
   <tr <c:if test="${e.VALID=='X'}"> style="background-color: #ebf1de; font-weight: bold;"</c:if>>
    <td>${e.LIFNR}</td>
    <td style="width: 120px">${e.LIFNM}</td>
    <td>${e.EKORG}</td>
    <td>${e.WERKS}</td>
    <td>${e.DATAB}</td>
    <td>${e.DATBI}</td>
    <td class="dec6"><fmt:formatNumber value="${e.UPR}" pattern="#,##0.000000" /></td>
    <td>${e.KONWA}</td>
    <td class="dec6"><fmt:formatNumber value="${e.UPF}" pattern="#,##0.000000" /></td>
    <td>${e.INFNR}</td>
    <td class="dec6"><fmt:formatNumber value="${e.DIF}" pattern="#,##0.000000" /></td>
    <td class="dec6"><fmt:formatNumber value="${e.DIFPT}" pattern="#,##0.00" />%</td>
   </tr>
  </c:forEach>
 </tbody>
</table>
