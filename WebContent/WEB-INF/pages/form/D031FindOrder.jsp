<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">OPEN SO查詢  Query</a></li>
 </ul>

 <div id="tab-1">

  <form action="D031" method="post" id="form1" name="form1">
   <input type="hidden" name="action" value="FindOrder" />
   <fieldset>
    <legend>訂單異動通知單  Order change notice - D031 - D031FindOrder</legend>
    <div id="icon">
     <button type="button" onclick="this.disabled=true;this.form.submit();">
      <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
     <button type="button" onclick="history.go(-1)">
      <img src="/iMes/stylesheet/icons/S_F_BACK.GIF" alt="" />
     </button>
    </div>
    <table>
     <tr>
      <th></th>
      <th>歸屬 <br/>Belonging</th>
      <th>接單倉別<br/>Orders warehouse</th>
      <th>訂單號碼<br/>Order number</th>
      <th>客戶號碼<br/>Customer number</th>
      <th>機種號碼<br/>Product number</th>
      <th>出貨日期<br/>Delivery date</th>
      <th>可用日期<br/>Available date</th>
     </tr>
     <tr>
      <th>從 [Low]</th>
      <td><input type="text" id="strVtweg" name="strVtweg" size="2" value="${param.strVtweg }" /></td>
      <td><input type="text" id="strWerks" name="strWerks" size="4" value="${param.strWerks }" /></td>
      <td><input type="text" id="strVbeln" name="strVbeln" size="10" value="${param.strVbeln }" /></td>
      <td><input type="text" id="strKunnr" name="strKunnr" size="10" value="${param.strKunnr }" /></td>
      <td><input type="text" id="strMatnr" name="strMatnr" size="18" value="${param.strMatnr }" /></td>
      <td><input type="text" id="strEdatu" name="strEdatu" size="8" value="${param.strEdatu }" /></td>
      <td><input type="text" id="strMbdat" name="strMbdat" size="8" value="${param.strMbdat }" /></td>
     </tr>
     <tr>
      <th>到[End]</th>
      <td><input type="text" id="endVtweg" name="endVtweg" size="2" value="${param.endVtweg }" /></td>
      <td><input type="text" id="endWerks" name="endWerks" size="4" value="${param.endWerks }" /></td>
      <td><input type="text" id="endVbeln" name="endVbeln" size="10" value="${param.endVbeln }" /></td>
      <td><input type="text" id="endKunnr" name="endKunnr" size="10" value="${param.endKunnr }" /></td>
      <td><input type="text" id="endMatnr" name="endMatnr" size="18" value="${param.endMatnr }" /></td>
      <td><input type="text" id="endEdatu" name="endEdatu" size="8" value="${param.endEdatu }" /></td>
      <td><input type="text" id="endMbdat" name="endMbdat" size="8" value="${param.endMbdat }" /></td>
     </tr>
    </table>
   </fieldset>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">

   <script type="text/javascript">
        $(document).ready(function() {
          $('#form2').submit(function() {
            if ($(':checkbox:checked', this)[0]) {
              // everything's fine...
            } else {
              alert('請至少勾選一個項次  Please tick one item at least');
              return false;
            }
          });

          $('.datepick').each(function() {
            $(this).datepicker();
          });

        });
        function chk(obj) {
          var a = '#s_' + obj.value;
          if (obj.checked) {
            $(a).removeAttr('disabled');
          } else {
            $(a).attr('disabled', 'disabled');
          }
        }
      </script>

   <form action="D031" method="post" id="form2" name="form2">
    <input type="hidden" name="action" value="New" />
    <fieldset>
     <legend>銷售訂單查詢結果<br/>Sales order query results</legend>
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
       <th>分割<br/>Division</th>
       <th>歸屬<br/>Belonging</th>
       <th>倉庫<br/>Warehouse</th>
       <th>客戶<br/>Customer</th>
       <th>短名<br/>Short name</th>
       <th>訂單號<br/>Order No.</th>
       <th>項號<br/>Item No.</th>
       <th>數量<br/>QTY</th>
       <th>交貨日期<br/>Delivery date</th>
       <th>可用日期<br/>Available date</th>
       <th>產品號碼<br/>Product number</th>
       <th>出貨<br/>Shipping</th>
       <th>付款條件<br/>Terms of payment</th>
       <th colspan="2">國貿條件<br/>China World Trade Center condition</th>
       <th>客戶物料<br/>Customer material</th>
       <th>單價<br/>Unit price</th>
       <th>幣別<br/>Currency</th>
       <th>產品說明<br/>Product description</th>
      </tr>
      <c:forEach var="e" items="${list}">
       <tr>
        <td><input type="checkbox" id="Vbeln_Posnr" name="Vbeln_Posnr" value="${e.VBELN}_${e.POSNR}" onclick="chk(this)" /></td>
        <td><input type="text" id="s_${e.VBELN}_${e.POSNR}" name="s_${e.VBELN}_${e.POSNR}" size="1" value="1" disabled="disabled" /></td>
        <td>${e.VTWEG}</td>
        <td>${e.WERKS}</td>
        <td>${e.KUNNR}</td>
        <td>${e.SORTL}</td>
        <td>${e.VBELN}</td>
        <td>${e.POSNR}</td>
        <td class="int"><fmt:formatNumber value="${e.OMENG}" pattern="#,###" /></td>
        <td>${e.EDATU}</td>
        <td>${e.MBDAT}</td>
        <td>${e.MATNR}</td>
        <td>${e.VSART}</td>
        <td>${e.ZTERM}</td>
        <td>${e.INCO1}</td>
        <td>${e.INCO2}</td>
        <td>${e.KDMAT}</td>
        <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.0000" /></td>
        <td>${e.WAERK}</td>
        <td>${e.ARKTX}</td>
       </tr>
      </c:forEach>
     </table>
    </fieldset>
   </form>
  </c:if>
 </div>
</div>
<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />