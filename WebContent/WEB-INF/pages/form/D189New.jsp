<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D189.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function save() {
    var errTxt = '';
    var errNum = 0;
    if ($("#BMMC").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 歸屬部門不能為空\n';
    }
      
    if ($("#KHBH").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 客戶編號不能為空\n';
    }
      
    if ($("#CQTY").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 重工数量\n';
    }
    
    if ($("#CHSJ").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 出货日期\n';
    }
    
    if ($("#SJBH").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 设计编号\n';
    }
      
    if ($("#KCCB").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 库存仓别\n';
    }
    
    if ($("#CGYY").val() == '') {
        errNum += 1;
        errTxt += errNum + '. 重工原因说明\n';
    }

    if (errNum > 0) {
      alert(errTxt);
      return false;
    }
      
    if (confirm('請確認資料是否正確并需要更新《重工申請單》?')) {
    	this.disabled=true;
        $('#FRM01').submit();
    }else{    	
    	this.disabled = false;
    	return false;
    }
}
$(document).ready(
	function(){

	});
</script>
<div id="tabs">
 <ul>
  <li><a href="#tab-1">Heavy Requisition<br />重工申請單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D189/SAVE" method="post" id="FRM01">
   <div id="icon">
    
   <button type="button" onclick="save()">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="save 保存" />
   </button>
   <button type="reset" onclick="location.href='/iMes/D189/CREATE'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="created 撤銷" />
   </button>
    
   </div>

   <table style="width: 1150px;">
    <caption>Heavy Requisition<br />重工單據</caption>
    <tbody>
     <tr>
      <th>Company Code <br />公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210">L210 DongGuan 東莞領航</option>
        <option value="L300" selected="selected">L300 DongGuan Leader 東莞立德</option>
        <option value="L400">L400 JiangSu 江蘇領先</option>
        <option value="L921">L921 Japanese 日本立德</option>
        <option value="L111">L111 Philippines</option>
      </select></td>
      <th>Stock warehouse Do<br />库存仓别</th>
      <td><select name="KCCB">
        <option value="381A">381A</option>
        <option value="281A">281A</option>
        <option value="481A">481A</option>
        <option value="701A">701A</option>
        <option value="482A">482A</option>
        <option value="101A">101A</option>
        <option value="111A">111A</option>
      </select></td>
      <th>Customer Number<br />客户编号</th>
      <td><input type="text" name="KHBH" id="KHBH" value=""></td>
     </tr>
     <tr>
      <th>Design No.<br />设计编号</th>
      <td><input type="text" name="SJBH" id="SJBH" value=""></td>
      <th>The home department<br />归属部門</th>
      <td><input type="text" name="BMMC" id="BMMC" value=""></td>
      <th>Heavy quantity<br />重工数量</th>
      <td><input type="text" name="CQTY" id="CQTY" value=""/></td>
      </tr>
     <tr>
      <th>Ship date<br />出货日期</th>
      <td><input type="text" name="CHSJ" id="CHSJ" value="" size="10" class="datepick"></td>
      <th>Form Number<br />表單編號</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <th>Forms attached file<br />表單附檔</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
	<tr>
      <th>Form date<br />表單日期</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <th>Heavy briefing<br />重工說明會</th>
      <td colspan="3"><input type="radio" name="SMH" value="1" checked>Not required 不需要  
				<input type="radio" name="SMH" value="0">Need 需要</td>
    </tr>
    <tr>
     <th>Heavy Reason Description<br />重工原因说明</th>
     <td colspan="6"><textarea name="CGYY" id="CGYY" rows="15" cols="150" ></textarea>
		</td>
    </tr>
    <tr>
     <th>Heavy handling instructions<br />重工处理方式说明</th>
     <td colspan="6"><textarea name="CGFS" id="CGFS" rows="15" cols="150" ></textarea>
		</td>
    </tr>
    </tbody>
   </table>
  </form>

 </div> 
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />