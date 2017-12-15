package imes.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import imes.core.Helper;
import imes.core.Mailer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class QHReminder implements Runnable {

	private ServletContext context;
	private ServletContextEvent event;
	private Logger logger = Logger.getLogger(this.getClass());

	public QHReminder(ServletContextEvent event) {
		super();
		this.event = event;
		this.context = event.getServletContext();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("QHReminder Start Up");
		if (Helper.fmtDate(new Date(), "HH").equals("07")) {
			try {
				sendReminder();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("QHReminder ShutDown Up");
	}

	private void sendReminder() throws Exception {
		try {
			Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
			String sql = "  SELECT ysyh " //
					+ "    FROM qh_bdlc, users " //
					+ "   WHERE qhzt = '2' AND users.userid = ysyh " //
					+ " GROUP BY ysyh " //
					+ " ORDER BY ysyh " //
			;
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {
				htmlMail(rst.getString("ysyh"), conn);
			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void htmlMail(String user, Connection conn) throws Exception {

		String httpUrl = event.getServletContext().getAttribute("HttpUrl").toString();
		// if (httpUrl == null || httpUrl.length() == 0) {
		httpUrl = "http://eform.l-e-i.com:3058/imes/";
		String imesurl = "http://172.31.4.177:8080/iMes/";
		// }

		String smtpHost = event.getServletContext().getAttribute("SmtpHost").toString();

		String sql = "  SELECT t1.gsdm, t1.bdbh, t1.ysyh, t1.sqyh, t1.bzdm, t1.bddm," //
				+ "         TO_CHAR ( t1.yjsj, 'YYYY/MM/DD HH24:MI:SS') yjsj, t2.bdtx " //
				+ "    FROM qh_bdlc t1, qh_bdtx t2 " //
				+ "   WHERE     t1.qhzt = '2' " //
				+ "         AND t1.gsdm = t2.gsdm " //
				+ "         AND t1.bddm = t2.bddm " //
				+ "         AND t1.bdbh = t2.bdbh " //
				+ "         AND ysyh = ? " //
				+ "ORDER BY ysyh, yjsj, t1.gsdm, t1.bddm, t1.bdbh, " //
				+ "         t1.bzdm " //
		;

		String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" //
				+ "<html>" //
				+ "<head>" //
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" //
				+ "<title>Insert title here</title>" //
				+ "</head><style>th,td {padding: 10px;}th{background-color: #deeaf5}</style>" //
				+ "<body>" //
				+ " <p>您好，以下是需要您簽核的表單</p>" //
				+ "	<table border=\"4\" style=\"border-collapse: collapse;\">" //
				+ "		<thead>" //
				+ "			<tr>" //
				+ "				<th>序號</th>" + "				<th>公司代碼</th>" //
				+ "				<th>表單說明</th>" //
				+ "				<th>动作</th>" //
				+ "				<th>通知時間</th>" //
				+ "				<th></th>" //
				//+ "				<th></th>" //
				+ "			</tr>" //
				+ "		</thead>" //
				+ "		<tbody>" //
		;

		String url;
		String pcurl;
		int qhCount = 0;
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, user);
		ResultSet rst = pstm.executeQuery();
		Calendar oldCal = Calendar.getInstance();
		while (rst.next()) {
			qhCount++;
			url = httpUrl + rst.getString("BDDM").toLowerCase() + "hs/"+rst.getString("BDBH")+"" //
					+ "?juid=" + rst.getString("YSYH") //
					+ "&GSDM=" + rst.getString("GSDM") //
					+ "&BDDM=" + rst.getString("BDDM") //
					+ "&BDBH=" + rst.getString("BDBH") //
					+ "&SID=" + Helper.md5Java(rst.getString("BDBH")+rst.getString("YSYH")+Helper.fmtDate(oldCal.getTime(), "yyyyMMdd")); //
			pcurl = imesurl+ rst.getString("BDDM") + "?action=QianHe" //
					+ "&juid=" + rst.getString("YSYH") //
					+ "&GSDM=" + rst.getString("GSDM") //
					+ "&BDDM=" + rst.getString("BDDM") //
					+ "&BDBH=" + rst.getString("BDBH"); //

			html += "<tr><td>" + qhCount + "</td>" + "<td>" + rst.getString("GSDM") + "</td>" //
					+ "<td>" + rst.getString("BDTX") + "</td>" // 
					+ "	<th><a href=\"" + pcurl + "\">管理</a></th>" //
					+ "<td>" + rst.getString("YJSJ") + "</td>" //
					+ "	<th><a href=\"" + url + "\">手机签核</a></th>" //
					//+ "	<th><a href=\"" + pcurl + "\">电脑签核</a></th>" //
					+ "</tr>" //
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
		mailer.setSubject("簽核通知清單:您一共有 " + qhCount + " 個表單需要審核");

		mailer.setMsgText(html);

		mailer.setTo(user + "@l-e-i.com");
		logger.info("Send Email to:" + mailer.getTo());

		if (qhCount > 0) {
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
