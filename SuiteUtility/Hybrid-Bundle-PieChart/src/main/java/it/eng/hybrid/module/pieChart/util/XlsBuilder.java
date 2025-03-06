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
		String url = PieChartApplication.getParameters().getXlsBuilderPercentageServlet() +lStrAttuale;
		FileOutputStream lFileOutputStream = new FileOutputStream(pFile);
		IOUtils.write(IOUtils.toByteArray(new URL(url).openStream()), lFileOutputStream);
		lFileOutputStream.flush();
		lFileOutputStream.close();
	}

}
