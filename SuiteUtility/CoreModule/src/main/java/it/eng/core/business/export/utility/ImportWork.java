package it.eng.core.business.export.utility;

import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.ForwardOnlyResultSetTableFactory;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.jdbc.Work;
import org.xml.sax.InputSource;

public class ImportWork implements Work {

	private ImportInfo iinfo;

	public ImportWork(ImportInfo iinfo) {
		super();
		this.iinfo = iinfo;
	}

	public ImportInfo getIinfo() {
		return iinfo;
	}

	public void setIinfo(ImportInfo iinfo) {
		this.iinfo = iinfo;
	}

	@Override
	public void execute(Connection jdbcConnection) throws SQLException {
		try {
			// test evita la ooo ma si perde la transazione
			if (!iinfo.isTransactional())
				jdbcConnection.setAutoCommit(true);
			IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
			// IDatabaseConnection connection = new DatabaseConnection(jdbcConnection,"PUBLIC");
			// TODO vedi http://www.dbunit.org/faq.html#streaming
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY, new ForwardOnlyResultSetTableFactory());

			// // full database import
			// FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			//
			// builder.setColumnSensing(true);
			// IDataSet dataSet = builder.build(new File("c:/audit/full.xml") );
			// //IDataSet dataSet = new FlatXmlDataSet(file, true);
			IDataSetProducer producer = new FlatXmlProducer(new InputSource(iinfo.getImportFile().getAbsolutePath()));
			IDataSet dataSet = new StreamingDataSet(producer);

			DatabaseOperation.INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			throw new SQLException("ImportWork error", e);
		}

	}

}
