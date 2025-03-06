package it.eng.hybrid.module.pieChart.util;

import it.eng.hybrid.module.pieChart.dataset.PieDataBean;
import it.eng.hybrid.module.pieChart.ui.ChartType;
import it.eng.hybrid.module.pieChart.ui.PieChartApplication;

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
			count++;
		}
		String url = PieChartApplication.getParameters().getPdfBuilderPercentageServlet() +lStrAttuale;
		FileOutputStream lFileOutputStream = new FileOutputStream(pFile);
		IOUtils.write(IOUtils.toByteArray(new URL(url).openStream()), lFileOutputStream);
		lFileOutputStream.flush();
		lFileOutputStream.close();

	}
}
