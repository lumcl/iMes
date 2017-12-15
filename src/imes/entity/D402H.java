package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class D402H {

	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String BDRQ = "";
	private String BMMC = "";
	private String JKYT = "";
	private double JKAMT = 0d;
	private String QHYH = "";
	private Date QHSJ = null;
	private String JLYH = "";
	private Date JLSJ = null;
	private String GXYH = "";
	private Date GXSJ = null;

	public int insertDb(Connection conn) throws Exception {
		int i = 0;
		String sql = "INSERT INTO D402H (GSDM,BDDM,BDBH,BDRQ,BMMC,JKYT,JKAMT,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, BDRQ);
		pstm.setString(5, BMMC);
		pstm.setString(6, JKYT);
		pstm.setDouble(7, JKAMT);
		pstm.setString(8, QHYH);
		pstm.setTimestamp(9, Helper.toSqlDate(QHSJ));
		pstm.setString(10, JLYH);
		pstm.setTimestamp(11, Helper.toSqlDate(JLSJ));
		pstm.setString(12, GXYH);
		pstm.setTimestamp(13, Helper.toSqlDate(GXSJ));
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public void find(Connection conn) {

		String sql = "SELECT * FROM D402H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			ResultSet rst = pstm.executeQuery();

			if (rst.next()) {
				GSDM = rst.getString("GSDM");
				BDDM = rst.getString("BDDM");
				BDBH = rst.getString("BDBH");
				BDRQ = rst.getString("BDRQ");
				BMMC = rst.getString("BMMC");
				JKYT = rst.getString("JKYT");
				JKAMT = rst.getDouble("JKAMT");
				QHYH = rst.getString("QHYH");
				QHSJ = rst.getTimestamp("QHSJ");
				JLYH = rst.getString("JLYH");
				JLSJ = rst.getTimestamp("JLSJ");
				GXYH = rst.getString("GXYH");
				GXSJ = rst.getTimestamp("GXSJ");
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

	public String getBMMC() {
		return BMMC;
	}

	public void setBMMC(String bMMC) {
		BMMC = bMMC;
	}

	public String getJKYT() {
		return JKYT;
	}

	public void setJKYT(String jKYT) {
		JKYT = jKYT;
	}

	public double getJKAMT() {
		return JKAMT;
	}

	public void setJKAMT(double jKAMT) {
		JKAMT = jKAMT;
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
}
