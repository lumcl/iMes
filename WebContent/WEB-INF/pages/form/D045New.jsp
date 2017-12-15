<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D045.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">出廠單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D045/SAVE" method="post" id="FRM01">
   <div id="icon">
    
   <button type="button" onclick="this.disabled=true;this.form.submit();">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
   </button>
   <button type="reset" onclick="location.href='/iMes/D045/CREATE'">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>
    
   </div>

   <table>
    <caption>出廠單據</caption>
    <tbody id="OUTFACTORY">
     <tr>
      <th>公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210">L210 東莞領航</option>
        <option value="L300" selected="selected">L300 東莞立德</option>
        <option value="L400">L400 江蘇領先</option>
      </select></td>
      <th>表單類型</th>
      <td><select name="BDLX" id="BDLX">
        <option value="成品出廠">成品出廠</option>
        <option value="出售原料">出售原料</option>
        <option value="出售樣品">出售樣品</option>
        <option value="借入材料歸還">借入材料歸還</option>
        <option value="借出材料">借出材料</option>
        <option value="其他">其他</option>
      </select></td>
      <th colspan="2">客戶</th>
      <td colspan="2"><input type="text" name="CUST" id="CUST" value=""></td>
     </tr>
     <tr>
      <th>發票號碼</th>
      <td><input type="text" name="INVNO" id="INVNO" value=""></td>
      <th>訂單號</th>
      <td><input type="text" name="CONO" id="CONO" value=""></td>
      <th colspan="2">表單日期</th>
      <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     </tr>
     <tr>
      <th>交貨地點</th>
      <td colspan="9"><textarea name="DELADD" id="DELADD" cols="100" rows="5"></textarea></td>
     </tr>
     <tr>
      <th>
      	<div id="icon">    
		   <button type="button" id="INSRLINE">
		    <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="新增" />
		   </button>
   		</div>
      </th>
      <th>规格</th>
      <th>料号</th>
      <th>数量</th>
      <th>单位</th>
      <th>单价</th>
      <th>币别</th>
      <th>单重</th>
     </tr>
     <tr id="ROWS">
      <td></td>
      <td><input type="text" name="CMAKTX" id="CMAKTX" value=""></td>
      <td><input type="text" name="CMATNR" id="CMATNR" value=""></td>
      <td><input type="text" name="CCQTY" id="CCQTY" value=""></td>
      <td><input type="text" name="CCUM" id="CCUM" value="" size="5"></td>
      <td><input type="text" name="CPRICE" id="CPRICE" value="" size="5"></td>
      <td><input type="text" name="CCURR" id="CCURR" value="" size="10"></td>
      <td><input type="text" name="CWEIGHT" id="CWEIGHT" value="" size="10"></td>
     </tr>
    </tbody>
   </table>
   
   
  </form>

 </div> 
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />