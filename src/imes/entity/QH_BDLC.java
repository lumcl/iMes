package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QH_BDLC implements Cloneable {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String LXDM = "";
	private String QHZT = "";
	private int BZDM = 0; // 簽核步驟代碼 -- 序號
	private String LCLX = ""; // 簽核流程類型 - Serial / Paraller
	private double MINQ = 0;
	private double MAXQ = 0;
	private double MINA = 0;
	private double MAXA = 0;
	private double MINP = 0;
	private double MAXP = 0;
	private String CURR = "";
	private String FTXT = "";
	private String TTXT = "";
	private String ZWDM = "";
	private String QHLX = ""; // 簽核類型 A,I,R
	private double YXXS = 0; // 簽核規定有效時間（小時）
	private String ZFYN = "";
	private String SQYH = "";
	private Date JLSJ = new Date();
	private Date YJSJ = null;
	private Date YXSJ = null;
	private String YSYH = "";
	private String DLYH = "";
	private String QHYH = "";
	private Date QHSJ = null;
	private String QHJG = ""; // 簽核結果
	private String QHNR = ""; // 簽核內容
	private String QHFD = ""; // 文件連接

	private String BDTX = "";

	public void find(Connection conn) throws Exception {
		// BZDM
		String sql = "SELECT * FROM QH_BDLC WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setInt(4, BZDM);

		ResultSet rst = pstm.executeQuery();

		if (rst.next()) {
			LXDM = rst.getString("LXDM");
			QHZT = rst.getString("QHZT");
			LCLX = rst.getString("LCLX");
			MINQ = rst.getDouble("MINQ");
			MAXQ = rst.getDouble("MAXQ");
			MINA = rst.getDouble("MINA");
			MAXA = rst.getDouble("MAXA");
			MINP = rst.getDouble("MINP");
			MAXP = rst.getDouble("MAXP");
			CURR = rst.getString("CURR");
			FTXT = rst.getString("FTXT");
			TTXT = rst.getString("TTXT");
			ZWDM = rst.getString("ZWDM");
			QHLX = rst.getString("QHLX");
			YXXS = rst.getDouble("YXXS");
			ZFYN = rst.getString("ZFYN");
			JLSJ = rst.getTimestamp("JLSJ");
			YJSJ = rst.getTimestamp("YJSJ");
			YXSJ = rst.getTimestamp("YXSJ");
			YSYH = rst.getString("YSYH");
			DLYH = rst.getString("DLYH");
			QHYH = rst.getString("QHYH");
			QHSJ = rst.getTimestamp("QHSJ");
			QHJG = rst.getString("QHJG");
			QHNR = rst.getString("QHNR");
			QHFD = rst.getString("QHFD");
			SQYH = rst.getString("SQYH");
		}

		rst.close();
		pstm.close();
	}

	public static List<QH_BDLC> findBy(String GSDM, String BDDM, String BDBH, Connection conn) {

		List<QH_BDLC> list = new ArrayList<QH_BDLC>();

		QH_BDLC e;

		String sql = "SELECT * FROM QH_BDLC WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY BZDM";

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

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
				e.setJLSJ(rst.getTimestamp("JLSJ"));
				e.setYJSJ(rst.getTimestamp("YJSJ"));
				e.setYXSJ(rst.getTimestamp("YXSJ"));
				e.setYSYH(rst.getString("YSYH")==null?rst.getString("YSYH"):rst.getString("YSYH").trim());
				e.setDLYH(rst.getString("DLYH")==null?rst.getString("DLYH"):rst.getString("DLYH").trim());
				e.setQHYH(rst.getString("QHYH")==null?rst.getString("QHYH"):rst.getString("QHYH").trim());
				e.setQHSJ(rst.getTimestamp("QHSJ"));
				if(rst.getString("QHLX")!=null && !"".equals(rst.getString("QHLX")) && rst.getString("QHLX").toUpperCase().equals("I")){
					e.setQHJG("");
				}else{
					e.setQHJG(rst.getString("QHJG"));
				}
				e.setQHNR(rst.getString("QHNR"));
				e.setQHFD(rst.getString("QHFD"));
				e.setSQYH(rst.getString("SQYH")==null?rst.getString("SQYH"):rst.getString("SQYH").trim());
				list.add(e);

			}

			rst.close();
			pstm.close();

		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return list;
	}

	
	public void verifyNewBZDM(Connection conn) throws Exception {
		
		int i;
		
		String sql = "SELECT COUNT(*) CNT FROM QH_BDLC WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=? ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst;
		
		while(true){
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);
			pstm.setInt(4, BZDM);
			
			rst = pstm.executeQuery();
			
			if (rst.next()){
				i = rst.getInt("CNT");
			}else{
				i = 1;
			}
			
			rst.close();
			
			if (i == 0){
				break;
			}else{
				BZDM = BZDM + 1;
			}
		}
		
		pstm.close();
	}
	
	public int insertDb(Connection conn) throws Exception {
		
//		System.out.println("GSDM:"+GSDM);
//		System.out.println("BDDM:"+BDDM);
//		System.out.println("BDBH:"+BDBH);
//		System.out.println("LXDM:"+LXDM);
//		System.out.println("QHZT:"+QHZT);
//		System.out.println("BZDM:"+BZDM);
//		System.out.println("LCLX:"+LCLX);
//		System.out.println("MINQ:"+MINQ);
//		System.out.println("MAXQ:"+MAXQ);
//		System.out.println("MINA:"+MINA);
//		System.out.println("MAXA:"+MAXA);
//		System.out.println("MINP:"+MINP);
//		System.out.println("MAXP:"+MAXP);
//		System.out.println("CURR:"+CURR);
//		System.out.println("FTXT:"+FTXT);
//		System.out.println("TTXT:"+TTXT);
//		System.out.println("ZWDM:"+ZWDM);
//		System.out.println("QHLX:"+QHLX);
//		System.out.println("YXXS:"+YXXS);
//		System.out.println("ZFYN:"+ZFYN);
//		System.out.println("JLSJ:"+JLSJ);
//		System.out.println("YJSJ:"+YJSJ);
//		System.out.println("YXSJ:"+YXSJ);
//		System.out.println("YSYH:"+YSYH);
//		System.out.println("DLYH:"+DLYH);
//		System.out.println("QHYH:"+QHYH);
//		System.out.println("QHSJ:"+QHSJ);
//		System.out.println("QHJG:"+QHJG);
//		System.out.println("QHNR:"+QHNR);
//		System.out.println("QHFD:"+QHFD);
//		System.out.println("SQYH:"+SQYH);

		
		int i = 0;
		String sql = "INSERT INTO QH_BDLC " //
				+ "(GSDM,BDDM,BDBH,LXDM,QHZT,BZDM,LCLX,MINQ,MAXQ,MINA,MAXA,MINP,MAXP,CURR,FTXT,TTXT,ZWDM,QHLX,YXXS,ZFYN,JLSJ,YJSJ,YXSJ,YSYH,DLYH,QHYH,QHSJ,QHJG,QHNR,QHFD,SQYH) " //
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, LXDM);
		pstm.setString(5, QHZT);
		pstm.setInt(6, BZDM);
		pstm.setString(7, LCLX);
		pstm.setDouble(8, MINQ);
		pstm.setDouble(9, MAXQ);
		pstm.setDouble(10, MINA);
		pstm.setDouble(11, MAXA);
		pstm.setDouble(12, MINP);
		pstm.setDouble(13, MAXP);
		pstm.setString(14, CURR);
		pstm.setString(15, FTXT);
		pstm.setString(16, TTXT);
		pstm.setString(17, ZWDM);
		pstm.setString(18, QHLX);
		pstm.setDouble(19, YXXS);
		pstm.setString(20, ZFYN);
		pstm.setTimestamp(21, Helper.toSqlDate(JLSJ));
		pstm.setTimestamp(22, Helper.toSqlDate(YJSJ));
		pstm.setTimestamp(23, Helper.toSqlDate(YXSJ));
		pstm.setString(24, YSYH);
		pstm.setString(25, DLYH);
		pstm.setString(26, QHYH);
		pstm.setTimestamp(27, Helper.toSqlDate(QHSJ));
		pstm.setString(28, QHJG);
		pstm.setString(29, QHNR);
		pstm.setString(30, QHFD);
		pstm.setString(31, SQYH);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public String getGSDM() {
		return GSDM;
	}

	public void setGSDM(String gSDM) {
		GSDM = gSDM;
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

	public String getLXDM() {
		return LXDM;
	}

	public void setLXDM(String lXDM) {
		LXDM = lXDM;
	}

	public String getQHZT() {
		return QHZT;
	}

	public void setQHZT(String qHZT) {
		QHZT = qHZT;
	}

	public int getBZDM() {
		return BZDM;
	}

	public void setBZDM(int bZDM) {
		BZDM = bZDM;
	}

	public String getLCLX() {
		return LCLX;
	}

	public void setLCLX(String lCLX) {
		LCLX = lCLX;
	}

	public double getMINQ() {
		return MINQ;
	}

	public void setMINQ(double mINQ) {
		MINQ = mINQ;
	}

	public double getMAXQ() {
		return MAXQ;
	}

	public void setMAXQ(double mAXQ) {
		MAXQ = mAXQ;
	}

	public double getMINA() {
		return MINA;
	}

	public void setMINA(double mINA) {
		MINA = mINA;
	}

	public double getMAXA() {
		return MAXA;
	}

	public void setMAXA(double mAXA) {
		MAXA = mAXA;
	}

	public double getMINP() {
		return MINP;
	}

	public void setMINP(double mINP) {
		MINP = mINP;
	}

	public double getMAXP() {
		return MAXP;
	}

	public void setMAXP(double mAXP) {
		MAXP = mAXP;
	}

	public String getCURR() {
		return CURR;
	}

	public void setCURR(String cURR) {
		CURR = cURR;
	}

	public String getFTXT() {
		return FTXT;
	}

	public void setFTXT(String fTXT) {
		FTXT = fTXT;
	}

	public String getTTXT() {
		return TTXT;
	}

	public void setTTXT(String tTXT) {
		TTXT = tTXT;
	}

	public String getZWDM() {
		return ZWDM;
	}

	public void setZWDM(String zWDM) {
		ZWDM = zWDM;
	}

	public String getQHLX() {
		return QHLX;
	}

	public void setQHLX(String qHLX) {
		QHLX = qHLX;
	}

	public double getYXXS() {
		return YXXS;
	}

	public void setYXXS(double yXXS) {
		YXXS = yXXS;
	}

	public String getZFYN() {
		return ZFYN;
	}

	public void setZFYN(String zFYN) {
		ZFYN = zFYN;
	}

	public Date getJLSJ() {
		return JLSJ;
	}

	public void setJLSJ(Date jLSJ) {
		JLSJ = jLSJ;
	}

	public Date getYJSJ() {
		return YJSJ;
	}

	public void setYJSJ(Date yJSJ) {
		YJSJ = yJSJ;
	}

	public Date getYXSJ() {
		return YXSJ;
	}

	public void setYXSJ(Date yXSJ) {
		YXSJ = yXSJ;
	}

	public String getYSYH() {
		return YSYH;
	}

	public void setYSYH(String ySYH) {
		YSYH = ySYH;
	}

	public String getDLYH() {
		return DLYH;
	}

	public void setDLYH(String dLYH) {
		DLYH = dLYH;
	}

	public String getQHYH() {
		return QHYH;
	}

	public void setQHYH(String qHYH) {
		QHYH = qHYH;
	}

	public Date getQHSJ() {
		return QHSJ;
	}

	public void setQHSJ(Date qHSJ) {
		QHSJ = qHSJ;
	}

	public String getQHJG() {
		return QHJG;
	}

	public void setQHJG(String qHJG) {
		QHJG = qHJG;
	}

	public String getQHNR() {
		return QHNR;
	}

	public void setQHNR(String qHNR) {
		QHNR = qHNR;
	}

	public String getQHFD() {
		return QHFD;
	}

	public void setQHFD(String qHFD) {
		QHFD = qHFD;
	}

	public String getBDTX() {
		return BDTX;
	}

	public void setBDTX(String bDTX) {
		BDTX = bDTX;
	}

	public String getSQYH() {
		return SQYH;
	}

	public void setSQYH(String sQYH) {
		SQYH = sQYH;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
