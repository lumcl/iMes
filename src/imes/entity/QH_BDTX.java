package imes.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QH_BDTX {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String BDTX = ""; // 說明

	public int insertDb(Connection conn) throws Exception {
		int i = 0;
		String sql = "INSERT INTO QH_BDTX " //
				+ "(GSDM,BDDM,BDBH,BDTX) " //
				+ "VALUES(?,?,?,?) "; //
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, BDTX);
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

	public String getBDTX() {
		return BDTX;
	}

	public void setBDTX(String bDTX) {
		BDTX = bDTX;
	}

}
