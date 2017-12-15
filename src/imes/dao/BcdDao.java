package imes.dao;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class BcdDao extends BaseDao {
	
	public BcdDao(HttpServletRequest req) {
		try {
			this.con = ((DataSource) req.getServletContext().getAttribute("imesds")).getConnection();
			this.req = req;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
