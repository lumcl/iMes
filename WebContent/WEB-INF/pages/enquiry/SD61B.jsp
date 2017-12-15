<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="SD61_table">
 <caption>
  計算呆料金額: RMB
  <fmt:formatNumber value="${idleAmt}" pattern="#,###" />
 </caption>
 <tr>
  <th>序</th>
  <th>倉庫</th>
  <th>料類</th>
  <th>規格</th>
  <th>料號</th>
  <th>單價</th>
  <th>耗用</th>
  <th>需求量</th>
  <th>呆料量</th>
  <th>單位</th>
  <th>庫存</th>
  <th>檢驗</th>
  <th>PO量</th>
  <th>共用</th>
  <th>L/T</th>
  <th>MOQ</th>
  <th>MRP</th>
  <th>特殊</th>
  <th>替代</th>
  <th>主輔</th>
  <th>組</th>
 </tr>
 <c:forEach var="e" items="${list}">
  <tr>
   <td class="dec6"><fmt:formatNumber value="${e.ROWID}" pattern="#,###.######" /></td>
   <td><input type="text" name="CWERKS_${e.ROWID}" value="${e.CWERKS}" size="5" readonly="readonly" tabindex="99"></td>
   <td>${e.CMATKL}</td>
   <td>${e.CMAKTX}</td>
   <td><input type="text" name="CMATNR_${e.ROWID}" value="${e.CMATNR}" size="18" readonly="readonly" tabindex="99"></td>
   <td class="dec6"><fmt:formatNumber value="${e.MATPR}" pattern="#,###.######" /></td>
   <td class="dec6"><fmt:formatNumber value="${e.DUSAGE}" pattern="#,###.######" /></td>
   <td class="dec4"><fmt:formatNumber value="${e.REQTY}" pattern="#,###.###" /></td>
   <td class="dec4"><input type="text" name="IDEQTY_${e.ROWID}" value="<fmt:formatNumber value="${e.IDEQTY}" pattern="#" />" size="8" class="num"></td>
   <td>${e.DUOM}</td>
   <td class="dec4"><fmt:formatNumber value="${e.LABST}" pattern="#,###.###" /></td>
   <td class="dec4"><fmt:formatNumber value="${e.INSME}" pattern="#,###.###" /></td>
   <td class="dec4"><fmt:formatNumber value="${e.MENGE}" pattern="#,###.###" /></td>
   <td>${e.COMMON}</td>
   <td class="dec4"><fmt:formatNumber value="${e.PLIFZ}" pattern="#,###.###" /></td>
   <td class="dec4"><fmt:formatNumber value="${e.BSTRF}" pattern="#,###.###" /></td>
   <td>${e.DISGR}</td>
   <td>${e.CSOBSL}</td>
   <td>${e.ALPOS}</td>
   <td class="dec4"><fmt:formatNumber value="${e.EWAHR}" pattern="#,###.###" /></td>
   <td>${e.ALPGR}</td>
  </tr>
 </c:forEach>
</table>