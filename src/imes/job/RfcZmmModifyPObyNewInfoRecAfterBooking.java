package imes.job;

import imes.core.Helper;
import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;
import imes.dao.D089Dao;
import imes.entity.D089Z;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class RfcZmmModifyPObyNewInfoRecAfterBooking implements Runnable {

	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcZmmModifyPObyNewInfoRecAfterBooking(ServletContext context) {
		super();
		this.context = context;
	}

	public void SapRfc() {

		List<HashMap<String, Object>> datas = getDatas();

		if (datas.isEmpty() == true) {
			return;
		}

		SapRfcConnection rfc;
		JCoFunction function;

		try {
			rfc = new SapRfcConnection();

			function = rfc.getFunction("ZMM_MODIFY_PO_BY_NEW_INFO_REC");

			JCoTable impDat = function.getTableParameterList().getTable("IMP_DAT");

			HashMap<String, String> bdbhs = new HashMap<String, String>();

			for (HashMap<String, Object> map : datas) {
				impDat.appendRow();
				impDat.setValue("EKESF", "X");
				impDat.setValue("LIFNR", map.get("LIFNR"));
				impDat.setValue("MATNR", map.get("MATNR"));
				impDat.setValue("WERKS", map.get("WERKS"));
				impDat.setValue("EKORG", map.get("EKORG"));
//				if (map.get("CHGPO").equals("2")) {
//					// All Open PO - don't care up or down
//					impDat.setValue("HIPRF", "X");
//				} else if (map.get("CHGPO").equals("1")) {
//					// All Open PO but if lower price
//					impDat.setValue("HIPRF", " ");
//				}
				impDat.setValue("HIPRF", "X");
				impDat.setValue("FLONO", map.get("BDBH"));
				impDat.setValue("ERDAB", "20110101");
				bdbhs.put(map.get("BDBH").toString(), map.get("GSDM").toString() + ";" + map.get("BDDM").toString());
			}

			rfc.execute(function);

			JCoTable errMsg = function.getTableParameterList().getTable("ERR_MSG");

			TableAdapterReader reader = new TableAdapterReader(errMsg);

			List<D089Z> list = new ArrayList<D089Z>();
			D089Z e;
			String[] buf, gsdmbddm;
			String matnr, gsdm, bddm, bdbh;
			for (int i = 0; i < reader.size(); i++) {

				matnr = "";
				gsdm = "";
				bddm = "";
				bdbh = "";

				buf = reader.get("MESSAGE").split(";");
				if (buf.length >= 1) {
					bdbh = buf[0];
					if (bdbhs.containsKey(bdbh)) {
						gsdmbddm = bdbhs.get(bdbh).split(";");
						gsdm = gsdmbddm[0];
						bddm = gsdmbddm[1];
					}
				}

				if (buf.length >= 2) {
					matnr = buf[1];
				}

				e = new D089Z();
				e.setGSDM(gsdm);
				e.setBDDM(bddm);
				e.setBDBH(bdbh);
				e.setMATNR(matnr);
				e.setMSGID(reader.get("ID"));
				e.setMSGNR(reader.get("NUMBER"));
				e.setMSGTY(reader.get("TYPE"));
				e.setMSGTX(reader.get("MESSAGE"));
				list.add(e);
				System.out.println(reader.get("MESSAGE"));
				reader.next();
			}

			if (list.isEmpty() == false) {

				try {
					Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
					conn.setAutoCommit(false);
					D089Dao dao = new D089Dao(conn);
					for (D089Z z : list) {
						dao.insertD089Z(z);
					}

					dao.updateD089LAfterModifyPobyNewInfoRec(datas);

					conn.commit();
					conn.setAutoCommit(true);
					dao = null;
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (NamingException ex) {
			ex.printStackTrace();
		}

		rfc = null;
	}

	public List<HashMap<String, Object>> getDatas() {

		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

		try {
			Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

			String sql = "SELECT GSDM, BDDM, BDBH_LINE, EKORG, WERKS, LIFNR, INFNR, MATNR, CHGPO, PODAT,SUBSTR(BDBH_LINE,1,15) BDBH, SUBSTR(BDBH_LINE,17,5) SQNR " //
					+ "  FROM (  SELECT H.GSDM, H.BDDM, MAX (H.BDBH || '_' || L.SQNR) BDBH_LINE, " //
					+ "                 H.EKORG, H.WERKS, H.LIFNR, " //
					+ "                 L.INFNR, L.MATNR, L.CHGPO, " //
					+ "                 L.PODAT " //
					+ "            FROM D089H H, D089L L " //
					+ "           WHERE     H.GSDM = L.GSDM " //
					+ "                 AND H.BDDM = L.BDDM " //
					+ "                 AND H.BDBH = L.BDBH " //
					+ "                 AND H.BDJG = 'Y' " //
					+ "                 AND L.SAPCF = 'C' " //
					+ "                 AND L.DLTFG = 'N' " //
					+ "                 AND L.CHGPO <> '3' " //
					+ "                 AND H.BDYY <> 'E. Close Agrm' " //
					+ "                 AND H.LIFNR || '_' || L.MATNR IN " //
					+ "                        ('BV1918_638625-000TLN', " //
					+ "                         'BV2147_852155', " //
					+ "                         'BV19072_357006-238R', " //
					+ "                         'BV2147_359084', " //
					+ "                         'BV2147_968722', " //
					+ "                         'BV2147_464295', " //
					+ "                         'BV19027_623369-096BLN', " //
					+ "                         'BV2147_701483', " //
					+ "                         'BV2147_443077', " //
					+ "                         'BV2147_425699', " //
					+ "                         'BV1946-1_321362-108R', " //
					+ "                         'LD2141_2103000011000', " //
					+ "                         'LD2141_2103000010100', " //
					+ "                         'LD2141_2102000000600', " //
					+ "                         'BV2147_2102000000900', " //
					+ "                         'BV2556_371378-266R', " //
					+ "                         'BV2147_934720', " //
					+ "                         'BV2147_882712', " //
					+ "                         'BV2147_760811', " //
					+ "                         'BV1627_757861-B1627', " //
					+ "                         'BV1403_733644-A01', " //
					+ "                         'BV1918_461660-000TLN', " //
					+ "                         'DT19551-Z_704016-000R', " //
					+ "                         'DT19551-Z_110485-000R', " //
					+ "                         'BV1946-1_704016-108R', " //
					+ "                         'DT19551-Z_686506-000R', " //
					+ "                         'BV1946-1_200840-000R', " //
					+ "                         'BV1946-1_776572-000R', " //
					+ "                         'BV2147_377635', " //
					+ "                         'BV2147_308536', " //
					+ "                         'BV19051_336912-044TLN', " //
					+ "                         'BV2147_317993', " //
					+ "                         'BV19210_346454-056BLN', " //
					+ "                         'BV2147_151254', " //
					+ "                         'DT19176_599557-078BLN', " //
					+ "                         'BV2116_302872', " //
					+ "                         'DT19238_133728-045TLN', " //
					+ "                         'BV2147_457175', " //
					+ "                         'LD2141_457175', " //
					+ "                         'BV2147_921564', " //
					+ "                         'BV2381-1_425982-02', " //
					+ "                         'BV2116_2103000010800', " //
					+ "                         'BV1946-1_1902000490503', " //
					+ "                         'BV2147_2102000000500', " //
					+ "                         'BV19074_1930000490025', " //
					+ "                         'LD1956_1958000450126', " //
					+ "                         'LD1606_1601004840000', " //
					+ "                         'DT2315_2301818930000', " //
					+ "                         'BV2358-2_0401143092200', " //
					+ "                         'BV0308-1_2901002180000', " //
					+ "                         'BV2604_398683-0U', " //
					+ "                         'BV2147_922943', " //
					+ "                         'DT0217-Z_893838-0/40', " //
					+ "                         'DT19551-Z_165026-000R', " //
					+ "                         'BV1946-1_534560-000R', " //
					+ "                         'BV1946-1_770795-000R', " //
					+ "                         'BV2147_309494', " //
					+ "                         'BV19072_326380-238R', " //
					+ "                         'BV1946-1_569119-000R', " //
					+ "                         'DT19551-Z_574201-000R', " //
					+ "                         'DT19551-Z_500917-000R', " //
					+ "                         'BV1946-1_925748-000R', " //
					+ "                         'BV1946-1_580716-000R', " //
					+ "                         'DT19551_991457-000R', " //
					+ "                         'DT19176_900273-078BLN', " //
					+ "                         'BV2147_556645', " //
					+ "                         'BV2147_729507', " //
					+ "                         'BV19051_765935-044R', " //
					+ "                         'DT19114_945226-517TLN', " //
					+ "                         'BV2147_679879', " //
					+ "                         'BV2116_2101111110000', " //
					+ "                         'BV2116_2103000010100', " //
					+ "                         'BV19012_1914002310011', " //
					+ "                         'DT19628_2907000110006', " //
					+ "                         'BV19027_1922001230029', " //
					+ "                         'BV2147_131318', " //
					+ "                         'BV2147_526967', " //
					+ "                         'BV1918_462535-000TLN', " //
					+ "                         'DT19551-Z_731919-000R', " //
					+ "                         'DT19551-Z_707228-000R', " //
					+ "                         'BV2147_734861', " //
					+ "                         'DT19551-Z_315052-000R', " //
					+ "                         'DT19551-Z_850934-000R', " //
					+ "                         'BV1946-1_935875-000R', " //
					+ "                         'BV19323-2_587710-026TLN', " //
					+ "                         'BV2116_513636', " //
					+ "                         'BV2177_325465-B2177', " //
					+ "                         'BV2116_870706', " //
					+ "                         'BV2147_942502', " //
					+ "                         'BV2147_2102000000100', " //
					+ "                         'LD1956_1956000090207', " //
					+ "                         'LD2368_0902003900000', " //
					+ "                         'BV1976_1943738911003', " //
					+ "                         'DT1956-Z_1902000230003', " //
					+ "                         'BV2147_2101001090000', " //
					+ "                         'BV19032_1950000210115', " //
					+ "                         'BV2147_120642', " //
					+ "                         'BV2147_343951', " //
					+ "                         'BV2147_386219', " //
					+ "                         'BV1946-1_554871-000R', " //
					+ "                         'BV2147_112364-B2133', " //
					+ "                         'BV1946-1_311944-108R', " //
					+ "                         'BV1946-1_312673-000R', " //
					+ "                         'DT19551-Z_659436-000R', " //
					+ "                         'DT19551-Z_791350-000R', " //
					+ "                         'DT19551-Z_575070-000R', " //
					+ "                         'BV2147_971103', " //
					+ "                         'BV2147_874523', " //
					+ "                         'DT2129_205176', " //
					+ "                         'DT2134_229628-S', " //
					+ "                         'BV2147_391956', " //
					+ "                         'LD19321_932736-175R', " //
					+ "                         'BV2526-1_965154', " //
					+ "                         'DT2315_908940', " //
					+ "                         'BV0410_219037-0-B0410', " //
					+ "                         'BV19328_1914005430006', " //
					+ "                         'BV1946-1_1903003630203', " //
					+ "                         'DT19156-Z_2901005180200', " //
					+ "                         'BV2147_596310', " //
					+ "                         'BV2147_596973', " //
					+ "                         'BV2147_839728', " //
					+ "                         'BV2147_834653', " //
					+ "                         'BV19210_679003-056BLN', " //
					+ "                         'DT19551-Z_839582-000R', " //
					+ "                         'DT19188-Z_363987-114BSN', " //
					+ "                         'BV1946-1_569119-108R', " //
					+ "                         'BV1946-1_947946-000R', " //
					+ "                         'DT1956-Z_876003-108TLN', " //
					+ "                         'BV19027_623369-096BSK', " //
					+ "                         'BV2147_997846', " //
					+ "                         'BV2147_625752', " //
					+ "                         'BV2147_771945', " //
					+ "                         'BV19323-2_381942-026TLN', " //
					+ "                         'BV19051_377341-044TLN', " //
					+ "                         'BV2174_776955-Q', " //
					+ "                         'BV2147_870706', " //
					+ "                         'BV2116_802247', " //
					+ "                         'DT19114_719870-517TLN', " //
					+ "                         'BV2116_2101222220000', " //
					+ "                         'LD2141_2102000000300', " //
					+ "                         'BV1586_1501002250100', " //
					+ "                         'BV2147_2103000010100', " //
					+ "                         'BV0106_0104000760020', " //
					+ "                         'BV2358-2_0401143002200', " //
					+ "                         'BV2511_2512000070000', " //
					+ "                         'LD1606_1601005450100', " //
					+ "                         'BV1946-1_1903005720303', " //
					+ "                         'BV1586_227501-4/24', " //
					+ "                         'BV2147_744506', " //
					+ "                         'BV2147_797936', " //
					+ "                         'BV1902-1_LS-16808-ST', " //
					+ "                         'BV1403_976725-54', " //
					+ "                         'BV1946-1_810541-000R', " //
					+ "                         'BV2147_193046', " //
					+ "                         'BV1918_661074-000TLN', " //
					+ "                         'BV1946-1_610348-108TLN', " //
					+ "                         'BV2147_872660', " //
					+ "                         'BV2147_660639', " //
					+ "                         'DT1957_747084-182BLN', " //
					+ "                         'DT2315_680052', " //
					+ "                         'BV2147_2103000010000', " //
					+ "                         'BV2147_2103000010200', " //
					+ "                         'BV2147_2101444440000', " //
					+ "                         'BV2116_2102000000100', " //
					+ "                         'DT0217-Z_1501002390200', " //
					+ "                         'LD2141_2102000000500', " //
					+ "                         'DT2105_2103000010100', " //
					+ "                         'LD1504_1503000610000', " //
					+ "                         'DT2315_2301002680000', " //
					+ "                         'DT1603-Z_1601004970200', " //
					+ "                         'BV1508_1501000980105', " //
					+ "                         'BV19990_1930000660021', " //
					+ "                         'DT19178_1914009710009', " //
					+ "                         'BV2147_469464', " //
					+ "                         'BV2147_112801', " //
					+ "                         'BV2147_864994', " //
					+ "                         'BV2116_996564', " //
					+ "                         'LD1946-1_534560-000R', " //
					+ "                         'BV1946-1_436364-000R', " //
					+ "                         'DT19551_277886-000R', " //
					+ "                         'BV1918_738662-000TLN', " //
					+ "                         'DT19551-Z_781061-000R', " //
					+ "                         'BV1946-1_781061-000R', " //
					+ "                         'BV2147_832286', " //
					+ "                         'BV2174_120359-2-Q', " //
					+ "                         'BV2147_447528', " //
					+ "                         'DT19114_377341-517TLN', " //
					+ "                         'BV2147_424951', " //
					+ "                         'BV2147_895636', " //
					+ "                         'LD2141_2101222220000', " //
					+ "                         'BV0106_0104000630020', " //
					+ "                         'DT2627-Z_2901002570000', " //
					+ "                         'BV19328_1914004230006') " //
					+ "        GROUP BY H.GSDM, H.BDDM, H.EKORG, H.WERKS, H.LIFNR, " //
					+ "                 L.INFNR, L.MATNR, L.CHGPO, L.PODAT) " //

			;

			PreparedStatement pstm = conn.prepareStatement(sql);
			datas = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException ex) {

			ex.printStackTrace();
		}

		return datas;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("RfcZmmModifyPObyNewInfoRec Start Up");
		SapRfc();
		logger.info("RfcZmmModifyPObyNewInfoRec Shutdown");
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

}
