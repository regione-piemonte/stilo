/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import it.eng.utility.export.IExport;
import it.eng.utility.ui.module.core.server.bean.ExportBean;

public class ExportXLS implements IExport {
	
	private Boolean fromReport = false;

	@Override
	public void export(File file, ExportBean bean)throws Exception {
		
		//Informo il sistema che lavoro in modalità "headless" (senza environment grafico)
		System.setProperty("java.awt.headless", "true");
		
		int numeroColonne = 0;
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("RESULTS");
		HSSFFont font = wb.createFont();
		font.setFontName("Arial");
		font.setColor((short) 0);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFFont font2 = wb.createFont();
		font.setFontName("Arial");
		font.setColor((short)0);
		font.setFontHeightInPoints((short)10);
		// passiamo a creare uno stile per la singola cella
		
		// Costruzione titolo del report
		if(bean != null && bean.getIntestazioneReport() != null && !"".equals(bean.getIntestazioneReport())){
			
			HSSFCellStyle cellStyleTitleReport = wb.createCellStyle();
			cellStyleTitleReport.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cellStyleTitleReport.setFont(font);
			cellStyleTitleReport.setFillForegroundColor(new HSSFColor.BLACK().getIndex());
			HSSFRow rowReport = sheet.createRow(0);
	
			createCell(cellStyleTitleReport, rowReport, 0, StringEscapeUtils.unescapeHtml(bean.getIntestazioneReport()));
			
			fromReport = true;
		}
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// Allineamento
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setFont(font);
		// Colore testo
		cellStyle.setFillForegroundColor(new HSSFColor.BLACK().getIndex());
		HSSFCellStyle cellStyle2 = wb.createCellStyle();
		// Allineamento
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle2.setFont(font2);
		// Colore testo
		cellStyle2.setFillForegroundColor(new HSSFColor.BLACK().getIndex());
		//Creo le intestatzioni
		
		int offset = 0;
		
		if(bean.showHeaders()) {
			
			//Se sono state specificate delle printInfo, creo la riga intestazioni
			//e memorizzo tutte le colonne che devono essere stampate.
			//Creo la prima riga (l'intestazione)
			
			HSSFRow row = null;
			if( fromReport != null && fromReport){
				row = sheet.createRow(1);
				offset =  2;
			} else {
				row = sheet.createRow(0);
				offset =  1;
			}
			
			for (int c = 0; c < bean.getHeaders().length; c++) {		
				createCell(cellStyle, row, c, StringEscapeUtils.unescapeHtml(bean.getHeaders()[c]));					
			}
		}
		
		for (int r = 0; r < bean.getRecords().length; r++) {
			
			@SuppressWarnings("unchecked")
			Map<String,String> record = bean.getRecords()[r];			
			
			HSSFRow row = sheet.createRow(r + offset);
			
			for (int c = 0; c < bean.getFields().length; c++) {
				String value = record.get(bean.getFields()[c]);
				createCell(cellStyle2, row, c, StringEscapeUtils.unescapeHtml(value));					 															
			}
		}		
		
		//STE 15.01.08: effettuo ora il resize delle colonne
		for (int c = 0; c < bean.getFields().length; c++) {
			sheet.autoSizeColumn(c);
		}
		
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
	}

	/*
	 * Crea una singola riga del foglio excel completa di propriet� graficeh
	 * come bordi, allineamento, colore
	 */
	private static void createCell(HSSFCellStyle cellStyle, HSSFRow row, int column, Object value) {
	    // crea la cella ed imposta il valore
		HSSFCell cell = row.createCell(column);
		if(value instanceof Number){
			cell.setCellValue(((Number)value).doubleValue());
		}else{
			String valueS = "";
			if(value != null){
				valueS = value.toString();
			}
			HSSFRichTextString text = new HSSFRichTextString(valueS);
			cell.setCellValue(text);
		}
		cell.setCellStyle(cellStyle);
	}
}
