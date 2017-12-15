package imes.test;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.HttpController;
import imes.job.POSchedule;

@WebServlet({ "/PTest", "/PTest/*" })
public class PTest extends HttpController{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		
		ServletContext context = req.getServletContext();
		POSchedule p = new POSchedule(context);
		p.start();
		
	}

}
