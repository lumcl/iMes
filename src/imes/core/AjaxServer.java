package imes.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ajax")
public class AjaxServer extends HttpController {

	// contentType: "application/x-www-form-urlencoded; charset=utf-8",
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		String output = "";

		if (getAction(req).equals("getUseridJson")) {

			output = getUseridJson(req.getParameter("term").toUpperCase().trim());

		} else if (getAction(req).equals("validateUserIds")) {

			output = validateUserIds(req.getParameter("userIds").toUpperCase());

		} else if (getAction(req).equals("getDeptJson")) {

			output = getDeptJson(req, req.getParameter("term").toUpperCase());
			// output =
			// "[{\"label\":\"博客园\",\"value\":\"cnblogs\"},{\"label\":\"囧月\",\"value\":\"囧月\"}]";
		} else if (getAction(req).equals("validateMatnr")) {

			output = validateMatnr(req);

		} else if (getAction(req).equals("getCustomerName")) {

			output = getCustomerName(req);

		} else if (getAction(req).equals("getSupplierName")) {

			output = getSupplierName(req);

		} else if (getAction(req).equals("getDeptName")) {

			output = getDeptName(req);
			
		} else if (getAction(req).equals("getMaterialInfo")) {

			output = getMaterialInfo(req);
			
		} else if (getAction(req).equals("setPrintStatus")) {

			setPrintStatus(req);
			
		} else if (getAction(req).equals("getZCName")) {

			output = getZCName(req);
			
		} else if (getAction(req).equals("getZCInfor")) {

			output = getZCInfor(req);
			
		} else if (getAction(req).equals("getD301_Supplier")) {

			output = getD301_Supplier(req);
			
		} 

		PrintWriter out = resp.getWriter();
		out.print(output);
		out.close();
	}

	private String getUseridJson(String term) {

		String json = "";

		Pattern pattern = Pattern.compile(".*" + term + ".*");

		Matcher matcher;

		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) getServletContext().getAttribute("userIds");

		for (String str : list) {

			matcher = pattern.matcher(str);

			if (matcher.matches()) {

				if (json.equals("")) {

					json = "[\"" + str + "\"";

				} else {

					json += ",\"" + str + "\"";
				}
			}
		}

		if (!json.equals("")) {

			json += "]";
		}

		return json;
	}

	private String validateUserIds(String userIds) {

		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) getServletContext().getAttribute("userIds");

		String msg = "";

		for (String str : userIds.split(",")) {
			if (!str.replaceAll(" ", "").equals("")) {

				if (!list.contains(str)) {

					msg = msg + str + ",";
				}
			}
		}
		return msg;
	}

	private String getDeptJson(HttpServletRequest req, String term) {

		String ktext = req.getParameter("term");

		try {
			ktext = URLDecoder.decode(req.getParameter("term"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String json = "";

		String sql = "SELECT * FROM QH_CSKT WHERE KTEXT LIKE '%" + ktext + "%' OR KOSTL LIKE '%" + term
				+ "%' AND SYSDATE BETWEEN YXFD AND YXTD AND GSDM=?";

		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("gsdm")));
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {

				if (json.equals("")) {

					json = "[{\"label\": \"" + rst.getString("KOSTL") + "-" + rst.getString("KTEXT") + "\", \"value\":\""
							+ rst.getString("KOSTL") + "\"}";

				} else {

					json += ",{\"label\": \"" + rst.getString("KOSTL") + "-" + rst.getString("KTEXT") + "\", \"value\":\""
							+ rst.getString("KOSTL") + "\"}";
				}
			}
			rst.close();
			pstm.close();
			conn.close();
			if (!json.equals("")) {

				json += "]";
			}

		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}

		return json;
	}

	private String validateMatnr(HttpServletRequest req) {
		String str = "";
		String sql = "SELECT MAKTX FROM SAPSR3.MAKT@SAPP WHERE MANDT='168' AND MATNR=? AND SPRAS='M'";
		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("matnr")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("MAKTX");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	private String getMaterialInfo(HttpServletRequest req){
		String str = "";
		String sql= "SELECT MARA.MATKL || ',' || MARA.MEINS || ',' || MAKT.MAKTX || ',' || MARA.ZEINR MARAD " //
				+ "  FROM SAPSR3.MARA, SAPSR3.MAKT " //
				+ " WHERE     MARA.MANDT = '168' " //
				+ "       AND MAKT.MANDT = MARA.MANDT " //
				+ "       AND MAKT.MATNR = MARA.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND MARA.MATNR = ? "; //
		try {
			Connection conn = getSapPrdConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("matnr")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("MARAD");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	private String getCustomerName(HttpServletRequest req) {
		String str = "";
		String sql = "SELECT SORTL FROM SAPSR3.KNA1@SAPP WHERE MANDT='168' AND KUNNR=?";
		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("KUNNR")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("SORTL");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	private String getSupplierName(HttpServletRequest req) {
		String str = "";
		String sql = "SELECT SORTL FROM SAPSR3.LFA1@SAPP WHERE MANDT='168' AND LIFNR=?";
		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("LIFNR")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("SORTL");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	private String getDeptName(HttpServletRequest req) {
		String str = "";
		String sql = "SELECT KTEXT FROM QH_CSKT WHERE KOSTL=? AND GSDM=?";
		try {
			
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("KOSTL")));
			pstm.setString(2, req.getParameter("GSDM"));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("KTEXT");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	private void setPrintStatus(HttpServletRequest req) {

		String sql = "UPDATE D400H SET DYZT = '1',DYYH = '"+getUid(req)+"' WHERE GSDM = ? AND BDDM = ? AND BDBH = ? ";
		//System.out.println("sql : "+sql);
		try {
			
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			//System.out.println("GSDM : "+Helper.toUpperCase(req.getParameter("GSDM")));
			pstm.setString(1, Helper.toUpperCase(req.getParameter("GSDM")));
			//System.out.println("BDDM : "+Helper.toUpperCase(req.getParameter("BDDM")));
			pstm.setString(2, Helper.toUpperCase(req.getParameter("BDDM")));
			//System.out.println("BDBH : "+Helper.toUpperCase(req.getParameter("BDBH")));
			pstm.setString(3, Helper.toUpperCase(req.getParameter("BDBH")));
			ResultSet rst = pstm.executeQuery();
			
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private String getZCName(HttpServletRequest req) {
		String str = "";
		String sql = "select TXT50 from SAPSR3.ANLA@SAPP where mandt = '168' and anln1 = ? and bukrs = ? ";
		try {
			//System.out.println(sql);
			//System.out.println(Helper.toUpperCase(req.getParameter("ZCBM")));
			//System.out.println(Helper.toUpperCase(req.getParameter("GSDM")));
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("ZCBM")));
			pstm.setString(2, Helper.toUpperCase(req.getParameter("GSDM")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("TXT50");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	private String getZCInfor(HttpServletRequest req) {
		String str = "";
		String sql = "SELECT ZCBH,ZCSM,COST,BGR,ZBDT,DEPT,ADDR,ZMJZ,QDJZ,JQZX,YLCZ,ZCY,YEAR,QTY,LJZJ,GSBM FROM ZC_INFORM WHERE ZCBH = ? AND GSBM = ?";
		try {
			System.out.println(sql);
			System.out.println(Helper.toUpperCase(req.getParameter("ZCBM")));
			System.out.println(Helper.toUpperCase(req.getParameter("GSDM")));
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("ZCBM")));
			pstm.setString(2, Helper.toUpperCase(req.getParameter("GSDM")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("ZCBH")+"@_@"+rst.getString("ZCSM")+"@_@"+rst.getString("COST")+"@_@"+rst.getString("BGR")+"@_@"+rst.getString("ZBDT")+"@_@"+rst.getString("DEPT")+"@_@"+rst.getString("ADDR")+"@_@"+rst.getString("ZMJZ")+"@_@"+rst.getString("QTY")+"@_@"+rst.getString("QDJZ")+"@_@"+rst.getString("GSBM");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	private String getD301_Supplier(HttpServletRequest req) {
		String str = "";
		String sql = "SELECT * FROM D301_SUPPLIER WHERE SUPPLIER = ? ";
		try {
			System.out.println(sql);
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("SUPPLIER")));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("SUPPLIER")+"@_@"+rst.getString("SUPPLIER_NAME")+"@_@"+rst.getString("CONTACT")+"@_@"+rst.getString("PHONE");
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
