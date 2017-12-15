package imes.web.enquiry;

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

@WebServlet("/AP63")
public class AP63 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/AP63.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {
			doSearch(req, resp);
		} catch (Exception e) {
			PrintError(resp, e);
		}
	}

	private void doSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT ekbe.budat, lfb1.bukrs, lfa1.lifnr, lfa1.name1, ekko.ebeln, " //
				+ "       ekpo.ebelp, ekpo.matnr, ekpo.txz01, ekpo.werks, ekpo.netpr, " //
				+ "       ekko.waers, ekbe.menge, ekbe.wrbtr, ekpo.mwskz, ekbe.bwart, " //
				+ "       ekbe.shkzg, ekbe.gjahr, ekbe.belnr, ekbe.buzei, ekko.bedat, " //
				+ "       ekbe.ernam, ekpo.peinh " //
				+ "  FROM sapsr3.lfb1, sapsr3.lfa1, sapsr3.ekko, sapsr3.ekpo, sapsr3.ekbe " //
				+ " WHERE     lfb1.mandt = '168' " //
				// + "       AND lfb1.zterm = '0001' " //
				// + "       AND lfb1.bukrs = 'L300' " //
				+ "       AND lfa1.mandt = lfb1.mandt " //
				+ "       AND lfa1.lifnr = lfb1.lifnr " //
				+ "       AND ekko.mandt = lfb1.mandt " //
				+ "       AND ekko.lifnr = lfb1.lifnr " //
				+ "       AND ekko.bukrs = lfb1.bukrs " //
				+ "       AND ekko.loekz <> 'X' " //
				+ "       AND ekko.reswk = ' ' " //
				+ "       AND ekpo.mandt = ekko.mandt " //
				+ "       AND ekpo.ebeln = ekko.ebeln " //
				+ "       AND ekbe.mandt = ekpo.mandt " //
				+ "       AND ekbe.ebeln = ekpo.ebeln " //
				+ "       AND ekbe.ebelp = ekpo.ebelp " //
				// + "       AND ekbe.budat BETWEEN '20120901' AND '20121031' "
				+ "       AND ekbe.vgabe = '1' " //
				+ "       AND ekbe.bewtp = 'E' " //
		;

		if (!getParam(req, "budat_low").equals("")) {
			sql += Helper.sqlParams(req, "ekbe.budat", "budat_low", "budat_high");
		}
		if (!getParam(req, "lifnr_low").equals("")) {
			sql += Helper.sqlParams(req, "lfb1.lifnr", "lifnr_low", "lifnr_high");
		}
		if (!getParam(req, "bukrs_low").equals("")) {
			sql += Helper.sqlParams(req, "lfb1.bukrs", "bukrs_low", "bukrs_high");
		}
		if (!getParam(req, "zterm_low").equals("")) {
			sql += Helper.sqlParams(req, "lfb1.zterm", "zterm_low", "zterm_low");
		}

		sql += " ORDER BY ekbe.budat, lfb1.lifnr";

		try {
			Connection conn = getSapPrdConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (HashMap<String, Object> row : list) {
			if (row.get("SHKZG").equals("H")) {
				row.put("MENGE", toDouble(row.get("MENGE")) * -1);
				row.put("WRBTR", toDouble(row.get("WRBTR")) * -1);
			}
		}

		req.setAttribute("list", list);
		
		render(req, resp, "/WEB-INF/pages/enquiry/AP63.jsp");
		
	}

}
