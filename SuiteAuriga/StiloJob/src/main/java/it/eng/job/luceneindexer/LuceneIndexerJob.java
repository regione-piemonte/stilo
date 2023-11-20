/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.IndexWriter;
import org.springframework.context.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import it.eng.dbpoolmanager.DBPoolManager;
import it.eng.dbpoolmanager.spring.FactorySpringDatasource;
import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.gd.lucenemanager.bean.SearchCategory;
import it.eng.gd.lucenemanager.service.LuceneService;
import it.eng.job.exception.AurigaJobException;
import it.eng.job.luceneindexer.model.DataToIndex;
import it.eng.job.luceneindexer.utils.JobUtilities;
import it.eng.storage.StorageRetriever;
import it.eng.utility.FileUtil;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

@Job(type = "LuceneIndexerJob")
@Named
public class LuceneIndexerJob extends AbstractJob<String> {

	private static final String ERRORE_CHIUSURA_INDEX_WRITER = "Errore chiusura indexWriter";

	private static final String ID_PIANO_CLASSIF = "ID_PIANO_CLASSIF";

	private static final String ID_ORGANIGRAMMA = "ID_ORGANIGRAMMA";

	private static final String ID_SP_AOO = "ID_SP_AOO";

	private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static final String _ID_FOLDER_PREFIX = "F";

	public static final String _ID_DOC_PREFIX = "D";

	public static final String _VERSION_SEP = "V";

	public static final String _DELETED_DOC = "#DELETED";

	public static final String _FILE_DOC = "#FILE";

	public static final String _SEPARATOR = "|*|";

	public static final String _STATO_DA_ESEGUIRE = "I";

	public static final String _STATO_IN_ESECUZIONE = "E";

	public static final String _STATO_ESEGUITA = "R";

	public static final String _STATO_IN_ATTESA = "W";

	public static final String _STATO_ERRORE = "X";

	public static final String _STATO_ELIMINATA = "D";

	public static final String _FIELD_NAME_ID_UD = "ID_UD";

	public static final String _MAX_ARCHIEVE_NUM_FILES = "maxFileInArchivio";

	private static final String OCR_OK = "OCR|*|";

	private static final String OCR_INPUT = "OCR_INPUT";

	private static final String OCR_OUTPUT = "OCR_OUTPUT";

	private String numBlocchi = "300";

	private String maxRecord = "500";

	private String tryNum = "5";

	private LuceneService service;

	private IndexWriter indexWriter;

	@Override
	public List<String> load() {
		// DenisBragato
		log.info("load");
		LuceneSpringAppContext.setContext(SpringHelper.getLuceneApplicationContext());
		ApplicationContext context = SpringHelper.getMainApplicationContext();
		// Istanzio il DBPoolManager
		try {
			FactorySpringDatasource.setAppContext(context);
		} catch (Exception e) {
			log.error("Exception: "+e.getMessage());
		}
		List<String> res = new ArrayList<>();
		if (getAttributes() == null) {
			return res;
		}
		// ciclo per tutti gli handler configurati
		for (int i = 1; i <= Integer.MAX_VALUE; i++) {
			// cerco la property iEsima
			String alias = (String) getAttribute("tblInfoXIndexerConnAlias." + i);
			// se nomeClasse vuoto, allora interrompe il ciclo
			if ((alias == null) || (alias.trim().equals(""))) {
				break;
			}
			// metto in lista
			res.add(alias);
		}
		return res;
	}

	@Override
	public void execute(String connAlias) {
		Connection con = null;
		try {
			// recupero della connessione
			String dbPgs = (String) getAttribute("dbPgs");
			String ulrPgs = (String) getAttribute("ulrPgs");
			String userPgs = (String) getAttribute("userPgs");
			String passwordPgs = (String) getAttribute("passwordPgs");
			if (dbPgs.equals("ALIAS_DEPOSITO"))
			{	
			 con = DriverManager.getConnection(ulrPgs, userPgs, passwordPgs);
			}
			else
			{	
			 con = DBPoolManager.createDBPoolManagerConnection(connAlias);
			}			
			
			log.debug("Recupero connessione");
			// fallita connessione al db
			if (con == null) {
				throw new Exception("Impossibile connettersi al database");
			}
			// autocommit
			con.setAutoCommit(false);

			// istanzio il version handler free extract per dopo
			log.debug("Recupero categoria e tabella di lettura");
			String categoria = (String) getAttribute("LuceneIndexingCategory");
			String tableName = (String) getAttribute("tblInfoXIndexer");
			String numeroBlocchi = (String) getAttribute("numeroBlocchi");
			String maxRecordToLoad = (String) getAttribute("maxRecordToLoad");
			String tryNumber = (String) getAttribute("tryNumber");
			if (numeroBlocchi != null) {
				numBlocchi = numeroBlocchi;
			}
			if (maxRecordToLoad != null) {
				maxRecord = maxRecordToLoad;
			}
			if (tryNumber != null) {
				tryNum = tryNumber;
			}
			// ricavo la lista dei MIME type corrispondenti ai formati
			// reperisco il nome dello schema
			log.info("Inizio indicizzazione Lucene");
			if (dbPgs.equals("ALIAS_DEPOSITO"))
			{	
			  doIndexingPostgres(con, tableName, categoria);
			}
			else
			{	
			  doIndexing(con, tableName, categoria);
			}
			
			log.info("Fine indicizzazione Lucene");
		} catch (Exception e) {
			log.error("Eccezione execute LuceneIndexerJob", e);
		} finally {
			try {
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception ex) {
				log.warn("Eccezione chiusura connessione db", ex);
			}
		}
	}

	private String getSchemaName(Connection con) throws SQLException {
		java.sql.CallableStatement stmt = null;
		String ret = null;
		try {
			String sql = "{call ? := DMPK_UTILITY.GetConnSchema(?)}";
			stmt = con.prepareCall(sql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			stmt.execute();
			int resultVal = stmt.getInt(1);
			if (resultVal == 1) {
				ret = stmt.getString(2);
			}
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				log.warn("Eccezione chiusura statement", e);
			}
		}
		return ret;
	}

	/**
	 * Metodo che effettua il recupero da database dei metadati o dei file da indicizzare e ne effettua l'indicizzazione
	 * 
	 * @param connection
	 * @param tabella
	 * @param categoria
	 * @param condizioneStatoInErrore
	 * @throws Exception
	 */

	private void indexByState(Connection connection, String tabella, String categoria, Boolean condizioneStatoInErrore) throws Exception {

		PreparedStatement statement = null;
		PreparedStatement statementUpdate = null;
		ResultSet resultSet = null;
		StringBuilder query = null;
		StringBuilder queryUpdate = null;

		try {

			String categoriaCondition = "";
			if (StringUtils.isNotEmpty(categoria)) {
				categoriaCondition = "AND indici.CATEGORIA = ? ";
			}

			query = new StringBuilder("SELECT indici.* FROM " + tabella + " indici WHERE ");

			// differenzio la query in base allo stato
			if (condizioneStatoInErrore) {
				query.append("indici.STATO IN ('E', 'X') ");
			} else {
				query.append("indici.STATO IS NULL ");
			}

			query.append("AND NVL(indici.TRY#, 0) < ? " + categoriaCondition + " AND ROWNUM < ?");

			if (SearchCategory.REP_DOC.getValore().equals(categoria)) {
				query.append(" ORDER BY indici.ID_SP_AOO");
			}

			List<DataToIndex> listaIndici = new LinkedList<>();

			try {
				Integer index = 1;
				statement = connection.prepareStatement(query.toString());
				statement.setString(index++, tryNum);
				if (StringUtils.isNotEmpty(categoria)) {
					statement.setString(index++, categoria);
				}
				statement.setString(index++, maxRecord);

				log.debug("Query estrazione record da indicizzare: " + query);
				resultSet = statement.executeQuery();
				creaListaRecord(resultSet, listaIndici);
			} catch (Exception e) {
				log.error("Eccezione estrazione record da indicizzare", e);
				throw e;
			} finally {
				if (statement != null) {
					try {
						statement.close();
						statement = null;
					} catch (SQLException ignore) {
						log.error("Eccezione chiusura statement", ignore);
					}
				}
				if (resultSet != null) {
					try {
						resultSet.close();
						resultSet = null;
					} catch (SQLException ignore) {
						log.error("Eccezione chiusura resultSet", ignore);
					}
				}
			}

			Map<String, LinkedList<DataToIndex>> valuesToProcess = partition(listaIndici);
			Set<String> chiaviDaProcessare = valuesToProcess.keySet();
			String note = null;
			int recordCount = 0;
			String dominio = null;

			for (String chiave : chiaviDaProcessare) {
				List<DataToIndex> valori = valuesToProcess.get(chiave);
				Map<String, Object> attributiIndicizzabili = new HashMap<>();
				Map<String, Object> attributiNonIndicizzabili = new HashMap<>();

				// logiche per distinguere tra attributi indicizzabili e non

				for (DataToIndex data : valori) {
					if (data.getAttrName().equals(ID_SP_AOO) || data.getAttrName().equals(ID_ORGANIGRAMMA) || data.getAttrName().equals(ID_PIANO_CLASSIF)) {
						attributiNonIndicizzabili.put(data.getAttrName(), data.getAttrValue());
					} else {
						attributiIndicizzabili.put(data.getAttrName(), data.getAttrValue());
					}
					if (SearchCategory.REP_DOC_FILE.getValore().equals(categoria) && _FILE_DOC.equals(data.getAttrName())) {
						note = data.getNote();
					}
				}
				// aggiorno lo stato del record e il numero di tentativi
				try {
					queryUpdate = new StringBuilder(
							"UPDATE " + tabella + " indici SET indici.STATO = ?, " + "TRY# = NVL(TRY#, 0) + 1, TS_LAST_TRY = SYSDATE WHERE ");

					// differenzio la query in base allo stato
					if (condizioneStatoInErrore) {
						queryUpdate.append("indici.STATO IN ('E', 'X') ");
					} else {
						queryUpdate.append("indici.STATO IS NULL ");
					}

					queryUpdate.append("AND indici.CI_OBJ = ?");

					statementUpdate = connection.prepareStatement(queryUpdate.toString());
					statementUpdate.setString(1, _STATO_IN_ESECUZIONE);
					statementUpdate.setString(2, chiave);
					log.debug("Aggiornamento stato esecuzione: " + query);
					statementUpdate.executeQuery();

				} catch (Exception e) {
					log.error("Eccezione aggiornamento stato esecuzione", e);
					throw e;
				} finally {
					try {
						if (statementUpdate != null) {
							statementUpdate.close();
							statementUpdate = null;
						}
					} catch (SQLException ignore) {
						log.error("Eccezione chiusura statement aggiornamento", ignore);
					}
				}

				if (SearchCategory.REP_DOC.getValore().equals(categoria) && (dominio == null || !dominio.equals(valori.get(0).getDominio()))) {
					log.debug("Reset index writer");
					resetIndexWriter(connection, valori.get(0).getDominio(), categoria);
				}

				dominio = valori.get(0).getDominio();

				LuceneIndexerAction lia = new LuceneIndexerAction(chiave, valori.get(0).getTsIns().toString(), dominio, attributiIndicizzabili,
						attributiNonIndicizzabili, note, valori.get(0).getCategoria());
				// eseguo la vera e propria indicizzazione
				indexLuceneObject(lia, connection, tabella, service, indexWriter);
				recordCount++;
				if (numBlocchi != null && !SearchCategory.REP_DOC_FILE.equals(categoria) && recordCount == Integer.valueOf(numBlocchi)) {
					log.info("Raggiunto limite per il blocco");
					log.info("Commit in database e indexwriter");
					indexWriter.commit();
					connection.commit();
					recordCount = 0;
				}

			}

			log.debug("Commit finale in database e indexwriter");
			if (chiaviDaProcessare != null && !chiaviDaProcessare.isEmpty()) {
				indexWriter.commit();
			}
			connection.commit();
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}
			log.error("Eccezione indicizzazione", e);
			throw e;
		} finally {
			// SE REP DOC ALLA FINE DEVO ASSOLUTAMENTE CHIUDERE L'ULTIMO INDICE
			if (SearchCategory.REP_DOC.getValore().equals(categoria)) {
				try {
					indexWriter.close();
				} catch (Exception e) {
					log.warn(ERRORE_CHIUSURA_INDEX_WRITER, e);
				}
			}
		}

	}
	private void indexByStatePostgres(Connection connection, String tabella, String categoria, Boolean condizioneStatoInErrore) throws Exception {

		PreparedStatement statement = null;
		PreparedStatement statementUpdate = null;
		ResultSet resultSet = null;
		StringBuilder query = null;
		StringBuilder queryUpdate = null;

		try {

			String categoriaCondition = "";
			if (StringUtils.isNotEmpty(categoria)) {
				categoriaCondition = "AND indici.CATEGORIA = ? ";
			}

			query = new StringBuilder("SELECT indici.* FROM " + tabella + " indici WHERE ");

			// differenzio la query in base allo stato
			if (condizioneStatoInErrore) {
				query.append("indici.STATO IN ('E', 'X') ");
			} else {
				query.append("indici.STATO IS NULL ");
			}
			
			query.append("AND coalesce(indici.TRY, null, 0)  < ? " + categoriaCondition + " LIMIT ?");

			List<DataToIndex> listaIndici = new LinkedList<>();

			try {
				Integer index = 1;
				statement = connection.prepareStatement(query.toString());
				//statement.setString(index++, tryNum);
				statement.setInt(index++, Integer.parseInt(tryNum));
				if (StringUtils.isNotEmpty(categoria)) {
					statement.setString(index++, categoria);
				}
				//statement.setString(index++, maxRecord);
				statement.setInt(index++, Integer.parseInt(maxRecord));
				log.debug("Query estrazione record da indicizzare: " + query);
				resultSet = statement.executeQuery();
				creaListaRecord(resultSet, listaIndici);
			} catch (Exception e) {
				log.error("Eccezione estrazione record da indicizzare", e);
				throw e;
			} finally {
				if (statement != null) {
					try {
						statement.close();
						statement = null;
					} catch (SQLException ignore) {
						log.error("Eccezione chiusura statement", ignore);
					}
				}
				if (resultSet != null) {
					try {
						resultSet.close();
						resultSet = null;
					} catch (SQLException ignore) {
						log.error("Eccezione chiusura resultSet", ignore);
					}
				}
			}

			Map<String, LinkedList<DataToIndex>> valuesToProcess = partition(listaIndici);
			Set<String> chiaviDaProcessare = valuesToProcess.keySet();
			String note = null;
			int recordCount = 0;
			String dominio = null;

			for (String chiave : chiaviDaProcessare) {
				List<DataToIndex> valori = valuesToProcess.get(chiave);
				Map<String, Object> attributiIndicizzabili = new HashMap<>();
				Map<String, Object> attributiNonIndicizzabili = new HashMap<>();

				// logiche per distinguere tra attributi indicizzabili e non

				for (DataToIndex data : valori) {
					if (data.getAttrName().equals(ID_SP_AOO) || data.getAttrName().equals(ID_ORGANIGRAMMA) || data.getAttrName().equals(ID_PIANO_CLASSIF)) {
						attributiNonIndicizzabili.put(data.getAttrName(), data.getAttrValue());
					} else {
						attributiIndicizzabili.put(data.getAttrName(), data.getAttrValue());
					}
					if (SearchCategory.REP_DOC_FILE.getValore().equals(categoria) && _FILE_DOC.equals(data.getAttrName())) {
						note = data.getNote();
					}
				}
				// aggiorno lo stato del record e il numero di tentativi
				try {
					queryUpdate = new StringBuilder(
							"UPDATE " + tabella + " SET STATO = ?, " + "TRY = coalesce(TRY, null, 0) + 1, TS_LAST_TRY = current_date WHERE ");

					// differenzio la query in base allo stato
					if (condizioneStatoInErrore) {
						queryUpdate.append("STATO IN ('E', 'X') ");
					} else {
						queryUpdate.append("STATO IS NULL ");
					}

					queryUpdate.append("AND CI_OBJ = ?");

					statementUpdate = connection.prepareStatement(queryUpdate.toString());
					statementUpdate.setString(1, _STATO_IN_ESECUZIONE);
					statementUpdate.setString(2, chiave);
					log.debug("Aggiornamento stato esecuzione: " + query);
					statementUpdate.executeUpdate();

				} catch (Exception e) {
					log.error("Eccezione aggiornamento stato esecuzione", e);
					throw e;
				} finally {
					try {
						if (statementUpdate != null) {
							statementUpdate.close();
							statementUpdate = null;
						}
					} catch (SQLException ignore) {
						log.error("Eccezione chiusura statement aggiornamento", ignore);
					}
				}

				if (SearchCategory.REP_DOC.getValore().equals(categoria) && (dominio == null || !dominio.equals(valori.get(0).getDominio()))) {
					log.debug("Reset index writer");
					resetIndexWriterPostgress(connection, valori.get(0).getDominio(), categoria);
				}

				dominio = valori.get(0).getDominio();

				LuceneIndexerAction lia = new LuceneIndexerAction(chiave, valori.get(0).getTsIns().toString(), dominio, attributiIndicizzabili,
						attributiNonIndicizzabili, note, valori.get(0).getCategoria());
				// eseguo la vera e propria indicizzazione
				indexLuceneObject(lia, connection, tabella, service, indexWriter);
				recordCount++;
				if (numBlocchi != null && !SearchCategory.REP_DOC_FILE.equals(categoria) && recordCount == Integer.valueOf(numBlocchi)) {
					log.info("Raggiunto limite per il blocco");
					log.info("Commit in database e indexwriter");
					indexWriter.commit();
					connection.commit();
					recordCount = 0;
				}

			}

			log.debug("Commit finale in database e indexwriter");
			if (chiaviDaProcessare != null && !chiaviDaProcessare.isEmpty()) {
				indexWriter.commit();
			}
			connection.commit();
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}
			log.error("Eccezione indicizzazione", e);
			throw e;
		} finally {
			// SE REP DOC ALLA FINE DEVO ASSOLUTAMENTE CHIUDERE L'ULTIMO INDICE
			if (SearchCategory.REP_DOC.getValore().equals(categoria)) {
				try {
					indexWriter.close();
				} catch (Exception e) {
					log.warn(ERRORE_CHIUSURA_INDEX_WRITER, e);
				}
			}
		}

	}


	/**
	 * ricava dalla tabella d'appoggio tutti i valori utili a indicizzare il nuovo file
	 * 
	 * @param ciObj
	 * @param tsInd
	 * @param tabella
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	private void doIndexing(Connection connection, String tabella, String categoria) throws Exception {

		service = (LuceneService) LuceneSpringAppContext.getContext().getBean("luceneService");

		service.setCategory(SearchCategory.valueOf(categoria));
		indexWriter = service.getIndexWriter();

		try {
			String tipo = "metadati";
			if (SearchCategory.REP_DOC_FILE.equals(categoria)) {
				tipo = "file";
			}
			// PRIMA PROCESSO I METADATI MAI PROCESSATI
			log.info("Inizio indicizzazione " + tipo + "  metadati mai processati");
			indexByState(connection, tabella, categoria, false);
			log.info("Fine indicizzazione " + tipo + " metadati mai processati");
			// POI PROCESSO I METADATI IN ERRORE
			log.info("Inizio indicizzazione " + tipo + " metadati in errore");
			indexByState(connection, tabella, categoria, true);
			log.info("Fine indicizzazione " + tipo + " metadati in errore");
		} catch (Exception e) {
			log.error("Eccezione indicizzazione", e);
			throw e;
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				log.warn(ERRORE_CHIUSURA_INDEX_WRITER, e);
			}
		}

	}
	
	private void doIndexingPostgres(Connection connection, String tabella, String categoria) throws Exception {

		service = (LuceneService) LuceneSpringAppContext.getContext().getBean("luceneService");

		service.setCategory(SearchCategory.valueOf(categoria));
		indexWriter = service.getIndexWriter();

		try {
			String tipo = "metadati";
			if (SearchCategory.REP_DOC_FILE.equals(categoria)) {
				tipo = "file";
			}
			// PRIMA PROCESSO I METADATI MAI PROCESSATI
			log.info("Inizio indicizzazione " + tipo + "  metadati mai processati");
			indexByStatePostgres(connection, tabella, categoria, false);
			log.info("Fine indicizzazione " + tipo + " metadati mai processati");
			// POI PROCESSO I METADATI IN ERRORE
			log.info("Inizio indicizzazione " + tipo + " metadati in errore");
			indexByStatePostgres(connection, tabella, categoria, true);
			log.info("Fine indicizzazione " + tipo + " metadati in errore");
		} catch (Exception e) {
			log.error("Eccezione indicizzazione", e);
			throw e;
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				log.warn(ERRORE_CHIUSURA_INDEX_WRITER, e);
			}
		}

	}

	/**
	 * @param resultSet
	 * @param listaIndici
	 * @throws SQLException
	 */
	private void creaListaRecord(ResultSet resultSet, List<DataToIndex> listaIndici) throws SQLException {
		while (resultSet.next()) {
			DataToIndex record = new DataToIndex();
			record.setCiObj(resultSet.getString("CI_OBJ"));
			record.setAttrName(resultSet.getString("ATTR_NAME"));
			record.setAttrValue(resultSet.getString("ATTR_VALUE"));
			record.setCategoria(resultSet.getString("CATEGORIA"));
			record.setNote(resultSet.getString("NOTE"));
			record.setTsIns(resultSet.getTimestamp("TS_INS"));
			record.setDominio(resultSet.getString(ID_SP_AOO) == null ? "" : resultSet.getInt(ID_SP_AOO) + "");
			listaIndici.add(record);
		}
	}

	private void indexLuceneObject(LuceneIndexerAction luceneIndexerAction, Connection con, String tblInfoIndexer, LuceneService service,
			IndexWriter indexWriter) throws AurigaJobException {

		// istanzio il servizio di Lucene
		boolean isDeletingLuceneDoc = false;
		String fileUri = null;
		String ud = "";
		boolean deleteRows = false;
		Set<String> types = new HashSet<>();
		if (luceneIndexerAction == null) {
			return;
		}

		log.info("Indicizzazione Lucene");
		log.info("CI_OBJ: " + luceneIndexerAction.getCiObj());
		log.info("ATTRIBUTI DA INDICIZZARE: ");
		for (String key : luceneIndexerAction.getAttributiDaIndicizzare().keySet()) {
			log.info(key + " -> " + luceneIndexerAction.getAttributiDaIndicizzare().get(key));
		}
		log.info("ATTRIBUTI NON INDICIZZABILI: ");
		for (String key : luceneIndexerAction.getAttributiNonIndicizzabili().keySet()) {
			log.info(key + " -> " + luceneIndexerAction.getAttributiNonIndicizzabili().get(key));
		}

		// ottengo n righe (tutte con lo stesso IdSpAoo) corrispondenti a n
		// proprietà (attributi)
		// se una proprietà un file lo estraggo e lo converto in stringa per
		// l'indicizzazione
		if (luceneIndexerAction.getAttributiDaIndicizzare().get(_DELETED_DOC) != null) {
			isDeletingLuceneDoc = true;
		}
		// recupero anche l'info sul reader da usare
		String note = luceneIndexerAction.getNote();
		if (luceneIndexerAction.getCategoria() != null) {
			types.add(luceneIndexerAction.getCategoria());
		}
		String tempDir = (String) getAttribute("tempDir");
		if (tempDir == null) {
			throw new AurigaJobException("Cartella temporanea non configurata!");
		}
		InputStream inputStream = null;
		File app = null;
		String messaggioErr = "";
		try {
			deleteRows = false;
			ud = (String) luceneIndexerAction.getAttributiDaIndicizzare().get(_FIELD_NAME_ID_UD);
			if (ud == null) {
				ud = "";
			}
			if ((fileUri = (String) luceneIndexerAction.getAttributiDaIndicizzare().get(_FILE_DOC)) != null) {
				// recupero l'input stream dall'uri (faccio la extract)
				log.info("Estrazione del file: " + fileUri + " note = " + note);
				inputStream = StorageRetriever.retrieveInput(fileUri);
				app = new File(tempDir + JobUtilities.randomHexString() + JobUtilities.getStrDate());
				FileUtil.writeStreamToFile(inputStream, app);
				luceneIndexerAction.getAttributiDaIndicizzare().remove(_FILE_DOC);
			}
			log.debug("Effettuo operazione su Lucene.....");
			// blocco che effettua l'indicizzazione vera e propria
			// a questo punto ho un oggetto MAP da passare lucene
			if (isDeletingLuceneDoc) {
				// delete
				log.info("Effettuo operazione di delete su ciObj=" + luceneIndexerAction.getCiObj());
				service.deleteDocument(luceneIndexerAction.getCiObj(), indexWriter);
				log.info("Effettuata delete");
			} else {
				// Se il documento esiste in Lucene, allora si tratta di una
				// modifica
				// altrimenti si tratta di un inserimento
				if (service.isDocumentIndexed(luceneIndexerAction.getCiObj())) {
					// modifica
					log.info("Sto per effettuare operazione di modifica su ciObj=" + luceneIndexerAction.getCiObj());
					if (luceneIndexerAction.getNote() != null && luceneIndexerAction.getNote().startsWith(OCR_OK)) {
						service.updateIndexedDocument(app, luceneIndexerAction.getCiObj(), luceneIndexerAction.getAttributiDaIndicizzare(),
								luceneIndexerAction.getAttributiNonIndicizzabili(), true, indexWriter);
					} else {
						service.updateIndexedDocument(app, luceneIndexerAction.getCiObj(), luceneIndexerAction.getAttributiDaIndicizzare(),
								luceneIndexerAction.getAttributiNonIndicizzabili(), false, indexWriter);
					}
					log.info("Effettuata modify da parte del Lucene Manager");
				} else {
					// aggiunta
					log.info("Sto per effettuare operazione di aggiunta su ciObj=" + luceneIndexerAction.getCiObj());
					// valuto se fare o meno l'OCR
					if (luceneIndexerAction.getNote() != null && luceneIndexerAction.getNote().startsWith(OCR_OK)) {
						service.indexDocument(app, luceneIndexerAction.getCiObj(), luceneIndexerAction.getAttributiDaIndicizzare(),
								luceneIndexerAction.getAttributiNonIndicizzabili(), fileUri, true, indexWriter);
					} else {
						log.info("service.getWriter().getUtils().getWsEndpoint() "+service.getWriter().getUtils().getWsEndpoint());
						service.indexDocument(app, luceneIndexerAction.getCiObj(), luceneIndexerAction.getAttributiDaIndicizzare(),
								luceneIndexerAction.getAttributiNonIndicizzabili(), fileUri, false, indexWriter);
					}
					log.info("Effettuata add da parte del Lucene Manager");
				}
			}
			if (luceneIndexerAction.getNote() != null
					&& (luceneIndexerAction.getNote().startsWith(OCR_OUTPUT) || luceneIndexerAction.getNote().startsWith(OCR_INPUT))) {
				if (fileUri != null) {
					StorageRetriever.deleteFile(fileUri);
				}
			}
			deleteRows = true;
			messaggioErr = "";
		} catch (Exception e) {
			log.warn("Eccezione indexLuceneObject", e);
			// se non riesco ad indicizzare il file ne tengo traccia come
			// warning
			messaggioErr = e.getMessage();
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura inputStream", e);
				}
			FileUtil.deleteFile(app);
			try {
				if (deleteRows) {
					deleteRows(con, luceneIndexerAction, tblInfoIndexer);
				} else {
					setRowsToStateError(con, luceneIndexerAction, tblInfoIndexer, messaggioErr);
				}
			} catch (Exception e) {
				log.warn("Eccezione cancellazione record processati", e);
			}
		}
	}

	private void resetIndexWriter(Connection con, String dominio, String filtroCategoria) throws Exception {

		log.debug("Reset index writer per cambio dominio (solo per categoria REP_DOC)");
		log.debug("Nuovo dominio: " + dominio);
		log.debug("Schema: " + getSchemaName(con));

		try {
			indexWriter.close();
		} catch (Exception e) {
			log.warn("resetIndexWriter - Errore chiusura indexWriter", e);
		}
		log.debug("Faccio la commit sulla connessione per cancellare i record già processati");
		con.commit();
		service = (LuceneService) LuceneSpringAppContext.getContext().getBean("luceneService");
		service.setCategory(SearchCategory.valueOf(filtroCategoria));
		service.setIdDominio(dominio);
		service.setDbName(getSchemaName(con));
		indexWriter = service.getIndexWriter();
		log.debug("Istanziato il nuovo indexWriter su directory: " + indexWriter.getDirectory().toString());
	}
	
	private void resetIndexWriterPostgress(Connection con, String dominio, String filtroCategoria) throws Exception {

		log.debug("Reset index writer per cambio dominio (solo per categoria REP_DOC)");
		log.debug("Nuovo dominio: " + dominio);
		log.debug("Schema: " + "auri_deposito");

		try {
			indexWriter.close();
		} catch (Exception e) {
			log.warn("resetIndexWriter - Errore chiusura indexWriter", e);
		}
		log.debug("Faccio la commit sulla connessione per cancellare i record già processati");
		con.commit();
		service = (LuceneService) LuceneSpringAppContext.getContext().getBean("luceneService");
		service.setCategory(SearchCategory.valueOf(filtroCategoria));
		service.setIdDominio(dominio);
		service.setDbName("auri_deposito");
		indexWriter = service.getIndexWriter();
		log.debug("Istanziato il nuovo indexWriter su directory: " + indexWriter.getDirectory().toString());
	}

	/**
	 * Metodo di cancellazione dei record corrispettivi agli indici
	 * 
	 * @param con
	 * @param luceneIndexerAction
	 * @param tableName
	 * @throws Exception
	 */

	private void deleteRows(Connection con, LuceneIndexerAction luceneIndexerAction, String tableName) throws Exception {
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM " + tableName + " indici WHERE indici.CI_OBJ = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, luceneIndexerAction.getCiObj());
			log.debug("Cancellazione record indice: " + stmt.toString());
			stmt.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			log.error("Eccezione cancellazione record indice", e);
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				log.error("Eccezione chiusura statement", e);
			}
		}
	}

	/**
	 * Metodo di aggiornamento dei record corrispettivi agli indici
	 * 
	 * @param con
	 * @param luceneIndexerAction
	 * @param tableName
	 * @param message
	 * @throws Exception
	 */

	private void setRowsToStateError(Connection con, LuceneIndexerAction luceneIndexerAction, String tableName, String message) throws Exception {
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE " + tableName + " indici SET indici.STATO = ?, ERR_MSG = ?, TS_LAST_TRY = SYSDATE WHERE indici.CI_OBJ = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, _STATO_ERRORE);
			stmt.setString(2, message);
			stmt.setString(3, luceneIndexerAction.getCiObj());
			log.debug("Cancellazione record indice: " + stmt.toString());
			stmt.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			log.error("Eccezione cancellazione record indice", e);
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				log.error("Eccezione chiusura statement", e);
			}
		}
	}

	/**
	 * Crea una map di sotto liste a partire da una lista
	 * 
	 * @param list
	 * @return
	 */
	public Map<String, LinkedList<DataToIndex>> partition(List<DataToIndex> list) {
		String currValue = null;
		LinkedHashMap<String, LinkedList<DataToIndex>> result = new LinkedHashMap<>();
		LinkedList<DataToIndex> currList = null;
		for (DataToIndex obj : list) {
			if (!obj.getCiObj().equals(currValue)) {
				currValue = obj.getCiObj();
				currList = new LinkedList<>();
				result.put(currValue, currList);
			}
			currList.add(obj);
		}
		return result;
	}

	public LuceneService getService() {
		return service;
	}

	public void setService(LuceneService service) {
		this.service = service;
	}

	@Override
	public void end(String obj) {
		log.info("Job completato");
	}

}