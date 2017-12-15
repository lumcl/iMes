<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<c:if test="${pageContext.request.method=='POST'}">
 <script type="text/javascript">
    $(document).ready(function() {
      $('.datepick').each(function() {
        $(this).datepicker({
          dateFormat : 'yymmdd',
          changeMonth : true,
          changeYear : true
        }).bind('keydown', false);
      });
    });
  </script>

 <form action="D402" method="post" id="form2" name="form2" onsubmit="return confirm('請確認資料是否正確并需要建立《借(领)款單 D402》?');">
  <input type="hidden" name="action" value="CREATE" />
  <fieldset>
   <legend>建立借(领)款單</legend>
   <div id="icon">
    <button type="button" onclick="this.disabled=true;this.form.submit();">
     <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
    </button>
    <button type="reset">
     <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
    </button>
   </div>
   <table>
    <c:forEach var="e" items="${list}">
     <input type="hidden" name="Vbeln_Posnr" value="${e.VBELN}_${e.POSNR}" />
     <input type="hidden" name="${e.VBELN}_${e.POSNR}" value="${e.SPLIT}" />
     <tr>
      <th>分割</th>
      <th>歸屬</th>
      <th>倉庫</th>
      <th>客戶</th>
      <th>短名</th>
      <th>訂單號</th>
      <th>項號</th>
      <th>數量</th>
      <th>交貨日期</th>
      <th>可用日期</th>
      <th>產品號碼</th>
      <th>出貨</th>
      <th>付款條件</th>
      <th colspan="2">國貿條件</th>
      <th>客戶物料</th>
      <th>單價</th>
      <th>幣別</th>
      <th>產品說明</th>
     </tr>
     <tr>
      <td>${e.SPLIT}</td>
      <td>${e.VTWEG}</td>
      <td>${e.WERKS}</td>
      <td>${e.KUNNR}</td>
      <td>${e.SORTL}</td>
      <td>${e.VBELN}</td>
      <td>${e.POSNR}</td>
      <td class="int"><fmt:formatNumber value="${e.OMENG}" pattern="#,###" /></td>
      <td>${e.EDATU}</td>
      <td>${e.MBDAT}</td>
      <td>${e.MATNR}</td>
      <td>${e.VSART}</td>
      <td>${e.ZTERM}</td>
      <td>${e.INCO1}</td>
      <td>${e.INCO2}</td>
      <td>${e.KDMAT}</td>
      <td class="dec4"><fmt:formatNumber value="${e.NETPR}" pattern="#,###.0000" /></td>
      <td>${e.WAERK}</td>
      <td>${e.ARKTX}</td>
     </tr>
     <tr>
      <td colspan="19">
       <table>
        <tr>
         <th></th>
         <th></th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <th>${i}</th>
         </c:forEach>
        </tr>
        <tr>
         <th>數量</th>
         <th class="int"><fmt:formatNumber value="${e.OMENG}" pattern="#,###" /></th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <td><input type="text" name="q_${e.VBELN}_${e.POSNR}_${i}" id="q_${e.VBELN}_${e.POSNR}_${i}" size="5" /></td>
         </c:forEach>
        </tr>
        <tr>
         <th>交貨日期</th>
         <th>${e.EDATU}</th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <td><input type="text" name="d_${e.VBELN}_${e.POSNR}_${i}" id="d_${e.VBELN}_${e.POSNR}_${i}" size="5"
           class="datepick" /></td>
         </c:forEach>
        </tr>
        <tr>
         <th>可用日期</th>
         <th>${e.MBDAT}</th>
         <c:forEach var="i" begin="1" end="${e.SPLIT}" step="1">
          <td><input type="text" name="m_${e.VBELN}_${e.POSNR}_${i}" id="m_${e.VBELN}_${e.POSNR}_${i}" size="5"
           class="datepick" /></td>
         </c:forEach>
        </tr>
       </table>
      </td>
     </tr>
    </c:forEach>
   </table>
   <table>
    <tr>
     <th>異動原因說明</th>
     <td><textarea name="YYSM" cols="80" rows="5"></textarea></td>
    </tr>
    <tr>
     <th>異動原因類別</th>
     <td><select name="YYLB">
       <option value="A. 客戶需求提前">A. 客戶需求提前</option>
       <option value="B. 客戶需求延後">B. 客戶需求延後</option>
       <option value="C. 客戶需求減少">C. 客戶需求減少</option>
       <option value="D. 客戶需求增加">D. 客戶需求增加</option>
       <option value="E. 客戶需求轉開">E. 客戶需求轉開</option>
       <option value="F. 物料因素延後">F. 物料因素延後</option>
       <option value="G. 品質因素延後">G. 品質因素延後</option>
       <option value="H. 產能因素調整">H. 產能因素調整</option>
       <option value="I. 試產失敗延後">I. 試產失敗延後</option>
       <option value="J. 安規因素調整">J. 安規因素調整</option>
       <option value="K. LEI自主取消避險">K. LEI自主取消避險</option>
       <option value="L. 取消重開改單價">L. 取消重開改單價</option>
       <option value="M. 業務合併出貨">M. 業務合併出貨</option>
     </select></td>
    </tr>
    <tr>
     <th>公司代碼</th>
     <td><select name="GSDM">
       <option value="L100">L100</option>
       <option value="L210">L210</option>
       <option value="L300">L300</option>
       <option value="L400" selected="selected">L400</option>
     </select></td>
    </tr>
   </table>
  </fieldset>
 </form>
</c:if>

<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />