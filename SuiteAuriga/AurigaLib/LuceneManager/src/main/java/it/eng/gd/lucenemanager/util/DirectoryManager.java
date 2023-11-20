/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.jdbc.dialect.OracleDialect;

import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.gd.lucenemanager.bean.SearchCategory;
import it.eng.gd.lucenemanager.config.DbConfig;
import it.eng.gd.lucenemanager.config.LuceneIndexConfig;
import it.eng.gd.lucenemanager.config.LuceneIndexDbConfig;
import it.eng.gd.lucenemanager.config.WriterDestinationConfig;
import oracle.jdbc.pool.OracleDataSource;

/**
 * gestore della directory
 * 
 * @author jravagnan
 * 
 */
public class DirectoryManager {

	private static Logger log = Logger.getLogger(DirectoryManager.class);

	public Directory getDirectory(SearchCategory category, String idDominio, String dbName) throws Exception {
		Directory directory = null;
		WriterDestinationConfig where = (WriterDestinationConfig) LuceneSpringAppContext.getContext().getBean(WriterDestinationConfig.CONFIG_NAME);
		log.info("Gli indici verrano scritti e letti su: " + where.getDestination());
		if (where == null || StringUtils.isEmpty(where.getDestination()) || where.getDestination().equals(WriterDestinationConfig.FILE_VALUE)) {
			LuceneIndexConfig config = (LuceneIndexConfig) LuceneSpringAppContext.getContext().getBean(LuceneIndexConfig.CONFIG_NAME);
			File path = config.retrieveCorrectPath(category, idDominio, dbName);
			if (!path.exists()) {
				path.mkdir();
			}
			directory = FSDirectory.open(path);
		} else if (where.getDestination().equals(WriterDestinationConfig.DB_VALUE)) {
			DbConfig config = (DbConfig) LuceneSpringAppContext.getContext().getBean(DbConfig.CONFIG_NAME);
			LuceneIndexDbConfig indexConfig = (LuceneIndexDbConfig) LuceneSpringAppContext.getContext().getBean(LuceneIndexDbConfig.CONFIG_NAME);
			if (config.getType().equalsIgnoreCase("ORACLE")) {
				OracleDataSource dataSource = new OracleDataSource();
				dataSource.setDriverType("thin");
				dataSource.setServerName(config.getServerName());
				dataSource.setPortNumber(Integer.parseInt(config.getPortNumber()));
				dataSource.setDatabaseName(config.getDatabaseName()); // Oracle SID
				dataSource.setUser(config.getUsername());
				dataSource.setPassword(config.getPassword());
				String nomeTabella = indexConfig.retrieveCorrectTable(category, idDominio, dbName);
				directory = new EnhancedJdbcDirectory(dataSource, new OracleDialect(), nomeTabella);
				// se non esiste la tabella la creo
				if (!((EnhancedJdbcDirectory) directory).tableExists()) {
					((EnhancedJdbcDirectory) directory).create();
				}
			}
		} else {
			log.error("Tipo scrittura indici: " + where.getDestination() + " non gestita!!!");
			throw new Exception("Tipo scrittura indici: " + where.getDestination() + " non gestita!!!");
		}
		return directory;
	}

}
