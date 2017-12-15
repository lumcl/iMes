package imes.web.form;

import imes.core.HttpController;
import imes.core.IRequest;
import imes.dao.PRD001Dao;
import imes.entity.PRD001H;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/PRD001", "/PRD001/*" })
public class PRD001 extends HttpController {

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
				render(req, resp, "/WEB-INF/pages/form/PRD001/list.jsp");
			} else if (action.equals("searchHeader")) {
				searchHeader(req, resp);
			} else if (action.equals("newHeader")) {
				newHeader(req, resp);
			} else if (action.equals("saveHeader")) {
				saveHeader(req, resp);
			} else if (action.equals("editHeader")) {
				editHeader(req, resp);
			} else if (action.equals("cancelAction")) {
				cancelAction(req, resp);
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

	private void searchHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		PRD001Dao prd001Dao = new PRD001Dao(getConnection());

		req.setAttribute("list", prd001Dao.searchHeader(req));

		prd001Dao.close();

		render(req, resp, "/WEB-INF/pages/form/PRD001/list.jsp");
	}

	private void newHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PRD001H prd001H = new PRD001H();
		try{
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date date = df.parse(req.getParameter("scsj")); 
			prd001H.setScsj(date);
			prd001H.setLine(req.getParameter("line"));
			prd001H.setMo(req.getParameter("mo"));
			prd001H.setPlanqty(toIntegerParam(req, "planqty"));
			prd001H.setFactqty(toIntegerParam(req, "factqty"));
			prd001H.setJlsj(new Date());
			prd001H.setJlyh(getUid(req));
		}catch(Exception ex){}

		req.setAttribute("prd001H", prd001H);
		render(req, resp, "/WEB-INF/pages/form/PRD001/edit.jsp");
	}

	private void editHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		PRD001H prd001H = null;

		PRD001Dao prd001Dao = new PRD001Dao(getConnection());

		if (getParam(req, "id").equals("")) {
//			
		} else {
			prd001H = prd001Dao.find(toIntegerParam(req, "id"));
		}

		req.setAttribute("prd001H", prd001H);

		render(req, resp, "/WEB-INF/pages/form/PRD001/edit.jsp");
	}

	private void saveHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		PRD001Dao prd001Dao = new PRD001Dao(getConnection());

		PRD001H prd001H = prd001Dao.find(toIntegerParam(req, "id"));

		updateAttributes(req, prd001H);

		if (prd001H.getId() == 0) {
			try{
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				Date date = df.parse(req.getParameter("scsj")); 
				prd001H.setScsj(date);
				prd001H.setLine(req.getParameter("line"));
				prd001H.setMo(req.getParameter("mo"));
				prd001H.setPlanqty(toIntegerParam(req, "planqty"));
				prd001H.setFactqty(toIntegerParam(req, "factqty"));
				prd001H.setJlsj(new Date());
				prd001H.setJlyh(getUid(req));
	
				prd001Dao.insert(prd001H);
				prd001Dao.close();
			}catch(Exception ex){}
		} else {

			prd001H.setGxyh(getUid(req));
			prd001H.setGxsj(new Date());

			prd001Dao.update(prd001H);
			prd001Dao.close();
		}

		req.setAttribute("prd001H", prd001H);
		render(req, resp, "/WEB-INF/pages/form/PRD001/edit.jsp");
	}	

	private void cancelAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

		PRD001Dao prd001Dao = new PRD001Dao(getConnection());

		String sid = req.getParameter("id");
		long lid = Long.valueOf(sid);
		if (getParam(req, "id").equals("")) {

		} else {
			prd001Dao.cancelAction(req, lid, getUid(req));
		}

		render(req, resp, "/WEB-INF/pages/form/PRD001/list.jsp");
	}
}
