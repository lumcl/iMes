$(document).ready(function() {
  $.validator.addMethod("validTo", function(value, element) {
    if (parseInt($('#yxtd').val()) < parseInt($('#yxfd').val())) {
      return false;
    } else
      return true;
  }, "Valid to date must be later then valid from date");

  $("#saveForm").validate({
    invalidHandler : function(form) {
      $('#saveBtn').removeAttr("disabled");
    },
    submitHandler : function(form) {
      form.submit();
    },
    rules : {
      yxtd : {
        required : true,
        validTo : true,
        remote : {
          url : "/iMes/DELEGATE/validateDate",
          data : {
            yxfd : function(){
              return $("#yxfd").val();
            },
            yxtd : function(){
              return $("#yxtd").val();
            }
          }
        }
      },
      yhto : {
        required : true,
        remote : {
          url : "/iMes/DELEGATE/validateUserId",
          data : {
            userid : function() {
              return $("#yhto").val();
            }
          }
        }
      }
    },
    messages : {
      yxtd : {
        remote : "Date range already defined and exists"
      },
      yhto : {
        remote : "Invalid User id"
      }
    }
  });

  $('.datepick').each(function() {
    $(this).datepicker({
      dateFormat : 'yymmdd',
      changeMonth : true,
      changeYear : true
    }).bind('keydown', false);
  });

  $('#yhto').each(function() {
    $(this).autocomplete({
      source : function(request, response) {
        $.getJSON("/iMes/ajax", {
          action : 'getUseridJson',
          term : encodeURI(request.term, "utf-8")
        }, response);
      },
      delay : 10,
      minLength : 0
    });
  });

  $("#saveBtn").click(function(event) {
    $('#saveBtn').attr('disabled', "true");
    $('#saveBtn').submit();
  });

  $('#saveBtn').submit(function() {
    return confirm('請確認資料是否正確并需要設置代理人?\n Please confirmed the delegation.');
  });

});