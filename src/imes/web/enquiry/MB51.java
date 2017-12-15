package imes.web.enquiry;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/MB51")
public class MB51 extends HttpController {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/MB51.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {
			ActionPost(req, resp);

		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String params = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "charg_low")
				+ getParam(req, "budat_low") + getParam(req, "mblnr_low") + getParam(req, "mjahr_low") + getParam(req, "matkl_low")
				+ getParam(req, "bwart_low") + getParam(req, "cpudt_low") + getParam(req, "matnr_textarea")
				+ getParam(req, "matkl_textarea") + getParam(req, "usnam");

		if (!params.equals("")) {
			String sql = "  SELECT MKPF.BUDAT, MSEG.WERKS, MSEG.BWART, MSEG.MATNR, " //
					+ "         MAKT.MAKTX, MARA.MATKL, MSEG.CHARG, CASE WHEN SHKZG = 'H' THEN MENGE * -1 ELSE MENGE END MENGE, " //
					+ "         MSEG.MEINS, MSEG.WAERS, CASE WHEN SHKZG = 'H' AND WAERS = 'RMB' THEN DMBTR * -1 WHEN SHKZG = 'H' AND WAERS = 'TWD' THEN DMBTR * -100 WHEN SHKZG = 'H' AND WAERS = 'HKD' THEN DMBTR * -100 WHEN SHKZG = 'H' AND WAERS = 'USD' THEN DMBTR * -100 WHEN SHKZG = 'H' AND WAERS = 'JPY' THEN DMBTR * -100 ELSE DMBTR END DMBTR, MSEG.AUFNR, " //
					+ "         AFPO.MATNR PMATNR, AFPO.PWERK, MSEG.LIFNR, LFA1.SORTL LNAME, " //
					+ "         MSEG.EBELN, MSEG.EBELP, MSEG.KUNNR, KNA1.SORTL KNAME, " //
					+ "         MSEG.KDAUF, MSEG.KDPOS, MSEG.KDEIN, MKPF.MBLNR, " //
					+ "         MKPF.MJAHR, MSEG.ZEILE, MKPF.CPUDT, MKPF.CPUTM, " //
					+ "         MKPF.USNAM, MSEG.KOSTL, " //
					+ "         (SELECT KTEXT FROM SAPSR3.CSKT WHERE MANDT= MSEG.MANDT AND SPRAS='M' AND KOKRS=MSEG.KOKRS AND KOSTL=MSEG.KOSTL AND DATBI >= TO_CHAR(SYSDATE,'YYYYMMDD') AND ROWNUM = 1) KTEXT, "
					+ "         (SELECT SUM (LABST) || ',' || SUM (INSME)  " //
					+ "       FROM SAPSR3.MARD  " //
					+ "         WHERE MANDT = MSEG.MANDT AND MATNR = MSEG.MATNR AND WERKS = MSEG.WERKS)   " //
					+ "         MARDD," //
					+ "         (SELECT LICHA FROM SAPSR3.MCH1 WHERE MANDT = MSEG.MANDT AND MATNR = MSEG.MATNR AND CHARG=MSEG.CHARG) LICHA "
					+ "    FROM SAPSR3.MKPF, " //
					+ "         SAPSR3.MSEG, " //
					+ "         SAPSR3.MARA, " //
					+ "         SAPSR3.MAKT, " //
					+ "         SAPSR3.AFPO, " //
					+ "         SAPSR3.KNA1, " //
					+ "         SAPSR3.LFA1 " //
					+ "   WHERE     MKPF.MANDT = '168' " //
					+ "         AND MSEG.MANDT = MKPF.MANDT " //
					+ "         AND MSEG.MBLNR = MKPF.MBLNR " //
					+ "         AND MSEG.MJAHR = MKPF.MJAHR " //
					+ "         AND MARA.MANDT = MSEG.MANDT " //
					+ "         AND MARA.MATNR = MSEG.MATNR " //
					+ "         AND MAKT.MANDT(+) = MSEG.MANDT " //
					+ "         AND MAKT.MATNR(+) = MSEG.MATNR " //
					+ "         AND MAKT.SPRAS(+) = 'M' " //
					+ "         AND LFA1.MANDT(+) = MSEG.MANDT " //
					+ "         AND LFA1.LIFNR(+) = MSEG.LIFNR " //
					+ "         AND KNA1.MANDT(+) = MSEG.MANDT " //
					+ "         AND KNA1.KUNNR(+) = MSEG.KUNNR " //
					+ "         AND AFPO.MANDT(+) = MSEG.MANDT " //
					+ "         AND AFPO.AUFNR(+) = MSEG.AUFNR "; //
			
			if (!getParam(req, "usnam_low").equals("")) {
				sql += Helper.sqlParams(req, "MKPF.USNAM", "usnam_low", "usnam_high");
			}
			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "MSEG.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MSEG.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "charg_low").equals("")) {
				sql += Helper.sqlParams(req, "MSEG.CHARG", "charg_low", "charg_high");
			}
			if (!getParam(req, "budat_low").equals("")) {
				sql += Helper.sqlParams(req, "MKPF.BUDAT", "budat_low", "budat_high");
			}
			if (!getParam(req, "mblnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MKPF.MBLNR", "mblnr_low", "mblnr_high");
			}
			if (!getParam(req, "mjahr_low").equals("")) {
				sql += Helper.sqlParams(req, "MKPF.MJAHR", "mjahr_low", "mjahr_high");
			}
			if (!getParam(req, "matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "MARA.MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req, "bwart_low").equals("")) {
				sql += Helper.sqlParams(req, "MSEG.BWART", "bwart_low", "bwart_high");
			}
			if (!getParam(req, "cpudt_low").equals("")) {
				sql += Helper.sqlParams(req, "MKPF.CPUDT", "cpudt_low", "cpudt_high");
			}
			if (!getParam(req, "matnr_textarea").trim().equals("")) {
				sql += "         AND MSEG.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "matkl_textarea").trim().equals("")) {
				sql += "         AND MARA.MATKL IN (" + toSqlString(textAreaToList(req, "matkl_textarea")) + ") ";
			}

			sql += "ORDER BY MKPF.CPUDT DESC, MKPF.CPUTM DESC, MSEG.WERKS, MSEG.MATNR"; //

			try {
				Connection conn = getSapPrdConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				list = Helper.resultSetToArrayList(pstm.executeQuery());
				pstm.close();
				conn.close();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		String[] buf;
		for (HashMap<String, Object> h : list) {
			if (h.get("MARDD") != null) {

				buf = h.get("MARDD").toString().split(",");

				if (buf.length > 1) {

					h.put("LABST", buf[0]);
					h.put("INSME", buf[1]);
				}
			}
		}
		req.setAttribute("list", list);

		render(req,resp,"/WEB-INF/pages/enquiry/MB51.jsp");
	}

}
