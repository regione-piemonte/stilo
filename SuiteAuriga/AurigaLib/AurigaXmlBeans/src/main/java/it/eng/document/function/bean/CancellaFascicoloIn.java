/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CancellaFascicoloIn implements Serializable{

	private BigDecimal idFolderIn;
	private BigDecimal idLibrary;
	private String nomeLibrary;
	private String folderPath;
	private int flgCancFisicaIn;
	public BigDecimal getIdFolderIn() {
		return idFolderIn;
	}
	public BigDecimal getIdLibrary() {
		return idLibrary;
	}
	public String getNomeLibrary() {
		return nomeLibrary;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public int getFlgCancFisicaIn() {
		return flgCancFisicaIn;
	}
	public void setIdFolderIn(BigDecimal idFolderIn) {
		this.idFolderIn = idFolderIn;
	}
	public void setIdLibrary(BigDecimal idLibrary) {
		this.idLibrary = idLibrary;
	}
	public void setNomeLibrary(String nomeLibrary) {
		this.nomeLibrary = nomeLibrary;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public void setFlgCancFisicaIn(int flgCancFisicaIn) {
		this.flgCancFisicaIn = flgCancFisicaIn;
	}
	
	}
