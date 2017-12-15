<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D300QHR.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

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
    var QHNR = prompt('轉呈上級主管簽核原因', '');
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
        alert("會簽人: " + msg + "不存在, 請修正會簽人后再保存.");
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

<div id="tabs">
  <form action="/iMes/D300/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D300.GSDM}">
   <input type="hidden" name="BDDM" value="${D300.BDDM}">
   <input type="hidden" name="BDBH" value="${D300.BDBH}">
   <input type="hidden" name="BDTX" value="${D300.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
 <ul>
  <li><a href="#tab-1">簽呈</a></li>
 </ul>
 
 <div id="tab-1">
  <form action="/iMes/D300/GLYQH" method="post" id="D300QHFRM">
   <input type="hidden" name="GSDM" value="${D300.GSDM}">
   <input type="hidden" name="BDDM" value="${D300.BDDM}">
   <input type="hidden" name="BDBH" value="${D300.BDBH}">
   <input type="hidden" name="BDTX" value="${D300.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
   <div id="icon">
     
    <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
    </button>
    
   </div>


   <table style="width:1050px;">
    <caption>簽呈單據</caption>
    <tbody>
     <tr>
      <th>公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210" <c:if test="${D300.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${D300.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${D300.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
        <option value="L921" <c:if test="${D300.GSDM == 'L921'}">selected="selected"</c:if>>L921 日本立德</option>
      </select></td>
      <th>派工類型</th>
      <td  colspan="5"><input type="checkbox" name="QCLX" value="1" <c:if test="${fn:contains(D300.QCLX, '1')}">checked</c:if>>1. 欠料
      	<input type="checkbox" name="QCLX" value="2" <c:if test="${fn:contains(D300.QCLX, '2')}">checked</c:if>>2. 品質異常
      	<input type="checkbox" name="QCLX" value="3" <c:if test="${fn:contains(D300.QCLX, '3')}">checked</c:if>>3. 材料異常
      	<input type="checkbox" name="QCLX" value="4" <c:if test="${fn:contains(D300.QCLX, '4')}">checked</c:if>>4. 試產異常
      	<input type="checkbox" name="QCLX" value="5" <c:if test="${fn:contains(D300.QCLX, '5')}">checked</c:if>>5. 樣品異常
      	<input type="checkbox" name="QCLX" value="6" <c:if test="${fn:contains(D300.QCLX, '6')}">checked</c:if>>6. 其他異常
	  </td>      
     </tr>
     <tr>
      <th>生產單位代碼</th>
      <td><input type="text" name="BMMC" id="BMMC" class="required" value="${D300.BMMC}"></td>
      <th>生產單位名稱</th>
      <td><input type="text" name="SCDW" id="SCDW" class="required" value="${D300.SCDW}"></td>
      <th>上線日期</th>
      <td><input type="text" name="SXRQ" id="SXRQ" class="required datepick" value="${D300.SXRQ}"/></td>
      <th>訂單數量</th>
      <td><input type="text" name="DDSL" id="DDSL" class="required" value="${D300.DDSL}"/></td>
     </tr>
     <tr>
      <th>客戶編碼</th>
      <td><input type="text" name="KHBM" id="KHBM" class="required" value="${D300.KHBM}" onchange="getSupplier()"/></td>
      <th>客戶名稱</th>
      <td><input type="text" name="KHMC" id="KHMC" class="required" value="${D300.KHMC}"/></td>
      <th>設計編號</th>
      <td><input type="text" name="SJBH" id="SJBH" class="required" value="${D300.SJBH}"/></td>
      <th>出貨日期</th>
      <td><input type="text" name="CHRQ" id="CHRQ" class="required datepick" value="${D300.CHRQ}"/></td>     
     </tr>
     <tr>
      <th>表單編號</th>
      <td><input type="text" name="BDBH" id="BDBH" value="${D300.BDBH}"/></td>
      <th>申請人</th>
      <td><input type="text" name="SQYH" id="SQYH" value="${D300.SQYH}"/></td>     
      <th>表單日期</th>
      <td><input type="text" name="KHBM" id="KHBM" class="required" value="${D300.BDRQ}"/></td>
      <th>表單附檔</th>
      <c:if test="${D300.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${D300.BDFD}">下載</a></td>
      </c:if>
      <c:if test="${D300.BDFD.length()== null ||D300.BDFD.length()==0 }">
       <td></td>
      </c:if>
     </tr>
    <tr>
     <th>異動原因</th>
     <td colspan="8"><textarea name="QCNR" id="QCNR" rows="150" cols="1050" >${D300.QCNR}</textarea> 
		<script type="text/javascript">  
        	window.onload = function(){  
            	CKEDITOR.replace('QCNR');  
        	}
    	</script></td>
    </tr>
    </tbody>
   </table>

   <table id="SHRTAB">
    <caption>選擇簽呈審核人</caption>
    <tbody>
     <tr id="ROWS">
      <td>審核人</td>
      <td><input type="text" id="SHR" name="SHR" style="width:800px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
    </tbody>
   </table>

   <table id="HQRTAB">
    <caption>選擇簽呈會簽人</caption>
    <tbody>
     <tr id="HQRROWS">
      <td>會簽人</td>
      <td><input type="text" id="HQR" name="HQR" style="width:800px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
    </tbody>
   </table>
   
   <table id="SHRTAB">
    <caption>選擇簽呈核準人</caption>
    <tbody>   
     <tr>
      <td>核準人</td>
      <td><select name="HJR" id="HJR">
      	<c:forEach var="e" items="${userlist}">
      		<option value="${e }">${e}</option>
      	</c:forEach>      
      </select></td>
     </tr>
    </tbody>
   </table>
    

<!--签核部分 start-->

<table>
<caption>簽核記錄</caption>
 <thead>
  <tr>
   <th>序號</th>
   <th></th>
   <th>簽核人</th>
   <th>通知<br />核准時間
   </th>
   <th>結果</th>
   <th>文件</th>
   <th>內容</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach var="e" items="${BDLCS}">
   <tr>
    <td>${e.BZDM}</td>
    <td>${e.QHLX}</td>
    <td>${e.YSYH}<br /> <b>${e.QHYH}</b>
    </td>
    <td><fmt:formatDate value="${e.YJSJ}" type="both" pattern="yyyyMMdd HH:mm" /> <br /> <b><fmt:formatDate value="${e.QHSJ}" type="both" pattern="yyyyMMdd HH:mm" /></b></td>

    <c:if test="${e.QHZT=='2'}">
     <td>
      <button type="button" onclick="this.disabled=true;escalations('${e.GSDM}','${e.BDDM}','${e.BDBH}','${e.BZDM}')">
       <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="轉簽到上一級主管" />
      </button>
     </td>
    </c:if>
    <c:if test="${e.QHZT!='2'}">
     <td>${e.QHJG}</td>
    </c:if>

    <c:if test="${e.QHFD.length() > 0}">
     <td><a href="/iMes/FileDownloader?filePath=${e.QHFD}">下載</a></td>
    </c:if>
    <c:if test="${e.QHFD.length()== null}">
     <td></td>
    </c:if>



    <td><textarea rows="3" cols="50" readonly="readonly">${e.QHNR}</textarea></td>
   </tr>
  </c:forEach>
 </tbody>
</table>

<!--签核部分 end-->


  </form>


  <form action="/iMes/FileUploader" method="post" enctype="multipart/form-data" id="SBFILEUPLOADER">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${D300.GSDM}">
   <input type="hidden" name="BDDM" value="${D300.BDDM}">
   <input type="hidden" name="BDBH" value="${D300.BDBH}">
   <input type="hidden" name="QHJG" value="Y">
   <input type="hidden" name="QHNR" value="">
   <input type="hidden" name="BZDM" value="300" />
  </form>
   
 </div> 

</div> 

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />