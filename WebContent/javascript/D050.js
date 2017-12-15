function ajaxGetCustomer() {
  jQuery.ajax({
    url : "/iMes/D050/ajaxGetCustomer",
    data : {
      kunnr : $('#kunnr').val(),
    },
    cache : false
  }).done(function(html) {
    if (html == ',') {
      alert("客戶不存在, Invalid Customer Code!");
      $('#name1').val('');
      $('#sortl').val('');
			$('#kunnr').focus();
			$('#kunnr').select();
    } else {
      var buf = html.split(',');
      $('#kunnr').val($('#kunnr').val().toUpperCase());
      $('#name1').val(buf[0]);
      $('#sortl').val(buf[1]);
			$('#bdyy').focus();
			$('#bdyy').select();
    }
    ;
  });
}

$(document).ready(function(){
	$("#saveHeaderBtn").click(function(event) {
		if (confirm('請確認資料是否正確并需保存?')) {
			$(this).attr('disabled', "true");
			$("#headerForm").submit();
		}
	});
});