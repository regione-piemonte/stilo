package it.eng.dm.sira.service.util;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.spring.utility.SpringAppContext;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

public class ConnectionUtil {

	private static Logger logger = Logger.getLogger(ConnectionUtil.class);

	public static Connection getConnection() throws SiraBusinessException {
		Connection conn = null;
		try {
			conn = askConnection();
		} catch (Exception e) {
			logger.error("Impossibile ottenere la connessione: ", e);
			throw new SiraBusinessException("Impossibile ottenere la connessione: ", e);
		}
		return conn;
	}

	private static Connection askConnection() throws Exception {
		ConnectionConfig config = (ConnectionConfig) SpringAppContext.getContext().getBean("connectionConfig");
		Class.forName(config.getDbDriver());
		Connection connection = null;
		connection = DriverManager.getConnection(config.getDbUrl(), config.getUser(), config.getPassword());
		return connection;
	}

}
