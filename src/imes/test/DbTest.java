package imes.test;

import imes.core.HttpController;
import imes.core.QianHe;
import imes.entity.QH_BDLC;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.ProcessEngines;

//@WebServlet("/DbTest")
public class DbTest extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		String sql = "  SELECT GSDM, BDDM, BDBH, " //
				+ "         BDRQ, BDZT, YYLB, " //
				+ "         YYSM, QHYH, QHSJ, " //
				+ "         YXSJ, JLYH, JLSJ, " //
				+ "         GXYH, GXSJ " //
				+ "    FROM IMES.D031H " //
				//+ "   WHERE TO_CHAR (JLSJ, 'YYYYMMDD') = '20120313' " //
				+ "ORDER BY BDBH "; //

		String dSql = "DELETE FROM IMES.QH_BDLC WHERE GSDM = ? AND BDDM = ? AND BDBH = ?";
				
		HttpSession session = req.getSession();

		out.println("Session id:" + session.getId());
		DataSource ds = getDataSource();
		List<QH_BDLC> routes = new ArrayList<QH_BDLC>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			PreparedStatement dpstm = conn.prepareStatement(dSql);
			
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {
				
				dpstm.setString(1, rst.getString("GSDM"));
				dpstm.setString(2, rst.getString("BDDM"));
				dpstm.setString(3, rst.getString("BDBH"));
				
				dpstm.executeUpdate();
				
				QianHe qh = new QianHe();
				qh.setGSDM(rst.getString("GSDM"));
				qh.setBDDM(rst.getString("BDDM"));
				qh.setBDBH(rst.getString("BDBH"));
				qh.setMgr("LEONNA.RAO");
				qh.setSQYH(rst.getString("JLYH"));
				routes = qh.crtRoute(conn, "Q", "0");
				
				for (QH_BDLC r : routes) {
					r.insertDb(conn);
				}
			}

			rst.close();
			pstm.close();
			dpstm.close();
			conn.close();
			
			out.println("Complete");
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ProcessEngine p = ProcessEngines.getDefaultProcessEngine();
		// System.out.println(p.VERSION);
		// System.out.println(p.toString());

	}

}
