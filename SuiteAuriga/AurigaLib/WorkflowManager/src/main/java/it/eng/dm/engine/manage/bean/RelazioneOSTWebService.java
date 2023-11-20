/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Enumeration contenente i tipi di messaggi interoperabili
 * @author jacopo
 *
 */
@XmlRootElement
public enum RelazioneOSTWebService {
	
	SOGGETTO_GIURIDICO("Soggetto Giuridico","it.eng.dm.sira.service.SoggettiGiuridiciService"),
	CONFERMA_RICEZIONE("Soggetto Fisico","it.eng.dm.sira.service.SoggettiFisiciService"),
	DEPURATORI("Depuratori","Acque");

	
	private String nomeOst;
	private String servizio;
	
	private RelazioneOSTWebService(String name,String servizio){
		this.nomeOst = name;
		this.servizio = servizio;
	}
	
	public String getNomeOst() {
		return nomeOst;
	}
	
	public String getServizio() {
		return servizio;
	}
	
	public static RelazioneOSTWebService getValueForServizio(String servizio,boolean casesensitive){
		for(RelazioneOSTWebService rel:RelazioneOSTWebService.values()){
			if(casesensitive){
				if(rel.servizio.equals(servizio)){
					return rel;
				}	
			}else{
				if(rel.servizio.equalsIgnoreCase(servizio)){
					return rel;
				}
			}			
		}
		return null;
	}
	
	public static RelazioneOSTWebService getValueForNomeOst(String nomeOst,boolean casesensitive){
		for(RelazioneOSTWebService rel:RelazioneOSTWebService.values()){
			if(casesensitive){
				if(rel.nomeOst.equals(nomeOst)){
					return rel;
				}	
			}else{
				if(rel.nomeOst.equalsIgnoreCase(nomeOst)){
					return rel;
				}
			}			
		}
		return null;
	}
	
}