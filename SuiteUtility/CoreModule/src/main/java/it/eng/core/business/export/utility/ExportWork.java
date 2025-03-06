package it.eng.core.business.export.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.ForwardOnlyResultSetTableFactory;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.jdbc.Work;

/**
 * classe che espleta l'export in base alle informazioni fornite da ExportInfo {@link ExportInfo}
 * 
 * @author Russo
 *
 */
public class ExportWork implements Work {

	private ExportInfo einfo;

	public ExportWork(ExportInfo einfo) {
		super();
		this.einfo = einfo;
	}

	public ExportInfo getEinfo() {
		return einfo;
	}

	public void setEinfo(ExportInfo einfo) {
		this.einfo = einfo;
	}

	@Override
	public void execute(Connection jdbcConnection) throws SQLException {
		try {
			// IDatabaseConnection connection = new DatabaseConnection(jdbcConnection,"PUBLIC");
			IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
			// TODO vedi http://www.dbunit.org/faq.html#streaming
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY, new ForwardOnlyResultSetTableFactory());

			ExportInfo info = getEinfo();
			//
			IDataSet exportDataSet = null;
			// connection.createDataSet();
			if (StringUtils.isNotBlank(info.getSqlFilter())) {
				QueryDataSet partialDataSet = new QueryDataSet(connection);
				partialDataSet.addTable("query", info.getSqlFilter());
				exportDataSet = partialDataSet;
			} else {
				// export all
				exportDataSet = connection.createDataSet();
				;
			}
			Set<ExportFormat> formati = info.getExportFormat();
			for (ExportFormat exportFormat : formati) {
				switch (exportFormat) {
				case XML:
					FlatXmlDataSet.write(exportDataSet, new FileOutputStream(info.getBaseFolder() + File.separator + info.getFileName()));
					break;
				case PDF:
					PdfDataSetWriter.write(exportDataSet, info.getBaseFolder(), info.getFileName(), info.getColumMapping());
					break;
				// not supported yet
				// case CSV:
				// CsvDataSetWriter.write(exportDataSet, new File(info.getBaseFolder()));
				// break;
				// case XLS:
				// XlsDataSet.write(exportDataSet, new FileOutputStream(info.getBaseFolder()+File.separator+"export.xml"));
				// break;
				// case CUSTOM:
				// IDataSetConsumer exporter=info.getE
				// DataSetProducerAdapter provider = new DataSetProducerAdapter(exportDataSet);
				// provider.setConsumer(this);
				// provider.produce();
				// break;
				default:
					break;
				}
			}

		} catch (Exception e) {
			throw new SQLException("ExportWork error", e);
		}

	}

}
