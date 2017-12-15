<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D045.js"></script>

<div id="tabs">
  <form action="/iMes/D045/SubmitQH" method="post" id="SBMQHFRM">
   <input type="hidden" name="GSDM" value="${D045.GSDM}">
   <input type="hidden" name="BDDM" value="${D045.BDDM}">
   <input type="hidden" name="BDBH" value="${D045.BDBH}">
   <input type="hidden" name="BDTX" value="dd">
   <input type="hidden" name="BDAMT" value="0">
  </form>
 <ul>
  <li><a href="#tab-1">出廠單</a></li>
 </ul>
  
 <div id="tab-1">
  <form action="/iMes/D045/UPDATE" method="post" id="FRM01">
   <input type="hidden" name="GSDM" value="${D045.GSDM}">
   <input type="hidden" name="BDDM" value="${D045.BDDM}">
   <input type="hidden" name="BDBH" value="${D045.BDBH}">
   <input type="hidden" name="BDTX" value="">
   <input type="hidden" name="BDAMT" value="0">
   <div id="icon">
    
   <c:if test="${QHKS == 'N'}"> 
   <button type="button" onclick="this.disabled=true;this.form.submit();">
    <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
   </button>
   </c:if>
   <button type="reset">
    <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="撤銷" />
   </button>
   
   <c:if test="${((D045.BDBH != '') && (D045.SQYH == sessionScope.uid)) && (QHKS == 'N')}">
    <button type="button" id="SBMQHBTN">
     <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
    </button>
   </c:if>
    
   </div>

   <table>
    <caption>出廠單據</caption>
    <tbody id="OUTFACTORY">
     <tr>
      <th>公司代碼</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L210" <c:if test="${D045.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${D045.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${D045.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
      </select></td>
      <th>表單類型</th>
      <td><select name="BDLX" id="BDLX">
        <option value="成品出廠" <c:if test="${D045.BDLX == '成品出廠'}">selected="selected"</c:if>>成品出廠</option>
        <option value="出售原料" <c:if test="${D045.BDLX == '出售原料'}">selected="selected"</c:if>>出售原料</option>
        <option value="出售樣品" <c:if test="${D045.BDLX == '出售樣品'}">selected="selected"</c:if>>出售樣品</option>
        <option value="借入材料歸還" <c:if test="${D045.BDLX == '借入材料歸還'}">selected="selected"</c:if>>借入材料歸還</option>
        <option value="借出材料" <c:if test="${D045.BDLX == '借出材料'}">selected="selected"</c:if>>借出材料</option>
        <option value="其他" <c:if test="${D045.BDLX == '其他'}">selected="selected"</c:if>>其他</option>
      </select></td>
      <th colspan="2">客戶</th>
      <td colspan="2"><input type="text" name="CUST" id="CUST" value="${D045.CUST}"></td>
     </tr>
     <tr>
      <th>發票號碼</th>
      <td><input type="text" name="INVNO" id="INVNO" value="${D045.INVNO}"></td>
      <th>訂單號</th>
      <td><input type="text" name="CONO" id="CONO" value="${D045.CONO}"></td>
      <th colspan="2">表單日期</th>
      <td colspan="2">${D045.BDRQ}</td>
     </tr>
     <tr>
      <th>交貨地點</th>
      <td colspan="9"><textarea name="DELADD" id="DELADD" cols="100" rows="5">${D045.DELADD}</textarea></td>
     </tr>
     <tr>
      <th>      
   		<c:if test="${QHKS == 'N'}"> 
	      	<div id="icon">    
			   <button type="button" id="INSRLINE">
			    <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="新增" />
			   </button>
	   		</div>
   		</c:if>
      </th>
      <th>规格</th>
      <th>料号</th>
      <th>数量</th>
      <th>单位</th>
      <th>单价</th>
      <th>币别</th>
      <th>单重</th>
     </tr>
     <c:if test="${list.isEmpty()}">
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
     </c:if>
     <c:forEach var="e" items="${list}">
     <tr id="ROWS">
      <td></td>
      <td><input type="text" name="CMAKTX" id="CMAKTX" value="${e.CMAKTX}"></td>
      <td><input type="text" name="CMATNR" id="CMATNR" value="${e.CMATNR}"></td>
      <td><input type="text" name="CCQTY" id="CCQTY" value="${e.CCQTY}"></td>
      <td><input type="text" name="CCUM" id="CCUM" value="${e.CCUM}" size="5"></td>
      <td><input type="text" name="CPRICE" id="CPRICE" value="${e.CPRICE}" size="5"></td>
      <td><input type="text" name="CCURR" id="CCURR" value="${e.CCURR}" size="10"></td>
      <td><input type="text" name="CWEIGHT" id="CWEIGHT" value="${e.CWEIGHT}" size="10"></td>
     </tr>
     </c:forEach>
    </tbody>
   </table>   
   
  </form>
  
  <jsp:include page="/WEB-INF/pages/form/_route.jsp" />

 </div> 
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />