<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
  function chkMatnr(value) {
    jQuery.ajax({
      url : "/iMes/ajax",
      data : {
        matnr : value,
        action : 'validateMatnr'
      },
      cache : false
    }).done(function(html) {
      $('#MAKTX_T').text(html);
      $('#MAKTX').val(html);
      if (html == '') {
        $('#MATNR').val('');
        $('#MATNR').focus();
      }
    });
  }

  function chkLifnr(value) {
    jQuery.ajax({
      url : "/iMes/ajax",
      data : {
        LIFNR : value,
        action : 'getSupplierName'
      },
      cache : false
    }).done(function(html) {
      $('#SORTL_T').text(html);
      $('#SORTL').val(html);
      if (html == '') {
        $('#LIFNR').val('');
        $('#LIFNR').focus();
      }
    });
  }

  $(document).ready(function() {
    $('.datepick').each(function() {
      $(this).datepicker({
        dateFormat : 'yymmdd',
        changeMonth : true,
        changeYear : true
      }).bind('keydown', false);
    });

    $("#SBMFRM01").click(function(event) {
      $('#FRM01').submit();
    });

    $('#FRM01').submit(function() {
      var errTxt = '';
      var errNum = 0;
      if ($("#MATNR").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 物料號不能為空\n';
      }
      if ($("#LIFNR").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 供應商代號不能為空\n';
      }

      if (errNum > 0) {
        alert(errTxt);
        return false;
      }
      return confirm('請確認資料是否正確并保存!!');
      ;
    });

  });
</script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">ME01</a></li>
 </ul>

 <div id="tab-1">
  <div id="icon">
   <button type="button" id="SBMFRM01">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="Save" />
   </button>
   <button type="reset">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="Reset" />
   </button>
  </div>
  <form action="SGME01" method="post" id="FRM01">
   <input type="hidden" name="action" value="Create">
   <input type="hidden" id="MAKTX" name="MAKTX">
   <input type="hidden" id="SORTL" name="SORTL">
   <table>
    <caption>輸入貨源清單</caption>
    <tr>
     <th>料號</th>
     <td><input id="MATNR" name="MATNR" type="text" onchange="chkMatnr(this.value)" /></td>
     <th>規格</th>
     <td id="MAKTX_T"></td>
    </tr>
    <tr>
     <th>供應商</th>
     <td><input id="LIFNR" name="LIFNR" type="text" onchange="chkLifnr(this.value)" /></td>
     <th>名稱</th>
     <td id="SORTL_T"></td>
    </tr>
    <tr>
     <th>工廠</th>
     <td><select id="WERKS" name="WERKS">
       <option value="481A" <c:if test="${param.WERKS == '481A'}">selected="selected"</c:if>>481A</option>
       <option value="482A" <c:if test="${param.WERKS == '482A'}">selected="selected"</c:if>>482A</option>
       <option value="101A" <c:if test="${param.WERKS == '101A'}">selected="selected"</c:if>>101A</option>
       <option value="701A" <c:if test="${param.WERKS == '701A'}">selected="selected"</c:if>>701A</option>
       <option value="281A" <c:if test="${param.WERKS == '281A'}">selected="selected"</c:if>>281A</option>
       <option value="381A" <c:if test="${param.WERKS == '381A'}">selected="selected"</c:if>>381A</option>
       <option value="382A" <c:if test="${param.WERKS == '382A'}">selected="selected"</c:if>>382A</option>
       <option value="921A" <c:if test="${param.WERKS == '921A'}">selected="selected"</c:if>>921A</option>
     </select></td>
     <th>採購組織</th>
     <td><select id="EKORG" name="EKORG">
       <option value="L400" <c:if test="${param.EKORG == 'L400'}">selected="selected"</c:if>>L400</option>
       <option value="L100" <c:if test="${param.EKORG == 'L100'}">selected="selected"</c:if>>L100</option>
       <option value="L700" <c:if test="${param.EKORG == 'L700'}">selected="selected"</c:if>>L700</option>
       <option value="L210" <c:if test="${param.EKORG == 'L210'}">selected="selected"</c:if>>L210</option>
       <option value="L300" <c:if test="${param.EKORG == 'L300'}">selected="selected"</c:if>>L300</option>
       <option value="L920" <c:if test="${param.EKORG == 'L300'}">selected="selected"</c:if>>L920</option>
     </select></td>
    </tr>
    <tr>
     <th>限制</th>
     <td>數量: <input type="text" name="FREI_MNG" size="7" onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);">
     </td>
     <th>有效日期</th>
     <td>從: <input type="text" name="VDATU" size="10" class="datepick" value="20120101" /> - 到: <input type="text" name="BDATU" size="10" class="datepick" value="" /></td>
    </tr>
    <tr>
     <th>凍結原因</th>
     <td colspan="3"><input type="text" name="SPERRGRUND" size="62"></td>
    </tr>
   </table>
  </form>

  <br />
  <table>
   <thead>
    <tr>
     <th></th>
     <th></th>
     <th>料號</th>
     <th>規格</th>
     <th>工廠</th>
     <th>供應商</th>
     <th>名稱</th>
     <th>組織</th>
     <th>版本</th>
     <th>生效日期</th>
     <th>有效日期</th>
     <th>限量數量</th>
     <th>凍結原因</th>
     <th>建立用戶</th>
     <th>建立時間</th>
     <th>ME01</th>
     <th>QI01</th>
     <th>ME01建立</th>
     <th>QI01建立</th>
     <th>ME01錯誤</th>
     <th>QI01錯誤</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach var="e" items="${list}">
     <tr>
      <th><c:if test='${e.ME01 == "E" || e.QI01 == "E"}'>
        <button type="button" onclick="location.href('/iMes/SGME01?action=Restart&id=${e.ID}')">
         <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="Reset" />
        </button>
       </c:if></th>
      <td>${e.ID}</td>
      <td>${e.MATNR}</td>
      <td>${e.MAKTX}</td>
      <td>${e.WERKS}</td>
      <td>${e.LIFNR}</td>
      <td>${e.SORTL}</td>
      <td>${e.EKORG}</td>
      <td>${e.REVLV}</td>
      <td>${e.VDATU}</td>
      <td>${e.BDATU}</td>
      <td>${e.FREI_MNG}</td>
      <td>${e.SPERRGRUND}</td>
      <td>${e.JLYH}</td>
      <td>${e.JLSJ}</td>
      <td>${e.ME01}</td>
      <td>${e.QI01}</td>
      <td>${e.ME01JLSJ}</td>
      <td>${e.QI01JLSJ}</td>
      <td>${e.ME01EMSG}</td>
      <td>${e.QI01EMSG}</td>
     </tr>
    </c:forEach>
   </tbody>
  </table>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />