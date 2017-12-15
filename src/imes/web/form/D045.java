package imes.web.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.IRequest;
import imes.core.QianHe;
import imes.dao.D045Dao;
import imes.entity.D045L;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;
import imes.vo.D045LVO;
import imes.vo.D045VO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/D045", "/D045/*"})
public class D045 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;		

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");
		
		try {

			if (action.equals("LIST")) {
				
				doListGetAction(req, resp);			
	
			} else if (action.equals("CREATE")) {
	
				doCreateGetAction(req, resp);
	
			} else if (action.equals("READ")) {
	
				//doReadGetAction(req, resp);
	
			} else if (action.equals("EDIT")) {
	
				doEditGetAction(req, resp);
	
			} else if (action.equals("DELETE")) {
	
				//doDeleteGetAction(req, resp);
	
			} else if (action.equals("SAVE")) {
	
				doSaveGetAction(req, resp);
	
			} else if (action.equals("UPDATE")) {
	
				doUpdateGetAction(req, resp);
	
			} else if (action.equals("SubmitQH")) {

				actionSubmitQH(req, resp);

			} else {

				getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D045.jsp").forward(req, resp);
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
		doGet(req,resp);
	}

	void actionPostInsertWipComp(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		int SQNR = 0;

		String sql = "SELECT NVL(MAX(SQNR),0) SQNR FROM D045L WHERE GSDM=? AND BDDM=? AND BDBH=?";

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));

		ResultSet rst = pstm.executeQuery();

		if (rst.next()) {
			SQNR = rst.getInt("SQNR");
		}
		rst.close();
		pstm.close();

		sql = "SELECT MARA.MATKL, MARA.MEINS, MAKT.MAKTX, CASE WHEN VPRSV = 'V' THEN VERPR / PEINH ELSE STPRS / PEINH END NETPR,EKGRP " //
				+ "  FROM SAPSR3.MARA@SAPP, " //
				+ "       SAPSR3.MAKT@SAPP, " //
				+ "       SAPSR3.MBEW@SAPP, " //
				+ "       SAPSR3.MARC@SAPP " //
				+ " WHERE     MARA.MANDT = '168' " //
				+ "       AND MARA.MATNR = ? " //
				+ "       AND MAKT.MANDT = MARA.MANDT " //
				+ "       AND MAKT.MATNR = MARA.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND MBEW.MANDT = MARA.MANDT " //
				+ "       AND MBEW.MATNR = MARA.MATNR " //
				+ "       AND MBEW.BWKEY = ? " //
				+ "       AND MBEW.BWTAR = ' ' " //
				+ "       AND MARC.MANDT = MBEW.MANDT " //
				+ "       AND MARC.MATNR = MBEW.MATNR " //
				+ "       AND MARC.WERKS = MBEW.BWKEY "; //

		pstm = conn.prepareStatement(sql);

		conn.setAutoCommit(false);

		String[] rowids = req.getParameterValues("ROWID");

		if (rowids != null) {
			for (String id : rowids) {

				D045L e = new D045L();
				SQNR += 10;
				e.setGSDM(req.getParameter("GSDM"));
				e.setBDDM(req.getParameter("BDDM"));
				e.setBDBH(req.getParameter("BDBH"));
				e.setSQNR(SQNR);
				e.setCMATNR(req.getParameter("CMATNR_" + id));
				e.setCWERKS(req.getParameter("CWERKS_" + id));
				//e.setREQQ(toDoubleParam(req, "REQTY_" + id));

				pstm.setString(1, e.getCMATNR());
				pstm.setString(2, e.getCWERKS());

				rst = pstm.executeQuery();

				if (rst.next()) {
					e.setCMATKL(rst.getString("MATKL"));
					e.setCMAKTX(rst.getString("MAKTX"));
					//e.setCMEINS(rst.getString("MEINS"));
					//e.setNETPR(rst.getDouble("NETPR"));
					//e.setCEKGRP(rst.getString("EKGRP"));
				}
				rst.close();

				//e.setREQA(e.getREQQ() * e.getNETPR());
				e.setJLYH(getUid(req));
				e.setJLSJ(new Date());
				
			}
		}

		pstm.close();

		sql = "UPDATE D045H SET BDAMT = (SELECT SUM(REQA) FROM D045L WHERE GSDM=D045H.GSDM AND BDDM=D045H.BDDM AND BDBH=D045H.BDBH) " //
				+ "WHERE GSDM=? AND BDDM=? AND BDBH=?"; //
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D045?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=" + req.getParameter("BDDM")
				+ "&BDBH=" + req.getParameter("BDBH"));
	}
	
	private void actionSubmitQH(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		String sql = "UPDATE D045H SET BDZT='0', BDJG='0' WHERE GSDM=? AND BDDM=? AND BDBH=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		QH_BDTX bdtx = new QH_BDTX();
		bdtx.setGSDM(req.getParameter("GSDM"));
		bdtx.setBDDM(req.getParameter("BDDM"));
		bdtx.setBDBH(req.getParameter("BDBH"));
		bdtx.setBDTX(req.getParameter("BDTX"));
		bdtx.insertDb(conn);

		QianHe qh = new QianHe();
		qh.setGSDM(req.getParameter("GSDM"));
		qh.setBDDM(req.getParameter("BDDM"));
		qh.setBDBH(req.getParameter("BDBH"));
		qh.setMgr(getUMgr(req));
		qh.setSQYH(getUid(req));
		for (QH_BDLC r : qh.crtRoute(conn, "A", req.getParameter("BDAMT"))) {
			r.insertDb(conn);
		}

		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D045/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
				+ "&STRBDBH=" + req.getParameter("BDBH"));

	}

	List<HashMap<String, Object>> getCompByRSNUM(String RSNUM) {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "  SELECT MAKT.MAKTX, RESB.MATNR, RESB.WERKS, RESB.MATKL, RESB.DUMPS, RESB.MEINS, " //
				+ "         RESB.ALPGR, RESB.ALPRF, SUM (RESB.BDMNG) BDMNG, SUM (RESB.ENMNG) ENMNG, SUM (RESB.VMENG) VMENG, " //
				+ "         MAX (MARD.LABST) LABST, MAX (MARD.INSME) INSME " //
				+ "    FROM SAPSR3.RESB, " //
				+ "         SAPSR3.MAKT, " //
				+ "         (  SELECT MATNR, WERKS, SUM (LABST) LABST, SUM (INSME) INSME " //
				+ "              FROM SAPSR3.MARD " //
				+ "             WHERE MANDT = '168' " //
				+ "                   AND MATNR IN (SELECT MATNR " //
				+ "                                   FROM SAPSR3.RESB " //
				+ "                                  WHERE RESB.MANDT = '168' AND RESB.RSNUM = ? GROUP BY MATNR) " //
				+ "          GROUP BY MATNR, WERKS) MARD " //
				+ "   WHERE     RESB.MANDT = '168' " //
				+ "         AND RESB.RSNUM = ? " //
				+ "         AND MAKT.MANDT = RESB.MANDT " //
				+ "         AND MAKT.MANDT = RESB.MANDT " //
				+ "         AND MAKT.MATNR = RESB.MATNR " //
				+ "         AND MAKT.SPRAS = 'M' " //
				+ "         AND MARD.MATNR(+) = RESB.MATNR " //
				+ "         AND MARD.WERKS(+) = RESB.WERKS " //
				+ "GROUP BY MAKT.MAKTX, RESB.MATNR, RESB.WERKS, RESB.MATKL, RESB.DUMPS,RESB.MEINS, " //
				+ "         RESB.ALPGR, RESB.ALPRF " //
				+ "ORDER BY MATKL,MAKTX "; //

		try {
			Connection conn = getSapPrdConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, RSNUM);
			pstm.setString(2, RSNUM);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}	
	
	private void doListGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		try{
			con = getConnection();
			String sql = "SELECT GSDM，BDDM，BDBH，BDRQ，BDZT，BDJG，BDLX，WERKS，CUST，KOSTL，KTEXT，DELADD，BDAMT，INVNO，CONO，SQYH，QHYH，QHSJ，JLYH，JLSJ，GXYH，GXSJ，BDFD，RSNUM, SAPUPDSTS, SAPUPDUSR, SAPUPDDAT, SAPUPDMSG " +
					" FROM D045H WHERE 1=1 "; //
			sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
			sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
			sql += Helper.sqlParams(req, "BDRQ", "STRBDRQ", "ENDBDRQ");
			sql += " ORDER BY GSDM,BDBH";
	
			List<HashMap<String, Object>> list = new D045Dao(con).getList(sql);
	
			req.setAttribute("list", list);
			
			con.close();
		} catch(Exception ex) {
			
		}

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D045.jsp").forward(req, resp);
	}

	private void doCreateGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D045New.jsp").forward(req, resp);
		
	}


	private void doSaveGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		D045VO hd = new D045VO();
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			String gsdm = req.getParameter("GSDM");
			String bddm = "D045";			
			String bdbh = D045Dao.getBDBH(con, req.getParameter("GSDM"), "D045", Helper.fmtDate(new Date(), "yyyy"));
			String bdrq = Helper.fmtDate(new Date(), "yyyyMMdd");
			String sqyh = getUid(req);
			
			hd.setGSDM(gsdm);
			hd.setBDDM(bddm);
			hd.setBDBH(bdbh);
			hd.setBDRQ(bdrq);
			hd.setSQYH(sqyh);
			hd.setBDLX(req.getParameter("BDLX"));
			hd.setCUST(req.getParameter("CUST"));
			hd.setDELADD(req.getParameter("DELADD"));
			hd.setINVNO(req.getParameter("INVNO"));
			hd.setCONO(req.getParameter("CONO"));			
			hd.setBDFD(req.getParameter("BDFD"));
			
			String [] sCMAKTX = req.getParameterValues("CMAKTX");
			String [] sCMATNR = req.getParameterValues("CMATNR");
			String [] sCCQTY = req.getParameterValues("CCQTY");
			String [] sCCUM = req.getParameterValues("CCUM");
			String [] sCPRICE = req.getParameterValues("CPRICE");
			String [] sCCURR = req.getParameterValues("CCURR");
			String [] sCWEIGHT = req.getParameterValues("CWEIGHT");
			
			new D045Dao(con).insertHeadDb(hd);
						
			for(int i = 0 ; i < sCMAKTX.length; i++){
				D045LVO d045LVO = new D045LVO();
				d045LVO.setGSDM(gsdm);
				d045LVO.setBDDM(bddm);
				d045LVO.setBDBH(bdbh);
				d045LVO.setCMAKTX(sCMAKTX[i]);
				d045LVO.setCMATNR(sCMATNR[i]);
				
				double dCCQTY = 0;
				try{
					dCCQTY = Double.parseDouble(sCCQTY[i]);
				}catch(Exception ex){}
				
				d045LVO.setCCQTY(dCCQTY);
				d045LVO.setCCUM(sCCUM[i]);
				
				double dCPRICE = 0;
				try{
					dCPRICE = Double.parseDouble(sCPRICE[i]);
				}catch(Exception ex){}
				
				d045LVO.setCPRICE(dCPRICE);
				d045LVO.setCCURR(sCCURR[i]);
				
				double dCWEIGHT = 0;
				try{
					dCWEIGHT = Double.parseDouble(sCWEIGHT[i]);
				}catch(Exception ex){}
				
				d045LVO.setCWEIGHT(dCWEIGHT);
				new D045Dao(con).insertLineDb(d045LVO);
			}
			
			String sql = "SELECT GSDM, BDDM, BDBH, SQNR, CMATNR, CWERKS, CMATKL, CMAKTX, CCUM, CCQTY, CPRICE, CCURR, CWEIGHT, JLYH, JLSJ, GXYH, GXSJ " +
					" FROM D045L WHERE 1=1 AND GSDM='"+gsdm+"' AND BDDM='"+bddm+"' AND BDBH='"+bdbh+"' ";
			
			List<HashMap<String, Object>> list = new D045Dao(con).getList(sql);
	
			req.setAttribute("list", list);
	
			req.setAttribute("D045", hd);
			
			List<QH_BDLC> BDLCS = QH_BDLC.findBy(gsdm, bddm, bdbh, con);
			
			String QHKS = "Y";
			if (BDLCS.isEmpty()) {
				QHKS = "N";
			}
			req.setAttribute("BDLCS", BDLCS);
			req.setAttribute("QHKS", QHKS);
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D045ED.jsp").forward(req, resp);
			
			con.commit();
			con.close();
		}catch(Exception ex){
			
		}
	}
	
	private void doUpdateGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		D045VO hd = new D045VO();
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			String gsdm = req.getParameter("GSDM");
			String bddm = "D045";			
			String bdbh = req.getParameter("BDBH");
			String bdrq = Helper.fmtDate(new Date(), "yyyyMMdd");
			String sqyh = getUid(req);
			
			hd.setGSDM(gsdm);
			hd.setBDDM(bddm);
			hd.setBDBH(bdbh);
			hd.setBDRQ(bdrq);
			hd.setSQYH(sqyh);
			hd.setBDLX(req.getParameter("BDLX"));
			hd.setCUST(req.getParameter("CUST"));
			hd.setDELADD(req.getParameter("DELADD"));
			hd.setINVNO(req.getParameter("INVNO"));
			hd.setCONO(req.getParameter("CONO"));			
			hd.setBDFD(req.getParameter("BDFD"));
			
			String [] sCMAKTX = req.getParameterValues("CMAKTX");
			String [] sCMATNR = req.getParameterValues("CMATNR");
			String [] sCCQTY = req.getParameterValues("CCQTY");
			String [] sCCUM = req.getParameterValues("CCUM");
			String [] sCPRICE = req.getParameterValues("CPRICE");
			String [] sCCURR = req.getParameterValues("CCURR");
			String [] sCWEIGHT = req.getParameterValues("CWEIGHT");
			
			new D045Dao(con).updateHeadDb(hd);
			if(new D045Dao(con).findLineDB(gsdm,bddm,bdbh)==1){
				new D045Dao(con).delLineDb(gsdm,bddm,bdbh);
			}
						
			for(int i = 0 ; i < sCMAKTX.length; i++){
				D045LVO d045LVO = new D045LVO();
				d045LVO.setGSDM(gsdm);
				d045LVO.setBDDM(bddm);
				d045LVO.setBDBH(bdbh);
				d045LVO.setCMAKTX(sCMAKTX[i]);
				d045LVO.setCMATNR(sCMATNR[i]);
				
				double dCCQTY = 0;
				try{
					dCCQTY = Double.parseDouble(sCCQTY[i]);
				}catch(Exception ex){}
				
				d045LVO.setCCQTY(dCCQTY);
				d045LVO.setCCUM(sCCUM[i]);
				
				double dCPRICE = 0;
				try{
					dCPRICE = Double.parseDouble(sCPRICE[i]);
				}catch(Exception ex){}
				
				d045LVO.setCPRICE(dCPRICE);
				d045LVO.setCCURR(sCCURR[i]);
				
				double dCWEIGHT = 0;
				try{
					dCWEIGHT = Double.parseDouble(sCWEIGHT[i]);
				}catch(Exception ex){}
				
				d045LVO.setCWEIGHT(dCWEIGHT);
				new D045Dao(con).insertLineDb(d045LVO);
			}
			
			String sql = "SELECT GSDM, BDDM, BDBH, SQNR, CMATNR, CWERKS, CMATKL, CMAKTX, CCUM, CCQTY, CPRICE, CCURR, CWEIGHT, JLYH, JLSJ, GXYH, GXSJ " +
					" FROM D045L WHERE 1=1 AND GSDM='"+gsdm+"' AND BDDM='"+bddm+"' AND BDBH='"+bdbh+"' ";
			
			List<HashMap<String, Object>> list = new D045Dao(con).getList(sql);
	
			req.setAttribute("list", list);
	
			req.setAttribute("D045", hd);
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D045ED.jsp").forward(req, resp);
			
			con.commit();
			con.close();
		}catch(Exception ex){
			
		}
	}

	private void doEditGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		D045VO d = new D045VO();

		try{
			con = getConnection();
			String sql = "SELECT GSDM，BDDM，BDBH，BDRQ，BDZT，BDJG，BDLX，WERKS，CUST，KOSTL，KTEXT，DELADD，BDAMT，INVNO，CONO，SQYH，QHYH，QHSJ，JLYH，JLSJ，GXYH，GXSJ，BDFD，RSNUM, SAPUPDSTS, SAPUPDUSR, SAPUPDDAT, SAPUPDMSG " +
					" FROM D045H WHERE 1=1 AND GSDM ='"+req.getParameter("GSDM")+"' AND BDDM ='D045' AND BDBH ='"+req.getParameter("BDBH")+"' ";
					
			d = new D045Dao(con).getVO(sql);
			
			List<QH_BDLC> BDLCS = QH_BDLC.findBy(d.getGSDM(), d.getBDDM(), d.getBDBH(), con);
	
			String QHKS = "Y";
			if (BDLCS.isEmpty()) {
				QHKS = "N";
			}
			
			String sql1 = "SELECT GSDM, BDDM, BDBH, SQNR, CMATNR, CWERKS, CMATKL, CMAKTX, CCUM, CCQTY, CPRICE, CCURR, CWEIGHT, JLYH, JLSJ, GXYH, GXSJ " +
					" FROM D045L WHERE 1=1 AND GSDM='"+d.getGSDM()+"' AND BDDM='"+d.getBDDM()+"' AND BDBH='"+d.getBDBH()+"' ";
			
			List<HashMap<String, Object>> list = new D045Dao(con).getList(sql1);
	
			req.setAttribute("list", list);
			
			req.setAttribute("D045", d);
			req.setAttribute("BDLCS", BDLCS);
			req.setAttribute("QHKS", QHKS);
			
			con.close();
		}catch(Exception ex){}	
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D045ED.jsp").forward(req, resp);
	}
}
