/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Datasource(id="TestAttributiDinamiciDatasource")
public class TestAttributiDinamiciDatasource extends AbstractServiceDataSource<AttributiDinamiciInputBean, AttributiDinamiciOutputBean> {
	
	public AttributiDinamiciOutputBean call(AttributiDinamiciInputBean input) throws Exception {
				
		AttributiDinamiciOutputBean output = new AttributiDinamiciOutputBean();
				
		String xml = readFile("ATTRIBUTI_DINAMICI.xml");
		StringReader sr = new StringReader(xml);
		Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
		output.setAttributiAdd(recuperaLista(lista, AttributoBean.class));
		
		HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista = new HashMap<String, List<DettColonnaAttributoListaBean>>();
		HashMap<String, List<HashMap<String, String>>> mappValoriAttrLista = new HashMap<String, List<HashMap<String, String>>>();
		HashMap<String, DocumentBean> mappaDocumenti = new HashMap<String, DocumentBean>();
		
		for(AttributoBean attr : output.getAttributiAdd()) {				
			
			if("LISTA".equals(attr.getTipo())) {
			
				String xmlDettLista = readFile(attr.getNome() + ".xml");		
				StringReader srDettLista = new StringReader(xmlDettLista);
				Lista listaDettLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srDettLista);
				List<DettColonnaAttributoListaBean> datiDettLista = recuperaLista(listaDettLista, DettColonnaAttributoListaBean.class);			
				mappaDettAttrLista.put(attr.getNome(), datiDettLista);
				
				String xmlValoriLista = readFile(attr.getNome() + "_VALORI.xml");			
				StringReader srValoriLista = new StringReader(xmlValoriLista);
				Lista listaValoriLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srValoriLista);			
				List<HashMap<String, String>> valoriLista = recuperaListaValori(listaValoriLista, mappaDettAttrLista.get(attr.getNome()), mappaDocumenti);			
				mappValoriAttrLista.put(attr.getNome(), valoriLista);
					
			} else if("DOCUMENT".equals(attr.getTipo())) {		
				DocumentBean document = buildDocumentBean(attr.getValore());
				attr.setValore(document.getIdDoc());
				mappaDocumenti.put(document.getIdDoc(), document);
			}
			
		}
		
		output.setMappaDettAttrLista(mappaDettAttrLista);
		output.setMappaValoriAttrLista(mappValoriAttrLista);
		output.setMappaDocumenti(mappaDocumenti);
	
		return output;
		
	}
	
	public DocumentBean buildDocumentBean(String valore) {
		StringSplitterServer st = new StringSplitterServer(valore != null ? valore : "", "|*|");
		String[] tokens = new String[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens()) {
			tokens[i++] = st.nextToken();						
		}					
		DocumentBean document = new DocumentBean();
		document.setIdDoc(getToken(0, tokens));
		document.setIdUd(getToken(1, tokens));
		document.setUriFile(getToken(2, tokens));
		document.setNomeFile(getToken(3, tokens));
		document.setRemoteUri(true);					
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setImpronta(getToken(4, tokens));						
		lMimeTypeFirmaBean.setCorrectFileName(document.getNomeFile());
		lMimeTypeFirmaBean.setFirmato(StringUtils.isNotBlank(getToken(5, tokens)) && "1".equals(getToken(5, tokens)));
		lMimeTypeFirmaBean.setFirmaValida(StringUtils.isNotBlank(getToken(6, tokens)) && "1".equals(getToken(6, tokens)));			
		lMimeTypeFirmaBean.setConvertibile(StringUtils.isNotBlank(getToken(7, tokens)) && "1".equals(getToken(7, tokens)));
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setMimetype(getToken(8, tokens));
		lMimeTypeFirmaBean.setBytes(StringUtils.isNotBlank(getToken(9, tokens)) ? new Long(getToken(9, tokens)) : 0);
		if (lMimeTypeFirmaBean.isFirmato()){
			lMimeTypeFirmaBean.setTipoFirma(document.getNomeFile().toUpperCase().endsWith("P7M")||document.getNomeFile().toUpperCase().endsWith("TSD")?"CAdES_BES":"PDF");
			lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(getToken(10, tokens))?getToken(10, tokens).split(","):null);
		}																
		document.setInfoFile(lMimeTypeFirmaBean);	
		return document;
	}
	
	public String getToken(int i, String[] tokens) {
		if(i >= tokens.length) return null;		
		return tokens[i];
	}
	
	private String readFile(String fileName) throws Exception {		
		String url = URLDecoder.decode(getClass().getClassLoader().getResource(fileName).getFile(), "UTF-8");
	    BufferedReader br = new BufferedReader(new FileReader(url));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }	        
	        return sb.toString();
	    } finally {
	        br.close();
	    }	    
	}
		
	private List<HashMap<String, String>> recuperaListaValori(Lista lLista, List<DettColonnaAttributoListaBean> dettAttrLista, HashMap<String, DocumentBean> mappaDocumenti) throws Exception {		
		List<HashMap<String, String>> lList = new ArrayList<HashMap<String, String>>();
		if(lLista != null) {
			HashMap<Integer,String> mappa = new HashMap<Integer, String>();
			HashMap<Integer,String> mappaTipi = new HashMap<Integer, String>();
			for(int i = 0; i < dettAttrLista.size(); i++) {
				mappa.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getNome());
				mappaTipi.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getTipo());
			}			
			List<Riga> righe = lLista.getRiga();			
			for (Riga lRiga : righe){
				HashMap<String, String> lMap = new HashMap<String, String>();				
				for (Colonna lColonna : lRiga.getColonna()){
					if("DOCUMENT".equals(mappaTipi.get(lColonna.getNro().intValue()))) {
						DocumentBean document = buildDocumentBean(lColonna.getContent());
						lMap.put(mappa.get(lColonna.getNro().intValue()), document.getIdDoc());
						mappaDocumenti.put(document.getIdDoc(), document);
					} 
					else {
						lMap.put(mappa.get(lColonna.getNro().intValue()), lColonna.getContent());
					}					
				}	
				lList.add(lMap);
			}													
		}		
		return lList;					
	}
	
	private <T> List<T> recuperaLista(Lista lLista, Class<T> lClass) throws Exception {		
		List<T> lList = new ArrayList<T>();
		if(lLista != null) {				
			List<Riga> righe = lLista.getRiga();			
			Field[] lFieldsLista = retrieveFields(lClass);
			BeanWrapperImpl wrapperObjectLista = BeanPropertyUtility.getBeanWrapper();
			for (Riga lRiga : righe){
				T lObjectLista = lClass.newInstance();
				wrapperObjectLista = BeanPropertyUtility.updateBeanWrapper(wrapperObjectLista, lObjectLista);
				for (Field lFieldLista : lFieldsLista){
					NumeroColonna lNumeroColonna = lFieldLista.getAnnotation(NumeroColonna.class);
					if (lNumeroColonna != null){
						int index = Integer.valueOf(lNumeroColonna.numero());
						for (Colonna lColonna : lRiga.getColonna()){
							if (lColonna.getNro().intValue() == index){
								String value = lColonna.getContent();
								setValueOnBean(lObjectLista, lFieldLista, lFieldLista.getName(), value, wrapperObjectLista);
							}
						}
					}
				}
				lList.add(lObjectLista);
			}													
		}		
		return lList;					
	}
	
	private Field[] retrieveFields(Class lClass) {
		Field[] lFieldsLista = lClass.getDeclaredFields();;
		if (lClass.getSuperclass()!=null && lClass.getSuperclass()!=java.lang.Object.class){
			Field[] inherited = lClass.getSuperclass().getDeclaredFields();
			Field[] original = lFieldsLista;
			lFieldsLista = new Field[inherited.length + original.length];
			int k = 0;
			for (Field lFieldInherited : inherited){
				lFieldsLista[k] = lFieldInherited;
				k++;
			}
			for (Field lFieldOriginal : original){
				lFieldsLista[k] = lFieldOriginal;
				k++;
			}
		}
		return lFieldsLista;
	}	
			
	private void setValueOnBean(Object nested, Field lFieldNest, String propertyNested, String valore, BeanWrapperImpl wrapperNested) throws Exception {
		if (lFieldNest.getType().isEnum()){
			BeanWrapperImpl wrapperObjectEnum = BeanPropertyUtility.getBeanWrapper();
			for (Object lObjectEnum : lFieldNest.getType().getEnumConstants()){
				wrapperObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrapperObjectEnum, lObjectEnum);
				String value = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrapperObjectEnum, "dbValue");
				// String value = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
				if (value == null){
					if (valore == null){
						BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
						// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
					} else {

					}
				} else if (value.equals(valore)){
					BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
					// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
				}
			}
		} else if (Date.class.isAssignableFrom(lFieldNest.getType())){
			TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
			SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
			if(StringUtils.isNotBlank(valore)) {
				Date lDate = lSimpleDateFormat.parse(valore);
				BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lDate);
				// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lDate);
			}
		} else {
			BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, valore);
			// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, valore);
		}
	}
	
}
