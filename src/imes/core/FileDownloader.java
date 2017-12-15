package imes.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileDownloader")
public class FileDownloader extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);

		try {

			downloadFile(req, resp);

		} catch (Exception ex) {

			PrintWriter out = resp.getWriter();
			ex.printStackTrace(out);
			out.close();
		}
	}

	private void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String filePath = req.getParameter("filePath");

		File obj = new File(filePath);
		if (!obj.exists()) {
			resp.getWriter().print("文件不存在");
			return;
		}

		int index = filePath.lastIndexOf("\\");
		String fileName = filePath.substring(index + 1);

		ServletOutputStream out = resp.getOutputStream();
		resp.setHeader("Content-disposition", "attachment;filename=" + fileName);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(filePath));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}

	}
}
