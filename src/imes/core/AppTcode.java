package imes.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AppTcode")
public class AppTcode extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);

		resp.sendRedirect("/iMes/home");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {

			ActionPost(req, resp);

		} catch (Exception e) {

			PrintError(resp, e);
		}

	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		resp.sendRedirect("/iMes/" + getParam(req, "AppTcode").toUpperCase());

	}

}
