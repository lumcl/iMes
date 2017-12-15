package imes.web.sapgui;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.entity.SGME01H;

@WebServlet("/SGME01")
public class SGME01 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);

		try {
			if (Authorised(req)) {
				if (getAction(req).equals("Restart")) {

					ActionGetRestart(req, resp);

				} else if (getAction(req).equals("")) {

					ActionGet(req, resp);
				}
			} else {
				resp.sendRedirect("/iMes/home");
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
			if (Authorised(req)) {
				if (getAction(req).equals("Create")) {

					ActionPostCreate(req, resp);
				}
			} else {
				resp.sendRedirect("/iMes/home");
			}
		} catch (Exception e) {

			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	private boolean Authorised(HttpServletRequest req) throws Exception {
		boolean authorised = false;
		Connection conn = getConnection();
		String sql = "SELECT PARAMS FROM USER_TCODE WHERE TCODE='SGME01' AND USERID = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, getUid(req));

		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			authorised = true;
		}
		rst.close();
		pstm.close();
		conn.close();
		return authorised;
	}

	private void ActionGetRestart(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "UPDATE SGME01 SET ME01=CASE WHEN ME01='X' THEN 'X' ELSE 'A' END, QI01=CASE WHEN QI01='X' THEN 'X' ELSE 'B' END WHERE ID=?";

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, toIntegerParam(req,"id"));

		pstm.executeUpdate();

		resp.sendRedirect("/iMes/SGME01");
	}

	private void ActionGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		String sql = "SELECT * FROM SGME01 WHERE ME01 <> 'X' OR QI01 <> 'X' OR (JLSJ BETWEEN SYSDATE - 1 AND SYSDATE + 1) ORDER BY ID";

		PreparedStatement pstm = conn.prepareStatement(sql);

		List<HashMap<String, Object>> list = Helper.resultSetToArrayList(pstm.executeQuery());

		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/sapgui/SGME01.jsp").forward(req, resp);
	}

	private void ActionPostCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// 日期到: <input type="text" name="FREI_DAT" size="10" class="datepick">

		SGME01H e = new SGME01H();

		e.setMATNR(toUpperCaseParam(req,"MATNR"));
		e.setMAKTX(req.getParameter("MAKTX"));
		e.setWERKS(toUpperCaseParam(req,"WERKS"));
		e.setVDATU(req.getParameter("VDATU"));

		if (req.getParameter("BDATU") == "") {
			e.setBDATU("99991231");
		} else {
			e.setBDATU(req.getParameter("BDATU"));
		}

		e.setLIFNR(toUpperCaseParam(req,"LIFNR"));
		e.setSORTL(req.getParameter("SORTL"));
		e.setEKORG(toUpperCaseParam(req,"EKORG"));

		// if (req.getParameter("FREI_DAT") == "") {
		// e.setFREI_DAT(Helper.fmtDate(new Date(), "yyyyMMdd"));
		// } else {
		// e.setFREI_DAT(req.getParameter("FREI_DAT"));
		// }

		if (req.getParameter("FREI_MNG").trim() != "") {
			e.setFREI_MNG(Double.parseDouble(req.getParameter("FREI_MNG")));
		}

		if (req.getParameter("SPERRGRUND").trim() != "") {
			e.setSPERRGRUND(toUpperCaseParam(req,"SPERRGRUND"));
		}

		e.setJLYH(getUid(req));
		e.setJLSJ(new Date());

		Connection conn = getConnection();

		String sql;
		PreparedStatement pstm;
		ResultSet rst;

		sql = "SELECT COUNT(*) CNT FROM SAPSR3.QINF@SAPP WHERE MANDT='168' AND MATNR=? AND WERK=? AND LIEFERANT=? AND REVLV = ' '";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, e.getMATNR());
		pstm.setString(2, e.getWERKS());
		pstm.setString(3, e.getLIFNR());

		rst = pstm.executeQuery();

		if (rst.next()) {
			if (rst.getInt("CNT") > 0) {
				e.setQI01("B");
				e.setQI01EMSG("SAP QI01記錄將被覆蓋");
			}
		}

		rst.close();
		pstm.close();

		sql = "SELECT COUNT(*) CNT FROM SAPSR3.EORD@SAPP WHERE MANDT='168' AND MATNR=? AND WERKS=? AND LIFNR=?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, e.getMATNR());
		pstm.setString(2, e.getWERKS());
		pstm.setString(3, e.getLIFNR());

		rst = pstm.executeQuery();

		if (rst.next()) {
			if (rst.getInt("CNT") > 0) {
				e.setME01("X");
				e.setME01EMSG("SAP ME01記錄已存在");

			}
		}
		rst.close();
		pstm.close();

		// if (!e.getQI01().equals("X")) {
		//
		// sql =
		// "SELECT REVLV FROM SAPSR3.AEOI@SAPP WHERE MANDT='168' AND AETYP='41' AND OBJKT=? ORDER BY REVLV DESC";
		//
		// pstm = conn.prepareStatement(sql);
		// pstm.setString(1, e.getMATNR());
		//
		// rst = pstm.executeQuery();
		//
		// if (rst.next()) {
		// e.setREVLV(rst.getString("REVLV"));
		// }
		//
		// rst.close();
		// pstm.close();
		// }
		//
		e.insertDb(conn);

		conn.close();

		resp.sendRedirect("/iMes/SGME01");
	}

}
