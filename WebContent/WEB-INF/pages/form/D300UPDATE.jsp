<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D300UPDATE.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
function PrintPage()
{
	var strURL = "/iMes/D300/Print?GSDM=${D300.GSDM}&BDDM=${D300.BDDM}&BDBH=${D300.BDBH}"; 
	winPrint=window.open(strURL,"","left=2000,top=2000,fullscreen=3"); 
}
</script>
<script type="text/javascript">
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
      data : "action=validateUserIds&userIds=" + $('#HQYH').val()
    }).done(function(msg) {
      if (msg == "") {
        $('#Qianhe').submit();
      } else {
        alert("會簽人: " + msg + "不存在, 請修正會簽人后再保存.");
      }
    });
  }
</script>
<div id="tabs">
  <form action="/iMes/D300/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D300.GSDM}">
   <input type="hidden" name="BDDM" value="${D300.BDDM}">
   <input type="hidden" name="BDBH" value="${D300.BDBH}">
   <input type="hidden" name="BDTX" value="${D300.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
  <form action="/iMes/D300/ADDQH" method="post" id="SBADDQH">
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
  <form action="/iMes/D300/UPDATE" method="post" id="FRM01">
   <div id="icon">
   <c:if test="${QHKS == 'N'}"> 
	   <button type="button" id="SBMFRM01">
	    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
	   </button>
   </c:if>
   <button type="reset" onclick="location.href='/iMes/D300'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>
   
    <c:if test="${((D300.BDBH != '') && (D300.SQYH == sessionScope.uid)) && (QHKS == 'N')}">
     <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
     </button>
    </c:if>
    
   <c:if test="${((D300.BDBH != '') && ((D300.SQYH == sessionScope.uid) || (sessionScope.uid == 'FELIX.JIANG')|| (sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'ANNIE.SU'))) && (QHKS == 'Y') }">
     <button type="button" id="SBMQHBTNEE" onclick="PrintPage();">
      <img src="/iMes/stylesheet/icons/S_F_PRINT.GIF" alt="打印" />
     </button>
   </c:if>
    
   <c:if test="${((D300.BDBH != '') && ((D300.SQYH == sessionScope.uid) || (sessionScope.uid == 'ANNIE.SU') || (sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'FELIX.JIANG'))) && (QHKS == 'Y') }">
     <button type="button" id="SBADDQHR" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="加簽核人" />
     </button>
   </c:if>
    
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
      <td  colspan="3"><input type="checkbox" name="QCLX" value="1" <c:if test="${fn:contains(D300.QCLX, '1')}">checked</c:if>>1. 欠料
      	<input type="checkbox" name="QCLX" value="2" <c:if test="${fn:contains(D300.QCLX, '2')}">checked</c:if>>2. 品質異常
      	<input type="checkbox" name="QCLX" value="3" <c:if test="${fn:contains(D300.QCLX, '3')}">checked</c:if>>3. 材料異常
      	<input type="checkbox" name="QCLX" value="4" <c:if test="${fn:contains(D300.QCLX, '4')}">checked</c:if>>4. 試產異常
      	<input type="checkbox" name="QCLX" value="5" <c:if test="${fn:contains(D300.QCLX, '5')}">checked</c:if>>5. 樣品異常
      	<input type="checkbox" name="QCLX" value="6" <c:if test="${fn:contains(D300.QCLX, '6')}">checked</c:if>>6. 其他異常
	  </td>
      <th>MO編號</th>
      <td><input type="text" name="MOBM" id="MOBM" class="required" value="${D300.MOBM}"></td>
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
  </form>
  <c:if test="${QHKS == 'N'}">
<c:if test="${D300.BDFD.length()== null ||D300.BDFD.length()==0 }">
   <form action="/iMes/FileUploader?action=AttachmentA" method="post" enctype="multipart/form-data">
    <input type="hidden" name="GSDM" value="${D300.GSDM}">
    <input type="hidden" name="BDDM" value="${D300.BDDM}">
    <input type="hidden" name="BDBH" value="${D300.BDBH}">
    <br />
    <table>
     <tr>
      <th colspan="2" align="center">文件上傳</th>
     </tr>
     <tr>
      <td><input type="file" name="file" style="font-size: large;" /></td>
      <td>
       <button type="button" onclick="this.disabled=true;this.form.submit();">
        <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
       </button>
      </td>
     </tr>
    </table>
   </form>
</c:if>
</c:if>
  
<form name="fEscalate" id="fEscalate" action="/iMes/qh" method="post">
 <input type="hidden" name="action" id="action" value="Escalate">
 <input type="hidden" name="GSDM" id="GSDM">
 <input type="hidden" name="BDDM" id="BDDM">
 <input type="hidden" name="BDBH" id="BDBH">
 <input type="hidden" name="QHNR" id="QHNR">
 <input type="hidden" name="BZDM" id="BZDM">
</form>

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



    <td><textarea rows="4" cols="60" readonly="readonly">${e.QHNR}</textarea></td>
   </tr>
  </c:forEach>
 </tbody>
</table>

<c:forEach var="e" items="${BDLCS}">
 <c:if test="${(e.QHLX=='A') && (e.QHJG == 'N')}">
 	<c:set var="IS_QHLX_OVER" scope="page" value="N"/>
 </c:if>
 <c:if test="${e.QHLX=='Z'}">
 	<c:set var="IS_QHLX_OVER" scope="page" value="N"/>
 </c:if>
 <c:if test="${(e.QHZT=='2' && ((e.YSYH==sessionScope.uid) || (e.DLYH==sessionScope.uid)))}">

  <form action="/iMes/FileUploader" method="post" enctype="multipart/form-data">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${e.GSDM}" />
   <input type="hidden" name="BDDM" value="${e.BDDM}" />
   <input type="hidden" name="BDBH" value="${e.BDBH}" />
   <input type="hidden" name="BZDM" value="${e.BZDM}" />
   <br />

   <table>
    <tr>
     <th colspan="2" align="center">文件上傳</th>
    </tr>
    <tr>
     <td><input type="file" name="file" style="font-size: large;" /></td>
     <td><button type="button" onclick="this.disabled=true;this.form.submit();">
       <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
      </button></td>
    </tr>
   </table>
  </form>


  <form action="/iMes/D300" method="post" name="QianHe" id="Qianhe">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${e.GSDM}" />
   <input type="hidden" name="BDDM" value="${e.BDDM}" />
   <input type="hidden" name="BDBH" value="${e.BDBH}" />
   <input type="hidden" name="BZDM" value="${e.BZDM}" />

   <table>
    <tbody>
     <tr>
      <th>簽核人</th>
      <td colspan="2">${e.YSYH}</td>
     </tr>
     <c:if test="${pageScope.IS_QHLX_OVER != 'N'}">
	     <tr>
	      <th>會簽</th>
	      <td colspan="2"><input type="text" id="HQYH" name="HQYH" size="80" style="width: 550px" onkeydown="if(event.keyCode=='13'){return false;}"></td>
	     </tr>
     </c:if>
     <c:if test="${pageScope.IS_QHLX_OVER == 'N'}">	    
	      <input type="hidden" id="HQYH" name="HQYH" value="">
     </c:if>
     <tr>
      <th>内容</th>
      <td colspan="2"><textarea rows="5" cols="70" name="QHNR">OK</textarea></td>
     </tr>
     <tr>
      <th>结果</th>
      <td>${sessionScope.uid} &nbsp;: &nbsp;<select name="QHJG">
        <option value="Y" selected="selected">同意</option>
        <option value="N">不同意</option>
      </select> &nbsp;&nbsp;<c:if test="${pageScope.IS_QHLX_OVER != 'N'}"> 重簽 &nbsp;<input type="checkbox" name="YHCQ"></c:if>
      </td>
      <td align="right">
	       <button type="button" onclick="this.disabled=true;submitQianHe();">
	        <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
	       </button>
      </td>
     </tr>

    </tbody>
   </table>
  </form>
 </c:if>
</c:forEach>
 </div> 
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />