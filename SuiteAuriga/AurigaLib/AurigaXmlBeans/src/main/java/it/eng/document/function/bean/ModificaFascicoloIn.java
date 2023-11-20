/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModificaFascicoloIn extends AllegatiBean implements Serializable{

	private BigDecimal idFolderIn;
	private BigDecimal idUdAtto;	
	private BigDecimal idLibrary;
	private String nomeLibrary;
	private Boolean isCaricaPraticaPregressa;
	private XmlFascicoloIn modificaFascicolo;
	private String folderPath;
	private Flag flgAppendDocFascIstruttoria;
	
	public BigDecimal getIdFolderIn() {
		return idFolderIn;
	}
	public void setIdFolderIn(BigDecimal idFolderIn) {
		this.idFolderIn = idFolderIn;
	}
	public BigDecimal getIdUdAtto() {
		return idUdAtto;
	}
	public void setIdUdAtto(BigDecimal idUdAtto) {
		this.idUdAtto = idUdAtto;
	}
	public BigDecimal getIdLibrary() {
		return idLibrary;
	}
	public void setIdLibrary(BigDecimal idLibrary) {
		this.idLibrary = idLibrary;
	}
	public String getNomeLibrary() {
		return nomeLibrary;
	}
	public void setNomeLibrary(String nomeLibrary) {
		this.nomeLibrary = nomeLibrary;
	}
	public Boolean getIsCaricaPraticaPregressa() {
		return isCaricaPraticaPregressa;
	}
	public void setIsCaricaPraticaPregressa(Boolean isCaricaPraticaPregressa) {
		this.isCaricaPraticaPregressa = isCaricaPraticaPregressa;
	}
	public XmlFascicoloIn getModificaFascicolo() {
		return modificaFascicolo;
	}
	public void setModificaFascicolo(XmlFascicoloIn modificaFascicolo) {
		this.modificaFascicolo = modificaFascicolo;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public Flag getFlgAppendDocFascIstruttoria() {
		return flgAppendDocFascIstruttoria;
	}
	public void setFlgAppendDocFascIstruttoria(Flag flgAppendDocFascIstruttoria) {
		this.flgAppendDocFascIstruttoria = flgAppendDocFascIstruttoria;
	}

}
