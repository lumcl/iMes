function getDeptName(value) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      KOSTL : value,
      GSDM : $("#GSDM").val(),
      action : 'getDeptName'
    },
    cache : false
  }).done(function(html) {
    $("#BMMC").val(html);
    if (html == '') {
      $("#BMMC").val('');
      $("#BMMC").focus();
    }
  });
}

function getCustomerName(value) {
  jQuery.ajax({
    url : "/iMes/ajax",
    data : {
      KUNNR : value,
      action : 'getCustomerName'
    },
    cache : false
  }).done(function(html) {
    $('#KHBH').val(html);
    if (html == '') {
      $("#KHBH").val('');
      $("#KHBH").focus();
    }
  });
}

$(document).ready(
    function() {		
      $("#BMMC").each(function() {   	  
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
	
      $("#KHBH").each(function() { 
        $(this).autocomplete({
          source : function(request, response) {
            $.getJSON("/iMes/ajax", {
              action : 'getCustomerName'
            }, response);
          },
          delay : 10,
          minLength : 0,
          change : function(event, ui) {
        	getCustomerName($('#KHBH').val());
          }
        });
      });      

      $("#FRM01").submit(function(event) {

      });
      
      $("#KHBH").focus();

    });
