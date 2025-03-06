package it.eng.applet.dataset;

import it.eng.applet.dataset.bean.PieDataBean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Costruisce un {@link PieChartProperty} a partire da un url di riferimento
 * Legge il contenuto del link come delle linee di tipo chiave=valore
 * dove chiave rappresenterà la label per gli spicchi e valore è il valore in percentuale
 * 
 * Si aspetta anche una chiave di nome title che indica il titolo del grafico
 * @author Rametta
 *
 */
public class PieChartBuilder {

	/**
	 * Funzione che fa il build del {@link PieChartProperty} come descritto 
	 * @param pStrUrl L'url di riferimento
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	
	public static HashMap<Integer, PieDataBean> mapValues;
	
	public static PieChartProperty retrieveDataset(String pStrUrl) throws MalformedURLException, IOException{
		mapValues = new HashMap<Integer, PieDataBean>();
		PieChartProperty lPieChartProperty = new PieChartProperty();
		DefaultPieDataset lPieDataset = new DefaultPieDataset();
		List<String> lList = IOUtils.readLines(new URL(pStrUrl).openStream());
		int i = 0;
		for (String lString : lList){
			String[] values = lString.split("="); 
			String key = values[0];
			String value = values[1];
			if (key.equals("title")){
				lPieChartProperty.setTitle(value.replace('#', '\n'));
			} else {
				String[] lStrValori = value.split(";");
				PieDataBean lPieDataBean = buildPieDataBean(key, lStrValori);
				mapValues.put(i, lPieDataBean);
				i++;
				lPieDataset.setValue(lPieDataBean, lPieDataBean.getPercArrotondata());
			}
		}
		lPieChartProperty.setDataset(lPieDataset);
		return lPieChartProperty;
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

	protected static Float getFloatValue(String lStrVal) {
		String perc = lStrVal;
		if (perc.contains(",")){
			perc = perc.replace(',', '.');
		}
		Float lFloat = Float.parseFloat(perc);
		return lFloat;
	}
}
