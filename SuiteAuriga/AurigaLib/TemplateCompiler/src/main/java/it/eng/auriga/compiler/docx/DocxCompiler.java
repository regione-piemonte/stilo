/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import it.eng.auriga.compiler.AbstractCompiler;
import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.compiler.utility.TemplateStorageFactory;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;

/**
 * Gestisce la creazione di una istanza di documento a partire dal modello e dai
 * dati recuperati dal db
 * 
 * @author massimo malvestio
 *
 */
public class DocxCompiler extends AbstractCompiler {

	// template in cui iniettare i dati
	private XWPFDocument template;

	// lista delle tabelle
	private List<XWPFTable> tables;

	// mappa i contenuti dei paragrafi ai paragrafi stessi, in maniera da
	// agevolare la ricerca dei placeholder, i quali potrebbero essere splittati
	// su più run dello stesso paragraph
	// In tal caso la ricerca per singolo run non funzionerebbe, in quanto
	// conterrebbe soltanto parte del placeholder
	private Map<String, XWPFParagraph> paragraphsContent;

	/**
	 * 
	 * @param datiModelloXml
	 *            stringa contenente la rappresentazione xml della SezioneCache
	 *            contenente i dati da iniettare
	 * @param docStorageUri
	 *            uri dello storage che rappresenta il template in cui iniettare
	 *            i dati
	 * @throws Exception
	 */
	public DocxCompiler(String datiModelloXml, String docStorageUri) throws Exception {

		template = retrieveDocument(docStorageUri);

		buildXml(datiModelloXml);

		// tutte le tabelle presenti nel documento
		tables = template.getTables();

		// mappo i valori semplici e quelli complessi
		mapSezioneCache();

		extractParagraphsContent();
	}

	/**
	 * 
	 * @param template
	 *            documento di modello in cui iniettare i dati
	 * @param variables
	 *            valori da iniettare nel modello
	 * @throws Exception
	 */
	public DocxCompiler(XWPFDocument template, SezioneCache variables) throws Exception {

		this.template = template;

		this.variables = variables;

		// tutte le tabelle presenti nel documento
		tables = template.getTables();

		// mappo i valori semplici e quelli complessi
		mapSezioneCache();

		extractParagraphsContent();

	}

	@Override
	protected File saveTemplateOnFile() throws IOException, FileNotFoundException {

		File destFile = File.createTempFile("docx", ".docx");
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
		template.write(outputStream);
		outputStream.flush();
		outputStream.close();

		return destFile;
	}

	/**
	 * Popola il modello ricercando le variabili definite nella SezioneCache
	 * specificata, rimpiazzandole con i relativi valori
	 */
	@Override
	protected void populate() {

		// Per prima cosa rimuovo i checkbox non selezionati, per evitare falsi
		// positivi nelle iniezioni successive
		// essendo dei dati semplici all'interno di una tabella
		removeUncheckedValues(tables);

		// inietto i valori semplici
		injectSimpleValues();

		// inietto i valori complessi
		injectTableValues();
	}

	/**
	 * Rimuove i valori relativi ai radio buttons non selezionati. Allo stato
	 * attuale tali valori sono organizzati mediante tabelle, in cui nella
	 * colonna 0 c'è la stringa di localizzazione del valore, nella colonna 1 la
	 * chiave mediante la quale recuperare dalla SezioneCache il relativo
	 * valore.
	 * 
	 * @param valoriSempliciMap
	 * @param tables
	 */
	private void removeUncheckedValues(List<XWPFTable> tables) {

		for (XWPFTable currentTable : tables) {

			// se la tabella contiene la variabile, allora, se la variabile è
			// valorizzata rimuovo la variabile stessa,
			// altrimenti rimuovo l'intera riga

			for (Entry<String, String> currentEntry : valoriSemplici.entrySet()) {

				String variableName = currentEntry.getKey();
				String variableValue = currentEntry.getValue();

				// trasformo il nome variabile in DEL{nome_variabile}
				variableName = String.format("DEL{%0$s}", variableName);

				// se il checkbox è stato selezionato allora vale 1 allora devo
				// solo eliminare il nome della variabile dalla cella
				if (variableValue != null && !variableValue.isEmpty() && variableValue.equals("1")) {

					// scansione delle righe della tabella alla ricerca di
					// quella contenente la variabile da eliminare
					for (XWPFTableRow currentRow : currentTable.getRows()) {

						// cella presente in posizione 1 che contiene la
						// variabile
						XWPFTableCell currentCell = currentRow.getCell(1);

						String cellContent = currentCell.getText();

						// la cella contiene la variabile ed il valore è pari a
						// 1 => checkbox selezionata, allora rimuovo solo la
						// variabile
						if (cellContent != null && cellContent.contains(variableName)) {

							XWPFParagraph cellParagraph = currentCell.getParagraphs().get(0);

							// setText di cell non sembra funzionare
							for (int i = cellParagraph.getRuns().size() - 1; i > -1; i--) {

								cellParagraph.removeRun(i);

							}
							break;

						}
					}
				} else {

					// il valore è null oppure 0 => la checkbox deve essere
					// eliminata, ovvero deve essere eliminata l'intera riga
					// per la tabella corrente scorro una ad una le righe
					for (int pos = 0; pos < currentTable.getNumberOfRows(); pos++) {

						// riga corrente
						XWPFTableRow currentRow = currentTable.getRow(pos);

						// cella che contiene il nome della variabile
						XWPFTableCell currentCell = currentRow.getCell(1);

						// contenuto della cella
						String cellContent = currentCell.getText();

						if (cellContent != null && cellContent.contains(variableName)) {

							// se la cella contiene la variabile allora rimuovo
							// l'intera riga
							currentTable.removeRow(pos);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Estrae il contenuto della cella di colonna 1 della riga di cui è stata
	 * specificata la posizione
	 * 
	 * @param currentTable
	 * @param rowPos
	 * @return
	 */
	protected String extractRemovableCellContent(XWPFTable currentTable, int rowPos) {
		XWPFTableRow currentRow = currentTable.getRow(rowPos);

		XWPFTableCell currentCell = currentRow.getCell(1);

		List<XWPFParagraph> paragraphs = currentCell.getParagraphs();

		String cellContent = null;
		if (!paragraphs.isEmpty() && !paragraphs.get(0).getRuns().isEmpty()) {

			cellContent = paragraphs.get(0).getText();

		}
		return cellContent;
	}

	protected void injectTableValues() {

		for (Entry<String, Lista> currentEntry : valoriComplessi.entrySet()) {

			// la chiave è il placeholder
			String currentKey = currentEntry.getKey();

			List<Riga> values = currentEntry.getValue().getRiga();

			for (XWPFTable currentTable : tables) {

				// per convenzione assumo che la prima cella della prima riga,
				// ad eccezione dell'intestazione,
				// contenga la chiave associata ai dati
				if (currentTable.getText().contains(currentKey)) {

					List<XWPFTableRow> rows = currentTable.getRows();

					int colsNumber = rows.get(0).getTableCells().size();

					int rowPosition = 0;

					for (XWPFTableRow currentRow : rows) {

						List<XWPFTableCell> rowCells = currentRow.getTableCells();

						if (rowCells != null && !rowCells.isEmpty()) {

							// ho trovato la riga contenente il placeholder di
							// interesse
							if (rowCells.get(0).getText().contains(currentKey)) {

								break;

							}

							rowPosition++;
						}
					}

					// se maggiore di -1 indica la riga in cui è stato trovato
					// il placehoder
					if (rowPosition > 0) {

						// aggiungo alla tabella tante righe quante sono le
						// righe di dati da mostrare
						for (int i = 0; i < values.size(); i++) {

							XWPFTableRow newRow = currentTable.createRow();
							injectRowData(newRow, values.get(i), colsNumber);
						}
						currentTable.removeRow(rowPosition);
					}
				}
			}
		}
	}

	/**
	 * Inietta i dati nella prima riga, non l'header, la quale contiene la
	 * chiave utilizzata nella ricerca per capire se c'è un placeholder da
	 * rimpiazzare con i dati specificati nella SezioneCache
	 * 
	 * @param tableRow
	 *            prima riga della tabella che deve contenere dati
	 * @param firstRiga
	 *            prima riga estratta da SezioneCache
	 */
	protected void injectRowData(XWPFTableRow tableRow, Riga firstRiga, int colsNumber) {

		Map<Integer, String> colValues = new HashMap<Integer, String>();

		for (Colonna currentColonna : firstRiga.getColonna()) {
			colValues.put(currentColonna.getNro().intValue() - 1, currentColonna.getContent());
		}

		for (int i = 0; i < colsNumber; i++) {

			String value = colValues.get(i);

			if (value == null) {
				value = " ";
			}

			setCellValue(value, tableRow, i);

		}
	}

	/**
	 * @param currentColumnValue
	 * @param tableRow
	 * @param rowCellIndex
	 */
	protected void setCellValue(String currentColumnValue, XWPFTableRow tableRow, int rowCellIndex) {
		XWPFTableCell currentCell = tableRow.getCell(rowCellIndex);

		List<XWPFParagraph> paragraphs = currentCell.getParagraphs();

		List<XWPFRun> cellRuns = paragraphs.get(0).getRuns();

		if (cellRuns.isEmpty()) {

			currentCell.setText(currentColumnValue);

		} else {

			for (int cellIndex = cellRuns.size() - 1; cellIndex > 0; cellIndex--) {
				paragraphs.get(0).removeRun(cellIndex);
			}

			cellRuns.get(0).setText(currentColumnValue, 0);

		}
	}

	/**
	 * Rimpiazza i placeholder presenti nel documento con il relativo valore
	 * eventualmente specificato
	 * 
	 * @param valoriSempliciMap
	 * @param runs
	 */
	protected void injectSimpleValues() {

		for (Entry<String, String> currentEntry : valoriSemplici.entrySet()) {

			String variableName = currentEntry.getKey();
			String variableValue = currentEntry.getValue();

			// per prima cosa ricerco i placeholder tra i paragrafi
			for (Entry<String, XWPFParagraph> currentParContent : paragraphsContent.entrySet()) {

				String parContent = currentParContent.getKey();

				XWPFParagraph paragraph = currentParContent.getValue();

				// se il contenuto del paragrafo contiene il placeholder, allora
				// rimpiazzo il contenuto
				if (parContent.contains(variableName)) {

					String actualParValue = paragraph.getText();

					// rimpiazzo il placeholder con il relativo valore
					actualParValue = actualParValue.replace(variableName, variableValue);

					// rimuovo tutti i run attualmente presenti tranne il primo,
					// in maniera tale da
					// 1) preservare gli stili impostati
					// 2) imporre come contenuto del run l'intera stringa.
					// Questo perchè una variabile potrebbe essere splittata tra
					// più run e quindi rimuovere i vari pezzi potrebbe essere
					// estremamente complicato
					for (int i = paragraph.getRuns().size() - 1; i > 0; i--) {

						paragraph.removeRun(i);

					}

					// nell'unico run rimasto, imposto il contenuto
					paragraph.getRuns().get(0).setText(actualParValue, 0);

				}
			}

			// il placeholder potrebbe essere presente anche in tabelle
			for (XWPFTable currentTable : tables) {

				// per convenzione assumo che la prima cella della prima riga,
				// ad eccezione dell'intestazione,
				// contenga la chiave associata ai dati
				if (currentTable.getText().contains(variableName)) {

					// righe della tabella - header e prima riga
					List<XWPFTableRow> tableRows = currentTable.getRows();

					// ci sono più righe da inserire, le inserisco
					for (XWPFTableRow tableRow : tableRows) {

						for (XWPFTableCell currentCell : tableRow.getTableCells()) {

							if (currentCell.getText().contains(variableName)) {

								List<XWPFParagraph> paragraphs = currentCell.getParagraphs();

								List<XWPFRun> cellRuns = paragraphs.get(0).getRuns();

								if (cellRuns.isEmpty()) {
									currentCell.setText(variableValue);
								} else {

									for (int cellIndex = cellRuns.size() - 1; cellIndex > 0; cellIndex--) {
										paragraphs.get(0).removeRun(cellIndex);
									}

									cellRuns.get(0).setText(variableValue, 0);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Estrae tutti i run di tutti i paragrafi presenti nel documento. Non
	 * vengono presi in considerazione quelli presenti nelle tabelle
	 * 
	 * @param template
	 * @return
	 */
	protected void extractParagraphsContent() {

		List<XWPFParagraph> paragraphs = template.getParagraphs();

		paragraphsContent = new LinkedHashMap<String, XWPFParagraph>();

		for (XWPFParagraph paragraph : paragraphs) {

			paragraphsContent.put(paragraph.getText(), paragraph);
		}

	}

	/**
	 * Estrae tutti i paragrafi dal template
	 * 
	 * @param template
	 * @return
	 */
	protected Paragraph[] extractParagraphs(HWPFDocument template) {

		Range paragraphsRange = template.getRange();

		Paragraph[] retValue = new Paragraph[paragraphsRange.numParagraphs()];

		for (int i = 0; i < paragraphsRange.numParagraphs(); i++) {

			retValue[i] = paragraphsRange.getParagraph(i);

		}

		return retValue;
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

		return template;
	}

}
