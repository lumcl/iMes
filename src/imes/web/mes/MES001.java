package imes.web.mes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet({ "/MES001", "/MES001/*" })
public class MES001 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getRequestURI().equals("/iMes/MES001/test")) {

		} else {
			render(req, resp, "/WEB-INF/pages/mes/MES001/list.jsp");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getRequestURI().equals("/iMes/MES001/enquiry")) {
			enquiry(req, resp);
		} else {
			render(req, resp, "/WEB-INF/pages/mes/MES001/list.jsp");
		}

	}

	// if (req.getRequestURI().equals("/iMes/MES001/")) {
	//
	// }

	private void enquiry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "matkl_low") + getParam(req, "crdat_low") + getParam(req, "ekgrp_low") + getParam(req, "lifnr_low")
				+ getParam(req, "refdn_low") + getParam(req, "imbil_low");

		if (jspParams.equals("")) {
			return;
		}

		String sql = "SELECT id, mandt, lifnr, sortl, refdn, " //
				+ "       imbil, matnr, matkl, maktx, ekgrp, " //
				+ "       werks, batch, prdat, vtype, ponum, " //
				+ "       poitm, ebqty, crdat, crtim, crnam, " //
				+ "       crtem, smdat, smtim, smnam, smtem, " //
				+ "       smflg, rtype, mtype, mesge, charg, " //
				+ "       mblnr, mjahr, zeile, rtdat, rttim " //
				+ "  FROM pdagdrcv " //
				+ " WHERE 1 = 1 " //
		;

		if (!getParam(req, "werks_low").equals("")) {
			sql += Helper.sqlParams(req, "werks", "werks_low", "werks_high");
		}
		if (!getParam(req, "matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "matnr", "matnr_low", "matnr_high");
		}
		if (!getParam(req, "lifnr_low").equals("")) {
			sql += Helper.sqlParams(req, "lifnr", "lifnr_low", "lifnr_high");
		}
		if (!getParam(req, "ekgrp_low").equals("")) {
			sql += Helper.sqlParams(req, "ekgrp", "ekgrp_low", "ekgrp_high");
		}
		if (!getParam(req, "matkl_low").equals("")) {
			sql += Helper.sqlParams(req, "matkl", "matkl_low", "matkl_high");
		}
		if (!getParam(req, "crdat_low").equals("")) {
			sql += Helper.sqlParams(req, "crdat", "crdat_low", "crdat_high");
		}
		if (!getParam(req, "refdn_low").equals("")) {
			sql += Helper.sqlParams(req, "refdn", "refdn_low", "refdn_high");
		}
		if (!getParam(req, "imbil_low").equals("")) {
			sql += Helper.sqlParams(req, "imbil", "imbil_low", "imbil_high");
		}

		//System.out.println(sql);
		
		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.setAttribute("list", list);
		render(req, resp, "/WEB-INF/pages/mes/MES001/list.jsp");
	}
}
