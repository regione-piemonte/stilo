package it.eng.applet.xls.util;

import it.eng.applet.PieChartApplet;
import it.eng.applet.dataset.bean.PieDataBean;
import it.eng.applet.panel.type.ChartType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.jfree.chart.JFreeChart;

public class XlsBuilder {

	private static int START_ROW = 1;
	
	
	public static void getXlsFromServer(File pFile, JFreeChart pJFreeChart, float width, float height, HashMap<Integer, PieDataBean> hashMap, ChartType type) 
	throws MalformedURLException, IOException{
		StringBuffer lStringBuffer = new StringBuffer();
		
		lStringBuffer.append("?numeroElementi=" + hashMap.size());
		lStringBuffer.append("&type=" + type.name());
		lStringBuffer.append("&title=" + URLEncoder.encode(pJFreeChart.getTitle().getText(), "UTF-8"));
//		if (type == ChartType.BAR){
//			lStringBuffer.append("&asseX=" + asseX);
//			lStringBuffer.append("&asseY=" + asseY);
//		}
		String lStrAttuale = new String(lStringBuffer.toString().getBytes(), "UTF-8");
		int count = 0;
		for (PieDataBean lPieDataBean : hashMap.values()){
			String label = URLEncoder.encode(lPieDataBean.getLabel(), "UTF-8");
			lStrAttuale += "&label" + count + "=" + label;
			lStrAttuale += "&percArrotondata" + count + "=" + lPieDataBean.getPercArrotondata();
			lStrAttuale += "&valore" + count + "=" + lPieDataBean.getValore();
//			System.out.println(label);
//			lStringBuffer.append("&valore" + count + "=" + URLEncoder.encode(lPieDataBean.getValore(), "UTF-8"));
//			lStringBuffer.append("&label" + count + "=" + label);
//			lStringBuffer.append("&percArrotondata" + count + "=" + lPieDataBean.getPercArrotondata());
			count++;
		}
		String url = PieChartApplet.getParameters().getXlsBuilderPercentageServlet() +lStrAttuale;
		FileOutputStream lFileOutputStream = new FileOutputStream(pFile);
		IOUtils.write(IOUtils.toByteArray(new URL(url).openStream()), lFileOutputStream);
		lFileOutputStream.flush();
		lFileOutputStream.close();
	}
	
	
//	public static void writeChartToXlsFile(File file, JFreeChart actualChart,
//			int width, int height, ChartType type, int level) throws IOException {
//		switch (type) {
//		case PIE:
//			createPieXls(file, actualChart);
//			break;
//		case BAR:
//			createBarXls(file, actualChart, level);
//			break;
//		}
//		
//		
//		
////		my_sheet.autoSizeColumn(0); //adjust width of the first column
////		my_sheet.autoSizeColumn(1);
//		
//	}
//
//	private static void createBarXls(File file, JFreeChart actualChart, int level) throws IOException {
//		InputStream chart_file_input = XlsBuilder.class.getClassLoader().getResourceAsStream("TemplateBar.xls");
//		/* Read chart data from XLSX Workbook */
//		HSSFWorkbook my_workbook = new HSSFWorkbook(chart_file_input);
//		/* Read worksheet that has pie chart input data information */
//		HSSFSheet my_sheet = my_workbook.getSheetAt(0);
//		CategoryPlot lCategoryPlot = (CategoryPlot) actualChart.getPlot();
//		CategoryDataset lCategoryDataset = lCategoryPlot.getDataset();
//		Row rowInit = retrieveOrCreateRow(-1, my_sheet);
//		Cell cellChi = retrieveOrCreateCell(rowInit, 0);
//		cellChi.setCellValue("Chi");
//		Cell cellConteggio = retrieveOrCreateCell(rowInit, 1);
//		cellConteggio.setCellValue("Tempo medio (ore)");
//		Cell cellPerc = retrieveOrCreateCell(rowInit, 2);
////		cellPerc.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
//		cellPerc.setCellValue("Perc.");
//		for (int i = 0; i<lCategoryDataset.getRowCount(); i++){
//			PieDataBean lPieDataBean =  PieChartApplet.mapValues.get(level).get(i);
//			Row row = retrieveOrCreateRow(i, my_sheet);
//			putLabelBar(row, lPieDataBean);
//			putValueBar(row, lPieDataBean);
//			putPercBar(row, lPieDataBean);
//		}
//		String title = actualChart.getTitle().getText();
//		Row row = my_sheet.getRow(5);
//		Cell cell = row.getCell(3);
//		cell.setCellValue(title);
//		FileOutputStream fileOut = new FileOutputStream(file);
//		my_workbook.write(fileOut);
//	    fileOut.close();
//		
//	}
//
//	private static void createPieXls(File file, JFreeChart actualChart) throws IOException {
//		InputStream chart_file_input = XlsBuilder.class.getClassLoader().getResourceAsStream("TemplatePie.xls");
//		/* Read chart data from XLSX Workbook */
//		HSSFWorkbook my_workbook = new HSSFWorkbook(chart_file_input);
//		/* Read worksheet that has pie chart input data information */
//		HSSFSheet my_sheet = my_workbook.getSheetAt(0);
//		PiePlot lPiePlot = (PiePlot) actualChart.getPlot();
//		PieDataset lPieDataset = lPiePlot.getDataset();
//		Row rowInit = retrieveOrCreateRow(-1, my_sheet);
//		Cell cellChi = retrieveOrCreateCell(rowInit, 0);
//		cellChi.setCellValue("Chi");
//		Cell cellConteggio = retrieveOrCreateCell(rowInit, 1);
//		cellConteggio.setCellValue("Conteggio");
//		Cell cellPerc = retrieveOrCreateCell(rowInit, 2);
////		cellPerc.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
//		cellPerc.setCellValue("%");
//		for (int i = 0; i<lPieDataset.getItemCount(); i++){
//			PieDataBean lPieDataBean = (PieDataBean)lPieDataset.getKey(i);
//			Row row = retrieveOrCreateRow(i, my_sheet);
//			putLabelPie(row, lPieDataBean);
//			putValuePie(row, lPieDataBean);
//			putPercPie(row, lPieDataBean);
//		}
//		String title = actualChart.getTitle().getText();
//		Row row = my_sheet.getRow(5);
//		Cell cell = row.getCell(3);
//		cell.setCellValue(title);
//		FileOutputStream fileOut = new FileOutputStream(file);
//		my_workbook.write(fileOut);
//	    fileOut.close();
//	}
//
//	private static void putPercPie(Row row, PieDataBean lPieDataBean) {
//		Cell cell = retrieveOrCreateCell(row, 2);
//		cell.setCellValue(lPieDataBean.getPercArrotondata());
//	}
//
//	private static void putValuePie(Row row, PieDataBean lPieDataBean) {
//		Cell cell = retrieveOrCreateCell(row, 1);
//		cell.setCellValue(lPieDataBean.getValore());
//	}
//	
//	private static Cell retrieveOrCreateCell(Row row, int i) {
//		Cell cell = row.getCell(i);
//		if (cell == null){
//			cell = row.createCell(i);
//		}
//		return cell;
//	}
//
//	private static void putLabelPie(Row row, PieDataBean lPieDataBean) {
//		Cell cell = retrieveOrCreateCell(row, 0);
//		float lFloat = BarChartBuilder.getFloatValue(lPieDataBean.getValore());
//		double lLong = (Math.round(lFloat*100.0)/100.0);
//		String legend = lPieDataBean.getLabel() + " - Conteggio : " + lLong + " (" + lPieDataBean.getPercArrotondata() + "%)";
//		cell.setCellValue(legend);
//	}
//	
//	private static void putLabelBar(Row row, PieDataBean lPieDataBean) {
//		Cell cell = retrieveOrCreateCell(row, 0);
//		float lFloat = BarChartBuilder.getFloatValue(lPieDataBean.getValore());
//		double lLong = (Math.round(lFloat*100.0)/100.0);
//		String legend = lPieDataBean.getLabel();
//		cell.setCellValue(legend);
//	}
//	
//	private static void putValueBar(Row row, PieDataBean lPieDataBean) {
//		Cell cell = retrieveOrCreateCell(row, 1);
//		float lFloat = BarChartBuilder.getFloatValue(lPieDataBean.getValore());
//		double lLong = (Math.round(lFloat*100.0)/100.0);
//		cell.setCellValue(lLong);
//	}
//	
//	private static void putPercBar(Row row, PieDataBean lPieDataBean) {
//		Cell cell = retrieveOrCreateCell(row, 2);
//		cell.setCellValue(lPieDataBean.getPercArrotondata());
//	}
//
//
//	private static Row retrieveOrCreateRow(int i, HSSFSheet my_sheet) {
//		Row lRow = my_sheet.getRow(START_ROW + i);
//		if (lRow == null){
//			lRow = my_sheet.createRow(START_ROW+i);
//		}
//		return lRow;
//	}

}
