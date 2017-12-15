<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

 <fieldset>
  <legend>訂單異動通知單 - D031</legend>
  <table>
   <tr>
    <td colspan="8" align="center"><h3>訂單異動通知單</h3></td>
   </tr>
   <tr>
    <th>公司代碼</th>
    <td>${d031h.GSDM}</td>
    <th>表單代碼</th>
    <td>${d031h.BDDM}</td>
    <th>表單編號</th>
    <td>${d031h.BDBH}</td>
    <th>表單日期</th>
    <td>${d031h.BDRQ}</td>
   </tr>
   <tr>
    <th>客戶代碼</th>
    <td>${list[0].KUNNR}</td>
    <th>客戶短名</th>
    <td>${list[0].SORTL}</td>
    <th>原因類別</th>
    <td colspan="3">${d031h.YYLB}</td>
   </tr>
   <tr>
    <th>異動說明</th>
    <td colspan="7">${d031h.YYSM}</td>
   </tr>
   <tr>
    <th>申請人</th>
    <td>${d031h.JLYH}</td>
    <th>申請時間</th>
    <td><fmt:formatDate value="${d031h.JLSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
    <th>更改人</th>
    <td>${d031h.GXYH}</td>
    <th>更新時間</th>
    <td><fmt:formatDate value="${d031h.GXSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
   </tr>

   <tr>
    <td colspan="8">
     <table>
      <c:forEach var="e" items="${list}">
       <c:if test="${e.XCLB=='A'}">
        <tr>
         <th>接單倉</th>
         <th>訂單號</th>
         <th>項號</th>
         <th>產品號碼</th>
         <th>出貨</th>
         <th>付款條件</th>
         <th colspan="2">國貿條件</th>
         <th>客戶物料</th>
         <th>單價</th>
         <th>幣別</th>
        </tr>
        <tr>
         <td>${e.WERKS}</td>
         <td>${e.VBELN}</td>
         <td>${e.POSNR}</td>
         <td>${e.MATNR}</td>
         <td>${e.VSART}</td>
         <td>${e.ZTERM}</td>
         <td>${e.INCO1}</td>
         <td>${e.INCO2}</td>
         <td>${e.KDMAT}</td>
         <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.0000" /></td>
         <td>${e.WAERK}</td>
        </tr>
        <tr>
         <td colspan="11">
          <table>
           <c:forEach var="f" items="${list}">
            <c:if test="${f.VBELN==e.VBELN && f.POSNR==e.POSNR}">
             <c:choose>
              <c:when test="${f.XCLB=='A'}">
               <c:set var="qty" value="${f.OMENG}"></c:set>
               <tr>
                <th></th>
                <th></th>
                <th>交貨數量</th>
                <th>交貨日期</th>
                <th>可用日期</th>
                <th>${f.WAERK}金額</th>
               </tr>
               <tr>
                <th></th>
                <th>目前交期</th>
                <th class="int"><fmt:formatNumber value="${f.OMENG}" pattern="#,###" /></th>
                <th>${f.EDATU}</th>
                <th>${f.MBDAT}</th>
                <th class="dec2"><fmt:formatNumber value="${f.OMENG * f.NETPR}" pattern="#,###.00" /></th>
               </tr>
              </c:when>
              <c:otherwise>
               <tr>
                <c:set var="qty" value="${qty - f.OMENG }"></c:set>
                <td>${f.XCXH - e.XCXH}</td>
                <td>異動要求</td>
                <td class="int"><fmt:formatNumber value="${f.OMENG}" pattern="#,###" /></td>
                <td>${f.EDATU}</td>
                <td>${f.MBDAT}</td>
                <td class="dec2"><fmt:formatNumber value="${f.OMENG * f.NETPR}" pattern="#,###.00" /></td>
               </tr>
              </c:otherwise>
             </c:choose>
            </c:if>
           </c:forEach>
           <tr>
            <th></th>
            <th>差異</th>
            <th class="int"><fmt:formatNumber value="${qty }" pattern="#,###" /></th>
            <th></th>
            <th></th>
            <th class="dec2"><fmt:formatNumber value="${qty * e.NETPR}" pattern="#,###.00" /></th>
           </tr>
          </table>
         </td>
        </tr>
       </c:if>

      </c:forEach>

     </table>
    </td>
   </tr>
  </table>
 </fieldset>
<jsp:include page="/WEB-INF/pages/form/_route.jsp" />
<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />