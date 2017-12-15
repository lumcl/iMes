<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D300.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">派工令異動申請單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D300/SAVE" method="post" id="FRMNEW01">
   <div id="icon">
    
   <button type="button" id="SBMFRM0001">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
   </button>
   <button type="reset" onclick="location.href='/iMes/D300/CREATE'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>
    
   </div>

   <table style="width: 1050px;">
    <caption>派工令異動申請單</caption>
    <tbody>
     <tr>
      <th>公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210">L210 東莞領航</option>
        <option value="L300" selected="selected">L300 東莞立德</option>
        <option value="L400">L400 江蘇領先</option>
        <option value="L921">L921 日本立德</option>
      </select></td>
      <th>派工類型</th>
      <td  colspan="3"><input type="checkbox" id="QCLX" name="QCLX" value="1" checked>1. 欠料
      	<input type="checkbox" id="QCLX" name="QCLX" value="2">2. 品質異常
      	<input type="checkbox" id="QCLX" name="QCLX" value="3">3. 材料異常
      	<input type="checkbox" id="QCLX" name="QCLX" value="4">4. 試產異常
      	<input type="checkbox" id="QCLX" name="QCLX" value="5">5. 樣品異常
      	<input type="checkbox" id="QCLX" name="QCLX" value="6">6. 其他異常
	  </td>
      <th>MO編號</th>
      <td><input type="text" name="MOBM" id="MOBM" class="required" value=""></td>
     </tr>
     <tr>
      <th>生產單位代碼</th>
      <td><input type="text" name="BMMC" id="BMMC" class="required" value=""></td>
      <th>生產單位名稱</th>
      <td><input type="text" name="SCDW" id="SCDW" class="required" value="" readonly="readonly"></td>
      <th>上線日期</th>
      <td><input type="text" name="SXRQ" id="SXRQ" class="required datepick" value="" readonly="readonly"/></td>
      <th>訂單數量</th>
      <td><input type="text" name="DDSL" id="DDSL" class="required" value="" /></td>
     </tr>
     <tr>
      <th>客戶編碼</th>
      <td><input type="text" name="KHBM" id="KHBM" class="required" value="" onchange="getSupplier()"/></td>
      <th>客戶名稱</th>
      <td><input type="text" name="KHMC" id="KHMC" class="required" value="" /></td>
      <th>設計編號</th>
      <td><input type="text" name="SJBH" id="SJBH" class="required" value=""/></td>
      <th>出貨日期</th>
      <td><input type="text" name="CHRQ" id="CHRQ" class="required datepick" value="" readonly="readonly"/></td>     
     </tr>
    <tr>
     <th>表單編號</th>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <th>申請人</th>
     <td><input type="text" name="SQYH" id="SQYH" value="${sessionScope.uid}" readonly="readonly"/></td>     
     <th>表單日期</th>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <th>表單附檔</th>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
     <th>異動原因</th>
     <td colspan="8"><textarea name="QCNR" id="QCNR" rows="150" cols="1050" ></textarea>
    	<script type="text/javascript">  
        	window.onload = function(){  
            	CKEDITOR.replace('QCNR');  
        	}  
    	</script>  
		</td>
    </tr>
    </tbody>
   </table>
  </form>

 </div> 
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />