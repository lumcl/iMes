package imes.core;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(description = "Application Filter", urlPatterns = { "/*" })
public class AppFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AppFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// System.out.println("Appfilter **" + req.getRequestURI() + "**");

		if (req.getRequestURI().equals("/iMes/") || (req.getRequestURI().equals("/iMes/login")) || (req.getRequestURI().contains("qrcode"))
				|| (req.getRequestURI().equals("/iMes/test.html")) || (req.getRequestURI().equals("/iMes/register.html"))
				|| (req.getRequestURI().contains("FileDownloader")) ||(req.getRequestURI().contains("FileUploader"))
				|| (req.getRequestURI().equals("/iMes/stylesheet/style.css")) || (req.getRequestURI().equals("/iMes/register"))) {
			chain.doFilter(request, response);
		} else {
			if (req.getSession().getAttribute("uid") == null) {

				String url = req.getRequestURL().toString();
				if (req.getQueryString() != null) {
					url += "?" + req.getQueryString();
				}

				// System.out.println("Appfilter divert:" +
				// req.getRequestURI());
				req.getSession().setAttribute("jurl", url);
				req.getSession().setAttribute("juid", req.getParameter("juid"));
				
				// req.getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				// response);

				resp.sendRedirect("/iMes/");

			} else {

				chain.doFilter(req, resp);
			}
		}
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
