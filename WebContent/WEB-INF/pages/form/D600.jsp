<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D600.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">試產单</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/D600/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
    <button id="create" type="button" onclick="create();">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="建立試產单" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司</th>
     <th>單號</th>
     <th>日期</th>
     <th>類型</th>
     <th>機種</th>
     <th>申請人</th>
     <th>結果</th>
     <th>狀態</th>
     <th>打印狀態</th>
     <th>打印人</th>
    </tr>
    <tr>
     <th>從</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="5"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
     <th><select name="QCLX">
        <option value="" <c:if test="${(param.QCLX != '試產NG需再試產') || (param.QCLX != '試產OK轉量產')}" >selected="selected"</c:if>></option>
        <option value="試產OK轉量產" <c:if test="${param.QCLX == '試產OK轉量產'}" >selected="selected"</c:if>>試產OK轉量產</option>
        <option value="試產NG需再試產" <c:if test="${param.QCLX == '試產NG需再試產'}" >selected="selected"</c:if>>試產NG需再試產</option>
      </select></th>
     <th><input type="text" name="QCAY" value="${param.QCAY}"></th>
     <th><input type="text" name="SQYH" value="${param.SQYH}"></th>
     <th><select name="BDJG">
        <option value="" <c:if test="${(param.BDJG == '') || (empty param.BDJG)}" >selected="selected"</c:if>>ALL</option>
        <option value="OTHER" <c:if test="${(param.BDJG != 'Y') && (param.BDJG != 'N') && (param.BDJG != '') && (!empty param.BDJG)}" >selected="selected"</c:if>>未簽核</option>
        <option value="Y" <c:if test="${param.BDJG == 'Y'}" >selected="selected"</c:if>>核准</option>
        <option value="N" <c:if test="${param.BDJG == 'N'}" >selected="selected"</c:if>>退單/作廢</option>
      </select></th>
     <th><select name="BDZT">
        <option value="" <c:if test="${param.BDZT == ''}" >selected="selected"</c:if>>ALL</option>
        <option value="OTHER" <c:if test="${(param.BDZT != '0') && (param.BDZT != 'X') && (param.BDZT != '') && (!empty param.BDZT)}" >selected="selected"</c:if>>流程未啟動</option>
        <option value="0" <c:if test="${param.BDZT == '0'}" >selected="selected"</c:if>>簽核中</option>
        <option value="X" <c:if test="${param.BDZT == 'X'}" >selected="selected"</c:if>>完成</option>
      </select></th>
     <th><select name="DYZT">
        <option value="ALL" <c:if test="${param.DYZT == 'ALL'}" >selected="selected"</c:if>>ALL</option>
        <option value="0" <c:if test="${(param.DYZT != '1') && (param.DYZT != 'ALL') && (!empty param.DYZT)}" >selected="selected"</c:if>>未打印</option>
        <option value="1" <c:if test="${param.DYZT == '1'}" >selected="selected"</c:if>>已打印</option>
      </select></th>
      <th><input type="text" name="DYYH" value="${param.DYYH}"></th>
    </tr>
    <tr>
     <th>到</th>
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
    <button type="button" onclick="$.toExcel('#D600HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
    <a href="/iMes/D600/EXPORTEXCEL?SQL=${D600sqlrst}">导出Excel</a>
   </div>
   <table id="D600HList">
    <thead>
     <tr>
      <th></th>
      <th>公司</th>
      <th>單號</th>
      <th>日期</th>
      <th>結果</th>
      <th>狀態</th>
      <th>申請人</th>
      <th>试产類型</th>
      <th>机种名称</th>
      <th>打印</th>
      <th>打印人</th>
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
       <td>核准</td>
       </c:when>
       <c:when test="${e.BDJG == 'N'}">
       <td>退單/作廢</td>
       </c:when>
	   <c:otherwise>
       <td>未簽核</td>
	   </c:otherwise>
       </c:choose>
       <c:choose>
       <c:when test="${e.BDZT == '0'}">
       <td>簽核中</td>
       </c:when>
       <c:when test="${e.BDZT == 'X'}">
       <td>完成</td>
       </c:when>
	   <c:otherwise>
       <td>流程未啟動</td>
	   </c:otherwise>
	   </c:choose>
       <td>${e.SQYH}</td>
       <td>${e.QCLX}</td>
       <td>${e.QCAY}</td>
       <c:choose>
       <c:when test="${e.DYZT == '1'}">
       <td>已经打印</td>
       </c:when>
	   <c:otherwise>
       <td>未打印</td>
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