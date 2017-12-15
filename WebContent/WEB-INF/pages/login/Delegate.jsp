<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript" src="/iMes/javascript/delegate.js"></script>

<div id="tabs">
 <ul>
  <li><a href="#tab-1">維護代理人[Maintain]</a></li>
  <li><a href="#tab-2">代理人查詢[List]</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/DELEGATE/CREATE" method="post" id="saveForm">
   <div id="icon">
    <button type=button id="saveBtn">
     <img src="/iMes/stylesheet/icons/S_F_SAVE.GIF" alt="保存" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
   </div>

   <table>
    <caption>設置代理人</caption>
    <tbody>
     <tr>
      <th>生效日期[Valid From]</th>
      <td><input type="text" name="yxfd" size="10" id="yxfd" class="datepick required"></td>
     </tr>
     <tr>
      <th>截止日期[Valid To]</th>
      <td><input type="text" name="yxtd" size="10" id="yxtd" class="datepick required"></td>
     </tr>
     <tr>
      <th>簽核人[Approval]</th>
      <td><input type="text" name="yhfr" size="30" value="${sessionScope.uid}" readonly="readonly"></td>
     </tr>
     <tr>
      <th>代理表单[Form]</th>
      <td>
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D031" checked="checked">D031
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D089" checked="checked">D089
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D188" checked="checked">D188
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D189" checked="checked">D189
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D272" checked="checked">D272
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D40R" checked="checked">D40R
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D40N" checked="checked">D40N
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D40G" checked="checked">D40G
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D40J" checked="checked">D40J
      <input type="checkbox" name="bddm" id="bddm" size="30" class="required" value="D40Y" checked="checked">D40Y
      </td>
     </tr>
     <tr>
      <th>代理人[Delegate]</th>
      <td><input type="text" name="yhto" size="30" id="yhto" class="required"></td>
     </tr>
     <tr>
      <th>代理原因[Reason]</th>
      <td><textarea cols="40" rows="5" name="dlyy" id="dlyy" class="required"></textarea></td>
     </tr>
    </tbody>
   </table>
  </form>

 </div>

<div id="tab-2">
  <form action="/iMes/DELEGATE/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="還原" />
    </button>
    <button type="button" onclick="$.toExcel('#Delegate_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <caption>代理人查詢</caption>
    <thead>
     <tr>
      <th></th>
      <th>開始時間<br />[Str Date]
      </th>
      <th>結束時間<br />[End Date]
      </th>
     </tr>
    </thead>
    <tbody>
     <tr>
      <th>從[From]</th>
      <td><input type="text" name="yxfd_low" size="10" value="${param.yxfd_low }" class="datepick"></td>
      <td><input type="text" name="yxtd_low" size="10" value="${param.yxtd_low }" class="datepick"></td>
     </tr>
     <tr>
      <th>到[Until]</th>
      <td><input type="text" name="yxfd_high" size="10" value="${param.yxfd_high }" class="datepick"></td>
      <td><input type="text" name="yxtd_high" size="10" value="${param.yxtd_high }" class="datepick"></td>
     </tr>
    </tbody>
   </table>
  </form>

   <table id="Delegate_table">
    <thead>
     <tr>
      <th>生效日期 [Valid From]</th>
      <th>截止日期 [Valid To]</th>
      <th>簽核人 [User]</th>
      <th>代理人 [Delegate]</th>
      <th>代理表单</th>
      <th>代理原因 [Reason]</th>
      <th>狀態[Status]</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr>
       <td id="YXFD"><fmt:formatDate value="${e.YXFD}" type="both" pattern="yyyyMMdd" /></td>
       <td id="YXTD"><fmt:formatDate value="${e.YXTD}" type="both" pattern="yyyyMMdd" /></td>
       <td>${e.YHFR}</td>
       <td>${e.YHTO}</td>
       <td>${e.BDDM}</td>
       <td>${e.DLYY}</td>
       <td><c:if test="${e.DLZT == 'A'}">生效[Valid]</c:if> <c:if test="${e.DLZT != 'A'}">失效[Invalid]</c:if></td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
 </div>
</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />