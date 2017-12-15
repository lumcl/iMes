<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D888UPDATE.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
function PrintPage()
{
	var strURL = "/iMes/D888/Print?GSDM=${D888.GSDM}&BDDM=${D888.BDDM}&BDBH=${D888.BDBH}"; 
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
  <form action="/iMes/D888/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D888.GSDM}">
   <input type="hidden" name="BDDM" value="${D888.BDDM}">
   <input type="hidden" name="BDBH" value="${D888.BDBH}">
   <input type="hidden" name="BDTX" value="${D888.BDBH}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
  <form action="/iMes/D888/ADDQH" method="post" id="SBADDQH">
   <input type="hidden" name="GSDM" value="${D888.GSDM}">
   <input type="hidden" name="BDDM" value="${D888.BDDM}">
   <input type="hidden" name="BDBH" value="${D888.BDBH}">
   <input type="hidden" name="BDTX" value="${D888.BDBH}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
 <ul>
  <li><a href="#tab-1">出廠單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D888/UPDATE" method="post" id="FRM01">
   <div id="icon">
   <button type="reset" onclick="location.href='/iMes/D888'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>   
    
   <c:if test="${((D888.BDBH != '') && ((D888.SQYH == sessionScope.uid) || (sessionScope.uid == 'FELIX.JIANG')|| (sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'ANNIE.SU'))) && (QHKS == 'Y') }">
     <button type="button" id="SBMQHBTNEE" onclick="PrintPage();">
      <img src="/iMes/stylesheet/icons/S_F_PRINT.GIF" alt="打印" />
     </button>
   </c:if>    
    
   </div>

   <table style="width:1150px;">
    <caption>出廠單單據</caption>
    <tbody>
     <tr>
      <th>公司<br />代碼</th>
      <td><select name="GSDM" id="GSDM" disabled="disabled">
        <option value="L210" <c:if test="${D888.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${D888.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${D888.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
      </select></td>
      <th>出廠<br />類型</th>
      <td><select name="CCLX" disabled="disabled">
        <option value="成品出廠" <c:if test="${D888.CCLX == '成品出廠'}">selected="selected"</c:if>>成品出廠</option>
        <option value="樣品出廠" <c:if test="${D888.CCLX == '樣品出廠'}">selected="selected"</c:if>>樣品出廠</option>
        <option value="SW樣品出廠" <c:if test="${D888.CCLX == 'SW樣品出廠'}">selected="selected"</c:if>>SW樣品出廠</option>
        <option value="LINEAR樣品出廠" <c:if test="${D888.CCLX == 'LINEAR樣品出廠'}">selected="selected"</c:if>>LINEAR樣品出廠</option>
        <option value="材料樣品出廠" <c:if test="${D888.CCLX == '材料樣品出廠'}">selected="selected"</c:if>>材料樣品出廠</option>
        <option value="品保樣品出廠" <c:if test="${D888.CCLX == '品保樣品出廠'}">selected="selected"</c:if>>品保樣品出廠</option>
        <option value="安规樣品出廠" <c:if test="${D888.CCLX == '安规樣品出廠'}">selected="selected"</c:if>>安规樣品出廠</option>
        <option value="材料調撥出廠" <c:if test="${D888.CCLX == '材料調撥出廠'}">selected="selected"</c:if>>材料調撥出廠</option>
        <option value="固定資產出廠" <c:if test="${D888.CCLX == '固定資產出廠'}">selected="selected"</c:if>>固定資產出廠</option>
        <option value="材料退貨出廠" <c:if test="${D888.CCLX == '材料退貨出廠'}">selected="selected"</c:if>>材料退貨出廠</option>
        <option value="材料超交出廠" <c:if test="${D888.CCLX == '材料超交出廠'}">selected="selected"</c:if>>材料超交出廠</option>
        <option value="資產報廢出廠" <c:if test="${D888.CCLX == '資產報廢出廠'}">selected="selected"</c:if>>資產報廢出廠</option>
        <option value="資產異動出廠" <c:if test="${D888.CCLX == '資產異動出廠'}">selected="selected"</c:if>>資產異動出廠</option>
        <option value="資產借出出廠" <c:if test="${D888.CCLX == '資產借出出廠'}">selected="selected"</c:if>>資產借出出廠</option>
        <option value="設備校驗出廠" <c:if test="${D888.CCLX == '設備校驗出廠'}">selected="selected"</c:if>>設備校驗出廠</option>
        <option value="設備外修出廠" <c:if test="${D888.CCLX == '設備外修出廠'}">selected="selected"</c:if>>設備外修出廠</option>
        <option value="會辦賬務出廠" <c:if test="${D888.CCLX == '會辦賬務出廠'}">selected="selected"</c:if>>會辦賬務出廠</option>
        <option value="雜項物品出廠" <c:if test="${D888.CCLX == '雜項物品出廠'}">selected="selected"</c:if>>雜項物品出廠</option>
        <option value="下腳料出廠" <c:if test="${D888.CCLX == '下腳料出廠'}">selected="selected"</c:if>>下腳料出廠</option>
        <option value="下腳料出售出廠" <c:if test="${D888.CCLX == '下腳料出售出廠'}">selected="selected"</c:if>>下腳料出售出廠</option>
        <option value="其它物品出廠" <c:if test="${D888.CCLX == '其它物品出廠'}">selected="selected"</c:if>>其它物品出廠</option>
        <option value="其他出廠" <c:if test="${D888.CCLX == '其他出廠'}">selected="selected"</c:if>>其他出廠</option>
      </select></td>
      <th>財產<br />編號</th>
      <td><input type="text" name="CCBH" id="CCBH" value="${D888.CCBH}" readonly="readonly"></td>
      <th>領料<br />編號</th>
      <td><input type="text" name="LLBH" id="LLBH" value="${D888.LLBH}" readonly="readonly"></td>
      <th>供應商</th>
      <td><input type="text" name="GYSM" id="GYSM" value=""></td>
     </tr>
     <tr>
      <th>部門<br />代碼</th>
      <td><input type="text" name="BMBH" id="BMBH" value="${D888.BMBH}" readonly="readonly"></td>
      <th>部門<br />名稱</th>
      <td><input type="text" name="BMMC" id="BMMC" value="${D888.BMMC}" readonly="readonly"></td>
      <th>總金額</th>
      <td><input type="text" name="ZJE" id="ZJE" value="${D888.ZJE}" readonly="readonly">元</td>
      <th>跟蹤</th>
      <td><select name="BDGZ" disabled="disabled">
        <option value="返廠" <c:if test="${D888.BDGZ == '返廠'}">selected="selected"</c:if>>返廠</option>
        <option value="不返廠" <c:if test="${D888.BDGZ == '不返廠'}">selected="selected"</c:if>>不返廠</option>
        <option value="立德-領航" <c:if test="${D888.BDGZ == '立德-領航'}">selected="selected"</c:if>>立德-領航</option>
        <option value="領航-立德" <c:if test="${D888.BDGZ == '領航-立德'}">selected="selected"</c:if>>領航-立德</option>
      </select></td>
      <th>表單<br />編號</th>
      <td colspan="3"><input type="text" name="BDBH" id="BDBH" value="${D888.BDBH}" readonly="readonly"></td>
     </tr>
    <tr>
     <th>退料<br />編號</th>
     <td><input type="text" name="TLBH" id="TLBH" value="${D888.TLBH}" readonly="readonly"></td>
     <th>物料<br />編號</th>
     <td><input type="text" name="WLBH" id="WLBH" value="${D888.WLBH}" readonly="readonly"></td>
     <th>訂單<br />編號</th>
     <td><input type="text" name="DDBH" id="DDBH" value="${D888.DDBH}" readonly="readonly"></td>
     <th>表單<br />日期</th>
     <td><input type="text" name="BDRQ" id="BDRQ" value="${D888.BDRQ}" readonly="readonly"></td>            
     <th>表單<br />附檔</th>
     <c:if test="${D888.BDFD.length() > 0}">
      <td><a href="/iMes/FileDownloader?filePath=${D888.BDFD}">下載</a></td>
     </c:if>
     <c:if test="${D888.BDFD.length()== null ||D888.BDFD.length()==0 }">
      <td></td>
     </c:if>
    </tr>
    <tr>
     <th>出廠<br />內容</th>
     <td colspan="10">${D888.CCNR}</td>
    </tr>
    </tbody>
   </table>
  </form>
  
<form name="fEscalate" id="fEscalate" action="/iMes/qh" method="post">
 <input type="hidden" name="action" id="action" value="Escalate">
 <input type="hidden" name="GSDM" id="GSDM">
 <input type="hidden" name="BDDM" id="BDDM">
 <input type="hidden" name="BDBH" id="BDBH">
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
    
    <td>${e.QHJG}</td>

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

 </div> 
 
</div>
