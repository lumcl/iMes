package imes.web.barcode;

import java.io.IOException;
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

@WebServlet({ "/BCD001", "/BCD001/*" })
public class BCD001 extends HttpController{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;
		
		if (req.getRequestURI().equals("/iMes/BCD001/enquiry")) {
			enquiry(req, resp);
		}else {
			render(req, resp, "/WEB-INF/pages/barcode/BCD001/list.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;
		
		if (req.getRequestURI().equals("/iMes/BCD001/enquiry")) {
			enquiry(req, resp);
		}
		
	}

	
	private void enquiry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String bcdnr = getParams("bcdnr");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> insp = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> link = new ArrayList<HashMap<String, Object>>();
		
		String sql = "select * from bcmast where bcdnr = ?";
		try {
			Connection con = getConnection();
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, bcdnr);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			
			sql = "select * from bcinsp where bcdnr = ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, bcdnr);
			insp = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			
			sql = "SELECT bclink.bcdnr, pmast.matnr pmatnr, pmast.maktx pmaktx, cmast.bcdnr cbcdnr, cmast.matnr cmatnr " //
					+ "       , cmast.maktx cmaktx, cmast.charg, bclink.ernam, bclink.erdat, bclink.ertim " //
					+ "       , bclink.posnr " //
					+ "  FROM bcmast cmast, bclink, bcmast pmast " //
					+ " WHERE     cmast.bcdnr(+) = bclink.cbcdnr " //
					+ "       AND pmast.bcdnr(+) = bclink.bcdnr " //
					+ "       AND bclink.bcdnr = ? " //
			;
			
			pstm = con.prepareStatement(sql);
			pstm.setString(1, bcdnr);
			link = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		req.setAttribute("list", list);
		req.setAttribute("insp", insp);
		req.setAttribute("link", link);
		render(req, resp, "/WEB-INF/pages/barcode/BCD001/list.jsp");
	}
}
