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
import imes.entity.D301H;
import imes.entity.D301R;
import imes.entity.D302H;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.CLOB;

@WebServlet({ "/D302", "/D302/*" })
public class D302 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		super.doGet(req, resp);

		try {
			String action2 = getAction(req);//System.out.println("action2 : "+action2);
		
			ireq = new IRequest(req);

			String action = ireq.getUrlMap().get("Action");

			if(action != null){
				if (action.equals("Query")) {
	
					actionPostList(req, resp);
	
				} else if (action.equals("CreateHeader302")) {
	
					actionPostCreateHeader(req, resp);
	
				} else if (action.equals("UpdateHeader302")) {
	
					actionPostUpdateHeader(req, resp);
	
				} else if(action.equals("New")) {
	
					actionGetNew(req, resp);
	
				} else if (action.equals("SubmitQH")) {
	
					actionSubmitQH(req, resp);
	
				} else if (action.equals("UpdateResp")) {
	
					//actionPostUpdateResp(req, resp);
	
				} else if (action.equals("Edit")) {
	
					actionGetEdit(req, resp);	
					
				} else if (action.equals("QianHe") && toIntegerParam(req, "BZDM") == 0) {
	
					actionGetEdit(req, resp);
	
				} else if (action.equals("QianHe") && toIntegerParam(req, "BZDM") > 0) {
	
					actionPostQianHe(req, resp);
	
				} else {
					
					String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND301 = 'X'";

					String KZ_USER = "N";
					
					if (!(getUserList(kz_user).isEmpty())) {
						KZ_USER = "Y";
					}

					req.setAttribute("kz_user", KZ_USER);
						
					getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D301.jsp").forward(req, resp);
						
				}

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
		
		doGet(req, resp);
//		try {
//
//			if (getAction(req).equals("Query")) {
//
//				actionPostList(req, resp);
//
//			} else if (getAction(req).equals("CreateHeader")) {
//
//				actionPostCreateHeader(req, resp);
//
//			} else if (getAction(req).equals("UpdateHeader")) {
//
//				actionPostUpdateHeader(req, resp);
//
//			} else if (getAction(req).equals("SubmitQH")) {
//
//				actionSubmitQH(req, resp);
//
//			} else if (getAction(req).equals("QianHe")) {
//
//				actionPostQianHe(req, resp);
//
//			} 
//
//		} catch (Exception e) {
//			PrintWriter out = resp.getWriter();
//			e.printStackTrace();
//			e.printStackTrace(out);
//			out.close();
//		}
	}

	private void actionPostList(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "SELECT * FROM D302H WHERE 1 = 1";

		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlParams(req, "BDRQ", "STRBDRQ", "ENDBDRQ");
		sql += Helper.sqlParams(req, "BDZT", "STRBDZT", "ENDBDZT");
		sql += Helper.sqlParams(req, "BDJG", "STRBDJG", "ENDBDJG");
		sql += Helper.sqlParams(req, "ZCBH", "STRZCBH", "ENDZCBH");
		sql += Helper.sqlParams(req, "ZCMC", "STRZCMC", "ENDZCMC");
		sql += Helper.sqlParams(req, "SQBM", "STRSQBM", "ENDSQBM");
		sql += Helper.sqlParams(req, "BMMC", "STRBMMC", "ENDBMMC");
		sql += Helper.sqlParams(req, "ZCLX", "STRZCLX", "ENDZCLX");
		sql += Helper.sqlParams(req, "SQYH", "STRSQYH", "ENDSQYH");

		sql += " ORDER BY BDBH";

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			Connection conn = getConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		req.setAttribute("list", list);
		
		String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND301 = 'X'";

		String KZ_USER = "N";
		
		if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D301.jsp").forward(req, resp);

	}

	private void actionPostQianHe(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		int BZDM = toIntegerParam(req, "BZDM");

		String QHNR = req.getParameter("QHNR");
		String QHJG = req.getParameter("QHJG");

		String HQYH = req.getParameter("HQYH");

		String YHCQ = req.getParameter("YHCQ");

		String sql;
		Connection conn = getConnection();
		conn.setAutoCommit(false);

		QH_BDLC old = new QH_BDLC();
		old.setGSDM(GSDM);
		old.setBDDM(BDDM);
		old.setBDBH(BDBH);
		old.setBZDM(BZDM);
		old.find(conn);

		QH_BDLC e;
		int i = BZDM;

		try {

			if (HQYH != null && !"".equals(HQYH)) {
				for (String str : HQYH.split(",")) {
					if (!str.replaceAll(" ", "").equals("")) {

						e = (QH_BDLC) old.clone();
						e.setBZDM(i + 1);
						e.setQHLX("I");
						e.setZWDM("");
						e.setZFYN("");
						e.setLCLX("P");
						e.setQHZT("1");
						e.setJLSJ(new Date());
						e.setYJSJ(null);
						e.setYSYH(str);
						e.verifyNewBZDM(conn);
						e.insertDb(conn);
						i = e.getBZDM();

					}
				}
			}

			if (YHCQ != null) {
				QHJG = "Y";
				e = (QH_BDLC) old.clone();
				e.setBZDM(i + 1);
				e.setQHZT("0");
				e.setYSYH(getUid(req));
				e.setJLSJ(new Date());
				e.setYJSJ(null);
				e.setQHFD("");
				e.insertDb(conn);

			}

			sql = "UPDATE QH_BDLC " //
					+ "   SET QHZT='3', " //
					+ "       QHYH=?, " //
					+ "       QHSJ=SYSDATE, " //
					+ "       QHJG=?, " //
					+ "       QHNR=? " //
					// + "       QHFD=? " //
					+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, getUid(req));
			pstm.setString(2, QHJG);
			pstm.setString(3, QHNR);
			// pstm.setString(4, "");
			pstm.setString(4, GSDM);
			pstm.setString(5, BDDM);
			pstm.setString(6, BDBH);
			pstm.setInt(7, BZDM);
			pstm.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			conn.rollback();
		}

		conn.close();

		resp.sendRedirect("/iMes/qh?action=Find&STRQHZT=2&STRYSYH=" + getUid(req));
	}

	private void actionGetEdit(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		D302H h302 = new D302H();
		h302.setGSDM(req.getParameter("GSDM"));
		h302.setBDDM(req.getParameter("BDDM"));
		h302.setBDBH(req.getParameter("BDBH"));
		h302.find(conn);
		
		D301H hd = new D301H();
		hd.setGSDM(h302.getGSDM());
		hd.setBDDM("D301");
		hd.setBDBH(h302.getWXDH());
		hd.find(conn);

		String sql = "SELECT * FROM D301H WHERE GSDM=? AND BDDM=? AND BDBH=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, req.getParameter("BDDM"));
		pstm.setString(3, req.getParameter("BDBH"));

		List<HashMap<String, Object>> lines = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();

		List<D301R> D301Rs = getD301Rs(req, conn);

		String ZWDM;
		String PURUSER = "N";
		sql = "SELECT ZWDM FROM QH_ZWYH WHERE SYSDATE BETWEEN YXFD AND YXTD AND YHDM = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, getUid(req));
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			ZWDM = rst.getString("ZWDM");
			if (ZWDM.contains("CG")) {
				PURUSER = "Y";
			}
		}
		rst.close();
		pstm.close();

		List<QH_BDLC> BDLCS = QH_BDLC.findBy(h302.getGSDM(), "D302", h302.getWXDH(), conn);

		List<QH_BDLC> BDLCS302 = QH_BDLC.findBy(h302.getGSDM(), "D302", h302.getBDBH(), conn);

		conn.close();

		String QHKS = "Y";
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}
		
		String QHKS302 = "Y";
		if (BDLCS302.isEmpty()) {
			QHKS302 = "N";
		}

		req.setAttribute("HD", hd);
		req.setAttribute("D302", h302);
		req.setAttribute("LN", lines);
		req.setAttribute("RS", D301Rs);
		req.setAttribute("PURUSER", PURUSER);
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("BDLCS302", BDLCS302);
		req.setAttribute("QHKS", QHKS);
		req.setAttribute("QHKS302", QHKS302);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D301ED.jsp").forward(req, resp);

	}
	
	private void actionGetNew(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setAttribute("HD", null);
		req.setAttribute("LN", null);
		req.setAttribute("RS", null);
		req.setAttribute("PURUSER", null);
		req.setAttribute("BDLCS", null);
		req.setAttribute("QHKS", null);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D302ED.jsp").forward(req, resp);

	}

	private void actionPostCreateHeader(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		String KTEXT = "";
		String sqbm = req.getParameter("SQBM");
		String sql = "SELECT KTEXT FROM QH_CSKT WHERE KOSTL=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, sqbm);
		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			KTEXT = rst.getString("KTEXT");
		}
		rst.close();
		pstm.close();

		D302H h = new D302H();
		h.setGSDM(req.getParameter("GSDM").toUpperCase());
		h.setBDDM("D302");
		h.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		h.setZCBH(req.getParameter("ZCBH").toUpperCase());
		h.setZCMC(req.getParameter("ZCMC").toUpperCase());
		h.setZCLX(req.getParameter("ZCLX").toUpperCase());
		h.setSQBM(sqbm);
		h.setBMMC(KTEXT);
		h.setSQYH(getUid(req));
		h.setJLYH("");
		h.setJLSJ(null);
		h.setWCRQ(req.getParameter("WCRQ"));
		h.setZCCB(req.getParameter("ZCCB").toUpperCase().replace("NULL", ""));
		h.setBGR(req.getParameter("BGR").toUpperCase().replace("NULL", ""));
		h.setZBDT(req.getParameter("ZBDT").toUpperCase().replace("NULL", ""));
	    h.setZCBG(req.getParameter("ZCBG").toUpperCase().replace("NULL", ""));
	    h.setADDR(req.getParameter("ADDR").toUpperCase().replace("NULL", ""));
	    //h.setQCNR(req.getParameter("QCNR1"));
	    h.setBDBJ(req.getParameter("BDBJ"));
	    try{
	    	h.setZMJZ(Double.parseDouble(req.getParameter("ZMJZ").toUpperCase()));
	    }catch(Exception e){
	    	h.setZMJZ(0);
	    }
	    try{
	    	h.setQTY(Double.parseDouble(req.getParameter("QTY").toUpperCase()));
	    }catch(Exception e){
	    	h.setQTY(0);
	    }
	    try{
	    	h.setQDJZ(Double.parseDouble(req.getParameter("QDJZ").toUpperCase()));
	    }catch(Exception e){
	    	h.setQDJZ(0);
	    }
	    h.setWXDH(req.getParameter("WXDH").toUpperCase().replace("NULL", ""));

	    h.setSUPPLIER(req.getParameter("SUPPLIER2").toUpperCase().replace("NULL", ""));
   		h.setSUPPLIER_NAME(req.getParameter("SUPPLIER_NAME2").toUpperCase().replace("NULL", ""));
		h.setCONTACT(req.getParameter("CONTACT2").toUpperCase().replace("NULL", ""));
		h.setPHONE(req.getParameter("PHONE2").toUpperCase().replace("NULL", ""));
		h.setPRICE(req.getParameter("PRICE2").toUpperCase().replace("NULL", "0"));
		h.setUNIT(req.getParameter("UNIT2").toUpperCase().replace("NULL", ""));
		h.setQUALITY(req.getParameter("QUALITY2").toUpperCase().replace("NULL", "0"));
		h.setCURRENCY(req.getParameter("CURRENCY2").toUpperCase().replace("NULL", ""));
		
//        CLOB clob = null;
        
		try {
			
			h.setBDBH(Helper.getBDBH(getConnection(), h.getGSDM(), h.getBDDM(), Helper.fmtDate(new Date(), "yyyy")));
			
			h.insertDb(conn);
            
//            sql = "SELECT QCNR FROM D302H " +
//            	" WHERE GSDM='"+h.getGSDM()+"' AND BDDM='"+h.getBDDM()+"'" +
//        		" AND BDBH='"+h.getBDBH()+"' for update ";
//            
//            pstm = conn.prepareStatement(sql);
//            
//            rst = pstm.executeQuery();
//
//            if(rst.next()){
//            	
//                clob = (CLOB)rst.getClob(1);
//                
//            }
//            
//            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream()); 
//
//            pw.write(h.getQCNR());
//
//            pw.flush(); 
//            
//            pstm.close();
              
//            sql = "UPDATE D302H SET QCNR=? " +
//            	" WHERE GSDM='"+h.getGSDM()+"' AND BDDM='"+h.getBDDM()+"'" +
//    			" AND BDBH='"+h.getBDBH()+"' ";
//
//            pstm = conn.prepareStatement(sql);
//            
//            pstm.setClob(1, clob);
//            
//            pstm.executeUpdate();
//            
//            pstm.close();
            conn.commit();  
//            pw.close();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		conn.setAutoCommit(true);
		conn.close();

		resp.sendRedirect("/iMes/D301?action=Edit&GSDM=" + h.getGSDM() + "&BDDM=D301&BDBH=" + h.getWXDH());

	}

	private void actionPostUpdateHeader(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sql = "UPDATE D302H SET SUPPLIER=?, SUPPLIER_NAME=?, CONTACT=?, PHONE=?, PRICE=?, UNIT=?, QUALITY=?, CURRENCY=?, BDBJ=? WHERE GSDM=? AND BDDM=? AND BDBH=?";
		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("SUPPLIER"));
		pstm.setString(2, req.getParameter("SUPPLIER_NAME"));
		pstm.setString(3, req.getParameter("CONTACT"));
		pstm.setString(4, req.getParameter("PHONE"));
		pstm.setString(5, req.getParameter("PRICE"));
		pstm.setString(6, req.getParameter("UNIT"));
		pstm.setString(7, req.getParameter("QUALITY"));
		pstm.setString(8, req.getParameter("CURRENCY"));
		pstm.setString(9, req.getParameter("BDBJ"));
		pstm.setString(10, req.getParameter("GSDM"));
		pstm.setString(11, req.getParameter("BDDM"));
		pstm.setString(12, req.getParameter("BDBH"));
		pstm.executeUpdate();
		pstm.close();
		conn.close();

		resp.sendRedirect("/iMes/D301?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=D301&BDBH=" + req.getParameter("WXDH"));

	}
	
	private void actionSubmitQH(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		String sql = "UPDATE D302H SET BDZT='0', BDJG='0' WHERE GSDM=? AND BDDM=? AND BDBH=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, req.getParameter("GSDM"));
		pstm.setString(2, "D302");
		pstm.setString(3, req.getParameter("BDBH"));
		pstm.executeUpdate();

		pstm.close();

		QH_BDTX bdtx = new QH_BDTX();
		bdtx.setGSDM(req.getParameter("GSDM"));
		bdtx.setBDDM("D302");
		bdtx.setBDBH(req.getParameter("BDBH"));
		String bdbh = req.getParameter("BDBH");
		String wxdh = req.getParameter("WXDH");
		bdtx.setBDTX(req.getParameter("BDTX"));
		bdtx.insertDb(conn);

		QianHe qh = new QianHe();
		qh.setGSDM(req.getParameter("GSDM"));
		qh.setBDDM("D302");
		qh.setBDBH(req.getParameter("BDBH"));
		qh.setMgr(getUMgr(req));
		qh.setSQYH(getUid(req));
		for (QH_BDLC r : qh.crtRoute(conn, "A", req.getParameter("BDAMT"))) {
			if(r.getBDDM() != null && r.getBDDM().equals("D301") && r.getZWDM() !=null && r.getZWDM().equalsIgnoreCase("MGR") && r.getBZDM() == 100){
				r.setZWDM("TXD3010GLY");
			}
			r.insertDb(conn);
		}
				
		List<QH_BDLC> q = new ArrayList<QH_BDLC>();
		QH_BDLC bdlc = null;
		
		int bzdm = 200;
		String qhzt = "0";
		String zwdm = "TXD3011GLY";
		
		String level = getLevel(qh.getSQYH().toUpperCase());//申請人級別
		if(level != null && level.equals("")){//申請人級別  課長
			String managerlevel = getLevel(findUserManager(qh.getSQYH().toUpperCase()));//申請人主管級別
			//System.out.println("申請人的主管級別："+managerlevel);
			if(managerlevel != null && managerlevel.equals("KZ")){//申請人的主管級別是經理、副理
				zwdm = "TXD3012GLY";
				bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),qhzt,bzdm,999,zwdm,"R","Y",qh.getSQYH().toUpperCase(),findUserManager2(qh.getSQYH().toUpperCase()));

				q.add(bdlc);
				bzdm+=100;
				
				zwdm = "TXD3013GLY";
				bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),qhzt,bzdm,999,zwdm,"R","Y",qh.getSQYH().toUpperCase(),findUserManager2(qh.getSQYH().toUpperCase()));

				q.add(bdlc);
				bzdm+=100;
			} else if (managerlevel != null && ( managerlevel.equals("JL") || managerlevel.equals("FL") || managerlevel.equals("XL") ) ) {

				bzdm+=100;	
				
				zwdm = "TXD3014GLY";
				bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),qhzt,bzdm,999,zwdm,"R","Y",qh.getSQYH().toUpperCase(),findUserManager(qh.getSQYH().toUpperCase()));

				q.add(bdlc);
				bzdm+=100;	
			}
		} else if (level != null && level.equals("KZ")){
			String managerlevel = getLevel(findUserManager(qh.getSQYH().toUpperCase()));//申請人主管級別
			//System.out.println("申請人的主管級別："+managerlevel);
			if(managerlevel != null && (managerlevel.equals("KZ") || managerlevel.equals("JL") || managerlevel.equals("FL") || managerlevel.equals("XL") ) && !getLevel(findUserManager(findUserManager(qh.getSQYH().toUpperCase()))).equalsIgnoreCase("ZJL")){//申請人的主管級別是經理、副理
				zwdm = "TXD3015GLY";
				bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),qhzt,bzdm,999,zwdm,"R","Y",qh.getSQYH().toUpperCase(),findUserManager2(qh.getSQYH().toUpperCase()));

				q.add(bdlc);
				bzdm+=100;
			}
		} else if (level != null && (level.equals("JL") || level.equals("FL"))){
			String managerlevel = getLevel(findUserManager(qh.getSQYH().toUpperCase()));//申請人主管級別
			//System.out.println("申請人的主管級別："+managerlevel);
			if(managerlevel != null && (managerlevel.equals("KZ") || managerlevel.equals("JL") || managerlevel.equals("FL") || managerlevel.equals("XL") ) && !getLevel(findUserManager(findUserManager(qh.getSQYH().toUpperCase()))).equalsIgnoreCase("ZJL")){//申請人的主管級別是經理、副理
				zwdm = "TXD3016GLY";
				bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),qhzt,bzdm,999,zwdm,"R","Y",qh.getSQYH().toUpperCase(),findUserManager2(qh.getSQYH().toUpperCase()));

				q.add(bdlc);
				bzdm+=100;
			}
		}

		String u_id = getZC_GLY(req.getParameter("ZCCB"));
		if(u_id != null && u_id.length()> 0)
		{
			bzdm += 1000;
			zwdm = "TXD3017GLY";
			bdlc = addQHE(qh.getGSDM(),qh.getBDDM(),qh.getBDBH(),qhzt,bzdm,999,zwdm,"R","Y",qh.getSQYH().toUpperCase(),u_id);

			q.add(bdlc);
			bzdm+=100;
		}
		
		for (QH_BDLC r : q) {				
			insertQH_BDLC(r);
		}
		conn.commit();
		conn.close();

		resp.sendRedirect("/iMes/D301?action=Edit&GSDM=" + req.getParameter("GSDM") + "&BDDM=D301&BDBH=" + wxdh);

	}

	private List<D301R> getD301Rs(HttpServletRequest req, Connection conn) {
		String sql = "SELECT * FROM D301R WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY SEQ";
		D301R e;
		int i = 0;

		List<D301R> list = new ArrayList<D301R>();

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, req.getParameter("GSDM"));
			pstm.setString(2, req.getParameter("BDDM"));
			pstm.setString(3, req.getParameter("BDBH"));

			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {
				i += 1;
				e = new D301R();
				e.setGSDM(rst.getString("GSDM"));
				e.setBDDM(rst.getString("BDDM"));
				e.setBDBH(rst.getString("BDBH"));
				e.setSEQ(i);
				e.setSUPPLIER(rst.getString("SUPPLIER").trim());
				e.setSUPPLIER_NAME(rst.getString("SUPPLIER_NAME") == null?"":rst.getString("SUPPLIER_NAME").trim());
				e.setCONTACT(rst.getString("CONTACT") == null?"":rst.getString("CONTACT").trim());
				e.setPHONE(rst.getString("PHONE") == null?"":rst.getString("PHONE").trim());
				e.setPRICE(rst.getString("PRICE") == null?"0":rst.getString("PRICE").trim());
				e.setUNIT(rst.getString("UNIT") == null?"":rst.getString("UNIT").trim());
				e.setQUALITY(rst.getString("QUALITY") == null?"0":rst.getString("QUALITY").trim());
				e.setCURRENCY(rst.getString("CURRENCY") == null?"":rst.getString("CURRENCY").trim());
				e.setBDFD(rst.getString("BDFD") == null?"":rst.getString("BDFD").trim());
				e.setJLYH(rst.getString("JLYH") == null?"":rst.getString("JLYH").trim());
				e.setJLSJ(rst.getDate("JLSJ"));
				list.add(e);
			}
			rst.close();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (int j = i + 1; j < 6; ++j) {
			e = new D301R();
			e.setSEQ(j);
			list.add(e);
		}

		return list;
	}
	
	public String findUserManager(String user) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = '"+user+"' ";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("MANAGER");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}

	public String findUserManager1(String user) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = '"+user+"' AND MANAGER IN (SELECT YHDM FROM QH_ZWYH WHERE SUBSTR(ZWDM,3,3) = 'ZJL' ) ";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("MANAGER");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}

	public String findUserGLY(String bdh) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM = '"+bdh+"'";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("YHDM");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}

	public boolean isUserManager2(String user) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		boolean has = true;

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = " +
				"(SELECT MANAGER FROM USERS WHERE USERID = '"+user+"') "+ 
				" AND MANAGER IN (SELECT YHDM FROM QH_ZWYH WHERE SUBSTR(ZWDM,3,3) = 'ZJL' ) ";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				has = false;
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return has;
	}

	public String findUserManager2(String user) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = (SELECT MANAGER FROM USERS WHERE USERID = '"+user+"')";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("MANAGER");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}
	
	public String getLevel(String sqyh) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String slevel = "";

		String sql = "SELECT KZ,JL,FL,XL,ZJL FROM USERS WHERE USERID = '"+sqyh+"'";
		//System.out.println("isLevel :"+sql);
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				String kz = rst.getString("KZ");
				if(kz!=null && kz.equals("X")){
					slevel = "KZ";
				}
				String fl = rst.getString("FL");
				if(fl!=null && fl.equals("X")){
					slevel = "FL";
				}
				String jl = rst.getString("JL");
				if(jl!=null && jl.equals("X")){
					slevel = "JL";
				}
				String xl = rst.getString("XL");
				if(xl!=null && xl.equals("X")){
					slevel = "XL";
				}
				String zjl = rst.getString("ZJL");
				if(zjl!=null && zjl.equals("X")){
					slevel = "ZJL";
				}
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return slevel;
	}
	
	public boolean isManagerLevel(String sqyh,String level) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		boolean isManager = false;

		String sql = "SELECT "+level+" FROM USERS WHERE USERID = (SELECT MANAGER FROM USERS WHERE USERID = '"+sqyh+"')";
		//System.out.println("isManagerLevel :"+sql);
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				if(rst.getString(1).equals("X")){
					isManager = true;
				}
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isManager;
	}

	public boolean isManager2Level(String sqyh,String level) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		boolean isManager = false;

		String sql = "SELECT "+level+" FROM USERS WHERE USERID =(SELECT MANAGER FROM USERS WHERE USERID = (SELECT MANAGER FROM USERS WHERE USERID = '"+sqyh+"'))";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				if(rst.getString(1).equals("X")){
					isManager = true;
				}
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isManager;
	}
	
	public boolean isSecurityUser(String userid) {
		String security =  findPostUser("D888");
		String [] userSecur = security.split(",");
		
		for(String us : userSecur){
			if(us.equals(userid)){
				return true;
			}
		}		
		
		return false;
	}
	
	public String findPostUser(String zwdm) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM = '"+zwdm+"' ";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("YHDM");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}
	
	public int getQH_BDLCPost(String GSDM,String BDDM,String BDBH) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		int qhdm = 0;

		String sql = "SELECT MAX(BZDM) AS BZDM FROM QH_BDLC WHERE GSDM = '"+GSDM+"' AND BDDM = '"+BDDM+"' AND BDBH = '"+BDBH+"' AND BZDM <> '9999' ";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				qhdm = rst.getInt("BZDM");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return qhdm;
	}
	
	public int updateTableH(String sql) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		int num = 0;

		try {
			conn = getConnection();
			
			pstm = conn.prepareStatement(sql);

			num = pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				pstm.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return num;
	}
	
	public String getZWDM(String zwdm,String gsdm) {
		String szwdm = "";
		if(gsdm.toUpperCase().equals("L300")){
			szwdm = "TX"+zwdm;
		}else if(gsdm.toUpperCase().equals("L210")){
			szwdm = "LH"+zwdm;
		}else if(gsdm.toUpperCase().equals("L400")){
			szwdm = "DT"+zwdm;
		}else if(gsdm.toUpperCase().equals("L100")){
			szwdm = "TP"+zwdm;
		}
		return szwdm;
	}
	
	private QH_BDLC addQHE(String gsdm, String bddm, String bdbh,String qhzt,int bzdm,int yxxs,String zwdm,String qhlx,String zfyn,String sqyh,String ysyh){

		QH_BDLC e = new QH_BDLC();
		e.setGSDM(gsdm);
		e.setBDDM(bddm);
		e.setBDBH(bdbh);
		e.setLXDM(null);
		e.setQHZT(qhzt);
		e.setBZDM(bzdm);
		e.setLCLX("S");
		e.setZWDM(zwdm);
		e.setQHLX(qhlx);
		e.setYXXS(yxxs);
		e.setZFYN(zfyn);
		e.setYJSJ(null);
		e.setYXSJ(null);
		e.setSQYH(sqyh);
		e.setYSYH(ysyh);
		
		if (e.getQHLX().contains("I")){
			e.setLCLX("P");
		}
		
		return e;
	}
	
	public int insertQH_BDLC(QH_BDLC q) {
		
		Connection con = null;
		
		int i = 0;
		
		String sql = "INSERT INTO QH_BDLC "
				+ "(GSDM,BDDM,BDBH,LXDM,QHZT,BZDM,LCLX,MINQ,MAXQ,MINA,MAXA,MINP,MAXP,CURR,FTXT,TTXT,ZWDM,QHLX,YXXS,ZFYN,JLSJ,YJSJ,YXSJ,YSYH,DLYH,QHYH,QHSJ,QHJG,QHNR,QHFD,SQYH) " //
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //
		
		PreparedStatement pstm = null;
		
		try{
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, q.getGSDM());
			pstm.setString(2, q.getBDDM());
			pstm.setString(3, q.getBDBH());
			pstm.setString(4, q.getLXDM());
			pstm.setString(5, q.getQHZT());
			pstm.setInt(6, q.getBZDM());
			pstm.setString(7, q.getLCLX());
			pstm.setDouble(8, q.getMINQ());
			pstm.setDouble(9, q.getMAXQ());
			pstm.setDouble(10, q.getMINA());
			pstm.setDouble(11, q.getMAXA());
			pstm.setDouble(12, q.getMINP());
			pstm.setDouble(13, q.getMAXP());
			pstm.setString(14, q.getCURR());
			pstm.setString(15, q.getFTXT());
			pstm.setString(16, q.getTTXT());
			pstm.setString(17, q.getZWDM());
			pstm.setString(18, q.getQHLX());
			pstm.setDouble(19, q.getYXXS());
			pstm.setString(20, q.getZFYN());
			pstm.setTimestamp(21, Helper.toSqlDate(q.getJLSJ()));
			pstm.setTimestamp(22, Helper.toSqlDate(q.getYJSJ()));
			pstm.setTimestamp(23, Helper.toSqlDate(q.getYXSJ()));
			pstm.setString(24, q.getYSYH());
			pstm.setString(25, q.getDLYH());
			pstm.setString(26, q.getQHYH());
			pstm.setTimestamp(27, Helper.toSqlDate(q.getQHSJ()));
			pstm.setString(28, q.getQHJG());
			pstm.setString(29, q.getQHNR());
			pstm.setString(30, q.getQHFD());
			pstm.setString(31, q.getSQYH());
			
			i = pstm.executeUpdate();
			
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	public String getZC_GLY(String cost) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String yhdm = "";

		String sql = "SELECT USERID FROM ZC_COST_GLY WHERE COST = '"+cost+"' ";

		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				
				yhdm = rst.getString("USERID");
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	
			try {
				rst.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yhdm;
	}

	public List<HashMap<String, Object>> getUserList(String sql) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		List<HashMap<String, Object>> list = null;
		
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
	
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			try {
				
				pstm.close();
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;		
	}
}
