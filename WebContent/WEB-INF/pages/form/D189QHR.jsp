<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D189QHR.js"></script>
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
  <form action="/iMes/D189/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D189.GSDM}">
   <input type="hidden" name="BDDM" value="${D189.BDDM}">
   <input type="hidden" name="BDBH" value="${D189.BDBH}">
   <input type="hidden" name="BDTX" value="${D189.CGYY}">
  </form>
 <ul>
  <li><a href="#tab-1">Heavy Requisition<br />重工申請單</a></li>
 </ul>
 
 <div id="tab-1">
  <form action="/iMes/D189/GLYQH" method="post" id="D189QHFRM">
   <input type="hidden" name="GSDM" value="${D189.GSDM}">
   <input type="hidden" name="BDDM" value="${D189.BDDM}">
   <input type="hidden" name="BDBH" value="${D189.BDBH}">
   <input type="hidden" name="BDTX" value="${D189.CGYY}">


   <table style="width:1150px;">
    <caption>Heavy documents<br />重工單據</caption>
    <tbody>
     <tr>
      <th>Company Code <br />公司代碼</th>
      <td>${D189.GSDM}</td>
      <th>Stock warehouse Do<br />库存仓别</th>
      <td>${D189.KCCB}</td>
      <th>Customer Number<br />客户编号</th>
      <td>${D189.KHBH}</td>
     </tr>
     <tr>
      <th>Design No.<br />设计编号</th>
      <td>${D189.SJBH}</td>
      <th>The home department<br />归属部門</th>
      <td>${D189.BMMC}</td>
      <th>Heavy quantity<br />重工数量</th>
      <td>${D189.CQTY}</td>
     </tr>
     <tr>
      <th>Ship date<br />出货日期</th>
      <td>${D189.CHSJ}</td>
      <th>Form Number<br />表單編號</th>
      <td>${D189.BDBH}</td> 
      <th>Forms attached file<br />表單附檔</th>
      <c:if test="${D189.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${D189.BDFD}">下載</a></td>
      </c:if>
      <c:if test="${D189.BDFD.length()== null ||D189.BDFD.length()==0 }">
       <td></td>
      </c:if>
     </tr>
     <tr>     
      <th>Form date<br />表單日期</th>
      <td>${D189.BDRQ}</td>
      <th>Heavy briefing<br />重工說明會</th>
      <td colspan="3">
      <c:choose>
        <c:when test="${D189.SMH eq '1'}"> 
	      <input type="radio" name="SMH" value="1" disabled="disabled" checked>Not required 不需要
	      <input type="radio" name="SMH" value="0" disabled="disabled">Need 需要
	    </c:when>
        <c:when test="${D189.SMH eq '0'}"> 
	      <input type="radio" name="SMH" value="1" disabled="disabled">Not required 不需要
	      <input type="radio" name="SMH" value="0" disabled="disabled" checked>Need 需要
	    </c:when>
	    <c:otherwise>
          <input type="radio" name="SMH" value="1" disabled="disabled">Not required 不需要
          <input type="radio" name="SMH" value="0" disabled="disabled" checked>Need 需要
	    </c:otherwise>
      </c:choose>
      </td>
     </tr>
     <tr>
      <th>Heavy Reason Description<br />重工原因说明</th>
      <td colspan="6"><textarea name="CGYY" id="CGYY" rows="15" cols="150" readonly ="readonly">${D189.CGYY}</textarea></td>
     </tr>
     <tr>
      <th>Heavy handling instructions<br />重工处理方式说明</th>
      <td colspan="6"><textarea name="CGFS" id="CGFS" rows="15" cols="150" readonly ="readonly">${D189.CGFS}</textarea></td>
     </tr>
    </tbody>
   </table>
   

<!--签核流程部分 start-->

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

<!--签核流程部分 end-->


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


  </form>


  <form action="/iMes/FileUploader" method="post" enctype="multipart/form-data" id="SBFILEUPLOADER">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${D189.GSDM}">
   <input type="hidden" name="BDDM" value="${D189.BDDM}">
   <input type="hidden" name="BDBH" value="${D189.BDBH}">
   <input type="hidden" name="BZDM" value="300" />
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
   <div id="icon">
     
    <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
    </button>
    
   </div>

</div> 

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />