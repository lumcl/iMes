package imes.dao;

import imes.core.Helper;
import imes.entity.PRD001H;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PRD001Dao extends BaseDao {

	public PRD001Dao(Connection con) {
		super.setCon(con);
	}	

	public List<HashMap<String, Object>> searchHeader(HttpServletRequest req) {
		String sql;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		sql = "SELECT ID,LINE,MO,FACTQTY,PLANQTY,JLYH,JLSJ,SCSJ,GXSJ,GXYH FROM prd001 ";

		if ((getParam(req, "scsj_low") + getParam(req, "line_low") + getParam(req, "line_high") + getParam(req, "mo_high") + getParam(req, "mo_low") + getParam(req, "jlyh_low") + getParam(req, "jlyh_high")).equals("")) {
			//sql = " WHERE 1= 1";
		} else {
			sql += " WHERE 1 = 1 ";
			if(!(getParam(req, "scsj_low").equals("")) && !(getParam(req, "scsj_high").equals(""))){
				sql += " AND (SCSJ >= to_date('"+getParam(req, "scsj_low")+"','yyyyMMdd') AND SCSJ <= to_date('"+getParam(req, "scsj_high")+"','yyyyMMdd')) ";
//			} else {
//				Calendar oldCal = Calendar.getInstance();
//				oldCal.add(Calendar.MONTH, -1);
//				sql += " AND (BDRQ >= '" + Helper.fmtDate(oldCal.getTime(), "yyyyMMdd") + "' AND BDRQ <= '" + Helper.fmtDate(new Date(), "yyyyMMdd") + "')";
			}
			if(!(getParam(req, "line_low").equals("")) || !(getParam(req, "line_high").equals(""))){
				if(!(getParam(req, "line_low").equals(""))){
					sql += " AND upper(LINE) like '"+getParam(req, "line_low")+"%' ";
				}
				if(!(getParam(req, "line_high").equals(""))){
					sql += " AND upper(LINE) like '"+getParam(req, "line_high")+"%' ";
				}
			}
			if (!(getParam(req, "mo_low").equals("")) || !(getParam(req, "mo_high").equals(""))) {
				if(!(getParam(req, "mo_low").equals(""))){
					sql += " AND upper(MO) like '"+getParam(req, "mo_low")+"%' ";
				}
				if(!(getParam(req, "mo_high").equals(""))){
					sql += " AND upper(MO) like '"+getParam(req, "mo_high")+"%' ";
				}
			}
			if (!(getParam(req, "jlyh_low").equals("")) || !(getParam(req, "jlyh_high").equals(""))) {
				if(!(getParam(req, "jlyh_low").equals(""))){
					sql += " AND upper(JLYH) = '"+getParam(req, "jlyh_low")+"' ";
				}
				if(!(getParam(req, "jlyh_high").equals(""))){
					sql += " AND upper(JLYH) = '"+getParam(req, "jlyh_high")+"' ";
				}
			}
		}
		
		sql += " order by SCSJ, LINE, MO ";

		try {
			pstm = con.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());

			for (HashMap<String, Object> e : list) {
				try{
					long pqty = Long.valueOf(e.get("PLANQTY").toString());
					long fqty = Long.valueOf(e.get("FACTQTY").toString());
					long qty = pqty-fqty;
					e.put("QTY", qty);
				}catch(Exception ex){}
			}

			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void insert(PRD001H e) {
		String sql = "INSERT INTO PRD001 (ID,LINE,MO,FACTQTY,PLANQTY,JLYH,JLSJ,SCSJ) " //
				+ " VALUES (PRD001H_SEQ.NEXTVAL,?,?,?,?,?,?,?) ";

		try {

			pstm = con.prepareStatement(sql, new String[] { "id" });
			pstm.setString(1, e.getLine());
			pstm.setString(2, e.getMo());
			pstm.setLong(3, e.getFactqty());
			pstm.setLong(4, e.getPlanqty());
			pstm.setString(5, e.getJlyh());
			pstm.setTimestamp(6, Helper.toSqlDate(e.getJlsj()));
			pstm.setTimestamp(7, Helper.toSqlDate(e.getScsj()));
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

	public void update(PRD001H e) {
		String sql = "UPDATE PRD001 " //
				+ " SET LINE = ?, MO = ?, FACTQTY = ?, PLANQTY = ?, GXYH = ?, " //
				+ "   GXSJ = ?, SCSJ = ? "
				+ " WHERE id = ? "; //
		try {
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, e.getLine());
			pstm.setString(2, e.getMo());
			pstm.setLong(3, e.getFactqty());
			pstm.setLong(4, e.getPlanqty());
			pstm.setString(5, e.getJlyh());
			pstm.setTimestamp(6, Helper.toSqlDate(e.getJlsj()));
			pstm.setTimestamp(7, Helper.toSqlDate(e.getScsj()));
			pstm.setInt(8, e.getId());
			
			pstm.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void load(ResultSet rs, PRD001H e) throws Exception {
		e.setLine(rs.getString("line"));
		e.setMo(rs.getString("mo"));
		e.setFactqty(rs.getLong("factqty"));
		e.setPlanqty(rs.getLong("planqty"));
		e.setJlyh(rs.getString("jlyh"));
		e.setJlsj(rs.getTimestamp("jlsj"));
		e.setScsj(rs.getTimestamp("scsj"));
		e.setId(rs.getInt("ID"));
	}

	public PRD001H find(int id) {

		PRD001H e = new PRD001H();

		if (id == 0)
			return e;

		String sql = "SELECT * FROM PRD001 WHERE id=?";

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

	public PRD001H find(String gsdm, String bddm, String bdbh) {
		PRD001H e = new PRD001H();

		String sql = "SELECT * FROM PRD001 WHERE gsdm=? AND bddm=? AND bdbh=?";

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

	public void cancelAction(HttpServletRequest req, long id, String uid) {

		PRD001H prd001H = find(Integer.parseInt(req.getParameter("id")));

		prd001H.setGxsj(new Date());
		prd001H.setGxyh(uid);

		try {			
			String sql = "DELETE PRD001 WHERE ID=? ";

			pstm = con.prepareStatement(sql);

			pstm.setLong(1, id);
			pstm.executeUpdate();

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
