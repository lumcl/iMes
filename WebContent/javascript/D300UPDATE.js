function getSupplier() {
  jQuery.ajax({
    url : "/iMes/D300/GETSUPPLIER",
    data : {
    	kunnr : $('#KHBM').val(),
    },
    cache : false
  }).done(function(html) {
    if (html == ',') {
      alert("Invalid Customer Code!");
      $('#KHMC').val('');
      $('#KHMC').focus();
    } else {
      var buf = html.split(',');
      $('#KHMC').val(buf[0]);
    }
    ;
  });
}

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
    $('#SCDW').val(html);
    if (html == '') {
      $('#SCDW').val('');
      $('#SCDW').focus();
    }
  });
}

$(document).ready(
    function() {    	
	  $('#SCDW').autocomplete({
	      create : function(event, ui) {
	        getDeptName($('#BMMC').val());
	      }
	  });
	  
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

      $("#SBMFRM01").click(function(event) {
        $('#FRM01').submit();
      });

      $('#FRM01').submit(function() {
    	  var errTxt = '';
          var errNum = 0;
      	  var regExp=/^\d+(\.\d+)?$/;
          var elementTxt = $("#DDSL").val();
      	
          if ($("#BMMC").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 部門編碼不能為空\n';
          }
          
          if ($("#SXRQ").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 上線日期不能為空\n';
          }

          if ($("#CHRQ").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 出貨日期不能為空\n';
          }
          
          if ($("#SCDW").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 部門名稱不能為空\n';
          }
          
          if ($("#KHBM").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 客戶代碼不能為空\n';
          }
          
          if ($("#KHMC").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 客戶名稱不能為空\n';
          }
          
          if ($("#DDSL").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 訂單數量不能為空\n';
          }
          
          if(regExp.test (elementTxt ))
          {
        	  
          }
          else
          {
              errNum += 1;
              errTxt += errNum + '. 請輸入正確數量\n';
          } 
          
          if ($("#QCNR").val() == '') {
              //errNum += 1;
              //errTxt += errNum + '. 異常原因不能為空\n';
          }

          if (errNum > 0) {
            alert(errTxt);
            return false;
          }
        return confirm('請確認資料是否正確并需要更新《派工令異動申請單(D300)》?');
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
      
      $("#BMMC").focus();

    });
