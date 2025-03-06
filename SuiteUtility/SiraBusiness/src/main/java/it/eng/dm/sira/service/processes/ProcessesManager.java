package it.eng.dm.sira.service.processes;

import it.eng.dm.sira.service.bean.DmtProcess;
import it.eng.spring.utility.SpringAppContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProcessesManager {

	private String dbUrl;

	private String user;

	private String password;

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public ProcessesManager() {
	}

	private Connection getConnection() throws Exception {
		Class.forName(DB_DRIVER);
		Connection connection = null;
		connection = DriverManager.getConnection(getDbUrl(), getUser(), getPassword());
		return connection;
	}

	public List<DmtProcess> selectProcessi(String data) throws Exception {
		List<DmtProcess> processi = new ArrayList<DmtProcess>();
		Connection connection = getConnection();
		Statement statement = null;
		String selectTableSQL = "select id_process, prov_ci_ty_flusso_wf,prov_ci_flusso_wf from dmt_processes where prov_ci_ty_flusso_wf is not null AND ts_fine is null and ts_ins>to_date('"
				+ data + "', 'yyyy/mm/dd')";
		try {
			statement = connection.createStatement();
			System.out.println(selectTableSQL);
			// execute insert SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				DmtProcess processo = new DmtProcess();
				processo.setDeploymentName(rs.getString("PROV_CI_TY_FLUSSO_WF"));
				processo.setInstanceId(rs.getString("PROV_CI_FLUSSO_WF"));
				processo.setIdProcess(rs.getString("ID_PROCESS"));
				processi.add(processo);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return processi;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
