/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;

import it.eng.job.aggiungiMarca.constants.PostManageActionEnum;
import it.eng.utility.storageutil.StorageService;



/**
 * Configurazioni generali
 * 
 *
 */

//@XmlRootElement
public class ConfigurazioniProcessoAggiuntaMarca {


	/**
	 * Id elaborazione
	 */

	private String id;
	private String numTentativiMarca;
	private PostManageActionEnum azioneFinale = DEFAULT_AZIONE;
	private StorageService storageUtilImpl;
	
	public static final PostManageActionEnum DEFAULT_AZIONE = PostManageActionEnum.DELETE;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PostManageActionEnum getAzioneFinale() {
		return azioneFinale;
	}

	public void setAzioneFinale(PostManageActionEnum azioneFinale) {
		this.azioneFinale = azioneFinale;
	}

	public StorageService getStorageUtilImpl() {
		return storageUtilImpl;
	}

	public void setStorageUtilImpl(StorageService storageUtilImpl) {
		this.storageUtilImpl = storageUtilImpl;
	}

	public String getNumTentativiMarca() {
		return numTentativiMarca;
	}

	public void setNumTentativiMarca(String numTentativiMarca) {
		this.numTentativiMarca = numTentativiMarca;
	}
	
	
}
