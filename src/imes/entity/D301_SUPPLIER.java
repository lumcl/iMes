package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class D301_SUPPLIER {
	private String SUPPLIER = "";
	private String SUPPLIER_NAME = "";
	private String CONTACT = "";
	private String PHONE = "";
	private String JLYH = "";
	private Date JLSJ = null;
	
	public void insertDb(Connection conn) throws Exception{
		String sql = "INSERT INTO D301_SUPPLIER (SUPPLIER,SUPPLIER_NAME,CONTACT,PHONE,JLYH,JLSJ) VALUES(?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,SUPPLIER);
		pstm.setString(2,SUPPLIER_NAME);
		pstm.setString(3,CONTACT);
		pstm.setString(4,PHONE);
		pstm.setString(5,JLYH);
		pstm.setTimestamp(6,Helper.toSqlDate(JLSJ));
		pstm.executeUpdate();
		pstm.close();
	}
	
	public Boolean queryDb(Connection conn) throws Exception{
		Boolean isSuccess = false;
		String sql = "SELECT * FROM D301_SUPPLIER WHERE SUPPLIER = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,SUPPLIER);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
		{
			isSuccess = true;			
		}
		rs.close();
		pstm.close();
		return isSuccess;
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

}
