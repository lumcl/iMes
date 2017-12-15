<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<script type="text/javascript">
function create() {
    if (confirm('is the Heavy Requisition for form to created? 是否寫重工單？')) {
    	location.href='/iMes/D189/CREATE';
    }
}
$(document).ready(
	function(){      
      $("#create").click(function(event) {
    	  create();
      });
	});
</script>
<div id="tabs">
 <ul>
  <li><a href="#tab-1">Heavy Requisition<br />   重工申请单</a></li>
 </ul>

 <div id="tab-1">
  <form action="/iMes/D189/LIST" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="Query 查詢" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="Return 還原" />
    </button>
    <c:if test="${kz_user == 'Y'}">
    <button id="create" type="button" onclick="create();">
     <img src="/iMes/stylesheet/icons/S_B_INSR.GIF" alt="The Heavy Requisition is created 建立重工单" />
    </button>
    </c:if>
   </div>

   <table>
    <tr>
     <th></th>
     <th>Company Code <br />公司</th>
     <th>Form Number<br />單號</th>
     <th>Form date<br />日期</th>
     <th>Applicant<br />申請人</th>
     <th>Result<br/>結果</th>
     <th>Status<br/>狀態</th>
    </tr>
    <tr>
     <th>from<br /> 從</th>
     <th><input type="text" name="STRGSDM" value="${param.STRGSDM}" size="5"></th>
     <th><input type="text" name="STRBDBH" value="${param.STRBDBH}"></th>
     <th><input type="text" name="STRBDRQ" value="${param.STRBDRQ}" size="9"></th>
     <th><input type="text" name="SQYH" value="${param.SQYH}"></th>
     <th><select name="BDJG">
        <option value="" <c:if test="${(param.BDJG == '') || (empty param.BDJG)}" >selected="selected"</c:if>>ALL</option>
        <option value="OTHER" <c:if test="${(param.BDJG != 'Y') && (param.BDJG != 'N') && (param.BDJG != '') && (!empty param.BDJG)}" >selected="selected"</c:if>>未簽核</option>
        <option value="Y" <c:if test="${param.BDJG == 'Y'}" >selected="selected"</c:if>>核准</option>
        <option value="N" <c:if test="${param.BDJG == 'N'}" >selected="selected"</c:if>>退單/作廢</option>
      </select></th>
     <th><select name="BDZT">
        <option value="" <c:if test="${param.BDZT == ''}" >selected="selected"</c:if>>ALL</option>
        <option value="OTHER" <c:if test="${(param.BDZT != '0') && (param.BDZT != 'X') && (param.BDZT != '') && (!empty param.BDZT)}" >selected="selected"</c:if>>流程未啟動</option>
        <option value="0" <c:if test="${param.BDZT == '0'}" >selected="selected"</c:if>>簽核中</option>
        <option value="X" <c:if test="${param.BDZT == 'X'}" >selected="selected"</c:if>>完成</option>
      </select></th>
    </tr>
    <tr>
     <th>to<br />到</th>
     <th><input type="text" name="ENDGSDM" value="${param.ENDGSDM}" size="5"></th>
     <th><input type="text" name="ENDBDBH" value="${param.ENDBDBH}"></th>
     <th><input type="text" name="ENDBDRQ" value="${param.ENDBDRQ}" size="9"></th>
     <th></th>
     <th></th>
     <th></th>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#D189HList')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
    <a href="/iMes/D189/EXPORTEXCEL?SQL=${D189sqlrst}">导出Excel</a>
   </div>
   <table id="D189HList">
    <thead>
     <tr>
      <th></th>
      <th>Company Code <br />公司</th>
      <th>Form Number<br />單號</th>
      <th>Form date<br />日期</th>
      <th>Result<br/>結果</th>
      <th>Status<br/>狀態</th>
      <th>Applicant<br />申請人</th>
      <th>Department Name<br />部門名稱</th>
      <th>Print<br />打印</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>
       <td><button type="button" onclick="location.href='/iMes/${e.BDDM}/EDIT?GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
         <img src="/iMes/stylesheet/icons/S_B_OVIW.GIF" alt="" />
        </button></td>
       <td>${e.GSDM}</td>
       <td>${e.BDBH}</td>
       <td>${e.BDRQ}</td>
       <c:choose>
       <c:when test="${e.BDJG == 'Y'}">
       <td>Approved 核准</td>
       </c:when>
       <c:when test="${e.BDJG == 'N'}">
       <td>Refund 退單 /Cancellation 作廢</td>
       </c:when>
	   <c:otherwise>
       <td>Not signed 未簽核</td>
	   </c:otherwise>
       </c:choose>
       <c:choose>
       <c:when test="${e.BDZT == '0'}">
       <td>Checking 簽核中</td>
       </c:when>
       <c:when test="${e.BDZT == 'X'}">
       <td>Complete 完成</td>
       </c:when>
	   <c:otherwise>
       <td>Process is not started 流程未啟動</td>
	   </c:otherwise>
	   </c:choose>
       <td>${e.SQYH}</td>
       <td>${e.BMMC}</td>
       <c:choose>
       <c:when test="${e.DYZT == '1'}">
       <td>Printed 已经打印</td>
       </c:when>
	   <c:otherwise>
       <td>Not print 未打印</td>
	   </c:otherwise>
	   </c:choose>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />