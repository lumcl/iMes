<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D400.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">D400 簽呈</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D400/SAVE" method="post" id="FRM01">
   <div id="icon">
    
   <button type="button" onclick="this.disabled=true;this.form.submit();">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存 Save" />
   </button>
   <button type="reset" onclick="location.href='/iMes/D400/CREATE'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷  Reset" />
   </button>
    
   </div>

   <table style="width: 1050px;">
    <caption>簽呈單據 D400 Form</caption>
    <tbody>
     <tr>
      <th>公司代碼<br />Company</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210">L210 東莞領航</option>
        <option value="L300" selected="selected">L300 東莞立德</option>
        <option value="L400">L400 江蘇領先</option>
        <option value="L921">L921 日本立德</option>
        <option value="L111">L111 菲律宾立德</option>
      </select></td>
      <th>簽呈類型<br />DocType</th>
      <td><select name="QCLX">
        <option value="人事">人事 HR</option>
        <option value="獎懲">獎懲 Reward and punishment</option>
        <option value="規章">規章Regulations</option>
        <option value="異常">異常Abnormal</option>
        <option value="一般">一般Commonly</option>
      </select></td>
      <th colspan="2">文件屬性<br />DocAttributes</th>
      <td><select name="WJSX">
       <option value="普通件">普通件General</option>
       <option value="急件">急件Dispatch</option>
       </select></td>
      <th>機密<br />Confidential</th>
      <td><select name="WJJM">
       <option value="非機密">非機密Non Confidential</option>
       <option value="機密">機密Confidential</option>
       </select></td>
     </tr>
     <tr>
      <th>部門代碼<br />Dept.</th>
      <td><input type="text" name="BMMC" id="BMMC" value=""></td>
      <th>部門名稱<br />Dept. Name</th>
      <td><input type="text" name="BMMC1" id="BMMC1" value=""></td>
      <th colspan="2">表單編號<br />DocNumber</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>      
      <th>表單日期<br />DocDate</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     </tr>
    <tr>
     <th>案由<br />Subject</th>
     <td colspan="6"><input type="text" name="QCAY" id="QCAY" style="width:500px;" value=""/></td>            
     <th>表單附檔<br />Attached</th>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
     <th>簽呈內容<br />DocContent</th>
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