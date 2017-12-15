package imes.dao;
//ZMM0004
import imes.core.Helper;
import imes.entity.D089H;
import imes.entity.D089L;
import imes.entity.D089Z;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class D089Dao extends BaseDao {

	public D089Dao(Connection con) {
		super.setCon(con);
	}

	public List<HashMap<String, Object>> getSapUpdateLog(HttpServletRequest req) {
		String sql = "SELECT h.lifnr, h.sortl, l.matnr, l.msgtx, to_char(l.saptm, 'yyyy/mm/dd HH24:MI:SS') saptm, " //
				+ "       l.maktx, h.bdyy, h.gsdm, h.bddm, h.bdbh, " //
				+ "       h.ekorg, h.werks, h.sqyh,h.mwskz, h.waers, l.netpr, " //
				+ "       l.peinh, l.datab, l.prdat, l.meins, l.plifz, " //
				+ "       l.bstrf, l.sqnr, l.id, l.infnr " //
				+ "  FROM d089h h, d089l l " //
				+ " WHERE     h.gsdm = l.gsdm " //
				+ "       AND h.bddm = l.bddm " //
				+ "       AND h.bdbh = l.bdbh " //
				+ "       AND h.bdjg = 'Y' " //
				+ "       AND h.sapcf = 'A' " //
				+ "       AND l.dltfg = 'N' " //
				+ "       AND h.bdyy <> 'E. Close Agrm' " //
				+ "       AND l.sapcf = 'A' " //
		;
		if (!getParam(req, "lifnr_low").equals("")) {
			sql += Helper.sqlParams(req, "h.lifnr", "lifnr_low", "lifnr_high");
		}
		if (!getParam(req, "sqyh_low").equals("")) {
			sql += Helper.sqlParams(req, "h.sqyh", "sqyh_low", "sqyh_high");
		}
		if (!getParam(req, "matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "l.matnr", "matnr_low", "matnr_high");
		}

		sql += " ORDER BY h.lifnr, l.matnr";
		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		try {
			pstm = con.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public void updateD089LAfterModifyPobyNewInfoRec(List<HashMap<String, Object>> listMap) {
		String sql = "UPDATE D089L SET RFCTM=SYSDATE, RFCCF='C' WHERE GSDM=? AND BDDM=? AND BDBH=? AND SQNR=?";
		try {

			for (HashMap<String, Object> map : listMap) {
				pstm = con.prepareStatement(sql);
				pstm.setString(1, map.get("GSDM").toString());
				pstm.setString(2, map.get("BDDM").toString());
				pstm.setString(3, map.get("BDBH").toString());
				pstm.setInt(4, Helper.toInteger(map.get("SQNR").toString()));
				pstm.executeUpdate();
				pstm.close();
			}

			sql = "UPDATE D089H " //
					+ "   SET RFCCF = 'C' " //
					+ " WHERE RFCCF = 'A' " //
					+ "       AND 0 = " //
					+ "              (SELECT COUNT (*) " //
					+ "                 FROM D089L " //
					+ "                WHERE     D089L.GSDM = D089H.GSDM " //
					+ "                      AND D089L.BDDM = D089H.BDDM " //
					+ "                      AND D089L.BDBH = D089H.BDBH " //
					+ "                      AND D089L.RFCCF = 'A') "; //
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			pstm.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void insertD089Z(D089Z e) {
		String sql = "INSERT INTO D089Z(GSDM,BDDM,BDBH,MSGID,MSGNR,MSGTY,MSGTX,JLSJ,MATNR) VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
			pstm.setString(4, e.getMSGID());
			pstm.setString(5, e.getMSGNR());
			pstm.setString(6, e.getMSGTY());
			pstm.setString(7, e.getMSGTX());
			pstm.setTimestamp(8, Helper.toSqlDate(e.getJLSJ()));
			pstm.setString(9, e.getMATNR());
			pstm.executeUpdate();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void insertD089H(D089H e) {

		String sql = "INSERT INTO D089H (id,RFCCF,MINPT,MAXPT,GSDM,BDDM,BDBH,BDRQ,BDZT,BDJG,BDFD,BDYY,BDNR,SQYH,QHYH,QHSJ,QHKS,JLYH,JLSJ,GXYH,GXSJ,MANDT,EKORG,WERKS,LIFNR,NAME1,SORTL,MWSKZ,WAERS,BDAMT,SAPCF,SAPRC,SAPEM,SNAMT,AGAMT,CUAMT,MTCUR) " //
				+ "VALUES (d089h_seq.NEXTVAL,'A',0,0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)  "; //
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
			pstm.setString(4, e.getBDRQ());
			pstm.setString(5, e.getBDZT());
			pstm.setString(6, e.getBDJG());
			pstm.setString(7, e.getBDFD());
			pstm.setString(8, e.getBDYY());
			pstm.setString(9, e.getBDNR());
			pstm.setString(10, e.getSQYH());
			pstm.setString(11, e.getQHYH());
			pstm.setTimestamp(12, Helper.toSqlDate(e.getQHSJ()));
			pstm.setString(13, e.getQHKS());
			pstm.setString(14, e.getJLYH());
			pstm.setTimestamp(15, Helper.toSqlDate(e.getJLSJ()));
			pstm.setString(16, e.getGXYH());
			pstm.setTimestamp(17, Helper.toSqlDate(e.getGXSJ()));
			pstm.setString(18, e.getMANDT());
			pstm.setString(19, e.getEKORG());
			pstm.setString(20, e.getWERKS());
			pstm.setString(21, e.getLIFNR());
			pstm.setString(22, e.getNAME1());
			pstm.setString(23, e.getSORTL());
			pstm.setString(24, e.getMWSKZ());
			pstm.setString(25, e.getWAERS());
			pstm.setDouble(26, e.getBDAMT());
			pstm.setString(27, e.getSAPCF());
			pstm.setString(28, e.getSAPRC());
			pstm.setString(29, e.getSAPEM());
			pstm.setDouble(30, e.getSNAMT());
			pstm.setDouble(31, e.getAGAMT());
			pstm.setDouble(32, e.getCUAMT());
			pstm.setString(33, e.getMTCUR());
			pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void updateD089H(D089H e) {

		String sql = "UPDATE D089H SET BDRQ=?,WERKS=?,EKORG=?,LIFNR=?,SORTL=?,MWSKZ=?,WAERS=?,NAME1=?,BDYY=?,BDNR=? ,SNAMT=?,AGAMT=?,CUAMT=?,MTCUR=?" //
				+ "WHERE GSDM=? AND BDDM=? AND BDBH=? "; //

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, e.getBDRQ());
			pstm.setString(2, e.getWERKS());
			pstm.setString(3, e.getEKORG());
			pstm.setString(4, e.getLIFNR());
			pstm.setString(5, e.getSORTL());
			pstm.setString(6, e.getMWSKZ());
			pstm.setString(7, e.getWAERS());
			pstm.setString(8, e.getNAME1());
			pstm.setString(9, e.getBDYY());
			pstm.setString(10, e.getBDNR());
			pstm.setDouble(11, e.getSNAMT());
			pstm.setDouble(12, e.getAGAMT());
			pstm.setDouble(13, e.getCUAMT());
			pstm.setString(14, e.getMTCUR());
			pstm.setString(15, e.getGSDM());
			pstm.setString(16, e.getBDDM());
			pstm.setString(17, e.getBDBH());
			pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void updateD089H_QHKS(String gsdm, String bddm, String bdbh) {
		String sql = "UPDATE D089H SET QHKS='X', GXSJ = SYSDATE, GXYH=SQYH, BDZT='Q' WHERE GSDM=? AND BDDM=? AND BDBH=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			pstm.executeUpdate();
			pstm.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateMinptMaxptD089H(String gsdm, String bddm, String bdbh) {
		String sql = "UPDATE D089H " //
				+ "   SET MINPT = " //
				+ "          (SELECT MIN (MINPT) " //
				+ "             FROM D089L " //
				+ "            WHERE GSDM = D089H.GSDM AND BDDM = D089H.BDDM AND BDBH = D089H.BDBH AND DLTFG<>'Y'), " //
				+ "       MAXPT = " //
				+ "          (SELECT MAX (MINPT) " //
				+ "             FROM D089L " //
				+ "            WHERE GSDM = D089H.GSDM AND BDDM = D089H.BDDM AND BDBH = D089H.BDBH AND DLTFG<>'Y') " //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? ";//
		try {

			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			pstm.executeUpdate();
			pstm.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public D089H find(String gsdm, String bddm, String bdbh) {

		D089H e = new D089H();

		String sql = "SELECT * FROM D089H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			rst = pstm.executeQuery();
			if (rst.next()) {
				e.setGSDM(rst.getString("GSDM"));
				e.setBDDM(rst.getString("BDDM"));
				e.setBDBH(rst.getString("BDBH"));
				e.setBDRQ(rst.getString("BDRQ"));
				e.setBDZT(rst.getString("BDZT"));
				e.setBDJG(rst.getString("BDJG"));
				e.setBDFD(rst.getString("BDFD"));
				e.setBDYY(rst.getString("BDYY"));
				e.setBDNR(rst.getString("BDNR"));
				e.setSQYH(rst.getString("SQYH"));
				e.setQHYH(rst.getString("QHYH"));
				e.setQHSJ(rst.getTimestamp("QHSJ"));
				e.setQHKS(rst.getString("QHKS"));
				e.setJLYH(rst.getString("JLYH"));
				e.setJLSJ(rst.getTimestamp("JLSJ"));
				e.setGXYH(rst.getString("GXYH"));
				e.setGXSJ(rst.getTimestamp("GXSJ"));
				e.setMANDT(rst.getString("MANDT"));
				e.setEKORG(rst.getString("EKORG"));
				e.setWERKS(rst.getString("WERKS"));
				e.setLIFNR(rst.getString("LIFNR"));
				e.setNAME1(rst.getString("NAME1"));
				e.setSORTL(rst.getString("SORTL"));
				e.setMWSKZ(rst.getString("MWSKZ"));
				e.setWAERS(rst.getString("WAERS"));
				e.setBDAMT(rst.getDouble("BDAMT"));
				e.setSAPCF(rst.getString("SAPCF"));
				e.setSAPRC(rst.getString("SAPRC"));
				e.setSAPEM(rst.getString("SAPEM"));
				e.setMINPT(rst.getDouble("MINPT"));
				e.setMAXPT(rst.getDouble("MAXPT"));
				e.setSNAMT(rst.getDouble("SNAMT"));
				e.setAGAMT(rst.getDouble("AGAMT"));
				e.setCUAMT(rst.getDouble("CUAMT"));
				e.setMTCUR(rst.getString("MTCUR"));
			}
			rst.close();
			pstm.close();

		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		return e;
	}

	public int insertD089L(D089L e) {
		int i = 0;

		String sql = "INSERT INTO D089L (id,DLTFG,RFCCF,GSDM,BDDM,BDBH,SQNR,INFNR,MATNR,MATKL,MAKTX,MEINS,EKGRP,DATAB,PRDAT,UNTPR,NETPR,PEINH,RMBPR,OLDPR,MINPR,OLDDF,OLDPT,MINDF,MINPT,PLIFZ,BSTRF,CHGPO,DATYP,PODAT,SAPCF,SAPRC,SAPEM,DLVDT) " //
				+ "VALUES (d089l_seq.NEXTVAL,'N','A',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
			pstm.setInt(4, e.getSQNR());
			pstm.setString(5, e.getINFNR());
			pstm.setString(6, e.getMATNR());
			pstm.setString(7, e.getMATKL());
			pstm.setString(8, e.getMAKTX());
			pstm.setString(9, e.getMEINS());
			pstm.setString(10, e.getEKGRP());
			pstm.setString(11, e.getDATAB());
			pstm.setString(12, e.getPRDAT());
			pstm.setDouble(13, e.getUNTPR());
			pstm.setDouble(14, e.getNETPR());
			pstm.setInt(15, e.getPEINH());
			pstm.setDouble(16, e.getRMBPR());
			pstm.setDouble(17, e.getOLDPR());
			pstm.setDouble(18, e.getMINPR());
			pstm.setDouble(19, e.getOLDDF());
			pstm.setDouble(20, e.getOLDPT());
			pstm.setDouble(21, e.getMINDF());
			pstm.setDouble(22, e.getMINPT());
			pstm.setInt(23, e.getPLIFZ());
			pstm.setInt(24, e.getBSTRF());
			pstm.setString(25, e.getCHGPO());
			pstm.setString(26, e.getDATYP());
			pstm.setString(27, e.getPODAT());
			pstm.setString(28, e.getSAPCF());
			pstm.setString(29, e.getSAPRC());
			pstm.setString(30, e.getSAPEM());
			pstm.setString(31, e.getDLVDT());
			
			i = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException ex) {

			ex.printStackTrace();
		}

		return i;
	}

	public int updateD089L(D089L e) {
		int i = 0;

		String sql = "UPDATE D089L SET DATAB=?, PRDAT=?, CHGPO=?, DLVDT=?, PODAT=? WHERE GSDM=? AND BDDM=? AND BDBH=? AND SQNR=?";

		try {

			pstm = con.prepareStatement(sql);
			pstm.setString(1, e.getDATAB());
			pstm.setString(2, e.getPRDAT());
			pstm.setString(3, e.getCHGPO());
			pstm.setString(4, e.getDLVDT());
			pstm.setString(5, e.getPODAT());
			pstm.setString(6, e.getGSDM());
			pstm.setString(7, e.getBDDM());
			pstm.setString(8, e.getBDBH());
			pstm.setInt(9, e.getSQNR());
			i = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException ex) {

			ex.printStackTrace();
		}
		return i;
	}

	public int updateDeleteMark(String rid, String uid, String dlttx, String gsdm, String bddm, String bdbh) {
		int i = 0;

		String sql = "UPDATE D089L SET DLTFG='Y', DLTID=?, DLTTX=?, DLTDT=SYSDATE, RFCCF='C', SAPCF='C' WHERE ROWID=CHARTOROWID(?)";

		try {

			pstm = con.prepareStatement(sql);
			pstm.setString(1, uid);
			pstm.setString(2, dlttx);
			pstm.setString(3, rid);
			i = pstm.executeUpdate();
			pstm.close();

			updateMinptMaxptD089H(gsdm, bddm, bdbh);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return i;
	}

	public int deleteAllD089L(String gsdm, String bddm, String bdbh) {
		int i = 0;

		String sql = "DELETE FROM D089L WHERE GSDM=? AND BDDM=? AND BDBH=?";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			i = pstm.executeUpdate();
			pstm.close();

			updateMinptMaxptD089H(gsdm, bddm, bdbh);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return i;
	}

	public int deleteD089L(String gsdm, String bddm, String bdbh, int sqnr) {
		int i = 0;

		String sql = "DELETE FROM D089L WHERE GSDM=? AND BDDM=? AND BDBH=? AND SQNR=?";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			pstm.setInt(4, sqnr);
			i = pstm.executeUpdate();
			pstm.close();

			updateMinptMaxptD089H(gsdm, bddm, bdbh);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return i;
	}

	public List<HashMap<String, Object>> getD089Ls(String gsdm, String bddm, String bdbh) {

		List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT ROWIDTOCHAR(D089L.ROWID) RID,D089L.* FROM D089L WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY DLTFG, MINPT DESC";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			rows = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		double minpt = 0;
		String bgcolor = "";
		String color = "";
		for (HashMap<String, Object> h : rows) {

			minpt = Double.parseDouble(h.get("MINPT").toString());

			color = "black";
			if (minpt > 0) {
				if (minpt <= 3) {
					bgcolor = "#FBEFEF";
				} else if (minpt <= 10) {
					bgcolor = "#F8E0E0";
				} else {
					bgcolor = "#FF0000";
					color = "white";
				}
			} else if (minpt < 0) {
				if (minpt >= -3) {
					bgcolor = "#D8F6CE";
				} else if (minpt >= -10) {
					bgcolor = "#BCF5A9";
				} else {
					bgcolor = "#9FF781";
				}
			} else {
				bgcolor = "#FFFFFF";
			}

			h.put("BGCOLOR", bgcolor);
			h.put("COLOR", color);
		}
		return rows;
	}

	public String getD089LMatkl(String gsdm, String bddm, String bdbh) {
		String matkl = "";
		String sql = "SELECT MATKL FROM D089L WHERE GSDM=? AND BDDM=? AND BDBH=? AND ROWNUM = 1";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			rst = pstm.executeQuery();
			if (rst.next()) {
				matkl = rst.getString("MATKL");
			}
			rst.close();
			pstm.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return matkl;
	}

	public int getD089LMaxSeq(String gsdm, String bddm, String bdbh) {
		int seq = 0;

		String sql = "SELECT MAX(SQNR) SQNR FROM D089L WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			rst = pstm.executeQuery();
			if (rst.next()) {
				seq = rst.getInt("SQNR");
			}
			rst.close();
			pstm.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return seq;
	}

	public void getMatInfo(HashMap<String, D089L> map, String werks, String lifnr) {

		int n = 0;
		String s = "";
		List<String> list = new ArrayList<String>();

		for (String matnr : map.keySet()) {
			n += 1;
			if (n == 1) {
				s = "'" + matnr + "'";
			} else {
				s += ",'" + matnr + "'";
			}

			if (n > 999) {
				list.add(s);
				n = 0;
				s = "";
			}
		} // for (String matnr : map.keySet()) {

		if (n != 0) {
			list.add(s);
		}

		String sql; //
		String gsdm = "", bddm = "", bdbh = "";
		try {

			D089L d089l;

			for (String matnr : list) {
				sql = "SELECT MARA.MATNR, MARA.MATKL, MAKT.MAKTX,MARA.ZEINR, MARA.ZEIVR, MARC.PLIFZ, " //
						+ "    MARC.EKGRP, MARC.BSTRF,MARA.MEINS,MBEW.PEINH " //
						+ "  FROM SAPSR3.MARA@SAPP, SAPSR3.MAKT@SAPP, SAPSR3.MARC@SAPP, SAPSR3.MBEW@SAPP " //
						+ " WHERE     MARA.MANDT = '168' " //
						+ "       AND MARA.MATNR IN (" + matnr + ") " //
						+ "       AND MAKT.MANDT = MARA.MANDT " //
						+ "       AND MAKT.MATNR = MARA.MATNR " //
						+ "       AND MAKT.SPRAS = 'M' " //
						+ "       AND MARC.MANDT(+) = MARA.MANDT " //
						+ "       AND MARC.MATNR(+) = MARA.MATNR " //
						+ "       AND MARC.WERKS(+) = ? " //
						+ "       AND MBEW.MANDT(+) = MARA.MANDT " //
						+ "       AND MBEW.MATNR(+) = MARA.MATNR " //
						+ "       AND MBEW.BWKEY(+) = ? " //
						+ "       AND MBEW.BWTAR(+) = ' ' "; //

				pstm = con.prepareStatement(sql);
				pstm.setString(1, werks);
				pstm.setString(2, werks);
				rst = pstm.executeQuery();
				while (rst.next()) {
					d089l = map.get(rst.getString("MATNR"));
					d089l.setMAKTX(rst.getString("MAKTX"));
					d089l.setMATKL(rst.getString("MATKL"));
					d089l.setMEINS(rst.getString("MEINS"));
					d089l.setEKGRP(rst.getString("EKGRP"));
					d089l.setPLIFZ(rst.getInt("PLIFZ"));
					d089l.setBSTRF(rst.getInt("BSTRF"));
					d089l.setPEINH(rst.getInt("PEINH"));

					if (d089l.getPEINH() == 0) {
						if (d089l.getMATKL().startsWith("19")) {
							d089l.setPEINH(10000);
						} else {
							d089l.setPEINH(1000);
						}
					}
				}
				rst.close();
				pstm.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		try {
			D089L d089l;

			String priceType;

			for (String matnr : list) {
				sql = "  SELECT MINMAX.MATNR, MINMAX.TXRPIR, MINMAX.TXRP, MINMAX.DTRPIR, MINMAX.DTRP, " //
						+ "         MINMAX.TXFPIR, MINMAX.TXFP, MINMAX.DTFPIR, MINMAX.DTFP, MINMAX.LEIRPIR, " //
						+ "         MINMAX.LEIRP, MINMAX.LEIFPIR, MINMAX.LEIFP, INFRCD.UPF, INFRCD.UPR, INFRCD.INFNR," //
						+ "         INFRCD.DATAB " //
						+ "    FROM IT.WPIRS@ORACLETW MINMAX, " //
						+ "         IT.WPURINFORCD@ORACLETW INFRCD " //
						+ "   WHERE     MINMAX.MATNR IN (" + matnr + ") " //
						+ "         AND INFRCD.MATNR(+) = MINMAX.MATNR " //
						+ "         AND TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN INFRCD.DATAB(+) AND INFRCD.DATBI(+) " //
						+ "         AND INFRCD.LIFNR(+) = ? " //
						+ "ORDER BY INFRCD.DATAB";//
				pstm = con.prepareStatement(sql);
				pstm.setString(1, lifnr);
				rst = pstm.executeQuery();
				while (rst.next()) {
					d089l = map.get(rst.getString("MATNR"));

					if (d089l.getGSDM().contains("L400")) {
						priceType = "DT";
					} else if (d089l.getGSDM().contains("L111")) {
						priceType = "PH";
					} else {
						priceType = "TX";
					}

					if (d089l.getWAERS().contains("RMB")) {
						priceType += "RP";
					} else {
						priceType += "FP";
					}
					
					if (d089l.getGSDM().contains("L111")) {
						priceType = "PHP";
					}

					d089l.setINFNR(rst.getString("INFNR"));
					d089l.setOLDPR(rst.getDouble("UPR"));
					d089l.setMINPR(rst.getDouble(priceType));

					gsdm = d089l.getGSDM();
					bddm = d089l.getBDDM();
					bdbh = d089l.getBDBH();
				}
				rst.close();
				pstm.close();

				sql = "DELETE FROM D089L WHERE GSDM=? AND BDDM=? AND BDBH=? AND MATNR IN (" + matnr + ") "; //
				pstm = con.prepareStatement(sql);
				pstm.setString(1, gsdm);
				pstm.setString(2, bddm);
				pstm.setString(3, bdbh);
				pstm.executeUpdate();
				pstm.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<HashMap<String, Object>> getCurrencyRates() {

		List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT KONWA, RMBRATE FROM IT.WPURINFORCD@ORACLETW GROUP BY KONWA, RMBRATE";

		try {
			pstm = con.prepareStatement(sql);
			rows = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return rows;
	}
}
