<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D400UPDATE.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
function PrintPage()
{
	var strURL = "/iMes/D400/Print?GSDM=${D400.GSDM}&BDDM=${D400.BDDM}&BDBH=${D400.BDBH}"; 
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
    var QHNR = prompt('轉呈上級主管簽核原因. Turn to the superior in charge of the check and reason', '');
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
        alert("會簽人Countersign: " + msg + "不存在, 請修正會簽人后再保存.Does not exist, please countersign after correction to save.");
      }
    });
  }
</script>
<div id="tabs">
  <form action="/iMes/D400/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D400.GSDM}">
   <input type="hidden" name="BDDM" value="${D400.BDDM}">
   <input type="hidden" name="BDBH" value="${D400.BDBH}">
   <input type="hidden" name="BDTX" value="${D400.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
  <form action="/iMes/D400/ADDQH" method="post" id="SBADDQH">
   <input type="hidden" name="GSDM" value="${D400.GSDM}">
   <input type="hidden" name="BDDM" value="${D400.BDDM}">
   <input type="hidden" name="BDBH" value="${D400.BDBH}">
   <input type="hidden" name="BDTX" value="${D400.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
 <ul>
  <li><a href="#tab-1">D400簽呈</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D400/UPDATE" method="post" id="FRM01">
   <div id="icon">
   <c:if test="${QHKS == 'N'}"> 
	   <button type="button" id="SBMFRM01">
	    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存 Save" />
	   </button>
   </c:if>
   <button type="reset" onclick="location.href='/iMes/D400'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷 Reset" />
   </button>
   
    <c:if test="${((D400.BDBH != '') && (D400.SQYH == sessionScope.uid)) && (QHKS == 'N')}">
     <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程 Start check process" />
     </button>
    </c:if>
    
   <c:if test="${((D400.BDBH != '') && ((D400.SQYH == sessionScope.uid) || (sessionScope.uid == 'FELIX.JIANG')|| (sessionScope.uid == 'LUM.CL')|| (sessionScope.uid == 'ZHENYAN.LONG') || (sessionScope.uid == 'ANNIE.SU')|| (sessionScope.uid == 'LUSSIYA.WANG'))) && (QHKS == 'Y') }">
     <button type="button" id="SBMQHBTNEE" onclick="PrintPage();">
      <img src="/iMes/stylesheet/icons/S_F_PRINT.GIF" alt="打印 Print" />
     </button>
   </c:if>
    
   <c:if test="${((D400.BDBH != '') && ((sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'FELIX.JIANG'))) && (QHKS == 'Y')}">
     <button type="button" id="SBADDQHR" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="加簽核人 Plus sign" />
     </button>
   </c:if>
    
   </div>

   <table style="width:1050px;">
    <caption>簽呈單據 D400 Form</caption>
    <tbody>
     <tr>
      <th>公司代碼<br />Company</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210" <c:if test="${D400.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${D400.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${D400.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
        <option value="L921" <c:if test="${D400.GSDM == 'L921'}">selected="selected"</c:if>>L921 日本立德</option>
        <option value="L111" <c:if test="${D400.GSDM == 'L111'}">selected="selected"</c:if>>L111 菲律宾立德</option>
      </select></td>
      <th>簽呈類型<br />DocType</th>
      <td><select name="QCLX">
        <option value="人事" <c:if test="${D400.QCLX == '人事'}">selected="selected"</c:if>>人事HR</option>
        <option value="獎懲" <c:if test="${D400.QCLX == '獎懲'}">selected="selected"</c:if>>獎懲Reward and punishment</option>
        <option value="規章" <c:if test="${D400.QCLX == '規章'}">selected="selected"</c:if>>規章Regulations</option>
        <option value="異常" <c:if test="${D400.QCLX == '異常'}">selected="selected"</c:if>>異常Abnormal</option>
        <option value="一般" <c:if test="${D400.QCLX == '一般'}">selected="selected"</c:if>>一般Commonly</option>
      </select></td>
      <th colspan="2">文件屬性<br />DocAttributes</th>
      <td><select name="WJSX">
       <option value="普通件" <c:if test="${D400.WJSX == '普通件'}">selected="selected"</c:if>>普通件General</option>
       <option value="急件" <c:if test="${D400.WJSX == '急件'}">selected="selected"</c:if>>急件Dispatch</option>
       </select></td>
      <th>機密<br />Confidential</th>
      <td><select name="WJJM">
       <option value="非機密" <c:if test="${D400.WJJM == '非機密'}">selected="selected"</c:if>>非機密Non Confidential</option>
       <option value="機密" <c:if test="${D400.WJJM == '機密'}">selected="selected"</c:if>>機密Confidential</option>
       </select></td>
     </tr>
     <tr>
      <th>部門代碼<br />Dept.</th>
      <td><input type="text" name="BMMC" id="BMMC" value="${D400.BMMC}"></td>
      <th>部門名稱<br />Dept. Name</th>
      <td><input type="text" name="BMMC1" id="BMMC1" value=""></td>
      <th colspan="2">表單編號<br />DocNumber</th>
      <td><input type="text" name="BDBH" id="BDBH" value="${D400.BDBH}" readonly="readonly"></td>
      <th>表單日期<br />DocDate</th>
      <td><input type="text" name="BDRQ" id="BDRQ" value="${D400.BDRQ}" readonly="readonly"></td>
     </tr>
    <tr>
     <th>案&nbsp;&nbsp;&nbsp;&nbsp;由<br />Subject</th>
     <td colspan="4"><input type="text" name="QCAY" id="QCAY" style="width:500px;" value="${D400.QCAY}"/></td>
     <th>申&nbsp;請&nbsp;人<br />Requester</th>
     <td><input type="text" name="SQYH" id="SQYH" value="${D400.SQYH}"/></td>
     <th>表單附檔<br />Attached</th>
     <c:if test="${D400.BDFD.length() > 0}">
      <td><a href="/iMes/FileDownloader?filePath=${D400.BDFD}">下載 Download</a></td>
     </c:if>
     <c:if test="${D400.BDFD.length()== null ||D400.BDFD.length()==0 }">
      <td></td>
     </c:if>
    </tr>
    <tr>
     <th>簽呈內容<br />DocContent</th>
     <td colspan="8"><textarea name="QCNR" id="QCNR" rows="150" cols="1050" >${D400.QCNR}</textarea> 
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
<c:if test="${D400.BDFD.length()== null ||D400.BDFD.length()==0 }">
   <form action="/iMes/FileUploader?action=AttachmentA" method="post" enctype="multipart/form-data">
    <input type="hidden" name="GSDM" value="${D400.GSDM}">
    <input type="hidden" name="BDDM" value="${D400.BDDM}">
    <input type="hidden" name="BDBH" value="${D400.BDBH}">
    <br />
    <table>
     <tr>
      <th colspan="2" align="center">文件上傳  File upload</th>
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
<caption>簽核記錄  Check record</caption>
 <thead>
  <tr>
   <th>序號  <br/>No.</th>
   <th></th>
   <th>簽核人<br />Nuclear person</th>
   <th>通知 Notice<br />核准時間 Approval time
   </th>
   <th>結果<br/>Results</th>
   <th>文件<br/>File</th>
   <th>內容<br/>Content</th>
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
       <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="轉簽到上一級主管 Turn on the first level supervisor" />
      </button>
     </td>
    </c:if>
    <c:if test="${e.QHZT!='2'}">
     <td>${e.QHJG}</td>
    </c:if>

    <c:if test="${e.QHFD.length() > 0}">
     <td><a href="/iMes/FileDownloader?filePath=${e.QHFD}">下載 Download</a></td>
    </c:if>
    <c:if test="${e.QHFD.length()== null}">
     <td></td>
    </c:if>



    <td style="white-space: normal; width:600px;"><div>${e.QHNR}</div></td>
   </tr>
  </c:forEach>
 </tbody>
</table>

<c:forEach var="e" items="${BDLCS}">
 <c:if test="${(e.QHLX=='A') && (e.QHJG == 'N')}">
 	<c:set var="IS_QHLX_OVER" scope="page" value="N"/>
 </c:if>
 <c:if test="${(e.QHLX=='I') && (e.QHZT=='2') && (e.YSYH==sessionScope.uid)}">
 	<c:set var="IS_QHLX_ZX" scope="page" value="Y"/>
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
     <th colspan="2" align="center">文件上傳 <br/>File upload</th>
    </tr>
    <tr>
     <td><input type="file" name="file" style="font-size: large;" /></td>
     <td><button type="button" onclick="this.disabled=true;this.form.submit();">
       <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
      </button></td>
    </tr>
   </table>
  </form>


  <form action="/iMes/D400" method="post" name="QianHe" id="Qianhe">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${e.GSDM}" />
   <input type="hidden" name="BDDM" value="${e.BDDM}" />
   <input type="hidden" name="BDBH" value="${e.BDBH}" />
   <input type="hidden" name="BZDM" value="${e.BZDM}" />

   <table>
    <tbody>
     <tr>
      <th>簽核人<br/>Nuclear person</th>
      <td colspan="2">${e.YSYH}</td>
     </tr>
     <c:if test="${pageScope.IS_QHLX_OVER != 'N'}">
	    <tr>
	      <th>會簽 <br/>Countersign</th>
	      <td colspan="2"><input type="text" id="HQYH" name="HQYH" size="80" style="width: 550px" onkeydown="if(event.keyCode=='13'){return false}"></td>
	    </tr>
     </c:if>
     <c:if test="${pageScope.IS_QHLX_OVER == 'N'}">	    
	      <input type="hidden" id="HQYH" name="HQYH" value="">
     </c:if>
     <tr>
      <th>内容<br/>Content</th>
      <td colspan="2"><textarea rows="5" cols="70" name="QHNR">OK</textarea></td>
     </tr>
     <tr>
      <th>结果<br/>Result</th>
      <td>${sessionScope.uid} &nbsp;: &nbsp;<select name="QHJG">
      <c:if test="${pageScope.IS_QHLX_ZX == 'Y'}">
        <option value="Y" selected="selected">知悉 Know</option>
      </c:if>
      <c:if test="${pageScope.IS_QHLX_ZX != 'Y'}">
        <option value="Y" selected="selected">同意 Agreed</option>
        <option value="N">不同意 Disagree</option>
      </c:if>
      </select> &nbsp;&nbsp; <c:if test="${(pageScope.IS_QHLX_OVER != 'N') && (pageScope.IS_QHLX_ZX != 'Y')}">重簽  &nbsp;Re-sign<input type="checkbox" name="YHCQ"></c:if>
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