<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/D272.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">溢領單 Overissue</a></li>
  <li><a href="#tab-2">溢領明細 Details</a></li>
  <!--
  <li><a href="#tab-3">工單材料</a></li>
 -->
  <c:if test="${((HD.BDBH != '') && (HD.SQYH == sessionScope.uid) && (QHKS == 'N'))}">
   <li><a href="#tab-3">材料輸入 Material entries</a></li>
  </c:if>

 </ul>

 <div id="tab-1">
  <form action="D272" method="post" id="SBMQHFRM">
   <input type="hidden" name="action" value="SubmitQH">
   <input type="hidden" name="GSDM" value="${HD.GSDM}">
   <input type="hidden" name="BDDM" value="${HD.BDDM}">
   <input type="hidden" name="BDBH" value="${HD.BDBH}">
   <input type="hidden" name="BDAMT" value="${HD.BDAMT}">
   <input type="hidden" name="KOSTL" value="${HD.KOSTL}">
   <input type="hidden" name="ZRFY" value="${HD.ZRFY}">
   <input type="hidden" name="BDTX" value="${HD.AUFNR} ${HD.MATNR} ${HD.YYLB}">
  </form>

  <form action="D272" method="post" id="FRM01">
   <c:if test="${HD.BDBH == ''}">
    <input type="hidden" name="action" value="CreateHeader">
   </c:if>
   <c:if test="${HD.BDBH != ''}">
    <input type="hidden" name="action" value="UpdateHeader">
    <input type="hidden" name="GSDM" value="${HD.GSDM}">
    <input type="hidden" name="BDDM" value="${HD.BDDM}">
    <input type="hidden" name="BDBH" value="${HD.BDBH}">
   </c:if>
   <div id="icon">

    <c:if test="${HD.BDBH == '' || ((HD.BDBH != '') && (HD.SQYH == sessionScope.uid))}">
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
    <caption>溢領料單據</caption>
    <tbody>
     <tr>
      <th>公司代碼<br/>Company</th>
      <td><select name="GSDM" id="GSDM">
        <option value="L111" <c:if test="${HD.GSDM == 'L111'}">selected="selected"</c:if>>L111 PH Leader</option>
        <option value="L210" <c:if test="${HD.GSDM == 'L210'}">selected="selected"</c:if>>L210 東莞領航</option>
        <option value="L300" <c:if test="${HD.GSDM == 'L300'}">selected="selected"</c:if>>L300 東莞立德</option>
        <option value="L400" <c:if test="${HD.GSDM == 'L400'}">selected="selected"</c:if>>L400 江蘇領先</option>
      </select></td>
      <th>表單代碼<br/>Type</th>
      <td>${HD.BDDM}</td>
      <th>表單編號<br/>Docno</th>
      <td>${HD.BDBH}</td>
      <th>表單日期<br/>Date</th>
      <td><input type="text" name="BDRQ" id="BDRQ" value="${HD.BDRQ}"></td>
     </tr>

     <tr>
      <th>部門代碼<br/>Dept</th>
      <td><input type="text" name="KOSTL" id="KOSTL" value="${HD.KOSTL}" class="KOSTL"></td>
      <th>部門名稱<br/>Name</th>
      <td>${HD.KTEXT}</td>
      <th>部門費用<br/>Expenses</th>
      <td><input type="checkbox" name="ZRFY" id="ZRFY" value="X" <c:if test="${HD.ZRFY=='X' }">checked="checked"</c:if>>自認 Self</td>
      <th>表單狀態<br/>Status</th>
      <td>${HD.BDJG} / ${HD.BDZT}</td>
     </tr>

     <tr>
      <th>生產訂單:<br/>MO no</th>
      <td><input type="text" name="AUFNR" id="AUFNR" value="${HD.AUFNR}" readonly="readonly"></td>
      <th>上線日期:<br/>StrDate</th>
      <td><input type="text" name="GSTRI" id="GSTRI" value="${HD.GSTRI}" readonly="readonly"></td>
      <th>訂單數量:<br/>Ord Qty</th>
      <td><input type="text" name="PSMNG" id="PSMNG" value="${HD.PSMNG}" readonly="readonly"></td>
      <th>完成數量:<br/>Completed</th>
      <td><input type="text" name="WEMNG" id="WEMNG" value="${HD.WEMNG}" readonly="readonly"></td>
     </tr>
     <tr>
      <th>機種:<br/>Product</th>
      <td><input type="text" name="MATNR" id="MATNR" value="${HD.MATNR}" size="18" readonly="readonly"></td>
      <th>機種說明:<br/>Description</th>
      <td colspan="3"><input type="text" name="MAKTX" id="MAKTX" value="${HD.MAKTX}" size="40" readonly="readonly"></td>
      <th>工廠:<br/>Plant</th>
      <td><input type="text" name="WERKS" id="WERKS" value="${HD.WERKS}" readonly="readonly"></td>
     </tr>
     <tr>
      <th>原因代碼<br/>Reason</th>
      <td><select name="YYLB" id="YYLB">
        <option value="" <c:if test="${HD.YYLB == ''}">selected="selected"</c:if>>請選擇</option>
        <option value="A. 設計異常" <c:if test="${HD.YYLB == 'A. 設計異常'}">selected="selected"</c:if>>A. 設計異常 Design</option>
        <option value="B. 製程異常" <c:if test="${HD.YYLB == 'B. 製程異常'}">selected="selected"</c:if>>B. 製程異常 Production</option>
        <option value="C. 材料異常" <c:if test="${HD.YYLB == 'C. 材料異常'}">selected="selected"</c:if>>C. 材料異常 Material</option>
        <option value="D. 設備治具異常" <c:if test="${HD.YYLB == 'D. 設備治具異常'}">selected="selected"</c:if>>D. 設備治具異常 Tools</option>
        <option value="E. 其它異常" <c:if test="${HD.YYLB == 'E. 其它異常'}">selected="selected"</c:if>>E. 其它異常 Others</option>
      </select></td>
      <th>工作站:<br/>Workstation</th>
      <td>${HD.selWS}</td>
      <th>原因說明<br/>Remarks</th>
      <td colspan="3"><textarea cols="90" rows="3" name="YYSM" id="YYSM">${HD.YYSM}</textarea></td>
     </tr>
     <tr>
      <th>申請者<br/>Requester</th>
      <td>${HD.SQYH}</td>
      <th>申請時間<br/>Req time</th>
      <td><fmt:formatDate value="${HD.JLSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
      <th>核准者<br/>Approver</th>
      <td>${HD.QHYH}</td>
      <th>核准時間<br/>Aprv time</th>
      <td>${HD.QHSJ}</td>
     </tr>
     <tr>
      <th>表單附檔<br/>Attachment</th>
      <c:if test="${HD.BDFD.length() > 0}">
       <td><a href="/iMes/FileDownloader?filePath=${HD.BDFD}">下載</a></td>
      </c:if>
      <c:if test="${HD.BDFD.length()== null ||HD.BDFD.length()==0 }">
       <td></td>
      </c:if>
      <th>需求號碼<br/>Reqno</th>
      <td><input type="text" name="RSNUM" id="RSNUM" value="${HD.RSNUM}" readonly="readonly"></td>
      <th></th>
      <td></td>
      <th>溢領金額<br/>Iss amount</th>
      <td class="dec2"><fmt:formatNumber value="${HD.BDAMT}" pattern="#,###.##" /></td>
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

  <form action="D272" method="post" id="FRM04">
   <input type="hidden" name="action" value="UpdateResp">
   <input type="hidden" name="GSDM" value="${HD.GSDM}">
   <input type="hidden" name="BDDM" value="${HD.BDDM}">
   <input type="hidden" name="BDBH" value="${HD.BDBH}">
   <div id="icon">
    <c:if test="${PBUSER=='Y' }">
     <button type="button" id="SBMFRM04">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
    </c:if>
   </div>

   <table>
    <caption>費用歸屬 Expense responsible</caption>
    <thead>
     <tr>
      <th>序號<br>Seq</th>
      <th>部門代碼<br>Dept</th>
      <th>供應商代碼<br>Supplier</th>
      <th>客戶代碼<br>Customer</th>
      <th>名稱<br>Name</th>
      <th>責任人<br>Responsible</th>
      <th>責任%<br>Resp%</th>
      <th>判決人<br>Adjudicator</th>
      <th>判決時間<br>judgment</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${RS}">
      <input type="hidden" name="SQNR" value="${e.SQNR}">
      <tr>
       <td>${e.SQNR}</td>
       <td><input type="text" id="KOSTL_${e.SQNR}" name="KOSTL_${e.SQNR}" size="12" value="${e.KOSTL}" class="KOSTL"></td>
       <td><input type="text" id="LIFNR_${e.SQNR}" name="LIFNR_${e.SQNR}" size="12" value="${e.LIFNR}" onchange="getSupplierName(this.value,${e.SQNR})"></td>
       <td><input type="text" id="KUNNR_${e.SQNR}" name="KUNNR_${e.SQNR}" size="12" value="${e.KUNNR}" onchange="getCustomerName(this.value,${e.SQNR})"></td>
       <td><input type="text" id="KTEXT_${e.SQNR}" name="KTEXT_${e.SQNR}" size="20" value="${e.KTEXT}" readonly="readonly" tabindex="99"></td>
       <td><input type="text" id="NAME1_${e.SQNR}" name="NAME1_${e.SQNR}" size="12" value="${e.NAME1}"></td>
       <td><input type="text" id="PCTG_${e.SQNR}" name="PCTG_${e.SQNR}" size="5" value="${e.PCTG}" onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
       <td>${e.JLYH}</td>
       <td>${e.JLSJ}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </form>

  <jsp:include page="/WEB-INF/pages/form/_route.jsp" />
 </div>

 <div id="tab-2">
  <form action="D272" method="post" id="FRM02">
   <input type="hidden" name="action" value="UpdateComp">
   <input type="hidden" name="GSDM" value="${HD.GSDM}">
   <input type="hidden" name="BDDM" value="${HD.BDDM}">
   <input type="hidden" name="BDBH" value="${HD.BDBH}">

   <div id="icon">
    <button type="button" id="SBMFRM02">
     <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
   </div>

   <table>
    <caption>溢領料明細表</caption>
    <tbody>
     <c:forEach var="e" items="${LN}">
      <input type="hidden" name="SQNR" value="${e.SQNR}">
      <tr>
       <th>項號<br/>Seq</th>
       <th>料號<br/>Material</th>
       <th>工廠<br/>Plant</th>
       <th>料類<br/>MatGrp</th>
       <th>規格<br/>Specifications</th>
       <th>單價<br/>Price</th>
       <th>需求數量<br/>Req.Qty</th>
       <th>單位<br/>UOM</th>
       <th>需求金額<br/>Req amt</th>
       <th>發料數量<br/>Iss qty</th>
       <th>發料金額<br/>Iss amt</th>
       <th>確認日期<br/>Cfm date</th>
      </tr>
      <tr>

       <td rowspan="2">${e.SQNR}</td>
       <td>${e.CMATNR}</td>
       <td>${e.CWERKS}</td>
       <td>${e.CMATKL}</td>
       <td>${e.CMAKTX}</td>
       <td id="NETPR_${e.SQNR}">${e.NETPR}</td>
       <td><input type="number" name="REQQ_${e.SQNR}" value="${e.REQQ}" size="9" id="REQQ_${e.SQNR}" onchange="updateREQA(${e.SQNR})"
         onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);" <c:if test="${(QHKS == 'Y')}"> readonly="readonly"</c:if>></td>
       <td>${e.CMEINS}</td>
       <td><input type="text" name="REQA_${e.SQNR}" value="${e.REQA}" size="9" id="REQA_${e.SQNR}" readonly="readonly"></td>
       <td>${e.ISSQ}</td>
       <td>${e.ISSA}</td>
       <td><input type="text" name="CFMDT_${e.SQNR}" value="${e.CFMDT}" size="9" <c:if test="${CGUSER != 'Y' }">readonly="readonly"</c:if>></td>
      </tr>
      <tr>
       <td colspan="4" valign="middle"><pre>需求說明Notes:           [${e.JLYH} - ${e.JLSJ}]</pre> <textarea rows="3" cols="60" name="XQSM_${e.SQNR}">${e.XQSM}</textarea></td>
       <td colspan="7" valign="middle"><pre>採購說明Purchaser:  ${e.CEKGRP}       [${e.CGYH}]</pre> <textarea rows="3" cols="60" name="CGSM_${e.SQNR}" <c:if test="${CGUSER != 'Y' }">readonly="readonly"</c:if>>${e.CGSM}</textarea></td>
      </tr>
     </c:forEach>
    </tbody>
   </table>


  </form>
 </div>
 <!--
 <div id="tab-3">
  <form action="D272" method="post" id="FRM03">
   <input type="hidden" name="action" value="InsertComp">
   <input type="hidden" name="GSDM" value="${HD.GSDM}">
   <input type="hidden" name="BDDM" value="${HD.BDDM}">
   <input type="hidden" name="BDBH" value="${HD.BDBH}">

   <div id="icon">
    <c:if test="${QHKS == 'N'}">
     <button type="button" id="INSRBTN">
      <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="" />
     </button>
     <button type="submit" id="SBMFRM03">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
     </button>
    </c:if>
   </div>

   <table>
    <caption>工單物料選項表</caption>
    <thead>
     <tr>
      <th>ID</th>
      <th>料類</th>
      <th>規格說明</th>
      <th>料號</th>
      <th>倉庫</th>
      <th>單位</th>
      <th>訂單數</th>
      <th>領用數</th>
      <th>庫存數</th>
      <th>檢驗數</th>
     </tr>
    </thead>
    <tbody id="ROWS">
     <c:forEach var="e" items="${DT}">
      <tr>
       <td><input type="checkbox" value="${e.MATNR}_${e.WERKS}" name="CBXMATNR"></td>
       <td>${e.MATKL}</td>
       <td>${e.MAKTX}</td>
       <td>${e.MATNR}</td>
       <td>${e.WERKS}</td>
       <td>${e.MEINS}</td>
       <td class="dec2"><fmt:formatNumber value="${e.BDMNG}" pattern="#,###.##" /></td>
       <td class="dec2"><fmt:formatNumber value="${e.ENMNG}" pattern="#,###.##" /></td>
       <td class="dec2"><fmt:formatNumber value="${e.LABST}" pattern="#,###.##" /></td>
       <td class="dec2"><fmt:formatNumber value="${e.INSME}" pattern="#,###.##" /></td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </form>
 </div>
-->
 <c:if test="${((HD.BDBH != '') && (HD.SQYH == sessionScope.uid) && (QHKS == 'N'))}">
  <div id="tab-3">
   <div>
    <table>
     <caption>半成品展材料明細 Bill of materials</caption>
     <tr>
      <th>半成品料號<br/>Wip matnr</th>
      <td><select name="WipMatnr" id="WipMatnr">
        <option value="">0 - 工單材料 MO Mat</option>
        <c:forEach var="e" items="${WMATNR}">
         <option value="${e.DPMATNR}">${e.BLEVEL} - ${e.DPMATNR}</option>
        </c:forEach>
      </select></td>
      <th>套數<br/>Qty</th>
      <td><input type="text" name="WipMenge" id="WipMenge" size="8" onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
      <th>工廠<br/>Plant</th>
      <td><input type="text" name="WipWerks" id="WipWerks" size="5" value="${HD.WERKS}" readonly="readonly"></td>
      <th>成品<br/>Product</th>
      <td><input type="text" name="Pmatnr" id="Pmatnr" size="20" value="${HD.MATNR}" readonly="readonly"></td>
     </tr>
    </table>
   </div>
   <form action="D272" method="post" id="FRM05">
    <input type="hidden" name="action" value="InsertWipComp">
    <input type="hidden" name="GSDM" value="${HD.GSDM}">
    <input type="hidden" name="BDDM" value="${HD.BDDM}">
    <input type="hidden" name="BDBH" value="${HD.BDBH}">

    <div id="icon">
     <button type="button" id="InsertWipBtn">
      <img src="/iMes/stylesheet/icons/S_B_SLIP.GIF" alt="WipBom" />
     </button>
     <!--button type="button" id="InsertNewItemBtn">
      <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="" />
     </button-->
     <button type="button" id="SBMFRM05">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
     </button>
    </div>

    <div id="InsertWipDiv"></div>
   </form>
  </div>
 </c:if>
</div>


<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />