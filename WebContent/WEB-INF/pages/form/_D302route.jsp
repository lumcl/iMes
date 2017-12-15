<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
  <c:forEach var="e302" items="${BDLCS302}">
   <tr>
    <td>${e302.BZDM}</td>
    <td>${e302.QHLX}</td>
    <td>${e302.YSYH}<br /> <b>${e302.QHYH}</b>
    </td>
    <td><fmt:formatDate value="${e302.YJSJ}" type="both" pattern="yyyyMMdd HH:mm" /> <br /> <b><fmt:formatDate value="${e302.QHSJ}" type="both" pattern="yyyyMMdd HH:mm" /></b></td>

    <c:if test="${e302.QHZT=='2'}">
     <td>
      <button type="button" onclick="this.disabled=true;escalations('${e302.GSDM}','${e302.BDDM}','${e302.BDBH}','${e302.BZDM}')">
       <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="轉簽到上一級主管" />
      </button>
     </td>
    </c:if>
    <c:if test="${e302.QHZT!='2'}">
     <td>${e302.QHJG}</td>
    </c:if>

    <c:if test="${e302.QHFD.length() > 0}">
     <td><a href="/iMes/FileDownloader?filePath=${e302.QHFD}">下載</a></td>
    </c:if>
    <c:if test="${e302.QHFD.length()== null}">
     <td></td>
    </c:if>



    <td style="white-space: normal; width:600px;"><div>${e302.QHNR}</div></td>
   </tr>
  </c:forEach>
 </tbody>
</table>

<c:forEach var="e302" items="${BDLCS302}">
 <c:if test="${(e302.QHLX=='A') && (e302.QHJG == 'N')}">
 	<c:set var="IS_QHLX_OVER" scope="page" value="N"/>
 </c:if>
 <c:if test="${(e302.QHLX=='I') && (e302.QHZT=='2') && (e302.YSYH==sessionScope.uid)}">
 	<c:set var="IS_QHLX_ZX" scope="page" value="Y"/>
 </c:if>
 <c:if test="${e302.QHLX=='Z'}">
 	<c:set var="IS_QHLX_OVER" scope="page" value="N"/>
 </c:if>
 <c:if test="${(e302.QHZT=='2' && ((e302.YSYH==sessionScope.uid) || (e302.DLYH==sessionScope.uid)))}">

  <form action="FileUploader" method="post" enctype="multipart/form-data">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${e302.GSDM}" />
   <input type="hidden" name="BDDM" value="${e302.BDDM}" />
   <input type="hidden" name="BDBH" value="${e302.BDBH}" />
   <input type="hidden" name="BZDM" value="${e302.BZDM}" />
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


  <form action="${e302.BDDM}" method="post" name="QianHe" id="Qianhe">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${e302.GSDM}" />
   <input type="hidden" name="BDDM" value="${e302.BDDM}" />
   <input type="hidden" name="BDBH" value="${e302.BDBH}" />
   <input type="hidden" name="BZDM" value="${e302.BZDM}" />

   <table>
    <tbody>
     <tr>
      <th>簽核人</th>
      <td colspan="2">${e302.YSYH}</td>
     </tr>
     <c:if test="${pageScope.IS_QHLX_OVER != 'N'}">
	     <tr>
	      <th>會簽</th>
	      <td colspan="2"><input type="text" id="HQYH" name="HQYH" size="80" style="width: 550px" onkeydown="if(event.keyCode=='13'){return false}"></td>
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
      <c:if test="${pageScope.IS_QHLX_ZX == 'Y' && e302.GSDM != L400}">
        <option value="Y" selected="selected">知悉</option>
      </c:if>
      <c:if test="${pageScope.IS_QHLX_ZX != 'Y' || e302.GSDM == L400}">
        <option value="Y" selected="selected">同意</option>
        <option value="N">不同意</option>
      </c:if>
      </select> &nbsp;&nbsp; <c:if test="${(pageScope.IS_QHLX_OVER != 'N') && (pageScope.IS_QHLX_ZX != 'Y')}">重簽 &nbsp;<input type="checkbox" name="YHCQ"></c:if>
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
