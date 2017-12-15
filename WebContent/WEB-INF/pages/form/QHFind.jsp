<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/layout/header.jsp" />

<div id="tabs">
 <ul>
  <li><a href="#tab-1">電子簽核查詢</a></li>
 </ul>

 <div id="tab-1">

  <form action="qh" method="post" id="form1" name="form1">
   <input type="hidden" name="action" value="Find" />
   <fieldset>
    <div id="icon">
     <button type="button" onclick="this.disabled=true;this.form.submit();">
      <img src="/iMes/stylesheet/icons/S_B_EXEC.GIF" alt="" />
     </button>
     <button type="reset">
      <img src="/iMes/stylesheet/icons/S_F_REDO.GIF" alt="" />
     </button>
     <button type="button" onclick="history.go(-1)">
      <img src="/iMes/stylesheet/icons/S_F_BACK.GIF" alt="" />
     </button>
    </div>

    <table>
     <thead>
      <tr>
       <th></th>
       <th>申請人</th>
       <th>狀態</th>
       <th>申請日期</th>
       <th>公司</th>
       <th>表單類別</th>
       <th>表單號碼</th>
       <th>簽核人</th>
      </tr>
     </thead>
     <tbody>
      <tr>
       <th>從</th>
       <td><input type="text" name="STRSQYH" value="${param.STRSQYH }"></td>
       <td><input type="text" name="STRQHZT" value="${param.STRQHZT }"></td>
       <td><input type="text" name="STRJLSJ" value="${param.STRJLSJ }"></td>
       <td><input type="text" name="STRGSDM" value="${param.STRGSDM }"></td>
       <td><input type="text" name="STRBDDM" value="${param.STRBDDM }"></td>
       <td><input type="text" name="STRBDBH" value="${param.STRBDBH }"></td>
       <td><input type="text" name="STRYSYH" value="${param.STRYSYH }"></td>
      </tr>
      <tr>
       <th>到</th>
       <td><input type="text" name="ENDSQYH" value="${param.ENDSQYH }"></td>
       <td><input type="text" name="ENDQHZT" value="${param.ENDQHZT }"></td>
       <td><input type="text" name="ENDJLSJ" value="${param.ENDJLSJ }"></td>
       <td><input type="text" name="ENDGSDM" value="${param.ENDGSDM }"></td>
       <td><input type="text" name="ENDBDDM" value="${param.ENDBDDM }"></td>
       <td><input type="text" name="ENDBDBH" value="${param.ENDBDBH }"></td>
       <td><input type="text" name="ENDYSYH" value="${param.ENDYSYH }"></td>
      </tr>
     </tbody>
    </table>

    <table>
     <thead>
      <tr>
       <th></th>
       <th>狀態</th>
       <th>公司</th>
       <th>表單</th>
       <th>表單編號</th>
       <th>項號</th>
       <th>表單內文</th>
       <th>申請人</th>
       <th>簽核人</th>
       <th>簽核</th>
       <th>結果</th>
       <th>建立時間</th>
       <th>通知時間</th>
       <th>有效時間</th>
      </tr>
     </thead>
     <tbody>
      <c:forEach var="e" items="${list}">
       <tr>
        <td><button type="button" onclick="location.href='${e.BDDM}?action=QianHe&GSDM=${e.GSDM}&BDDM=${e.BDDM}&BDBH=${e.BDBH}'">
          <img src="/iMes/stylesheet/icons/S_B_CHNG.GIF" alt="" />
         </button></td>
        <td>${e.QHZT}</td>
        <td>${e.GSDM}</td>
        <td>${e.BDDM}</td>
        <td>${e.BDBH}</td>
        <td>${e.BZDM}</td>
        <td><textarea cols="50" rows="3" readonly="readonly">${e.BDTX}</textarea></td>
        <td>${e.SQYH}</td>
        <td>${e.YSYH}</td>
        <td align="center">${e.QHLX}</td>
        <td align="center">${e.QHJG}</td>
        <td><fmt:formatDate value="${e.JLSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
        <td><fmt:formatDate value="${e.YJSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
        <td><fmt:formatDate value="${e.YXSJ}" type="both" pattern="yyyyMMdd HH:mm" /></td>
       </tr>
      </c:forEach>
     </tbody>
    </table>
   </fieldset>
  </form>
 </div>
</div>
<jsp:include page="/WEB-INF/pages/layout/footer.jsp" />