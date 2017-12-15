package imes.web.form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.HttpController;
import imes.core.IRequest;

@WebServlet({ "/D145", "/D145/*" })
public class D145 extends HttpController {

	private static final long serialVersionUID = 1L;

	private IRequest ireq;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ireq = new IRequest(req);
		String action = ireq.getUrlMap().get("Action");

		if (action.equals("LIST")) {

			doListGetAction(req, resp);

		} else if (action.equals("CREATE")) {

		} else if (action.equals("READ")) {

		} else if (action.equals("UPDATE")) {

		} else if (action.equals("DELETE")) {

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	private void doListGetAction(HttpServletRequest req, HttpServletResponse resp) {

	}
}
