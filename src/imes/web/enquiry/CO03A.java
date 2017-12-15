package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/CO03A")
public class CO03A extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req,resp,"/WEB-INF/pages/enquiry/CO03A.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {
			ActionPost(req,resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			PrintError(resp,e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String params = getParam(req,"werks_low") + getParam(req,"matnr_low") + getParam(req,"charg_low") + getParam(req,"budat_low")
				+ getParam(req,"mblnr_low") + getParam(req,"mjahr_low") + getParam(req,"matkl_low") + getParam(req,"bwart_low")
				+ getParam(req,"aufnr_low") + getParam(req,"aufnr_textarea") + getParam(req,"matnr_textarea") + getParam(req,"matkl_textarea");

		if (!params.equals("")) {
			String sql = "  SELECT AUFM.AUFNR, " //
					+ "         AUFM.BUDAT, " //
					+ "         AUFM.BWART, " //
					+ "         AUFM.MATNR, " //
					+ "         MAKT.MAKTX, " //
					+ "         MARA.MATKL, " //
					+ "         AUFM.WERKS, " //
					+ "         AUFM.LGORT, " //
					+ "         AUFM.CHARG, " //
					+ "         CASE WHEN AUFM.SHKZG = 'H' THEN DMBTR * -1 ELSE DMBTR END DMBTR, " //
					+ "         AUFM.WAERS, " //
					+ "         CASE WHEN SHKZG = 'H' THEN MENGE * -1 ELSE MENGE END MENGE, " //
					+ "         AUFM.MEINS, " //
					+ "         AUFM.SHKZG, " //
					+ "         AUFM.RSNUM, " //
					+ "         AUFM.RSPOS, " //
					+ "         AUFM.MBLNR, " //
					+ "         AUFM.MJAHR, " //
					+ "         AUFM.ZEILE, " //
					+ "         (SELECT MKPF.BUDAT || ',' || LFA1.SORTL || ',' || MSEG.LIFNR || ',' || MSEG.EBELN || ',' || MSEG.EBELP || ',' || MSEG.AUFNR || ',' || MSEG.WERKS " //
					+ "            FROM SAPSR3.MSEG, SAPSR3.MKPF, SAPSR3.LFA1 " //
					+ "           WHERE     MSEG.MANDT = '168' " //
					+ "                 AND MSEG.BWART = '101' " //
					+ "                 AND MSEG.MATNR = AUFM.MATNR " //
					+ "                 AND MSEG.CHARG = AUFM.CHARG " //
					+ "                 AND MSEG.INSMK = 'X' " //
					+ "                 AND MKPF.MANDT(+) = MSEG.MANDT " //
					+ "                 AND MKPF.MBLNR(+) = MSEG.MBLNR " //
					+ "                 AND MKPF.MJAHR(+) = MSEG.MJAHR " //
					+ "                 AND LFA1.MANDT(+) = MSEG.MANDT " //
					+ "                 AND LFA1.LIFNR(+) = MSEG.LIFNR " //
					+ "                 AND ROWNUM = 1) " //
					+ "            MSEGD " //
					+ "    FROM SAPSR3.AUFM, SAPSR3.MARA, SAPSR3.MAKT " //
					+ "   WHERE     AUFM.MANDT = '168' " //
					+ "         AND MARA.MANDT = AUFM.MANDT " //
					+ "         AND MARA.MATNR = AUFM.MATNR " //
					+ "         AND MAKT.MANDT = AUFM.MANDT " //
					+ "         AND MAKT.MATNR = AUFM.MATNR " //
					+ "         AND MAKT.SPRAS = 'M' "; //
			if (!getParam(req,"aufnr_low").equals("")) {
				sql += Helper.sqlParamsFormat(req, "AUFM.AUFNR", "aufnr_low", "aufnr_high", "%012d");
			}
			if (!getParam(req,"werks_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req,"matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req,"charg_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.CHARG", "charg_low", "charg_high");
			}
			if (!getParam(req,"budat_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.BUDAT", "budat_low", "budat_high");
			}
			if (!getParam(req,"mblnr_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.MBLNR", "mblnr_low", "mblnr_high");
			}
			if (!getParam(req,"mjahr_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.MJAHR", "mjahr_low", "mjahr_high");
			}
			if (!getParam(req,"matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "MARA.MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req,"bwart_low").equals("")) {
				sql += Helper.sqlParams(req, "AUFM.BWART", "bwart_low", "bwart_high");
			}
			if (!getParam(req,"aufnr_textarea").trim().equals("")) {
				sql += "         AND AUFM.AUFNR IN (" + toSqlString(textAreaToList(req,"aufnr_textarea", "%012d")) + ") ";
			}
			if (!getParam(req,"matnr_textarea").trim().equals("")) {
				sql += "         AND AUFM.MATNR IN (" + toSqlString(textAreaToList(req,"matnr_textarea")) + ") ";
			}
			if (!getParam(req,"matkl_textarea").trim().equals("")) {
				sql += "         AND MARA.MATKL IN (" + toSqlString(textAreaToList(req,"matkl_textarea")) + ") ";
			}
			sql += "ORDER BY AUFM.AUFNR, AUFM.BUDAT, AUFM.MATNR, AUFM.CHARG ";//

			try {
				Connection conn = getSapPrdConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				list = Helper.resultSetToArrayList(pstm.executeQuery());
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String[] buf;
		for (HashMap<String, Object> h : list) {
			if (h.get("MSEGD") != null) {
				buf = h.get("MSEGD").toString().split(",");
				h.put("MBUDAT", buf[0]);
				h.put("SORTL", buf[1]);
				h.put("LIFNR", buf[2]);
				h.put("EBELN", buf[3]);
				h.put("EBELP", buf[4]);
				h.put("RAUFNR", buf[5]);
				h.put("RWERK", buf[6]);
			}
		}

		req.setAttribute("list", list);

		render(req,resp,"/WEB-INF/pages/enquiry/CO03A.jsp");
	}
}
