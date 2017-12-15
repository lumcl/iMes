package imes.core.sap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRepository;


/**
 * Connection allows to get and execute SAP functions. The constructor expect a
 * SapSystem and will save the connection data to a file. The connection will
 * also be automatically be established.
 */

public class SapRfcConnection {
	static String SAP_SERVER = "SAP_SERVER";
	private JCoRepository repos;
	private JCoDestination dest;

	public JCoRepository getRepos() {
		return repos;
	}

	public JCoDestination getDest() {
		return dest;
	}

	public SapRfcConnection() throws NamingException {

		if (!com.sap.conn.jco.ext.Environment
				.isDestinationDataProviderRegistered())
			registerDestinationDataProvider();

		try {
			dest = JCoDestinationManager.getDestination(SAP_SERVER);
			// System.out.println("Attributes:");
			// System.out.println(dest.getAttributes());
			repos = dest.getRepository();
		} catch (JCoException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Method getFunction read a SAP Function and return it to the caller. The
	 * caller can then set parameters (import, export, tables) on this function
	 * and call later the method execute.
	 * 
	 * getFunction translates the JCo checked exceptions into a non-checked
	 * exceptions
	 */
	public JCoFunction getFunction(String functionStr) {
		JCoFunction function = null;
		try {
			function = repos.getFunction(functionStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Problem retrieving JCO.Function object.");
		}
		if (function == null) {
			throw new RuntimeException("Not possible to receive function. ");
		}

		return function;
	}

	/**
	 * Method execute will call a function. The Caller of this function has
	 * already set all required parameters of the function
	 * 
	 */
	public void execute(JCoFunction function) {
		try {
			JCoContext.begin(dest);
			function.execute(dest);

		} catch (JCoException e) {
			e.printStackTrace();
		} finally {
			try {
				JCoContext.end(dest);
			} catch (JCoException e) {
				e.printStackTrace();
			}
		}
	}

	private void registerDestinationDataProvider() throws NamingException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		SapSystem sapSystem = (SapSystem) envContext
				.lookup("bean/SapSystemFactory");
		envContext.close();
		initContext.close();
		MyDestinationDataProvider myProvider = new MyDestinationDataProvider(
				sapSystem);
		com.sap.conn.jco.ext.Environment
				.registerDestinationDataProvider(myProvider);
	}
}