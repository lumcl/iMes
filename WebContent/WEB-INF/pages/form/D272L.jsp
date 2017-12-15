<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">D272L - 元件查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="D272L" method="post">
   <input type="hidden" name="action" value="A">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#D272L_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th>溢領單</th>
     <th>工單</th>
    </tr>
    <tr>
     <td><textarea cols="25" rows="4" name="bdbh_textarea">${param.bdbh_textarea}</textarea></td>
     <td><textarea cols="16" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
    </tr>
    <tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="D272L_table">
    <thead>
     <tr>
      <th>公司</th>
      <th>表單</th>
      <th>編號</th>
      <th>工單</th>
      <th>序號</th>
      <th>元件</th>
      <th>倉庫</th>
      <th>料類</th>
      <th>規格</th>
      <th>需求</th>
      <th>UM</th>
      <th>單價</th>
      <th>金額</th>
      <th>採購確認</th>
      <th>採購組</th>
      <th>原因類別</th>
      <th>原因說明</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>
       <td>${e.GSDM}</td>
       <td>${e.BDDM}</td>
       <td>${e.BDBH}</td>
       <td>${e.AUFNR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.SQNR}" pattern="#,###.###" /></td>
       <td>${e.CMATNR}</td>
       <td>${e.CWERKS}</td>
       <td>${e.CMATKL}</td>
       <td>${e.CMAKTX}</td>
       <td class="dec4"><fmt:formatNumber value="${e.REQQ}" pattern="#,###.###" /></td>
       <td>${e.CMEINS}</td>
       <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.REQA}" pattern="#,###.###" /></td>
       <td>${e.CFMDT}</td>
       <td>${e.CEKGRP}</td>
       <td>${e.YYLB}</td>
       <td>${e.YYSM}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />