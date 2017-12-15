package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.IRequest;

@WebServlet({ "/MM66", "/MM66/*" })
public class MM66 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");

		if (action.equals("LIST")) {

			render(req, resp, "/WEB-INF/pages/enquiry/MM66.jsp");

		} else if (action.equals("CREATE")) {

		} else if (action.equals("READ")) {

		} else if (action.equals("UPDATE")) {

		} else if (action.equals("DELETE")) {

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ireq = new IRequest(req);
			String action = ireq.getUrlMap().get("Action");

			if (action.equals("LIST")) {

				doListPostAction(req, resp);
				render(req, resp, "/WEB-INF/pages/enquiry/MM66.jsp");

			} else if (action.equals("CREATE")) {

			} else if (action.equals("READ")) {

			} else if (action.equals("UPDATE")) {

			} else if (action.equals("DELETE")) {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doListPostAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "matkl_low")
				+ getParam(req, "lifnr_low") + getParam(req, "maktx_low") + getParam(req, "ekgrp_low")
				+ getParam(req, "matnr_textarea") + getParam(req, "matkl_textarea") + getParam(req, "charg_textarea")
				+ getParam(req, "ekgrp_textarea") + getParam(req, "userid_low");

		if (!jspParams.equals("")) {

			String sql = "SELECT ZMM66.*, USERID FROM ZMM66,QH_EKGRP T1 WHERE T1.GSDM(+) = 'L400' AND T1.EKGRP(+)=ZMM66.EKGRP";

			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "ZMM66.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "ZMM66.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "ekgrp_low").equals("")) {
				sql += Helper.sqlParams(req, "ZMM66.EKGRP", "ekgrp_low", "ekgrp_high");
			}
			if (!getParam(req, "matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "ZMM66.MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req, "maktx_low").equals("")) {
				sql += " AND ZMM66.MAKTX LIKE '%" + getParam(req, "maktx_low") + "%'";
			}
			if (!getParam(req, "matnr_textarea").equals("")) {
				sql += " AND ZMM66.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "matkl_textarea").equals("")) {
				sql += " AND ZMM66.MATKL IN (" + toSqlString(textAreaToList(req, "matkl_textarea")) + ") ";
			}
			if (!getParam(req, "ekgrp_textarea").equals("")) {
				sql += " AND ZMM66.EKGRP IN (" + toSqlString(textAreaToList(req, "ekgrp_textarea")) + ") ";
			}
			if (!getParam(req, "lifnr_low").equals("")) {
				sql += Helper.sqlParams(req, "ZMM66.LIFNR", "lifnr_low", "lifnr_high");
			}
			if (!getParam(req, "userid_low").equals("")) {
				sql += Helper.sqlParams(req, "T1.USERID", "userid_low", "userid_high");
			}

			sql += " ORDER BY WERKS,MATNR,BDTER ";
			
			Connection conn = getConnection();
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			
			pstm.close();
			conn.close();
		}

		for (HashMap<String, Object> h : list) {
			if (h.get("AUFNR").equals(" ")){
				h.put("AUFNR", h.get("PLNUM"));
			}
		}

		req.setAttribute("list", list);
	}
}
