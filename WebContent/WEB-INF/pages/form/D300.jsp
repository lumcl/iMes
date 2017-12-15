<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D300.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">派工令異動申請單</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/D300/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
    <button id="create" type="button" onclick="create();">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="建立" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司</th>
     <th>單號</th>
     <th>日期</th>
     <th>類型</th>
     <th>申請人</th>
     <th>結果</th>
     <th>狀態</th>
    </tr>
    <tr>
     <th>從</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="5"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
     <th><select name="QCLX">
        <option value="" <c:if test="${(param.QCLX != '人事') || (param.QCLX != '獎懲')|| (param.QCLX != '規章')|| (param.QCLX != '異常')|| (param.QCLX != '一般')}" >selected="selected"</c:if>></option>
        <option value="人事" <c:if test="${param.QCLX == '人事'}" >selected="selected"</c:if>>人事</option>
        <option value="獎懲" <c:if test="${param.QCLX == '獎懲'}" >selected="selected"</c:if>>獎懲</option>
        <option value="規章" <c:if test="${param.QCLX == '規章'}" >selected="selected"</c:if>>規章</option>
        <option value="異常" <c:if test="${param.QCLX == '異常'}" >selected="selected"</c:if>>異常</option>
        <option value="一般" <c:if test="${param.QCLX == '一般'}" >selected="selected"</c:if>>一般</option>
      </select></th>
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
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D300HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
    <a href="/iMes/D300/EXPORTEXCEL?SQL=${D300sqlrst}">导出Excel</a>
   </div>
   <table id="D300HList">
    <thead>
     <tr>
      <th></th>
      <th>公司</th>
      <th>單號</th>
      <th>日期</th>
      <th>結果</th>
      <th>狀態</th>
      <th>申請人</th>
      <th>類型</th>
      <th>生產單位編號</th>
      <th>生產單位名稱</th>
      <th>客戶名稱</th>
      <th>設計編號</th>
      <th>訂單數量</th>
      <th>出貨日期</th>
      <th>上線日期</th>
      <th>打印</th>
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
       <td>
       <c:if test="${fn:contains(e.QCLX, '1')}">
       1. 欠料
       </c:if>
       <c:if test="${fn:contains(e.QCLX, '2')}">
       2. 品質異常
       </c:if>
       <c:if test="${fn:contains(e.QCLX, '3')}">
       3. 材料異常
       </c:if>
       <c:if test="${fn:contains(e.QCLX, '4')}">
       4. 試產異常
       </c:if>
       <c:if test="${fn:contains(e.QCLX, '5')}">
       5. 樣品異常
       </c:if>
       <c:if test="${fn:contains(e.QCLX, '6')}">
       6. 其他異常
       </c:if>
       <c:if  test="!${fn:contains(e.QCLX, '6')} || !${fn:contains(e.QCLX, '5')} || !${fn:contains(e.QCLX, '4')} || !${fn:contains(e.QCLX, '3')} || !${fn:contains(e.QCLX, '2')} || !${fn:contains(e.QCLX, '1')}">
       &nbsp;
	   </c:if>
	   </td>
       <td>${e.BMMC}</td>
       <td>${e.SCDW}</td>
       <td>${e.KHMC}</td>
       <td>${e.SJBH}</td>
       <td>${e.DDSL}</td>
       <td>${e.CHRQ}</td>
       <td>${e.SXRQ}</td>
       <c:choose>
       <c:when test="${e.DYZT == '1'}">
       <td>已打印</td>
       </c:when>
	   <c:otherwise>
       <td>未打印</td>
	   </c:otherwise>
	   </c:choose>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />