package imes.web.form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.HttpController;
import imes.core.IRequest;

@WebServlet({ "/D402", "/D402/*" })
public class D402 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");

		if (action.equals("LIST")) {

			doListGetAction(req, resp);

		} else if (action.equals("CREATE")) {

			doCreateAction(req, resp);

		} else if (action.equals("READ")) {

			doReadAction(req, resp);

		} else if (action.equals("UPDATE")) {

			doUpdateAction(req, resp);

		} else if (action.equals("DELETE")) {

			doDeleteAction(req, resp);

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

	private void doListGetAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		render(req, resp, "/WEB-INF/pages/form/D402.jsp");

	}

	private void doCreateAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		render(req, resp, "/WEB-INF/pages/form/D402New.jsp");
	}

	private void doReadAction(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void doUpdateAction(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void doDeleteAction(HttpServletRequest req, HttpServletResponse resp) {

	}
}
