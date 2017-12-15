<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">外修單查詢</a></li>
  <li><a href="#tab-2">外修验收查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="D301" method="post">
   <input type="hidden" name="action" value="Query">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>    
    <c:if test="${kz_user == 'Y'}">
    <button type="button" onclick="location.href='/iMes/D301?action=New'">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="建立外修單" />
    </button>
    </c:if>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司<br/>Cmp</th>
     <th>單號<br/>Docno</th>
     <th>日期<br/>Date</th>
     <th>狀態<br/>Sts</th>
     <th>結果<br/>Result</th>
     <th>资产编号<br/>Assets No.</th>
     <th>资产<br/>Assets Desc.</th>
     <th>部門编号<br/>Dept No.</th>
     <th>部門<br/>Dept</th>
     <th>类型<br/>Type</th>
     <th>申請人<br/>Requester</th>
    </tr>
    <tr>
     <th>從</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="2"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
     <th><input type="text" name="STRBDZT" value="${param.STRBDZT}" size="2"></th>
     <th><input type="text" name="STRBDJG" value="${param.STRBDJG}" size="2"></th>
     <th><input type="text" name="STRZCBH" value="${param.STRZCBH}"></th>
     <th><input type="text" name="STRZCMC" value="${param.STRZCMC}"></th>
     <th><input type="text" name="STRSQBM" value="${param.STRSQBM}"></th>
     <th><input type="text" name="STRBMMC" value="${param.STRBMMC}"></th>
     <th><input type="text" name="STRZCLX" value="${param.STRZCLX}"></th>
     <th><input type="text" name="STRSQYH" value="${param.STRSQYH}" size="9"></th>
    </tr>
    <tr>
     <th>到</th>
     <th><input type="text" name="ENDGSDM" value="${param.ENDGSDM}" size="5"></th>
     <th><input type="text" name="ENDBDBH" value="${param.ENDBDBH}"></th>
     <th><input type="text" name="ENDBDRQ" value="${param.ENDBDRQ}" size="9"></th>
     <th><input type="text" name="ENDBDZT" value="${param.ENDBDZT}" size="2"></th>
     <th><input type="text" name="ENDBDJG" value="${param.ENDBDJG}" size="2"></th>
     <th><input type="text" name="ENDZCBH" value="${param.ENDZCBH}"></th>
     <th><input type="text" name="ENDZCMC" value="${param.ENDZCMC}"></th>
     <th><input type="text" name="ENDSQBM" value="${param.ENDSQBM}"></th>
     <th><input type="text" name="ENDBMMC" value="${param.ENDBMMC}"></th>
     <th><input type="text" name="ENDZCLX" value="${param.ENDZCLX}"></th>
     <th><input type="text" name="ENDSQYH" value="${param.ENDSQYH}" size="9"></th>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D301HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>
   <table id="D301HList">
    <thead>
     <tr>
      <th></th>
      <th>公司<br/>Cmp</th>
      <th>單號<br/>Docno</th>
      <th>日期<br/>Date</th>
      <th>狀態<br/>Sts</th>
      <th>結果<br/>Result</th>
      <th>资产编号</th>
      <th>资产名稱</th>
      <th>类型</th>
      <th>部門<br/>Dept</th>
      <th>部門名稱<br/>Deptname</th>
      <th>申請人<br/>Requester</th>
      <th>核准人<br/>Approver</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>
       <td><button type="button" onclick="location.href='${e.BDDM}?action=Edit&GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
        </button></td>
       <td>${e.GSDM}</td>
       <td>${e.BDBH}</td>
       <td>${e.BDRQ}</td>
       <td>${e.BDZT}</td>
       <td>${e.BDJG}</td>
       <td>${e.ZCBH}</td>
       <td>${e.ZCMC}</td>
       <td>${e.ZCLX}</td>
       <td>${e.SQBM}</td>
       <td>${e.BMMC}</td>
       <td>${e.SQYH}</td>
       <td>${e.QHYH}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

 <div id="tab-2">
  <form action="D301" method="post">
   <input type="hidden" name="action" value="Query302">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司<br/>Cmp</th>
     <th>單號<br/>Docno</th>
     <th>日期<br/>Date</th>
     <th>狀態<br/>Sts</th>
     <th>結果<br/>Result</th>
     <th>资产编号<br/>Assets No.</th>
     <th>资产<br/>Assets Desc.</th>
     <th>部門编号<br/>Dept No.</th>
     <th>部門<br/>Dept</th>
     <th>类型<br/>Type</th>
     <th>申請人<br/>Requester</th>
    </tr>
    <tr>
     <th>從</th>
     <th><input type="text" name="STRGSDM302" value="${param.STRGSDM302}" size="2"></th>
     <th><input type="text" name="STRBDBH302" value="${param.STRBDBH302}"></th>
     <th><input type="text" name="STRBDRQ302" value="${param.STRBDRQ302}" size="9"></th>
     <th><input type="text" name="STRBDZT302" value="${param.STRBDZT302}" size="2"></th>
     <th><input type="text" name="STRBDJG302" value="${param.STRBDJG302}" size="2"></th>
     <th><input type="text" name="STRZCBH302" value="${param.STRZCBH302}"></th>
     <th><input type="text" name="STRZCMC302" value="${param.STRZCMC302}"></th>
     <th><input type="text" name="STRSQBM302" value="${param.STRSQBM302}"></th>
     <th><input type="text" name="STRBMMC302" value="${param.STRBMMC302}"></th>
     <th><input type="text" name="STRZCLX302" value="${param.STRZCLX302}"></th>
     <th><input type="text" name="STRSQYH302" value="${param.STRSQYH302}" size="9"></th>
    </tr>
    <tr>
     <th>到</th>
     <th><input type="text" name="ENDGSDM302" value="${param.ENDGSDM302}" size="5"></th>
     <th><input type="text" name="ENDBDBH302" value="${param.ENDBDBH302}"></th>
     <th><input type="text" name="ENDBDRQ302" value="${param.ENDBDRQ302}" size="9"></th>
     <th><input type="text" name="ENDBDZT302" value="${param.ENDBDZT302}" size="2"></th>
     <th><input type="text" name="ENDBDJG302" value="${param.ENDBDJG302}" size="2"></th>
     <th><input type="text" name="ENDZCBH302" value="${param.ENDZCBH302}"></th>
     <th><input type="text" name="ENDZCMC302" value="${param.ENDZCMC302}"></th>
     <th><input type="text" name="ENDSQBM302" value="${param.ENDSQBM302}"></th>
     <th><input type="text" name="ENDBMMC302" value="${param.ENDBMMC302}"></th>
     <th><input type="text" name="ENDZCLX302" value="${param.ENDZCLX302}"></th>
     <th><input type="text" name="ENDSQYH302" value="${param.ENDSQYH302}" size="9"></th>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D302HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>
   <table id="D302HList">
    <thead>
     <tr>
      <th></th>
      <th>公司<br/>Cmp</th>
      <th>單號<br/>Docno</th>
      <th>日期<br/>Date</th>
      <th>狀態<br/>Sts</th>
      <th>結果<br/>Result</th>
      <th>资产编号</th>
      <th>资产名稱</th>
      <th>类型</th>
      <th>部門<br/>Dept</th>
      <th>部門名稱<br/>Deptname</th>
      <th>申請人<br/>Requester</th>
      <th>核准人<br/>Approver</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e302" items="${list302}">
      <tr>
       <td><button type="button" onclick="location.href='${e302.BDDM}?action=Edit&GSDM=${e302.GSDM}&BDDM=${e302.BDDM}&BDBH=${e302.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
        </button></td>
       <td>${e302.GSDM}</td>
       <td>${e302.BDBH}</td>
       <td>${e302.BDRQ}</td>
       <td>${e302.BDZT}</td>
       <td>${e302.BDJG}</td>
       <td>${e302.ZCBH}</td>
       <td>${e302.ZCMC}</td>
       <td>${e302.ZCLX}</td>
       <td>${e302.SQBM}</td>
       <td>${e302.BMMC}</td>
       <td>${e302.SQYH}</td>
       <td>${e302.QHYH}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />