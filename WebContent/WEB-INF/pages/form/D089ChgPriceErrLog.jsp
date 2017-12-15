<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table>
 <caption>Change PO Price Error Logs</caption>
 <thead>
  <tr>
   <th>表單編號<br/>Doc no</th>
   <th>物料編號<br/>Material</th>
   <th>信息指示<br/>ID</th>
   <th>信息號碼<br/>Number</th>
   <th>信息類別<br/>Type</th>
   <th>信息內容<br/>Message</th>
   <th>建立日期<br/>Date</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach items="${list}" var="e">
   <tr>
    <td>${e.BDBH}</td>
    <td>${e.MATNR}</td>
    <td>${e.MSGID}</td>
    <td>${e.MSGNR}</td>
    <td>${e.MSGTY}</td>
    <td>${e.MSGTX}</td>
    <td>${e.JLSJ}</td>
   </tr>
  </c:forEach>
 </tbody>
</table>