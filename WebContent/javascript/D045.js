function chkMatnr(value, rowid) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      matnr : value,
      action : 'validateMatnr'
    },
    cache : false
  }).done(function(html) {
    $('#MAKTX_' + rowid).text(html);
    if (html == '') {
      $('#MATNR_' + rowid).val('');
      $('#MATNR_' + rowid).focus();
    }
  });
}

function getCustomerName(value, rowid) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      KUNNR : value,
      action : 'getCustomerName'
    },
    cache : false
  }).done(function(html) {
    $('#KTEXT_' + rowid).val(html);
    if (html == '') {
      $('#KUNNR_' + rowid).val('');
      $('#KUNNR_' + rowid).focus();
    }
  });
}

function getDeptName(value, rowid) {
  jQuery.ajax({
	    url : "/iMes/ajax",
	    data : {
	      BMMC : value,
	      GSDM : $('#GSDM').val(),
	      action : 'getDeptName'
	    },
	    cache : false
	  }).done(function(html) {
	    $('#BMMC1_' + rowid).val(html);
	    if (html == '') {
	      $('#BMMC_' + rowid).val('');
	      $('#BMMC_' + rowid).focus();
	    }
	  });
}

function getSupplierName(value, rowid) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      LIFNR : value,
      action : 'getSupplierName'
    },
    cache : false
  }).done(function(html) {
    $('#KTEXT_' + rowid).val(html);
    if (html == '') {
      $('#LIFNR_' + rowid).val('');
      $('#LIFNR_' + rowid).focus();
    }
  });
}

function updateREQA(SQNR) {
  var i = 0;
  i = $('#NETPR_' + SQNR).text() * $('#REQQ_' + SQNR).val();
  $('#REQA_' + SQNR).val(i);
}

$(document).ready(
    function() {
    	
    	var rowid = 0;
    	
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
              getDeptName(this.value, this.id.split('_')[1]);
            }
          });
        });
        
        $("#INSRLINE").click(
            function(event) {
              rowid = rowid + 1;
              var row = "<tr id=\'FROTR"+rowid+"'>";
			      row += "<td></td>";
			      row += "<td><input type=\"text\" name=\"CMAKTX\" id=\"CMAKTX\" value=\"\"></td>";
			      row += "<td><input type=\"text\" name=\"CMATNR\" id=\"CMATNR\" value=\"\"></td>";
			      row += "<td><input type=\"text\" name=\"CCQTY\" id=\"CCQTY\" value=\"\"></td>";
			      row += "<td><input type=\"text\" name=\"CCUM\" id=\"CCUM\" value=\"\" size=\"5\"></td>";
			      row += "<td><input type=\"text\" name=\"CPRICE\" id=\"CPRICE\" value=\"\" size=\"5\"></td>";
			      row += "<td><input type=\"text\" name=\"CCURR\" id=\"CCURR\" value=\"\" size=\"10\"></td>";
			      row += "<td><input type=\"text\" name=\"CWEIGHT\" id=\"CWEIGHT\" value=\"\" size=\"10\"></td>";
			      row += "</tr>";

              $('#ROWS').after(row);
            });
        

        $("#SBMFRM01").click(function(event) {
          $('#FRM01').submit();
        });

        $('#FRM01').submit(function() {
          var errTxt = '';
          var errNum = 0;
          if ($("#BMMC").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 部門代碼不能為空\n';
          }
          
          if ($("#QCAY").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 案由不能為空\n';
          }
          
          if ($("#QCNR").val() == '') {
              errNum += 1;
              errTxt += errNum + '. 簽呈內容不能為空\n';
          }

          if (errNum > 0) {
            alert(errTxt);
            return false;
          }
          return confirm('請確認資料是否正確并需要建立《出厂单D045》?');
          ;
        });

        $("#SBMFRM04").click(function(event) {
          $('#FRM04').submit();
        });


        $("#SBMQHBTN").click(function(event) {
          if (confirm('請確認資料是否要啟動送簽流程?')) {
            $('#SBMQHFRM').submit();
          }
        });
        
        $("#SBMFRM05").click(function(event) {
          if (confirm('請確認資料是否正確?')) {
            $('#FRM05').submit();
          }
        });
        
        $("#SBMFRM02").click(function(event) {
          if (confirm('請確認資料是否正確?')) {
            $('#FRM02').submit();
          }
        });
        
        
        $("#BMMC").focus();

    });
