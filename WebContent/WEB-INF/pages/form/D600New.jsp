<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D600.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
$(function() {
    function split(val) {
      return val.split(/,\s*/);
    }
    function extractLast(term) {
      return split(term).pop();
    }

    $("#RDYH")
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

    $("#SDYH")
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
 <ul>
  <li><a href="#tab-1">試產单</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D600/SAVE" method="post" id="FRM01">
   <div id="icon">
    
   <button type="button" onclick="this.disabled=true;this.form.submit();">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
   </button>
   <button type="reset" onclick="location.href='/iMes/D600/CREATE'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>
    
   </div>

   <table style="width: 1050px;">
    <caption>試產单單據</caption>
    <tbody>
     <tr>
      <th>公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210">L210 東莞領航</option>
        <option value="L300" selected="selected">L300 東莞立德</option>
        <option value="L400">L400 江蘇領先</option>
        <option value="L921">L921 日本立德</option>
        <option value="L111">L111 菲律宾立德</option>
      </select></td>
      <th>類型</th>
      <td><select name="QCLX">
        <option value="試產OK轉量產">試產OK轉量產</option>
        <option value="試產NG需再試產">試產NG需再試產</option>
      </select></td>
      <th>机种名称</th>
      <td colspan="3"><input type="text" name="QCAY" id="QCAY" value=""></td>
     </tr>
     <tr>
      <th>表單編號</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>      
      <th>表單日期</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>    
      <th>申&nbsp;請&nbsp;人</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <th>表單附檔</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     </tr>
     <tr>
      <th>RD人员</th>
      <td colspan="7"><input type="text" name="RDYH" id="RDYH" style="width:500px;" value=""></td>
     </tr>
     <tr>
      <th>SD人员</th>
      <td colspan="7"><input type="text" name="SDYH" id="SDYH" style="width:500px;" value=""></td>
     </tr>
    <tr>
     <th>备注</th>
     <td colspan="10"><textarea name="QCNR" id="QCNR" rows="150" cols="1050" ></textarea>
    	<script type="text/javascript">  
        	window.onload = function(){  
            	CKEDITOR.replace('QCNR');  
        	}  
    	</script>  
		</td>
    </tr>
    </tbody>
   </table>
  </form>

 </div> 
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />