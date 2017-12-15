<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
  $(document).ready(function() {

    $("#SRCH_A").click(function(event) {
      jQuery.ajax({
        url : "/iMes/SD61",
        data : {
          action : 'SD61GetSoLine',
          VBELN : $('#VBELN').val(),
          POSNR : $('#POSNR').val(),
          WEDATU : $('#WEDATU').val(),
          CNDAT : $('#CNDAT').val(),
          CNQTY : $('#CNQTY').val()
        },
        cache : false,
        beforeSend : function() {
          $('#SD61AJSP').html(ajaxLoading);
        }
      }).done(function(html) {
        $('#icon').show();
        $('#SD61AJSP').html(html);
        if ($('#CNQTY').val() == '') {
          $('#CNQTY').val($('#KBMENG').html());
        }
      });
    });

    $("#EXEC_B").click(function(event) {
      jQuery.ajax({
        url : "/iMes/SD61",
        data : {
          action : 'SD61GetBomStockBalance',
          MATNR : $('#MATNR').html(),
          VTWEG : $('#VTWEG').html(),
          WERKS : $('#WERKS').html(),
          EDATU : $('#EDATU').html(),
          CNDAY : $('#CNDAY').html(),
          CNDAT : $('#CNDAT').val(),
          CNQTY : $('#CNQTY').val()
        },
        cache : false,
        beforeSend : function() {
          $('#SD61BJSP').html(ajaxLoading);
        }
      }).done(function(html) {
        $('#SD61BJSP').html(html);
      });
    });

    $('#icon').hide();

  });
</script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">SD61 - 取消訂單呆料計算</a></li>
 </ul>

 <div id="tab-1">

  <div>
   <table>
    <tr>
     <th>銷售文件/行號</th>
     <th>取消日期</th>
     <th>取消數量</th>
     <th>更改交期</th>
     <th rowspan="2">
      <button type="button" id="SRCH_A">
       <img src="/iMes/stylesheet/icons/S_B_SRCH.GIF" alt="" />
      </button>
     </th>
    </tr>
    <tr>
     <td><input type="text" id="VBELN" name="VBELN" size="12" value="${param.VBELN}"> <input type="text" id="POSNR" name="POSNR" size="6" value="${param.POSNR}"></td>
     <td><input type="text" id="CNDAT" name="CNDAT" size="10" value="${CNDAT}" /></td>
     <td><input type="text" id="CNQTY" name="CNQTY" size="10" value="${CNQTY}" /></td>
     <td><input type="text" id="WEDATU" name="WEDATU" size="10" value="${WEDATU}" /></td>
    </tr>
   </table>
  </div>

  <div id="SD61AJSP"></div>

  <div id="icon">
   <button type="button" id="EXEC_B">
    <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
   </button>
   <button type="button" onclick="$.toExcel('#SD61_table')">
    <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
   </button>
  </div>
  <div id="SD61BJSP"></div>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />