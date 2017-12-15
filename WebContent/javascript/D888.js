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

function create() {
    if (confirm('確定創建出廠單!')) {
    	location.href='/iMes/D888/CREATE';
    }
}

$(document).ready(
    function() {		
      $('#BMBH').each(function() {   	  
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
            getDeptName($('#BMBH').val());
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
        if ($("#BMBH").val() == '') {
          errNum += 1;
          errTxt += errNum + '. 部門代碼不能為空\n';
        }
        
        if ($("#QCAY").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 案由不能為空\n';
        }
        
        if ($("#CCNR").val() == '') {
            errNum += 1;
            errTxt += errNum + '. 出廠內容不能為空\n';
        }

        if (errNum > 0) {
          alert(errTxt);
          return false;
        }
        return confirm('請確認資料是否正確并需要建立《出廠單D888》?');
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
      
      
      $("#BMBH").focus();

    });
