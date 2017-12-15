package imes.web.barcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.vo.LedlinkVO;

//@P@130608@01@0161@1200001770

@WebServlet({ "/BCD005", "/BCD005/*" })
public class BCD005 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/BCD005/enquiry")) {
			enquiry(req, resp);
		} else {
			render(req, resp, "/WEB-INF/pages/barcode/BCD005/list.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.resp = resp;
		super.req = req;

		if (req.getRequestURI().equals("/iMes/BCD005/enquiry")) {
			enquiry(req, resp);
		}

	}

	private void enquiry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String bcdnr_str = getParams("bcdnr");
		String bcdnr_end = getParams("bcdnr_end");

		if (bcdnr_end.equals("")) {
			bcdnr_end = bcdnr_str;
		}
		
		
		HashMap<String, LedlinkVO> mtable = new HashMap<String, LedlinkVO>();
		
		List<HashMap<String, Object>> insp = new ArrayList<HashMap<String, Object>>();
		
		List<LedlinkVO> list = new ArrayList<LedlinkVO>();
		LedlinkVO e, mvalue;
		String buf[];
		String cbcdnr;
		String mkey;
		
		String driverBarcode = "";
		
		String sql = "";
		try {

			PreparedStatement pstm, pstm1, pstm2, pstm3, pstm4, pstm5;
			ResultSet rst, rst1, rst2, rst3, rst4, rst5;
			Connection con = getConnection();
			
			sql = "select distinct bcdnr from bclink where bcdnr between ? and ? and bcdnr like '%@P%'";
			pstm5 = con.prepareStatement(sql);
			pstm5.setString(1, bcdnr_str);
			pstm5.setString(2, bcdnr_end);
			rst5 = pstm5.executeQuery();
			while (rst5.next()) {
				boolean printProduct = true;
				String bcdnr = rst5.getString("BCDNR");
				sql = "select * from bclink where bcdnr = ? and bcdnr like '%@P%'";
				pstm = con.prepareStatement(sql);
				pstm.setString(1, bcdnr);
				rst = pstm.executeQuery();
				while (rst.next()) {
					// Product
					if (printProduct) {
						printProduct = false;
						buf = bcdnr.split("@");
						e = new LedlinkVO();
						e.setLevel("1");
						e.setBcdnr(bcdnr);
						e.setDspbcdnr(bcdnr);
						e.setProduct("Finish Product");
						e.setGroup("Finish Product");
						e.setErdat(rst.getString("ERDAT"));
						e.setErtim(rst.getString("ERTIM"));
						e.setErnam(rst.getString("ERNAM"));
						
						sql = "SELECT afpo.matnr, maktx " //
								+ "  FROM sapsr3.afpo@sapp, sapsr3.makt@sapp " //
								+ " WHERE     afpo.mandt = '168' " //
								+ "       AND afpo.aufnr = ? " //
								+ "       AND makt.mandt = afpo.mandt " //
								+ "       AND makt.matnr = afpo.matnr " //
								+ "       AND makt.spras = 'M' " //
						;
						pstm4 = con.prepareStatement(sql);
						pstm4.setString(1, "00"+buf[5]);
						rst4 = pstm4.executeQuery();
						if (rst4.next()) {
							e.setProduct(rst4.getString("MAKTX"));
							e.setMatnr(rst4.getString("MATNR"));
						}
						rst4.close();
						pstm4.close();
						list.add(e);
					}
					// 2nd Level
					cbcdnr = rst.getString("cbcdnr");
					buf = cbcdnr.split("@");
					e = new LedlinkVO();
					e.setLevel("&nbsp;&nbsp;2");
					e.setBcdnr(cbcdnr);
					e.setDspbcdnr("&nbsp;&nbsp;" + cbcdnr);
					if (e.getBcdnr().startsWith("@L")) {
						e.setProduct("Lighting Engine");
						e.setGroup("Lighting Engine");
					} else if (e.getBcdnr().startsWith("@S")) {
						e.setProduct("Led Driver");
						e.setGroup("Led Driver");
						driverBarcode = e.getBcdnr();
						System.out.println(driverBarcode);
					}
					e.setErdat(rst.getString("ERDAT"));
					e.setErtim(rst.getString("ERTIM"));
					e.setErnam(rst.getString("ERNAM"));
					list.add(e);

					// Third Level
					sql = "select * from bclink where bcdnr = ?";
					pstm1 = con.prepareStatement(sql);
					pstm1.setString(1, rst.getString("cbcdnr"));
					rst1 = pstm1.executeQuery();
					while (rst1.next()) {
						cbcdnr = rst1.getString("cbcdnr");
						if (cbcdnr.startsWith("@M") || cbcdnr.startsWith("@F")) {
							buf = cbcdnr.split("@");
							e = new LedlinkVO();
							e.setLevel("&nbsp;&nbsp;&nbsp;&nbsp;3");
							e.setBcdnr(cbcdnr);
							e.setDspbcdnr("&nbsp;&nbsp;&nbsp;&nbsp;" + cbcdnr);
							e.setProduct("MPCB");
							e.setGroup("MPCB");
							e.setErdat(rst1.getString("ERDAT"));
							e.setErtim(rst1.getString("ERTIM"));
							e.setErnam(rst1.getString("ERNAM"));

							sql = "select mtype,mev,mtcp from bcinsp where bcdnr = ?";
							pstm3 = con.prepareStatement(sql);
							pstm3.setString(1, cbcdnr);
							rst3 = pstm3.executeQuery();
							if (rst3.next()) {
								e.setMtype(rst3.getString("MTYPE"));
								e.setMev(rst3.getString("MEV"));
								e.setMtcp(rst3.getString("MTCP"));
							}
							rst3.close();
							pstm3.close();

							list.add(e);

							// Fourth Level
							sql = "select * from bclink where bcdnr = ?";
							pstm2 = con.prepareStatement(sql);
							pstm2.setString(1, cbcdnr);
							rst2 = pstm2.executeQuery();
							while (rst2.next()) {
								cbcdnr = rst2.getString("cbcdnr");
								if (cbcdnr.startsWith("@D")) {
									buf = cbcdnr.split("@");
									e = new LedlinkVO();
									e.setLevel("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4");
									e.setBcdnr(cbcdnr);
									e.setDspbcdnr("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + cbcdnr);
									e.setProduct("LED");
									e.setGroup("LED");
									e.setErdat(rst2.getString("ERDAT"));
									e.setErtim(rst2.getString("ERTIM"));
									e.setErnam(rst2.getString("ERNAM"));
									e.setMatnr(buf[2].replaceFirst("^0+(?!$)", ""));
									mkey = buf[2].replaceFirst("^0+(?!$)", "") + "_" + buf[3];

									if (mtable.containsKey(mkey)) {
										mvalue = mtable.get(mkey);
										e.setProduct(mvalue.getProduct());
										e.setLifnr(mvalue.getLifnr());
										e.setSortl(mvalue.getSortl());
									} else {
										sql = "SELECT mseg.ebeln, mseg.lifnr, lfa1.sortl, maktx " //
												+ "  FROM sapsr3.mseg@sapp, sapsr3.lfa1@sapp, sapsr3.makt@sapp " //
												+ " WHERE     mseg.mandt = '168' " //
												+ "       AND mseg.matnr = ? " //
												+ "       AND mseg.charg = ? " //
												+ "       AND mseg.bwart = '101' " //
												+ "       AND lfa1.mandt = mseg.mandt " //
												+ "       AND lfa1.lifnr = mseg.lifnr " //
												+ "       AND makt.mandt = mseg.mandt " //
												+ "       AND makt.matnr = mseg.matnr " //
												+ "       AND makt.spras = 'M' " //
												+ "       AND ROWNUM = 1";
										pstm4 = con.prepareStatement(sql);
										pstm4.setString(1, buf[2].replaceFirst("^0+(?!$)", ""));
										pstm4.setString(2, buf[3]);
										rst4 = pstm4.executeQuery();
										if (rst4.next()) {
											e.setProduct(rst4.getString("MAKTX"));
											e.setLifnr(rst4.getString("LIFNR"));
											e.setSortl(rst4.getString("SORTL"));
											mtable.put(mkey, e);
										}
										rst4.close();
										pstm4.close();
									}
									list.add(e);
								}
							}
							rst2.close();
							pstm2.close();
						}

					}
					rst1.close();
					pstm1.close();
				}
				rst.close();
				pstm.close();
			}
			rst5.close();
			pstm5.close();
			if (driverBarcode.contains("@S")) {
				sql = "select * from bcinsp where bcdnr = ?";
				pstm = con.prepareStatement(sql);
				pstm.setString(1, driverBarcode);
				insp = Helper.resultSetToArrayList(pstm.executeQuery());
				pstm.close();
			}
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		try {
			output2csv(list);
			System.out.println("I outputed in Csv");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		req.setAttribute("insp", insp);
		req.setAttribute("list", list);
		render(req, resp, "/WEB-INF/pages/barcode/BCD005/list.jsp");
	}
	
	private void output2csv(List<LedlinkVO> list) throws Exception {
		BufferedWriter out = new BufferedWriter(new FileWriter("\\report\\bcd005.txt"));
		String separator = "|";
		out.write("層次");
		out.write(separator);
		out.write("條碼");
		out.write(separator);
		out.write("說明");
		out.write(separator);
		out.write("MT");
		out.write(separator);
		out.write("MEV");
		out.write(separator);
		out.write("MTCP");
		out.write(separator);
		out.write("供應商");
		out.write(separator);
		out.write("名稱");
		out.write(separator);
		out.write("輸入日期");
		out.write(separator);
		out.write("輸入時間");
		out.write(separator);
		out.write("作業人員");
		out.write(separator);
		out.write("料號");
		out.write(separator);
		
		out.newLine();
		
		for(LedlinkVO e : list) {
			out.write(e.getLevel());
			out.write(separator);
			out.write(e.getDspbcdnr());
			out.write(separator);
			out.write(e.getProduct());
			out.write(separator);
			out.write(e.getMtype());
			out.write(separator);
			out.write(e.getMev());
			out.write(separator);
			out.write(e.getMtcp());
			out.write(separator);
			out.write(e.getLifnr());
			out.write(separator);
			out.write(e.getSortl());
			out.write(separator);
			out.write(e.getErdat());
			out.write(separator);
			out.write(e.getErdat());
			out.write(separator);
			out.write(e.getErtim());
			out.write(separator);
			out.write(e.getMatnr());
			out.write(separator);
			out.newLine();
			out.flush();
		}
		out.close();
	}
	
}
