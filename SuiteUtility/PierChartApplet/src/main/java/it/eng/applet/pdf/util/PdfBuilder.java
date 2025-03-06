package it.eng.applet.pdf.util;

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

/**
 * 
 * Classe per la generazione di un pdf a partire da un {@link JFreeChart}
 * @author Rametta
 *
 */
public class PdfBuilder {

	/**
	 * Genera un pdf a partire dal {@link JFreeChart} ricevuto in ingresso e lo scrive nel file 
	 * ricevuto in ingresso. Le dimensioni vengono gestite come parametri.
	 * 
	 * L'immagine viene centrata nel pdf
	 * @param pFile Il file in cui scrivere
	 * @param pJFreeChart Il grafico da convertire
	 * @param width La larghezza
	 * @param height L'altezza
	 * @param hashMap 
	 * @param type 
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void writeChartToPdfFile(File pFile, JFreeChart pJFreeChart, float width, float height, HashMap<Integer, PieDataBean> hashMap, ChartType type,
			String asseX, String asseY) throws MalformedURLException, IOException{
		
		StringBuffer lStringBuffer = new StringBuffer();
		
		lStringBuffer.append("?numeroElementi=" + hashMap.size());
		lStringBuffer.append("&type=" + type.name());
		lStringBuffer.append("&title=" + URLEncoder.encode(pJFreeChart.getTitle().getText(), "UTF-8"));
		if (type == ChartType.BAR){
			lStringBuffer.append("&asseX=" + asseX);
			lStringBuffer.append("&asseY=" + asseY);
		}
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
		String url = PieChartApplet.getParameters().getPdfBuilderPercentageServlet() +lStrAttuale;
		FileOutputStream lFileOutputStream = new FileOutputStream(pFile);
		IOUtils.write(IOUtils.toByteArray(new URL(url).openStream()), lFileOutputStream);
		lFileOutputStream.flush();
		lFileOutputStream.close();
//		new URL(url).openStream();
//		
//		PdfWriter writer = null;
//		Document document = new Document();
//		writer = PdfWriter.getInstance(document, new FileOutputStream(pFile));
//		document.open();
//		PdfContentByte contentByte = writer.getDirectContent();
//		
//		float widthPdf = contentByte.getPdfDocument().getPageSize().getWidth();
//		float heigthPdf = contentByte.getPdfDocument().getPageSize().getHeight();
//		width = widthPdf;
//		height = heigthPdf;
//		PdfTemplate template = contentByte.createTemplate(width, height);
//		Graphics2D graphics2d = template.createGraphics(width, height,
//				new DefaultFontMapper());
//		Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
//		pJFreeChart.draw(graphics2d, rectangle2d);
//		graphics2d.dispose();
//		contentByte.addTemplate(template, (widthPdf-width)/2, (heigthPdf-height)/2);
//		document.close();
	}
}
