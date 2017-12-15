package imes.web.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.QianHe;
import imes.entity.D031FindOrderVM;
import imes.entity.D031H;
import imes.entity.D031L;
import imes.entity.D031M;
import imes.entity.D031VList;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;

@WebServlet("/D031")
public class D031 extends HttpController {

	private static final long serialVersionUID = 1L;

	private PreparedStatement pstm;
	private ResultSet rst;
	private String sql;
	private String action;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		action = Helper.getAction(req);
		try {
			if (Helper.getAction(req).equals("Create")) {

				// getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031Edit.jsp").forward(req,
				// resp);

			} else if (getAction(req).equals("QianHe")) {

				actGetQianHe(req, resp);

			} else if (getAction(req).equals("FindOrder")) {

				getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031FindOrder.jsp").forward(req, resp);

			} else if (getAction(req).equals("CreateD031M")) {

				actionGetCreateD031M(req, resp);

			} else {
				actionGetList(req, resp);
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
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		action = Helper.getAction(req);
		try {
			if (action.equals("FindOrder")) {

				req.setAttribute("list", actionFindOrder(req, resp));
				getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031FindOrder.jsp").forward(req, resp);

			} else if (action.equals("New")) {

				actionNew(req, resp);

			} else if (action.equals("Create")) {

				actionCreate(req, resp);

			} else if (getAction(req).equals("QianHe")) {

				actPostQianHe(req, resp);

			} else if (getAction(req).equals("List")) {

				actionPostList(req, resp);

			} else if (getAction(req).equals("UpdMat")) {

				actionPostUpdMat(req, resp);

			} else if (getAction(req).equals("UpdYYLB")) {

				actionPostUpdYYLB(req, resp);
			}

		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	private void actPostQianHe(HttpServletRequest req, HttpServletResponse resp) throws Exception {

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

			if (!HQYH.equals("")) {
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

	private void actGetQianHe(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		D031H d031h = new D031H();

		d031h.setGSDM(req.getParameter("GSDM"));
		d031h.setBDDM(req.getParameter("BDDM"));
		d031h.setBDBH(req.getParameter("BDBH"));

		d031h.find(conn);

		List<D031L> d031ls = D031L.findBy(d031h.getGSDM(), d031h.getBDDM(), d031h.getBDBH(), conn);

		List<QH_BDLC> bdlcs = QH_BDLC.findBy(d031h.getGSDM(), d031h.getBDDM(), d031h.getBDBH(), conn);

		req.setAttribute("D031H", d031h);
		req.setAttribute("D031LS", d031ls);
		req.setAttribute("BDLCS", bdlcs);
		req.setAttribute("D031SM", D031L.getSummary(d031h.getGSDM(), d031h.getBDDM(), d031h.getBDBH(), conn));
		req.setAttribute("D031MS", D031M.findBy(d031h.getGSDM(), d031h.getBDDM(), d031h.getBDBH(), conn));
		conn.close();

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031QH.jsp").forward(req, resp);

	}

	private void actionCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		resp.addHeader("Pragma", "No-cache");
		resp.addHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", -10);

		@SuppressWarnings("unchecked")
		List<D031FindOrderVM> list = (List<D031FindOrderVM>) req.getSession().getAttribute("D031");

		List<D031L> d031Ls = new ArrayList<D031L>();

		D031H h;
		D031L d, c;

		String omeng, edatu, mbdat, key, BDBH, GSDM;

		GSDM = req.getParameter("GSDM");

		BDBH = Helper.getBDBH(getConnection(), GSDM, "D031", Helper.fmtDate(new Date(), "yyyy"));

		h = new D031H();

		h.setGSDM(GSDM);
		h.setBDDM("D031");
		h.setBDBH(BDBH);
		h.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		h.setBDZT("0");
		h.setYYLB(req.getParameter("YYLB"));
		h.setYYSM(req.getParameter("YYSM"));
		h.setJLYH(getUid(req));
		h.setQHSJ(null);
		h.setYXSJ(null);

		double chkqty = 0;
		boolean diffMonth = false;

		int j = 0;
		for (D031FindOrderVM e : list) {

			j += 1;
			d = new D031L();
			d.setGSDM(GSDM);
			d.setXCLB("A");
			d.setBDDM("D031");
			d.setBDBH(BDBH);
			d.setXCZT("0");
			d.setXCXH(j);
			d.setJLYH(getUid(req));
			d.setVTWEG(e.getVTWEG());
			d.setWERKS(e.getWERKS());
			d.setKUNNR(e.getKUNNR());
			d.setSORTL(e.getSORTL());
			d.setAUART(e.getAUART());
			d.setVBELN(e.getVBELN());
			d.setPOSNR(e.getPOSNR());
			d.setETENR(e.getETENR());
			d.setEDATU(e.getEDATU());
			d.setMBDAT(e.getMBDAT());
			d.setMATNR(e.getMATNR());
			d.setARKTX(e.getARKTX());
			d.setMATKL(e.getMATKL());
			d.setOMENG(e.getOMENG());
			d.setMEINS(e.getMEINS());
			d.setNETPR(e.getNETPR());
			d.setWAERK(e.getWAERK());
			d.setVSART(e.getVSART());
			d.setZTERM(e.getZTERM());
			d.setINCO1(e.getINCO1());
			d.setINCO2(e.getINCO2());
			d.setKDMAT(e.getKDMAT());
			d.setGZSJ(null);
			d031Ls.add(d);

			chkqty = chkqty + d.getOMENG();

			c = new D031L();

			for (int i = 1; i <= e.getSPLIT(); i++) {
				key = d.getVBELN() + "_" + d.getPOSNR();
				omeng = Helper.toUpperCase(req.getParameter("q_" + key + "_" + Integer.toString(i)));
				edatu = Helper.toUpperCase(req.getParameter("d_" + key + "_" + Integer.toString(i)));
				mbdat = Helper.toUpperCase(req.getParameter("m_" + key + "_" + Integer.toString(i)));
				if (omeng.equals("") && edatu.equals("") && mbdat.equals("")) {
					// ignore -- no changes
				} else {
					c = (D031L) d.clone();
					j += 1;
					c.setXCLB("C");
					c.setXCXH(j);

					if (omeng.equals("")) {
						c.setOMENG(d.getOMENG());
					} else {

						try {
							c.setOMENG(Double.parseDouble(omeng));
						} catch (Exception e2) {
							c.setOMENG(0);
						}
					}

					if (edatu.equals("")) {
						c.setEDATU(d.getEDATU());
					} else {
						c.setEDATU(edatu);
					}

					if (mbdat.equals("")) {
						c.setMBDAT(c.getEDATU());
					} else {
						c.setMBDAT(mbdat);
					}

				}
				chkqty = chkqty - c.getOMENG();

				if (diffMonth == false && !(d.getEDATU().substring(0, 6).equals(c.getEDATU().substring(0, 6)))) {
					diffMonth = true;
				}

				d031Ls.add(c);
			}
		}
		List<QH_BDLC> routes = new ArrayList<QH_BDLC>();
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try {

			h.insertDb(conn);

			String text = "";

			for (D031L l : d031Ls) {
				l.insertDb(conn);
				text = l.getWERKS() + " " + l.getSORTL() + " " + l.getMATNR();
			}

			QH_BDTX bdtx = new QH_BDTX();
			bdtx.setGSDM(h.getGSDM());
			bdtx.setBDDM(h.getBDDM());
			bdtx.setBDBH(h.getBDBH());
			bdtx.setBDTX(h.getBDBH() + h.getYYLB() + " " + text);
			bdtx.insertDb(conn);

			QianHe qh = new QianHe();
			qh.setGSDM(h.getGSDM());
			qh.setBDDM(h.getBDDM());
			qh.setBDBH(h.getBDBH());
			qh.setMgr(getUMgr(req));
			qh.setSQYH(getUid(req));

//			if (diffMonth || chkqty > 0) {
//				routes = qh.crtRoute(conn, "Q", "1");
//			} else {
//				routes = qh.crtRoute(conn, "Q", "0");
//			}

//			for (QH_BDLC r : routes) {
//				r.setSQYH(getUid(req));

//				if (Helper.toUpperCase(h.getYYSM()).startsWith("@M")) {
//					if (r.getBZDM() == 100 || r.getBZDM() == 9999) {
//						r.insertDb(conn);
//					}
//				} else {
//					r.insertDb(conn);
//				}
//			}
			
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

			int js = 100;
			for(String s : shr){  //1. 審核人
				if(!"".equals(s.trim().toUpperCase())){
					i++;
					bzdm = i*100+js;
					zwdm = "SHR";
					bdlc = addQHE(h.getGSDM(),h.getBDDM(),h.getBDBH(),qhzt,bzdm,999,zwdm,"R","N",getUid(req).toUpperCase(),s.toUpperCase());
					q.add(bdlc);
				}
			}
			for(String s : hqr){  //2. 會簽人
				if(!"".equals(s.trim())){
					i++;
					bzdm = i*100+js;
					zwdm = "HQR";
					bdlc = addQHE(h.getGSDM(),h.getBDDM(),h.getBDBH(),qhzt,bzdm,999,zwdm,"I","N",getUid(req).toUpperCase(),s.toUpperCase());
					q.add(bdlc);
				}
			}
			
			if(!"".equals(hjr)){  //3. 核准人
				bzdm = (i+1) *100+js;
				zwdm = "HJR";
				bdlc = addQHE(h.getGSDM(),h.getBDDM(),h.getBDBH(),qhzt,bzdm,9999,zwdm,"A","Y",getUid(req).toUpperCase(),hjr.toUpperCase());
				q.add(bdlc);
			}

			bdlc = addQHE(h.getGSDM(),h.getBDDM(),h.getBDBH(),qhzt,9999,9999,"SQYH","Z","Y",getUid(req).toUpperCase(),getUid(req).toUpperCase());
			q.add(bdlc);
			
			for (QH_BDLC r : q) {				
				insertQH_BDLC(r);
				routes = q;
			}

			conn.commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			conn.rollback();
		}
		conn.setAutoCommit(true);
		conn.close();

		req.setAttribute("D031H", h);
		req.setAttribute("D031LS", d031Ls);
		req.setAttribute("BDLCS", routes);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031QH.jsp").forward(req, resp);
	}

	private void actionNew(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		resp.addHeader("Pragma", "No-cache");
		resp.addHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", -10);

		List<D031FindOrderVM> list = new ArrayList<D031FindOrderVM>();

		sql = "  SELECT VBAK.VTWEG, " //
				+ "         VBBE.WERKS, " //
				+ "         VBBE.KUNNR, " //
				+ "         KNA1.SORTL, " //
				+ "         VBBE.AUART, " //
				+ "         VBBE.VBELN, " //
				+ "         VBBE.POSNR, " //
				+ "         VBBE.ETENR, " //
				+ "         VBEP.EDATU, " //
				+ "         VBEP.MBDAT, " //
				+ "         VBBE.MATNR, " //
				+ "         VBAP.ARKTX, " //
				+ "         VBAP.MATKL, " //
				+ "         VBBE.OMENG, " //
				+ "         VBBE.MEINS, " //
				+ "         (VBAP.NETPR / VBAP.KPEIN) NETPR, " //
				+ "         VBAP.WAERK, " //
				+ "         NVL (VBKD.INCO1, TVBKD.INCO1) INCO1, " //
				+ "         NVL (VBKD.INCO2, TVBKD.INCO2) INCO2, " //
				+ "         VBAP.KDMAT, " //
				+ "         (SELECT BEZEI " //
				+ "            FROM SAPSR3.T173T@SAPP " //
				+ "           WHERE MANDT = '168' AND SPRAS = 'M' AND VSART = NVL (VBKD.VSART, TVBKD.VSART)) " //
				+ "            VSART, " //
				+ "         (SELECT VTEXT FROM SAPSR3.TVZBT@SAPP WHERE MANDT = '168' AND SPRAS = 'M' AND ZTERM=NVL (VBKD.ZTERM, TVBKD.ZTERM)) ZTERM " //
				+ "    FROM SAPSR3.VBBE@SAPP, " //
				+ "         SAPSR3.VBEP@SAPP, " //
				+ "         SAPSR3.VBAK@SAPP, " //
				+ "         SAPSR3.VBAP@SAPP, " //
				+ "         SAPSR3.VBKD@SAPP, " //
				+ "         SAPSR3.VBKD@SAPP TVBKD, " //
				+ "         SAPSR3.KNA1@SAPP " //
				+ "   WHERE     VBBE.MANDT = '168' " //
				+ "         AND VBBE.VBTYP IN ('C','I') " //
				+ "         AND VBBE.OMENG > 0 " //
				+ "         AND VBEP.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBEP.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBEP.POSNR(+) = VBBE.POSNR " //
				+ "         AND VBEP.ETENR(+) = VBBE.ETENR " //
				+ "         AND VBAK.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBAK.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBAP.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBAP.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBAP.POSNR(+) = VBBE.POSNR " //
				+ "         AND VBKD.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBKD.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBKD.POSNR(+) = VBBE.POSNR " //
				+ "         AND TVBKD.MANDT(+) = VBBE.MANDT " //
				+ "         AND TVBKD.VBELN(+) = VBBE.VBELN " //
				+ "         AND TVBKD.POSNR(+) = '000000' " //
				+ "         AND KNA1.MANDT(+) = VBBE.MANDT " //
				+ "         AND KNA1.KUNNR(+) = VBBE.KUNNR "; //

		String[] strs = req.getParameterValues("Vbeln_Posnr");
		String[] Vbeln_Posnr;
		String condition = "";
		for (String e : strs) {
			Vbeln_Posnr = e.split("_");
			if (!condition.equals("")) {
				condition += " OR ";
			}
			condition += "(vbbe.vbeln = '" + Vbeln_Posnr[0] + "' AND vbbe.posnr = '" + Vbeln_Posnr[1] + "')";
		}
		sql += "AND (" + condition + ")";

		D031FindOrderVM e;

		Connection conn = getConnection();
		pstm = conn.prepareStatement(sql);
		rst = pstm.executeQuery();
		while (rst.next()) {
			e = new D031FindOrderVM();
			e.setVTWEG(rst.getString("VTWEG"));
			e.setWERKS(rst.getString("WERKS"));
			e.setKUNNR(rst.getString("KUNNR"));
			e.setSORTL(rst.getString("SORTL"));
			e.setAUART(rst.getString("AUART"));
			e.setVBELN(rst.getString("VBELN"));
			e.setPOSNR(rst.getString("POSNR"));
			e.setETENR(rst.getString("ETENR"));
			e.setEDATU(rst.getString("EDATU"));
			e.setMBDAT(rst.getString("MBDAT"));
			e.setMATNR(rst.getString("MATNR"));
			e.setARKTX(rst.getString("ARKTX"));
			e.setMATKL(rst.getString("MATKL"));
			e.setOMENG(rst.getDouble("OMENG"));
			e.setMEINS(rst.getString("MEINS"));
			e.setNETPR(rst.getDouble("NETPR"));
			e.setWAERK(rst.getString("WAERK"));
			e.setVSART(rst.getString("VSART"));
			e.setZTERM(rst.getString("ZTERM"));
			e.setINCO1(rst.getString("INCO1"));
			e.setINCO2(rst.getString("INCO2"));
			e.setKDMAT(rst.getString("KDMAT"));
			//handling jpy & twd currency
			if (e.getWAERK().equals("JPY") || e.getWAERK().equals("TWD")) {
				e.setNETPR(e.getNETPR() * 100);
			}
			e.setSPLIT(toIntegerParam(req, "s_" + e.getVBELN() + "_" + e.getPOSNR()));
			list.add(e);
		}
		rst.close();
		pstm.close();
		conn.close();
		
		List<String> userlist = findUserZJL();
		req.setAttribute("userlist", userlist);
		req.setAttribute("list", list);
		req.getSession().setAttribute("D031", list);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031New.jsp").forward(req, resp);
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
	
	private List<D031FindOrderVM> actionFindOrder(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		List<D031FindOrderVM> list = new ArrayList<D031FindOrderVM>();

		sql = "  SELECT VBAK.VTWEG, " //
				+ "         VBBE.WERKS, " //
				+ "         VBBE.KUNNR, " //
				+ "         KNA1.SORTL, " //
				+ "         VBBE.AUART, " //
				+ "         VBBE.VBELN, " //
				+ "         VBBE.POSNR, " //
				+ "         VBBE.ETENR, " //
				+ "         VBEP.EDATU, " //
				+ "         VBEP.MBDAT, " //
				+ "         VBBE.MATNR, " //
				+ "         VBAP.ARKTX, " //
				+ "         VBAP.MATKL, " //
				+ "         VBBE.OMENG, " //
				+ "         VBBE.MEINS, " //
				+ "         (VBAP.NETPR / VBAP.KPEIN) NETPR, " //
				+ "         VBAP.WAERK, " //
				+ "         NVL (VBKD.INCO1, TVBKD.INCO1) INCO1, " //
				+ "         NVL (VBKD.INCO2, TVBKD.INCO2) INCO2, " //
				+ "         VBAP.KDMAT, " //
				+ "         (SELECT BEZEI " //
				+ "            FROM SAPSR3.T173T@SAPP " //
				+ "           WHERE MANDT = '168' AND SPRAS = 'M' AND VSART = NVL (VBKD.VSART, TVBKD.VSART)) " //
				+ "            VSART, " //
				+ "         (SELECT VTEXT FROM SAPSR3.TVZBT@SAPP WHERE MANDT = '168' AND SPRAS = 'M' AND ZTERM=NVL (VBKD.ZTERM, TVBKD.ZTERM)) ZTERM " //
				+ "    FROM SAPSR3.VBBE@SAPP, " //
				+ "         SAPSR3.VBEP@SAPP, " //
				+ "         SAPSR3.VBAK@SAPP, " //
				+ "         SAPSR3.VBAP@SAPP, " //
				+ "         SAPSR3.VBKD@SAPP, " //
				+ "         SAPSR3.VBKD@SAPP TVBKD, " //
				+ "         SAPSR3.KNA1@SAPP " //
				+ "   WHERE     VBBE.MANDT = '168' " //
				+ "         AND VBBE.VBTYP IN ('C','I') " //
				+ "         AND VBBE.OMENG > 0 " //
				+ "         AND VBEP.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBEP.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBEP.POSNR(+) = VBBE.POSNR " //
				+ "         AND VBEP.ETENR(+) = VBBE.ETENR " //
				+ "         AND VBAK.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBAK.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBAP.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBAP.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBAP.POSNR(+) = VBBE.POSNR " //
				+ "         AND VBKD.MANDT(+) = VBBE.MANDT " //
				+ "         AND VBKD.VBELN(+) = VBBE.VBELN " //
				+ "         AND VBKD.POSNR(+) = VBBE.POSNR " //
				+ "         AND TVBKD.MANDT(+) = VBBE.MANDT " //
				+ "         AND TVBKD.VBELN(+) = VBBE.VBELN " //
				+ "         AND TVBKD.POSNR(+) = '000000' " //
				+ "         AND KNA1.MANDT(+) = VBBE.MANDT " //
				+ "         AND KNA1.KUNNR(+) = VBBE.KUNNR "; //

		sql += Helper.sqlParams(req, "vbak.vtweg", "strVtweg", "endVtweg");
		sql += Helper.sqlParams(req, "vbbe.werks", "strWerks", "endWerks");
		sql += Helper.sqlParams(req, "vbbe.vbeln", "strVbeln", "endVbeln");
		sql += Helper.sqlParams(req, "vbbe.kunnr", "strKunnr", "endKunnr");
		sql += Helper.sqlParams(req, "vbbe.matnr", "strMatnr", "endMatnr");
		sql += Helper.sqlParams(req, "vbep.edatu", "strEdatu", "endEdatu");
		sql += Helper.sqlParams(req, "vbep.mbdat", "strMbdat", "endMbdat");

		sql += " ORDER BY vbbe.kunnr, vbep.edatu, vbbe.vbeln, vbbe.posnr";

		D031FindOrderVM e;

		Connection conn = getConnection();
		pstm = conn.prepareStatement(sql);
		rst = pstm.executeQuery();
		while (rst.next()) {
			e = new D031FindOrderVM();
			e.setVTWEG(rst.getString("VTWEG"));
			e.setWERKS(rst.getString("WERKS"));
			e.setKUNNR(rst.getString("KUNNR"));
			e.setSORTL(rst.getString("SORTL"));
			e.setAUART(rst.getString("AUART"));
			e.setVBELN(rst.getString("VBELN"));
			e.setPOSNR(rst.getString("POSNR"));
			e.setETENR(rst.getString("ETENR"));
			e.setEDATU(rst.getString("EDATU"));
			e.setMBDAT(rst.getString("MBDAT"));
			e.setMATNR(rst.getString("MATNR"));
			e.setARKTX(rst.getString("ARKTX"));
			e.setMATKL(rst.getString("MATKL"));
			e.setOMENG(rst.getDouble("OMENG"));
			e.setMEINS(rst.getString("MEINS"));
			e.setNETPR(rst.getDouble("NETPR"));
			e.setWAERK(rst.getString("WAERK"));
			e.setVSART(rst.getString("VSART"));
			e.setZTERM(rst.getString("ZTERM"));
			e.setINCO1(rst.getString("INCO1"));
			e.setINCO2(rst.getString("INCO2"));
			e.setKDMAT(rst.getString("KDMAT"));
			list.add(e);
		}
		rst.close();
		pstm.close();
		conn.close();

		return list;
	}

	private void actionGetList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031List.jsp").forward(req, resp);
	}

	private void actionPostList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, Exception {

		List<D031VList> list = new ArrayList<D031VList>();

		String sql = "  SELECT HD.BDFD,HD.BDJG, HD.BDZT, B4.BDBH, B4.WERKS, B4.KUNNR, B4.SORTL, " //
				+ "         B4.VBELN, B4.POSNR, B4.MATNR, HD.JLYH, HD.JLSJ, B4.WAERK, " //
				+ "         B4.NETPR, B4.OMENG B4QTY, B4.EDATU B4DAT, AF.OMENG AFQTY, AF.EDATU AFDAT, AF.XCXH, " //
				+ "         HD.YYLB, HD.YYSM, HD.QHYH, HD.QHSJ, HD.GSDM, HD.BDDM," //
				+ "         (SELECT YSYH || TO_CHAR(YJSJ,'; YYYYMMDD HH:MI') " //
				+ "          FROM (  SELECT GSDM,BDDM,BDBH,YSYH,BZDM,YJSJ " //
				+ "                    FROM QH_BDLC " //
				+ "                   WHERE QHZT = '2' " //
				+ "                ORDER BY BZDM) QH " //
				+ "         WHERE ROWNUM = 1 AND QH.GSDM = HD.GSDM AND QH.BDDM = HD.BDDM AND QH.BDBH = HD.BDBH) YSYH " //
				+ "    FROM D031L B4, D031L AF, D031H HD " //
				+ "   WHERE     B4.XCLB = 'A' " //
				+ "         AND AF.XCLB = 'C' " //
				+ "         AND B4.GSDM = AF.GSDM " //
				+ "         AND B4.BDDM = AF.BDDM " //
				+ "         AND B4.BDBH = AF.BDBH " //
				+ "         AND B4.VBELN = AF.VBELN " //
				+ "         AND B4.POSNR = AF.POSNR " //
				+ "         AND HD.GSDM = AF.GSDM " //
				+ "         AND HD.BDDM = AF.BDDM " //
				+ "         AND HD.BDBH = AF.BDBH "; //

		sql += Helper.sqlParams(req, "B4.MATNR", "STRMATNR", "ENDMATNR");
		sql += Helper.sqlParams(req, "B4.KUNNR", "STRKUNNR", "ENDKUNNR");
		sql += Helper.sqlParams(req, "HD.JLYH", "STRSQYH", "ENDSQYH");
		sql += Helper.sqlDateParams(req, "HD.JLSJ", "STRJLSJ", "ENDJLSJ");
		sql += Helper.sqlParams(req, "B4.BDBH", "STRBDBH", "ENDBDBH");
		sql += Helper.sqlDateParams(req, "HD.QHSJ", "STRQHSJ", "ENDQHSJ");
		sql += Helper.sqlParams(req, "HD.BDZT", "STRBDZT", "STRBDZT");
		sql += Helper.sqlParams(req, "HD.BDJG", "STRBDJG", "STRBDJG");
		sql += Helper.sqlParamsValues(req, "HD.YYLB", "STRYYLB");
		sql += " ORDER BY B4.WERKS,B4.KUNNR,B4.MATNR,B4.EDATU,B4.VBELN, B4.POSNR "; //

		// System.out.println(sql);

		Connection conn = getConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rst = pstm.executeQuery();

		D031VList e;

		while (rst.next()) {

			e = new D031VList();
			e.setBDFD(rst.getString("BDFD"));
			e.setBDJG(rst.getString("BDJG"));
			e.setBDZT(rst.getString("BDZT"));
			e.setBDBH(rst.getString("BDBH"));
			e.setWERKS(rst.getString("WERKS"));
			e.setKUNNR(rst.getString("KUNNR"));
			e.setSORTL(rst.getString("SORTL"));
			e.setVBELN(rst.getString("VBELN"));
			e.setPOSNR(rst.getString("POSNR"));
			e.setMATNR(rst.getString("MATNR"));
			e.setJLYH(rst.getString("JLYH"));
			e.setJLSJ(rst.getTimestamp("JLSJ"));
			e.setWAERK(rst.getString("WAERK"));
			e.setNETPR(rst.getDouble("NETPR"));
			e.setBFQTY(rst.getDouble("B4QTY"));
			e.setBFDAT(rst.getString("B4DAT"));
			e.setAFQTY(rst.getDouble("AFQTY"));
			e.setAFDAT(rst.getString("AFDAT"));
			e.setXCXH(rst.getInt("XCXH"));
			e.setYYLB(rst.getString("YYLB"));
			e.setYYSM(rst.getString("YYSM"));
			e.setYSYH(rst.getString("YSYH"));
			// e.setQHNR(rst.getString("QHNR"));
			e.setQHYH(rst.getString("QHYH"));
			e.setQHSJ(rst.getTimestamp("QHSJ"));
			e.setGSDM(rst.getString("GSDM"));
			e.setBDDM(rst.getString("BDDM"));

			list.add(e);
		}

		rst.close();
		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D031List.jsp").forward(req, resp);
	}

	private void actionPostUpdMat(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		String[] strbuf;
		String PMATNR, SQNR, FLDNAME, key, param;

		HashMap<String, D031M> map = new HashMap<String, D031M>();

		D031M e;

		for (Enumeration<String> s = req.getParameterNames(); s.hasMoreElements();) {

			param = s.nextElement();
			strbuf = param.split("_");

			if (strbuf.length > 2) {

				PMATNR = strbuf[0];
				SQNR = strbuf[1];
				FLDNAME = strbuf[2];

				key = PMATNR + "_" + SQNR;

				if (map.containsKey(key)) {
					e = map.get(key);
				} else {
					e = new D031M();
					e.setGSDM(GSDM);
					e.setBDDM(BDDM);
					e.setBDBH(BDBH);
					e.setPMATNR(PMATNR);

					try {
						e.setSQNR(Integer.parseInt(SQNR));
					} catch (Exception e1) {

						e.setSQNR(0);
					}

					e.setGXYH(getUid(req));
				}

				if (FLDNAME.equals("IDEQ")) {

					e.setIDEQ(toDoubleParam(req, param));

				} else if (FLDNAME.equals("CFMDT")) {

					e.setCFMDT(req.getParameter(param));

				} else if (FLDNAME.equals("LTEXT")) {

					e.setLTEXT(req.getParameter(param));
				}

				map.put(key, e);
			} // if (strbuf.length > 2)
		}

		List<D031M> list = new ArrayList<D031M>();

		for (D031M m : map.values()) {
			list.add(m);
		}

		D031M.updateDb(list, getConnection());

		list.clear();
		map.clear();

		// iMes/D031?action=QianHe&GSDM=L400&BDDM=D031&BDBH=DT-D031-1200109
		resp.sendRedirect("/iMes/D031?action=QianHe&GSDM=" + GSDM + "&BDDM=" + BDDM + "&BDBH=" + BDBH);
	}

	private void actionPostUpdYYLB(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		String YYLB = req.getParameter("YYLB");

		String sql = "UPDATE D031H SET YYLB=? WHERE GSDM=? AND BDDM=? AND BDBH=?";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, YYLB);
		pstm.setString(2, GSDM);
		pstm.setString(3, BDDM);
		pstm.setString(4, BDBH);
		pstm.executeUpdate();

		pstm.close();
		conn.close();

		resp.sendRedirect("/iMes/D031?action=QianHe&GSDM=" + GSDM + "&BDDM=" + BDDM + "&BDBH=" + BDBH);
	}

	private void actionGetCreateD031M(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");

		D031M e = new D031M();

		e.setGSDM(GSDM);
		e.setBDDM(BDDM);
		e.setBDBH(BDBH);
		e.setJLYH(getUid(req));

		Connection conn = getConnection();
		e.create(conn);
		conn.close();

		resp.sendRedirect("/iMes/D031?action=QianHe&GSDM=" + GSDM + "&BDDM=" + BDDM + "&BDBH=" + BDBH);

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
}
