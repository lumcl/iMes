package imes.job;

import imes.core.Helper;
import imes.core.Mailer;
import imes.entity.QH_BDLC;
import imes.entity.QH_YHDL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class QHEngine implements Runnable {

	private ServletContextEvent event;

	private HashMap<String, List<QH_BDLC>> hmap = new HashMap<String, List<QH_BDLC>>();

	private HashMap<String, QH_YHDL> yhdl = new HashMap<String, QH_YHDL>();

	private Logger logger = Logger.getLogger(this.getClass());

	private Connection conn;

	List<QH_BDLC> emails = new ArrayList<QH_BDLC>();

	List<QH_BDLC> notificatios = new ArrayList<QH_BDLC>();

	@Override
	public void run() {

		try {
			logger.info("QH Engine Startup");

			emails.clear();
			
			notificatios.clear();
			
			DataSource ds = (DataSource) event.getServletContext().getAttribute("imesds");
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			try {

				restartRoute();
				getDaiLiYonghu();
				getOpenQH();
				processOpenQH();
				conn.commit();

				removeDuplication();

				sendReminder(emails);

				d400glyQH();
			} catch (Exception e) {

				conn.rollback();
				e.printStackTrace();
			}

			conn.setAutoCommit(true);

			sendNotification();

			notificatios.clear();

			conn.close();

			//sendMail(emails);

			emails.clear();

			hmap.clear();

			yhdl.clear();

			logger.info("QH Engine Shutdown");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public QHEngine(ServletContextEvent event) {
		super();
		this.event = event;
	}

	private void restartRoute() throws Exception {
		String sql = "SELECT t1.gsdm, t1.bddm, t1.bdbh, t1.bzdm " //
				+ "  FROM (SELECT t1.gsdm, " //
				+ "               t1.bddm, " //
				+ "               t1.bdbh, " //
				+ "               t1.bzdm, " //
				+ "               (SELECT COUNT (*) " //
				+ "                  FROM qh_bdlc t2 " //
				+ "                 WHERE     gsdm = t1.gsdm " //
				+ "                       AND bddm = t1.bddm " //
				+ "                       AND bdbh = t1.bdbh " //
				+ "                       AND qhzt BETWEEN '1' AND '3' " //
				+ "                       AND bzdm <> 9999) " //
				+ "                  cnt " //
				+ "          FROM (  SELECT t1.gsdm, t1.bddm, t1.bdbh, MIN (t1.bzdm) bzdm " //
				+ "                    FROM qh_bdlc t1 " //
				+ "                   WHERE t1.qhzt = 0 AND t1.bzdm <> 9999 " //
				+ "                GROUP BY t1.gsdm, t1.bdbh, t1.bddm) t1) t1 " //
				+ " WHERE cnt = 0 " //
		;
		List<QH_BDLC> list = new ArrayList<QH_BDLC>();

		QH_BDLC e;

		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			e = new QH_BDLC();
			e.setGSDM(rst.getString("GSDM"));
			e.setBDDM(rst.getString("BDDM"));
			e.setBDBH(rst.getString("BDBH"));
			e.setBZDM(rst.getInt("BZDM"));
			list.add(e);
		}
		rst.close();
		pstm.close();

		sql = "UPDATE QH_BDLC " //
				+ "   SET QHZT='1'" //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //
		pstm = conn.prepareStatement(sql);
		for (QH_BDLC k : list) {
			pstm.setString(1, k.getGSDM());
			pstm.setString(2, k.getBDDM());
			pstm.setString(3, k.getBDBH());
			pstm.setInt(4, k.getBZDM());
			pstm.executeUpdate();
		}

		pstm.close();

		conn.commit();

		list.clear();
		list = null;
	}

	private void removeDuplication() throws Exception {
		String sql = "SELECT t1.gsdm, t1.bddm, t1.bdbh, t1.ysyh, t1.bzdm, t2.maxbzdm " //
				+ "  FROM qh_bdlc t1, " //
				+ "       (  SELECT t1.gsdm, t1.bdbh, t1.ysyh, MAX (t1.bzdm) maxbzdm " //
				+ "            FROM qh_bdlc t1 " //
				+ "           WHERE t1.qhzt < '9' AND t1.bzdm <> 9999 " //
				+ "       AND (t1.zwdm IS NULL OR NOT substr(t1.zwdm,length(t1.zwdm)-2, 3) = 'GLY' )  " //是管理員時不取消重復簽核
				+ "        GROUP BY t1.gsdm, t1.bdbh, t1.ysyh " //
				+ "          HAVING COUNT (*) > 1) t2 " //
				+ " WHERE     t1.gsdm = t2.gsdm " //
				+ "       AND t1.bdbh = t2.bdbh " //
				+ "       AND t1.ysyh = t2.ysyh " //
				+ "       AND t1.qhzt < '9' " //
				+ "       AND t1.bzdm <> t2.maxbzdm " //
				+ "       AND t1.QHLX <> 'A' " //
				+ "       AND (t1.zwdm IS NULL OR NOT substr(t1.zwdm,length(t1.zwdm)-2, 3) = 'GLY' )  " //是管理員時不取消重復簽核
		;

		List<QH_BDLC> list = new ArrayList<QH_BDLC>();

		QH_BDLC e;

		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();

		while (rst.next()) {
			e = new QH_BDLC();
			e.setGSDM(rst.getString("GSDM"));
			e.setBDDM(rst.getString("BDDM"));
			e.setBDBH(rst.getString("BDBH"));
			e.setBZDM(rst.getInt("BZDM"));
			e.setYSYH(rst.getString("YSYH"));
			e.setQHNR("取消重複簽核，到序號" + rst.getString("MAXBZDM") + "才簽");
			list.add(e);
		}
		rst.close();
		pstm.close();

		conn.setAutoCommit(false);

		sql = "UPDATE QH_BDLC " //
				+ "   SET QHZT='3', " //
				
				+ "       QHYH='SYSTEM', " //
				+ "       QHSJ=SYSDATE, " //
				+ "       QHJG='Y', " //
				+ "       QHNR=? " //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=?"; //
		pstm = conn.prepareStatement(sql);
		for (QH_BDLC k : list) {
			pstm.setString(1, k.getQHNR());
			pstm.setString(2, k.getGSDM());
			pstm.setString(3, k.getBDDM());
			pstm.setString(4, k.getBDBH());
			pstm.setInt(5, k.getBZDM());
			pstm.executeUpdate();
		}

		pstm.close();

		conn.commit();
		conn.setAutoCommit(true);

		list.clear();
		list = null;
	}

	private void sendNotification() throws Exception {

		Mailer mailer = new Mailer();
		mailer.setFrom("lum.cl@l-e-i.com");

		String sql = "SELECT YSYH FROM QH_BDLC WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND ZWDM = 'SQYH' AND QHLX = 'Z' AND QHJG = 'N' AND BDDM = 'D400' GROUP BY YSYH";

		PreparedStatement pstm = conn.prepareStatement(sql);

		String mailList, url;

		ResultSet rst;

		for (QH_BDLC e : notificatios) {

			mailList = e.getSQYH() + "@l-e-i.com";

/*
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());

			rst = pstm.executeQuery();

			while (rst.next()) {
				
				mailList = mailList + ", " + rst.getString("YSYH") + "@l-e-i.com";
			}

			rst.close();
*/
			mailer.setSubject("簽核完成通知 - " + e.getBDTX());

			url = "http://eform.l-e-i.com:3058/imes/" + e.getBDDM().toLowerCase() + "hs/"+e.getBDBH()+"" //
					+ "?juid=" + e.getYSYH() //
					+ "&GSDM=" + e.getGSDM() //
					+ "&BDDM=" + e.getBDDM() //
					+ "&BDBH=" + e.getBDBH(); //

			mailer.setMsgText(url);

			// mailList = mailList + ", lum.cl@l-e-i.com";

			mailer.setTo(mailList);

			logger.info("Send Email to:" + mailer.getTo());

			for (int i = 0; i < 3; i++) {
				try {
					//mailer.sendMail();
					break;
				} catch (Exception e2) {
					logger.error(e2.getMessage());
					Thread.sleep(i * 1000);
				}
			}
		}

		pstm.close();

		mailer = null;
	}

	private void sendD400Notification(QH_BDLC e) throws Exception {
		if(e != null && e.getBDDM().equals("D400")){

			Mailer mailer = new Mailer();
			mailer.setFrom("lum.cl@l-e-i.com");
	
			String sql = "SELECT distinct(YSYH) FROM QH_BDLC WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND (ZWDM LIKE '______GLY%' OR  QHLX = 'Z') AND QHLX <> 'A' AND BDDM = 'D400' GROUP BY YSYH";
	
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
	
			String mailList, url;
			mailList = "";
	
			ResultSet rst;
	
			rst = pstm.executeQuery();
	
			while (rst.next()) {
				if(mailList == null || mailList.equals("")){
					mailList = rst.getString("YSYH") + "@l-e-i.com";
				}else{
					mailList = mailList +","+ rst.getString("YSYH") + "@l-e-i.com";
				}
			}
			mailer.setSubject("簽核完成通知 - " + e.getBDTX());
	
			url = "http://eform.l-e-i.com:3058/imes/" + e.getBDDM().toLowerCase() + "hs/"+e.getBDBH()+"" //
					+ "?juid=" + e.getYSYH() //
					+ "&GSDM=" + e.getGSDM() //
					+ "&BDDM=" + e.getBDDM() //
					+ "&BDBH=" + e.getBDBH(); //
	
			mailer.setMsgText(url);
	
			mailer.setTo(mailList);
	
			logger.info("Send Email to:" + mailer.getTo());
	
			for (int i = 0; i < 3; i++) {
				try {
					mailer.sendMail();
					break;
				} catch (Exception e2) {
					logger.error(e2.getMessage());
					Thread.sleep(i * 1000);
				}
			}
	
	
			pstm.close();
	
			mailer = null;
		}
	}

	private void terminateQH(List<QH_BDLC> list) throws Exception {

		String sql = "UPDATE QH_BDLC SET QHZT='9', QHJG='N' " //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND QHZT < '9' AND BZDM < 9999"; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		for (QH_BDLC e : list) {

			//notificatios.add(e);

			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
			pstm.executeUpdate();

			updForm(e);

		}

		pstm.close();
	}

	private void d400glyQH() throws Exception {

		String sql = "update qh_bdlc set qhlx = 'R' where bdbh in ( "
				+" select bdbh from qh_bdlc where bdbh in "
				+" ( "
				+" select bdbh from qh_bdlc "
				+" where qhzt = 2 and BDDM='D400' and GSDM IN ('L300','L210') and QHLX= 'I' AND bzdm <> 300 "
				+" ) and QHLX= 'R' AND bzdm = 300 "
				+" and qhzt = 2 and GSDM IN ('L300','L210') and BDDM='D400' "
				+" )"; //

		String sql1 = "update qh_bdlc set qhzt = 0 "
				+" where BZDM = 300 AND BDBH IN ( "
				+" select bdbh from qh_bdlc "
				+" where qhzt = 2 and BDDM='D400' and GSDM IN ('L300','L210') and QHLX= 'R' AND bzdm <> 300 "
				+" ) and QHLX= 'R' AND bzdm = 300 "
				+" and qhzt = 2 and GSDM IN ('L300','L210') and BDDM='D400' "; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.executeUpdate();
		
		pstm.close();

		PreparedStatement pstm1 = conn.prepareStatement(sql1);
		
		pstm1.executeUpdate();

		pstm1.close();
		
	}
	
	private void updForm(QH_BDLC e) throws Exception {

		String sql = "UPDATE " + e.getBDDM() + "H SET QHYH=?, QHSJ=?, BDJG=? WHERE GSDM=? AND BDDM=? AND BDBH=?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, e.getQHYH());
		pstm.setTimestamp(2, Helper.toSqlDate(e.getQHSJ()));
		pstm.setString(3, e.getQHJG());
		pstm.setString(4, e.getGSDM());
		pstm.setString(5, e.getBDDM());
		pstm.setString(6, e.getBDBH());
		pstm.executeUpdate();

		pstm.close();
	}

	private void updFormComplete(QH_BDLC e) throws Exception {

		String sql = "UPDATE " + e.getBDDM() + "H SET BDZT='X' WHERE GSDM=? AND BDDM=? AND BDBH=?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, e.getGSDM());
		pstm.setString(2, e.getBDDM());
		pstm.setString(3, e.getBDBH());
		pstm.executeUpdate();

		pstm.close();
	}

	private void completeQH(List<QH_BDLC> list) throws Exception {

		String QHZT;

		String sql = "UPDATE QH_BDLC SET QHZT = ? " //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=? "; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		for (QH_BDLC e : list) {

			QHZT = "9";

			pstm.setString(1, QHZT);
			pstm.setString(2, e.getGSDM());
			pstm.setString(3, e.getBDDM());
			pstm.setString(4, e.getBDBH());
			pstm.setInt(5, e.getBZDM());
			pstm.executeUpdate();

			if (e.getQHLX().equals("A")) {

				if (e.getQHJG().equals("Y")){
					notificatios.add(e);
					sendD400Notification(e);
				}

				updForm(e);
			}

			if (e.getBZDM() == 9999) {

				updFormComplete(e);
			}
		}

	}

	private void htmlMail(String user) throws Exception {

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

	private void sendReminder(List<QH_BDLC> list) throws Exception {
		List<String> uids = new ArrayList<String>();
		String ysyh;
		for (QH_BDLC e : list) {
			ysyh = Helper.toUpperCase(e.getYSYH());
			if (!uids.contains(ysyh)) {
				uids.add(ysyh);
			}
		}

		for (String uid : uids) {
			htmlMail(uid);
		}

		list.clear();
		uids.clear();
	}

	@SuppressWarnings("unused")
	private void sendMail(List<QH_BDLC> list) throws Exception {

		String httpUrl = event.getServletContext().getAttribute("HttpUrl").toString();

		if (httpUrl == null || httpUrl.length() == 0) {
			httpUrl = "http://eform.l-e-i.com:3058/imes/";
		}

		Mailer mailer = new Mailer();
		mailer.setFrom("lum.cl@l-e-i.com");

		String smtpHost = event.getServletContext().getAttribute("SmtpHost").toString();
		if (smtpHost != null) {
			if (smtpHost.length() > 0) {
				mailer.setHost(smtpHost);
			}
		}

		String url;

		// D031?action=QianHe&GSDM=L400&BDDM=D031&BDBH=DT-D031-1200102

		for (QH_BDLC e : list) {
			if (!Helper.toUpperCase(e.getYSYH()).equals("")) {
				mailer.setSubject(e.getBDTX());
				// mailer.setMsgText("http://127.0.0.1:8080/iMes/qh?juid=" +
				// e.getYSYH());

				url = httpUrl + e.getBDDM().toLowerCase() + "hs/"+e.getBDBH() //
						+ "?juid=" + e.getYSYH() //
						+ "&GSDM=" + e.getGSDM() //
						+ "&BDDM=" + e.getBDDM() //
						+ "&BDBH=" + e.getBDBH(); //

				mailer.setMsgText(url);

				mailer.setTo(e.getYSYH() + "@l-e-i.com");
				logger.info("Send Email to:" + mailer.getTo());

				for (int i = 0; i < 3; i++) {
					try {
						mailer.sendMail();
						break;
					} catch (Exception e2) {
						logger.error(e2.getMessage());
						Thread.sleep(i * 1000);
					}
				}
			}// for (int i = 0; i < 3; i++)
		} // for (QH_BDLC e : list)

	}

	private void startRoute(List<QH_BDLC> list) throws Exception {

		String sql = "UPDATE QH_BDLC SET QHZT = '2', YJSJ = SYSDATE, YXSJ = SYSDATE + (YXXS / 24) " //
				+ " WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND BZDM = ? AND QHZT < '2'"; //
		PreparedStatement pstm = conn.prepareStatement(sql);

		for (QH_BDLC e : list) {
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
			pstm.setInt(4, e.getBZDM());
			pstm.executeUpdate();
		}

		pstm.close();

		sql = null;
	}

	private void createEscalation(List<QH_BDLC> list) throws Exception {
		if (list.isEmpty())
			return;

		HashMap<String, String> users = new HashMap<String, String>();

		String sql = "SELECT USERID,MANAGER FROM USERS";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();

		while (rst.next()) {

			users.put(rst.getString("USERID"), rst.getString("MANAGER"));
		}

		rst.close();
		pstm.close();

		sql = "UPDATE QH_BDLC SET QHZT = '9', QHSJ = SYSDATE, QHYH=?, QHJG='Y', QHNR=CONCAT(QHNR,?) " //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=? "; //

		pstm = conn.prepareStatement(sql);

		QH_BDLC f;

		for (QH_BDLC e : list) {

			f = (QH_BDLC) e.clone();
			f.setBZDM(e.getBZDM() + 1);
			f.setQHZT("1");
			f.setJLSJ(new Date());
			f.setYJSJ(null);
			f.setYXSJ(null);
			f.setZWDM("");
			f.setQHNR("");
			f.setYSYH(users.get(f.getYSYH()));
			f.verifyNewBZDM(conn);
			f.insertDb(conn);
			emails.add(f);

			logger.info("Escalate to:" + f.getYSYH());

			pstm.setString(1, f.getYSYH());
			pstm.setString(2, " 超過有效簽核時間, 系統自動轉簽到上一級主管-" + f.getYSYH());
			pstm.setString(3, e.getGSDM());
			pstm.setString(4, e.getBDDM());
			pstm.setString(5, e.getBDBH());
			pstm.setInt(6, e.getBZDM());
			pstm.executeUpdate();

		}

	}

	private void createDelegation(List<QH_BDLC> list) throws Exception {
		if (list.isEmpty())
			return;

		String sql = "UPDATE QH_BDLC SET QHZT = '9', QHSJ = SYSDATE, QHYH=?, QHJG='Y', QHNR=CONCAT(QHNR,?) " //
				+ " WHERE GSDM=? AND BDDM=? AND BDBH=? AND BZDM=? "; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		QH_BDLC f;
		QH_YHDL y;

		for (QH_BDLC e : list) {

			y = yhdl.get(e.getYSYH());

			f = (QH_BDLC) e.clone();
			f.setBZDM(e.getBZDM() + 1);
			f.setQHZT("1");
			f.setJLSJ(new Date());
			f.setYJSJ(null);
			f.setYXSJ(null);
			f.setZWDM(e.getZWDM()==null?"":e.getZWDM());
			f.setQHNR("");
			f.setYSYH(y.getYHTO());
			f.verifyNewBZDM(conn);
			f.insertDb(conn);
			emails.add(f);

			logger.info("Delegate to:" + f.getYSYH());

			pstm.setString(1, f.getYSYH());
			pstm.setString(2, "從 " + Helper.fmtDate(y.getYXFD(), "yyyyMMdd") + " 到 " + Helper.fmtDate(y.getYXTD(), "yyyyMMdd") + " 設置代理人為: " + f.getYSYH());
			pstm.setString(3, e.getGSDM());
			pstm.setString(4, e.getBDDM());
			pstm.setString(5, e.getBDBH());
			pstm.setInt(6, e.getBZDM());
			pstm.executeUpdate();

		}

	}

	private void processOpenQH() throws Exception {

		List<QH_BDLC> escalations = new ArrayList<QH_BDLC>();
		List<QH_BDLC> delegations = new ArrayList<QH_BDLC>();
		List<QH_BDLC> completions = new ArrayList<QH_BDLC>();
		List<QH_BDLC> terminations = new ArrayList<QH_BDLC>();

		boolean stillQH, first, terminate;

		int length;

		QH_BDLC e;

		for (List<QH_BDLC> list : hmap.values()) {

			length = list.size();

			stillQH = false;

			terminate = false;

			for (int i = 0; i < length; i++) {

				e = list.get(i);

				if (e.getQHZT().equals("1")) {

					emails.add(e);

				} else if (e.getQHZT().equals("2")) {
					/*
					 * if (e.getYXSJ().before(new Date()) && (e.getBZDM() !=
					 * 9999)) {
					 * 
					 * escalations.add(e);
					 * 
					 * }else if (yhdl.containsKey(e.getYSYH())){
					 * 
					 * delegations.add(e); }
					 */
					if (yhdl.containsKey(e.getYSYH())) {

						delegations.add(e);
					}

				} else if (e.getQHZT().equals("3")) {

					// Allow for terminations

					if (Helper.toUpperCase(e.getZFYN()).equals("Y") && Helper.toUpperCase(e.getQHJG()).equals("N") && e.getBZDM() != 9999) {

						terminations.add(e);
						terminate = true;

					} else {

						for (QH_BDLC t : list) {
							if (t.getQHZT().equals("2")) {
								stillQH = true;
							}
						}

						if (!stillQH) {
							first = false;
							for (QH_BDLC t : list) {
								if (first) {
									if (t.getQHZT().equals("0") && t.getYSYH().equals("KY.CHOU")) {
										break;
									}
									else if (t.getQHZT().equals("0") && t.getLCLX().equals("S")) {
										// System.out.println(t.getBZDM());
										break;
									}
								}

								if (t.getQHZT().equals("0")) {
									first = true;
									emails.add(t);
								}

							} // for (QH_BDLC t : list)

						} // if (!stillQH)

						completions.add(e);

					} // if (e.getZFYN().equals("Y") && e.getQHJG().equals("N"))

				} else if (e.getQHZT().equals("8")) {

				} else if (terminate && e.getBZDM() == 9999 && e.getQHZT().equals("0")) {

					emails.add(e);
				}

			}

		}

		createEscalation(escalations);
		createDelegation(delegations);
		startRoute(emails);
		terminateQH(terminations);
		completeQH(completions);

		escalations = null;
		delegations = null;
		terminations = null;
		completions = null;

	}

	private void getOpenQH() throws Exception {

		String sql, key;
		PreparedStatement pstm;
		ResultSet rst;

		List<QH_BDLC> list;

		QH_BDLC e;

		sql = "SELECT T1.*,T2.BDTX FROM QH_BDLC T1, QH_BDTX T2" //
				+ " WHERE T1.QHZT < '9' AND T1.GSDM=T2.GSDM AND T1.BDDM=T2.BDDM AND T1.BDBH=T2.BDBH" //
				+ " ORDER BY T1.GSDM,T1.BDDM,T1.BDBH,T1.BZDM"; //

		pstm = conn.prepareStatement(sql);
		rst = pstm.executeQuery();
		while (rst.next()) {

			key = rst.getString("GSDM") + "_" + rst.getString("BDDM") + "_" + rst.getString("BDBH");

			if (hmap.containsKey(key)) {

				list = hmap.get(key);

			} else {

				list = new ArrayList<QH_BDLC>();
			}

			e = new QH_BDLC();
			e.setGSDM(rst.getString("GSDM"));
			e.setBDDM(rst.getString("BDDM"));
			e.setBDBH(rst.getString("BDBH"));
			e.setLXDM(rst.getString("LXDM"));
			e.setQHZT(rst.getString("QHZT"));
			e.setBZDM(rst.getInt("BZDM"));
			e.setLCLX(rst.getString("LCLX"));
			e.setMINQ(rst.getDouble("MINQ"));
			e.setMAXQ(rst.getDouble("MAXQ"));
			e.setMINA(rst.getDouble("MINA"));
			e.setMAXA(rst.getDouble("MAXA"));
			e.setMINP(rst.getDouble("MINP"));
			e.setMAXP(rst.getDouble("MAXP"));
			e.setCURR(rst.getString("CURR"));
			e.setFTXT(rst.getString("FTXT"));
			e.setTTXT(rst.getString("TTXT"));
			e.setZWDM(rst.getString("ZWDM"));
			e.setQHLX(rst.getString("QHLX"));
			e.setYXXS(rst.getDouble("YXXS"));
			e.setZFYN(rst.getString("ZFYN"));
			e.setSQYH(rst.getString("SQYH"));
			e.setJLSJ(rst.getTimestamp("JLSJ"));
			e.setYJSJ(rst.getTimestamp("YJSJ"));
			e.setYXSJ(rst.getTimestamp("YXSJ"));
			e.setYSYH(rst.getString("YSYH"));
			e.setDLYH(rst.getString("DLYH"));
			e.setQHYH(rst.getString("QHYH"));
			e.setQHSJ(rst.getTimestamp("QHSJ"));
			e.setQHJG(rst.getString("QHJG"));
			e.setQHNR(rst.getString("QHNR"));
			e.setQHFD(rst.getString("QHFD"));
			e.setBDTX(rst.getString("BDTX"));

			list.add(e);

			hmap.put(key, list);
		}
		rst.close();
		pstm.close();

	}

	private void getDaiLiYonghu() {

		try {
			QH_YHDL e;
			String sql = "SELECT YXFD, YXTD, YHFR, YHTO, TBGP FROM IMES.QH_YHDL WHERE TO_CHAR(SYSDATE,'yyyymmdd') BETWEEN TO_CHAR(YXFD,'yyyymmdd') AND TO_CHAR(YXTD,'yyyymmdd') AND DLZT='A'";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {
				e = new QH_YHDL();
				e.setYXFD(rst.getTimestamp("YXFD"));
				e.setYXTD(rst.getTimestamp("YXTD"));
				e.setYHFR(rst.getString("YHFR"));
				e.setYHTO(rst.getString("YHTO"));
				e.setTBGP(rst.getString("TBGP"));
				yhdl.put(e.getYHFR(), e);
			}
			rst.close();
			pstm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
