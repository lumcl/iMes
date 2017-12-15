package imes.core;

public class AppConst {

	final static public String BdztCreate = "C";
	final static public String BdztRelease = "0";
	final static public String BdztComplete = "X";

	final static public String BdjgApproved = "Y";
	final static public String BdjgRejected = "N";
	final static public String BdjgCancelled = "X";

	final static private String BdztCreateTxt = "建立中";
	final static private String BdztReleaseTxt = "簽核中";
	final static private String BdztCompleteTxt = "簽核完成";

	final static private String BdjgApprovedTxt = "核准";
	final static private String BdjgRejectedTxt = "否决";
	final static private String BdjgCancelledTxt = "取消";

	final public static String CHECKED = "checked=\"checked\"";
	final public static String SELECTED = "selected=\"selected\"";
	
	static public String getBdztText(String bdzt) {
		if (bdzt == null)
			return "";
		if (bdzt.equals(BdztCreate))
			return BdztCreateTxt;
		else if (bdzt.equals(BdztRelease))
			return BdztReleaseTxt;
		else if (bdzt.equals(BdztComplete))
			return BdztCompleteTxt;
		else
			return bdzt;
	}

	static public String getBdjgText(String bdjq) {
		if (bdjq == null)
			return "";
		if (bdjq.equals(BdjgApproved))
			return BdjgApprovedTxt;
		else if (bdjq.equals(BdjgRejected))
			return BdjgRejectedTxt;
		else if (bdjq.equals(BdjgCancelled))
			return BdjgCancelledTxt;
		else
			return bdjq;
	}
	
	static public String getSex(String sex) {
		if (sex == null)
			return "";
		return (sex.equals("M")) ? "男" : "女";
	}
}
