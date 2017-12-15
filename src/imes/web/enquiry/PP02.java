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

@WebServlet("/PP02")
public class PP02 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/PP02.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		try {
			ActionPost(req, resp);
			render(req, resp, "/WEB-INF/pages/enquiry/PP02.jsp");
		} catch (Exception ex) {
			PrintError(resp, ex);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "werks_low") + getParam(req, "dispo_low") + getParam(req, "gstrp_low")
				+ getParam(req, "gltrp_low") + getParam(req, "matnr_low") + getParam(req, "aufnr_low")
				+ getParam(req, "dispo_textarea") + getParam(req, "matnr_textarea") + getParam(req, "aufnr_textarea");

		if (jspParams.isEmpty()) {
			return;
		}

		String sql = "SELECT AFPO.AUFNR, " //
				+ "       MARC.FEVOR DISPO, " //
				+ "       MARA.MSTAE, " //
				+ "       AUFK.WERKS, " //
				+ "       AFPO.MATNR, " //
				+ "       (select max(zsd0012.umdat) from sapsr3.zsd0012 where zsd0012.mandt='168' and zsd0012.matnr=afpo.matnr and zsd0012.werks=aufk.werks and zsd0012.del12=afpo.aufnr) UMDAT,"
				+ "       MARA.MATKL, " //
				+ "       MAKT.MAKTX, " //
				+ "       AFPO.PSMNG, " //
				+ "       AFPO.WEMNG, " //
				+ "       AFPO.MEINS, " //
				+ "       AFKO.GSTRP, " //
				+ "       AFKO.GLTRP, " //
				+ "       AFKO.GSTRI, " //
				+ "       AFKO.GLTRI, " //
				+ "       AFKO.FTRMI, " //
				+ "       NVL ( " //
				+ "          (SELECT KNA1.SORTL || ',' || KNA1.KUNNR " //
				+ "             FROM SAPSR3.VBBE, SAPSR3.KNA1 " //
				+ "            WHERE     VBBE.MANDT = '168' " //
				+ "                  AND VBBE.MATNR = AFPO.MATNR " //
				+ "                  AND KNA1.MANDT = VBBE.MANDT " //
				+ "                  AND KNA1.KUNNR = VBBE.KUNNR " //
				+ "                  AND ROWNUM = 1), " //
				+ "          (SELECT KNA1.SORTL || ',' || KNA1.KUNNR " //
				+ "             FROM SAPSR3.KNMT, SAPSR3.KNA1 " //
				+ "            WHERE     KNMT.MANDT = '168' " //
				+ "                  AND KNMT.MATNR = AFPO.MATNR " //
				+ "                  AND KNA1.MANDT = KNMT.MANDT " //
				+ "                  AND KNA1.KUNNR = KNMT.KUNNR " //
				+ "                  AND ROWNUM = 1)) " //
				+ "          KNMTD, " //
				+ " (select sum (a.vgw01 * a.mgvrg / a.bmsch) || ',' || sum (a.vgw05 * a.mgvrg / a.bmsch) " //
                + "　from sapsr3.afvv a " //
                + "   join sapsr3.afvc b on b.mandt=a.mandt and b.aufpl=a.aufpl and b.aplzl=a.aplzl "//
                + "   left join sapsr3.jest c on c.mandt=b.mandt and c.objnr=b.objnr and c.inact=' ' and c.stat='I0013' "//
                + " where a.mandt='168' and a.aufpl=afko.aufpl and c.mandt is null "//
                + " group by a.aufpl) " //
				+ "          AFVVD, " //
				+ "       (SELECT MAX (DECODE (STAT, 'I0002', 'REL ')) || MAX (DECODE (STAT, 'I0004', 'MSPT ')) || MAX (DECODE (STAT, 'I0009', 'CNF ')) || MAX (DECODE (STAT, 'I0010', 'PCNF ')) || MAX (DECODE (STAT, 'I0012', 'DLV ')) || MAX (DECODE (STAT, 'I0074', 'PDLV ')) || MAX (DECODE (STAT, 'I0321', 'RMWB ')) || MAX (DECODE (STAT, 'I0340', 'MACM')) || MAX (DECODE (STAT, 'I0045', 'TECO')) || MAX (DECODE (STAT, 'I0046', '關閉')) || MAX (DECODE (STAT, 'I0076', 'DLTD')) " //
				+ "          FROM SAPSR3.JEST " //
				+ "         WHERE JEST.MANDT = '168' AND JEST.INACT = ' ' AND JEST.OBJNR = AUFK.OBJNR) " //
				+ "          JESTD, " //
				+ "       (SELECT MIN (BUDAT) " //
				+ "          FROM SAPSR3.AUFM " //
				+ "         WHERE MANDT = '168' AND AUFNR = AUFK.AUFNR AND BWART = '261') " //
				+ "          AUFMD " //
				+ "  FROM SAPSR3.AFPO, " //
				+ "       SAPSR3.AFKO, " //
				+ "       SAPSR3.AUFK, " //
				+ "       SAPSR3.MARA, " //
				+ "       SAPSR3.MAKT, " //
				+ "       SAPSR3.MARC " //
				+ " WHERE     AFKO.MANDT = AFPO.MANDT " //
				+ "       AND AFKO.AUFNR = AFPO.AUFNR " //
				+ "       AND AUFK.MANDT = AFPO.MANDT " //
				+ "       AND AUFK.AUFNR = AFPO.AUFNR " //
				+ "       AND AUFK.AUTYP = '10' " //
				+ "       AND MARA.MANDT = AFPO.MANDT " //
				+ "       AND MARA.MATNR = AFPO.MATNR " //
				+ "       AND MAKT.MANDT = AFPO.MANDT " //
				+ "       AND MAKT.MATNR = AFPO.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND MARC.MANDT = AFPO.MANDT " //
				+ "       AND MARC.MATNR = AFPO.MATNR " //
				+ "       AND MARC.WERKS = AUFK.WERKS " //
				+ "       AND AFPO.MANDT = '168' "; //

		if (req.getParameter("teko_checkbox") == null) {
			sql += "       AND AFPO.ELIKZ = ' ' ";//
			sql += "       AND AFPO.DNREL = ' ' "; //
		}
		if (!getParam(req, "werks_low").equals("")) {
			sql += Helper.sqlParams(req, "AUFK.WERKS", "werks_low", "werks_high");
		}
		if (!getParam(req, "dispo_low").equals("")) {
			sql += Helper.sqlParams(req, "MARC.FEVOR", "dispo_low", "dispo_high");
		}
		if (!getParam(req, "gstrp_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.GSTRP", "gstrp_low", "gstrp_high");
		}
		if (!getParam(req, "gltrp_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.GLTRP", "gltrp_low", "gltrp_high");
		}
		if (!getParam(req, "matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "AFPO.MATNR", "matnr_low", "matnr_high");
		}
		if (!getParam(req, "aufnr_low").equals("")) {
			sql += Helper.sqlParamsFormat(req, "AFPO.AUFNR", "aufnr_low", "aufnr_high", "%012d");
		}
		if (!getParam(req, "dispo_textarea").equals("")) {
			sql += "         AND AFKO.DISPO IN (" + toSqlString(textAreaToList(req, "dispo_textarea")) + ") ";
		}
		if (!getParam(req, "matnr_textarea").equals("")) {
			sql += "         AND AFPO.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
		}
		if (!getParam(req, "aufnr_textarea").equals("")) {
			sql += "         AND AFPO.AUFNR IN (" + toSqlString(textAreaToList(req, "aufnr_textarea", "%012d")) + ") ";
		}
		sql += "       ORDER BY AFKO.GLTRP, AFKO.DISPO "; //

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		String aufnr;
		String buf[];
		HashMap<String, String> texts = GetProductOrderText(list);

		for (HashMap<String, Object> e : list) {
			aufnr = e.get("AUFNR").toString();
			if (texts.containsKey(aufnr)) {
				e.put("TEXT1", texts.get(aufnr));
			}

			if (e.get("AFVVD") != null) {
				buf = e.get("AFVVD").toString().split(",");

				if (buf.length > 1) {
					e.put("VGW01", buf[0]);
					e.put("VGW05", buf[1]);
				}
			}

			if (e.get("KNMTD") != null) {
				buf = e.get("KNMTD").toString().split(",");

				if (buf.length > 1) {
					e.put("SORTL", buf[0]);
					e.put("KUNNR", buf[1]);
				}
			}
		}

		if (req.getParameter("aufnr_checkbox") == null) {

			sql = "SELECT PLAF.PLNUM, " //
					+ "       MARC.FEVOR DISPO, " //
					+ "       MARA.MSTAE, " //
					+ "       PLAF.PLWRK, " //
					+ "       PLAF.MATNR, " //
					+ "       MARA.MATKL, " //
					+ "       MAKT.MAKTX, " //
					+ "       PLAF.GSMNG, " //
					+ "       PLAF.MEINS, " //
					+ "       PLAF.PSTTR, " //
					+ "       PLAF.PEDTR, " //
					+ "       PLAF.PBDNR KUNNR, " //
					+ "       KNA1.SORTL, " //
					+ "       (SELECT SUM ( (VGW01 * GSMNG) / BMSCH) || ',' || SUM ( (VGW05 * GSMNG) / BMSCH) VGW05 " //
					+ "          FROM SAPSR3.MAPL, SAPSR3.PLAS, SAPSR3.PLPO " //
					+ "         WHERE     MAPL.MANDT = '168' " //
					+ "               AND MAPL.PLNTY = 'N' " //
					+ "               AND MAPL.MATNR = PLAF.MATNR " //
					+ "               AND MAPL.WERKS = PLAF.PLWRK " //
					+ "               AND MAPL.LOEKZ = ' ' " //
					+ "               AND MAPL.DATUV = " //
					+ "                      (SELECT MAX (DATUV) " //
					+ "                         FROM SAPSR3.MAPL " //
					+ "                        WHERE     MANDT = '168' " //
					+ "                              AND PLNTY = 'N' " //
					+ "                              AND MATNR = PLAF.MATNR " //
					+ "                              AND WERKS = PLAF.PLWRK " //
					+ "                              AND LOEKZ = ' ') " //
					+ "               AND PLAS.MANDT = MAPL.MANDT " //
					+ "               AND PLAS.PLNTY = MAPL.PLNTY " //
					+ "               AND PLAS.PLNNR = MAPL.PLNNR " //
					+ "               AND PLAS.PLNAL = MAPL.PLNAL " //
					+ "               AND PLAS.LOEKZ = ' ' " //
					+ "               AND PLPO.MANDT = PLAS.MANDT " //
					+ "               AND PLPO.PLNTY = PLAS.PLNTY " //
					+ "               AND PLPO.PLNNR = PLAS.PLNNR " //
					+ "               AND PLPO.ZAEHL = PLAS.ZAEHL " //
					+ "               AND PLPO.PLNKN = PLAS.PLNKN " //
					+ "               AND PLPO.LOEKZ = ' ') " //
					+ "          PLPOD, " //
					+ "       NVL ( " //
					+ "          (SELECT KNA1.SORTL || ',' || KNA1.KUNNR " //
					+ "             FROM SAPSR3.VBBE, SAPSR3.KNA1 " //
					+ "            WHERE     VBBE.MANDT = '168' " //
					+ "                  AND VBBE.MATNR = PLAF.MATNR " //
					+ "                  AND KNA1.MANDT = VBBE.MANDT " //
					+ "                  AND KNA1.KUNNR = VBBE.KUNNR " //
					+ "                  AND ROWNUM = 1), " //
					+ "          (SELECT KNA1.SORTL || ',' || KNA1.KUNNR " //
					+ "             FROM SAPSR3.KNMT, SAPSR3.KNA1 " //
					+ "            WHERE     KNMT.MANDT = '168' " //
					+ "                  AND KNMT.MATNR = PLAF.MATNR " //
					+ "                  AND KNA1.MANDT = KNMT.MANDT " //
					+ "                  AND KNA1.KUNNR = KNMT.KUNNR " //
					+ "                  AND ROWNUM = 1)) " //
					+ "          KNMTD " //
					+ "  FROM SAPSR3.PLAF, " //
					+ "       SAPSR3.KNA1, " //
					+ "       SAPSR3.MARA, " //
					+ "       SAPSR3.MAKT, " //
					+ "       SAPSR3.MARC " //
					+ " WHERE     PLAF.MANDT = '168' " //
					+ "       AND PLAF.BESKZ = 'E' " //
					+ "       AND PLAF.PAART <> 'VP' " //
					+ "       AND MARA.MANDT = PLAF.MANDT " //
					+ "       AND MARA.MATNR = PLAF.MATNR " //
					+ "       AND MAKT.MANDT = PLAF.MANDT " //
					+ "       AND MAKT.MATNR = PLAF.MATNR " //
					+ "       AND MAKT.SPRAS = 'M' " //
					+ "       AND MARC.MANDT = PLAF.MANDT " //
					+ "       AND MARC.MATNR = PLAF.MATNR " //
					+ "       AND MARC.WERKS = PLAF.PLWRK " //
					+ "       AND KNA1.MANDT(+) = PLAF.MANDT " //
					+ "       AND KNA1.KUNNR(+) = PLAF.PBDNR "; //

			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.PLWRK", "werks_low", "werks_high");
			}
			if (!getParam(req, "dispo_low").equals("")) {
				sql += Helper.sqlParams(req, "MARC.FEVOR", "dispo_low", "dispo_high");
			}
			if (!getParam(req, "aufnr_low").equals("")) {
				sql += Helper.sqlParamsFormat(req, "PLAF.PLNUM", "aufnr_low", "aufnr_high", "%010d");
			}
			if (!getParam(req, "gstrp_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.PSTTR", "gstrp_low", "gstrp_high");
			}
			if (!getParam(req, "gltrp_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.PEDTR", "gltrp_low", "gltrp_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "dispo_textarea").equals("")) {
				sql += "         AND PLAF.DISPO IN (" + toSqlString(textAreaToList(req, "dispo_textarea")) + ") ";
			}
			if (!getParam(req, "matnr_textarea").equals("")) {
				sql += "         AND PLAF.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "aufnr_textarea").equals("")) {
				sql += "         AND PLAF.PLNUM IN (" + toSqlString(textAreaToList(req, "aufnr_textarea", "%010d")) + ") ";
			}
			sql += "       ORDER BY PLAF.PSTTR, PLAF.DISPO "; //

			List<HashMap<String, Object>> plafs = new ArrayList<HashMap<String, Object>>();

			conn = getSapPrdConnection();
			pstm = conn.prepareStatement(sql);

			plafs = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

			HashMap<String, Object> p;
			int rowId = list.size();
			for (HashMap<String, Object> e : plafs) {
				p = new HashMap<String, Object>();

				rowId += 1;
				p.put("ROWID", rowId);
				p.put("AUFNR", e.get("PLNUM"));
				p.put("DISPO", e.get("DISPO"));
				p.put("WERKS", e.get("PLWRK"));
				p.put("MATNR", e.get("MATNR"));
				p.put("PSMNG", e.get("GSMNG"));
				p.put("WEMNG", e.get(0));
				p.put("MEINS", e.get("MEINS"));
				p.put("GSTRP", e.get("PSTTR"));
				p.put("GLTRP", e.get("PEDTR"));
				p.put("MAKTX", e.get("MAKTX"));
				p.put("MATKL", e.get("MATKL"));
				p.put("MSTAE", e.get("MSTAE"));
				p.put("UMDAT", "0");		
				if (e.get("PLPOD") != null) {
					buf = e.get("PLPOD").toString().split(",");

					if (buf.length > 1) {
						p.put("VGW01", buf[0]);
						p.put("VGW05", buf[1]);
					}
				}

				if (e.get("SORTL") == null) {
					if (e.get("KNMTD") != null) {
						buf = e.get("KNMTD").toString().split(",");

						if (buf.length > 1) {
							p.put("SORTL", buf[0]);
							p.put("KUNNR", buf[1]);
						}
					}
				} else {
					p.put("SORTL", e.get("SORTL"));
					p.put("KUNNR", e.get("KUNNR"));
				}

				list.add(p);
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
				} else {
					texts.put(aufnr, texts.get(aufnr).trim() + reader.get("TDLINE"));
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
