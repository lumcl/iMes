package imes.core;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class IRequest {

	private HttpServletRequest req;

	public IRequest(HttpServletRequest req) {
		super();
		this.req = req;
	}

	public HashMap<String, String> getUrlMap() {

		HashMap<String, String> map = new HashMap<String, String>();

		if (req.getParameter("action") != null && !req.getParameter("action").equals("")){
			map.put("Action", req.getParameter("action"));
			return map;
		}
		
		
		String buf[] = {};
		if (req.getPathInfo() != null) {
			buf = req.getPathInfo().split("/");
		}

		if (buf.length == 0) {
			map.put("Action", "LIST");
		} else if (buf.length == 2) {
			map.put("Action", buf[1]);
		} else if (buf.length == 3) {
			map.put("Action", buf[1]);
			map.put("Id", buf[2]);
		}

		if (buf.length >= 3) {
			for (int i=2; i< buf.length; i++){
				map.put("U"+Integer.toString(i), buf[i]);
			}
		}

		return map;
	}
	
	public String getParameter(String param){
		
		if (req.getParameter(param) == null){
			return "";
		}
		
		return req.getParameter(param).trim().toUpperCase();
	}
}
