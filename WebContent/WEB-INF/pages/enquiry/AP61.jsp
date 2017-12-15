<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">AP 未結清查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="AP_RPT" method="post">
   <input type="hidden" name="action" value="AP61">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
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
     <th>過帳日期</th>
     <th>供應商</th>
     <th>工廠</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="STRBUKRS" value="${param.STRBUKRS}"></td>
     <td><input type="text" name="STRBUDAT" value="${param.STRBUDAT}"></td>
     <td><input type="text" name="STRLIFNR" value="${param.STRLIFNR}"></td>
     <td><input type="text" name="STRWERKS" value="${param.STRWERKS}"></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="ENDBUKRS" value="${param.ENDBUKRS}"></td>
     <td><input type="text" name="ENDBUDAT" value="${param.ENDBUDAT}"></td>
     <td><input type="text" name="ENDLIFNR" value="${param.ENDLIFNR}"></td>
     <td><input type="text" name="ENDWERKS" value="${param.ENDWERKS}"></td>
    </tr>
   </table>
  </form>
 </div>

 <c:if test="${pageContext.request.method=='POST'}">
  <div id="icon">
   <button type="button" onclick="$.toExcel('#AP61List')">
    <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
   </button>
  </div>

  <table id="AP61List">
   <thead>
    <tr>
     <th>採購單</th>
     <th>行號</th>
     <th>公司</th>
     <th>科目</th>
     <th>年度</th>
     <th>文件號</th>
     <th>項號</th>
     <th>參考號</th>
     <th>供應商</th>
     <th>名稱</th>
     <th>過帳日期</th>
     <th>借貸</th>
     <th>數量</th>
     <th>單位</th>
     <th>貨幣</th>
     <th>貨幣金額</th>
     <th>本金額</th>
     <th>物料號</th>
     <th>規格</th>
     <th>料類</th>
     <th>工廠</th>
     <th>單價</th>
     <th>聯繫人</th>
     <th>電話</th>
     <th>類別</th>
     <th>來源</th>
     <th>單據</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach var="e" items="${list}">
     <tr>
      <td>${e.EBELN}</td>
      <td>${e.EBELP}</td>
      <td>${e.BUKRS}</td>
      <td>${e.HKONT}</td>
      <td>${e.GJAHR}</td>
      <td>${e.BELNR}</td>
      <td>${e.BUZEI}</td>
      <td>${e.XBLNR}</td>
      <td>${e.LIFNR}</td>
      <td>${e.NAME1}</td>
      <td>${e.BUDAT}</td>
      <td>${e.SHKZG}</td>
      <c:if test="${e.MEINS == 'EA'}">
       <td class="int"><fmt:formatNumber value="${e.MENGE}" pattern="#,###" /></td>
      </c:if>
      <c:if test="${e.MEINS != 'EA'}">
       <td class="dec4"><fmt:formatNumber value="${e.MENGE}" pattern="#,##0.0000" /></td>
      </c:if>
      <td>${e.MEINS}</td>
      <td>${e.WAERS}</td>
      <td class="dec2"><fmt:formatNumber value="${e.WRBTR}" pattern="#,##0.00" /></td>
      <td class="dec2"><fmt:formatNumber value="${e.DMBTR}" pattern="#,##0.00" /></td>
      <td>${e.MATNR}</td>
      <td>${e.TXZ01}</td>
      <td>${e.MATKL}</td>
      <td>${e.WERKS}</td>
      <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,##0.0000" /></td>
      <td>${e.VERKF}</td>
      <td>${e.TELF1}</td>
      <td>${e.BLART}</td>
      <td>${e.AWTYP}</td>
      <td>${e.ACTIV}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
 </c:if>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />