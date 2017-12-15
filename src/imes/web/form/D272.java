package imes.web.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.QianHe;
import imes.entity.D272H;
import imes.entity.D272L;
import imes.entity.D272R;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/D272")
public class D272 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		super.doGet(req, resp);

		try {

			if (getAction(req).equals("SelectMO")) {

				actionGetSelectMO(req, resp);

			} else if (getAction(req).equals("New")) {

				actionGetNew(req, resp);

			} else if (getAction(req).equals("Edit") || getAction(req).equals("QianHe")) {

				actionGetEdit(req, resp);

			} else if (getAction(req).equals("WipComponentList")) {

				actionGetWipComponentList(req, resp);

			} else if (getAction(req).equals("InsertNewItem")) {

				render(req, resp, "/WEB-INF/pages/form/D272WIP.jsp");

			} else {

				getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D272.jsp").forward(req, resp);
			}

		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);

		try {

			if (getAction(req).equals("SelectMO")) {

				actionPostSelectMO(req, resp);

			} else if (getAction(req).equals("CreateHeader")) {

				actionPostCreateHeader(req, resp);

			} else if (getAction(req).equals("UpdateHeader")) {

				actionPostUpdateHeader(req, resp);

			} else if (getAction(req).equals("InsertComp")) {

				actionPostInsertComp(req, resp);

			} else if (getAction(req).equals("UpdateComp")) {

				actionPostUpdateComp(req, resp);

			} else if (getAction(req).equals("UpdateResp")) {

				actionPostUpdateResp(req, resp);

			} else if (getAction(req).equals("SubmitQH")) {

				actionSubmitQH(req, resp);

			} else if (getAction(req).equals("QianHe")) {

				actionPostQianHe(req, resp);

			} else if (getAction(req).equals("D272HList")) {

				actionPostList(req, resp);

			} else if (getAction(req).equals("InsertWipComp")) {

				actionPostInsertWipComp(req, resp);
			}

		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	private void actionPostList(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "SELECT * FROM D272H WHERE 1 = 1";

		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlParams(req, "BDRQ", "STRBDRQ", "ENDBDRQ");
		sql += Helper.sqlParams(req, "BDZT", "STRBDZT", "ENDBDZT");
		sql += Helper.sqlParams(req, "BDJG", "STRBDJG", "ENDBDJG");
		sql += Helper.sqlParams(req, "KOSTL", "STRKOSTL", "ENDKOSTL");
		sql += Helper.sqlParams(req, "AUFNR", "STRAUFNR", "ENDAUFNR");
		sql += Helper.sqlParams(req, "MATNR", "STRMATNR", "ENDMATNR");
		sql += Helper.sqlParams(req, "SQYH", "STRSQYH", "ENDSQYH");

		sql += " ORDER BY BDBH";

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			Connection conn = getConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D272.jsp").forward(req, resp);

	}

	private void actionPostQianHe(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		int BZDM = toIntegerParam(req, "BZDM");

		String QHNR = req.getParameter("QHNR");
		String QHJG = req.getParameter("QHJG");

		String HQYH = req.getParameter("HQYH");

		String YHCQ = req.getParameter("YHCQ");

		String sql;
		Connection conn = getConnection();
		conn.setAutoCommit(false);

		QH_BDLC old = new QH_BDLC();
		old.setGSDM(GSDM);
		old.setBDDM(BDDM);
		old.setBDBH(BDBH);
		old.setBZDM(BZDM);
		old.find(conn);

		QH_BDLC e;
		int i = BZDM;

		try {

			if (!HQYH.equals("")) {
				for (String str : HQYH.split(",")) {
					if (!str.replaceAll(" ", "").equals("")) {

						e = (QH_BDLC) old.clone();
						e.setBZDM(i + 1);
						e.setQHLX("I");
						e.setZWDM("");
						e.setZFYN("");
						e.setLCLX("P");
						e.setQHZT("1");
						e.setJLSJ(new Date());
						e.setYJSJ(null);
						e.setYSYH(str);
						e.verifyNewBZDM(conn);
						e.insertDb(conn);
						i = e.getBZDM();

					}
				}
			}

			if (YHCQ != null) {
				QHJG = "Y";
				e = (QH_BDLC) old.clone();
				e.setBZDM(i + 1);
				e.setQHZT("0");
				e.setYSYH(getUid(req));
				e.setJLSJ(new Date());
				e.setYJSJ(null);
				e.setQHFD("");
				e.insertDb(conn);

			}

			sql = "UPDATE QH_BDLC " //
					+ "   SET QHZT='3', " //
					+ "       QHYH=?, " //
					+ "       QHSJ=SYSDATE, " //
					+ "       QHJG=?, " //
					+ "       QHNR=? " //
					// + "       QHFD=? " //
					+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, getUid(req));
			pstm.setString(2, QHJG);
			pstm.setString(3, QHNR);
			// pstm.setString(4, "");
			pstm.setString(4, GSDM);
			pstm.setString(5, BDDM);
			pstm.setString(6, BDBH);
			pstm.setInt(7, BZDM);
			pstm.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			conn.rollback();
		}

		conn.close();

		resp.sendRedirect("/iMes/qh?action=Find&STRQHZT=2&STRYSYH=" + getUid(req));
	}

	private void actionGetEdit(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		D272H hd = new D272H();
		hd.setGSDM(req.getParameter("GSDM"));
		hd.setBDDM(req.getParameter("BDDM"));
		hd.setBDBH(req.getParameter("BDBH"));
		hd.find(conn);

		String sql = "SELECT * FROM D272L WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY SQNR";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));

		List<HashMap<String, Object>> lines = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();

		List<D272R> D272Rs = getD272Rs(req, conn);

		String ZWDM;
		String PBUSER = "N";
		String CGUSER = "N";
		sql = "SELECT ZWDM FROM QH_ZWYH WHERE SYSDATE BETWEEN YXFD AND YXTD AND YHDM = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, getUid(req));
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			ZWDM = rst.getString("ZWDM");
			if (ZWDM.contains("PB")) {
				PBUSER = "Y";
			}
			if (ZWDM.contains("CG")) {
				CGUSER = "Y";
			}
		}
		rst.close();
		pstm.close();

		List<QH_BDLC> BDLCS = QH_BDLC.findBy(hd.getGSDM(), hd.getBDDM(), hd.getBDBH(), conn);

		conn.close();

		String QHKS = "Y";
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}

		req.setAttribute("HD", hd);
		req.setAttribute("LN", lines);
		req.setAttribute("RS", D272Rs);
		req.setAttribute("WMATNR", getWipMatnr(hd.getMATNR(), hd.getWERKS()));
		if (QHKS.equals("N")) {
			req.setAttribute("WMATNR", getWipMatnr(hd.getMATNR(), hd.getWERKS()));
		}
		req.setAttribute("PBUSER", PBUSER);
		req.setAttribute("CGUSER", CGUSER);
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D272ED.jsp").forward(req, resp);

	}

	private void actionGetSelectMO(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D272MO.jsp").forward(req, resp);
	}

	private void actionPostSelectMO(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "SELECT AFPO.AUFNR, AFKO.GSTRI, AFPO.PSMNG, AFPO.WEMNG, AFPO.MATNR, " //
				+ "       AFPO.PWERK WERKS, MAKT.MAKTX " //
				+ "  FROM SAPSR3.AFPO@SAPP, SAPSR3.AFKO@SAPP, SAPSR3.MAKT@SAPP " //
				+ " WHERE     AFKO.MANDT = AFPO.MANDT " //
				+ "       AND AFKO.AUFNR = AFPO.AUFNR " //
				+ "       AND MAKT.MANDT = AFPO.MANDT " //
				+ "       AND MAKT.MATNR = AFPO.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND AFPO.ELIKZ = ' ' " //
				+ "       AND AFPO.DNREL = ' ' " //
				+ "       AND AFPO.MANDT = '168' "; //

		sql += Helper.sqlParamsFormat(req, "AFPO.AUFNR", "STRAUFNR", "ENDAUFNR", "%012d");
		sql += Helper.sqlParams(req, "AFPO.MATNR", "STRMATNR", "ENDMATNR");
		sql += Helper.sqlParams(req, "AFPO.PWERK", "STRWERKS", "ENDWERKS");
		sql += Helper.sqlParams(req, "AFKO.GSTRI", "STRGSTRI", "ENDGSTRI");
		sql += " ORDER BY AFPO.AUFNR";

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);

		List<HashMap<String, Object>> list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D272MO.jsp").forward(req, resp);

	}

	private void actionGetNew(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "SELECT AFPO.AUFNR, AFKO.GSTRI, AFPO.PSMNG, AFPO.WEMNG, AFPO.MATNR, " //
				+ "       AFPO.PWERK WERKS, MAKT.MAKTX, AFKO.RSNUM" //
				+ "  FROM SAPSR3.AFPO@SAPP, SAPSR3.AFKO@SAPP, SAPSR3.MAKT@SAPP " //
				+ " WHERE     AFKO.MANDT = AFPO.MANDT " //
				+ "       AND AFKO.AUFNR = AFPO.AUFNR " //
				+ "       AND MAKT.MANDT = AFPO.MANDT " //
				+ "       AND MAKT.MATNR = AFPO.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND AFKO.AUFNR = ? " //
				+ "       AND AFPO.MANDT = '168' "; //

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("AUFNR"));

		ResultSet rst = pstm.executeQuery();
		
		D272H h = new D272H();

		h.setSQYH(getUid(req));
		h.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		h.setJLSJ(new Date());

		if (rst.next()) {

			h.setAUFNR(rst.getString("AUFNR"));
			h.setWERKS(rst.getString("WERKS"));
			h.setGSTRI(rst.getString("GSTRI"));
			h.setPSMNG(rst.getDouble("PSMNG"));
			h.setWEMNG(rst.getDouble("WEMNG"));
			h.setMATNR(rst.getString("MATNR"));
			h.setMAKTX(rst.getString("MAKTX"));
			h.setRSNUM(rst.getString("RSNUM"));

			if (h.getWERKS().equals("281A")) {

				h.setGSDM("L210");

			} else if (h.getWERKS().equals("381A")) {

				h.setGSDM("L300");

			} else if (h.getWERKS().equals("481A")) {

				h.setGSDM("L400");

			} else if (h.getWERKS().equals("482A")) {

				h.setGSDM("L400");

			}			

		}

		h.setSelWS(getSelectWS(h.getAUFNR(), conn));
		rst.close();
		pstm.close();
		conn.close();

		req.setAttribute("HD", h);
		// req.setAttribute("DT", getCompByRSNUM(h.getRSNUM()));

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D272ED.jsp").forward(req, resp);

	}

	private void actionPostCreateHeader(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		String KTEXT = "";
		String sql = "SELECT KTEXT FROM QH_CSKT WHERE KOSTL=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("KOSTL"));
		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			KTEXT = rst.getString("KTEXT");
		}
		rst.close();
		pstm.close();

		D272H h = new D272H();
		h.setGSDM(req.getParameter("GSDM").toUpperCase());
		h.setBDDM("D272");
		h.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		h.setYYLB(req.getParameter("YYLB"));
		h.setYYSM(req.getParameter("YYSM"));
		h.setAUFNR(req.getParameter("AUFNR"));
		h.setWERKS(req.getParameter("WERKS"));
		h.setGSTRI(req.getParameter("GSTRI"));
		h.setPSMNG(toDoubleParam(req, "PSMNG"));
		h.setWEMNG(toDoubleParam(req, "WEMNG"));
		h.setMATNR(req.getParameter("MATNR"));
		h.setMAKTX(req.getParameter("MAKTX"));
		h.setKOSTL(req.getParameter("KOSTL"));
		h.setKTEXT(KTEXT);
		h.setWAERS(req.getParameter("WAERS"));
		if (req.getParameter("ZRFY") != null) {
			h.setZRFY(req.getParameter("ZRFY"));
		}
		h.setSQYH(getUid(req));
		h.setJLYH(getUid(req));
		h.setJLSJ(new Date());
		h.setRSNUM(req.getParameter("RSNUM"));
		String ws = req.getParameter("WoSt");
		String [] wost;
		String VORNR = "";
		String ARBPL = "";
		if(ws != null && ws.length()>0){
			wost = ws.split("_");
			VORNR = wost[0];
			ARBPL = wost[1];
		}
		h.setVORNR(VORNR);
		h.setARBPL(ARBPL);
		try {
			h.setBDBH(Helper.getBDBH(getConnection(), h.getGSDM(), h.getBDDM(), Helper.fmtDate(new Date(), "yyyy")));

			h.insertDb(conn);

			if (h.getZRFY().equals("X")) {
				D272R r = new D272R();
				r.setGSDM(h.getGSDM());
				r.setBDDM(h.getBDDM());
				r.setBDBH(h.getBDBH());
				r.setSQNR(1);
				r.setKOSTL(h.getKOSTL());
				r.setKTEXT(h.getKTEXT());
				r.setPCTG(100);
				r.setJLYH(h.getJLYH());
				r.setJLSJ(h.getJLSJ());
				r.insertDb(conn);
			}

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		conn.setAutoCommit(true);
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + h.getGSDM() + "&BDDM=" + h.getBDDM() + "&BDBH=" + h.getBDBH());

	}

	private void actionPostUpdateHeader(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "UPDATE D272H SET YYLB=?, YYSM=? WHERE GSDM=? AND BDDM=? AND BDBH=?";
		Connection conn = getConnection();
		String ws = req.getParameter("WoSt");
		String [] wost;
		String VORNR = "";
		String ARBPL = "";
		if(ws != null && ws.length()>0){
			wost = ws.split("_");
			VORNR = wost[0];
			ARBPL = wost[1];
		}
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("YYLB"));
		pstm.setString(2, req.getParameter("YYSM"));
		//pstm.setString(3, VORNR);
		//pstm.setString(4, ARBPL);
		pstm.setString(3, req.getParameter("GSDM"));
		pstm.setString(4, req.getParameter("BDDM"));
		pstm.setString(5, req.getParameter("BDBH"));
		pstm.executeUpdate();
		pstm.close();
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));

	}

	void actionPostInsertWipComp(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		int SQNR = 0;

		String sql = "SELECT NVL(MAX(SQNR),0) SQNR FROM D272L WHERE GSDM=? AND BDDM=? AND BDBH=?";

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));

		ResultSet rst = pstm.executeQuery();

		if (rst.next()) {
			SQNR = rst.getInt("SQNR");
		}
		rst.close();
		pstm.close();

		sql = "SELECT MARA.MATKL, MARA.MEINS, MAKT.MAKTX, CASE WHEN VPRSV = 'V' THEN VERPR / PEINH ELSE STPRS / PEINH END NETPR,EKGRP " //
				+ "  FROM SAPSR3.MARA@SAPP, " //
				+ "       SAPSR3.MAKT@SAPP, " //
				+ "       SAPSR3.MBEW@SAPP, " //
				+ "       SAPSR3.MARC@SAPP " //
				+ " WHERE     MARA.MANDT = '168' " //
				+ "       AND MARA.MATNR = ? " //
				+ "       AND MAKT.MANDT = MARA.MANDT " //
				+ "       AND MAKT.MATNR = MARA.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND MBEW.MANDT = MARA.MANDT " //
				+ "       AND MBEW.MATNR = MARA.MATNR " //
				+ "       AND MBEW.BWKEY = ? " //
				+ "       AND MBEW.BWTAR = ' ' " //
				+ "       AND MARC.MANDT = MBEW.MANDT " //
				+ "       AND MARC.MATNR = MBEW.MATNR " //
				+ "       AND MARC.WERKS = MBEW.BWKEY "; //

		pstm = conn.prepareStatement(sql);

		conn.setAutoCommit(false);

		String[] rowids = req.getParameterValues("ROWID");

		if (rowids != null) {
			for (String id : rowids) {

				D272L e = new D272L();
				SQNR += 10;
				e.setGSDM(req.getParameter("GSDM"));
				e.setBDDM(req.getParameter("BDDM"));
				e.setBDBH(req.getParameter("BDBH"));
				e.setSQNR(SQNR);
				e.setCMATNR(req.getParameter("CMATNR_" + id));
				e.setCWERKS(req.getParameter("CWERKS_" + id));
				e.setREQQ(toDoubleParam(req, "REQTY_" + id));

				pstm.setString(1, e.getCMATNR());
				pstm.setString(2, e.getCWERKS());

				rst = pstm.executeQuery();

				if (rst.next()) {
					e.setCMATKL(rst.getString("MATKL"));
					e.setCMAKTX(rst.getString("MAKTX"));
					e.setCMEINS(rst.getString("MEINS"));
					e.setNETPR(rst.getDouble("NETPR"));
					e.setCEKGRP(rst.getString("EKGRP"));
				}
				rst.close();

				e.setREQA(e.getREQQ() * e.getNETPR());
				e.setJLYH(getUid(req));
				e.setJLSJ(new Date());
				if (e.getREQQ() > 0) {
					e.insertDb(conn);
				}
			}
		}

		pstm.close();

		sql = "UPDATE D272H SET BDAMT = (SELECT SUM(REQA) FROM D272L WHERE GSDM=D272H.GSDM AND BDDM=D272H.BDDM AND BDBH=D272H.BDBH) " //
				+ "WHERE GSDM=? AND BDDM=? AND BDBH=?"; //
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));
	}

	private void actionPostInsertComp(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		int SQNR = 0;

		String sql = "SELECT NVL(MAX(SQNR),0) SQNR FROM D272L WHERE GSDM=? AND BDDM=? AND BDBH=?";

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));

		ResultSet rst = pstm.executeQuery();

		if (rst.next()) {
			SQNR = rst.getInt("SQNR");
		}
		rst.close();
		pstm.close();

		sql = "SELECT MARA.MATKL,MARA.MEINS, MAKT.MAKTX, CASE WHEN VPRSV = 'V' THEN VERPR / PEINH ELSE STPRS / PEINH END NETPR " //
				+ "  FROM SAPSR3.MARA@SAPP, SAPSR3.MAKT@SAPP, SAPSR3.MBEW@SAPP " //
				+ " WHERE     MARA.MANDT = '168' " //
				+ "       AND MARA.MATNR = ? " //
				+ "       AND MAKT.MANDT = MARA.MANDT " //
				+ "       AND MAKT.MATNR = MARA.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND MBEW.MANDT = MARA.MANDT " //
				+ "       AND MBEW.MATNR = MARA.MATNR " //
				+ "       AND MBEW.BWKEY = ? " //
				+ "       AND MBEW.BWTAR = ' ' "; //

		pstm = conn.prepareStatement(sql);

		conn.setAutoCommit(false);

		String[] matnrs = req.getParameterValues("CBXMATNR");
		String[] rowids = req.getParameterValues("ROWID");

		if (rowids != null) {
			for (String id : rowids) {

				D272L e = new D272L();
				SQNR += 10;
				e.setGSDM(req.getParameter("GSDM"));
				e.setBDDM(req.getParameter("BDDM"));
				e.setBDBH(req.getParameter("BDBH"));
				e.setSQNR(SQNR);
				e.setCMATNR(req.getParameter("MATNR_" + id));
				e.setCWERKS(req.getParameter("WERKS_" + id));

				pstm.setString(1, e.getCMATNR());
				pstm.setString(2, e.getCWERKS());

				rst = pstm.executeQuery();

				if (rst.next()) {
					e.setCMATKL(rst.getString("MATKL"));
					e.setCMAKTX(rst.getString("MAKTX"));
					e.setCMEINS(rst.getString("MEINS"));
					e.setNETPR(rst.getDouble("NETPR"));
				}
				rst.close();

				e.setJLYH(getUid(req));
				e.setJLSJ(new Date());
				e.insertDb(conn);
			}
		}

		String[] mw;
		if (matnrs != null) {
			for (String m : matnrs) {
				mw = m.split("_");
				D272L e = new D272L();
				SQNR += 10;
				e.setGSDM(req.getParameter("GSDM"));
				e.setBDDM(req.getParameter("BDDM"));
				e.setBDBH(req.getParameter("BDBH"));
				e.setSQNR(SQNR);
				e.setCMATNR(mw[0]);
				e.setCWERKS(mw[1]);

				pstm.setString(1, e.getCMATNR());
				pstm.setString(2, e.getCWERKS());

				rst = pstm.executeQuery();

				if (rst.next()) {
					e.setCMATKL(rst.getString("MATKL"));
					e.setCMAKTX(rst.getString("MAKTX"));
					e.setCMEINS(rst.getString("MEINS"));
					e.setNETPR(rst.getDouble("NETPR"));
				}
				rst.close();

				e.setJLYH(getUid(req));
				e.setJLSJ(new Date());
				e.insertDb(conn);
			}
		}

		pstm.close();
		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));
	}

	private void actionPostUpdateComp(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "UPDATE D272L SET REQQ=?, REQA=?, CFMDT=?, CGSM=?, CGYH=?, XQSM=?, GXYH=?, GXSJ=SYSDATE " //
				+ "WHERE GSDM=? AND BDDM=? AND BDBH=? AND SQNR=? "; //

		Connection conn = getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstm = conn.prepareStatement(sql);

		for (String SQNR : req.getParameterValues("SQNR")) {
			pstm.setDouble(1, toDoubleParam(req, "REQQ_" + SQNR));
			pstm.setDouble(2, toDoubleParam(req, "REQA_" + SQNR));
			pstm.setString(3, req.getParameter("CFMDT_" + SQNR));
			pstm.setString(4, req.getParameter("CGSM_" + SQNR));
			pstm.setString(5, getUid(req));
			pstm.setString(6, req.getParameter("XQSM_" + SQNR));
			pstm.setString(7, getUid(req));
			pstm.setString(8, req.getParameter("GSDM"));
			pstm.setString(9, req.getParameter("BDDM"));
			pstm.setString(10, req.getParameter("BDBH"));
			pstm.setInt(11, Integer.parseInt(SQNR));
			pstm.executeUpdate();
		}

		pstm.close();

		sql = "UPDATE D272H SET BDAMT = (SELECT SUM(REQA) FROM D272L WHERE GSDM=D272H.GSDM AND BDDM=D272H.BDDM AND BDBH=D272H.BDBH) " //
				+ "WHERE GSDM=? AND BDDM=? AND BDBH=?"; //
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));
	}

	private void actionPostUpdateResp(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		String sql = "DELETE FROM D272R WHERE GSDM=? AND BDDM=? AND BDBH=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		sql = "DELETE FROM QH_BDLC WHERE GSDM=? AND BDDM=? AND BDBH=? AND TTXT='RESP'";

		int BZDM = 0;

		sql = "SELECT MAX(BZDM) BZDM FROM QH_BDLC WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM < 9999";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));

		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			BZDM = rst.getInt("BZDM");
		}
		rst.close();
		pstm.close();

		QH_BDLC e;
		String KOSTL, QHYH;

		sql = "SELECT QHYH FROM QH_CSKT WHERE GSDM=? AND KOSTL=?";
		pstm = conn.prepareStatement(sql);

		int i = 0;
		D272R r;
		for (String SQNR : req.getParameterValues("SQNR")) {

			if (toIntegerParam(req, "PCTG_" + SQNR) != 0) {
				i += 1;
				r = new D272R();
				r.setGSDM(req.getParameter("GSDM"));
				r.setBDDM(req.getParameter("BDDM"));
				r.setBDBH(req.getParameter("BDBH"));
				r.setSQNR(i);
				r.setKOSTL(req.getParameter("KOSTL_" + SQNR));
				r.setLIFNR(req.getParameter("LIFNR_" + SQNR));
				r.setKUNNR(req.getParameter("KUNNR_" + SQNR));
				r.setKTEXT(req.getParameter("KTEXT_" + SQNR));
				r.setNAME1(req.getParameter("NAME1_" + SQNR));
				r.setPCTG(toIntegerParam(req, "PCTG_" + SQNR));
				r.setJLYH(getUid(req));
				r.setJLSJ(new Date());
				r.insertDb(conn);

				if (!r.getKOSTL().equals("")) {
					KOSTL = r.getKOSTL().trim();
				} else if (!r.getLIFNR().equals("")) {
					KOSTL = "SUPPLIER";
				} else {
					KOSTL = "CUSTOMER";
				}

				QHYH = "LUM.CL";
				pstm.setString(1, req.getParameter("GSDM"));
				pstm.setString(2, KOSTL);

				rst = pstm.executeQuery();

				if (rst.next()) {
					QHYH = rst.getString("QHYH");
				}
				rst.close();

				e = new QH_BDLC();
				e.setGSDM(r.getGSDM());
				e.setBDDM(r.getBDDM());
				e.setBDBH(r.getBDBH());
				e.setLXDM(r.getBDDM());
				e.setQHZT("0");
				e.setBZDM(BZDM + i);
				e.setLCLX("S");
				e.setQHLX("R");
				e.setYXXS(100);
				e.setFTXT(KOSTL);
				e.setTTXT("RESP");
				e.setYJSJ(null);
				e.setYXSJ(null);
				e.setJLSJ(new Date());
				e.setSQYH(getUid(req));
				e.setYSYH(QHYH);
				e.verifyNewBZDM(conn);
				e.insertDb(conn);
			}
		}

		pstm.close();

		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));

	}

	private void actionSubmitQH(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		String sql = "UPDATE D272H SET BDZT='0', BDJG='0' WHERE GSDM=? AND BDDM=? AND BDBH=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		QH_BDTX bdtx = new QH_BDTX();
		bdtx.setGSDM(req.getParameter("GSDM"));
		bdtx.setBDDM(req.getParameter("BDDM"));
		bdtx.setBDBH(req.getParameter("BDBH"));
		bdtx.setBDTX(req.getParameter("BDTX"));
		bdtx.insertDb(conn);

		QianHe qh = new QianHe();
		qh.setGSDM(req.getParameter("GSDM"));
		qh.setBDDM(req.getParameter("BDDM"));
		qh.setBDBH(req.getParameter("BDBH"));
		qh.setMgr(getUMgr(req));
		qh.setSQYH(getUid(req));

		D272H d272h = new D272H();
		d272h.setGSDM(qh.getGSDM());
		d272h.setBDDM(qh.getBDDM());
		d272h.setBDBH(qh.getBDBH());
		d272h.find(conn);
		
		for (QH_BDLC r : qh.crtRoute(conn, "A", req.getParameter("BDAMT"))) {

			if( (qh.getGSDM().equals("L300")||qh.getGSDM().equals("L210")) && d272h.getYYLB().contains("E") && d272h.getZRFY()!=null && d272h.getZRFY().equalsIgnoreCase("X")){
				if(r.getZWDM().contains("TXPEFL") || r.getZWDM().contains("TXPEKZ")){
					r.setBDBH(r.getBDBH()+"_");
					r.setQHZT("9");
				}
			}
			
			if (r.getZWDM().contains("DTZZFL")) {
				if (req.getParameter("KOSTL").contains("H2006") || req.getParameter("KOSTL").contains("H2008")) {
					r.setYSYH("HC.ZHANG");
				}
			}
			
			if (req.getParameter("ZRFY").equals("X") && r.getZWDM().contains("PB")) {
				// noneed pingbao
			} else if (r.getZWDM().contains("DTCGWY") || r.getZWDM().contains("TXCGWY")) {
				QianHeByPurchaseGroup(r, conn);
			} else if (r.getZWDM().contains("SG")) {
				QianHeByPlanner(r, conn);
			} else if (r.getZWDM().contains("TXZZ")) {
				QianHeByTXZZJL2(r, conn);
			} else {
				r.insertDb(conn);
			}
		}
		

//		List<QH_BDLC> q = new ArrayList<QH_BDLC>();
//		QH_BDLC bdlc = null;
//		if( (qh.getGSDM().equals("L300")||qh.getGSDM().equals("L210")) && d272h.getYYLB().contains("E") && d272h.getZRFY().equalsIgnoreCase("X")){
//			bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),"0",300,999,"TXPEJL","R","N",qh.getSQYH().toUpperCase(),findUserGLY("TXPEJL"));
//			q.add(bdlc);
//		}
//
//		for (QH_BDLC r : q) {				
//			insertQH_BDLC(r);
//		}
		
		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D272?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));

	}
	
	public int insertQH_BDLC(QH_BDLC q) {
		
		Connection con = null;
		
		int i = 0;
		
		String sql = "INSERT INTO QH_BDLC "
				+ "(GSDM,BDDM,BDBH,LXDM,QHZT,BZDM,LCLX,MINQ,MAXQ,MINA,MAXA,MINP,MAXP,CURR,FTXT,TTXT,ZWDM,QHLX,YXXS,ZFYN,JLSJ,YJSJ,YXSJ,YSYH,DLYH,QHYH,QHSJ,QHJG,QHNR,QHFD,SQYH) " //
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //
		
		PreparedStatement pstm = null;
		
		try{
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, q.getGSDM());
			pstm.setString(2, q.getBDDM());
			pstm.setString(3, q.getBDBH());
			pstm.setString(4, q.getLXDM());
			pstm.setString(5, q.getQHZT());
			pstm.setInt(6, q.getBZDM());
			pstm.setString(7, q.getLCLX());
			pstm.setDouble(8, q.getMINQ());
			pstm.setDouble(9, q.getMAXQ());
			pstm.setDouble(10, q.getMINA());
			pstm.setDouble(11, q.getMAXA());
			pstm.setDouble(12, q.getMINP());
			pstm.setDouble(13, q.getMAXP());
			pstm.setString(14, q.getCURR());
			pstm.setString(15, q.getFTXT());
			pstm.setString(16, q.getTTXT());
			pstm.setString(17, q.getZWDM());
			pstm.setString(18, q.getQHLX());
			pstm.setDouble(19, q.getYXXS());
			pstm.setString(20, q.getZFYN());
			pstm.setTimestamp(21, Helper.toSqlDate(q.getJLSJ()));
			pstm.setTimestamp(22, Helper.toSqlDate(q.getYJSJ()));
			pstm.setTimestamp(23, Helper.toSqlDate(q.getYXSJ()));
			pstm.setString(24, q.getYSYH());
			pstm.setString(25, q.getDLYH());
			pstm.setString(26, q.getQHYH());
			pstm.setTimestamp(27, Helper.toSqlDate(q.getQHSJ()));
			pstm.setString(28, q.getQHJG());
			pstm.setString(29, q.getQHNR());
			pstm.setString(30, q.getQHFD());
			pstm.setString(31, q.getSQYH());
			
			i = pstm.executeUpdate();
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	private QH_BDLC addQHE(String gsdm, String bddm, String bdbh,String qhzt,int bzdm,int yxxs,String zwdm,String qhlx,String zfyn,String sqyh,String ysyh){

		QH_BDLC e = new QH_BDLC();
		e.setGSDM(gsdm);
		e.setBDDM(bddm);
		e.setBDBH(bdbh);
		e.setLXDM(null);
		e.setQHZT(qhzt);
		e.setBZDM(bzdm);
		e.setLCLX("S");
		e.setZWDM(zwdm);
		e.setQHLX(qhlx);
		e.setYXXS(yxxs);
		e.setZFYN(zfyn);
		e.setYJSJ(null);
		e.setYXSJ(null);
		e.setSQYH(sqyh);
		e.setYSYH(ysyh);
		
		if (e.getQHLX().contains("I")){
			e.setLCLX("P");
		}
		
		return e;
	}

	public String findUserGLY(String bdh) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM = '"+bdh+"'";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("YHDM");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}
	
	private void QianHeByTXZZJL2(QH_BDLC q, Connection conn) {
		try {
			String MATNR = "";
			String KOSTL = "";
			String ZWDM = "TXZZJL2";

			String sql = "SELECT MATNR,WERKS,KOSTL,KTEXT FROM D272H WHERE GSDM=? AND BDDM=? AND BDBH=?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, q.getGSDM());
			pstm.setString(2, q.getBDDM());
			pstm.setString(3, q.getBDBH());

			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				MATNR = rst.getString("MATNR");
				KOSTL = rst.getString("KOSTL");
			}
			rst.close();
			pstm.close();

			if (MATNR != null && MATNR.trim().toUpperCase().startsWith("LS")) {				
				sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, ZWDM);
				rst = pstm.executeQuery();
				if (rst.next()){
					q.setYSYH(rst.getString("YHDM"));
					q.setZWDM(ZWDM);
				}
				q.insertDb(conn);
			}/* else if(KOSTL != null && KOSTL.trim().equalsIgnoreCase("J2103")){

				ZWDM = "TXZZJL3";
				sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, ZWDM);
				rst = pstm.executeQuery();
				if (rst.next()){
					q.setYSYH(rst.getString("YHDM"));
					q.setZWDM(ZWDM);
				}
				QH_BDLC q1 = (QH_BDLC)q.clone();
				ZWDM = "TXZZFL3";
				sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, ZWDM);
				rst = pstm.executeQuery();
				if (rst.next()){
					q1.setYSYH(rst.getString("YHDM"));
					q1.setZWDM(ZWDM);
				}
				q1.setBZDM(q1.getBZDM()-45);
				q1.insertDb(conn);
				q.insertDb(conn);
			} */else if(KOSTL != null && KOSTL.trim().equalsIgnoreCase("J2001")) {
				ZWDM = "TXZZJL2";
				sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, ZWDM);
				rst = pstm.executeQuery();
				if (rst.next()){
					q.setYSYH(rst.getString("YHDM"));
					q.setZWDM(ZWDM);
				}
				q.insertDb(conn);
			}else{
				if(q.getZWDM().equals("TXZZJL")){
					QH_BDLC q1 = (QH_BDLC)q.clone();
					ZWDM = "TXZZFL";
					sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM=?";
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, ZWDM);
					rst = pstm.executeQuery();
					if (rst.next()){
						q1.setYSYH(rst.getString("YHDM"));
						q1.setZWDM(ZWDM);						
					}
					
					q1.setBZDM(q1.getBZDM()-45);
					q1.insertDb(conn);
				}
				q.insertDb(conn);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void QianHeByPlanner(QH_BDLC q, Connection conn) {
		try {
			String MATNR = "";
			String WERKS = "";
			String FEVOR = "";
			String ZWDM = "";

			String sql = "SELECT MATNR,WERKS FROM D272H WHERE GSDM=? AND BDDM=? AND BDBH=?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, q.getGSDM());
			pstm.setString(2, q.getBDDM());
			pstm.setString(3, q.getBDBH());

			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				MATNR = rst.getString("MATNR");
				WERKS = rst.getString("WERKS");
			}
			rst.close();
			pstm.close();

			sql = "SELECT FEVOR FROM SAPSR3.MARC@SAPP WHERE MANDT = '168' AND MATNR = ? AND WERKS = ? ";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, MATNR);
			pstm.setString(2, WERKS);

			rst = pstm.executeQuery();
			if (rst.next()) {
				FEVOR = rst.getString("FEVOR");
			}
			rst.close();
			pstm.close();

			if (FEVOR == null || FEVOR.trim().equals("")) {
				q.insertDb(conn);
			} else {
				ZWDM = q.getZWDM().substring(0, 4) + FEVOR;
				
				sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, ZWDM);
				rst = pstm.executeQuery();
				if (rst.next()){
					q.setYSYH(rst.getString("YHDM"));
					q.setZWDM(ZWDM);
				}
				q.insertDb(conn);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void QianHeByPurchaseGroup(QH_BDLC q, Connection conn) {

		try {
			String sql = "  SELECT USERID " //
					+ "    FROM D272L, QH_EKGRP " //
					+ "   WHERE     D272L.GSDM = ? " //
					+ "         AND D272L.BDDM = ? " //
					+ "         AND D272L.BDBH = ? " //
					+ "         AND QH_EKGRP.GSDM = D272L.GSDM " //
					+ "         AND QH_EKGRP.EKGRP = D272L.CEKGRP " //
					+ "GROUP BY USERID " //
					+ "ORDER BY USERID "; //

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, q.getGSDM());
			pstm.setString(2, q.getBDDM());
			pstm.setString(3, q.getBDBH());

			ResultSet rst = pstm.executeQuery();

			int bzdm = q.getBZDM();
			QH_BDLC newObj;
			while (rst.next()) {
				newObj = (QH_BDLC) q.clone();
				newObj.setBZDM(bzdm);
				bzdm += 10;
				newObj.setLCLX("P");
				newObj.setYSYH(rst.getString("USERID"));
				newObj.insertDb(conn);
			}

			rst.close();
			pstm.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void actionGetWipComponentList(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		if (toUpperCaseParam(req, "WipMatnr").equals("")) {

			String sql = "WITH TBOM " //
					+ "     AS (  SELECT RESB.MATNR CMATNR, RESB.WERKS CWERKS, MARA.MATKL CMATKL, MAKT.MAKTX CMAKTX, RESB.MEINS DUOM, " //
					+ "                  SUM (RESB.BDMNG) DUSAGE " //
					+ "             FROM SAPSR3.RESB, SAPSR3.MARA, SAPSR3.MAKT " //
					+ "            WHERE     RESB.MANDT = '168' " //
					+ "                  AND RESB.RSNUM = ? " //
					+ "                  AND MARA.MANDT = RESB.MANDT " //
					+ "                  AND MARA.MATNR = RESB.MATNR " //
					+ "                  AND MAKT.MANDT = MARA.MANDT " //
					+ "                  AND MAKT.MATNR = MARA.MATNR " //
					+ "                  AND MAKT.SPRAS = 'M' " //
					+ "         GROUP BY RESB.MATNR, RESB.WERKS, MARA.MATKL, MAKT.MAKTX, RESB.MEINS), " //
					+ "     TMARD " //
					+ "     AS (  SELECT MATNR, WERKS, SUM (LABST) LABST, SUM (UMLME + INSME) INSME " //
					+ "             FROM SAPSR3.MARD, " //
					+ "                  (  SELECT CMATNR, CWERKS " //
					+ "                       FROM TBOM " //
					+ "                   GROUP BY CMATNR, CWERKS) GBOM " //
					+ "            WHERE MARD.MANDT = '168' AND MARD.MATNR = GBOM.CMATNR AND MARD.WERKS = GBOM.CWERKS " //
					+ "         GROUP BY MATNR, WERKS) " //
					+ "  SELECT CMATNR, CWERKS, CMATKL, CMAKTX, DUOM, " //
					+ "         (DUSAGE / ?) DUSAGE, ROUND ( (DUSAGE / ? * ?), DECAN) REQTY, LABST, INSME, ROWNUM " //
					+ "    FROM TBOM, TMARD, SAPSR3.T006 " //
					+ "   WHERE TMARD.MATNR = CMATNR AND TMARD.WERKS = CWERKS AND T006.MANDT = '168' AND T006.MSEHI = DUOM " //
					+ "ORDER BY CMATKL, CMAKTX, CMATNR "; //

			Connection conn = getSapPrdConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, toUpperCaseParam(req, "Rsnum"));
			pstm.setDouble(2, toDoubleParam(req, "Psmng"));
			pstm.setDouble(3, toDoubleParam(req, "Psmng"));
			pstm.setDouble(4, toDoubleParam(req, "WipMenge"));

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} else {

			String sql = "WITH TBOM " //
					+ "     AS (SELECT CMATNR, CWERKS, CMATKL, CMAKTX, CSOBSL, " //
					+ "                ALPOS, EWAHR, ALPGR, DUOM, DUSAGE " //
					+ "           FROM IT.SBOMXTB " //
					+ "          WHERE PMATNR = ? AND WERKS = ? AND DPMATNR = ? AND CSOBSL <>'50'), " //
					+ "     TMARD " //
					+ "     AS (  SELECT MATNR, WERKS, SUM (LABST) LABST, SUM (UMLME + INSME) INSME " //
					+ "             FROM SAPSR3.MARD@SAPP, " //
					+ "                  (  SELECT CMATNR, CWERKS " //
					+ "                       FROM TBOM " //
					+ "                   GROUP BY CMATNR, CWERKS) GBOM " //
					+ "            WHERE MARD.MANDT = '168' AND MARD.MATNR = GBOM.CMATNR AND MARD.WERKS = GBOM.CWERKS " //
					+ "         GROUP BY MATNR, WERKS) " //
					+ "SELECT CMATNR, CWERKS, CMATKL, CMAKTX, CSOBSL, " //
					+ "       ALPOS, EWAHR, ALPGR, DUOM, DUSAGE, " //
					+ "         ROUND((DUSAGE * ?),ANDEC) REQTY, LABST, INSME, ROWNUM, 'X' WIPCOMP " //
					+ "    FROM TBOM, TMARD, SAPSR3.T006@SAPP " //
					+ "   WHERE TMARD.MATNR = CMATNR AND TMARD.WERKS = CWERKS " //
					+ "   AND T006.MANDT='168' AND T006.MSEHI=DUOM " //
					+ "ORDER BY CMATKL, CMAKTX, CMATNR"; //

			Connection conn = getSapCoConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, toUpperCaseParam(req, "Pmatnr"));
			pstm.setString(2, toUpperCaseParam(req, "WipWerks"));
			pstm.setString(3, toUpperCaseParam(req, "WipMatnr"));
			pstm.setDouble(4, toDoubleParam(req, "WipMenge"));

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();
		}

		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/form/D272WIP.jsp");
	}

	private List<HashMap<String, Object>> getWipMatnr(String pmatnr, String werks) {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT DPMATNR,BLEVEL FROM IT.SBOMXTB WHERE PMATNR=? AND WERKS=? GROUP BY DPMATNR,BLEVEL ORDER BY BLEVEL,DPMATNR";
		try {
			Connection conn = getSapCoConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, pmatnr);
			pstm.setString(2, werks);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	List<HashMap<String, Object>> getCompByRSNUM(String RSNUM) {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "  SELECT MAKT.MAKTX, RESB.MATNR, RESB.WERKS, RESB.MATKL, RESB.DUMPS, RESB.MEINS, " //
				+ "         RESB.ALPGR, RESB.ALPRF, SUM (RESB.BDMNG) BDMNG, SUM (RESB.ENMNG) ENMNG, SUM (RESB.VMENG) VMENG, " //
				+ "         MAX (MARD.LABST) LABST, MAX (MARD.INSME) INSME " //
				+ "    FROM SAPSR3.RESB, " //
				+ "         SAPSR3.MAKT, " //
				+ "         (  SELECT MATNR, WERKS, SUM (LABST) LABST, SUM (INSME) INSME " //
				+ "              FROM SAPSR3.MARD " //
				+ "             WHERE MANDT = '168' " //
				+ "                   AND MATNR IN (SELECT MATNR " //
				+ "                                   FROM SAPSR3.RESB " //
				+ "                                  WHERE RESB.MANDT = '168' AND RESB.RSNUM = ? GROUP BY MATNR) " //
				+ "          GROUP BY MATNR, WERKS) MARD " //
				+ "   WHERE     RESB.MANDT = '168' " //
				+ "         AND RESB.RSNUM = ? " //
				+ "         AND MAKT.MANDT = RESB.MANDT " //
				+ "         AND MAKT.MANDT = RESB.MANDT " //
				+ "         AND MAKT.MATNR = RESB.MATNR " //
				+ "         AND MAKT.SPRAS = 'M' " //
				+ "         AND MARD.MATNR(+) = RESB.MATNR " //
				+ "         AND MARD.WERKS(+) = RESB.WERKS " //
				+ "GROUP BY MAKT.MAKTX, RESB.MATNR, RESB.WERKS, RESB.MATKL, RESB.DUMPS,RESB.MEINS, " //
				+ "         RESB.ALPGR, RESB.ALPRF " //
				+ "ORDER BY MATKL,MAKTX "; //

		try {
			Connection conn = getSapPrdConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, RSNUM);
			pstm.setString(2, RSNUM);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	private List<D272R> getD272Rs(HttpServletRequest req, Connection conn) {
		String sql = "SELECT * FROM D272R WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY SQNR";
		D272R e;
		int i = 0;

		List<D272R> list = new ArrayList<D272R>();

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, req.getParameter("GSDM"));
			pstm.setString(2, req.getParameter("BDDM"));
			pstm.setString(3, req.getParameter("BDBH"));

			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {
				i += 1;
				e = new D272R();
				e.setGSDM(rst.getString("GSDM"));
				e.setBDDM(rst.getString("BDDM"));
				e.setBDBH(rst.getString("BDBH"));
				e.setSQNR(i);
				e.setKOSTL(rst.getString("KOSTL"));
				e.setKUNNR(rst.getString("KUNNR"));
				e.setLIFNR(rst.getString("LIFNR"));
				e.setKTEXT(rst.getString("KTEXT"));
				e.setNAME1(rst.getString("NAME1"));
				e.setPCTG(rst.getInt("PCTG"));
				e.setJLYH(rst.getString("JLYH"));
				e.setJLSJ(rst.getTimestamp("JLSJ"));
				list.add(e);
			}
			rst.close();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (int j = i + 1; j < 6; ++j) {
			e = new D272R();
			e.setSQNR(j);
			list.add(e);
		}

		return list;
	}
	
	private String getSelectWS(String aufnr, Connection conn) {
		String sql = "select distinct b.vornr,d.arbpl " +
					" from sapsr3.afpo@SAPP a " +
					" join sapsr3.resb@SAPP b on b.mandt='168' and b.aufnr=a.aufnr and b.dumps=' ' and b.xloek=' ' and bdmng > 0 " +
					" join sapsr3.afvc@SAPP c on c.mandt='168' and c.aufpl=b.aufpl and c.aplzl=b.aplzl " +
					" join sapsr3.crhd@SAPP d on d.mandt='168' and d.objty='A' and d.objid=c.arbid and d.endda = '99991231' " +
					" where a.mandt='168' and a.aufnr=? " +
					" order by b.vornr";
		String WS = "";

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, aufnr);

			WS = "<select name=\"WoSt\" id=\"WoSt\">";
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {					
		        WS = WS + "<option value=\""+rst.getString("vornr")+"_"+rst.getString("arbpl")+"\">"+rst.getString("vornr")+"_"+rst.getString("arbpl")+"</option>";
			}
			WS = WS+"</select>";
			rst.close();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return WS;
	}
}
