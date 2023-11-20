/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/xlsBuilderPercentageServlet")
public class XlsBuilderPercentageServlet {
	
	
	private static int START_ROW = 1;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> download(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int numeroElementi = Integer.valueOf(req.getParameter("numeroElementi"));
		List<ReportResultBean> lList = new ArrayList<ReportResultBean>();
		for (int i = 0; i<numeroElementi; i++){
			ReportResultBean lReportResultBean = new ReportResultBean();
			lReportResultBean.setValore(req.getParameter("valore" + i));
			lReportResultBean.setLabel(req.getParameter("label" + i));
			lReportResultBean.setPercArrotondata(req.getParameter("percArrotondata" + i));
			lList.add(lReportResultBean);
		}
		String chartType = req.getParameter("type");
		String title = req.getParameter("title");
		if (chartType.equals("BAR")){
			return buildBar(lList, title);
		} else if (chartType.equals("PIE")){
			return buildPie(lList, title);
		}
		return null;
	}

	private static HttpEntity<byte[]> buildBar(List<ReportResultBean> lList, String title) throws IOException {
		InputStream chart_file_input = XlsBuilderPercentageServlet.class.getClassLoader().getResourceAsStream("TemplateBar.xls");
		/* Read chart data from XLSX Workbook */
		HSSFWorkbook my_workbook = new HSSFWorkbook(chart_file_input);
		/* Read worksheet that has pie chart input data information */
		HSSFSheet my_sheet = my_workbook.getSheetAt(0);

		Row rowInit = retrieveOrCreateRow(-1, my_sheet);
		Cell cellChi = retrieveOrCreateCell(rowInit, 0);
		cellChi.setCellValue("Chi");
		Cell cellConteggio = retrieveOrCreateCell(rowInit, 1);
		cellConteggio.setCellValue("Tempo medio (ore)");
		Cell cellPerc = retrieveOrCreateCell(rowInit, 2);
//		cellPerc.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
		cellPerc.setCellValue("Perc.");
		int i = 0;
		for (ReportResultBean lReportResultBean : lList){
			Row row = retrieveOrCreateRow(i, my_sheet);
			putLabelBar(row, lReportResultBean);
			putValueBar(row, lReportResultBean);
			putPercBar(row, lReportResultBean);
			i++;
		}
		Row row = my_sheet.getRow(5);
		Cell cell = row.getCell(3);
		cell.setCellValue(title);
		ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream();
		my_workbook.write(lByteArrayOutputStream);
		return new HttpEntity<byte[]>(lByteArrayOutputStream.toByteArray());
		
	}

	private static HttpEntity<byte[]> buildPie(List<ReportResultBean> lList, String title) throws IOException {
		InputStream chart_file_input = XlsBuilderPercentageServlet.class.getClassLoader().getResourceAsStream("TemplatePie.xls");
		/* Read chart data from XLSX Workbook */
		HSSFWorkbook my_workbook = new HSSFWorkbook(chart_file_input);
		/* Read worksheet that has pie chart input data information */
		HSSFSheet my_sheet = my_workbook.getSheetAt(0);
		Row rowInit = retrieveOrCreateRow(-1, my_sheet);
		Cell cellChi = retrieveOrCreateCell(rowInit, 0);
		cellChi.setCellValue("Chi");
		Cell cellConteggio = retrieveOrCreateCell(rowInit, 1);
		cellConteggio.setCellValue("Conteggio");
		Cell cellPerc = retrieveOrCreateCell(rowInit, 2);
//		cellPerc.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
		cellPerc.setCellValue("%");
		int i = 0;
		for (ReportResultBean lReportResultBean : lList){
			Row row = retrieveOrCreateRow(i, my_sheet);
			putLabelPie(row, lReportResultBean);
			putValuePie(row, lReportResultBean);
			putPercPie(row, lReportResultBean);
			i++;
		}
		Row row = my_sheet.getRow(5);
		Cell cell = row.getCell(3);
		cell.setCellValue(title);
		ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream();
		my_workbook.write(lByteArrayOutputStream);
		return new HttpEntity<byte[]>(lByteArrayOutputStream.toByteArray());
	}

	private static void putPercPie(Row row, ReportResultBean lReportResultBean) {
		Cell cell = retrieveOrCreateCell(row, 2);
		cell.setCellValue(PdfBuilderPercentageServlet.getFloatValue(lReportResultBean.getPercArrotondata()));
	}

	private static void putValuePie(Row row, ReportResultBean lReportResultBean) {
		Cell cell = retrieveOrCreateCell(row, 1);
		cell.setCellValue(lReportResultBean.getValore());
	}
	
	private static Cell retrieveOrCreateCell(Row row, int i) {
		Cell cell = row.getCell(i);
		if (cell == null){
			cell = row.createCell(i);
		}
		return cell;
	}

	private static void putLabelPie(Row row, ReportResultBean lReportResultBean) {
		Cell cell = retrieveOrCreateCell(row, 0);
		float lFloat = PdfBuilderPercentageServlet.getFloatValue(lReportResultBean.getValore());
		double lLong = (Math.round(lFloat*100.0)/100.0);
		String legend = lReportResultBean.getLabel() + " - Conteggio : " + lLong + " (" + lReportResultBean.getPercArrotondata() + "%)";
		cell.setCellValue(legend);
	}
	
	private static void putLabelBar(Row row, ReportResultBean lReportResultBean) {
		Cell cell = retrieveOrCreateCell(row, 0);
		float lFloat = PdfBuilderPercentageServlet.getFloatValue(lReportResultBean.getValore());
		double lLong = (Math.round(lFloat*100.0)/100.0);
		String legend = lReportResultBean.getLabel();
		cell.setCellValue(legend);
	}
	
	private static void putValueBar(Row row, ReportResultBean lReportResultBean) {
		Cell cell = retrieveOrCreateCell(row, 1);
		float lFloat = PdfBuilderPercentageServlet.getFloatValue(lReportResultBean.getValore());
		double lLong = (Math.round(lFloat*100.0)/100.0);
		cell.setCellValue(lLong);
	}
	
	private static void putPercBar(Row row, ReportResultBean lReportResultBean) {
		Cell cell = retrieveOrCreateCell(row, 2);
		cell.setCellValue(lReportResultBean.getPercArrotondata());
	}


	private static Row retrieveOrCreateRow(int i, HSSFSheet my_sheet) {
		Row lRow = my_sheet.getRow(START_ROW + i);
		if (lRow == null){
			lRow = my_sheet.createRow(START_ROW+i);
		}
		return lRow;
	}
}
