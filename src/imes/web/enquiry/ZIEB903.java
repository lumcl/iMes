package imes.web.enquiry;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/ZIEB903")
public class ZIEB903 extends HttpController {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/ZIEB903.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {
			ActionPost(req, resp);

		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String params = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "charg_low")
				+ getParam(req, "budat_low") + getParam(req, "mblnr_low") + getParam(req, "mjahr_low") + getParam(req, "matkl_low")
				+ getParam(req, "bwart_low") + getParam(req, "cpudt_low") + getParam(req, "matnr_textarea")
				+ getParam(req, "matkl_textarea");

		if (!params.equals("")) {
			String sql = "SELECT * FROM ZIEB903 WHERE 1=1  ";
			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "charg_low").equals("")) {
				sql += Helper.sqlParams(req, "CHARG", "charg_low", "charg_high");
			}
			if (!getParam(req, "budat_low").equals("")) {
				sql += Helper.sqlParams(req, "BUDAT", "budat_low", "budat_high");
			}
			if (!getParam(req, "mblnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MBLNR", "mblnr_low", "mblnr_high");
			}
			if (!getParam(req, "mjahr_low").equals("")) {
				sql += Helper.sqlParams(req, "MJAHR", "mjahr_low", "mjahr_high");
			}
			if (!getParam(req, "matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req, "bwart_low").equals("")) {
				sql += Helper.sqlParams(req, "BWART", "bwart_low", "bwart_high");
			}
			if (!getParam(req, "cpudt_low").equals("")) {
				sql += Helper.sqlParams(req, "CPUDT", "cpudt_low", "cpudt_high");
			}
			if (!getParam(req, "matnr_textarea").trim().equals("")) {
				sql += "         AND MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "matkl_textarea").trim().equals("")) {
				sql += "         AND MATKL IN (" + toSqlString(textAreaToList(req, "matkl_textarea")) + ") ";
			}

			sql += "ORDER BY CPUDT DESC, CPUTM DESC, WERKS, MATNR"; //

			try {
				Connection conn = getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				list = Helper.resultSetToArrayList(pstm.executeQuery());
				pstm.close();
				conn.close();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		req.setAttribute("list", list);

		render(req,resp,"/WEB-INF/pages/enquiry/ZIEB903.jsp");
	}

}
