<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table>
 <caption>半成品展材料明細</caption>
 <thead>
  <tr>
   <th></th>
   <th>替代</th>
   <th>主/替</th>
   <th>組</th>
   <th>料號</th>
   <th>倉庫</th>
   <th>料類</th>
   <th>規格</th>
   <th>單位</th>
   <th>耗用量</th>
   <th>需求量</th>
   <th>庫存數</th>
   <th>檢驗數</th>
   
  </tr>
 </thead>
 <tbody>
  <c:if test="${list == null }">
   <tr>
    <td><input type="checkbox" name="ROWID" value="1"></td>
    <td></td>
    <td></td>
    <td></td>
    <td><input type="text" name="CMATNR_1" value="" size="20" id="MATNR_1" onchange="chkMatnr(this.value,1)"></td>
    <td><select name="CWERKS_1">
      <option value="481A">481A</option>
      <option value="482A">482A</option>
    </select></td>
    <td id="CMATKL_1"></td>
    <td id="MAKTX_1"></td>
    <td></td>
    <td></td>
    <td class="dec4"><input type="text" name="REQTY_1" value='<fmt:formatNumber value="0" pattern="#,###.###" />' size="10" style="text-align: right;"
      onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
    <td></td>
    <td></td>
   </tr>

   <tr>
    <td><input type="checkbox" name="ROWID" value="2"></td>
    <td></td>
    <td></td>
    <td></td>
    <td><input type="text" name="CMATNR_2" value="" size="20" id="MATNR_2" onchange="chkMatnr(this.value,2)"></td>
    <td><select name="CWERKS_2">
      <option value="481A">481A</option>
      <option value="482A">482A</option>
    </select></td>
    <td id="CMATKL_2"></td>
    <td id="MAKTX_2"></td>
    <td></td>
    <td></td>
    <td class="dec4"><input type="text" name="REQTY_2" value='<fmt:formatNumber value="0" pattern="#,###.###" />' size="10" style="text-align: right;"
      onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
    <td></td>
    <td></td>
   </tr>

   <tr>
    <td><input type="checkbox" name="ROWID" value="3"></td>
    <td></td>
    <td></td>
    <td></td>
    <td><input type="text" name="CMATNR_3" value="" size="20" id="MATNR_3" onchange="chkMatnr(this.value,3)"></td>
    <td><select name="CWERKS_3">
      <option value="481A">481A</option>
      <option value="482A">482A</option>
    </select></td>
    <td id="CMATKL_3"></td>
    <td id="MAKTX_3"></td>
    <td></td>
    <td></td>
    <td class="dec4"><input type="text" name="REQTY_3" value='<fmt:formatNumber value="0" pattern="#,###.###" />' size="10" style="text-align: right;"
      onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
    <td></td>
    <td></td>
   </tr>

   <tr>
    <td><input type="checkbox" name="ROWID" value="4"></td>
    <td></td>
    <td></td>
    <td></td>
    <td><input type="text" name="CMATNR_4" value="" size="20" id="MATNR_4" onchange="chkMatnr(this.value,4)"></td>
    <td><select name="CWERKS_4">
      <option value="481A">481A</option>
      <option value="482A">482A</option>
    </select></td>
    <td id="CMATKL_4"></td>
    <td id="MAKTX_4"></td>
    <td></td>
    <td></td>
    <td class="dec4"><input type="text" name="REQTY_4" value='<fmt:formatNumber value="0" pattern="#,###.###" />' size="10" style="text-align: right;"
      onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
    <td></td>
    <td></td>
   </tr>

   <tr>
    <td><input type="checkbox" name="ROWID" value="5"></td>
    <td></td>
    <td></td>
    <td></td>
    <td><input type="text" name="CMATNR_5" value="" size="20" id="MATNR_5" onchange="chkMatnr(this.value,5)"></td>
    <td><select name="CWERKS_5">
      <option value="481A">481A</option>
      <option value="482A">482A</option>
    </select></td>
    <td id="CMATKL_5"></td>
    <td id="MAKTX_5"></td>
    <td></td>
    <td></td>
    <td class="dec4"><input type="text" name="REQTY_5" value='<fmt:formatNumber value="0" pattern="#,###.###" />' size="10" style="text-align: right;"
      onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
    <td></td>
    <td></td>
   </tr>
  </c:if>
  <c:forEach var="e" items="${list}">
   <tr>
    <td><input type="checkbox" name="ROWID" value="${e.ROWNUM}" <c:if test="${e.WIPCOMP == 'X'}">checked="checked"</c:if> ></td>
    <td>${e.ALPOS}</td>
    <td>${e.EWAHR}</td>
    <td>${e.ALPGR}</td>
    <td><input type="text" name="CMATNR_${e.ROWNUM}" value="${e.CMATNR}" readonly="readonly" size="20" tabindex="99"></td>
    <td><input type="text" name="CWERKS_${e.ROWNUM}" value="${e.CWERKS}" size="5"></td>
    <td>${e.CMATKL}</td>
    <td>${e.CMAKTX}</td>
    <td>${e.DUOM}</td>
    <td class="dec4"><fmt:formatNumber value="${e.DUSAGE}" pattern="#,###.###" /></td>
    <td class="dec4"><input type="text" name="REQTY_${e.ROWNUM}" value='<fmt:formatNumber value="${e.REQTY}" pattern="#,###.###" />' size="10" style="text-align: right;"
      onkeyup="if(isNaN(value)) value=value.substring(0,value.length-1);"></td>
    <td class="dec4"><fmt:formatNumber value="${e.LABST}" pattern="#,###.###" /></td>
    <td class="dec4"><fmt:formatNumber value="${e.INSME}" pattern="#,###.###" /></td>
   </tr>
  </c:forEach>
 </tbody>
</table>