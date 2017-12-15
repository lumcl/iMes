<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">借(领)款单</a></li>
 </ul>

 <div id="tab-1">
  <form action="D402" method="post">
   <input type="hidden" name="action" value="D402HList">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
    <button type="button" onclick="location.href='/iMes/D402?Action=CREATE'">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="建立借(领)款单" />
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
     <th>工單</th>
     <th>機種</th>
     <th>申請人</th>
    </tr>
    <tr>
     <th>從</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="5"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
    </tr>
    <tr>
     <th>到</th>
     <th><input type="text" name="ENDGSDM" value="${param.ENDGSDM}" size="5"></th>
     <th><input type="text" name="ENDBDBH" value="${param.ENDBDBH}"></th>
     <th><input type="text" name="ENDBDRQ" value="${param.ENDBDRQ}" size="9"></th>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D402HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>
   <table id="D402HList">
    <thead>
     <tr>
      <th></th>
      <th>公司</th>
      <th>單號</th>
      <th>日期</th>
      <th>狀態</th>
      <th>結果</th>
      <th>原因</th>
      <th>工單號</th>
      <th>工廠</th>
      <th>開始日</th>
      <th>工單量</th>
      <th>機種</th>
      <th>機種內文</th>
      <th>部門</th>
      <th>部門名稱</th>
      <th>幣別</th>
      <th>金額</th>
      <th>自認</th>
      <th>申請人</th>
      <th>核准人</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>
       <td rowspan="2"><button type="button" onclick="location.href='${e.BDDM}?action=Edit&GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
        </button></td>
       <td rowspan="2">${e.GSDM}</td>
       <td rowspan="2">${e.BDBH}</td>
       <td>${e.BDRQ}</td>
      </tr>
      <tr>
       <td colspan="18">${e.YYSM}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />