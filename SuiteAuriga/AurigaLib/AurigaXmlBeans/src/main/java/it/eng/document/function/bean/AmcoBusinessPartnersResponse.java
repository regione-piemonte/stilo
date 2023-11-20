/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoBusinessPartnersResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<AmcoBusinessPartners> businessPartners;
	private String descrizioneErrore;
	
	public List<AmcoBusinessPartners> getBusinessPartners() {
		return businessPartners;
	}
	
	public void setBusinessPartners(List<AmcoBusinessPartners> businessPartners) {
		this.businessPartners = businessPartners;
	}
	
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	@Override
	public String toString() {
		return "AmcoBusinessPartnersResponse [businessPartners=" + businessPartners + ", descrizioneErrore="
				+ descrizioneErrore + "]";
	}
	
}
