/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Enumeration contenente i tipi di messaggi interoperabili
 * @author jacopo
 *
 */
@XmlRootElement
public enum TipoInteroperabilita {
	
	SEGNATURA("Segnatura","Segnatura.xml"),
	CONFERMA_RICEZIONE("Conferma","Conferma.xml"),
	AGGIORNAMENTO_CONFERMA("Aggiornamento","Aggiornamento.xml"),
	NOTIFICA_ECCEZIONE("Eccezione","Eccezione.xml"),
	ANNULLAMENTO_PROTOCOLLAZIONE("Annullamento","Annullamento.xml");

	
	private String name;
	private String filename;
	
	private TipoInteroperabilita(String name,String filename){
		this.name = name;
		this.filename = filename;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public static TipoInteroperabilita getValueForFileName(String filename,boolean casesensitive){
		for(TipoInteroperabilita intertype:TipoInteroperabilita.values()){
			if(casesensitive){
				if(intertype.filename.equals(filename)){
					return intertype;
				}	
			}else{
				if(intertype.filename.equalsIgnoreCase(filename)){
					return intertype;
				}
			}			
		}
		return null;
	}
	
}