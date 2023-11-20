/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraRifImport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<SicraCodici> codici;
	
	public List<SicraCodici> getCodici() {
		return codici;
	}
	
	public void setCodici(List<SicraCodici> codici) {
		this.codici = codici;
	}
	
}
