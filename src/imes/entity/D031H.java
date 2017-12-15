package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class D031H {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String BDRQ = "";
	private String BDZT = "";
	private String YYLB = "";
	private String YYSM = "";
	private String QHYH = "";
	private Date QHSJ = new Date();
	private Date YXSJ = new Date();
	private String JLYH = "";
	private Date JLSJ = new Date();
	private String GXYH = "";
	private Date GXSJ = new Date();
	private String BDJG = "0";
	private String BDFD = "";

	public int insertDb(Connection conn) throws Exception {
		int i;
		String sql = "INSERT INTO D031H(GSDM,BDDM,BDBH,BDRQ,BDZT,YYLB,YYSM,QHYH,QHSJ,YXSJ,JLYH,JLSJ,GXYH,GXSJ,BDJG)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, BDRQ);
		pstm.setString(5, BDZT);
		pstm.setString(6, YYLB);
		pstm.setString(7, YYSM);
		pstm.setString(8, QHYH);
		pstm.setTimestamp(9, Helper.toSqlDate(QHSJ));
		pstm.setTimestamp(10, Helper.toSqlDate(YXSJ));
		pstm.setString(11, JLYH);
		pstm.setTimestamp(12, Helper.toSqlDate(JLSJ));
		pstm.setString(13, GXYH);
		pstm.setTimestamp(14, Helper.toSqlDate(GXSJ));
		pstm.setString(15, BDJG);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public void find(Connection conn) {

		String sql = "SELECT * FROM D031H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			ResultSet rst = pstm.executeQuery();

			if (rst.next()) {

				BDRQ = rst.getString("BDRQ");
				BDZT = rst.getString("BDZT");
				YYLB = rst.getString("YYLB");
				YYSM = rst.getString("YYSM");
				QHYH = rst.getString("QHYH");
				QHSJ = rst.getTimestamp("QHSJ");
				YXSJ = rst.getTimestamp("YXSJ");
				JLYH = rst.getString("JLYH");
				JLSJ = rst.getTimestamp("JLSJ");
				GXYH = rst.getString("GXYH");
				GXSJ = rst.getTimestamp("GXSJ");
				BDJG = rst.getString("BDJG");
				BDFD = rst.getString("BDFD");
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

	public String getYYLB() {
		return YYLB;
	}

	public void setYYLB(String yYLB) {
		YYLB = yYLB;
	}

	public String getYYSM() {
		return YYSM;
	}

	public void setYYSM(String yYSM) {
		YYSM = yYSM;
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

	public Date getYXSJ() {
		return YXSJ;
	}

	public void setYXSJ(Date yXSJ) {
		YXSJ = yXSJ;
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

	public String getBDJG() {
		return BDJG;
	}

	public void setBDJG(String bDJG) {
		BDJG = bDJG;
	}

	public String getBDFD() {
		return BDFD;
	}

	public void setBDFD(String bDFD) {
		BDFD = bDFD;
	}
	
}
