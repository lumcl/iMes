package imes.test;

import imes.core.HttpController;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Servlet implementation class Hibernate4Test
 */
@WebServlet({ "/Hibernate4Test", "/Hibernate4Test/*" })
public class Hibernate4Test extends HttpController {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Hibernate4Test() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();		
		out.write("getLocalAddr:" + request.getLocalAddr() + "<br/>");

		Session session = getHibernateSession();
		
		Transaction tx = null;
		try {
			// 开始事务
			tx = session.beginTransaction();
			// 给对象设定值
			Student s = new Student();
			s.setName("panda");
			s.setAge(20);
			System.out.println("Start to insert into database....");
			// 保存数据到数据库
			session.save(s);
			// 从持久化类Student中读取数据
			Student s1 = (Student) session.load(Student.class,
					new Integer(1));
			System.out.println("Load from database name:"
					+ s1.getName());
			// 结束事务
			tx.commit();
			tx = null;
			System.out.println("Congratuation!It works!");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
        
        out.println("<br/>success!");
        		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
