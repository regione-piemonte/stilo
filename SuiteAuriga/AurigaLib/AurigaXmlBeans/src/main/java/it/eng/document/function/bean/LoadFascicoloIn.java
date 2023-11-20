/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoadFascicoloIn implements Serializable{

	private BigDecimal idFolderIn;
	private String flgSoloAbilAzioni;		
	private String flgFascInCreazione;	
	private String idNodeScrivania;	
	
	public BigDecimal getIdFolderIn() {
		return idFolderIn;
	}

	public void setIdFolderIn(BigDecimal idFolderIn) {
		this.idFolderIn = idFolderIn;
	}

	public String getFlgSoloAbilAzioni() {
		return flgSoloAbilAzioni;
	}

	public void setFlgSoloAbilAzioni(String flgSoloAbilAzioni) {
		this.flgSoloAbilAzioni = flgSoloAbilAzioni;
	}

	public String getFlgFascInCreazione() {
		return flgFascInCreazione;
	}

	public void setFlgFascInCreazione(String flgFascInCreazione) {
		this.flgFascInCreazione = flgFascInCreazione;
	}

	public String getIdNodeScrivania() {
		return idNodeScrivania;
	}

	public void setIdNodeScrivania(String idNodeScrivania) {
		this.idNodeScrivania = idNodeScrivania;
	}
	
}
