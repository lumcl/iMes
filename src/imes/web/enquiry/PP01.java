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

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;

@WebServlet("/PP01")
public class PP01 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		render(req, resp,"/WEB-INF/pages/enquiry/PP01.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		try {
			ActionPost(req, resp);
			render(req, resp,"/WEB-INF/pages/enquiry/PP01.jsp");
		} catch (Exception ex) {
			PrintError(resp,ex);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req,"werks_low") + getParam(req,"dispo_low") + getParam(req,"gstrp_low") + getParam(req,"gltrp_low")
				+ getParam(req,"matnr_low") + getParam(req,"aufnr_low") + getParam(req,"dispo_textarea") + getParam(req,"matnr_textarea")
				+ getParam(req,"aufnr_textarea");

		if (jspParams.isEmpty()) {
			return;
		}

		String sql = "WITH T002 " //
				+ "     AS (  SELECT MATNR, MAX (KUNNR) KUNNR " //
				+ "             FROM SAPSR3.VBBE " //
				+ "            WHERE MANDT = '168' AND KUNNR <> ' ' " //
				+ "         GROUP BY MATNR) " //
				+ "  SELECT AFPO.AUFNR, " //
				+ "         AFKO.DISPO, " //
				+ "         AUFK.WERKS, " //
				+ "         AFPO.MATNR, " //
				+ "         MAKT.MAKTX, " //
				+ "         AFVV.MGVRG PSMNG, " //
				+ "         AFVV.LMNGA WEMNG, " //
				+ "         AFPO.MEINS, " //
				+ "         T002.KUNNR, " //
				+ "         KNA1.SORTL, " //
				+ "         AFKO.GSTRP, " //
				+ "         AFKO.GLTRP, " //
				+ "         AFKO.GSTRI, " //
				+ "         AFKO.GLTRI, " //
				+ "         AFVC.VORNR, " //
				+ "         CRHD.ARBPL, " //
				+ "         AFVC.LTXA1, " //
				+ "         (AFVV.VGW01 * AFVV.MGVRG / AFVV.BMSCH) VGW01, " //
				+ "         (AFVV.VGW05 * AFVV.MGVRG / AFVV.BMSCH) VGW05, " //
				+ "         (SELECT MAX (DECODE (STAT, 'I0002', 'REL ')) || MAX (DECODE (STAT, 'I0004', 'MSPT ')) || MAX (DECODE (STAT, 'I0009', 'CNF ')) || MAX (DECODE (STAT, 'I0010', 'PCNF ')) || MAX (DECODE (STAT, 'I0012', 'DLV ')) || MAX (DECODE (STAT, 'I0074', 'PDLV ')) || MAX (DECODE (STAT, 'I0321', 'RMWB ')) || MAX (DECODE (STAT, 'I0340', 'MACM')) || MAX (DECODE (STAT, 'I0045', 'TECO')) || MAX (DECODE (STAT, 'I0046', '關閉')) || MAX (DECODE (STAT, 'I0076', 'DLTD')) " //
				+ "            FROM SAPSR3.JEST " //
				+ "           WHERE JEST.MANDT = '168' AND JEST.INACT = ' ' AND JEST.OBJNR = AUFK.OBJNR) " //
				+ "            JESTD " //
				+ "    FROM SAPSR3.AFPO, " //
				+ "         SAPSR3.AFKO, " //
				+ "         SAPSR3.AUFK, " //
				+ "         SAPSR3.AFVV, " //
				+ "         SAPSR3.AFVC, " //
				+ "         SAPSR3.MAKT, " //
				+ "         SAPSR3.KNA1, " //
				+ "         SAPSR3.CRHD, " //
				+ "         T002 " //
				+ "   WHERE     AFKO.MANDT = AFPO.MANDT " //
				+ "         AND AFKO.AUFNR = AFPO.AUFNR " //
				+ "         AND AUFK.MANDT = AFPO.MANDT " //
				+ "         AND AUFK.AUFNR = AFPO.AUFNR " //
				+ "         AND AUFK.AUTYP = '10' " //
				+ "         AND AFVC.MANDT = AFKO.MANDT " //
				+ "         AND AFVC.AUFPL = AFKO.AUFPL " //
				+ "         AND AFVV.MANDT = AFVC.MANDT " //
				+ "         AND AFVV.AUFPL = AFVC.AUFPL " //
				+ "         AND AFVV.APLZL = AFVC.APLZL " //
				+ "         AND MAKT.MANDT = AFPO.MANDT " //
				+ "         AND MAKT.MATNR = AFPO.MATNR " //
				+ "         AND MAKT.SPRAS = 'M' " //
				+ "         AND AFPO.ELIKZ = ' ' " //
				+ "         AND AFPO.DNREL = ' ' " //
				+ "         AND AFPO.MANDT = '168' " //
				+ "         AND CRHD.MANDT(+) = AFVC.MANDT " //
				+ "         AND CRHD.OBJID(+) = AFVC.ARBID " //
				+ "         AND CRHD.OBJTY(+) = 'A' " //
				+ "         AND T002.MATNR(+) = AFPO.MATNR " //
				+ "         AND KNA1.MANDT(+) = '168' " //
				+ "         AND KNA1.KUNNR(+) = T002.KUNNR ";//

		if (!getParam(req,"werks_low").equals("")) {
			sql += Helper.sqlParams(req, "AUFK.WERKS", "werks_low", "werks_high");
		}
		if (!getParam(req,"dispo_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.DISPO", "dispo_low", "dispo_high");
		}
		if (!getParam(req,"gstrp_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.GSTRP", "gstrp_low", "gstrp_high");
		}
		if (!getParam(req,"gltrp_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.GLTRP", "gltrp_low", "gltrp_high");
		}
		if (!getParam(req,"arbpl_low").equals("")) {
			sql += Helper.sqlParams(req, "CRHD.ARBPL", "arbpl_low", "arbpl_high");
		}
		if (!getParam(req,"matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "AFPO.MATNR", "matnr_low", "matnr_high");
		}
		if (!getParam(req,"aufnr_low").equals("")) {
			sql += Helper.sqlParamsFormat(req, "AFPO.AUFNR", "aufnr_low", "aufnr_high", "%012d");
		}
		if (!getParam(req,"dispo_textarea").equals("")) {
			sql += "         AND AFKO.DISPO IN (" + toSqlString(textAreaToList(req,"dispo_textarea")) + ") ";
		}
		if (!getParam(req,"arbpl_textarea").equals("")) {
			sql += "         AND CRHD.ARBPL IN (" + toSqlString(textAreaToList(req,"arbpl_textarea")) + ") ";
		}
		if (!getParam(req,"matnr_textarea").equals("")) {
			sql += "         AND AFPO.MATNR IN (" + toSqlString(textAreaToList(req,"matnr_textarea")) + ") ";
		}
		if (!getParam(req,"aufnr_textarea").equals("")) {
			sql += "         AND AFPO.AUFNR IN (" + toSqlString(textAreaToList(req,"aufnr_textarea", "%012d")) + ") ";
		}
		sql += "       ORDER BY AFKO.GLTRP, AFKO.DISPO "; //

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		String aufnr;
		HashMap<String, String> texts = GetProductOrderText(list);

		for (HashMap<String, Object> e : list) {
			aufnr = e.get("AUFNR").toString();
			if (texts.containsKey(aufnr)) {
				e.put("TEXT1", texts.get(aufnr));
			}

		}

		req.setAttribute("list", list);

	}

	private HashMap<String, String> GetProductOrderText(List<HashMap<String, Object>> list) {

		HashMap<String, String> texts = new HashMap<String, String>();

		try {
			SapRfcConnection rfcConn = new SapRfcConnection();
			JCoFunction function = rfcConn.getFunction("RFC_READ_TEXT");
			JCoTable text_lines = function.getTableParameterList().getTable("TEXT_LINES");
			for (HashMap<String, Object> e : list) {
				text_lines.appendRow();
				text_lines.setValue("MANDT", "168");
				text_lines.setValue("TDOBJECT", "AUFK");
				text_lines.setValue("TDNAME", "168" + e.get("AUFNR").toString());
				text_lines.setValue("TDID", "KOPF");
				text_lines.setValue("TDSPRAS", "M");
			}
			rfcConn.execute(function);

			String aufnr;

			JCoTable table = function.getTableParameterList().getTable("TEXT_LINES");
			TableAdapterReader reader = new TableAdapterReader(table);
			for (int i = 0; i < reader.size(); i++) {

				aufnr = reader.get("TDNAME").substring(3);
				if (!texts.containsKey(aufnr)) {
					texts.put(aufnr, reader.get("TDLINE"));
				}
				reader.next();
			}

			reader = null;
			table.clear();
			table = null;
			text_lines.clear();
			text_lines = null;
			function = null;
			rfcConn = null;

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return texts;

	}
}
