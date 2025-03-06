package it.eng.applet.dataset;

import it.eng.applet.dataset.bean.PieDataBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartBuilder {
	
	public static HashMap<Integer, PieDataBean> mapValues;

	public static BarChartProperty retrieveDataset(String pStrUrl) throws MalformedURLException, IOException{
		mapValues = new HashMap<Integer, PieDataBean>();
		BarChartProperty lBarChartProperty = new BarChartProperty();
		DefaultCategoryDataset lBarDataset = new DefaultCategoryDataset();
		List<String> lList = IOUtils.readLines(new URL(pStrUrl).openStream());
		int i = 0;
		for (String lString : lList){
			String[] values = lString.split("="); 
			String key = values[0];
			String value = values[1];
			if (key.equals("title")){
				lBarChartProperty.setTitle(value.replace('#', '\n'));
			} else if (key.equals("title")){
				lBarChartProperty.setTitle(value.replace('#', '\n'));
			} else if (key.equals("asseX")){
				lBarChartProperty.setAssexLabel(value);
			} else if (key.equals("asseY")){
				lBarChartProperty.setAsseyLabel(value);
			} else {
				String[] lStrValori = value.split(";");
				PieDataBean lPieDataBean = buildPieDataBean(key, lStrValori);
				float lFloat = getFloatValue(lPieDataBean.getValore());
				double lLong = (Math.round(lFloat*100.0)/100.0);
				lPieDataBean.setLabel(lPieDataBean.getLabel() + " (Ore : " + lLong + " - perc. " + lPieDataBean.getPercArrotondata() + ")");
				mapValues.put(i, lPieDataBean);
				i++;
				lBarDataset.setValue(lLong, lPieDataBean.getLabel(), "");
			}
		}
		lBarChartProperty.setDataset(lBarDataset);
		return lBarChartProperty;
	}

	private static PieDataBean buildPieDataBean(String key, String[] lStrValori) {
		PieDataBean lPieDataBean = new PieDataBean();
		lPieDataBean.setIdSoggetto(key);
		lPieDataBean.setLabel(lStrValori[0]);
		lPieDataBean.setValore(lStrValori[1]);
		Float lFloat = getFloatValue(lStrValori[2]);
		lPieDataBean.setPerc(lFloat);
		Float lFloatArr = getFloatValue(lStrValori[3]);
		lPieDataBean.setPercArrotondata(lFloatArr);
		return lPieDataBean;
	}

	public static Float getFloatValue(String lStrVal) {
		String perc = lStrVal;
		if (perc.contains(",")){
			perc = perc.replace(',', '.');
		}
		Float lFloat = Float.parseFloat(perc);
		return lFloat;
	}
}
