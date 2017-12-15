<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">出廠單查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/D045/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
    <button type="button" onclick="location.href='/iMes/D045/CREATE'">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="建立出廠單" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司</th>
     <th>單號</th>
     <th>日期</th>
     <th>狀態</th>
     <th>結果</th>
     <th>部門</th>
     <th>申請人</th>
    </tr>
    <tr>
     <th>從</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="5"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
     <th><input type="text" name="STRBDZT" value="${param.STRBDZT}" size="2"></th>
     <th><input type="text" name="STRBDJG" value="${param.STRBDJG}" size="2"></th>
     <th><input type="text" name="STRKOSTL" value="${param.STRKOSTL}" size="10"></th>
     <th><input type="text" name="STRSQYH" value="${param.STRSQYH}" size="9"></th>
    </tr>
    <tr>
     <th>到</th>
     <th><input type="text" name="ENDGSDM" value="${param.ENDGSDM}" size="5"></th>
     <th><input type="text" name="ENDBDBH" value="${param.ENDBDBH}"></th>
     <th><input type="text" name="ENDBDRQ" value="${param.ENDBDRQ}" size="9"></th>
     <th><input type="text" name="ENDBDZT" value="${param.ENDBDZT}" size="2"></th>
     <th><input type="text" name="ENDBDJG" value="${param.ENDBDJG}" size="2"></th>
     <th><input type="text" name="ENDKOSTL" value="${param.ENDKOSTL}" size="10"></th>
     <th><input type="text" name="ENDSQYH" value="${param.ENDSQYH}" size="9"></th>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D045HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>
   <table id="D045HList">
    <thead>
     <tr>
      <th></th>
      <th>公司</th>
      <th>單號</th>
      <th>日期</th>
      <th>狀態</th>
      <th>結果</th>
      <th>出廠類型</th>
      <th>客戶</th>
      <th>交貨地點</th>
      <th>發票號碼</th>
      <th>訂單號</th>
      <th>申請人</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>      
       <td><button type="button" onclick="location.href='/iMes/${e.BDDM}/EDIT?GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
        </button></td>
       <td>${e.GSDM}</td>
       <td>${e.BDBH}</td>
       <td>${e.BDRQ}</td>
       <td>${e.BDZT}</td>
       <td>${e.BDJG}</td>
       <td>${e.BDLX}</td>
       <td>${e.CUST}</td>
       <td>${e.DELADD}</td>
       <td>${e.INVNO}</td>
       <td>${e.CONO}</td>
       <td>${e.SQYH}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />