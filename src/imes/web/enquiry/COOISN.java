package imes.web.enquiry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/COOISN")
public class COOISN extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		try {
			if (getAction(req).equals("A")) {

				ActionGetA(req, resp);

			}
			if (getAction(req).equals("AP62")) {

			} else if (getAction(req).equals("XXXX")) {

			} else {
				ActionGetA(req, resp);
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
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {
			if (getAction(req).equals("A")) {

				ActionPostA(req, resp);
			}
			if (getAction(req).equals("AP62")) {

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

	private void ActionGetA(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> options = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT NAME1 FROM TCODE_PARAMS WHERE TCODE='COOISA' ORDER BY NAME1";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		options = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();

		String tcode_params = "";
		sql = "SELECT PARAMS FROM USER_TCODE WHERE TCODE='COOISA' AND USERID = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, getUid(req));

		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			tcode_params = rst.getString("PARAMS");
		}
		rst.close();
		pstm.close();
		conn.close();

		req.setAttribute("options", options);

		render(req,resp,"/WEB-INF/pages/enquiry/COOISN.jsp" + tcode_params);
		return;
	}

	private void ActionPostA(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> options = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT NAME1 FROM TCODE_PARAMS WHERE TCODE='COOISA' ORDER BY NAME1";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		options = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();

		req.setAttribute("options", options);

		String PKEY1 = "", PKEY2 = "", PVAL1 = "", PVAL2 = "", tcodeParamsSql = "";

		if (!req.getParameter("tcode_params").equals("")) {

			sql = "SELECT * FROM TCODE_PARAMS WHERE TCODE='COOISA' AND NAME1=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, req.getParameter("tcode_params"));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				PKEY1 = Helper.toUpperCase(rst.getString("PKEY1"));
				PVAL1 = Helper.toUpperCase(rst.getString("PVAL1"));
				PKEY2 = Helper.toUpperCase(rst.getString("PKEY2"));
				PVAL2 = Helper.toUpperCase(rst.getString("PVAL2"));
			}
			rst.close();
			pstm.close();

			String[] values;
			boolean first;
			if (!PVAL1.equals("")) {
				tcodeParamsSql += " AND (";
				values = PVAL1.split(",");
				first = true;
				for (String v : values) {
					if (first) {
						tcodeParamsSql += " (" + PKEY1 + v + ")";
						first = false;
					} else {
						tcodeParamsSql += " OR (" + PKEY1 + v + ")";
					}
				}
				tcodeParamsSql += ") ";
			}

			if (!PVAL2.equals("")) {
				tcodeParamsSql += " AND (";
				values = PVAL2.split(",");
				first = true;
				for (String v : values) {
					if (first) {
						tcodeParamsSql += " (" + PKEY2 + v + ")";
						first = false;
					} else {
						tcodeParamsSql += " AND (" + PKEY2 + v + ")";
					}
				}
				tcodeParamsSql += ") ";
			}

		}

		conn.close();

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		sql = "SELECT afko.aufnr, " //
				+ "       afko.stlbez, " //
				+ "       resb.werks, " //
				+ "       resb.matnr, " //
				+ "       bdmng, " //
				+ "       vmeng, " //
				+ "       enmng, " //
				+ "       mara.meins, " //
				+ "       makt.maktx, " //
				+ "       mara.matkl, " //
				+ "       (SELECT SUM (labst) || ',' || SUM (insme) " //
				+ "          FROM sapsr3.mard " //
				+ "         WHERE     mandt = resb.mandt " //
				+ "               AND matnr = resb.matnr " //
				+ "               AND werks = resb.werks) " //
				+ "          mardd, " //
				+ "       resb.vornr, " //
				+ "       resb.posnr, " //
				+ "       crhd.arbpl, " //
				+ "       afvc.ltxa1, " //
				+ "       aufm.charg, " //
				+ "       aufm.menge " //
				+ "  FROM sapsr3.resb, " //
				+ "       sapsr3.mara, " //
				+ "       sapsr3.makt, " //
				+ "       sapsr3.afko, " //
				+ "       sapsr3.afvc, " //
				+ "       sapsr3.crhd, " //
				+ "       sapsr3.aufm " //
				+ " WHERE     resb.mandt = afko.mandt " //
				+ "       AND resb.rsnum = afko.rsnum " //
				+ "       AND resb.bdmng <> 0 " //
				+ "       and aufm.mandt = resb.mandt " //
				+ "       and aufm.aufnr = resb.aufnr " //
				+ "       and aufm.rsnum = resb.rsnum " //
				+ "       and aufm.rspos = resb.rspos " //
				+ "       AND mara.mandt = resb.mandt " //
				+ "       AND mara.matnr = resb.matnr " //
				+ "       AND makt.mandt = resb.mandt " //
				+ "       AND makt.matnr = resb.matnr " //
				+ "       AND makt.spras = 'M' " //
				+ "       AND afvc.mandt(+) = resb.mandt " //
				+ "       AND afvc.aufpl(+) = resb.aufpl " //
				+ "       AND afvc.vornr(+) = resb.vornr " //
				+ "       AND crhd.mandt(+) = afvc.mandt " //
				+ "       AND crhd.objty(+) = 'A' " //
				+ "       AND crhd.objid(+) = afvc.arbid " //
				+ "       AND afko.mandt = '168' " //
				;
		sql += Helper.sqlParamsFormat(req, "AFKO.AUFNR", "aufnr_low", "aufnr_high", "%012d");
		sql += Helper.sqlParams(req, "MARA.MATKL", "matkl_low", "matkl_high");

		if (!req.getParameter("aufnr_textarea").trim().equals("")) {
			sql += "         AND AFKO.AUFNR IN (" + toSqlString(textAreaToList(req,"aufnr_textarea", "%012d")) + ") ";
		}

		if (!req.getParameter("matkl_textarea").trim().equals("")) {
			sql += "         AND MARA.MATKL IN (" + toSqlString(textAreaToList(req,"matkl_textarea")) + ") ";
		}

		if (req.getParameter("xloek") == null){
			sql += 	 "       AND RESB.XLOEK = ' ' "; //
		}
		
		sql += tcodeParamsSql;

		sql += " ORDER BY AFKO.AUFNR,MARA.MATKL,MAKT.MAKTX, AUFM.CHARG";

		try {

			conn = getSapPrdConnection();
			pstm = conn.prepareStatement(sql);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			conn.close();
			pstm.close();

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

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		req.setAttribute("list", list);

		render(req,resp,"/WEB-INF/pages/enquiry/COOISN.jsp");
		return;
	}
}
