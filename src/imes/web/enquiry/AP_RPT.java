package imes.web.enquiry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/AP_RPT")
public class AP_RPT extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		try {
			if (getAction(req).equals("AP61")) {

				ActionGetAP61(req, resp);

			}
			if (getAction(req).equals("AP62")) {

				ActionGetAP62(req, resp);

			} else if (getAction(req).equals("XXXX")) {

			} else {
				PrintWriter out = resp.getWriter();
				out.print("頁面不存在!");
				out.close();
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
			if (getAction(req).equals("AP61")) {

				ActionPostAP61(req, resp);

			}
			if (getAction(req).equals("AP62")) {

				ActionPostAP62(req, resp);

			} else if (getAction(req).equals("XXXX")) {

			} else {
				PrintWriter out = resp.getWriter();
				out.print("頁面不存在!");
				out.close();
			}
		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	private void ActionGetAP61(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		getServletContext().getRequestDispatcher("/WEB-INF/pages/enquiry/AP61.jsp").forward(req, resp);
	}

	private void ActionPostAP61(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "  SELECT SUBSTR (BSIS.ZUONR, 1, 10) EBELN, " //
				+ "         SUBSTR (BSIS.ZUONR, 11, 5) EBELP, " //
				+ "         BSIS.BUKRS, " //
				+ "         BSIS.HKONT, " //
				+ "         BSIS.GJAHR, " //
				+ "         BSIS.BELNR, " //
				+ "         BSIS.BUZEI, " //
				+ "         BSIS.XBLNR, " //
				+ "         EKKO.LIFNR, " //
				+ "         LFA1.NAME1, " //
				+ "         BSIS.BUDAT, " //
				+ "         BSIS.SHKZG, " //
				+ "         (SELECT MENGE " //
				+ "            FROM SAPSR3.EKBE@SAPP " //
				+ "           WHERE     MANDT = '168' " //
				+ "                 AND EBELN = SUBSTR (BSIS.ZUONR, 1, 10) " //
				+ "                 AND EBELP = SUBSTR (BSIS.ZUONR, 11, 5) " //
				+ "                 AND WRBTR = BSIS.WRBTR " //
				+ "                 AND BUDAT = BSIS.BUDAT " //
				+ "                 AND ROWNUM = 1) " //
				+ "            MENGE, " //
				+ "         MEINS, " //
				+ "         BSIS.WAERS, " //
				+ "         CASE WHEN BSIS.SHKZG = 'S' THEN BSIS.WRBTR * -1 ELSE BSIS.WRBTR END WRBTR, " //
				+ "         CASE WHEN BSIS.SHKZG = 'S' THEN BSIS.DMBTR * -1 ELSE BSIS.DMBTR END DMBTR, " //
				+ "         MATNR, " //
				+ "         TXZ01, " //
				+ "         MATKL, " //
				+ "         BSIS.WERKS, " //
				+ "         (NETPR / PEINH) NETPR, " //
				+ "         EKKO.VERKF, " //
				+ "         EKKO.TELF1, " //
				+ "         BSIS.BLART, " //
				+ "         T_01.AWTYP, " //
				+ "         T_01.ACTIV " //
				+ "    FROM SAPSR3.BSIS@SAPP, " //
				+ "         SAPSR3.FAGLFLEXA@SAPP T_01, " //
				+ "         SAPSR3.EKPO@SAPP, " //
				+ "         SAPSR3.EKKO@SAPP, " //
				+ "         SAPSR3.LFA1@SAPP " //
				+ "   WHERE     BSIS.MANDT = '168' " //
				+ "         AND BSIS.HKONT IN ('0021440000', '0021440001') " //
				+ "         AND T_01.RBUKRS = BSIS.BUKRS " //
				+ "         AND T_01.BELNR = BSIS.BELNR " //
				+ "         AND T_01.GJAHR = BSIS.GJAHR " //
				+ "         AND T_01.BUZEI = BSIS.BUZEI " //
				+ "         AND T_01.RCLNT = BSIS.MANDT " //
				+ "         AND T_01.RLDNR = '0L' " //
				+ "         AND EKPO.MANDT(+) = BSIS.MANDT " //
				+ "         AND EKPO.EBELN(+) = SUBSTR (BSIS.ZUONR, 1, 10) " //
				+ "         AND EKPO.EBELP(+) = SUBSTR (BSIS.ZUONR, 11, 5) " //
				+ "         AND EKKO.MANDT(+) = BSIS.MANDT " //
				+ "         AND EKKO.EBELN(+) = SUBSTR (BSIS.ZUONR, 1, 10) " //
				+ "         AND LFA1.MANDT(+) = EKKO.MANDT " //
				+ "         AND LFA1.LIFNR(+) = EKKO.LIFNR "; //

		sql += Helper.sqlParams(req, "BSIS.BUKRS", "STRBUKRS", "ENDBUKRS");
		sql += Helper.sqlParams(req, "BSIS.BUDAT", "STRBUDAT", "ENDBUDAT");
		sql += Helper.sqlParams(req, "EKKO.LIFNR", "STRLIFNR", "ENDLIFNR");
		sql += Helper.sqlParams(req, "BSIS.WAERS", "STRWAERS", "ENDWAERS");
		sql += "ORDER BY BSIS.BUDAT,EKKO.LIFNR,BSIS.XBLNR";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		List<HashMap<String, Object>> list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/enquiry/AP61.jsp").forward(req, resp);
	}

	private void ActionGetAP62(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		getServletContext().getRequestDispatcher("/WEB-INF/pages/enquiry/AP62.jsp").forward(req, resp);
	}

	private void ActionPostAP62(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String sql = "SELECT T010.IMPNR " //
				+ "       , T010.IMPIM " //
				+ "       , T010.BNAREA " //
				+ "       , T010.CONNR " //
				+ "       , T010.LIFNR " //
				+ "       , T010.DLFNR " //
				+ "       , TO_DATE (T010.IMDAT, 'YYYYMMDD') IMDAT " //
				+ "       , T010.EBELN " //
				+ "       , T010.EBELP " //
				+ "       , T010.MATNR " //
				+ "       , T010.TXZ01 " //
				+ "       , T010.MATKL " //
				+ "       , T010.MENGE " //
				+ "       , T010.MEINS " //
				+ "       , T010.NETPR " //
				+ "       , T010.WAERS " //
				+ "       , T010.WRBTR " //
				+ "       , T010.ZTERM " //
				+ "       , TO_DATE (T010.BASED, 'YYYYMMDD') BASED " //
				+ "       , T010.ZTAGG " //
				+ "       , T052.ZFAEL " //
				+ "       , T052.ZMONA " //
				+ "       , LFA1.NAME1 " //
				+ "       , LFA1.SORTL " //
				+ "       , (TO_DATE (T010.IMDAT, 'YYYYMMDD') + 27) REGDT " //
				+ "       , CASE WHEN T052.ZFAEL = '00' THEN TO_DATE (T010.IMDAT, 'YYYYMMDD') + T052.ZTAG1 ELSE ADD_MONTHS (TO_DATE (CONCAT (SUBSTR (T010.BASED, 1, 6), T052.ZFAEL), 'YYYYMMDD'), T052.ZMONA) END PAYDT " //
				+ "       , T052U.TEXT1 " //
				+ "  FROM SAPSR3.T052@SAPP, " //
				+ "       SAPSR3.LFA1@SAPP, " //
				+ "       SAPSR3.T052U@SAPP, " //
				+ "       (SELECT T001.IMPNR, " //
				+ "               T002.IMPIM, " //
				+ "               T001.BNAREA, " //
				+ "               T001.CONNR, " //
				+ "               EKKO.LIFNR, " //
				+ "               T001.DLFNR, " //
				+ "               T001.IMDAT, " //
				+ "               T002.INVNR, " //
				+ "               T002.EBELN, " //
				+ "               T002.EBELP, " //
				+ "               T002.MATNR, " //
				+ "               EKPO.TXZ01, " //
				+ "               EKPO.MATKL, " //
				+ "               T002.MENGE, " //
				+ "               T002.MEINS, " //
				+ "               (T002.NETPR / T002.PEINH) NETPR, " //
				+ "               T002.WAERS, " //
				+ "               T002.WRBTR, " //
				+ "               EKKO.ZTERM, " //
				+ "               TO_CHAR ( (ADD_MONTHS (TO_DATE (CONCAT (SUBSTR (IMDAT, 1, 6), '01'), 'YYYYMMDD'), 1) - 1), 'YYYYMMDD') " //
				+ "                  BASED, " //
				+ "               (SELECT ZTAGG " //
				+ "                  FROM SAPSR3.T052@SAPP " //
				+ "                 WHERE MANDT = '168' AND ZTERM = EKKO.ZTERM " //
				+ "                       AND (ZTAGG = 0 " //
				+ "                            OR ZTAGG >= " //
				+ "                                  SUBSTR ( " //
				+ "                                     TO_CHAR ( " //
				+ "                                        (ADD_MONTHS (TO_DATE (CONCAT (SUBSTR (IMDAT, 1, 6), '01'), 'YYYYMMDD'), 1) - 1), " //
				+ "                                        'YYYYMMDD'), " //
				+ "                                     7, " //
				+ "                                     2)) " //
				+ "                       AND ROWNUM = 1) " //
				+ "                  ZTAGG " //
				+ "          FROM SAPSR3.ZIEBI001@SAPP T001, " //
				+ "               SAPSR3.ZIEBI002@SAPP T002, " //
				+ "               SAPSR3.EKKO@SAPP, " //
				+ "               SAPSR3.EKPO@SAPP " //
				+ "         WHERE     T001.MANDT = '168' " //
				+ "               AND T001.LOEKZ <> 'X' "; //
		sql += Helper.sqlParams(req, "T001.BUKRS", "STRBUKRS", "ENDBUKRS");
		sql += Helper.sqlParams(req, "T001.IMDAT", "STRIMDAT", "ENDIMDAT");
		sql += Helper.sqlParams(req, "EKKO.LIFNR", "STRLIFNR", "ENDLIFNR");
		sql += Helper.sqlParams(req, "T001.IMPNR", "STRIMPNR", "ENDIMPNR");
		sql += "               AND T002.MANDT = T001.MANDT " //
				+ "               AND T002.IMPNR = T001.IMPNR " //
				+ "               AND EKKO.MANDT = T002.MANDT " //
				+ "               AND EKKO.EBELN = T002.EBELN " //
				+ "               AND EKPO.MANDT = T002.MANDT " //
				+ "               AND EKPO.EBELN = T002.EBELN " //
				+ "               AND EKPO.EBELP = T002.EBELP) T010 " //
				+ " WHERE     T052.MANDT(+) = '168' " //
				+ "       AND T052.ZTERM(+) = T010.ZTERM " //
				+ "       AND T052.ZTAGG(+) = T010.ZTAGG " //
				+ "       AND LFA1.MANDT(+) = '168' " //
				+ "       AND LFA1.LIFNR(+) = T010.LIFNR " //
				+ "       AND T052U.MANDT(+) = T052.MANDT " //
				+ "       AND T052U.ZTERM(+) = T052.ZTERM " //
				+ "       AND T052U.ZTAGG(+) = T052.ZTAGG " //
				+ "       AND T052U.SPRAS(+) = 'M' "
				+ " ORDER BY T010.IMPNR,T010.IMPIM"; //
		
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		System.out.println(sql);
		List<HashMap<String, Object>> list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/enquiry/AP62.jsp").forward(req, resp);
	}

}
