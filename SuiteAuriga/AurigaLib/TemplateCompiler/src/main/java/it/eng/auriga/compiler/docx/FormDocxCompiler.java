/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import it.eng.auriga.compiler.AbstractCompiler;
import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.compiler.utility.TemplateStorageFactory;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;

public class FormDocxCompiler extends AbstractCompiler {

	//template in cui iniettare i dati
	protected XWPFDocument template;
	
	//placeholders per stringhe semplici
	protected HashMap<String, CTSdtRun> availablePlaceholders;
	
	//placeholders per checkbox
	protected HashMap<String, CTSdtRun> availableCheckBoxPlaceholders;

	protected HashMap<String, XWPFTable> tables;
	
	//tabelle utilizzate come layout, k -> il tag, v -> la cella che contiene il tag
	protected HashMap<String, XWPFTableCell> layoutTables;
	
	public FormDocxCompiler(String datiModelloXml, String docStorageUri) throws Exception {

		template = retrieveDocument(docStorageUri);
		
		//converto la stringa in un oggetto di tipo SezioneCache
		buildXml(datiModelloXml);
		
		//mappo i valori semplici e quelli complessi
		mapSezioneCache();
		
	}
	
	public  FormDocxCompiler(XWPFDocument template, SezioneCache variables) throws Exception {

		this.template = template;
		
		this.variables = variables;
		
		//mappo i valori semplici e quelli complessi
		mapSezioneCache();
		
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

	@Override
	protected void populate() {
		
		//popolamento delle strutture di appoggio relative ai valori semplici
		availablePlaceholders = new HashMap<String, CTSdtRun>();
		
		availableCheckBoxPlaceholders = new HashMap<String, CTSdtRun>(); 
		
		layoutTables = new HashMap<String, XWPFTableCell>();
		
		for(XWPFParagraph currentPar : template.getParagraphs()) {
			
			List<CTSdtRun> runs = currentPar.getCTP().getSdtList();
			
			if(!runs.isEmpty()) {
				CTSdtRun taggedRun = runs.get(0);
				
				String tagValue = taggedRun.getSdtPr().getTagList().get(0).getVal();
				
				if(tagValue.contains(",")) {
					
					String[] tokens = tagValue.split(",");
					
					if(tokens[1].equals("checkbox")) {
						availableCheckBoxPlaceholders.put(tokens[0].trim(), taggedRun);
					}
					
				} else {
					
					availablePlaceholders.put(tagValue.trim(), taggedRun);
					
				}
			}
		}
		
		//popolamento della struttura di appoggio relativa alle tabelle
		tables = new HashMap<String, XWPFTable>();
		
		for (XWPFTable currentTable : template.getTables()) {
		
			//insieme delle righe della tabella
			List<XWPFTableRow> tableRows = currentTable.getRows();
			
			//prima cella della prima riga, usualmente si tratta dell'intestazione, ma se la tabella viene usata
			//per definire il layout, allora non contiene il tag che si suppone ci sia
			XWPFTableCell cell = tableRows.get(0).getCell(0);
			
			List<CTSdtRun> sdtList = cell.getCTTc().getPList().get(0).getSdtList();
			
			//tabella standard intestazione + riga vuota con tag nell'intestazione
			if(!sdtList.isEmpty()) {
			
				CTSdtPr paragraph = sdtList.get(0).getSdtPr();
		
				String tagValue = paragraph.getTagList().get(0).getVal();
				
				tables.put(tagValue, currentTable);
				
			} else {
				
				//la tabella viene utilizzata come layout, quindi i tag potrebbero essere ovunque
					
				for (XWPFTableCell currentCell : tableRows.get(0).getTableCells()) {
					
					sdtList = currentCell.getCTTc().getPList().get(0).getSdtList();
					
					//tabella standard intestazione + riga vuota con tag nell'intestazione
					if(!sdtList.isEmpty()) {
					
						CTSdtPr paragraph = sdtList.get(0).getSdtPr();
				
						String tagValue = paragraph.getTagList().get(0).getVal();
						
						layoutTables.put(tagValue, currentCell);
						
					}
				}
			}
		}
		
		//iniezione dei dati
		injectTableValues();
		
		injectParagraphsValues();
		
	}

	/**
	 * Recupera dallo storage il documento identificato dall'uri specificato
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

	private void injectTableValues() {
	
		for(String currentTableName : valoriComplessi.keySet()) {
			
			//dati da iniettare
			Lista currentLista = valoriComplessi.get(currentTableName);
			
			List<Riga> righe = currentLista.getRiga();
			
			if(!righe.isEmpty()) {
							
				//tabella in cui iniettare i dati
				XWPFTable currentTable = tables.get(currentTableName);
				
				if(currentTable != null) {
					
					List<XWPFTableRow> tableRows = currentTable.getRows();

					//numero di colonne della tabella
					int colsNumber = tableRows.get(0).getTableCells().size();
					
					//la tabella contiene più righe di quante debbano essere inserite, devo quindi rimuovere quelle in più, il contenuto delle altre verrà sovrascritto
					boolean resetTableRows = tableRows.size() - 1 > righe.size();
					
					//elimino tutte le righe successive alla prima
					if(resetTableRows) {
						
						//riga indice 0, header della tabella
						for(int rowIndex  = tableRows.size() -1; rowIndex > 1; rowIndex--) {
							
							currentTable.removeRow(rowIndex);
							
						}
						
					}
					
					//prima riga dell'oggetto specificato nella sezione cache associato alla tabella da iniettare
					Riga firstRow = righe.get(0);
				
					//prima riga della tabella che deve contenere dati, in posizione 0 si trova l'header
					XWPFTableRow firstTableRow = tableRows.get(1);
					
					injectRowData(firstTableRow, firstRow, colsNumber);
					
					//ci sono più righe da inserire, le inserisco
					for(int  i = 1; i < righe.size(); i++) {

						XWPFTableRow  newRow = currentTable.createRow();
						
						 injectRowData(newRow, righe.get(i), colsNumber);
					}
				}
			}			
		}		
	}
	
	private void injectParagraphsValues() {
		
		//faccio la scansione di tutti i valori semplici e se trovo dei placeholders compatibili, allora inietto il relativo valore
		for(Entry<String,String> currentEntry : valoriSemplici.entrySet()) {
			
			String currentKey = currentEntry.getKey();
			
			String currentValue = currentEntry.getValue();
			
			CTSdtRun currentRun = availablePlaceholders.get(currentKey);
			

			if(currentRun != null) {
				
				//si tratta di una stringa semplice da iniettare

				List<CTR> ctrList = currentRun.getSdtContent().getRList();
				
				CTR palceholderCtr = null;
				
				if(ctrList.isEmpty()) {
					palceholderCtr = currentRun.getSdtContent().addNewR();
					palceholderCtr.addNewT();
				} else {
					palceholderCtr = ctrList.get(0);
				}
				
				palceholderCtr.getTList().get(0).setStringValue(currentValue);
				
			} else {
				
				currentRun = availableCheckBoxPlaceholders.get(currentKey);
				
				if(currentRun != null) {
					
					List<CTR> ctrList = currentRun.getSdtContent().getRList();
					
					List<CTText> text = ctrList.get(0).getTList();
					
					//si tratta di un checkbox, devo quindi analizzare  il valore e se è pari ad 1 applicare il check
					if(currentValue.equals("1")) {
						
						text.get(0).setStringValue("☑");
						
					} else {
						
						text.get(0).setStringValue("☐");
						
					}
				} else {
					
					//la tabella viene utilizzata come layout
					XWPFTableCell currentCell = layoutTables.get(currentKey);
					
					if(currentCell != null) {
					
						//paragrafi presenti nella cella
						List<XWPFParagraph> paragraphs = currentCell.getParagraphs();
											
						XWPFParagraph referenceParagraph = paragraphs.get(0);
						
						//insieme dei run che contengono il testo
						List<XWPFRun> cellRuns = referenceParagraph.getRuns();
						
						if(cellRuns.isEmpty()) {
							
							//non ci sono run quindi la cella è vuota, inserisco il testo
							currentCell.setText(currentValue);
							
						} else {
							
							//rimuovo tutti i paragrafi ad eccezione del primo, posizione 0
							for(int paragraphIndex = paragraphs.size() - 1; paragraphIndex > 0; paragraphIndex--) {
								currentCell.removeParagraph(paragraphIndex);
							}
							
							//rimuovo tutti i run ad eccezione del primo, posizione 0
							for(int runPosition = cellRuns.size() -1; runPosition > 0; runPosition--) {
								referenceParagraph.removeRun(runPosition);
							}
							
							//la cella ha già dei contenuti, li sovrascrivo
							cellRuns.get(0).setText(currentValue, 0);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Inietta i dati nella prima riga, non l'header, la quale contiene la chiave utilizzata nella ricerca per capire 
	 * se c'è un placeholder da rimpiazzare con i dati specificati nella SezioneCache
	 * @param tableRow prima riga della tabella che deve contenere dati
	 * @param firstRiga prima riga estratta da SezioneCache
	 */
	protected void injectRowData(XWPFTableRow tableRow, Riga firstRiga, int colsNumber) {

		Map<Integer, String> colValues = new HashMap<Integer, String>();

		for(Colonna currentColonna : firstRiga.getColonna()) {
			colValues.put(currentColonna.getNro().intValue() - 1, currentColonna.getContent());
		}

		for (int i = 0; i < colsNumber; i++) {
			
			String value = colValues.get(i);
			
			if(value == null) {
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
				
		if(cellRuns.isEmpty()) {
			
			currentCell.setText(currentColumnValue);
			
		} else {
			
			for(int cellIndex = cellRuns.size() -1; cellIndex > 0; cellIndex--) {
				paragraphs.get(0).removeRun(cellIndex);
			}
			
			cellRuns.get(0).setText(currentColumnValue, 0);
			
		}
	}
	
}
