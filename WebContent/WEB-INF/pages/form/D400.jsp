<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D400.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">D400 签呈</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/D400/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢  Query" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原 reset" />
    </button>
    <c:if test="${kz_user == 'Y'}">
    <button id="create" type="button" onclick="create();">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="建立签呈 Created" />
    </button>
    </c:if>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司<br />Company</th>
     <th>單號<br />DocNumber</th>
     <th>日期<br />DocDate</th>
     <th>類型<br />DocType</th>
     <th>申請人<br />Requester</th>
     <th>結果<br />Result</th>
     <th>狀態<br />Status</th>
     <th>打印狀態<br />PrintStatus</th>
     <th>打印人<br />PrintPerson</th>
    </tr>
    <tr>
     <th>從<br />from</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="5"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
     <th><select name="QCLX">
        <option value="" <c:if test="${(param.QCLX != '人事') || (param.QCLX != '獎懲')|| (param.QCLX != '規章')|| (param.QCLX != '異常')|| (param.QCLX != '一般')}" >selected="selected"</c:if>></option>
        <option value="人事" <c:if test="${param.QCLX == '人事'}" >selected="selected"</c:if>>人事HR</option>
        <option value="獎懲" <c:if test="${param.QCLX == '獎懲'}" >selected="selected"</c:if>>獎懲Reward and punishment</option>
        <option value="規章" <c:if test="${param.QCLX == '規章'}" >selected="selected"</c:if>>規章Regulations</option>
        <option value="異常" <c:if test="${param.QCLX == '異常'}" >selected="selected"</c:if>>異常Abnormal</option>
        <option value="一般" <c:if test="${param.QCLX == '一般'}" >selected="selected"</c:if>>一般Commonly</option>
      </select></th>
     <th><input type="text" name="SQYH" value="${param.SQYH}"></th>
     <th><select name="BDJG">
        <option value="" <c:if test="${(param.BDJG == '') || (empty param.BDJG)}" >selected="selected"</c:if>>ALL</option>
        <option value="OTHER" <c:if test="${(param.BDJG != 'Y') && (param.BDJG != 'N') && (param.BDJG != '') && (!empty param.BDJG)}" >selected="selected"</c:if>>未簽核Not signed</option>
        <option value="Y" <c:if test="${param.BDJG == 'Y'}" >selected="selected"</c:if>>核准Approved</option>
        <option value="N" <c:if test="${param.BDJG == 'N'}" >selected="selected"</c:if>>退單Cancel/作廢Invalid</option>
      </select></th>
     <th><select name="BDZT">
        <option value="" <c:if test="${param.BDZT == ''}" >selected="selected"</c:if>>ALL</option>
        <option value="OTHER" <c:if test="${(param.BDZT != '0') && (param.BDZT != 'X') && (param.BDZT != '') && (!empty param.BDZT)}" >selected="selected"</c:if>>流程未啟動Process not started</option>
        <option value="0" <c:if test="${param.BDZT == '0'}" >selected="selected"</c:if>>簽核中Checking</option>
        <option value="X" <c:if test="${param.BDZT == 'X'}" >selected="selected"</c:if>>完成Complete</option>
      </select></th>
     <th><select name="DYZT">
        <option value="ALL" <c:if test="${param.DYZT == 'ALL'}" >selected="selected"</c:if>>ALL</option>
        <option value="0" <c:if test="${(param.DYZT != '1') && (param.DYZT != 'ALL') && (!empty param.DYZT)}" >selected="selected"</c:if>>未打印Not print</option>
        <option value="1" <c:if test="${param.DYZT == '1'}" >selected="selected"</c:if>>已打印Printed</option>
      </select></th>
      <th><input type="text" name="DYYH" value="${param.DYYH}"></th>
    </tr>
    <tr>
     <th>到<br />to</th>
     <th><input type="text" name="ENDGSDM" value="${param.ENDGSDM}" size="5"></th>
     <th><input type="text" name="ENDBDBH" value="${param.ENDBDBH}"></th>
     <th><input type="text" name="ENDBDRQ" value="${param.ENDBDRQ}" size="9"></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D400HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
    <a href="/iMes/D400/EXPORTEXCEL?SQL=${D400sqlrst}">Export Excel</a>
   </div>
   <table id="D400HList">
    <thead>
     <tr>
      <th></th>
      <th>公司<br />Company</th>
      <th>單號<br />DocNumber</th>
      <th>日期<br />DocDate</th>
      <th>結果<br />Result</th>
      <th>狀態<br />Status</th>
      <th>申請人<br />Requester</th>
      <th>簽呈類型<br />DocType</th>
      <th>部門名稱<br />Dept.</th>
      <th>文件屬性<br />DocAttributes</th>
      <th>案由<br />Subject</th>
      <th>簽呈內容<br />DocContent</th>
      <th>打印<br />PrintStatus</th>
      <th>打印人<br />Printer</th>
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
       <c:choose>
       <c:when test="${e.BDJG == 'Y'}">
       <td>核准<br />Approved</td>
       </c:when>
       <c:when test="${e.BDJG == 'N'}">
       <td>退單/作廢<br />Cancel/Invalid</td>
       </c:when>
	   <c:otherwise>
       <td>未簽核<br />Not signed</td>
	   </c:otherwise>
       </c:choose>
       <c:choose>
       <c:when test="${e.BDZT == '0'}">
       <td>簽核中<br />Checking</td>
       </c:when>
       <c:when test="${e.BDZT == 'X'}">
       <td>完成<br />Complete</td>
       </c:when>
	   <c:otherwise>
       <td>流程未啟動<br />Process not started</td>
	   </c:otherwise>
	   </c:choose>
       <td>${e.SQYH}</td>
       <td>${e.QCLX}</td>
       <td>${e.BMMC}</td>
       <td>${e.WJSX}</td>
       <td>${e.QCAY}</td>
       <td>${e.QCNR}</td>
       <c:choose>
       <c:when test="${e.DYZT == '1'}">
       <td>已经打印<br />Printed</td>
       </c:when>
	   <c:otherwise>
       <td>未打印<br />Not print</td>
	   </c:otherwise>
	   </c:choose>
       <td>${e.DYYH}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />