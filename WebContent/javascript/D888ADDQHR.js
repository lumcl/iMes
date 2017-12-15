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
	  
	  $("#SBMQHBTN").click(function(event) {
		this.disabled = true;
		if (confirm('請確認是否要加签核人?')) {
			$('#D888QHFRM').submit();
		}
	  });    
});