package imes.dao;

import imes.core.Helper;
import imes.entity.QH_BDLC;
import imes.vo.D400VO;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.sql.CLOB;

public class D400Dao extends BaseDao {	
	
	public D400Dao(Connection con){
		super.setCon(con);
	}
	
	public D400VO getVO(String sql,String GSDM,String BDDM,String BDBH) {
		D400VO d = new D400VO();
		try {			
			pstm = con.prepareStatement(sql);
	
			rst = pstm.executeQuery();
			while(rst.next()){
				d.setGSDM(rst.getString("GSDM"));
				d.setBDDM(rst.getString("BDDM"));
				d.setBDBH(rst.getString("BDBH"));
				d.setSQYH(rst.getString("SQYH"));
				d.setBDRQ(rst.getString("BDRQ"));
				d.setQCLX(rst.getString("QCLX"));
				d.setBMMC(rst.getString("BMMC"));
				d.setWJSX(rst.getString("WJSX"));
				d.setWJJM(rst.getString("WJJM"));
				d.setQCAY(rst.getString("QCAY"));
				d.setQCNR(readClob(GSDM,BDDM,BDBH));
				d.setBDFD(rst.getString("BDFD"));
				d.setQHYH(rst.getString("QHYH"));
				d.setBDZT(rst.getString("BDZT"));
				d.setBDJG(rst.getString("BDJG"));
				d.setDYZT(rst.getString("DYZT"));
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
	
	public List<HashMap<String, Object>> getUserList(String sql) {
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
	
//	public int insertDb(D400VO d400VO) {
//		int i = 0;
//		String sql = "INSERT INTO D400H (GSDM,BDDM,BDBH,BDRQ,BMMC,SQYH,QCLX,WJSX,QCAY,QCNR,BDFD,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//		try {
//			pstm = con.prepareStatement(sql);
//			pstm.setString(1, d400VO.getGSDM());
//			pstm.setString(2, d400VO.getBDDM());
//			pstm.setString(3, d400VO.getBDBH());
//			pstm.setString(4, d400VO.getBDRQ());
//			pstm.setString(5, d400VO.getBMMC());
//			pstm.setString(6, d400VO.getSQYH());
//			pstm.setString(7, d400VO.getQCLX());
//			pstm.setString(8, d400VO.getWJSX());
//			pstm.setString(9, d400VO.getQCAY());
//			pstm.setString(10, d400VO.getQCNR());
//			pstm.setString(11, d400VO.getBDFD());
//			pstm.setString(12, d400VO.getQHYH());
//			pstm.setTimestamp(13, Helper.toSqlDate(d400VO.getQHSJ()));
//			pstm.setString(14, d400VO.getJLYH());
//			pstm.setTimestamp(15, Helper.toSqlDate(d400VO.getJLSJ()));
//			pstm.setString(16, d400VO.getGXYH());
//			pstm.setTimestamp(17, Helper.toSqlDate(d400VO.getGXSJ()));
//			i = pstm.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				pstm.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return i;
//	}
	
	public int updateDb(D400VO d400VO) {
		int i = 0;
		String sql = "UPDATE D400H SET BMMC=?,SQYH=?,QCLX=?,WJSX=?,QCAY=?,WJJM=? WHERE GSDM=? AND BDDM=? AND BDBH=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, d400VO.getBMMC());
			pstm.setString(2, d400VO.getSQYH());
			pstm.setString(3, d400VO.getQCLX());
			pstm.setString(4, d400VO.getWJSX());
			pstm.setString(5, d400VO.getQCAY());
			pstm.setString(6, d400VO.getWJJM());
			pstm.setString(7, d400VO.getGSDM());
			pstm.setString(8, d400VO.getBDDM());
			pstm.setString(9, d400VO.getBDBH());
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

	public D400VO find(String GSDM,String BDDM,String BDBH) {
		
		D400VO d400VO = new D400VO();

		String sql = "SELECT * FROM D400H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			rst = pstm.executeQuery();
			
			if (rst.next()) {
				d400VO.setGSDM(rst.getString("GSDM"));
				d400VO.setBDDM(rst.getString("BDDM"));
				d400VO.setBDBH(rst.getString("BDBH"));
				d400VO.setBDRQ(rst.getString("BDRQ"));
				d400VO.setBMMC(rst.getString("BMMC"));
				d400VO.setSQYH(rst.getString("SQYH"));
				d400VO.setQCLX(rst.getString("QCLX"));
				d400VO.setWJSX(rst.getString("WJSX"));
				d400VO.setWJJM(rst.getString("WJJM"));
				d400VO.setQCAY(rst.getString("QCAY"));
				d400VO.setQCNR(readClob(GSDM,BDDM,BDBH));
				d400VO.setBDFD(rst.getString("BDFD"));
				d400VO.setQHYH(rst.getString("QHYH"));
				d400VO.setQHYH(rst.getString("QHYH"));
				d400VO.setQHSJ(rst.getTimestamp("QHSJ"));
				d400VO.setJLYH(rst.getString("JLYH"));
				d400VO.setJLSJ(rst.getTimestamp("JLSJ"));
				d400VO.setGXYH(rst.getString("GXYH"));
				d400VO.setGXSJ(rst.getTimestamp("GXSJ"));
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d400VO;
	}

	public static String getBDBH(Connection con, String GSDM, String BDDM, String LX, String BDYR) {
		String sql, BDQZ = "";
		PreparedStatement pstm, upstm;
		ResultSet rst;
		int BDHM = 0;

		try {
			sql = "select bdqz,bdhm from qh_bdbh where gsdm=? and bddm=? and bdqz like ? and bdyr=? for update nowait";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, LX);
			pstm.setString(4, BDYR);

			sql = "update qh_bdbh set bdhm = ? where gsdm=? and bddm=? and bdqz like ? and bdyr=?";
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
						upstm.setString(4, LX);
						upstm.setString(5, BDYR);
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

	public List<String> findUserHtml() {
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT USERID,NAME FROM USERS ";
		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				String userid = rst.getString("USERID");
				String name = rst.getString("NAME");
				String html = "<option value=\""+userid+"\">"+name+"-"+userid+"</option>";				
				list.add(html);
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<String> findUserSpan() {
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT USERID,NAME FROM USERS ORDER BY USERID";
		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				String userid = rst.getString("USERID");			
				list.add(userid);
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<String> findUserZJL() {
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE SUBSTR(ZWDM,3,3) = 'ZJL'";
		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				String userid = rst.getString("YHDM");			
				list.add(userid);
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<String> findUserDiv() {
		
		List<String> list = new ArrayList<String>();

		String sql = "SELECT USERID,NAME FROM USERS ";
		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			while(rst.next()) {
				String userid = rst.getString("USERID");
				//String name = rst.getString("NAME");
				String html = "<input type=\"text\" id=\"SHR\" name=\"SHR\" value="+userid+">";				
				list.add(html);
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int insertQH_BDLC(QH_BDLC q) {
		int i = 0;
		String sql = "INSERT INTO QH_BDLC "
				+ "(GSDM,BDDM,BDBH,LXDM,QHZT,BZDM,LCLX,MINQ,MAXQ,MINA,MAXA,MINP,MAXP,CURR,FTXT,TTXT,ZWDM,QHLX,YXXS,ZFYN,JLSJ,YJSJ,YXSJ,YSYH,DLYH,QHYH,QHSJ,QHJG,QHNR,QHFD,SQYH) " //
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //
		PreparedStatement pstm = null;
		try{
			pstm = con.prepareStatement(sql);
			pstm.setString(1, q.getGSDM());
			pstm.setString(2, q.getBDDM());
			pstm.setString(3, q.getBDBH());
			pstm.setString(4, q.getLXDM());
			pstm.setString(5, q.getQHZT());
			pstm.setInt(6, q.getBZDM());
			pstm.setString(7, q.getLCLX());
			pstm.setDouble(8, q.getMINQ());
			pstm.setDouble(9, q.getMAXQ());
			pstm.setDouble(10, q.getMINA());
			pstm.setDouble(11, q.getMAXA());
			pstm.setDouble(12, q.getMINP());
			pstm.setDouble(13, q.getMAXP());
			pstm.setString(14, q.getCURR());
			pstm.setString(15, q.getFTXT());
			pstm.setString(16, q.getTTXT());
			pstm.setString(17, q.getZWDM());
			pstm.setString(18, q.getQHLX());
			pstm.setDouble(19, q.getYXXS());
			pstm.setString(20, q.getZFYN());
			pstm.setTimestamp(21, Helper.toSqlDate(q.getJLSJ()));
			pstm.setTimestamp(22, Helper.toSqlDate(q.getYJSJ()));
			pstm.setTimestamp(23, Helper.toSqlDate(q.getYXSJ()));
			pstm.setString(24, q.getYSYH());
			pstm.setString(25, q.getDLYH());
			pstm.setString(26, q.getQHYH());
			pstm.setTimestamp(27, Helper.toSqlDate(q.getQHSJ()));
			pstm.setString(28, q.getQHJG());
			pstm.setString(29, q.getQHNR());
			pstm.setString(30, q.getQHFD());
			pstm.setString(31, q.getSQYH());
			i = pstm.executeUpdate();
			pstm.close();
		}catch(Exception ex){System.out.println(ex.toString());}
		return i;
	}
	
	public boolean inserClob(D400VO d400VO) {  
        boolean suc = false;  
        CLOB clob = null;  
        String sql = "INSERT INTO D400H (GSDM,BDDM,BDBH,BDRQ,BMMC," +
        		"SQYH,QCLX,WJSX,QCAY,QCNR1,BDFD,QHYH,QHSJ,JLYH," +
        		"JLSJ,GXYH,GXSJ,WJJM) " +
        		"VALUES ('"+d400VO.getGSDM()+"','"+d400VO.getBDDM()+"'" +
        		",'"+d400VO.getBDBH()+"','"+d400VO.getBDRQ()+"'," +
        		"'"+d400VO.getBMMC()+"','"+d400VO.getSQYH()+"'," +
        		"'"+d400VO.getQCLX()+"','"+d400VO.getWJSX()+"'" +
        		",'"+d400VO.getQCAY()+"',empty_clob()" +
        		",'"+d400VO.getBDFD()+"','"+d400VO.getQHYH()+"'" +
        		","+Helper.toSqlDate(d400VO.getQHSJ())+",'"+d400VO.getJLYH()+"'" +
        		","+Helper.toSqlDate(d400VO.getJLSJ())+",'"+d400VO.getGXYH()+"'" +
        		","+Helper.toSqlDate(d400VO.getGXSJ())+",'"+d400VO.getWJJM()+"')";
        try {  
            con.setAutoCommit(false);  
            pstm = con.prepareStatement(sql);
            pstm.executeUpdate();  
            pstm.close();  
            
            sql = "SELECT QCNR1 FROM D400H " +
            	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
        		" AND BDBH='"+d400VO.getBDBH()+"' for update ";
            pstm = con.prepareStatement(sql);
            rst = pstm.executeQuery();  
            if(rst.next()){
                clob = (CLOB)rst.getClob(1);  
            }
            
            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream()); 

            pw.write(d400VO.getQCNR());

            pw.flush();  
            pstm.close();
              
            sql = "UPDATE D400H SET QCNR1=? " +
            	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    			" AND BDBH='"+d400VO.getBDBH()+"' ";  
            pstm = con.prepareStatement(sql); 
            pstm.setClob(1, clob);
            pstm.executeUpdate();  
            pstm.close();
            con.commit();  
            pw.close();  
            con.setAutoCommit(true);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  

        return suc;  
    }  
    /** 
     * 输出Clob对象 
     * @param  String GSDM,String BDDM,String BDBH
     */  
    public String readClob(String GSDM,String BDDM,String BDBH){  
        String test_clob = "";  
        CLOB clob = null;  
        StringBuffer sb = new StringBuffer();  
        String sql = "SELECT QCNR1 FROM D400H WHERE GSDM='"+GSDM+"' AND BDDM='"+BDDM+"' AND BDBH='"+BDBH+"'";  
        try {  
            pstm = con.prepareStatement(sql);  
            rst = pstm.executeQuery();  
            if(rst.next()){  
                clob = (CLOB)rst.getClob("QCNR1");
            }
            if(clob != null){
	            Reader reader = clob.getCharacterStream();  
	            char[] buffer = new char[1024];  
	            int length = 0;  
	            while((length = reader.read(buffer))!=-1){  
	                sb.append(buffer, 0, length);  
	            }
            }
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        test_clob = sb.toString();

        return test_clob;  
    }
    
	public boolean updateClob(D400VO d400VO) {  
        boolean suc = false;  
        CLOB clob = null;
  
        String sql = "SELECT * FROM D400H " +
        	" WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    		" AND BDBH='"+d400VO.getBDBH()+"' for update ";
        try {  
            con.setAutoCommit(false);
 
            pstm = con.prepareStatement(sql);
            rst = pstm.executeQuery();  
            if(rst.next()){
                clob = (CLOB)rst.getClob("QCNR1");  
            }
  
            PrintWriter pw = new PrintWriter(clob.getCharacterOutputStream());
            pw.write(d400VO.getQCNR());
  
            pw.flush();  
            pstm.close();
              
            sql = "UPDATE D400H SET QCNR1=? " +
            	  " WHERE GSDM='"+d400VO.getGSDM()+"' AND BDDM='"+d400VO.getBDDM()+"'" +
    			  " AND BDBH='"+d400VO.getBDBH()+"' ";  
            pstm = con.prepareStatement(sql); 
            pstm.setClob(1, clob);
            pstm.executeUpdate();  
            pstm.close();
            con.commit();  
            pw.close();  
            con.setAutoCommit(true);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  

        return suc;
    }

	public String findUserManager1(String user) {
		
		String yhdm = "";

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = '"+user+"' AND MANAGER IN (SELECT YHDM FROM QH_ZWYH WHERE SUBSTR(ZWDM,3,3) = 'ZJL' ) ";

		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				yhdm = rst.getString("MANAGER");
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yhdm;
	}

	public String findUserGLY(String bdh) {
		
		String yhdm = "";

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM = '"+bdh+"'";

		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				yhdm = rst.getString("YHDM");
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yhdm;
	}

	public boolean isUserManager2(String user) {
		
		boolean has = true;

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = " +
				"(SELECT MANAGER FROM USERS WHERE USERID = '"+user+"') "+ 
				" AND MANAGER IN (SELECT YHDM FROM QH_ZWYH WHERE SUBSTR(ZWDM,3,3) = 'ZJL' ) ";

		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				has = false;
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return has;
	}

	public String findUserManager2(String user) {
		
		String yhdm = "";

		String sql = "SELECT MANAGER FROM USERS WHERE USERID = (SELECT MANAGER FROM USERS WHERE USERID = '"+user+"')";

		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				yhdm = rst.getString("MANAGER");
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yhdm;
	}
	
	public String findPostUser(String zwdm) {
		
		String yhdm = "";

		String sql = "SELECT YHDM FROM QH_ZWYH WHERE ZWDM = '"+zwdm+"' ";

		try {
			pstm = con.prepareStatement(sql);

			rst = pstm.executeQuery();

			if(rst.next()) {
				yhdm = rst.getString("YHDM");
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yhdm;
	}
}
