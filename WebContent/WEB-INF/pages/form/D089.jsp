<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">D089</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/D089/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
    <button id="create" type="button" onclick="location.href='/iMes/D089/CREATE'">
     <img src="/iMes/stylesheet/icons/S_B_CREA.GIF" alt="建立報價單" />
    </button>
    <button type="button" onclick="$.toExcel('#D089_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司<br />Company
     </th>
     <th>單號<br />DocNumber
     </th>
     <th>日期<br />DocDate
     </th>
     <th>供應商<br />Supplier
     </th>
     <th>申請人<br />Requester
     </th>
     <th>結果<br />Result
     </th>
     <th>狀態<br />Status
     </th>
    </tr>
    <tr>
     <th>從[Low]</th>
     <td><input type="text" name="gsdm_low" value="${param.gsdm_low}"></td>
     <td><input type="text" name="bdbh_low" value="${param.bdbh_low}"></td>
     <td><input type="text" name="bdrq_low" value="${param.bdrq_low}"></td>
     <td><input type="text" name="lifnr_low" value="${param.lifnr_low}"></td>
     <td><input type="text" name="sqyh_low" value="${sessionScope.uid}"></td>
     <td><input type="text" name="bdjq_low" value="${param.bdjq_low}"></td>
     <td><input type="text" name="bdzt_low" value="${param.bdzt_low}"></td>
    </tr>
    <tr>
     <th>到[End]</th>
     <td><input type="text" name="gsdm_high" value="${param.gsdm_high}"></td>
     <td><input type="text" name="bdbh_high" value="${param.bdbh_high}"></td>
     <td><input type="text" name="bdrq_high" value="${param.bdrq_high}"></td>
     <td><input type="text" name="lifnr_high" value="${param.lifnr_high}"></td>
     <td><input type="text" name="sqyh_high" value="${param.sqyh_high}"></td>
     <td><input type="text" name="bdjq_high" value="${param.bdjq_high}"></td>
     <td><input type="text" name="bdzt_high" value="${param.bdzt_high}"></td>
    </tr>
   </table>
  </form>


  <c:if test="${pageContext.request.method=='POST'}">
   <table id="D089_table">
    <thead>
     <tr>
      <th></th>
      <th>公司</th>
      <th>單號</th>
      <th>日期</th>
      <th>狀態</th>
      <th>結果</th>
      <th>供應商</th>
      <th>名稱</th>
      <th>報價願意</th>
      <th>申請者</th>
      <th>簽核開始</th>
      <th>採購組</th>
      <th>工廠</th>
      <th>稅碼</th>
      <th>幣別</th>
      <th>金額</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr>
       <td><button type="button" onclick="location.href='/iMes/D089/EDIT?GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_DAIL.GIF" alt="" />
        </button></td>
       <td>${e.GSDM}</td>
       <td>${e.BDBH}</td>
       <td>${e.BDRQ}</td>
       <td>${e.BDZT}</td>
       <td>${e.BDJG}</td>
       <td>${e.LIFNR}</td>
       <td>${e.SORTL}</td>
       <td>${e.BDYY}</td>
       <td>${e.SQYH}</td>
       <td>${e.QHKS}</td>
       <td>${e.EKORG}</td>
       <td>${e.WERKS}</td>
       <td>${e.MWSKZ}</td>
       <td>${e.WAERS}</td>
       <td class="dec4"><fmt:formatNumber value="${e.BDAMT}" pattern="#,###.###" /></td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>
 </div>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />