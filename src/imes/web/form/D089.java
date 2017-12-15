package imes.web.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.IRequest;
import imes.core.QianHe;
import imes.dao.D089Dao;
import imes.entity.D089H;
import imes.entity.D089L;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;

@WebServlet({ "/D089", "/D089/*" })
public class D089 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ireq = new IRequest(req);
			String action = ireq.getUrlMap().get("Action");

			if (action != null) {
				if (action.equals("LIST")) {

					getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D089.jsp").forward(req, resp);

				} else if (action.equals("LISTMATNR")) {

					getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D089L.jsp").forward(req, resp);

				}

				else if (action.equals("CREATE")) {

					doCreateGetAction(req, resp);

				} else if (action.equals("EDIT") || action.equals("QianHe")) {

					doEditGetAction(req, resp);

				} else if (action.equals("GETSUPPLIER")) {

					ajaxGetSupplier(req, resp);

				} else if (action.equals("D089LDLT")) {

					doD089LDeleteGetAction(req, resp);

				} else if (action.equals("D089LDLTALL")) {

					doD089LDeleteAllGetAction(req, resp);

				} else if (action.equals("D089SBMQH")) {

					doD089SubmitQHGetAction(req, resp);

				} else if (action.equals("GETPRICEHISTORY")) {

					ajaxGetPriceHistory(req, resp);

				} else if (action.equals("GETOPENPO")) {

					ajaxGetOpenPO(req, resp);

				} else if (action.equals("SUBMITQH")) {

					doD089SubmitQHGetAction(req, resp);

				} else if (action.equals("GetChgPriceErrLog")) {

					ajaxGetChgPriceErrLog(req, resp);
				} else if (action.equals("SapUpdateLog")) {

					doSapUpdateLog(req, resp);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ireq = new IRequest(req);
			String action = ireq.getUrlMap().get("Action");

			if (action != null) {
				if (action.equals("LIST")) {

					doD089ListPostAction(req, resp);
					getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D089.jsp").forward(req, resp);

				} else if (action.equals("LISTMATNR")) {

					doD089ListMatPostAction(req, resp);
					getServletContext().getRequestDispatcher("/WEB-INF/pages/form/D089L.jsp").forward(req, resp);

				} else if (action.equals("D089HSAVE")) {

					doD089HSavePostAction(req, resp);

				} else if (action.equals("MATUPLOAD")) {

					doD089MatUploadPostAction(req, resp);

				} else if (action.equals("D089LEDIT")) {

					doD089LEditPostAction(req, resp);

				} else if (action.equals("QianHe")) {

					doD089QianHePostAction(req, resp);

				} else if (action.equals("D089LDELETEMARK")) {

					doD089LDeleteMarkPostAction(req, resp);

				} else if (action.equals("SapUpdateLog")) {

					doSapUpdateLog(req, resp);
				}

				// SapUpdateLog
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doSapUpdateLog(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		D089Dao d089Dao = new D089Dao(getConnection());
		req.setAttribute("list", d089Dao.getSapUpdateLog(req));
		d089Dao.close();
		render(req, resp, "/WEB-INF/pages/form/D089SapUpdateLog.jsp");
	}

	private void doD089QianHePostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

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

		boolean huiqian = false;

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
						e.verifyNewBZDM(conn);
						e.insertDb(conn);
						i = e.getBZDM();
						huiqian = true;
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

			if (BZDM == 9999 && huiqian) {
				e = (QH_BDLC) old.clone();
				e.setBZDM(i + 1);
				e.setQHLX("Z");
				e.setZWDM("");
				e.setZFYN("Y");
				e.setLCLX("S");
				e.setJLSJ(new Date());
				e.setYJSJ(null);
				e.verifyNewBZDM(conn);
				e.setQHZT("3");
				e.setQHYH(getUid(req));
				e.setQHSJ(new Date());
				e.setQHJG(QHJG);
				e.setQHNR(QHNR);
				e.insertDb(conn);
				i = e.getBZDM();
			} else {
				sql = "UPDATE QH_BDLC " //
						+ "   SET QHZT='3', " //
						+ "       QHYH=?, " //
						+ "       QHSJ=SYSDATE, " //
						+ "       QHJG=?, " //
						+ "       QHNR=? " //
						+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //

				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, getUid(req));
				pstm.setString(2, QHJG);
				pstm.setString(3, QHNR);
				pstm.setString(4, GSDM);
				pstm.setString(5, BDDM);
				pstm.setString(6, BDBH);
				pstm.setInt(7, BZDM);
				pstm.executeUpdate();
			}

			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			conn.rollback();
		}

		conn.close();

		resp.sendRedirect("/iMes/qh?action=Find&STRQHZT=2&STRYSYH=" + getUid(req));
	}

	private void doD089SubmitQHGetAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();
		conn.setAutoCommit(false);

		D089Dao d089Dao = new D089Dao(conn);

		String matkl = d089Dao.getD089LMatkl(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));
		D089H d089h = d089Dao.find(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));
		
		String strategicsourcing = "";
		String strategicsourcing19 = "";
		List<String> matkls = d089Dao.getD089LMatkls(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));
		for(String mat : matkls)
		{
			if(mat == null || mat.equals("")) {
				strategicsourcing19 = "19";
			} else {
				if(mat.substring(0, 2).equals("19"))
				{
					strategicsourcing19 = "19";		
				}
				else
				{
					strategicsourcing = "00";
				}
			}
		}
		
		QianHe qh = new QianHe();
		qh.setGSDM(d089h.getGSDM());
		qh.setBDDM(d089h.getBDDM());
		qh.setBDBH(d089h.getBDBH());
		qh.setMgr(getUMgr(req));
		qh.setSQYH(getUid(req));

		double value = d089h.getMAXPT();

		if (d089h.getBDYY() != null && d089h.getBDYY().substring(0, 1).equals("E")) {
			value = 0;
		} else if (d089h.getBDYY() != null && d089h.getBDYY().substring(0, 1).equals("A") && value < 0) {
			value = 0;
		}

		List<QH_BDLC> bdlcs = qh.crtRoute(conn, "P", Double.toString(value));

		try {
			QH_BDTX bdtx = new QH_BDTX();
			bdtx.setGSDM(d089h.getGSDM());
			bdtx.setBDDM(d089h.getBDDM());
			bdtx.setBDBH(d089h.getBDBH());
			bdtx.setBDTX("Supplier Quotation Approval-" + d089h.getLIFNR() + " " + d089h.getNAME1());
			bdtx.insertDb(conn);
			
			for (QH_BDLC bdlc : bdlcs) {
				if(bdlc.getBZDM() == 100 && bdlc.getYSYH().equals("YANXIA.ZHANG") && (bdlc.getSQYH().equals("ALICE.GER") ||bdlc.getSQYH().equals("NEIL.WANG") || bdlc.getSQYH().equals("LUCY.LI")|| bdlc.getSQYH().equals("LILY.WU")|| bdlc.getSQYH().equals("INA.GAO"))){
					bdlc.setYSYH("CHRISTINA.HUANG");					
				}
				

				if (bdlc.getFTXT() == null || bdlc.getFTXT().equals("") || bdlc.getFTXT().equals(" ")) {
		
					if (null != bdlc.getZWDM() && bdlc.getZWDM().contains("SSPUR")) {
						if (strategicsourcing19.equals("") && strategicsourcing.equals("00")) {
							bdlc.setYSYH("GLASS.LEE");
						}
						if (strategicsourcing19.equals("19") && strategicsourcing.equals("")) {
							bdlc.setYSYH("TIFFANY.TU");
						}
						if (strategicsourcing19.equals("19") && strategicsourcing.equals("00")) {
							QH_BDLC q_clone = (QH_BDLC)bdlc.clone();
							q_clone.setBZDM(q_clone.getBZDM()+10);
							q_clone.setYSYH("GLASS.LEE");
							q_clone.insertDb(conn);
							bdlc.setYSYH("TIFFANY.TU");
						}
					}

					bdlc.insertDb(conn);
				} else {
					if ((matkl.compareTo(bdlc.getFTXT()) >= 0) && (matkl.compareTo(bdlc.getTTXT()) <= 0)) {
						if (null != bdlc.getZWDM() && bdlc.getZWDM().contains("SSPUR")) {
							if (strategicsourcing19.equals("") && strategicsourcing.equals("00")) {
								bdlc.setYSYH("GLASS.LEE");
							}
							if (strategicsourcing19.equals("19") && strategicsourcing.equals("")) {
								bdlc.setYSYH("TIFFANY.TU");
							}
							if (strategicsourcing19.equals("19") && strategicsourcing.equals("00")) {
								QH_BDLC q_clone = (QH_BDLC)bdlc.clone();
								q_clone.setBZDM(q_clone.getBZDM()+10);
								q_clone.setYSYH("GLASS.LEE");
								q_clone.insertDb(conn);
								bdlc.setYSYH("TIFFANY.TU");
							}
						}
						bdlc.insertDb(conn);
					}
				}

			}
			d089Dao.updateD089H_QHKS(d089h.getGSDM(), d089h.getBDDM(), d089h.getBDBH());
			conn.commit();

		} catch (Exception ex) {
			conn.rollback();
			ex.printStackTrace();
		}

		conn.setAutoCommit(false);
		conn.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH=" + getParam(req, "BDBH"));
	}

	private void doD089LEditPostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Connection conn = getConnection();

		D089Dao d089Dao = new D089Dao(conn);

		try {
			for (String sqnr : req.getParameterValues("SQNRCbx")) {
				D089L d089l = new D089L();
				d089l.setGSDM(getParam(req, "GSDM"));
				d089l.setBDDM(getParam(req, "BDDM"));
				d089l.setBDBH(getParam(req, "BDBH"));
				d089l.setSQNR(toInteger(sqnr));
				d089l.setDATAB(getParam(req, "DATAB_" + sqnr));
				d089l.setPRDAT(getParam(req, "PRDAT_" + sqnr));
				d089l.setCHGPO(getParam(req, "CHGPO_" + sqnr));
				d089l.setDLVDT(getParam(req, "DLVDT_" + sqnr));
				d089l.setPODAT(getParam(req, "PODAT_" + sqnr));

				d089Dao.updateD089L(d089l);
			}
		} catch (Exception e) {

		}
		conn.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH=" + getParam(req, "BDBH") + "#tab-2");
	}

	private void doD089LDeleteMarkPostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Connection conn = getConnection();

		D089Dao d089Dao = new D089Dao(conn);

		d089Dao.updateDeleteMark(getParam(req, "RID"), getUid(req), getParam(req, "DLTTX"), getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));
		conn.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH=" + getParam(req, "BDBH") + "#tab-2");
	}

	private void doD089LDeleteAllGetAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Connection conn = getConnection();

		D089Dao d089Dao = new D089Dao(conn);

		d089Dao.deleteAllD089L(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));

		conn.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH=" + getParam(req, "BDBH") + "#tab-2");
	}

	private void doD089LDeleteGetAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		D089Dao d089Dao = new D089Dao(conn);

		d089Dao.deleteD089L(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"), toInteger(getParam(req, "SQNR")));

		conn.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH=" + getParam(req, "BDBH") + "#tab-2");
	}

	private void doD089MatUploadPostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		try {

			conn.setAutoCommit(false);

			D089Dao d089Dao = new D089Dao(conn);

			D089H d089h = d089Dao.find(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));

			List<String> list = textAreaToList(req, "PASTE");

			String[] buf;

			String matnr;
			double netpr, rmbpr;

			D089L d089l;

			List<HashMap<String, Object>> currencies;

			currencies = d089Dao.getCurrencyRates();

			HashMap<String, D089L> d089lMap = new HashMap<String, D089L>();

			for (String record : list) {
				try {

					if (record.contains("\t")) {
						buf = record.split("\t");
					} else {
						buf = record.split(" ");
					}

					if (buf.length > 1) {

						d089l = new D089L();

						matnr = buf[0].toUpperCase().trim();
						netpr = Double.parseDouble(buf[1]);
						d089l.setGSDM(d089h.getGSDM());
						d089l.setBDDM(d089h.getBDDM());
						d089l.setBDBH(d089h.getBDBH());
						d089l.setWAERS(d089h.getWAERS());
						d089l.setMATNR(matnr);
						d089l.setUNTPR(netpr);
						d089l.setMAKTX("Error:Material master not exists");
						d089l.setDATAB(getParam(req, "DATAB"));
						d089l.setPRDAT(getParam(req, "PRDAT"));
						d089l.setCHGPO(getParam(req, "CHGPO"));
						d089l.setPODAT(getParam(req, "PODAT"));
						d089l.setDLVDT(getParam(req, "DLVDT"));
						d089l.setDATYP(getParam(req, "DATYP"));

						d089lMap.put(matnr, d089l);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} //

			/*
			 * Retrieve Material Info from Database.
			 */
			d089Dao.getMatInfo(d089lMap, d089h.getWERKS(), d089h.getLIFNR());

			int sqnr = d089Dao.getD089LMaxSeq(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));

			sqnr += 1; // Increase 1 for the counter

			for (String key : d089lMap.keySet()) {
				try {

					d089l = d089lMap.get(key);

					rmbpr = 0;

					for (HashMap<String, Object> map : currencies) {
						if (map.get("KONWA").equals(d089h.getWAERS())) {
							rmbpr = d089l.getUNTPR() * toDouble(map.get("RMBRATE"));
							break;
						}
					}

					d089l.setSQNR(sqnr);
					d089l.setNETPR(d089l.getUNTPR() * d089l.getPEINH());
					d089l.setRMBPR(rmbpr);

					d089l.setOLDDF(d089l.getRMBPR() - d089l.getOLDPR());
					if (d089l.getOLDPR() != 0) {
						d089l.setOLDPT(d089l.getOLDDF() / d089l.getOLDPR() * 100);
					}
					d089l.setMINDF(d089l.getRMBPR() - d089l.getMINPR());
					if (d089l.getMINPR() != 0) {
						d089l.setMINPT(d089l.getMINDF() / d089l.getMINPR() * 100);
					}

					sqnr += d089Dao.insertD089L(d089l);

				} catch (Exception ex) {

					System.out.println("Error : " + key);
					ex.printStackTrace();
				}

			}// for (String record : list)

			d089Dao.updateMinptMaxptD089H(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			ex.printStackTrace();

		}
		conn.setAutoCommit(true);
		conn.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + getParam(req, "GSDM") + "&BDDM=" + getParam(req, "BDDM") + "&BDBH=" + getParam(req, "BDBH") + "#tab-2");
	}

	private void doD089ListPostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "gsdm_low") + getParam(req, "bdbh_low") + getParam(req, "bdrq_low") + getParam(req, "lifnr_low") + getParam(req, "sqyh_low") + getParam(req, "bdjq_low")
				+ getParam(req, "bdzt_low");

		if (!jspParams.equals("")) {

			String sql = "SELECT * FROM D089H WHERE 1=1 ";

			if (!getParam(req, "gsdm_low").equals("")) {
				sql += Helper.sqlParams(req, "GSDM", "gsdm_low", "gsdm_high");
			}

			if (!getParam(req, "bdbh_low").equals("")) {
				sql += Helper.sqlParams(req, "BDBH", "bdbh_low", "bdbh_high");
			}

			if (!getParam(req, "bdrq_low").equals("")) {
				sql += Helper.sqlParams(req, "BDRQ", "bdrq_low", "bdrq_high");
			}

			if (!getParam(req, "lifnr_low").equals("")) {
				sql += Helper.sqlParams(req, "LIFNR", "lifnr_low", "lifnr_high");
			}

			if (!getParam(req, "sqyh_low").equals("")) {
				sql += Helper.sqlParams(req, "SQYH", "sqyh_low", "sqyh_high");
			}

			if (!getParam(req, "bdjq_low").equals("")) {
				sql += Helper.sqlParams(req, "BDJQ", "bdjq_low", "bdjq_high");
			}

			if (!getParam(req, "bdzt_low").equals("")) {
				sql += Helper.sqlParams(req, "BDZT", "bdzt_low", "bdzt_high");
			}

			sql += " ORDER BY GSDM desc,BDBH desc ";

			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		}

		req.setAttribute("list", list);
	}

	private void doD089ListMatPostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "gsdm_low") + getParam(req, "bdbh_low") + getParam(req, "bdrq_low") + getParam(req, "lifnr_low") + getParam(req, "matnr_low") + getParam(req, "bdjq_low")
				+ getParam(req, "bdzt_low");

		if (!jspParams.equals("")) {

			String sql = "SELECT D089L.BDBH, " //
					+ "       D089L.GSDM, " //
					+ "       D089H.BDRQ, " //
					+ "       TO_CHAR (D089H.QHSJ, 'YYYYMMDD') QHSJ, " //
					+ "       D089H.BDJG, " //
					+ "       D089H.BDYY, " //
					+ "       D089H.LIFNR, " //
					+ "       D089H.SORTL, " //
					+ "       D089L.MATNR, " //
					+ "       D089L.MAKTX, " //
					+ "       D089L.MEINS, " //
					+ "       D089H.WAERS, " //
					+ "       D089L.UNTPR, " //
					+ "       D089L.RMBPR, " //
					+ "       D089L.OLDPR, " //
					+ "       D089L.OLDPT, " //
					+ "       D089L.SAPCF, " //
					+ "       D089L.RFCCF, " //
					+ "       D089L.MATKL, " //
					+ "       D089L.EKGRP, " //
					+ "       D089H.BDDM, " //
					+ "       D089H.WERKS " //
					+ "  FROM IMES.D089L, IMES.D089H " //
					+ " WHERE D089H.GSDM = D089L.GSDM AND D089H.BDBH = D089L.BDBH " //
			;

			if (!getParam(req, "gsdm_low").equals("")) {
				sql += Helper.sqlParams(req, "D089L.GSDM", "gsdm_low", "gsdm_high");
			}

			if (!getParam(req, "bdbh_low").equals("")) {
				sql += Helper.sqlParams(req, "D089L.BDBH", "bdbh_low", "bdbh_high");
			}

			if (!getParam(req, "bdrq_low").equals("")) {
				sql += Helper.sqlParams(req, "D089H.BDRQ", "bdrq_low", "bdrq_high");
			}

			if (!getParam(req, "lifnr_low").equals("")) {
				sql += Helper.sqlParams(req, "D089H.LIFNR", "lifnr_low", "lifnr_high");
			}

			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "D089L.MATNR", "matnr_low", "matnr_high");
			}

			if (!getParam(req, "bdjq_low").equals("")) {
				sql += Helper.sqlParams(req, "D089H.BDJQ", "bdjq_low", "bdjq_high");
			}

			if (!getParam(req, "bdzt_low").equals("")) {
				sql += Helper.sqlParams(req, "D089H.BDZT", "bdzt_low", "bdzt_high");
			}

			sql += " ORDER BY D089L.MATNR,D089L.GSDM,D089L.BDBH";

			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		}

		req.setAttribute("list", list);
	}

	private void doEditGetAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Connection conn = getConnection();

		D089Dao d089Dao = new D089Dao(conn);

		D089H d089h = d089Dao.find(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));

		List<HashMap<String, Object>> d089ls = d089Dao.getD089Ls(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));

		List<QH_BDLC> BDLCS = QH_BDLC.findBy(d089h.getGSDM(), d089h.getBDDM(), d089h.getBDBH(), conn);

		conn.close();

		req.setAttribute("D089H", d089h);
		req.setAttribute("D089L", d089ls);
		req.setAttribute("BDLCS", BDLCS);

		render(req, resp, "/WEB-INF/pages/form/D089Edit.jsp");
	}

	private void doD089HSavePostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		D089H d089h = new D089H();

		d089h.setGSDM(getParam(req, "GSDM"));
		d089h.setBDDM(getParam(req, "BDDM"));
		d089h.setBDBH(getParam(req, "BDBH"));
		d089h.setBDRQ(getParam(req, "BDRQ"));
		d089h.setWERKS(getParam(req, "WERKS"));
		d089h.setEKORG(getParam(req, "EKORG"));
		d089h.setLIFNR(getParam(req, "LIFNR"));
		d089h.setSORTL(getParam(req, "SORTL"));
		d089h.setMWSKZ(getParam(req, "MWSKZ"));
		d089h.setWAERS(getParam(req, "WAERS"));
		d089h.setNAME1(getParam(req, "NAME1"));
		d089h.setBDYY(getParam(req, "BDYY"));
		d089h.setBDNR(getParam(req, "BDNR"));
		d089h.setMTCUR(getParam(req, "MTCUR"));
		d089h.setSNAMT(toDoubleParam(req, "SNAMT"));
		d089h.setAGAMT(toDoubleParam(req, "AGAMT"));
		d089h.setCUAMT(toDoubleParam(req, "CUAMT"));

		D089Dao d089Dao = new D089Dao(getConnection());

		if (d089h.getWERKS().equals("701A") || d089h.getEKORG().equals("L700") || d089h.getWAERS().equals("921A") || d089h.getEKORG().equals("L920")) {
			
		}else {
			if (d089h.getBDBH().equals("")) {

				d089h.setBDBH(Helper.getBDBH(getConnection(), d089h.getGSDM(), d089h.getBDDM(), d089h.getBDRQ().substring(0, 4)));
				d089h.setBDZT("C");
				d089h.setMANDT("168");
				d089h.setSQYH(getUid(req));
				d089h.setJLYH(getUid(req));
				d089h.setJLSJ(new Date());
				d089Dao.insertD089H(d089h);

			} else {

				d089h.setGXYH(getUid(req));
				d089h.setGXSJ(new Date());
				d089Dao.updateD089H(d089h);
			}
		}
		

		d089Dao.con.close();

		resp.sendRedirect("/iMes/D089/EDIT?GSDM=" + d089h.getGSDM() + "&BDDM=" + d089h.getBDDM() + "&BDBH=" + d089h.getBDBH());

	}

	private void doCreateGetAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		D089H d089h = new D089H();

		d089h.setBDRQ(Helper.fmtDate(new Date(), "yyyyMMdd"));
		d089h.setSQYH(getUid(req));
		d089h.setBDDM("D089");

		req.setAttribute("D089H", d089h);

		render(req, resp, "/WEB-INF/pages/form/D089Edit.jsp");

	}

	private void ajaxGetSupplier(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String sinfo = "";
		String ekorg = "";
		String waers = "";
		String werks = "";

		String sql = "SELECT (REPLACE(NAME1,',','') || ',' || SORTL || ',' || STCD2 || ',' || WAERS || ',' || EKORG) SINFO, EKORG, WAERS " //
				+ "  FROM SAPSR3.LFA1, SAPSR3.LFM1 " //
				+ " WHERE LFA1.MANDT = '168' AND LFA1.LIFNR = ? AND LFM1.MANDT = LFA1.MANDT AND LFM1.LIFNR = LFA1.LIFNR AND LFM1.LOEVM <> 'X'" //
				+ " ORDER BY EKORG DESC";

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, getParam(req, "lifnr"));
		ResultSet rst = pstm.executeQuery();
		if (rst.next()) {
			sinfo = rst.getString("SINFO");
			ekorg = rst.getString("EKORG");
			waers = rst.getString("WAERS");
		}
		rst.close();
		pstm.close();
		conn.close();

		if (ekorg.equals("L400")) {
			if (waers.equals("RMB")) {
				werks = "482A";
			} else {
				werks = "481A";
			}
		} else if (ekorg.equals("L300")) {
			if (waers.equals("RMB")) {
				werks = "382A";
			} else {
				werks = "381A";
			}
		} else if (ekorg.equals("L700")) {
			werks = "701A";
		} else if (ekorg.equals("L210")) {
			werks = "281A";
		} else if (ekorg.equals("L100")) {
			werks = "101A";
		} else if (ekorg.equals("L111")) {
			if (waers.equals("RMB")) {
				werks = "111A";
			} else {
				werks = "112A";
			}
		}

		sinfo += "," + werks;
		PrintWriter out = resp.getWriter();
		out.print(sinfo);
		out.close();

	}

	private void ajaxGetPriceHistory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "  SELECT LIFNR, LIFNM, EKORG, WERKS, DATAB, " //
				+ "         DATBI, UPR, KONWA, UPF, INFNR, " //
				+ "         CASE WHEN TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN DATAB AND DATBI THEN 'X' END VALID " //
				+ "    FROM IT.WPURINFORCD@ORACLETW " //
				+ "   WHERE MATNR = ? " //
				+ "ORDER BY VALID, DATAB DESC ";//

		Connection conn = getSapCoConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, getParam(req, "MATNR"));
		list = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();
		conn.close();

		double upr, dif, difpt;
		for (HashMap<String, Object> h : list) {

			upr = toDouble(h.get("UPR").toString());

			dif = toDoubleParam(req, "RMBPR") - upr;

			if (upr != 0) {
				difpt = (dif / upr) * 100;
			} else {
				difpt = 0;
			}

			h.put("DIF", dif);
			h.put("DIFPT", difpt);
		}

		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/form/D089PrcHis.jsp");

	}

	private void ajaxGetOpenPO(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT MDBS.EBELN, " //
				+ "       MDBS.EBELP, " //
				+ "       MDBS.ETENR, " //
				+ "       MDBS.MATNR, " //
				+ "       MDBS.WERKS, " //
				+ "       MDBS.LGORT, " //
				+ "       MDBS.MEINS, " //
				+ "       MDBS.MENGE, " //
				+ "       MDBS.WEMNG, " //
				+ "       (MDBS.MENGE - MDBS.WEMNG) REMNG, " //
				+ "       EKKO.LIFNR, " //
				+ "       EKKO.AEDAT, " //
				+ "       EKKO.EKORG, " //
				+ "       LFA1.SORTL, " //
				+ "       EKKO.WAERS, " //
				+ "       (EKPO.NETPR / EKPO.PEINH) NETPR, " //
				+ "       NVL ( " //
				+ "          (SELECT CASE WHEN EKKO.WAERS IN ('JPY', 'TWD') THEN 100 * UKURS ELSE UKURS END " //
				+ "             FROM SAPSR3.TCURR " //
				+ "            WHERE     MANDT = '168' " //
				+ "                  AND KURST = 'M' " //
				+ "                  AND TCURR = 'RMB' " //
				+ "                  AND FCURR = EKKO.WAERS " //
				+ "                  AND (99999999 - GDATU) <= TO_CHAR (SYSDATE, 'YYYYMMDD') " //
				+ "                  AND ROWNUM = 1), " //
				+ "          1) " //
				+ "          MRATE, " //
				+ "          MDBS.EINDT " //
				+ "  FROM SAPSR3.MDBS, " //
				+ "       SAPSR3.EKKO, " //
				+ "       SAPSR3.LFA1, " //
				+ "       SAPSR3.EKPO " //
				+ " WHERE     MDBS.MANDT = '168' " //
				+ "       AND MDBS.BSTYP = 'F' " //
				+ "       AND MDBS.MATNR <> ' ' " //
				+ "       AND (MDBS.LOEKZ = ' ' OR MDBS.LOEKZ = 'S') " //
				+ "       AND MDBS.ELIKZ = ' ' " //
				+ "       AND MDBS.MENGE <> MDBS.WEMNG " //
				+ "       AND EKKO.MANDT = MDBS.MANDT " //
				+ "       AND EKKO.EBELN = MDBS.EBELN " //
				+ "       AND EKKO.LIFNR NOT BETWEEN 'L1' AND 'L9' " //
				+ "       AND EKKO.EKORG IN ('L400', 'L100') " //
				+ "       AND LFA1.MANDT = EKKO.MANDT " //
				+ "       AND LFA1.LIFNR = EKKO.LIFNR " //
				+ "       AND EKPO.MANDT = MDBS.MANDT " //
				+ "       AND EKPO.EBELN = MDBS.EBELN " //
				+ "       AND EKPO.EBELP = MDBS.EBELP " //
				+ "       AND MDBS.MATNR = ? ORDER BY EINDT DESC"; //

		Connection conn = getSapPrdConnection();

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, getParam(req, "MATNR"));
		list = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();
		conn.close();

		double upr, dif, difpt;
		for (HashMap<String, Object> h : list) {

			upr = toDouble(h.get("NETPR").toString()) * toDouble(h.get("MRATE").toString());

			dif = toDoubleParam(req, "RMBPR") - upr;

			if (upr != 0) {
				difpt = (dif / upr) * 100;
			} else {
				difpt = 0;
			}

			h.put("UPR", upr);
			h.put("DIF", dif);
			h.put("DIFPT", difpt);

			if (h.get("LIFNR").equals(getParam(req, "LIFNR"))) {
				h.put("BGCOLOR", "#ebf1de");
			} else {
				h.put("BGCOLOR", "#ffffff");
			}
		}

		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/form/D089OpenPO.jsp");

	}

	private void ajaxGetChgPriceErrLog(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT * FROM D089Z WHERE GSDM=? AND BDDM=? AND BDBH=?";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, getParam(req, "GSDM"));
		pstm.setString(2, getParam(req, "BDDM"));
		pstm.setString(3, getParam(req, "BDBH"));
		list = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/form/D089ChgPriceErrLog.jsp");
	}
}
