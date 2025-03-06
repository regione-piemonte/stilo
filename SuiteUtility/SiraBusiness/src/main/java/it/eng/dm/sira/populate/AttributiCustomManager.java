package it.eng.dm.sira.populate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AttributiCustomManager {

	private static final String DB_USER = "auri_owner_1";

	private static final String DB_PASSWD = "auri_owner_1";

	private static final String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=sira2-dbt-scan)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SIRADBS)))";

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public AttributiCustomManager() {
	}

	private Connection getConnection() throws Exception {
		Class.forName(DB_DRIVER);
		Connection connection = null;
		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
		return connection;
	}

	public void insertRecord(AttributiCustomIn in) throws Exception {
		Connection connection = getConnection();
		Statement statement = null;
		String attributoSuperiore = in.getAttrNameSup() == null ? "" : in.getAttrNameSup();
		String xmlReq = in.getXmlRequestWs() == null ? "" : in.getXmlRequestWs();
		String wsdl = in.getWsdlUrl() == null ? "" : in.getWsdlUrl();
		String insertTableSQL = "INSERT INTO DMT_ATTRIBUTES_DEF"
				+ "(ATTR_NAME,ATTR_LABEL,ATTR_TYPE,ATTR_NAME_SUP,ATTR_SIZE,WSDL_URL,XML_REQUEST_WS,PROV_PROPERTY,ATTR_WIDTH,NRO_RIGA_IN_SUP,NRO_ORD_IN_SUP,FLG_OBBLIG_IN_SUP) " + "VALUES" + "('"
				+ in.getAttrName() + "','" + in.getAttrLabel() + "','" + in.getAttrType() + "','" + attributoSuperiore + "',"
				+ in.getAttrSize() + ",'" 
				+ wsdl + "','" 
				+ xmlReq + "','" 
				+ in.getProvProperty() + "'," 
				+ in.getAttrWidth() + ","
				+ in.getRowNumber() + ","
				+ in.getOrder() + ","
				+ (in.getFlagMandatory() == true ? "1" : "''") + ")";
		try {
			statement = connection.createStatement();
			System.out.println(insertTableSQL);
			// execute insert SQL stetement
			statement.executeUpdate(insertTableSQL);
			System.out.println("Record is inserted into DBUSER table!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Thread.sleep(2000);
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
