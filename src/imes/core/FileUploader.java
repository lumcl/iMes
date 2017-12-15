package imes.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/FileUploader")
@MultipartConfig(fileSizeThreshold = 102400)
public class FileUploader extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);

		try {
			if (getAction(req).equals("QianHe")) {

				actionPostQianHe(req, resp);
				
			}else if (getAction(req).equals("Document")){
				
				actionPostDocument(req, resp);
				
			}else if (getAction(req).equals("DocumentA")){
				
				actionPostDocumentA(req, resp);
				
			}else if (getAction(req).equals("Attachment")){
				
				actionPostAttachment(req, resp);
				
			}else if (getAction(req).equals("AttachmentA")){
				
				actionPostAttachmentA(req, resp);
				
			}else if (getAction(req).equals("AttachmentD301R")){
				
				actionPostAttachmentD301R(req, resp);
				
			}
		} catch (Exception ex) {

			PrintWriter out = resp.getWriter();
			ex.printStackTrace();
			ex.printStackTrace(out);
			out.close();
		}
	}

	private void actionPostQianHe(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		int BZDM = Integer.parseInt(req.getParameter("BZDM"));

		String SAVE_PATH = req.getServletContext().getAttribute("UploadFilePath").toString();

		Part part = req.getPart("file");

		if (part.getSize() > 0) {

			File f = new File(File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}

			String h = part.getHeader("content-disposition").replace("\"", "");

			String suffix = "";
			if (h.contains(".")) {

				suffix = h.split("\\.")[1];
			}

			String filename = GSDM + "_" + BDDM + "_" + BDBH + "_" + Integer.toString(BZDM) + "." + suffix;

			if (filename.equals("")) {
				try{
					filename = getUid(req).replace(".", "_") + "_" + Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
				}catch(Exception e){
					filename = Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
				}
			}

			String filePath = SAVE_PATH + filename;
			part.write(filePath);
			
			Connection conn = getConnection();
			
			String sql = "UPDATE QH_BDLC SET QHFD = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND BZDM = ? AND QHZT = '2'";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, filePath);
			pstm.setString(2, GSDM);
			pstm.setString(3, BDDM);
			pstm.setString(4, BDBH);
			pstm.setInt(5, BZDM);
			
			pstm.executeUpdate();
			
			pstm.close();
			conn.close();
			
		}

        try{
			resp.sendRedirect("/iMes/" + BDDM + "?action=QianHe" //
						+ "&juid=" + getUid(req)//
						+ "&GSDM=" + GSDM //
						+ "&BDDM=" + BDDM //
						+ "&BDBH=" + BDBH); //
		}catch(Exception e){
			resp.sendRedirect("/iMes/" + BDDM + "?action=QianHe" //
					+ "&GSDM=" + GSDM //
					+ "&BDDM=" + BDDM //
					+ "&BDBH=" + BDBH); //			
		}
		
	}
	
	private void actionPostDocument(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");

		String SAVE_PATH = req.getServletContext().getAttribute("UploadFilePath").toString();

		Part part = req.getPart("file");

		if (part.getSize() > 0) {

			File f = new File(File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}

			String h = part.getHeader("content-disposition").replace("\"", "");

			String suffix = "";
			if (h.contains(".")) {

				suffix = h.split("\\.")[1];
			}

			String filename = GSDM + "_" + BDDM + "_" + BDBH + "_H"  + "." + suffix;

			if (filename.equals("")) {
				filename = getUid(req).replace(".", "_") + "_" + Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
			}

			String filePath = SAVE_PATH + filename;
			part.write(filePath);
			
			Connection conn = getConnection();
			
			String sql = "UPDATE "+BDDM+"H SET BDFD = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, filePath);
			pstm.setString(2, GSDM);
			pstm.setString(3, BDDM);
			pstm.setString(4, BDBH);
			
			pstm.executeUpdate();
			
			pstm.close();
			conn.close();
			
		}

		resp.sendRedirect("/iMes/" + BDDM + "?action=QianHe" //
					+ "&juid=" + getUid(req)//
					+ "&GSDM=" + GSDM //
					+ "&BDDM=" + BDDM //
					+ "&BDBH=" + BDBH); //
		
	}

	private void actionPostAttachment(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");

		String SAVE_PATH = req.getServletContext().getAttribute("UploadFilePath").toString();

		Part part = req.getPart("file");

		if (part.getSize() > 0) {

			File f = new File(File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}

			String h = part.getHeader("content-disposition").replace("\"", "");

			String suffix = "";
			if (h.contains(".")) {

				suffix = h.split("\\.")[1];
			}

			String filename = GSDM + "_" + BDDM + "_" + BDBH + "_H"  + "." + suffix;

			if (filename.equals("")) {
				filename = getUid(req).replace(".", "_") + "_" + Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
			}

			String filePath = SAVE_PATH + filename;
			part.write(filePath);
			
			Connection conn = getConnection();
			
			String sql = "UPDATE "+BDDM+"H SET BDFD = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, filePath);
			pstm.setString(2, GSDM);
			pstm.setString(3, BDDM);
			pstm.setString(4, BDBH);
			
			pstm.executeUpdate();
			
			pstm.close();
			conn.close();
			
		}

		resp.sendRedirect("/iMes/" + BDDM + "/EDIT" //
					+ "?juid=" + getUid(req)//
					+ "&GSDM=" + GSDM //
					+ "&BDDM=" + BDDM //
					+ "&BDBH=" + BDBH); //
		
	}

	private void actionPostDocumentA(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");

		String SAVE_PATH = req.getServletContext().getAttribute("UploadFilePath").toString();

		Part part = req.getPart("file");

		if (part.getSize() > 0) {

			File f = new File(File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}

			String h = part.getHeader("content-disposition").replace("\"", "");

			String suffix = "";
			if (h.contains(".")) {

				suffix = h.split("\\.")[1];
			}

			String filename = GSDM + "_" + BDDM + "_" + BDBH + "_H"  + "." + suffix;

			if (filename.equals("")) {
				filename = getUid(req).replace(".", "_") + "_" + Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
			}

			String filePath = SAVE_PATH + filename;
			part.write(filePath);
			
			Connection conn = getConnection();
			
			String sql = "UPDATE "+BDDM+"H SET BDFD = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, filePath);
			pstm.setString(2, GSDM);
			pstm.setString(3, BDDM);
			pstm.setString(4, BDBH);
			
			pstm.executeUpdate();
			
			pstm.close();
			conn.close();
			
		}
		String URL = (String)req.getSession().getAttribute("URL");
		if(URL==null|| "".equals(URL)){
			URL = "/iMes/" + BDDM + "?action=QianHe" 
			+ "&juid=" + getUid(req)
			+ "&GSDM=" + GSDM 
			+ "&BDDM=" + BDDM 
			+ "&BDBH=" + BDBH;
		}
		req.getSession().setAttribute("URL", null);
		resp.sendRedirect(URL); 		
	}
	
	private void actionPostAttachmentA(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");

		String SAVE_PATH = req.getServletContext().getAttribute("UploadFilePath").toString();

		Part part = req.getPart("file");

		if (part.getSize() > 0) {

			File f = new File(File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}

			String h = part.getHeader("content-disposition").replace("\"", "");

			String suffix = "";
			if (h.contains(".")) {

				suffix = h.split("\\.")[1];
			}

			String filename = GSDM + "_" + BDDM + "_" + BDBH + "_H"  + "." + suffix;

			if (filename.equals("")) {
				filename = getUid(req).replace(".", "_") + "_" + Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
			}

			String filePath = SAVE_PATH + filename;
			part.write(filePath);
			
			Connection conn = getConnection();
			
			String sql = "UPDATE "+BDDM+"H SET BDFD = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ? ";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, filePath);
			pstm.setString(2, GSDM);
			pstm.setString(3, BDDM);
			pstm.setString(4, BDBH);
			
			pstm.executeUpdate();
			
			pstm.close();
			conn.close();
			
		}

		String URL = (String)req.getSession().getAttribute("URL");
		if(URL==null|| "".equals(URL)){
			URL = "/iMes/" + BDDM + "?action=QianHe" 
			+ "&juid=" + getUid(req)
			+ "&GSDM=" + GSDM 
			+ "&BDDM=" + BDDM 
			+ "&BDBH=" + BDBH;
		}
		req.getSession().setAttribute("URL", null);
		resp.sendRedirect(URL);
	}
	
	private void actionPostAttachmentD301R(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String GSDM = req.getParameter("GSDM");
		String BDDM = req.getParameter("BDDM");
		String BDBH = req.getParameter("BDBH");
		String SEQ = req.getParameter("SEQ");

		String SAVE_PATH = req.getServletContext().getAttribute("UploadFilePath").toString();
		
		System.out.println("UploadFilePath - "+SAVE_PATH);

		Collection<Part> parts = req.getParts();
//		Part part = null;
//		if (parts.size()==1) {
//			part = parts[0];
//		}
		
		Part part = req.getPart("file");
//		//获取上传的文件集合
//         Collection<Part> parts = req.getParts();
//         //上传单个文件
//         if (parts.size()==1) {
//              //Servlet3.0将multipart/form-data的POST请求封装成Part，通过Part对上传的文件进行操作。
//             //Part part = parts[0];//从上传的文件集合中获取Part对象
//             Part part = req.getPart("file");//通过表单file控件(<input type="file" name="file">)的名字直接获取Part对象
//             //Servlet3没有提供直接获取文件名的方法,需要从请求头中解析出来
//             //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
//             String header = part.getHeader("content-disposition");
//             //获取文件名
//             //String fileName = getFileName(header);
//             
//             //把文件写到指定路径
//             part.write(SAVE_PATH+File.separator+fileName);
//             
//         }else {
//             //一次性上传多个文件
//             for (Part part : parts) {//循环处理上传的文件
//                 //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
//                 String header = part.getHeader("content-disposition");
//                 //获取文件名
//                 String fileName = getFileName(header);
//                 //把文件写到指定路径
//                 part.write(savePath+File.separator+fileName);
//             }
//         }
//         PrintWriter out = resp.getWriter();

		if (part.getSize() > 0) {

			File f = new File(File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}

			String h = part.getHeader("content-disposition").replace("\"", "");

			String suffix = "";
			if (h.contains(".")) {

				suffix = h.split("\\.")[1];
			}

			String filename = GSDM + "_" + BDDM + "_" + BDBH + "_R_"+ SEQ  + "." + suffix;

			if (filename.equals("")) {
				filename = getUid(req).replace(".", "_") + "_" + Helper.fmtDate(new Date(), "yyyyMMddHHmmss") + "." + suffix;
			}

			String filePath = SAVE_PATH + filename;
			part.write(filePath);
			
			Connection conn = getConnection();
			
			String sql = "UPDATE "+BDDM+"R SET BDFD = ? WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND SEQ = ?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, filePath);
			pstm.setString(2, GSDM);
			pstm.setString(3, BDDM);
			pstm.setString(4, BDBH);
			pstm.setString(5, SEQ);
			
			pstm.executeUpdate();
			
			pstm.close();
			conn.close();
			
		}

		String URL = (String)req.getSession().getAttribute("URL");
		if(URL==null|| "".equals(URL)){
			URL = "/iMes/" + BDDM + "?action=Edit" 
			+ "&juid=" + getUid(req)
			+ "&GSDM=" + GSDM 
			+ "&BDDM=" + BDDM 
			+ "&BDBH=" + BDBH;
		}
		req.getSession().setAttribute("URL", null);
		resp.sendRedirect(URL);
	}
}
