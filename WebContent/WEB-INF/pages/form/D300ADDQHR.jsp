<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D300ADDQHR.js"></script>

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
  <li><a href="#tab-1">加簽核人</a></li>
 </ul>
 
 <div id="tab-1">
  <form action="/iMes/D300/ADDQHR" method="post" id="D300QHFRM">
   <input type="hidden" name="GSDM" value="${D300.GSDM}">
   <input type="hidden" name="BDDM" value="${D300.BDDM}">
   <input type="hidden" name="BDBH" value="${D300.BDBH}">
   <input type="hidden" name="BDTX" value="${D300.QCAY}">
   <input type="hidden" name="BDAMT" value="0">
   <div id="icon">
     
    <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="加簽核人" />
    </button>
    
   </div>

   <table id="SHRTAB" style="width:1050px;">
    <caption>選擇簽呈審核人</caption>
    <tbody>
     <tr id="ROWS">
      <td>審核人</td>
      <td><input type="text" id="SHR" name="SHR" style="width:1000px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
    </tbody>
   </table>

   <table id="HQRTAB" style="width:1050px;">
    <caption>選擇簽呈會簽人</caption>
    <tbody>
     <tr id="HQRROWS">
      <td>會簽人</td>
      <td><input type="text" id="HQR" name="HQR" style="width:1000px;" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
    </tbody>
   </table>
   
   <table id="SHRTAB" style="width:1050px;">
    <caption>選擇簽呈核準人</caption>
    <tbody>   
     <tr>
      <td>核準人</td>
      <td><select name="HJR" id="HJR" style="width:1000px;">
      		<option value=""></option>
      	<c:forEach var="e" items="${userlist}">
      		<option value="${e }">${e}</option>
      	</c:forEach>      
      </select></td>
     </tr>
    </tbody>
   </table>
    
  </form>
  
 <form action="/iMes/FileUploader" method="post" enctype="multipart/form-data" id="SBFILEUPLOADER">
   <input type="hidden" name="action" value="QianHe" />
   <input type="hidden" name="GSDM" value="${D400.GSDM}">
   <input type="hidden" name="BDDM" value="${D400.BDDM}">
   <input type="hidden" name="BDBH" value="${D400.BDBH}">
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

</div> 

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />