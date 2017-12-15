package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class D302H {
	private String GSDM = "";//公司
	private String BDDM = "";//表单
	private String BDBH = "";//表单编号
	private String BDRQ = "";//表单日期
	private String BDZT = "";//表单状态
	private String BDJG = "";//表单结果
	private String ZCBH = "";//资产编码
	private String ZCMC = "";//资产名称
	private String ZCLX = "";//资产类型
	private double BDAMT = 0;//
	private String SQYH = "";//申请用户
	private String QHYH = "";//签核用户
	private Date QHSJ = null;//
	private String JLYH = "";//
	private Date JLSJ = null;//
	private String GXYH = "";//
	private Date GXSJ = null;//
	private String BDFD = "";//附件
	private String WCRQ = "";//完成日期
	private String SQBM = "";//申请部门
	private String BMMC = "";//部门名称
    private String ZCCB = "";//资产成本中心
    private String BGR = "";//保管人
    private String ZBDT = "";//資產取得日期
    private String ZCBG = "";//资产保管部门
    private String ADDR = "";//資產存放地點
    private double ZMJZ = 0;//帳面價值
    private double QDJZ = 0;//取得價值
    private double QTY = 0;//數量
    private String QCNR = "";
	private String SUPPLIER = "";
	private String SUPPLIER_NAME = "";
	private String CONTACT = "";
	private String PHONE = "";
	private String PRICE = "";
	private String UNIT = "";
	private String QUALITY = "";
	private String CURRENCY = "";
	private String WXDH = "";
	private String BDBJ = ""; //表单备注
    
    public int insertDb(Connection conn) throws Exception {
		int i = 0;
		String sql = "INSERT INTO D302H (GSDM,BDDM,BDBH,BDRQ,BDZT,BDJG,ZCBH,ZCMC,ZCLX,SQBM,BMMC,BDAMT,SQYH,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,BDFD,WCRQ,ZCCB,BGR,ZBDT,ZCBG,ADDR,ZMJZ,QTY,QCNR,QDJZ,SUPPLIER,SUPPLIER_NAME,CONTACT,PHONE,PRICE,UNIT,QUALITY,CURRENCY,WXDH,BDBJ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, BDRQ);
		pstm.setString(5, BDZT);
		pstm.setString(6, BDJG);
		pstm.setString(7, ZCBH);
		pstm.setString(8, ZCMC);
		pstm.setString(9, ZCLX);
		pstm.setString(10, SQBM);
		pstm.setString(11, BMMC);
		pstm.setDouble(12, BDAMT);
		pstm.setString(13, SQYH);
		pstm.setString(14, QHYH);
		pstm.setTimestamp(15, Helper.toSqlDate(QHSJ));
		pstm.setString(16, JLYH);
		pstm.setTimestamp(17, Helper.toSqlDate(JLSJ));
		pstm.setString(18, GXYH);
		pstm.setTimestamp(19, Helper.toSqlDate(GXSJ));
		pstm.setString(20, BDFD);
		pstm.setString(21, WCRQ);
		pstm.setString(22, ZCCB);
		pstm.setString(23, BGR);
		pstm.setString(24, ZBDT);
		pstm.setString(25, ZCBG);
		pstm.setString(26, ADDR);
		pstm.setDouble(27, ZMJZ);
		pstm.setDouble(28, QTY);
		pstm.setString(29, QCNR);
		pstm.setDouble(30, QDJZ);
		pstm.setString(31,SUPPLIER);
		pstm.setString(32,SUPPLIER_NAME);
		pstm.setString(33,CONTACT);
		pstm.setString(34,PHONE);
		pstm.setString(35,PRICE);
		pstm.setString(36,UNIT);
		pstm.setString(37,QUALITY);
		pstm.setString(38,CURRENCY);
		pstm.setString(39,WXDH);
		pstm.setString(40, BDBJ);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public void find(Connection conn) {

		String sql = "SELECT * FROM D302H WHERE GSDM=? AND BDDM=? AND BDBH=?";
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
				ZCBH = rst.getString("ZCBH");
				ZCMC = rst.getString("ZCMC");
				ZCLX = rst.getString("ZCLX");
				SQBM = rst.getString("SQBM");
				BMMC = rst.getString("BMMC");
				BDAMT = rst.getDouble("BDAMT");
				SQYH = rst.getString("SQYH");
				QHYH = rst.getString("QHYH");
				QHSJ = rst.getTimestamp("QHSJ");
				JLYH = rst.getString("JLYH");
				JLSJ = rst.getTimestamp("JLSJ");
				GXYH = rst.getString("GXYH");
				GXSJ = rst.getTimestamp("GXSJ");
				BDFD = rst.getString("BDFD");
				WCRQ = rst.getString("WCRQ");
				ZCCB = rst.getString("ZCCB");
				BGR = rst.getString("BGR");
				ZBDT = rst.getString("ZBDT");
				ZCBG = rst.getString("ZCBG");
				ADDR = rst.getString("ADDR");
				ZMJZ = rst.getDouble("ZMJZ");
				QTY = rst.getDouble("QTY");
				QCNR = rst.getString("QCNR");
				QDJZ = rst.getDouble("QDJZ");
				SUPPLIER = rst.getString("SUPPLIER");
				SUPPLIER_NAME = rst.getString("SUPPLIER_NAME");
				CONTACT = rst.getString("CONTACT");
				PHONE = rst.getString("PHONE");
				PRICE = rst.getString("PRICE");
				UNIT = rst.getString("UNIT");
				QUALITY = rst.getString("QUALITY");
				CURRENCY = rst.getString("CURRENCY");
				WXDH = rst.getString("WXDH");
				BDBJ = rst.getString("BDBJ");
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findBy301H(Connection conn) {

		String sql = "SELECT * FROM D302H WHERE GSDM=? AND BDDM=? AND WXDH=?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, WXDH);

			ResultSet rst = pstm.executeQuery();

			if (rst.next()) {
				BDBH = rst.getString("BDBH");
				BDRQ = rst.getString("BDRQ");
				BDZT = rst.getString("BDZT");
				BDJG = rst.getString("BDJG");
				ZCBH = rst.getString("ZCBH");
				ZCMC = rst.getString("ZCMC");
				ZCLX = rst.getString("ZCLX");
				SQBM = rst.getString("SQBM");
				BMMC = rst.getString("BMMC");
				BDAMT = rst.getDouble("BDAMT");
				SQYH = rst.getString("SQYH");
				QHYH = rst.getString("QHYH");
				QHSJ = rst.getTimestamp("QHSJ");
				JLYH = rst.getString("JLYH");
				JLSJ = rst.getTimestamp("JLSJ");
				GXYH = rst.getString("GXYH");
				GXSJ = rst.getTimestamp("GXSJ");
				BDFD = rst.getString("BDFD");
				WCRQ = rst.getString("WCRQ");
				ZCCB = rst.getString("ZCCB");
				BGR = rst.getString("BGR");
				ZBDT = rst.getString("ZBDT");
				ZCBG = rst.getString("ZCBG");
				ADDR = rst.getString("ADDR");
				ZMJZ = rst.getDouble("ZMJZ");
				QTY = rst.getDouble("QTY");
				QCNR = rst.getString("QCNR");
				QDJZ = rst.getDouble("QDJZ");
				SUPPLIER = rst.getString("SUPPLIER");
				SUPPLIER_NAME = rst.getString("SUPPLIER_NAME");
				CONTACT = rst.getString("CONTACT");
				PHONE = rst.getString("PHONE");
				PRICE = rst.getString("PRICE");
				UNIT = rst.getString("UNIT");
				QUALITY = rst.getString("QUALITY");
				CURRENCY = rst.getString("CURRENCY");
				WXDH = rst.getString("WXDH");
				BDBJ = rst.getString("BDBJ");
			}else{
				BDRQ = "";
				BDZT = "";
				BDJG = "";
				ZCBH = "";
				ZCMC = "";
				ZCLX = "";
				SQBM = "";
				BMMC = "";
				BDAMT = 0;
				SQYH = "";
				QHYH = "";
				QHSJ = null;
				JLYH = "";
				JLSJ = null;
				GXYH = "";
				GXSJ = null;
				BDFD = "";
				WCRQ = "";
				ZCCB = "";
				BGR = "";
				ZBDT = "";
				ZCBG = "";
				ADDR = "";
				ZMJZ = 0;
				QTY = 0;
				QCNR = "";
				QDJZ = 0;
				SUPPLIER = "";
				SUPPLIER_NAME = "";
				CONTACT = "";
				PHONE = "";
				PRICE = "";
				UNIT = "";
				QUALITY = "";
				CURRENCY = "";
				WXDH = "";
				BDBJ = "";
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

	public String getZCBH() {
		return ZCBH;
	}

	public void setZCBH(String zCBH) {
		ZCBH = zCBH;
	}

	public String getZCMC() {
		return ZCMC;
	}

	public void setZCMC(String zCMC) {
		ZCMC = zCMC;
	}

	public String getZCLX() {
		return ZCLX;
	}

	public void setZCLX(String zCLX) {
		ZCLX = zCLX;
	}

	public double getBDAMT() {
		return BDAMT;
	}

	public void setBDAMT(double bDAMT) {
		BDAMT = bDAMT;
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

	public String getWCRQ() {
		return WCRQ;
	}

	public void setWCRQ(String wCRQ) {
		WCRQ = wCRQ;
	}

	public String getSQBM() {
		return SQBM;
	}

	public void setSQBM(String sQBM) {
		SQBM = sQBM;
	}

	public String getBMMC() {
		return BMMC;
	}

	public void setBMMC(String bMMC) {
		BMMC = bMMC;
	}

	public String getZCCB() {
		return ZCCB;
	}

	public void setZCCB(String zCCB) {
		ZCCB = zCCB;
	}

	public String getBGR() {
		return BGR;
	}

	public void setBGR(String bGR) {
		BGR = bGR;
	}

	public String getZBDT() {
		return ZBDT;
	}

	public void setZBDT(String zBDT) {
		ZBDT = zBDT;
	}

	public String getZCBG() {
		return ZCBG;
	}

	public void setZCBG(String zCBG) {
		ZCBG = zCBG;
	}

	public String getADDR() {
		return ADDR;
	}

	public void setADDR(String aDDR) {
		ADDR = aDDR;
	}

	public double getZMJZ() {
		return ZMJZ;
	}

	public void setZMJZ(double zMJZ) {
		ZMJZ = zMJZ;
	}

	public double getQDJZ() {
		return QDJZ;
	}

	public void setQDJZ(double qDJZ) {
		QDJZ = qDJZ;
	}

	public double getQTY() {
		return QTY;
	}

	public void setQTY(double qTY) {
		QTY = qTY;
	}

	public String getQCNR() {
		return QCNR;
	}

	public void setQCNR(String qCNR) {
		QCNR = qCNR;
	}

	public String getSUPPLIER() {
		return SUPPLIER;
	}

	public void setSUPPLIER(String sUPPLIER) {
		SUPPLIER = sUPPLIER;
	}

	public String getSUPPLIER_NAME() {
		return SUPPLIER_NAME;
	}

	public void setSUPPLIER_NAME(String sUPPLIER_NAME) {
		SUPPLIER_NAME = sUPPLIER_NAME;
	}

	public String getCONTACT() {
		return CONTACT;
	}

	public void setCONTACT(String cONTACT) {
		CONTACT = cONTACT;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getPRICE() {
		return PRICE;
	}

	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	public String getQUALITY() {
		return QUALITY;
	}

	public void setQUALITY(String qUALITY) {
		QUALITY = qUALITY;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getWXDH() {
		return WXDH;
	}

	public void setWXDH(String wXDH) {
		WXDH = wXDH;
	}

	public String getBDBJ() {
		return BDBJ;
	}

	public void setBDBJ(String bDBJ) {
		BDBJ = bDBJ;
	}

}
