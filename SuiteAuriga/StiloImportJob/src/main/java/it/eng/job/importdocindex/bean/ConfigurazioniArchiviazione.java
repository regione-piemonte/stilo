/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.eng.utility.storageutil.StorageService;

/**
 * Configurazioni specifiche per l'archiviazione del file
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class ConfigurazioniArchiviazione {

	/**
	 * Abilita archiviazione con StorageUtil
	 */

	private Boolean abilitaArchiviazioneStorageUtil;

	/**
	 * Implementazione da usare per StorageUtil
	 */

	@XmlTransient
	private StorageService storageUtilImpl;

	/**
	 * Abilita controllo MimeType per i file referenziati dal file indice
	 */

	private Boolean abilitaControlloMimeTypeFileReferenziati;

	/**
	 * MimeType di default, se valorizzato bypassa automaticamente il controllo del MimeType (solo per i file referenziati dai file indice, per il file indice
	 * il contrllo è sempre effettuato)
	 */

	private String defaultMimeTypeFileReferenziati;

	/**
	 * Abilita calcolo impronta con algoritmo e encoding sotto indicati
	 */

	private Boolean abilitaCalcoloImpronta;

	/**
	 * Encoding da utilizzare per il calcolo dell'impronta
	 */

	private String encodingImpronta;

	/**
	 * Algoritmo da utilizzare per il calcolo dell'impronta
	 */

	private String algoritmoImpronta;

	public Boolean getAbilitaArchiviazioneStorageUtil() {
		return abilitaArchiviazioneStorageUtil;
	}

	public void setAbilitaArchiviazioneStorageUtil(Boolean abilitaArchiviazioneStorageUtil) {
		this.abilitaArchiviazioneStorageUtil = abilitaArchiviazioneStorageUtil;
	}

	@XmlTransient
	public StorageService getStorageUtilImpl() {
		return storageUtilImpl;
	}

	public void setStorageUtilImpl(StorageService storageUtilImpl) {
		this.storageUtilImpl = storageUtilImpl;
	}

	public Boolean getAbilitaControlloMimeTypeFileReferenziati() {
		return abilitaControlloMimeTypeFileReferenziati;
	}

	public void setAbilitaControlloMimeTypeFileReferenziati(Boolean abilitaControlloMimeTypeFileReferenziati) {
		this.abilitaControlloMimeTypeFileReferenziati = abilitaControlloMimeTypeFileReferenziati;
	}

	public String getDefaultMimeTypeFileReferenziati() {
		return defaultMimeTypeFileReferenziati;
	}

	public void setDefaultMimeTypeFileReferenziati(String defaultMimeTypeFileReferenziati) {
		this.defaultMimeTypeFileReferenziati = defaultMimeTypeFileReferenziati;
	}

	public Boolean getAbilitaCalcoloImpronta() {
		return abilitaCalcoloImpronta;
	}

	public void setAbilitaCalcoloImpronta(Boolean abilitaCalcoloImpronta) {
		this.abilitaCalcoloImpronta = abilitaCalcoloImpronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

}
