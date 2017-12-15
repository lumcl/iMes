package imes.test;

import imes.core.HttpController;
import imes.core.sap.SapRfcConnection;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.NamingException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.sap.TableAdapterReader;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


public class RfcReadTable extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		String OBJECTCLAS = "VERKBELEG";
		String OBJECTID = "1200010936";
		try {

			SapRfcConnection connect = new SapRfcConnection();
			JCoFunction function = connect.getFunction("RFC_READ_TABLE");
			function.getImportParameterList().setValue("QUERY_TABLE", "CDPOS");
			function.getImportParameterList().setValue("DELIMITER", "_");

			JCoTable fields = function.getTableParameterList().getTable("FIELDS");
			fields.appendRow();
			fields.setValue("FIELDNAME", "CHANGENR");
			fields.appendRow();
			fields.setValue("FIELDNAME", "TABKEY");
			fields.appendRow();
			fields.setValue("FIELDNAME", "FNAME");
			fields.appendRow();
			fields.setValue("FIELDNAME", "VALUE_OLD");
//			fields.appendRow();
//			fields.setValue("FIELDNAME", "VALUE_NEW");
			JCoTable options = function.getTableParameterList().getTable("OPTIONS");
			options.clear();
			options.appendRow();
			options.setValue("TEXT", "OBJECTCLAS EQ '" + OBJECTCLAS + "'");
			options.appendRow();
			options.setValue("TEXT", " AND OBJECTID EQ '" + OBJECTID + "'");
			options.appendRow();
			options.setValue("TEXT", " AND FNAME IN ('BMENG','EDATU')");

			connect.execute(function);

			JCoTable table = function.getTableParameterList().getTable("DATA");
			
			TableAdapterReader adapter = new TableAdapterReader(table);
			
			for (int i = 0; i < adapter.size(); i++){
				String[] fieldsValue = adapter.get("WA").split("_");
				out.println(adapter.get("WA"));
				out.println(fieldsValue[1].substring(13, 19));
				out.println(fieldsValue[1].substring(19, 23));
				out.println("<br/>");
				adapter.next();
			}
			table.clear();
			fields.clear();
			fields.appendRow();
			fields.setValue("FIELDNAME", "CHANGENR");
			fields.appendRow();
			fields.setValue("FIELDNAME", "TABKEY");
			fields.appendRow();
			fields.setValue("FIELDNAME", "FNAME");
			fields.appendRow();
			fields.setValue("FIELDNAME", "VALUE_NEW");
			
			connect.execute(function);

			table = function.getTableParameterList().getTable("DATA");
			
			adapter = new TableAdapterReader(table);
			
			for (int i = 0; i < adapter.size(); i++){
				out.println(adapter.get("WA"));
				out.println("<br/>");
				adapter.next();
			}
			
			function = null;
			table.clear();
			table = null;
			adapter = null;

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();

	}

}
