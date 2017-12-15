package imes.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HtmlHelper {

	public static String inputRadio(String name, String value, HashMap<String, String> table) {

		List<String> list = new ArrayList<String>();

		for (String str : table.keySet()) {
			list.add(str);
		}

		Collections.sort(list, new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				return (arg0).compareTo(arg1);
			}
		});

		String radio = "";
		for (String key : list) {
			radio += "<input type='radio' id='" + name + "' name='" + name + "' value='" + key + "' class='required' " + (key.equals(value) ? AppConst.CHECKED : "")
					+ " /><span class='label-for-radio'>" + table.get(key) + "</span>";
		}

		list.clear();
		list = null;
		table.clear();
		table = null;
		return radio;
	}
}
