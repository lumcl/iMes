package imes.core;

import imes.entity.User;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebListener
public class AppInit implements ServletContextListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private ServletContext context;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		context.removeAttribute("imesds");
		context.removeAttribute("userIds");
		context.removeAttribute("DataSource");
		context.removeAttribute("UploadFilePath");
		context.removeAttribute("HttpUrl");
		context.removeAttribute("SmtpHost");
		context.removeAttribute("SessionFactory");
		logger.info("Context Attribute Remove");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		context = sce.getServletContext();

		readConfiguration();

		getDBConnectionPool();
		getSapCoDBConnectionPool();
		getSapPrdDBConnectionPool();
		//setupHibernateSessionFactory();
	}

	private void setupHibernateSessionFactory() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		context.setAttribute("SessionFactory", sessionFactory);
	}

	private void getDBConnectionPool() {

		String dataSourceName = context.getAttribute("DataSource").toString();

		try {
			Context initContext = new InitialContext();
			//Context envContext = (Context) initContext.lookup("java:/comp/env");
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup(dataSourceName);
			context.setAttribute("imesds", ds);
			envContext.close();
			initContext.close();

			Connection conn = ds.getConnection();

			context.setAttribute("userIds", User.getAllUserIds(conn));

			conn.close();

		} catch (Exception e) {

			logger.error(dataSourceName + " datasource registed failure.", e);
			throw new RuntimeException(e);
		}

		logger.info(dataSourceName + " datasource registered successful as [imesds].");
	}

	private void getSapCoDBConnectionPool() {

		String dataSourceName = context.getAttribute("SapCoDataSource").toString();

		try {
			Context initContext = new InitialContext();
//			Context envContext = (Context) initContext.lookup("java:/comp/env");
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup(dataSourceName);
			context.setAttribute("sapcods", ds);
			envContext.close();
			initContext.close();

		} catch (Exception e) {

			logger.error(dataSourceName + " datasource registed failure.", e);
			throw new RuntimeException(e);
		}

		logger.info(dataSourceName + " datasource registered successful as [sapcods].");
	}

	private void getSapPrdDBConnectionPool() {

		String dataSourceName = context.getAttribute("SapPrdDataSource").toString();

		try {
			Context initContext = new InitialContext();
//			Context envContext = (Context) initContext.lookup("java:/comp/env");
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup(dataSourceName);
			context.setAttribute("sapprdds", ds);
			envContext.close();
			initContext.close();

		} catch (Exception e) {

			logger.error(dataSourceName + " datasource registed failure.", e);
			throw new RuntimeException(e);
		}

		logger.info(dataSourceName + " datasource registered successful as [sapprdds].");
	}

	private void readConfiguration() {

		Properties prop = new Properties();

		try {
			// set the properties value
			prop.load(new FileInputStream("iMesconfig.properties"));

			context.setAttribute("DataSource", prop.getProperty("DataSource"));
			context.setAttribute("SapCoDataSource", prop.getProperty("SapCoDataSource"));
			context.setAttribute("SapPrdDataSource", prop.getProperty("SapPrdDataSource"));
			context.setAttribute("UploadFilePath", prop.getProperty("UploadFilePath"));
			context.setAttribute("HttpUrl", prop.getProperty("HttpUrl"));
			context.setAttribute("SmtpHost", prop.getProperty("SmtpHost"));

			prop.clear();

		} catch (Exception ex) {

			logger.error("iMesconfig.properties not found, using default setting");
			context.setAttribute("DataSource", "java:/comp/env");
			context.setAttribute("UploadFilePath", "//");

		}

		prop = null;

	}

}
