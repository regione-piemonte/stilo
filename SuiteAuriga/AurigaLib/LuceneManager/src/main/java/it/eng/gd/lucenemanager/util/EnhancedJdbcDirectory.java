/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectorySettings;
import org.apache.lucene.store.jdbc.dialect.Dialect;

public class EnhancedJdbcDirectory extends JdbcDirectory {

	public EnhancedJdbcDirectory(DataSource dataSource, Dialect dialect, JdbcDirectorySettings settings, String tableName) {
		super(dataSource, dialect, settings, tableName);
	}

	public EnhancedJdbcDirectory(DataSource dataSource, Dialect dialect, String tableName) {
		super(dataSource, dialect, tableName);
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @return the string[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see org.apache.lucene.store.Directory#listAll()
	 */
	@Override
	public String[] listAll() throws IOException {
		return super.list();
	}
}
