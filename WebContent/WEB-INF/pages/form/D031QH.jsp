<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
  $(document)
      .ready(
          function() {
            $('#slip')
                .click(
                    function(event) {
                      $('#slip').attr({
                        "disabled" : "disabled"
                      });
                      location.href = '/iMes/D031?action=CreateD031M&GSDM=${D031H.GSDM}&BDDM=${D031H.BDDM}&BDBH=${D031H.BDBH}'
                    });
          });
</script>

<div id="tabs">
 <ul>
  <li><a href="#tabs-1">表單簽核</a></li>
  <li><a href="#tabs-2">材料</a></li>
  <c:if test="${D031H.JLYH==sessionScope.uid}">
   <li><a href="#tabs-3">更改原因</a></li>
  </c:if>
  <c:if test="${D031H.JLYH==sessionScope.uid && (D031H.BDFD ==null)}">
   <li><a href="#tabs-4">上传附件</a></li>
  </c:if>
 </ul>

 <div id="tabs-1">
  <fieldset>
   <legend>選項</legend>
   <div id="icon">
    <button type="button" onclick="location.href='/iMes/qh?action=Find&STRQHZT=2&STRYSYH=${sessionScope.uid}'">
     <img src="/iMes/stylesheet/icons/S_B_STAT.GIF" alt="" />
    </button>
    <button type="button" onclick="history.go(-1)">
     <img src="/iMes/stylesheet/icons/S_F_BACK.GIF" alt="" />
    </button>
   </div>
  </fieldset>

  <fieldset>
   <legend>訂單異動通知單 Order change notice- D031</legend>
   <table>
    <tr>
     <td colspan="8" align="center"><h3>訂單異動通知單  Order change notice</h3></td>
    </tr>
    <tr>
     <th>公司代碼<br/>Company code</th>
     <td>${D031H.GSDM}</td>
     <th>表單代碼<br/>Form code</th>
     <td>${D031H.BDDM}</td>
     <th>表單編號<br/>Form number</th>
     <td>${D031H.BDBH}</td>
     <th>表單日期<br/>Form date</th>
     <td>${D031H.BDRQ}</td>
    </tr>
    <tr>
     <th>客戶代碼<br/>Customer Code</th>
     <td>${D031LS[0].KUNNR}</td>
     <th>客戶短名<br/>Customer short name</th>
     <td>${D031LS[0].SORTL}</td>
     <th>附件<br/>Attachment</th>
     <c:if test="${D031H.BDFD.length() > 0}">
      <td><a href="/iMes/FileDownloader?filePath=${D031H.BDFD}">下載<br/>Download</a></td>
     </c:if>
     <c:if test="${D031H.BDFD.length()== null ||D031H.BDFD.length()==0 }">
      <td></td>
     </c:if>
     <th>原因類別<br/>Reason category</th>
     <td>${D031H.YYLB}</td>
    </tr>
    <tr>
     <th>異動說明<br/>Transaction description</th>
     <td colspan="7"><textarea rows="3" cols="80"> ${D031H.YYSM}</textarea></td>
    </tr>
    <tr>
     <th>申請人<br/>Applicant</th>
     <td>${D031H.JLYH}</td>
     <th>申請時間<br/>Applicant date</th>
     <td><fmt:formatDate value="${D031H.JLSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
     <th>更改人<br/>Change people</th>
     <td>${D031H.GXYH}</td>
     <th>更新時間<br/>Update time</th>
     <td><fmt:formatDate value="${D031H.GXSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
    </tr>

    <tr>
     <td colspan="8">
      <table>
       <c:forEach var="e" items="${D031LS}">
        <c:if test="${e.XCLB=='A'}">
         <tr>
          <th>接單倉<br/>Warehouse</th>
          <th>訂單號<br/>Order No.</th>
          <th>項號<br/>Item No.</th>
          <th>產品號碼<br/>Product number</th>
          <th>出貨<br/>Shipping</th>
          <th>付款條件<br/>Terms of payment</th>
          <th colspan="2">國貿條件<br/>China World Trade Center condition</th>
          <th>客戶物料<br/>Customer material</th>
          <th>單價<br/>Unit price</th>
          <th>幣別<br/>Currency</th>
         </tr>
         <tr>
          <td>${e.WERKS}</td>
          <td>${e.VBELN}</td>
          <td>${e.POSNR}</td>
          <td>${e.MATNR}</td>
          <td>${e.VSART}</td>
          <td>${e.ZTERM}</td>
          <td>${e.INCO1}</td>
          <td>${e.INCO2}</td>
          <td>${e.KDMAT}</td>
          <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.0000" /></td>
          <td>${e.WAERK}</td>
         </tr>
         <tr>
          <td colspan="11">
           <table>
            <c:forEach var="f" items="${D031LS}">
             <c:if test="${f.VBELN==e.VBELN && f.POSNR==e.POSNR}">
              <c:choose>
               <c:when test="${f.XCLB=='A'}">
                <c:set var="qty" value="${f.OMENG}"></c:set>
                <tr>
                 <th></th>
                 <th></th>
                 <th>交貨數量<br/>Delivery quantity</th>
                 <th>交貨日期<br/>Delivery date</th>
                 <th>可用日期<br/>Available date</th>
                 <th>${f.WAERK}金額<br/>Amount</th>
                </tr>
                <tr>
                 <th></th>
                 <th>目前交期<br/>Current delivery period</th>
                 <th class="int"><fmt:formatNumber value="${f.OMENG}" pattern="#,###" /></th>
                 <th>${f.EDATU}</th>
                 <th>${f.MBDAT}</th>
                 <th class="dec2"><fmt:formatNumber value="${f.OMENG * f.NETPR}" pattern="#,###.00" /></th>
                </tr>
               </c:when>
               <c:otherwise>
                <tr>
                 <c:set var="qty" value="${qty - f.OMENG }"></c:set>
                 <td>${f.XCXH - e.XCXH}</td>
                 <td>異動要求<br/></td>
                 <td class="int"><fmt:formatNumber value="${f.OMENG}" pattern="#,###" /></td>
                 <td>${f.EDATU}</td>
                 <td>${f.MBDAT}</td>
                 <td class="dec2"><fmt:formatNumber value="${f.OMENG * f.NETPR}" pattern="#,###.00" /></td>
                </tr>
               </c:otherwise>
              </c:choose>
             </c:if>
            </c:forEach>
            <tr>
             <th></th>
             <th>差異<br/>The difference</th>
             <th class="int"><fmt:formatNumber value="${qty }" pattern="#,###" /></th>
             <th></th>
             <th></th>
             <th class="dec2"><fmt:formatNumber value="${qty * e.NETPR}" pattern="#,###.00" /></th>
            </tr>
           </table>
          </td>
         </tr>
        </c:if>

       </c:forEach>

      </table>
     </td>
    </tr>
   </table>
   <jsp:include page="/WEB-INF/pages/form/_route.jsp" />
  </fieldset>
 </div>

 <div id="tabs-2">
  <fieldset>
   <legend>訂單異動總表<br/>Total transaction order</legend>
   <table>
    <tr>
     <th>表單號碼<br/>Form number</th>
     <th>客戶名稱<br/>Customer code</th>
     <th>機種<br/>Product</th>
     <th>倉庫<br/>Warehouse</th>
     <th>修正前數量<br/>Number before moving</th>
     <th>修正前日期<br/>Delivery date before moving</th>
     <th>修正后數量<br/>Modified number</th>
     <th>修正后日期<br/>Modified delivery time</th>
     <th>修正原因<br/>Reason category</th>
     <th>申請者<br/>Applicant</th>
     <th>申請時間<br/>Application time</th>
     <th>核准者<br/>Approved person</th>
     <th>核准時間<br/>Approval time</th>
     <th>結果<br/>Results</th>
     <th>差異數量<br/>Difference quantity</th>
    </tr>
    <c:forEach var="e" items="${D031SM}">
     <tr>
      <td>${e.BDBH}</td>
      <td>${e.SORTL}</td>
      <td>${e.MATNR}</td>
      <td>${e.WERKS}</td>
      <td class="int"><fmt:formatNumber value="${e.BFQTY}" pattern="#,###" /></td>
      <td>${e.BFDAT}</td>
      <td class="int"><fmt:formatNumber value="${e.AFQTY}" pattern="#,###" /></td>
      <td>${e.AFDAT}</td>
      <td>${e.YYLB}</td>
      <td>${e.JLYH}</td>
      <td>${e.JLSJ}</td>
      <td>${e.QHYH}</td>
      <td>${e.QHSJ}</td>
      <td>${e.BDJG}</td>
      <td class="int"><fmt:formatNumber value="${e.DFQTY}" pattern="#,###" /></td>
     </tr>
    </c:forEach>
   </table>

   <legend>
    <strong>機種物料明細<br/>Product material list</strong>
   </legend>

   <form action="D031" method="post">
    <input type="hidden" name="action" value="UpdMat">
    <input type="hidden" name="GSDM" value="${D031H.GSDM}">
    <input type="hidden" name="BDDM" value="${D031H.BDDM}">
    <input type="hidden" name="BDBH" value="${D031H.BDBH}">
    <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
     <c:if test="${D031MS[0] == null}">
      <button type="button" id="slip">
       <img src="/iMes/stylesheet/icons/S_B_SLIP.GIF" alt="" />
      </button>
     </c:if>
    </div>

    <table>
     <thead>
      <tr>
       <th>序號<br/>No.</th>
       <th>料號<br/>Material number</th>
       <th>料倉<br/>Warehouse</th>
       <th>料類<br/>Type of Material</th>
       <th>規格<br/>Specifications</th>
       <th>單位<br/>Unit</th>
       <th>耗用<br/>expend</th>
       <th>需求<br/>demand</th>
       <th>庫存<br/>Inventory</th>
       <th>PO</th>
       <th>單價<br/>Unit price</th>
       <th>呆滯數量Slack quantity<br />確認日期Confirmation date
       </th>
       <th>內文<br/>Text</th>
      </tr>
     </thead>
     <tbody>
      <c:forEach var="e" items="${D031MS}">
       <c:if test="${e.SQNR==1}">
        <tr>
         <th colspan="2"><strong>${e.PMATNR}</strong></th>
         <th><strong>${e.PWERKS}</strong></th>
         <th colspan="11"></th>
        </tr>
       </c:if>
       <tr>
        <td class="int"><fmt:formatNumber value="${e.SQNR}" pattern="#,###" /></td>
        <td>${e.CMATNR}</td>
        <td>${e.CWERKS}</td>
        <td>${e.CMATKL}</td>
        <td class="wrap">${e.CMAKTX}</td>
        <td>${e.CMEINS}</td>
        <td class="dec4"><fmt:formatNumber value="${e.BOMQ}" pattern="#,##0.0000" /></td>
        <td class="dec2"><fmt:formatNumber value="${e.TRNQ}" pattern="#,##0.00" /></td>
        <td class="int"><fmt:formatNumber value="${e.STKQ}" pattern="#,###" /></td>
        <td class="int"><fmt:formatNumber value="${e.PURQ}" pattern="#,###" /></td>
        <td class="dec6"><fmt:formatNumber value="${e.NETPR}" pattern="#,##0.000000" /></td>
        <td class="dec2"><input type="text" name="${e.PMATNR}_${e.SQNR}_IDEQ" value="${e.IDEQ}" style="font-size: small;" size="10"> <br /> <input type="text"
          name="${e.PMATNR}_${e.SQNR}_CFMDT" value="${e.CFMDT}" size="10" style="font-size: small;"></td>
        <td><textarea rows="3" cols="30" name="${e.PMATNR}_${e.SQNR}_LTEXT" style="font-size: small;">${e.LTEXT}</textarea></td>
       </tr>
      </c:forEach>
     </tbody>
    </table>
   </form>
  </fieldset>
 </div>

 <c:if test="${D031H.JLYH==sessionScope.uid}">
  <div id="tabs-3">
   <form action="D031" method="post">
    <input type="hidden" name="action" value="UpdYYLB">
    <input type="hidden" name="GSDM" value="${D031H.GSDM}">
    <input type="hidden" name="BDDM" value="${D031H.BDDM}">
    <input type="hidden" name="BDBH" value="${D031H.BDBH}">
    <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
      <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
    </div>

    <table>
     <tr>
      <th>公司代碼<br/>Company code</th>
      <td>${D031H.GSDM}</td>
      <th>表單代碼<br/>Form code</th>
      <td>${D031H.BDDM}</td>
      <th>表單編號<br/>Form number</th>
      <td>${D031H.BDBH}</td>
      <th>表單日期<br/>Form date</th>
      <td>${D031H.BDRQ}</td>
     </tr>
     <tr>
      <th>客戶代碼<br/>Customer code</th>
      <td>${D031LS[0].KUNNR}</td>
      <th>客戶短名<br/>Customer short name</th>
      <td>${D031LS[0].SORTL}</td>
      <th>原因類別<br/>Reason category</th>
      <td colspan="3"><select name="YYLB">
        <option value="A. 客戶需求提前" <c:if test="${D031H.YYLB == 'A. 客戶需求提前'}">selected="selected"</c:if>>A. 客戶需求提前Customer demand ahead of schedule</option>
        <option value="B. 客戶需求延後" <c:if test="${D031H.YYLB == 'B. 客戶需求延後'}">selected="selected"</c:if>>B. 客戶需求延後Customer demand postponed</option>
        <option value="C. 客戶需求減少" <c:if test="${D031H.YYLB == 'C. 客戶需求減少'}">selected="selected"</c:if>>C. 客戶需求減少Reduced customer demand</option>
        <option value="D. 客戶需求增加" <c:if test="${D031H.YYLB == 'D. 客戶需求增加'}">selected="selected"</c:if>>D. 客戶需求增加Increased customer demand</option>
        <option value="E. 客戶需求轉開" <c:if test="${D031H.YYLB == 'E. 客戶需求轉開'}">selected="selected"</c:if>>E. 客戶需求轉開Customer needs to open</option>
        <option value="F. 物料因素延後" <c:if test="${D031H.YYLB == 'F. 物料因素延後'}">selected="selected"</c:if>>F. 物料因素延後Delayed material factors</option>
        <option value="G. 品質因素延後" <c:if test="${D031H.YYLB == 'G. 品質因素延後'}">selected="selected"</c:if>>G. 品質因素延後Delay of quality factors</option>
        <option value="H. 產能因素調整" <c:if test="${D031H.YYLB == 'H. 產能因素調整'}">selected="selected"</c:if>>H. 產能因素調整Adjustment of production capacity</option>
        <option value="I. 試產失敗延後" <c:if test="${D031H.YYLB == 'I. 試產失敗延後'}">selected="selected"</c:if>>I. 試產失敗延後Failure to delivery delay</option>
        <option value="J. 安規因素調整" <c:if test="${D031H.YYLB == 'J. 安規因素調整'}">selected="selected"</c:if>>J. 安規因素調整Adjustment of safety factor</option>
        <option value="K. LEI自主取消避險" <c:if test="${D031H.YYLB == 'K. LEI自主取消避險'}">selected="selected"</c:if>>K. LEI自主取消避險LEI self cancellation hedge</option>
        <option value="L. 取消重開改單價" <c:if test="${D031H.YYLB == 'L. 取消重開改單價'}">selected="selected"</c:if>>L. 取消重開改單價Cancel re opened to change the unit price</option>
        <option value="M. 業務合併出貨" <c:if test="${D031H.YYLB == 'M. 業務合併出貨'}">selected="selected"</c:if>>M. 業務合併出貨Business combination shipping</option>
	      <option value="N. 當月跨月提前">N. 當月跨月提前Month ahead of the month</option>
        <option value="O. 當月跨月延後">O. 當月跨月延後Month delay</option>
        <option value="P. 跨月延後">P. 跨月延後Months delayed</option>
      </select></td>
     </tr>
    </table>
   </form>
  </div>
 </c:if>

<c:if test="${D031H.JLYH==sessionScope.uid && (D031H.BDFD ==null)}">
  <div id="tabs-4">
   <form action="/iMes/FileUploader" method="post" enctype="multipart/form-data">
   <input type="hidden" name="action" value="Document" />
   <input type="hidden" name="GSDM" value="${D031H.GSDM}" />
   <input type="hidden" name="BDDM" value="${D031H.BDDM}" />
   <input type="hidden" name="BDBH" value="${D031H.BDBH}" />
   <br />

   <table>
    <tr>
     <th colspan="2" align="center">文件上傳<br/>File upload</th>
    </tr>
    <tr>
     <td><input type="file" name="file" style="font-size: large;" /></td>
     <td><button type="button" onclick="this.disabled=true;this.form.submit();">
       <img src="/iMes/stylesheet/icons/S_ATTACH.GIF" alt="" />
      </button></td>
    </tr>
   </table>
  </form>
  </div>
 </c:if>
 
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />