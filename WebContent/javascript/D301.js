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
      $('#SQBM').val('');
      $('#SQBM').focus();
    }
  });
}

function getZCName(value) {
	  jQuery.ajax({
	    url : "/iMes/ajax",
	    data : {
	      ZCBM : value,
	      GSDM : $('#GSDM').val(),
	      action : 'getZCName'
	    },
	    cache : false
	  }).done(function(html) {
	    $('#ZCMC').val(html);
	    if (html == '') {
	      $('#ZCBH').val('');
	      $('#ZCBH').focus();
	    }
	  });
	}

function getZCInfor(value) {
	  jQuery.ajax({
	    url : "/iMes/ajax",
	    data : {
	      ZCBM : value,
	      GSDM : $('#GSDM').val(),
	      action : 'getZCInfor'
	    },
	    cache : false
	  }).done(function(html) {
	    $('#ZCMC').val(html.split("@_@")[1]);
	    $('#ZCCB').val(html.split("@_@")[2]);
	    $('#BGR').val(html.split("@_@")[3]);
	    $('#ZBDT').val(html.split("@_@")[4]);
	    $('#ZCBG').val(html.split("@_@")[5]);
	    $('#ADDR').val(html.split("@_@")[6]);
	    $('#ZMJZ').val(html.split("@_@")[7]);
	    $('#QTY').val(html.split("@_@")[8]);
	    $('#QDJZ').val(html.split("@_@")[9]);
	    if (html == '') {
	      $('#ZCBH').val('');
	      $('#ZCBH').focus();
	    }
	  });
	}

function getSupplier(value, rowid) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      SUPPLIER : value,
      action : 'getD301_Supplier'
    },
    cache : false
  }).done(function(html) {
    $('#SUPPLIER_NAME_' + rowid).val(html.split("@_@")[1]);
    $('#CONTACT_' + rowid).val(html.split("@_@")[2]);
    $('#PHONE_' + rowid).val(html.split("@_@")[3]);
    if (html == '') {
      //$('#SUPPLIER_' + rowid).val('');
      $('#SUPPLIER_' + rowid).focus();
    }
  });
}

function querySuppliers(value) {
	  jQuery.ajax({
	    url : "/iMes/ajax",
	    data : {
	      SUPPLIER : value,
	      action : 'getD301_Supplier'
	    },
	    cache : false
	  }).done(function(html) {
	    $('#SUPPLIER_NAME').val(html.split("@_@")[1]);
	    $('#CONTACT').val(html.split("@_@")[2]);
	    $('#PHONE').val(html.split("@_@")[3]);
	    if (html == '') {
	      $('#SUPPLIER').focus();
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

      $('.SQBM').each(function() {
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
        if ($("#SQBM").val() == '') {
          errNum += 1;
          errTxt += errNum + '. 部門代碼不能為空\n';
        }

        if (errNum > 0) {
          alert(errTxt);
          return false;
        }
        return confirm('請確認資料是否正確并需要建立《外修單 D301》?');
        ;
      });
      
      $("#SBMFRM302").click(function(event) {
          $('#FRM302').submit();
      });
      
      $('#FRM302').submit(function() {
          var errTxt = '';
          var errNum = 0;
          //if ($("#SQBM").val() == '') {
          //  errNum += 1;
          //  errTxt += errNum + '. 部門代碼不能為空\n';
          //}

          //if (errNum > 0) {
          //  alert(errTxt);
          //  return false;
          //}
          return confirm('請確認資料是否正確并需要建立《验收单 D301》?');
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
            $('.SQBM').each(
                function() {
                  if (this.id.split("_")[1] != null) {
                    rowid = this.id.split("_")[1];
                  }

                });
          });

      $("#SBMQHBTN").click(function(event) {
        if (confirm('請確認資料是否要啟動送簽流程?')) {
          $('#SBMQHFRM').submit();
        }
      });

      $("#SBMQHBTN302").click(function(event) {
        if (confirm('請確認資料是否要啟動送簽流程?')) {
          $('#SBMQHFRM302').submit();
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
          url : "/iMes/D301",
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
          url : "/iMes/D301",
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
