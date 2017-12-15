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
      KOSTL : value,
      GSDM : $('#GSDM').val(),
      action : 'getDeptName'
    },
    cache : false
  }).done(function(html) {
    $('#KTEXT_' + rowid).val(html);
    if (html == '') {
      $('#KOSTL_' + rowid).val('');
      $('#KOSTL_' + rowid).focus();
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

      $('.KOSTL').each(function() {
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

      $("#INSRBTN").click(
          function(event) {
            rowid = rowid + 1;
            var row = "<tr><td><input name='ROWID'value='" + rowid
                + "' type='checkbox' checked=checked></td><td></td>";
            row += "<td id='MAKTX_" + rowid + "'></td>";
            row += "<td><input name='MATNR_" + rowid
                + "' type='text' onchange='chkMatnr(this.value," + rowid
                + ")' id='MATNR_" + rowid + "'></td>";
            row += "<td><input name='WERKS_" + rowid
                + "' type='text' size='4'></td>";
            row += "<td></td><td></td><td></td><td></td><td></td>";
            row += "</tr>";
            $('#ROWS').prepend(row);
          });

      $("#SBMFRM01").click(function(event) {
        $('#FRM01').submit();
      });

      $('#FRM01').submit(function() {
        var errTxt = '';
        var errNum = 0;
        if ($("#KOSTL").val() == '') {
          errNum += 1;
          errTxt += errNum + '. 部門代碼不能為空\n';
        }
        if ($("#YYSM").val() == '') {
          errNum += 1;
          errTxt += errNum + '. 原因說明不能為空\n';
        }

        if ($("#YYLB").val() == '') {
          errNum += 1;
          errTxt += errNum + '. 原因代碼不能為空\n';
        }

        if (errNum > 0) {
          alert(errTxt);
          return false;
        }
        return confirm('請確認資料是否正確并需要建立《溢領單 D272》?');
        ;
      });

      $("#SBMFRM04").click(function(event) {
        $('#FRM04').submit();
      });

      $('#FRM04').submit(
          function() {
            var rowid = '';
            var errTxt = '';
            var errNum = 0;
            var totalPCTG = 0;
            $('.KOSTL').each(
                function() {
                  if (this.id.split("_")[1] != null) {
                    rowid = this.id.split("_")[1];
                    if ($('#KOSTL_' + rowid).val() != ''
                        && ($('#LIFNR_' + rowid).val() != '' || $(
                            '#KUNNR_' + rowid).val() != '')) {
                      errNum += 1;
                      errTxt += errNum + '. 在一條記錄里不允許超過一個責任歸屬單位.\n';
                      return false;
                    }
                    if ($('#LIFNR_' + rowid).val() != ''
                        && ($('#KOSTL_' + rowid).val() != '' || $(
                            '#KUNNR_' + rowid).val() != '')) {
                      errNum += 1;
                      errTxt += errNum + '. 在一條記錄里不允許超過一個責任歸屬單位.\n';
                      return false;
                    }
                    if ($('#KUNNR_' + rowid).val() != ''
                        && ($('#KOSTL_' + rowid).val() != '' || $(
                            '#LIFNR_' + rowid).val() != '')) {
                      errNum += 1;
                      errTxt += errNum + '. 在一條記錄里不允許超過一個責任歸屬單位.\n';
                      return false;
                    }

                    if (parseInt($('#PCTG_' + rowid).val()) != 0
                        && $('#KTEXT_' + rowid).val() == '') {
                      errNum += 1;
                      errTxt += errNum + '. 有責任%但沒有個責任歸屬單位.\n';
                      return false;
                    }
                    totalPCTG += parseInt($('#PCTG_' + rowid).val());
                  }

                });

            if (totalPCTG != 100) {
              errNum += 1;
              errTxt += errNum + '. 加總所有責任%必須等於100.\n';
            }

            if (errNum > 0) {
              alert(errTxt);
              return false;
            }
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
      
      
      $("#InsertWipBtn").click(function(event) {
        jQuery.ajax({
          url : "/iMes/D272",
          data : {
            action : 'WipComponentList',
            Pmatnr : $('#Pmatnr').val(),
            WipMatnr : $('#WipMatnr').val(),
            WipMenge : $('#WipMenge').val(),
            WipWerks : $('#WipWerks').val(),
            Psmng : $('#PSMNG').val(),
            Rsnum : $('#RSNUM').val()
          },
          cache : false,
          beforeSend : function(){
            $('#InsertWipDiv').html(ajaxLoading);
          }
        }).done(function(html) {
          $('#InsertWipDiv').html(html);
        });
      });

      $("#InsertNewItemBtn").click(function(event) {
        jQuery.ajax({
          url : "/iMes/D272",
          data : {
            action : 'InsertNewItem'
          },
          cache : false,
          beforeSend : function(){
            $('#InsertWipDiv').html(ajaxLoading);
          }
        }).done(function(html) {
          $('#InsertWipDiv').html(html);
        });
      });
      
      $("#KOSTL").focus();

    });
