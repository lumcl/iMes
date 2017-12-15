package imes.web.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.IRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/DELEGATE", "/DELEGATE/*" })
public class Delegate extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");
		if (action.equals("LIST")) {
			
			doListPostAction(req, resp);
			render(req, resp, "/WEB-INF/pages/login/Delegate.jsp");

		} else if (action.equals("CREATE")) {

		} else if (action.equals("validateUserId")) {

			PrintWriter out = resp.getWriter();
			out.print(validateUserId(getParam(req, "userid")));
			out.close();

		} else if (action.equals("validateDate")) {

			PrintWriter out = resp.getWriter();
			out.print(validateDate(req, resp));
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");

		if (action.equals("LIST")) {

			doListPostAction(req, resp);
			render(req, resp, "/WEB-INF/pages/login/Delegate.jsp");

		} else if (action.equals("CREATE")) {
			
			doCreatePostAction(req, resp);
			resp.sendRedirect("/iMes/DELEGATE/LIST");
			
		}
	}

	private void doCreatePostAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String sql = "INSERT INTO QH_YHDL (YXFD,YXTD,YHFR,YHTO,TBGP,DLYY,DLZT,BDDM) " //
				+ "VALUES(TO_DATE(?,'yyyymmdd'),TO_DATE(?,'yyyymmdd'),?,?,?,?,'A',?) "; //

		try {
			Connection conn = getConnection();
			PreparedStatement pstm = null;
			String yxfd = getParam(req, "yxfd");
			String yxtd = getParam(req, "yxtd");
			String uid = getUid(req);
			String yhto = getParam(req, "yhto");
			String dlyy = getParam(req, "dlyy");
			String [] bddm = req.getParameterValues("bddm");
			for(int i = 0; i< bddm.length;i++){
				if(!bddm[i].equals("")){
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, yxfd);
					pstm.setString(2, yxtd);
					pstm.setString(3, uid);
					pstm.setString(4, yhto);
					pstm.setString(5, "A");
					pstm.setString(6, dlyy);
					pstm.setString(7, bddm[i]);
					pstm.executeUpdate();
				}
			}
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doListPostAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT * FROM QH_YHDL WHERE YHFR=? ORDER BY YXFD DESC";

		if (!getParam(req, "yxfd_low").equals("")) {
			sql += Helper.sqlParams(req, "YXFD", "yxfd_low", "yxfd_high");
		}

		if (!getParam(req, "yxtd_low").equals("")) {
			sql += Helper.sqlParams(req, "YXTD", "yxtd_low", "yxtd_high");
		}

		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, getUid(req));
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.setAttribute("list", list);
	}

	private String validateUserId(String userId) {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) getServletContext().getAttribute("userIds");
		if (list.contains(userId)) {
			return "true";
		} else {
			return "false";
		}
	}

	private String validateDate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String str = "false";
		try {
			String sql = "SELECT COUNT(*) CNT " //
					+ "  FROM QH_YHDL " //
					+ " WHERE YHFR = ? " //
					+ "       AND ( (? BETWEEN TO_CHAR (YXFD, 'yyyymmdd') AND TO_CHAR (YXTD, 'yyyymmdd')) " //
					+ "            OR (? BETWEEN TO_CHAR (YXFD, 'yyyymmdd') AND TO_CHAR (YXTD, 'yyyymmdd'))) "; //
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, getUid(req));
			pstm.setString(2, getParam(req, "yxfd"));
			pstm.setString(3, getParam(req, "yxtd"));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				if (rst.getInt("CNT") == 0) {
					str = "true";
				}
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}
}
