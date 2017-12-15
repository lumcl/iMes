<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D400QHR.js"></script>
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
 
  $(function() {
    function split(val) {
      return val.split(/,\s*/);
    }
    function extractLast(term) {
      return split(term).pop();
    }

    $("#ZXR")
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
<c:forEach var="e" items="${BDLCS}">
 <c:if test="${(e.QHZT=='2' && ((e.YSYH==sessionScope.uid) || (e.DLYH==sessionScope.uid)))}">
 	<c:set var="IS_QHGLY_BZDM" scope="page" value="${e.BZDM}"/>
 </c:if>
 </c:forEach>
<div id="tabs">
  <form action="/iMes/D400/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D400.GSDM}">
   <input type="hidden" name="BDDM" value="${D400.BDDM}">
   <input type="hidden" name="BDBH" value="${D400.BDBH}">
   <input type="hidden" name="BZDM" value="${IS_QHGLY_BZDM}">
   <input type="hidden" name="BDTX" value="${D400.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
 <ul>
  <li><a href="#tab-1">簽呈</a></li>
 </ul>
 
 <div id="tab-1">
  <form action="/iMes/D400/GLYQH" method="post" id="D400QHFRM">
   <input type="hidden" name="GSDM" value="${D400.GSDM}">
   <input type="hidden" name="BDDM" value="${D400.BDDM}">
   <input type="hidden" name="BDBH" value="${D400.BDBH}">
   <input type="hidden" name="BZDM" value="${IS_QHGLY_BZDM}">
   <input type="hidden" name="BDTX" value="${D400.QCAY}">
   <!-- input type="hidden" name="QHNR" value=""-->
   <input type="hidden" name="QHJG" value="Y">
   <input type="hidden" name="BDAMT" value="0">
   <div id="icon">
     
    <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
    </button>
    
   </div>


   <table style="width: 1050px;">
    <caption>簽呈單據</caption>
    <tbody>
     <tr>
      <th>公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210" <c:if test="${D400.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${D400.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${D400.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
        <option value="L921" <c:if test="${D400.GSDM == 'L921'}">selected="selected"</c:if>>L921 日本立德</option>
        <option value="L111" <c:if test="${D400.GSDM == 'L111'}">selected="selected"</c:if>>L111 菲律宾立德</option>
      </select></td>
      <th>簽呈類型</th>
      <td><select name="QCLX">
        <option value="人事" <c:if test="${D400.QCLX == '人事'}">selected="selected"</c:if>>人事</option>
        <option value="獎懲" <c:if test="${D400.QCLX == '獎懲'}">selected="selected"</c:if>>獎懲</option>
        <option value="規章" <c:if test="${D400.QCLX == '規章'}">selected="selected"</c:if>>規章</option>
        <option value="異常" <c:if test="${D400.QCLX == '異常'}">selected="selected"</c:if>>異常</option>
        <option value="一般" <c:if test="${D400.QCLX == '一般'}">selected="selected"</c:if>>一般</option>
      </select></td>
      <th colspan="2">文件屬性</th>
      <td><select name="WJSX">
       <option value="普通件" <c:if test="${D400.WJSX == '普通件'}">selected="selected"</c:if>>普通件</option>
       <option value="急件" <c:if test="${D400.WJSX == '急件'}">selected="selected"</c:if>>急件</option>
       </select></td>
      <th>機密</th>
      <td><select name="WJJM">
       <option value="非機密" <c:if test="${D400.WJJM == '非機密'}">selected="selected"</c:if>>非機密</option>
       <option value="機密" <c:if test="${D400.WJJM == '機密'}">selected="selected"</c:if>>機密</option>
       </select></td>
     </tr>
     <tr>
      <th>部門代碼</th>
      <td><input type="text" name="BMMC" id="BMMC" value="${D400.BMMC}"></td>
      <th>部門名稱</th>
      <td><input type="text" name="BMMC1" id="BMMC1" value=""></td>
      <th colspan="2">表單編號</th>
      <td><input type="text" name="BDBH" id="BDBH" value="${D400.BDBH}" readonly="readonly"></td>
      <th>表單日期</th>
      <td><input type="text" name="BDRQ" id="BDRQ" value="${D400.BDRQ}" readonly="readonly"></td>
     </tr>
    <tr>
     <th>案&nbsp;&nbsp;&nbsp;&nbsp;由</th>
     <td colspan="4"><input type="text" name="QCAY" id="QCAY" style="width:500px;" value="${D400.QCAY}"/></td>
     <th>申&nbsp;請&nbsp;人</th>
     <td><input type="text" name="SQYH" id="SQYH" size="15" value="${D400.SQYH}"/></td>
     <th>表單附檔</th>
     <c:if test="${D400.BDFD.length() > 0}">
      <td><a href="/iMes/FileDownloader?filePath=${D400.BDFD}">下載</a></td>
     </c:if>
     <c:if test="${D400.BDFD.length()== null ||D400.BDFD.length()==0 }">
      <td></td>
     </c:if>
    </tr>
    <tr>
     <th>簽呈內容</th>
     <td colspan="8"><textarea name="QCNR" id="QCNR" rows="150" cols="1050" >${D400.QCNR}</textarea> 
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
      	<option value=""></option>
      </select></td>
     </tr>
    </tbody>
   </table>
   
   <table id="ZXRTAB">
    <caption>選擇簽呈知悉人</caption>
    <tbody>
     <tr id="ZXRROWS">
      <td>知悉人</td>
      <td><input type="text" id="ZXR" name="ZXR" style="width:800px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
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



    <td style="white-space: normal; width:600px;">${e.QHNR}</td>
   </tr>
  </c:forEach>
 </tbody>
</table>

<c:forEach var="e" items="${BDLCS}">
 <c:if test="${(e.QHZT=='2' && ((e.YSYH==sessionScope.uid) || (e.DLYH==sessionScope.uid)))}">
  
   <table>
    <tbody>
     <tr>
      <th>簽核人</th>
      <td colspan="2">${e.YSYH}</td>
     </tr>
     <tr>
      <th>内容</th>
      <td colspan="2"><textarea rows="5" cols="70" id="QHNR" name="QHNR">OK</textarea></td>
     </tr>
     <tr>
      <th>结果</th>
      <td>${sessionScope.uid} &nbsp;: &nbsp;<select name="QHJG">
        <option value="Y" selected="selected">同意</option>
        <option value="N">不同意</option>
      </select> &nbsp;&nbsp; 重簽 &nbsp;<input type="checkbox" name="YHCQ">
      </td>
      <td align="right">
      </td>
     </tr>

    </tbody>
   </table>
 </c:if>
</c:forEach>
<!--签核部分 end-->

  </form>

  
  <form action="/iMes/FileUploader" method="post" enctype="multipart/form-data" id="SBFILEUPLOADER">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${D400.GSDM}">
   <input type="hidden" name="BDDM" value="${D400.BDDM}">
   <input type="hidden" name="BDBH" value="${D400.BDBH}">
   <input type="hidden" name="BZDM" value="${IS_QHGLY_BZDM}">
   <table>
    <tr>
     <th colspan="2" align="center">文件上傳</th>
    </tr>
    <tr>
     <td><input type="file" name="file" style="font-size: large;" /></td>
     <td><button type="button" id="FILEUPLOADERBTN">
       <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
      </button></td>
    </tr>
   </table>
  </form>
  
 </div> 

</div> 

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />