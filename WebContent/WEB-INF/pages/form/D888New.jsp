<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D888.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">出廠單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D888/SAVE" method="post" id="FRM01">
   <div id="icon">
    
   <button type="button" onclick="this.disabled=true;this.form.submit();">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
   </button>
   <button type="reset" onclick="location.href='/iMes/D888/CREATE'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>
    
   </div>

   <table style="width: 1050px;">
    <caption>出廠單單據</caption>
    <tbody>
     <tr>
      <th>公司<br />代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210">L210 東莞領航</option>
        <option value="L300" selected="selected">L300 東莞立德</option>
        <option value="L400">L400 江蘇領先</option>
      </select></td>
      <th>出廠<br />類型</th>
      <td><select name="CCLX">
        <option value="成品出廠">成品出廠</option>
        <option value="SW樣品出廠">SW樣品出廠</option>
        <option value="LINEAR樣品出廠">LINEAR樣品出廠</option>
        <option value="材料樣品出廠">材料樣品出廠</option>
        <option value="品保樣品出廠">品保樣品出廠</option>
        <option value="安规樣品出廠">安规樣品出廠</option>
        <option value="材料調撥出廠">材料調撥出廠</option>
        <option value="固定資產出廠">固定資產出廠</option>
        <option value="材料退貨出廠">材料退貨出廠</option>
        <option value="材料超交出廠">材料超交出廠</option>
        <option value="資產報廢出廠">資產報廢出廠</option>
        <option value="資產異動出廠">資產異動出廠</option>
        <option value="資產借出出廠">資產借出出廠</option>
        <option value="設備校驗出廠">設備校驗出廠</option>
        <option value="設備外修出廠">設備外修出廠</option>
        <option value="會辦賬務出廠">會辦賬務出廠</option>
        <option value="雜項物品出廠">雜項物品出廠</option>
        <option value="下腳料出廠">下腳料出廠</option>
        <option value="下腳料出售出廠">下腳料出售出廠</option>
        <option value="其它物品出廠">其它物品出廠</option>
        <option value="其他出廠">其他出廠</option>
      </select></td>
      <th>財產<br />編號</th>
      <td><input type="text" name="CCBH" id="CCBH" value=""></td>
      <th>領料<br />編號</th>
      <td><input type="text" name="LLBH" id="LLBH" value=""></td>
      <th>供應商</th>
      <td><input type="text" name="GYSM" id="GYSM" value=""></td>
     </tr>
     <tr>
      <th>部門<br />代碼</th>
      <td><input type="text" name="BMBH" id="BMBH" value=""></td>
      <th>部門<br />名稱</th>
      <td><input type="text" name="BMMC" id="BMMC" value="" readonly="readonly"></td>
      <th>總金額</th>
      <td><input type="text" name="ZJE" id="ZJE" value="">元</td>
      <th>跟蹤</th>
      <td><select name="BDGZ">
        <option value="返廠">返廠</option>
        <option value="不返廠">不返廠</option>
        <option value="立德-領航">立德-領航</option>
        <option value="領航-立德">領航-立德</option>
      </select></td>
      <th>表單<br />編號</th>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     </tr>
    <tr>
     <th>退料<br />編號</th>
     <td><input type="text" name="TLBH" id="TLBH" value=""/></td>
     <th>物料<br />編號</th>
     <td><input type="text" name="WLBH" id="WLBH" value=""></td>
     <th>訂單<br />編號</th>
     <td><input type="text" name="DDBH" id="DDBH" value=""></td>
     <th>表單<br />日期</th>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>            
     <th>表單<br />附檔</th>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
     <th>出廠<br />內容</th>
     <td colspan="10"><textarea name="CCNR" id="CCNR" rows="150" cols="1050" >
     <p>&nbsp;</p>
     <table width="630" cellspacing="0" cellpadding="0" border="1">
    <colgroup><col width="72" style="width:54pt" />  <col width="112" style="mso-width-source:userset;mso-width-alt:3584;width:84pt" />  <col width="72" span="4" style="width:54pt" />  <col width="158" style="mso-width-source:userset;mso-width-alt:5056;width:119pt" />  </colgroup>
    <tbody>
        <tr height="18" style="height:13.5pt">
            <td width="72" height="18" style="height:13.5pt;width:54pt" class="xl64">規格</td>
            <td width="112" style="border-left:none;width:84pt" class="xl64">品名</td>
            <td width="112" style="border-left:none;width:84pt" class="xl64">供應商</td>
            <td width="112" style="border-left:none;width:84pt" class="xl64">料號</td>
            <td width="72" style="border-left:none;width:54pt" class="xl64">數量</td>
            <td width="72" style="border-left:none;width:54pt" class="xl64">單位</td>
            <td width="72" style="border-left:none;width:54pt" class="xl64">幣別</td>
            <td width="72" style="border-left:none;width:54pt" class="xl64">金額</td>
            <td width="158" style="border-left:none;width:119pt" class="xl64">備註</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
        <tr height="18" style="height:13.5pt">
            <td height="18" style="height:13.5pt;border-top:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
            <td style="border-top:none;border-left:none" class="xl63">　</td>
        </tr>
    </tbody>
</table>
     
     
     
     </textarea>
     	<script type="text/javascript">  
        	window.onload = function(){  
            	CKEDITOR.replace('CCNR');  
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