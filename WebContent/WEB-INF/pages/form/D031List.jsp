<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">訂單異動查詢   Order transaction query</a></li>
 </ul>

 <div id="tab-1">
  <form action="D031" method="post">
   <input type="hidden" name="action" value="List">
   <fieldset>
    <legend>訂單異動通知單查詢清單<br/>Order change notification query list</legend>
    <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
      <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
    </div>

    <table>
     <tr>
      <th></th>
      <th>從[Low]</th>
      <th>到[End]</th>
      <th></th>
      <th>從[Low]</th>
      <th>到[End]</th>
     </tr>
     <tr>
      <th>機種料號<br/>Product of material</th>
      <td><input type="text" name="STRMATNR" value="${param.STRMATNR }"></td>
      <td><input type="text" name="ENDMATNR" value="${param.ENDMATNR }"></td>
      <th>客戶代號<br/>Customer code</th>
      <td><input type="text" name="STRKUNNR" value="${param.STRKUNNR }"></td>
      <td><input type="text" name="ENDKUNNR" value="${param.ENDKUNNR }"></td>
     </tr>
     <tr>
      <th>申請人<br/>Applicant</th>
      <td><input type="text" name="STRSQYH" value="${param.STRSQYH }"></td>
      <td><input type="text" name="ENDSQYH" value="${param.ENDSQYH }"></td>
      <th>建立日期<br/>creation date</th>
      <td><input type="text" name="STRJLSJ" value="${param.STRJLSJ }"></td>
      <td><input type="text" name="ENDJLSJ" value="${param.ENDJLSJ }"></td>
     </tr>
     <tr>
      <th>表單編號<br/>Form number</th>
      <td><input type="text" name="STRBDBH" value="${param.STRBDBH }"></td>
      <td><input type="text" name="ENDBDBH" value="${param.ENDBDBH }"></td>
      <th>核准日期<br/>Approved date</th>
      <td><input type="text" name="STRQHSJ" value="${param.STRQHSJ }"></td>
      <td><input type="text" name="ENDQHSJ" value="${param.ENDQHSJ }"></td>
     </tr>
     <tr>
      <th></th>
      <th>原因類別<br/>Reason category</th>
      <th></th>
      <th></th>
      <th>核准結果<br/>Approved result</th>
      <th>表單狀態<br/>Form status</th>
     </tr>
     <tr>
      <th></th>
      <td><select name="STRYYLB" multiple="multiple" style="width:300px">
        <option value="A. 客戶需求提前">A. 客戶需求提前 Customer demand ahead of schedule</option>
        <option value="B. 客戶需求延後">B. 客戶需求延後Customer demand postponed</option>
        <option value="C. 客戶需求減少">C. 客戶需求減少Reduced customer demand</option>
        <option value="D. 客戶需求增加">D. 客戶需求增加Increased customer demand</option>
        <option value="E. 客戶需求轉開">E. 客戶需求轉開Customer needs to open</option>
        <option value="F. 物料因素延後">F. 物料因素延後Delayed material factors</option>
        <option value="G. 品質因素延後">G. 品質因素延後Delay of quality factors</option>
        <option value="H. 產能因素調整">H. 產能因素調整Adjustment of production capacity</option>
        <option value="I. 試產失敗延後">I. 試產失敗延後Failure to delivery delay</option>
        <option value="J. 安規因素調整">J. 安規因素調整Adjustment of safety factor</option>
        <option value="K. LEI自主取消避險">K. LEI自主取消避險LEI self cancellation hedge</option>
        <option value="L. 取消重開改單價">L. 取消重開改單價Cancel re opened to change the unit price</option>
        <option value="M. 業務合併出貨">M. 業務合併出貨Business combination shipping</option>
	      <option value="N. 當月跨月提前">N. 當月跨月提前Month ahead of the month</option>
        <option value="O. 當月跨月延後">O. 當月跨月延後Month delay</option>
        <option value="P. 跨月延後">P. 跨月延後Months delayed</option>
      </select></td>
      <td></td>
      <th></th>
      <td><input type="radio" name="STRBDJG" value="Y" <c:if test="${param.STRBDJG=='Y'}">CHECKED</c:if>>[Y] 已核准Approved<br /> <input type="radio" name="STRBDJG" value="N"
        <c:if test="${param.STRBDJG=='N'}">CHECKED</c:if>>[N] 已退件Back part<br /> <input type="radio" name="STRBDJG" value="0" <c:if test="${param.STRBDJG=='0'}">CHECKED</c:if>>[0] 核准中in Approving</td>
      <td><input type="radio" name="STRBDZT" value="0">[0] 簽核中In the nucleus<br /> <input type="radio" name="STRBDZT" value="X">[X] 簽核完成Nuclear completion</td>
     </tr>
    </table>
   </fieldset>
  </form>



  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D031List')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>
   <table id="D031List">
    <thead>
     <tr>
      <th></th>
      <th>結果<br/>Results</th>
      <th>狀態<br/>State</th>
      <th>表單編碼<br/>Form coding</th>
      <th>倉庫<br/>Warehouse</th>
      <th>客戶代號<br/>Customer code</th>
      <th>名稱<br/>Name</th>
      <th>SO單號<br/>SO number</th>
      <th>SO項號<br/>SO item number</th>
      <th>機種<br/>Product</th>
      <th>申請人<br/>Applicant</th>
      <th>申請時間<br/>Application time</th>
      <th>幣別<br/>Currency</th>
      <th>單價<br/>Unit price</th>
      <th>移動前數量<br/>Number before moving</th>
      <th>移動前交期<br/>Delivery date before moving</th>
      <th>修改后數量<br/>Modified number</th>
      <th>修改后交期<br/>Modified delivery time</th>
      <th>原因類別<br/>Reason category</th>
      <th>正在簽核人<br/>Being signed on</th>
      <th>核准人<br/>Approved person</th>
      <th>核准時間<br/>Approval time</th>
      <th>原因說明<br/>Explanation of reason</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>
       <td><button type="button" onclick="location.href='${e.BDDM}?action=QianHe&GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
        </button></td>
       <td>${e.BDJG}</td>
       <td>${e.BDZT}</td>
       <td>${e.BDBH}</td>
       <td>${e.WERKS}</td>
       <td>${e.KUNNR}</td>
       <td>${e.SORTL}</td>
       <td>${e.VBELN}</td>
       <td>${e.POSNR}</td>
       <td>${e.MATNR}</td>
       <td>${e.JLYH}</td>
       <td>${e.JLSJ}</td>
       <td>${e.WAERK}</td>
       <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.0000" /></td>
       <td class="int"><fmt:formatNumber value="${e.BFQTY}" pattern="#,###" /></td>
       <td>${e.BFDAT}</td>
       <td class="int"><fmt:formatNumber value="${e.AFQTY}" pattern="#,###" /></td>
       <td>${e.AFDAT}</td>
       <td>${e.YYLB}</td>
       <td>${e.YSYH}</td>
       <td>${e.QHYH}</td>
       <td>${e.QHSJ}</td>
       <td>${e.YYSM}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>
 </div>
</div>
<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />