/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.bean.SearchCategory;
import it.eng.gd.lucenemanager.dao.DaoTMimeTypeFmtDig;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.document.reader.OcrDocumentReader;
import it.eng.gd.lucenemanager.exception.LuceneManagerException;
import it.eng.gd.lucenemanager.manager.entity.TMimetypeFmtDig;
import it.eng.gd.lucenemanager.storage.StorageCenter;
import it.eng.gd.lucenemanager.util.DirectoryManager;
import it.eng.gd.lucenemanager.util.JobUtils;
import it.eng.utility.FileUtil;

/**
 * Classe che implementa i metodi di scrittura degli indici di lucene
 * 
 * @author Administrator
 * 
 */
@Entity
public class LuceneIndexWriter {

	public static final String LUCENE_CONFIG = "luceneindex";

	public static final String JOB_UTILS = "jobUtils";

	public static final String READER_OBJECT = "#FILE";

	public static final String ID_OBJECT = "OBJECT_ID_KEY";

	public static final String FILE_NAME = "FILENAME";

	public static final String RELATIVE_URI = "RELATIVE_URI";

	public static final String MIME_TYPE = "MIME_TYPE";

	public static final String MIME_TYPE_FOLDER = "folder";

	public static final String LOCK_FILENAME = "write.lock";

	public static final Double RAM_SIZE = new Double(256);

	public static final Version _LUCENE_VERSION = Version.LUCENE_35;

	private DaoTMimeTypeFmtDig daoMimeType;

	private StorageCenter storCen;

	private JobUtils utils;

	private LuceneIndexReader luceneReader;

	private boolean abilitaOCR;

	private Logger log = Logger.getLogger(LuceneIndexWriter.class);

	// categoria a cui fa riferimento l'indice
	private SearchCategory category;

	private String idDominio;

	private String dbName;

	private DirectoryManager dirManager;

	/**
	 * Indicizza un folder con i soli metadati
	 * 
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void addFolder(String foldername, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri) throws Exception {
		addDocument(null, foldername, MIME_TYPE_FOLDER, id, indexmetadata, unindexmetadata, uri, false, null);
	}

	public void addDocument(File file, String mimetype, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri)
			throws Exception {
		addDocument(file, file != null ? FilenameUtils.getName(file.getAbsolutePath()) : null, mimetype, id, indexmetadata, unindexmetadata, uri, false, null);
	}

	public void addDocument(File file, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri) throws Exception {
		String mimeType = null;
		if (file != null) {
			mimeType = utils.getFormato(file);
		}
		addDocument(file, file != null ? FilenameUtils.getName(file.getAbsolutePath()) : null, mimeType, id, indexmetadata, unindexmetadata, uri, false, null);
	}

	public void addDocument(File file, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri, IndexWriter iwriter)
			throws Exception {
		String mimeType = null;
		if (file != null) {
			mimeType = utils.getFormato(file);
		}
		addDocument(file, file != null ? FilenameUtils.getName(file.getAbsolutePath()) : null, mimeType, id, indexmetadata, unindexmetadata, uri, false,
				iwriter);
	}

	public void addDocument(File file, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri, boolean OCR)
			throws Exception {
		String mimeType = null;
		if (file != null) {
			mimeType = utils.getFormato(file);
		}
		addDocument(file, file != null ? FilenameUtils.getName(file.getAbsolutePath()) : null, mimeType, id, indexmetadata, unindexmetadata, uri, OCR, null);
	}

	public void addDocument(File file, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri, boolean OCR,
			IndexWriter iwriter) throws Exception {
		String mimeType = null;
		if (file != null) {
			mimeType = utils.getFormato(file);
		}
		addDocument(file, file != null ? FilenameUtils.getName(file.getAbsolutePath()) : null, mimeType, id, indexmetadata, unindexmetadata, uri, OCR, iwriter);
	}

	public void addDocument(String filename, String mimetype, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri)
			throws Exception {
		addDocument(null, filename, mimetype, id, indexmetadata, unindexmetadata, uri, false, null);
	}

	/**
	 * Aggiunge un documento da indicizzare
	 * 
	 * @param name
	 */
	private void addDocument(File fileToAdd, String filename, String mimetype, String id, Map<String, Object> indexmetadata,
			Map<String, Object> unindexmetadata, String uri, boolean OCR, IndexWriter iwriter) throws Exception {
		Reader reader = null;
		FileVO[] readers = null;
		String fileuri = null;
		File file = null;
		try {
			if (fileToAdd != null) {
				// Controllo se un file firmato e lo sbusto
				InputStream stream = utils.recursiveEnvelopedFileDecription(fileToAdd);
				// String mimetypeFile=util.getFormato(fileToAdd);
				if (stream != null) {
					file = File.createTempFile("Extract", "tmp");
					FileOutputStream out = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int len = stream.read(buffer);
					while (len != -1) {
						out.write(buffer, 0, len);
						len = stream.read(buffer);
					}
					stream.close();
					out.flush();
					out.close();
					// elimino i file temporanei
					utils.deletetmpfile();
				} else {
					file = fileToAdd;
				}
				if (file != null) {
					if (StringUtils.contains(mimetype, ";")) {
						mimetype = StringUtils.split(mimetype, ";")[0];
					}
					log.debug("Aggiungo un documento da indicizzare con il seguente mimetype: [" + mimetype+"]");
					AbstractDocumentReader abstractreader = null;
					List<TMimetypeFmtDig> listaReaders = daoMimeType.getAllRecords();
					if (uri == null || uri == "") {
						// salvo il file nello storage
						fileuri = storCen.getGlobalStorage().store(file);
					} else
						fileuri = uri;
					// una volta che trovo il mimetype giusto istanzio la classe per reflection
					for (TMimetypeFmtDig bean : listaReaders) {
						if (bean.getId().getMimetype().contains(mimetype)) {
							log.debug("Cerco di istanziare il seguente reader: " + bean.getRifReader());
							if (bean.getRifReader() != null) {
								abstractreader = (AbstractDocumentReader) Class.forName(bean.getRifReader()).newInstance();
								abstractreader.setOcrOperation(false);

								if (file != null && abilitaOCR) {
									if ("application/pdf".equalsIgnoreCase(mimetype) && isPdfImage(file)) {
										abstractreader.setOcrOperation(true);
									}else {
										abstractreader.setOcrOperation(OCR);
									}
								}
								
								if (abstractreader instanceof OcrDocumentReader) {
									String estensione = ((OcrDocumentReader) abstractreader).getEstensioneByFormato(mimetype);
									log.debug("estensione" + estensione);
									((OcrDocumentReader) abstractreader).setEstensione(estensione);
								}
								break;
							} else {
								log.error("Nessun reader associato al documento con il seguente mimetype: " + mimetype);
								throw new LuceneManagerException("Nessun reader associato al documento con il seguente mimetype: " + mimetype);
							}
						}
					}
					if (abstractreader != null) {
						if (abstractreader.getContent(file) != null)
							reader = abstractreader.getContent(file);
						else if (abstractreader.getContents(file, mimetype) != null)
							readers = abstractreader.getContents(file, mimetype);
					}

				}

			}
			if (reader == null && readers != null) {
				// ciclo
				for (FileVO fileVO : readers) {
					indicizzaFileVO(fileVO, indexmetadata, unindexmetadata, id, fileuri, iwriter);
				}
			} else {
				creaDocumentoDaIndicizzare(fileToAdd, indexmetadata, unindexmetadata, mimetype, reader, id, fileuri, iwriter);
			}
		} catch (Exception e) {
			log.error("Impossibile trovare un reader adatto al file", e);
			throw new LuceneManagerException("Impossibile trovare un reader adatto al file", e);
		} finally {
			// cancello anche il temporaneo dell'estrazione
			if (file != null) {
				FileUtil.deleteFile(file);
			}
		}

	}

	private void indicizzaFileVO(FileVO fileVO, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String id, String fileuri,
			IndexWriter iwriter) throws Exception {
		Reader reader = fileVO.getAbstractreader().getContent(fileVO.getFile());
		fileuri = fileuri + "/" + fileVO.getPath();
		creaDocumentoDaIndicizzare(fileVO.getFile(), indexmetadata, unindexmetadata, fileVO.getMimeType(), reader, id, fileuri, iwriter);
		if (fileVO.getListaFileVO() != null && fileVO.getListaFileVO().size() > 0) {
			for (FileVO fileVO1 : fileVO.getListaFileVO()) {
				indicizzaFileVO(fileVO1, indexmetadata, unindexmetadata, id, fileuri, iwriter);
			}
		}
	}

	private void creaDocumentoDaIndicizzare(File fileToAdd, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String mimetype,
			Reader reader, String id, String fileuri, IndexWriter iwriter) throws Exception {
		IndexWriter writer = null;
		try {
			// Creo il nuovo documento da indicizzare
			Document doc = new Document();
			// Aggiungo la chiave dell'oggetto per il recupero
			doc.add(new Field(ID_OBJECT, StringUtils.remove(id, "-"), Field.Store.YES, Field.Index.ANALYZED));
			if (fileToAdd != null) {
				doc.add(new Field(RELATIVE_URI, fileuri, Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			if (indexmetadata.containsKey(FILE_NAME)) {
				// Salvo il filename del file
				log.debug("Sto indicizzando il filename: " + (String) indexmetadata.get(FILE_NAME));
				doc.add(new Field(FILE_NAME, (String) indexmetadata.get(FILE_NAME), Field.Store.YES, Field.Index.ANALYZED));
				indexmetadata.remove(FILE_NAME);
			} else {
				if (fileToAdd != null)
					doc.add(new Field(FILE_NAME, fileToAdd.getName(), Field.Store.YES, Field.Index.ANALYZED));
			}

			if (mimetype != null) {
				// Salvo il mimetype del file
				doc.add(new Field(MIME_TYPE, mimetype, Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			// Aggiungo i metadati da indicizzare
			if (indexmetadata != null) {
				// Aggiungo tutte le proprieta' del documento da indicizzare
				log.debug("Aggiungo i metadati da indicizzare");
				Iterator<String> iterator = indexmetadata.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = indexmetadata.get(key);
					log.debug(key + " -> " + value);
					if (value instanceof Date) {
						doc.add(new Field(key, DateTools.timeToString(((Date) value).getTime(), DateTools.Resolution.SECOND), Field.Store.YES,
								Field.Index.NOT_ANALYZED));
					} else {
						doc.add(new Field(key, value == null ? "" : value.toString(), Field.Store.YES, Field.Index.ANALYZED));
					}
				}
			}
			// Aggiungo i metadati da non indicizzare
			if (unindexmetadata != null) {
				// Aggiungo tutte le proprieta' del documento da non indicizzare
				log.debug("Aggiungo i metadati da non indicizzare");
				Iterator<String> iterator = unindexmetadata.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = unindexmetadata.get(key);
					log.debug(key + " -> " + value);
					if (value instanceof Date) {
						doc.add(new Field(key, DateTools.timeToString(((Date) value).getTime(), DateTools.Resolution.SECOND), Field.Store.YES, Field.Index.NO));
					} else {
						doc.add(new Field(key, value == null ? "" : value.toString(), Field.Store.YES, Field.Index.NO));
					}
				}
			}
			if (reader != null) {
				// Aggiungo il contenuto del file se esite
				log.debug("Aggiungo il contenuto del file indicizzato con la seguente chiave: " + READER_OBJECT);
				String text = IOUtils.toString(reader);
				log.debug("Il testo che vado ad aggiungere è il seguente: " + text);
				doc.add(new Field(READER_OBJECT, text, Field.Store.YES, Field.Index.ANALYZED));
			}
			// Aggiungo il documento all'indice
			if (iwriter == null) {
				log.debug("iWriter passato in input è nullo perciò ne istanzio uno nuovo");
				writer = getWriter();
			} else {
				writer = iwriter;
			}
			log.debug("Directory in cui vado a sovrascrivere gli indici:" + writer.getDirectory().toString());
			log.debug("Aggiungo il documento all'indice");
			writer.addDocument(doc);
			// Committo il writer
			if (iwriter == null) {
				writer.commit();
			}
		} catch (Exception e) {
			log.error("Eccezione metodo creaDocumentoDaIndicizzare", e);
		}
	}

	/**
	 * aggiorno gli indici
	 * 
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void updateDocument(File fileToAdd, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, boolean OCR,
			IndexWriter iwriter) throws Exception {
		// Recupero i valori del documento da indicizzare
		Map<String, Object> valoriCampi = luceneReader.getFieldValuesByObjId(id);
		// inserisco i valori nuovi
		if (indexmetadata != null) {
			List<String> keysToUpdate = new ArrayList<String>(indexmetadata.keySet());
			for (String key : keysToUpdate) {
				// non devo inserire negli attributi da indicizzare la chiave
				if (key != ID_OBJECT) {
					valoriCampi.put(key, indexmetadata.get(key));
				}
			}
		}
		if (unindexmetadata != null) {
			List<String> keysToUpdate = new ArrayList<String>(unindexmetadata.keySet());
			for (String key : keysToUpdate) {
				if (key != ID_OBJECT) {
					valoriCampi.put(key, unindexmetadata.get(key));
				}
			}
		}
		// cancello i dati vecchi
		delete(id, iwriter);
		if (fileToAdd == null) {
			// reinserisco quelli nuovi
			addDocument(null, id, valoriCampi, null, null, iwriter);
		} else {
			addDocument(fileToAdd, id, valoriCampi, null, fileToAdd.getAbsolutePath(), OCR, iwriter);
		}
	}

	/**
	 * aggiorno gli indici
	 * 
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void updateDocument(String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata) throws Exception {
		// Recupero i valori del documento da indicizzare
		Map<String, Object> valoriCampi = luceneReader.getFieldValuesByObjId(id);
		// inserisco i valori nuovi
		if (indexmetadata != null) {
			List<String> keysToUpdate = new ArrayList<String>(indexmetadata.keySet());
			for (String key : keysToUpdate) {
				// non devo inserire negli attributi da indicizzare la chiave
				if (key != ID_OBJECT) {
					valoriCampi.put(key, indexmetadata.get(key));
				}
			}
		}
		if (unindexmetadata != null) {
			List<String> keysToUpdate = new ArrayList<String>(unindexmetadata.keySet());
			for (String key : keysToUpdate) {
				if (key != ID_OBJECT) {
					valoriCampi.put(key, unindexmetadata.get(key));
				}
			}
		}
		// cancello i dati vecchi
		delete(id, null);
		// reinserisco quelli nuovi
		addDocument(null, id, valoriCampi, null, null);
	}

	/**
	 * Rimuove il documento dagli indici di lucene
	 */
	public void delete(String objid, IndexWriter iwriter) throws Exception {
		IndexWriter writer = null;
		if (iwriter == null) {
			writer = getWriter();
		} else {
			writer = iwriter;
		}
		// creo la query da cui cancellare il documento
		BooleanQuery currentSearching = new BooleanQuery();
		QueryParser parser = new QueryParser(LuceneIndexReader._LUCENE_VERSION, LuceneIndexWriter.ID_OBJECT,
				new StandardAnalyzer(LuceneIndexReader._LUCENE_VERSION));
		Query queryFieldName = parser.parse(objid);
		currentSearching.add(queryFieldName, BooleanClause.Occur.MUST);
		writer.deleteDocuments(currentSearching);
		if (iwriter == null) {
			writer.commit();
		}
	}

	/**
	 * Recupera il writer dalla directory
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public IndexWriter getWriter() throws Exception {
		// Recupero le configurazioni di lucene
		Directory directory = dirManager.getDirectory(getCategory(), getIdDominio(), getDbName());
		log.debug("Directory writer: " + directory.toString());
		// if (directory instanceof FSDirectory) {
		// // VERIFICO L'ESISTENZA DI UN FILE DI LOCK ED EVENTUALMENTE
		// // LO ELIMINO
		// log.debug("Cerco file di lock");
		// File[] matches = ((FSDirectory) directory).getDirectory().listFiles(new FilenameFilter() {
		//
		// public boolean accept(File path, String name) {
		// return name.endsWith(LOCK_FILENAME);
		// }
		// });
		// if (matches != null) {
		// for (File match : matches) {
		// log.error("Trovato file di lock " + match.getAbsolutePath() + " - provo a cancellarlo");
		// boolean deleted = match.delete();
		// if (!deleted) {
		// log.error("Impossibile cancellare il file di lock, procedere manualmente");
		// }
		// }
		// }
		//
		// }
		IndexWriter writer = null;
		// setto la factory di lock e il timeout in scrittura
		// se da configurazione non ho settato nessuna factory di lock
		// imposto la simpleFS
		IndexWriterConfig config = new IndexWriterConfig(_LUCENE_VERSION, new StandardAnalyzer(_LUCENE_VERSION));
		IndexWriterConfig.setDefaultWriteLockTimeout(new Long("20000"));
		config.setRAMBufferSizeMB(RAM_SIZE);
		config.setMaxBufferedDocs(100);
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		writer = new IndexWriter(directory, config);
		return writer;
	}

	private boolean isPdfImage(File file) {

	 	PDFParser parser = null;
	    PDDocument pdDoc = null;
	    COSDocument cosDoc = null;
	    PDFTextStripper pdfStripper;

	    String parsedText;
	    
	    	try {
		    	RandomAccessRead source = new RandomAccessBufferedFileInputStream(new FileInputStream(file));
		        parser = new PDFParser(source);
		        parser.parse();
		        cosDoc = parser.getDocument();
		        pdfStripper = new PDFTextStripper();
		        pdDoc = new PDDocument(cosDoc);
		        parsedText = pdfStripper.getText(pdDoc);
		        if(StringUtils.isBlank(parsedText)) {
		        	return true;
		        }else {
		        	return false;		        
		        }
		    } catch (Exception e) {
		    	log.error("Errore durante la verifica del pdfImage: " + e.getMessage(), e);
		    	log.info("Non sono riuscito a verificare se � un pdfImmagine, lo inserisco comunque nel path");
		    	return true;
		    }finally {
		    	try {
		            if (cosDoc != null)
		                cosDoc.close();
		            if (pdDoc != null)
		                pdDoc.close();
		        } catch (Exception e1) {		        	
		        }
		    }    
	}
	public DaoTMimeTypeFmtDig getDaoMimeType() {
		return daoMimeType;
	}

	public void setDaoMimeType(DaoTMimeTypeFmtDig daoMimeType) {
		this.daoMimeType = daoMimeType;
	}

	public StorageCenter getStorCen() {
		return storCen;
	}

	public void setStorCen(StorageCenter storCen) {
		this.storCen = storCen;
	}

	public JobUtils getUtils() {
		return utils;
	}

	public void setUtils(JobUtils utils) {
		this.utils = utils;
	}

	public SearchCategory getCategory() {
		return category;
	}

	public void setCategory(SearchCategory category) {
		this.category = category;
	}

	public LuceneIndexReader getLuceneReader() {
		return luceneReader;
	}

	public void setLuceneReader(LuceneIndexReader luceneReader) {
		this.luceneReader = luceneReader;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public DirectoryManager getDirManager() {
		return dirManager;
	}

	public void setDirManager(DirectoryManager dirManager) {
		this.dirManager = dirManager;
	}

	public boolean isAbilitaOCR() {
		return abilitaOCR;
	}

	public void setAbilitaOCR(boolean abilitaOCR) {
		this.abilitaOCR = abilitaOCR;
	}

}
