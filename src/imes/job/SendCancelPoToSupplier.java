package imes.job;

import imes.core.Mailer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class SendCancelPoToSupplier implements Runnable {
	private ServletContext context;
	private ServletContextEvent event;
	private Logger logger = Logger.getLogger(this.getClass());

	public SendCancelPoToSupplier(ServletContextEvent event) {
		super();
		this.event = event;
		this.context = event.getServletContext();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("SendCancelPoToSupplier Start Up");
		try {
			sendReminder();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("SendCancelPoToSupplier Shutdown");
	}

	private void sendReminder() throws Exception {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		Connection conn = ((DataSource) getContext().getAttribute("sapcods")).getConnection();

		String sql = "SELECT DISTINCT A.LIFNR, B.SMTP_ADDR EMAIL " //
				+ "  FROM DLTPOS A, LIFNR_ADDR B " //
				+ " WHERE A.LIFNR = B.LIFNR(+) " //
				+ " AND A.LIFNR = 'DT19205'"
		;

		String lifnr, email;
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			lifnr = rst.getString("LIFNR");
			email = rst.getString("EMAIL");
			System.out.println(lifnr + "-" + email);
			map = new HashMap<String, String>();
			map.put("LIFNR", lifnr);
			map.put("EMAIL", email);
			list.add(map);
		}
		rst.close();
		pstm.close();

		int i = 0;

		for (HashMap<String, String> m : list) {
			lifnr = m.get("LIFNR");
			email = m.get("EMAIL");

			i++;

			System.out.println(i + "/" + list.size() + "-" + lifnr + "-" + email);

			htmlMail(lifnr, email, conn);
		}

		conn.close();
	}

	private void htmlMail(String lifnr, String email, Connection conn) throws Exception {

		String smtpHost = event.getServletContext().getAttribute("SmtpHost").toString();

		String sql = "  SELECT WERKS, BEDAT, EBELN, EBELP, MATNR, MAKTX, MNG01, MEINS " //
				+ "    FROM DLTPOS " //
				+ "   WHERE LIFNR = ? " //
				+ "ORDER BY WERKS, BEDAT, EBELN, EBELP " //
		;

		String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" //
				+ "<html>" //
				+ "<head>" //
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" //
				+ "<title>Insert title here</title>" //
				+ "</head><style>th,td {padding: 10px;}th{background-color: #deeaf5}</style>" //
				+ "<body>" //
				+ " <p>您好，以下採購訂單已經被我司系統自動刪除, 如有疑問請聯繫我司採購員, 謝謝!</p>" //
				+ "	<table border=\"4\" style=\"border-collapse: collapse;\">" //
				+ "		<thead>" //
				+ "			<tr>" //
				+ "				<th>工廠</th>" + "				<th>訂單日期</th>" //
				+ "				<th>訂單號</th>" //
				+ "				<th>物料號</th>" //
				+ "				<th>規格</th>" //
				+ "				<th>數量</th>" //
				+ "				<th>單位</th>" //
				+ "			</tr>" //
				+ "		</thead>" //
				+ "		<tbody>" //
		;

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, lifnr);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			html += "" //
					+ "			<tr>" //
					+ "				<th>" + rst.getString("WERKS") + "</th>" + "				<th>" + rst.getString("BEDAT") + "</th>" + "				<th>" + rst.getString("EBELN") + "-" + rst.getString("EBELP") + "</th>"
					+ "				<th>" + rst.getString("MATNR") + "</th>" + "				<th>" + rst.getString("MAKTX") + "</th>" + "				<th>" + rst.getString("MNG01") + "</th>" + "				<th>"
					+ rst.getString("MEINS") + "</th>" + "			</tr>" //
			;
		}
		rst.close();
		pstm.close();

		html += "		</tbody>" //
				+ "	</table>" //
				+ "</body>" //
				+ "</html>" //
		;

		Mailer mailer = new Mailer();
		mailer.setFrom("lum.cl@l-e-i.com");
		mailer.setHost(smtpHost);
		mailer.setSubject(lifnr + "- 採購訂單取消通知");

		mailer.setMsgText(html);

		//mailer.setTo(email);
		mailer.setTo("lum.cl@l-e-i.com");
		logger.info("Send Email to:" + mailer.getTo());

		for (int i = 0; i < 3; i++) {
			try {
				mailer.sendHtml();
				break;
			} catch (Exception e2) {
				logger.error(e2.getMessage());
				Thread.sleep(i * 1000);
			}
		}

	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public ServletContextEvent getEvent() {
		return event;
	}

	public void setEvent(ServletContextEvent event) {
		this.event = event;
	}

}
