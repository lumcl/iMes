package imes.core;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Mailer {
	private String to = "";
	private String from = "lum.cl@l-e-i.com";
	private String host = "172.31.1.253";
	private String filename = "";
	private String msgText = "";
	private String subject = "";

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void sendMail() throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, null);
		session.setDebug(false);
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			String[] tos = to.split(",");
			InternetAddress[] address = new InternetAddress[tos.length];
			int i = 0;
			for (String add : tos) {
				address[i++] = new InternetAddress(add);
			}
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText);

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);

			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);
	}

	public void sendHtml() throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, null);
		session.setDebug(false);
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			String[] tos = to.split(",");
			InternetAddress[] address = new InternetAddress[tos.length];
			int i = 0;
			for (String add : tos) {
				address[i++] = new InternetAddress(add);
			}
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));

			// create and fill the first message part
			//MimeBodyPart mbp1 = new MimeBodyPart();
			//mbp1.setText(msgText);

			//Multipart mp = new MimeMultipart();
			//mp.addBodyPart(mbp1);

			msg.setContent(msgText, "text/html;charset=utf-8");

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);
	}

	
	public void sendFile() {
		// create some properties and get the default Session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, null);
		session.setDebug(false);
		try {
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			String[] tos = to.split(",");
			InternetAddress[] address = new InternetAddress[tos.length];
			int i = 0;
			for (String add : tos) {
				address[i++] = new InternetAddress(add);
			}
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText);

			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			mbp2.attachFile(filename);

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			// add the Multipart to the message
			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

}
