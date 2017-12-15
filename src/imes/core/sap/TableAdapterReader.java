package imes.core.sap;

import java.util.Date;

import com.sap.conn.jco.JCoTable;

public class TableAdapterReader {
	protected JCoTable table;

	public TableAdapterReader(JCoTable table) {
		this.table = table;
	}

	public String get(String s) {
		return table.getValue(s).toString();
	}

	public Double getDouble(String s) {
		return table.getDouble(s);
	}
	
	public Date getDate(String s){
		return (Date) table.getValue(s);
	}
	
	public Boolean getBoolean(String s) {
		String value = table.getValue(s).toString();
		return value.equals("X");
	}

	public String getMessage() {
		return table.getString("MESSAGE");
	}

	public int size() {
		return table.getNumRows();
	}

	public void next() {
		table.nextRow();
	}
}