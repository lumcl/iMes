$(document).ready(
    function() {
	  
	  $("#SBMQHBTN").click(function(event) {
		this.disabled = true;
		if (confirm('請確認是否要加签核人?')) {
			$('#D189QHFRM').submit();
		}
	  });    
});