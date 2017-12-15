package imes.web.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.AppConst;
import imes.core.Helper;
import imes.core.HttpController;
import imes.dao.BaseDao;
import imes.entity.D050H;

@WebServlet({ "/D050", "/D050/*" })
public class D050 extends HttpController {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/D050/enquiry")) {
			enquiry();
		}

		else if (req.getRequestURI().contains("newHeader")) {
			newHeader();
		}
		
		else if (req.getRequestURI().contains("ajaxGetCustomer")) {
			try {
				ajaxGetCustomer();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			render(req, resp, "/WEB-INF/pages/form/D050/list.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().contains("saveHeader")) {
			saveHeader();
		}
		
	}

	private void enquiry() {

	}
	
	private void newHeader() throws ServletException, IOException {
		D050H d050h = new D050H();
		d050h.setGsdm("L400");
		d050h.setBdrq(Helper.fmtDate(new Date(), "yyyyMMdd"));
		d050h.setSqyh(getUid(req));
		req.setAttribute("d050h", d050h);
		render(req, resp, "/WEB-INF/pages/form/D050/edit.jsp");
	}
	
	private void saveHeader() throws ServletException, IOException {
		D050H d050h = new D050H();
		BaseDao dao = new BaseDao();
		try {
			if (d050h.getId() == 0) {
				updateAttributes(req, d050h);
				d050h.setBddm("D050");
				d050h.setBdzt(AppConst.BdztCreate);
				d050h.setJlyh(getUid(req));
				d050h.setSqyh(getUid(req));
				d050h.setBdbh(Helper.getBDBH(getConnection(), d050h.getGsdm(), d050h.getBddm(), d050h.getBdrq().substring(0, 4)));
			} else {
				dao.get(d050h, d050h.getId());
				updateAttributes(req, d050h);
				d050h.setGxyh(getUid(req));
				d050h.setGxsj(new Date());
			}
			dao.setCon(getConnection());
			dao.save(d050h);
			dao.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		req.setAttribute("d050h", d050h);
		render(req, resp, "/WEB-INF/pages/form/D050/edit.jsp");
	}
	
	private void ajaxGetCustomer() throws SQLException, IOException {
		String sinfo = "";
		String sql = "SELECT (REPLACE ( NAME1, ',', '') || ',' || SORTL) SINFO FROM SAPSR3.KNA1 WHERE MANDT = '168' AND KUNNR = ?";
		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, getParam(req, "kunnr").toUpperCase());
		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			sinfo = rst.getString("SINFO");
		}
		rst.close();
		pstm.close();
		conn.close();
		PrintWriter out = resp.getWriter();
		out.print(sinfo);
		out.close();
	}
	

}
