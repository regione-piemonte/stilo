/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.ui.module.layout.server.attiTrasparenza.datasource.bean.SimpleKeyValueTrasparenzaBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.xml.XmlListaUtility;

public class XmlUtility {

	
	
	public static List<XmlListaSimpleBean> recuperaListaSimpleValue(String xmlIn) throws Exception{
		return XmlListaUtility.recuperaLista(xmlIn, XmlListaSimpleBean.class);
	}
	
	public static List<SimpleKeyValueBean> recuperaListaSemplice(String xmlIn) throws Exception{
		List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlIn, XmlListaSimpleBean.class);
		List<SimpleKeyValueBean> lListReturn = new ArrayList<SimpleKeyValueBean>();
		for (XmlListaSimpleBean lXmlListaSimpleBean : lista){
			SimpleKeyValueBean lBean = new SimpleKeyValueBean();
			lBean.setKey(lXmlListaSimpleBean.getKey());
			lBean.setValue(lXmlListaSimpleBean.getValue());
			lListReturn.add(lBean);
		}
		return lListReturn;
	}
	
	public static List<SimpleKeyValueBean> recuperaListaSempliceSubstring(String xmlIn) throws Exception{
		List<SimpleKeyValueBean> lListReturn = new ArrayList<SimpleKeyValueBean>();
		if(StringUtils.isNotBlank(xmlIn)) {
			List<XmlListaSimpleBean> lista = XmlListaUtility.recuperaLista(xmlIn, XmlListaSimpleBean.class);
			for (XmlListaSimpleBean lXmlListaSimpleBean : lista){
				SimpleKeyValueBean lBean = new SimpleKeyValueBean();
				String colonna1 = lXmlListaSimpleBean.getKey();
				lBean.setKey(colonna1.substring(colonna1.indexOf(";") + 1));
				lBean.setKey(lXmlListaSimpleBean.getKey());
				lBean.setValue(lXmlListaSimpleBean.getValue());
				lListReturn.add(lBean);
			}
		}
		return lListReturn;
	}
	
	public static List<SimpleKeyValueTrasparenzaBean> recuperaListaSempliceTrasparenza(String xmlIn) throws Exception{
		List<SimpleKeyValueTrasparenzaBean> lListReturn = new ArrayList<SimpleKeyValueTrasparenzaBean>();
		if(StringUtils.isNotBlank(xmlIn)) {
			List<XmlListaSimpleWithFlagBean> lista = XmlListaUtility.recuperaLista(xmlIn, XmlListaSimpleWithFlagBean.class);
			for (XmlListaSimpleWithFlagBean lXmlListaSimpleWithFlagBean : lista){
				SimpleKeyValueTrasparenzaBean lBean = new SimpleKeyValueTrasparenzaBean();
				String colonna1 = lXmlListaSimpleWithFlagBean.getKey();
				if(colonna1.contains("|*|")) {
					lBean.setKey(colonna1.substring(0, colonna1.indexOf("|*|")));
					lBean.setValue(colonna1.substring(colonna1.indexOf("|*|") + "|*|".length()));
				} else if (colonna1.contains("|$|")){
					lBean.setKey(colonna1.substring(0, colonna1.indexOf("|$|")));
					lBean.setValue(colonna1.substring(colonna1.indexOf("|$|") + "|$|".length()));
				} else {
					lBean.setKey(lXmlListaSimpleWithFlagBean.getKey());
					lBean.setValue(lXmlListaSimpleWithFlagBean.getValue());
				}
				lBean.setFlgBeneficiariObblig(lXmlListaSimpleWithFlagBean.getFlag());
				lListReturn.add(lBean);
			}
		}
		return lListReturn;
	}
	
	/**
	 * 
	 * @param r la riga di cui si vogliono estrarre i valori
	 * @return  Vector contenente in formato stringa i valori dei campi della riga specificata.<br/>
	 * ATTENZIONE: il Vector è una struttura dati sincronizzata, quindi più lenta nell'accesso ai dati.<br/> 
	 * Se non si devono gestire problematicità relative alla concorrenza, per questioni di performance sarbbe meglo utilizzare il corrispettivo metodo che restituisce una lista
	 */
	public Vector<String> getValoriRiga(Riga r) {			
		Vector<String> v = new Vector<String>();
		int oldNumCol = 0;	        		
   		for (int j = 0; j < r.getColonna().size(); j++) {
   			// Aggiungo le colonne vuote
   			for(int k=(oldNumCol+1); k < r.getColonna().get(j).getNro().intValue(); k++) v.add(null);	
   			String content = r.getColonna().get(j).getContent();
   			// aggiungo la colonna
   			if(StringUtils.isNotBlank(content)) {
				try {
					SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));
					v.add(content);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					v.add(content.replace("\n","<br/>"));
				}
   			} else {
   				v.add(null);
   			}
   			oldNumCol = r.getColonna().get(j).getNro().intValue(); // aggiorno l'ultimo numero di colonna
   		}
   		return v;
	}
	
	/**
	 * 
	 * @param riga la riga di cui si vogliono estrarre i valori
	 * @return lista contenente in formato stringa i valori dei campi della riga specificata
	 */
	public List<String> getValoriRigaAsList(Riga riga) {			
		
		List<String> retValue = new ArrayList<String>();
		
		int oldNumCol = 0;	        		
   		
		for (int j = 0; j < riga.getColonna().size(); j++) {
   		
			// Aggiungo le colonne vuote
			for(int k=(oldNumCol+1); k < riga.getColonna().get(j).getNro().intValue(); k++) retValue.add(null);	
   			
			String content = riga.getColonna().get(j).getContent();
			
   			// aggiungo la colonna
   			if(StringUtils.isNotBlank(content)) {
   				
				try {
					SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));
					retValue.add(content);
				} catch (JAXBException e) {
					retValue.add(content.replace("\n","<br/>"));
				}
				
   			} else {
   				retValue.add(null);
   			}
   			
   			// aggiorno l'ultimo numero di colonna
   			oldNumCol = riga.getColonna().get(j).getNro().intValue(); 
   		}
   		return retValue;
	}
}
