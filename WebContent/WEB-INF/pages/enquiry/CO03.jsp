<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">CO03 - 生產訂單 - 材料</a></li>
  <li><a href="#tab-2">CO03 - 生產訂單 - 作業</a></li>
 </ul>

 <div id="tab-1">
  <form action="CO03" method="post">
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
    <button type="button" onclick="$.toExcel('#CO03M_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <table>
    <tr>
     <th>生產訂單<br/>PrdOrd</th>
     <td><input type="text" name="aufnr_low" size="15" value=${param.aufnr_low }></td>
    </tr>
   </table>
  </form>
  <c:if test="${pageContext.request.method=='POST'}">
   <c:forEach items="${afpo}" var="e">

    <table>
     <thead>
      <tr>
       <th>生產訂單<br/>PrdOrd</th>
       <th>工廠<br/>Plant</th>
       <th>工單數<br/>OrdQty</th>
       <th>已交貨<br/>FinQty</th>
       <th>單位<br/>UM</th>
       <th>機種<br/>FinishGoods</th>
       <th>說明<br/>Description</th>
       <th>基本開始日<br/>StrDt</th>
       <th>基本完成日<br/>EndDt</th>
       <th>計劃員<br/>Planner</th>
      </tr>
     </thead>
     <tbody>
      <tr>
       <td>${e.AUFNR }</td>
       <td>${e.PWERK }</td>
       <td class="dec4"><fmt:formatNumber value="${e.PSMNG}" pattern="#,###.### " /></td>
       <td class="dec4"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###.### " /></td>
       <td>${e.MEINS }</td>
       <td>${e.MATNR }</td>
       <td>${e.MAKTX}</td>
       <td class="int"><fmt:formatNumber value="${e.GSTRP}" pattern="#" /></td>
       <td class="int"><fmt:formatNumber value="${e.GLTRP}" pattern="#" /></td>
       <td>${e.DISPO }</td>
      </tr>
     </tbody>
    </table>
   </c:forEach>

   <table id="CO03M_table">
    <thead>
     <tr>
      <th>項號<br/>Sq</th>
      <th>工序<br/>Op</th>
      <th>料序<br/>MSq</th>
      <th>料號<br/>Component</th>
      <th>規格<br/>Description</th>
      <th>料類<br/>MatGrp</th>
      <th>倉庫<br/>Whse</th>
      <th>儲位<br/>Loc</th>
      <th>單個用量<br/>Usage</th>
      <th>損耗<br/>Loss</th>
      <th>需求<br/>ReqQty</th>
      <th>承約<br/>Booked</th>
      <th>已領<br/>Issued</th>
      <th>單位<br/>UM</th>
      <th>需求日<br/>ReqDt</th>
      <th>位置<br/>Pos</th>
      <th>半成品<br/>Wip</th>
      <th>ECN</th>
      <th>圖號<br/>Drawing</th>
      <th>虛擬<br/>Virt</th>
      <th>結料<br/>FinM</th>
      <th>刪除<br/>Dlt</th>
      <th>欠料<br/>Less</th>
      <th>替代<br/>Alt</th>
      <th>主替<br/>Main</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${resb}" var="e">
      <tr ${e.COLOR}>
       <td class="dec4"><fmt:formatNumber value="${e.ROWID}" pattern="#,###.###" /></td>
       <td>${e.VORNR}</td>
       <td>${e.POSNR}</td>
       <td>${e.MATNR}</td>
       <td>${e.MAKTX}</td>
       <td>${e.MATKL}</td>
       <td>${e.WERKS}</td>
       <td>${e.LGORT}</td>
       <td class="dec4"><fmt:formatNumber value="${e.ESMNG}" pattern="#,###.######" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.AUSCH}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.BDMNG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.VMENG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ENMNG}" pattern="#,###.###" /></td>
       <td>${e.MEINS}</td>
       <td>${e.BDTER}</td>
       <td>${e.POTX1}</td>
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

 <div id="tab-2">
  <c:if test="${pageContext.request.method=='POST'}">
   <div id="icon">
    <button type="button" onclick="$.toExcel('#CO03R_table')">
     <img src="/iMes/stylesheet/icons/S_X__XLS.GIF" alt="Excel" />
    </button>
   </div>

   <c:forEach items="${afpo}" var="e">
    <table>
     <thead>
      <tr>
       <th>生產訂單<br/>PrdOrd</th>
       <th>工廠<br/>Plant</th>
       <th>工單數<br/>OrdQty</th>
       <th>已交貨<br/>FinQty</th>
       <th>單位<br/>UM</th>
       <th>機種<br/>FinishGoods</th>
       <th>說明<br/>Desciption</th>
       <th>基本開始日<br/>StrDt</th>
       <th>基本完成日<br/>EndDt</th>
       <th>計劃員<br/>Planner</th>
      </tr>
     </thead>
     <tbody>
      <tr>
       <td>${e.AUFNR }</td>
       <td>${e.PWERK }</td>
       <td class="dec4"><fmt:formatNumber value="${e.PSMNG}" pattern="#,###.### " /></td>
       <td class="dec4"><fmt:formatNumber value="${e.WEMNG}" pattern="#,###.### " /></td>
       <td>${e.MEINS }</td>
       <td>${e.MATNR }</td>
       <td>${e.MAKTX}</td>
       <td class="int"><fmt:formatNumber value="${e.GSTRP}" pattern="#" /></td>
       <td class="int"><fmt:formatNumber value="${e.GLTRP}" pattern="#" /></td>
       <td>${e.DISPO }</td>
      </tr>
     </tbody>
    </table>
   </c:forEach>

   <table id="CO03R_table">
    <thead>
     <tr>
      <th>工序<br/>Op</th>
      <th>工作中心<br/>WrkCtr</th>
      <th>說明<br/>Description</th>
      <th>開始日期<br/>StrDt</th>
      <th>開始時間<br/>EndDt</th>
      <th>作業數<br/>ReqQty</th>
      <th>完成數<br/>FinQty</th>
      <th>UM</th>
      <th>處理小時<br/>Process</th>
      <th>標準小時<br/>StdHrs</th>
      <th>基準<br/>Base</th>
      <th>作業小時<br/>LabHrs</th>
      <th>機器小時<br/>MacHrs</th>
      <th>實際開始<br/>ActStr</th>
      <th>實際完成<br/>ActEnd</th>
      <th>正常<br/>Norm</th>
      <th>A</th>
      <th>B</th>
      <th>C</th>
      <th>工時<br/>Lab</th>
      <th>機器<br/>Mac</th>
      <th>類型<br/>Type</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach items="${afvc}" var="e">
      <tr>
       <td>${e.VORNR}</td>
       <td>${e.ARBPL}</td>
       <td>${e.LTXA1}</td>
       <td>${e.SSAVD}</td>
       <td>${e.SSAVZ}</td>
       <td class="dec4"><fmt:formatNumber value="${e.MGVRG}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.LMNGA}" pattern="#,###.###" /></td>
       <td>${e.MEINH}</td>
       <td class="dec4"><fmt:formatNumber value="${e.BEARZ}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.TVGW01}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.BMSCH}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.VGW01}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.VGW05}" pattern="#,###.###" /></td>
       <td>${e.ISAVD}</td>
       <td>${e.IEAVD}</td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM01}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM02}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM03}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM04}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.LABHR}" pattern="#,###.###" /></td>
       <td class="dec4"><fmt:formatNumber value="${e.ISM05}" pattern="#,###.###" /></td>
       <td>${e.STEUS}</td>
      </tr>
     </c:forEach>
    </tbody>
   </table>
  </c:if>
 </div>

</div>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />