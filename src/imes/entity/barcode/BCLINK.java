package imes.entity.barcode;

public class BCLINK {
	private int id = 0; // id
	private String bcdnr = ""; // parent id
	private String cbcdnr = ""; // child id
	private String posnr = ""; // position
	private String ernam = ""; // enter user
	private String erdat = ""; // enter date
	private String ertim = ""; // enter time

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBcdnr() {
		return bcdnr;
	}

	public void setBcdnr(String bcdnr) {
		this.bcdnr = bcdnr;
	}

	public String getCbcdnr() {
		return cbcdnr;
	}

	public void setCbcdnr(String cbcdnr) {
		this.cbcdnr = cbcdnr;
	}

	public String getPosnr() {
		return posnr;
	}

	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}

	public String getErnam() {
		return ernam;
	}

	public void setErnam(String ernam) {
		this.ernam = ernam;
	}

	public String getErdat() {
		return erdat;
	}

	public void setErdat(String erdat) {
		this.erdat = erdat;
	}

	public String getErtim() {
		return ertim;
	}

	public void setErtim(String ertim) {
		this.ertim = ertim;
	}

}
