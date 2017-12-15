package imes.core.sap;

import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;


/**
 * Represents the destination to a specific SAP system. 
 * The destination is maintained via a property file
 * 
 */
public class MyDestinationDataProvider implements DestinationDataProvider {
	static String SAP_SERVER = "SAP_SERVER";
	private final Properties ABAP_AS_properties;

	public MyDestinationDataProvider(SapSystem system) {
		Properties properties = new Properties();
		properties.setProperty(DestinationDataProvider.JCO_ASHOST, system
				.getHost());
		properties.setProperty(DestinationDataProvider.JCO_SYSNR, system
				.getSystemNumber());
		properties.setProperty(DestinationDataProvider.JCO_CLIENT, system
				.getClient());
		
		properties.setProperty(DestinationDataProvider.JCO_USER, system
				.getUser());
		properties.setProperty(DestinationDataProvider.JCO_PASSWD, system
				.getPassword());
	
		ABAP_AS_properties = properties;
	}
	
	@Override
	public Properties getDestinationProperties(String system) {
		return ABAP_AS_properties;
	}

	@Override
	public void setDestinationDataEventListener(
			DestinationDataEventListener eventListener) {
	}

	@Override
	public boolean supportsEvents() {
		return false;
	}

}
