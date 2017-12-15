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

function create() {
    if (confirm('人事字：凡人員增補、錄用、異動、調薪調職之簽呈。'+
    		'獎懲字：凡人事獎勵和懲處之簽呈。'+
    		'規章字：所有公司各類管理規定及標準書之簽呈。'+
    		'異常字：所有作業異常、客戶退貨、扣款及其他非正常費用的發生之簽呈。'+
    		'一般字：不屬於以上之其他所有簽呈。'+
			'Word: Where personnel supplement, recruitment, staff turnover, '+
			'salary adjustment petition transfer. '+
			'Words: all personnel incentive reward and punishment of petition. '+
			'Words: all the company management rules and regulations and standard letter of petition. '+
			'Abnormal words: all work abnormal and customer return, '+
			'deductions and other non normal expenses of petition. '+
			'General character: do not belong to more than all other petition.')) {
    	location.href='/iMes/D400/CREATE';
    }
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

      $("#SBMFRM01").click(function(event) {
        $('#FRM01').submit();
      });
      
      $("#create").click(function(event) {
    	  create();
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
            errNum += 1;
            errTxt += errNum + '. 簽呈內容不能為空. Content code can not be empty\n';
        }

        if (errNum > 0) {
          alert(errTxt);
          return false;
        }
        return confirm('請確認資料是否正確并需要建立《簽呈D400》? Please confirm the information is correct and the need to establish "D400" petition?');
      });

      $("#SBMFRM04").click(function(event) {
        $('#FRM04').submit();
      });


      $("#SBMQHBTN").click(function(event) {
        if (confirm('請確認資料是否要啟動送簽流程? Please confirm whether the information should be started to send the check flow?')) {
          $('#SBMQHFRM').submit();
        }
      });
      
      $("#SBMFRM05").click(function(event) {
        if (confirm('請確認資料是否正確? Please confirm whether the information is correct?')) {
          $('#FRM05').submit();
        }
      });
      
      $("#SBMFRM02").click(function(event) {
        if (confirm('請確認資料是否正確? Please confirm whether the information is correct?')) {
          $('#FRM02').submit();
        }
      });
      
      
      $("#BMMC").focus();

    });
