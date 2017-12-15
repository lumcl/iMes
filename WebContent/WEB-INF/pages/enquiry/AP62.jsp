<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">IEB進口明細清單</a></li>
 </ul>

 <div id="tab-1">
  <form action="AP_RPT" method="post">
   <input type="hidden" name="action" value="AP62">
   <div id="icon">
    <button type="submit">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>公司代碼</th>
     <th>進口日期</th>
     <th>供應商</th>
     <th>進口號碼</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="STRBUKRS" value="${param.STRBUKRS}"></td>
     <td><input type="text" name="STRIMDAT" value="${param.STRIMDAT}"></td>
     <td><input type="text" name="STRLIFNR" value="${param.STRLIFNR}"></td>
     <td><input type="text" name="STRIMPNR" value="${param.STRIMPNR}"></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="ENDBUKRS" value="${param.ENDBUKRS}"></td>
     <td><input type="text" name="ENDIMDAT" value="${param.ENDIMDAT}"></td>
     <td><input type="text" name="ENDLIFNR" value="${param.ENDLIFNR}"></td>
     <td><input type="text" name="ENDIMPNR" value="${param.ENDIMPNR}"></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <div id="icon">
   <button type="button" onclick="$.toExcel('#AP62List')">
    <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
   </button>
  </div>

  <table id="AP62List">
   <thead>
    <tr>
     <th>進口號碼</th>
     <th>進口項目</th>
     <th>保稅範圍</th>
     <th>電子帳冊號</th>
     <th>供應商</th>
     <th>報關單號</th>
     <th>進口日期</th>
     <th>採購文件</th>
     <th>項目</th>
     <th>物料號碼</th>
     <th>物料規格</th>
     <th>料類</th>
     <th>數量</th>
     <th>單位</th>
     <th>單價</th>
     <th>幣別</th>
     <th>總價</th>
     <th>付款條件</th>
     <th>基準日</th>
     <th>日限制</th>
     <th>基準日</th>
     <th>附加月份</th>
     <th>公司名稱</th>
     <th>簡稱</th>
     <th>應登記日</th>
     <th>預計付款日</th>
     <th>付款說明</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach var="e" items="${list}">
     <tr>
      <td>${e.IMPNR}</td>
      <td>${e.IMPIM}</td>
      <td>${e.BNAREA}</td>
      <td>${e.CONNR}</td>
      <td>${e.LIFNR}</td>
      <td>${e.DLFNR}</td>
      <td>${e.IMDAT}</td>
      <td>${e.EBELN}</td>
      <td>${e.EBELP}</td>
      <td>${e.MATNR}</td>
      <td>${e.TXZ01}</td>
      <td>${e.MATKL}</td>
      <td class="dec4">${e.MENGE}</td>
      <td>${e.MEINS}</td>
      <td class="dec6">${e.NETPR}</td>
      <td>${e.WAERS}</td>
      <td class="dec2">${e.WRBTR}</td>
      <td>${e.ZTERM}</td>
      <td>${e.BASED}</td>
      <td>${e.ZTAGG}</td>
      <td>${e.ZFAEL}</td>
      <td>${e.ZMONA}</td>
      <td>${e.NAME1}</td>
      <td>${e.SORTL}</td>
      <td>${e.REGDT}</td>
      <td>${e.PAYDT}</td>
      <td>${e.TEXT1}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />