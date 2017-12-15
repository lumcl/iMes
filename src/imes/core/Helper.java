package imes.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Helper {

	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static String md5Java(String message){
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
           
            //converting byte array to Hexadecimal String
           StringBuilder sb = new StringBuilder(2*hash.length);
           for(byte b : hash){
               sb.append(String.format("%02x", b&0xff));
           }
          
           digest = sb.toString();
          
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }

	public static String getAction(HttpServletRequest req) {
		if (req.getParameter("action") == null) {
			return "";
		} else {
			return req.getParameter("action").toString().trim();
		}
	}

	public static String toUpperCase(String str) {
		if (str == null)
			return "";
		else
			return str.toUpperCase().trim();
	}

	public static String sqlParamsFormat(HttpServletRequest req, String fldName, String strParam, String endParam, String fmt) {
		String str = toUpperCase(req.getParameter(strParam));
		String end = toUpperCase(req.getParameter(endParam));
		if (str.equals(""))
			return "";
		if (end.equals(""))
			end = str;
		return " AND " + fldName + " BETWEEN '" + String.format(fmt, Long.parseLong(str.trim())) + "' AND '" + String.format(fmt, Long.parseLong(end.trim())) + "'";
	}

	public static String sqlParams(HttpServletRequest req, String fldName, String strParam, String endParam) {
		String str = toUpperCase(req.getParameter(strParam));
		String end = toUpperCase(req.getParameter(endParam));
		if (str.equals(""))
			return "";
		if (end.equals(""))
			end = str;
		return " AND " + fldName + " BETWEEN '" + str + "' AND '" + end + "'";
	}

	public static String sqlDateParams(HttpServletRequest req, String fldName, String strParam, String endParam) {
		String str = toUpperCase(req.getParameter(strParam));
		String end = toUpperCase(req.getParameter(endParam));
		if (str.equals(""))
			return "";
		if (end.equals(""))
			end = str;
		return " AND TO_CHAR(" + fldName + ",'YYYYMMDD') BETWEEN '" + str + "' AND '" + end + "'";
	}

	public static String sqlParamsValues(HttpServletRequest req, String fldName, String strParam) {

		if (req.getParameterValues(strParam) == null || req.getParameterValues(strParam).length == 0) {
			return "";
		}

		String val = "";
		for (String str : req.getParameterValues(strParam)) {

			if (val.equals("")) {
				val = "'" + str.trim() + "'";
			} else {
				val += ", '" + str.trim() + "'";
			}
		}
		return " AND " + fldName + " IN (" + val + ")";
	}

	public static String getBDBH(Connection conn, String GSDM, String BDDM, String BDYR) {
		String sql, BDQZ = "";
		PreparedStatement pstm, upstm;
		ResultSet rst;
		int BDHM = 0;

		try {
			sql = "select bdqz,bdhm from qh_bdbh where gsdm=? and bddm=? and bdyr=? for update nowait";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDYR);

			sql = "update qh_bdbh set bdhm = ? where gsdm=? and bddm=? and bdyr=?";
			upstm = conn.prepareStatement(sql);

			for (int i = 0; i < 5; i++) {
				try {
					rst = pstm.executeQuery();
					if (rst.next()) {
						BDQZ = rst.getString("BDQZ");
						BDHM = rst.getInt("BDHM");
						BDHM += 1;
						upstm.setInt(1, BDHM);
						upstm.setString(2, GSDM);
						upstm.setString(3, BDDM);
						upstm.setString(4, BDYR);
						upstm.executeUpdate();
						break;
					}
					rst.close();

				} catch (Exception e) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}

			pstm.close();
			upstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return BDQZ + Integer.toString(BDHM);
	}

	public static String fmtDate(Date date, String fmt) {
		// yyyy-MM-dd HH:mm:ss
		if (date == null) {
			return "";
		}
		long time = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String str = sdf.format(time);
		sdf = null;
		return str;
	}

	public static java.sql.Timestamp toSqlDate(java.util.Date date) {
		if (date == null)
			return null;
		else
			return new java.sql.Timestamp(date.getTime());
	}

	public static List<HashMap<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException {

		int rowid = 1;
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>();

			for (int i = 1; i <= columns; i++) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			row.put("ROWID", rowid);
			list.add(row);
			rowid += 1;
		}

		rs.close();

		md = null;
		return list;
	}

	public static String getPrefix(String gsdm) {
		String prefix = "";
		if (gsdm.equals("L400")) {
			prefix = "DT";
		} else if (gsdm.equals("L300")) {
			prefix = "TX";
		} else if (gsdm.equals("L210")) {
			prefix = "LH";
		}
		return prefix;
	}

	public static String toSqlString(List<String> array) {
		String s = "";
		int i = 0;
		for (String a : array) {
			i = i + 1;
			if (i > 999) {
				break;
			}
			if (s.equals(""))
				s = "'" + a + "'";
			else
				s += ",'" + a + "'";
		}
		return s;
	}

	public static Integer toInteger(Object obj) {
		if (obj == null) {
			return 0;
		}
		try {
			return Integer.parseInt(obj.toString().trim());

		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static Double toDouble(Object obj) {
		return obj == null ? 0 : Double.parseDouble(obj.toString().trim());
	}
}
