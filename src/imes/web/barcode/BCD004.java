package imes.web.barcode;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import imes.core.Helper;
import imes.core.HttpController;
import imes.dao.BcdDao;
import imes.entity.barcode.BCLINK;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Reverse Link Child vs Parent

@WebServlet({ "/BCD004", "/BCD004/*" })
public class BCD004 extends HttpController {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/BCD004/enquiry")) {
			// create(req, resp);
		} else {
			render(req, resp, "/WEB-INF/pages/barcode/BCD004/new.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/BCD004/create")) {
			create(req, resp);
		}

	}

	private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String bcdnr = getParams("bcdnr");
		int bqty = toInteger(getParams("bqty"));

		if (bcdnr.isEmpty() == false)
			req.getSession().setAttribute("bcd004_bcdnr", bcdnr);

		if (bqty != 0)
			req.getSession().setAttribute("bcd004_bqty", bqty);

		String name, cbcdnr;
		BCLINK e;
		BcdDao dao = new BcdDao(req);

		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.startsWith("cb_") && req.getParameterValues(name)[0].isEmpty() == false) {
				for (int i = 1; i <= bqty; i++) {
					cbcdnr = req.getParameterValues(name)[0].toUpperCase();
					e = new BCLINK();
					e.setBcdnr(cbcdnr);
					e.setCbcdnr(bcdnr);
					e.setErdat(Helper.fmtDate(new Date(), "yyyyMMdd"));
					e.setErtim(Helper.fmtDate(new Date(), "HHmmss"));
					e.setErnam(getUid(req));
					e.setPosnr(Integer.toString(i));
					try {
						dao.save(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		String sql = "SELECT bclink.cbcdnr,bclink.bcdnr, pmast.matnr pmatnr, pmast.maktx pmaktx, cmast.bcdnr cbcdnr1, cmast.matnr cmatnr " //
				+ "       , cmast.maktx cmaktx, cmast.charg, bclink.ernam, bclink.erdat, bclink.ertim " //
				+ "       , bclink.posnr " //
				+ "  FROM bcmast cmast, bclink, bcmast pmast " //
				+ " WHERE     cmast.bcdnr(+) = bclink.cbcdnr " //
				+ "       AND pmast.bcdnr(+) = bclink.bcdnr " //
				+ "       AND bclink.cbcdnr = ? and bclink.posnr='1'" //
				+ " ORDER BY bclink.posnr";

		try {
			PreparedStatement pstm = dao.con.prepareStatement(sql);
			pstm.setString(1, bcdnr);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		dao.close();

		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/barcode/BCD004/list.jsp");
	}

}
