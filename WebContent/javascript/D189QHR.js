$(document).ready(
    function() {
	  
	  $("#SBMQHBTN").click(function(event) {
		this.disabled = true;
		if (confirm('請確認是否要送簽流程?')) {
			$('#D189QHFRM').submit();
		}
	  });
	  
	  $("#FILEUPLOADERBTN").click(function(event) {
		  $('#SBFILEUPLOADER').submit();
	  });
});