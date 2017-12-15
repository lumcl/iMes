package imes.web.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.entity.QH_BDLC;

@WebServlet("/qh")
public class QHServlet extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		try {
			if (getAction(req).equals("Find")) {

				actPostFind(req, resp);

			}

			else {

				// List Screen
				// actList(req, resp);
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

		super.doPost(req, resp);
		try {

			if (getAction(req).equals("Find")) {

				actPostFind(req, resp);

			} else if (getAction(req).equals("Escalate")) {

				actPostEscalate(req, resp);

			}

		} catch (Exception ex) {
			PrintWriter out = resp.getWriter();
			ex.printStackTrace(out);
			out.close();
		}
	}
	
	
	private void actPostEscalate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String QHNR = "**"+getUid(req)+"申請轉簽到上一級主管; 原因:"+req.getParameter("QHNR");
		
		Connection conn = getConnection();
		
		String sql = "UPDATE QH_BDLC SET YXSJ = SYSDATE, QHNR = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND BZDM = ? AND QHZT = '2'";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, QHNR);
		pstm.setString(2, req.getParameter("GSDM"));
		pstm.setString(3, req.getParameter("BDDM"));
		pstm.setString(4, req.getParameter("BDBH"));
		pstm.setInt(5, Integer.parseInt(req.getParameter("BZDM")));
		
		pstm.executeUpdate();
		
		resp.sendRedirect("/iMes/qh?action=Find&STRQHZT=2&STRSQYH="+getUid(req));
		
	}
	

	private void actPostFind(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<QH_BDLC> list = new ArrayList<QH_BDLC>();

		QH_BDLC e;

		Connection conn = getConnection();

		String sql = "  SELECT T1.*, T2.BDTX FROM QH_BDLC T1, QH_BDTX T2 " //
				+ "   WHERE T1.GSDM=T2.GSDM AND T1.BDDM=T2.BDDM AND T1.BDBH=T2.BDBH "; //
		sql += Helper.sqlParams(req, "T1.SQYH", "STRSQYH", "ENDSQYH");
		sql += Helper.sqlParams(req, "T1.QHZT", "STRQHZT", "ENDQHZT");
		sql += Helper.sqlParams(req, "T1.GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "T1.BDDM", "STRBDDM", "ENDBDDM");
		sql += Helper.sqlParams(req, "T1.BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlParams(req, "T1.YSYH", "STRYSYH", "ENDYSYH");
		sql += Helper.sqlDateParams(req, "T1.JLSJ", "STRJLSJ", "ENDJLSJ");
		sql += " ORDER BY T1.GSDM,T1.BDDM,T1.BDBH,T1.BZDM";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rst = pstm.executeQuery();

		while (rst.next()) {

			e = new QH_BDLC();

			e.setGSDM(rst.getString("GSDM"));
			e.setBDDM(rst.getString("BDDM"));
			e.setBDBH(rst.getString("BDBH"));
			e.setLXDM(rst.getString("LXDM"));
			e.setQHZT(rst.getString("QHZT"));
			e.setBZDM(rst.getInt("BZDM"));
			e.setLCLX(rst.getString("LCLX"));
			e.setMINQ(rst.getDouble("MINQ"));
			e.setMAXQ(rst.getDouble("MAXQ"));
			e.setMINA(rst.getDouble("MINA"));
			e.setMAXA(rst.getDouble("MAXA"));
			e.setMINP(rst.getDouble("MINP"));
			e.setMAXP(rst.getDouble("MAXP"));
			e.setCURR(rst.getString("CURR"));
			e.setFTXT(rst.getString("FTXT"));
			e.setTTXT(rst.getString("TTXT"));
			e.setZWDM(rst.getString("ZWDM"));
			e.setQHLX(rst.getString("QHLX"));
			e.setYXXS(rst.getDouble("YXXS"));
			e.setZFYN(rst.getString("ZFYN"));
			e.setSQYH(rst.getString("SQYH"));
			e.setJLSJ(rst.getTimestamp("JLSJ"));
			e.setYJSJ(rst.getTimestamp("YJSJ"));
			e.setYXSJ(rst.getTimestamp("YXSJ"));
			e.setYSYH(rst.getString("YSYH"));
			e.setDLYH(rst.getString("DLYH"));
			e.setQHYH(rst.getString("QHYH"));
			e.setQHSJ(rst.getTimestamp("QHSJ"));
			e.setQHJG(rst.getString("QHJG"));
			e.setQHNR(rst.getString("QHNR"));
			e.setQHFD(rst.getString("QHFD"));
			e.setBDTX(rst.getString("BDTX"));

			list.add(e);
		}

		rst.close();

		pstm.close();

		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/QHFind.jsp").forward(req, resp);

	}

}
