function getDeptName(value) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      KOSTL : value,
      GSDM : $('#GSDM').val(),
      action : 'getDeptName'
    },
    cache : false
  }).done(function(html) {
    $('#BMMC').val(html);
    if (html == '') {
      $('#BMMC').val('');
      $('#BMMC').focus();
    }
  });
}

function getCustomerName(value) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      KUNNR : value,
      action : 'getCustomerName'
    },
    cache : false
  }).done(function(html) {
    $('#KHBH').val(html);
    if (html == '') {
      $("#KHBH").val('');
      $("#KHBH").focus();
    }
  });
}

$(document).ready(
    function() {	  
      $('#BMMC').each(function() {
          $(this).autocomplete({
            source : function(request, response) {
              $.getJSON("/iMes/ajax", {
                action : 'getDeptJson',
                gsdm : $('#GSDM').val(),
                term : encodeURI(request.term, "utf-8")
              }, response);
            },
            delay : 10,
            minLength : 0,
            change : function(event, ui) {
              getDeptName($('#BMMC').val());
            }
          });
      });
	
      $("#KHBH").each(function() { 
        $(this).autocomplete({
          source : function(request, response) {
            $.getJSON("/iMes/ajax", {
              action : 'getCustomerName'
            }, response);
          },
          delay : 10,
          minLength : 0,
          change : function(event, ui) {
        	getCustomerName($('#KHBH').val());
          }
        });
      });

      $("#SBMFRM01").click(function(event) {
        $('#FRM01').submit();
      });

      $('#FRM01').submit(function(event) {
        var errTxt = '';
        var errNum = 0;
        if ($("#BMMC").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 歸屬部門不能為空\n';
          }
          
          if ($("#KHBH").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 客戶編號不能為空\n';
          }
          
          if ($("#CQTY").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 重工数量\n';
          }
          
          if ($("#CHSJ").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 出货日期\n';
          }
          
          if ($("#SJBH").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 设计编号\n';
          }
          
          if ($("#KCCB").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 库存仓别\n';
          }
          
          if ($("#CGYY").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 重工原因说明\n';
          }

          if (errNum > 0) {
            alert(errTxt);
            return false;
          }
          return confirm('請確認資料是否正確并需要更新《重工申請單》?');
      });

      $("#SBMFRM04").click(function(event) {
      	this.disabled = true;
        $('#FRM04').submit();
      });

      $('#FRM04').submit(
          function() {
            var errTxt = '';
            var errNum = 0;

            if (errNum > 0) {
              alert(errTxt);
              return false;
            }
          });

      $("#SBMQHBTN").click(function(event) {
    	this.disabled = true;
        if (confirm('請確認資料是否要啟動送簽流程?')) {
          $('#SBMQHFRM').submit();
        }
      });
      
      $("#SBMFRM05").click(function(event) {
      	this.disabled = true;
        if (confirm('請確認資料是否正確?')) {
          $('#FRM05').submit();
        }
      });
      
      $("#SBMFRM02").click(function(event) {
        if (confirm('請確認資料是否正確?')) {
          $('#FRM02').submit();
        }
      });

      $("#SBADDQHR").click(function(event) {
    	this.disabled = true;
        if (confirm('請確認是否要加簽核人?')) {
          $('#SBADDQH').submit();
        }
      });
      
      $("#KHBH").focus();

    });
