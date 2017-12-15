package imes.core;

import imes.entity.QH_BDLC;
import imes.entity.QH_BZLC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QianHe {

	private String GSDM;
	private String LXDM;
	private String BDDM;
	private String BDBH;
	private String Mgr;
	private String SQYH;

	public QianHe() {
		super();
	}

	public QianHe(String gSDM, String lXDM, String bDDM, String bDBH, String mgr) {
		super();
		GSDM = gSDM;
		LXDM = lXDM;
		BDDM = bDDM;
		BDBH = bDBH;
		Mgr = mgr;
	}

	public QianHe(String gSDM, String bDDM, String bDBH, String mgr) {
		super();
		GSDM = gSDM;
		LXDM = bDDM;
		BDDM = bDDM;
		BDBH = bDBH;
		Mgr = mgr;
	}
	
	// public List<QH_BDLC> crtRoute(Connection conn) throws Exception {
	//
	// Boolean mgrFound = false;
	//
	// List<QH_BDLC> routes = new ArrayList<QH_BDLC>();
	// QH_BDLC e;
	// List<QH_BZLC> stdRoutes = getStdRoutes(conn);
	//
	// for (QH_BZLC s : stdRoutes) {
	// e = new QH_BDLC();
	// e.setGSDM(GSDM);
	// e.setBDDM(BDDM);
	// e.setBDBH(BDBH);
	// e.setLXDM(LXDM);
	// e.setQHZT("0");
	// e.setBZDM(s.getBZDM());
	// e.setLCLX(s.getLCLX());
	// e.setMINQ(s.getMINQ());
	// e.setMAXQ(s.getMAXQ());
	// e.setMINA(s.getMINA());
	// e.setMAXA(s.getMAXA());
	// e.setMINP(s.getMINP());
	// e.setMAXP(s.getMAXP());
	// e.setCURR(s.getCURR());
	// e.setFTXT(s.getFTXT());
	// e.setTTXT(s.getTTXT());
	// e.setZWDM(s.getZWDM());
	// e.setQHLX(s.getQHLX());
	// e.setYXXS(s.getYXXS());
	// e.setZFYN(s.getZFYN());
	// e.setYJSJ(null);
	// e.setYXSJ(null);
	// e.setYSYH(s.getYHDM());
	// routes.add(e);
	//
	// if (Mgr.equals(e.getYSYH())) {
	//
	// mgrFound = true;
	// }
	// }
	//
	// if (!mgrFound) {
	//
	// e = crtMgrRoute();
	// routes.add(e);
	// }
	//
	// return routes;
	// }

	public List<QH_BDLC> crtRoute(Connection conn, String condField, String condValue) throws Exception {

		boolean approverFound = false;

		List<QH_BDLC> routes = new ArrayList<QH_BDLC>();
		QH_BDLC e = crtMgrRoute();
		routes.add(e);
		routes.add(crtSQYHRoute());
		
		List<QH_BZLC> stdRoutes = getStdRoutes(conn);
		for (QH_BZLC s : stdRoutes) {
			e = new QH_BDLC();
			e.setGSDM(GSDM);
			e.setBDDM(BDDM);
			e.setBDBH(BDBH);
			e.setLXDM(LXDM);
			e.setQHZT("0");
			e.setBZDM(s.getBZDM());
			e.setLCLX(s.getLCLX());
			e.setMINQ(s.getMINQ());
			e.setMAXQ(s.getMAXQ());
			e.setMINA(s.getMINA());
			e.setMAXA(s.getMAXA());
			e.setMINP(s.getMINP());
			e.setMAXP(s.getMAXP());
			e.setCURR(s.getCURR());
			e.setFTXT(s.getFTXT());
			e.setTTXT(s.getTTXT());
			e.setZWDM(s.getZWDM());
			e.setYXXS(s.getYXXS());
			e.setZFYN(s.getZFYN());
			e.setSQYH(SQYH);
			e.setYJSJ(null);
			e.setYXSJ(null);
			e.setYSYH(s.getYHDM());

			if (s.getQHLX().equals("A")) {
				if (condField.equals("Q")) {
					double value = Double.parseDouble(condValue);
					if (value >= s.getMINQ() && value <= s.getMAXQ()) {
						e.setQHLX(s.getQHLX());
					} else {
						e.setQHLX("R");
					}
				} else if (condField.equals("A")){
					double value = Double.parseDouble(condValue);
					if (value >= s.getMINA() && value <= s.getMAXA()) {
						e.setQHLX(s.getQHLX());
					} else {
						e.setQHLX("R");
					}
				} else if (condField.equals("P")) {
					double value = Double.parseDouble(condValue);
					if (value < 0) {
						e.setQHLX(s.getQHLX());
					} else if (value >= s.getMINP() && value <= s.getMAXP()) {
						e.setQHLX(s.getQHLX());
					} else {
						e.setQHLX("R");
					}
				}
			} else {
				e.setQHLX(s.getQHLX());
			}

			if (approverFound && s.getQHLX().equals("A")) {

				// only allow one approval in the route

			} else {

				if (approverFound && s.getQHLX().equals("R")){
					e.setQHLX("I");
					e.setLCLX("P");
				}
				
				routes.add(e);
			}

			if (e.getQHLX().equals("A")) {
				approverFound = true;
			}

		} // for (QH_BZLC s : stdRoutes)
		return routes;
	}

	private List<QH_BZLC> getStdRoutes(Connection conn) throws Exception {
		QH_BZLC e;
		List<QH_BZLC> list = new ArrayList<QH_BZLC>();
		String sql = "SELECT GSDM,LXDM,BZDM,T1.YXFD,T1.YXTD,LCLX,MINQ,MAXQ,MINA,MAXA,MINP,MAXP,CURR,FTXT,TTXT,T1.ZWDM,QHLX,YXXS,ZFYN,NVL(QHYH,YHDM) YHDM  " //
				+ "FROM QH_BZLC T1,QH_ZWYH T2 WHERE T2.ZWDM(+)=T1.ZWDM AND SYSDATE BETWEEN T1.YXFD AND T1.YXTD AND SYSDATE BETWEEN T2.YXFD(+) AND T2.YXTD(+) " //
				+ "AND GSDM=? AND LXDM=? ORDER BY BZDM ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			e = new QH_BZLC();
			e.setGSDM(rst.getString("GSDM"));
			e.setLXDM(rst.getString("LXDM"));
			e.setBZDM(rst.getInt("BZDM"));
			e.setYXFD(rst.getString("YXFD"));
			e.setYXTD(rst.getString("YXTD"));
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
			e.setYHDM(rst.getString("YHDM"));
			list.add(e);
		}
		rst.close();
		pstm.close();
		return list;
	}

	private QH_BDLC crtMgrRoute() {
		QH_BDLC e = new QH_BDLC();
		e.setGSDM(GSDM);
		e.setBDDM(BDDM);
		e.setBDBH(BDBH);
		e.setLXDM(LXDM);
		e.setQHZT("1");
		e.setBZDM(100);
		e.setLCLX("S");
		e.setZWDM("MGR");
		e.setQHLX("R");
		e.setYXXS(100);
		e.setZFYN("Y");
		e.setYJSJ(null);
		e.setYXSJ(null);
		e.setSQYH(SQYH);
		e.setYSYH(Mgr);
		return e;
	}

	private QH_BDLC crtSQYHRoute() {
		QH_BDLC e = new QH_BDLC();
		e.setGSDM(GSDM);
		e.setBDDM(BDDM);
		e.setBDBH(BDBH);
		e.setLXDM(LXDM);
		e.setQHZT("0");
		e.setBZDM(9999);
		e.setLCLX("S");
		e.setZWDM("SQYH");
		e.setQHLX("Z");
		e.setYXXS(1000);
		e.setZFYN("Y");
		e.setYJSJ(null);
		e.setYXSJ(null);
		e.setYSYH(SQYH);
		e.setSQYH(SQYH);
		return e;
	}
	
	public List<QH_BDLC> getNewStdRoutes(Connection conn, String condField, String condValue) {
		QH_BDLC e;
		List<QH_BDLC> routes = new ArrayList<QH_BDLC>();
		try {
			routes.add(crtSQYHRoute());
			
			List<QH_BZLC> stdRoutes = getStdRoutes(conn);
			for (QH_BZLC s : stdRoutes) {
				e = new QH_BDLC();
				e.setGSDM(GSDM);//System.out.println("GSDM:"+GSDM);
				e.setBDDM(BDDM);
				e.setBDBH(BDBH);
				e.setLXDM(LXDM);
				e.setQHZT("0");
				e.setBZDM(s.getBZDM());//System.out.println("s.getBZDM():"+s.getBZDM());
				e.setLCLX(s.getLCLX());
				e.setMINQ(s.getMINQ());
				e.setMAXQ(s.getMAXQ());
				e.setMINA(s.getMINA());
				e.setMAXA(s.getMAXA());
				e.setMINP(s.getMINP());
				e.setMAXP(s.getMAXP());
				e.setCURR(s.getCURR());
				e.setFTXT(s.getFTXT());
				e.setTTXT(s.getTTXT());
				e.setZWDM(s.getZWDM());
				e.setYXXS(s.getYXXS());
				e.setZFYN(s.getZFYN());
				e.setSQYH(SQYH);
				e.setYJSJ(null);
				e.setYXSJ(null);
				e.setYSYH(s.getYHDM());

				if (s.getQHLX().equals("A")) {
					if (condField.equals("Q")) {
						double value = Double.parseDouble(condValue);
						if (value >= s.getMINQ() && value <= s.getMAXQ()) {
							e.setQHLX(s.getQHLX());
						} else {
							e.setQHLX("R");
						}
					} else if (condField.equals("A")){
						double value = Double.parseDouble(condValue);
						if (value >= s.getMINA() && value <= s.getMAXA()) {
							e.setQHLX(s.getQHLX());
						} else {
							e.setQHLX("R");
						}
					}
				} else {
					e.setQHLX(s.getQHLX());
				}

//				if (approverFound && s.getQHLX().equals("A")) {
//
//					// only allow one approval in the route
//
//				} else {
//
					routes.add(e);
//				}
//
//				if (e.getQHLX().equals("A")) {
//					approverFound = true;
//				}

			}
			return routes;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return null;
	}
	
	public String getGSDM() {
		return GSDM;
	}

	public void setGSDM(String gSDM) {
		GSDM = gSDM;
	}

	public String getLXDM() {
		return LXDM;
	}

	public void setLXDM(String lXDM) {
		LXDM = lXDM;
	}

	public String getBDDM() {
		return BDDM;
	}

	public void setBDDM(String bDDM) {
		BDDM = bDDM;
	}

	public String getBDBH() {
		return BDBH;
	}

	public void setBDBH(String bDBH) {
		BDBH = bDBH;
	}

	public String getMgr() {
		return Mgr;
	}

	public void setMgr(String mgr) {
		Mgr = mgr;
	}

	public String getSQYH() {
		return SQYH;
	}

	public void setSQYH(String sQYH) {
		SQYH = sQYH;
	}

}
