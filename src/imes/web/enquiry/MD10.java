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

@WebServlet("/MD10")
public class MD10 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.req = req;
		super.resp = resp;
		render(req, resp, "/WEB-INF/pages/enquiry/MD10.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.req = req;
		super.resp = resp;
		doActionPost(req, resp);
	}

	private void doActionPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String params = getParam(req, "strwerks") + getParam(req, "strmatnr");

		if (!params.equals("")) {
			String sql = "  SELECT cmatkl, cwerks, cmatnr, cmaktx, marc.plifz ldtim, " //
					+ "         marc.bstrf moq, duom, mrp.poqty, mrp.prqty, mrp.plnord, " //
					+ "         whsbal, supply, reqmnt, (whsbal + supply - reqmnt) mrpbal, bomqty " //
					+ "    FROM sapsr3.marc@sapp, " //
					+ "         (  SELECT cmatkl, cwerks, cmatnr, cmaktx, duom, " //
					+ "                   SUM (sbomxtb.dusage * (1 + (sbomxtb.ckausf * 0.01))) bomqty " //
					+ "              FROM it.sbomxtb " //
					+ "             WHERE     werks = ? " //
					+ "                   AND pmatnr = ? " //
					+ "                   AND cmtart = 'ZROH' " //
					+ "                   AND ( (alpos = 'X' AND ewahr = 100) OR alpos = ' ') " //
					+ "          GROUP BY cwerks, cmatnr, cmaktx, cmatkl, duom) bom, " //
					+ "         (  SELECT werks, matnr, SUM (DECODE (plumi, 'B', mng01, 0)) whsbal, SUM ( DECODE (plumi, '+', mng01, 0)) supply, SUM ( DECODE (plumi, '-', mng01, 0)) reqmnt " //
					+ "                   , SUM (DECODE (delkz, 'BA', mng01, 0)) prqty, SUM ( DECODE (delkz, 'BE', mng01, 0)) poqty, SUM ( DECODE (delkz, 'PA', mng01, 0)) plnord " //
					+ "              FROM it.wmd04s " //
					+ "          GROUP BY werks, matnr) mrp " //
					+ "   WHERE     mrp.werks(+) = bom.cwerks " //
					+ "         AND mrp.matnr(+) = bom.cmatnr " //
					+ "         AND marc.mandt(+) = '168' " //
					+ "         AND marc.matnr(+) = bom.cmatnr " //
					+ "         AND marc.werks(+) = bom.cwerks " //
					+ "ORDER BY cmatkl, cmaktx, cmatnr " //
			;
			
			try {
				Connection conn = getSapCoConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, getParam(req, "strwerks"));
				pstm.setString(2, getParam(req, "strmatnr"));
				list = Helper.resultSetToArrayList(pstm.executeQuery());
				pstm.close();
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}

			double bomqty;
			double reqqty;
			double mrpbal;
			double atpqty;
			for (HashMap<String, Object> h : list) {
				bomqty = Double.parseDouble(h.get("BOMQTY").toString());
				mrpbal = Double.parseDouble(h.get("MRPBAL").toString());
				reqqty = bomqty * Double.parseDouble(getParam(req, "qty"));
				atpqty = mrpbal - reqqty;
				h.put("REQQTY", reqqty);
				h.put("ATPQTY", atpqty);
			}
		}
		
		req.setAttribute("list", list);
		render(req, resp, "/WEB-INF/pages/enquiry/MD10.jsp");
	}
	
}
