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
import imes.vo.D400VO;

@WebServlet({ "/D400", "/D400/*" })
public class D400 extends HttpController {
	
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
					if(getUid(req).equalsIgnoreCase("FELIX.JIANG")||getUid(req).equalsIgnoreCase("LUM.CL")||getUid(req).equalsIgnoreCase("CHICHEN.LIN") ||getUid(req).equalsIgnoreCase("LINDA.HSIEH") ){
						doAdminListGetAction(req, resp);
					//}else if(getUserAllow(getUid(req))){
						//doAllowListGetAction(req,resp);//
					}else{
						doListGetD40RHeJunAction(req, resp);
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
					
					actionSubmitQH(req, resp);
					
				} else if (action.equals("GLYQH")) {

					actionSubmitQHGLY(req, resp);
					
				} else if (action.equals("Print")) {

					doPrintGetAction(req, resp);
					
				} else if (action.equals("ADDQH")) {

					doADDQHGetAction(req, resp);
					
				} else if (action.equals("ADDQHR")) {

					actionSubmitQHR(req, resp);
					
				} else if (action.equals("EXPORTEXCEL")) {

					actionExportExcel(req, resp);
					
				}
			}
			if (action2 != null && action2.equals("QianHe")) {

				if(req.getParameter("BZDM")!= null && !"".equals(req.getParameter("BZDM"))){

					actionPostQianHe(req, resp);
					
				} else {
					
					resp.sendRedirect("/iMes/D400/EDIT?GSDM="+req.getParameter("GSDM")+"&BDDM="+req.getParameter("BDDM")+"&BDBH="+req.getParameter("BDBH")+"&HQYH="+getUid(req)+"");
					
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

		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR1,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 "; //
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
		
		String qclx = req.getParameter("QCLX");
		
		if(qclx != null && !"".equals(qclx)){
			sql += " AND QCLX = '"+qclx+"' ";
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
		
		String dyzt = req.getParameter("DYZT");

		if(dyzt != null && !"ALL".equals(dyzt)){
			if("1".equals(dyzt)){
				sql += " AND (DYZT = '1')";
			}else if("0".equals(dyzt)){
				sql += " AND (DYZT is null or NOT DYZT in ('1')) ";
			}
		}
		
		String sqyh = req.getParameter("SQYH");
		
		if(sqyh != null && !"".equals(sqyh)){
			sql += " AND UPPER(SQYH) like '%'||UPPER('"+sqyh+"')||'%' ";
		}
		
		String dyyh = req.getParameter("DYYH");
		
		if(dyyh != null && !"".equals(dyyh)){
			sql += " AND UPPER(DYYH) like '%'||UPPER('"+dyyh+"')||'%' ";
		}
		
		sql += " ORDER BY GSDM,BDBH";//System.out.println("sql : "+sql);

		req.setAttribute("D400sqlrst", sql);
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND400 = 'x'";

		String KZ_USER = "N";
		
		if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400.jsp").forward(req, resp);
	}
	
	private void doAllowListGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR1,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 "; //
		
		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		
		String strbdrq = req.getParameter("STRBDRQ");
		String endbdrq = req.getParameter("ENDBDRQ");
		
		if(strbdrq != null && endbdrq != null && !"".equals(strbdrq) && !"".equals(endbdrq)){
			sql += " AND ((BDRQ >= '"+strbdrq+"' AND BDRQ <= '"+endbdrq+"') ";
		} else {
			Calendar oldCal = Calendar.getInstance();
			oldCal.add(Calendar.MONTH, -1);
			sql += " AND ((BDRQ >= '" + Helper.fmtDate(oldCal.getTime(), "yyyyMMdd") + "' AND BDRQ <= '" + Helper.fmtDate(new Date(), "yyyyMMdd") + "')";
		}
		
		String qclx = req.getParameter("QCLX");
		
		if(qclx != null && !"".equals(qclx)){
			sql += " AND QCLX = '"+qclx+"' ";
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
		
		String dyzt = req.getParameter("DYZT");
		
		if(dyzt != null && !"ALL".equals(dyzt)){
			if("1".equals(dyzt)){
				sql += " AND (DYZT = '1')";
			}else if("0".equals(dyzt)){
				sql += " AND (DYZT is null or NOT DYZT in ('1')) ";
			}
		}
		
		String sqyh = req.getParameter("SQYH");
		
		if(sqyh != null && !"".equals(sqyh)){
			sql += " AND UPPER(SQYH) like '%'||UPPER('"+sqyh+"')||'%' ";
		}
		
		String dyyh = req.getParameter("DYYH");
		
		if(dyyh != null && !"".equals(dyyh)){
			sql += " AND UPPER(DYYH) like '%'||UPPER('"+dyyh+"')||'%' ";
		}

		sql += " AND (BDBH IN ( SELECT BDBH FROM QH_BDLC WHERE 1=1 ";
		sql += " AND YSYH = '"+getUid(req)+"') OR BDBH IN ( ";
		sql += " SELECT BDBH FROM D400H WHERE 1=1 ";
		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlParams(req, "BDRQ", "STRBDRQ", "ENDBDRQ");
		sql += " AND SQYH = '"+getUid(req)+"')))"; 
		
		//if(condition.length()>0){
		//	sql = sql + " "+ condition ;
		//}
		
		sql += " ORDER BY GSDM,BDBH";//System.out.println("doAllowListGetAction sql : "+sql);

		req.setAttribute("D400sqlrst", sql);
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND400 = 'x'";

		String KZ_USER = "N";
		
		if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400.jsp").forward(req, resp);
	}

	private void doListGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR1,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 "; //
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
		
		String qclx = req.getParameter("QCLX");
		
		if(qclx != null && !"".equals(qclx)){
			sql += " AND QCLX = '"+qclx+"' ";
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
		
		String dyzt = req.getParameter("DYZT");
		
		if(dyzt != null && !"ALL".equals(dyzt)){
			if("1".equals(dyzt)){
				sql += " AND (DYZT = '1')";
			}else if("0".equals(dyzt)){
				sql += " AND (DYZT is null or NOT DYZT in ('1')) ";
			}
		}
		
		String sqyh = req.getParameter("SQYH");
		
		if(sqyh != null && !"".equals(sqyh)){
			sql += " AND UPPER(SQYH) like '%'||UPPER('"+sqyh+"')||'%' ";
		}
		
		String dyyh = req.getParameter("DYYH");
		
		if(dyyh != null && !"".equals(dyyh)){
			sql += " AND UPPER(DYYH) like '%'||UPPER('"+dyyh+"')||'%' ";
		}

		sql += " AND (BDBH IN ( SELECT BDBH FROM QH_BDLC WHERE 1=1 ";
		sql += " AND YSYH = '"+getUid(req)+"') OR BDBH IN ( ";
		sql += " SELECT BDBH FROM D400H WHERE 1=1 ";
		sql += Helper.sqlParams(req, "GSDM", "STRGSDM", "ENDGSDM");
		sql += Helper.sqlParams(req, "BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlParams(req, "BDRQ", "STRBDRQ", "ENDBDRQ");
		sql += " AND SQYH = '"+getUid(req)+"'))"; 
		
		sql += " ORDER BY GSDM,BDBH";//System.out.println("doListGetAction sql : "+sql);

		req.setAttribute("D400sqlrst", sql);
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND400 = 'x'";

		String KZ_USER = "N";
		
		if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400.jsp").forward(req, resp);
	}
	
	private void doListGetD40RHeJunAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR1,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 "; //
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
		
		String qclx = req.getParameter("QCLX");
		
		if(qclx != null && !"".equals(qclx)){
			sql += " AND QCLX = '"+qclx+"' ";
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
		
		String dyzt = req.getParameter("DYZT");
		
		if(dyzt != null && !"ALL".equals(dyzt)){
			if("1".equals(dyzt)){
				sql += " AND (DYZT = '1')";
			}else if("0".equals(dyzt)){
				sql += " AND (DYZT is null or NOT DYZT in ('1')) ";
			}
		}
		
		String sqyh = req.getParameter("SQYH");
		
		if(sqyh != null && !"".equals(sqyh)){
			sql += " AND UPPER(SQYH) like '%'||UPPER('"+sqyh+"')||'%' ";
		}
		
		String dyyh = req.getParameter("DYYH");
		
		if(dyyh != null && !"".equals(dyyh)){
			sql += " AND UPPER(DYYH) like '%'||UPPER('"+dyyh+"')||'%' ";
		}
		
		sql += " AND (BDBH IN ( SELECT BDBH FROM QH_BDLC WHERE 1=1 AND YSYH = '"+getUid(req)+"' AND substr(BDBH, 4, 4)<>'D40R' ) ";
		sql += " OR BDBH IN ( SELECT BDBH FROM D400H WHERE 1=1 AND SQYH = '"+getUid(req)+"' ) ";
		sql += " OR BDBH IN ( SELECT BDBH FROM QH_BDLC WHERE 1=1 AND YSYH = '"+getUid(req)+"' AND QHLX <> 'A' AND substr(BDBH, 4, 4)='D40R' AND BDBH IN (SELECT BDBH FROM QH_BDLC WHERE (QHLX NOT IN ('A') OR (QHJG IS NULL AND QHLX = 'A')) AND substr(BDBH, 4, 4)='D40R')) ";
		sql += " OR BDBH IN ( SELECT BDBH FROM D400H WHERE 1=1 AND BDJG ='Y' AND QHYH = '"+getUid(req)+"' )  ";
		sql += " OR BDBH IN ( SELECT BDBH FROM QH_BDLC WHERE 1=1 AND YSYH = '"+getUid(req)+"' AND SUBSTR(ZWDM,3,7) = 'D40RGLY' AND substr(BDBH, 4, 4)='D40R' ) ) ";
		
		sql += " ORDER BY GSDM,BDBH";System.out.println("doListGetD40RHeJunAction sql : "+sql);

		req.setAttribute("D400sqlrst", sql);
		
		List<HashMap<String, Object>> list = getList(sql);

		req.setAttribute("list", list);
		
		String kz_user = "SELECT * FROM USERS WHERE USERID = '"+getUid(req)+"' AND FUND400 = 'x'";

		String KZ_USER = "N";
		
		if (!(getUserList(kz_user).isEmpty())) {
			KZ_USER = "Y";
		}

		req.setAttribute("kz_user", KZ_USER);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400.jsp").forward(req, resp);
	}

	private void doCreateGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400New.jsp").forward(req, resp);
		
	}

	private void doADDQHGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D400VO d = new D400VO();
		String gsdm = req.getParameter("GSDM");
		String bddm = "D400";
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D400' AND BDBH ='"+bdbh+"' ";

		d = getVO(sql,gsdm,bddm,bdbh);

		List<String> userlist = findUserZJL();
		req.setAttribute("userlist", userlist);
		
		req.setAttribute("D400", d);
		
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400ADDQHR.jsp").forward(req, resp);
		
	}

	private void doReadGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	private void doEditGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D400VO d = new D400VO();
		String gsdm = req.getParameter("GSDM");
		String bddm = "D400";
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D400' AND BDBH ='"+bdbh+"' ";
				
		List<QH_BDLC> BDLCS = new ArrayList<QH_BDLC>();
		
		String qh_user = "SELECT * FROM QH_BDLC WHERE GSDM = '"+gsdm+"' AND BDDM = '"+bddm+"' AND BDBH = '"+bdbh+"' AND YSYH = '"+getUid(req)+"' ";
			
		String sq_user = "SELECT * FROM D400H WHERE GSDM = '"+gsdm+"' AND BDDM = '"+bddm+"' AND BDBH = '"+bdbh+"' AND SQYH = '"+getUid(req)+"'";
	
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
		String QHJG = "Y";
				
		if (BDLCS.isEmpty()) {
			QHKS = "N";
		}else{
			try{
				for(QH_BDLC e :BDLCS){
					if(Helper.toUpperCase(e.getGSDM()).equals("L300")||Helper.toUpperCase(e.getGSDM()).equals("L210"))
					{
						if(Helper.toUpperCase(e.getQHLX()).equals("A")&&Helper.toUpperCase(e.getQHJG()).equals("N")){
							QHJG = "N";
						}
						if(Helper.toUpperCase(e.getQHLX()).equals("Z")){
							QHJG = "N";
						}
					}
				}
			}catch(Exception e){}
		}
		
		req.setAttribute("D400", d);
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);
		req.setAttribute("QHJG", QHJG);

		String url = "/iMes/D400/EDIT?juid=" + getUid(req)+"&GSDM="+d.getGSDM()+"&BDDM="+d.getBDDM()+"&BDBH="+d.getBDBH()+"";
		req.getSession().setAttribute("URL", url);
		
		boolean qcgly = false;
		for(QH_BDLC qh : BDLCS){
			String bdbhsubA = bdbh.substring(0, 2);
			String bdbhsub = bdbh.substring(3, 7);
			try{
				if(Helper.toUpperCase(qh.getZWDM()).equals(bdbhsubA+bdbhsub+"GLY") && Helper.toUpperCase(qh.getYSYH()).equals(getUid(req)) && Helper.toUpperCase(qh.getQHZT()).equals("2") && Helper.toUpperCase(qh.getYSYH()).equals(findUserGLY(bdbhsubA+bdbhsub+"GLY"))){
					qcgly = true;
				}
			}catch(Exception e){}
		}
		
		List<String> userlist = findUserZJL();
		req.setAttribute("userlist", userlist);
		
		if(qcgly==true){
			String url1 = "/iMes/D400/EDIT?juid=" + getUid(req)+"&GSDM="+d.getGSDM()+"&BDDM="+d.getBDDM()+"&BDBH="+d.getBDBH()+"";
			req.getSession().setAttribute("URL", url1);
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400QHR.jsp").forward(req, resp);
		}else{
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400UPDATE.jsp").forward(req, resp);
		}
	}

	private void doUpdateGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		D400VO hd = new D400VO();
		
		hd.setGSDM(req.getParameter("GSDM"));
		hd.setBDDM("D400");
		hd.setBDBH(req.getParameter("BDBH"));
		hd.setBMMC(req.getParameter("BMMC"));
		hd.setSQYH(getUid(req));
		hd.setQCLX(req.getParameter("QCLX"));
		hd.setWJSX(req.getParameter("WJSX"));
		hd.setWJJM(req.getParameter("WJJM"));
		hd.setQCAY(req.getParameter("QCAY"));
		hd.setQCNR(req.getParameter("QCNR"));
		hd.setBDFD(req.getParameter("BDFD"));
		
		updateDb(hd);
		updateClob2(hd);
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 AND GSDM ='"+req.getParameter("GSDM")+"' AND BDDM ='D400' AND BDBH ='"+req.getParameter("BDBH")+"' ";
				
		D400VO d = new D400VO();
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

		req.setAttribute("D400", d);
		
		String url = "/iMes/D400/UPDATE?juid=" + getUid(req)+"&GSDM="+hd.getGSDM()+"&BDDM="+hd.getBDDM()+"&BDBH="+hd.getBDBH()+"";
		req.getSession().setAttribute("URL", url);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400UPDATE.jsp").forward(req, resp);
	}

	private void doDeleteGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	private void doSaveGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		D400VO hd = new D400VO();
		String strqclx = req.getParameter("QCLX");
		String lx = "";
		if(strqclx.equals("人事")){
			lx = "D40R";
		}else if(strqclx.equals("獎懲")){
			lx = "D40J";
		}else if(strqclx.equals("規章")){
			lx = "D40G";
		}else if(strqclx.equals("異常")){
			lx = "D40N";
		}else if(strqclx.equals("一般")){
			lx = "D40Y";
		}
		lx = "%"+lx+"%";
		
		hd.setGSDM(req.getParameter("GSDM"));
		hd.setBDDM("D400");
		hd.setBDBH(getBDBH(req.getParameter("GSDM"), "D400", lx, Helper.fmtDate(new Date(), "yyyy")));
		hd.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		hd.setBMMC(req.getParameter("BMMC"));
		hd.setSQYH(getUid(req));
		hd.setQCLX(req.getParameter("QCLX"));
		hd.setWJSX(req.getParameter("WJSX"));
		hd.setWJJM(req.getParameter("WJJM"));
		hd.setQCAY(req.getParameter("QCAY"));
		hd.setQCNR(req.getParameter("QCNR"));
		hd.setBDFD(req.getParameter("BDFD")==null?"":req.getParameter("BDFD"));
		
		inserClob(hd);		
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ " +
				" FROM D400H WHERE 1=1 ";
		
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
		
		req.setAttribute("D400", hd);
		req.setAttribute("BDLCS", BDLCS);
		req.setAttribute("QHKS", QHKS);
		
		String url = "/iMes/D400/EDIT?juid=" + getUid(req)+"&GSDM="+hd.getGSDM()+"&BDDM="+hd.getBDDM()+"&BDBH="+hd.getBDBH()+"";
		req.getSession().setAttribute("URL", url);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400UPDATE.jsp").forward(req, resp);
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
	
			String sql = "UPDATE D400H SET BDZT='0', BDJG='0' WHERE GSDM=? AND BDDM=? AND BDBH=?";
			
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
			String zwdm = "MGR";

			String bdbhsub = bdbh.substring(3, 7).toUpperCase();
			String bdbhsub1 = bdbh.substring(0, 2).toUpperCase();
			String glry = findUserGLY(bdbhsub1+bdbhsub+"GLY");

			if(isUserManager2(sqyh.toUpperCase())){ //二级审核人				
				String manager2 = findUserManager2(sqyh.toUpperCase());
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,1000,zwdm,"R","Y",sqyh,manager2);
				q.add(bdlc);
			}
			
			
			//签呈管理人员
			bzdm = 300;
			zwdm = bdbhsub1+bdbhsub+"GLY";
			bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,1000,zwdm,"R","Y",sqyh,glry);
			q.add(bdlc);

			bzdm = 9000;
			zwdm = "GLY9000";
			bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,1000,zwdm,"R","Y",sqyh,glry);
			q.add(bdlc);			
			
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}

			//if((findUserManager1(sqyh.toUpperCase())).isEmpty()){  //一级主管				
				QianHe qh = new QianHe();
				qh.setGSDM(req.getParameter("GSDM"));
				qh.setBDDM(req.getParameter("BDDM"));
				qh.setBDBH(req.getParameter("BDBH"));
				qh.setMgr(getUMgr(req));
				qh.setSQYH(getUid(req));
				for (QH_BDLC r : qh.crtRoute(con, "A", req.getParameter("BDAMT"))) {
					r.insertDb(con);
				}
			//}
			
			con.commit();
			resp.sendRedirect("/iMes/D400/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
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
		String sbzdm = req.getParameter("BZDM");
		int garm_bzdm = 0;

		try{
			garm_bzdm = Integer.parseInt(sbzdm);
		}catch(Exception e){}
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D400' AND BDBH ='"+bdbh+"' ";
				
		D400VO	d = getVO(sql,gsdm,bddm,bdbh);
		
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

			String strzxr = req.getParameter("ZXR");//知悉人
			String [] zxr = strzxr.split(","); 
			
			List<QH_BDLC> q = new ArrayList<QH_BDLC>();
			
			QH_BDLC bdlc = null;
			int i = 0;
			int bzdm = 0;
			
			String qhzt = "0";
			String zwdm = "";
			

			for(String h : hqr){  //1. 會簽人
				if(!"".equals(h.trim())){
					i++;
					bzdm = i*10+400;
					zwdm = "HQR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"I","Y",d.getSQYH().toUpperCase(),h.toUpperCase());
					q.add(bdlc);
				}
			}
			
			for(String s : shr){  //2. 審核人
				if(!"".equals(s.trim().toUpperCase())){
					i++;
					bzdm = i*10+400;
					zwdm = "SHR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","Y",d.getSQYH().toUpperCase(),s.toUpperCase());
					q.add(bdlc);
				}
			}
			
			if(!"".equals(hjr))
			{
				bzdm = (i+1) *10+400;
				zwdm = "HJR";  //3. 核準人
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,9999,zwdm,"A","Y",d.getSQYH().toUpperCase(),hjr.toUpperCase());
				q.add(bdlc);
			}
			
			i++;
			for(String z : zxr){  //4. 知悉人
				if(!"".equals(z.trim())){
					i++;
					bzdm = i*10+400;
					zwdm = "ZXR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"I","Y",d.getSQYH().toUpperCase(),z.toUpperCase());
					q.add(bdlc);
				}
			}
			
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}
			String qhnr = req.getParameter("QHNR");
			String qhjg = req.getParameter("QHJG");
			
			QianHeQC(gsdm,bddm,bdbh,garm_bzdm,qhyh.toUpperCase(),qhjg,qhnr,con);
			QianHeZT(gsdm,bddm,bdbh,410,con);
			
			con.commit();
			resp.sendRedirect("/iMes/D400/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
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
		
	private void actionExportExcel(HttpServletRequest req, HttpServletResponse resp) {
		
		resp.reset();//清除Buffer
		String filename = "D400.xls";
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
			Label labelCF9 = new Label(8, 0, "类型");
			Label labelCF10 = new Label(9, 0, "部门");
			Label labelCF11 = new Label(10, 0, "文件");
			Label labelCF12 = new Label(11, 0, "机密");
			Label labelCF13 = new Label(12, 0, "案由");
			Label labelCF14 = new Label(13, 0, "流程号");
			Label labelCF15 = new Label(14, 0, "");
			Label labelCF16 = new Label(15, 0, "签核状态");
			Label labelCF17 = new Label(16, 0, "应签核人");
			Label labelCF18 = new Label(17, 0, "实签核人");
			Label labelCF19 = new Label(18, 0, "签核时间");
			Label labelCF20 = new Label(19, 0, "签核结果");
			Label labelCF21 = new Label(20, 0, "签核内容");
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
		  
			String sqlrst = req.getParameter("SQL");
			
			if(sqlrst!=null && sqlrst.length()>0){
				
				String sql = "SELECT D400.GSDM,D400.BDDM,D400.BDBH,D400.SQYH,"
						+"BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR1,BDFD," 
						+"D400.QHYH,D400.QHSJ,JLYH,D400.JLSJ,GXYH,GXSJ,DYZT,DYYH," 
						+"QH.YSYH,QH.QHYH QHYH2,QH.QHSJ QHSJ2,QH.QHJG,QH.QHNR,"
						+"QH.LCLX,QH.BZDM,QH.QHZT "
						+" FROM "
						+" ( "+sqlrst+" ) "
						+" D400 "
						+" JOIN QH_BDLC QH ON D400.GSDM = QH.GSDM AND D400.BDDM = QH.BDDM AND D400.BDBH = QH.BDBH ";
			
				ps = con.prepareStatement(sql);//System.out.println("sql :"+sql);
				rs = ps.executeQuery();
				int sn = 0;
		    	while(rs.next()) {
		    		sn++;//System.out.println("GSDM:"+rs.getString("GSDM"));
	                Label data1 = new Label(0, sn, sn+"");
	                Label data2 = new Label(1, sn, rs.getString("GSDM"));
	                Label data3 = new Label(2, sn, rs.getString("BDDM"));
	                Label data4 = new Label(3, sn, rs.getString("BDBH"));
					//Number data5 = new Number(4, sn,Float.parseFloat(df.format(rs.getBigDecimal("SQYH"))), priceformat);
	                Label data5 = new Label(4, sn, rs.getString("SQYH")); 
	                Label data6 = new Label(5, sn,rs.getString("BDRQ"));   
	                Label data7= new Label(6, sn,rs.getString("BDZT"));   
	                Label data8= new Label(7, sn,rs.getString("BDJG"));   
	                Label data9= new Label(8, sn,rs.getString("QCLX"));   
	                Label data10= new Label(9, sn,rs.getString("BMMC"));
					Label data11 = new Label(10, sn,rs.getString("WJSX"));   
					Label data12 = new Label(11, sn,rs.getString("WJJM"));  
					Label data13 = new Label(12, sn,rs.getString("QCAY"));
					Label data14 = new Label(13, sn,rs.getString("BZDM"));
					Label data15 = new Label(14, sn,rs.getString("LCLX"));
					Label data16 = new Label(15, sn,rs.getString("QHZT"));
					Label data17 = new Label(16, sn,rs.getString("YSYH"));
					Label data18 = new Label(17, sn,rs.getString("QHYH2"));
					Label data19 = new Label(18, sn,rs.getString("QHSJ2"));   
					Label data20 = new Label(19, sn,rs.getString("QHJG"));  
					Label data21 = new Label(20, sn,rs.getString("QHNR"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void actionSubmitQHR(HttpServletRequest req, HttpServletResponse resp) {
		String gsdm = req.getParameter("GSDM");
		String bddm = req.getParameter("BDDM");
		String bdbh = req.getParameter("BDBH");
		
		String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
				" FROM D400H WHERE 1=1 AND GSDM ='"+gsdm+"' AND BDDM ='D400' AND BDBH ='"+bdbh+"' ";
				
		D400VO	d = getVO(sql,gsdm,bddm,bdbh);
		
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
			//String qhyh = getUid(req);
			
			List<QH_BDLC> q = new ArrayList<QH_BDLC>();
			
			QH_BDLC bdlc = null;
			int i = 0;
			int bzdm = 0;
			String qhzt = "0";
			String zwdm = "";

			for(String h : hqr){  //3. 會簽人
				if(!"".equals(h.trim())){
					i++;
					bzdm = i*100+js;
					zwdm = "HQR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"I","N",d.getSQYH().toUpperCase(),h.toUpperCase());
					q.add(bdlc);
				}
			}
			for(String s : shr){  //1. 審核人
				if(!"".equals(s.trim().toUpperCase())){
					i++;
					bzdm = i*100+js;
					zwdm = "SHR";
					bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,999,zwdm,"R","N",d.getSQYH().toUpperCase(),s.toUpperCase());
					q.add(bdlc);
				}
			}
			
			if(!"".equals(hjr)){  //2. 核准人
				bzdm = (i+1) *100+js;
				zwdm = "HJR";
				bdlc = addQHE(gsdm,bddm,bdbh,qhzt,bzdm,9999,zwdm,"A","Y",d.getSQYH().toUpperCase(),hjr.toUpperCase());
				q.add(bdlc);
			}
			
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
			}
			
			
			con.commit();
			resp.sendRedirect("/iMes/D400/LIST?STRGSDM=" + req.getParameter("GSDM") + "&STRBDDM=" + req.getParameter("BDDM")
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
			
			D400VO hd = new D400VO();
			
			hd.setGSDM(req.getParameter("GSDM"));
			hd.setBDDM("D400");
			hd.setBDBH(req.getParameter("BDBH"));
			hd.setBMMC(req.getParameter("BMMC"));
			hd.setSQYH(getUid(req));
					
			String sql = "SELECT GSDM,BDDM,BDBH,SQYH,BDRQ,BDZT,BDJG,QCLX,BMMC,WJSX,WJJM,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,DYZT,DYYH " +
					" FROM D400H WHERE 1=1 AND GSDM ='"+req.getParameter("GSDM")+"' AND BDDM ='D400' AND BDBH ='"+req.getParameter("BDBH")+"' ";
					
			D400VO d = new D400VO();
			
			d = getVO(sql,hd.getGSDM(),hd.getBDDM(),hd.getBDBH());
			
			List<QH_BDLC> BDLCS = QH_BDLC.findBy(d.getGSDM(), d.getBDDM(), d.getBDBH(), con);
	
			String QHKS = "Y";
			
			if (BDLCS.isEmpty()) {
				QHKS = "N";
			}
			
			req.setAttribute("BDLCS", BDLCS);
			req.setAttribute("QHKS", QHKS);
	
			req.setAttribute("D400", d);
			
			getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D400Print.jsp").forward(req, resp);

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
		
	public int updateDb(D400VO d400VO) {
		
		Connection conn = null;
		
		PreparedStatement pstm1 = null;
		
		int i = 0;
		
		String sql = "UPDATE D400H SET BMMC=?,SQYH=?,QCLX=?,WJSX=?,QCAY=?,WJJM=? WHERE GSDM=? AND BDDM=? AND BDBH=? ";

		try {
			conn = getConnection();
			
			pstm1 = conn.prepareStatement(sql);
			
			pstm1.setString(1, d400VO.getBMMC());
			pstm1.setString(2, d400VO.getSQYH());
			pstm1.setString(3, d400VO.getQCLX());
			pstm1.setString(4, d400VO.getWJSX());
			pstm1.setString(5, d400VO.getQCAY());
			pstm1.setString(6, d400VO.getWJJM());
			pstm1.setString(7, d400VO.getGSDM());
			pstm1.setString(8, d400VO.getBDDM());
			pstm1.setString(9, d400VO.getBDBH());
			
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
	public boolean updateClob3(D400VO d400VO) {
		
		Connection conn = null;
		
		PreparedStatement pstm1 = null;
		
		ResultSet rst1 = null;
		
        boolean suc = false;

        CLOB clob = null;
  
        String sql = "SELECT * FROM D400H " +
        	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    		" AND BDBH='"+d400VO.getBDBH()+"' for update ";
        
        try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
 
			pstm1 = conn.prepareStatement(sql);
			
			rst1 = pstm1.executeQuery();  
			
            if(rst1.next()){
                clob = (CLOB)rst1.getClob("QCNR1");  
            }            
            
            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream());
            pw.write(d400VO.getQCNR());
  
            pw.flush();  
            pstm1.close();
              
            sql = "UPDATE D400H SET QCNR1=? " +
            	  " WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    			  " AND BDBH='"+d400VO.getBDBH()+"' "; 
            
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

	public boolean updateClob2(D400VO d400VO) {
		
		Connection conn = null;
		
		PreparedStatement pstm1 = null;
		
		ResultSet rst1 = null;
		
        boolean suc = false;

        CLOB clob = null;
  
        String sql = "SELECT * FROM D400H " +
        	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    		" AND BDBH='"+d400VO.getBDBH()+"' for update ";
        
        try {
			conn = getConnection();
			
			conn.setAutoCommit(false);
 
			pstm1 = conn.prepareStatement(sql);
			
			rst1 = pstm1.executeQuery();  
			
            if(rst1.next()){
                clob = (CLOB)rst1.getClob("QCNR1");

                clob.putChars(1, d400VO.getQCNR().toCharArray());

                
                sql = "UPDATE D400H SET QCNR1=? " +
                	  " WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
        			  " AND BDBH='"+d400VO.getBDBH()+"' "; 

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
	
	public D400VO getVO(String sql,String GSDM,String BDDM,String BDBH) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		D400VO d = new D400VO();
		
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
	
			rst = pstm.executeQuery();
			
			while(rst.next()){
				d.setGSDM(rst.getString("GSDM"));
				d.setBDDM(rst.getString("BDDM"));
				d.setBDBH(rst.getString("BDBH"));
				d.setSQYH(rst.getString("SQYH"));
				d.setBDRQ(rst.getString("BDRQ"));
				d.setQCLX(rst.getString("QCLX"));
				d.setBMMC(rst.getString("BMMC"));
				d.setWJSX(rst.getString("WJSX"));
				d.setWJJM(rst.getString("WJJM"));
				d.setQCAY(rst.getString("QCAY"));
				d.setQCNR(readClob(GSDM,BDDM,BDBH));
				d.setBDFD(rst.getString("BDFD"));
				d.setQHYH(rst.getString("QHYH"));
				d.setBDZT(rst.getString("BDZT"));
				d.setBDJG(rst.getString("BDJG"));
				d.setDYZT(rst.getString("DYZT"));
				d.setDYYH(rst.getString("DYYH"));
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
	

	public D400VO find(String GSDM,String BDDM,String BDBH) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		D400VO d400VO = new D400VO();

		String sql = "SELECT * FROM D400H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			rst = pstm.executeQuery();
			
			if (rst.next()) {
				d400VO.setGSDM(rst.getString("GSDM"));
				d400VO.setBDDM(rst.getString("BDDM"));
				d400VO.setBDBH(rst.getString("BDBH"));
				d400VO.setBDRQ(rst.getString("BDRQ"));
				d400VO.setBMMC(rst.getString("BMMC"));
				d400VO.setSQYH(rst.getString("SQYH"));
				d400VO.setQCLX(rst.getString("QCLX"));
				d400VO.setWJSX(rst.getString("WJSX"));
				d400VO.setWJJM(rst.getString("WJJM"));
				d400VO.setQCAY(rst.getString("QCAY"));
				d400VO.setQCNR(readClob(GSDM,BDDM,BDBH));
				d400VO.setBDFD(rst.getString("BDFD"));
				d400VO.setQHYH(rst.getString("QHYH"));
				d400VO.setQHYH(rst.getString("QHYH"));
				d400VO.setQHSJ(rst.getTimestamp("QHSJ"));
				d400VO.setJLYH(rst.getString("JLYH"));
				d400VO.setJLSJ(rst.getTimestamp("JLSJ"));
				d400VO.setGXYH(rst.getString("GXYH"));
				d400VO.setGXSJ(rst.getTimestamp("GXSJ"));
				d400VO.setDYZT(rst.getString("DYZT"));
				d400VO.setDYYH(rst.getString("DYYH"));
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
		return d400VO;
	}

	public Boolean getUserAllow(String USERID) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		boolean allow = false;

		String sql = "SELECT * FROM QH_ALLOW_BD WHERE USERID=? ";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, USERID);

			rst = pstm.executeQuery();
			
			while (rst.next()) {
				allow = true;
			}
//System.out.println("sql bd;; "+sql);
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
		return allow;
	}

	public String getD400H_QHJG(String bdbh) {

		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
		String QHJG = "";

		String sql = "SELECT * FROM QH_BDLC WHERE QHLX= 'A' AND BDBH=? ";
		try {
			con = getConnection();
			
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, bdbh);

			rst = pstm.executeQuery();
			
			while (rst.next()) {
				QHJG = rst.getString("QHJG");
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
		return QHJG;
	}
	

	public String getBDBH(String GSDM, String BDDM, String LX, String BDYR) {


		Connection conn = null;
		
		PreparedStatement pstm = null;

		PreparedStatement upstm = null;
		
		ResultSet rst = null;
		
		String sql, BDQZ = "";
		
		int BDHM = 0;

		try {
			conn = getConnection();
			
			sql = "select bdqz,bdhm from qh_bdbh where gsdm=? and bddm=? and bdqz like ? and bdyr=? for update nowait";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, LX);
			pstm.setString(4, BDYR);

			sql = "update qh_bdbh set bdhm = ? where gsdm=? and bddm=? and bdqz like ? and bdyr=?";
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
						upstm.setString(4, LX);
						upstm.setString(5, BDYR);
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
	public boolean inserClob(D400VO d400VO) {
		
		Connection con = null;
		
		PreparedStatement pstm = null;
		
		ResultSet rst = null;
		
        boolean suc = false;
        
        CLOB clob = null;
        
        String sql = "INSERT INTO D400H (GSDM,BDDM,BDBH,BDRQ,BMMC," +
        		"SQYH,QCLX,WJSX,QCAY,QCNR1,BDFD,QHYH,QHSJ,JLYH," +
        		"JLSJ,GXYH,GXSJ,WJJM) " +
        		"VALUES ('"+d400VO.getGSDM()+"','"+d400VO.getBDDM()+"'" +
        		",'"+d400VO.getBDBH()+"','"+d400VO.getBDRQ()+"'," +
        		"'"+d400VO.getBMMC()+"','"+d400VO.getSQYH()+"'," +
        		"'"+d400VO.getQCLX()+"','"+d400VO.getWJSX()+"'" +
        		",'"+d400VO.getQCAY()+"',empty_clob()" +
        		",'"+d400VO.getBDFD()+"','"+d400VO.getQHYH()+"'" +
        		","+Helper.toSqlDate(d400VO.getQHSJ())+",'"+d400VO.getJLYH()+"'" +
        		","+Helper.toSqlDate(d400VO.getJLSJ())+",'"+d400VO.getGXYH()+"'" +
        		","+Helper.toSqlDate(d400VO.getGXSJ())+",'"+d400VO.getWJJM()+"')";
        
        try {
        	con = getConnection();
        	
            con.setAutoCommit(false);
            
            pstm = con.prepareStatement(sql);
            
            pstm.executeUpdate();
            
            pstm.close();  
            
            sql = "SELECT QCNR1 FROM D400H " +
            	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
        		" AND BDBH='"+d400VO.getBDBH()+"' for update ";
            
            pstm = con.prepareStatement(sql);
            
            rst = pstm.executeQuery();

            if(rst.next()){
            	
                clob = (CLOB)rst.getClob(1);
                
            }
            
            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream()); 

            pw.write(d400VO.getQCNR());

            pw.flush(); 
            
            pstm.close();
              
            sql = "UPDATE D400H SET QCNR1=? " +
            	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    			" AND BDBH='"+d400VO.getBDBH()+"' ";

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
        
        String sql = "SELECT QCNR1 FROM D400H WHERE GSDM='"+GSDM+"' AND BDDM='"+BDDM+"' AND BDBH='"+BDBH+"'";
        
        try {
        	con = getConnection();
        	
            pstm = con.prepareStatement(sql);
            
            rst = pstm.executeQuery();
            
            if(rst.next()){
            	
                clob = (CLOB)rst.getClob("QCNR1");
                
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
}
