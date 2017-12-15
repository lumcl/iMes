<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
  function chkMatnr() {
    jQuery.ajax({
      url : "/iMes/ajax",
      data : {
        matnr : $('#matnr').val(),
        action : 'getMaterialInfo'
      },
      cache : false
    }).done(function(html) {
      if (html == '') {
        $('#matnr').val('');
        $('#matnr').focus();
        $('#matkl').text('');
        $('#meins').text('');
        $('#maktx').text('');
        $('#zeinr').text('');
      } else {
        var buf = html.split(",");
        $('#matkl').text(buf[0]);
        $('#meins').text(buf[1]);
        $('#maktx').text(buf[2]);
        $('#zeinr').text(buf[3]);
      }
    });
  }
  $(document).ready(function() {
    $("#EXEC_A").click(function(event) {
      if ($('#matnr').val() == '' || $('#werks').val() == '') {
        alert("料號或者工廠不能為空!, 請再輸入!\n Material and Plan Cannot Empty!");
        return false;
      }

      jQuery.ajax({
        url : "/iMes/MD04",
        data : {
          action : 'B',
          matnr : $('#matnr').val(),
          werks : $('#werks').val()
        },
        cache : false,
        beforeSend : function() {
          $("#EXEC_A").attr("disabled","disabled");
          $('#MD04BJSP').html(ajaxLoading);
        }
      }).done(function(html) {
        $('#MD04BJSP').html(html);
        $("#EXEC_A").removeAttr("disabled");
      });
    });
  });
</script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">MD04A - 庫存需求清單</a></li>
 </ul>

 <div id="tab-1">
  <div id="icon">
   <button type="button" onclick="$.toExcel('#MD04B_table')">
    <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
   </button>
   <button type="button" id="EXEC_A">
    <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
   </button>
  </div>

  <div>
   <table>
    <tr>
     <th>物料<br/>Material</th>
     <td><input type="text" id="matnr" name="matnr" size="20" value="${param.matnr }" onchange="chkMatnr()"></td>
     <th>工廠<br/>Plan</th>
     <td><input type="text" id="werks" name="werks" size="5" value="${param.werks }"></td>
     <th>說明<br/>Description</th>
     <td id="maktx"></td>
     <th>料類<br/>MatlGrp</th>
     <td id="matkl"></td>
     <th>單位<br/>UM</th>
     <td id="meins"></td>
     <th>圖號<br/>Drawing</th>
     <td id="zeinr"></td>
    </tr>
   </table>
  </div>


  <div id="MD04BJSP"></div>


 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />