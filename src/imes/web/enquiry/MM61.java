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

@WebServlet("/MM61")
public class MM61 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		try {

			render(req, resp, "/WEB-INF/pages/enquiry/MM61.jsp");
		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {
			actionPostMM61(req, resp);
		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	void actionPostMM61(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<String> matnrs = null;
		if (!req.getParameter("MATNRS").equals("")) {
			matnrs = textAreaToList(req, "MATNRS");
		}

		String sql;
		sql = "SELECT MAKT.MATNR, " //
				+ "       MAKT.MAKTX, " //
				+ "       MARA.MATKL, " //
				+ "       MARA.MEINS, " //
				+ "       DTRPIR, " //
				+ "       (SELECT LFA1.SORTL " //
				+ "          FROM IT.WPURINFORCD, SAPSR3.LFA1@SAPP " //
				+ "         WHERE     TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN DATAB AND DATBI " //
				+ "               AND LFA1.MANDT = '168' " //
				+ "               AND LFA1.LIFNR = WPURINFORCD.LIFNR " //
				+ "               AND ROWNUM = 1 " //
				+ "               AND INFNR = DTRPIR) " //
				+ "          DTRPSP, " //
				+ "       DTRP, " //
				+ "       DTFPIR, " //
				+ "       (SELECT LFA1.SORTL " //
				+ "          FROM IT.WPURINFORCD, SAPSR3.LFA1@SAPP " //
				+ "         WHERE     TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN DATAB AND DATBI " //
				+ "               AND LFA1.MANDT = '168' " //
				+ "               AND LFA1.LIFNR = WPURINFORCD.LIFNR " //
				+ "               AND ROWNUM = 1 " //
				+ "               AND INFNR = DTFPIR) " //
				+ "          DTFPSP, " //
				+ "       DTFP, " //
				+ "       (SELECT MIN (ZPLP2 / PEINH) " //
				+ "          FROM SAPSR3.MBEW@SAPP " //
				+ "         WHERE MANDT = '168' AND BWKEY IN ('481A', '482A') AND ZPLP2 > 0 AND MATNR = MAKT.MATNR) " //
				+ "          DTZPLP2, " //
				+ "       (SELECT MIN (VERPR / PEINH) " //
				+ "          FROM SAPSR3.MBEW@SAPP " //
				+ "         WHERE MANDT = '168' AND BWKEY IN ('481A', '482A') AND VERPR > 0 AND MATNR = MAKT.MATNR) " //
				+ "          DTVERPR, " //
				+ "       TXRPIR, " //
				+ "       (SELECT LFA1.SORTL " //
				+ "          FROM IT.WPURINFORCD, SAPSR3.LFA1@SAPP " //
				+ "         WHERE     TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN DATAB AND DATBI " //
				+ "               AND LFA1.MANDT = '168' " //
				+ "               AND LFA1.LIFNR = WPURINFORCD.LIFNR " //
				+ "               AND ROWNUM = 1 " //
				+ "               AND INFNR = TXRPIR) " //
				+ "          TXRPSP, " //
				+ "       TXRP, " //
				+ "       TXFPIR, " //
				+ "       (SELECT LFA1.SORTL " //
				+ "          FROM IT.WPURINFORCD, SAPSR3.LFA1@SAPP " //
				+ "         WHERE     TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN DATAB AND DATBI " //
				+ "               AND LFA1.MANDT = '168' " //
				+ "               AND LFA1.LIFNR = WPURINFORCD.LIFNR " //
				+ "               AND ROWNUM = 1 " //
				+ "               AND INFNR = TXFPIR) " //
				+ "          TXFPSP, " //
				+ "       TXFP, " //
				+ "       (SELECT MIN (ZPLP2 / PEINH) " //
				+ "          FROM SAPSR3.MBEW@SAPP " //
				+ "         WHERE MANDT = '168' AND BWKEY IN ('381A', '382A') AND ZPLP2 > 0 AND MATNR = MAKT.MATNR) " //
				+ "          TXZPLP2, " //
				+ "       (SELECT MIN (VERPR / PEINH) " //
				+ "          FROM SAPSR3.MBEW@SAPP " //
				+ "         WHERE MANDT = '168' AND BWKEY IN ('381A', '382A') AND VERPR > 0 AND MATNR = MAKT.MATNR) " //
				+ "          TXVERPR " //
				+ "  FROM IT.WPIRS, " //
				+ "       (SELECT MATNR, MAKTX " //
				+ "          FROM SAPSR3.MAKT@SAPP " //
				+ "         WHERE MANDT = '168' AND SPRAS = 'M'  "; //

		if (!req.getParameter("MATNRS").trim().equals("")) {
			matnrs = textAreaToList(req, "MATNRS");
			sql += "         AND MATNR IN (" + toSqlString(matnrs) + ") "; //
		}

		if (!req.getParameter("MAKTX").equals("")) {
			sql += "         AND UPPER (MAKTX) LIKE '%" + req.getParameter("MAKTX").toUpperCase() + "%' "; //
		}
		sql += "         ) MAKT, " //
				+ "       SAPSR3.MARA@SAPP " //
				+ " WHERE WPIRS.MATNR(+) = MAKT.MATNR AND MARA.MANDT(+) = '168' AND MARA.MATNR(+) = MAKT.MATNR "
				+ " ORDER BY MARA.MATKL,MAKT.MAKTX,MAKT.MATNR"; //

		Connection conn = getSapCoConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		List<HashMap<String, Object>> list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/enquiry/MM61.jsp");
	}
}
