<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />



<div id="tabs">
 <ul>
  <li><a href="#tab-1">生產訂單查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="D272" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
   </div>
   <input type="hidden" name="action" value="SelectMO">
   <table>
    <tr>
     <th></th>
     <th>生產訂單</th>
     <th>機種編號</th>
     <th>工廠</th>
     <th>上線日期</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="STRAUFNR" value="${param.STRAUFNR }" onkeyup="value=value.replace(/[^\d]/g, '')"></td>
     <td><input type="text" name="STRMATNR" value="${param.STRMATNR }" size="25"></td>
     <td><input type="text" name="STRWERKS" value="${param.STRWERKS }" size="5"></td>
     <td><input type="text" name="STRGSTRI" value="${param.STRGSTRI }"></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="ENDAUFNR" value="${param.ENDAUFNR }" onkeyup="value=value.replace(/[^\d]/g, '')"></td>
     <td><input type="text" name="ENDMATNR" value="${param.ENDMATNR }" size="25"></td>
     <td><input type="text" name="ENDWERKS" value="${param.ENDWERKS }" size="5"></td>
     <td><input type="text" name="ENDGSTRI" value="${param.ENDGSTRI }"></td>
    </tr>
   </table>
  </form>

  <table>
   <thead>
    <tr>
     <th></th>
     <th>生產訂單</th>
     <th>上線日期</th>
     <th>訂單數量</th>
     <th>入庫數量</th>
     <th>機種編號</th>
     <th>工廠</th>
     <th>機種內文</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach var="e" items="${list}">
     <tr>
      <td><c:if test="${e.PSMNG != e.WEMNG }">
        <button type="button" onclick="location.href='D272?action=New&AUFNR=${e.AUFNR}'">
         <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="" />
        </button>
       </c:if></td>
      <td>${e.AUFNR}</td>
      <td>${e.GSTRI}</td>
      <td class="int"><fmt:formatNumber value="${e.PSMNG}" pattern="#,###" /></td>
      <td class="int"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###" /></td>
      <td>${e.MATNR}</td>
      <td>${e.WERKS}</td>
      <td>${e.MAKTX}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </div>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />