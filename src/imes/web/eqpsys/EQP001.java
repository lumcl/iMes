package imes.web.eqpsys;

import imes.core.HttpController;
import imes.core.IRequest;
import imes.dao.EqpDao;
import imes.entity.eqpsys.EQPMAS;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/EQP001", "/EQP001/*" })
public class EQP001 extends HttpController {

	private static final long serialVersionUID = 1L;
	private IRequest ireq;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");

		if (action.equals("LIST")) {
			render(req, resp, "/WEB-INF/pages/eqpsys/EQP001/list.jsp");
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
		
		List<HashMap<String, Object>> list = dao.EQP001Search();
		
		dao.close();
		
		req.setAttribute("list", list);
		
		render(req, resp, "/WEB-INF/pages/eqpsys/EQP001/list.jsp");
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		EQPMAS eqpMas = new EQPMAS();
		try {
			EqpDao dao = new EqpDao(req);
			dao.get(eqpMas, toIntegerParam(req, "id"));
			dao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.setAttribute("eqpMas", eqpMas);
		render(req, resp, "/WEB-INF/pages/eqpsys/EQP001/edit.jsp");
	}

	private void save(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		EQPMAS eqpMas = new EQPMAS();
		updateAttributes(req, eqpMas);

		eqpMas.setChgdat(new Date());
		eqpMas.setChguid(getUid(req));

		try {
			EqpDao dao = new EqpDao(req);
			dao.save(eqpMas);
			dao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect("/iMes/EQP001/edit?id=" + eqpMas.getId());
	}

	private void create(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EQPMAS eqpMas = new EQPMAS();
		eqpMas.setCrtuid(getUid(req));
		eqpMas.setChguid(getUid(req));
		eqpMas.setEqpsts("OK");
		eqpMas.setFixtyp("FA");
		eqpMas.setEqpuom("EA");
		req.setAttribute("eqpMas", eqpMas);
		render(req, resp, "/WEB-INF/pages/eqpsys/EQP001/edit.jsp");
	}

}
