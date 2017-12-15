package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class D045H {
	
	private String GSDM = "";  // -- 公司代码
	private String BDDM = "";  // -- 表单代码
	private String BDBH = "";  // -- 表单编号
	private String BDRQ = "";  // -- 表单日期
	private String BDZT = "";  // -- 簽核狀態
	private String BDJG = "";  // -- 簽核結果
	private String BDLX = "";  // -- 表单类型
	private String WERKS = ""; // -- 厂别
	private String CUST = "";  // -- 客户名称
	private String KOSTL = ""; // -- 部门编号
	private String KTEXT = ""; // -- 部门名称
	private String DELADD = "";// -- 交货地点
	private double BDAMT = 0;  // -- 表单金额
	private String INVNO = ""; // -- 发票号    
	private String CONO = "";  // -- 订单号
	private String SQYH = "";  // -- 签核用户
	private String QHYH = "";  // -- 签核用户
	private Date QHSJ = null;  // -- 签核时间
	private String JLYH = "";  // -- 用户
	private Date JLSJ = null;  // -- 时间
	private String GXYH = "";  // -- 用户
	private Date GXSJ = null;  // -- 时间
	private String BDFD = "";  // -- 签呈附件
	private String RSNUM = ""; // -- 记录数
	private String SAPUPDSTS = "";
	private String SAPUPDUSR = "";
	private Date SAPUPDDAT = null;
	private String SAPUPDMSG = "";

	public int insertDb(Connection conn) throws Exception {
		int i = 0;
		String sql = "INSERT INTO D045H (GSDM，BDDM，BDBH，BDRQ，BDZT，BDJG，BDLX，WERKS，CUST，KOSTL，KTEXT，DELADD，BDAMT，INVNO，CONO，SQYH，QHYH，QHSJ，JLYH，JLSJ，GXYH，GXSJ，BDFD，RSNUM, SAPUPDSTS, SAPUPDUSR, SAPUPDDAT, SAPUPDMSG) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, BDRQ);
		pstm.setString(5, BDZT);
		pstm.setString(6, BDJG);
		pstm.setString(7, BDLX);
		pstm.setString(8, WERKS);
		pstm.setString(9, CUST);
		pstm.setString(10, KOSTL);
		pstm.setString(11, KTEXT);
		pstm.setString(12, DELADD);
		pstm.setDouble(13, BDAMT);
		pstm.setString(14, INVNO);
		pstm.setString(15, CONO);
		pstm.setString(16, SQYH);
		pstm.setString(17, QHYH);
		pstm.setTimestamp(18, Helper.toSqlDate(QHSJ));
		pstm.setString(19, JLYH);
		pstm.setTimestamp(20, Helper.toSqlDate(JLSJ));
		pstm.setString(21, GXYH);
		pstm.setTimestamp(22, Helper.toSqlDate(GXSJ));
		pstm.setString(23, BDFD);
		pstm.setString(24, RSNUM);
		pstm.setString(25, SAPUPDSTS);
		pstm.setString(26, SAPUPDUSR);
		pstm.setTimestamp(27, Helper.toSqlDate(SAPUPDDAT));
		pstm.setString(28, SAPUPDMSG);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public void find(Connection conn) {

		String sql = "SELECT * FROM D045H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			ResultSet rst = pstm.executeQuery();

			if (rst.next()) {
				BDRQ = rst.getString("BDRQ");
				BDZT = rst.getString("BDZT");
				BDJG = rst.getString("BDJG");
				BDLX = rst.getString("BDLX");
				WERKS = rst.getString("WERKS");
				CUST = rst.getString("CUST");
				KOSTL = rst.getString("KOSTL");
				KTEXT = rst.getString("KTEXT");
				DELADD = rst.getString("DELADD");
				BDAMT = rst.getDouble("BDAMT");
				INVNO = rst.getString("INVNO");
				CONO = rst.getString("CONO");
				SQYH = rst.getString("SQYH");
				QHYH = rst.getString("QHYH");
				QHSJ = rst.getTimestamp("QHSJ");
				JLYH = rst.getString("JLYH");
				JLSJ = rst.getTimestamp("JLSJ");
				GXYH = rst.getString("GXYH");
				GXSJ = rst.getTimestamp("GXSJ");
				BDFD = rst.getString("BDFD");
				RSNUM = rst.getString("RSNUM");
				SAPUPDSTS = rst.getString("SAPUPDSTS");
				SAPUPDUSR = rst.getString("SAPUPDUSR");
				SAPUPDDAT = rst.getTimestamp("SAPUPDDAT");
				SAPUPDMSG = rst.getString("SAPUPDMSG");				
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getBDRQ() {
		return BDRQ;
	}

	public void setBDRQ(String bDRQ) {
		BDRQ = bDRQ;
	}

	public String getBDZT() {
		return BDZT;
	}

	public void setBDZT(String bDZT) {
		BDZT = bDZT;
	}

	public String getBDJG() {
		return BDJG;
	}

	public void setBDJG(String bDJG) {
		BDJG = bDJG;
	}

	public String getBDLX() {
		return BDLX;
	}

	public void setBDLX(String bDLX) {
		BDLX = bDLX;
	}

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getCUST() {
		return CUST;
	}

	public void setCUST(String cUST) {
		CUST = cUST;
	}

	public String getKOSTL() {
		return KOSTL;
	}

	public void setKOSTL(String kOSTL) {
		KOSTL = kOSTL;
	}

	public String getKTEXT() {
		return KTEXT;
	}

	public void setKTEXT(String kTEXT) {
		KTEXT = kTEXT;
	}

	public String getDELADD() {
		return DELADD;
	}

	public void setDELADD(String dELADD) {
		DELADD = dELADD;
	}

	public double getBDAMT() {
		return BDAMT;
	}

	public void setBDAMT(double bDAMT) {
		BDAMT = bDAMT;
	}

	public String getINVNO() {
		return INVNO;
	}

	public void setINVNO(String iNVNO) {
		INVNO = iNVNO;
	}

	public String getCONO() {
		return CONO;
	}

	public void setCONO(String cONO) {
		CONO = cONO;
	}

	public String getSQYH() {
		return SQYH;
	}

	public void setSQYH(String sQYH) {
		SQYH = sQYH;
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

	public String getJLYH() {
		return JLYH;
	}

	public void setJLYH(String jLYH) {
		JLYH = jLYH;
	}

	public Date getJLSJ() {
		return JLSJ;
	}

	public void setJLSJ(Date jLSJ) {
		JLSJ = jLSJ;
	}

	public String getGXYH() {
		return GXYH;
	}

	public void setGXYH(String gXYH) {
		GXYH = gXYH;
	}

	public Date getGXSJ() {
		return GXSJ;
	}

	public void setGXSJ(Date gXSJ) {
		GXSJ = gXSJ;
	}

	public String getBDFD() {
		return BDFD;
	}

	public void setBDFD(String bDFD) {
		BDFD = bDFD;
	}

	public String getRSNUM() {
		return RSNUM;
	}

	public void setRSNUM(String rSNUM) {
		RSNUM = rSNUM;
	}

	public String getSAPUPDSTS() {
		return SAPUPDSTS;
	}

	public void setSAPUPDSTS(String sAPUPDSTS) {
		SAPUPDSTS = sAPUPDSTS;
	}

	public String getSAPUPDUSR() {
		return SAPUPDUSR;
	}

	public void setSAPUPDUSR(String sAPUPDUSR) {
		SAPUPDUSR = sAPUPDUSR;
	}

	public Date getSAPUPDDAT() {
		return SAPUPDDAT;
	}

	public void setSAPUPDDAT(Date sAPUPDDAT) {
		SAPUPDDAT = sAPUPDDAT;
	}

	public String getSAPUPDMSG() {
		return SAPUPDMSG;
	}

	public void setSAPUPDMSG(String sAPUPDMSG) {
		SAPUPDMSG = sAPUPDMSG;
	}
}
