<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D189UPDATE.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
function PrintPage()
{
	var strURL = "/iMes/D189/Print?GSDM=${D189.GSDM}&BDDM=${D189.BDDM}&BDBH=${D189.BDBH}"; 
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
  <form action="/iMes/D189/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D189.GSDM}">
   <input type="hidden" name="BDDM" value="${D189.BDDM}">
   <input type="hidden" name="BDBH" value="${D189.BDBH}">
   <input type="hidden" name="BDTX" value="${D189.CGYY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
  <form action="/iMes/D189/ADDQH" method="post" id="SBADDQH">
   <input type="hidden" name="GSDM" value="${D189.GSDM}">
   <input type="hidden" name="BDDM" value="${D189.BDDM}">
   <input type="hidden" name="BDBH" value="${D189.BDBH}">
   <input type="hidden" name="BDTX" value="${D189.CGYY}">
   <input type="hidden" name="BDAMT" value="0">
  </form>
 <ul>
  <li><a href="#tab-1">Heavy Requisition<br />重工申請單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D189/UPDATE" method="post" id="FRM01">
   <div id="icon">
   <c:if test="${QHKS == 'N'}"> 
	   <button type="button" id="SBMFRM01">
	    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="save<br /> 保存" />
	   </button>
   </c:if>
   <button type="reset" onclick="location.href='/iMes/D189'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="reset<br />撤銷" />
   </button>
   
    <c:if test="${((D189.BDBH != '') && (D189.SQYH == sessionScope.uid)) && (QHKS == 'N')}">
     <button type="button" id="SBMQHBTN" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="action <br /> 啟動簽核流程" />
     </button>
    </c:if>
    
   <c:if test="${((D189.BDBH != '') && ((D189.SQYH == sessionScope.uid) || (sessionScope.uid == 'FELIX.JIANG')|| (sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'IRIS.ZHENG'))) && (QHKS == 'Y') }">
     <button type="button" id="SBMQHBTNEE" onclick="PrintPage();">
      <img src="/iMes/stylesheet/icons/S_F_PRINT.GIF" alt="print<br />Print  打印" />
     </button>
   </c:if>
    
   <c:if test="${((D189.BDBH != '') && ((D189.SQYH == sessionScope.uid) || (sessionScope.uid == 'ANNIE.SU') || (sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'FELIX.JIANG'))) && (QHKS == 'Y') }">
     <button type="button" id="SBADDQHR" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="Add Person<br />Add Person 加簽核人" />
     </button>
   </c:if>
    
   <c:if test="${((D189.BDBH != '') && ((D189.SQYH == sessionScope.uid) || (sessionScope.uid == 'IRIS.ZHENG') || (sessionScope.uid == 'LUM.CL') || (sessionScope.uid == 'FELIX.JIANG'))) && (QHKS == 'Y') }">
     <button type="button" id="SBCOST" onclick="this.disabled=true;">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="Save<br />Edit 修改部门" />
     </button>
   </c:if>
    
   </div>

   <table style="width:1150px;">
    <caption>Heavy Requisition<br /> 重工單據</caption>
    <tbody>
     <tr>
      <th>Company Code <br />公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="" <c:if test="${D189.GSDM == 'L210'}">selected="selected"</c:if>>L210 DongGuan 東莞領航</option>
        <option value="L300" <c:if test="${D189.GSDM == 'L300'}">selected="selected"</c:if>>L300 DongGuan Leader 東莞立德</option>
        <option value="L400" <c:if test="${D189.GSDM == 'L400'}">selected="selected"</c:if>>L400 JiangSu 江蘇領先</option>
        <option value="L921" <c:if test="${D189.GSDM == 'L921'}">selected="selected"</c:if>>L921 Japanese 日本立德</option>
        <option value="L111" <c:if test="${D189.GSDM == 'L111'}">selected="selected"</c:if>>L111 Philippines</option>
      </select></td>
      <th>Stock warehouse Do<br />库存仓别</th>
      <td><select name="KCCB">
        <option value="381A" <c:if test="${D189.KCCB == '381A'}">selected="selected"</c:if>>381A</option>
        <option value="281A" <c:if test="${D189.KCCB == '281A'}">selected="selected"</c:if>>281A</option>
        <option value="481A" <c:if test="${D189.KCCB == '481A'}">selected="selected"</c:if>>481A</option>
        <option value="701A" <c:if test="${D189.KCCB == '701A'}">selected="selected"</c:if>>701A</option>
        <option value="482A" <c:if test="${D189.KCCB == '482A'}">selected="selected"</c:if>>482A</option>
        <option value="101A" <c:if test="${D189.KCCB == '101A'}">selected="selected"</c:if>>101A</option>
        <option value="111A" <c:if test="${D189.KCCB == '111A'}">selected="selected"</c:if>>111A</option>
      </select></td>
      <th>Customer Number<br />客户编号</th>
      <td><input type="text" name="KHBH" id="KHBH" style="width:150px;" value="${D189.KHBH}"></td>
     </tr>
     <tr>
      <th>Design No.<br />设计编号</th>
      <td><input type="text" name="SJBH" id="SJBH" style="width:150px;" value="${D189.SJBH}"></td>
      <th>The home department<br />归属部門</th>
      <td><input type="text" name="BMMC" id="BMMC" style="width:150px;" value="${D189.BMMC}"></td>
      <th>Heavy quantity<br />重工数量</th>
      <td><input type="text" name="CQTY" id="CQTY" style="width:150px;" value="${D189.CQTY}"/></td>
     </tr>
     <tr>
      <th>Ship date<br />出货日期</th>
      <td><input type="text" name="CHSJ" id="CHSJ" style="width:150px;" value="${D189.CHSJ}" size="10" class="datepick"></td>
      <th>Form Number<br />表單編號</th>
      <td><input type="text" name="BDBH" id="BDBH" style="width:180px;" value="${D189.BDBH}"></td> 
      <th>Forms attached file<br />表單附檔</th>
      <c:if test="${D189.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${D189.BDFD}">Download 下載</a></td>
      </c:if>
      <c:if test="${D189.BDFD.length()== null ||D189.BDFD.length()==0 }">
       <td></td>
      </c:if>
     </tr>
     <tr>     
      <th>Form date<br />表單日期</th>
      <td><input type="text" name="BDRQ" id="BDRQ" value="${D189.BDRQ}" size="10" class="datepick"></td>
      <th>Heavy briefing<br />重工說明會</th>
      <td colspan="3">
      <c:choose>
        <c:when test="${D189.SMH eq '1'}"> 
	      <input type="radio" name="SMH" value="1" checked>Not required 不需要
	      <input type="radio" name="SMH" value="0">Need 需要
	    </c:when>
        <c:when test="${D189.SMH eq '0'}"> 
	      <input type="radio" name="SMH" value="1">Not required 不需要
	      <input type="radio" name="SMH" value="0" checked>Need 需要
	    </c:when>
	    <c:otherwise>
          <input type="radio" name="SMH" value="1">Not required 不需要
          <input type="radio" name="SMH" value="0" checked>Need 需要
	    </c:otherwise>
      </c:choose>
      </td>
     </tr>
     <tr>
      <th>Heavy Reason Description<br />重工原因说明</th>
      <td colspan="6"><textarea name="CGYY" id="CGYY" rows="15" cols="150">${D189.CGYY}</textarea></td>
     </tr>
     <tr>
      <th>Heavy handling instructions<br />重工处理方式说明</th>
      <td colspan="6"><textarea name="CGFS" id="CGFS" rows="15" cols="150">${D189.CGFS}</textarea></td>
     </tr>
    </tbody>
   </table>
  </form>
  <c:if test="${QHKS == 'N'}">
<c:if test="${D189.BDFD.length()== null ||D189.BDFD.length()==0 }">
   <form action="/iMes/FileUploader?action=AttachmentA" method="post" enctype="multipart/form-data">
    <input type="hidden" name="GSDM" value="${D189.GSDM}">
    <input type="hidden" name="BDDM" value="${D189.BDDM}">
    <input type="hidden" name="BDBH" value="${D189.BDBH}">
    <br />
    <table>
     <tr>
      <th colspan="2" align="center">Documents upload 文件上傳</th>
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
     <td><a href="/iMes/FileDownloader?filePath=${e.QHFD}">Download 下載</a></td>
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
     <th colspan="2" align="center">Documents upload 文件上傳</th>
    </tr>
    <tr>
     <td><input type="file" name="file" style="font-size: large;" /></td>
     <td><button type="button" onclick="this.disabled=true;this.form.submit();">
       <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
      </button></td>
    </tr>
   </table>
  </form>


  <form action="/iMes/D189" method="post" name="QianHe" id="Qianhe">
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
     <tr>
      <th>會簽</th>
      <td colspan="2"><input type="text" id="HQYH" name="HQYH" size="80" style="width: 550px" onkeydown="if(event.keyCode=='13'){return false}"></td>
     </tr>
     <tr>
      <th>内容</th>
      <td colspan="2"><textarea rows="5" cols="70" name="QHNR">OK</textarea></td>
     </tr>
     <tr>
      <th>结果</th>
      <td>${sessionScope.uid} &nbsp;: &nbsp;<select name="QHJG">
        <option value="Y" selected="selected">同意</option>
        <option value="N">不同意</option>
      </select> &nbsp;&nbsp; 重簽 &nbsp;<input type="checkbox" name="YHCQ">
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