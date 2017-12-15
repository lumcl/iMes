package imes.dao;

import imes.core.AppConst;
import imes.core.Helper;
import imes.core.QianHe;
import imes.entity.Q001H;
import imes.entity.Q001L;
import imes.entity.Q001P;
import imes.entity.QH_BDLC;
import imes.entity.QH_BDTX;
import imes.entity.QH_BZLC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Q001Dao extends BaseDao {

	public Q001Dao(Connection con) {
		super.setCon(con);
	}
	
	public void copyVisitor(int q001h_id, int q001h_newId,String bdbh, String uid) throws IllegalArgumentException, IllegalAccessException{
		String sql = "SELECT * from q001l where q001h_id = ?";
		try {
			Q001L e;
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, q001h_id);
			rst = pstm.executeQuery();
			while (rst.next()) {
				e = new Q001L();
				e.setQ001h_id(q001h_newId);
				e.setBdbh(bdbh);
				e.setSqnr(rst.getInt("SQNR"));
				e.setLfxm(rst.getString("LFXM"));
				e.setSex(rst.getString("SEX"));
				e.setZjlx(rst.getString("ZJLX"));
				e.setZjhm(rst.getString("ZJHM"));
				e.setLfdw(rst.getString("LFDW"));
				e.setLfzw(rst.getString("LFZW"));
				e.setLfyj(rst.getString("LFYJ"));
				e.setAddr(rst.getString("ADDR"));
				e.setCphm(rst.getString("CPHM"));
				e.setHghm(rst.getString("HGHM"));
				e.setLfbz(rst.getString("LFBZ"));
				e.setJlsj(new Date());
				e.setGxsj(new Date());
				e.setJlyh(uid);
				e.setGxyh(uid);
				insert(e);
			}
			rst.close();
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<HashMap<String, Object>> visitors(int q001h_id) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT * from q001l where q001h_id = ?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, q001h_id);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();

			for (HashMap<String, Object> e : list) {
				if (e.get("SEX") != null) {
					e.put("SEX", AppConst.getSex(e.get("SEX").toString()));
				}

				if (e.get("SDRQ") == null || e.get("SDRQ").toString().trim().equals("")) {
					e.put("JspSdsj", "<a href='/iMes/Q001/arriveVisitor?id=" + e.get("ID").toString() + "&q001h_id=" + e.get("Q001H_ID").toString() + "'>到達確認</a>");
				} else {
					e.put("JspSdsj", e.get("SDRQ") + " " + e.get("SDSJ"));
				}

				if (e.get("SLRQ") == null || e.get("SLRQ").toString().trim().equals("")) {
					e.put("JspSlsj", "<a href='/iMes/Q001/leaveVisitor?id=" + e.get("ID").toString() + "&q001h_id=" + e.get("Q001H_ID").toString() + "'>離開確認</a>");
				} else {
					e.put("JspSlsj", e.get("SLRQ") + " " + e.get("SLSJ"));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<HashMap<String, Object>> searchHeader(HttpServletRequest req) {
		String sql;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		if ((getParam(req, "gsdm_low") + getParam(req, "ydrq_low") + getParam(req, "bdbh_low") + getParam(req, "sqyh_low")).equals("")) {
			sql = "SELECT id,gsdm,bdbh,bdrq,bdzt,bdjg,bdyy,sqyh,qhks,lflb,lfdw,lfdd,ydrq,ydsj,ylrq,ylsj,lfmd,u.name as sqyh_name FROM q001h left join users u on Q001H.SQYH = u.userid WHERE  (bdjg <> 'X' or bdjg is null) and ydrq >= TO_CHAR(SYSDATE, 'yyyymmdd') AND ROWNUM <= 200";
		} else {
			sql = "SELECT id,gsdm,bdbh,bdrq,bdzt,bdjg,bdyy,sqyh,qhks,lflb,lfdw,lfdd,ydrq,ydsj,ylrq,ylsj,lfmd,u.name as sqyh_name FROM q001h left join users u on Q001H.SQYH = u.userid WHERE 1 = 1";
			if (!getParam(req, "ydrq_low").equals("")) {
				sql += Helper.sqlParams(req, "ydrq", "ydrq_low", "ydrq_high");
			}else{
				Calendar oldCal = Calendar.getInstance();
				oldCal.add(Calendar.DAY_OF_MONTH, 6);
				sql += " AND (ydrq >= '" + Helper.fmtDate(new Date(), "yyyyMMdd") + "' AND ydrq <= '" + Helper.fmtDate(oldCal.getTime(), "yyyyMMdd") + "')";
			}
			if (!getParam(req, "bdbh_low").equals("")) {
				sql += Helper.sqlParams(req, "bdbh", "bdbh_low", "bdbh_high");
			}
			if (!getParam(req, "sqyh_low").equals("")) {
				sql += Helper.sqlParams(req, "sqyh", "sqyh_low", "sqyh_high");
			}
			if (!getParam(req, "gsdm_low").equals("")) {
				sql += Helper.sqlParams(req, "gsdm", "gsdm_low", "gsdm_high");
			}
		}

		sql += " order by ydrq, ydsj";

		try {
			System.out.println(" sql :"+sql);
			pstm = con.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());

			for (HashMap<String, Object> e : list) {
				e.put("BDZT", AppConst.getBdztText((e.get("BDZT") == null) ? "" : e.get("BDZT").toString()));
				e.put("BDJG", AppConst.getBdjgText((e.get("BDJG") == null) ? "" : e.get("BDJG").toString()));
				
				String [] lfdd = e.get("LFDD").toString().split(",");
				String lfdd_name ="";
				for(int i=0 ;i<lfdd.length;i++)
				{
					String user_query_sql = "";
					try{
						user_query_sql = "SELECT NAME FROM USERS WHERE USERID = '"+lfdd[i].toString()+"' ";
						pstm = con.prepareStatement(user_query_sql);
						List<HashMap<String, Object>> luser = Helper.resultSetToArrayList(pstm.executeQuery());
						if(luser.size()<1)
						{
							lfdd_name += lfdd[i]+",";
						}
						for(HashMap<String, Object> euser : luser)
						{
							lfdd_name += euser.get("NAME")+",";
						}
					}catch(Exception ex){
						System.out.println("user_query_sql: "+user_query_sql);
					}
				}
				e.put("LFDD_NAME", lfdd_name);
			}

			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void insert(Q001H e) {
		String sql = "INSERT INTO Q001H (GSDM,BDDM,BDBH,BDRQ,BDZT,BDJG,BDFD,BDYY,SQYH,QHYH,QHSJ,QHKS,HQYH,JLYH,JLSJ,GXYH,GXSJ,LFLB,LFDW,LFDD,YDRQ,YDSJ,YLRQ,YLSJ,LFMD,PBRY,GJZG,HQRQ,HQSJ,BEZU,JCFS,JSDD,BFRS,JCDD,JCBZ,JCSJ,JCRY,FYGS,ZSAP,ZSTS,HYZY,XLFS,QTZY,ZWBZ,SDRQ,SDSJ,SLRQ,SLSJ,ID) " //
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,q001h_seq.NEXTVAL ) ";

		try {

			pstm = con.prepareStatement(sql, new String[] { "id" });
			pstm.setString(1, e.getGsdm());
			pstm.setString(2, e.getBddm());
			pstm.setString(3, e.getBdbh());
			pstm.setString(4, e.getBdrq());
			pstm.setString(5, e.getBdzt());
			pstm.setString(6, e.getBdjg());
			pstm.setString(7, e.getBdfd());
			pstm.setString(8, e.getBdyy());
			pstm.setString(9, e.getSqyh());
			pstm.setString(10, e.getQhyh());
			pstm.setTimestamp(11, Helper.toSqlDate(e.getQhsj()));
			pstm.setString(12, e.getQhks());
			pstm.setString(13, e.getHqyh());
			pstm.setString(14, e.getJlyh());
			pstm.setTimestamp(15, Helper.toSqlDate(new Date()));
			pstm.setString(16, e.getGxyh());
			pstm.setTimestamp(17, Helper.toSqlDate(new Date()));
			pstm.setString(18, e.getLflb());
			pstm.setString(19, e.getLfdw());
			pstm.setString(20, e.getLfdd());
			pstm.setString(21, e.getYdrq());
			pstm.setString(22, e.getYdsj());
			pstm.setString(23, e.getYlrq());
			pstm.setString(24, e.getYlsj());
			pstm.setString(25, e.getLfmd());
			pstm.setString(26, e.getPbry());
			pstm.setString(27, e.getGjzg());
			pstm.setString(28, e.getHqrq());
			pstm.setString(29, e.getHqsj());
			pstm.setString(30, e.getBezu());
			pstm.setString(31, e.getJcfs());
			pstm.setString(32, e.getJsdd());
			pstm.setInt(33, e.getBfrs());
			pstm.setString(34, e.getJcdd());
			pstm.setDouble(35, e.getJcbz());
			pstm.setString(36, e.getJcsj());
			pstm.setString(37, e.getJcry());
			pstm.setString(38, e.getFygs());
			pstm.setString(39, e.getZsap());
			pstm.setInt(40, e.getZsts());
			pstm.setString(41, e.getHyzy());
			pstm.setInt(42, e.getXlfs());
			pstm.setString(43, e.getQtzy());
			pstm.setString(44, e.getZwbz());
			pstm.setString(45, e.getSdrq());
			pstm.setString(46, e.getSdsj());
			pstm.setString(47, e.getSlrq());
			pstm.setString(48, e.getSlsj());
			pstm.executeUpdate();

			rst = pstm.getGeneratedKeys();
			if (rst.next()) {
				e.setId(rst.getInt(1));
			}
			rst.close();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void update(Q001H e) {
		String sql = "UPDATE q001h " //
				+ "   SET gsdm = ?, bddm = ?, bdbh = ?, bdrq = ?, bdzt = ?, " //
				+ "       bdjg = ?, bdfd = ?, bdyy = ?, sqyh = ?, qhyh = ?, " //
				+ "       qhsj = ?, qhks = ?, hqyh = ?, jlyh = ?, jlsj = ?, " //
				+ "       gxyh = ?, gxsj = ?, lflb = ?, lfdw = ?, lfdd = ?, " //
				+ "       ydrq = ?, ydsj = ?, ylrq = ?, ylsj = ?, lfmd = ?, " //
				+ "       pbry = ?, gjzg = ?, hqrq = ?, hqsj = ?, bezu = ?, " //
				+ "       jcfs = ?, jsdd = ?, bfrs = ?, jcdd = ?, jcbz = ?, " //
				+ "       jcsj = ?, jcry = ?, fygs = ?, zsap = ?, zsts = ?, " //
				+ "       hyzy = ?, xlfs = ?, qtzy = ?, zwbz = ?, sdrq = ?, " //
				+ "       sdsj = ?, slrq = ?, slsj = ? " //
				+ " WHERE id = ? "; //
		try {
			pstm = con.prepareStatement(sql);

			pstm.setString(1, e.getGsdm());
			pstm.setString(2, e.getBddm());
			pstm.setString(3, e.getBdbh());
			pstm.setString(4, e.getBdrq());
			pstm.setString(5, e.getBdzt());
			pstm.setString(6, e.getBdjg());
			pstm.setString(7, e.getBdfd());
			pstm.setString(8, e.getBdyy());
			pstm.setString(9, e.getSqyh());
			pstm.setString(10, e.getQhyh());
			pstm.setTimestamp(11, Helper.toSqlDate(e.getQhsj()));
			pstm.setString(12, e.getQhks());
			pstm.setString(13, e.getHqyh());
			pstm.setString(14, e.getJlyh());
			pstm.setTimestamp(15, Helper.toSqlDate(e.getJlsj()));
			pstm.setString(16, e.getGxyh());
			pstm.setTimestamp(17, Helper.toSqlDate(new Date()));
			pstm.setString(18, e.getLflb());
			pstm.setString(19, e.getLfdw());
			pstm.setString(20, e.getLfdd());
			pstm.setString(21, e.getYdrq());
			pstm.setString(22, e.getYdsj());
			pstm.setString(23, e.getYlrq());
			pstm.setString(24, e.getYlsj());
			pstm.setString(25, e.getLfmd());
			pstm.setString(26, e.getPbry());
			pstm.setString(27, e.getGjzg());
			pstm.setString(28, e.getHqrq());
			pstm.setString(29, e.getHqsj());
			pstm.setString(30, e.getBezu());
			pstm.setString(31, e.getJcfs());
			pstm.setString(32, e.getJsdd());
			pstm.setInt(33, e.getBfrs());
			pstm.setString(34, e.getJcdd());
			pstm.setDouble(35, e.getJcbz());
			pstm.setString(36, e.getJcsj());
			pstm.setString(37, e.getJcry());
			pstm.setString(38, e.getFygs());
			pstm.setString(39, e.getZsap());
			pstm.setInt(40, e.getZsts());
			pstm.setString(41, e.getHyzy());
			pstm.setInt(42, e.getXlfs());
			pstm.setString(43, e.getQtzy());
			pstm.setString(44, e.getZwbz());
			pstm.setString(45, e.getSdrq());
			pstm.setString(46, e.getSdsj());
			pstm.setString(47, e.getSlrq());
			pstm.setString(48, e.getSlsj());
			pstm.setInt(49, e.getId());
			pstm.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void load(ResultSet rs, Q001H e) throws Exception {
		e.setId(rs.getInt("ID"));
		e.setGsdm(rs.getString("GSDM"));
		e.setBddm(rs.getString("BDDM"));
		e.setBdbh(rs.getString("BDBH"));
		e.setBdrq(rs.getString("BDRQ"));
		e.setBdzt(rs.getString("BDZT"));
		e.setBdjg(rs.getString("BDJG"));
		e.setBdfd(rs.getString("BDFD"));
		e.setBdyy(rs.getString("BDYY"));
		e.setSqyh(rs.getString("SQYH"));
		e.setQhyh(rs.getString("QHYH"));
		e.setQhsj(rs.getTimestamp("QHSJ"));
		e.setQhks(rs.getString("QHKS"));
		e.setHqyh(rs.getString("HQYH"));
		e.setJlyh(rs.getString("JLYH"));
		e.setJlsj(rs.getTimestamp("JLSJ"));
		e.setGxyh(rs.getString("GXYH"));
		e.setGxsj(rs.getTimestamp("GXSJ"));
		e.setLflb(rs.getString("LFLB"));
		e.setLfdw(rs.getString("LFDW"));
		e.setLfdd(rs.getString("LFDD"));
		e.setYdrq(rs.getString("YDRQ"));
		e.setYdsj(rs.getString("YDSJ"));
		e.setYlrq(rs.getString("YLRQ"));
		e.setYlsj(rs.getString("YLSJ"));
		e.setLfmd(rs.getString("LFMD"));
		e.setPbry(rs.getString("PBRY"));
		e.setGjzg(rs.getString("GJZG"));
		e.setHqrq(rs.getString("HQRQ"));
		e.setHqsj(rs.getString("HQSJ"));
		e.setBezu(rs.getString("BEZU"));
		e.setJcfs(rs.getString("JCFS"));
		e.setJsdd(rs.getString("JSDD"));
		e.setBfrs(rs.getInt("BFRS"));
		e.setJcdd(rs.getString("JCDD"));
		e.setJcbz(rs.getDouble("JCBZ"));
		e.setJcsj(rs.getString("JCSJ"));
		e.setJcry(rs.getString("JCRY"));
		e.setFygs(rs.getString("FYGS"));
		e.setZsap(rs.getString("ZSAP"));
		e.setZsts(rs.getInt("ZSTS"));
		e.setHyzy(rs.getString("HYZY"));
		e.setXlfs(rs.getInt("XLFS"));
		e.setQtzy(rs.getString("QTZY"));
		e.setZwbz(rs.getString("ZWBZ"));
		e.setSdrq(rs.getString("SDRQ"));
		e.setSdsj(rs.getString("SDSJ"));
		e.setSlrq(rs.getString("SLRQ"));
		e.setSlsj(rs.getString("SLSJ"));
	}

	public Q001H find(int id) {

		Q001H e = new Q001H();

		if (id == 0)
			return e;

		String sql = "SELECT * FROM Q001H WHERE id=?";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			rst = pstm.executeQuery();
			if (rst.next()) {
				load(rst, e);
			}
			rst.close();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	public Q001H find(String gsdm, String bddm, String bdbh) {
		Q001H e = new Q001H();

		String sql = "SELECT * FROM Q001H WHERE gsdm=? AND bddm=? AND bdbh=?";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, gsdm);
			pstm.setString(2, bddm);
			pstm.setString(3, bdbh);
			rst = pstm.executeQuery();
			if (rst.next()) {
				load(rst, e);
			}
			rst.close();
			pstm.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	public void cancellation(HttpServletRequest req, String uid) {

		Q001H q001H = find(Integer.parseInt(req.getParameter("id")));

		q001H.setBdjg("X");
		q001H.setBdzt("X");
		q001H.setGxsj(new Date());
		q001H.setGxyh(uid);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		String dateString = sdf.format(new Date());

		try {
			con.setAutoCommit(false);
			update(q001H);

			String sql = "UPDATE QH_BDLC SET QHZT='9', QHJG='N', QHNR=? " //
					+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND QHZT < '9' AND BZDM < 9999"; //

			pstm = con.prepareStatement(sql);

			pstm.setString(1, uid + " 在 " + dateString + " 取消拜訪通知");
			pstm.setString(2, q001H.getGsdm());
			pstm.setString(3, q001H.getBddm());
			pstm.setString(4, q001H.getBdbh());
			pstm.executeUpdate();

			pstm.close();

			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		sdf = null;
	}

	public void leaveVisitor(int id, int q001h_id) throws SQLException {
		String sql = "UPDATE q001l SET slrq = TO_CHAR(SYSDATE, 'YYYYMMDD'), slsj = TO_CHAR(SYSDATE,'HH24MI') WHERE id=" + id;
		try {
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			pstm.close();
			sql = "UPDATE q001h SET slrq = TO_CHAR(SYSDATE, 'YYYYMMDD'), slsj = TO_CHAR(SYSDATE,'HH24MI') WHERE id = " + q001h_id;
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			pstm.close();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}

	public void arriveVisitor(int id, int q001h_id) throws SQLException {
		String sql = "UPDATE q001l SET sdrq = TO_CHAR(SYSDATE, 'YYYYMMDD'), sdsj = TO_CHAR(SYSDATE,'HH24MI') WHERE id=" + id;
		try {
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			pstm.close();
			sql = "UPDATE q001h set sdrq = TO_CHAR(SYSDATE, 'YYYYMMDD'), sdsj = TO_CHAR(SYSDATE,'HH24MI') WHERE sdrq is null and id = " + q001h_id;
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			pstm.close();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}

	public void maintainQ001P(Q001L e) {
		Q001P p = new Q001P();
		p.setLfxm(e.getLfxm());
		p.setSex(e.getSex());
		p.setZjlx(e.getZjlx());
		p.setZjhm(e.getZjhm());
		p.setLfdw(e.getLfdw());
		p.setLfzw(e.getLfzw());
		p.setLfdh(e.getLfdh());
		p.setLfyj(e.getLfyj());
		p.setAddr(e.getAddr());
		p.setCphm(e.getCphm());
		p.setHghm(e.getHghm());
		p.setLfbz(e.getLfbz());
		p.setLffd(e.getLffd());
		p.setGxsj(e.getGxsj());
		p.setGxyh(e.getGxyh());

		String sql = "SELECT id FROM Q001P WHERE lfxm=?";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getLfxm());
			rst = pstm.executeQuery();
			if (rst.next()) {
				p.setId(rst.getInt("ID"));
			} else {
				p.setJlyh(e.getGxyh());
			}
			rst.close();
			pstm.close();

			try {
				save(p);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void startWorkFlow(int id, String uid) {

		Q001H q001h = find(id);

		q001h.setGxsj(new Date());
		q001h.setGxyh(uid);
		q001h.setQhks("Y");
		q001h.setBdzt("0");

		String huiQian = "";

		if (q001h.getLfdd() != null) {
			for (String yh : q001h.getLfdd().split("\\,")) {
				if (!huiQian.contains(yh)) {
					huiQian += (huiQian.length() == 0) ? yh : "," + yh;
				}
			}
		}

		if (q001h.getPbry() != null) {
			for (String yh : q001h.getPbry().split("\\,")) {
				if (!huiQian.contains(yh)) {
					huiQian += (huiQian.length() == 0) ? yh : "," + yh;
				}
			}
		}

		if (q001h.getGjzg() != null) {
			for (String yh : q001h.getGjzg().split("\\,")) {
				if (!huiQian.contains(yh)) {
					huiQian += (huiQian.length() == 0) ? yh : "," + yh;
				}
			}
		}

		if (q001h.getHqyh() != null) {
			for (String yh : q001h.getHqyh().split("\\,")) {
				if (!huiQian.contains(yh)) {
					huiQian += (huiQian.length() == 0) ? yh : "," + yh;
				}
			}
		}

		if (q001h.getGsdm().equals("L400") && !huiQian.contains("GUARD.DT")) {
			huiQian += (huiQian.length() == 0) ? "GUARD.DT" : ",GUARD.DT";
		}

		if (q001h.getGsdm().equals("L300") && !huiQian.contains("GUARD.TX")) {
			huiQian += (huiQian.length() == 0) ? "GUARD.TX" : ",GUARD.TX";
		}

		try {

			con.setAutoCommit(false);

			update(q001h);

			QH_BDTX bdtx = new QH_BDTX();
			bdtx.setGSDM(q001h.getGsdm());
			bdtx.setBDDM(q001h.getBddm());
			bdtx.setBDBH(q001h.getBdbh());
			bdtx.setBDTX("來訪通知：" + q001h.getLfdw());
			bdtx.insertDb(con);

			int bzdm = 0;

			QH_BDLC bdlc = new QH_BDLC();
			bdlc.setGSDM(q001h.getGsdm());
			bdlc.setBDDM(q001h.getBddm());
			bdlc.setBDBH(q001h.getBdbh());
			bdlc.setLXDM(q001h.getBddm());
			bdlc.setQHZT("1");
			bdlc.setLCLX("P");
			bdlc.setQHLX("I");
			bdlc.setYXXS(100);
			bdlc.setZFYN("N");
			bdlc.setSQYH(q001h.getSqyh());

			for (String yh : huiQian.split("\\,")) {
				bdlc.setBZDM(bzdm += 100);
				bdlc.setYSYH(yh);
				bdlc.insertDb(con);
			}

			bdlc.setBZDM(9999);
			bdlc.setYSYH(uid);
			bdlc.setLCLX("S");
			bdlc.setQHLX("A");
			bdlc.insertDb(con);				

			con.commit();
			con.setAutoCommit(false);

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public List<HashMap<String, Object>> getFrequentDelivery() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "select LFDW,max(id) id from q001h where bdyy='送货' group by LFDW order by count(*) desc, LFDW";
		try {
			pstm = con.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<QH_BDLC> crtRoute(Connection conn, String condField, String condValue, Q001H q) throws Exception {

		boolean approverFound = false;

		List<QH_BDLC> routes = new ArrayList<QH_BDLC>();
				
		List<QH_BZLC> stdRoutes = getStdRoutes(conn,q.getGsdm(),q.getBddm());
		for (QH_BZLC s : stdRoutes) {
			QH_BDLC e = new QH_BDLC();
			e.setGSDM(q.getGsdm());
			e.setBDDM(q.getBddm());
			e.setBDBH(q.getBdbh());
			e.setLXDM("Q001");
			e.setQHZT("0");
			e.setBZDM(s.getBZDM());
			e.setLCLX(s.getLCLX());
			e.setMINQ(s.getMINQ());
			e.setMAXQ(s.getMAXQ());
			e.setMINA(s.getMINA());
			e.setMAXA(s.getMAXA());
			e.setMINP(s.getMINP());
			e.setMAXP(s.getMAXP());
			e.setCURR(s.getCURR());
			e.setFTXT(s.getFTXT());
			e.setTTXT(s.getTTXT());
			e.setZWDM(s.getZWDM());
			e.setYXXS(s.getYXXS());
			e.setZFYN(s.getZFYN());
			e.setSQYH(q.getSqyh());
			e.setYJSJ(null);
			e.setYXSJ(null);
			e.setYSYH(s.getYHDM());

			if (s.getQHLX().equals("A")) {
				if (condField.equals("Q")) {
					double value = Double.parseDouble(condValue);
					if (value >= s.getMINQ() && value <= s.getMAXQ()) {
						e.setQHLX(s.getQHLX());
					} else {
						e.setQHLX("R");
					}
				} else if (condField.equals("A")){
					double value = Double.parseDouble(condValue);
					if (value >= s.getMINA() && value <= s.getMAXA()) {
						e.setQHLX(s.getQHLX());
					} else {
						e.setQHLX("R");
					}
				} else if (condField.equals("P")) {
					double value = Double.parseDouble(condValue);
					if (value < 0) {
						e.setQHLX(s.getQHLX());
					} else if (value >= s.getMINP() && value <= s.getMAXP()) {
						e.setQHLX(s.getQHLX());
					} else {
						e.setQHLX("R");
					}
				}
			} else {
				e.setQHLX(s.getQHLX());
			}

			if (approverFound && s.getQHLX().equals("A")) {

				// only allow one approval in the route

			} else {

				if (approverFound && s.getQHLX().equals("R")){
					e.setQHLX("I");
					e.setLCLX("P");
				}
				
				routes.add(e);
			}

			if (e.getQHLX().equals("A")) {
				approverFound = true;
			}

		} // for (QH_BZLC s : stdRoutes)
		return routes;
	}
	
	public List<QH_BZLC> getStdRoutes(Connection conn, String GSDM,String BDDM) throws Exception {
		QH_BZLC e;
		List<QH_BZLC> list = new ArrayList<QH_BZLC>();
		String sql = "SELECT GSDM,LXDM,BZDM,T1.YXFD,T1.YXTD,LCLX,MINQ,MAXQ,MINA,MAXA,MINP,MAXP,CURR,FTXT,TTXT,T1.ZWDM,QHLX,YXXS,ZFYN,NVL(QHYH,YHDM) YHDM  " //
				+ "FROM QH_BZLC T1,QH_ZWYH T2 WHERE T2.ZWDM(+)=T1.ZWDM AND SYSDATE BETWEEN T1.YXFD AND T1.YXTD AND SYSDATE BETWEEN T2.YXFD(+) AND T2.YXTD(+) " //
				+ "AND GSDM=? AND LXDM=? ORDER BY BZDM ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			e = new QH_BZLC();
			e.setGSDM(rst.getString("GSDM"));
			e.setLXDM(rst.getString("LXDM"));
			e.setBZDM(rst.getInt("BZDM"));
			e.setYXFD(rst.getString("YXFD"));
			e.setYXTD(rst.getString("YXTD"));
			e.setLCLX(rst.getString("LCLX"));
			e.setMINQ(rst.getDouble("MINQ"));
			e.setMAXQ(rst.getDouble("MAXQ"));
			e.setMINA(rst.getDouble("MINA"));
			e.setMAXA(rst.getDouble("MAXA"));
			e.setMINP(rst.getDouble("MINP"));
			e.setMAXP(rst.getDouble("MAXP"));
			e.setCURR(rst.getString("CURR"));
			e.setFTXT(rst.getString("FTXT"));
			e.setTTXT(rst.getString("TTXT"));
			e.setZWDM(rst.getString("ZWDM"));
			e.setQHLX(rst.getString("QHLX"));
			e.setYXXS(rst.getDouble("YXXS"));
			e.setZFYN(rst.getString("ZFYN"));
			e.setYHDM(rst.getString("YHDM"));
			list.add(e);
		}
		rst.close();
		pstm.close();
		return list;
	}
}
