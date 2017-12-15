function getSupplier() {
  jQuery.ajax({
    url : "/iMes/D089/GETSUPPLIER",
    data : {
      lifnr : $('#LIFNR').val(),
    },
    cache : false
  }).done(function(html) {
    if (html == ',') {
      alert("Invalid Supplier Code!");
      $('#NAME1').val('');
      $('#SORTL').val('');
      $('#MWSKZ').val('');
      $('#WAERS').val('');
      $('#LIFNR').val('');
      $('#EKORG').val('');
      $('#GSDM').val('');
      $('#WERKS'), val('');
      $('#LIFNR').focus();
    } else {
      var buf = html.split(',');
      $('#NAME1').val(buf[0]);
      $('#SORTL').val(buf[1]);
      $('#MWSKZ').val(buf[2]);
      $('#WAERS').val(buf[3]);
      $('#GSDM').val(buf[4]);
      $('#EKORG').val(buf[4]);
      $('#WERKS').val(buf[5]);
    }
    ;
  });
}

function deleteLine(id){
  var dlttx = prompt('請輸入刪除原因及說明,\nPlease enter delete reason and description', '');
  if (dlttx != null && dlttx.length > 0){
    $('#RID').val(id);
    $('#DLTTX').val(dlttx);
    $('#fDeleteLine').submit();
  }
}

function getPriceHistory(id, matnr, rmbpr) {
  if (document.getElementById("P" + id)) {
    $('#P' + id).remove();
  } else {
    jQuery.ajax({
      url : "/iMes/D089/GETPRICEHISTORY",
      data : {
        MATNR : matnr,
        RMBPR : rmbpr
      },
      cache : false
    }).done(function(html) {
      htmlStr = "<tr id='P" + id + "'><td colspan='25'>" + html + "</td></tr>";
      $('#' + id).after(htmlStr);
    });
  }
}

function getOpenPO(id, matnr, lifnr, rmbpr) {
  if (document.getElementById("O" + id)) {
    $('#O' + id).remove();
  } else {
    jQuery.ajax({
      url : "/iMes/D089/GETOPENPO",
      data : {
        MATNR : matnr,
        LIFNR : lifnr,
        RMBPR : rmbpr
      },
      cache : false
    }).done(function(html) {
      htmlStr = "<tr id='O" + id + "'><td colspan='25'>" + html + "</td></tr>";
      $('#' + id).after(htmlStr);
    });
  }
}

function checkEkorg() {
  if ($('#EKORG').val() == '') {
    alert("請選擇採購組織\n[Please select purchase organisation code]");
    $('#EKORG').focus();
  }
}

function toggleInputField(obj) {
  if ($(obj).attr("checked") == "checked") {
    $("#DATAB_" + obj.value).removeAttr("disabled");
    $("#PRDAT_" + obj.value).removeAttr("disabled");
    $("#CHGPO_" + obj.value).removeAttr("disabled");
    $("#DLVDT_" + obj.value).removeAttr("disabled");
    $("#PODAT_" + obj.value).removeAttr("disabled");
  } else {
    $("#DATAB_" + obj.value).attr('disabled', "disabled");
    $("#PRDAT_" + obj.value).attr('disabled', "disabled");
    $("#CHGPO_" + obj.value).attr('disabled', "disabled");
    $("#DLVDT_" + obj.value).attr('disabled', "disabled");
    $("#PODAT_" + obj.value).attr('disabled', "disabled");
  }
}

$(document).ready(function() {

  $('.datepick').each(function() {
    $(this).datepicker({
      dateFormat : 'yymmdd',
      changeMonth : true,
      changeYear : true
    }).bind('keydown', false);
  });

  $("#saveBtn").click(function(event) {
    $('#saveBtn').attr('disabled', "true");
    $('#saveBtn').submit();
  });

  $('#saveBtn').submit(function() {
    return confirm('請確認資料是否正確并需保存?\n Please confirmed the form data.');
  });

  $("#saveForm").validate({
    invalidHandler : function(form) {
      $('#saveBtn').removeAttr("disabled");
    },
    submitHandler : function(form) {
      form.submit();
    }
  });

  /*
   * Material List Section matEditForm
   */
  $("#matEditBtn").click(function(event) {
    $('#matEditBtn').attr('disabled', "true");
    $('#matEditBtn').submit();
  });

  $('#matEditBtn').submit(function() {
    return confirm('請確認資料是否正確并需保存?\n Please confirmed the form data.');
  });

  $("#matEditForm").validate({
    invalidHandler : function(form) {
      $('#matEditBtn').removeAttr("disabled");
    },
    submitHandler : function(form) {
      form.submit();
    }
  });

  /*
   * Upload Tab Section matUploadBtn
   */
  $("#matUploadBtn").click(function(event) {
    $('#matUploadBtn').attr('disabled', "true");
    $('#matUploadBtn').submit();
  });

  $('#matUploadBtn').submit(function() {
    return confirm('請確認資料是否正確并需保存?\n Please confirmed the form data.');
  });

  $("#matUploadForm").validate({
    invalidHandler : function(form) {
      $('#matUploadBtn').removeAttr("disabled");
    },
    submitHandler : function(form) {
      form.submit();
    }
  });
});