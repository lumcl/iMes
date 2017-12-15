package imes.dao;

import imes.core.Helper;
import imes.entity.eqpsys.EQPMAS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class EqpDao extends BaseDao {

	public EqpDao(HttpServletRequest req) {
		try {
			this.con = ((DataSource) req.getServletContext().getAttribute("imesds")).getConnection();
			this.req = req;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<HashMap<String, Object>> EQP002Search() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String params = getParam("cmpnbr_low") + getParam("facnbr_low") + getParam("matnbr_low") + getParam("mattyp_low") + getParam("matgrp_low") + getParam("matsts_low") + getParam("rspdep_low")
				+ getParam("rspuid_low");

		if (params.equals(""))
			return list;
		
		String sql = "SELECT * FROM MTRMAS WHERE 1=1";
		sql += getParam("cmpnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "cmpnbr", "cmpnbr_low", "cmpnbr_high"); //
		sql += getParam("facnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "facnbr", "facnbr_low", "facnbr_high"); //
		sql += getParam("matnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "matnbr", "matnbr_low", "matnbr_high"); //
		sql += getParam("mattyp_low").length() == 0 ? "" : Helper.sqlParams(req, "mattyp", "mattyp_low", "mattyp_high"); //
		sql += getParam("matgrp_low").length() == 0 ? "" : Helper.sqlParams(req, "matgrp", "matgrp_low", "matgrp_high"); //
		sql += getParam("matsts_low").length() == 0 ? "" : Helper.sqlParams(req, "matsts", "matsts_low", "matsts_high"); //
		sql += getParam("rspdep_low").length() == 0 ? "" : Helper.sqlParams(req, "rspdep", "rspdep_low", "rspdep_high"); //
		sql += getParam("rspuid_low").length() == 0 ? "" : Helper.sqlParams(req, "rspuid", "rspuid_low", "rspuid_high"); //
		sql += " ORDER BY matnbr";
		
		System.out.println(sql);
		
		try {
			pstm = con.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<HashMap<String, Object>> EQP001Search() {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String params = req.getParameter("cmpnbr_low") + req.getParameter("facnbr_low") + req.getParameter("eqpnbr_low") + req.getParameter("eqptyp_low") + req.getParameter("eqpgrp_low")
				+ req.getParameter("astnbr_low") + req.getParameter("eqpsts_low") + req.getParameter("rspdep_low") + req.getParameter("rspuid_low") + req.getParameter("prdnbr_low");

		if (req.getParameter("cmpnbr_low") == null || params.trim().equals("")) {
			return list;
		}
		String sql = "SELECT id,cmpnbr,facnbr,eqpnbr,eqptyp,eqpgrp,astnbr,eqpsts,rspdep,rspuid,prdnbr,eqpdes,locate,locker,pcbnbr,compnr,serial,prdlin,mntdat FROM EQPMAS WHERE 1 = 1";

		sql += req.getParameter("cmpnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "cmpnbr", "cmpnbr_low", "cmpnbr_high"); //
		sql += req.getParameter("facnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "facnbr", "facnbr_low", "facnbr_high"); //
		sql += req.getParameter("eqpnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "eqpnbr", "eqpnbr_low", "eqpnbr_high"); //
		sql += req.getParameter("eqptyp_low").length() == 0 ? "" : Helper.sqlParams(req, "eqptyp", "eqptyp_low", "eqptyp_high"); //
		sql += req.getParameter("eqpgrp_low").length() == 0 ? "" : Helper.sqlParams(req, "eqpgrp", "eqpgrp_low", "eqpgrp_high"); //
		sql += req.getParameter("astnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "astnbr", "astnbr_low", "astnbr_high"); //
		sql += req.getParameter("eqpsts_low").length() == 0 ? "" : Helper.sqlParams(req, "eqpsts", "eqpsts_low", "eqpsts_high"); //
		sql += req.getParameter("rspdep_low").length() == 0 ? "" : Helper.sqlParams(req, "rspdep", "rspdep_low", "rspdep_high"); //
		sql += req.getParameter("rspuid_low").length() == 0 ? "" : Helper.sqlParams(req, "rspuid", "rspuid_low", "rspuid_high"); //
		sql += req.getParameter("prdnbr_low").length() == 0 ? "" : Helper.sqlParams(req, "prdnbr", "prdnbr_low", "prdnbr_high"); //
		sql += " ORDER BY eqpnbr";

		try {
			pstm = con.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		HashMap<String, String> eqptyp = EQPMAS.eqptyp();

		for (HashMap<String, Object> e : list) {
			e.put("EQPSTS", EQPMAS.getEqpstsTxt(toString(e.get("EQPSTS"))));
			e.put("EQPTYP", eqptyp.get(toString(e.get("EQPTYP"))));
		}

		eqptyp.clear();
		eqptyp = null;

		return list;
	}
}
