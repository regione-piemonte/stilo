/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * Classe per la mappatura dei campi XMLDatiFileDaInviareOut (DMPK_GAE.GetFileEventiToSend) e XMLDatiFileRicevutoIn (DMPK_GAE.ArchiviaFileEventiRicevuto)
 * 
 * @author denbraga
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DatiFileInTransito {

	@XmlVariabile(nome = "#NomeFile", tipo = TipoVariabile.SEMPLICE)
	private String nomeFile;

	@XmlVariabile(nome = "#PathFile", tipo = TipoVariabile.SEMPLICE)
	private String pathFile;

	@XmlVariabile(nome = "#DimensioneFile", tipo = TipoVariabile.SEMPLICE)
	private String dimensioneFile;

	@XmlVariabile(nome = "#TsRicezioneFile", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsRicezioneFile;

	@XmlVariabile(nome = "#NomeFileSec", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileSec;

	@XmlVariabile(nome = "#PathFileSec", tipo = TipoVariabile.SEMPLICE)
	private String pathFileSec;

	@XmlVariabile(nome = "#CodSistemaDestinatarioSec", tipo = TipoVariabile.SEMPLICE)
	private String codSistemaDestinatarioSec;

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(String dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	public Date getTsRicezioneFile() {
		return tsRicezioneFile;
	}

	public void setTsRicezioneFile(Date tsRicezioneFile) {
		this.tsRicezioneFile = tsRicezioneFile;
	}

	public String getNomeFileSec() {
		return nomeFileSec;
	}

	public void setNomeFileSec(String nomeFileSec) {
		this.nomeFileSec = nomeFileSec;
	}

	public String getPathFileSec() {
		return pathFileSec;
	}

	public void setPathFileSec(String pathFileSec) {
		this.pathFileSec = pathFileSec;
	}

	public String getCodSistemaDestinatarioSec() {
		return codSistemaDestinatarioSec;
	}

	public void setCodSistemaDestinatarioSec(String codSistemaDestinatarioSec) {
		this.codSistemaDestinatarioSec = codSistemaDestinatarioSec;
	}
}
