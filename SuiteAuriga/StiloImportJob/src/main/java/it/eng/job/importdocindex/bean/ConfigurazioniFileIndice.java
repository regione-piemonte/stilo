/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.job.importdocindex.constants.FilePostManageActionEnum;
import it.eng.job.importdocindex.constants.IndexFileTypeEnum;

/**
 * Configurazioni specifiche per l'elaborazione di un file indice
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class ConfigurazioniFileIndice {

	/**
	 * Valore di default colonna file indice con riferimento URI file <br>
	 * L'indice delle colonna parte da 0 <br>
	 * L'ultima colonna si può genericamente indicare con -1
	 */

	public static final Integer DEFAULT_COLONNA_URI_FILE_INDICE = -1;

	/**
	 * Valore di default prima riga dati nel file indice <br>
	 * L'indice delle righe parte da 0
	 */

	public static final Integer DEFAULT_RIGA_DATI_FILE_INDICE = 0;

	/**
	 * Valore di default azione sul file indice post elaborazione
	 */

	public static final FilePostManageActionEnum DEFAULT_AZIONE_FILE_INDICE = FilePostManageActionEnum.KEEP;

	public static final Character DEFAULT_SEPARATORE = ';';

	/**
	 * Formato del file indice
	 */
	private IndexFileTypeEnum tipoFileIndice;

	/**
	 * Colonna del file indice da utilizzare contenente il percorso del file da elaborare
	 */

	private Integer colonnaURIFileIndice = DEFAULT_COLONNA_URI_FILE_INDICE;

	/**
	 * Riga del file indice da cui partire per la lettura dei dati
	 */

	private Integer rigaDatiFileIndice = DEFAULT_RIGA_DATI_FILE_INDICE;

	/**
	 * Azione da eseguire al termine dell'elaborazione del file indice
	 */

	private FilePostManageActionEnum azioneFileIndice = DEFAULT_AZIONE_FILE_INDICE;

	/**
	 * Separatore fra i dati del csv
	 */

	private Character separatore;

	public IndexFileTypeEnum getTipoFileIndice() {
		return tipoFileIndice;
	}

	public void setTipoFileIndice(IndexFileTypeEnum tipoFileIndice) {
		this.tipoFileIndice = tipoFileIndice;
	}

	public Integer getColonnaURIFileIndice() {
		return colonnaURIFileIndice;
	}

	public void setColonnaURIFileIndice(Integer colonnaURIFileIndice) {
		this.colonnaURIFileIndice = colonnaURIFileIndice;
	}

	public Integer getRigaDatiFileIndice() {
		return rigaDatiFileIndice;
	}

	public void setRigaDatiFileIndice(Integer rigaDatiFileIndice) {
		this.rigaDatiFileIndice = rigaDatiFileIndice;
	}

	public FilePostManageActionEnum getAzioneFileIndice() {
		return azioneFileIndice;
	}

	public void setAzioneFileIndice(FilePostManageActionEnum azioneFileIndice) {
		this.azioneFileIndice = azioneFileIndice;
	}

	public Character getSeparatore() {
		return separatore;
	}

	public void setSeparatore(Character separatore) {
		this.separatore = separatore;
	}

}
