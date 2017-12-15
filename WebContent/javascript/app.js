jQuery.toExcel = function(table) {
	if (table) {
		$('#htmlStrInput').val($(table).html());
		$('#excelform').submit();
	}
};
// 去除字符串空格
/** * Start of Modify ** */
function ltrim(str) {
	/*
	 * Trims leading spaces - returns string Returns trimmed string
	 */
	return str.replace(/^\s+/, '');
}// eof - ltrim

function rtrim(str) {
	/*
	 * Trims trailing spaces - returns string Returns trimmed string
	 */
	return str.replace(/\s+$/, '');
}// eof - rtrim

function alltrim(str) {
	/*
	 * Trims leading and trailing spaces Returns trimmed string
	 */
	return str.replace(/^\s+|\s+$/g, '');
}// eof - alltrim

function split(val) {
	return val.split(/,\s*/);
}

function extractLast(term) {
	return split(term).pop();
}

$(document).ready(function() {
	
	$(":text").change(function(){
		this.value = this.value.toUpperCase();
	});
	
	$("#tabs").tabs();
	
	$("#AppTcode_form").submit(function(event) {
		if ($('#AppTcode').val() == '') {
			alert("TCODE 不能為空!");
			return false;
		} else {
			$('#AppTcode_form').submit();
		}
	});
	
	$('.datepick').each(function() {
		$(this).datepicker({
			dateFormat : 'yymmdd',
			changeMonth : true,
			changeYear : true
		}).bind('keydown', false);
	});
	
	$('.users').each(function() {
		$(this).bind("keydown", function(event) {
			if (event.keyCode == '13') {
				return false;
			} else if (event.keyCode === $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
				event.preventDefault();
			}
		}).autocomplete({
			delay : 0,
			source : function(request, response) {
				$.getJSON("/iMes/ajax", {
					term : extractLast(request.term),
					action : 'getUseridJson'
				}, response);
			},
			search : function() {
				// custom minLength
				var term = extractLast(this.value);
				if (term.length < 1) {
					return false;
				}
			},
			focus : function() {
				// prevent value inserted on focus
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				// remove the current input
				terms.pop();
				// add the selected item
				terms.push(ui.item.value);
				// add placeholder to get the comma-and-space at the end
				terms.push("");
				this.value = terms.join(",");
				return false;
			}
		});
		
	});
	
	$('.depts').each(function() {
		$(this).bind("keydown", function(event) {
			if (event.keyCode == '13') {
				return false;
			} else if (event.keyCode === $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
				event.preventDefault();
			}
		}).autocomplete({
			source : function(request, response) {
				$.getJSON("/iMes/ajax", {
					action : 'getDeptJson',
					gsdm : $('#gsdm').val(),
					term : encodeURI(alltrim(request.term), "utf-8")
				}, response);
			},
			delay : 10,
			minLength : 0,
			change : function(event, ui) {
				this.value = ui.item.label;
			}
		});
	});
	
});

jQuery.extend(jQuery.validator.messages, {
  required: "必輸",
  remote: "Please fix this field.",
  email: "Please enter a valid email address.",
  url: "Please enter a valid URL.",
  date: "Please enter a valid date.",
  dateISO: "Please enter a valid date (ISO).",
  number: "Please enter a valid number.",
  digits: "Please enter only digits.",
  creditcard: "Please enter a valid credit card number.",
  equalTo: "Please enter the same value again.",
  accept: "Please enter a value with a valid extension.",
  maxlength: jQuery.validator.format("Please enter no more than {0} characters."),
  minlength: jQuery.validator.format("Please enter at least {0} characters."),
  rangelength: jQuery.validator.format("Please enter a value between {0} and {1} characters long."),
  range: jQuery.validator.format("Please enter a value between {0} and {1}."),
  max: jQuery.validator.format("Please enter a value less than or equal to {0}."),
  min: jQuery.validator.format("Please enter a value greater than or equal to {0}.")
});

var ajaxLoading = '<img src="/iMes/stylesheet/icons/AJAX.GIF" alt="Loading..." />';