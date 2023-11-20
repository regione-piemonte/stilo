/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBException;

import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;

/**
 * Astrae i metodi comuni ai vari compiler
 * @author massimo malvestio
 *
 */
public abstract class AbstractCompiler {

	//K -> nome del valore V -> valore semplice
	protected LinkedHashMap<String, String> valoriSemplici;
	
	//K -> nome del valore V -> Lista
	protected  LinkedHashMap<String, Lista> valoriComplessi;

	protected SezioneCache variables;

	/**
	 * A partire dall'uri specificato recupera il documento da usare come
	 * modello e inserisce al suo interno i valori specificati dall'xml sotto
	 * forma di stringa specificato
	 * 
	 * @param datiModelloXml
	 * @param docStorageUri
	 * @return il file temporaneo creato a partire dal modello specificato e dai dati recuperati dal sistema
	 * @throws Exception 
	 */
	public File fillDocument() throws Exception {

		populate();
		
		File destFile = saveTemplateOnFile();
		
		return destFile;
		
	}
	

	/**
	 * Genera il documento a partire dal template in cui sono stati iniettati i dati
	 * @param template
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected abstract File saveTemplateOnFile() throws IOException, FileNotFoundException;
	
	/**
	 * Popola il template iniettando i valori
	 */
	protected abstract void populate();
	
	/**
	 * Genera una rappresentazione java dell'xml fornito come stringa
	 * @param datiModelloXml
	 * @return
	 * @throws JAXBException
	 */
	protected void buildXml(String datiModelloXml) throws JAXBException {
		
		variables = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(datiModelloXml));

	}
	
	/**
	 * Popola due Map con i valori presenti nella sezione cache, una contenente i valori semplici, una contenente i valori complessi.<br/>
	 * Valori semplici: K -> nome del valore V -> valore semplice
	 * Valori complessi: K -> nome del valore V -> Lista
	 * @param valuesToInject
	 */
	protected void mapSezioneCache() {
		
		valoriSemplici = new LinkedHashMap<String, String>();
		valoriComplessi = new LinkedHashMap<String, Lista>();
		
		for(Variabile var : variables.getVariabile()) {
			
			String valoreSemplice = var.getValoreSemplice();
			
			String variableName = var.getNome().trim();
			if(valoreSemplice != null) {
				
				valoriSemplici.put("$" + variableName.trim() + "$", valoreSemplice);
				
			} else {
				
				Lista values = var.getLista();
				valoriComplessi.put("$" + variableName.trim() + "$", values);
			}
		}
		
	}
}
