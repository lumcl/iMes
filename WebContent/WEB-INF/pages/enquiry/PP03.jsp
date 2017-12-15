<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">PP03 - 生產訂單元件查詢</a></li>
 </ul>

 <div id="tab-1">
  <form action="PP03" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#PP03_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th></th>
     <th>只顯示<br/>Only</th>
     <th>工廠<br/>Plan</th>
     <th>工單/計劃單<br/>Order</th>
     <th>計劃員<br/>Planner</th>
     <th>基本開始日期<br/>Start Dt</th>
     <th>基本完成日期<br/>End Dt</th>
     <th>機種<br/>Finish Goods</th>
     <th>元件<br/>Component</th>
     <th>工單/計劃單<br/>Order</th>
     <th>計劃員<br/>Planner</th>
     <th>機種<br/>Finish Goods</th>
     <th>元件<br/>Component</th>
     <th>採購群組<br/>PurGrp</th>
     <th>料類<br/>MatGrp</th>
    </tr>
    <tr>
     <th>從</th>
     <td rowspan="2">
      <input type="checkbox" name="teko_checkbox" value="X"  checked="checked" > 未完工[Open] 
      <br/>
      <input type="checkbox" name="aufnr_checkbox" value="X" <c:if test="${param.aufnr_checkbox=='X'}"> checked="checked" </c:if>> 工單[MO] 
      <br/>
      <input type="checkbox" name="xfehl_checkbox" value="X" <c:if test="${param.xfehl_checkbox=='X'}"> checked="checked" </c:if>> 欠料[Less] 
      <br/>
      <input type="checkbox" name="extra_checkbox" value="X" <c:if test="${param.extra_checkbox=='X'}"> checked="checked" </c:if>> 溢領[Extra]
     </td>
     <td><input type="text" name="werks_low" size="6" value=${param.werks_low }></td>
     <td><input type="text" name="aufnr_low" size="14" value=${param.aufnr_low }></td>
     <td><input type="text" name="dispo_low" size="5" value=${param.dispo_low }></td>
     <td><input type="text" name="gstrp_low" size="10" value=${param.gstrp_low }></td>
     <td><input type="text" name="gltrp_low" size="10" value=${param.gltrp_low }></td>
     <td><input type="text" name="pmatnr_low" size="20" value=${param.pmatnr_low }></td>
     <td><input type="text" name="cmatnr_low" size="20" value=${param.cmatnr_low }></td>
     <td rowspan="2"><textarea cols="16" rows="4" name="aufnr_textarea">${param.aufnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="7" rows="4" name="dispo_textarea">${param.dispo_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="pmatnr_textarea">${param.pmatnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="22" rows="4" name="cmatnr_textarea">${param.cmatnr_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="7" rows="4" name="ekgrp_textarea">${param.ekgrp_textarea}</textarea></td>
     <td rowspan="2"><textarea cols="9" rows="4" name="matkl_textarea">${param.matkl_textarea}</textarea></td>
    </tr>
    <tr>
     <th>到</th>
     <td><input type="text" name="werks_high" size="6" value=${param.werks_high }></td>
     <td><input type="text" name="aufnr_high" size="14" value=${param.aufnr_high }></td>
     <td><input type="text" name="dispo_high" size="5" value=${param.dispo_high }></td>
     <td><input type="text" name="gstrp_high" size="10" value=${param.gstrp_high }></td>
     <td><input type="text" name="gltrp_high" size="10" value=${param.gltrp_high }></td>
     <td><input type="text" name="pmatnr_high" size="20" value=${param.pmatnr_high }></td>
     <td><input type="text" name="cmatnr_high" size="20" value=${param.cmatnr_high }></td>
    </tr>
   </table>
  </form>

  <c:if test="${pageContext.request.method=='POST'}">
   <table id="PP03_table">
    <thead>
     <tr>
      <th>項號<br/>Seq</th>
      <th>工廠<br/>Plan</th>
      <th>工單<br/>Order</th>
      <th>機種<br/>Finish Goods</th>
      <th>工序<br/>Route</th>
      <th>料序<br/>MatSeq</th>
      <th>料號<br/>Material</th>
      <th>規格<br/>Description</th>
      <th>需求日<br/>ReqDt</th>
      <th>需求<br/>ReqQty</th>
      <th>承約<br/>Reserved</th>
      <th>已領<br/>Issued</th>
      <th>缺料<br/>Less</th>
      <th>未限制<br/>Unrest</th>
      <th>品檢<br/>Inspec</th>
      <th>RVMI<br/>RVMI</th>
      <th>單位<br/>UM</th>
      <th>採購<br/>PGrp</th>
      <th>料類<br/>MGrp</th>
      <th>倉庫<br/>Whse</th>
      <th>儲位<br/>Loc</th>
      <th>倉庫2<br/>Whse2</th>
      <th>庫存2<br/>Bal2</th>
      <th>單耗<br/>Usage</th>
      <th>損耗<br/>Loss</th>
      <th>位置<br/>Pos.</th>
      <th>作業說明<br/>WorkCenter</th>
      <th>機種說明<br/>FG Description</th>
      <th>PC</th>
      <th>半成品<br/>WIP</th>
      <th>ECN</th>
      <th>圖號<br/>Drawing</th>
      <th>虛擬<br/>Virt</th>
      <th>結料<br/>Comp</th>
      <th>刪除<br/>Dlt</th>
      <th>欠料<br/>Less</th>
      <th>替代<br/>Alt</th>
      <th>主替<br/>Main</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${list}" var="e">
      <tr ${e.COLOR}>
       <td class="dec4"><fmt:formatNumber value="${e.ROWID}" pattern="#,###.###" /></td>
       <td>${e.PWERK}</td>
       <td><fmt:formatNumber value="${e.AUFNR}" pattern="#" /></td>
       <td>${e.PMATNR}</td>
       <td>${e.VORNR}</td>
       <td>${e.POSNR}</td>
       <td>${e.MATNR}</td>
       <td>${e.MAKTX}</td>
       <td>${e.BDTER}</td>
       <td class="dec4"><fmt:formatNumber value="${e.BDMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.VMENG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ENMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.QTY}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.LABST}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.INSME}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.RVMI}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.EKGRP}</td>
       <td>${e.MATKL}</td>
       <td>${e.WERKS}</td>
       <td>${e.LGORT}</td>
       <td bgcolor="#ccecff">${e.WERKSA}</td>
       <td bgcolor="#ccecff" class="dec4"><fmt:formatNumber value="${e.MARDA}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ESMNG}" pattern="#,###.######" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.AUSCH}" pattern="#,###.###" /></td>
       <td>${e.POTX1}</td>
       <td>${e.LTXA1}</td>
       <td>${e.PMAKTX}</td>
       <td>${e.DISPO}</td>
       <td>${e.BAUGR}</td>
       <td>${e.AENNR}</td>
       <td>${e.ZEINR}</td>
       <td>${e.DUMPS}</td>
       <td>${e.KZEAR}</td>
       <td>${e.XLOEK}</td>
       <td>${e.XFEHL}</td>
       <td>${e.ALPOS}</td>
       <td class="dec4"><fmt:formatNumber value="${e.EWAHR}" pattern="#,###.###" /></td>
      </tr>
     </c:forEach>
    </tbody>
   </table>

  </c:if>

 </div>


</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />