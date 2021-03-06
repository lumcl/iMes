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
    $('#BMMC1').val(html);
    if (html == '') {
      $('#BMMC1').val('');
      $('#BMMC1').focus();
    }
  });
}

$(document).ready(
    function() {    	
	  $('#BMMC1').autocomplete({
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
        if ($("#BMMC").val() == '') {
          errNum += 1;
          errTxt += errNum + '. 部門代碼不能為空\n';
        }

        if ($("#QCAY").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 案由不能為空\n';
        }

        if ($("#QCNR").val() == '') {
            //errNum += 1;
            //errTxt += errNum + '. 簽呈內容不能為空\n';
        }

        if (errNum > 0) {
          alert(errTxt);
          return false;
        }
        return confirm('請確認資料是否正確并需要更新《試產单D600》?');
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
