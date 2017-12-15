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

@WebServlet("/CO03")
public class CO03 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req,resp,"/WEB-INF/pages/enquiry/CO03.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {
			ActionPost(req, resp);
		} catch (Exception e) {
			PrintError(resp,e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> afpo = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> resb = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> afvc = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT AFPO.AUFNR, AFPO.PWERK, AFPO.MATNR, MAKT.MAKTX, AFPO.PSMNG, " //
				+ "       AFPO.WEMNG, AFKO.GLTRP, AFKO.GLUZP, AFKO.GLTRS, AFKO.GLUZS, " //
				+ "       AFKO.GLTRI, AFKO.GSTRP, AFKO.GSUZP, AFKO.GSTRS, AFKO.GSUZS, " //
				+ "       AFKO.GSTRI, AFKO.GSUZI, AFKO.FTRMS, AFKO.FTRMI, AFKO.RSNUM, " //
				+ "       AFKO.DISPO, AFPO.MEINS, AFKO.AUFPL " //
				+ "  FROM SAPSR3.AFPO, SAPSR3.AFKO, SAPSR3.MAKT " //
				+ " WHERE     AFKO.MANDT = AFPO.MANDT " //
				+ "       AND AFKO.AUFNR = AFPO.AUFNR " //
				+ "       AND MAKT.MANDT(+) = AFPO.MANDT " //
				+ "       AND MAKT.MATNR(+) = AFPO.MATNR " //
				+ "       AND MAKT.SPRAS(+) = 'M' " //
				+ "       AND AFPO.MANDT = '168' " //
				+ "       AND AFPO.AUFNR = ?  ";//

		try {
			Connection conn = getSapPrdConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, getParam(req,"aufnr_low", "%012d"));
			afpo = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();

			String rsnum = "";
			String aufpl = "";
			for (HashMap<String, Object> h : afpo) {
				rsnum = h.get("RSNUM").toString();
				aufpl = h.get("AUFPL").toString();
			}

			sql = "  SELECT RESB.VORNR, RESB.POSNR, RESB.MATNR, MAKT.MAKTX, RESB.MATKL, " //
					+ "         RESB.WERKS, RESB.LGORT, RESB.BDMNG, RESB.VMENG, RESB.ENMNG, " //
					+ "         RESB.MEINS, RESB.BDTER, RESB.POTX1, RESB.BAUGR, RESB.AENNR, " //
					+ "         MARA.ZEINR, RESB.DUMPS, RESB.KZEAR, RESB.XLOEK, RESB.XFEHL, " //
					+ "         RESB.ALPOS, RESB.EWAHR, RESB.AUSCH, RESB.ESMNG " //
					+ "    FROM SAPSR3.RESB, SAPSR3.MAKT, SAPSR3.MARA " //
					+ "   WHERE     RESB.MANDT = '168' " //
					+ "         AND RESB.RSNUM = ? " //
					+ "         AND MAKT.MANDT(+) = RESB.MANDT " //
					+ "         AND MAKT.MATNR(+) = RESB.MATNR " //
					+ "         AND MAKT.SPRAS(+) = 'M' " //
					+ "         AND MARA.MANDT(+) = RESB.MANDT " //
					+ "         AND MARA.MATNR(+) = RESB.MATNR " //
					+ "ORDER BY RESB.VORNR, RESB.POSNR, RESB.ALPOS DESC, RESB.EWAHR DESC "; //

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, rsnum);
			resb = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();

			for (HashMap<String, Object> h : resb) {
				if (h.get("XFEHL").equals("X")){
					h.put("COLOR", "bgcolor='#ffff99'");
				}else if (h.get("DUMPS").equals("X")){
					h.put("COLOR", "bgcolor='#eaeaea'");
				}else if (h.get("VORNR").toString().startsWith("9") || h.get("POSNR").toString().startsWith("Z")){
					h.put("COLOR", "bgcolor='#ccff99'");
				}
			}
			
			sql= "  SELECT AFVC.VORNR, " //
					+ "         CRHD.ARBPL, " //
					+ "         AFVC.LTXA1, " //
					+ "         AFVV.SSAVD, " //
					+ "         AFVV.SSAVZ, " //
					+ "         AFVV.BEARZ, " //
					+ "         AFVV.BEAZE, " //
					+ "         AFVV.MGVRG, " //
					+ "         AFVV.LMNGA, " //
					+ "         AFVV.MEINH, " //
					+ "         (AFVV.VGW01) TVGW01, " //
					+ "         AFVV.BMSCH, " //
					+ "         (AFVV.VGW01 * AFVV.MGVRG / AFVV.BMSCH) VGW01, " //
					+ "         AFVV.VGE01, " //
					+ "         (AFVV.VGW05 * AFVV.MGVRG / AFVV.BMSCH) VGW05, " //
					+ "         AFVV.VGE05, " //
					+ "         AFVV.ISAVD, " //
					+ "         AFVV.IEAVD, " //
					+ "         AFVC.STEUS, " //
					+ "         AFVC.RUECK, " //
					+ "         (SELECT SUM (CASE WHEN STZHL = '00000000' THEN ISM01 ELSE ISM01 * -1 END) || ',' || SUM (CASE WHEN STZHL = '00000000' THEN ISM02 ELSE ISM02 * -1 END) || ',' || SUM (CASE WHEN STZHL = '00000000' THEN ISM03 ELSE ISM03 * -1 END) || ',' || SUM (CASE WHEN STZHL = '00000000' THEN ISM04 ELSE ISM04 * -1 END) || ',' || SUM (CASE WHEN STZHL = '00000000' THEN ISM05 ELSE ISM05 * -1 END) " //
					+ "            FROM SAPSR3.AFRU " //
					+ "           WHERE MANDT = '168' AND RUECK = AFVC.RUECK) " //
					+ "            AFRUD " //
					+ "    FROM SAPSR3.AFVC, SAPSR3.AFVV, SAPSR3.CRHD " //
					+ "   WHERE     AFVV.MANDT = AFVC.MANDT " //
					+ "         AND AFVV.AUFPL = AFVC.AUFPL " //
					+ "         AND AFVV.APLZL = AFVC.APLZL " //
					+ "         AND CRHD.MANDT(+) = AFVC.MANDT " //
					+ "         AND CRHD.OBJTY(+) = 'A' " //
					+ "         AND CRHD.OBJID(+) = AFVC.ARBID " //
					+ "         AND AFVC.MANDT = '168' " //
					+ "         AND AFVC.AUFPL = ? " //
					+ "ORDER BY VORNR "; //
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, aufpl);
			afvc = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
			String[] buf;
			double labhr;
			for (HashMap<String, Object> row : afvc) {
				if (row.get("AFRUD") != null) {
					buf = row.get("AFRUD").toString().split(",");
					if (buf.length > 1) {
						row.put("ISM01", buf[0]);
						row.put("ISM02", buf[1]);
						row.put("ISM03", buf[2]);
						row.put("ISM04", buf[3]);
						row.put("ISM05", buf[4]);
						
						labhr = Double.parseDouble(buf[0]) + Double.parseDouble(buf[1]) + Double.parseDouble(buf[2]) + Double.parseDouble(buf[3]);
						
						row.put("LABHR", labhr);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.setAttribute("afpo", afpo);
		req.setAttribute("resb", resb);
		req.setAttribute("afvc", afvc);

		render(req,resp,"/WEB-INF/pages/enquiry/CO03.jsp");

	}
}
