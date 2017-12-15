package imes.web.barcode;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.dao.BcdDao;
import imes.entity.barcode.BCLINK;

@WebServlet({ "/BCD002", "/BCD002/*" })
public class BCD002 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/BCD001/enquiry")) {
			create(req, resp);
		} else {
			render(req, resp, "/WEB-INF/pages/barcode/BCD002/new.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/BCD002/create")) {
			create(req, resp);
		}

	}

	private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		BcdDao dao = new BcdDao(req);

		String bcdnr = getParams("bcdnr");
		/*
		List<String> cbcdnrs = textAreaToList(req, "cbcdnrs");

		BCLINK e;
		int i = 0;
		for (String cbcdnr : cbcdnrs) {
			// // yyyy-MM-dd HH:mm:ss
			i++;
			e = new BCLINK();
			e.setBcdnr(bcdnr);
			e.setCbcdnr(cbcdnr);
			e.setErdat(Helper.fmtDate(new Date(), "yyyyMMdd"));
			e.setErtim(Helper.fmtDate(new Date(), "HHmmss"));
			e.setErnam(getUid(req));
			e.setPosnr(Integer.toString(i));
			try {
			//	dao.save(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		*/
/*
		String sql = "SELECT bclink.bcdnr, pmast.matnr pmatnr, pmast.maktx pmaktx, cmast.bcdnr cbcdnr, cmast.matnr cmatnr " //
				+ "       , cmast.maktx cmaktx, cmast.charg, bclink.ernam, bclink.erdat, bclink.ertim " //
				+ "       , bclink.posnr " //
				+ "  FROM bcmast cmast, bclink, bcmast pmast " //
				+ " WHERE     cmast.bcdnr(+) = bclink.cbcdnr " //
				+ "       AND pmast.bcdnr(+) = bclink.bcdnr " //
				+ "       AND bclink.bcdnr = ? " //
		;
*/
		
		String sql = "SELECT * FROM BCLINK WHERE BCDNR = ? ORDER BY POSNR";
		
		try {
			PreparedStatement pstm = dao.con.prepareStatement(sql);
			pstm.setString(1, bcdnr);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		dao.close();
		
		req.setAttribute("list", list);
		
		render(req, resp, "/WEB-INF/pages/barcode/BCD002/list.jsp");
	}
}
