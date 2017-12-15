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

@WebServlet("/PP06")
public class PP06 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/PP06.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {

			ActionPost(req, resp);
			render(req, resp, "/WEB-INF/pages/enquiry/PP06.jsp");

		} catch (Exception e) {

			PrintError(resp, e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String jspParams = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "aufnr_low")
				+ getParam(req, "matnr_textarea") + getParam(req, "aufnr_textarea");

		if (jspParams.isEmpty()) {
			return;
		}

		String sql = "SELECT AFPO.AUFNR, " //
				+ "       AUFK.WERKS, " //
				+ "       AFPO.MATNR, " //
				+ "       AFPO.PSMNG, " //
				+ "       AFPO.WEMNG, " //
				+ "       AFKO.GLTRP, " //
				+ "       MARC.FEVOR, " //
				+ "       MARA.MATKL, " //
				+ "       MARA.MEINS, " //
				+ "       MAKT.MAKTX, " //
				+ "       NVL((SELECT RPAD(VBAK.VKORG,4,' ') || RPAD(VBAK.VTWEG,2,' ')|| RPAD(VBAK.KUNNR,10,' ')||VBBE.MATNR || ',' || KNA1.SORTL || ',' || VBAK.KUNNR" //
				+ "          FROM SAPSR3.VBBE, SAPSR3.VBAK, SAPSR3.KNA1 " //
				+ "         WHERE     VBBE.MANDT = '168' " //
				+ "               AND VBBE.MANDT = VBAK.MANDT " //
				+ "               AND VBBE.VBELN = VBAK.VBELN " //
				+ "               AND VBBE.MATNR = AFPO.MATNR " //
				+ "               AND VBAK.VTWEG = CASE WHEN AUFK.WERKS IN ('481A', '482A') THEN 'DT' ELSE 'TX' END " //
				+ "               AND VBBE.WERKS = DECODE (AUFK.WERKS,  '281A', '281A',  '482A', '482A',  VBBE.WERKS) " //
				+ "               AND KNA1.MANDT = VBAK.MANDT " //
				+ "               AND KNA1.KUNNR = VBAK.KUNNR " //
				+ "               AND ROWNUM = 1), " //
				+ "               (SELECT RPAD(KNMT.VKORG,4,' ') || RPAD(KNMT.VTWEG,2,' ')|| RPAD(KNMT.KUNNR,10,' ')||KNMT.MATNR || ',' || KNA1.SORTL || ',' || KNMT.KUNNR" //
				+ "          FROM SAPSR3.KNMT,  SAPSR3.KNA1 " //
				+ "         WHERE     KNMT.MANDT = '168' " //
				+ "               AND KNMT.MATNR = AFPO.MATNR " //
				+ "               AND KNMT.VTWEG = CASE WHEN AUFK.WERKS IN ('481A', '482A') THEN 'DT' ELSE 'TX' END " //
				+ "               AND KNA1.MANDT = KNMT.MANDT " //
				+ "               AND KNA1.KUNNR = KNMT.KUNNR " //
				+ "               AND ROWNUM = 1) " //
				+ "               ) " //
				+ "          VBBED " //
				+ "  FROM SAPSR3.AFPO, SAPSR3.AUFK, SAPSR3.AFKO, SAPSR3.MARC,SAPSR3.MARA,SAPSR3.MAKT " //
				+ " WHERE     AFKO.MANDT = AFPO.MANDT " //
				+ "       AND AFKO.AUFNR = AFPO.AUFNR " //
				+ "       AND MARC.MANDT = AFPO.MANDT " //
				+ "       AND MARC.MATNR = AFPO.MATNR " //
				+ "       AND MARC.WERKS = AUFK.WERKS " //
				+ "       AND MARA.MANDT = AFPO.MANDT " //
				+ "       AND MARA.MATNR = AFPO.MATNR " //
				+ "       AND MAKT.MANDT = AFPO.MANDT " //
				+ "       AND MAKT.MATNR = AFPO.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND AFPO.MANDT = '168' " //
				+ "       AND AFPO.ELIKZ = ' ' " //
				+ "       AND AFPO.DNREL = ' ' " //
				+ "       AND AFPO.DAUTY = '10' " //
				+ "       AND AUFK.MANDT = AFPO.MANDT " //
				+ "       AND AUFK.AUFNR = AFPO.AUFNR " //
				+ "       AND AUFK.LOEKZ <> 'X' " //
				+ "       AND AUFK.AUART <> 'ZP06' ";//

		if (!getParam(req, "werks_low").equals("")) {
			sql += Helper.sqlParams(req, "AUFK.WERKS", "werks_low", "werks_high");
		}
		if (!getParam(req, "matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "AFPO.MATNR", "matnr_low", "matnr_high");
		}
		if (!getParam(req, "aufnr_low").equals("")) {
			sql += Helper.sqlParamsFormat(req, "AUFK.AUFNR", "aufnr_low", "aufnr_high", "%012d");
		}
		if (!getParam(req, "matnr_textarea").equals("")) {
			sql += "         AND AFPO.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
		}
		if (!getParam(req, "aufnr_textarea").equals("")) {
			sql += "         AND AUFK.AUFNR IN (" + toSqlString(textAreaToList(req, "aufnr_textarea", "%012d")) + ") ";
		}
		sql += "ORDER BY AUFK.AUFNR"; //

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		HashMap<String, HashMap<String, String>> shippingMarks = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> map;

		String[] buf;
		for (HashMap<String, Object> e : list) {
			if (e.get("VBBED") != null) {

				buf = e.get("VBBED").toString().split(",");
				if (buf.length > 2) {
					map = new HashMap<String, String>();
					map.put("Z001", "");
					map.put("Z002", "");
					shippingMarks.put(buf[0].trim(), map);
					e.put("KNMT", buf[0].trim());
					e.put("SORTL", buf[1]);
					e.put("KUNNR", buf[2]);
				}
			}
		}

		GetShippingMark(shippingMarks);

		String key;
		for (HashMap<String, Object> e : list) {

			if (e.get("KNMT") != null) {

				key = e.get("KNMT").toString();

				if (shippingMarks.containsKey(e.get("KNMT").toString())) {
					map = shippingMarks.get(key);
					e.put("Z001", map.get("Z001"));
					e.put("Z002", map.get("Z002"));
				}
			}
		}

		req.setAttribute("list", list);

	}

	private void GetShippingMark(HashMap<String, HashMap<String, String>> shippingMarks) {

		try {

			SapRfcConnection rfcConn = new SapRfcConnection();

			JCoFunction function = rfcConn.getFunction("RFC_READ_TEXT");

			JCoTable text_lines = function.getTableParameterList().getTable("TEXT_LINES");

			for (String key : shippingMarks.keySet()) {
				text_lines.appendRow();
				text_lines.setValue("MANDT", "168");
				text_lines.setValue("TDOBJECT", "KNMT");
				text_lines.setValue("TDNAME", key);
				text_lines.setValue("TDID", "Z001");
				text_lines.setValue("TDSPRAS", "M");
				text_lines.appendRow();
				text_lines.setValue("MANDT", "168");
				text_lines.setValue("TDOBJECT", "KNMT");
				text_lines.setValue("TDNAME", key);
				text_lines.setValue("TDID", "Z002");
				text_lines.setValue("TDSPRAS", "M");
			}

			rfcConn.execute(function);

			JCoTable table = function.getTableParameterList().getTable("TEXT_LINES");
			TableAdapterReader reader = new TableAdapterReader(table);

			String key;
			HashMap<String, String> map;

			for (int i = 0; i < reader.size(); i++) {

				key = reader.get("TDNAME").trim();
				map = shippingMarks.get(key);

				if (reader.get("TDID").equals("Z001")) {
					map.put("Z001", map.get("Z001") + reader.get("TDLINE") + "<br/>");
				}

				if (reader.get("TDID").equals("Z002")) {
					map.put("Z002", map.get("Z002") + reader.get("TDLINE") + "<br/>");
				}

				shippingMarks.put(key, map);

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

	}
}
