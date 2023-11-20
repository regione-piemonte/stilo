/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraLiquidazioneOrdinativo extends SicraLiquidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<SicraOrdinativo> ordinativi;

	public List<SicraOrdinativo> getOrdinativi() {
		return ordinativi;
	}

	public void setOrdinativi(List<SicraOrdinativo> ordinativi) {
		this.ordinativi = ordinativi;
	}
	
}
