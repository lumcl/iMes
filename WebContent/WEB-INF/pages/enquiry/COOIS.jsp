<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">COOIS - 元件查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="COOIS" method="post">
   <input type="hidden" name="action" value="A">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#COOISA_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
    </div>

   <table>
    <tr>
     <th></th>
     <th>工單號碼<br/>PrdOrd</th>
     <th>物料群組<br/>MatGrp</th>
     <th>工單號碼<br/>PrdOrd</th>
     <th>物料群組<br/>MatGrp</th>
     <th>參數<br/>Param</th>
    </tr>
    <tr>
     <th>從</th>
     <td><input type="text" name="aufnr_low" size="15" value=${param.aufnr_low }></td>
     <td><input type="text" name="matkl_low" size="8" value=${param.matkl_low }></td>
     <td rowspan="2"><textarea cols="18" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="10" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
     <td><select name="tcode_params">
       <option value="">不使用預配置</option>
       <c:forEach var="e" items="${options}">
        <option value="${e.NAME1}"  <c:if test="${param.tcode_params == e.NAME1 }">selected="selected"</c:if>>${e.NAME1}</option>
       </c:forEach>
     </select></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="aufnr_high" size="15" value=${param.aufnr_high }></td>
     <td><input type="text" name="matkl_high" size="8" value=${param.matkl_high }></td>
     <td><input type="checkbox" name="xloek" value="X"  <c:if test="${param.xloek == 'X'}">checked="checked"</c:if>> 顯示全部</td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="COOISA_table">
    <thead>
     <tr>
      <th>工單號<br/>PrdOrd</th>
      <th>機種號<br/>FinishGoods</th>
      <th>倉庫<br/>Whse</th>
      <th>料號<br/>Material</th>
      <th>需求<br/>ReqQty</th>
      <th>已領料<br/>Issued</th>
      <th>承約<br/>Booked</th>
      <th>未限制<br/>Unrest</th>
      <th>品質檢驗<br/>Inspec</th>
      <th>單位<br/>UM</th>
      <th>物料說明<br/>MatDesc</th>
      <th>物料群組<br/>MatGrp</th>
      <th>工序<br/>Op</th>
      <th>料序<br/>Mat</th>
      <th>工作中心<br/>WrkCtr</th>
      <th>說明<br/>Description</th>
      <th>備註<br/>RervTxt</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="e" items="${list}">
      <tr>
       <td>${e.AUFNR}</td>
       <td>${e.STLBEZ}</td>
       <td>${e.WERKS}</td>
       <td>${e.MATNR}</td>
       <td class="dec4"><fmt:formatNumber value="${e.BDMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ENMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.VMENG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.LABST}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.INSME}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.MAKTX}</td>
       <td>${e.MATKL}</td>
       <td>${e.VORNR}</td>
       <td>${e.POSNR}</td>
       <td>${e.ARBPL}</td>
       <td>${e.LTXA1}</td>
       <td>${e.POTX1}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>

 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />