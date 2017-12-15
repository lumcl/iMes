package imes.core;

import imes.entity.QH_BDLC;
import imes.vo.D400VO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrinterServer
 */
@WebServlet("/PrinterServer")
public class PrinterServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrinterServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		List<D400VO> s40 = new ArrayList<D400VO>();
		for(int i=1;i<21;i++){
			D400VO hd = new D400VO();
			
			hd.setGSDM("sfsd"+i);
			hd.setBDDM("D400"+i);
			hd.setBDBH("wefrew"+i);
			hd.setBMMC("ddfsd"+i);
			hd.setSQYH("df"+i);
			
			s40.add(hd);
		}
		
		request.setAttribute("d", s40);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/form/CodePrint1.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
