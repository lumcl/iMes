package imes.entity.eqpsys;

import java.util.HashMap;

public class BASDAT {

	public static HashMap<String, String> uoms() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EA", "EA");
		map.put("KG", "KG");
		map.put("M", "M");
		map.put("L", "L");
		return map;
	}

	public static HashMap<String, String> cmps() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("L210", "L210 東莞領航");
		map.put("L300", "L300 東莞立德");
		map.put("L400", "L400 江蘇領先");
		return map;	
	}
	public static HashMap<String, String> facs() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("281A", "281A 東莞領航");
		map.put("381A", "381A 東莞立德");
		map.put("382A", "382A 東莞立德");
		map.put("481A", "481A 江蘇領先");
		map.put("482A", "482A 江蘇領先");
		return map;	
	}
	
	
}
