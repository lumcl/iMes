package imes.web.eqpsys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.HttpController;
import imes.core.IRequest;
import imes.dao.EqpDao;
import imes.entity.eqpsys.MTRMAS;

@WebServlet({ "/EQP002", "/EQP002/*" })
public class EQP002 extends HttpController {

	private static final long serialVersionUID = 1L;
	private IRequest ireq;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");

		if (action.equals("LIST")) {
			render(req, resp, "/WEB-INF/pages/eqpsys/EQP002/list.jsp");
		} else if (action.equals("create")) {
			create(req, resp);
		} else if (action.equals("save")) {
			save(req, resp);
		} else if (action.equals("edit")) {
			edit(req, resp);
		} else if (action.equals("search")) {
			search(req, resp);
		} else {
			PrintWriter writer = resp.getWriter();
			writer.println("<h1>action " + action + " not found</h1>");
		}

	}
	
	private void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		EqpDao dao = new EqpDao(req);
		List<HashMap<String, Object>> list = dao.EQP002Search();
		dao.close();
		req.setAttribute("list", list);
		render(req, resp, "/WEB-INF/pages/eqpsys/EQP002/list.jsp");
	}
	
	private void edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		MTRMAS mtrMas = new MTRMAS();
		try {
			EqpDao dao = new EqpDao(req);
			dao.get(mtrMas, toIntegerParam(req, "id"));
			dao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.setAttribute("mtrMas", mtrMas);
		render(req, resp, "/WEB-INF/pages/eqpsys/EQP002/edit.jsp");
	}

	
	private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MTRMAS mtrMas = new MTRMAS();
		mtrMas.setCrtuid(getUid(req));
		mtrMas.setChguid(getUid(req));
		req.setAttribute("mtrMas", mtrMas);
		render(req, resp, "/WEB-INF/pages/eqpsys/EQP002/edit.jsp");
	}
	
	private void save(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		MTRMAS mtrMas = new MTRMAS();
		updateAttributes(req, mtrMas);

		mtrMas.setChgdat(new Date());
		mtrMas.setChguid(getUid(req));

		try {
			EqpDao dao = new EqpDao(req);
			dao.save(mtrMas);
			dao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect("/iMes/EQP002/edit?id=" + mtrMas.getId());
	}
}
