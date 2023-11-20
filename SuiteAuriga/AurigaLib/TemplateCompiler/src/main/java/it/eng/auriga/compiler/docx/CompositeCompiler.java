/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import it.eng.auriga.compiler.docx.model.DirectiveEnum;
import it.eng.auriga.compiler.docx.model.PlaceholderWrapper;
import it.eng.auriga.compiler.docx.model.TableWrapper;
import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.compiler.utility.TemplateStorageFactory;
import it.eng.auriga.compiler.utility.TemplateUtility;
import it.eng.bean.ExecutionResultBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;

/**
 * Iniettore di dati
 * <ul>
 * <li>estrae le variabili dai paragrafi</li>
 * <li>estrae le variabili dalle tabelle</li>
 * <li>inietta i dati</li>
 * <li>replica le tabelle (Sperimentale, non sono ancora riuscito a preservare
 * le varie tabelle replicate, word le unifica)</li>
 * </ul>
 * 
 * @author massimo malvestio
 * @TODO allineare il codice agli altri compiler, estendendo AbstractCompiler e
 *       rifattorizzando i nomi dei metodi in maniera tale da implementare i
 *       metodi astratti della superclasse
 */
public class CompositeCompiler {

	private Logger logger = Logger.getLogger(CompositeCompiler.class);

	private XWPFDocument template;

	private SezioneCache variables;

	private Map<String, String> valoriSemplici;

	private Map<String, Lista> valoriComplessi;

	/**
	 * k-> id del placeholder, v -> lista di tutti i placeholder che hanno come
	 * id la chiave specificata
	 */
	private Map<String, List<PlaceholderWrapper>> simplePlaceholders;

	/**
	 * k -> id della tabella, v -> la tabella stessa
	 */
	protected Map<String, TableWrapper> tables;

	/**
	 * k -> id della tabella, v -> la tabella stessa
	 */
	protected Map<String, TableWrapper> replicableTables;

	private Map<PlaceholderWrapper, XWPFTable> tablesToRemove = new HashMap<PlaceholderWrapper, XWPFTable>();

	public CompositeCompiler(String datiModelloXml, String storageTemplateUri) throws Exception {
		
		TemplateStorage templateStorage = TemplateStorageFactory.getTemplateStorageImpl(); 

		File templateFile = templateStorage.extractFile(storageTemplateUri);

		template = TemplateUtility.generateXWPFDocument(templateFile);

		this.variables = buildXml(datiModelloXml);

	}

	public CompositeCompiler(SezioneCache variables, XWPFDocument template) {
		this.template = template;
		this.variables = variables;
	}

	@SuppressWarnings("unchecked")
	public File injectData() {

		// mappo i valori semplici e quelli complessi
		ExecutionResultBean result = TemplateUtility.mapSezioneCache(variables);

		valoriSemplici = (Map<String, String>) result.getAdditionalInformation("valoriSemplici");

		valoriComplessi = (Map<String, Lista>) result.getAdditionalInformation("valoriComplessi");

		extractParagraphsPlaceHolders();

		extractTablesPlaceholders();

		injectValues();

		injectComplexValues();

		injectReplicableTables();

		removeTables();

		File injectedTemplate = null;

		try {

			injectedTemplate = saveTemplateOnFile();

			logger.info(
					String.format("Il file %1$s è stato correttamente generato", injectedTemplate.getAbsolutePath()));

		} catch (Exception e) {

			logger.error(
					"Durante il salvataggio del template con i dati iniettati, si è verificata la seguente eccezione",
					e);

		}

		return injectedTemplate;
	}

	private void removeTables() {

		for (Entry<PlaceholderWrapper, XWPFTable> currentEntry : tablesToRemove.entrySet()) {

			template.removeBodyElement(template.getPosOfTable(currentEntry.getValue()));

			logger.debug("Rimossa tabella");
		}

	}

	/**
	 * Crea una o più repliche, in base agli elementi delle relative variabili
	 * complesse, delle tabelle contenenti la direttiva replicate.<br/>
	 * Allo stato attuale si tratta di codice sperimentale non funzionante, nel
	 * senso che la replica e l'iniezione dei dati avviene correttamente, ma non
	 * riesco ad inserire un paragrafo tra due tabelle consecutive, in meniera
	 * da matenerle separate
	 */
	private void injectReplicableTables() {

		for (Entry<String, Lista> entry : valoriComplessi.entrySet()) {

			String key = entry.getKey();

			Lista values = entry.getValue();

			TableWrapper originalTable = replicableTables.get(key);

			if (originalTable != null) {

				// recupero la posizione della tabella originale tra i body
				// elements
				Integer originalTablePosition = template.getPosOfTable(originalTable.getTable());

				template.getDocument().getBody().getTblList();

				// è stata trovata una tabella da replicare

				if (values.getRiga().size() == 1) {

					// devo popolare una sola tabella, quindi utilizzo quella
					// originale
					injectValues(mapRiga(values.getRiga().get(0)), generatePlaceholdersMap(originalTable));

				} else if (values.getRiga().size() > 1) {

					// replico n-1 volte la tabella originale e le valorizzo

					for (int indiceTabella = 1; indiceTabella < values.getRiga().size(); indiceTabella++) {

						Riga currentRiga = values.getRiga().get(indiceTabella);

						TableWrapper replicatedTable = replicateTable(originalTable, originalTablePosition);

						injectValues(mapRiga(currentRiga), generatePlaceholdersMap(replicatedTable));
					}

					// inietto i dati nella tabella originale
					injectValues(mapRiga(values.getRiga().get(0)), generatePlaceholdersMap(originalTable));
				}
			}
		}

		// normalizeDocument();
	}

	/**
	 * Normalizza la struttura del documento inserendo dei paragrafi tra
	 * tabrelle contigue, in maniera tale da evitare che poi vengano fuse da
	 * word alla prima apertura
	 */
	private void normalizeDocument() {

		// posizione in cui inserire il paragrafo
		List<Integer> paragraphsPositions = new ArrayList<Integer>();

		// posizione della tabella da cui generare l'XmlCursor da usare per
		// inserire il paragrafo separatore
		Map<Integer, Integer> tablePositions = new HashMap<Integer, Integer>();

		Integer tablePosition = 0;

		for (int bodyElementIndex = 1; bodyElementIndex < template.getBodyElements().size(); bodyElementIndex++) {

			IBodyElement currentElement = template.getBodyElements().get(bodyElementIndex);

			if (currentElement instanceof XWPFTable) {

				tablePosition++;

				IBodyElement previousElement = template.getBodyElements().get(bodyElementIndex - 1);

				if (previousElement instanceof XWPFTable) {

					// ho trovato due table consecutive, inserisco un p nel
					// mezzo per evitare che vengano poi fuse da word alla prima
					// apertura

					paragraphsPositions.add(bodyElementIndex);

					tablePositions.put(bodyElementIndex, Integer.valueOf(tablePosition));
				}
			}
		}

		int maxIndex = template.getDocument().getBody().getTblList().size();

		for (int index = 0; index < paragraphsPositions.size(); index++) {

			Integer paragraphsPosition = paragraphsPositions.get(index);

			Integer tablePositionInList = tablePositions.get(paragraphsPosition);

			if (paragraphsPosition < maxIndex) {

				template.getDocument().getBody().insertNewP(paragraphsPosition + index);

				XmlCursor paragraphCursor = template.getDocument().getBody().getTblArray(tablePositionInList)
						.newCursor();

				template.insertNewParagraph(paragraphCursor);
			}
		}
	}

	/**
	 * A partire dalla tabella specificata genera una mappa in cui K -> id del
	 * placeholder, V -> insieme di tutti i placeholder aventi la chiave
	 * specificata
	 * 
	 * @param replicatedTable
	 * @return
	 */
	protected Map<String, List<PlaceholderWrapper>> generatePlaceholdersMap(TableWrapper replicatedTable) {
		Map<String, List<PlaceholderWrapper>> placeholdersMap = new HashMap<String, List<PlaceholderWrapper>>();

		for (PlaceholderWrapper plcHolder : replicatedTable.getVariables()) {

			List<PlaceholderWrapper> p1 = placeholdersMap.get(plcHolder.getPlaceholderIdentifier());

			if (p1 == null) {

				p1 = new ArrayList<PlaceholderWrapper>();
				placeholdersMap.put(plcHolder.getPlaceholderIdentifier(), p1);
			}

			p1.add(plcHolder);

		}
		return placeholdersMap;
	}

	/**
	 * Converte la riga specificata in una mappa dove k -> $<nome_colonna>(se
	 * null, numero_riga)$, v -> content
	 * 
	 * @param currentRiga
	 * @return
	 */
	private Map<String, String> mapRiga(Riga currentRiga) {

		Map<String, String> retValue = new HashMap<String, String>();

		for (Colonna currentColonna : currentRiga.getColonna()) {

			if (!StringUtils.isBlank(currentColonna.getNome())) {
				retValue.put("$" + currentColonna.getNome() + "$", currentColonna.getContent());
			} else {
				retValue.put("$" + currentColonna.getNro() + "$", currentColonna.getContent());
			}
		}

		return retValue;
	}

	/**
	 * Duplica la tabella specificata; i contenuti vengono replicati esattamente
	 * come sono stati definiti nella tabella originale, quindi i placeholder
	 * avranno gli stessi id degli originali
	 * 
	 * @param table
	 * @param indiceTabella
	 * @return
	 */
	private TableWrapper replicateTable(TableWrapper table, int indiceTabella) {

		// tabella originale
		XWPFTable original = table.getTable();

		XmlCursor originalTableCursor = original.getCTTbl().newCursor();

		template.insertNewParagraph(originalTableCursor);

		// inserisce la tabella all'interno dell'XmlObject che rappresenta il
		// body
		CTTbl tbl = template.getDocument().getBody().insertNewTbl(indiceTabella);
		tbl.set(original.getCTTbl());

		// devo inserire un paragrafo perchè altrimenti la tabella successiva
		// viene appiccicata e quindi vengono fuse
		// CTP newP =
		// template.getDocument().getBody().insertNewP(indiceTabella);

		XWPFTable table2 = new XWPFTable(tbl, template);

		// inserisce la tabella nella lista delle tabelle del documento
		template.insertTable(indiceTabella, table2);

		// template.insertNewParagraph(newP.newCursor());

		List<PlaceholderWrapper> variables = TemplateUtility.getTableVariables(table2);
		PlaceholderWrapper tableId = TemplateUtility.getTableId(variables);
		TableWrapper retValue = new TableWrapper(table2, variables, tableId);

		return retValue;
	}

	/**
	 * Inietta i valori presenti nei placeholder "semplici", ovvero che non
	 * rappresentano una tabella
	 */
	protected void injectValues() {

		for (Entry<String, String> entry : valoriSemplici.entrySet()) {

			List<PlaceholderWrapper> placeholderWrappers = simplePlaceholders.remove(entry.getKey());

			if (placeholderWrappers != null) {

				for (PlaceholderWrapper wrapper : placeholderWrappers) {

					logger.debug("Iniezione di " + wrapper.getPlaceholderIdentifier());

					wrapper.injectValue(entry.getValue());

				}
			}
		}

		// gestione dei placeholder che non sono stati valorizzati
		for (List<PlaceholderWrapper> placeholders : simplePlaceholders.values()) {

			for (PlaceholderWrapper placeholder : placeholders) {

				Set<DirectiveEnum> directives = placeholder.getDirectives();

				if (directives.contains(DirectiveEnum.REMOVE_ON_EMPTY)) {
					placeholder.removeFromDocument();
				}
			}
		}
	}

	/**
	 * Inietta i valori nei relativi placeholder. Per quelli sprovvisti di
	 * placeholder, se presente la direttiva REMOVE_ON_EMPTY, li rimuove dal
	 * documento, quindi a seconda dell'implementazione verranno rimossi i
	 * singoli placeholder oppure la relativa riga della tabella in cui si
	 * trovano
	 * 
	 * @param values
	 * @param tablePlaceholders
	 */
	protected void injectValues(Map<String, String> values, Map<String, List<PlaceholderWrapper>> tablePlaceholders) {

		for (Entry<String, String> entry : values.entrySet()) {

			List<PlaceholderWrapper> placeholderWrappers = tablePlaceholders.remove(entry.getKey());

			if (placeholderWrappers != null) {

				for (PlaceholderWrapper wrapper : placeholderWrappers) {

					wrapper.injectValue(entry.getValue());

				}
			}
		}

		// gestione dei placeholder che non sono stati valorizzati
		for (List<PlaceholderWrapper> placeholders : tablePlaceholders.values()) {

			for (PlaceholderWrapper placeholder : placeholders) {

				Set<DirectiveEnum> directives = placeholder.getDirectives();

				if (directives.contains(DirectiveEnum.REMOVE_ON_EMPTY)) {
					placeholder.removeFromDocument();
				}
			}
		}
	}

	/**
	 * Analizza le tabelle presenti nel documento, estraendo i placeholder in
	 * esse presenti separandoli in
	 * <ul>
	 * <li>variabili semplici, presenti in tabelle di layout</li>
	 * <li>identificativi delle tabelle, associate a valori complessi</li>
	 * </ul>
	 */
	private void extractTablesPlaceholders() {

		tables = new HashMap<String, TableWrapper>();

		replicableTables = new HashMap<String, TableWrapper>();

		for (XWPFTable currentTable : template.getTables()) {

			List<PlaceholderWrapper> variables = TemplateUtility.getTableVariables(currentTable);

			PlaceholderWrapper tableId = TemplateUtility.removeTableId(variables);

			// se tableId è nullo, allora si tratta di una tabella di
			// layout, altrimenti si tratta di una tabella dinamica da
			// popolare
			if (tableId == null) {

				// tabella di layout, tutte la variabile trovate sono da
				// iniettare

				mapVariables(variables, currentTable);

			} else {

				String tableIdentifierValue = tableId.getPlaceholderIdentifier();

				if (tableIdentifierValue == null) {

					// non è stato impostato alcun tag come identificativo della
					// variabile
					logger.error("L'identificativo della tabella è null");

				} else {

					TableWrapper tableWrapper = new TableWrapper(currentTable, variables, tableId);

					if (isReplicableTable(tableId)) {
						replicableTables.put(tableIdentifierValue, tableWrapper);
					} else {
						tables.put(tableIdentifierValue, tableWrapper);
					}
				}
			}
		}
	}

	/**
	 * Se true la tabella cui appartengono le variabili è una tabella
	 * replicabile
	 * 
	 * @param variables
	 * @return
	 */
	private boolean isReplicableTable(PlaceholderWrapper tableId) {

		return tableId.getDirectives().contains(DirectiveEnum.REPLICATE);
	}

	/**
	 * Estrae tutti i placeholder presenti in tutti i paragrafi del documento
	 */
	private void extractParagraphsPlaceHolders() {

		// popolamento delle strutture di appoggio relative ai placeholder non
		// presenti all'interno di tabelle
		simplePlaceholders = new HashMap<String, List<PlaceholderWrapper>>();

		for (XWPFParagraph currentPar : template.getParagraphs()) {

			// lista dei vari componenti del paragrafo che si sta analizzando
			List<PlaceholderWrapper> runs = TemplateUtility.getVariables(currentPar);

			mapVariables(runs, null);
		}
	}

	/**
	 * @param runs
	 * @param table
	 */
	protected void mapVariables(List<PlaceholderWrapper> runs, XWPFTable table) {

		for (PlaceholderWrapper currentRun : runs) {

			String placeholderIdentifier = currentRun.getPlaceholderIdentifier();

			if (placeholderIdentifier == null) {

				logger.error("Nessun identificativo specificato per la variabile");

			} else {

				logger.debug(placeholderIdentifier);

				List<PlaceholderWrapper> currentControlList = simplePlaceholders.get(placeholderIdentifier);

				if (currentControlList == null) {
					currentControlList = new ArrayList<PlaceholderWrapper>();
					simplePlaceholders.put(placeholderIdentifier, currentControlList);
				}

				if (currentRun.getDirectives().contains(DirectiveEnum.REMOVE_TABLE)) {

					String value = valoriSemplici.get(currentRun.getPlaceholderIdentifier());

					if (StringUtils.isNotBlank(value) && ("1".equals(value) || "true".equals(value))) {
						tablesToRemove.put(currentRun, table);
					}
				} else {
					currentControlList.add(currentRun);
				}
			}
		}
	}

	/**
	 * Gestisce l'iniezione dei dati complessi dentro le relative tabelle
	 */
	protected void injectComplexValues() {

		for (Entry<String, Lista> entry : valoriComplessi.entrySet()) {

			String key = entry.getKey();

			Lista values = entry.getValue();

			TableWrapper table = tables.get(key);

			if (table != null) {

				addRowsToTable(table, values);

			}
		}
	}

	/**
	 * Aggiunge alla tabella specificata una riga per ogni elemento presente
	 * nella lista
	 * 
	 * @param table
	 * @param values
	 */
	private void addRowsToTable(TableWrapper table, Lista values) {

		for (int i = 0; i < values.getRiga().size(); i++) {

			Riga currentRiga = values.getRiga().get(i);

			table.injectNewRow(currentRiga);

		}
	}

	/**
	 * Salva in un file temporaneo il documento in memoria con i dati iniettati
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected File saveTemplateOnFile() throws IOException, FileNotFoundException {

		File destFile = File.createTempFile("docx", ".docx");
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
		template.write(outputStream);
		outputStream.flush();
		outputStream.close();

		return destFile;

	}

	/**
	 * Recupera dallo storage il documento identificato dall'uri specificato
	 * 
	 * @param docStorageUri
	 * @return
	 * @throws Exception
	 */
	protected XWPFDocument retrieveDocument(String docStorageUri) throws Exception {
		
		TemplateStorage templateStorage = TemplateStorageFactory.getTemplateStorageImpl();

		InputStream lInputStream = templateStorage.extract(docStorageUri);
		BufferedInputStream fis = new BufferedInputStream(lInputStream);
		XWPFDocument template = new XWPFDocument(fis);

		fis.close();

		return template;
	}

	/**
	 * Genera una rappresentazione java dell'xml fornito come stringa
	 * 
	 * @param datiModelloXml
	 * @return
	 * @throws JAXBException
	 */
	protected SezioneCache buildXml(String datiModelloXml) throws JAXBException {

		SezioneCache retValue = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(datiModelloXml));
		return retValue;
	}
}
