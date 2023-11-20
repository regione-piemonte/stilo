/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.Marshaller;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciSetattrdinamiciBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttributiDinamiciDocBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CellXlsBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DatiExcelBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DatiXlsInErrorBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EstremiProtocolloBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImportDatiFromExcelBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.RowXlsBean;
import it.eng.client.DmpkAttributiDinamiciSetattrdinamici;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id="AttributiDinamiciDocDatasource")
public class AttributiDinamiciDocDatasource extends AbstractServiceDataSource<AttributiDinamiciDocBean, AttributiDinamiciDocBean>{

	private static final Logger logger = Logger.getLogger(AttributiDinamiciDocDatasource.class);
	private static final String IMPORTO_PATTERN = "#,##0.00";
	
	public boolean inBold = false;
	public boolean inItalic = false;
	public boolean inUnderLine=false;
	    
	@Override
	public AttributiDinamiciDocBean call(AttributiDinamiciDocBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		String flgIgnoreObblig = getExtraparams().get("flgIgnoreObblig");
		
		DmpkAttributiDinamiciSetattrdinamiciBean lBean = new DmpkAttributiDinamiciSetattrdinamiciBean();
		lBean.setCodidconnectiontokenin(token);
		lBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		lBean.setNometabellain("DMT_DOCUMENTS");
		lBean.setRowidin(pInBean.getRowId());
		lBean.setActivitynamewfin(pInBean.getActivityNameWF());
		lBean.setEsitoactivitywfin(pInBean.getEsitoActivityWF());
		
		if(StringUtils.isNotBlank(flgIgnoreObblig)) {
			lBean.setFlgignoraobbligin(new Integer(flgIgnoreObblig));
		}
		
		if(pInBean.getValori() != null && pInBean.getValori().size() > 0 && pInBean.getTipiValori() != null && pInBean.getTipiValori().size() > 0) {
			SezioneCache lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, pInBean.getValori(), pInBean.getTipiValori(), getSession());			
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			lBean.setAttrvaluesxmlin(lStringWriter.toString());
		}
		
		DmpkAttributiDinamiciSetattrdinamici store = new DmpkAttributiDinamiciSetattrdinamici();
		StoreResultBean<DmpkAttributiDinamiciSetattrdinamiciBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()){
			throw new StoreException(result);
		}
		
		return pInBean;
	}

	public ImportDatiFromExcelBean importaDatiFromExcel(ImportDatiFromExcelBean bean) throws Exception {
		
		List<HashMap<String, Object>> valoriAttrLista = new ArrayList<HashMap<String, Object>>();
		
		/*
		if (bean.getValoriAttrLista() != null && bean.getValoriAttrLista().size() > 0) {
			valoriAttrLista = bean.getValoriAttrLista();
		}
		*/
		
		String uriExcel = bean.getUriExcel(); 
		String mimeType = bean.getMimeType(); 
		
		List<DettColonnaAttributoListaBean> dettAttrLista = bean.getDettAttrLista();
		
		// leggo i dati dal foglio excel
		DatiExcelBean datiExcel = leggiDatiFromExcel(uriExcel,mimeType, dettAttrLista);
		
		
		// copio i dati del foglio exel nel bean di out
		ImportDatiFromExcelBean out = new ImportDatiFromExcelBean();
			
		for (RowXlsBean lRowXlsBean : datiExcel.getListaDatiXls()) {
			HashMap<String, Object> lMap = new HashMap<String, Object>();
			int i=0;
			for (CellXlsBean lCellXlsBean : lRowXlsBean.getListaCellXls()) {
				DettColonnaAttributoListaBean lDettColonnaAttributoListaBean = dettAttrLista.get(i);
				String nomeCella = lDettColonnaAttributoListaBean.getNome();
				lMap.put(nomeCella, lCellXlsBean.getValoreCella());
				i++;
			}
			valoriAttrLista.add(lMap);		
		}
		out.setValoriAttrLista(valoriAttrLista);
		out.setListaDatiXlsInError(datiExcel.getListaDatiXlsInError());
		
		return out;
	}
	
	public DatiExcelBean leggiDatiFromExcel(String uriExcel,String mimeType, List<DettColonnaAttributoListaBean> dettAttrLista) throws Exception {
		
		DatiExcelBean datiExcel = new DatiExcelBean();
		
		try {

			boolean isXls = mimeType.equals("application/excel");
			boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			/**
			 * Implementazione HSSF (Horrible SpreadSheet Format): indica un'API che funziona con Excel 2003 o versioni precedenti.
			 */
			
			if (isXls) {
				datiExcel = caricaDatiFromXls(uriExcel, dettAttrLista);
			}
			
			/**
			 * Implementazione XSSF (XML SpreadSheet Format): indica un'API che funziona con Excel 2007 o versioni successive.
			 */
			else if (isXlsx) { 
				datiExcel = caricaDatiFromXlsx(uriExcel, dettAttrLista);
			} 
			
			else {
				String message = "Il formato del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi";
				logger.error(message);

				throw new StoreException(message);
			}
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file, si è verificata la seguente eccezione: " + errorMessage;
			logger.error(message, e);

			throw new StoreException(message); 
		}
		return datiExcel;
	}
	
	private DatiExcelBean caricaDatiFromXlsx(String uriExcel, List<DettColonnaAttributoListaBean> dettAttrLista) throws Exception {
		
		FileInputStream fis = null;
		List<RowXlsBean> listaRowXls = new ArrayList<RowXlsBean>();
		List<DatiXlsInErrorBean> listaDatiXlsInError = new ArrayList<DatiXlsInErrorBean>();
		
		DatiExcelBean out = new DatiExcelBean();
		
		try{
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
			fis = new FileInputStream(document); 
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0); 
			
		    int rows = sheet.getLastRowNum();    // Numero di righe
		    
		    for(int r = 0; r < rows +1; r++) {
		    	Row row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {
					List<CellXlsBean> listaCellXls = new ArrayList<CellXlsBean>();
					/**CONTROLLO SE C'E' L'INTESTAZIONE
					FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
					if(row.getCell(0)!=null && formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType()==Cell.CELL_TYPE_STRING 
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Movimento U/E")) {
						continue;
					}
					*/
					
					FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
					if(row.getCell(0)!=null && formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType()==Cell.CELL_TYPE_STRING 
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase(dettAttrLista.get(0).getLabel())) {
						continue;
					}
					
					try {
						    // leggo una riga 
							RowXlsBean rowXlsBean = new RowXlsBean();
							
							for (int c = 0; c < dettAttrLista.size(); c++) {
								CellXlsBean cellXlsBean = new CellXlsBean();
								Cell cell = row.getCell(c);       		// leggo una cella
								
								String valueCell = "";
								if (cell != null  && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK ) {
									try {
										// Controllo se il valore della cella e' formalmente corretto e lo converto nel formato atteso
										valueCell = getValueCell(formulaEvaluator, cell, r, c, dettAttrLista.get(c), wb);
									} catch (Exception e) {
										throw new Exception(e.getMessage());
									}
								} else{
									// Se e' obbligatoria restituisco errore
									if(dettAttrLista.get(c).getObbligatorio().equalsIgnoreCase("1")){
										throw new ValidatorException("Il valore corrispondente alla riga " + (r + 1 ) + " e colonna " + (c + 1) + " è mancante ed è obbligatorio.");
									}
								}
								cellXlsBean.setValoreCella(valueCell);
								listaCellXls.add(cellXlsBean);								
							}
							rowXlsBean.setListaCellXls(listaCellXls);  // Aggiungo le colonne alla riga 
							listaRowXls.add(rowXlsBean);      		   // Aggiungo la riga alla lista
					} catch (Exception e) {
						DatiXlsInErrorBean erroreRiga = new DatiXlsInErrorBean();
						erroreRiga.setNumeroRiga(String.valueOf(r+1));
						erroreRiga.setMotivo(e.getMessage());
						listaDatiXlsInError.add(erroreRiga);
					}
				}		        
		    }
		}  
		catch(Exception e) { 
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(),e);
			}
			  
			throw e;
			
		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (Exception e) {}
			}
		}
		out.setListaDatiXls(listaRowXls);
		out.setListaDatiXlsInError(listaDatiXlsInError);
		return out;
	}
	
	private String getValueCell(FormulaEvaluator formulaEvaluator, Cell cell, int riga, int colonna, DettColonnaAttributoListaBean dettColonnaAttributoListaBean, Workbook wb) throws Exception {
		
		String valueCell = null;
		
		// Controllo se e' un testo
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("TEXT")){
			valueCell = normalizeTesto(cell, riga, colonna);
		}
		
		// Controllo se e' un html
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("CKEDITOR")){
			valueCell = normalizeRichTesto(cell, riga, colonna, wb);
		}
		
		// Controllo se e' un importo 
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("INTEGER")){
			valueCell = normalizeInteger(formulaEvaluator, cell, riga, colonna);
		}
		
		// Controllo se e' un importo 
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("EURO")){
			valueCell = normalizeImporto(formulaEvaluator, cell, riga, colonna);
		}
		
		// Controllo se e' una data 
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("DATE")){
			valueCell = normalizeData(cell, riga, colonna);
			
		}
		// Controllo se e' una data e ora 
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("DATETIME")){
			valueCell = normalizeDataOra(cell, riga, colonna);
		}
		
		// Controllo se e' una lista di documenti (e' una stringa del tipo Sigla-Anno-Numero o loro concatenazioni separate da “;” o “,” o carriage return)
		if (dettColonnaAttributoListaBean.getTipo().equalsIgnoreCase("DOCUMENTLIST")){
			//valueCell = normalizeDocumentList(cell, riga, colonna);
			valueCell = normalizeTesto(cell, riga, colonna);
		}
				
		
		return valueCell;
	}

	
	
	public String normalizeDocumentList(Cell cell, int riga, int colonna) throws Exception {
		try {			
			cell.setCellType(Cell.CELL_TYPE_STRING);			
			
			// E' una stringa che contiene la lista di documenti nel formato Sigla-Anno-Numero o loro concatenazioni separate da ";" o "," o carriage return
			// Estraggo la lista dei documenti (Sigla-Anno-Numero)
			String stringCellValue = cell.getStringCellValue();
			
			String stringOut = "";
			if (stringCellValue!=null && !stringCellValue.equalsIgnoreCase("")){
				List<EstremiProtocolloBean> listaDocumenti = getListaDocumenti(stringCellValue);
				
				// Restituisco la lista di IDUD separati da ";"
				if (listaDocumenti!=null && listaDocumenti.size()>0){
					 String[] listaIdUdFromLista = getListaIdUdFromListaEstremiProtocollo(listaDocumenti);
					 List<String> listaIdUd = new ArrayList<String>();
					 listaIdUd = Arrays.asList(listaIdUdFromLista);
					 // Concateno gli idud
					 if (listaIdUd!=null && listaIdUd.size()>0){
						 for (int i = 0; i < listaIdUd.size(); i++) {
							 String idUd = listaIdUd.get(i);
							 stringOut =  stringOut + idUd + ";";		 
						 }
					 }
				}
			}
            return stringOut;
			
		} catch (Exception e) {
			logger.error(e);
			throw new ValidatorException("Il valore inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
		}
	}
	
	public List<EstremiProtocolloBean> getListaDocumenti(String listaDocumentiIn) throws Exception {
		
		List<EstremiProtocolloBean> listaDocumentiOut = new ArrayList<EstremiProtocolloBean>();
		
		// Estraggo le stringhe separate da ";" o "," o carriage return		
		String[] lListaEstremiProtocolli = EstremiProtocolloSplitter.splitListaEstremiProtocolli(listaDocumentiIn);
		List<String> listEstremiProtocollo = new ArrayList<String>();
		listEstremiProtocollo = Arrays.asList(lListaEstremiProtocolli);
		
		// Da ciascuna stringa estraggo Sigla-Anno-Numero
		if (listEstremiProtocollo!=null && listEstremiProtocollo.size()>0){
			for (int i = 0; i < listEstremiProtocollo.size(); i++) {
				String estremiProtocollo = listEstremiProtocollo.get(i);
				
				String[] lEstremiProtocolli = EstremiProtocolloSplitter.splitEstremiProtocollo(estremiProtocollo);
				List<String> lEstremiProtocollo = new ArrayList<String>();
				lEstremiProtocollo = Arrays.asList(lEstremiProtocolli);
				
				EstremiProtocolloBean lEstremiProtocolloBean = new EstremiProtocolloBean();
				String siglaProtocollo = null;
				String annoProtocollo = null;
				String nroProtocollo = null;
				
				// Estraggo la SIGLA
				if (lEstremiProtocollo.size()>0)
				   siglaProtocollo = lEstremiProtocollo.get(0);
				
				//Estraggo l'ANNO
				if (lEstremiProtocollo.size()>1)
					annoProtocollo = lEstremiProtocollo.get(1);
				
				// Estraggo il NUMERO
				if (lEstremiProtocollo.size()>2)
					nroProtocollo = lEstremiProtocollo.get(2);
				
				lEstremiProtocolloBean.setSiglaProtocollo(siglaProtocollo);
				lEstremiProtocolloBean.setAnnoProtocollo(annoProtocollo);
				lEstremiProtocolloBean.setNroProtocollo(nroProtocollo);
				
				listaDocumentiOut.add(lEstremiProtocolloBean);
			}
		}		
		return listaDocumentiOut;		
	}
	
	// DA FARE ...
	public String[] getListaIdUdFromListaEstremiProtocollo(List<EstremiProtocolloBean> listaDocumentiIn) {
		
		Collection<String> listaIdUdOut = new ArrayList<String>();
		
		if (listaDocumentiIn!=null && listaDocumentiIn.size()>0){
		    // Trovo l'idud ... 
			String idUd ="111111";
			listaIdUdOut.add(idUd);
		}
		
		return listaIdUdOut.toArray(new String[0]);
	}
	
	public String normalizeTesto(Cell cell, int riga, int colonna) throws Exception {
		
		try {			
			cell.setCellType(Cell.CELL_TYPE_STRING);			
			String stringCellValue = cell.getStringCellValue();
			return stringCellValue;
		} catch (Exception e) {
			logger.error(e);
			throw new ValidatorException("Il valore inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
		}
	}
	
	public String normalizeRichTesto(Cell cell, int riga, int colonna, Workbook wb) throws Exception {
		
		String stringCellValue = null;
		
		try {	
			if(cell.getStringCellValue() != null && cell.getStringCellValue().trim().startsWith("<") && cell.getStringCellValue().trim().endsWith(">")) {
				stringCellValue = cell.getStringCellValue();
			} else {
				RichTextStringToHtm lRichTextStringToHtm = new RichTextStringToHtm(cell);
				stringCellValue = lRichTextStringToHtm.getHtmlValue();
			}
			return stringCellValue;
			
		} catch (Exception e) {
			logger.error(e);
			throw new ValidatorException("Il valore inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
		}
	}
	
	public String normalizeDataOra(Cell cell, int riga, int colonna) throws Exception {
		try {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				String pattern = "dd/MM/yyyy HH:mm";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				return simpleDateFormat.format(cell.getDateCellValue());
			} else {
				throw new ValidatorException("Il valore della data inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
			}
		} catch (Exception e) {
			if (cell.getCellType()==cell.CELL_TYPE_STRING) {
				if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
					try {
						String pattern = "dd/MM/yyyy HH:mm";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						Date parse = simpleDateFormat.parse(cell.getStringCellValue());
						return simpleDateFormat.format(parse);
					} catch (ParseException e1) {
						logger.error(e1);
						throw new ValidatorException("Errore durante la conversione della data");
					}
				} else {
					return null;
				}
			} else {
				logger.error(e);
				throw new ValidatorException("Il valore della data inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
			}
		}
	}
	
	public String normalizeData(Cell cell, int riga, int colonna) throws Exception {
		try {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				String pattern = "dd/MM/yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				return simpleDateFormat.format(cell.getDateCellValue());
			} else {
				throw new ValidatorException("Il valore della data inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
			}
		} catch (Exception e) {
			if (cell.getCellType()==cell.CELL_TYPE_STRING) {
				if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
					try {
						String pattern = "dd/MM/yyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						Date parse = simpleDateFormat.parse(cell.getStringCellValue());
						return simpleDateFormat.format(parse);
					} catch (ParseException e1) {
						logger.error(e1);
						throw new ValidatorException("Errore durante la conversione della data");
					}
				} else {
					return null;
				}
			} else {
				logger.error(e);
				throw new ValidatorException("Il valore della data inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
			}
		}
	}
	
	public String normalizeImporto(FormulaEvaluator formulaEvaluator, Cell cell, int riga, int colonna) throws Exception {
		try {
			DecimalFormat format = (DecimalFormat)NumberFormat.getNumberInstance(Locale.ITALIAN);
			format.applyPattern(IMPORTO_PATTERN);
			if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
				double valoreImportoDouble = cell.getNumericCellValue();
				return String.valueOf(format.format(new Double(valoreImportoDouble)));	
			} else {
				if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
					String valoreImportoString = cell.getStringCellValue();
					String importoDaValutare = valoreImportoString.replaceAll("\\.", "");
					if (importoDaValutare.contains(",")) {
						importoDaValutare = importoDaValutare.replaceAll(",", ".");
					}
					double parseDoubleImporto = Double.parseDouble(importoDaValutare);
					return format.format(parseDoubleImporto);
				} else if ("".equals(cell.getStringCellValue())) {
					return null;
				} else {
					throw new ValidatorException("Il valore dell'importo inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new ValidatorException("Il valore dell'importo inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
		}
	}
	
	public String normalizeInteger(FormulaEvaluator formulaEvaluator, Cell cell, int riga, int colonna) throws Exception {
		try {
			if(formulaEvaluator.evaluateInCell(cell).getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
				return String.valueOf((int) cell.getNumericCellValue());	
			} else {
				if (cell.getStringCellValue()!=null && !"".equals(cell.getStringCellValue())) {
					String valoreString = cell.getStringCellValue();					
					int num = Integer.parseInt(valoreString);
					return String.valueOf(num);					
				} else if ("".equals(cell.getStringCellValue())) {
					return null;
				} else {
					throw new ValidatorException("Il valore dell'intero inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new ValidatorException("Il valore dell'intero inserito in riga " + (riga + 1 ) + " e colonna " + (colonna + 1) + " non risulta essere valido" );
		}
	}
	
	private DatiExcelBean caricaDatiFromXls(String uriExcel,List<DettColonnaAttributoListaBean> dettAttrLista) throws Exception {
		BufferedInputStream documentStream = null;
		InputStream fis = null;
		List<RowXlsBean> listaRowXls = new ArrayList<RowXlsBean>();
		List<DatiXlsInErrorBean> listaDatiXlsInError = new ArrayList<DatiXlsInErrorBean>();
		DatiExcelBean out = new DatiExcelBean();
		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);

			fis = new FileInputStream(document);
			documentStream = new BufferedInputStream(fis);
			HSSFWorkbook wb = new HSSFWorkbook(documentStream);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
		
			int rows = sheet.getPhysicalNumberOfRows();   // Numero di righe

			for (int r = 0; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {

				    List<CellXlsBean> listaCellXls = new ArrayList<CellXlsBean>();
				
					/** CONTROLLO SE C'E' L'INTESTAZIONE 
					FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
					if (row.getCell(0) != null
							&& formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType() == Cell.CELL_TYPE_STRING
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Movimento U/E")) {
						continue;
					}
					*/

					FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
					
					if (row.getCell(0) != null
							&& formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType() == Cell.CELL_TYPE_STRING
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase(dettAttrLista.get(0).getLabel())) {
						continue;
					}
					
					try {
					      // leggo una riga 
						  RowXlsBean rowXlsBean = new RowXlsBean();
						  for (int c = 0; c < dettAttrLista.size(); c++) {
								CellXlsBean cellXlsBean = new CellXlsBean();
								HSSFCell cell = row.getCell(c);       		// leggo una cella
								String valueCell = "";
								if (cell != null  && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK ) {
									try {
										// Controllo se il valore della cella e' formalmente corretto e lo converto nel formato atteso
										valueCell = getValueCell(formulaEvaluator, cell, r, c, dettAttrLista.get(c), wb);
									} catch (Exception e) {
										throw new Exception(e.getMessage());
									}
								} else{
									// Se e' obbligatoria restituisco errore
									if(dettAttrLista.get(c).getObbligatorio().equalsIgnoreCase("1")){
										throw new ValidatorException("Il valore corrispondente alla riga " + (r + 1 ) + " e colonna " + (c + 1) + " è mancante ed è obbligatorio.");
									}										
								} 
								cellXlsBean.setValoreCella(valueCell);
								listaCellXls.add(cellXlsBean);								
							}
							rowXlsBean.setListaCellXls(listaCellXls);  // Aggiungo le colonne alla riga 
							listaRowXls.add(rowXlsBean);      		   // Aggiungo la riga alla lista

					} catch (Exception e) {
						DatiXlsInErrorBean erroreRiga = new DatiXlsInErrorBean();
						erroreRiga.setNumeroRiga(String.valueOf(r+1));
						erroreRiga.setMotivo(e.getMessage());
						listaDatiXlsInError.add(erroreRiga);
					}
				}
			}
		} catch (Exception e) {
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(), e);
			}

			throw e;

		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
			if (documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error("Impossibile chiudere lo stream legato al documento a causa della seguente eccezione ",
							e);
				}
			}
		}
		out.setListaDatiXls(listaRowXls);
		out.setListaDatiXlsInError(listaDatiXlsInError);
		return out;
	}
	
	public static boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
}