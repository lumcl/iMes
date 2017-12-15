$(document).ready(function() {
	$("#saveHeaderBtn").click(function(event) {
		if (confirm('請確認資料是否正確并需保存?')) {
			$(this).attr('disabled', "true");
			$("#headerForm").submit();
		}
	});
	
	$('#workflowBtn').click(function(){
		if (confirm('請確認是否啟動會簽流程?')) {
			location.href="/iMes/PRD001/startWorkflow?id="+$("#id").val();
		}
	});
	
	$("#headerForm").validate({
		rules : {
			ydsj_xs : {
				range : [ 0, 23 ]
			},
			ydsj_fz : {
				range : [ 0, 59 ]
			},
			ylsj_xs : {
				range : [ 0, 23 ]
			},
			ylsj_fz : {
				range : [ 0, 59 ]
			},
			hqsj_xs : {
				range : [ 0, 23 ]
			},
			hqsj_fz : {
				range : [ 0, 59 ]
			}
		},
		invalidHandler : function(form) {
			$('#saveHeaderBtn').removeAttr("disabled");
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
	
});