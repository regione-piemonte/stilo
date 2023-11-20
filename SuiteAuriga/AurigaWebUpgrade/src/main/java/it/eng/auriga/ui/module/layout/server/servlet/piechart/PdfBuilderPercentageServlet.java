/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleInsets;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.awt.PdfPrinterGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("/pdfBuilderPercentageServlet")
public class PdfBuilderPercentageServlet {
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
		String asseX = req.getParameter("asseX");
		String asseY = req.getParameter("asseY");
		if (chartType.equals("BAR")){
			return buildBar(lList, title, asseX, asseY);
		} else if (chartType.equals("PIE")){
			return buildPie(lList, title);
		}
		return null;
	}

	private HttpEntity<byte[]> buildPie(List<ReportResultBean> pList, String pTitle) throws FileNotFoundException, DocumentException, IOException {
		DefaultPieDataset lPieDataset = new DefaultPieDataset();
		for (ReportResultBean lReportResultBean : pList){
			lPieDataset.setValue(lReportResultBean, Float.valueOf(lReportResultBean.getPercArrotondata()));
		}
		JFreeChart chart = ChartFactory.createPieChart(pTitle,
				lPieDataset,            // data
				true,              // no legend
				true,               // tooltips
				false);              // no URL generation
		TextTitle lTextTitle = chart.getTitle();
		Font lFont = lTextTitle.getFont();
		int lIntFont = lFont.getSize();
		lIntFont = 15;
		Font lFont2 = new Font(lFont.getName(), lFont.getStyle(), lIntFont);
		lTextTitle.setFont(lFont2);
		chart.setTitle(lTextTitle);
		chart.setPadding(new RectangleInsets(4, 8, 2, 2));
		PiePlot plot = (PiePlot) chart.getPlot();
		//Se devo generare gli url
		
		plot.setLabelGenerator(new PieSectionLabelGenerator(){

			@Override
			public String generateSectionLabel(PieDataset paramPieDataset,
					Comparable paramComparable) {
				ReportResultBean lPieDataBean = (ReportResultBean)paramComparable;
				return lPieDataBean.getLabel() + " (" + lPieDataBean.getPercArrotondata() + "%)";
			}

			@Override
			public AttributedString generateAttributedSectionLabel(
					PieDataset paramPieDataset, Comparable paramComparable) {
				ReportResultBean lPieDataBean = (ReportResultBean)paramComparable;
				return new AttributedString(lPieDataBean.getLabel());
			}
			
		});
		plot.setLegendLabelGenerator(new PieSectionLabelGenerator(){

			@Override
			public String generateSectionLabel(PieDataset paramPieDataset,
					Comparable paramComparable) {
				ReportResultBean lPieDataBean = (ReportResultBean)paramComparable;
				float lFloat = getFloatValue(lPieDataBean.getValore());
				double lLong = (Math.round(lFloat*100.0)/100.0);
				return lPieDataBean.getLabel() + " - Conteggio : " + lLong + " (" + lPieDataBean.getPercArrotondata() + "%)";
			}

			@Override
			public AttributedString generateAttributedSectionLabel(
					PieDataset paramPieDataset, Comparable paramComparable) {
				ReportResultBean lPieDataBean = (ReportResultBean)paramComparable;
				return new AttributedString(lPieDataBean.getLabel());
			}
			
		});
		return buildPdfFromChart(chart);
	}

	protected HttpEntity<byte[]> buildPdfFromChart(JFreeChart chart)
			throws DocumentException {
		PdfWriter writer = null;
		Document document = new Document();
		ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream();
		writer = PdfWriter.getInstance(document, lByteArrayOutputStream);
		document.open();
		PdfContentByte contentByte = writer.getDirectContent();
		
		float widthPdf = contentByte.getPdfDocument().getPageSize().getWidth();
		float heigthPdf = contentByte.getPdfDocument().getPageSize().getHeight();
		float width = widthPdf;
		float height = heigthPdf;
		PdfTemplate template = contentByte.createTemplate(width, height);
		PdfGraphics2D graphics2d = new PdfGraphics2D(contentByte, width, height, new DefaultFontMapper());	
		Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
		chart.draw(graphics2d, rectangle2d);
		graphics2d.dispose();
		contentByte.addTemplate(template, (widthPdf-width)/2, (heigthPdf-height)/2);
		document.close();
		return new HttpEntity<byte[]>(lByteArrayOutputStream.toByteArray());
	}

	protected static float getFloatValue(String valore) {
		String perc = valore;
		if (perc.contains(",")){
			perc = perc.replace(',', '.');
		}
		Float lFloat = Float.parseFloat(perc);
		return lFloat;
	}

	private HttpEntity<byte[]> buildBar(List<ReportResultBean> pList, String title, String asseX, String asseY) throws DocumentException {
		DefaultCategoryDataset lBarDataset = new DefaultCategoryDataset();
		for (ReportResultBean lReportResultBean : pList){
			float lFloat = getFloatValue(lReportResultBean.getValore());
			double lLong = (Math.round(lFloat*100.0)/100.0);
			String label = lReportResultBean.getLabel() + " (Ore : " + lLong + " - perc. " + lReportResultBean.getPercArrotondata() + ")";
			lBarDataset.setValue(lLong, label, "");
		}
		
		JFreeChart chart = ChartFactory.createBarChart(title, asseX, asseY,
				lBarDataset,  PlotOrientation.VERTICAL,          // data
				true,              // no legend
				true,               // tooltips
				false);               // no URL generation
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		barrenderer.setMaximumBarWidth(.20);
		final GradientPaint gp2 = new GradientPaint(
	            0.0f, 0.0f, Color.red, 
	            0.0f, 0.0f, Color.lightGray
	        );
		barrenderer.setSeriesPaint(0, gp2);
		TextTitle lTextTitle = chart.getTitle();
		Font lFont = lTextTitle.getFont();
		int lIntFont = lFont.getSize();
		lIntFont = 15;
		Font lFont2 = new Font(lFont.getName(), lFont.getStyle(), lIntFont);
		lTextTitle.setFont(lFont2);
		chart.setTitle(lTextTitle);
		chart.setPadding(new RectangleInsets(4, 8, 2, 2));
		return buildPdfFromChart(chart);
	}
}
