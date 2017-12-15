<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D301.js"></script>
<script type="text/javascript" src="/iMes/ckeditor/ckeditor.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">外修單</a></li>
  <li><a href="#tab-2">外修验收单</a></li>
 </ul>

 <div id="tab-1">
  <form action="D301" method="post" id="SBMQHFRM">
   <input type="hidden" name="action" value="SubmitQH">
   <input type="hidden" name="GSDM" value="${HD.GSDM}">
   <input type="hidden" name="BDDM" value="${HD.BDDM}">
   <input type="hidden" name="BDBH" value="${HD.BDBH}">
   <input type="hidden" name="ZCCB" value="${HD.ZCCB}">
   <input type="hidden" name="BDAMT" value="${HD.BDAMT}">
   <input type="hidden" name="BDTX" value="外修单">
  </form>

  <form action="D301" method="post" id="FRM01">
   <c:if test="${HD.BDBH == null || HD.BDBH == ''}">
    <input type="hidden" name="action" value="CreateHeader">
   </c:if>
   <c:if test="${HD.BDBH != null && HD.BDBH != ''}">
    <input type="hidden" name="action" value="UpdateHeader">
    <input type="hidden" name="GSDM" value="${HD.GSDM}">
    <input type="hidden" name="BDDM" value="${HD.BDDM}">
    <input type="hidden" name="BDBH" value="${HD.BDBH}">
   </c:if>
   <div id="icon">

    <c:if test="${HD.BDBH == null ||HD.BDBH == '' || ((HD.BDBH != '') && (HD.SQYH == sessionScope.uid))}">
     <button type="button" id="SBMFRM01">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
    </c:if>

    <c:if test="${((HD.BDBH != '') && (HD.SQYH == sessionScope.uid) && (QHKS == 'N'))}">
     <button type="button" id="SBMQHBTN">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
     </button>
    </c:if>

   </div>

   <table>
    <caption>外修料單據</caption>
    <tbody>
     <tr>
      <th>公司代碼<br/>Company</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L111" <c:if test="${HD.GSDM == 'L111'}">selected="selected"</c:if>>L111 PH Leader</option>
        <option value="L210" <c:if test="${HD.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${HD.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${HD.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
      </select></td>
      <th>表單編號<br/>Docno</th>
      <td>${HD.BDBH}</td>
      <th>表單日期<br/>Date</th>
      <td>${HD.BDRQ}</td>
      <th>类型</th>
      <td><select name="ZCLX" id="ZCLX">
        <option value="" <c:if test="${HD.ZCLX == ''}">selected="selected"</c:if>>請選擇</option>
        <option value="A. 维修" <c:if test="${HD.ZCLX == 'A. 维修' || HD.ZCLX == '' || HD.ZCLX == null}">selected="selected"</c:if>>A. 维修</option>
        <option value="B. 其它" <c:if test="${HD.ZCLX == 'B. 其它'}">selected="selected"</c:if>>B. 其它</option>
      </select></td>      
     </tr>

     <tr>
      <th>资产编号</th>
      <td><input type="text" name="ZCBH" id="ZCBH" value="${HD.ZCBH}" class="KOSTL" onchange="getZCInfor(this.value)"></td>
      <th>资产名称</th>
      <td><input type="text" name="ZCMC" id="ZCMC" value="${HD.ZCMC}" class="KOSTL"></td>
      <th>资产成本中心</th>
      <td><input type="text" name="ZCCB" id="ZCCB" value="${HD.ZCCB}" class="KOSTL"></td>
      <th>资产保管部门</th>
      <td><input type="text" name="ZCBG" id="ZCBG" value="${HD.ZCBG}" class="KOSTL"></td>
     </tr>
     <tr>
      <th>帳面價值</th>
      <td><input type="text" name="ZMJZ" id="ZMJZ" value="${HD.ZMJZ}" class="KOSTL"></td>
      <th>保管人</th>
      <td><input type="text" name="BGR" id="BGR" value="${HD.BGR}" class="KOSTL"></td>
      <th>數量</th>
      <td><input type="text" name="QTY" id="QTY" value="${HD.QTY}" class="KOSTL"></td>
      <th>資產取得日期</th>
      <td><input type="text" name="ZBDT" id="ZBDT" value="${HD.ZBDT}" class="KOSTL"></td>
     </tr>
     <tr>
      <th>取得價值</th>
      <td><input type="text" name="QDJZ" id="QDJZ" value="${HD.QDJZ}" class="KOSTL"></td>
      <th>資產存放地點</th>
      <td  colspan="7"><input type="text" name="ADDR" id="ADDR" value="${HD.ADDR}" class="KOSTL"></td>
     </tr>
     <tr>
      <th>部門代碼<br/>Dept</th>
      <td><input type="text" name="SQBM" id="SQBM" value="${HD.SQBM}" class="KOSTL" onchange="getDeptName(this.value)"></td>
      <th>部門名稱<br/>Name</th>
      <td><input type="text" name="BMMC" id="BMMC" value="${HD.BMMC}" class="KOSTL"></td>
      <th>表單附檔<br/>Attachment</th>
      <c:if test="${HD.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${HD.BDFD}">下載</a></td>
      </c:if>
      <c:if test="${HD.BDFD.length()== null ||HD.BDFD.length()==0 }">
       <td></td>
      </c:if>
      <th>表單狀態<br/>Status</th>
      <td>${HD.BDJG} / ${HD.BDZT}</td>
     </tr>
     <tr>
      <th>申請者<br/>Requester</th>
      <td>${HD.SQYH}</td>
      <th>核准者<br/>Approver</th>
      <td>${HD.QHYH}</td>
      <th>核准時間<br/>Aprv time</th>
      <td colspan="3">${HD.QHSJ}</td>
     </tr>
    <tr>
     <th>故障原因</th>
     <td colspan="8"><textarea name="QCNR" id="QCNR" rows="150" cols="1050" >${HD.QCNR}</textarea>
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

  <c:if test="${((HD.BDBH != '') && (HD.SQYH == sessionScope.uid) && (QHKS == 'N'))}">
   <form action="FileUploader" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="Document" />
    <input type="hidden" name="GSDM" value="${HD.GSDM}">
    <input type="hidden" name="BDDM" value="${HD.BDDM}">
    <input type="hidden" name="BDBH" value="${HD.BDBH}">
    <br />
    <table>
     <tr>
      <th colspan="2" align="center">文件上傳<br>Upload file</th>
     </tr>
     <tr>
      <td><input type="file" name="file" style="font-size: large;" /></td>
      <td>
       <button type="button" onclick="this.disabled=true;this.form.submit();">
        <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
       </button>
      </td>
     </tr>
    </table>
   </form>
  </c:if>
    
  <form action="D301" method="post" id="FRM04">
   <input type="hidden" name="action" value="UpdateResp">
   <input type="hidden" name="GSDM" value="${HD.GSDM}">
   <input type="hidden" name="BDDM" value="${HD.BDDM}">
   <input type="hidden" name="BDBH" value="${HD.BDBH}">
   <div id="icon">
    <c:if test="${PURUSER=='Y' }">
     <button type="button" id="SBMFRM04">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
    </c:if>
   </div>

   <table>
    <caption>报价</caption>
    <thead>
     <tr>
      <th>序號<br>Seq</th>
      <th>供應商代碼<br>Supplier</th>
      <th>供應商<br>Supplier</th>
      <th>联系人<br>Contact</th>
      <th>联系电话<br>Phone</th>
      <th>报价<br>Price</th>
      <th>单位<br>Unit</th>
      <th>数量<br>Quality</th>
      <th>币别<br>Currency</th>
      <th>附檔<br/>Attachment</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${RS}">
      <input type="hidden" name="SEQ" value="${e.SEQ}">
      <tr>
       <td>${e.SEQ}</td>
       <td><input type="text" id="SUPPLIER_${e.SEQ}" name="SUPPLIER_${e.SEQ}" size="12" value="${e.SUPPLIER}" class="KOSTL" onchange="getSupplier(this.value,${e.SEQ})"></td>
       <td><input type="text" id="SUPPLIER_NAME_${e.SEQ}" name="SUPPLIER_NAME_${e.SEQ}" size="12" value="${e.SUPPLIER_NAME}" class="KOSTL"></td>
       <td><input type="text" id="CONTACT_${e.SEQ}" name="CONTACT_${e.SEQ}" size="12" value="${e.CONTACT}"></td>
       <td><input type="text" id="PHONE_${e.SEQ}" name="PHONE_${e.SEQ}" size="12" value="${e.PHONE}"></td>
       <td><input type="text" id="PRICE_${e.SEQ}" name="PRICE_${e.SEQ}" size="20" value="${e.PRICE}" tabindex="99"></td>
       <td><input type="text" id="UNIT_${e.SEQ}" name="UNIT_${e.SEQ}" size="12" value="${e.UNIT}"></td>
       <td><input type="text" id="QUALITY_${e.SEQ}" name="QUALITY_${e.SEQ}" size="5" value="${e.QUALITY}" onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
       <td><input type="text" id="CURRENCY_${e.SEQ}" name="CURRENCY_${e.SEQ}" size="12" value="${e.CURRENCY}"></td>
       <td>
  		<c:if test="${e.BDFD != ''}">
  			<a href="/iMes/FileDownloader?filePath=${e.BDFD}">下載</a>
  		</c:if>
	   </td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </form>

  <c:if test="${PURUSER=='Y' }">
  <c:forEach var="e" items="${RS}">
   <form action="FileUploader" method="post" enctype="multipart/form-data" id='${e.SEQ}'>
   <c:if test="${e.SUPPLIER != null && e.SUPPLIER != '' }">
    <input type="hidden" name="action" value="AttachmentD301R" />
    <input type="hidden" name="GSDM" value="${HD.GSDM}">
    <input type="hidden" name="BDDM" value="${HD.BDDM}">
    <input type="hidden" name="BDBH" value="${HD.BDBH}">
    <input type="hidden" name="SEQ" value="${e.SEQ}">
    <br />
    <table>
     <tr>
      <th>序號<br>Seq</th>
      <th>供應商代碼<br>Supplier</th>
      <th>供應商<br>Supplier</th>
      <th>联系人<br>Contact</th>
      <th>联系电话<br>Phone</th>
      <th>报价<br>Price</th>
      <th>单位<br>Unit</th>
      <th>数量<br>Quality</th>
      <th>币别<br>Currency</th>
      <th colspan="2" align="center">文件上傳<br>Upload file</th>
     </tr>
     <tr>
      <td>${e.SEQ}</td>
      <td>${e.SUPPLIER}</td>
      <td>${e.SUPPLIER_NAME}</td>
      <td>${e.CONTACT}</td>
      <td>${e.PHONE}</td>
      <td>${e.PRICE}</td>
      <td>${e.UNIT}</td>
      <td>${e.QUALITY}</td>
      <td>${e.CURRENCY}</td>
      <td><input type="file" name="file" style="font-size: large;" /></td>
      <td>
       <button type="button" onclick="this.disabled=true;this.form.submit();">
        <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
       </button>
      </td>
     </tr>
    </table>
    </c:if>
   </form>
  </c:forEach>
  </c:if>
  <jsp:include page="/WEB-INF/pages/form/_route.jsp" />
 </div>


 <div id="tab-2">
  <form action="D302" method="post" id="SBMQHFRM302">
   <input type="hidden" name="action" value="SubmitQH">
   <input type="hidden" name="GSDM" value="${D302.GSDM}">
   <input type="hidden" name="BDDM" value="${D302.BDDM}">
   <input type="hidden" name="BDBH" value="${D302.BDBH}">
   <input type="hidden" name="BDAMT" value="${D302.BDAMT}">
   <input type="hidden" name="BDTX" value="外修验收单">
   <input type="hidden" name="WXDH" value="${D302.WXDH}">
  </form>
  <form action="D302" method="post" id="FRM302">
   <c:if test="${D302.BDBH == null || D302.BDBH == ''}">
   	<input type="hidden" name="action" value="CreateHeader302">
    <input type="hidden" name="WXDH" value="${D302.WXDH}">
   </c:if>
   <c:if test="${D302.BDBH != null && D302.BDBH != ''}">
   	<input type="hidden" name="action" value="UpdateHeader302">
    <input type="hidden" name="GSDM" value="${D302.GSDM}">
    <input type="hidden" name="BDDM" value="${D302.BDDM}">
    <input type="hidden" name="BDBH" value="${D302.BDBH}">
    <input type="hidden" name="WXDH" value="${D302.WXDH}">
   </c:if>

   <div id="icon">
    <c:if test="${((D302.BDBH == null || D302.BDBH == '') || (D302.SQYH == sessionScope.uid) && (QHKS302 == 'N'))}">
    <button type="button" id="SBMFRM302">
     <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
    </button>
    </c:if>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <c:if test="${((D302.BDBH != '') && (D302.SQYH == sessionScope.uid) && (QHKS302 == 'N'))}">
     <button type="button" id="SBMQHBTN302">
      <img src="/iMes/stylesheet/icons/S_B_MNGR.GIF" alt="啟動簽核流程" />
     </button>
    </c:if>
   </div>


   <table>
    <caption>验收单内容</caption>    
    <tbody>
     <tr>
      <th>公司代碼<br/>Company</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L111" <c:if test="${D302.GSDM == 'L111'}">selected="selected"</c:if>>L111 PH Leader</option>
        <option value="L210" <c:if test="${D302.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${D302.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${D302.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
      </select></td>
      <th>表單編號<br/>Docno</th>
      <td>${D302.BDBH}</td>
      <th>表單日期<br/>Date</th>
      <td>${D302.BDRQ}</td>
      <th>类型</th>
      <td><select name="ZCLX" id="ZCLX">
        <option value="" <c:if test="${D302.ZCLX == ''}">selected="selected"</c:if>>請選擇</option>
        <option value="A. 维修" <c:if test="${D302.ZCLX == 'A. 维修' || D302.ZCLX == '' || D302.ZCLX == null}">selected="selected"</c:if>>A. 维修</option>
        <option value="B. 其它" <c:if test="${D302.ZCLX == 'B. 其它'}">selected="selected"</c:if>>B. 其它</option>
      </select></td>      
     </tr>

     <tr>
      <th>资产编号</th>
      <td><input type="text" name="ZCBH" id="ZCBH" value="${D302.ZCBH}" class="KOSTL" onchange="getZCInfor(this.value)"></td>
      <th>资产名称</th>
      <td><input type="text" name="ZCMC" id="ZCMC" value="${D302.ZCMC}" class="KOSTL"></td>
      <th>资产成本中心</th>
      <td><input type="text" name="ZCCB" id="ZCCB" value="${D302.ZCCB}" class="KOSTL"></td>
      <th>资产保管部门</th>
      <td><input type="text" name="ZCBG" id="ZCBG" value="${D302.ZCBG}" class="KOSTL"></td>
     </tr>
     <tr>
      <th>帳面價值</th>
      <td><input type="text" name="ZMJZ" id="ZMJZ" value="${D302.ZMJZ}" class="KOSTL"></td>
      <th>保管人</th>
      <td><input type="text" name="BGR" id="BGR" value="${D302.BGR}" class="KOSTL"></td>
      <th>數量</th>
      <td><input type="text" name="QTY" id="QTY" value="${D302.QTY}" class="KOSTL"></td>
      <th>資產取得日期</th>
      <td><input type="text" name="ZBDT" id="ZBDT" value="${D302.ZBDT}" class="KOSTL"></td>
     </tr>
     <tr>
      <th>取得價值</th>
      <td><input type="text" name="QDJZ" id="QDJZ" value="${D302.QDJZ}" class="KOSTL"></td>
      <th>資產存放地點</th>
      <td  colspan="7"><input type="text" name="ADDR" id="ADDR" value="${D302.ADDR}" class="KOSTL"></td>
     </tr>
     <tr>
      <th>部門代碼<br/>Dept</th>
      <td><input type="text" name="SQBM" id="SQBM" value="${D302.SQBM}" class="KOSTL" onchange="getDeptName(this.value)"></td>
      <th>部門名稱<br/>Name</th>
      <td><input type="text" name="BMMC" id="BMMC" value="${D302.BMMC}" class="KOSTL"></td>
      <th>表單附檔<br/>Attachment</th>
      <c:if test="${D302.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${D302.BDFD}">下載</a></td>
      </c:if>
      <c:if test="${D302.BDFD.length()== null ||D302.BDFD.length()==0 }">
       <td></td>
      </c:if>
      <th>表單狀態<br/>Status</th>
      <td>${D302.BDJG} / ${D302.BDZT}</td>
     </tr>
     <tr>
      <th>申請者<br/>Requester</th>
      <td>${D302.SQYH}</td>
      <th>核准者<br/>Approver</th>
      <td>${D302.QHYH}</td>
      <th>核准時間<br/>Aprv time</th>
      <td colspan="3">${D302.QHSJ}</td>
     </tr>
    <tr>
     <th>验收说明</th>
     <td colspan="8">
     	<textarea name="BDBJ" id="BDBJ" rows="30" cols="120" >${D302.BDBJ}</textarea>    	
	 </td>
    </tr>
    </tbody>
   </table>

   <table>
    <caption>验收明细</caption>    
    <tbody>
     <tr>
      <th>供應商代碼</th>
      <td><input type="text" name="SUPPLIER2" id="SUPPLIER2" value="${D302.SUPPLIER}" class="KOSTL" onchange="querySuppliers(this.value)"></td>
      <th>供應商</th>
      <td><input type="text" name="SUPPLIER_NAME2" id="SUPPLIER_NAME2" value="${D302.SUPPLIER_NAME}" class="KOSTL"></td>
      <th>联系人</th>
      <td><input type="text" name="CONTACT2" id="CONTACT2" value="${D302.CONTACT}" class="KOSTL"></td>
      <th>联系电话</th>
      <td><input type="text" name="PHONE2" id="PHONE2" value="${D302.PHONE}" class="KOSTL"></td>
     </tr>
     <tr>     
      <th>报价</th>
      <td><input type="text" name="PRICE2" id="PRICE2" value="${D302.PRICE}" class="KOSTL"></td>
      <th>单位</th>
      <td><input type="text" name="UNIT2" id="UNIT2" value="${D302.UNIT}" class="KOSTL"></td>
      <th>数量</th>
      <td><input type="text" name="QUALITY2" id="QUALITY2" value="${D302.QUALITY}" class="KOSTL"></td>
      <th>币别</th>
      <td><input type="text" name="CURRENCY2" id="CURRENCY2" value="${D302.CURRENCY}" class="KOSTL"></td>
      </tr>
    </tbody>
   </table>

  </form>
  <jsp:include page="/WEB-INF/pages/form/_D302route.jsp" />
 </div>
 
 
</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />