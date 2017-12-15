package imes.dao;

import imes.core.Helper;
import imes.entity.QH_BDLC;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class BaseDao {
	public Connection con = null;
	public PreparedStatement pstm = null;
	public ResultSet rst = null;
	public HttpServletRequest req = null;

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection conn) {
		this.con = conn;
	}

	public void close() {
		try {
			rst = null;
			pstm = null;
			con.close();
		} catch (SQLException e) {
			con = null;
			e.printStackTrace();
		}
		;
	}

	public String toString(Object object) {
		return object == null ? "" : object.toString().trim();
	}

	public String getParam(String param) {
		return req.getParameter(param) == null ? "" : req.getParameter(param).trim();
	}
	
	public String getParam(HttpServletRequest req, String param) {
		return req.getParameter(param) == null ? "" : req.getParameter(param).trim();
	}

	public String currentUser() {
		return req.getSession().getAttribute("uid").toString();
	}
	
	public String getParam(HttpServletRequest req, String param, String format) {
		if (req.getParameter(param) == null) {
			return "";
		} else {
			try {
				return String.format(format,
						Long.parseLong(req.getParameter(param).trim()));
			} catch (Exception e) {
				return "";
			}
		}
	}

	public String toOracleSqlDate(Object obj) {
		if (obj == null)
			return "TO_DATE('','YYYYMMDDHH24MISS')";

		Date date = (Date) obj;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = sdf.format(date);
		sdf = null;
		return "TO_DATE('" + dateString + "','YYYYMMDDHH24MISS')";
	}

	public void get(Object obj, int id) {
		@SuppressWarnings("rawtypes")
		Class objClass = obj.getClass();
		String buf[] = objClass.getName().split("\\.");
		String tableName = (buf.length > 0) ? buf[buf.length - 1] : buf[0];
		String fieldType = "";

		String sql = "SELECT * FROM " + tableName + " WHERE id=" + id;
		try {
			pstm = con.prepareStatement(sql);
			rst = pstm.executeQuery();
			if (rst.next()) {

				for (Field field : objClass.getDeclaredFields()) {

					field.setAccessible(true);
					fieldType = field.getType().getName().toLowerCase();

					if (fieldType.contains("date")) {

						field.set(obj,
								rst.getTimestamp(field.getName().toUpperCase()));

					} else if (fieldType.contains("int")) {

						field.set(obj,
								rst.getInt(field.getName().toUpperCase()));

					} else if (fieldType.contains("double")) {

						field.set(obj,
								rst.getDouble(field.getName().toUpperCase()));

					} else if (fieldType.contains("float")) {

						field.set(obj,
								rst.getFloat(field.getName().toUpperCase()));

					} else if (fieldType.contains("string")) {

						field.set(obj,
								rst.getString(field.getName().toUpperCase()));

					} else if (fieldType.contains("boolean")) {

						if (rst.getString(field.getName().toUpperCase()) != null) {

							field.set(
									obj,
									rst.getString(field.getName().toUpperCase())
											.equals("Y") ? true : false);
						}

					} else {

						field.set(obj,
								rst.getObject(field.getName().toUpperCase()));
					}
				}
			}
			rst.close();
			pstm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int delete(@SuppressWarnings("rawtypes") Class objClass, int id)
			throws SQLException {
		String buf[] = objClass.getName().split("\\.");
		String tableName = (buf.length > 0) ? buf[buf.length - 1] : buf[0];
		String sql = "DELETE FROM " + tableName + " WHERE id=" + id;
		pstm = con.prepareStatement(sql);
		int i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public int save(Object obj) throws SQLException, IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchFieldException {

		Field field = obj.getClass().getDeclaredField("id");
		field.setAccessible(true);
		return (field.getInt(obj) == 0) ? insert(obj) : update(obj);
	}

	public int update(Object obj) throws SQLException,
			IllegalArgumentException, IllegalAccessException {
		int id = 0;

		@SuppressWarnings("rawtypes")
		Class objClass = obj.getClass();
		String buf[] = objClass.getName().split("\\.");
		String tableName = (buf.length > 0) ? buf[buf.length - 1] : buf[0];
		String values = "";
		String value = "";
		String fieldType = "";

		for (Field field : objClass.getDeclaredFields()) {

			field.setAccessible(true);
			fieldType = field.getType().getName().toLowerCase();

			if (field.getName().toLowerCase().equals("id")) {
				id = field.getInt(obj);
			} else {
				value = field.getName() + "=";
				if (fieldType.contains("date")) {
					value += toOracleSqlDate(field.get(obj));

				} else if (fieldType.contains("int")) {
					value += (field.get(obj) == null) ? "0" : Integer
							.toString(field.getInt(obj));

				} else if (fieldType.contains("double")
						|| fieldType.contains("float")) {
					value += (field.get(obj) == null) ? "0" : Double
							.toString(field.getDouble(obj));

				} else if (fieldType.contains("string")) {
					value += (field.get(obj) == null) ? "''" : "'"
							+ field.get(obj).toString() + "'";

				} else if (fieldType.contains("boolean")) {
					value += (field.get(obj) == null) ? "' '" : field
							.getBoolean(obj) ? "'Y'" : "'N'";
				} else {
					value += (field.get(obj) == null) ? "''" : "'"
							+ field.get(obj).toString() + "'";
				}

				values += (values.length() == 0) ? value : "," + value;
			}
		}

		if (id == 0)
			return 0;

		String sql = "UPDATE " + tableName + " SET " + values + " WHERE id="
				+ Integer.toString(id);

		pstm = con.prepareStatement(sql);
		pstm.executeUpdate();
		pstm.close();

		return id;
	}

	public int insert(Object obj) throws SQLException,
			IllegalArgumentException, IllegalAccessException {

		int id = 0;
		@SuppressWarnings("rawtypes")
		Class objClass = obj.getClass();
		String buf[] = objClass.getName().split("\\.");
		String tableName = (buf.length > 0) ? buf[buf.length - 1] : buf[0];
		String fieldNames = "";
		String values = "";
		String value = "";
		String fieldType = "";

		for (Field field : objClass.getDeclaredFields()) {
			fieldNames += (fieldNames.length() == 0) ? field.getName() : ","
					+ field.getName();

			field.setAccessible(true);
			fieldType = field.getType().getName().toLowerCase();

			if (field.getName().toLowerCase().equals("id")) {
				value = tableName + "_seq.NEXTVAL";

			} else {
				if (fieldType.contains("date")) {
					value = toOracleSqlDate(field.get(obj));

				} else if (fieldType.contains("int")) {
					value = (field.get(obj) == null) ? "0" : Integer
							.toString(field.getInt(obj));

				} else if (fieldType.contains("double")
						|| fieldType.contains("float")) {
					value = (field.get(obj) == null) ? "0" : Double
							.toString(field.getDouble(obj));

				} else if (fieldType.contains("string")) {
					value = (field.get(obj) == null) ? "''" : "'"
							+ field.get(obj).toString() + "'";

				} else if (fieldType.contains("boolean")) {
					value = (field.get(obj) == null) ? "' '" : field
							.getBoolean(obj) ? "'Y'" : "'N'";
				} else {
					value = (field.get(obj) == null) ? "''" : "'"
							+ field.get(obj).toString() + "'";
				}

			}

			values += (values.length() == 0) ? value : "," + value;
		}

		String sql = "INSERT INTO " + tableName + "(" + fieldNames
				+ ") VALUES (" + values + ")";

		pstm = con.prepareStatement(sql, new String[] { "id" });

		pstm.executeUpdate();
		rst = pstm.getGeneratedKeys();
		if (rst.next()) {
			id = (rst.getInt(1));
		}
		rst.close();
		pstm.close();

		try {
			Field field = objClass.getDeclaredField("id");
			field.setAccessible(true);
			field.set(obj, id);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void qianHeResponse(HttpServletRequest req, String uid) {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");

		int BZDM = Helper.toInteger(getParam(req, "BZDM"));

		String QHNR = req.getParameter("QHNR");
		String QHJG = req.getParameter("QHJG");

		String HQYH = req.getParameter("HQYH");

		String YHCQ = req.getParameter("YHCQ");

		String sql;

		try {
			con.setAutoCommit(false);

			QH_BDLC old = new QH_BDLC();
			old.setGSDM(GSDM);
			old.setBDDM(BDDM);
			old.setBDBH(BDBH);
			old.setBZDM(BZDM);
			old.find(con);

			QH_BDLC e;
			int i = BZDM;

			boolean huiqian = false;

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
						e.verifyNewBZDM(con);
						e.insertDb(con);
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
				e.setYSYH(uid);
				e.setJLSJ(new Date());
				e.setYJSJ(null);
				e.setQHFD("");
				e.insertDb(con);
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
				e.verifyNewBZDM(con);
				e.setQHZT("3");
				e.setQHYH(uid);
				e.setQHSJ(new Date());
				e.setQHJG(QHJG);
				e.setQHNR(QHNR);
				e.insertDb(con);
				i = e.getBZDM();
			} else {
				sql = "UPDATE QH_BDLC " //
						+ "   SET QHZT='3', " //
						+ "       QHYH=?, " //
						+ "       QHSJ=SYSDATE, " //
						+ "       QHJG=?, " //
						+ "       QHNR=? " //
						+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //

				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, uid);
				pstm.setString(2, QHJG);
				pstm.setString(3, QHNR);
				pstm.setString(4, GSDM);
				pstm.setString(5, BDDM);
				pstm.setString(6, BDBH);
				pstm.setInt(7, BZDM);
				pstm.executeUpdate();
			}

			con.commit();
			con.setAutoCommit(true);

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

}
