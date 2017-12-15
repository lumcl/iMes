package imes.web.form;

import imes.core.AppConst;
import imes.core.Helper;
import imes.core.HttpController;
import imes.core.IRequest;
import imes.dao.Q001Dao;
import imes.entity.Q001H;
import imes.entity.Q001L;
import imes.entity.QH_BDLC;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/Q001", "/Q001/*" })
public class Q001 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			ireq = new IRequest(req);
			String action = ireq.getUrlMap().get("Action");

			if (action == null)
				return;

			if (action.equals("LIST")) {
				render(req, resp, "/WEB-INF/pages/form/Q001/list.jsp");
			} else if (action.equals("searchHeader")) {
				searchHeader(req, resp);
			} else if (action.equals("newHeader")) {
				newHeader(req, resp);
			} else if (action.equals("saveHeader")) {
				saveHeader(req, resp);
			} else if (action.equals("editHeader")) {
				editHeader(req, resp);
			} else if (action.equals("newVisitor")) {
				newVisitor(req, resp);
			} else if (action.equals("editVisitor")) {
				editVisitor(req, resp);
			} else if (action.equals("saveVisitor")) {
				saveVisitor(req, resp);
			} else if (action.equals("deleteVisitor")) {
				deleteVisitor(req, resp);
			} else if (action.equals("arriveVisitor")) {
				arriveVisitor(req, resp);
			} else if (action.equals("leaveVisitor")) {
				leaveVisitor(req, resp);
			} else if (action.equals("startWorkflow")) {
				startWorkflow(req, resp);
			} else if (action.equals("QianHe")) {
				qianHe(req, resp);
			} else if (action.equals("cancellation")) {
				cancellation(req, resp);
			} else if (action.equals("visitors")) {
				visitors(req, resp);
			} else if (action.equals("selectVisitor")) {
				selectVisitor(req, resp);
			} else if (action.equals("copy")) {
				copy(req, resp);
			} else if (action.equals("QianHe")) {
				if(req.getParameter("BZDM")!= null && !"".equals(req.getParameter("BZDM"))){

					//actionPostQianHe(req, resp);
					
				} else {
					
					resp.sendRedirect("/iMes/DQ001/editHeader?GSDM="+req.getParameter("GSDM")+"&BDDM="+req.getParameter("BDDM")+"&BDBH="+req.getParameter("BDBH")+"&HQYH="+getUid(req)+"");
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	private void cancellation(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.cancellation(req, getUid(req));
		q001Dao.close();
		resp.sendRedirect("/iMes/Q001/editHeader?id=" + getParam(req, "id"));
	}

	private void qianHe(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equals("GET")) {
			editHeader(req, resp);
		} else {
			Q001Dao q001Dao = new Q001Dao(getConnection());
			q001Dao.qianHeResponse(req, getUid(req));
			q001Dao.close();
			resp.sendRedirect("/iMes/qh?action=Find&STRQHZT=2&STRYSYH=" + getUid(req));
		}
	}

	private void startWorkflow(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.startWorkFlow(toIntegerParam(req, "id"), getUid(req));
		q001Dao.close();
		resp.sendRedirect("/iMes/Q001/editHeader?id=" + getParam(req, "id"));
	}

	private void newVisitor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, Exception {
		Q001L q001L = new Q001L();
		req.setAttribute("q001L", q001L);
		render(req, resp, "/WEB-INF/pages/form/Q001/_visitor.jsp");
	}

	private void editVisitor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, Exception {
		Q001L q001L = new Q001L();
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.get(q001L, toIntegerParam(req, "id"));
		q001Dao.close();
		req.setAttribute("q001L", q001L);
		render(req, resp, "/WEB-INF/pages/form/Q001/_visitor.jsp");
	}

	private void deleteVisitor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.delete(Q001L.class, toIntegerParam(req, "id"));
		q001Dao.close();
		resp.sendRedirect("/iMes/Q001/editHeader?id=" + getParam(req, "q001h_id"));
	}

	private void saveVisitor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, IllegalArgumentException, IllegalAccessException, SecurityException,
			NoSuchFieldException {
		Q001L q001L = new Q001L();
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.get(q001L, toIntegerParam(req, "id"));
		updateAttributes(req, q001L);

		q001L.setGxsj(new Date());
		q001L.setGxyh(getUid(req));
		if (q001L.getId() == 0) {
			q001L.setJlsj(new Date());
			q001L.setJlyh(getUid(req));
		}
		q001Dao.save(q001L);
		q001Dao.maintainQ001P(q001L);
		q001Dao.close();

		resp.sendRedirect("/iMes/Q001/editHeader?id=" + q001L.getQ001h_id());
	}

	private void arriveVisitor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.arriveVisitor(toIntegerParam(req, "id"), toIntegerParam(req, "q001h_id"));
		q001Dao.close();
		resp.sendRedirect("/iMes/Q001/editHeader?id=" + getParam(req, "q001h_id"));
	}

	private void leaveVisitor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		Q001Dao q001Dao = new Q001Dao(getConnection());
		q001Dao.leaveVisitor(toIntegerParam(req, "id"), toIntegerParam(req, "q001h_id"));
		q001Dao.close();
		resp.sendRedirect("/iMes/Q001/editHeader?id=" + getParam(req, "q001h_id"));
	}

	private void searchHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		Q001Dao q001Dao = new Q001Dao(getConnection());

		req.setAttribute("list", q001Dao.searchHeader(req));

		q001Dao.close();

		render(req, resp, "/WEB-INF/pages/form/Q001/list.jsp");
	}

	private void newHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Q001H q001H = new Q001H();
		//q001H.setGsdm("L400");
		q001H.setBdrq(Helper.fmtDate(new Date(), "yyyyMMdd"));
		q001H.setSqyh(getUid(req));
		q001H.setJlsj(new Date());
		q001H.setLfdd(getUid(req) + ",");
		//q001H.setJcsj("1230 - 1310");

		req.setAttribute("q001H", q001H);
		render(req, resp, "/WEB-INF/pages/form/Q001/edit.jsp");
	}

	private void editHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		Q001H q001H;

		Q001Dao q001Dao = new Q001Dao(getConnection());

		if (getParam(req, "id").equals("")) {
			q001H = q001Dao.find(getParam(req, "GSDM"), getParam(req, "BDDM"), getParam(req, "BDBH"));
		} else {
			q001H = q001Dao.find(toIntegerParam(req, "id"));
		}

		List<HashMap<String, Object>> visitors = q001Dao.visitors(q001H.getId());

		if (q001H.getQhks() != null && q001H.getQhks().equals("Y")) {
			req.setAttribute("BDLCS", QH_BDLC.findBy(q001H.getGsdm(), q001H.getBddm(), q001H.getBdbh(), q001Dao.con));
		}
		q001Dao.close();

		req.setAttribute("q001H", q001H);
		req.setAttribute("visitors", visitors);

		render(req, resp, "/WEB-INF/pages/form/Q001/edit.jsp");
	}

	private void saveHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		Q001Dao q001Dao = new Q001Dao(getConnection());

		Q001H q001H = q001Dao.find(toIntegerParam(req, "id"));

		updateAttributes(req, q001H);

		q001H.setGsdm(getParam(req, "gsdm"));
		q001H.setYdsj(toIntegerParam(req, "ydsj_xs"), toIntegerParam(req, "ydsj_fz"));
		q001H.setYlsj(toIntegerParam(req, "ylsj_xs"), toIntegerParam(req, "ylsj_fz"));
		q001H.setHqsj(toIntegerParam(req, "hqsj_xs"), toIntegerParam(req, "hqsj_fz"));

		if (q001H.getId() == 0) {

			q001H.setBddm("Q001");
			q001H.setBdzt(AppConst.BdztCreate);
			q001H.setJlyh(getUid(req));
			q001H.setSqyh(getUid(req));
			q001H.setBdbh(Helper.getBDBH(getConnection(), q001H.getGsdm(), q001H.getBddm(), q001H.getBdrq().substring(0, 4)));

			q001Dao.insert(q001H);
			q001Dao.close();

		} else {

			q001H.setGxyh(getUid(req));
			q001H.setGxsj(new Date());

			q001Dao.update(q001H);
			q001Dao.close();
		}

		req.setAttribute("q001H", q001H);
		render(req, resp, "/WEB-INF/pages/form/Q001/edit.jsp");
	}

	private void visitors(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String term = req.getParameter("term");

		try {
			term = URLDecoder.decode(req.getParameter("term"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String json = "";

		String sql = "SELECT  lfxm, zjhm, lfxm || '|' || sex || '|' || zjlx || '|' || zjhm || '|' || lfdw || '|' || lfzw || '|' || lfdh || '|' || lfyj || '|' || addr || '|' || cphm || '|' || hghm || '|' || lfbz datas FROM Q001P WHERE (lfxm LIKE ?) OR (zjhm LIKE ?)";

		try {
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%" + term + "%");
			pstm.setString(2, term + "%");
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {

				if (json.equals("")) {

					json = "[{\"label\": \"" + rst.getString("LFXM") + "-" + rst.getString("ZJHM") + "\", \"value\":\"" + rst.getString("DATAS") + "\"}";

				} else {

					json += ",{\"label\": \"" + rst.getString("LFXM") + "-" + rst.getString("ZJHM") + "\", \"value\":\"" + rst.getString("DATAS") + "\"}";
				}
			}
			rst.close();
			pstm.close();
			conn.close();
			if (!json.equals("")) {

				json += "]";
			}

		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}

		PrintWriter writer = resp.getWriter();
		writer.print(json);
		writer.flush();
		writer.close();
	}

	private void selectVisitor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Q001Dao q001Dao;
		try {
			q001Dao = new Q001Dao(getConnection());
			req.setAttribute("list", q001Dao.getFrequentDelivery());
			q001Dao.close();
			render(req, resp, "/WEB-INF/pages/form/Q001/frequentVisitorCreation.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void copy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, Exception, IllegalAccessException {

		Q001Dao q001Dao = new Q001Dao(getConnection());

		Q001H q001H = q001Dao.find(toIntegerParam(req, "id"));

		q001H.setYdrq(Helper.fmtDate(new Date(), "yyyyMMdd"));
		q001H.setYlrq(q001H.getYdrq());
		q001H.setYdsj(00, 00);
		q001H.setYlsj(00, 00);
		q001H.setHqsj(00, 00);
		q001H.setId(0);
		q001H.setBddm("Q001");
		q001H.setBdrq(q001H.getYdrq());
		q001H.setBdzt(AppConst.BdztCreate);
		q001H.setBdjg("");
		q001H.setBdfd("");
		q001H.setJlyh(getUid(req));
		q001H.setSqyh(getUid(req));
		q001H.setQhyh("");
		q001H.setQhsj(null);
		q001H.setQhks("");
		q001H.setSdrq("");
		q001H.setSdsj("");
		q001H.setSlrq("");
		q001H.setSlsj("");
		q001H.setBdbh(Helper.getBDBH(getConnection(), q001H.getGsdm(), q001H.getBddm(), q001H.getBdrq().substring(0, 4)));

		q001Dao.insert(q001H);
		
		q001Dao.copyVisitor(toIntegerParam(req, "id"), q001H.getId(), q001H.getBdbh(), getUid(req));
		
		q001Dao.close();
		
		req.setAttribute("q001H", q001H);
		resp.sendRedirect("/iMes/Q001/editHeader?id=" + q001H.getId());
	}
}
