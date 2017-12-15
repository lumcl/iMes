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
          errTxt += errNum + '. 部門代碼不能為空. Dept code can not be empty\n';
        }

        if ($("#QCAY").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 案由不能為空. Subject code can not be empty\n';
        }

        if ($("#QCNR").val() == '') {
            //errNum += 1;
            //errTxt += errNum + '. 簽呈內容不能為空. Content code can not be empty\n';
        }

        if (errNum > 0) {
          alert(errTxt);
          return false;
        }
        return confirm('請確認資料是否正確并需要更新《簽呈D400》?Please confirm the information is correct and the need to establish "D400" petition?');
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
        if (confirm('請確認資料是否要啟動送簽流程?Please confirm whether the information should be started to send the check flow?')) {
          $('#SBMQHFRM').submit();
        }
      });
      
      $("#SBMFRM05").click(function(event) {
      	this.disabled = true;
        if (confirm('請確認資料是否正確?Please confirm whether the information is correct?')) {
          $('#FRM05').submit();
        }
      });
      
      $("#SBMFRM02").click(function(event) {
        if (confirm('請確認資料是否正確?Please confirm whether the information is correct?')) {
          $('#FRM02').submit();
        }
      });

      $("#SBADDQHR").click(function(event) {
    	this.disabled = true;
        if (confirm('請確認是否要加簽核人?Please confirm whether to add the signature of the person?')) {
          $('#SBADDQH').submit();
        }
      });
      
      $("#BMMC").focus();

    });
