package imes.web.form;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import oracle.sql.CLOB;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.IRequest;
import imes.core.QianHe;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;
import imes.vo.D888VO;

@WebServlet({ "/D888", "/D888/*" })
public class D888 extends HttpController {
	
	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			String action2 = getAction(req);
					
			ireq = new IRequest(req);
			
			String action = ireq.getUrlMap().get("Action");
			
			if(action != null){
				if (action.equals("LIST")) {
					if(getUid(req).equalsIgnoreCase("FELIX.JIANG")||getUid(req).equalsIgnoreCase("LUM.CL")||getUid(req).equalsIgnoreCase("CHICHEN.LIN") ||getUid(req).equalsIgnoreCase("LINDA.HSIEH") || isSecurityUser(getUid(req))){
						doAdminListGetAction(req, resp);
					}else{
						doListGetAction(req, resp);
					}
		
				} else if (action.equals("CREATE")) {
		
					doCreateGetAction(req, resp);
		
				} else if (action.equals("READ")) {
		
					doReadGetAction(req, resp);
		
				} else if (action.equals("EDIT")) {
					
					doEditGetAction(req, resp);
		
				} else if (action.equals("DELETE")) {
		
					doDeleteGetAction(req, resp);
		
				} else if (action.equals("SAVE")) {
		
					doSaveGetAction(req, resp);
		
				} else if (action.equals("UPDATE")) {
		
					doUpdateGetAction(req, resp);
		
				} else if (action.equals("SubmitQH")) {
					
					//actionSubmitQH(req, resp);
					actionSubmitQHA(req, resp);
					
				} else if (action.equals("GLYQH")) {

					//actionSubmitQHGLY(req, resp);
					actionSubmitQHA(req, resp);
					
				} else if (action.equals("Print")) {

					doPrintGetAction(req, resp);
					
				} else if (action.equals("ADDQH")) {

					doADDQHGetAction(req, resp);
					
				} else if (action.equals("ADDQHR")) {

					actionSubmitQHR(req, resp);
					
				} else if (action.equals("EXPORTEXCEL")) {

					actionExportExcel(req, resp);
					
				} else if (action.equals("CANCEL")) {

					actionCancel(req, resp);
					
				}
			}
			if (action2 != null && action2.equals("QianHe")) {

				if(req.getParameter("BZDM")!= null && !"".equals(req.getParameter("BZDM"))){

					actionPostQianHe(req, resp);
					
				} else {
					
					resp.sendRedirect("/iMes/D888/EDIT?GSDM="+req.getParameter("GSDM")+"&BDDM="+req.getParameter("BDDM")+"&BDBH="+req.getParameter("BDBH")+"&HQYH="+getUid(req)+"");
					
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

	private void doAdminListGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 "; //
		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		
		String strbdrq = req.getParameter("STRBDRQ");
		String endbdrq = req.getParameter("ENDBDRQ");
		if(strbdrq != null && endbdrq != null && !"".equals(strbdrq) && !"".equals(endbdrq)){
			sql += " AND (BDRQ >= '"+strbdrq+"' AND BDRQ <= '"+endbdrq+"') ";
		} else {
			Calendar oldCal = Calendar.getInstance();
			oldCal.add(Calendar.MONTH, -1);
			sql += " AND (BDRQ >= '" + Helper.fmtDate(oldCal.getTime(), "yyyyMMdd") + "' AND BDRQ <= '" + Helper.fmtDate(new Date(), "yyyyMMdd") + "')";
		}
		
		String cclx = req.getParameter("CCLX");
		
		if(cclx != null && !"".equals(cclx)){
			sql += " AND CCLX = '"+cclx+"' ";
		}

		String bdjg = req.getParameter("BDJG");
		
		if(bdjg != null && !"".equals(bdjg)){
			if("OTHER".equals(bdjg)){
				sql += " AND (BDJG NOT IN ('Y','N') OR BDJG IS NULL) ";
			} else {
				sql += " AND BDJG = '"+bdjg+"' ";
			}
		} 
		
		String bdzt = req.getParameter("BDZT");
		
		if(bdzt != null && !"".equals(bdzt)){
			if("OTHER".equals(bdzt)){
				sql += " AND (BDZT NOT IN ('0','X') OR BDZT IS NULL)";
			} else {
				sql += " AND BDZT = '"+bdzt+"' ";
			}
		}
		
		String sqyh = req.getParameter("SQYH");
		
		if(sqyh != null && !"".equals(sqyh)){
			sql += " AND UPPER(SQYH) like '%'||UPPER('"+sqyh+"')||'%' ";
		}
		
		sql += " ORDER BY GSDM,BDBH DESC";//System.out.println("sql : "+sql);

		req.setAttribute("D888sqlrst", sql);
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		//String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND888 = 'x'";

		String KZ_USER = "N";
		
		//if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		//}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888.jsp").forward(req, resp);
	}

	private void doListGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sql = "SELECT GSDM,BDDM,BDBH,BMMC,SQYH,BDRQ,BDZT,BDJG,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 "; //
		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		
		String strbdrq = req.getParameter("STRBDRQ");
		String endbdrq = req.getParameter("ENDBDRQ");
		
		if(strbdrq != null && endbdrq != null && !"".equals(strbdrq) && !"".equals(endbdrq)){
			sql += " AND (BDRQ >= '"+strbdrq+"' AND BDRQ <= '"+endbdrq+"') ";
		} else {
			Calendar oldCal = Calendar.getInstance();
			oldCal.add(Calendar.MONTH, -1);
			sql += " AND (BDRQ >= '" + Helper.fmtDate(oldCal.getTime(), "yyyyMMdd") + "' AND BDRQ <= '" + Helper.fmtDate(new Date(), "yyyyMMdd") + "')";
		}
		
		String cclx = req.getParameter("CCLX");
		
		if(cclx != null && !"".equals(cclx)){
			sql += " AND CCLX = '"+cclx+"' ";
		}

		String bdjg = req.getParameter("BDJG");
		
		if(bdjg != null && !"".equals(bdjg)){
			if("OTHER".equals(bdjg)){
				sql += " AND (BDJG NOT IN ('Y','N') OR BDJG IS NULL) ";
			} else {
				sql += " AND BDJG = '"+bdjg+"' ";
			}
		} 
		
		String bdzt = req.getParameter("BDZT");
		
		if(bdzt != null && !"".equals(bdzt)){
			if("OTHER".equals(bdzt)){
				sql += " AND (BDZT NOT IN ('0','X') OR BDZT IS NULL)";
			} else {
				sql += " AND BDZT = '"+bdzt+"' ";
			}
		}
		
		String sqyh = req.getParameter("SQYH");
		
		if(sqyh != null && !"".equals(sqyh)){
			sql += " AND UPPER(SQYH) like '%'||UPPER('"+sqyh+"')||'%' ";
		}

		sql += " AND (BDBH IN ( SELECT BDBH FROM QH_BDLC WHERE 1=1 ";
		sql += " AND YSYH = '"+getUid(req)+"') OR BDBH IN ( ";
		sql += " SELECT BDBH FROM D888H WHERE 1=1 ";
		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlParams(req, "BDRQ", "STRBDRQ", "ENDBDRQ");
		sql += " AND SQYH = '"+getUid(req)+"'))"; 
		
		sql += " ORDER BY GSDM,BDBH DESC";//System.out.println("list sql : "+sql);

		req.setAttribute("D888sqlrst", sql);
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		//String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND888 = 'x'";

		String KZ_USER = "N";
		
		//if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		//}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888.jsp").forward(req, resp);
	}

	private void doCreateGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888New.jsp").forward(req, resp);
		
	}

	private void doADDQHGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D888VO d = new D888VO();
		String gsdm = req.getParameter("GSDM");
		String bddm = "D888";
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D888' AND BDBH ='"+bdbh+"' ";

		d = getVO(sql,gsdm,bddm,bdbh);

		List<String> userlist = findUserZJL();
		req.setAttribute("userlist", userlist);
		
		req.setAttribute("D888", d);
		
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888ADDQHR.jsp").forward(req, resp);
		
	}

	private void doReadGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D888VO hd = new D888VO();
		
		hd.setGSDM(req.getParameter("GSDM"));
		hd.setBDDM("D888");
		hd.setBDBH(req.getParameter("BDBH"));
//		hd.setBMMC(req.getParameter("BMMC"));
//		hd.setSQYH(getUid(req));//CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,CCNR,
//		hd.setCCLX(req.getParameter("CCLX"));
//		hd.setBMBH(req.getParameter("BMBH"));
//		hd.setCCBH(req.getParameter("CCBH"));
//		hd.setLLBH(req.getParameter("LLBH"));
//		hd.setTLBH(req.getParameter("TLBH"));
//		hd.setWLBH(req.getParameter("WLBH"));
//		hd.setDDBH(req.getParameter("DDBH"));
				
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,GYSM,BDGZ,ZJE,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+req.getParameter("GSDM")+"' AND BDDM ='D888' AND BDBH ='"+req.getParameter("BDBH")+"' ";
				
		D888VO d = new D888VO();
		d = getVO(sql,hd.getGSDM(),hd.getBDDM(),hd.getBDBH());
		
		Connection con = null;
		
		List<QH_BDLC> BDLCS = null;
		try {
			con = getConnection();
			
			BDLCS = QH_BDLC.findBy(hd.getGSDM(), hd.getBDDM(), hd.getBDBH(), con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String QHKS = "Y";
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}
		
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);

		req.setAttribute("D888", d);
		
		String url = "/iMes/D888/View?juid=" + getUid(req)+"&GSDM="+hd.getGSDM()+"&BDDM="+hd.getBDDM()+"&BDBH="+hd.getBDBH()+"";
		req.getSession().setAttribute("URL", url);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888View.jsp").forward(req, resp);
	}

	private void doEditGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D888VO d = new D888VO();
		String gsdm = req.getParameter("GSDM");
		String bddm = "D888";
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D888' AND BDBH ='"+bdbh+"' ";
				
		List<QH_BDLC> BDLCS = new ArrayList<QH_BDLC>();
		
		String qh_user = "SELECT * FROM QH_BDLC WHERE GSDM = '"+gsdm+"' AND BDDM = '"+bddm+"' AND BDBH = '"+bdbh+"' AND YSYH = '"+getUid(req)+"' ";
			
		String sq_user = "SELECT * FROM D888H WHERE GSDM = '"+gsdm+"' AND BDDM = '"+bddm+"' AND BDBH = '"+bdbh+"' AND SQYH = '"+getUid(req)+"'";
	
		if(!(getUserList(qh_user)).isEmpty() || !(getUserList(sq_user)).isEmpty() || getUid(req).equalsIgnoreCase("FELIX.JIANG")||getUid(req).equalsIgnoreCase("LUM.CL")||getUid(req).equalsIgnoreCase("CHICHEN.LIN") ||getUid(req).equalsIgnoreCase("LINDA.HSIEH")){

			d = getVO(sql,gsdm,bddm,bdbh);
			
			Connection con = null;
			
			try {
				con = getConnection();
			
				BDLCS = QH_BDLC.findBy(d.getGSDM(), d.getBDDM(), d.getBDBH(), con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		String QHKS = "Y";
		
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}
		
		req.setAttribute("D888", d);
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);

		String url = "/iMes/D888/EDIT?juid=" + getUid(req)+"&GSDM="+d.getGSDM()+"&BDDM="+d.getBDDM()+"&BDBH="+d.getBDBH()+"";
		req.getSession().setAttribute("URL", url);
		
		boolean qcgly = false;
		for(QH_BDLC qh : BDLCS){
			String bdbhsubA = "";
			try{
				bdbhsubA = bdbh.substring(0, 2);
			}catch(Exception ex){}
			String bdbhsub = "";
			try{
				bdbhsub = bdbh.substring(3, 7);
			}catch(Exception ex){}
			if(qh.getBZDM() == 300 && qh.getYSYH().equals(getUid(req)) && qh.getQHZT().equals("2") && qh.getYSYH().equals(findUserGLY(bdbhsubA+bdbhsub+"GLY"))){
				qcgly = true;
			}
		}
		
		List<String> userlist = findUserZJL();
		req.setAttribute("userlist", userlist);
		
		if(qcgly==true){
			String url1 = "/iMes/D888/EDIT?juid=" + getUid(req)+"&GSDM="+d.getGSDM()+"&BDDM="+d.getBDDM()+"&BDBH="+d.getBDBH()+"";
			req.getSession().setAttribute("URL", url1);
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888QHR.jsp").forward(req, resp);
		}else{
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888UPDATE.jsp").forward(req, resp);
		}
	}

	private void doUpdateGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D888VO hd = new D888VO();
		
		hd.setGSDM(req.getParameter("GSDM"));
		hd.setBDDM("D888");
		hd.setBDBH(req.getParameter("BDBH"));
		hd.setBMMC(req.getParameter("BMMC"));
		hd.setSQYH(getUid(req));//CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,
		hd.setCCLX(req.getParameter("CCLX"));
		hd.setBMBH(req.getParameter("BMBH"));
		hd.setCCBH(req.getParameter("CCBH"));
		hd.setLLBH(req.getParameter("LLBH"));
		hd.setTLBH(req.getParameter("TLBH"));
		hd.setWLBH(req.getParameter("WLBH"));
		hd.setDDBH(req.getParameter("DDBH"));
		hd.setGYSM(req.getParameter("GYSM"));
		hd.setZJE(Double.parseDouble(req.getParameter("ZJE")));
		hd.setCCNR(req.getParameter("CCNR"));
		hd.setBDFD(req.getParameter("BDFD"));
		
		updateDb(hd);
		updateClob2(hd);
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+req.getParameter("GSDM")+"' AND BDDM ='D888' AND BDBH ='"+req.getParameter("BDBH")+"' ";
				
		D888VO d = new D888VO();
		d = getVO(sql,hd.getGSDM(),hd.getBDDM(),hd.getBDBH());
		
		Connection con = null;
		
		List<QH_BDLC> BDLCS = null;
		try {
			con = getConnection();
			
			BDLCS = QH_BDLC.findBy(hd.getGSDM(), hd.getBDDM(), hd.getBDBH(), con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String QHKS = "Y";
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}
		
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);

		req.setAttribute("D888", d);
		
		String url = "/iMes/D888/UPDATE?juid=" + getUid(req)+"&GSDM="+hd.getGSDM()+"&BDDM="+hd.getBDDM()+"&BDBH="+hd.getBDBH()+"";
		req.getSession().setAttribute("URL", url);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888UPDATE.jsp").forward(req, resp);
	}

	private void doDeleteGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	private void doSaveGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		D888VO hd = new D888VO();
		hd.setGSDM(req.getParameter("GSDM"));
		hd.setBDDM("D888");
		hd.setBDBH(getBDBH(req.getParameter("GSDM"), "D888", Helper.fmtDate(new Date(), "yyyy")));
		hd.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		hd.setBMMC(req.getParameter("BMMC"));
		hd.setSQYH(getUid(req));//CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,
		hd.setCCLX(req.getParameter("CCLX"));
		hd.setBMBH(req.getParameter("BMBH"));
		hd.setCCBH(req.getParameter("CCBH"));
		hd.setLLBH(req.getParameter("LLBH"));
		hd.setTLBH(req.getParameter("TLBH"));
		hd.setWLBH(req.getParameter("WLBH"));
		hd.setDDBH(req.getParameter("DDBH"));
		hd.setGYSM(req.getParameter("GYSM"));
		hd.setBDGZ(req.getParameter("BDGZ"));
		try{
			hd.setZJE(Double.parseDouble(req.getParameter("ZJE")));
		}catch(Exception ex){
			hd.setZJE(0);
		}
		hd.setCCNR(req.getParameter("CCNR"));
		hd.setBDFD(req.getParameter("BDFD")==null?"":req.getParameter("BDFD"));
		
		inserClob(hd);		
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ " +
				" FROM D888H WHERE 1=1 ";
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		Connection con = null;
		
		List<QH_BDLC> BDLCS = null;
		try {
			con = getConnection();
			
			BDLCS = QH_BDLC.findBy(hd.getGSDM(), hd.getBDDM(), hd.getBDBH(), con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		

		String QHKS = "Y";
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}
		
		req.setAttribute("D888", hd);
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);
		
		String url = "/iMes/D888/EDIT?juid=" + getUid(req)+"&GSDM="+hd.getGSDM()+"&BDDM="+hd.getBDDM()+"&BDBH="+hd.getBDBH()+"";
		req.getSession().setAttribute("URL", url);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888UPDATE.jsp").forward(req, resp);
	}
	
	private void actionSubmitQH(HttpServletRequest req, HttpServletResponse resp) {
		String gsdm = req.getParameter("GSDM");
		String bddm = req.getParameter("BDDM");
		String bdbh = req.getParameter("BDBH");
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		try{
			con = getConnection();
			
			con.setAutoCommit(false);
	
			String sql = "UPDATE D888H SET BDZT='0', BDJG='0' WHERE GSDM=? AND BDDM=? AND BDBH=?";
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			
			pstm.executeUpdate();

			pstm.close();
	
			QH_BDTX bdtx = new QH_BDTX();
			bdtx.setGSDM(gsdm);
			bdtx.setBDDM(bddm);
			bdtx.setBDBH(bdbh);
			bdtx.setBDTX(req.getParameter("BDTX"));
			
			bdtx.insertDb(con);			
						
			//自定義審核人及核准人 
			String sqyh = getUid(req);
			
			List<QH_BDLC> q = new ArrayList<QH_BDLC>();
			
			QH_BDLC bdlc = null;
			int bzdm = 200;
			String qhzt = "0";
			String zwdm = "Mgr";

			String bdbhsub = "";
			try{
				bdbhsub = bdbh.substring(3, 7).toUpperCase();
			}catch(Exception ex){}
			String bdbhsub1 = "";
			try{
				bdbhsub1 = bdbh.substring(0, 2).toUpperCase();
			}catch(Exception ex){}
			String glry = findUserGLY(bdbhsub1+bdbhsub+"GLY");

			if(isUserManager2(sqyh.toUpperCase())){ //二级审核人
				String manager2 = findUserManager2(sqyh.toUpperCase());
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,1000,zwdm,"R","Y",sqyh,manager2);
				q.add(bdlc);
			}
			
			//签呈管理人员
			bzdm = 300;
			zwdm = "GLR";
			bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,1000,zwdm,"R","Y",sqyh,glry);
			q.add(bdlc);
			
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}

			if((findUserManager1(sqyh.toUpperCase())).isEmpty()){  //一级主管
				QianHe qh = new QianHe();
				qh.setGSDM(req.getParameter("GSDM"));
				qh.setBDDM(req.getParameter("BDDM"));
				qh.setBDBH(req.getParameter("BDBH"));
				qh.setMgr(getUMgr(req));
				qh.setSQYH(getUid(req));
				for (QH_BDLC r : qh.crtRoute(con, "A", req.getParameter("BDAMT"))) {
					r.insertDb(con);
				}
			}
			
			con.commit();
			resp.sendRedirect("/iMes/D888/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
					+ "&STRBDBH=" + req.getParameter("BDBH"));

		} catch(SQLException e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void actionSubmitQHGLY(HttpServletRequest req, HttpServletResponse resp) {
		String gsdm = req.getParameter("GSDM");
		String bddm = req.getParameter("BDDM");
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D888' AND BDBH ='"+bdbh+"' ";
				
		D888VO	d = getVO(sql,gsdm,bddm,bdbh);
		
		Connection con = null;
		try{
			con = getConnection();
			
			con.setAutoCommit(false);			
			//自定義審核人及核准人 
			String strshr = req.getParameter("SHR");
			String [] shr = strshr.split(","); 
			
			String strhqr = req.getParameter("HQR");
			String [] hqr = strhqr.split(","); 
						
			String hjr = req.getParameter("HJR");
			String qhyh = getUid(req);
			
			List<QH_BDLC> q = new ArrayList<QH_BDLC>();
			
			QH_BDLC bdlc = null;
			int i = 0;
			int bzdm = 0;
			String qhzt = "0";
			String zwdm = "";
			
			for(String s : shr){  //1. 審核人
				if(!"".equals(s.trim().toUpperCase())){
					i++;
					bzdm = i*10+400;
					zwdm = "SHR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),s.toUpperCase());
					q.add(bdlc);
				}
			}
			for(String h : hqr){  //3. 會簽人
				if(!"".equals(h.trim())){
					i++;
					bzdm = i*10+400;
					zwdm = "HQR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"I","Y",d.getSQYH().toUpperCase(),h.toUpperCase());
					q.add(bdlc);
				}
			}
			
			bzdm = (i+1) *10+400;
			zwdm = "HJR";
			bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,9999,zwdm,"A","Y",d.getSQYH().toUpperCase(),hjr.toUpperCase());
			q.add(bdlc);
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}
			String qhnr = req.getParameter("QHNR");
			String qhjg = req.getParameter("QHJG");

			QianHeQC(gsdm,bddm,bdbh,300,qhyh.toUpperCase(),qhjg,qhnr,con);
			QianHeZT(gsdm,bddm,bdbh,410,con);
			
			con.commit();
			resp.sendRedirect("/iMes/D888/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
					+ "&STRBDBH=" + req.getParameter("BDBH"));

		} catch(SQLException e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void actionSubmitQHA(HttpServletRequest req, HttpServletResponse resp) {
		String gsdm = req.getParameter("GSDM");
		String bddm = req.getParameter("BDDM");
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D888' AND BDBH ='"+bdbh+"' ";
				
		D888VO d = getVO(sql,gsdm,bddm,bdbh);
		Connection con = null;
		
		QianHe qh = new QianHe();
		qh.setGSDM(d.getGSDM());
		qh.setBDDM(d.getBDDM());
		qh.setBDBH(d.getBDBH());
		qh.setMgr(getUMgr(req));
		qh.setSQYH(getUid(req));
		try {
			con = getConnection();
			
			con.setAutoCommit(false);	
			
			String sqlh = "UPDATE D888H SET BDZT='0', BDJG='0' WHERE GSDM='"+d.getGSDM()+"' AND BDDM='"+d.getBDDM()+"' AND BDBH='"+d.getBDBH()+"'";
			
			updateTableH(sqlh);	// 更新表單狀態！
				
			List<QH_BDLC> bdlcs = qh.crtRoute(con, "P", Double.toString(d.getZJE()));
			
			QH_BDTX bdtx = new QH_BDTX();
			bdtx.setGSDM(d.getGSDM());
			bdtx.setBDDM(d.getBDDM());
			bdtx.setBDBH(d.getBDBH());
			bdtx.setBDTX("-" + d.getBDBH());
			bdtx.insertDb(con);

			for (QH_BDLC bdlc : bdlcs) {

				if (bdlc.getFTXT() == null || bdlc.getFTXT().equals("") || bdlc.getFTXT().equals(" ")) {
					bdlc.insertDb(con);
				}				
				
			}
			
			List<QH_BDLC> q = new ArrayList<QH_BDLC>();
			QH_BDLC bdlc = null;
			
			int bzdm = 200;
			String qhzt = "0";
			String zwdm = "TXSGWY";
			
//			if(d.getZJE() >= 5000 && d.getZJE() < 50000 ){
//				
//				zwdm = getZWDM("ZJL",d.getGSDM());				
//				
//				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,2000,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
//	
//				q.add(bdlc);
//			} else if (d.getZJE() >= 50000 ){
//				zwdm = getZWDM("ZJL","L100");
//				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,2000,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
//				
//				q.add(bdlc);
//			}
			
			String level = getLevel(d.getSQYH().toUpperCase());//申請人級別
			if(level != null && level.equals("KZ")){//申請人級別  課長
				String managerlevel = getLevel(findUserManager(d.getSQYH().toUpperCase()));//申請人主管級別
				//System.out.println("申請人的主管級別："+managerlevel);
				if(managerlevel != null && (managerlevel.equals("JL") || managerlevel.equals("FL")) && !getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase()))).equalsIgnoreCase("ZJL")){//申請人的主管級別是經理、副理
					zwdm = "JL";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager2(d.getSQYH().toUpperCase()));
	
					q.add(bdlc);
					bzdm+=100;
				}
			}else if(level != null && level.equals("JL")){
				//申請人級別  經理
			}else if(level != null && level.equals("FL")){
				//申請人級別  副理
			}else if(level != null && level.equals("XL")){
				//申請人級別  協理
			}else if(level != null && level.equals("ZJL")){
				//申請人級別  總經理
			}else {//申請人級別  一般
				String managerlevel = getLevel(findUserManager(d.getSQYH().toUpperCase()));//申請人主管級別
				//System.out.println("申請人的主管級別："+managerlevel);
				if(managerlevel != null && managerlevel.equals("KZ")&& !managerlevel.equals("ZJL")){//申請人的主管級別是課長
					zwdm = "JL";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager2(d.getSQYH().toUpperCase()));

					q.add(bdlc);
					bzdm+=100;
					
					String manager2level = getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase())));//申請人主管級別
					//System.out.println("申請人的主管的主管級別："+manager2level);
					if(manager2level != null && (manager2level.equals("FL")|| manager2level.equals("JL"))&& !manager2level.equals("ZJL")){
						zwdm = "XL";
						bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager(findUserManager2(d.getSQYH().toUpperCase())));
	
						q.add(bdlc);
						bzdm+=100;
					}
				}else if(managerlevel != null && (managerlevel.equals("FL") || managerlevel.equals("JL"))){//申請人的主管級別是經理或是副理
					String manager2level = getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase())));//申請人主管級別
					//System.out.println("申請人的主管的主管級別："+manager2level);
					if(manager2level != null && !manager2level.equals("ZJL")){
						zwdm = "JL";
						bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager2(d.getSQYH().toUpperCase()));
	
						q.add(bdlc);
						bzdm+=100;
					}
				}else if(managerlevel != null && managerlevel.equals("XL") ){//申請人的主管級別是協理
					// 
				}else if(managerlevel != null && managerlevel.equals("ZJL") ){//申請人的主管級別是總經理
					// 
				}
				
			}
			if(d.getCCLX().equals("成品出廠")){
				zwdm = getZWDM("YWJL",d.getGSDM());
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
				
			}else if(d.getCCLX().equals("樣品出廠")){

				zwdm = getZWDM("RDXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
			}else if(d.getCCLX().equals("安规樣品出廠")){

				zwdm = getZWDM("AGXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
			}else if(d.getCCLX().equals("品保樣品出廠")){

				zwdm = getZWDM("PBXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
			}else if(d.getCCLX().equals("SW樣品出廠")){

				zwdm = getZWDM("SWXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("LINEAR樣品出廠")){

				zwdm = getZWDM("LIXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("材料樣品出廠")){

				zwdm = getZWDM("ZCXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
			}else if(d.getCCLX().equals("材料調撥出廠")){

				zwdm = getZWDM("ZCXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;

				zwdm = getZWDM("GLXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
			}else if(d.getCCLX().equals("固定資產出廠")){

				zwdm = getZWDM("GLXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("材料退貨出廠")){
				zwdm = getZWDM("ZCXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("材料超交出廠")){
				zwdm = getZWDM("CKKZ",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"I","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("ZCXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("資產報廢出廠")){

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("資產異動出廠")){

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				 
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("資產借出出廠")){

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("設備校驗出廠")){

				zwdm = getZWDM("ZZXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;

				zwdm = getZWDM("GLXL",d.getGSDM());
				
				//System.out.println("getLevel : "+findUserManager(findUserManager(d.getSQYH().toUpperCase())) + " fff  "+ findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase()))));
				if(getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase()))).equalsIgnoreCase("JL") && getLevel(findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase())))).equalsIgnoreCase("JL")){
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase()))));
					
					q.add(bdlc);
					bzdm+=100;
				}
				if(getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase()))).equalsIgnoreCase("JL") && getLevel(findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase())))).equalsIgnoreCase("XL")){
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase()))));
					
					q.add(bdlc);
					bzdm+=100;
				}
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
			}else if(d.getCCLX().equals("設備外修出廠")){

				zwdm = getZWDM("ZZXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
					
				q.add(bdlc);
				bzdm+=100;

				zwdm = getZWDM("GLXL",d.getGSDM());

				if(getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase()))).equalsIgnoreCase("JL") && getLevel(findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase())))).equalsIgnoreCase("XL")){
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase()))));
					
					q.add(bdlc);
					bzdm+=100;
				}
				if(getLevel(findUserManager(findUserManager(d.getSQYH().toUpperCase()))).equalsIgnoreCase("JL") && getLevel(findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase())))).equalsIgnoreCase("JL")){
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findUserManager(findUserManager(findUserManager(d.getSQYH().toUpperCase()))));
					
					q.add(bdlc);
					bzdm+=100;
				}
				
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("會辦賬務出廠")){

				zwdm = getZWDM("CWJL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
				
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("雜項物品出廠")){

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("其它物品出廠")){
				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
								
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("其他出廠")||d.getCCLX().equals("下腳料出廠")){

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}else if(d.getCCLX().equals("下腳料出售出廠")){

				zwdm = getZWDM("GLKZ",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;

				zwdm = getZWDM("CWCN",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;

				zwdm = getZWDM("GLXL",d.getGSDM());

				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"A","Y",d.getSQYH().toUpperCase(),findPostUser(zwdm).toUpperCase());
				
				q.add(bdlc);
				bzdm+=100;
			}

			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}
			
			con.commit();
			
			resp.sendRedirect("/iMes/D888/READ?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH="
					+ getParam(req, "BDBH"));

		} catch(SQLException e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(false);
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
		
	private void actionExportExcel(HttpServletRequest req, HttpServletResponse resp) {
		
		resp.reset();//清除Buffer
		String filename = "D888.xls";
		resp.setHeader("Content-Disposition" ,"attachment;filename="+filename);
        resp.setContentType("application/msexcel"); //定义输出类型 
        
		WritableWorkbook wwb = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			
			OutputStream os = resp.getOutputStream();//将 WritableWorkbook 写入到输出流
			
			wwb = Workbook.createWorkbook(os);//创建可写工作薄
			
			WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
			//Label labelCF=new Label(0, 0, "hello");//创建写入位置和内容
			//ws.addCell(labelCF);//将Label写入sheet中
			ws.setColumnView(0,8);
			  
			ws.setColumnView(1,10);
			ws.setColumnView(2,10);
			ws.setColumnView(3,10);
			ws.setColumnView(4,14);
			ws.setColumnView(5,14);
			ws.setColumnView(6,14);
			ws.setColumnView(7,14);
			ws.setColumnView(8,14);
			ws.setColumnView(9,14);

			Label labelCF1 = new Label(0, 0, "序号");
			Label labelCF2 = new Label(1, 0, "公司");			  
			Label labelCF3 = new Label(2, 0, "表单代码");
			Label labelCF4 = new Label(3, 0, "單號");
			Label labelCF5 = new Label(4, 0, "申请人");
			Label labelCF6 = new Label(5, 0, "日期");			  
			Label labelCF7 = new Label(6, 0, "状态");
			Label labelCF8 = new Label(7, 0, "结果");
			Label labelCF9 = new Label(8, 0, "部門名稱");
			Label labelCF10 = new Label(9, 0, "部門編號");
			Label labelCF11 = new Label(10, 0, "出廠类型");
			Label labelCF12 = new Label(11, 0, "領料編號");
			Label labelCF13 = new Label(12, 0, "退料編號");
			Label labelCF14 = new Label(13, 0, "物料編號");
			Label labelCF15 = new Label(14, 0, "訂單編號");
			Label labelCF16 = new Label(15, 0, "總金額");
			Label labelCF17 = new Label(16, 0, "供應商");
			Label labelCF18 = new Label(17, 0, "跟蹤");
			Label labelCF19 = new Label(18, 0, "");
			Label labelCF20 = new Label(19, 0, "流程号");
			Label labelCF21 = new Label(20, 0, "");
			Label labelCF22 = new Label(21, 0, "签核状态");
			Label labelCF23 = new Label(22, 0, "应签核人");
			Label labelCF24 = new Label(23, 0, "实签核人");
			Label labelCF25 = new Label(24, 0, "签核时间");
			Label labelCF26 = new Label(25, 0, "签核结果");
			Label labelCF27 = new Label(26, 0, "签核内容");
			ws.addCell(labelCF1);
			ws.addCell(labelCF2);
			ws.addCell(labelCF3);
			ws.addCell(labelCF4);
			ws.addCell(labelCF5);
			ws.addCell(labelCF6);
			ws.addCell(labelCF7);
			ws.addCell(labelCF8);
			ws.addCell(labelCF9);
			ws.addCell(labelCF10);
			ws.addCell(labelCF11);
			ws.addCell(labelCF12);
			ws.addCell(labelCF13);
			ws.addCell(labelCF14);
			ws.addCell(labelCF15);
			ws.addCell(labelCF16);
			ws.addCell(labelCF17);
			ws.addCell(labelCF18);
			ws.addCell(labelCF19);
			ws.addCell(labelCF20);
			ws.addCell(labelCF21);
			ws.addCell(labelCF22);
			ws.addCell(labelCF23);
			ws.addCell(labelCF24);
			ws.addCell(labelCF25);
			ws.addCell(labelCF26);
			ws.addCell(labelCF27);
		  
			String sqlrst = req.getParameter("SQL");
			
			if(sqlrst!=null && sqlrst.length()>0){
				
				String sql = "SELECT D888.GSDM,D888.BDDM,D888.BDBH,D888.SQYH,"
						+"BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD," 
						+"D888.QHYH,D888.QHSJ,JLYH,D888.JLSJ,GXYH,GXSJ,DYZT," 
						+"QH.YSYH,QH.QHYH QHYH2,QH.QHSJ QHSJ2,QH.QHJG,QH.QHNR,"
						+"QH.LCLX,QH.BZDM,QH.QHZT "
						+" FROM "
						+" ( "+sqlrst+" ) "
						+" D888 "
						+" JOIN QH_BDLC QH ON D888.GSDM = QH.GSDM AND D888.BDDM = QH.BDDM AND D888.BDBH = QH.BDBH ";
			
				ps = con.prepareStatement(sql);//System.out.println("sql :"+sql);
				rs = ps.executeQuery();
				int sn = 0;
		    	while(rs.next()) {
		    		sn++;//System.out.println("GSDM:"+rs.getString("GSDM"));
	                Label data1 = new Label(0, sn, sn+"");
	                Label data2 = new Label(1, sn, rs.getString("GSDM"));
	                Label data3 = new Label(2, sn, rs.getString("BDDM"));
	                Label data4 = new Label(3, sn, rs.getString("BDBH"));
	                Label data5 = new Label(4, sn, rs.getString("SQYH")); 
	                Label data6 = new Label(5, sn,rs.getString("BDRQ"));   
	                Label data7= new Label(6, sn,rs.getString("BDZT"));   
	                Label data8= new Label(7, sn,rs.getString("BDJG"));    
	                Label data9= new Label(8, sn,rs.getString("BMMC"));   
	                Label data10= new Label(9, sn,rs.getString("BMBH"));  
	                Label data11= new Label(10, sn,rs.getString("CCLX"));   
	                Label data12= new Label(11, sn,rs.getString("LLBH"));
					Label data13 = new Label(12, sn,rs.getString("TLBH"));   
					Label data14 = new Label(13, sn,rs.getString("WLBH"));  
					Label data15 = new Label(14, sn,rs.getString("DDBH"));  
					Label data16 = new Label(15, sn,rs.getString("ZJE"));  
					Label data17 = new Label(16, sn,rs.getString("GYSM"));  
					Label data18 = new Label(17, sn,rs.getString("BDGZ"));  
					Label data19 = new Label(18, sn,"");//rs.getString("CCNR"));
					Label data20 = new Label(19, sn,rs.getString("BZDM"));
					Label data21 = new Label(20, sn,rs.getString("LCLX"));
					Label data22 = new Label(21, sn,rs.getString("QHZT"));
					Label data23 = new Label(22, sn,rs.getString("YSYH"));
					Label data24 = new Label(23, sn,rs.getString("QHYH2"));
					Label data25 = new Label(24, sn,rs.getString("QHSJ2"));   
					Label data26 = new Label(25, sn,rs.getString("QHJG"));  
					Label data27 = new Label(26, sn,rs.getString("QHNR"));
					ws.addCell(data1);
					ws.addCell(data2);
					ws.addCell(data3);
					ws.addCell(data4);
					ws.addCell(data5);
					ws.addCell(data6);
					ws.addCell(data7);
					ws.addCell(data8);
					ws.addCell(data9);
					ws.addCell(data10);
					ws.addCell(data11);
					ws.addCell(data12);
					ws.addCell(data13);
					ws.addCell(data14);
					ws.addCell(data15);
					ws.addCell(data16);
					ws.addCell(data17);
					ws.addCell(data18);
					ws.addCell(data19);
					ws.addCell(data20);
					ws.addCell(data21);
					ws.addCell(data22);
					ws.addCell(data23);
					ws.addCell(data24);
					ws.addCell(data25);
					ws.addCell(data26);
					ws.addCell(data27);
		    	}
			    rs.close();
			    ps.close();
			}
			os.flush();
			wwb.write();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(rs!=null){	
				try {		
					rs.close();		
				} catch (Exception e) {		
					e.printStackTrace();		
				}	
			}
			if(ps!=null){	
				try {		
					ps.close();		
				} catch (Exception e) {		
					e.printStackTrace();		
				}	
			}
			if(wwb!=null){	
				try {		
					wwb.close();		
				} catch (Exception e) {		
					e.printStackTrace();		
				}	
			}
			try {		
				con.close();	
			} catch (SQLException e) {		
				e.printStackTrace();		
			}			
		}
	}

	private void actionCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gsdm = req.getParameter("GSDM");
		String bddm = req.getParameter("BDDM");
		String bdbh = req.getParameter("BDBH");
		String zt = req.getParameter("BDZT");
		String jg = req.getParameter("BDJG");
		Connection con = null;
		try{
			con = getConnection();
			Cancel(gsdm, bddm,bdbh,zt,jg,con);
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888.jsp").forward(req, resp);
	}
	
	private void actionSubmitQHR(HttpServletRequest req, HttpServletResponse resp) {
		String gsdm = req.getParameter("GSDM");
		String bddm = req.getParameter("BDDM");
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
				" FROM D888H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D888' AND BDBH ='"+bdbh+"' ";
				
		D888VO	d = getVO(sql,gsdm,bddm,bdbh);
		
		int js = getQH_BDLCPost(gsdm,bddm,bdbh);
		
		Connection con = null;
		try{
			con = getConnection();
			
			con.setAutoCommit(false);			
			//自定義審核人及核准人 
			String strshr = req.getParameter("SHR");
			String [] shr = strshr.split(","); 
			
			String strhqr = req.getParameter("HQR");
			String [] hqr = strhqr.split(","); 
						
			String hjr = req.getParameter("HJR");
			String qhyh = getUid(req);
			
			List<QH_BDLC> q = new ArrayList<QH_BDLC>();
			
			QH_BDLC bdlc = null;
			int i = 0;
			int bzdm = 0;
			String qhzt = "0";
			String zwdm = "";
			
			for(String s : shr){  //1. 審核人
				if(!"".equals(s.trim().toUpperCase())){
					i++;
					bzdm = i*100+js;
					zwdm = "SHR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","N",d.getSQYH().toUpperCase(),s.toUpperCase());
					q.add(bdlc);
				}
			}
			for(String h : hqr){  //3. 會簽人
				if(!"".equals(h.trim())){
					i++;
					bzdm = i*100+js;
					zwdm = "HQR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"I","N",d.getSQYH().toUpperCase(),h.toUpperCase());
					q.add(bdlc);
				}
			}
			
			if(!"".equals(hjr)){
				bzdm = (i+1) *100+js;
				zwdm = "HJR";
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,9999,zwdm,"A","Y",d.getSQYH().toUpperCase(),hjr.toUpperCase());
				q.add(bdlc);
			}
			
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}
			
			
			con.commit();
			resp.sendRedirect("/iMes/D888/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
					+ "&STRBDBH=" + req.getParameter("BDBH"));

		} catch(SQLException e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
	
	public String getUserJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = "";
		String sql = "SELECT USERID FROM USERS ORDER BY USERID";
		try {
			//System.out.println("getUserJson start");
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				str = rst.getString("USERID");
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
	
	private void actionPostQianHe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Connection con = null;
				
		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		int BZDM = toIntegerParam(req, "BZDM");

		String QHNR = req.getParameter("QHNR");
		String QHJG = req.getParameter("QHJG");

		String HQYH = req.getParameter("HQYH");

		String YHCQ = req.getParameter("YHCQ");

		String sql;
		
		try{
			con = getConnection();
			
			con.setAutoCommit(false);
	
			QH_BDLC old = new QH_BDLC();
			old.setGSDM(GSDM);
			old.setBDDM(BDDM);
			old.setBDBH(BDBH);
			old.setBZDM(BZDM);
			try {
				old.find(con);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			QH_BDLC e;
			int i = BZDM;
	
			try {
	
				if (!HQYH.equals("")) {
					for (String str : HQYH.split(",")) {
						if (!str.replaceAll(" ", "").equals("")) {
	
							e = (QH_BDLC) old.clone();
							e.setBZDM(i + 1);
							e.setQHLX("I");
							e.setZWDM("");
							e.setZFYN("N");
							e.setLCLX("P");
							e.setQHZT("1");
							e.setJLSJ(new Date());
							e.setYJSJ(null);
							e.setYSYH(str);
							e.verifyNewBZDM(con);
							e.insertDb(con);
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
					
					e.insertDb(con);
	
				}
	
				sql = "UPDATE QH_BDLC " //
						+ "   SET QHZT='3', " //
						+ "       QHYH=?, " //
						+ "       QHSJ=SYSDATE, " //
						+ "       QHJG=?, " //
						+ "       QHNR=? " //
						// + "       QHFD=? " //
						+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //
	
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, getUid(req));
				pstm.setString(2, QHJG);
				pstm.setString(3, QHNR);
				// pstm.setString(4, "");
				pstm.setString(4, GSDM);
				pstm.setString(5, BDDM);
				pstm.setString(6, BDBH);
				pstm.setInt(7, BZDM);
				
				pstm.executeUpdate();
				
				pstm.close();
	
				con.commit();
				
			} catch (Exception ex) {
				con.rollback();
				ex.printStackTrace();
			}
		}catch(SQLException ex){
			ex.printStackTrace();	
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resp.sendRedirect("/iMes/qh?action=Find&STRQHZT=2&STRYSYH=" + getUid(req));
	}
	
	public boolean getGLYQH(String gsdm,String bddm,String bdbh) throws ServletException, IOException {
		boolean has = false;
		try {

			Connection conn = getConnection();
			if("".equals(gsdm) && QH_BDLC.findBy(gsdm, bddm, bdbh, conn).isEmpty()){
				
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return has;
	}
		
	private int QianHeQC(String GSDM, String BDDM,String BDBH,int BZDM,String USER,String QHJG,String QHNR,Connection conn) {
		String sql = "";
		
		int i = 0;
		
		PreparedStatement pstm = null;
		
		try {	
			sql = "UPDATE QH_BDLC " //
					+ "   SET QHZT='9', " //
					+ "       QHYH=?, " //
					+ "       QHSJ=SYSDATE, " //
					+ "       QHJG=?, " //
					+ "       QHNR=? " //
					+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=? AND QHZT='2'"; //

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, USER);
			pstm.setString(2, QHJG);
			pstm.setString(3, QHNR);
			pstm.setString(4, GSDM);
			pstm.setString(5, BDDM);
			pstm.setString(6, BDBH);
			pstm.setInt(7, BZDM);
			
			i = pstm.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	private int Cancel(String GSDM, String BDDM,String BDBH,String ZT,String JG,Connection conn) {
		String sql = "";
		
		int i = 0;
		
		PreparedStatement pstm = null;
		try {	
			sql = "UPDATE D888 " 
					+ " SET BDZT=? , BDJG=? " 
					+ " WHERE GSDM=? AND BDDM=? AND BDBH=? "; //
	
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, ZT);
			pstm.setString(2, JG);
			pstm.setString(3, GSDM);
			pstm.setString(4, BDDM);
			pstm.setString(5, BDBH);
			
			i = pstm.executeUpdate();
	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
		
	private int QianHeZT(String GSDM, String BDDM,String BDBH,int BZDM,Connection conn) {
		String sql = "";
		
		int i = 0;
		
		PreparedStatement pstm = null;
		try {	
			sql = "UPDATE QH_BDLC " 
					+ " SET QHZT='1' " 
					+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=? AND QHZT='0'"; //
	
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);
			pstm.setInt(4, BZDM);
			
			i = pstm.executeUpdate();
	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	private void doPrintGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Connection con = null;
		
		try {
			con = getConnection();
			
			D888VO hd = new D888VO();
			
			hd.setGSDM(req.getParameter("GSDM"));
			hd.setBDDM("D888");
			hd.setBDBH(req.getParameter("BDBH"));
			hd.setBMMC(req.getParameter("BMMC"));
			hd.setSQYH(getUid(req));
					
			String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,BMMC,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT " +
					" FROM D888H WHERE 1=1 AND GSDM ='"+req.getParameter("GSDM")+"' AND BDDM ='D888' AND BDBH ='"+req.getParameter("BDBH")+"' ";
					
			D888VO d = new D888VO();
			
			d = getVO(sql,hd.getGSDM(),hd.getBDDM(),hd.getBDBH());
			
			List<QH_BDLC> BDLCS = QH_BDLC.findBy(d.getGSDM(), d.getBDDM(), d.getBDBH(), con);
	
			String QHKS = "Y";
			
			if (BDLCS.isEmpty()) {
				QHKS = "N";
			}
			
			req.setAttribute("BDLCS", BDLCS);
			req.setAttribute("QHKS", QHKS);
	
			req.setAttribute("D888", d);
			
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D888Print.jsp").forward(req, resp);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public int updateDb(D888VO D888VO) {
		
		Connection conn = null;
		
		PreparedStatement pstm1 = null;
		
		int i = 0;
		
		String sql = "UPDATE D888H SET BMMC=?,SQYH=?,CCLX=?,BMBH=?,CCBH=?,LLBH=?,TLBH=?,WLBH=?,DDBH=?,ZJE=?,CCNR=? WHERE GSDM=? AND BDDM=? AND BDBH=? ";

		try {
			conn = getConnection();
			
			pstm1 = conn.prepareStatement(sql);
			
			pstm1.setString(1, D888VO.getBMMC());
			pstm1.setString(2, D888VO.getSQYH());//CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,
			pstm1.setString(3, D888VO.getCCLX());
			pstm1.setString(4, D888VO.getBMBH());
			pstm1.setString(5, D888VO.getCCBH());
			pstm1.setString(6, D888VO.getLLBH());
			pstm1.setString(7, D888VO.getTLBH());
			pstm1.setString(8, D888VO.getWLBH());
			pstm1.setString(9, D888VO.getDDBH());
			pstm1.setDouble(10, D888VO.getZJE());
			pstm1.setString(11, D888VO.getCCNR());
			pstm1.setString(12, D888VO.getGSDM());
			pstm1.setString(13, D888VO.getBDDM());
			pstm1.setString(14, D888VO.getBDBH());
			
			i = pstm1.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm1.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	@SuppressWarnings("deprecation")
	public boolean updateClob3(D888VO D888VO) {
		
		Connection conn = null;
		
		PreparedStatement pstm1 = null;
		
		ResultSet rst1 = null;
		
        boolean suc = false;

        CLOB clob = null;
  
        String sql = "SELECT * FROM D888H " +
        	" WHERE GSDM='"+D888VO.getGSDM()+"' AND BDDM='"+D888VO.getBDDM()+"'" +
    		" AND BDBH='"+D888VO.getBDBH()+"' for update ";
        
        try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
 
			pstm1 = conn.prepareStatement(sql);
			
			rst1 = pstm1.executeQuery();  
			
            if(rst1.next()){
                clob = (CLOB)rst1.getClob("CCNR");  
            }            
            
            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream());
            pw.write(D888VO.getCCNR());
  
            pw.flush();  
            pstm1.close();
              
            sql = "UPDATE D888H SET CCNR=? " +
            	  " WHERE GSDM='"+D888VO.getGSDM()+"' AND BDDM='"+D888VO.getBDDM()+"'" +
    			  " AND BDBH='"+D888VO.getBDBH()+"' "; 
            
            pstm1 = conn.prepareStatement(sql);

            pstm1.setClob(1, clob);
            
            pstm1.executeUpdate();

            pstm1.close();
            
            conn.commit();

            pw.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {
			try {				
	            conn.setAutoCommit(true);
	            conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return suc;
    }

	@SuppressWarnings("deprecation")
	public boolean updateClob2(D888VO D888VO) {
		
		Connection conn = null;
		
		PreparedStatement pstm1 = null;
		
		ResultSet rst1 = null;
		
        boolean suc = false;

        CLOB clob = null;
  
        String sql = "SELECT * FROM D888H " +
        	" WHERE GSDM='"+D888VO.getGSDM()+"' AND BDDM='"+D888VO.getBDDM()+"'" +
    		" AND BDBH='"+D888VO.getBDBH()+"' for update ";
        
        try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
 
			pstm1 = conn.prepareStatement(sql);
			
			rst1 = pstm1.executeQuery();  
			
            if(rst1.next()){
                clob = (CLOB)rst1.getClob("CCNR");

                clob.putChars(1, D888VO.getCCNR().toCharArray());

                
                sql = "UPDATE D888H SET CCNR=? " +
                	  " WHERE GSDM='"+D888VO.getGSDM()+"' AND BDDM='"+D888VO.getBDDM()+"'" +
        			  " AND BDBH='"+D888VO.getBDBH()+"' "; 

                pstm1 = conn.prepareStatement(sql);
                
                pstm1.setClob(1,clob);

                pstm1.executeUpdate();
            }            
              
            pstm1.close();
            
            conn.commit();

        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {
			try {				
	            conn.setAutoCommit(true);
	            conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return suc;
    }
	
	public D888VO getVO(String sql,String GSDM,String BDDM,String BDBH) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		D888VO d = new D888VO();
		
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
	
			rst = pstm.executeQuery();
			//CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,
			while(rst.next()){
				d.setGSDM(rst.getString("GSDM"));
				d.setBDDM(rst.getString("BDDM"));
				d.setBDBH(rst.getString("BDBH"));
				d.setSQYH(rst.getString("SQYH"));
				d.setBDRQ(rst.getString("BDRQ"));
				d.setBMMC(rst.getString("BMMC"));
				d.setCCLX(rst.getString("CCLX"));
				d.setBMBH(rst.getString("BMBH"));
				d.setCCBH(rst.getString("CCBH"));
				d.setLLBH(rst.getString("LLBH"));
				d.setTLBH(rst.getString("TLBH"));
				d.setWLBH(rst.getString("WLBH"));
				d.setDDBH(rst.getString("DDBH"));
				d.setGYSM(rst.getString("GYSM"));
				d.setBDGZ(rst.getString("BDGZ"));
				d.setZJE(rst.getDouble("ZJE"));
				d.setCCNR(readClob(GSDM,BDDM,BDBH));
				d.setBDFD(rst.getString("BDFD"));
				d.setQHYH(rst.getString("QHYH"));
				d.setBDZT(rst.getString("BDZT"));
				d.setBDJG(rst.getString("BDJG"));
				d.setDYZT(rst.getString("DYZT"));
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
		return d;		
	}

	public List<HashMap<String, Object>> getList(String sql) {

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
	
	public List<HashMap<String, Object>> insertDb(String sql) {

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

	public D888VO find(String GSDM,String BDDM,String BDBH) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		D888VO D888VO = new D888VO();

		String sql = "SELECT * FROM D888H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			rst = pstm.executeQuery();
			
			if (rst.next()) {
				D888VO.setGSDM(rst.getString("GSDM"));
				D888VO.setBDDM(rst.getString("BDDM"));
				D888VO.setBDBH(rst.getString("BDBH"));
				D888VO.setBDRQ(rst.getString("BDRQ"));
				D888VO.setBMMC(rst.getString("BMMC"));
				D888VO.setSQYH(rst.getString("SQYH"));//CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,ZJE,GYSM,BDGZ,CCNR,
				D888VO.setCCLX(rst.getString("CCLX"));
				D888VO.setBMBH(rst.getString("BMBH"));
				D888VO.setCCBH(rst.getString("CCBH"));
				D888VO.setLLBH(rst.getString("LLBH"));
				D888VO.setTLBH(rst.getString("TLBH"));
				D888VO.setWLBH(rst.getString("WLBH"));
				D888VO.setDDBH(rst.getString("DDBH"));
				D888VO.setGYSM(rst.getString("GYSM"));
				D888VO.setBDGZ(rst.getString("BDGZ"));
				D888VO.setZJE(rst.getDouble("ZJE"));
				D888VO.setCCNR(readClob(GSDM,BDDM,BDBH));
				D888VO.setBDFD(rst.getString("BDFD"));
				D888VO.setQHYH(rst.getString("QHYH"));
				D888VO.setQHYH(rst.getString("QHYH"));
				D888VO.setQHSJ(rst.getTimestamp("QHSJ"));
				D888VO.setJLYH(rst.getString("JLYH"));
				D888VO.setJLSJ(rst.getTimestamp("JLSJ"));
				D888VO.setGXYH(rst.getString("GXYH"));
				D888VO.setGXSJ(rst.getTimestamp("GXSJ"));
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
		return D888VO;
	}

	public String getBDBH(String GSDM, String BDDM, String BDYR) {


		Connection conn = null;
		
		PreparedStatement pstm = null;

		PreparedStatement upstm = null;
		
		ResultSet rst = null;
		
		String sql, BDQZ = "";
		
		int BDHM = 0;

		try {
			conn = getConnection();
			
			sql = "select bdqz,bdhm from qh_bdbh where gsdm=? and bddm=? and bdyr=? for update nowait";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDYR);

			sql = "update qh_bdbh set bdhm = ? where gsdm=? and bddm=? and bdyr=?";
			upstm = conn.prepareStatement(sql);

			for (int i = 0; i < 5; i++) {
				try {
					rst = pstm.executeQuery();
					if (rst.next()) {
						BDQZ = rst.getString("BDQZ");
						BDHM = rst.getInt("BDHM");
						BDHM += 1;
						upstm.setInt(1, BDHM);
						upstm.setString(2, GSDM);
						upstm.setString(3, BDDM);
						upstm.setString(4, BDYR);
						upstm.executeUpdate();
						break;
					}

				} catch (Exception e) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}

			pstm.close();
			upstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rst.close();
				pstm.close();
				upstm.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return BDQZ + Integer.toString(BDHM);
	}

	public List<String> findUserHtml() {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT USERID,NAME FROM USERS ";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				String userid = rst.getString("USERID");
				String name = rst.getString("NAME");
				String html = "<option value=\""+userid+"\">"+name+"-"+userid+"</option>";				
				list.add(html);
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
		return list;
	}

	public List<String> findUserSpan() {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT USERID,NAME FROM USERS ORDER BY USERID";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				String userid = rst.getString("USERID");			
				list.add(userid);
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
		return list;
	}

	public List<String> findUserZJL() {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE SUBSTR(ZWDM,3,3) = 'ZJL'";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				
				String userid = rst.getString("YHDM");
				
				list.add(userid);
				
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
		return list;
	}
	
	public List<String> findUserDiv() {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT USERID,NAME FROM USERS ";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				
				String userid = rst.getString("USERID");
				
				String html = "<input type=\"text\" id=\"SHR\" name=\"SHR\" value="+userid+">";
				
				list.add(html);
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
		return list;
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

	@SuppressWarnings("deprecation")
	public boolean inserClob(D888VO D888VO) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
        boolean suc = false;
        
        CLOB clob = null;
        
        String sql = "INSERT INTO D888H (GSDM,BDDM,BDBH,BDRQ,BMMC," +
        		"SQYH,CCLX,BMBH,CCBH,LLBH,TLBH,WLBH,DDBH,GYSM,BDGZ,ZJE,CCNR,BDFD,QHYH,QHSJ,JLYH," +
        		"JLSJ,GXYH,GXSJ) " +
        		"VALUES ('"+D888VO.getGSDM()+"','"+D888VO.getBDDM()+"'" +
        		",'"+D888VO.getBDBH()+"','"+D888VO.getBDRQ()+"'," +
        		"'"+D888VO.getBMMC()+"','"+D888VO.getSQYH()+"'," +
        		"'"+D888VO.getCCLX()+"','"+D888VO.getBMBH()+"'," +
        		"'"+D888VO.getCCBH()+"','"+D888VO.getLLBH()+"'," +
        		"'"+D888VO.getTLBH()+"','"+D888VO.getWLBH()+"'," +
        		"'"+D888VO.getDDBH()+"','"+D888VO.getGYSM()+"','"+D888VO.getBDGZ()+"'" +
        		",'"+D888VO.getZJE()+"',empty_clob()" +
        		",'"+D888VO.getBDFD()+"','"+D888VO.getQHYH()+"'" +
        		","+Helper.toSqlDate(D888VO.getQHSJ())+",'"+D888VO.getJLYH()+"'" +
        		","+Helper.toSqlDate(D888VO.getJLSJ())+",'"+D888VO.getGXYH()+"'" +
        		","+Helper.toSqlDate(D888VO.getGXSJ())+")";
        
        try {
        	con = getConnection();
        	
            con.setAutoCommit(false);
            
            pstm = con.prepareStatement(sql);
            
            pstm.executeUpdate();
            
            pstm.close();  
            
            sql = "SELECT CCNR FROM D888H " +
            	" WHERE GSDM='"+D888VO.getGSDM()+"' AND BDDM='"+D888VO.getBDDM()+"'" +
        		" AND BDBH='"+D888VO.getBDBH()+"' for update ";
            
            pstm = con.prepareStatement(sql);
            
            rst = pstm.executeQuery();

            if(rst.next()){
            	
                clob = (CLOB)rst.getClob(1);
                
            }
            
            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream()); 

            pw.write(D888VO.getCCNR());

            pw.flush(); 
            
            pstm.close();
              
            sql = "UPDATE D888H SET CCNR=? " +
            	" WHERE GSDM='"+D888VO.getGSDM()+"' AND BDDM='"+D888VO.getBDDM()+"'" +
    			" AND BDBH='"+D888VO.getBDBH()+"' ";

            pstm = con.prepareStatement(sql);
            
            pstm.setClob(1, clob);
            
            pstm.executeUpdate();
            
            pstm.close();
            con.commit();  
            pw.close();
        } catch (SQLException e) {
        	try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            e.printStackTrace();  
        } finally {
			try {				
	            con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 

        return suc;  
    }

    /** 
     * 输出Clob对象 
     * @param  String GSDM,String BDDM,String BDBH
     */  
    public String readClob(String GSDM,String BDDM,String BDBH){ 
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
        String test_clob = "";

        CLOB clob = null;
        
        StringBuffer sb = new StringBuffer();
        
        String sql = "SELECT CCNR FROM D888H WHERE GSDM='"+GSDM+"' AND BDDM='"+BDDM+"' AND BDBH='"+BDBH+"'";
        
        try {
        	con = getConnection();
        	
            pstm = con.prepareStatement(sql);
            
            rst = pstm.executeQuery();
            
            if(rst.next()){
            	
                clob = (CLOB)rst.getClob("CCNR");
                
            }
            if(clob != null){
            	
	            Reader reader = clob.getCharacterStream();
	            
	            char[] buffer = new char[1024];
	            
	            int length = 0;
	            
	            while((length = reader.read(buffer))!=-1){
	            	
	                sb.append(buffer, 0, length);
	                
	            }
            }
        } catch (SQLException e) {

            e.printStackTrace();
            
        } catch (IOException e) {
        	
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
        
        test_clob = sb.toString();

        return test_clob;  
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
}
