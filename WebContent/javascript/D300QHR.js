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
	  
	  $("#SBMQHBTN").click(function(event) {
		this.disabled = true;
		if (confirm('請確認是否要送簽流程?')) {
			$('#D300QHFRM').submit();
		}
	  });
	  
	  $("#FILEUPLOADERBTN").click(function(event) {
		  $('#SBFILEUPLOADER').submit();
	  });
});