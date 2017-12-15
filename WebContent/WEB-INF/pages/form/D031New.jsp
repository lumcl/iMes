<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<c:if test="${pageContext.request.method=='POST'}">
 <script type="text/javascript">
    $(document).ready(function() {
      $('.datepick').each(function() {
        $(this).datepicker({
          dateFormat : 'yymmdd',
          changeMonth : true,
          changeYear : true
        }).bind('keydown', false);
      });
    });
  </script>
<script type="text/javascript">
  $(function() {
    function split(val) {
      return val.split(/,\s*/);
    }
    function extractLast(term) {
      return split(term).pop();
    }

    $("#SHR")
    // don't navigate away from the field on tab when selecting an item
    .bind(
        "keydown",
        function(event) {
          if (event.keyCode === $.ui.keyCode.TAB
              && $(this).data("autocomplete").menu.active) {
            event.preventDefault();
          }
        }).autocomplete({
      delay : 0,
      source : function(request, response) {
        $.getJSON("/iMes/ajax", {
          term : extractLast(request.term),
          action : 'getUseridJson'
        }, response);
      },
      search : function() {
        // custom minLength
        var term = extractLast(this.value);
        if (term.length < 1) {
          return false;
        }
      },
      focus : function() {
        // prevent value inserted on focus
        return false;
      },
      select : function(event, ui) {
        var terms = split(this.value);
        // remove the current input
        terms.pop();
        // add the selected item
        terms.push(ui.item.value);
        // add placeholder to get the comma-and-space at the end
        terms.push("");
        this.value = terms.join(",");
        return false;
      }
    });
  });
  
  $(function() {
    function split(val) {
      return val.split(/,\s*/);
    }
    function extractLast(term) {
      return split(term).pop();
    }

    $("#HQR")
    // don't navigate away from the field on tab when selecting an item
    .bind(
        "keydown",
        function(event) {
          if (event.keyCode === $.ui.keyCode.TAB
              && $(this).data("autocomplete").menu.active) {
            event.preventDefault();
          }
        }).autocomplete({
      delay : 0,
      source : function(request, response) {
        $.getJSON("/iMes/ajax", {
          term : extractLast(request.term),
          action : 'getUseridJson'
        }, response);
      },
      search : function() {
        // custom minLength
        var term = extractLast(this.value);
        if (term.length < 1) {
          return false;
        }
      },
      focus : function() {
        // prevent value inserted on focus
        return false;
      },
      select : function(event, ui) {
        var terms = split(this.value);
        // remove the current input
        terms.pop();
        // add the selected item
        terms.push(ui.item.value);
        // add placeholder to get the comma-and-space at the end
        terms.push("");
        this.value = terms.join(",");
        return false;
      }
    });
  });
 

  function escalations(GSDM, BDDM, BDBH, BZDM) {
    var QHNR = prompt('轉呈上級主管簽核原因 Turn to the superior in charge of the check and reason', '');
    if (QHNR == null) {
    } else {
      //location.href("/iMes/qh?action=Escalate&GSDM="+GSDM+"&BDDM="+BDDM+"&BDBH="+BDBH+"&QHNR="+QHNR);
      $('#GSDM').val(GSDM);
      $('#BDDM').val(BDDM);
      $('#BDBH').val(BDBH);
      $('#BZDM').val(BZDM);
      $('#QHNR').val(QHNR);
      $('#fEscalate').submit();
    }
  }

  function submitQianHe() {
    jQuery.ajax({
      url : "/iMes/ajax",
      data : "action=validateUserIds&userIds=" + $('#SHR').val()
    }).done(function(msg) {
      if (msg == "") {
        $('#Qianhe').submit();
      } else {
        alert("會簽人Countersign: " + msg + "不存在, 請修正會簽人后再保存.Does not exist, please countersign after correction to save.");
      }
    });
  }
  
  $(function() {
    function split(val) {
      return val.split(/,\s*/);
    }
    function extractLast(term) {
      return split(term).pop();
    }

    $("#HQYH")
    // don't navigate away from the field on tab when selecting an item
    .bind(
        "keydown",
        function(event) {
          if (event.keyCode === $.ui.keyCode.TAB
              && $(this).data("autocomplete").menu.active) {
            event.preventDefault();
          }
        }).autocomplete({
      delay : 0,
      source : function(request, response) {
        $.getJSON("/iMes/ajax", {
          term : extractLast(request.term),
          action : 'getUseridJson'
        }, response);
      },
      search : function() {
        // custom minLength
        var term = extractLast(this.value);
        if (term.length < 1) {
          return false;
        }
      },
      focus : function() {
        // prevent value inserted on focus
        return false;
      },
      select : function(event, ui) {
        var terms = split(this.value);
        // remove the current input
        terms.pop();
        // add the selected item
        terms.push(ui.item.value);
        // add placeholder to get the comma-and-space at the end
        terms.push("");
        this.value = terms.join(",");
        return false;
      }
    });
  });
</script>

 <form action="D031" method="post" id="form2" name="form2" onsubmit="return confirm('請確認資料是否正確并需要建立《訂單異動通知單 D031》?');">
  <input type="hidden" name="action" value="Create" />
  <fieldset>
   <legend>建立異動通知單<br/>Establish transaction notice</legend>
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
   </div>
   <table>
    <c:forEach var="e" items="${list}">
     <input type="hidden" name="Vbeln_Posnr" value="${e.VBELN}_${e.POSNR}" />
     <input type="hidden" name="${e.VBELN}_${e.POSNR}" value="${e.SPLIT}" />
     <tr>
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
     <tr>
      <td>${e.SPLIT}</td>
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
     <tr>
      <td colspan="19">
       <table>
        <tr>
         <th></th>
         <th></th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <th>${i}</th>
         </c:forEach>
        </tr>
        <tr>
         <th>數量<br/>QTY</th>
         <th class="int"><fmt:formatNumber value="${e.OMENG}" pattern="#,###" /></th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <td><input type="text" name="q_${e.VBELN}_${e.POSNR}_${i}" id="q_${e.VBELN}_${e.POSNR}_${i}" size="5" /></td>
         </c:forEach>
        </tr>
        <tr>
         <th>交貨日期<br/>Delivery date</th>
         <th>${e.EDATU}</th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <td><input type="text" name="d_${e.VBELN}_${e.POSNR}_${i}" id="d_${e.VBELN}_${e.POSNR}_${i}" size="5"
           class="datepick" /></td>
         </c:forEach>
        </tr>
        <tr>
         <th>可用日期<br/>Available date</th>
         <th>${e.MBDAT}</th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <td><input type="text" name="m_${e.VBELN}_${e.POSNR}_${i}" id="m_${e.VBELN}_${e.POSNR}_${i}" size="5"
           class="datepick" /></td>
         </c:forEach>
        </tr>
       </table>
      </td>
     </tr>
    </c:forEach>
   </table>
   <table>
    <tr>
     <th>異動原因說明<br/>Explain reason</th>
     <td><textarea name="YYSM" cols="80" rows="5"></textarea></td>
    </tr>
    <tr>
     <th>異動原因類別<br/>Reason category</th>
     <td><select name="YYLB" style="width:400px">
       <option value="A. 客戶需求提前">A. 客戶需求提前Customer demand ahead of schedule</option>
       <option value="B. 客戶需求延後">B. 客戶需求延後Customer demand postponed</option>
       <option value="C. 客戶需求減少">C. 客戶需求減少Reduced customer demand</option>
       <option value="D. 客戶需求增加">D. 客戶需求增加Increased customer demand</option>
       <option value="E. 客戶需求轉開">E. 客戶需求轉開Customer needs to open</option>
       <option value="F. 物料因素延後">F. 物料因素延後Delayed material factors</option>
       <option value="G. 品質因素延後">G. 品質因素延後Delay of quality factors</option>
       <option value="H. 產能因素調整">H. 產能因素調整Adjustment of production capacity</option>
       <option value="I. 試產失敗延後">I. 試產失敗延後Failure to delivery delay</option>
       <option value="J. 安規因素調整">J. 安規因素調整Adjustment of safety factor</option>
       <option value="K. LEI自主取消避險">K. LEI自主取消避險LEI self cancellation hedge</option>
       <option value="L. 取消重開改單價">L. 取消重開改單價Cancel re opened to change the unit price</option>
       <option value="M. 業務合併出貨">M. 業務合併出貨Business combination shipping</option>
       <option value="N. 當月跨月提前">N. 當月跨月提前Month ahead of the month</option>
       <option value="O. 當月跨月延後">O. 當月跨月延後Month delay</option>
       <option value="P. 跨月延後">P. 跨月延後Months delayed</option>
     </select></td>
    </tr>
    <tr>
     <th>公司代碼<br/>Company code</th>
     <td><select name="GSDM">
       <option value="L111">L111</option>
       <option value="L100">L100</option>
       <option value="L210">L210</option>
       <option value="L300">L300</option>
       <option value="L400" selected="selected">L400</option>
     </select></td>
    </tr>
   </table>
  </fieldset>
  
  
  
  <table id="SHRTAB" style="width:1050px;">
    <caption>選擇審核人<br/>Select audit person</caption>
    <tbody>
     <tr id="ROWS">
      <td>審核人<br/>Audit person</td>
      <td><input type="text" id="SHR" name="SHR" style="width:1000px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
    </tbody>
   </table>

   <table id="HQRTAB" style="width:1050px;">
    <caption>選擇會簽人<br/>People choose to sign</caption>
    <tbody>
     <tr id="HQRROWS">
      <td>會簽人<br/>Countersign</td>
      <td><input type="text" id="HQR" name="HQR" style="width:1000px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
    </tbody>
   </table>
   
   <table id="SHRTAB" style="width:1050px;">
    <caption>選擇核準人<br/>Select approved person</caption>
    <tbody>   
     <tr>
      <td>核準人<br/>Approved person</td>
      <td><select name="HJR" id="HJR" style="width:1000px;">
      		<option value=""></option>
      	<c:forEach var="u" items="${userlist}">
      		<option value="${u }">${u}</option>
      	</c:forEach>      
      </select></td>
     </tr>
    </tbody>
   </table>
  
 </form>
</c:if>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />