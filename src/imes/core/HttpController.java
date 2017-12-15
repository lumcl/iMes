package imes.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

public class HttpController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public HttpServletRequest req;
	public HttpServletResponse resp;

	public DataSource getDataSource() {
		return (DataSource) getServletContext().getAttribute("imesds");
	}

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public Connection getSapCoConnection() throws SQLException {
		return ((DataSource) getServletContext().getAttribute("sapcods"))
				.getConnection();
	}

	public Connection getSapPrdConnection() throws SQLException {
		return ((DataSource) getServletContext().getAttribute("sapprdds"))
				.getConnection();
	}

	public Session getHibernateSession() {
		return ((SessionFactory) getServletContext().getAttribute(
				"SessionFactory")).openSession();
	}

	public String getUid(HttpServletRequest req) {
		return req.getSession().getAttribute("uid").toString();
	}

	public String getUMgr(HttpServletRequest req) {
		return req.getSession().getAttribute("umgr").toString();
	}

	public String getAction(HttpServletRequest req) {
		if (req.getParameter("action") == null) {
			return "";
		} else {
			return req.getParameter("action").toString().trim();
		}
	}

	public String toUpperCaseParam(HttpServletRequest req, String param) {
		if (req.getParameter(param) == null) {
			return "";
		}
		return req.getParameter(param).toUpperCase().trim();
	}

	public double toDoubleParam(HttpServletRequest req, String param) {
		if (req.getParameter(param) == null) {
			return 0;
		}
		try {
			return Double.parseDouble(req.getParameter(param).trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public double toDouble(Object obj) {
		if (obj == null) {
			return 0;
		}
		try {
			return Double.parseDouble(obj.toString().trim());

		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public Integer toInteger(Object obj) {
		if (obj == null) {
			return 0;
		}
		try {
			return Integer.parseInt(obj.toString().trim());

		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public int toIntegerParam(HttpServletRequest req, String param) {
		if (req.getParameter(param) == null) {
			return 0;
		}
		try {
			return Integer.parseInt(req.getParameter(param).trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void render(HttpServletRequest req, HttpServletResponse resp,
			String jspFilename) throws ServletException, IOException {
		if (req.getSession().getAttribute("uid") == null) {
			resp.sendRedirect("/iMes/");
		} else {
			getServletContext().getRequestDispatcher(jspFilename).forward(req,
					resp);
			return;
		}
	}

	public String getParam(HttpServletRequest req, String param) {
		if (req.getParameter(param) == null) {
			return "";
		} else {
			return req.getParameter(param).trim();
		}
	}

	public String getParam(HttpServletRequest req, String param, String format) {
		if (req.getParameter(param) == null) {
			return "";
		} else {
			try {
				return String.format(format,
						Long.parseLong(req.getParameter(param).trim()));
			} catch (Exception e) {
				return "";
			}
		}
	}

	public void PrintError(HttpServletResponse resp, Exception e) {
		try {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public List<String> textAreaToList(HttpServletRequest req, String param) {
		String str = req.getParameter(param).toUpperCase();
		List<String> list = new ArrayList<String>();
		String[] buf = str.split("\n");
		for (String s : buf) {
			list.add(s.trim().replace("\r", ""));
		}
		buf = null;
		return list;
	}

	public List<String> textAreaToList(HttpServletRequest req, String param,
			String fmt) {
		String str = req.getParameter(param).toUpperCase();
		List<String> list = new ArrayList<String>();
		String[] buf = str.split("\n");
		for (String s : buf) {
			if (s == null || s.trim().equals("")) {
				// if null or space
			} else {
				try {
					list.add(String.format(fmt,
							Long.parseLong(s.trim().replace("\r", ""))));
				} catch (NumberFormatException e) {
				}
			}
		}
		buf = null;
		return list;
	}

	public String toSqlString(List<String> array) {
		String s = "";
		int i = 0;
		for (String a : array) {
			i = i + 1;
			if (i > 999) {
				break;
			}
			if (s.equals(""))
				s = "'" + a + "'";
			else
				s += ",'" + a + "'";
		}
		return s;
	}

	public List<HashMap<String, Object>> resultSetToArrayList(ResultSet rs)
			throws SQLException {

		int rowid = 1;
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>();

			for (int i = 1; i <= columns; i++) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			row.put("ROWID", rowid);
			list.add(row);
			rowid += 1;
		}

		rs.close();

		md = null;
		return list;
	}

	public String getValues(HttpServletRequest req, String param) {
		String val = "";
		for (String str : req.getParameterValues(param)) {
			if (val.equals(""))
				val = str;
			else
				val += "," + str;
		}
		return val;
	}

	@SuppressWarnings("rawtypes")
	public void updateAttributes(HttpServletRequest req, Object obj) {

		Class objClass = obj.getClass();

		Field field;
		String fieldType, param;
		for (Enumeration<String> e = req.getParameterNames(); e
				.hasMoreElements();) {

			param = e.nextElement();
			try {
				field = objClass.getDeclaredField(param);
				field.setAccessible(true);
				fieldType = field.getType().getName();

				if (fieldType.contains("String")) {
					field.set(obj, getValues(req, param));
				} else if (fieldType.contains("double")) {
					field.set(obj, Double.parseDouble(getValues(req, param)));
				} else if (fieldType.contains("int")) {
					field.set(obj, Integer.parseInt(getValues(req, param)));
				} else if (fieldType.contains("Date")) {

				} else if (fieldType.contains("float")) {
					field.set(obj, Float.parseFloat(getValues(req, param)));
				}

				field.setAccessible(false);
				
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} catch (NoSuchFieldException ex) {
				
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}

		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// req.setCharacterEncoding("UTF-8");
		// resp.setContentType("text/html");
		// resp.setCharacterEncoding("UTF-8");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// req.setCharacterEncoding("UTF-8");
		// resp.setContentType("text/html");
		// resp.setCharacterEncoding("UTF-8");
	}

	public String getParams(String name) {
		return req.getParameter(name) == null ? "" : req.getParameter(name).trim().toUpperCase();
	}
}
