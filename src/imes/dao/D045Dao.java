package imes.dao;

import imes.core.Helper;
import imes.vo.D045LVO;
import imes.vo.D045VO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class D045Dao extends BaseDao {
	
	
	public D045Dao(Connection con){
		super.setCon(con);
	}
	
	public D045VO getVO(String sql) {
		D045VO d = new D045VO();
		try {
			
			pstm = con.prepareStatement(sql);
	
			rst = pstm.executeQuery();
			while(rst.next()){				
				d.setGSDM(rst.getString("GSDM"));
				d.setBDDM(rst.getString("BDDM"));
				d.setBDBH(rst.getString("BDBH"));
				d.setBDRQ(rst.getString("BDRQ"));
				d.setBDZT(rst.getString("BDZT"));
				d.setBDJG(rst.getString("BDJG"));
				d.setBDLX(rst.getString("BDLX"));
				d.setWERKS(rst.getString("WERKS"));
				d.setCUST(rst.getString("CUST"));
				d.setKOSTL(rst.getString("KOSTL"));
				d.setKTEXT(rst.getString("KTEXT"));
				d.setDELADD(rst.getString("DELADD"));
				d.setBDAMT(rst.getDouble("BDAMT"));
				d.setINVNO(rst.getString("INVNO"));
				d.setCONO(rst.getString("CONO"));
				d.setSQYH(rst.getString("SQYH"));
				d.setQHYH(rst.getString("QHYH"));
				d.setQHSJ(rst.getDate("QHSJ"));
				d.setJLYH(rst.getString("JLYH"));
				d.setJLSJ(rst.getDate("JLSJ"));
				d.setGXYH(rst.getString("GXYH"));
				d.setGXSJ(rst.getDate("GXSJ"));
				d.setBDFD(rst.getString("BDFD"));
				d.setRSNUM(rst.getString("RSNUM"));
				d.setSAPUPDSTS(rst.getString("SAPUPDSTS"));
				d.setSAPUPDUSR(rst.getString("SAPUPDUSR"));
				d.setSAPUPDDAT(rst.getDate("SAPUPDDAT"));
				d.setSAPUPDMSG(rst.getString("SAPUPDMSG"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d;		
	}

	public List<HashMap<String, Object>> getList(String sql) {
		List<HashMap<String, Object>> list = null;
		try {			
			pstm = con.prepareStatement(sql);
	
			list = Helper.resultSetToArrayList(pstm.executeQuery());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;		
	}
	
	public List<HashMap<String, Object>> insertDb(String sql) {
		List<HashMap<String, Object>> list = null;
		try {			
			pstm = con.prepareStatement(sql);
	
			list = Helper.resultSetToArrayList(pstm.executeQuery());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;		
	}
	
	public int insertHeadDb(D045VO d045VO) {
		int i = 0;
		String sql = "INSERT INTO D045H (GSDM，BDDM，BDBH，BDRQ，BDZT，BDJG，BDLX，WERKS，CUST，KOSTL，KTEXT，DELADD，BDAMT，INVNO，CONO，SQYH，QHYH，QHSJ，JLYH，JLSJ，GXYH，GXSJ，BDFD，RSNUM, SAPUPDSTS, SAPUPDUSR, SAPUPDDAT, SAPUPDMSG) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, d045VO.getGSDM());
			pstm.setString(2, d045VO.getBDDM());
			pstm.setString(3, d045VO.getBDBH());
			pstm.setString(4, d045VO.getBDRQ());
			pstm.setString(5, d045VO.getBDZT());
			pstm.setString(6, d045VO.getBDJG());
			pstm.setString(7, d045VO.getBDLX());
			pstm.setString(8, d045VO.getWERKS());
			pstm.setString(9, d045VO.getCUST());
			pstm.setString(10, d045VO.getKOSTL());
			pstm.setString(11, d045VO.getKTEXT());
			pstm.setString(12, d045VO.getDELADD());
			pstm.setDouble(13, d045VO.getBDAMT());
			pstm.setString(14, d045VO.getINVNO());
			pstm.setString(15, d045VO.getCONO());
			pstm.setString(16, d045VO.getSQYH());
			pstm.setString(17, d045VO.getQHYH());
			pstm.setTimestamp(18, Helper.toSqlDate(d045VO.getQHSJ()));
			pstm.setString(19, d045VO.getJLYH());
			pstm.setTimestamp(20, Helper.toSqlDate(d045VO.getJLSJ()));
			pstm.setString(21, d045VO.getGXYH());
			pstm.setTimestamp(22, Helper.toSqlDate(d045VO.getGXSJ()));
			pstm.setString(23, d045VO.getBDFD());
			pstm.setString(24, d045VO.getRSNUM());
			pstm.setString(25, d045VO.getSAPUPDSTS());
			pstm.setString(26, d045VO.getSAPUPDUSR());
			pstm.setTimestamp(27, Helper.toSqlDate(d045VO.getSAPUPDDAT()));
			pstm.setString(28, d045VO.getSAPUPDMSG());
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	public int updateHeadDb(D045VO d045VO) {
		int i = 0;
		String sql = "UPDATE D045H SET BDLX=?，WERKS=?，CUST=?，KOSTL=?，KTEXT=?，DELADD=?，BDAMT=?，INVNO=?，CONO=? WHERE GSDM=? AND BDDM=? AND BDBH=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, d045VO.getBDLX());
			pstm.setString(2, d045VO.getWERKS());
			pstm.setString(3, d045VO.getCUST());
			pstm.setString(4, d045VO.getKOSTL());
			pstm.setString(5, d045VO.getKTEXT());
			pstm.setString(6, d045VO.getDELADD());
			pstm.setDouble(7, d045VO.getBDAMT());
			pstm.setString(8, d045VO.getINVNO());
			pstm.setString(9, d045VO.getCONO());
			pstm.setString(10, d045VO.getGSDM());
			pstm.setString(11, d045VO.getBDDM());
			pstm.setString(12, d045VO.getBDBH());
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	public D045VO find(String GSDM,String BDDM,String BDBH) {
		
		D045VO d045VO = new D045VO();

		String sql = "SELECT * FROM D045H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			rst = pstm.executeQuery();
			

			if (rst.next()) {
//				d045VO.setGSDM(rst.getString("GSDM"));
//				d045VO.setBDDM(rst.getString("BDDM"));
//				d045VO.setBDBH(rst.getString("BDBH"));
//				d045VO.setBDRQ(rst.getString("BDRQ"));
//				d045VO.setBMMC(rst.getString("BMMC"));
//				d045VO.setSQYH(rst.getString("SQYH"));
//				d045VO.setQCLX(rst.getString("QCLX"));
//				d045VO.setWJSX(rst.getString("WJSX"));
//				d045VO.setQCAY(rst.getString("QCAY"));
//				d045VO.setQCNR(rst.getString("QCNR"));
//				d045VO.setBDFD(rst.getString("BDFD"));
//				d045VO.setQHYH(rst.getString("QHYH"));
//				d045VO.setQHYH(rst.getString("QHYH"));
//				d045VO.setQHSJ(rst.getTimestamp("QHSJ"));
//				d045VO.setJLYH(rst.getString("JLYH"));
//				d045VO.setJLSJ(rst.getTimestamp("JLSJ"));
//				d045VO.setGXYH(rst.getString("GXYH"));
//				d045VO.setGXSJ(rst.getTimestamp("GXSJ"));
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d045VO;
	}

	public static String getBDBH(Connection con, String GSDM, String BDDM, String BDYR) {
		String sql, BDQZ = "";
		PreparedStatement pstm, upstm;
		ResultSet rst;
		int BDHM = 0;

		try {
			sql = "select bdqz,bdhm from qh_bdbh where gsdm=? and bddm=? and bdyr=? for update nowait";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDYR);

			sql = "update qh_bdbh set bdhm = ? where gsdm=? and bddm=? and bdyr=?";
			upstm = con.prepareStatement(sql);

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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return BDQZ + Integer.toString(BDHM);
	}
	
	public int insertLineDb(D045LVO d045LVO) {
		int i = 0;
		String sql = "INSERT INTO D045L(GSDM, BDDM, BDBH, SQNR, CMATNR, CWERKS, CMATKL, CMAKTX, CCUM, CCQTY, CPRICE, CCURR, CWEIGHT, JLYH, JLSJ, GXYH, GXSJ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,d045LVO.getGSDM());
			pstm.setString(2,d045LVO.getBDDM());
			pstm.setString(3,d045LVO.getBDBH());
			pstm.setInt(4,d045LVO.getSQNR());
			pstm.setString(5,d045LVO.getCMATNR());
			pstm.setString(6,d045LVO.getCWERKS());
			pstm.setString(7,d045LVO.getCMATKL());
			pstm.setString(8,d045LVO.getCMAKTX());
			pstm.setString(9,d045LVO.getCCUM());
			pstm.setDouble(10,d045LVO.getCCQTY());
			pstm.setDouble(11,d045LVO.getCPRICE());
			pstm.setString(12,d045LVO.getCCURR());
			pstm.setDouble(13,d045LVO.getCWEIGHT());
			pstm.setString(14,d045LVO.getJLYH());
			pstm.setTimestamp(15,Helper.toSqlDate(d045LVO.getJLSJ()));
			pstm.setString(16,d045LVO.getGXYH());
			pstm.setTimestamp(17,Helper.toSqlDate(d045LVO.getGXSJ()));
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	public int delLineDb(String GSDM,String BDDM,String BDBH) {
		int i = 0;
		String sql = "DELETE D045L WHERE GSDM = ? AND BDDM = ?, AND BDBH = ?";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,GSDM);
			pstm.setString(2,BDDM);
			pstm.setString(3,BDBH);
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	public int findLineDB(String GSDM,String BDDM,String BDBH){
		int i = 0;

		String sql = "SELECT * FROM D045L WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			rst = pstm.executeQuery();
			if (rst.next()) {
				i = 1;
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
