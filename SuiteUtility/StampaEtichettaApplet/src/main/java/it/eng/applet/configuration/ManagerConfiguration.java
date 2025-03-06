package it.eng.applet.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import it.eng.applet.configuration.ParameterBean.TipoStampa;
import it.eng.applet.configuration.bean.PdfPropertiesBean;
import it.eng.applet.configuration.bean.PdfRigaBean;
import it.eng.applet.configuration.bean.StampaEtichettaPropertiesBean;
import it.eng.applet.configuration.bean.ZebraRigaBean;
import it.eng.applet.util.DownloadFileUtil;
import it.eng.applet.util.FileConfigurationUtil;

public class ManagerConfiguration {
	public static String userPrefDirPath = System.getProperty("user.home") + File.separator + "etichettaConfig";
	public static String userPreferencesFile = "stampaetichettaapplet.properties";
	public static String pdfPreferencesFile = "pdf.properties";
	public static String pathPreferences = userPrefDirPath + File.separator + userPreferencesFile;
	public static String pathPdfPreferences = userPrefDirPath + File.separator + pdfPreferencesFile;
	
	private static StampaEtichettaPropertiesBean properties;
	private static PdfPropertiesBean pdfProperties;
	
	public static void init(ParameterBean pParameterBean) throws IOException, IllegalAccessException, InvocationTargetException {
		//Creo la directory
		FileConfigurationUtil.createDirIfNotExist();
		if (pParameterBean.getTipoStampa() != null && TipoStampa.PRN == pParameterBean.getTipoStampa()) {
			//Download del file di prn properties - se necessario
			DownloadFileUtil.downloadFile(pParameterBean.getPropertiesServlet(), pParameterBean.getIdUtente(), 
					pParameterBean.getIdSchema(), pParameterBean.getIdDominio(), "", pathPreferences);
		} else if (pParameterBean.getTipoStampa() != null && TipoStampa.PDF == pParameterBean.getTipoStampa()) {
			//Download del file di pdf properties - se necessario
			DownloadFileUtil.downloadFile(pParameterBean.getPdfServlet(), pParameterBean.getIdUtente(), 
					pParameterBean.getIdSchema(), pParameterBean.getIdDominio(), "", pathPdfPreferences);
		}
		//Inizializziamo le properties
		initProperties(pParameterBean);
	}

	private static void initProperties(ParameterBean pParameterBean) throws IOException, IllegalAccessException, InvocationTargetException {
		if (pParameterBean.getTipoStampa() != null && TipoStampa.PRN == pParameterBean.getTipoStampa()) {
			initEtichetteProperties();
		} else if (pParameterBean.getTipoStampa() != null && TipoStampa.PDF == pParameterBean.getTipoStampa()) {
			initPdfProperties();
		}
	}

	private static void initPdfProperties() throws FileNotFoundException, IOException, IllegalAccessException,
	InvocationTargetException {
		PdfPropertiesBean lPdfPropertiesBean = new PdfPropertiesBean();
		File lFile = new File(userPrefDirPath + File.separator + pdfPreferencesFile);
		InputStream lInputStream = new FileInputStream(lFile);
		Properties lProperties = new Properties();
		lProperties.load(lInputStream);
		Iterator<Object> lIterator = lProperties.keySet().iterator();
		
		Map<Integer, String> marginBottoms = new HashMap<Integer, String>();
		Map<Integer, String> leading = new HashMap<Integer, String>();
		Map<Integer, String> textAlign = new HashMap<Integer, String>();
		Map<Integer, String> fontSize = new HashMap<Integer, String>();
		Map<Integer, String> fontWeight = new HashMap<Integer, String>();
		Map<Integer, String> marginTop = new HashMap<Integer, String>();
		
		Map<Integer, String> secondLabelMarginBottoms = new HashMap<Integer, String>();
		Map<Integer, String> secondLabelLeading = new HashMap<Integer, String>();
		Map<Integer, String> secondLabelTextAlign = new HashMap<Integer, String>();
		Map<Integer, String> secondLabelFontSize = new HashMap<Integer, String>();
		Map<Integer, String> secondLabelFontWeight = new HashMap<Integer, String>();
		Map<Integer, String> secondLabelMarginTop = new HashMap<Integer, String>();
		
		Map<Integer, String> repertorioLabelMarginBottoms = new HashMap<Integer, String>();
		Map<Integer, String> repertorioLabelLeading = new HashMap<Integer, String>();
		Map<Integer, String> repertorioLabelTextAlign = new HashMap<Integer, String>();
		Map<Integer, String> repertorioLabelFontSize = new HashMap<Integer, String>();
		Map<Integer, String> repertorioLabelFontWeight = new HashMap<Integer, String>();
		Map<Integer, String> repertorioLabelMarginTop = new HashMap<Integer, String>();
		
		Map<Integer, String> faldoneLabelMarginBottoms = new HashMap<Integer, String>();
		Map<Integer, String> faldoneLabelLeading = new HashMap<Integer, String>();
		Map<Integer, String> faldoneLabelTextAlign = new HashMap<Integer, String>();
		Map<Integer, String> faldoneLabelFontSize = new HashMap<Integer, String>();
		Map<Integer, String> faldoneLabelFontWeight = new HashMap<Integer, String>();
		Map<Integer, String> faldoneLabelMarginTop = new HashMap<Integer, String>();
		
		while (lIterator.hasNext()){
			String lString = (String)lIterator.next();
			BeanUtilsBean2.getInstance().setProperty(lPdfPropertiesBean, lString, lProperties.getProperty(lString));
			// Layout seconda pagina
			if (lString.startsWith("marginBottom")){
				marginBottoms.put(Integer.valueOf(lString.substring("marginBottom".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("leading")){
				leading.put(Integer.valueOf(lString.substring("leading".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("textAlign")){
				textAlign.put(Integer.valueOf(lString.substring("textAlign".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("fontSize")){
				fontSize.put(Integer.valueOf(lString.substring("fontSize".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("fontWeight")){
				fontWeight.put(Integer.valueOf(lString.substring("fontWeight".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("marginTop")){
				marginTop.put(Integer.valueOf(lString.substring("marginTop".length())),  lProperties.getProperty(lString));
			}
			// Layout seconda pagina
			if (lString.startsWith("secondLabelMarginBottom")){
				secondLabelMarginBottoms.put(Integer.valueOf(lString.substring("secondLabelMarginBottom".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("secondLabelLeading")){
				secondLabelLeading.put(Integer.valueOf(lString.substring("secondLabelLeading".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("secondLabelTextAlign")){
				secondLabelTextAlign.put(Integer.valueOf(lString.substring("secondLabelTextAlign".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("secondLabelFontSize")){
				secondLabelFontSize.put(Integer.valueOf(lString.substring("secondLabelFontSize".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("secondLabelFontWeight")){
				secondLabelFontWeight.put(Integer.valueOf(lString.substring("secondLabelFontWeight".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("secondLabelMarginTop")){
				secondLabelMarginTop.put(Integer.valueOf(lString.substring("secondLabelMarginTop".length())),  lProperties.getProperty(lString));
			}
			// Layout pagina repertorio
			if (lString.startsWith("repertorioLabelMarginBottom")){
				repertorioLabelMarginBottoms.put(Integer.valueOf(lString.substring("repertorioLabelMarginBottom".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("repertorioLabelLeading")){
				repertorioLabelLeading.put(Integer.valueOf(lString.substring("repertorioLabelLeading".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("repertorioLabelTextAlign")){
				repertorioLabelTextAlign.put(Integer.valueOf(lString.substring("repertorioLabelTextAlign".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("repertorioLabelFontSize")){
				repertorioLabelFontSize.put(Integer.valueOf(lString.substring("repertorioLabelFontSize".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("repertorioLabelFontWeight")){
				repertorioLabelFontWeight.put(Integer.valueOf(lString.substring("repertorioLabelFontWeight".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("repertorioLabelMarginTop")){
				repertorioLabelMarginTop.put(Integer.valueOf(lString.substring("repertorioLabelMarginTop".length())),  lProperties.getProperty(lString));
			}
			// Layout pagina faldone
			if (lString.startsWith("faldoneLabelMarginBottom")){
				faldoneLabelMarginBottoms.put(Integer.valueOf(lString.substring("faldoneLabelMarginBottom".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("faldoneLabelLeading")){
				faldoneLabelLeading.put(Integer.valueOf(lString.substring("faldoneLabelLeading".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("faldoneLabelTextAlign")){
				faldoneLabelTextAlign.put(Integer.valueOf(lString.substring("faldoneLabelTextAlign".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("faldoneLabelFontSize")){
				faldoneLabelFontSize.put(Integer.valueOf(lString.substring("faldoneLabelFontSize".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("faldoneLabelFontWeight")){
				faldoneLabelFontWeight.put(Integer.valueOf(lString.substring("faldoneLabelFontWeight".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("faldoneLabelMarginTop")){
				faldoneLabelMarginTop.put(Integer.valueOf(lString.substring("faldoneLabelMarginTop".length())),  lProperties.getProperty(lString));
			}
			
		}
		
		List<PdfRigaBean> lList = new ArrayList<PdfRigaBean>();
		int maxValue = getMax(marginBottoms.size(), textAlign.size(), fontSize.size());
		for (int lInteger=0; lInteger<maxValue; lInteger++){
			PdfRigaBean lPdfRigaBean = new PdfRigaBean();
			lPdfRigaBean.setNumeroRiga(lInteger+1);
			lPdfRigaBean.setFontSize(fontSize.get(lInteger+1));
			lPdfRigaBean.setMarginBottom(marginBottoms.get(lInteger+1));
			lPdfRigaBean.setLeading(leading.get(lInteger+1));
			lPdfRigaBean.setTextAlign(textAlign.get(lInteger+1));
			lPdfRigaBean.setFontWeight(fontWeight.get(lInteger+1));
			lPdfRigaBean.setMarginTop(marginTop.get(lInteger+1));
			lList.add(lPdfRigaBean);
		}
		lPdfPropertiesBean.setRighe(lList);
		
		List<PdfRigaBean> lSecondLabelList = new ArrayList<PdfRigaBean>();
		int secondLabelMaxValue = getMax(secondLabelMarginBottoms.size(), secondLabelTextAlign.size(), secondLabelFontSize.size());
		for (int lInteger=0; lInteger<secondLabelMaxValue; lInteger++){
			PdfRigaBean lPdfRigaBean = new PdfRigaBean();
			lPdfRigaBean.setNumeroRiga(lInteger+1);
			lPdfRigaBean.setFontSize(secondLabelFontSize.get(lInteger+1));
			lPdfRigaBean.setMarginBottom(secondLabelMarginBottoms.get(lInteger+1));
			lPdfRigaBean.setLeading(secondLabelLeading.get(lInteger+1));
			lPdfRigaBean.setTextAlign(secondLabelTextAlign.get(lInteger+1));
			lPdfRigaBean.setFontWeight(secondLabelFontWeight.get(lInteger+1));
			lPdfRigaBean.setMarginTop(secondLabelMarginTop.get(lInteger+1));
			lSecondLabelList.add(lPdfRigaBean);
		}
		lPdfPropertiesBean.setSecondLabelRighe(lSecondLabelList);

		List<PdfRigaBean> lRepertorioLabelList = new ArrayList<PdfRigaBean>();
		int repertorioLabelMaxValue = getMax(repertorioLabelMarginBottoms.size(), repertorioLabelTextAlign.size(), repertorioLabelFontSize.size());
		for (int lInteger=0; lInteger<repertorioLabelMaxValue; lInteger++){
			PdfRigaBean lPdfRigaBean = new PdfRigaBean();
			lPdfRigaBean.setNumeroRiga(lInteger+1);
			lPdfRigaBean.setFontSize(repertorioLabelFontSize.get(lInteger+1));
			lPdfRigaBean.setMarginBottom(repertorioLabelMarginBottoms.get(lInteger+1));
			lPdfRigaBean.setLeading(repertorioLabelLeading.get(lInteger+1));
			lPdfRigaBean.setTextAlign(repertorioLabelTextAlign.get(lInteger+1));
			lPdfRigaBean.setFontWeight(repertorioLabelFontWeight.get(lInteger+1));
			lPdfRigaBean.setMarginTop(repertorioLabelMarginTop.get(lInteger+1));
			lRepertorioLabelList.add(lPdfRigaBean);
		}
		lPdfPropertiesBean.setRepertorioLabelRighe(lRepertorioLabelList);
		
		List<PdfRigaBean> lFaldoneLabelList = new ArrayList<PdfRigaBean>();
		int faldoneLabelMaxValue = getMax(faldoneLabelMarginBottoms.size(), faldoneLabelTextAlign.size(), faldoneLabelFontSize.size());
		for (int lInteger=0; lInteger<faldoneLabelMaxValue; lInteger++){
			PdfRigaBean lPdfRigaBean = new PdfRigaBean();
			lPdfRigaBean.setNumeroRiga(lInteger+1);
			lPdfRigaBean.setFontSize(faldoneLabelFontSize.get(lInteger+1));
			lPdfRigaBean.setMarginBottom(faldoneLabelMarginBottoms.get(lInteger+1));
			lPdfRigaBean.setLeading(faldoneLabelLeading.get(lInteger+1));
			lPdfRigaBean.setTextAlign(faldoneLabelTextAlign.get(lInteger+1));
			lPdfRigaBean.setFontWeight(faldoneLabelFontWeight.get(lInteger+1));
			lPdfRigaBean.setMarginTop(faldoneLabelMarginTop.get(lInteger+1));
			lFaldoneLabelList.add(lPdfRigaBean);
		}		
		lPdfPropertiesBean.setFaldoneLabelRighe(lFaldoneLabelList);
		
		pdfProperties = lPdfPropertiesBean;
	}

	private static int getMax(int size, int size2, int size3) {
		if (size >= size2){
			if (size>=size3) return size;
			else return size3;
		} else {
			if (size2>=size3) return size2;
			else return size3;
		}
	}

	protected static void initEtichetteProperties()
			throws FileNotFoundException, IOException, IllegalAccessException,
			InvocationTargetException {
		ConvertUtils.register(new EnumConverter(), TipoStampa.class);
		StampaEtichettaPropertiesBean lStampaEtichettaPropertiesBean = 
			new StampaEtichettaPropertiesBean();
		
		File lFile = new File(userPrefDirPath + File.separator + userPreferencesFile);
		InputStream lInputStream = new FileInputStream(lFile);
		Properties lProperties = new Properties();
		lProperties.load(lInputStream);
		Iterator<Object> lIterator = lProperties.keySet().iterator();
		Map<Integer, String> offsetx = new HashMap<Integer, String>();
		Map<Integer, String> offsety = new HashMap<Integer, String>();
		Map<Integer, String> fontSize = new HashMap<Integer, String>();
		while (lIterator.hasNext()){
			String lString = (String)lIterator.next();
			BeanUtilsBean2.getInstance().setProperty(lStampaEtichettaPropertiesBean, lString, lProperties.getProperty(lString));
			if (lString.startsWith("offsetx")){
				offsetx.put(Integer.valueOf(lString.substring("offsetx".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("offsety")){
				offsety.put(Integer.valueOf(lString.substring("offsety".length())),  lProperties.getProperty(lString));
			}
			if (lString.startsWith("fontSize")){
				fontSize.put(Integer.valueOf(lString.substring("fontSize".length())),  lProperties.getProperty(lString));
			}
		}
		List<ZebraRigaBean> lList = new ArrayList<ZebraRigaBean>();
		int maxValue = getMax(offsetx.size(), offsety.size(), fontSize.size());
		for (int lInteger=0; lInteger<maxValue; lInteger++){
			ZebraRigaBean lZebraRigaBean = new ZebraRigaBean();
			lZebraRigaBean.setNumeroRiga(lInteger+1);
			lZebraRigaBean.setFontSize(fontSize.get(lInteger+1));
			lZebraRigaBean.setOffsetx(offsetx.get(lInteger+1));
			lZebraRigaBean.setOffsety(offsety.get(lInteger+1));
			lList.add(lZebraRigaBean);
		}
		lStampaEtichettaPropertiesBean.setRighe(lList);
		properties = lStampaEtichettaPropertiesBean;
	}
	
	public static StampaEtichettaPropertiesBean getProperties(){
		return properties;
	}
	public static PdfPropertiesBean getPdfProperties(){
		return pdfProperties;
	}
	
	static class EnumConverter implements Converter {

		public Object convert(Class arg0, Object arg1) {
			return TipoStampa.getRealValue((String)arg1);
		}    
	   
	}
}
