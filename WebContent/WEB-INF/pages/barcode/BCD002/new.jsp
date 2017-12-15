<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
$(document).ready(function() {  
	 $('#bcdnr').focus();
	 $('#bcdnr').keypress(function(event) {
     if(event.keyCode == 13) { 
         nextBox = $('#cbcdnrs');
         nextBox.focus();
         nextBox.select();
         event.preventDefault();
         return false; 
     }
 });
}); 
</script>

<div id="tabs">
	<ul>
		<li><a href="#tab-1">BCD002 Serial Number Create Linkage</a></li>
	</ul>

	<div id="tab-1">
		<form action="/iMes/BCD002/create" method="post">
			<div id="nav">
				<ul>
					<li>
						<button type="submit" id="submit">
							<img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存 Save" />保存 Save
						</button>
					</li>
					<li>
						<button type="button" onclick="$.toExcel('#BCD002_table')">
							<img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />導出Excel
						</button>
					</li>
				</ul>
			</div>

			<table>
				<tr>
					<th>Parent SN#</th>
					<td>
						<input type="text" name="bcdnr" id="bcdnr" style="width: 300px;" value="${param.mattyp_low}">
					</td>
				</tr>
				<!--
				<tr>
				 
				<th>Child Linkage</th>
				<td>
					<textarea rows="20" cols="40" name="cbcdnrs" id="cbcdnrs"></textarea>
				</td>
				</tr>
				 -->
			</table>
			<input type="reset">
			<input type="submit" style="width: 100px; font-size: 30px;">
		</form>



	</div>

</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />